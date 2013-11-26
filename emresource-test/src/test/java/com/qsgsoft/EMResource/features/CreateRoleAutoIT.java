package com.qsgsoft.EMResource.features;     
       
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;

/**********************************************************************
' Description :This class includes Create Role requirement testcases
' Precondition:
' Date		  :11-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/
@SuppressWarnings("unused")
public class CreateRoleAutoIT  {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CreateRole");
	static{
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
	Properties propPathDetails;
	Properties propAutoItDetails;
	String gstrErrorMsg;
	Selenium selenium,seleniumPrecondition;
	
	String gstrTimeOut="";
	

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
		propAutoItDetails= objPaths_Properties.ReadAutoit_FilePath();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		propPathDetails=objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
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
		
		selenium.stop();
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	
	
	/********************************************************************************
	'Description	:Verify that a user can be provided a role with right to Maintain
	 			     Document Library.
	'Precondition	:1. Folder F1 is created in Document Library.
	'				 2. User U1 is created. (created within testcase)
	'Arguments		:None
	'Returns		:None
	'Date	 		:22-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	

	@Test
	public void testBQS69268() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Roles objRoles = new Roles();
		DocumentLibrary objDocumentLibrary=new DocumentLibrary();
		
		try {
			gstrTCID = "BQS-69268 ";
			gstrTO = "Verify that a user can be provided a role with "
					+ "right to Maintain Document Library.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strRolesName = "";

			String strRoleValue = "";

			String strOptions = "";
			
			//User mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			//Craeting folder
			String strFolderName = "";
			String strFldDesc = "";
			String strTempFoldr_1 = "";
			String strTempFoldr_2 = "";
			String strTempDocName_1 = "";
			String strTempDocName_2 = "";
			String strTempDocName_3 = "";
			
			String strFldSelValue_1="";
			String strFldSelValue_2="";
			
			
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */ 
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Create a role 'R1' by selecting 'Maintain Document Library'
			 * right from 'Select the Rights for this Role' section. Role R1 is
			 * displayed in 'Roles List' page.
			 */ 
			
			try {
				assertEquals("", strFuncResult);

				strRolesName = "Role" + System.currentTimeMillis();
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintainDocLibrary");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

		
			try {
				assertEquals("", strFuncResult);
				
				selenium.click(propElementDetails
						.getProperty("CreateNewRole.CreateRole.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[3][text()='"
									+ strRolesName + "']"));

					log4j.info("Role is displayed in the role list Page");
					
				} catch (AssertionError Ae) {

					log4j.info("Role is NOT displayed in the role list Page");
					log4j.info(Ae);

					strFuncResult = "Role is NOT displayed in the role list Page";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				log4j.info("Role is present in the Roles List");

				String strReferenceLink = selenium
						.getAttribute("//div[@id='mainContainer']/table/tbody/"
								+ "tr/td[3][text()='" + strRolesName
								+ "']/preceding-sibling::td/a/@href");

				log4j.info(strReferenceLink);

				String[] splitStrValue = strReferenceLink.split("=");
				log4j.info(splitStrValue[4]);// Role value

				strRoleValue = splitStrValue[4];

			} catch (AssertionError Ae) {
				strFuncResult = "Role is NOT present in the Roles List" + Ae;
				log4j.info("Role is present NOT in the Roles List" + Ae);
				gstrReason = strFuncResult;

			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Navigate to Setup>>Users and click on the 'Edit' link
			 * associated with user U1 and select the role R1 from 'User Type &
			 * Roles' section and save the page. 'Users List' page is displayed.
			 */ 
			try {
				assertEquals("", strFuncResult);

				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 9, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = strUserName + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

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

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(selenium
							.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
							1);

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserName + "']"));
					log4j.info("User " + strUserName
							+ " Present in the User List ");

				} catch (AssertionError Ae) {
					strFuncResult = "User " + strUserName
							+ " NOT Present in the User List " + Ae;
					log4j.info("User " + strUserName
							+ " NOT Present in the User List " + Ae);
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Prcondition folder 
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult=objDocumentLibrary.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Fetch the value of the folder
			
			try {
				assertEquals("", strFuncResult);

				strFolderName = "Auto_Foldr_1" + System.currentTimeMillis();
				strTempFoldr_1=strFolderName;
				
				strFldDesc = "Dynamic Folder";
				strFuncResult = objDocumentLibrary.createNewFolder(selenium,
						strFolderName, strFldDesc, true, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFldSelValue_1 = selenium
						.getAttribute("//div[@id='mainContainer']/div[2]/a/span[text()='"
								+ strFolderName + "']/parent::a@onclick");
				log4j.info(strFldSelValue_1);

				String strFoldrValue[] = strFldSelValue_1.split("'");
				for (String s : strFoldrValue) {
					log4j.info(s);
				}

				
				strFldSelValue_1 = strFoldrValue[1].substring(2);
				log4j.info(strFldSelValue_1);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Logout as region admin
			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult=objDocumentLibrary.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFolderName = "Auto_Foldr_2" + System.currentTimeMillis();
				strTempFoldr_2=strFolderName;
				
				strFldDesc = "Dynamic Folder";
				strFuncResult = objDocumentLibrary.createNewFolder(selenium,
						strFolderName, strFldDesc, true, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Click on 'Add a New Document' button. 'Add A Document' screen
			 * is displayed.
			 */
			/*
			 * 8 Provide TextDoc as the 'Title', browse for an .text file for
			 * the field 'Attached File', select folder F1 under 'Create in' and
			 * click on 'Save'. TextDoc is displayed under folder F1 on
			 * 'Document Library' screen with "Delete" and "Move" buttons.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFldSelValue_2 = selenium
						.getAttribute("//div[@id='mainContainer']/div[2]/a/span[text()='"
								+ strFolderName + "']/parent::a@onclick");
				log4j.info(strFldSelValue_2);

				String strFoldrValue[] = strFldSelValue_2.split("'");
				for (String s : strFoldrValue) {
					log4j.info(s);
				}

				strFldSelValue_2 = strFoldrValue[1].substring(2);
				log4j.info(strFldSelValue_2);
				

				String strDocTitle = "AutoDocTxt" + System.currentTimeMillis();
				strTempDocName_1=strDocTitle;
				
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadTxtFile_OpenPath");
				
				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitle, strTempFoldr_1, false, strFldSelValue_1,
						strAutoFilePath, strAutoFileName, strUploadFilePath);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Repeat the above step to upload a .pdf and .HTML files.
			 * Uploaded documents are displayed under folder F1 on 'Document
			 * Library' screen.
			 */ 
			
			try {
				assertEquals("", strFuncResult);
				
				String strDocTitle = "AutoDocPdf" + System.currentTimeMillis();
				strTempDocName_2=strDocTitle;
				
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadPdfFile_OpenPath");
				
				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitle, strTempFoldr_1, false, strFldSelValue_1,
						strAutoFilePath, strAutoFileName, strUploadFilePath);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				
				String strDocTitle = "AutoDocHTML" + System.currentTimeMillis();
				strTempDocName_3=strDocTitle;
				
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadHTMLFile_OpenPath");
				
				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitle, strTempFoldr_2, false, strFldSelValue_2,
						strAutoFilePath, strAutoFileName, strUploadFilePath);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Expand folder F1, select "Move" button corresponding to file
			 * TextDoc "Move A Document" screen is displayed with folders F1 and
			 * F2 listed for the "Move Document To" field.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strOldFoldName = strTempFoldr_1;
				String strOldFldSelValue =  strFldSelValue_1;

				String strNewFoldName = strTempFoldr_2;
				String strNewFldSelValue = strFldSelValue_2;
				
				String strDocument = strTempDocName_1;
				
				strFuncResult = objDocumentLibrary.moveDocToFolder(selenium,
						strOldFoldName, strOldFldSelValue, strNewFoldName,
						strNewFldSelValue, false, strDocument);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 12 Expand folder F1, select "Delete" button corresponding to file
			 * PDFDoc A confirmation window is displayed.
			 */
			/*
			 * 13 Click on OK on the confirmation window On 'Document Library'
			 * screen,
			 * 
			 * 1. PDFDoc is not displayed under folder F1. 2. PDFDoc is NOT
			 * displayed under All Folders/F2.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFoldName = strTempFoldr_1;
				String strFldSelValue = strFldSelValue_1;
				String strDocument = strTempDocName_2;
				strFuncResult = objDocumentLibrary.deleteDocument(selenium,
						strFoldName, strFldSelValue, strDocument);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 14 Click on 'Rename a Folder' button on 'Document Library'
			 * screen. "Select Folder to Rename" screen is displayed.
			 */
			/*
			 * 15 Select folder F1 and click on 'Rename' "Edit Folder" screen is
			 * displayed
			 */
			/*
			 * 16 Edit the folder name form 'F1' to 'F1-edit' and save On
			 * 'Document Library' screen, the chnages are reflected for the
			 * folder.
			 */
			try {
				assertEquals("", strFuncResult);

				String strFoldName = "EDIT"+strTempFoldr_1;
				String strFoldDesc = "Dynamic folder renamed";
				String strFldSelValue = strFldSelValue_1;
				strFuncResult = objDocumentLibrary.renameFolder(selenium,
						strFoldName, strFoldDesc, strFldSelValue, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 17 Click on 'Delete Folder' button on 'Document Library' screen.
			 * "Delete Folder" screen is displayed.
			 */
			/*
			 * 18 Select folder F2 and select the checkbox
			 * "Delete documents too?" and click on Save A confirmation window
			 * is displayed.
			 */
			/*
			 * 19 Click on OK on the confirmation window. Folder F2 is not
			 * displayed on the 'Document Library' screen. On 'Document Library'
			 * screen, HTMLDoc is NOT displayed under All Folders/F2.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFoldName = strTempFoldr_2;
				String strFldSelValue = strFldSelValue_2;
				String[] strDocuments = {strTempDocName_3};
				strFuncResult = objDocumentLibrary.deleteAFolder(selenium,
						strFoldName, strFldSelValue, true, false, strDocuments);

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
			gstrTCID = "BQS-69268";
			gstrTO = "Verify that a user can be provided a role with"
					+ " right to Maintain Document Library.";
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
	public void testBQS69227() throws Exception {
		try {
			gstrTCID = "BQS-69227"; // Test Case Id
			gstrTO = "Verify that a user can be provided a role with right "
					+ "to generate Status Snapshot report.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();

			General objGen = new General();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();

			Roles objRole = new Roles();

			String strFuncResult = "";

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();
			

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strUsrRoleValue = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			;
			String strUpdPwd = "abc123";

			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = { { "43", "true" } };

			String[] strRlViewRtForUsr = new String[1];
			String[] strRlUpdRtForUsr = {};

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			strSTvalue[0] = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_" + gstrTCID + "_" + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatSnapRep_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strUpdMulValue = "";

			String strGenDate = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";

			String strLastUpdMST = "";

			String strCurYear = dts.getCurrentDate("yyyy");
			String strRegGenTime = "";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
						strAdmPassword);
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
						strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

			// Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUpdUsrName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

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
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource, false, false, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
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
			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(
						selenium, strResource, statTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strStatusName2,
						strStatusValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "1");
				strFuncResult = strArFunRes[1];
				strUpdMulValue = strArFunRes[0];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

				strFuncResult = strArFunRes[4];
				strLastUpdMST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// login as region admin

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
						strAdmPassword);
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRlViewRtForUsr[0] = strSTvalue[0];
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleForUser, strRoleRitsForUsr, strRlViewRtForUsr,
						true, strRlUpdRtForUsr, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUsrRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleForUser);

				if (strUsrRoleValue.compareTo("") != 0) {
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
			

				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strUsrRoleValue, true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
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
				Thread.sleep(60000);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
				"M/d/yyyy");
				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strCSTtime, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			int intCount = 0;
			int intCount1=0;
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
						{
								"Status Snapshot - " + strGenDate + " "
										+ strRegGenTime + " "+propEnvDetails.getProperty("TimeZone"), "", "", "",
								"", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, statTypeName,
								strUpdMulValue, "", strLastUpdMST } };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);
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
			gstrTCID = "BQS-69227";
			gstrTO = "Verify that a user can be provided a role with right" +
					" to generate Status Snapshot report.";
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


	

	
	/*******************************************************************************************
	'Description	:Verify that a user can be provided a role with right to generate Event Snapshot report. 
	'Precondition	:	Preconditions:

					1. Status types NST (Number), MST (Multi), TST (Text) and SST (Saturation Score) are created. 

					2. Status types NST (Number), MST (Multi), TST (Text) and SST (Saturation Score) are associated with resource type RT. 
					
					3. Resource RS is created under resource type RT. 
					
					4. Event template ET1 is created selecting resource type RT and status types NST, MST, TST, SST. 
					
					5. Event E1 is created under ET1 selecting resource RS. 
					
					6. Status Types NST, MST, TST and SST are updated on day D1 time hour H1 for resource RS. 
					
					7. User U1 is created with 'Run Reports' right on resource RS. 		
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-Aug-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS118733() throws Exception {

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
		General objGeneral = new General();
		EventSetup objEventSetup = new EventSetup();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "118733"; // Test Case Id
			gstrTO = "Verify that a user can be provided a role with "
					+ "right to generate Event Snapshot report. ";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strApplTime = "";
			// Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName = "NST" + strTimeText;
			String statTextTypeName = "TST" + strTimeText;
			String statMultiTypeName = "MST" + strTimeText;
			String statSaturatTypeName = "SST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// General variable
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserNameUpdate = "AutoUsr_Update"
					+ System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strFulUserNameUpdate = strUserNameUpdate;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strETValue = "";

			String strHr = "";
			String strEventValue = "";
			String strMin = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strTimeReport = "";

			String strLastUpdArr[] = {};
			String strUpdateDataHr = "";
			String strUpdateDataMin = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating TST
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
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating SST
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
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status Type Multi status type is created.
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
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
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

			// 1. Resource type RT

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
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[2] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[3] + "']");

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

			/* 2. Resources RS is created under RT. */

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
			 * 4. Event template ET1 is created selecting resource type RT and
			 * status types NST, MST, TST, SST
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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
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
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
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
				/*
				 * strHr = selenium
				 * .getValue("//select[@name='startHour']/option/@selected");
				 * System.out.println(strHr); strMin = selenium
				 * .getValue("//select[@name='startMinute']/option/@selected");
				 * System.out.println(strMin);
				 * selenium.click("css=input[value='Save']");
				 * selenium.waitForPageToLoad(gstrTimeOut);
				 */

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

			/*
			 * 6. User U1 has following rights:
			 * 
			 * a. Report - Event Snapshot b. No role is associated with User U1
			 * c. 'View Status' and 'Run Report' rights on resources RS.
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
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
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

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

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 3 Create a role 'R1' by selecting 'Report - Event Snapshot' right
			 * from 'Select the Rights for this Role' section. Role R1 is
			 * displayed in 'Roles List' page.
			 */

			try {
				assertEquals("", strFuncResult);

				strRoleName = "Role" + System.currentTimeMillis();
				strFuncResult = objRole.rolesMandatoryFlds(selenium,
						strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.savAndVerifyRoles(selenium, strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Setup>>Users and click on the 'Edit' link
			 * associated with user U1 and select the role R1 from 'User Type &
			 * Roles' section and save the page. 'Users List' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Any User

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameUpdate, strInitPwd, strConfirmPwd,
						strFulUserNameUpdate);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameUpdate, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
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
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserNameUpdate, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName,
						statNumTypeName, statSaturatTypeName, statTextTypeName, };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Number Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, statNumTypeName, strSTvalue[0], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Text Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource, statTextTypeName, strSTvalue[1], "1", "2");
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
						selenium, strResource, statSaturatTypeName,
						strSTvalue[2], strUpdateValue1, strUpdateValue2, "393",
						"429");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Multi Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.updateMultiStatusType_ChangeVal(
						selenium, strResource, statMultiTypeName,
						strSTvalue[3], strStatusName1, strStatusValue[0],
						strStatusName2, strStatusValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr = selenium.getText(
						"//span[text()='" + strStatusName1 + "']"
								+ "/following-sibling::span[@class='time'][1]")
						.split(" ");
				strLastUpdArr = strLastUpdArr[2].split(":");
				strUpdateDataHr = strLastUpdArr[0];
				strUpdateDataMin = strLastUpdArr[1];

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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
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

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
			 * 5 Login as user U1, navigate to Reports>>Event Reports, click on
			 * 'Event Snapshot'. 'Event Snapshot Report (Step 1 of 2)' screen is
			 * displayed with:
			 * 
			 * 1. Start Date field with calender widget 2. End Date field with
			 * calender widget 2. List of Event Templates
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToEventSnapshotReport(selenium);
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

				strFuncResult = objRep.selectHtmlOrExcelformatInEventSnapShotReport(
						selenium, strApplTime, strApplTime, strETValue,true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "m",
						"mm");
				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "H",
						"HH");

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);
				
				String str=strLastUpdArr[0]+":"+strLastUpdArr[1];
				str=dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr=str.split(":");
				
				String strCSTtime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTtime, strEventValue, strLastUpdArr[0],
						strLastUpdArr[1]);

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

				strLastUpdArr[0] = dts.converDateFormat(strLastUpdArr[0], "HH",
						"H");
				/*strLastUpdArr[1] = dts.converDateFormat(strLastUpdArr[1], "mm",
						"m");*/

				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");
				String str = "Event Snapshot for " + strEveName + " - "
						+ strApplTime + " " + strLastUpdArr[0] + ":"
						+ strLastUpdArr[1]
						+ " Central Standard Time. Event Start:" + " "
						+ strTime + " " + strHr + ":" + strMin
						+ ". Event End: " + strFutureDate + "" + " " + strHr
						+ ":" + strMin + ". ";

				String[][] strTestData = {
						{ str, "", "", "", "", "", "", "", "" },
						{ "Resource Type", "Resource", statMultiTypeName,
								statNumTypeName, statSaturatTypeName,
								statTextTypeName, "Comment", "Last Update",
								"By User" },
						{
								strResrctTypName,
								strResource,strStatusName1,
								"1",
								"393",
								"1",
								"",
								strTime + " " + strUpdateDataHr + ":"
										+ strUpdateDataMin, strUserNameUpdate } };

				strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
						strCSVDownlRenamedPath);
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
			gstrTCID = "118733";
			gstrTO = "Verify that a user can be provided a role with right"
					+ " to generate Event Snapshot report. ";
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
	'Description	: Verify that a user can be provided a role with 
	                  right to generate Status Summary report.
	'Arguments		:None
	'Returns		:None
	'Date			:21-08-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	
	@Test
	public void testBQS69184() throws Exception {
		try {
			gstrTCID = "BQS-69184"; // Test Case Id
			gstrTO = "Verify that a user can be provided a role with right to generate Status Summary report.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			CSVFunctions objCSV = new CSVFunctions();

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();

			Roles objRole = new Roles();

			String strFuncResult = "";

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login

			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String[] strRoleValue = new String[2];

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			strSTvalue[0] = "";
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_"
					+ gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_"
					+ gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String[][] strReportData = {
					{ "Status Summary", "**", "**", "**", "**" },
					{ "Status Type", "Resource", "Status", "Total Hours",
							"% of Total Hours" },
					{ statTypeName, strResource, strStatusName1, "\\d+\\.\\d+",
							"\\d+" },
					{ statTypeName, strResource, strStatusName2, "\\d+\\.\\d+",
							"\\d+" },
					{ "**", "**", "**", "**", "**" },
					{ "Aggregate Status Summary", "**", "**", "**", "**" },
					{ "Status Type", "Resource Type", "Status", "Total Hours",
							"% of Total Hours" },
					{ statTypeName, strResType, strStatusName1, "\\d+\\.\\d+",
							"\\d+" },
					{ statTypeName, strResType, strStatusName2, "\\d+\\.\\d+",
							"\\d+" } };

			String strRoleForUser = "AutoRL" + strTimeText;

			String[] strRlUpdRtForUsr = {};

			String strRoleUsrVal = "";
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strAdmUserName, strAdmPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResType, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);

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
			// create role to update

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalue, true, strSTvalue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);

				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Update user
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
						seleniumPrecondition, strUpdUsrName, strUpdPwd,
						strUpdPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUpdUsrName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user
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
						seleniumPrecondition, strUserName, strUsrPassword,
						strUsrPassword, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, false, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUpdUsrName, strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(
						seleniumPrecondition, strResource, statTypeName,
						strSTvalue[0], strStatusName1, strStatusValue[0],
						strStatusName2, strStatusValue[1]);
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
				strFuncResult = objLogin.login(seleniumPrecondition,
						strAdmUserName, strAdmPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// create role for user
			try {
				assertEquals("", strFuncResult);
				String strRoleRitsForUsr1[][] = { { "40", "true" } };
				strFuncResult = objRole.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleForUser,
						strRoleRitsForUsr1, strSTvalue, true, strRlUpdRtForUsr,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleForUser);

				if (strRoleUsrVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleUsrVal, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
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
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportDetAndGenReport(selenium,
						strRSValue[0], strSTvalue[0], strCurrDate, strCurrDate,
						true);
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
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportDetAndGenReport(selenium,
						strRSValue[0], strSTvalue[0], strCurrDate, strCurrDate,
						false);
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
				strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
						strReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource;
				strTestData[5] = statTypeName;
				strTestData[6] = strStatusName1 + "," + strStatusName2;
				strTestData[5] = "Check the Status Summary details in PDF file: "
						+ strPDFDownlPath;

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69184";
			gstrTO = "Verify that a user can be provided a role with right to generate Status Summary report. ";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/***************************************************************
	'Description	:Verify that a user can be provided a role with right to
	                  generate Status Reason Summary report. 
	'Arguments		:None
	'Returns		:None
	'Date			:21-08-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testBQS69195() throws Exception {
		try {
			gstrTCID = "BQS-69195"; // Test Case Id
			gstrTO = "Verify that a user can be provided a role with right to generate Status" +
					" Reason Summary report. ";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			CSVFunctions objCSV = new CSVFunctions();

			StatusReason objStatRes = new StatusReason();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();

			Roles objRole = new Roles();

			String strFuncResult = "";

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login

			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			
			String strUpdPwd = "abc123";

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			strSTvalue[0] = "";
			strRSValue[0] = "";
					    
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String CSVName = "StatReasSumRep_" + gstrTCID + "_" + strTimeText
					+ ".csv";
			
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleForUser = "AutoRL" + strTimeText;

			String[] strRlUpdRtForUsr = {};

			String strRoleUsrVal = "";

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strAdmUserName, strAdmPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.createStatusReasn(seleniumPrecondition, strReasonName,
								"", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResType, strContFName, strContLName, strState,
						strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);

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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strAdmUserName, strAdmPassword);
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
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//create role to update
									
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(
						selenium, strRoleName, strRoleRights,
						strSTvalue, true, strSTvalue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(
						selenium, strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			//Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
								
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						selenium, strUpdUsrName, strUpdPwd,
						strUpdPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						selenium, strResource, false, true, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						selenium, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUpdUsrName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						selenium, strUserName, strUsrPassword,
						strUsrPassword, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						selenium, strResource, false, false, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
							
			try {
				assertEquals("", strFuncResult);
			
				String[] strEventStatType = {};
				String[] strRoleStatType = {statTypeName};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateMultiStatusTypeWithReason(selenium,
							strResource, statTypeName, strSTvalue[0],
							strStatusName1,strStatusValue[0],strStatusName2,strStatusValue[1],strReasonVal,strReasonVal);
				
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try{
				assertEquals("", strFuncResult);
				String[] strArFunRes=new String[5];
				strArFunRes=objViewMap.getUpdSTValueInMap(selenium, "1");			
				strFuncResult=strArFunRes[1];			
				strUpdMulValue=strArFunRes[0];
											
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				if(strUpdMulValue.equals(strStatusName1)){
					strUpdValCheck=strStatusValue[0];
				}else{
					strUpdValCheck=strStatusValue[1];
				}
				
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
				strFuncResult = objLogin.login(selenium,strAdmUserName,
						strAdmPassword);
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
				strFuncResult = objRole.navRolesListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//create role for user
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRitsForUsr1={{"47","true"}};
				strFuncResult = objRole.CreateRoleWithAllFields(selenium, strRoleForUser, strRoleRitsForUsr1, strSTvalue, true, strRlUpdRtForUsr, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(selenium, strRoleForUser);
				
				if(strRoleUsrVal.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch Role value";
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
				
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
									
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleUsrVal, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
								
				strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName);
				
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
				strFuncResult = objLogin.newUsrLogin(selenium,strUserName,
						strUsrPassword);
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
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(selenium, strRSValue[0], statTypeName,strCurrDate, strCurrDate, true, strUpdValCheck, strReasonVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strPDFDownlPath};
				
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
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(selenium, strRSValue[0], statTypeName,strCurrDate, strCurrDate, false, strUpdValCheck, strReasonVal);
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
			try {
				assertEquals("", strFuncResult);
				File fileCSV = new File(strCSVDownlPath);
				File filePDF = new File(strPDFDownlPath);

				if (fileCSV.exists() && filePDF.exists()) {
					gstrResult = "PASS";

					String[] strTestData1 = {propEnvDetails.getProperty("Build"),
					gstrTCID,
					strUserName+"/"+strUsrPassword,
					strResType,
					strResource,
					statTypeName,
					strUpdMulValue,
					"Check the Status Summary details in PDF file:" +
					" "+strPDFDownlPath+"status reason: "+strReasonName};

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");

					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Status_Detail_Report");

					String strTestData2[] = {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strResource, strUpdMulValue,strReasonName,CSVName,
							"Status Detail Report need to be checked in CSV file" };

					strWriteFilePath = pathProps
							.getProperty("WriteResultPath");

					objOFC.writeResultData(strTestData2, strWriteFilePath,
							"Status_Detail_Report");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}															
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69195";
			gstrTO = "Verify that a user can be provided a role with right to generate Status Reason Summary report. ";
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
	'Description		:Verify that a user can be provided a role with right to generate Status Reason Detail report. 
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:21-08-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	
	@Test
	public void testBQS69225() throws Exception {
		try {
			gstrTCID = "BQS-69225"; // Test Case Id
			gstrTO = "Verify that a user can be provided a role with right to generate Status Reason Detail report. ";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			CSVFunctions objCSV = new CSVFunctions();

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			General objGen = new General();
			StatusReason objStatRes = new StatusReason();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();

			Roles objRole = new Roles();

			String strFuncResult = "";

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login

			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";

			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();

			String strUpdPwd = "abc123";
	
			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			strSTvalue[0] = "";
			strRSValue[0] = "";

			String strGenDate = "";
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlViewRtForUsr = {};

			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";
			String strRegGenTime = "";
			String strCurYear = dts.getCurrentDate("yyyy");
			String strLastUpdDate = "";
			String strLastUpdTime = "";
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION Starts~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
						strAdmPassword);
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
				strFuncResult = objStatRes.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
								strStatusTypeValue, statTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName2);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
			// create role to update

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

			// Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUpdUsrName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource, false, false, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
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
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~Test case" + gstrTCID + " EXECUTION starts~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusTypeWithReason(
						selenium, strResource, statTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strStatusName2,
						strStatusValue[1], strReasonVal, strReasonVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "1");
				strFuncResult = strArFunRes[1];
				strUpdMulValue = strArFunRes[0];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

				strLastUpdTime = strArFunRes[2] + ":" + strArFunRes[3];

				strLastUpdDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"dd-MMM-yyyy");
				strFuncResult = strArFunRes[4];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (strUpdMulValue.equals(strStatusName1)) {
					strUpdValCheck = strStatusValue[0];
				} else {
					strUpdValCheck = strStatusValue[1];
				}

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
				strFuncResult = objLogin.login(selenium, strAdmUserName,
						strAdmPassword);
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
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// create role for user
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRightsUsr = { { "29", "true" } };
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleForUser, strRoleRightsUsr, strSTvalue, true,
						strRlUpdRtForUsr, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(selenium,
						strRoleForUser);

				if (strRoleUsrVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleUsrVal, true);

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
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
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
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
				strFuncResult = objRep.enterRepDetailsAndGenStatusReasonDetRep(
						selenium, strRSValue[0], statTypeName, strCSTTime,
						strCSTTime, true, strUpdValCheck, strReasonVal);
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
				strFuncResult = objRep.navToStatusReasonDetail(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

				strFuncResult = objRep.enterRepDetailsAndGenStatusReasonDetRep(
						selenium, strRSValue[0], statTypeName, strCSTTime,
						strCSTTime, false, strUpdValCheck, strReasonVal);
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
				selenium.selectWindow("");
				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"dd-MMM-yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{ "Resource", "Status", "Status Reason",
								"Status Start Date", "Status End Date",
								"Duration ", "User", "IP", "Trace", "Comments" },

						{ strResource, strUpdMulValue, strReasonName,
								strLastUpdDate + " " + strLastUpdTime,
								strGenDate + " " + strRegGenTime,
								"\\d+\\.\\d+", strUpdUsrName, propEnvDetails.getProperty("ExternalIP"),
								"**", strReasonName + " " }, };

				strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
						strReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data

				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource;
				strTestData[5] = statTypeName;
				strTestData[6] = strUpdMulValue;
				strTestData[5] = "Check the Status Reason details in PDF file: "
						+ strPDFDownlPath
						+ "Start Date: "
						+ strLastUpdDate
						+ " "
						+ strLastUpdTime
						+ "End Date: "
						+ strGenDate
						+ " "
						+ strRegGenTime
						+ "Status Reason: "
						+ strReasonName + "IP: " + propEnvDetails.getProperty("ExternalIP");

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC
						.writeResultData(strTestData, strWriteFilePath,
								"Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69225";
			gstrTO = "Verify that a user can be provided a role with right" +
					" to generate Status Reason Detail report. ";
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

	/*******************************************************************************************
	'Description	:Verify that a user can be provided a role with right to generate Status Detail report.
	'Precondition	:1. Status type 'ST' (Multi status type) is created selecting role 'R1' under both view and update right on the status type.
					2. Statuses S1 and S2 are created under Status type ST.
					3. Status type ST is associated with resource type RT.
					4. Resource RS is created under resource type RT.
					5. Status type ST is updated with statuses S1 or S2 on day D1. 

	'Arguments		:None
	'Returns		:None
	'Date	 		:31-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS69194() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		CSVFunctions objCSV=new CSVFunctions();
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
			gstrTCID = "BQS-69194"; // Test Case Id
			gstrTO = "Verify that a user can be provided a role with " +
					"right to generate Status Detail report.";
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
			
			
				
			String statTypeName="MST"+strTimeText;
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
			
			String strUserNameUpdate = "AutoUsr_Updat" + System.currentTimeMillis();
			String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameUpdate = strUserNameUpdate;
			String strUsrFulName1 = strUserName1;
			
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
			
			String strRoleName1 = "AutoR_1" + strTimeText;
			String strRoleValue1 = "";
			
			
		
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

			String CSVName = "StatSummary_" + gstrTCID + "_" + strTimeText
					+ ".csv";
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
			 * 1. Status type 'ST' (Multi status type) is created selecting role
			 * 'R1' under both view and update right on the status type.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Statuses S1 and S2 are created under Status type ST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, statTypeName, strStatusName1);
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
						selenium, statTypeName, strStatusName2);
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

				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*3. Status type ST is associated with resource type RT. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*4. Resource RS is created under resource type RT.*/ 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			
			//Update user
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameUpdate,
								strInitPwd, strConfirmPwd, strUsrFulNameUpdate);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameUpdate, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*6. User U1 is created with 'Run Reports' right on resource RS. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);

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
			
			
			
			/*5. Status type ST is updated with statuses S1 or S2 on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameUpdate,
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
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strUpdateResult[] = objStatusTypes
						.updateMultiStatusType_ChangeValWithTime(selenium,
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

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
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

			
			/*
			 * 3 Create a role 'R1' by selecting 'Report - Status Detail' right
			 * from 'Select the Rights for this Role' section. Role R1 is
			 * displayed in 'Roles List' page.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.rolesMandatoryFlds(selenium, strRoleName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(selenium, strRoleName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue1 = objRole.fetchRoleValueInRoleList(selenium,
						strRoleName1);

				if (strRoleValue1.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 4 Navigate to Setup>>Users and click on the 'Edit' link
			 * associated with user U1 and select the role R1 from 'User Type &
			 * Roles' section and save the page. 'Users List' page is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.navUserListPge(selenium);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.slectAndDeselectRole(selenium, strRoleValue1, true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			/*
			 * 5 Login as User U1, navigate to Reports>>Status Reports, click on
			 * 'Status Detail'. 'Status Detail Report (Step 1 of 2)' screen is
			 * displayed.
			 * 
			 * (by default Adobe Acrobat (PDF) is selected).
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
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
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.checkPDFIsSelectedByDefault(selenium);
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
				
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strCSTApplTime,
								strCSTApplTime, true, statTypeName,strStatusValue[0], "HH:mm:ss");

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
				System.out.println("CSV generation time "+strReportGenrtTimePDF);

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
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strCSTApplTime,
								strCSTApplTime, false, statTypeName,
								strStatusValue[0], "HH:mm:ss");
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

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);
				try {
					assertEquals("", strFuncResult);
					File fileCSV = new File(strCSVDownlPath);
					File filePDF = new File(strPDFDownlPath);

					if (fileCSV.exists() && filePDF.exists()) {
						gstrResult = "PASS";

						String[] strTestData = {
								propEnvDetails.getProperty("Build"), gstrTCID,
								strResource, strUpdatdVale,
								strCurrDate + " " + strUpdatedDate,
								strCurrDate + " " + strReportGenrtTimePDF,
								strDurationDiffPDF,
								strUserNameUpdate + "," + strUserName1, propEnvDetails.getProperty("ExternalIP"), "",
								PDFName,
								"Status Detail Report need to be checked in PDF file" };

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");

						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Status_Detail_Report");

						String strTestData2[] = {
								propEnvDetails.getProperty("Build"), gstrTCID,
								strResource, strUpdatdVale,
								strCurrDate + " " + strDurationUpdat,
								strCurrDate + " " + strDurationGenerat,
								strDurationDiff,
								strUserNameUpdate + "," + strUserName1, propEnvDetails.getProperty("ExternalIP"), "",
								CSVName,
								"Status Detail Report need to be checked in CSV file" };

						strWriteFilePath = pathProps
								.getProperty("WriteResultPath");

						objOFC.writeResultData(strTestData2, strWriteFilePath,
								"Status_Detail_Report");
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69194";
			gstrTO = "Verify that a user can be provided a role with right to generate Status Detail report.";
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
