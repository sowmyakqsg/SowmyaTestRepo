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

public class NewFor3pt15NotToForceStatUpdate {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RecvExpiredStatusNotifiForMST");
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

	Selenium selenium, seleniumPrecondition;

	@Before
	public void setUp() throws Exception {

		gdtStartDate = new Date();
		gstrBrowserName = "IE 8";

	
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
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

	/***********************************************************************
	'Description	:Verify that user receives the following when the number status of a resource expires at the SHIFT time:
					1. Status update prompt.
					2. Expired status notification
					1. Status update prompt.
					2. Expired status notification
	'Precondition	:1. Test user has created a number status type 'NST' which is associated to Resource Type RT and Resource RS.
					3. 'NST' is provided with a shift time.
					2. View 'V1' is created selecting 'NST' and 'RS'
					3. Test user is assigned to role 'R' and has update right on NST
					4. Test user can receive expired status notification via e-mail and pager
					5. Test user has 'Must update overdue status' right 
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-April-2012                               <Name>
	************************************************************************/

	
	@Test
	public void testBQS23665() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login

		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		General objGeneral = new General();

		try {
			gstrTCID = "BQS-23665";
			gstrTO = "Verify that user is required to update status of "
					+ "only the status types which are created without"
					+ " selecting 'Exempt from Must Update' when there"
					+ " are multiple status types with the resource.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Status Type
			String strStatusTypeValue = "Number";
			String statTypeName1 = "AutoNST1_" + strTimeText;
			String statTypeName2 = "AutoNST2_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strRoleValue = "";

			String strViewName = "AutoV_" + strTimeText;
			String strHavBed = "No";

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status"
					+ " types as rows. Status, comments and timestamps"
					+ " as columns.)";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
								strStatusTypeValue, statTypeName1,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName2, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Enter shift time

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition
						.click(propElementDetails
								.getProperty("StatusType.CreateStatType.ExemptFrmUpdate"));

				// Save status type
				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				// Verify status type in Status type list
				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName2 + "']"));

					log4j.info("Status type " + statTypeName2
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					// Fetch status type value
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeName1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strSTvalue[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status type1 value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeName2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strSTvalue[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status type2 value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Navigate to resource type list
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Fill resource type fields
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
								strResrctTypName,
								"css=input[name='statusTypeID'][value='"
										+ strSTvalue[0] + "']");
						seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Save and verify resource type

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
								strResrctTypName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Navigate to resource list page
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Navigate to Create Resources page
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Create resource

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWithMandFields(
								seleniumPrecondition, strResource, strAbbrv,
								strResrctTypName, strStandResType,
								strContFName, strContLName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Save and navigate to resource list page
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
								strResource);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
								strResource, strHavBed, "", strAbbrv,
								strResrctTypName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
						strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName, strRoleRights,
								strSTvalue, false, strSTvalue, false, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strRoleValue = objRole.fetchRoleValueInRoleList(
								seleniumPrecondition, strRoleName);

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
						strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

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
						strFuncResult = objCreateUsers
								.selectResourceRights(seleniumPrecondition, strResource,
										false, true, false, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
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
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savVrfyUserWithSearchUser(seleniumPrecondition,
										strUserName, strByRole,
										strByResourceType, strByUserInfo,
										strNameFormat);
						log4j.info(strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

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
						strFuncResult = objViews.createView(seleniumPrecondition,
								strViewName, strVewDescription, strViewType,
								true, false, strSTvalue, false, strRSValue);

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

					log4j.info("~~~~~TEST-CASE" + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strInitPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.checkUpdateStatPrompt(
								selenium, strResource, statTypeName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.checkUpdateStatPrompt(
								selenium, strResource, statTypeName2);

						try {
							assertEquals("'Update Status' prompt for "
									+ statTypeName2 + " "
									+ "is NOT displayed or it is not expanded",
									strFuncResult);
							selenium.click(propElementDetails
									.getProperty("Cancel"));
							strFuncResult = "";
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.checkUpdateStatPrompt(
										selenium, strResource, statTypeName1);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews.navToUserView(
										selenium, strViewName);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals(strViewName
										+ " page is NOT displayed",
										strFuncResult);
								log4j
										.info("User is not allowed to use the application "
												+ "until he updates the status.");
								strFuncResult = "";

								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews
											.remindMeAndCheckPrompt(selenium,
													statTypeName1);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								try {
									assertEquals("", strFuncResult);
									// Wait for 10 mins
									Thread.sleep(720000);
									strFuncResult = objGeneral
											.waitForMailNotification(selenium,
													60,
													"//span[@class='overdue'][text()='Status is Overdue']");

								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}

								try {
									assertEquals("", strFuncResult);

									strFuncResult = objViews
											.checkUpdateStatPrompt(selenium,
													strResource, statTypeName1);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}

								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews
											.checkUpdateStatPrompt(selenium,
													strResource, statTypeName2);

									try {
										assertEquals(
												"'Update Status' prompt for "
														+ statTypeName2
														+ " is NOT displayed or it is not expanded",
												strFuncResult);
										gstrResult = "PASS";
									} catch (AssertionError Ae) {
										gstrReason = "'Update Status' prompt for "
												+ statTypeName2
												+ " is displayed.";
									}

								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}

							} catch (AssertionError Ae) {
								log4j
										.info("User is allowed to use the application until he updates the status.");
								gstrReason = "User is allowed to use the application until he updates the status.";
							}
						} catch (AssertionError Ae) {
							gstrReason = "'Update Status' prompt for "
									+ statTypeName2 + " is displayed.";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName2
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName2
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-23665";
			gstrTO = "Verify that user is required to update status of only the status types which are created without selecting 'Exempt from Must Update' when there are multiple status types with the resource.";
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
