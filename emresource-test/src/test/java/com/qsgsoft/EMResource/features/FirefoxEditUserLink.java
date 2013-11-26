package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

/********************************************************************
' Description :This class includes EditUserLink requirement testcases
' Precondition:
' Date		  :22-Aug-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FirefoxEditUserLink  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditUserLink");
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
	
	Selenium selenium;
	
	@Before
	public void setUp() throws Exception {

		gdtStartDate = new Date();
		gstrBrowserName = "Firefox 4.0.1";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrBrowserName = propEnvDetails.getProperty("BrowserFirefox").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersionFirefox");
		
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		Paths_Properties objPaths_Properties = new Paths_Properties();
		propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();
		
		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
						.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		selenium.close();
		
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
	'Description	:Verify that the URL of a user link can be updated.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS733() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		UserLinks objUserLinks =new UserLinks();
		General objGeneral=new General();
		
		try {
			gstrTCID = "BQS-733 ";
			gstrTO = "Verify that the URL of a user link can be updated.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			//String strFILE_PATH = pathProps.getProperty("TestData_path");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strUploadFilePath = pathProps
					.getProperty("CreateEve_UploadImageFile_OpenPath");

			String strLablText = "Link" + System.currentTimeMillis();
			String strExternalURL = "www.qsgsoft.com";
			boolean blnQuicklaunch = false;

			//String strImage = "/GetLinkImage?uid=4551a2955c6eba75:-1dfff5a9:13965b882aa:-7fd1";
			String strDestinationURL = "www.google.com";
			String strImageSize = "32x27";
			String strFileSize = "16.55 kb";
			

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			 * 1 Login as RegAdmin and navigate to Setup>>User links User Link
			 * list screen is displayed
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUserLinks.createUserLinkFirefox(selenium,
						strLablText, strExternalURL, blnQuicklaunch,
						strUploadFilePath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Click on ''Edit'' corresponding to a user link created by
			 * providing a URL The data entered is retained in the ''Edit user
			 * link'' screen.
			 * 
			 * The option to change the attached file is not available.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUserLinks.navEditUserLinkPage(selenium,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUserLinks
						.verifyUserLinkretainedInEditUsrLinkPge(selenium,
								strLablText, strExternalURL, blnQuicklaunch,
								strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUserLinks.saveUserLink(selenium, strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUserLinks.navEditUserLinkPage(selenium,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strLablText="Edit"+strLablText;
				strExternalURL="www.google.com";
				strFuncResult = objUserLinks.fillUserLinkFieldsInEditUserPage(
						selenium, strLablText, strExternalURL, blnQuicklaunch, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*3 	Edit the URL and save. 		The user link is listed on the User Link list screen with the following columns:
				a. Action
				i. Edit
				ii. Delete
				iii. Hide
				iv. Up
				v. Down
				b. Link: Label text
				c. Show as: User Link
				d. Image: The attached image
				e. Destination URL: Updated URL
				f. Image size: Attached file size (in pixels)
				g. File size: Attached file size (in bytes) 
			
*/
			
			try {
				assertEquals("", strFuncResult);

				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Show";
				String strLablHeaderActionHide = "Hide";
				String strLablHeaderActionUp = "Up";
				String strLablHeaderActionDown = "Down";

				strFuncResult = objUserLinks
						.verifyLinkHeadersInUserLinkListPage(selenium,
								strLablText, strLablHeaderAction,
								strLablHeaderActionEdit,
								strLablHeaderActionDelete,
								strLablHeaderActionHide,
								strLablHeaderActionShow, strLablHeaderActionUp,
								strLablHeaderActionDown,blnQuicklaunch);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/thead/tr/th[4][text()='Image']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("The user link is listed on the User Link list screen with 'Image' column ");
			} catch (AssertionError Ae) {
				strFuncResult="The user link is NOT listed on the User Link list screen with 'Image' column ";
				log4j.info("The user link is NOT listed on the User Link list screen with 'Image' column ");
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertEquals("Destination URL", selenium.getText("//div[@id='mainContainer']/table/thead/tr/th[5]"));

				log4j.info("The user link is listed on the User Link list screen with 'Destination URL' column ");
			} catch (AssertionError Ae) {
				strFuncResult="The user link is NOT listed on the User Link list screen with 'Destination URL' column ";
				log4j.info("The user link is NOT listed on the User Link list screen with 'Destination URL' column ");
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				assertEquals("Image Size", selenium.getText("//div[@id='mainContainer']/table/thead/tr/th[6]"));

				log4j.info("The user link is listed on the User Link list screen with 'Image Size' column ");
			} catch (AssertionError Ae) {
				strFuncResult="The user link is NOT listed on the User Link list screen with 'Image Size' column ";
				log4j.info("The user link is NOT listed on the User Link list screen with 'Image Size' column ");
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertEquals("File Size", selenium.getText("//div[@id='mainContainer']/table/thead/tr/th[7]"));

				log4j.info("The user link is listed on the User Link list screen with 'File Size' column ");
			} catch (AssertionError Ae) {
				strFuncResult="The user link is NOT listed on the User Link list screen with 'File Size' column ";
				log4j.info("The user link is NOT listed on the User Link list screen with 'File Size' column ");
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				assertEquals(strDestinationURL, selenium.getText("//div[@id='mainContainer']/table/tbody/tr/td[5]"));

				log4j.info("The user link is listed on the User Link list screen with Destination URL data ");
			} catch (AssertionError Ae) {
				strFuncResult="The user link is NOT listed on the User Link list screen with Destination URL data ";
				log4j.info("The user link is NOT listed on the User Link list screen with Destination URL data ");
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				assertEquals(strImageSize, selenium.getText("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"+strLablText+"']/following-sibling::td[4]"));

				log4j.info("The user link is listed on the User Link list screen with Image size data ");
			} catch (AssertionError Ae) {
				strFuncResult="The user link is NOT listed on the User Link list screen with Image size data ";
				log4j.info("The user link is NOT listed on the User Link list screen with Image size data ");
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				assertEquals(
						strFileSize,
						selenium
								.getText("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
										+ strLablText
										+ "']/following-sibling::td[5]"));

				log4j
						.info("The user link is listed on the User Link list screen with File size data ");
			} catch (AssertionError Ae) {
				strFuncResult = "The user link is NOT listed on the User Link list screen with File size data ";
				log4j
						.info("The user link is NOT listed on the User Link list screen with File size data ");
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strTitle="Google";
				strFuncResult = objUserLinks
						.openImgLinkAndVerifyUsrLinkByUsrLinkName(selenium,
								strDestinationURL, strTitle,strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("",strFuncResult);

				gstrResult = "PASS";
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
		

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-733";
			gstrTO = "Verify that the URL of a user link can be updated.";
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
	'Description		:Verify that the Form selected for user link can be changed.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/28/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS734() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();// object of class	
		Forms objForms = new Forms();
		UserLinks objUserLinks=new UserLinks();
	try{	
		gstrTCID = "734";	//Test Case Id	
		gstrTO = " Verify that the Form selected for user link can be changed.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

				Date_Time_settings dts = new Date_Time_settings();
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
				selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				String strLoginUserName = "";
				String strLoginPassword = "";
				String strRegn =  rdExcel.readData("Login", 3, 4);

				// Data for creating user
				String strUserName = "AutoUsr" + System.currentTimeMillis();
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);
				String strUsrFulName = "FN" + strUserName;
				// search user data
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);

				// form
				String strFormTempTitleOF1 = "FORM1_" + strTimeText;
				String strFormTempTitleOF2 = "FORM2_" + strTimeText;
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Users";
				String strDescription = "Description";

				// Basic Information
				String strNFQuestTitl = "NFQBT" + strTimeText;
				String strDesc = "Descritption";
				String strAbbr = "Abb";
				String strInstructn = "Instruction";
				String strLabl = "Text";

				String strTextTitl = "Text" + strTimeText;
				String strTextAbb = "Tabb";
				String strTextLength = "26";
				// User Link

				String strLablText = "USER" + strTimeText;
				String strExternalURL = "";
				String strUploadFilePath = pathProps
						.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage.jpg";
				
				log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
						+ " EXECUTION STATRTS~~~~~");
				try {
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

				// USER
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.navUserListPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
							strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
							selenium, strUserName, strByRole, strByResourceType,
							strByUserInfo, strNameFormat);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Form 1
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.navToFormConfig(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms
							.fillAllFieldsInCreateNewForm(selenium,
									strFormTempTitleOF1, strDescription,
									strFormActiv, strComplFormDel, false, false,
									false, false, true, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.selectUsersForForm(selenium,
							strUserName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResource = "";
					strFuncResult = objForms.selectResourcesForForm(selenium,
							strResource, strFormTempTitleOF1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Form 2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.navToFormConfig(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms
							.fillAllFieldsInCreateNewForm(selenium,
									strFormTempTitleOF2, strDescription,
									strFormActiv, strComplFormDel, false, false,
									false, false, true, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.selectUsersForForm(selenium,
							strUserName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strResource = "";
					strFuncResult = objForms.selectResourcesForForm(selenium,
							strResource, strFormTempTitleOF2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Adding a questionarri to a form
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.navToAddQestnPageOfNF(selenium,
							strFormTempTitleOF2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.newFormQuestnBasicInfo(selenium,
							strNFQuestTitl, strDesc, strAbbr, strInstructn, true,
							strLabl);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.newFormQuestnTextInfo(selenium,
							strTextTitl, strTextAbb, strTextLength, true, true);
					selenium.selectWindow("");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// User Link

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.navToUserLinkList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
							selenium, strLablText, strExternalURL, false, true,
							strFormTempTitleOF1, false, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}


				log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					     + " EXECUTION ENDS~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup>>User links
		  Expected Result:User Link list screen is displayed
		*/
		//2373
		      log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.navToUserLinkList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

		/*
		* STEP :
		  Action:Click on ''Edit'' corresponding to a user link created for ''EMResource form'' for the form F1.
		  Expected Result:The data entered is retained in the ''Edit user link'' screen.
		  The option to change the attached file is not available.
		*/
		//2374
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.navEditUserLinkPage(selenium, strLablText);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.verDataRetainInEditUsrLinkPageForForm(selenium,
							strLablText, strFormTempTitleOF1, false);
					selenium.select("name=questionaireID", "label="+strFormTempTitleOF2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

		/*
		* STEP :
		  Action:Change the form selected for ''EMResource Form'' from F1 to F2 and save.
		  Expected Result:The user link is listed on the User Link list screen with the following columns: 
	           a. Action 
		            i. Edit
		            ii. Delete
		            iii. Hide
		            iv. Up
		             v. Down
			   b. Link: Updated Label text
			   c. Show as: User Link
			   d. Image: The attached image
			   e. Destination URL: Updated Form name
			   f. Image size: Attached file size (in pixels)
			   g. File size: Attached file size (in bytes)
		*/
		//2375
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.saveUserLink(selenium, strLablText);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					String strLablHeaderAction = "Action";
					String strLablHeaderActionEdit = "Edit";
					String strLablHeaderActionDelete = "Delete";
					String strLablHeaderActionShow = "Show";
					String strLablHeaderActionHide = "Hide";
					String strLablHeaderActionUp = "Up";
					String strLablHeaderActionDown = "Down";
					strFuncResult = objUserLinks.verifyLinkHeadersInUserLinkListPage(selenium, strLablText,
							strLablHeaderAction, strLablHeaderActionEdit, strLablHeaderActionDelete, strLablHeaderActionHide,
							strLablHeaderActionShow, strLablHeaderActionUp, strLablHeaderActionDown, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					String strShow="User Link";
					String strImgSize="32x27";
					String strFileSize="16.55 kb";
					strFuncResult = objUserLinks.varOtherFldsInUserLink(selenium, strLablText,
							strShow, strFormTempTitleOF2, strImgSize, strFileSize);
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
					gstrResult = "PASS";
					String[] strTestData = {
							propEnvDetails.getProperty("Build"),
							gstrTCID,
							strUserName + "/" + strInitPwd,
							"",
							"",
							"Verify form 4th step.",
							strTextTitl};				
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");

					objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "BQS-734";
		gstrTO = "Verify that the Form selected for user link can be changed.";
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
