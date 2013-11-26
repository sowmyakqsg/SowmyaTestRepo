package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
' Description :This class includes test cases to Inactivate and reactivate Saturation Score Status types
' Precondition:
' Functions: testBQS69199(),testBQS66234()
' Date		  :09-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/
public class InactiveSaturScoreStatType {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.InactiveSaturScoreStatType");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;	
	Selenium selenium1,selenium2,selenium3,seleniumMain,seleniumFirefox;	
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails,propAutoItDetails;
	public static Properties browserProps = new Properties();
	
	private String browser="";
	
	private String json;
	public static long sysDateTime;	
	public static long gsysDateTime;
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId,StrSessionId1,StrSessionId2;
	public static String gstrTimeOut="";
	
	@Before
	public void setUp() throws Exception {
		
		
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
					
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");		
		
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {
		         
	        
			selenium1 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.json, propEnvDetails.getProperty("urlEU"));
			
	       
		} else {
			selenium1 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));			
			selenium2 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));	
			selenium3 = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));	
			seleniumMain=new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));
			seleniumFirefox = new DefaultSelenium(propEnvDetails
					.getProperty("Server"), 4444,propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
					.getProperty("urlEU"));
		
		}		
		//start session	for Browser1
		selenium1.start();
		selenium1.windowMaximize();
		selenium1.setTimeout(gstrTimeOut);

		//start session	for Browser2
		selenium2.start();
		selenium2.windowMaximize();
		selenium2.setTimeout(gstrTimeOut);
		
		//start session	for Browser3
		selenium3.start();
		selenium3.windowMaximize();
		selenium3.setTimeout(gstrTimeOut);
		
		seleniumMain.start();
		seleniumMain.windowMaximize();
		seleniumMain.setTimeout(gstrTimeOut);
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout(gstrTimeOut);
		
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();		
		
	}

	@After
	public void tearDown() throws Exception {	
		
		try{
			selenium1.close();
			
		}catch(Exception e){
			
		}
		
		try{
			selenium2.close();
			
		}catch(Exception e){
			
		}
		
		try{
			selenium3.close();
			
		}catch(Exception e){
			
		}
		
		try{
			seleniumFirefox.close();
		}catch(Exception e){
			
		}
	
		selenium1.stop();
		selenium2.stop();
		selenium3.stop();
		seleniumMain.stop();
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
		gstrdate = dts.getCurrentDate(selenium1, "yyyy-MM-dd");
		gstrBuild = propEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}

	/***********************************************************************
	'Description	:Verify that a saturation score status type can be reactivated.
	'Precondition	: 1. Test user has created a number status type 'SST'
					2. Resource Type 'RT' is associated with 'SST' and a resource 'RS' is created under 'RT'
					3. User 'A' has update right on 'RS' and is assigned a role which has update right on 'SST'
					4. View 'V1' is created selecting 'SST' and 'RS'
					5. Status type Section 'S1' is created for status type 'SST'
					6. After setting the above preconditions, deactivate the status type 'SST' 
	'Arguments		:void
	'Returns		:void
	'Date	 		:09-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Name>                              		<Name>
	************************************************************************/
	@Ignore
	public void testBQS69234Old()throws Exception{
		try{
			gstrTCID = "69234";			
			gstrTO = "Verify that a saturation score status type can be reactivated";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("hh:mm:ss");
			
			Login objLogin = new Login();// object of class Login
			StatusTypes objStatTyp=new StatusTypes();
			ReadData objReadData = new ReadData ();
			Views objView=new Views();
			ViewMap objMap=new ViewMap();
			General objRef=new General();
			//login details
			String strUserName = objReadData.readData("Login", 27, 1);
			String strPassword = objReadData.readData("Login", 27, 2);
						
			String strViewName="AutoSstView1";
					
			String strResource="AutoRs3";
			String strStatType1="AutoSatuSt1";
									
			String strEventStatType[]={};
			String []strRoleStatType={strStatType1};
			
					
			String strRegn=objReadData.readData("Login", 3, 4);

			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
			
			int intResCnt=0;
			//login to application
			seleniumMain.open(propEnvDetails.getProperty("urlRel"));
			//Login as Region Admin
			String strFailMsg = objLogin.login(seleniumMain, strLoginUserName,
					 strLoginPassword);
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(seleniumMain, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objStatTyp.navStatusTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//deactivate the status type to reset to the pre-condtion
				strFailMsg=objStatTyp.activeStatusType(seleniumMain, strStatType1, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+" "+strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				/*
				 * Step 2: Login as user 'A' and navigate to 'View >> V1' on browser 'B1' <-> V1 page is displayed with instruction 'There are no resources to display in this view'. 
				 */
				selenium1.open(propEnvDetails.getProperty("urlRel"));
				strFailMsg = objLogin.login(selenium1, strUserName,
						strPassword);	
				
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg=objView.navToUserView(selenium1, strViewName);
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
				
						
				try {
					assertTrue(strFailMsg.equals(""));		
					try{						
						assertTrue(selenium1.isTextPresent("There are no resources to display in this view."));
						log4j.info(strViewName+" page is displayed with instruction 'There are no resources to display in this view'.");
						intResCnt++;
					}catch(AssertionError ae){
						log4j.info(strViewName+" page is NOT displayed with instruction 'There are no resources to display in this view'.");
						gstrReason=strViewName+" page is NOT displayed with instruction 'There are no resources to display in this view'.";
					}
																		
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}	
				
				/*
				 * Step 3: Login as user 'A' and navigate to 'Resource Detail Screen' screen (from map screen)on browser 'B2' <-> 'Resource Detail Screen' is displayed where 'SST' is not displayed.
				 */
				selenium2.open(propEnvDetails.getProperty("urlRel"));
				strFailMsg = objLogin.login(selenium2, strUserName,
						strPassword);	
				
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg=objMap.verifyStatTypesInResourcePopup(selenium2, strResource, strEventStatType, strRoleStatType, false, false);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
				try {
					assertTrue(strFailMsg.equals(""));
					
					//click on View Info
					selenium2.click("link=View Info");
					selenium2.waitForPageToLoad(gstrTimeOut);
					
					try{
						assertEquals("View Resource Detail", selenium2.getText(propElementDetails
									.getProperty("Header.Text")));
						log4j.info("View Resource Detail screen is displayed");
						
						try{
							assertFalse(selenium2.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"+strStatType1+"']"));
							log4j.info(strStatType1+" is not displayed in 'View Resource Detail'.");
							intResCnt++;
						}catch(AssertionError ae){
							log4j.info(strStatType1+" is still displayed in 'View Resource Detail'.");
							gstrReason=gstrReason+" "+strStatType1+" is still displayed in 'View Resource Detail'.";
						}
						
					}catch(AssertionError ae){
						log4j.info("View Resource Detail screen is NOT displayed");
						gstrReason="View Resource Detail screen is NOT displayed";
					}
					
					
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
								
				/*
				 * Step 4: Login as user 'A' and navigate to 'View >> Map' on browser 'B3'and select the resource icon of 'RS' <->	'Regional Map View' screen is displayed along with resource info pop up window where 'SST' is not displayed. 
				 */
				selenium3.open(propEnvDetails.getProperty("urlRel"));
				strFailMsg = objLogin.login(selenium3, strUserName,
						strPassword);	
				
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg=objMap.verifyStatTypesInResourcePopup(selenium3, strResource, strEventStatType, strRoleStatType, false, false);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
												
				try {
					assertTrue(strFailMsg.equals(""));	
					intResCnt++;
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				//logout of the application
				try {
					assertTrue(strFailMsg.equals(""));				
					strFailMsg=objLogin.logout(seleniumMain);					
									
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}	
				/*
				 * Step 5: 	Login as RegAdmin, navigate to 'Setup >> Status Types' 	<->	'Status Type List' screen is displayed where 'SST' is not displayed
				 */
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg = objLogin.login(seleniumMain, strLoginUserName,
							 strLoginPassword);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
							
				try {
					assertTrue(strFailMsg.equals(""));	
					//nav to default region
					strFailMsg = objLogin.navUserDefaultRgn(seleniumMain, strRegn);
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
				
				try {
					assertTrue(strFailMsg.equals(""));	
					strFailMsg=objStatTyp.navStatusTypList(seleniumMain);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
				try {
					assertTrue(strFailMsg.equals(""));	
					
					try{
						assertFalse(seleniumMain.isElementPresent("//div[@id='mainContainer']/table[@class="
								+ "'displayTable striped border sortable']/"
								+ "tbody/tr/td[2][text()='" + strStatType1
								+ "']"));
						log4j.info(strStatType1+" is NOT displayed");
						/*
						 * Step 6: Select the checkbox 'include inactive status types' present at the top right corner. <->	'SST' is displayed in the 'Status Type List' screen.

							<-> 'Disabled' is displayed under the 'Active' column for SST status type. 
						 */
						//select include inactive status types
						seleniumMain.click(propElementDetails
								.getProperty("StatusType.IncludeInactiveST"));
						seleniumMain.waitForPageToLoad(gstrTimeOut);
						
						try{
							assertTrue(seleniumMain.isElementPresent("//div[@id='mainContainer']/table[@class="
									+ "'displayTable striped border sortable']/"
									+ "tbody/tr/td[2][text()='" + strStatType1
									+ "']"));
							assertTrue(seleniumMain.isElementPresent("//div[@id='mainContainer']/table[@class="
									+ "'displayTable striped border sortable']/"
									+ "tbody/tr/td[2][text()='" + strStatType1
									+ "']/parent::tr/td[3][text()='Disabled']"));
							log4j.info(strStatType1+" is displayed in the 'Status Type List' screen. and 'Disabled' is displayed under the 'Active' column for " + strStatType1
									+ " status type");
							
							//select include inactive status types
							seleniumMain.click(propElementDetails
									.getProperty("StatusType.IncludeInactiveST"));
							seleniumMain.waitForPageToLoad(gstrTimeOut);
							/*
							 * Step 7: Click on 'Edit' link next to SST, select 'Active' check box and click on 'Save'. <->	'Status Type List' screen is displayed.

							<-> 'Active' is displayed under the 'Active' column. 
							 */
														
							try {
								assertTrue(strFailMsg.equals(""));	
								strFailMsg=objStatTyp.activeStatusType(seleniumMain, strStatType1, true);
							} catch (AssertionError Ae) {
								gstrReason = gstrReason+" "+strFailMsg;
							}
							
							try {
								assertTrue(strFailMsg.equals(""));
								/*
								 * Step 8: Refresh the View 'V1' screen on browser 'B1' <->	SST is displayed 
								 */
								strFailMsg=objRef.refreshPage(selenium1);
								try {
									assertTrue(strFailMsg.equals(""));
							
									try{
										assertTrue(selenium1.isElementPresent("//div[@id='mainContainer']/div/table/thead/tr/th[3]/a[text()='"+strStatType1+"']"));										
										log4j.info(strStatType1+" is displayed");
										intResCnt++;
									}catch(AssertionError ae){
										log4j.info(strStatType1+" is NOT displayed");
										gstrReason=gstrReason+" "+strStatType1+" is NOT displayed";
									}
								
								} catch (AssertionError Ae) {
									gstrReason = gstrReason+" "+strFailMsg;
								}
								/*
								 * Step 9: Refresh the 'Resource Detail View' on browser 'B2' <->	SST is displayed 
								 */
								strFailMsg=objRef.refreshPage(selenium2);
								try {
									assertTrue(strFailMsg.equals(""));
									try{
										assertTrue(selenium2.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"+strStatType1+"']"));
										log4j.info(strStatType1+" is displayed in 'View Resource Detail'.");
										intResCnt++;
									}catch(AssertionError ae){
										log4j.info(strStatType1+" is NOT displayed in 'View Resource Detail'.");
										gstrReason=gstrReason+" "+strStatType1+" is NOT displayed in 'View Resource Detail'.";
									}
								
								} catch (AssertionError Ae) {
									gstrReason = gstrReason+" "+strFailMsg;
								}
								/*
								 * Step 10: Refresh the 'Regional Map View' on browser 'B3' <->	SST is displayed
								 */
								strFailMsg=objRef.refreshPage(selenium3);
								try {
									assertTrue(strFailMsg.equals(""));
									try{
										assertTrue(selenium3.getText(propElementDetails.getProperty("ViewMap.ResPopup.StatTypeList")).contains(strStatType1));										
										log4j.info(strStatType1+" is displayed in Regional Map View");
										intResCnt++;
									}catch(AssertionError ae){
										log4j.info(strStatType1+" is NOT displayed in Regional Map View");
										gstrReason=gstrReason+" "+strStatType1+" is NOT displayed in Regional Map View";
									}
									
								} catch (AssertionError Ae) {
									gstrReason = gstrReason+" "+strFailMsg;
								}
								
								if(intResCnt==6)
									gstrResult="PASS";
								
							} catch (AssertionError Ae) {
								gstrReason = gstrReason+" "+strFailMsg;
							}
							
						} catch (AssertionError Ae) {
							log4j.info(strStatType1+" is displayed in the 'Status Type List' screen. and 'Disabled' is displayed under the 'Active' column for " + strStatType1
									+ " status type");
							gstrReason = gstrReason+" "+strStatType1+" is NOT displayed in the 'Status Type List' screen. and 'Disabled' is displayed under the 'Active' column for " + strStatType1
							+ " status type";
						}
						
					} catch (AssertionError Ae) {
						log4j.info(strStatType1+" is NOT displayed");
						gstrReason = strStatType1+" is NOT displayed";
					}
				
				
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+" "+strFailMsg;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "69234";
			gstrTO = "Verify that a saturation score status type can be reactivated";
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
	}
	
	
	/***********************************************************************
	'Description	:Verify that a saturation score status type can be deactivated.
	'Precondition	: 1. Test user has created a number status type 'NST'
					2. Resource Type 'RT' is associated with 'NST' and a resource 'RS' is created under 'RT'
					3. User 'A' has update right on 'RS' and is assigned a role which has update right on 'NST'
					4. View 'V1' is created selecting 'NST' and 'RS'
					5. Status type Section 'S1' is created for status type 'NST'
					6. After setting the above preconditions, deactivate the status type 'NST' .
	'Arguments		:void
	'Returns		:void
	'Date	 		:20-Mar-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Name>                              		<Name>
	************************************************************************/
	@Test
	public void testBQS69199() throws Exception {
		String strFuncResult = "";
		try {
			gstrTCID = "69199";
			gstrTO = "Verify that a saturation score status type can be deactivated.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			Login objLogin = new Login();// object of class Login
			StatusTypes objStatusTypes = new StatusTypes();
			ReadData objReadData = new ReadData();
			Views objView = new Views();
			ViewMap objMap = new ViewMap();
			General objRef = new General();
			Roles objRoles = new Roles();
			ResourceTypes objRT = new ResourceTypes();
			CreateUsers objCreateUsers = new CreateUsers();// object of class
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			Resources objRs = new Resources();
			Views objViews = new Views();

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			// login details
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;

			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);

			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String statrSatuTypeName = "SST" + strTimeText;
			String strStatType1 = "ST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strNSTValue = "Saturation Score";
			String strSTvalue[] = new String[2];

			String strStatTypeArr[] = { statrSatuTypeName };
			String strEventStatType[] = {};
			String[] strRoleStatType = { statrSatuTypeName, strStatType1 };

			String strRegn = objReadData.readData("Login", 3, 4);
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "Abbrv";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			String strSection1 = "AB_1" + strTimeText;
			String strArStatType1[] = { statrSatuTypeName, strStatType1 };

			int intResCnt = 0;

			strFuncResult = objLogin.login(seleniumMain, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(seleniumMain,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumMain, strNSTValue, statrSatuTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumMain, statrSatuTypeName);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumMain, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumMain, strStatType1);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumMain,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				String strSTvaluesUpdate[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "false" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumMain, false, false, strSTvalues,
						strSTvaluesUpdate, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(seleniumMain,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumMain,
						strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 2. Resource Type 'RT' is associated with 'NST', 'ST' and a
			 * resource 'RS' is created under 'RT'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumMain,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				seleniumMain.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[1] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumMain,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumMain,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumMain, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumMain,
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
			 * 3. User 'A' has update right on 'RS' and is assigned a role which
			 * has update right on 'NST'
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumMain);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumMain, strUserName_A, strInitPwd, strConfirmPwd,
						strUsrFulName_A);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumMain, strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumMain, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumMain, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. View 'V1' is created selecting 'NST' and 'RS'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[]={strSTvalue[0]};
				strFuncResult = objViews.createView(seleniumMain, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Status type Section 'S1' is created for status type 'NST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumMain);
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
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType1, strSection1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium1, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objView.navToUserView(selenium1, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				try {
					assertTrue(selenium1
							.isElementPresent("//div[@id='mainContainer']"
									+ "/div/table/thead/tr/th[2]/a[text()='"
									+ strResrctTypName + "']"));
					assertTrue(selenium1
							.isElementPresent("//div[@id='mainContainer']"
									+ "/div/table/tbody/tr/td[2]/a[text()='"
									+ strResource + "']"));
					assertTrue(selenium1
							.isElementPresent("//div[@id='mainContainer']"
									+ "/div/table/thead/tr/th[3]/a[text()='"
									+ statrSatuTypeName + "']"));
					log4j.info("Resource " + strResource
							+ " is displayed under " + strResrctTypName
							+ " along with " + statrSatuTypeName + " in view "
							+ strViewName + " screen");
					intResCnt++;
				} catch (AssertionError Ae) {
					log4j.info("Resource " + strResource
							+ " is NOT displayed under " + strResrctTypName
							+ " along with " + statrSatuTypeName + " in view "
							+ strViewName + " screen");
					gstrReason = "Resource " + strResource
							+ " is NOT displayed under " + strResrctTypName
							+ " along with " + statrSatuTypeName + " in view "
							+ strViewName + " screen";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Step 3: Login as user 'A' and navigate to 'View Resource Detail'
			 * screen on browser 'B2' <-> NST is displayed under section S1 on
			 * 'View Resource Detail'.
			 */

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.login(selenium2, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objView.navToUserView(selenium2, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objView.navAndCheckInViewResourceDetail(
						selenium2, strSection1, strStatTypeArr, strResource);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));
				intResCnt++;
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			/*
			 * Step 4: Login as user 'A' and navigate to 'View >> Map' on
			 * browser 'B3' <-> 'Regional Map View' screen is displayed.
			 * 
			 * <-> NST and ST status types are displayed on the resource RS pop
			 * up window
			 */

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.login(selenium3, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objMap.verifyStatTypesInResourcePopup(
						selenium3, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				intResCnt++;
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			/*
			 * Step 5: Login as RegAdmin, navigate to 'Setup >> Status Types'
			 * and inactivate the status type 'NST' <-> 'NST' is no longer
			 * displayed in the 'Status Type List' screen.
			 */
			// Login as Region Admin

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.login(seleniumMain, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.navUserDefaultRgn(seleniumMain,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objStatusTypes.navStatusTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objStatusTypes.activeStatusType(seleniumMain,
						statrSatuTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));

				try {
					selenium1.selectWindow("");
					selenium1.selectFrame("Data");
					Thread.sleep(1000);
					objRef.refreshPageNew(selenium1);

				} catch (Exception e) {
					log4j.info("Refresh failed for first Time");

					try {
						objRef.refreshPageNew(selenium1);
					} catch (Exception e2) {
						log4j.info("Refresh failed for second Time");

						try {
							objRef.refreshPageNew(selenium1);
						} catch (Exception e3) {
							log4j.info("Refresh failed for third Time");
						}
					}
				}
				
				try {
					objRef.refreshPageNew(selenium1);
				} catch (Exception e) {
				}
				try {
					objRef.refreshPageNew(selenium1);
				} catch (Exception e) {
				}
				

				try {
					assertTrue(strFuncResult.equals(""));

					try {
						assertFalse(selenium1
								.isElementPresent("//div[@id='mainContainer']/div/table/thead/tr/th[3]/a[text()='"
										+ statrSatuTypeName + "']"));
						assertTrue(selenium1
								.isTextPresent("There are no resources to display in this view."));
						log4j
								.info(statrSatuTypeName
										+ " is no longer displayed and There are no resources to display in this view.' message is displayed");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j
								.info(statrSatuTypeName
										+ " is still displayed and There are no resources to display in this view.' message is NOT displayed");
						gstrReason = gstrReason
								+ " "
								+ statrSatuTypeName
								+ " is still displayed and There are no resources to display in this view.' message is NOT displayed";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));

					try {
						selenium2.selectWindow("");
						selenium2.selectFrame("Data");
						Thread.sleep(1000);
						objRef.refreshPageNew(selenium2);

					} catch (Exception e) {
						log4j.info("Refresh failed for first Time");

						try {
							objRef.refreshPageNew(selenium2);
						} catch (Exception e2) {
							log4j.info("Refresh failed for second Time");

							try {
								objRef.refreshPageNew(selenium2);
							} catch (Exception e3) {
								log4j.info("Refresh failed for third Time");
							}
						}
					}
					
					try {
						objRef.refreshPageNew(selenium2);
					} catch (Exception e) {
					}
					
					try {
						objRef.refreshPageNew(selenium2);
					} catch (Exception e) {
					}
					
					try {
						assertFalse(selenium2
								.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ statrSatuTypeName + "']"));
						log4j
								.info(statrSatuTypeName
										+ " is not displayed in 'View Resource Detail'.");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j
								.info(statrSatuTypeName
										+ " is still displayed in 'View Resource Detail'.");
						gstrReason = gstrReason
								+ " "
								+ statrSatuTypeName
								+ " is still displayed in 'View Resource Detail'.";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));

					try {
						selenium3.selectWindow("");
						selenium3.selectFrame("Data");
						Thread.sleep(1000);
						objRef.refreshPageNew(selenium3);

					} catch (Exception e) {
						log4j.info("Refresh failed for first Time");

						try {
							objRef.refreshPageNew(selenium3);
						} catch (Exception e2) {
							log4j.info("Refresh failed for second Time");

							try {
								objRef.refreshPageNew(selenium3);
							} catch (Exception e3) {
								log4j.info("Refresh failed for third Time");
							}
						}
					}
					
					try {
						objRef.refreshPageNew(selenium3);
					} catch (Exception e) {
					}
					
					try {
						objRef.refreshPageNew(selenium3);
					} catch (Exception e) {
					}
					
					try {
						assertFalse(selenium3
								.getText(
										propElementDetails
												.getProperty("ViewMap.ResPopup.StatTypeList"))
								.contains(statrSatuTypeName));
						assertTrue(selenium3
								.getText(
										propElementDetails
												.getProperty("ViewMap.ResPopup.StatTypeList"))
								.contains(strStatType1));
						log4j.info(statrSatuTypeName
								+ " is no longer displayed and " + strStatType1
								+ " is displayed");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j.info(statrSatuTypeName
								+ " is still displayed and " + statrSatuTypeName
								+ " is displayed");
						gstrReason = gstrReason + " " + statrSatuTypeName
								+ " is still displayed and " + statrSatuTypeName
								+ " is displayed";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objLogin.logout(seleniumMain);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));
					if (intResCnt == 6)
						gstrResult = "PASS";
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "69199";
			gstrTO = "Verify that a saturation score status type can be deactivated.";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
	
	
	/***********************************************************************
	'Description	:Verify that a saturation score status type can be reactivated.
	'Precondition	: 1. Test user has created a number status type 'NST'
					2. Resource Type 'RT' is associated with 'NST' and a resource 'RS' is created under 'RT'
					3. User 'A' has update right on 'RS' and is assigned a role which has update right on 'NST'
					4. View 'V1' is created selecting 'NST' and 'RS'
					5. Status type Section 'S1' is created for status type 'NST'
					6. After setting the above preconditions, deactivate the status type 'NST' .
	'Arguments		:void
	'Returns		:void
	'Date	 		:08-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Name>                              		<Name>
	************************************************************************/
	@Test
	public void testBQS69234() throws Exception {
		String strFuncResult = "";
		try {
			gstrTCID = "69234";
			gstrTO = "Verify that a saturation score status type can be reactivated.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			Login objLogin = new Login();// object of class Login
			StatusTypes objStatusTypes = new StatusTypes();
			ReadData objReadData = new ReadData();
			Views objView = new Views();
			ViewMap objMap = new ViewMap();
			General objRef = new General();
			Roles objRoles = new Roles();
			ResourceTypes objRT = new ResourceTypes();
			CreateUsers objCreateUsers = new CreateUsers();// object of class
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			Resources objRs = new Resources();
			Views objViews = new Views();

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			// login details
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;

			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);

			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String statrNumTypeName = "SST" + strTimeText;
			String strStatType1 = "ST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strNSTValue = "Saturation Score";
			String strSTvalue[] = new String[2];

			String strRegn = objReadData.readData("Login", 3, 4);
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "Abbrv";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			String strSection1 = "AB_1" + strTimeText;
			String strArStatType1[] = { statrNumTypeName, strStatType1 };

			int intResCnt = 0;

			strFuncResult = objLogin.login(seleniumMain, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(seleniumMain,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumMain, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumMain, statrNumTypeName);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumMain, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumMain, strStatType1);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumMain,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				String strSTvaluesUpdate[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "false" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumMain, false, false, strSTvalues,
						strSTvaluesUpdate, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(seleniumMain,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumMain,
						strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 2. Resource Type 'RT' is associated with 'NST', 'ST' and a
			 * resource 'RS' is created under 'RT'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumMain,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				seleniumMain.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumMain,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumMain,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumMain, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumMain,
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
			 * 3. User 'A' has update right on 'RS' and is assigned a role which
			 * has update right on 'NST'
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumMain);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumMain, strUserName_A, strInitPwd, strConfirmPwd,
						strUsrFulName_A);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumMain, strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumMain, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumMain, strUserName_A, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. View 'V1' is created selecting 'NST' and 'RS'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumMain, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Status type Section 'S1' is created for status type 'NST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumMain);
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
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType1, strSection1, true);

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
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.login(seleniumMain, strLoginUserName,
						strLoginPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.navUserDefaultRgn(seleniumMain,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objStatusTypes.navStatusTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objStatusTypes.activeStatusType(seleniumMain,
						statrNumTypeName, false);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.logout(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium1, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objView.navToUserView(selenium1, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));

				try {
					assertFalse(selenium1
							.isElementPresent("//div[@id='mainContainer']/div/table/thead/tr/th[3]/a[text()='"
									+ statrNumTypeName + "']"));
					log4j
							.info(statrNumTypeName
									+ " is no longer displayed and There are no resources to display in this view.' message is displayed");
					intResCnt++;
				} catch (AssertionError ae) {
					log4j
							.info(statrNumTypeName
									+ " is still displayed and There are no resources to display in this view.' message is NOT displayed");
					gstrReason = gstrReason
							+ " "
							+ statrNumTypeName
							+ " is still displayed and There are no resources to display in this view.' message is NOT displayed";
				}

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.login(selenium2, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objView.navToUserView(selenium2, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objView.navToViewResourceDetailPage(selenium2,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				assertFalse(selenium2
						.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
								+ statrNumTypeName + "']"));
				log4j.info(statrNumTypeName
						+ " is not displayed in 'View Resource Detail'.");
				intResCnt++;
			} catch (AssertionError ae) {
				log4j.info(statrNumTypeName
						+ " is still displayed in 'View Resource Detail'.");
				gstrReason = gstrReason + " " + statrNumTypeName
						+ " is still displayed in 'View Resource Detail'.";
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.login(selenium3, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objMap.navToRegionalMapView(selenium3);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objMap
						.navResPopupWindow(selenium3, strResource);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				assertFalse(selenium3.getText(
						propElementDetails
								.getProperty("ViewMap.ResPopup.StatTypeList"))
						.contains(statrNumTypeName));
				assertTrue(selenium3.getText(
						propElementDetails
								.getProperty("ViewMap.ResPopup.StatTypeList"))
						.contains(strStatType1));
				log4j.info(statrNumTypeName + " is no longer displayed and "
						+ statrNumTypeName + " is displayed");
				intResCnt++;
			} catch (AssertionError ae) {
				log4j.info(statrNumTypeName + " is still displayed and "
						+ statrNumTypeName + " is displayed");
				gstrReason = gstrReason + " " + statrNumTypeName
						+ " is still displayed and " + statrNumTypeName
						+ " is displayed";
			}
			// Login as Region Admin

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.login(seleniumMain, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objLogin.navUserDefaultRgn(seleniumMain,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objStatusTypes.navStatusTypList(seleniumMain);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objStatusTypes.activeStatusType(seleniumMain,
						statrNumTypeName, true);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			
			
			try {
				assertTrue(strFuncResult.equals(""));

				try {
					selenium1.selectWindow("");
					selenium1.selectFrame("Data");
					Thread.sleep(1000);
					objRef.refreshPageNew(selenium1);

				} catch (Exception e) {
					log4j.info("Refresh failed for first Time");

					try {
						objRef.refreshPageNew(selenium1);
					} catch (Exception e2) {
						log4j.info("Refresh failed for second Time");

						try {
							objRef.refreshPageNew(selenium1);
						} catch (Exception e3) {
							log4j.info("Refresh failed for third Time");
						}
					}
				}

				try {
					objRef.refreshPageNew(selenium1);
				} catch (Exception e) {
				}
				
				try {
					objRef.refreshPageNew(selenium1);
				} catch (Exception e) {
				}
				
				try {
					assertTrue(strFuncResult.equals(""));

					try {
						assertTrue(selenium1
								.isElementPresent("//div[@id='mainContainer']"
										+ "/div/table/thead/tr/th[3]/a[text()='"
										+ statrNumTypeName + "']"));
					
						log4j.info(statrNumTypeName
								+ " is still displayed and There are no "
								+ "resources to display in this view.'"
								+ " message is NOT displayed");

						intResCnt++;
					} catch (AssertionError ae) {
						log4j.info(statrNumTypeName
								+ " is no longer displayed and There are no"
								+ " resources to display in this view."
								+ "' message is displayed");
						gstrReason = gstrReason
								+ " "
								+ statrNumTypeName
								+ " is no longer displayed and There are no resources "
								+ "to display in this view.' message is displayed";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));

					try {
						selenium2.selectWindow("");
						selenium2.selectFrame("Data");
						Thread.sleep(1000);
						
						objRef.refreshPageNew(selenium2);

					} catch (Exception e) {
						log4j.info("Refresh failed for first Time");

						try {
							objRef.refreshPageNew(selenium2);
						} catch (Exception e2) {
							log4j.info("Refresh failed for second Time");

							try {
								objRef.refreshPageNew(selenium2);
							} catch (Exception e3) {
								log4j.info("Refresh failed for third Time");
							}
						}
					}
					
					try {
						objRef.refreshPageNew(selenium2);
					} catch (Exception e) {
					}
					
					try {
						objRef.refreshPageNew(selenium2);
					} catch (Exception e) {
					}
					
					
					try {
						assertTrue(selenium2
								.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ statrNumTypeName + "']"));
						log4j
								.info(statrNumTypeName
										+ " is still displayed in 'View Resource Detail'.");
						intResCnt++;
					} catch (AssertionError ae) {
						log4j
								.info(statrNumTypeName
										+ " is not displayed in 'View Resource Detail'.");

						gstrReason = gstrReason
								+ " "
								+ statrNumTypeName
								+ " is not displayed in 'View Resource Detail'.";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));

					try {
						selenium3.selectWindow("");
						selenium3.selectFrame("Data");
						Thread.sleep(1000);
						objRef.refreshPageNew(selenium3);

					} catch (Exception e) {
						log4j.info("Refresh failed for first Time");

						try {
							objRef.refreshPageNew(selenium3);
						} catch (Exception e2) {
							log4j.info("Refresh failed for second Time");

							try {
								objRef.refreshPageNew(selenium3);
							} catch (Exception e3) {
								log4j.info("Refresh failed for third Time");
							}
						}
					}
					
					try {
						objRef.refreshPageNew(selenium3);
					} catch (Exception e) {
					}
					try {
						objRef.refreshPageNew(selenium3);
					} catch (Exception e) {
					}
					
					
					try {
						assertTrue(selenium3
								.getText(
										propElementDetails
												.getProperty("ViewMap.ResPopup.StatTypeList"))
								.contains(statrNumTypeName));
						assertTrue(selenium3
								.getText(
										propElementDetails
												.getProperty("ViewMap.ResPopup.StatTypeList"))
								.contains(strStatType1));
						log4j.info(statrNumTypeName
								+ " is still displayed and " + statrNumTypeName
								+ " is displayed");
						intResCnt++;
					} catch (AssertionError ae) {
						
						log4j.info(statrNumTypeName
								+ " is no longer displayed and " + statrNumTypeName
								+ " is displayed");
						
						gstrReason = gstrReason + " " + statrNumTypeName
						+ " is no longer displayed and " + statrNumTypeName
						+ " is displayed";
					}

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objLogin.logout(seleniumMain);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

				try {
					assertTrue(strFuncResult.equals(""));
					if (intResCnt == 6)
						gstrResult = "PASS";
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "69234";
			gstrTO = "Verify that a saturation score status type can be reactivated.";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}
}
