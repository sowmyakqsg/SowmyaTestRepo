package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;
import static org.junit.Assert.*;
import org.junit.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/****************************************************************************
' Description       :This class includes requirement testcases
' Requirement Group :Multi Region
' Requirement       :Multi Region- User with Login access to multiple regions
' Date		        :13-Nov-2012
' Author	        :QSG
'----------------------------------------------------------------------------
' Modified Date                                                 Modified By
' <Date>                           	                            <Name>
'****************************************************************************/

public class MultiRegionUserWithLoginAccessToMultipleRegions {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.MultiRegionUserWithLoginAccessToMultipleRegions");
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
	String gstrTimeOut;

	Selenium selenium, seleniumPrecondition;

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
				propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));

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

		try{
			selenium.close();
		}catch(Exception e){
			
		}	
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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	/***********************************************************************************************
	'Description	:User U1 has login access to regions A & B and has added resource and associated 
	'				status types to custom view. After removing the user's role in region A, verify 
	'				that user CANNOT view/add status types on/to custom view from region B.
	'Arguments		:None
	'Returns		:None
	'Date	 		:16-Nov-2012
	'Author			:QSG
	'------------------------------------------------------------------------------------------------
	'Modified Date                                                                  Modified By
	'<Date>                                                                         <Name>
	*************************************************************************************************/

	@Test
	public void testBQS42183() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		General objGeneral = new General();
		MobileView objMobileView = new MobileView();

		try {
			gstrTCID = "BQS-42183 ";
			gstrTO = "User U1 has login access to regions A & B and has added resource"
					+ " and associated status types to custom view. After removing the user"
					+ "'s role in region A, verify that user CANNOT view/add status types "
					+ "on/to custom view from region B.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + System.currentTimeMillis();
			String strRTVal = "";

			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName, statpNumTypeName,
					statpTextTypeName, statpMultiTypeName,
					statpSaturatTypeName, statsNumTypeName, statsTextTypeName,
					statsMultiTypeName, statsSaturatTypeName };
	/*
	 * 'Precondition:1. User U1 has access to Region RG1 and RG2.
			    2. RG1 and RG2 do NOT have other region view agreement.						
				3. Email and pager addresses are provided for user U1
				4. User U1 has the right to 'Edit Status Change Preferences' in RG2.
				5. In Region RG1:
				Resource type RT1 is associated with the following status types which are ROLE based and PRIVATE:						
						rNST1 (ROLE based Number),
						rMST1 (ROLE based Multi),
						rTST1 (ROLE based Text),
						rSST1 (ROLE based Saturation score),pNST1 (PRIVATE Number),
						                                    pMST1 (PRIVATE Multi),
						                                    pTST1 (PRIVATE Text),
						                                    pSST1 (PRIVATE Saturation score)						
						sNST1 (SHARED Number),
						sMST1 (SHARED Multi),
						sTST1 (SHARED Text),
						sSST1 (SHARED Saturation score)						
				6. Resource RS is created under RT1 with address.
				7. RS is NOT shared.
				8. User U1 has 'View Resources', 'Update Status', 'Associated with' and 'Run Report' right on 
				resource RS and a role ROL to view all the above (role based and shared) status types.
				9. Status Change Preferences are set for user U1 in region RG2 selecting resources RS and
				 all the above mentioned status types (role based and private) 
 */
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/* 1. User U1 has access to Region RG1 and RG2. */

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5. In Region RG1:
			 * 
			 * Resource type RT1 is associated with the following status types
			 * which are ROLE based, PRIVATE and SHARED: rNST1 (ROLE based
			 * Number), rMST1 (ROLE based Multi), rTST1 (ROLE based Text), rSST1
			 * (ROLE based Saturation score)
			 * 
			 * pNST1 (PRIVATE Number), pMST1 (PRIVATE Multi), pTST1 (PRIVATE
			 * Text), pSST1 (PRIVATE Saturation score)
			 * 
			 * sNST1 (SHARED Number), sMST1 (SHARED Multi), sTST1 (SHARED Text),
			 * sSST1 (SHARED Saturation score)
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Shared status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statsNumTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statsTextTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statsSaturatTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statsMultiTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
/*
			 6. Resource RS is created under RT1 with address. 
			 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };
				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };

				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, false, false);

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
			 * 8. User U1 has 'View Resources', 'Update Status', 'Associated
			 * with' and 'Run Report' right on resource RS and a role ROL to
			 * view all the above (role based and shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType1, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
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

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
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

			// Fetach region values

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objRegion.navRegionList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRegionValue[0] = objRegion.fetchRegionValue(selenium,
						strRegn1);

				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strRegionValue[1] = objRegion.fetchRegionValue(selenium,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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

		/*	
			 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
			 * NOT have other region view agreement.
			 
*/
			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						selenium, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
		/*	 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
/*
			
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.*/
			 

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*	
			 * 8. Custom View is created in region RG2 for user U1 selecting
			 * resources RS and all the above mentioned status types (role
			 * based, private and shared).
			 
			
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(selenium,
						strRS, strResrctTypName1, statrNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (String s : str_roleStatusTypeValues) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objPreferences
							.navEditCustomViewPage(selenium);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objPreferences
							.navEditCustomViewOptionPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					String strSTValue[][] = { { s, "true" } };
					strFuncResult = objPreferences
							.addSTInEditCustViewOptionPage(selenium,
									strSTValue, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			
			
			for (String s : str_sharedStatusTypeValues) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objPreferences
							.navEditCustomViewPage(selenium);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objPreferences
							.navEditCustomViewOptionPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					String strSTValue[][] = { { s, "true" } };
					strFuncResult = objPreferences
							.addSTInEditCustViewOptionPage(selenium,
									strSTValue, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			
			
			for (String s : str_privateStatusTypeValues) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objPreferences
							.navEditCustomViewPage(selenium);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objPreferences
							.navEditCustomViewOptionPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					String strSTValue[][] = { { s, "true" } };
					strFuncResult = objPreferences
							.addSTInEditCustViewOptionPage(selenium,
									strSTValue, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			
/*
			
			 * 2 Login as user RegAdmin, select region RG1, navigate to Setup >>
			 * Users. 'Users List' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
			

	/*		
			 * 3 Click on 'Edit' link next to users U1, deselect role ROL and
			 * save. 'Users List' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
	/*		
			
			 * 4 Login as User U1 in RG2, navigate to Views >> Custom Private
			 * status types pNST1, pMST1, pSST1 and pTST1 are displayed for
			 * resource RS under resource type RT. Shared and role based status
			 * types are not displayed.
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
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr = { statpNumTypeName,
						statpTextTypeName, statpMultiTypeName,
						statpSaturatTypeName };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrctTypName1, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr = { statrNumTypeName,
						statrTextTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrctTypName1, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals(
						"Resource '"
								+ strResource
								+ "' is NOT displayed on the 'Cusotm View table' screen under "
								+ strResrctTypName1
								+ " along with all the status types.",
						strFuncResult);

				String[] strStatTypeArr = {  statsMultiTypeName,
						statsNumTypeName, statsSaturatTypeName,
						statsTextTypeName};
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTabl(selenium,
								strResrctTypName1, strResource, strStatTypeArr);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
/*			
			 * 5 Click on 'Customize' link. 'Edit Custom View' screen is
			 * displayed.
			 */
			
			try {
				assertEquals(
						"Resource '"
								+ strResource
								+ "' is NOT displayed on the 'Cusotm View table' screen under "
								+ strResrctTypName1
								+ " along with all the status types.",
						strFuncResult);

				strFuncResult = objViews.navEditCustomViewPageFromViewCustom(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	/*		
			
			 * 6 Click on 'Options' button. 'Edit Custom View Options (Columns)'
			 * screen is displayed. Private status types pNST1, pMST1, pSST1 and
			 * pTST1 are displayed. Shared and role based status types are not
			 * displayed.
			 */
			
			
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr = { statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };

				for (String s : strStatTypeArr) {
					strFuncResult = objPreferences
							.checkStatTypeInEditCustViewOptions(selenium, s);
					
					strFuncResult =objPreferences.navBackToEditCustomViewPge(selenium);
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypeArr = { statsMultiTypeName,
						statsNumTypeName, statsSaturatTypeName,
						statsTextTypeName };

				for (String s : strStatTypeArr) {
					strFuncResult = objPreferences
							.checkStatTypeInEditCustViewOptions(selenium, s);

					try {
						assertEquals(
								"Status Type "
										+ s
										+ " is NOT displayed in Edit Custom View Options (Columns)",
								strFuncResult);

						strFuncResult =objPreferences.navBackToEditCustomViewPge(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("",strFuncResult);
				

				String[] strStatTypeArr = { statrMultiTypeName,
						statrNumTypeName, statrSaturatTypeName,
						statrTextTypeName };

				for (String s : strStatTypeArr) {
					strFuncResult = objPreferences
							.checkStatTypeInEditCustViewOptions(selenium, s);

					try {
						assertEquals(
								"Status Type "
										+ s
										+ " is NOT displayed in Edit Custom View Options (Columns)",
								strFuncResult);
						strFuncResult =objPreferences.navBackToEditCustomViewPge(selenium);
					

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*	
			 * 7 Click on 'Show Map' link on the custom view screen. 'Custom
			 * View - Map' screen is displayed.
			 
			*/
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
	/*		 * 8 Select resource RS from the find resource drop down. Private
			 * status types pNST1, pMST1, pSST1 and pTST1 are displayed on the
			 * resource pop-up window for resource RS. Shared and role based
			 * status types are not displayed.
			 
		
				*/
			
			try {
				assertEquals("",strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = {statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
  	  		
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statrMultiTypeName,
						statrNumTypeName, statrSaturatTypeName,
						statrTextTypeName };

				for (String s : strRoleStatType) {

					String str[] = { s };
					strFuncResult = objViewMap
							.verifyStatTypesInResourcePopup_ShowMap(selenium,
									strResource, strEventStatType, str, false,
									true);
					try {
						assertEquals(" Role Based Status type "
										+ s
										+ " is NOT displayed", strFuncResult);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals(" Role Based Status type "
										+ statrTextTypeName
										+ " is NOT displayed", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statsMultiTypeName,
						statsNumTypeName, statsSaturatTypeName,
						statsTextTypeName };

				for (String s : strRoleStatType) {

					String str[] = { s };
					strFuncResult = objViewMap
							.verifyStatTypesInResourcePopup_ShowMap(selenium,
									strResource, strEventStatType, str, false,
									true);
					try {
						assertEquals(" Role Based Status type "
										+ s
										+ " is NOT displayed", strFuncResult);
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
		/*	
			 * 9 Navigate to Views >> Map. Resource is not displayed in the
			 * 'Find Resource' drop down.
			 
			*/
			try {
				assertEquals(" Role Based Status type " + statsTextTypeName
						+ " is NOT displayed", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("",strFuncResult);

				String strElementID="//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']";
				strFuncResult =objGeneral.checkForAnElements(selenium, strElementID);


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*	
			
			 * 10 Click on 'Mobile View' link. 'Main Menu' screen is displayed.
			 
			*/
			try {
				assertEquals("Element NOT Present", strFuncResult);

				log4j
						.info("Resource is NOT displayed in the 'Find Resource' drop down. ");
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				log4j
						.info("Resource is displayed in the 'Find Resource' drop down. ");
				strFuncResult = "Resource is displayed in the 'Find Resource' drop down. ";
				gstrReason = strFuncResult;
			}
			
		
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView
						.navToCustomPageInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
	/*		
			 * 11 Click on 'Resources' link and click on 'Custom' link. Resource
			 * RS is displayed on 'Custom' screen.
			 
			
			
			
			 * 12 Click on resource RS. Only private status types pNST1, pMST1,
			 * pSST1 and pTST1 are displayed under the appropriate sections.
			 * Role based and shared status types are not displayed.*/
			 
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objMobileView.chkSTInCustomPageInMobileView(
						selenium, strResource, strSection1, strStatTypeArr,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statsMultiTypeName,
						statsNumTypeName, statsSaturatTypeName,
						statsTextTypeName };
				strFuncResult = objMobileView.chkSTInCustomPageInMobileView(
						selenium, strResource, strSection1, strStatTypeArr,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals(statsTextTypeName+" is NOT displayed correctly " +
						"under section "+strSection1, strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNumTypeName, statrSaturatTypeName,
						statrTextTypeName };
				strFuncResult = objMobileView.chkSTInCustomPageInMobileView(
						selenium, strResource, strSection1, strStatTypeArr,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals(statrTextTypeName+" is NOT displayed correctly " +
						"under section "+strSection1, strFuncResult);
				strFuncResult="";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-42183";
			gstrTO = "User U1 has login access to regions A & B and has added resource and" +
					" associated status types to custom view. After removing the user's role" +
					" in region A, verify that user CANNOT view/add status types on/to custom" +
					" view from region B.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	/*******************************************************************
	'Description	:User U1 has login access to regions A & B. Verify that user 
					CANNOT view/edit status change preferences for private status types of region
					 A from region B when affiliated resource rights for the resource are removed 
					 in region A.
	'Precondition	:1. User U1 has access to Region RG1 and RG2.
					2. RG1 and RG2 do NOT have other region view agreement.
					
					3. Email and pager addresses are provided for user U1
					4. User U1 has the right to 'Edit Status Change Preferences' in RG2.
					
					5. In Region RG1:
					
					Resource type RT1 is associated with the following status types which are ROLE based and PRIVATE:
					
					rNST1 (ROLE based Number),
					rMST1 (ROLE based Multi),
					rTST1 (ROLE based Text),
					rSST1 (ROLE based Saturation score)
					
					pNST1 (PRIVATE Number),
					pMST1 (PRIVATE Multi),
					pTST1 (PRIVATE Text),
					pSST1 (PRIVATE Saturation score)
					
					6. Resource RS is created under RT1 with address.
					7. RS is NOT shared.
					8. User U1 has 'View Resources', 'Update Status', 'Associated with' and 'Run Report' right on resource RS and a role ROL to view all the above (role based and shared) status types.
					9. Status Change Preferences are set for user U1 in region RG2 selecting resources RS and all the above mentioned status types (role based and private) 
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-Nov-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS50625() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();

		try {
			gstrTCID = "BQS-50625 ";
			gstrTO = "User U1 has login access to regions A & B. Verify that user "
					+ "CANNOT view/edit status change preferences for private status "
					+ "types of region A from region B when affiliated resource rights"
					+ " for the resource are removed in region A.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + System.currentTimeMillis();
			String strRTVal = "";

			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/* 1. User U1 has access to Region RG1 and RG2. */

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. In Region RG1:
			 * 
			 * Resource type RT1 is associated with the following status types
			 * which are ROLE based and PRIVATE:
			 * 
			 * rNST1 (ROLE based Number), rMST1 (ROLE based Multi), rTST1 (ROLE
			 * based Text), rSST1 (ROLE based Saturation score)
			 * 
			 * pNST1 (PRIVATE Number), pMST1 (PRIVATE Multi), pTST1 (PRIVATE
			 * Text), pSST1 (PRIVATE Saturation score)
			 * 
			 * 6. Resource RS is created under RT1 with address. 7. RS is NOT
			 * shared. 8. User U1 has 'View Resources', 'Update Status',
			 * 'Associated with' and 'Run Report' right on resource RS and a
			 * role ROL to view all the above (role based and shared) status
			 * types. 9. Status Change Preferences are set for user U1 in region
			 * RG2 selecting resources RS and all the above mentioned status
			 * types (role based and private)
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6. Resource RS is created under RT1 with address. */
			/* 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3] };
				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3] };

				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, false, false);

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
			 * 8. User U1 has 'View Resources', 'Update Status', 'Associated
			 * with' and 'Run Report' right on resource RS and a role ROL to
			 * view all the above (role based and shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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
						seleniumPrecondition, strResource, strRSValue[0], true, true, true,
						true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Email and pager addresses are provided for user U1 */

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetach region values

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegion.navRegionList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);

				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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

			/*
			 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
			 * NOT have other region view agreement.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
			 */

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. Status Change Preferences are set for user U1 in region RG2
			 * selecting resources RS and all the above mentioned status types
			 * (role based and private)
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST Role based status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_roleStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_roleStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST Role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private status types

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_privateStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_privateStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST Role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user RegAdmin, select region RG1, navigate to Setup >>
			 * Users. 'Users List' screen is displayed.
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

			/*
			 * 3 Click on 'Edit' link next to users U1, deselect 'Update
			 * Status', 'Associated with' and 'Run report' check boxes and save.
			 * 'Users List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as User U1 in RG2, navigate to Preferences >> Status
			 * Change Prefs 'My Status Change Preferences' screen is displayed.
			 * Previously set status change preferences for role based status
			 * types are displayed.
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
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statrMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statrMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statrSaturatTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statrSaturatTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on 'Edit' link next to resource RS. Role based status
			 * types are displayed on 'Edit My Status Change Preferences'
			 * screen. Private status types are not displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "100", "true", "true", "true" },
						{ "50", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefsNew(selenium,
								statrNumTypeName, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "400", "true", "true", "true" },
						{ "200", "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefsNew(selenium,
								statrSaturatTypeName, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = {
						{ str_roleStatusName1, "true", "true", "true" },
						{ str_roleStatusName2, "true", "true", "true" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefsMST(selenium,
								statrMultiTypeName, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statpSaturatTypeName
								+ " is NOT displayed in Edit My Status Change Preferences page ",
						strFuncResult);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-50625";
			gstrTO = "User U1 has login access to regions A & B. Verify that user CANNOT "
					+ "view/edit status change preferences for private status types of "
					+ "region A from region B when affiliated resource rights for the "
					+ "resource are removed in region A.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	
	/*******************************************************************
	'Description	:User U1 has login access to regions A & B. Verify that, after removing the �View Resource�
	'				 right for the in region A, user CANNOT view/edit status change preferences for resource
	'				 from region B.
	'Precondition	:1. User U1 has access to Region RG1 and RG2.
					2. RG1 and RG2 do NOT have other region view agreement.
					
					3. Email and pager addresses are provided for user U1
					4. User U1 has the right to 'Edit Status Change Preferences' in RG2.
					
					5. In Region RG1:
					
					Resource type RT1 is associated with the following status types which are ROLE based and PRIVATE:
					
					rNST1 (ROLE based Number),
					rMST1 (ROLE based Multi),
					rTST1 (ROLE based Text),
					rSST1 (ROLE based Saturation score)
					
					pNST1 (PRIVATE Number),
					pMST1 (PRIVATE Multi),
					pTST1 (PRIVATE Text),
					pSST1 (PRIVATE Saturation score)
					
					sNST1 (SHARED Number),
					sMST1 (SHARED Multi),
					sTST1 (SHARED Text),
					sSST1 (SHARED Saturation score)

					
					6. Resource RS is created under RT1 with address.
					7. RS is NOT shared.
					8. User U1 has 'View Resources', 'Update Status', 'Associated with' and 'Run Report' right on resource RS and a role ROL to view all the above (role based and shared) status types.
					9. Status Change Preferences are set for user U1 in region RG2 selecting resources RS and all the above mentioned status types (role based and private) 
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-Nov-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS46577() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();
		General objGeneral=new General();
		

		try {
			gstrTCID = "BQS-46577 ";
			gstrTO = "User U1 has login access to regions A & B. Verify that, after" +
					" removing the �View Resource� right for the in region A, user " +
					"CANNOT view/edit status change preferences for resource from" +
					" region B.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";
			
			//Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + System.currentTimeMillis();
			String strRTVal = "";

			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/* 1. User U1 has access to Region RG1 and RG2. */

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*5. In Region RG1:

				Resource type RT1 is associated with the following status types which are ROLE based,
				 PRIVATE and SHARED:
				rNST1 (ROLE based Number),
				rMST1 (ROLE based Multi),
				rTST1 (ROLE based Text),
				rSST1 (ROLE based Saturation score)

				pNST1 (PRIVATE Number),
				pMST1 (PRIVATE Multi),
				pTST1 (PRIVATE Text),
				pSST1 (PRIVATE Saturation score)

				sNST1 (SHARED Number),
				sMST1 (SHARED Multi),
				sTST1 (SHARED Text),
				sSST1 (SHARED Saturation score) */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Role based status type
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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			// Shared status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue,statsNumTypeName , strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statsTextTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statsSaturatTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statsMultiTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");
				
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6. Resource RS is created under RT1 with address. */
			/* 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {
						str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						
						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };
				String[] strViewRightValue = {
						str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						
						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };

				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, false, false);

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
			 * 8. User U1 has 'View Resources', 'Update Status', 'Associated
			 * with' and 'Run Report' right on resource RS and a role ROL to
			 * view all the above (role based and shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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
						seleniumPrecondition, strResource, strRSValue[0], true, true, true,
						true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Email and pager addresses are provided for user U1 */

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetach region values

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegion.navRegionList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);

				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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

			/*
			 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
			 * NOT have other region view agreement.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
			 */

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. Status Change Preferences are set for user U1 in region RG2
			 * selecting resources RS and all the above mentioned status types
			 * (role based and private)
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST Role based status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_roleStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_roleStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST Role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private status types

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_privateStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_privateStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Shared status types

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[0],
								statsNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[0],
								statsNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_sharedStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[2],
								statsSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[2],
								statsSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_sharedStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[3],
								statsMultiTypeName, str_sharedStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[3],
								statsMultiTypeName, str_sharedStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			 * 2 Login as user RegAdmin, select region RG1, navigate to Setup >>
			 * Users. 'Users List' screen is displayed.
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

			/*
			 * 3 Click on 'Edit' link next to users U1, deselect 'View Resource'
			 * right and save. 'Users List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as User U1 in RG2, navigate to Preferences >> Status
			 * Change Prefs 'My Status Change Preferences' screen is displayed.
			 * Previously set status change preferences for role based, shared
			 * and private status types are not displayed. Role base, shared and
			 * private status types are not displayed.
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
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statrNumTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statpMultiTypeName,
						statpSaturatTypeName, statsNumTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			 * 5 Navigate to Preferences >> Status Change Prefs 'My Status
			 * Change Preferences' screen is displayed.
			 */
			

			try {
				assertEquals(
						" Status Type "
								+ statsSaturatTypeName
								+ " is NOT displayed in Edit My Status Change Preferences page ",
						strFuncResult);

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Click on 'Add' button, provide resource RS in the 'name' field.
			 * 'No resources match your criteria...' message is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences.navToFindResourcesPage(selenium);
				selenium.type("css=#searchName", strResource);
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='mainContainer']/form/table/tbody/tr/td/h2";
				strFuncResult = objGeneral.assertEQUALS(selenium, strElementID,
						"No resources match your criteria...");
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
			gstrTCID = "BQS-46577";
			gstrTO = "User U1 has login access to regions A & B. Verify that, after removing " +
					"the �View Resource� right for the in region A, user CANNOT view/edit " +
					"status change preferences for resource from region B.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	
	/*******************************************************************
	'Description	:For a user U1 who has login access to regions A and B, deselect status 
	'				types in the �Refine� window from region A and save. Verify that user cannot 
	'				view/edit status change preferences for the status type from region B.
	'Precondition	:1. User U1 has access to Region RG1 and RG2.
					2. RG1 and RG2 do NOT have other region view agreement.
					
					3. Email and pager addresses are provided for user U1
					4. User U1 has the right to 'Edit Status Change Preferences' in RG2.
					
					5. In Region RG1:
					
					Resource type RT1 is associated with the following status types which are ROLE based and PRIVATE:
					
					rNST1 (ROLE based Number),
					rMST1 (ROLE based Multi),
					rTST1 (ROLE based Text),
					rSST1 (ROLE based Saturation score)
					
					pNST1 (PRIVATE Number),
					pMST1 (PRIVATE Multi),
					pTST1 (PRIVATE Text),
					pSST1 (PRIVATE Saturation score)
					
					sNST1 (SHARED Number),
					sMST1 (SHARED Multi),
					sTST1 (SHARED Text),
					sSST1 (SHARED Saturation score)

					
					6. Resource RS is created under RT1 with address.
					7. RS is NOT shared.
					8. User U1 has 'View Resources', 'Update Status', 'Associated with' and 'Run Report' right on resource RS and a role ROL to view all the above (role based and shared) status types.
					9. Status Change Preferences are set for user U1 in region RG2 selecting resources RS and all the above mentioned status types (role based and private) 
	'Arguments		:None
	'Returns		:None
	'Date	 		:15-Nov-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS46576() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();

		try {
			gstrTCID = "BQS-46576 ";
			gstrTO = "For a user U1 who has login access to regions A and B, deselect"
					+ " status types in the �Refine� window from region A and save. Verify"
					+ " that user cannot view/edit status change preferences for the status"
					+ " type from region B.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + System.currentTimeMillis();
			String strRTVal = "";

			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/* 1. User U1 has access to Region RG1 and RG2. */

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5. In Region RG1:
			 * 
			 * Resource type RT1 is associated with the following status types
			 * which are ROLE based, PRIVATE and SHARED: rNST1 (ROLE based
			 * Number), rMST1 (ROLE based Multi), rTST1 (ROLE based Text), rSST1
			 * (ROLE based Saturation score)
			 * 
			 * pNST1 (PRIVATE Number), pMST1 (PRIVATE Multi), pTST1 (PRIVATE
			 * Text), pSST1 (PRIVATE Saturation score)
			 * 
			 * sNST1 (SHARED Number), sMST1 (SHARED Multi), sTST1 (SHARED Text),
			 * sSST1 (SHARED Saturation score)
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Shared status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statsNumTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statsTextTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statsSaturatTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statsMultiTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6. Resource RS is created under RT1 with address. */
			/* 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };
				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };

				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, false, false);

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
			 * 8. User U1 has 'View Resources', 'Update Status', 'Associated
			 * with' and 'Run Report' right on resource RS and a role ROL to
			 * view all the above (role based and shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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

			/* 3. Email and pager addresses are provided for user U1 */

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetach region values

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegion.navRegionList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);

				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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

			/*
			 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
			 * NOT have other region view agreement.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
			 */

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. Status Change Preferences are set for user U1 in region RG2
			 * selecting resources RS and all the above mentioned status types
			 * (role based and private)
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST Role based status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_roleStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_roleStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST Role based status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private status types

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_privateStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_privateStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Shared status types

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[0],
								statsNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[0],
								statsNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								str_sharedStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[2],
								statsSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[2],
								statsSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_sharedStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[3],
								statsMultiTypeName, str_sharedStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[3],
								statsMultiTypeName, str_sharedStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user RegAdmin, select region RG1, navigate to Setup >>
			 * Users. 'Users List' screen is displayed.
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

			/*
			 * 3 Click on 'Edit' link next to users U1, select the link 'Refine'
			 * corresponding to resource RS, deselect all role based and shared
			 * status types and save the user. 'Users List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				for (String s : str_roleStatusTypeValues) {

					strFuncResult = objCreateUsers
							.selAndDeselSTInRefineVisibleST(selenium, s, false);
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				for (String s : str_sharedStatusTypeValues) {

					strFuncResult = objCreateUsers
							.selAndDeselSTInRefineVisibleST(selenium, s, false);
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as User U1 in RG2, navigate to Preferences >> Status
			 * Change Prefs 'My Status Change Preferences' screen is displayed.
			 * Previously set status change preferences are displayed only for
			 * private status types pNST1, pMST1, pTST1 and pSST1 Status change
			 * preferences set for shared and role based status types are not
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
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statpSaturatTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statpSaturatTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Role based status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statrNumTypeName, "Above: 100", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statrNumTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);

				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statrMultiTypeName, str_roleStatusName1, "X",
								"X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statrMultiTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);

				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statrSaturatTypeName, "Above: 400", "X", "X",
								"X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Shared status types

			try {
				assertEquals(
						" Status Type "
								+ statrSaturatTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statsNumTypeName, "Above: 100", "X", "X", "X");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsNumTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statsMultiTypeName, str_sharedStatusName1, "X",
								"X", "X");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsMultiTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statsSaturatTypeName, "Above: 400", "X", "X",
								"X");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsSaturatTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on 'Edit' link next to resource RS. 'Edit My Status
			 * Change Preferences' screen is displayed. Only private status
			 * types pNST1, pMST1, pTST1 and pSST1 are displayed. Shared and
			 * role based status types are not displayed.
			 */
			

			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statpNumTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statrNumTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statsNumTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsSaturatTypeName
								+ " is NOT displayed in Edit My Status Change Preferences page ",
						strFuncResult);

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on 'Add' search for resource RS and click on
			 * 'Notifications' button Role based and shared status types are are
			 * not displayed in the 'Edit My Status Change Preferences' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statpNumTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statrNumTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statsNumTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals(
						" Status Type "
								+ statsSaturatTypeName
								+ " is NOT displayed in Edit My Status Change Preferences page ",
						strFuncResult);

				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-46576";
			gstrTO = "For a user U1 who has login access to regions A and B, deselect "
					+ "status types in the �Refine� window from region A and save."
					+ " Verify that user cannot view/edit status change preferences for "
					+ "the status type from region B.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}

	
	
	
	/*******************************************************************
	'Description	:User U1 has login access to regions A & B and has status change preferences set
	'				 for a resource. After removing the user's role in region A, verify that user 
	'				CANNOT view/edit status change preferences for those status types from region B.
	'Precondition	:1. User U1 has access to Region RG1 and RG2.
					2. RG1 and RG2 do NOT have other region view agreement.
					
					3. Email and pager addresses are provided for user U1
					4. User U1 has the right to 'Edit Status Change Preferences' in RG2.
					
					5. In Region RG1:
					
					Resource type RT1 is associated with the following status types which are ROLE based and PRIVATE:
					
					rNST1 (ROLE based Number),
					rMST1 (ROLE based Multi),
					rTST1 (ROLE based Text),
					rSST1 (ROLE based Saturation score)
					
					pNST1 (PRIVATE Number),
					pMST1 (PRIVATE Multi),
					pTST1 (PRIVATE Text),
					pSST1 (PRIVATE Saturation score)
					
					sNST1 (SHARED Number),
					sMST1 (SHARED Multi),
					sTST1 (SHARED Text),
					sSST1 (SHARED Saturation score)

					
					6. Resource RS is created under RT1 with address.
					7. RS is NOT shared.
					8. User U1 has 'View Resources', 'Update Status', 'Associated with' and 'Run Report' right on resource RS and a role ROL to view all the above (role based and shared) status types.
					9. Status Change Preferences are set for user U1 in region RG2 selecting resources RS and all the above mentioned status types (role based and private) 
	'Arguments		:None
	'Returns		:None
	'Date	 		:16-Nov-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS43656() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();

		try {
			gstrTCID = "BQS-43656 ";
			gstrTO = "User U1 has login access to regions A & B and has status change" +
					" preferences set for a resource. After removing the user's role " +
					"in region A, verify that user CANNOT view/edit status change " +
					"preferences for those status types from region B.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + System.currentTimeMillis();
			String strRTVal = "";

			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/* 1. User U1 has access to Region RG1 and RG2. */

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5. In Region RG1:
			 * 
			 * Resource type RT1 is associated with the following status types
			 * which are ROLE based, PRIVATE and SHARED: rNST1 (ROLE based
			 * Number), rMST1 (ROLE based Multi), rTST1 (ROLE based Text), rSST1
			 * (ROLE based Saturation score)
			 * 
			 * pNST1 (PRIVATE Number), pMST1 (PRIVATE Multi), pTST1 (PRIVATE
			 * Text), pSST1 (PRIVATE Saturation score)
			 * 
			 * sNST1 (SHARED Number), sMST1 (SHARED Multi), sTST1 (SHARED Text),
			 * sSST1 (SHARED Saturation score)
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Shared status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statsNumTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statsTextTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statsSaturatTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statsMultiTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6. Resource RS is created under RT1 with address. */
			/* 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };
				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };

				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, false, false);

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
			 * 8. User U1 has 'View Resources', 'Update Status', 'Associated
			 * with' and 'Run Report' right on resource RS and a role ROL to
			 * view all the above (role based and shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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

			/* 3. Email and pager addresses are provided for user U1 */

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetach region values

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegion.navRegionList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);

				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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

			/*
			 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
			 * NOT have other region view agreement.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 has the right to 'Edit Status Change Preferences' in
			 * RG2.
			 */

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. Status Change Preferences are set for user U1 in region RG2
			 * selecting resources RS and all the above mentioned status types
			 * (role based and private)
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
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST Role based status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(seleniumPrecondition,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(seleniumPrecondition,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[0], statrNumTypeName,
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[0],
								str_roleStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST role based status type

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
						.selectSTChangeNotifInEditMySTNotifPage(seleniumPrecondition,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
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
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[2],
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_roleStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST Role based status type

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
						.selectSTChangeNotifInEditMySTNotifPageOfMST(seleniumPrecondition,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(seleniumPrecondition,
								strResource, strRSValue[0],
								str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private status types

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
						.selectSTChangeNotifInEditMySTNotifPage(seleniumPrecondition,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
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
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[0],
								statpNumTypeName, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[0],
								str_privateStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST private status type

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
						.selectSTChangeNotifInEditMySTNotifPage(seleniumPrecondition,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
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
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[2],
								statpSaturatTypeName, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_privateStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST private status type

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
						.selectSTChangeNotifInEditMySTNotifPageOfMST(seleniumPrecondition,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[0],
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(seleniumPrecondition,
								strResource, strRSValue[0],
								str_privateStatusTypeValues[3],
								statpMultiTypeName, str_privateStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Shared status types

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
						.selectSTChangeNotifInEditMySTNotifPage(seleniumPrecondition,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[0],
								statsNumTypeName, true, true, true);
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
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[0],
								statsNumTypeName, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strAbove, strBelow, strRSValue[0],
								str_sharedStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST private status type

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
						.selectSTChangeNotifInEditMySTNotifPage(seleniumPrecondition,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[2],
								statsSaturatTypeName, true, true, true);
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
						.selectSTChangeNotifInEditMySTNotifPageBelow(seleniumPrecondition,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[2],
								statsSaturatTypeName, true, true, true);
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
						.provideSTAboveBelowRangeInEditMySTNotifPage(seleniumPrecondition,
								strSaturAbove, strSaturBelow, strRSValue[0],
								str_sharedStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST private status type

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
						.selectSTChangeNotifInEditMySTNotifPageOfMST(seleniumPrecondition,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[3],
								statsMultiTypeName, str_sharedStatusValue[0],
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(seleniumPrecondition,
								strResource, strRSValue[0],
								str_sharedStatusTypeValues[3],
								statsMultiTypeName, str_sharedStatusValue[1],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user RegAdmin, select region RG1, navigate to Setup >>
			 * Users. 'Users List' screen is displayed.
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

			/*
			 * 3 Click on 'Edit' link next to user U1, deselect role ROL and
			 * save. 'Users List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as User U1 in RG2, navigate to Preferences >> Status
			 * Change Prefs 'My Status Change Preferences' screen is displayed.
			 * Previously set status change preferences are displayed only for
			 * private status types pNST1, pMST1, pTST1 and pSST1 Status change
			 * preferences set for shared and role based status types are not
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
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statpNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statpMultiTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageAbove(selenium,
								strResource, statpSaturatTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifyNotifInEditMySTNotifPageBelow(selenium,
								strResource, statpSaturatTypeName, true, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Role based status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statrNumTypeName, "Above: 100", "X", "X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statrNumTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);

				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statrMultiTypeName, str_roleStatusName1, "X",
								"X", "X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statrMultiTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);

				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statrSaturatTypeName, "Above: 400", "X", "X",
								"X");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Shared status types

			try {
				assertEquals(
						" Status Type "
								+ statrSaturatTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statsNumTypeName, "Above: 100", "X", "X", "X");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsNumTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statsMultiTypeName, str_sharedStatusName1, "X",
								"X", "X");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsMultiTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInMyStatusChangePrefs(selenium,
								statsSaturatTypeName, "Above: 400", "X", "X",
								"X");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsSaturatTypeName
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on 'Edit' link next to resource RS. 'Edit My Status
			 * Change Preferences' screen is displayed. Only private status
			 * types pNST1, pMST1, pTST1 and pSST1 are displayed. Shared and
			 * role based status types are not displayed.
			 */
			

			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statpNumTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatusType = { statrNumTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statsNumTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences
						.checkOnlyStatTypesInEditMyStatChangePrefs(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statsSaturatTypeName
								+ " is NOT displayed in Edit My Status Change Preferences page ",
						strFuncResult);

				assertEquals("", gstrReason);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-43656";
			gstrTO = "User U1 has login access to regions A & B and has status change " +
					"'preferences set for a resource. After removing the user's role " +
					"in region A, verify that user CANNOT view/edit status change preferences" +
					" for those status types from region B.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	/***************************************************************
	'Description		:User U1 has login access to regions A & B and has added resource and associated status types to custom view. Verify that, after removing the "View Resource" right for the in region A, user CANNOT view/add resource on/to custom view from region B.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:11/19/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS46474() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();
		MobileView objMobileView = new MobileView();
		try {
			gstrTCID = "46474"; // Test Case Id
			gstrTO = " User U1 has login access to regions A & B and has added"
					+ " resource and associated status types"
					+ " to custom view. Verify that, after removing the 'View"
					+ " Resource' right for the in region A, "
					+ "user CANNOT view/add resource on/to custom view from region B.";// Test
																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + strTimeText;
			String strRTVal = "";

			// Resource
			String strResource = "RS" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";

			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Preconditions: 5. In Region RG1: Resource type RT1
			 * is associated with the following status types which are ROLE
			 * based, PRIVATE and SHARED: rNST1 (ROLE based Number), rMST1 (ROLE
			 * based Multi), rTST1 (ROLE based Text), rSST1 (ROLE based
			 * Saturation score) pNST1 (PRIVATE Number), pMST1 (PRIVATE Multi),
			 * pTST1 (PRIVATE Text), pSST1 (PRIVATE Saturation score) sNST1
			 * (SHARED Number), sMST1 (SHARED Multi), sTST1 (SHARED Text), sSST1
			 * (SHARED Saturation score)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Shared status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statsNumTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statsTextTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statsSaturatTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statsMultiTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6. Resource RS is created under RT1 with address. */
			/* 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

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
				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };

				strFuncResult = objRoles
						.CreateRoleWithAllFieldsCorrect(seleniumPrecondition, strRolesName,
								strRoleRights, strViewRightValue, true,
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

			/*
			 * 8. User U1 has 'View Resources' and 'Update Status' right on
			 * resource RS and a role ROL to view all the above (role based and
			 * shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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

			/* 3. Email and pager addresses are provided for user U1 */

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetach region values

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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
			/*
			 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
			 * NOT have other region view agreement.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumPrecondition);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrNumTypeName,
						statrTextTypeName, statrMultiTypeName,
						statrSaturatTypeName, statpNumTypeName,
						statpTextTypeName, statpMultiTypeName,
						statpSaturatTypeName, statsNumTypeName,
						statsTextTypeName, statsMultiTypeName,
						statsSaturatTypeName };
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumPrecondition,
						strStatTypeArr, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4. User U1 has the right to 'View Custom View' in RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. Custom View is created in region RG2 for user U1 selecting
			 * resources RS and all the above mentioned status types (role
			 * based, private and shared). 10. All status types are under status
			 * type section SEC1. Expected Result:No Expected Result
			 */

			// Custom View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strState = "Alabama";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName,
						statsNumTypeName, statsTextTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user RegAdmin, select region RG1, navigate
			 * to Setup >> Users. Expected Result:'Users List' screen is
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

			/*
			 * STEP : Action:Click on 'Edit' link next to users U1, deselect
			 * 'View Resource' right and save. Expected Result:'Users List'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as User U1 in RG2, navigate to Views >>
			 * Custom Expected Result:Message 'No Statuses in Custom View There
			 * are no statuses (columns) to display in your custom view.' 'Click
			 * here to add status columns to your custom view.' (link) is
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
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objPreferences
						.varErrorMsgInCustomViewTableNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Show Map' link on the custom view screen.
			 * Expected Result:'Custom View - Map' screen is displayed. Resource
			 * RS is not displayed in the 'Find Resource' drop down.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResource, false);
				gstrReason = strFuncResult;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Customize' link. Expected Result:Resource
			 * RS is not displayed in the section on 'Edit Custom View' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navEditCustomViewPageFromViewCustom(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Add More Resources' button. Expected
			 * Result:'Find Resources' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide resource RS in the Name field and click on
			 * 'Search'. Expected Result:'No resources match your criteria...'
			 * message is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				strFuncResult = objPreferences.findResourcesVarErrMsg(selenium,
						strResource, strCategory, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Cancel' button. Expected Result:'Edit
			 * Custom View' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.cancelAndNavToEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Options' button. Expected Result:'Edit
			 * Custom View Options (Columns)' screen is displayed Private, Role
			 * based and shared status types are not displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName,
						statsNumTypeName, statsTextTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Mobile View' link. Expected Result:'Main
			 * Menu' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Resources' link and click on 'Custom'
			 * link. Expected Result:Resource RS is not displayed on 'Custom'
			 * screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView
						.navToCustomPageInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource };
				strFuncResult = objMobileView.chkResInCustomPageInMobileView(
						selenium, strResources, false);
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-46474";
			gstrTO = "User U1 has login access to regions A & B and has added"
					+ " resource and associated status types to "
					+ "custom view. Verify that, after removing the 'View"
					+ " Resource right' for the in region A,"
					+ " user CANNOT view/add resource on/to custom view from region B.";
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
				gstrResult = "FAIL";
			}
		}
	}
	
	
	/*************************************************************************************
	'Description	:For a user U1 who has login access to regions A & B, deselect status 
	                 types in the 'Refine' window from region A and save. Verify that user
	                 cannot view/add those status types on/to custom view from region B.
	'Arguments		:None
	'Returns		:None
	'Date			:11/20/2012
	'Author			:QSG
	'-------------------------------------------------------------------------------------
	'Modified Date				                                              Modified By
	'Date					                                                  Name 
	**************************************************************************************/	 
	@Test
	public void testBQS46510() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();
		MobileView objMobileView = new MobileView();
		try {
			gstrTCID = "46510"; // Test Case Id
			gstrTO = " For a user U1 who has login access to regions A & B, "
					+ "deselect status types in the 'Refine' window from region "
					+ "A and save. Verify that user cannot view/add those status"
					+ " types on/to custom view from region B.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + strTimeText;
			String strRTVal = "";

			// Resource
			String strResource = "RS" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";

			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			// ST
			String[] strPStatTypes = { statpNumTypeName, statpTextTypeName,
					statpMultiTypeName, statpSaturatTypeName };
			String[] strRSstatTypes = { statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName, statsNumTypeName,
					statsTextTypeName, statsMultiTypeName, statsSaturatTypeName };

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Preconditions: 5. In Region RG1: Resource type RT1
			 * is associated with the following status types which are ROLE
			 * based, PRIVATE and SHARED: rNST1 (ROLE based Number), rMST1 (ROLE
			 * based Multi), rTST1 (ROLE based Text), rSST1 (ROLE based
			 * Saturation score) pNST1 (PRIVATE Number), pMST1 (PRIVATE Multi),
			 * pTST1 (PRIVATE Text), pSST1 (PRIVATE Saturation score) sNST1
			 * (SHARED Number), sMST1 (SHARED Multi), sTST1 (SHARED Text), sSST1
			 * (SHARED Saturation score)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Shared status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statsNumTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statsTextTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statsSaturatTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statsMultiTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6. Resource RS is created under RT1 with address. */
			/* 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

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
				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3] };

				strFuncResult = objRoles
						.CreateRoleWithAllFieldsCorrect(seleniumPrecondition, strRolesName,
								strRoleRights, strViewRightValue, true,
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

			/*
			 * 8. User U1 has 'View Resources' and 'Update Status' right on
			 * resource RS and a role ROL to view all the above (role based and
			 * shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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

			/* 3. Email and pager addresses are provided for user U1 */

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetach region values

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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
		/*
		 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
		 * NOT have other region view agreement.
		 */

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrNumTypeName,
						statrTextTypeName, statrMultiTypeName,
						statrSaturatTypeName, statpNumTypeName,
						statpTextTypeName, statpMultiTypeName,
						statpSaturatTypeName, statsNumTypeName,
						statsTextTypeName, statsMultiTypeName,
						statsSaturatTypeName };
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strStatTypeArr, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 4. User U1 has the right to 'View Custom View' in RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. Custom View is created in region RG2 for user U1 selecting
			 * resources RS and all the above mentioned status types (role
			 * based, private and shared). 10. All status types are under status
			 * type section SEC1. Expected Result:No Expected Result
			 */

			// Custom View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strState = "Alabama";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName,
						statsNumTypeName, statsTextTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user RegAdmin, select region RG1, navigate
			 * to Setup >> Users. Expected Result:'Users List' screen is
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

			/*
			 * STEP : Action:Click on 'Edit' link next to users U1, select the
			 * link 'Refine' corresponding to resource RS, deselect all role
			 * based and shared status types and save the user. Expected
			 * Result:'Users List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_roleStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_roleStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_roleStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_roleStatusTypeValues[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_sharedStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_sharedStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_sharedStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, str_sharedStatusTypeValues[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
			 * STEP : Action:Login as User U1 in RG2, navigate to Views >>
			 * Custom Expected Result:Private status types pNST1, pMST1, pSST1
			 * and pTST1 are displayed for resource RS under resource type RT.
			 * Shared and role based status types are not displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTablNew(selenium,
								strResrctTypName1, strResource, strPStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTNOtPresentInViewCustTableNew(
						selenium, strResrctTypName1, strResource,
						strRSstatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Customize' link. Expected Result:'Edit
			 * Custom View' screen is displayed.
			 */
			try {
				assertEquals("Status type is NOT displayed for Resource",
						strFuncResult);
				strFuncResult = objViews
						.navEditCustomViewPageFromViewCustom(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Options' button. Expected Result:'Edit
			 * Custom View Options (Columns)' screen is displayed. Private
			 * status types pNST1, pMST1, pSST1 and pTST1 are displayed. Shared
			 * and role based status types are not displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.chkSTPresenceInEditCustViewOptionPage(selenium,
								strPStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strRSstatTypes);
				selenium.click("//input[@value='Cancel']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Show Map' link on the custom view screen.
			 * Expected Result:'Custom View - Map' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select resource RS from the find resource drop
			 * down. Expected Result:Private status types pNST1, pMST1, pSST1
			 * and pTST1 are displayed on the resource pop-up window for
			 * resource RS1. Shared and role based status types are not
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};

				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strPStatTypes,
								false, true);
				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				for (int i = 0; i < strRSstatTypes.length; i++) {
					try {
						assertFalse(strStatType.contains(strRSstatTypes[i]));
						log4j.info("Role Based Status type "
								+ strRSstatTypes[i] + " is not displayed");
					} catch (AssertionError ae) {
						log4j.info("Role Based Status type "
								+ strRSstatTypes[i] + " is still displayed");
						strFuncResult = " Role Based Status type "
								+ strRSstatTypes[i] + " is still displayed";
					}
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'View Info' link on the resource pop up
			 * window. Expected Result:Private status types pNST1, pMST1, pSST1
			 * and pTST1 are displayed on the 'View Resource Detail' page Shared
			 * and role based status types are not displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strPStatTypes);

				for (int i = 0; i < strRSstatTypes.length; i++) {
					try {
						assertFalse(selenium
								.isElementPresent("//table[starts-with(@id,"
										+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ strRSstatTypes[i] + "']"));

						log4j.info("The Status Type " + strRSstatTypes[i]
								+ " is NOT displayed on the "
								+ "view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j.info("The Status Type " + strRSstatTypes[i]
								+ " is displayed on"
								+ " the view resource detail screen. ");
						strFuncResult = "The Status Type " + strRSstatTypes[i]
								+ " is displayed on "
								+ "the view resource detail screen. ";
						gstrReason = strFuncResult;

					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Mobile View' link. Expected Result:'Main
			 * Menu' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Resources' link and click on 'Custom'
			 * link. Expected Result:Resource RS is displayed on 'Custom'
			 * screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView
						.navToCustomPageInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource };
				strFuncResult = objMobileView.chkResInCustomPageInMobileView(
						selenium, strResources, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on resource RS. Expected Result:Only private
			 * status types pNST1, pMST1, pSST1 and pTST1 are displayed under
			 * the appropriate sections. Role based and shared status types are
			 * not displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.chkSTInCustomPageInMobileView(
						selenium, strResource, strSection1, strPStatTypes,
						false);
				for (int i = 0; i < strRSstatTypes.length; i++) {
					try {
						assertFalse(selenium
								.isElementPresent("//div/h3/following-sibling::table[@class='topicListItem']/tbody/tr/td[contains(text(),'"
										+ strRSstatTypes[i] + "')]"));
						log4j.info(strRSstatTypes[i]
								+ " is NOT displayed correctly under section "
								+ strSection1);

					} catch (AssertionError ae) {
						log4j.info(strRSstatTypes[i]
								+ " is displayed correctly under section "
								+ strSection1);
						strFuncResult = strRSstatTypes[i]
								+ " is displayed correctly under section "
								+ strSection1;
						gstrReason = strFuncResult;
					}
				}
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-46510";
			gstrTO = "For a user U1 who has login access to regions A & B, deselect status types in the 'Refine' window from region A and save. Verify that user cannot view/add those status types on/to custom view from region B.";
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
				gstrResult = "FAIL";
			}
		}
	}
	/***************************************************************
	'Description		:User U1 has login access to regions A & B. Verify that user CANNOT view/add private status types of region A on/to his custom view from region B when affiliated resource rights for the resource are removed in region A.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:11/21/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS50477() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Regions objRegion = new Regions();
		MobileView objMobileView = new MobileView();
		try {
			gstrTCID = "50477"; // Test Case Id
			gstrTO = " User U1 has login access to regions A & B. Verify that user "
					+ "CANNOT view/add private status types of region A on/to his custom"
					+ " view from region B when affiliated resource rights for the resource"
					+ " are removed in region A.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			String strRegionValue[] = new String[2];

			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Status types
			String strStatTypDefn = "Automation";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeText;
			String str_roleStatusName2 = "rSb" + strTimeText;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeText;
			String str_privateStatusName2 = "pSb" + strTimeText;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// RT data
			String strResrctTypName1 = "RT1" + strTimeText;
			String strRTVal = "";

			// Resource
			String strResource = "RS" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";

			// sec data
			String strSection1 = "AB1_" + strTimeText;
			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			// ST
			String[] strPStatTypes = { statpNumTypeName, statpTextTypeName,
					statpMultiTypeName, statpSaturatTypeName };
			String[] strRStatTypes = { statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName };

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Preconditions: 5. In Region RG1: Resource type RT1
			 * is associated with the following status types which are ROLE
			 * based, PRIVATE and SHARED: rNST1 (ROLE based Number), rMST1 (ROLE
			 * based Multi), rTST1 (ROLE based Text), rSST1 (ROLE based
			 * Saturation score) pNST1 (PRIVATE Number), pMST1 (PRIVATE Multi),
			 * pTST1 (PRIVATE Text), pSST1 (PRIVATE Saturation score)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);

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
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// private status type

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName1);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6. Resource RS is created under RT1 with address. */
			/* 7. RS is NOT shared. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Role

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
				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3] };

				strFuncResult = objRoles
						.CreateRoleWithAllFieldsCorrect(seleniumPrecondition, strRolesName,
								strRoleRights, strViewRightValue, true,
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

			/*
			 * 8.8. User U1 has 'View Resources', 'Update Status', 'Associated
			 * with' and 'Run Report' right on resource RS and a role ROL to
			 * view all the above (role based and shared) status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

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
						seleniumPrecondition, strResource, strRSValue[0], true, true, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Email and pager addresses are provided for user U1 */

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
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetach region values

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn1);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue[1] = objRegion.fetchRegionValue(seleniumPrecondition,
						strRegn2);

				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
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
			/*
			 * 1. User U1 has access to Region RG1 and RG2. 2. RG1 and RG2 do
			 * NOT have other region view agreement.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strRegionValues = { strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}



			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumPrecondition);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrNumTypeName,
						statrTextTypeName, statrMultiTypeName,
						statrSaturatTypeName, statpNumTypeName,
						statpTextTypeName, statpMultiTypeName,
						statpSaturatTypeName };
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumPrecondition,
						strStatTypeArr, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4. User U1 has the right to 'View Custom View' in RG2.
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9. Custom View is created in region RG2 for user U1 selecting
			 * resources RS and all the above mentioned status types (role based
			 * and private).
			 */

			// Custom View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strState = "Alabama";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user RegAdmin, select region RG1, navigate
			 * to Setup >> Users. Expected Result:'Users List' screen is
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
			/*
			 * STEP : Action:Click on 'Edit' link next to users U1, deselect
			 * 'Update Status', 'Associated with' and 'Run report' check boxes
			 * and save. Expected Result:'Users List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as User U1 in RG2, navigate to Views >>
			 * Custom Expected Result:Only Role based status types are displayed
			 * on 'Custom View-Table' screen. Private status types are not
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
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTablNew(selenium,
								strResrctTypName1, strResource, strRStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.chkSTNOtPresentInViewCustTable(selenium,
								strResrctTypName1, strResource, strPStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Show Map' link on the custom view screen.
			 * Expected Result:'Custom View - Map' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select resource RS from the find resource drop
			 * down. Expected Result:Role based status types rNST1, rMST1, rSST1
			 * and rTST1 are displayed on the resource pop-up window for
			 * resource RS. Private status types are not displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRStatTypes,
								false, true);

				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				for (int i = 0; i < strPStatTypes.length; i++) {
					try {
						assertFalse(strStatType.contains(strPStatTypes[i]));
						log4j.info("Role Based Status type " + strPStatTypes[i]
								+ " is not displayed");
					} catch (AssertionError ae) {
						log4j.info("Role Based Status type " + strPStatTypes[i]
								+ " is still displayed");
						strFuncResult = " Role Based Status type "
								+ strPStatTypes[i] + " is still displayed";
					}
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized view Expected
			 * Result:Resource RS is displayed in the section on 'Edit Custom
			 * View' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
				try {
					assertTrue(selenium
							.isElementPresent("//li[@class='item']/div[contains(text(),'"
									+ strResource + "')]"));
					assertTrue(selenium
							.isVisible("//li[@class='item']/div[contains(text(),'"
									+ strResource + "')]"));
					log4j
							.info("Resource "
									+ strResource
									+ "is displayed in the section on 'Edit Custom View' screen.");

				} catch (AssertionError ae) {
					log4j
							.info("Resource "
									+ strResource
									+ "is NOT displayed in the section on 'Edit Custom View' screen.");
					strFuncResult = "Resource "
							+ strResource
							+ "is NOT displayed in the section on 'Edit Custom View' screen.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Options' button. Expected Result:'Edit
			 * Custom View Options (Columns)' screen is displayed Only role
			 * based status types are displayed. Private status types are not
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.chkSTPresenceInEditCustViewOptionPage(selenium,
								strRStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strPStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized view, click
			 * on 'Delete' link next to the section name (default resource type
			 * name) and click on 'Save'. Expected Result:No Statuses in Custom
			 * View Message 'There are no statuses (columns) to display in your
			 * custom view.' 'Click here to add status columns to your custom
			 * view.' (link) is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.deleteSectionDefaultRTName(selenium,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized view, click
			 * on 'Add More resources' button provide RS in the 'Name' field and
			 * click on 'Search' Expected Result:All the resources matching the
			 * search criteria are listed on the 'Find Resources' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select resource RS and click on 'Add to Custom
			 * view' button. Expected Result:Resource RS is displayed in the
			 * section on 'Edit Custom View' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strState = "Alabama";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
				try {
					assertTrue(selenium
							.isElementPresent("//li[@class='item']/div[contains(text(),'"
									+ strResource + "')]"));
					assertTrue(selenium
							.isVisible("//li[@class='item']/div[contains(text(),'"
									+ strResource + "')]"));
					log4j
							.info("Resource "
									+ strResource
									+ "is displayed in the section on 'Edit Custom View' screen.");
				} catch (AssertionError ae) {
					log4j
							.info("Resource "
									+ strResource
									+ "is NOT displayed in the section on 'Edit Custom View' screen.");
					strFuncResult = "Resource "
							+ strResource
							+ "is NOT displayed in the section on 'Edit Custom View' screen.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Options' Expected Result:Only role based
			 * status types are listed on 'Edit Custom View Options (Columns)'
			 * screen Private status types are not listed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.chkSTPresenceInEditCustViewOptionPage(selenium,
								strRStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strPStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select Role based status types and click on save.
			 * Expected Result:Only role based status types are displayed on the
			 * 'Custom View-Table' screen. Private status types are not listed.
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName };
				strFuncResult = objPreferences.editCustomViewOptionsNew(
						selenium, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTablNewMapOrTableOption(selenium,
								strResrctTypName1, strResource, strRStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.chkSTNOtPresentInViewCustTable(selenium,
								strResrctTypName1, strResource, strPStatTypes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Mobile View' link. Expected Result:'Main
			 * Menu' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Resources' link and click on 'Custom'
			 * link. Expected Result:Resource RS is displayed on 'Custom'
			 * screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView
						.navToCustomPageInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource };
				strFuncResult = objMobileView.chkResInCustomPageInMobileView(
						selenium, strResources, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on resource RS. Expected Result:Only role
			 * based status types rNST1, rMST1, rSST1 and rTST1 are displayed
			 * under the appropriate sections. Private status types are not
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.chkSTInCustomPageInMobileView(
						selenium, strResource, strSection1, strRStatTypes,
						false);
				for (int i = 0; i < strPStatTypes.length; i++) {
					try {
						assertFalse(selenium
								.isElementPresent("//div/h3/following-sibling::table[@class='topicListItem']/tbody/tr/td[contains(text(),'"
										+ strPStatTypes[i] + "')]"));
						log4j.info(strPStatTypes[i]
								+ " is NOT displayed correctly under section "
								+ strSection1);

					} catch (AssertionError ae) {
						log4j.info(strPStatTypes[i]
								+ " is displayed correctly under section "
								+ strSection1);
						strFuncResult = strPStatTypes[i]
								+ " is displayed correctly under section "
								+ strSection1;
						gstrReason = strFuncResult;
					}
				}
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-50477";
			gstrTO = "User U1 has login access to regions A & B. Verify that user CANNOT view/add private status types of region A on/to his custom view from region B when affiliated resource rights for the resource are removed in region A.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
}
