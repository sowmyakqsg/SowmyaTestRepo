package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

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
 * Description :This class includes Role Based Status types  testcases 
 * Precondition:
 * Date :14-June-2012 
 * Author :QSG
 * '------------------------------------------------------------------- 
 * Modified Date 									Modified By ' 
 *   <Date> 											<Name> '
 *******************************************************************/



public class RoleBasedStatusTypes {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RoleBasedStatusTypes");
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

	Selenium selenium,seleniumFirefox,seleniumPrecondition;
	
/*	@BeforeClass
	public static void setUpInactivateRTAndST() throws Exception {

		Date gdtStartDate;
		ReadData rdExcel;

		String gstrTCID, gstrTO, gstrResult, gstrReason;
		String gstrTimetake = "", gstrdate, gstrBuild, gstrBrowserName, strSessionId = "";
		double gdbTimeTaken;
		long gslsysDateTime = 0;
		OfficeCommonFunctions objOFC;
		Properties propEnvDetails;

		Properties pathProps;

		Selenium selenium;

		gdtStartDate = new Date();
		gstrBrowserName = "IE 8";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();

		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Views objViews = new Views();

		try {

			gstrTCID = "Inactivate Status Type";
			gstrTO = "Inactivate Status Type";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.ReadData("Login", 3, 1);
			String strLoginPassword = rdExcel.ReadData("Login", 3, 2);
			String strRegn = rdExcel.ReadData("Login", 3, 4);
			
			String statpNumTypeName = "NST" + strTimeText;
			String statpTextTypeName = "TST" + strTimeText;
			String statpSaturatTypeName = "SST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			

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
				blnLogin = true;
				strFuncResult = objRT.navResourceTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				if (selenium
						.isElementPresent("//div[@id='mainContainer']/table" +
								"[@summary='Resource Types']/tbody/tr")) {
					int intResourceTypeCnt = selenium
							.getXpathCount(
									"//div[@id='mainContainer']/table" +
									"[@summary='Resource Types']/tbody/tr")
							.intValue();
					log4j.info(intResourceTypeCnt);

					for (int i = intResourceTypeCnt; i >= 1; i--) {
						log4j.info(i);
						
						if (i % 20 == 0) {

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.logout(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								blnLogin = false;
								strFuncResult = objLogin.login(selenium,
										strLoginUserName, strLoginPassword);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.navUserDefaultRgn(
										selenium, strRegn);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							try {
								assertEquals("", strFuncResult);
								blnLogin = true;
								strFuncResult = objRT
										.navResourceTypList(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

						}
						try {

							String strResrcTypName = selenium
									.getText("//div[@id='mainContainer']/table" +
											"[@summary='Resource Types']"
											+ "/tbody/tr[" + i + "]/td[2]");

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objRT.navToeditResrcTypepage(
										selenium, strResrcTypName);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objRT
										.activateAndDeactivateRTNew(selenium,
												strResrcTypName, false);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

						} catch (Exception e) {
							strFuncResult = e.toString();
							gstrReason = strFuncResult;

						}
					}

				} else {
					log4j.info("No Resource type found in Resource type list");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				if (selenium.isElementPresent("//div[@id='mainContainer']"
						+ "/table[@summary='Status" + " Types']/tbody/tr")) {
					int intStatusTypeCnt = selenium.getXpathCount(
							"//div[@id='mainContainer']/table"
									+ "[@summary='Status Types']/tbody/tr")
							.intValue();
					log4j.info(intStatusTypeCnt);
					if (intStatusTypeCnt == 2) {

						try {
							assertEquals("", strFuncResult);

							strFuncResult = objStatusTypes
									.selStatusTypesAndCreatePrivateST(selenium,
											strNSTValue, statpNumTypeName,
											strStatTypDefn, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} else if (intStatusTypeCnt == 1) {

				
						try {
							assertEquals("", strFuncResult);

							strFuncResult = objStatusTypes
									.selStatusTypesAndCreatePrivateST(selenium,
											strNSTValue, statpNumTypeName,
											strStatTypDefn, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							strFuncResult = objStatusTypes
									.selStatusTypesAndCreatePrivateST(selenium,
											strTSTValue, statpTextTypeName,
											strStatTypDefn, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					}
					for (int i = intStatusTypeCnt; i >= 4; i--) {
						log4j.info(i);

						if (i % 20 == 0) {

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.logout(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								blnLogin = false;
								strFuncResult = objLogin.login(selenium,
										strLoginUserName, strLoginPassword);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.navUserDefaultRgn(
										selenium, strRegn);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							try {
								assertEquals("", strFuncResult);
								blnLogin = true;
								strFuncResult = objStatusTypes
										.navStatusTypList(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

						}
						try {

							String strStatusTypName = selenium
									.getText("//div[@id='mainContainer']/table"
											+ "[@summary='Status Types']"
											+ "/tbody/tr[" + i + "]/td[2]");
							String strStatusTyp = selenium
									.getText("//div[@id='mainContainer']/table"
											+ "[@summary='Status Types']"
											+ "/tbody/tr[" + i + "]/td[3]");

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objStatusTypes
										.editStatusTypePage(selenium,
												strStatusTypName, strStatusTyp);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objStatusTypes
										.activateAndDeactivateST(selenium,
												strStatusTypName, false, true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

						} catch (Exception e) {
							strFuncResult = e.toString();
							gstrReason = strFuncResult;

						}

					}
					String strStatusTypName = selenium
							.getText("//div[@id='mainContainer']/table"
									+ "[@summary='Status Types']"
									+ "/tbody/tr[1]/td[2]");
					String strStatusTyp = selenium
							.getText("//div[@id='mainContainer']/table"
									+ "[@summary='Status Types']"
									+ "/tbody/tr[1]/td[3]");

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatusTypes.editStatusTypePage(
								selenium, strStatusTypName, strStatusTyp);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatusTypes.selectVisibiltyValue(
								selenium, strStatusTypName, "PRIVATE", true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} else {
					log4j.info("No Status type found in Status type list");
					
					try {
						assertEquals("", strFuncResult);

						strFuncResult = objStatusTypes
								.selStatusTypesAndCreatePrivateST(selenium,
										strNSTValue, statpNumTypeName,
										strStatTypDefn, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						strFuncResult = objStatusTypes
								.selStatusTypesAndCreatePrivateST(selenium,
										strTSTValue, statpTextTypeName,
										strStatTypDefn, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						strFuncResult = objStatusTypes
								.selStatusTypesAndCreatePrivateST(selenium,
										strSSTValue, statpSaturatTypeName,
										strStatTypDefn, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					String strStatusTypName = selenium
							.getText("//div[@id='mainContainer']/table"
									+ "[@summary='Status Types']"
									+ "/tbody/tr[1]/td[2]");
					String strStatusTyp = selenium
							.getText("//div[@id='mainContainer']/table"
									+ "[@summary='Status Types']"
									+ "/tbody/tr[1]/td[3]");

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatusTypes.editStatusTypePage(
								selenium, strStatusTypName, strStatusTyp);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatusTypes.selectVisibiltyValue(
								selenium, strStatusTypName, "PRIVATE", true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}

				statpNumTypeName = selenium
						.getText("//div[@id='mainContainer']/table"
								+ "[@summary='Status Types']"
								+ "/tbody/tr[1]/td[2]");

				statpTextTypeName = selenium
						.getText("//div[@id='mainContainer']/table"
								+ "[@summary='Status Types']"
								+ "/tbody/tr[2]/td[2]");

				statpSaturatTypeName = selenium
						.getText("//div[@id='mainContainer']/table"
								+ "[@summary='Status Types']"
								+ "/tbody/tr[3]/td[2]");
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
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				if (selenium.isElementPresent("//li[starts-with(@id,'g_')]")) {
					int intSectionCnt = selenium.getXpathCount(
							"//li[starts-with(@id,'g_')]").intValue();
					log4j.info(intSectionCnt);

					for (int i = intSectionCnt; i >= 1; i--) {
						log4j.info(i);
						if (i % 10 == 0) {

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.logout(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								blnLogin = false;
								strFuncResult = objLogin.login(selenium,
										strLoginUserName, strLoginPassword);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.navUserDefaultRgn(
										selenium, strRegn);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								blnLogin = true;
								strFuncResult = objViews
										.navRegionViewsList(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews
										.navToEditResDetailViewSections(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

						}

						String strSectionName = selenium.getText("//li[" + i
								+ "][starts-with(@id,'g_')]/div/span");
						String strSectionNames[] = strSectionName.split("\\(");
						strSectionName = strSectionNames[0];

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.deleteSection(selenium,
									strSectionName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.navToEditResDetailViewSections(selenium);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					}

					try {
						assertEquals("", strFuncResult);
						String strSection = "AB" + System.currentTimeMillis();
						String[] strStatType = { statpNumTypeName,
								statpTextTypeName, statpSaturatTypeName };
						strFuncResult = objViews.dragAndDropSTtoSection(
								selenium, strStatType, strSection, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} else {
					log4j.info("No Section found in Status type list");

					try {
						assertEquals("", strFuncResult);
						String strSection = "AB" + System.currentTimeMillis();
						String[] strStatType = { statpNumTypeName,
								statpTextTypeName, statpSaturatTypeName };
						strFuncResult = objViews.dragAndDropSTtoSection(
								selenium, strStatType, strSection, true);

					} catch (AssertionError Ae) {
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
				gstrReason = strFuncResult;
			}
			
		} catch (Exception e) {
			gstrTCID = "Inactivate Status Type";
			gstrTO = "Inactivate Status Type";
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

		// kill browser
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

		FILE_PATH = pathProps.getProperty("Resultpath");
		// and execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		gstrBuild = propEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);

	}*/

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
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		selenium.start();
		selenium.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");
		
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}
		try{
			seleniumFirefox.close();
		}catch(Exception e){
			
		}
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		selenium.stop();	
		seleniumFirefox.stop();
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	

	/***************************************************************************************
	 * 'Description Create a status type ST selecting a role R1 under �Roles
					 * with view rights� and �Roles with update rights� sections, associate ST
					 * with resource RS at the resource type level and verify that the user with
					 * role R1 and �Update Status� right on resource RS receive expired status
					 * notification for resource RS when the status of ST expires. 'Precondition
					 * : Preconditions:
	 * 
	 * 1.Status type 'ST1'(Multi status type) is created by associating the
	 * expiration time (say 'T1') and selecting role 'R1' under both to view and
	 * update the status type.
	 * 
	 * 2.Status type 'ST2' (Multi status type) is created by associating the
	 * shift time (say 'S1') and selecting role 'R1' under both to view and
	 * update the status type.
	 * 
	 * 3.Resource 'RS' is created by proving address under the resource type
	 * 'RT', associating 'ST1' and 'ST2' at the 'RS' level.
	 * 
	 * 4.User A is associated with role 'R1'.
	 * 
	 * 5.User A has subscribed to receive expired status notifications via
	 * E-mail and pager.
	 * 
	 * 6.User A has �Update Status� right on resource 'RS'. 'Arguments :None
	 * 'Returns :None 'Date :18-June-2012 'Author :QSG
	 * '-----------------------------------------------------------------------
	 * 'Modified Date 										Modified By 
	 * '  <date> 											   <Name>
	 ************************************************************************/

	@Test
	public void testBQS44200() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-44200 ";
			gstrTO = "Create a status type ST selecting a role R1 under "
					+ "�Roles with view rights� and �Roles with update"
					+ " rights� sections, associate ST with resource RS at"
					+ " the resource type level and verify that the user "
					+ "with role R1 and �Update Status� right on resource "
					+ "RS receive expired status notification for resource"
					+ " RS when the status of ST expires.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));

			int intPositionShift = 0;
			int intPositionExpire = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intTimeDiffOutPut = 0;

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			String StatusTime = "";
			String strUpdTime = "";
			String strUpdTime_Shift = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurDate = "";

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");

			String strAbbrv = "A" + strTmText;

			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserNameA;

			String strRoleValue = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strMsgBody2Expire = "";
			String strMsgBody1Expire = "";
			String strMsgBody2ExpireAnother = "";
			String strMsgBody1ExpireAnother = "";

			String strMsgBody2Shift = "";// Pager body
			String strMsgBody1Shift = "";// Email body
			String strMsgBody2ShiftAnother = "";
			String strMsgBody1ShiftAnother = "";

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			// Navigate to Role list
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameShift);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[1], true);
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

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(seleniumPrecondition,
						statTypeNameShift, strStatusTypeValueShift);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							seleniumPrecondition, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
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

			log4j
					.info("~~~~~TEST-CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

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
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					intTimeDiffOutPut = intTimeDiffOutPut * 1000;
					Thread.sleep(intTimeDiffOutPut);

					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, 30, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("//span[text()='"
						+ strStatusNameExpire1
						+ "']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;

				String strCurYear = dts.getTimeOfParticularTimeZone("CST",
						"yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time for NST: " + strUpdTime);

				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
						"HH:mm");

				strMsgBody1Expire = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1ExpireAnother = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Expire = "EMResource expired status: " + strResource
						+ ". " + statTypeNameExpire + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody2ExpireAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameExpire
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strAdUpdTimeShift = dts.addTimetoExisting(
						strUpdTime_Shift, 1, "HH:mm");

				strMsgBody1Shift = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime_Shift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1ShiftAnother = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Shift = "EMResource expired status: " + strResource
						+ ". " + statTypeNameShift + " status expired "
						+ strCurDate + " " + strUpdTime_Shift + ".";

				strMsgBody2ShiftAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameShift
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertTrue(strFuncResult.equals(""));
				for (int i = 0; i < 2; i++) {

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBody2Expire)
								|| strMsg.equals(strMsgBody2ExpireAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Expire
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBody2Shift)
								|| strMsg.equals(strMsgBody2ShiftAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Shift
									+ " is displayed for Pager Notification");
						} else {
							log4j.info("Pager is NOT displayed ");
							gstrReason = "Pager is NOT displayed ";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			for (int i = 0; i < 4; i++) {

				strSubjName = "EMResource Expired Status Notification: "
						+ strResource;
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);

					if (strMsg.equals(strMsgBody1Expire)
							|| strMsg.equals(strMsgBody1ExpireAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Expire
								+ " is displayed for Email Notification");
					} else if (strMsg.equals(strMsgBody1Shift)
							|| strMsg.equals(strMsgBody1ShiftAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Shift
								+ " is displayed for Email Notification");
					} else {
						log4j.info("Email is NOT displayed ");
						gstrReason = "Email is NOT displayed ";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-44200";
			gstrTO = "Create a status type ST selecting a role R1 under"
					+ " �Roles with view rights� and �Roles with update rights"
					+ "� sections, associate ST with resource RS at the resource"
					+ " type level and verify that the user with role R1 and "
					+ "�Update Status� right on resource RS receive expired"
					+ " status notification for resource RS when the status of ST expires.";
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
	
	
	
	/*****************************************************************************************
	 * 'Description :Create a status type ST selecting a role R1 under �Roles
					 with view rights� ' section, associate ST with resource RS at the
					 resource type level and verify ' that the user with role R1 and �Update
					 Status� right on resource RS receives ' expired status notification for
					 resource RS when the status of ST expires.
					 
	 * 'Precondition :1.Status type 'ST1' (Multi status type) is created by
					 associating the expiration time (say 'T1') and selecting only role 'R1'
					 under view right and role 'R2' under both view and update right section.
					 
					 2.Status type 'ST2' (Multi status type) is created by associating the
					 shift time (say 'S1') and selecting only role 'R1' under view right and
					 role 'R2' under both view and update right section.
					 
					 3.Resource 'RS' is created by proving address under the resource type
					 'RT', associating 'ST1' and 'ST2' at the 'RT' level.
					 
					 4.User B is associated with role 'R2'.
					 
					 5.User A has subscribed to receive expired status notifications via
					 E-mail and pager and is associated with a role 'R1'.
					 
					 6.User A and User B has �Update Status� right on resource 'RS'.
	 * 'Arguments    :None  
	 * 'Returns 	 :None 
	 * 'Date 		 :19-June-2012
	 * 'Author		 :QSG
	 * '-----------------------------------------------------------------------
	 * 'Modified Date									 Modified By 
	 * '<date>											   <Name>
	 ************************************************************************/

	@Test
	public void testBQS44217() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-44217 ";
			gstrTO = "Create a status type ST selecting a role R1 under �Roles with view"
					+ " rights� section, associate ST with resource RS at the resource type"
					+ " level and verify that the user with role R1 and �Update Status� right"
					+ " on resource RS receives expired status notification for resource RS "
					+ "when the status of ST expires.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));

			int intPositionShift = 0;
			int intPositionExpire = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intTimeDiffOutPut = 0;

			// Admin User Name
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTmText = dts.getCurrentDate("HHmm");

			// Status Shift time
			String StatusTime = "";
			String strUpdTime = "";

			String strUpdTime_Shift = "";

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurDate = "";

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			

			String strAbbrv = "A" + strTmText;

			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strRoleValue[] = new String[2];
			strRoleValue[0] = "";
			strRoleValue[1] = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName_1 = "AutoR_1" + strTimeText;
			String strRoleName_2 = "AutoR_2" + strTimeText;

			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";

			// Email sub Name
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strMsgBody2Expire = "";
			String strMsgBody1Expire = "";
			String strMsgBody2ExpireAnother = "";
			String strMsgBody1ExpireAnother = "";

			String strMsgBody2Shift = "";// Pager body
			String strMsgBody1Shift = "";// Email body
			String strMsgBody2ShiftAnother = "";
			String strMsgBody1ShiftAnother = "";

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Navigate to Role list and create Role 1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName_1, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName_1);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list and create Role 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName_2, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName_2);

				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleViewValue = { { strRoleValue[0], "true" },
						{ strRoleValue[1], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[0], "false" },
						{ strRoleValue[1], "true" } };

				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition,
						false, false, strRoleViewValue, strRoleUpdateValue,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleViewValue = { { strRoleValue[0], "true" },
						{ strRoleValue[1], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[0], "false" },
						{ strRoleValue[1], "true" } };

				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition,
						false, false, strRoleViewValue, strRoleUpdateValue,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameShift);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[1], true);
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
			// Associate Role R2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[1], true);

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
						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User A
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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Associate Role R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[0], true);

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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(seleniumPrecondition,
						statTypeNameShift, strStatusTypeValueShift);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							seleniumPrecondition, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					intTimeDiffOutPut = intTimeDiffOutPut * 1000;
					Thread.sleep(intTimeDiffOutPut);

					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, 30, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("//span[text()='"
						+ strStatusNameExpire1
						+ "']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;

				String strCurYear = dts.getTimeOfParticularTimeZone("CST",
						"yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time for NST: " + strUpdTime);

				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
						"HH:mm");

				strMsgBody1Expire = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				Writer output = null;
				String text = strMsgBody1Expire;
				File file = new File("C:\\Documents and Settings\\All Users\\Desktop\\strMsgBody1Expire.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();
				
				strMsgBody1ExpireAnother = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Expire = "EMResource expired status: " + strResource
						+ ". " + statTypeNameExpire + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody2ExpireAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameExpire
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);

				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime_Shift, 1,
						"HH:mm");

				strMsgBody1Shift = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime_Shift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1ShiftAnother = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Shift = "EMResource expired status: " + strResource
						+ ". " + statTypeNameShift + " status expired "
						+ strCurDate + " " + strUpdTime_Shift + ".";

				strMsgBody2ShiftAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameShift
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertTrue(strFuncResult.equals(""));
				for (int i = 0; i < 2; i++) {

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBody2Expire)
								|| strMsg.equals(strMsgBody2ExpireAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Expire
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBody2Shift)
								|| strMsg.equals(strMsgBody2ShiftAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Shift
									+ " is displayed for Pager Notification");
						} else {
							log4j.info("Pager is NOT displayed ");
							gstrReason = "Pager is NOT displayed ";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			
			for (int i = 0; i < 4; i++) {

				strSubjName = "EMResource Expired Status Notification: "
						+ strResource;
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);
					
					Writer output = null;
					String text = strMsg;
					File file = new File("C:\\Documents and Settings\\All Users\\Desktop\\strMsg"+i+".txt");
					output = new BufferedWriter(new FileWriter(file));
					output.write(text);
					output.close();
					

					if (strMsg.equals(strMsgBody1Expire)
							|| strMsg.equals(strMsgBody1ExpireAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Expire
								+ " is displayed for Email Notification");
					} else if (strMsg.equals(strMsgBody1Shift)
							|| strMsg.equals(strMsgBody1ShiftAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Shift
								+ " is displayed for Email Notification");
					} else {
						log4j.info("Email is NOT displayed ");
						gstrReason = "Email is NOT displayed ";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-44217";
			gstrTO = "Create a status type ST selecting a role R1 under �Roles with view"
					+ " rights� section, associate ST with resource RS at the resource type"
					+ " level and verify that the user with role R1 and �Update Status� right"
					+ " on resource RS receives expired status notification for resource RS"
					+ " when the status of ST expires.";
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
	
	/*****************************************************************************************
	 * 'Description :Create a status type ST selecting a role R1 under �Roles with view rights�
	 * '			 section, associate ST with resource RS at the resource level and verify 
	 * '			 that the user with role R1 and �Update Status� right on resource RS receives
	 * '			 expired status notification for resource RS when the status of ST expires.
					 
	 * 'Precondition :1.Status type 'ST1' (Multi status type) is created by
					 associating the expiration time (say 'T1') and selecting only role 'R1'
					 under view right and role 'R2' under both view and update right section.
					 
					 2.Status type 'ST2' (Multi status type) is created by associating the
					 shift time (say 'S1') and selecting only role 'R1' under view right and
					 role 'R2' under both view and update right section.
					 
					 3.Resource 'RS' is created by proving address under the resource type
					 'RT', associating 'ST1' and 'ST2' at the 'RT' level.
					 
					 4.User B is associated with role 'R2'.
					 
					 5.User A has subscribed to receive expired status notifications via
					 E-mail and pager and is associated with a role 'R1'.
					 
					 6.User A and User B has �Update Status� right on resource 'RS'.
	 * 'Arguments    :None  
	 * 'Returns 	 :None 
	 * 'Date 		 :20-June-2012
	 * 'Author		 :QSG
	 * '-----------------------------------------------------------------------
	 * 'Modified Date									 Modified By 
	 * '<date>											   <Name>
	 ************************************************************************/

	@Test
	public void testBQS44279() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-44279 ";
			gstrTO = "Create a status type ST selecting a role R1 under �Roles "
					+ "with view rights� section, associate ST with resource RS"
					+ " at the resource level and verify that the user with role"
					+ " R1 and �Update Status� right on resource RS receives expired"
					+ " status notification for resource RS when the status of ST expires.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));
			
			// Admin User Name
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status Shift time
			String StatusTime = "";
			String strUpdTime = "";

			String strUpdTime_Shift = "";

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";
			
			int intPositionShift = 0;
			int intPositionExpire = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intTimeDiffOutPut = 0;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurDate = "";

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");

			String strAbbrv = "A" + strTmText;

			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strRoleValue[] = new String[2];
			strRoleValue[0] = "";
			strRoleValue[1] = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName_1 = "AutoR_1" + strTimeText;
			String strRoleName_2 = "AutoR_2" + strTimeText;

			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";

			String strSubjName ="";

			String strMsgBody2Expire = "";
			String strMsgBody1Expire = "";
			String strMsgBody2ExpireAnother = "";
			String strMsgBody1ExpireAnother = "";

			String strMsgBody2Shift = "";// Pager body
			String strMsgBody1Shift = "";// Email body
			String strMsgBody2ShiftAnother = "";
			String strMsgBody1ShiftAnother = "";
			
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Navigate to Role list and create Role 1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName_1, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName_1);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list and create Role 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName_2, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName_2);

				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleViewValue = { { strRoleValue[0], "true" },
						{ strRoleValue[1], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[0], "false" },
						{ strRoleValue[1], "true" } };

				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition,
						false, false, strRoleViewValue, strRoleUpdateValue,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeNameExpire);
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
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								seleniumPrecondition, statTypeNameExpire,
								strStatusNameExpire1, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								seleniumPrecondition, statTypeNameExpire,
								strStatusNameExpire2, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameExpire, strStatusNameExpire1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameExpire, strStatusNameExpire2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleViewValue = { { strRoleValue[0], "true" },
						{ strRoleValue[1], "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue[0], "false" },
						{ strRoleValue[1], "true" } };

				strFuncResult = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition,
						false, false, strRoleViewValue, strRoleUpdateValue,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeNameShift);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strSTvalue[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status type value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeNameShift,
								strStatusNameShift1, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeNameShift,
								strStatusNameShift2, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameShift, strStatusNameShift1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameShift, strStatusNameShift2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

		

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

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
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
			// Associate Role R2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[1], true);

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
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				strInitPwd = rdExcel.readInfoExcel("User_Template", 7, 9,
						strFILE_PATH);

				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(seleniumPrecondition, strUserNameB,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(seleniumPrecondition
							.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
							1);

					assertTrue(seleniumPrecondition
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserNameB + "']"));
					log4j.info("User " + strUserNameB
							+ " Present in the User List ");

				} catch (AssertionError Ae) {
					strFuncResult = "User " + strUserNameB
							+ " NOT Present in the User List " + Ae;
					log4j.info("User " + strUserNameB
							+ " NOT Present in the User List " + Ae);
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User A
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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Associate Role R1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[0], true);

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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(seleniumPrecondition,
						statTypeNameShift, strStatusTypeValueShift);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							seleniumPrecondition, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					intTimeDiffOutPut = intTimeDiffOutPut * 1000;
					Thread.sleep(intTimeDiffOutPut);

					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, 30, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("//span[text()='"
						+ strStatusNameExpire1
						+ "']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;

				String strCurYear = dts.getTimeOfParticularTimeZone("CST",
						"yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time for NST: " + strUpdTime);

				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
						"HH:mm");

				strMsgBody1Expire = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1ExpireAnother = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Expire = "EMResource expired status: " + strResource
						+ ". " + statTypeNameExpire + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody2ExpireAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameExpire
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strAdUpdTimeShift = dts.addTimetoExisting(
						strUpdTime_Shift, 1, "HH:mm");

				strMsgBody1Shift = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime_Shift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1ShiftAnother = "For "
						+ strUsrFulNameA
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Shift = "EMResource expired status: " + strResource
						+ ". " + statTypeNameShift + " status expired "
						+ strCurDate + " " + strUpdTime_Shift + ".";

				strMsgBody2ShiftAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameShift
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertTrue(strFuncResult.equals(""));
				for (int i = 0; i < 2; i++) {

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBody2Expire)
								|| strMsg.equals(strMsgBody2ExpireAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Expire
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBody2Shift)
								|| strMsg.equals(strMsgBody2ShiftAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Shift
									+ " is displayed for Pager Notification");
						} else {
							log4j.info("Pager is NOT displayed ");
							gstrReason = "Pager is NOT displayed ";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			for (int i = 0; i < 4; i++) {

				strSubjName = "EMResource Expired Status Notification: "
						+ strResource;
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);

					if (strMsg.equals(strMsgBody1Expire)
							|| strMsg.equals(strMsgBody1ExpireAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Expire
								+ " is displayed for Email Notification");
					} else if (strMsg.equals(strMsgBody1Shift)
							|| strMsg.equals(strMsgBody1ShiftAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Shift
								+ " is displayed for Email Notification");
					} else {
						log4j.info("Email is NOT displayed ");
						gstrReason = "Email is NOT displayed ";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-44279";
			gstrTO = "Create a status type ST selecting a role R1 under �Roles with "
					+ "view rights� section, associate ST with resource RS at the "
					+ "resource level and verify that the user with role R1 and �"
					+ "Update Status� right on resource RS receives expired status "
					+ "notification for resource RS when the status of ST expires.";
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
	'Description		:Verify that when the view permissions of a status type are edited, the changes are reflected on �My Status Change Preferences� screen.
	'Precondition		:1.Provide user A with the following rights:
						a.Setup Status Types.
						b.Edit Status Change Notification Preferences.
						c.Setup Resource Types.
						2.Role 'R1' is associated with User A.					
						3.Resource 'RS' is created under resource Type 'RT' selecting 'ST' at 'RT' level. 
	'Arguments		:None
	'Returns		:None
	'Date			:18/6/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS44299() throws Exception {
		try {

			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Preferences objPreferences = new Preferences();
			Resources objResources = new Resources();
			Roles objRoles = new Roles();
			CreateUsers objCreateUsers = new CreateUsers();

			gstrTCID = "44299"; // Test Case Id
			gstrTO = " Verify that when the view permissions of a status type are "
					+ "edited, the changes are reflected on �My Status Change"
					+ " Preferences� screen.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFuncResult = "";

			// Admin User Name
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			// Rol
			String strRolesName = "Rol299" + System.currentTimeMillis();
			String strRoleValue = "";

			// ST creation and verify data
			String strStatusTypeValue = "Number";

			String strStatusTypeValues[] = new String[4];

			String statTypeName1 = "ST1" + System.currentTimeMillis();
			String statTypeName2 = "ST2" + System.currentTimeMillis();
			String statTypeName3 = "ST3" + System.currentTimeMillis();
			String statTypeName4 = "ST4" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
			String strSTtype = "Number";
			String strStandardSTtype = "";
			String strDescription = "Automation";
			String strStatues = "";
			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			// Resource
			String strResource = "RS299" + System.currentTimeMillis();
			String strHavBed = "No";
			String strTmText = dts.getCurrentDate("HHmm");

			String strAbbrv = "A" + strTmText;

			String strResVal = "";
			String strRSValue[] = new String[1];

			// add res data
			String strSearchCategory = "(Any)";
			String strRegion = "";
			String strSearchWhere = "";
			String strSearchState = "(Any)";

			// Precondition

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
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName4,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(seleniumPrecondition,
						statTypeName4, strSTtype, strStandardSTtype,
						strDescription, strStatues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName4);

				if (strStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, strResrctTypName, strStatusTypeValues[3]);
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
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
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
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(seleniumPrecondition,
						strResource, strHavBed, "", strAbbrv, strResrctTypName);

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
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource, false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer"),
								true);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

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
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type,and Click on 'Create New Status Types' button. Expected
			 * Result:'Select Status Type' screen is displayed.
			 */
			// 259142

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Number','Multi','Text' or 'Saturation' from
			 * the 'Select Type' dropdown list (If 'Number' is selected from
			 * 'Select Type' dropdown list) and then click on 'Next'. Expected
			 * Result:Create Number Status Type' screen is displayed.
			 */
			// 259182

			/*
			 * STEP : Action:Create a status type 'ST1' by filling all the
			 * mandatory data and select role 'R1' under both 'Roles with view
			 * rights' and 'Roles with update rights' sections, then click on
			 * 'Save' Expected Result:Status type 'ST1' is displayed in 'Status
			 * Type List' screen.
			 */
			// 259218

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };// AutoRol299
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName1, strSTtype, strStandardSTtype,
						strDescription, strStatues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Create a status type 'ST2' by filling all the
			 * mandatory data and select role 'R1' only under 'Roles with view
			 * rights' sections, then click on 'Save'. Expected Result:Status
			 * type 'ST2' is displayed in 'Status Type List' screen.
			 */
			// 259253

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "false" } };// AutoRol299
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName2, strSTtype, strStandardSTtype,
						strDescription, strStatues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Create a status type 'ST3' by filling all the
			 * mandatory data and DO NOT select any role,click on 'Save'.
			 * Expected Result:Status type 'ST3' is displayed in 'Status Type
			 * List' screen.
			 */
			// 259274

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "false" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "false" } };// AutoRol299
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName3, strSTtype, strStandardSTtype,
						strDescription, strStatues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resource Type, and click on
			 * 'Edit' link associated with 'RT' and select 'ST1'.'ST2' and 'ST3'
			 * under 'Status Types' section and click on 'Save'. Expected
			 * Result:'RT' is displayed in 'Resource Type List' screen
			 */
			// 259333

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[0], true);
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
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
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
			 * STEP : Action:Logout and login as User A and navigate to
			 * Preferences >> Status Change Prefs, Add resource 'RS' for
			 * notification Expected Result:Status type 'ST1' and 'ST2' are
			 * displayed and 'ST3' is not displayed in 'Edit My Status Change
			 * Preferences' screen
			 */
			// 259350

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
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
				strFuncResult = objPreferences.navTOFindResourcesPage(selenium,
						strResource, strSearchCategory, strRegion,
						strSearchWhere, strSearchState, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "", "false", "false", "false" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName1, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "", "false", "false", "false" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName2, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "", "false", "false", "false" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName3, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Status Type,and Click on
			 * 'Edit' link associated with 'ST1', deselect the role 'R1' from
			 * both view and update rights, and click on 'Save'. Expected
			 * Result:Status type 'ST1' is displayed in 'Status Type List'
			 * screen.
			 */
			// 259353

			try {
				assertEquals(
						" Status Type "
								+ statTypeName3
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName1, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "false" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "false" } };// AutoRol299
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName1, strSTtype, strStandardSTtype,
						strDescription, strStatues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with 'ST2',select a
			 * role 'R1' for both view and update rights, and click on 'Save'.
			 * Expected Result:Status type 'ST2' is displayed in 'Status Type
			 * List' screen.
			 */
			// 259356
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName2, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };// AutoRol299
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName2, strSTtype, strStandardSTtype,
						strDescription, strStatues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with 'ST3',select a
			 * role 'R1' only for view right, and click on 'Save'. Expected
			 * Result:Status type 'ST3' is displayed in 'Status Type List'
			 * screen.
			 */
			// 259376
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName3, strSTtype);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "false" } };// AutoRol299
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyST(selenium,
						statTypeName3, strSTtype, strStandardSTtype,
						strDescription, strStatues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Logout and login as User A and navigate to
			 * Preferences >> Status Change Prefs, Add resource 'RS' for
			 * notification. Expected Result:Status type 'ST2' and 'ST3' are
			 * displayed and 'ST1' is not displayed in 'Edit My Status Change
			 * Preferences' screen
			 */
			// 259412

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
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
				strFuncResult = objPreferences.navTOFindResourcesPage(selenium,
						strResource, strSearchCategory, strRegion,
						strSearchWhere, strSearchState, strResVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "", "false", "false", "false" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName2, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "", "false", "false", "false" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName3, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData = { { "", "false", "false", "false" } };
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statTypeName1, strNotifData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(
						" Status Type "
								+ statTypeName1
								+ " is NOT displayed with appropriate notification methods",
						strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-44299";
			gstrTO = "Verify that when the view permissions of a status type are edited, the changes are reflected on �My Status Change Preferences� screen.";
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
	
	/***********************************************************************
	'Description	:Create status type ST selecting a role R1 both under �Roles with view rights� and �Roles with Update rights� sections, associate ST with resource RS at resource TYPE level and verify that user with role R1 and with �Update Status� right on RS CAN view and update the status of ST from following screens:

				a. Region view
				b. Map screen (from resource pop window)
				c. Custom view
				d. View Resource Detail
				e. Event detail
	'Precondition	:Preconditions:
					1.Provide user A with the following rights:
					a.Setup Status Types.
					b.Setup Resource Types.
					c.Setup Resources.
					d.Edit Resources Only.
					e.Maintain Events.
					f.Maintain Event Templates.
					g.Configure region views.
					h.View Custom View
					
					2.Resource 'RS1' is created with address under resource Type 'RT1' associating 'ST1' at the 'RT1' level.
					
					3.Role 'R1' is associated with User A.
					
					4.View 'V1' is created selecting 'RS1' and 'ST1'.
					
					5.Event template 'ET1' is created selecting 'RT1' and 'ST1'.
					
					6.User A is having �Update Status� and 'View Resource' right on resource 'RS1'.
	'Arguments		:None
	'Returns		:None
	'Date	 		:03-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                               <Name>
	************************************************************************/

	
	@Test
	public void testBQS99087() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		Preferences objPref=new Preferences();
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT=new ResourceTypes();
		Resources objRs=new Resources();
		CreateUsers objCreateUsers=new CreateUsers();
		Views objViews=new Views();
		Roles objRole=new Roles();
		ViewMap objMap=new ViewMap();
		EventSetup objEve=new EventSetup();
	
		EventList objELs=new EventList();
		try {
			gstrTCID = "99087";
			gstrTO = "Create status type ST selecting a role R1 both under " +
					"�Roles with view rights� and �Roles with Update rights�" +
					" sections, associate ST with resource RS at resource TYPE " +
					"level and verify that user with role R1 and with �Update " +
					"Status� right on RS CAN view and update the status of ST from following screens: " +
					"a. Region view " +
					"b. Map screen (from resource pop window) " +
					"c. Custom view " +
					"d. View Resource Detail " +
					"e. Event detail";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
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
			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strEveTemp="AutoET_"+strTimeText;
			String strEveName="AutoEve_"+strTimeText;
	
			String strTempDef=rdExcel.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=rdExcel.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=rdExcel.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
			//String strIconSrc=rdExcel.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			String strIconSrc=rdExcel.readInfoExcel("FirefoxTestData", 2, 3, strFILE_PATH);
		
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
		
				
			String strComment1="st1";
						
			String strNSTValue="Number";
			String strNumStatType="A11_"+strTimeText;
			String strStatTypDefn="Auto";
							
			String strMSTValue="Multi";
			String strMultiStatType="A1_"+strTimeText;
			
			/*String strTSTValue="Text";
			String strTxtStatType="AutoTSt_"+strTimeText;
						
			String strSSTValue="Saturation Score";
			String strSatuStatType="AutoScSt_"+strTimeText;*/
			
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
					
			String strStatTypeColor="Black";
			
			String strResrctTypName="AutoRt_"+strTimeText;
			String strStatValue="";
					
			String strResource="AutoRs_"+strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
					
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=rdExcel.readData("Login", 4, 2);
			String strConfirmPwd=rdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			
			String strRoleValue="";
				
			String strSTvalue[]=new String[1];
			String strMSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			String strRTValue[]=new String[1];
			
			String strResVal="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			strSTvalue[0]="";
					
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Summary Plus (Resources as rows. Status types and comments as columns.)";
			
				
			String[] strRoleStatType = { strNumStatType,strMultiStatType};		
			String strStatusValue[]=new String[2];
			
			String strSection="AB_"+strTimeText;
			
			
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
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNSTValue, strNumStatType, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strNumStatType);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTvalue[0]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResrctTypName, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {
					
					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
					

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
								
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFuncResult="";
					strRSValue[0]=strResVal;
				}else{
					strFuncResult="Failed to fetch Resource value";
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
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch Role value";
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
				
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
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource, false, true, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.Advoptn.SetUPResources"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.CustomView"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertTrue(strFuncResult.equals(""));	
			
				strFuncResult= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(seleniumPrecondition));
				strFuncResult="";	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));	
				//navigate to Event Template
				strFuncResult=objEve.createEventTemplate(seleniumPrecondition);
								
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));
				// fill the required fields in Create Event Template and save
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(
						seleniumPrecondition, strEveTemp, strTempDef,
						strEveColor, strAsscIcon, strIconSrc, "", "", "", "",
						true, strRTValue, strSTvalue, true, false, false);

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
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strMSTValue, strMultiStatType, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String []strRoleVal={strRoleValue};
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false, false, strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strMultiStatType);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strMSTvalue[0]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}						
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						strMultiStatType, strStatusName1);
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						selenium, strMultiStatType, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						strMultiStatType, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navToeditResrcTypepage(selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			for(int intST=0;intST<strMSTvalue.length;intST++){
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium, strMSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium, strResrctTypName);
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
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(selenium, strMSTvalue[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(selenium, strRTValue[0], true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium, strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox, strRoleStatType, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium, strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(selenium, strStatusValue[0], strMSTvalue[0], "", strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium, strMultiStatType,  strStatusName1,"4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(selenium, strResVal, strMSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(selenium, strStatusValue[1], strMSTvalue[0], "", "View Resource Detail");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium, strSection, strMultiStatType, strStatusName2, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				
				strFuncResult = objMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(selenium, strStatusValue[0], strMSTvalue[0],strComment1, "Regional Map View");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objMap.verifyUpdSTValandComments(selenium,strResource, strStatusName1,strComment1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertTrue(strFuncResult.equals(""));	
			
				strFuncResult= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFuncResult="";	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));	
			
				strFuncResult=objEve.editEventTemplate(selenium, strEveTemp);
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));	
			
				strFuncResult=objEve.selectAndDeselectST(selenium, strMSTvalue, true, false);
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));	
			
				strFuncResult=objEve.savAndVerifyEventtemplate(selenium, strEveTemp);
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));	
				strFuncResult= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFuncResult="";	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));
								
				strFuncResult=objEve.createNewEvent(selenium, strEveTemp, strResource, strEveName, "AutoEvent", true);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objELs.clickEventBanner(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium, strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(selenium, strStatusValue[1], strMSTvalue[0], "", "Event Status");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium, strMultiStatType,  strStatusName2,"4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objPref.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			String[] strArRes={strResource};
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objPref.createCustomViewNew(selenium, strArRes, strResrctTypName, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium, strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(selenium, strStatusValue[0], strMSTvalue[0], "", "Custom View - Table");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPref.verifyUpdateSTValInCustomView(selenium, strMultiStatType,  strStatusName1,"3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navViewCustomTableOrCustmViewMap(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				
				strFuncResult = objMap.verifyStatTypesInResourcePopup_ShowMap(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(selenium, strStatusValue[1], strMSTvalue[0],strComment1, "Custom View - Map");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objMap.verifyUpdSTValandComments(selenium,strResource, strStatusName2,strComment1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult="PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "99087";
			gstrTO = "Create status type ST selecting a role R1 both under �Roles with view rights� and �Roles with Update rights� sections, associate ST with resource RS at resource TYPE level and verify that user with role R1 and with �Update Status� right on RS CAN view and update the status of ST from following screens: " +
					"a. Region view " +
					"b. Map screen (from resource pop window) " +
					"c. Custom view " +
					"d. View Resource Detail " +
					"e. Event detail";
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
			selenium.selectWindow("");
			strFuncResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}
	
	/***********************************************************************
	'Description	:Create a status type ST selecting a role R1 both under �Roles with view rights� and �Roles with Update rights� sections, add ST to resource RS at the resource level and verify that user with role R1 and with �Update Status� right on resource RS CAN view and update the status of ST from following screens:

		a. Region view
		b. Map screen (from resource pop window)
		c. Custom view
		d. View Resource Detail
		e. Event detail 
	'Precondition	:Preconditions:
					
			1.Provide user A with the following rights:
			a.Setup Status Types.
			b.Setup Resource Types.
			c.Setup Resources.
			d.Edit Resources Only.
			e.Maintain Events.
			f.Maintain Event Templates.
			g.Configure region views.
			h.View Custom View
			
			2.Resource 'RS1' is created with address under resource Type 'RT1' associating 'ST1' at the 'RS1' level.
			
			3.Role 'R1' is associated with User U1.
			
			4.View 'V1' is created selecting 'RS1' and 'ST1'.
			
			5.Event template 'ET1' is created selecting 'RT1' and 'ST1'.
			
			6.Event 'EV' is created under the template 'ET1' selecting 'RT1'
			
			7.User A is having �Update Status� right on resource 'RS1'. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:04-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                               <Name>
	************************************************************************/

	
	@Test
	public void testBQS44253() throws Exception {
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login

		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		ViewMap objMap = new ViewMap();
		EventSetup objEve = new EventSetup();
		Preferences objPref = new Preferences();
		EventList objELs = new EventList();
		try {
			gstrTCID = "44253";
			gstrTO = "Create a status type ST selecting a role R1 both"
					+ " under �Roles with view rights� and �Roles with "
					+ "Update rights� sections, add ST to resource RS at"
					+ " the resource level and verify that user with role"
					+ " R1 and with �Update Status� right on resource RS"
					+ " CAN view and update the status of ST from following screens:"
					+ "a. Region view "
					+ "b. Map screen (from resource pop window) "
					+ "c. Custom view " + "d. View Resource Detail "
					+ "e. Event detail";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("FirefoxTestData", 2, 2,
					strFILE_PATH);
			String strIconSrc = rdExcel.readInfoExcel("FirefoxTestData", 2, 3,
					strFILE_PATH);

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strComment1 = "st1";

			String strNSTValue = "Number";
			String strNumStatType = "A11_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strStatType = "AutoST_" + strTimeText;
			String strMSTValue = "Multi";
			String strMultiStatType = "A1_" + strTimeText;

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatTypeColor = "Black";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strRoleValue = "";

			String strSTvalue[] = new String[2];
			String strMSTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";

			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String[] strRoleStatType = { strNumStatType, strMultiStatType };
			String strStatusValue[] = new String[2];

			String strSection = "AB_" + strTimeText;

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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNumStatType, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNumStatType);
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strStatType, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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

				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selAndDeselSTInEditResLevelST(seleniumPrecondition,
						strSTvalue[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.savAndVerifyEditRSLevelPage(seleniumPrecondition);

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
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

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
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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

				strFuncResult = objCreateUsers.savVrfyUser(seleniumPrecondition,
						strUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(seleniumPrecondition));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				// navigate to Event Template
				strFuncResult = objEve.createEventTemplate(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				// fill the required fields in Create Event Template and save
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strSTvalue, true, false, false);

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


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strMultiStatType, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strRoleVal = { strRoleValue };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(selenium,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strMultiStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strMSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMultiStatType, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						strMultiStatType, strStatusName1);
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
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						strMultiStatType, strStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						strMultiStatType, strStatusName2);
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
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selAndDeselSTInEditResLevelST(selenium,
						strMSTvalue[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);

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
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strMSTvalue[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRTValue[0], true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strRoleStatType, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
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
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[0], strMSTvalue[0], "",
						strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strMultiStatType, strStatusName1, "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strResVal, strMSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[1], strMSTvalue[0], "",
						"View Resource Detail");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection, strMultiStatType, strStatusName2, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};

				strFuncResult = objMap.verifyStatTypesInResourcePopup(selenium,
						strResource, strEventStatType, strRoleStatType, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[0], strMSTvalue[0],
						strComment1, "Regional Map View");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.verifyUpdSTValandComments(selenium,
						strResource, strStatusName1, strComment1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEve.editEventTemplate(selenium, strEveTemp);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEve.selectAndDeselectST(selenium,
						strMSTvalue, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEve.savAndVerifyEventtemplate(selenium,
						strEveTemp);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEve.createNewEvent(selenium, strEveTemp,
						strResource, strEveName, "AutoEvent", true);
				log4j.info(strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objELs.clickEventBanner(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[1], strMSTvalue[0], "",
						"Event Status");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strMultiStatType, strStatusName2, "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPref.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArRes = { strResource };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPref.createCustomViewNewWitTablOrMapOption(
						selenium, strArRes, strResrctTypName, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[0], strMSTvalue[0], "",
						"Custom View - Table");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPref.verifyUpdateSTValInCustomView(selenium,
						strMultiStatType, strStatusName1, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};

				strFuncResult = objMap.verifyStatTypesInResourcePopup_ShowMap(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[1], strMSTvalue[0],
						strComment1, "Custom View - Map");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.verifyUpdSTValandComments(selenium,
						strResource, strStatusName2, strComment1);
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
			gstrTCID = "44253";
			gstrTO = "Create a status type ST selecting a role R1 both under �Roles with view rights� and �Roles with Update rights� sections, add ST to resource RS at the resource level and verify that user with role R1 and with �Update Status� right on resource RS CAN view and update the status of ST from following screens:"
					+ "a. Region view "
					+ "b. Map screen (from resource pop window) "
					+ "c. Custom view "
					+ "d. View Resource Detail "
					+ "e. Event detail";
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

	/***************************************************************
	'Description		:Create status type ST selecting a role R1 under �Roles with view rights�, associate ST 
	                     with resource RS at resource TYPE level and verify that user with role R1 and with 
	                     only 'View Resource' right on RS CAN view ST on following screens:
						 a. Region view
						 b. Map (status type dropdown and resource pop up window)
						 c. View Resource Detail
						 d. Custom view
						 e. Event detail
						 f. Mobile view
						 g. Edit My Status Change preferences
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :9/3/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                     Modified By
	'Date					                            Name
	***************************************************************/

	@Test
	public void testBQS99519() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "99519"; // Test Case Id
			gstrTO = " Create status type ST selecting a role R1 under �Roles with view rights�, associate ST "
					+ "with resource RS at resource TYPE level and verify that user with role R1 and "
					+ "with only 'View Resource' right on RS CAN view ST on following screens:"
					+ "a. Region view"
					+ "b.Map (status type dropdown and resource pop up window)"
					+ "c.View Resource Detail"
					+ "d.Custom view"
					+ "e.Event detail"
					+ "f.Mobile view"
					+ "g.Edit My Status Change preferences";// Test Objective

			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_Multi" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusName1 = "Sta" + strTimeText;

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_1" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRolesName1 = "AutoRol_1" + strTimeText;
			String strRoleValue[] = new String[2];

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";


			String strTempName = "Autotemp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// Section
			String strSection = "AB_1" + strTimeText;
			String strArStatType[] = { statTypeName2 };

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Precondition: 1.Provide user A with the following
			 * rights: a.Setup Status Types. b.Setup Resource Types. c.Setup
			 * Resources. d.Edit Resources Only. e.Maintain Events. f.Maintain
			 * Event Templates. g.Configure region views. h.View Custom View
			 * 
			 * 2.Resource 'RS1' is created by proving address under resource
			 * Type 'RT1' associating 'ST1' at 'RT1' level. 3.Role 'R1' is
			 * associated with User U1. 4.View 'V1' is created selecting 'RS1'
			 * and 'ST1'. 5.Event template 'ET1' is created selecting 'RT1' and
			 * 'ST1'. 6.Event 'EV' is created under the template 'ET1' selecting
			 * 'RT1' 7.User A is not having any resource rights on resource
			 * 'RS1'.
			 */
			// 269227

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {

				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				String[] strRSValue = { strRSValues[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
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
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName1, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER
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
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type,and Click on 'Create New Status Types' button Select 'Multi'
			 * from the 'Select Type' dropdown list and then click on 'Next'. .
			 * Expected Result:'Create Multi Status Type' screen is displayed .
			 */
			// 269228
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action: Create a status type 'ST' fill all the mandatory
			 * data and select a role 'R1' only under 'Roles with view rights'
			 * and not role 'R1' under 'Roles with update rights' sections, then
			 * click on 'Save' Expected Result:Status type 'ST' is displayed in
			 * 'Status Type List' screen.
			 */
			// 269240
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
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
				String strStatusTypeValue = "Multi";
				String strStatTypeColor = "Black";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName2, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statTypeName2, "Multi");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue[0], "true" } };
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource type, and click on
			 * 'Edit' link associated with 'RT1' and select 'ST' under 'Status
			 * Types' section and click on 'Save' Expected Result:'RT1' is
			 * diaplayed in 'Resource Type List' screen
			 */
			// 269241
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
			 * STEP : Action:Navigate to Setup >> Views, click on 'Edit' link
			 * associated with view 'V1',select the status type 'ST' and
			 * resourse type (master check box) 'RT1' then click on 'Save'.
			 * Expected Result:View 'V1' is diaplayed in 'Region Views List'
			 * screen.
			 */
			// 269406

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
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' Screen,
			 * create a section 'Sec' then click on 'Uncategorized' section,
			 * Drag and drop the status type 'ST' to section 'Sec' and click on
			 * 'Save' Expected Result:'Region Views List' screen is displayed.
			 */
			// 269405
			// Section creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and login as user A and navigate to View >>
			 * V1 Expected Result:Status type 'ST' is displayed on view 'V1'
			 * screen.
			 */
			// 269407
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
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
			 * STEP : Action:Click on Resource 'RS1' Expected Result:Status type
			 * 'ST' is displayed under the section 'Sec' in 'View Resource
			 * Detail' screen.
			 */
			// 269645
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statTypeName2 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> Map, select resource 'RS1'
			 * under 'Find Resource' dropdown Expected Result:Status type 'ST'
			 * is displayed on 'resource pop up window' screen
			 */
			// 269654
			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName2 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Status Type (to color icons)' dropdown
			 * list. Expected Result:Status Type 'ST' is displayed in the
			 * dropdown list.
			 */
			// 269656
			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("css=option[value=\""
							+ strStatusTypeValues[1] + "\"]"));
					log4j.info("Status Type '" + statTypeName2
							+ "' is displayed in the dropdown list.");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type '" + statTypeName2
							+ "' is NOT displayed in the dropdown list.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View,add
			 * resources 'RS1' to custom view and then click on 'Options'.
			 * Expected Result:Status type 'ST' is available under respective
			 * section.
			 */
			// 269657
			// Adding custom view

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Add 'ST' to the custom view and click on 'Save'
			 * Expected Result:Status type 'ST' is displayed in 'Custom View -
			 * Table' screen.
			 */
			// 269659
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName2 };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to the Map format of custom view,Select
			 * resource 'RS1' in the 'Find Resource 'dropdown' list. Expected
			 * Result:Status type 'ST' is displayed in the 'Custom View - Map'
			 * screen.
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName2 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' ('RT1' is
			 * already been selected in the earlier steps) select 'ST' and click
			 * on 'Save' Expected Result:'Event Template List' screen is
			 * displayed with the event template 'ET1'
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management, create an
			 * event 'EV' under 'ET1' selecting 'RS1' and click on 'Save' now
			 * click on the event banner 'EV' which is displayed at the top.
			 * Expected Result:'Event Template List' screen is displayed with
			 * the event template 'ET1'
			 */
			// 269414
			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFldsEndNever(
						selenium, strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Partial data
			String strTestData[] = new String[10];
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strInitPwd;
				strTestData[3] = statTypeName1;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 16th step";
				strTestData[6] = strResource + "/" + strResource;
				strTestData[7] = strSection;
				strTestData[8] = strEveName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "99519"; // Test Case Id
			gstrTO = " Create status type ST selecting a role R1 under �Roles with view rights�, associate ST "
					+ "with resource RS at resource TYPE level and verify that user with role R1 and "
					+ "with only 'View Resource' right on RS CAN view ST on following screens:"
					+ "a. Region view"
					+ "b.Map (status type dropdown and resource pop up window)"
					+ "c.View Resource Detail"
					+ "d.Custom view"
					+ "e.Event detail"
					+ "f.Mobile view"
					+ "g.Edit My Status Change preferences";// Test Objective
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
	'Description		: Create status type ST without selecting any roles (view/update), add ST to resource 
	                     RS at resource level and verify that ST is NOT displayed for non system admin user
	                      on following screens:
							a. Region view screen
							b. Map (in the status type dropdown and in resource pop up window)
							c. View Resource Detail
							d. Custom view
							e. Event details
							f. Mobile view
							g. Edit My Status Change Preferences
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :9/3/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                     Modified By
	'Date					                            Name
	***************************************************************/

	@Test
	public void testBQS99605() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "99605"; // Test Case Id
			gstrTO = "  Create status type ST without selecting any roles (view/update), add ST to resource RS "
					+ "at resource level and verify that ST is NOT displayed for non system admin user on "
					+ "following screens:"
					+ "a. Region view screen"
					+ "b. Map (in the status type dropdown and in resource pop up window)"
					+ "c. View Resource Detail"
					+ "d. Custom view"
					+ "e. Event details"
					+ "f. Mobile view"
					+ "g. Edit My Status Change Preferences";// Test Objective

			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_Multi" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusName1 = "Sta" + strTimeText;

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_1" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRolesName1 = "AutoRol_1" + strTimeText;
			String strRoleValue[] = new String[1];

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";


			String strTempName = "Autotemp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// Section
			String strSection = "AB_1" + strTimeText;
			String strArStatType[] = { statTypeName1, statTypeName2 };

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Precondition: 1.Provide user A with the following
			 * rights: a.Setup Status Types. b.Setup Resource Types. c.Setup
			 * Resources. d.Edit Resources Only. e.Setup Roles. f.Maintain
			 * Events. g.Maintain Event Templates. i.Configure region views.
			 * j.View Custom View
			 * 
			 * 2.Status type 'ST1' is created selecting a role 'Rol' to view and
			 * update. 3.Resource 'RS1' is created providing address under
			 * resource Type 'RT1' associating 'ST1' at 'RS1' level. 4.View 'V1'
			 * is created selecting 'ST1' and 'RS1'. 5.Event template 'ET1' is
			 * created selecting 'RT1' and 'ST1'. 6.Event 'EV' is created under
			 * the template 'ET1' selecting 'RT1'.
			 */
			// 269227

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {

				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName1, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
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
				String[][] strRoleUpdateValue = { { strRoleValue[0], "true" } };
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				String[] strRSValue = { strRSValues[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type,and Click on 'Create New Status Types' button Select 'Multi'
			 * from the 'Select Type' dropdown list and then click on 'Next'.
			 * Expected Result:'Create Multi Status Type' screen is displayed
			 */
			// 269228
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action: Create a status type 'ST' fill all the mandatory
			 * data and DO NOT select any role under 'Roles with view rights'
			 * and 'Roles with update rights' sections, then click on 'Save'
			 * Expected Result:Status type 'ST' is displayed in 'Status Type
			 * List' screen.
			 */
			// 269240
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
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
				String strStatusTypeValue = "Multi";
				String strStatTypeColor = "Black";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName2, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources, and click on 'Edit
			 * Status types' link associated with 'RS1' and select 'ST' under
			 * 'Additional Status Types' section and click on 'Save' Expected
			 * Result:'RS1' is displayed in 'Resource List' screen
			 */
			// 269241
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action: Navigate to Setup >> Views, click on 'Edit' link
			 * associated with view 'V1',select the status type 'ST' and
			 * resourse type (master check box) 'RT1' then click on 'Save'.
			 * Expected Result:View 'V1' is diaplayed in 'Region Views List'
			 * screen.
			 */
			// 269406

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
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' Screen,
			 * create a section 'Sec' then click on 'Uncategorized' section,
			 * Drag and drop the status type 'ST' and 'ST1' to section 'Sec' and
			 * click on 'Save' Expected Result:'Region Views List' screen is
			 * displayed.
			 */
			// 269405
			// Section creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and login as user A and navigate to View >>
			 * V1 Expected Result: Status type 'ST' is not displayed on view
			 * 'V1' screen.
			 */
			// 269407
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource 'RS1' Expected Result:Status type
			 * 'ST1' is displayed but not ST under the section 'Sec' in 'View
			 * Resource Detail' screen
			 */
			// 269645
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> Map, select resource 'RS1'
			 * under 'Find Resource' dropdown Expected Result: Status type 'ST'
			 * is not displayed on 'resource pop up window' screen
			 */
			// 269654
			try {
				assertEquals("The Status Type " + statTypeName2
						+ " is NOT displayed on "
						+ "the view resource detail screen. ", strFuncResult);
				String[] strRoleStatType = { statTypeName2 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Status Type (to color icons)' dropdown
			 * list. Expected Result:Status Type 'ST' is not displayed in the
			 * dropdown list.
			 */
			// 269656
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium.isElementPresent("css=option[value=\""
							+ strStatusTypeValues[1] + "\"]"));
					log4j.info("Status Type '" + statTypeName2
							+ "' is NOT  displayed in the dropdown list.");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type '" + statTypeName2
							+ "' is displayed in the dropdown list.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View,add
			 * resources 'RS1' to custom view and then click on 'Options'.
			 * Expected Result:Status type 'ST' is not available and 'ST1' is
			 * available.
			 */
			// 269657

			// Adding custom view

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInEditCustViewOptions(selenium,
								statTypeName1);
				try {
					assertFalse(selenium.isElementPresent("//b[text()='"
							+ statTypeName2 + "']"));
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type "
							+ statTypeName2
							+ " is displayed in Edit Custom View Options (Columns)";
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is displayed in Edit Custom View Options (Columns)");
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action: Add 'ST1' to the custom view and click on 'Save'
			 * Expected Result:Status type 'ST1' is displayed in 'Custom View -
			 * Table' screen.
			 */
			// 269659
			try {
				assertEquals("", strFuncResult);
				String[][] strSTValue = { { strStatusTypeValues[0], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
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
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to the Map format of custom view,Select
			 * resource 'RS1' in the 'Find Resource 'dropdown' list. Expected
			 * Result:Only Status type 'ST1' is displayed and Status type 'ST'
			 * is not displayed in 'Custom View - Map' screen.
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName1 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName2 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' ('RT1' is
			 * already been selected in the earlier steps) select 'ST' and click
			 * on 'Save' Expected Result:'Event Template List' screen is
			 * displayed with the event template 'ET1'
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management, create an
			 * event 'EV' under 'ET1' selecting 'RS1' and click on 'Save' now
			 * click on the event banner 'EV' which is displayed at the top.
			 * Expected Result:Status Type 'ST' is not displayed in the 'Event
			 * Status' screen.
			 */
			// 269414
			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFldsEndNever(
						selenium, strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/"
									+ "th/a[text()='" + statTypeName2 + "']"));
					log4j
							.info("Status Type '"
									+ statTypeName2
									+ "' is not displayed in the 'Event Status' screen.");
				} catch (AssertionError Ae) {
					log4j.info("Status Type '" + statTypeName2
							+ "' is displayed in the 'Event Status' screen.");
					strFuncResult = "Status Type '" + statTypeName2
							+ "' is displayed in the 'Event Status' screen.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Partial data
			String strTestData[] = new String[10];
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strInitPwd;
				strTestData[3] = statTypeName1;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 16th step";
				strTestData[6] = strResource + "/" + strResource;
				strTestData[7] = strSection;
				strTestData[8] = strEveName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "99605"; // Test Case Id
			gstrTO = "  Create status type ST without selecting any roles (view/update), add ST to resource RS "
					+ "at resource level and verify that ST is NOT displayed for non system admin user on "
					+ "following screens:"
					+ "a. Region view screen"
					+ "b. Map (in the status type dropdown and in resource pop up window)"
					+ "c. View Resource Detail"
					+ "d. Custom view"
					+ "e. Event details"
					+ "f. Mobile view"
					+ "g. Edit My Status Change Preferences";// Test Objective
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
	'Description		:Create status type ST without selecting any roles (view/update), associate ST with 
	                     resource RS at resource TYPE level and verify that ST is NOT displayed for non
	                     system admin user on following screens:
							a. Region view
							b. Map (in the status type dropdown and in resource pop up window)
							c. View Resource Detail
							d. Custom view
							e. Event details
							f. Mobile view
							g. Edit My Status Change Preferences
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :9/3/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                     Modified By
	'Date					                            Name
	***************************************************************/

	@Test
	public void testBQS49691() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "49691"; // Test Case Id
			gstrTO = "Create status type ST without selecting any roles (view/update), associate ST with resource RS"
					+ " at resource TYPE level and verify that ST is NOT displayed for non system admin user on following screens:"
					+ "a. Region view"
					+ "b. Map (in the status type dropdown and in resource pop up window)"
					+ "c. View Resource Detail"
					+ "d. Custom view"
					+ "e. Event details"
					+ "f. Mobile view"
					+ "g. Edit My Status Change Preferences";// Test Objective

			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_Multi" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusName1 = "Sta" + strTimeText;

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_1" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRolesName1 = "AutoRol_1" + strTimeText;
			String strRoleValue[] = new String[1];

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			String strTempName = "Autotemp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// Section
			String strSection = "AB_1" + strTimeText;
			String strArStatType[] = { statTypeName1, statTypeName2 };

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Precondition: 1.Provide user A with the following
			 * rights: a.Setup Status Types. b.Setup Resource Types. c.Setup
			 * Resources. d.Edit Resources Only. e.View custom views. f.Maintain
			 * Events. g.Maintain Event Templates. i.Configure region views.
			 * 2.Status type 'ST1' is created selecting a role 'Rol' to view and
			 * update. 3.Resource 'RS1' is created providing address under
			 * resource Type 'RT1' associating 'ST1' at 'RT1' level. 4.View 'V1'
			 * is created selecting 'ST1' and 'RS1'. 5.Event template 'ET1' is
			 * created selecting 'RT1' and 'ST1'. 6.Event 'EV' is created under
			 * the template 'ET1' selecting 'RT1'. 7.User A is associated with
			 * role 'Rol'
			 */
			// 269227

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {

				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName1, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
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
				String[][] strRoleUpdateValue = { { strRoleValue[0], "true" } };
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				String[] strRSValue = { strRSValues[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type,and Click on 'Create New Status Types' button Select 'Multi'
			 * from the 'Select Type' dropdown list and then click on 'Next'.
			 * Expected Result:'Create Multi Status Type' screen is displayed
			 */
			// 269228
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action: Create a status type 'ST' fill all the mandatory
			 * data and DO NOT select any role under 'Roles with view rights'
			 * and 'Roles with update rights' sections, then click on 'Save'
			 * Expected Result:Status type 'ST' is displayed in 'Status Type
			 * List' screen.
			 */
			// 269240
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
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
				String strStatusTypeValue = "Multi";
				String strStatTypeColor = "Black";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName2, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources types, and click on
			 * 'Edit' link associated with 'RT1' and select 'ST' under 'Status
			 * Types' section and click on 'Save' Expected Result:'RT1' is
			 * diaplayed in 'Resource Type List' screen
			 */
			// 269241
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
			 * STEP : Action: Navigate to Setup >> Views, click on 'Edit' link
			 * associated with view 'V1',select the status type 'ST' and
			 * resourse type (master check box) 'RT1' then click on 'Save'.
			 * Expected Result:View 'V1' is diaplayed in 'Region Views List'
			 * screen.
			 */
			// 269406

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
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' Screen,
			 * create a section 'Sec' then click on 'Uncategorized' section,
			 * Drag and drop the status type 'ST' and 'ST1' to section 'Sec' and
			 * click on 'Save' Expected Result:'Region Views List' screen is
			 * displayed.
			 */
			// 269405
			// Section creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and login as user A and navigate to View >>
			 * V1 Expected Result: Status type 'ST' is not displayed on view
			 * 'V1' screen.
			 */
			// 269407
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource 'RS1' Expected Result:Status type
			 * 'ST1' is displayed but not ST under the section 'Sec' in 'View
			 * Resource Detail' screen
			 */
			// 269645
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statTypeName1, statTypeName2 };
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> Map, select resource 'RS1'
			 * under 'Find Resource' dropdown Expected Result: Status type 'ST'
			 * is not displayed on 'resource pop up window' screen
			 */
			// 269654
			try {
				assertEquals("The Status Type " + statTypeName2
						+ " is NOT displayed on "
						+ "the view resource detail screen. ", strFuncResult);
				String[] strRoleStatType = { statTypeName2 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Status Type (to color icons)' dropdown
			 * list. Expected Result:Status Type 'ST' is not displayed in the
			 * dropdown list.
			 */
			// 269656
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium.isElementPresent("css=option[value=\""
							+ strStatusTypeValues[1] + "\"]"));
					log4j.info("Status Type '" + statTypeName2
							+ "' is NOT  displayed in the dropdown list.");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type '" + statTypeName2
							+ "' is displayed in the dropdown list.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View,add
			 * resources 'RS1' to custom view and then click on 'Options'.
			 * Expected Result:Status type 'ST' is not available and 'ST1' is
			 * available.
			 */
			// 269657

			// Adding custom view

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInEditCustViewOptions(selenium,
								statTypeName1);
				try {
					assertFalse(selenium.isElementPresent("//b[text()='"
							+ statTypeName2 + "']"));
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type "
							+ statTypeName2
							+ " is displayed in Edit Custom View Options (Columns)";
					log4j
							.info("Status Type "
									+ statTypeName2
									+ " is displayed in Edit Custom View Options (Columns)");
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action: Add 'ST1' to the custom view and click on 'Save'
			 * Expected Result:Status type 'ST1' is displayed in 'Custom View -
			 * Table' screen.
			 */
			// 269659
			try {
				assertEquals("", strFuncResult);
				String[][] strSTValue = { { strStatusTypeValues[0], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to the Map format of custom view,Select
			 * resource 'RS1' in the 'Find Resource 'dropdown' list. Expected
			 * Result:Only Status type 'ST1' is displayed and Status type 'ST'
			 * is not displayed in 'Custom View - Map' screen.
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName1 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRoleStatType = { statTypeName2 };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' ('RT1' is
			 * already been selected in the earlier steps) select 'ST' and click
			 * on 'Save' Expected Result:'Event Template List' screen is
			 * displayed with the event template 'ET1'
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management, create an
			 * event 'EV' under 'ET1' selecting 'RS1' and click on 'Save' now
			 * click on the event banner 'EV' which is displayed at the top.
			 * Expected Result:Status Type 'ST' is not displayed in the 'Event
			 * Status' screen.
			 */
			// 269414
			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName1 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/"
									+ "th/a[text()='" + statTypeName2 + "']"));
					log4j
							.info("Status Type '"
									+ statTypeName2
									+ "' is not displayed in the 'Event Status' screen.");
				} catch (AssertionError Ae) {
					log4j.info("Status Type '" + statTypeName2
							+ "' is displayed in the 'Event Status' screen.");
					strFuncResult = "Status Type '" + statTypeName2
							+ "' is displayed in the 'Event Status' screen.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Partial data
			String strTestData[] = new String[10];
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strInitPwd;
				strTestData[3] = statTypeName1;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 16th step";
				strTestData[6] = strResource + "/" + strResource;
				strTestData[7] = strSection;
				strTestData[8] = strEveName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "49691"; // Test Case Id
			gstrTO = "Create status type ST without selecting any roles (view/update), associate ST with resource RS"
					+ " at resource TYPE level and verify that ST is NOT displayed for non system admin user on following screens:"
					+ "a. Region view"
					+ "b. Map (in the status type dropdown and in resource pop up window)"
					+ "c. View Resource Detail"
					+ "d. Custom view"
					+ "e. Event details"
					+ "f. Mobile view"
					+ "g. Edit My Status Change Preferences";// Test Objective
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
	'Description		: Create status type ST selecting a role R1 under �Roles with view rights� section, 
	                      associate ST with resource RS at resource TYPE level and verify that a user with
	                      role R1 and �Update Status� right on RS CAN only view ST but CANNOT update the
	                      status of ST from following screens:
							a. Region view
							b. Map screen (from resource pop up window)
							c. Custom view
							d. View Resource Detail
							e. Event detail
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :9/3/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				                     Modified By
	'Date					                            Name
	***************************************************************/

	@Test
	public void testBQS100650() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "100650"; // Test Case Id
			gstrTO = " Create status type ST selecting a role R1 under �Roles with view rights� section, associate"
					+ " ST with resource RS at resource TYPE level and verify that a user with role R1 and �Update"
					+ " Status� right on RS CAN only view ST but CANNOT update the status of ST from following screens:"
					+ "a. Region view"
					+ "b. Map screen (from resource pop up window)"
					+ "c. Custom view"
					+ "d. View Resource Detail"
					+ "e. Event detail";// Test Objective

			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_Multi" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusName1 = "Sta" + strTimeText;

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_1" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRolesName1 = "AutoRol_1" + strTimeText;
			String strRoleValue[] = new String[1];

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			String strTempName = "Autotemp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// Section
			String strSection = "AB_1" + strTimeText;
			String strArStatType[] = { statTypeName1, statTypeName2 };

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Precondition: 1.Provide user A with the following
			 * rights: a.Setup Status Types. b.Setup Resource Types. c.Setup
			 * Resources. d.Edit Resources Only. e.Maintain Events. f.Maintain
			 * Event Templates. g.Configure region views. h.View Custom View
			 * 
			 * 2.Resource 'RS1' is created providing address under resource Type
			 * 'RT1' associating 'ST1' at 'RT1' level. 3.Role 'R1' is associated
			 * with User U1. 4.View 'V1' is created selecting 'RS1' and 'ST1'.
			 * 5.Event template 'ET1' is created selecting 'RT1' and 'ST1'.
			 * 6.Event 'EV' is created under the template 'ET1' selecting 'RT1'
			 * 7.User A is having �Update Status� right on resource 'RS1'.
			 */
			// 269227

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName1, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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
				String[] strRSValue = { strRSValues[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValues[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type,and Click on 'Create New Status Types' button Select 'Multi'
			 * from the 'Select Type' dropdown list and then click on 'Next'.
			 * Expected Result:'Create Multi Status Type' screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a status type 'ST' by filling all the
			 * mandatory data and select a role 'R1' only under 'Roles with view
			 * rights', then click on 'Save' ' Expected Result:Status type 'ST'
			 * is displayed in 'Status Type List' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
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
				String strStatusTypeValue = "Multi";
				String strStatTypeColor = "Black";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName2, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources types, and click on
			 * 'Edit' link associated with 'RT1' and select 'ST' under 'Status
			 * Types' section and click on 'Save' Expected Result:'RT1' is
			 * diaplayed in 'Resource Type List' screen
			 */
			// 269241
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
			 * STEP : Action: Navigate to Setup >> Views, click on 'Edit' link
			 * associated with view 'V1',select the status type 'ST' and
			 * resourse type (master check box) 'RT1' then click on 'Save'.
			 * Expected Result:View 'V1' is diaplayed in 'Region Views List'
			 * screen.
			 */
			// 269406

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
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' Screen,
			 * create a section 'Sec' then click on 'Uncategorized' section,
			 * Drag and drop the status type 'ST' and 'ST1' to section 'Sec' and
			 * click on 'Save' Expected Result:'Region Views List' screen is
			 * displayed.
			 */
			// 269405
			// Section creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and login as User A and Navigate to View >>
			 * V1,and hover the mouse on the status cell of 'ST Expected
			 * Result:Hand cursor is not displayed (Status cell is not hyper
			 * linked).
			 */
			// 269407
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed (Status cell is not hyper linked).");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource 'RS1' Expected Result:'You are
			 * not authorized to update any statuses for this resource' message
			 * is displayed and status type 'ST' is not visible
			 */
			// 269645

			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j.info("The Status Type " + statTypeName2
							+ " is NOT displayed on the "
							+ "view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statTypeName2
							+ " is displayed on"
							+ " the view resource detail screen. ");
					strFuncResult = "The Status Type " + statTypeName2
							+ " is displayed on "
							+ "the view resource detail screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource 'RS1' and hover the mouse on the
			 * status cell 'ST'. Expected Result:Hand cursor is not displayed
			 * and User A is not able to update the status type 'ST' in 'View
			 * Resource Detail' screen.
			 */
			// 269654
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed and User is not able to update the status type "
									+ statTypeName2
									+ "in 'View Resource Detail' screen.");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> Map, select resource 'RS1'
			 * under 'Find Resource' dropdown and click on 'Update Status link'
			 * Expected Result:'You are not authorized to update any statuses
			 * for this resource' message is displayed in 'Status Update'
			 * screen.
			 */
			// 269656
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
				selenium.click("link=Update Status");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View,add
			 * resources 'RS1' and the status type 'ST' to the custom level and
			 * click on 'Save'. Expected Result:'Custom View - Table' screen is
			 * displayed with the status type 'ST'
			 */
			// 269657

			// Adding custom view
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName2 };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on key icon associated with 'RS1' Expected
			 * Result:'You are not authorized to update any statuses for this
			 * resource' message is displayed and status type 'ST' is not
			 * visible
			 */
			// 269659
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j.info("The Status Type " + statTypeName2
							+ " is displayed on the "
							+ "Update status screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statTypeName2
							+ " is NOT displayed on"
							+ " the Update status screen. ");
					strFuncResult = "The Status Type " + statTypeName2
							+ " is NOT displayed on "
							+ "the Update status screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> Custom view,and hover the mouse
			 * on the status cell of 'ST' Expected Result:Hand cursor is not
			 * displayed.
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed (Status cell is not hyper linked).");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to the Map format of custom view,Select
			 * resource 'RS1' in the 'Find Resource 'dropdown' list, Click on
			 * 'Update Status' link. Expected Result:'You are not authorized to
			 * update any statuses for this resource' message is displayed and
			 * status type 'ST' is not visible
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
				try {
					assertFalse(selenium
							.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"
									+ statTypeName2 + "')]"));
					log4j.info("Status Type " + statTypeName2
							+ "is NOT Visible");

				} catch (AssertionError ae) {
					log4j.info("Status Type " + statTypeName2 + "is Visible");
					strFuncResult = "Status Type " + statTypeName2
							+ "is Visible";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' ('RT1' is
			 * already been selected in the earlier steps) select 'ST' and click
			 * on 'Save' Expected Result:'Event Template List' screen is
			 * displayed with the event template 'ET1'
			 */
			// 269414

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management, create an
			 * event 'EV' under 'ET1' selecting 'RS1' and click on 'Save' now
			 * click on the event banner 'EV' which is displayed at the top.
			 * Expected Result:Status Type 'ST' is displayed in the 'Event
			 * Status' screen.
			 */
			// 269414
			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on key icon associated with 'RS1' Expected
			 * Result:'You are not authorized to update any statuses for this
			 * resource' message is displayed and status type 'ST' is not
			 * visible
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j.info("The Status Type " + statTypeName2
							+ " is displayed on the "
							+ "Update status screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statTypeName2
							+ " is NOT displayed on"
							+ " the Update status screen. ");
					strFuncResult = "The Status Type " + statTypeName2
							+ " is NOT displayed on "
							+ "the Update status screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner 'EV' which is displayed
			 * at the top and hover the mouse on the status cell 'ST' Expected
			 * Result:Hand cursor is not displayed.
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed and User is not able to update the status type "
									+ statTypeName1 + ".");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "100650"; // Test Case Id
			gstrTO = " Create status type ST selecting a role R1 under �Roles with view rights� section, associate"
					+ " ST with resource RS at resource TYPE level and verify that a user with role R1 and �Update"
					+ " Status� right on RS CAN only view ST but CANNOT update the status of ST from following screens:"
					+ "a. Region view"
					+ "b. Map screen (from resource pop up window)"
					+ "c. Custom view"
					+ "d. View Resource Detail"
					+ "e. Event detail";// Test Objective
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
'Description    :Create status type ST selecting a role R1 under 'Roles with view rights' section, add ST 
                 to resource RS at the resource level and verify that the user with role R1 and 'Update Status'
                 right on resource RS CAN only view ST but CANNOT update the status of ST from following screens:
					a. Region view
					b. Map screen (from the resource pop up window)
					c. Custom view
					d. View Resource Detail
					e. Event detail
'Arguments		:None
'Returns		:None
'Date			:10/17/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				                 Modified By
'Date					                         Name
***************************************************************/

	@Test
	public void testBQS100652() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "100652"; // Test Case Id
			gstrTO = "Create status type ST selecting a role R1 under 'Roles with view rights' section, add ST to "
					+ "resource RS at the resource level and verify that the user with role R1 and 'Update Status' right"
					+ "on resource RS CAN only view ST but CANNOT update the status of ST from following screens:"
					+ "a. Region view"
					+ "b. Map screen (from the resource pop up window)"
					+ "c. Custom view"
					+ "d. View Resource Detail"
					+ "e. Event detail";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_Multi" + strTimeText;
			String statTypeName3 = "AutoST_num" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatusName1 = "Sta" + strTimeText;

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_1" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRolesName1 = "AutoRol_1" + strTimeText;
			String strRoleValue[] = new String[1];

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			String strTempName = "Autotemp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			// Section
			String strSection = "AB_1" + strTimeText;
			String strArStatType[] = { statTypeName1, statTypeName2 };

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";
			/*
			 * STEP : Action:Precondition: 1.Provide user A with the following
			 * rights: a.Setup Status Types. b.Setup Resource Types. c.Setup
			 * Resources. d.Edit Resources Only. e.Maintain Events. f.Maintain
			 * Event Templates. g.Configure region views. h.View Custom View
			 * 2.Resource 'RS1' is created by proving address under resource
			 * Type 'RT1' associating 'ST1' at 'RS1' level 3.Role 'R1' is
			 * associated with User U1. 4.View 'V1' is created selecting 'RS1'
			 * and 'ST1'. 5.Event template 'ET1' is created selecting 'RT1' and
			 * 'ST1'. 6.Event 'EV' is created under the template 'ET1' selecting
			 * 'RT1' 7.User A is having 'Update Status' right on resource 'RS1'.
			 * Expected Result:No Expected Result
			 */
			// 258746
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName1, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
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
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
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

			// normal
			// ST3
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName3);
				if (strStatusTypeValues[2].compareTo("") != 0) {
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
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[2]);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						seleniumPrecondition, strStatusTypeValues[0], true);
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
				String[] strRSValue = { strRSValues[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValues[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type,and Click on 'Create New Status Types' button Select 'Multi'
			 * from the 'Select Type' dropdown list and then click on 'Next'.
			 * Expected Result:'Create Multi Status Type' screen is displayed
			 */
			// 258747
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = {};
				String[][] strRoleViewValue = { { strRoleValue[0], "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a status type 'ST' by filling all the
			 * mandatory data and select a role 'R1' only under 'Roles with view
			 * rights', then click on 'Save' Expected Result:Status type 'ST' is
			 * displayed in 'Status Type List' screen.
			 */
			// 258748
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
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
				String strStatusTypeValue = "Multi";
				String strStatTypeColor = "Black";
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName2, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources, and click on 'Edit
			 * Status Types' link associated with 'RS1' and select 'ST' under
			 * 'Additional Status Types' section and click on 'Save'. Expected
			 * Result:'RT1' is diaplayed in 'Resource Type List' screen
			 */
			// 258749
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
			 * STEP : Action:Navigate to Setup >> Views, click on 'Edit' link
			 * associated with view 'V1',select the status type 'ST' and
			 * resourse type (master check box) 'RT1' then click on 'Save'.
			 * Expected Result:View 'V1' is diaplayed in 'Region Views List'
			 * screen.
			 */
			// 258750
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
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRsTypeValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
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
				blnLogin=true;
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			
			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' Screen,
			 * create a section 'Sec' then click on 'Uncategorized' section,
			 * Drag and drop the status type 'ST' and 'ST1' to section 'Sec' and
			 * click on 'Save' Expected Result:'Region Views List' screen is
			 * displayed.
			 */
			// 258751
			// Section creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and login as User A and Navigate to View >>
			 * V1,and hover the mouse on the status cell of 'ST' Expected
			 * Result:Hand cursor is not displayed (Status cell is not hyper
			 * linked).
			 */
			// 258752
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed (Status cell is not hyper linked).");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on key icon associated with 'RS1' Expected
			 * Result:'You are not authorized to update any statuses for this
			 * resource' message is displayed and status type 'ST' is not
			 * visible
			 */
			// 258753
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j.info("The Status Type " + statTypeName2
							+ " is NOT displayed on the "
							+ "view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statTypeName2
							+ " is displayed on"
							+ " the view resource detail screen. ");
					strFuncResult = "The Status Type " + statTypeName2
							+ " is displayed on "
							+ "the view resource detail screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource 'RS1' and hover the mouse on the
			 * status cell 'ST'. Expected Result:Hand cursor is not displayed
			 * and User A is not able to update the status type 'ST' in 'View
			 * Resource Detail' screen.
			 */
			// 258754
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed and User is not able to update the status type "
									+ statTypeName2
									+ "in 'View Resource Detail' screen.");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> Map, select resource 'RS1'
			 * under 'Find Resource' dropdown and click on 'Update Status link'
			 * Expected Result:'You are not authorized to update any statuses
			 * for this resource' message is displayed in 'Status Update'
			 * screen.
			 */
			// 258755
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
				selenium.click("link=Update Status");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View,add
			 * resources 'RS1' and the status type 'ST' to the custom level and
			 * click on 'Save'. Expected Result:'Custom View - Table' screen is
			 * displayed with the status type 'ST'
			 */
			// 258756
			// Adding custom view
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName2 };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrcTypName, strResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on key icon associated with 'RS1' Expected
			 * Result:'You are not authorized to update any statuses for this
			 * resource' message is displayed and status type 'ST' is not
			 * visible
			 */
			// 258757
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j.info("The Status Type " + statTypeName2
							+ " is displayed on the "
							+ "Update status screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statTypeName2
							+ " is NOT displayed on"
							+ " the Update status screen. ");
					strFuncResult = "The Status Type " + statTypeName2
							+ " is NOT displayed on "
							+ "the Update status screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Custom view,and hover the mouse
			 * on the status cell of 'ST' Expected Result:Hand cursor is not
			 * displayed.
			 */
			// 258758

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed (Status cell is not hyper linked).");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to the Map format of custom view,Select
			 * resource 'RS1' in the 'Find Resource 'dropdown' list, Click on
			 * 'Update Status' link. Expected Result:'You are not authorized to
			 * update any statuses for this resource' message is displayed and
			 * status type 'ST' is not visible
			 */
			// 269414
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
				try {
					assertFalse(selenium
							.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"
									+ statTypeName2 + "')]"));
					log4j.info("Status Type " + statTypeName2
							+ "is NOT Visible");

				} catch (AssertionError ae) {
					log4j.info("Status Type " + statTypeName2 + "is Visible");
					strFuncResult = "Status Type " + statTypeName2
							+ "is Visible";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' ('RT1' is
			 * already been selected in the earlier steps) select 'ST' and click
			 * on 'Save' Expected Result:'Event Template List' screen is
			 * displayed with the event template 'ET1'
			 */
			// 258759
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Management, create an
			 * event 'EV' under 'ET1' selecting 'RS1' and click on 'Save' now
			 * click on the event banner 'EV' which is displayed at the top.
			 * Expected Result:Status Type 'ST' is displayed in the 'Event
			 * Status' screen.
			 */
			// 258760
			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						selenium, strResource, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statTypeName2 };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrcTypName, strResource,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on key icon associated with 'RS1' Expected
			 * Result:'You are not authorized to update any statuses for this
			 * resource' message is displayed and status type 'ST' is not
			 * visible
			 */
			// 258761
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strErrorMsg = "You are not authorized to update any statuses for this resource.";
				strFuncResult = objViews.VerifyErrorMsg(selenium, strErrorMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,"
									+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j.info("The Status Type " + statTypeName2
							+ " is displayed on the "
							+ "Update status screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type " + statTypeName2
							+ " is NOT displayed on"
							+ " the Update status screen. ");
					strFuncResult = "The Status Type " + statTypeName2
							+ " is NOT displayed on "
							+ "the Update status screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the event banner 'EV' which is displayed
			 * at the top and hover the mouse on the status cell 'ST' Expected
			 * Result:Hand cursor is not displayed.
			 */
			// 258762
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.clickEventBanner(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.mouseOver("//td[@id='v_" + strRSValues[0] + "_"
						+ strStatusTypeValues[1] + "']");
				try {
					assertTrue(selenium.isElementPresent("//td[@id='v_"
							+ strRSValues[0] + "_" + strStatusTypeValues[1]
							+ "']" + "[@class='addToolTipText statusWhite']"));
					log4j
							.info("Hand cursor is not displayed and User is not able to update the status type "
									+ statTypeName1 + ".");
				} catch (AssertionError Ae) {
					log4j
							.info("Hand cursor is displayed (Status cell is not hyper linked).");
					gstrReason = strFuncResult
							+ "Hand cursor is displayed (Status cell is not hyper linked).";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "100652"; // Test Case Id
			gstrTO = "Create status type ST selecting a role R1 under 'Roles with view rights' section, add ST to "
					+ "resource RS at the resource level and verify that the user with role R1 and 'Update Status' right"
					+ "on resource RS CAN only view ST but CANNOT update the status of ST from following screens:"
					+ "a. Region view"
					+ "b. Map screen (from the resource pop up window)"
					+ "c. Custom view"
					+ "d. View Resource Detail"
					+ "e. Event detail";// Test Objective
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
	public void testBQS100426() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "BQS-100426 ";
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

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

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

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statTypeName = "ST" + strTimeText;
			String strSTVal = "";
			String strNSTValue = "Number";
			String strStatTypDefn = "Automation";

			String statrMultiTypeName = "RMST" + strTimeText;
			String strStatusTypeValues[] = new String[1];
			String strMSTValue = "Multi";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

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
			String strArStatType1[] = { statrMultiTypeName, statTypeName };

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			String strEveName1 = "Event_1" + System.currentTimeMillis();
			String strEventValue1 = "";

			String strRolesName = "Rol_1" + System.currentTimeMillis();
			String strRoleValue = "";

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

			/*
			 * 2.Resource 'RS1' is created by proving address under resource
			 * Type 'RT1' associating 'ST1' at 'RS1' level.
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
						seleniumPrecondition, strNSTValue, statTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTVal = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);

				if (strSTVal.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3.Role 'R1' is associated with User U1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
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
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='" + strSTVal
								+ "']");

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

			/*
			 * 1.Provide user A with the following rights: a.Setup Status Types.
			 * b.Setup Resource Types. c.Setup Resources. d.Edit Resources Only.
			 * e.Maintain Events. f.Maintain Event Templates. g.Configure region
			 * views. h.View Custom View
			 */

			/*
			 * 7.User A is not having any resource rights on resource 'RS1'.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_A, strInitPwd, strConfirmPwd,
						strUsrFulName_A);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer"),
								true);
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
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4.View 'V1' is created selecting 'RS1' and 'ST1'.
			 */

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
				String strSTValues[] = { strSTVal };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5.Event template 'ET1' is created selecting 'RT1' and 'ST1'.
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

				String[] strResTypeValue = { strRTVal };
				String[] strStatusTypeVal = { strSTVal };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6.Event 'EV' is created under the template 'ET1' selecting 'RT1' */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as User A and Navigate to Setup >> Status Type,and Click
			 * on 'Create New Status Types' button Select 'Multi' from the
			 * 'Select Type' dropdown list and then click on 'Next'. 'Create
			 * Multi Status Type' screen is displayed
			 */

			try {

				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Create a status type 'ST' fill all the mandatory data and
			 * select a role 'R1' only under 'Roles with view rights' and not
			 * role 'R1' under 'Roles with update rights' sections, then click
			 * on 'Save' Status type 'ST' is displayed in 'Status Type List'
			 * screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = {};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrMultiTypeName);

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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						selenium, statrMultiTypeName, strStatusName1);
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
						selenium, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Setup >> Resources, and click on 'Edit Status
			 * Types' link associated with 'RS1' and select 'ST' under
			 * 'Additional Status Types' section and click on 'Save' 'RS1' is
			 * diaplayed in 'Resource List' screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strStatusTypeValues[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Navigate to Setup >> Views, click on 'Edit' link associated
			 * with view 'V1',select the status type 'ST' and resourse type
			 * (master check box) 'RT1' then click on 'Save'. View 'V1' is
			 * diaplayed in 'Region Views List' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToEditView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRTVal, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

			/*
			 * 6 Click on 'Customize Resource Detail View' Screen, create a
			 * section 'Sec' then click on 'Uncategorized' section, Drag and
			 * drop the status type 'ST' and 'ST1' to section 'Sec' and click on
			 * 'Save' 'Region Views List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType1, strSection1, true);

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
				strSectionValue = objViews.fetchSectionID(seleniumFirefox,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Logout and login as user A and navigate to View >> V1 Status
			 * type 'ST' is displayed on view 'V1' screen.
			 */

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strArStatType2 = { statTypeName, statrMultiTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Click on Resource 'RS1' Status type 'ST' is displayed under the
			 * section 'Sec' in 'View Resource Detail'
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

				String[] strArStatType2 = { statTypeName, statrMultiTypeName };
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Navigate to View >> Map, select resource 'RS1' under 'Find
			 * Resource' dropdown Status type 'ST' is displayed on 'resource pop
			 * up window' screen
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strArStatType2 = { statrMultiTypeName };

				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType2, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 10 Click on 'Status Type (to color icons)' dropdown list. Status
			 * Type 'ST' is displayed in the dropdown list.
			 */

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statrMultiTypeName + "']"));

				log4j.info("status type " + statrMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statrMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statrMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}

			/*
			 * 11 Navigate to Preferences >> Customized View,add resources 'RS1'
			 * to custom view and then click on 'Options'. Status type 'ST' is
			 * available under respective section.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 12 Add 'ST' to the custom view and click on 'Save'. Status type
			 * 'ST' is displayed in 'Custom View - Table' screen.
			 */

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewNewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statrMultiTypeName);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strSTName = { statrMultiTypeName };
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strSTName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 13 Navigate to the Map format of custom view,Select resource
			 * 'RS1' in the 'Find Resource 'dropdown' list. Status type 'ST' is
			 * displayed in the 'Custom View - Map' screen.
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

				String[] strArStatType2 = { statrMultiTypeName };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType2,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkRTInEditEventTemplatePage(
						selenium, strRTVal, true, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName, true, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(selenium,
						strTempName, strResource, strEveName1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue1 = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName1);
				if (strEventValue1.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strArStatType2 = { statrMultiTypeName, statTypeName };
				strEventValue1 = objEventList.checkInEventBannerNew(selenium,
						strEveName1, strResrctTypName, strResource,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, strUserName_A + "/" + strInitPwd,
						statTypeName + "," + statrMultiTypeName, strViewName_1,
						"From 16th Step", strResource, strSection1,
						strEveName + "," + strEveName1, strTempName,
						strSection1, strResrctTypName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-100426";
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

	
	/*****************************************************************************************
	 * 'Description :Status type ST is associated with resource RS at the
					 resource type level. ' Verify that a user with �Update Status� right on
					 resource RS but without ' view/update rights for status type ST DOES NOT
					 receive expired status ' notifications when the status of ST expires.
	 'Precondition :1.Status type 'ST1' (Multi status type) is created by
					 associating the expiration time (say 'T1') and selecting only role 'R1'
					 under view and update right for the status type. ' 2.Status type 'ST2'
					 (Multi status type) is created by associating the shift ' time (say 'S1')
					 and selecting only role 'R1' under view and update right ' for the status
					 type. ' 3.Resource 'RS' is created by proving address under the resource
					 type ' 'RT', associating 'ST1' and 'ST2' at the 'RT' level. ' 4.User B is
					 associated with role 'R1'. ' 5.User A has subscribed to receive expired
					 status notifications via E-mail ' and pager and is not associated with
					 any role. ' 6.User A and User B has �Update Status� right on resource 'RS'.
	 'Arguments	   :None 
	 'Returns 	   :None 
	 'Date 		   :14-June-2012 
	 'Author       :QSG
	 '-----------------------------------------------------------------------------------------
	 'Modified Date 							Modified By 	
	 '14-June-2012							 <Name> 
	 ***************************************************************************************/

	@Test
	public void testBQS44223() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-44223 ";
			gstrTO = "Status type ST is associated with resource RS at the "
					+ "resource type level. Verify that a user with �Update "
					+ "Status� right on resource RS but without view/update"
					+ " rights for status type ST DOES NOT receive expired status"
					+ " notifications when the status of ST expires.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));

			int intPositionShift = 0;
			int intPositionExpire = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intTimeDiffOutPut = 0;

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String StatusTime = "";

			String strUpdTime_Shift = "";

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

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
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			String strTmText = dts.getCurrentDate("HHmm");
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;

			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strRoleValue = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			// Navigate to Role list
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeNameExpire + "']"));

					log4j.info("Status type " + statTypeNameExpire
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeNameExpire);
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
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								seleniumPrecondition, statTypeNameExpire,
								strStatusNameExpire1, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								seleniumPrecondition, statTypeNameExpire,
								strStatusNameExpire2, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameExpire, strStatusNameExpire1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameExpire, strStatusNameExpire2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeNameExpire
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeNameExpire
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameShift);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[1], true);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

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
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(seleniumPrecondition,
						statTypeNameShift, strStatusTypeValueShift);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							seleniumPrecondition, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as User B, navigate to View >> Map. Select resource 'RS'
			 * under 'Find Resource' dropdown and update status values of status
			 * types 'ST1' and 'ST2' from resource pop up window. Status type
			 * 'ST1' and 'ST2' are updated and are displayed on 'resource pop up
			 * window' screen
			 */

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

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					intTimeDiffOutPut = intTimeDiffOutPut * 1000;
					Thread.sleep(intTimeDiffOutPut);

					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, 30, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				for (int i = 0; i < 2; i++) {

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmailSubName(selenium,
							strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intPagerRes++;

					} catch (AssertionError Ae) {
						gstrReason = gstrReason+"The mail with subject " + strSubjName
								+ " is present in the inbox";
					}

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int i = 0; i < 4; i++) {
				strSubjName = "EMResource Expired Status Notification: "
						+ strResource;
				strFuncResult = objMail.verifyEmailSubName(selenium,
						strSubjName);

				try {

					assertTrue(strFuncResult.equals("The mail with subject "
							+ strSubjName + " is NOT present in the inbox"));

					intEMailRes++;

				} catch (AssertionError Ae) {
					gstrReason = gstrReason+"The mail with subject " + strSubjName
							+ " is present in the inbox";
				}

			}

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-44223";
			gstrTO = "Status type ST is associated with resource RS at the resource"
					+ " type level. Verify that a user with �Update Status� right "
					+ "on resource RS but without view/update rights for status "
					+ "type ST DOES NOT receive expired status notifications when "
					+ "the status of ST expires.";
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
				gstrReason = gstrReason + strFuncResult;
			}
		}
	}
	

	/*****************************************************************************************
	 * 'Description :Status type ST is associated with resource RS at the
					 resource type level. ' Verify that a user with �Update Status� right on
					 resource RS but without ' view/update rights for status type ST DOES NOT
					 receive expired status ' notifications when the status of ST expires.
	 'Precondition :1.Status type 'ST1' (Multi status type) is created by
					 associating the expiration time (say 'T1') and selecting only role 'R1'
					 under view and update right for the status type. ' 2.Status type 'ST2'
					 (Multi status type) is created by associating the shift ' time (say 'S1')
					 and selecting only role 'R1' under view and update right ' for the status
					 type. ' 3.Resource 'RS' is created by proving address under the resource
					 type ' 'RT', associating 'ST1' and 'ST2' at the 'RT' level. ' 4.User B is
					 associated with role 'R1'. ' 5.User A has subscribed to receive expired
					 status notifications via E-mail ' and pager and is not associated with
					 any role. ' 6.User A and User B has �Update Status� right on resource 'RS'.
	 'Arguments	   :None 
	 'Returns 	   :None 
	 'Date 		   :14-June-2012 
	 'Author       :QSG
	 '-----------------------------------------------------------------------------------------
	 'Modified Date 							Modified By 	
	 '14-June-2012							 <Name> 
	 ***************************************************************************************/

	@Test
	public void testBQS44281() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-44281 ";
			gstrTO = "Status type ST is associated with resource RS at the"
					+ " resource level. Verify that a user with �Update Status�"
					+ " right on resource RS but without view/update permissions "
					+ "for status type ST DOES NOT receive expired status notificat"
					+ "ions when the status of ST expires.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));

			int intPositionShift = 0;
			int intPositionExpire = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intTimeDiffOutPut = 0;

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String StatusTime = "";

			String strUpdTime_Shift = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strStatusTypeST = "Number";
			String statTypeNameST = "ST_" + strTimeText;

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			String strTmText = dts.getCurrentDate("HHmm");
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strRoleValue = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";
			strSTvalue[2] = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			// Navigate to Role list
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeNameExpire + "']"));

					log4j.info("Status type " + statTypeNameExpire
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, statTypeNameExpire);
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
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								seleniumPrecondition, statTypeNameExpire,
								strStatusNameExpire1, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								seleniumPrecondition, statTypeNameExpire,
								strStatusNameExpire2, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameExpire, strStatusNameExpire1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
								statTypeNameExpire, strStatusNameExpire2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeNameExpire
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeNameExpire
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameShift);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeST, statTypeNameST, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameST);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
								+ strSTvalue[2] + "']");

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
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

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
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(seleniumPrecondition,
						statTypeNameShift, strStatusTypeValueShift);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							seleniumPrecondition, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
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

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as User B, navigate to View >> Map. Select resource 'RS'
			 * under 'Find Resource' dropdown and update status values of status
			 * types 'ST1' and 'ST2' from resource pop up window. Status type
			 * 'ST1' and 'ST2' are updated and are displayed on 'resource pop up
			 * window' screen
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					intTimeDiffOutPut = intTimeDiffOutPut * 1000;
					Thread.sleep(intTimeDiffOutPut);

					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, 30, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				for (int i = 0; i < 2; i++) {

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmailSubName(selenium,
							strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						intPagerRes++;

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + "The mail with subject "
								+ strSubjName + " is present in the inbox";
					}

				}

			} catch (AssertionError Ae) {
				gstrReason =strFuncResult;
			}
			
			for (int i = 0; i < 4; i++) {

				strSubjName = "EMResource Expired Status Notification: "
						+ strResource;
				strFuncResult = objMail.verifyEmailSubName(selenium,
						strSubjName);

				try {

					assertTrue(strFuncResult.equals("The mail with subject "
							+ strSubjName + " is NOT present in the inbox"));

					intEMailRes++;

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + "The mail with subject "
							+ strSubjName + " is present in the inbox";
				}
			}

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			selenium.selectWindow("");
			gstrTCID = "BQS-44281";
			gstrTO = "Status type ST is associated with resource RS at the "
					+ "resource level. Verify that a user with �Update Status�"
					+ " right on resource RS but without view/update permissions"
					+ " for status type ST DOES NOT receive expired status notifications"
					+ " when the status of ST expires.";
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
				gstrReason = gstrReason+strFuncResult;
			}
		}
	}
	
	
	
	/***************************************************************
	'Description		:Create a status type ST1 (role-based) and ST2 (shared) without selecting any roles (view/update) and verify that ST can be viewed by any user (non system admin) in the following setup pages:<br>
	a.	Status Type List<br>
	b.	Create New /Edit Resource Type<br>
	c.	Create /Edit Role<br>
	d.	Edit Resource Level Status Types<br>
	e.	Create New/Edit/Copy View<br>
	f.	Edit Resource Detail View Sections<br>
	g.	Create/Edit Event Template<br>
	e.	Edit event <br>
	f.	While configuring the form
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/31/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS49714() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		Forms objForms = new Forms();
		try {
			gstrTCID = "49714"; // Test Case Id
			gstrTO = "Create a status type ST1 (role-based) and ST2 (shared) without"
					+ " selecting any roles (view/update) and verify that ST can be viewed"
					+ " by any user (non system admin) in the following setup pages:"
					+ "a.	Status Type List"
					+ "b.	Create New /Edit Resource Type"
					+ "c.	Create /Edit Role"
					+ "d.	Edit Resource Level Status Types"
					+ "e.	Create New/Edit/Copy View"
					+ "f.	Edit Resource Detail View Sections"
					+ "g.	Create/Edit Event Template"
					+ "e.	Edit event"
					+ "f.	While configuring the form";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[3];
			String statTypeName1 = "AutoST_" + strTimeText;
			String statTypeName2 = "AutoST_1" + strTimeText;
			String statTypeName3 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strHavBed = "No";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";

			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";

			// form
			String strFormTempTitle = "OF" + strTimeText;
			String strFormActiv = "As Certain Status Changes";
			String strFormDesc = "Automation";
			/*
			 * STEP : Action:Precondition: 1.Provide user A with the following
			 * rights: a.Setup Status Types. b.Setup Resource Types. c.Setup
			 * Resources. d.Edit Resources Only. e.Setup Roles. f.Maintain
			 * Events. g.Maintain Event Templates. i.Configure region views.
			 * j.Form - User may create and modify forms 2.Resource 'RS1' is
			 * created under resource Type 'RT1' selecting 'ST' at 'RT1' level.
			 * 3.Role 'R1' is created selecting 'ST' and 'RS1'. 4.View 'V1' is
			 * created selecting 'ST' and 'RT1'. 5.Event template 'ET1' is
			 * created selecting 'RT1' and 'ST'. 6.Event 'EV' is created under
			 * the template 'ET1' selecting 'RT1'. Expected Result:No Expected
			 * Result
			 */
			// 282733
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {

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
				String[] strRSValue = { strResVal };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition,
						strRsTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strStatusTypeval = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strInfo, false);
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
				String[] updateRightValue = {};
				String[] strViewRightValue = { strStatusTypeValues[0] };
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, false);
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

			// USER
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
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strResVal, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * STEP : Action:Login as User A and Navigate to Setup >> Status
			 * Type,and Click on 'Create New Status Types' button. Expected
			 * Result:'Select Status Type' screen is displayed.
			 */
			// 282734
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Number','Multi','Text' or 'Saturation' from
			 * the 'Select Type' dropdown list(If 'Number' is selected from
			 * 'Select Type' dropdown list) and then click on 'Next' Expected
			 * Result:'Create Number Status Type' screen is displayed
			 */
			// 282735
			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a status type 'ST1' by filling all the
			 * mandatory data and DO NOT select any role under 'Roles with view
			 * rights' and 'Roles with update rights' sections, then click on
			 * 'Save' Expected Result:Status type 'ST1' is displayed in 'Status
			 * Type List' screen.
			 */
			// 282736
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName2);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a status type 'ST2' by filling all the
			 * mandatory data, also by selecting the radio button 'Shared' and
			 * DO NOT select any role under 'Roles with view rights' and 'Roles
			 * with update rights' sections, then click on 'Save' Expected
			 * Result:Status type 'ST2' is displayed in 'Status Type List'
			 * screen.
			 */
			// 282737
			// ST2
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
				selenium.click("css=input['name=visibility'][value='SHARED']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName3);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource Types. Expected
			 * Result:'Resource Type List' screen is displayed
			 */
			// 282738
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link associated with 'RT1' resource
			 * type, and select status types 'ST1' and 'ST2' and click on
			 * 'Save'. Expected Result:Resource Type 'RT1' is displayed in
			 * 'Resource Type List' screen
			 */
			// 282739
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
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
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
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
			 * STEP : Action:Navigate to Setup >> Resource Types and Click on
			 * 'Create New Resource Type' button Expected Result:'Create New
			 * Resource Type' screen is displayed and status types 'ST1' and
			 * 'ST2' are displayed under 'Status Types' section.
			 */
			// 282740
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navToCreateNewResrcTypePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(
						selenium, statTypeName2, strStatusTypeValues[1], true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(
						selenium, statTypeName3, strStatusTypeValues[2], true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Roles and Click on 'Edit' link
			 * associated with role 'R1'. Expected Result:Status type 'ST1' and
			 * 'ST2' are displayed under following sections: a.Select the Status
			 * Types this Role may view. b.Select the Status Types this Role may
			 * update
			 */
			// 282741

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(
						selenium, strStatusTypeValues[1], true,
						strStatusTypeValues[1], true, statTypeName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(
						selenium, strStatusTypeValues[2], true,
						strStatusTypeValues[2], true, statTypeName3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource and Click on 'Edit
			 * Status Types' link associated with resource 'RS1'. Expected
			 * Result:'ST1' and 'ST2' are displayed under 'Additional Status
			 * Types' section
			 */
			// 282742

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statTypeName3, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Views. Expected Result:'Region
			 * Views List' screen is displayed
			 */
			// 282743
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New View' button. Expected
			 * Result:'Create New View' screen is displayed and status types
			 * 'ST1' and 'ST2' are displayed under 'Select Status Types'
			 * section.
			 */
			// 282744
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[1], statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[2], statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with view 'V1'
			 * Expected Result:'Edit View' screen is displayed and status types
			 * 'ST1' and 'ST2' are displayed under 'Select Status Types'
			 * section.
			 */
			// 282745
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
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[1], statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[2], statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Copy' link associated with view 'V1'
			 * Expected Result:'Edit View' screen is displayed and status types
			 * 'ST1' and 'ST2' are displayed under 'Select Status Types'
			 * section.
			 */
			// 282746
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditViewByCopyLink(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[1], statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[2], statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views and Click on'Customize
			 * Resource Detail View'. Expected Result:'Edit Resource Detail View
			 * Sections' screen is displayed.
			 */
			// 282747
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Uncategorized' section Expected
			 * Result:Status types 'ST1' and 'ST2' are displayed with other
			 * status types
			 */
			// 282748
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTEditResDetailViewSec(selenium,
						true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTEditResDetailViewSec(selenium,
						true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Create New Event Template' button. Expected Result:'Create New
			 * Event Template' screen is displayed with the status types 'ST1'
			 * and 'ST2' under 'Status Types' section.
			 */
			// 282749
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[2], true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup and Click on
			 * 'Edit' link associated with event template 'ET1' ('RT1' is
			 * already been selected in the earlier steps) select 'ST1' and
			 * 'ST2' and click on 'Save' Expected Result:'Event Template List'
			 * screen is displayed with the event tmplate 'ET1'.
			 */
			// 282750
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.slectAndDeselectSTInEditEventPage(selenium,
								strStatusTypeValues[2], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.savAndVerifyEventtemplate(
						selenium, strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Event >> Event Management,click on
			 * 'Edit' link associated with event 'EV' Expected Result:'Edit
			 * Event' screen is displayed with status types 'ST1' and 'ST2'
			 * under 'Status types' section.
			 */
			// 282751
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium,
						strStatusTypeValues[1], true, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium,
						strStatusTypeValues[2], true, statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Forms >> Configure Forms. Expected
			 * Result:'Form Configuration' screen is displayed.
			 */
			// 282752
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New Form Template' Expected
			 * Result:'Create New Form Template' screen is displayed.
			 */
			// 282753
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide form title and description and select 'As
			 * Status changes' from 'Form Activation' dropdown list and click on
			 * 'Next'. Expected Result:Status types 'ST1' and 'ST2' are
			 * displayed under 'Form Activation Status Type' section
			 */
			// 282754
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.filAllFldsInCreatNewFormAsStatusChanges(selenium,
								strFormTempTitle, strFormDesc, strFormActiv,
								false, false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statTypeName2, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statTypeName3, strStatusTypeValues[2], true);
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
			gstrTCID = "BQS-49714";
			gstrTO = "Create a status type ST1 (role-based) and ST2 (shared) without selecting any roles (view/update) "
					+ "and verify that ST can be viewed by any user (non system admin) in the following setup pages:"
					+ "a.	Status Type List"
					+ "b.	Create New /Edit Resource Type"
					+ "c.	Create /Edit Role"
					+ "d.	Edit Resource Level Status Types"
					+ "e.	Create New/Edit/Copy View"
					+ "f.	Edit Resource Detail View Sections"
					+ "g.	Create/Edit Event Template"
					+ "e.	Edit event"
					+ "f.	While configuring the form";
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

	/***************************************************************************************
	'Description	Create a status type ST selecting a role R1 under �Roles with view rights�
	'			    and �Roles with update rights� sections, associate ST with resource RS at
	'				 the resource level and verify that the user with role R1 and �Update 
	'				Status� right on resource RS receive expired status notification for
	'				 resource RS when the status of ST expires.
	'Precondition	:	Preconditions:

					1.Status type 'ST1'(Multi status type) is created by associating the expiration time (say 'T1') and selecting role 'R1' under both to view and update the status type.
					
					2.Status type 'ST2' (Multi status type) is created by associating the shift time (say 'S1') and selecting role 'R1' under both to view and update the status type.
					
					3.Resource 'RS' is created by proving address under the resource type 'RT', associating 'ST1' and 'ST2' at the 'RS' level.
					
					4.User A is associated with role 'R1'.
					
					5.User A has subscribed to receive expired status notifications via E-mail and pager.
					
					6.User A has �Update Status� right on resource 'RS'. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:15-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<date>                                   <Name>
	************************************************************************/

	@Test
	public void testBQS44275() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-44275 ";
			gstrTO = "Status type ST is associated with resource RS at the resource"
					+ " type level. Verify that a user with �Update Status� right on"
					+ " resource RS but without view/update rights for status type ST "
					+ "DOES NOT receive expired status notifications when the status"
					+ " of ST expires.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));

			String strLoginUserName = "";
			String strLoginPassword = "";

			String strRegn = "";

			String StatusTime = "";

			String strUpdTime = "";
			String strUpdTime_Shift = "";

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurDate = "";

			int intPositionShift = 0;
			int intPositionExpire = 0;
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intTimeDiffOutPut = 0;

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			// String strUserNameB="AutoUsr_B"+System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strRoleValue = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strMsgBody2Expire = "";
			String strMsgBody1Expire = "";
			String strMsgBody2ExpireAnother = "";
			String strMsgBody1ExpireAnother = "";

			String strMsgBody2Shift = "";// Pager body
			String strMsgBody1Shift = "";// Email body
			String strMsgBody2ShiftAnother = "";
			String strMsgBody1ShiftAnother = "";

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			// Admin login credential
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			strRegn = rdExcel.readData("Login", 3, 4);
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Navigate to Role list
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						seleniumPrecondition, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(seleniumPrecondition, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeNameShift);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2,
						strStatusTypeValueShift, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeNameShift, strStatusNameShift2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueShift[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
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

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						seleniumPrecondition, strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						seleniumPrecondition, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(seleniumPrecondition,
						statTypeNameShift, strStatusTypeValueShift);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							seleniumPrecondition, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(seleniumPrecondition);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					intTimeDiffOutPut = intTimeDiffOutPut * 1000;
					Thread.sleep(intTimeDiffOutPut);

					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, 30, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("//span[text()='"
						+ strStatusNameExpire1
						+ "']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;

				String strCurYear = dts.getTimeOfParticularTimeZone("CST",
						"yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time for NST: " + strUpdTime);

				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
						"HH:mm");

				strMsgBody1Expire = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1ExpireAnother = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Expire = "EMResource expired status: " + strResource
						+ ". " + statTypeNameExpire + " status expired "
						+ strCurDate + " " + strUpdTime + ".";

				strMsgBody2ExpireAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameExpire
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strAdUpdTimeShift = dts.addTimetoExisting(
						strUpdTime_Shift, 1, "HH:mm");

				strMsgBody1Shift = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime_Shift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody1ShiftAnother = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBody2Shift = "EMResource expired status: " + strResource
						+ ". " + statTypeNameShift + " status expired "
						+ strCurDate + " " + strUpdTime_Shift + ".";

				strMsgBody2ShiftAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameShift
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertTrue(strFuncResult.equals(""));
				for (int i = 0; i < 2; i++) {

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBody2Expire)
								|| strMsg.equals(strMsgBody2ExpireAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Expire
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBody2Shift)
								|| strMsg.equals(strMsgBody2ShiftAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Shift
									+ " is displayed for Pager Notification");
						} else {
							log4j.info("Pager is NOT displayed ");
							gstrReason = "Pager is NOT displayed ";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			for (int i = 0; i < 4; i++) {

				strSubjName = "EMResource Expired Status Notification: "
						+ strResource;
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);

					if (strMsg.equals(strMsgBody1Expire)
							|| strMsg.equals(strMsgBody1ExpireAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Expire
								+ " is displayed for Email Notification");
					} else if (strMsg.equals(strMsgBody1Shift)
							|| strMsg.equals(strMsgBody1ShiftAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Shift
								+ " is displayed for Email Notification");
					} else {
						log4j.info("Email is NOT displayed ");
						gstrReason = "Email is NOT displayed ";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				selenium.selectFrame("relative=up");
				selenium.selectFrame("horde_main");
				// click on Back to Inbox
				selenium.click("link=Back to Inbox");
				selenium.waitForPageToLoad("90000");
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-44275";
			gstrTO = "Status type ST is associated with resource RS at the resource type"
					+ " level. Verify that a user with �Update Status� right on resource "
					+ "RS but without view/update rights for status type ST DOES NOT"
					+ " receive expired status notifications when the status of ST expires.";
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
