package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/******************************************************************************
' Description      :This class includes test case related to Edit Status Reason
' Requirement Group:Setting up Status types 
' Requirement      :Edit status reason
' Date		       :11-May-2012
' Author	       :QSG
'-----------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************************/

public class EditStatusReason {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.EditStatusReason");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	Selenium selenium, seleniumPrecondition;
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails, propAutoItDetails;
	public static Properties browserProps = new Properties();

	private String browser = "";
	ReadData rdExcel;
	private String json;
	public static long sysDateTime;
	public static long gsysDateTime;
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId, StrSessionId1, StrSessionId2;
	public String gstrTimeOut;

	@Before
	public void setUp() throws Exception {

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {

			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.json,
					propEnvDetails.getProperty("urlEU"));

		} else {
			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.browser,
					propEnvDetails.getProperty("urlEU"));

		}
		// start session for Browser1
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout(gstrTimeOut);

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		// kill browser
		selenium.close();
		selenium.stop();
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();

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
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);
	}

	/**********************************************************
	'Description	:Verify that user can edit a status reason
	'Arguments		:void
	'Returns		:void
	'Date	 		:11-May-2012
	'Author			:QSG
	'----------------------------------------------------------
	'Modified Date                            Modified By
	'<Name>                              		<Name>
	***********************************************************/
	@Test
	public void testBQS68940()throws Exception{
		boolean blnLogin=false;//keep track of logout of application		
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusReason objStatusReason=new StatusReason();
		ResourceTypes objResourceTypes=new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Roles objRoles=new Roles();
		Views objViews = new Views();
		
		try{
			gstrTCID = "68940";			
			gstrTO = "Verify that user can edit a status reason";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST_1" + strTimeText;
			String strStatTypDefn = "Automation";
			//Status
			String strStatusName="St"+strTimeText;
			String strStatTypeColor="Red";
			//Status Reason			
			String strMandReasonName="SR"+strTimeText;
			String strEdStatReasn="SE"+strTimeText;
			String strSRValue[]=new String[1];
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strHavBed = "No";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" +strTimeText;
			String strRoleValue = "";	
			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			
			// View
			String strViewName = "AutoV_" +strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID  + " EXECUTION STATRTS~~~~~");
		
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusReason.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusReason.createStatusReasn(seleniumPrecondition, strMandReasonName, strStatTypDefn,
						strAbbrv, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSRValue[0] = objStatusReason.fetchStatReasonValue(seleniumPrecondition, strMandReasonName);
				if (strSRValue[0].compareTo("") != 0) {
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

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectAndDeselectStatusReason(seleniumPrecondition, strSRValue[0], true);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
					seleniumPrecondition.click("link=Return to Status Type List");
					seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
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
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusReason.selectAndDeselectStatusReasInStatus(seleniumPrecondition,strSRValue[0], true);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
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

			// RS
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
				String[] updateRightValue = { strStatusTypeValues[0]};
				String[] strViewRightValue = { strStatusTypeValues[0]};
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
            //user
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(seleniumPrecondition, strResource, strResVal, false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 //View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strStatusTypeValues[0]};
				String[] strRSValue={strResVal};
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
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
				log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
				
			/*
			 * Step 2: 	Login as user 'A' and navigate to View>>V1 <-> 'V1' page is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult=objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			/*
			 * Step 3: Update status of 'MST' selecting 'S1' and 'SR' <-> Status is updated where
			 *  'SR' is displayed under the 'Comments' section
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objStatusTypes.UpdateStatusOFMultiST(selenium, statTypeName, strStatusName, strMandReasonName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			//logout the application
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.logout(selenium);						
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * Step 4: Login as RegAdmin and navigate to 'Setup >> Status Reason' and edit the 'Name' 
			 * and 'Definition' of 'SR' <->	The status reason is updated in the status reason list
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

			// navigate to default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusReason.navStatusReasonList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusReason.editStatusReasn(selenium,
						strMandReasonName, strEdStatReasn, strStatTypDefn, "",
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals("Status Reason List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
									+ strEdStatReasn + "']"));
					log4j
							.info("The status reason is updated in the status reason list");

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
			/*
			 * Step 5: Login as user 'A' and navigate to View>>V1 <-> 'V1' page is displayed.
					<-> Previously updated status reason is intact.
			 */
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.login(selenium, strUserName,
								strInitPwd);
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.navToUserView(selenium,
								strViewName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='viewContainer']/table/thead/tr/th[4][text()='Comment']"));
							assertTrue(selenium
									.isElementPresent("//div[@id='viewContainer']/table/tbody/tr/td[4][text()='"
											+ strMandReasonName + "']"));
							log4j
									.info("Previously updated status reason is intact.");

			/*
			 * Step 6: Click on the key to update status 'MST'
			 * <-> Edited status reason 'Name' and 'Definition'
			 * is displayed.
			 */
						try {
							assertEquals("", strFuncResult);
							String strNavElement = "css=img.noprint";
							strFuncResult = objViews.navToUpdateStatus(
									selenium, strNavElement);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertTrue(selenium
									.isElementPresent("//div[@class='multiStatus']/div/span/label[text()='"
											+ strStatusName
											+ "']/parent::span/following-sibling::div/div/label[contains(text(),'"
											+ strEdStatReasn + "')]"));
							assertEquals(
									"-- " + strStatTypDefn,
									selenium
											.getText("//div[@class='multiStatus']/div/span/label[text()='"
													+ strStatusName
													+ "']/parent::span/following-sibling::div/div/label[contains(text(),'"
													+ strEdStatReasn
													+ "')]/span"));
							log4j.info("Edited status reason 'Name'"+strEdStatReasn+" and 'Definition'"+strStatTypDefn+" is displayed. ");

			/*
			 * Step 7: Save the page by selecting the edited
			 * status reason <-> Status is updated with the
			 * edited data under the 'Comments' section.
			 */
					// select status reason
					selenium
					.click("//div[@class='multiStatus']/div/span/label[text()='"
							+ strStatusName
							+ "']/parent::span/following-sibling::div/div/label[contains(text(),'"
							+ strEdStatReasn
							+ "')]/preceding-sibling::input");

					// save
					selenium.click(propElementDetails
							.getProperty("View.UpdateStatus.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

								try {
									assertTrue(selenium
											.isElementPresent("//div[@id='viewContainer']/table/thead/tr/th[4][text()='Comment']"));
			assertTrue(selenium.isElementPresent("//div[@id='viewContainer']/table/tbody/tr/td[4][text()='"
													+ strEdStatReasn + "']"));
									log4j.info("Status is updated with the edited data under the 'Comments' section.");

								} catch (AssertionError ae) {
									log4j.info("Status is NOT updated with the edited data under the 'Comments' section.");
									gstrReason = "Status is NOT updated with the edited data under the 'Comments' section.";
								}

							} catch (AssertionError ae) {
								log4j.info("Previously updated status reason is NOT intact.");
								gstrReason = "Previously updated status reason is NOT intact.";
							}

						} catch (AssertionError ae) {
							log4j.info("Previously updated status reason is NOT intact.");
							gstrReason = "Previously updated status reason is NOT intact.";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

	try {
						assertTrue(strFuncResult.equals(""));
						gstrResult = "PASS";
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				} catch (AssertionError Ae) {

					gstrReason = "The status reason is NOT updated in the status reason list";

					log4j.info("The status reason is NOT updated in the status reason list");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68940";
			gstrTO = "Verify that user can edit a status reason";
			gstrResult = "FAIL";
			excReason = null;
			log4j.info(e);
			log4j.info("========== Test Case '"+gstrTCID+"' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();	
		
		}	
		 if (blnLogin) {
				strFuncResult= objLogin.logout(selenium);

				try {
					assertEquals("", strFuncResult);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
	}
	
	
}
