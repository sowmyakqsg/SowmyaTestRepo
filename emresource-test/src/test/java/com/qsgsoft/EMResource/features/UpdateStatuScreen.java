package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**********************************************************************************
' Description       :This class includes UpdateStatuScreen requirement testcases
' Requirement Group :Viewing & managing EMResource entities on the 'View' interface
' Requirement       :Update Status screen
' Date		        :4-April-2012
' Author	        :QSG
'----------------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'**********************************************************************************/

public class UpdateStatuScreen {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.UpdateStatuScreen");
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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));
		

		seleniumFirefox = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		// kill browser
		try {
			selenium.close();
		} catch (Exception e) {

		}
		try {

			seleniumFirefox.close();
		} catch (Exception e) {

		}
		try {

			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		
		seleniumPrecondition.stop();
		selenium.stop();
		seleniumFirefox.stop();
		
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
	
	
	/********************************************************************************
	'Description	:Verify that clicking on the 'Show all statuses' option displays all the status types to which the user has update right to. 
	'Precondition	:	Preconditions:

					1. Role based status types NST (Number), MST(Multi), SST (saturation score), TST (Text) are created.
					2. NST , MST, SST, TST status types are moved to section SEC1.
					3. Resource type RT is created selecting NST , MST, SST, TST.
					4. Resource RS is created under RT.
					5. User U1 is created selecting the following rights:
					
					a. View Resource and Update Resource right on RS.
					b. Role R1 to view and update NST , MST, SST, TST status types.
					c. 'Configure Region View' right.
					6. View V1 is created selecting RS, NST, MST, TST and SST. 		No 
	'Arguments		:None
	'Returns		:None
	'Date	 		:29-May-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-Oct-2012                                     <Name>
	**********************************************************************************/
	
	@Test
	public void testBQS69946() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Views objViews = new Views();
		ResourceTypes objRT=new ResourceTypes();
		Resources objRs=new Resources();
		ReadData objReadData = new ReadData (); 
		StatusTypes objST=new StatusTypes();
		CreateUsers objCreateUsers=new CreateUsers();
		Roles objRole=new Roles();
		Date_Time_settings dts = new Date_Time_settings();
	
		try {
			gstrTCID = "BQS-69946 ";
			gstrTO = "Verify that clicking on the 'Show all statuses' "
					+ "option displays all the status types to which the"
					+ " user has update right to. ";
			gstrResult = "FAIL";
			gstrReason = "";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strResource="AutoRs_"+strTimeText;

			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTmText = dts.getCurrentDate("HHmm");
								
			String strMSTValue="Multi";
			String strNSTValue="Number";
			String strTSTValue="Text";
			String strSSTValue="Saturation Score";						
							
			String strNumStat="Aa1_"+strTimeText;
			String strMulStat="Aa2_"+strTimeText;
			String strTextStat="Aa3_"+strTimeText;
			String strSatuStat="Aa4_"+strTimeText;			
				
		
			String []strStatTypeArr={strNumStat,strMulStat,strTextStat,strSatuStat};
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			
			String strStatTypDefn="Auto";
			String strFailMsg ="";
			String strRSTvalue[]=new String[4];
		
			String strStatValue="";
		
			String strResrctTypName="AutoRt_"+strTimeText;
			String strRTValue[]=new String[1];
			String strRSValue[]=new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal="";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=rdExcel.readData("Login", 4, 2);
			String strConfirmPwd=rdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			String strRoleValue="";
			
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strSection="AB_"+strTimeText;
			
			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
		
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * 1. Role based status types NST (Number), MST(Multi), SST
			 * (saturation score), TST (Text) are created.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMulStat,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMulStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, strMulStat, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, strMulStat, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objST.fetchStatValInStatusList(
						seleniumPrecondition, strMulStat, strStatusName1);
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
				strStatusValue[1] = objST.fetchStatValInStatusList(
						seleniumPrecondition, strMulStat, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStat,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTextStat,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTextStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStat,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			
			/*
			 * 3. Resource type RT is created selecting NST , MST, SST, TST.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			for (int intST = 0; intST < strRSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(
							seleniumPrecondition, strRSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
				if (strFailMsg.compareTo("") != 0) {

					strRTValue[0] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * 4. Resource RS is created under RT.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strRSTvalue, true, strRSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFailMsg = "";

				} else {
					strFailMsg = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * 5. User U1 is created selecting the following rights:
			 * 
			 * a. View Resource and Update Resource right on RS. b. Role R1 to
			 * view and update NST , MST, SST, TST status types. c. 'Configure
			 * Region View' right
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * 6. View V1 is created selecting RS, NST, MST.
			 */

			try {
				assertEquals("", strFailMsg);
     			strFailMsg = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strSTvalueBef[] = { strRSTvalue[0], strRSTvalue[1] };
				strFailMsg = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalueBef, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * 2. NST , MST, SST, TST status types are moved to section SEC1.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strStatTypeArr, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			
			try {
				assertTrue(strFailMsg.equals(""));
				blnLogin=false;
				strFailMsg = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");
						
			/*
			 * 2 Login as user U1, navigate to View >> V1 Resource RS is
			 * displayed under RT along with NST , MST, status types.
			 */
			try {
				assertTrue(strFailMsg.equals(""));
				blnLogin=true;
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);

				String[] strResources = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName, strResources,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strStatType={strNumStat,strMulStat};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			 * 3 Click on update status key. NST and MST are displayed on the
			 * 'Update Status' screen.
			 * 
			 * Status types NST and MST status types are expanded.
			 * 
			 * 'Show All Statuses' link is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strNumStat,strMulStat};
				strFuncResult = objViews.verifyExpandedSTInUpdateSTScreen(selenium,
						strStatType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strSatuStat};
				strFuncResult = objViews.verifyExpandedSTInUpdateSTScreen(selenium,
						strStatType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals(strSatuStat + " is NOT displayed", strFuncResult);
				String[] strStatType={strTextStat};
				strFuncResult = objViews.verifyExpandedSTInUpdateSTScreen(selenium,
						strStatType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals(strTextStat + " is NOT displayed", strFuncResult);
				
				strFuncResult = objViews.navToShowAllStatus(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strNumStat,strMulStat,strTextStat,strSatuStat};
				strFuncResult = objViews.verifyExpandedSTInUpdateSTScreen(selenium,
						strStatType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5 Update all status types NST, MST, TST, SST and click on 'Save'.
			 * The statuses are updated properly and displayed on the view
			 * screen V1.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strRSTvalue[1], false, "", "");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Text", strRSTvalue[2], false, "", "");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strRSTvalue[3]);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strRSTvalue[0], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium, strNumStat, "10", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strMulStat, strStatusName1, "4");
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
			gstrTCID = "BQS-69946";
			gstrTO = "Verify that clicking on the 'Show all statuses' "
					+ "option displays all the status types to which "
					+ "the user has update right to. ";
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
	'Description	:Verify that a user can snooze the update status prompt.
	'Precondition	:	Preconditions:

					1. Role based status types NST (Number), MST(Multi), SST (saturation score), TST (Text) are created selecting 5 min expiration time.
					2. NST , MST, SST, TST status types are moved to section SEC1.
					3. Resource type RT is created selecting NST , MST, SST, TST.
					4. Resource RS is created under RT.
					5. User U1 is created selecting the following rights:
					
					a. View Resource and Update Resource right on RS.
					b. Role R1 to view and update NST , MST, SST, TST status types.
					c. 'Configure Region View' right.
					d. 'User must update overdue status'
					6. View V1 is created selecting RS, NST, MST, TST and SST. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:29-May-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-Oct-2012                                     <Name>
	**********************************************************************************/
	@Test
	public void testBQS70158() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ReadData objReadData = new ReadData();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		General objGeneral = new General();
		ViewMap objViewMap = new ViewMap();
		try {
			gstrTCID = "BQS-70158";
			gstrTO = "Verify that a user can snooze the update status prompt.";

			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strResource = "AutoRs_" + strTimeText;

			String strMSTValue = "Multi";
			String strNSTValue = "Number";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strNumStat = "Aa1_" + strTimeText;
			String strMulStat = "Aa2_" + strTimeText;
			String strTextStat = "Aa3_" + strTimeText;
			String strSatuStat = "Aa4_" + strTimeText;

			String[] strStatTypeArr = { strNumStat, strMulStat, strTextStat,
					strSatuStat };

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatTypDefn = "Auto";
			String strFailMsg = "";
			String strRSTvalue[] = new String[4];

			String strStatValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;
			String strRoleValue = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strSection = "AB_" + strTimeText;

			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STATRTS~~~~~");

			strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * 1. Role based status types NST (Number), MST(Multi), SST
			 * (saturation score), TST (Text) are created.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strMulStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			String strExpHr = "00";
			String strExpMn = "05";
			try {
				assertEquals("", strFuncResult);
				objST.createSTWithinMultiTypeSTExpTime(seleniumPrecondition, strMulStat,
						strStatusName1, strMSTValue, strStatTypeColor,
						strExpHr, strExpMn, "", "", true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				objST.createSTWithinMultiTypeSTExpTime(seleniumPrecondition, strMulStat,
						strStatusName2, strMSTValue, strStatTypeColor,
						strExpHr, strExpMn, "", "", true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMulStat, strStatusName1);
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
				strStatusValue[1] = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMulStat, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectSTypesAndFilMandFldsWithExTime(
						seleniumPrecondition, strNSTValue, strNumStat, strStatTypDefn,
						strExpHr, strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNumStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectSTypesAndFilMandFldsWithExTime(
						seleniumPrecondition, strTSTValue, strTextStat, strStatTypDefn,
						strExpHr, strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strTextStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectSTypesAndFilMandFldsWithExTime(
						seleniumPrecondition, strSSTValue, strSatuStat, strStatTypDefn,
						strExpHr, strExpMn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strSatuStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * 3. Resource type RT is created selecting NST , MST, SST, TST.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			for (int intST = 1; intST < strRSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(seleniumPrecondition,
							strRSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName);
				if (strFailMsg.compareTo("") != 0) {

					strRTValue[0] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * 4. Resource RS is created under RT.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strRSTvalue, true,
						strRSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFailMsg = "";

				} else {
					strFailMsg = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * 5. User U1 is created selecting the following rights:
			 * 
			 * a. View Resource and Update Resource right on RS. b. Role R1 to
			 * view and update NST , MST, SST, TST status types. c. 'Configure
			 * Region View' right
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MustOverDue"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*
			 * 6. View V1 is created selecting RS, NST, MST.
			 */

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strRSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * 2. NST , MST, SST, TST status types are moved to section SEC1.
			 */
			log4j.info("~~~~TEST CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strStatTypeArr, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertTrue(strFailMsg.equals(""));
				blnLogin = false;
				strFailMsg = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");

		/*
		 * 2 Login as user U1, navigate to View >> V1 Resource RS is
		 * displayed under RT along with NST , MST, status types.
		 */
			try {
				assertTrue(strFailMsg.equals(""));
				blnLogin = true;
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, strNumStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, strMulStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, strTextStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, strSatuStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.remindMeAndCheckPrompt(selenium,
						strSatuStat);

				// Wait for 10 mins
				Thread.sleep(600000);

				if (selenium.isElementPresent("link=refresh")) {
					try {
						objGeneral.refreshPageNew(selenium);
					} catch (Exception e) {

					}
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objGeneral.waitForMailNotification(selenium,
						60,
						"//span[@class='overdue'][text()='Status is Overdue']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, strSatuStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusTypeForOverdue(
						selenium, strRSTvalue[0], strStatusValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strNumVal = "2";
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strNumVal, strRSTvalue[1], "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strTxtVal = "st";
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strTxtVal, strRSTvalue[2], "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strSatuChkVal = "393";
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSaveUpdStatusSSTWithComments(
						selenium, strScUpdValue1, strRSTvalue[3], "",
						"Region Default");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strArUpdVal[] = { strNumVal, strStatusName1, strTxtVal,
					strSatuChkVal };
			for (int intST = 0; intST < strStatTypeArr.length; intST++) {

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
							strStatTypeArr[intST], strArUpdVal[intST],
							String.valueOf((intST + 3)));

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}
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
			gstrTCID = "BQS-70158";
			gstrTO = "Verify that a user can snooze the update status prompt.";
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

	
