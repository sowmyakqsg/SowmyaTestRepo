package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
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

/**********************************************************************
' Description :This class includes EditResourceType requirement 
'				testcases
' Precondition:
' Date		  :30-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class EditResourceType  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditResourceType");
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
	
	Selenium selenium,seleniumFirefox,seleniumPrecondition;
	
	
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

		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444,propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
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
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		selenium.close();
		
		 // kill browser
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

		  gstrReason=gstrReason.replaceAll("'", " ");
		  objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
		    gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}
	
	
	

	/********************************************************************************
	'Description	:Verify that a resource type can be edited.
	
	'Precondition	:11. Test user has created a status type 'ST1' & 'ST2'
					2. Resource type 'RT' is created selecting 'ST1' and a resource 'RS' is created selecting 'RT'
					3. User 'A' has update right on 'RS' and is assigned a role which has update right on 'ST1' & 'ST2'
					4. User 'A' has 'View Custom View' right and has created a custom view for resource 'RS' and status type 'ST1'
					5. View 'V1' is created selecting 'ST1' and 'RS'
					6. Event Template ET is created selecting ST1 and RT
					7. Event 'EVE' is created under ET selecting RS
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-Nov-2012                                     <Name>
	**********************************************************************************/
	
	@Test
	public void testBQS103818() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		
		Login objLogin = new Login();
		Views objViews = new Views();
		Preferences objPreferences=new Preferences();
		ResourceTypes objResourceTypes=new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		EventSetup objEventSetup = new EventSetup();
		Roles objRoles=new Roles();
		ViewMap objViewMap = new ViewMap();
		
		try {
			gstrTCID = "BQS-103818 ";
			gstrTO = "Verify that a resource type can be edited.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);
		
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String streditResrcTypName="ERT"+strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strHavBed = "No";
			String strResVal = "";
			String strRSValue[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" +strTimeText;
			String strRoleValue = "";	
			// View
			String strViewName = "AutoV_" +strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			
			//Event
			String strEveName="AutoEve_"+strTimeText;
					
			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;
			
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION STATRTS~~~~~");
			
			/*
			 * 1. Test user has created a status type 'ST1' & 'ST2'
			 */
			try {
				assertEquals("", strFuncResult);
								strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
            //ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//ST2
			
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. Resource type 'RT' is created selecting 'ST1' and a resource
			 * 'RS' is created selecting 'RT'
			 */
			
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
					strFuncResult = "Failed to fetch Resource type value";
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
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName,
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
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

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
			 * 3. Status type 'ST2' has been added to 'RS' at the resource
			 * level.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(seleniumPrecondition,
								strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						seleniumPrecondition, strStatusTypeValues[1], true);

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
				String[] updateRightValue = { strStatusTypeValues[0],strStatusTypeValues[1]};
				String[] strViewRightValue = { strStatusTypeValues[0],strStatusTypeValues[1]};
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
			
			
			/*
			 * 4. User 'A' has update right on 'RS' and is assigned a role which
			 * has update right on 'ST1' & 'ST2'
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
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
						seleniumPrecondition, strResource, strResVal, false, true, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. User 'A' has 'View Custom View' right and has created a custom
			 * view for resource 'RS' and status type 'ST1' and 'ST2'
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
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

			/*
			 * 5. View 'V1' is created selecting 'ST1','ST2' and 'RS'
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0],strStatusTypeValues[1]};
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 6. Event Template ET is created selecting ST1, ST2 and RT
			 */
			
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
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7. Event 'EVE' is created under ET selecting RS
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(seleniumPrecondition,
						strEveName, true);
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
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Add custom view
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewNewWitTablOrMapOption(seleniumPrecondition,
						strRS, strResrcTypName, statTypeName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				String strSTValue[][] = { { strStatusTypeValues[1], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						seleniumPrecondition, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP 2: Login as RegAdmin, navigate to Setup >> Resource Types
			 * <->'Resource Type List' screen is displayed.
			 */		
				
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3: Select the option 'Edit' corresponding to resource type
			 * 'RT' <->'Edit Resource Type' screen is displayed.
			 */
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.editResrcTypeMandatoryFlds(
						selenium, strResrcTypName, streditResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 4: Edit mandatory data and save <->Changes are updated in
			 * resource type list screen
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						streditResrcTypName);
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
			 * 5 Navigate to a view 'V1' which displays the resource type ST1 is
			 * not displayed on the view screen. Only ST2 is displayed.
			 */
			
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, streditResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String statTypeName[] = { statTypeName2 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 6 Navigate to 'View >> Custom View' On the Custom View Table
			 * screen only status type 'ST2' is displayed and not 'ST1'.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navViewCustomTableOrCustmViewMap(selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {statTypeName2};
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {statTypeName1};
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTablNew(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			


			/*
			 * 7 Select the event banner 'Eve' Status type 'ST2' is displayed
			 * and not 'ST1' in the Event Detail screen.
			 */

			try {
				assertEquals(statTypeName1 + "is NOT displayed for Resource"
						+ strResource, strFuncResult);

				strFuncResult = objViewMap.navEventStatusPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				String statTypeName[] = { statTypeName2 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName1);
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
			gstrTCID = "BQS-103818";
			gstrTO = "Verify that a resource type can be edited.";
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
	'Description	:Verify that newly added status types for a resource type are 
					updated on the View screen.	
	'Precondition	:Verify that newly added status types for a resource type are 
				     updated on the View screen.
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	
	@Test
	public void testBQS69417() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		Views objViews = new Views();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-69417 ";
			gstrTO = "Verify that newly added status types for a"
					+ " resource type are updated on the View screen.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST1_" + strTimeText;
			String statTypeName2 = "AutoST2_" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strResVal = "";
			String strState = "Indiana";
			String strCountry = "Carroll County";
			String strRSValues[] = new String[1];
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

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
			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName2);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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

			// RS1
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
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);
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
					+ " EXECUTION STATRTS~~~~~");
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * 2 Login as RegAdmin, navigate to 'View >> V1' 'RS' is displayed
			 * under 'RT' along with status type 'ST1' on 'V1' view screen.
			 */
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to 'Setup >> Resource Types' and select the option
			 * 'Edit' corresponding to resource type 'RT' 'Edit Resource Type'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select the check box corresponding to status type 'ST2' and
			 * 'Save' 'Resource Type List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], true);
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

			/*
			 * 5 Navigate to 'Setup >> Views', edit view 'V1', add status type
			 * 'ST2' and 'Save' 'Region Views List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[value='Save']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				
				intCnt=0;
				do{
					try {

						assertEquals("Region Views List", selenium
								.getText(propElementDetails.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");
			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT  displayed ");
				strFuncResult = " Region Views List page is NOT  displayed ";
				gstrReason = strFuncResult;

			}

			/*
			 * 6 Navigate to 'View >> V1' ST1 and newly added ST2 are displayed
			 * corresponding to RS under RT.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrcTypName, strRS,
						strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate to View>>Map ST1 and ST2 are displayed in resource pop
			 * up window of RS.
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1, statTypeName2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
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
			gstrTCID = "BQS-69417";
			gstrTO = "Verify that newly added status types "
					+ "for a resource type are updated on the View screen.";
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
	'Description		:Verify that a status type deselected from a
	                     resource type is no longer displayed on the View screen.
	'Precondition		:1. Status types 'ST1' and 'ST2' are created.
						 2. Resource type 'RT' is created selecting 'ST1' & 'ST2'
						 3. Resource 'RS' is created providing address and selecting 'RT'
						 4. View 'V1' is created selecting 'ST1', 'ST2' & 'RS' 		
	'Arguments		    :None
	'Returns		    :None
	'Date			    :4/June/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                        Modified By
	'Date					                            Name
	***************************************************************/
	@Ignore
	public void testBQS69430() throws Exception {
	try{
			
			Login objLogin = new Login();
			ResourceTypes objResourceTypes=new ResourceTypes();
			Views objViews=new Views();
			ViewMap objViewMap=new ViewMap();
			gstrTCID = "69430";
			gstrTO = " Verify that a status type deselected from a resource type"+
			         "is no longer displayed on the View screen.";
			gstrReason = "";
			gstrResult = "FAIL";	

			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");	
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
		
			String strRegn = rdExcel.readData("Login", 3, 4);
								
			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			ReadData objReadData = new ReadData (); 
		
			StatusTypes objST=new StatusTypes();
				
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
		
			String strResource="AutoRs_"+strTimeText;
				
			String strNSTValue="Number";
												
			String strNumStat1="Aa1_"+strTimeText;
			String strNumStat2="Aa2_"+strTimeText;
		
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
		
			String strStatTypDefn="Auto";
			String strFailMsg ="";
			String strRSTvalue[]=new String[2];
		
			String strStatValue="";
		
			String strResrctTypName="AutoRt_"+strTimeText;
			String strRTValue[]=new String[1];
			String strRSValue[]=new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal="";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strSection="AB_"+strTimeText;
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Summary Plus (Resources as rows. Status types and comments as columns.)";
			
			String[] strEventStatType={};
		
			
			String strRT=strResrctTypName;
			String[] strRS={strResource};
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNSTValue, strNumStat1, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumStat1);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNSTValue, strNumStat2, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumStat2);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResrctTypName, "css=input[name='statusTypeID'][value='"+strRSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			for(int intST=0;intST<strRSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(seleniumPrecondition, strRSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResrctTypName);
				if (strFailMsg.compareTo("") != 0) {
					
					strRTValue[0] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
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
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
					

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType,
						true, false, strRSTvalue, false, strRSValue);

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
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}



			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFailMsg);
				String[] strRoleStatType={strNumStat2,strNumStat1};
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox, strRoleStatType, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP :
		  Action:Login as RegAdmin, navigate to View >> V1 
		  Expected Result:Resource RS is displayed under RT along with status types ST1, ST2. 
		 */
		
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
			strFuncResult = objViews.navToUserView(selenium, strViewName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.checkResTypeAndResourceInUserView(
					selenium, strViewName, strRT, strRS, strRTValue[0]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String[] strRoleStatType={strNumStat1,strNumStat2};
			strFuncResult = objViews.checkStatusTypeInUserView(selenium,
					strRoleStatType);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

			/*
			 * STEP :
			  Action:Navigate to 'Setup >> Resource Types' and select 
			         the option 'Edit' corresponding to resource type 'RT' 
			  Expected Result:'Edit Resource Type' screen is displayed. 
			 */
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.navResourceTypList(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			String strResrcTypName=strRT;
			strFuncResult = objResourceTypes.navToeditResrcTypepage(
					selenium, strResrcTypName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
				
			/*
			 * STEP :
			  Action:Deselect the check box corresponding to status type 'ST1' and 'Save' 
			  Expected Result:'Resource Type List' screen is displayed. 
			 */
				
		try {
			assertEquals("", strFuncResult);
		
			strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
					selenium, strRSTvalue[0], false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
		
			strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
					strResrctTypName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

			/*
			 * STEP :
			  Action:	Navigate to View >> V1 
			  Expected Result:Resource RS is displayed under RT along with status type ST2.
			  ST1 is not displayed. 
			 */
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.navToUserView(selenium, strViewName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.checkResTypeAndResourceInUserView(
					selenium, strViewName, strRT, strRS, strRTValue[0]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String[] strRoleStatType={strNumStat2,strNumStat1};
			strFuncResult = objViews.checkStatusTypeInUserView(selenium,
					strRoleStatType);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}		
					
			/*
			 * STEP :
			  Action:Click on resource RS. 
			  Expected Result:'View Resource Detail' screen is displayed.
			  RS is displayed under RT with status type ST2
			  ST1 status type is not displayed. 
			 */

		try {
			assertEquals(
					"Status Type "+strNumStat1+" is NOT displayed in the User view screen",
					strFuncResult);
			String[] strRoleStatType={strNumStat2,strNumStat1};
			strFuncResult = objViews.navAndCheckInViewResourceDetail(
					selenium, strSection, strRoleStatType, strResource);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
			
						
			/*
			 * STEP :
			  Action:Navigate to View>>Map 
			  Expected Result:Only ST2 is displayed in the resource pop up window of RS.
			 */
		try {
			assertEquals(
					strNumStat1+" is NOT displayed under section "+strSection+" on 'View Resource Detail'.",
					strFuncResult);
			String strStatType[]={strNumStat2};
			strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
					selenium, strResource, strEventStatType,
					strStatType, true, true);
		
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
		gstrTCID = "BQS-69430";
		gstrTO = " Verify that a status type deselected from a resource type"
				+ "is no longer displayed on the View screen.";
		gstrResult = "FAIL";
		log4j.info(e);
		log4j.info("========== Test Case '" + gstrTCID
				+ "' has FAILED ==========");
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j
				.info("----------------------------------------------------------");
		gstrReason = e.toString();
	}
}
	
	/********************************************************************************
	'Description	:Create a status type ST selecting a role R1 under �Roles with view rights�, add ST to resource RS at the resource level and verify that user with role R1 & with 'View Resource' right on RS CAN view ST on following screens:
					a. Region view screen
					b. Map (in the status type dropdown and in resource pop up window)
					c. View Resource Detail
					d. Custom view
					e. Event details
					f. Mobile view
					g. Edit My Status Change Preferences
	'Precondition	:1. Resource type RT is created selecting a role based status type ST
					2. Resource RS is created providing address under resource type RT
					3. View V1 is created selecting ST and RT
					4. Event template ET is created selecting ST and RT
					5. Event E1 is created selecting RS
					
					6. Users U1, U2, U3, U4 are created and has following rights:
					a) "View Custom View"
					b) "View Resource" right on RS
					c) Role to view the status type ST
					7. All the users have added resource RS and status types ST to their respective custom views 			
	'Arguments		:None
	'Returns		:None
	'Date	 		:15-Oct-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testBQS100307() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-100307 ";
			gstrTO = "Create a status type ST selecting a role R1 under "
					+ "�Roles with view rights�, add ST to resource RS at the"
					+ " resource level and verify that user with role R1 & with "
					+ "'View Resource' right on RS CAN view ST on following screens:"
					+ "a. Region view screen b. Map (in the status type dropdown "
					+ "and in resource pop up window)c. View Resource Detail "
					+ "d. Custom view e. Event details f. Mobile view "
					+ "g. Edit My Status Change Preferences";

			gstrResult = "FAIL";
			gstrReason = "";

			// String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName_1 = "ST1" + strTimeText;
			String statTypeName_2 = "ST2" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatusTypeValues[] = new String[2];

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";
			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName_1, statTypeName_2 };

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 1. Status types 'ST1' and 'ST2' are created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statTypeName_1, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName_1);

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

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statTypeName_2, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName_2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. Resource type 'RT' is created selecting 'ST1' & 'ST2' */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strStatusTypeValues[1] + "']");

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
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Resource 'RS' is created providing address and selecting 'RT' */

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
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
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

			/* 4. View 'V1' is created selecting 'ST1', 'ST2' & 'RS' */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strStatusTypeValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 5. Section S1 is created with status type ST1 and ST2. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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
				assertEquals("", strFuncResult);
				strSectionValue = objViews
						.fetchSectionID(seleniumFirefox, strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
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

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as RegAdmin, navigate to View >> V1 Resource RS is
			 * displayed under RT along with status types ST1, ST2.
			 */

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to 'Setup >> Resource Types' and select the option
			 * 'Edit' corresponding to resource type 'RT' 'Edit Resource Type'
			 * screen is displayed.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Deselect the check box corresponding to status type 'ST1' and
			 * 'Save' 'Resource Type List' screen is displayed.
			 */
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Navigate to View >> V1 Resource RS is displayed under RT along
			 * with status type ST2.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("Status Type is displayed in the User view Detail screen", strFuncResult);

				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on resource RS. 'View Resource Detail' screen is
			 * displayed.
			 * 
			 * RS is displayed under RT with status type ST2
			 * 
			 * ST1 status type is not displayed.
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

				String[] strStatTypep = { statTypeName_1 };
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("The Status Type is NOT displayed on "
						+ "the view resource detail screen. ", strFuncResult);

				String[] strStatTypep = { statTypeName_2 };
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate to View>>Map Only ST2 is displayed in the resource pop
			 * up window of RS.
			 */

			try {

				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName_2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName_1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals(" Role Based Status type " + statTypeName_1
						+ " is NOT displayed", strFuncResult);
				strFuncResult = "";
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
			gstrTCID = "BQS-100307";
			gstrTO = "Create a status type ST selecting a role R1 under "
					+ "�Roles with view rights�, add ST to resource RS at the"
					+ " resource level and verify that user with role R1 & with "
					+ "'View Resource' right on RS CAN view ST on following screens:"
					+ "a. Region view screen b. Map (in the status type dropdown "
					+ "and in resource pop up window)c. View Resource Detail "
					+ "d. Custom view e. Event details f. Mobile view "
					+ "g. Edit My Status Change Preferences";

			gstrResult = "FAIL";
			gstrReason = "";

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

	
