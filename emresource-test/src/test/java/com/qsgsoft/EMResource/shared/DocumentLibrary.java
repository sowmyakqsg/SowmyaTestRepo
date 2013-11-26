package com.qsgsoft.EMResource.shared;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;
public class DocumentLibrary {

	static Logger log4j = Logger
	.getLogger("com.qsgsoft.EMResource.shared.DocumentLibrary");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;

	/***********************************************************************
	'Description	:Navigate to document library section
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String navToDocumentLibrary(Selenium selenium){
		String strReason="";
		
		try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//mouse hover on Regional Info Tab
			selenium.mouseOver(propElementDetails.getProperty("RegionalInfo"));
			
			//Click on Document Library link
			selenium.click(propElementDetails.getProperty("RegionalInfo.DocumentLibrary"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Document Library", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Document Library page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Document Library page is NOT displayed" + Ae;
				log4j.info("Document Library page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("navToDocumentLibrary function failed" + e);
			strReason = "navToDocumentLibrary function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	 'Description :navigate to Rename a folder
	 'Precondition :None
	 'Arguments  :selenium,strFoldName,strFoldDesc,strFldSelValue,blnAllFolders
	 'Returns  :strReason
	 'Date    :21-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	  
	public String navRenameFolderPge(Selenium selenium) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on Rename a Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.RenameAFolder"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Select Folder to Rename", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			try {
				assertEquals("Select Folder to Rename", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Select Folder to Rename page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Select Folder to Rename page is NOT displayed"
						+ Ae;
				log4j.info("Select Folder to Rename page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("navRenameFolderPge function failed" + e);
			strReason = "navRenameFolderPge function failed" + e;
		}
		return strReason;
	}
	
	/***********************
	 'Description :Rename a folder new 
	 'Precondition :None
	 'Arguments  :selenium,strFoldName,strFoldDesc,strFldSelValue,blnAllFolders
	 'Returns  :strReason,blnSave
	 'Date    :13-July-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	  
	public String renameFolderFrmSelectFoldrPge(Selenium selenium,
			String strFoldName, String strFoldDesc, String strFldSelValue,
			boolean blnAllFolders, boolean blnSave) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnAllFolders) {
				// select All Folders
				selenium.click("css=input[name=chosenFolderID][value='0']");
			} else {
				// select the particular folder for 'Create In'
				selenium.click("css=input[name=chosenFolderID][value='"
						+ strFldSelValue + "']");
			}
			// click on rename
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.RenameAFolder.Rename"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Edit Folder", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			try {
				assertEquals("Edit Folder", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Edit Folder page is displayed");

				// Enter Folder name and description
				selenium
						.type(propElementDetails
								.getProperty("DocumentLibrary.FolderName"),
								strFoldName);
				selenium.type(propElementDetails
						.getProperty("DocumentLibrary.FolderDescr"),
						strFoldDesc);

				if (blnSave) {
					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try{
							assertEquals("Document Library", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						}
						catch(Exception e){
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);

					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFoldName + "']"));
						log4j.info("Updated Folder " + strFoldName
								+ " is displayed on 'Document Library' screen");
					} catch (AssertionError Ae) {
						strReason = "Updated Folder "
								+ strFoldName
								+ " is NOT displayed on 'Document Library' screen";
						log4j
								.info("Updated Folder "
										+ strFoldName
										+ " is NOT displayed on 'Document Library' screen");
					}

				}

			} catch (AssertionError Ae) {

				strReason = "Edit Folder is NOT displayed" + Ae;
				log4j.info("Edit Folder page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("renameFolderFrmSelectFoldrPge function failed" + e);
			strReason = "renameFolderFrmSelectFoldrPge function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Create a new folder
	'Precondition	:None
	'Arguments		:selenium,strFolderName,strFldDesc,blnAllFolders,strFldSelValue
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String createNewFolder(Selenium selenium, String strFolderName,
			String strFldDesc, boolean blnAllFolders, String strFldSelValue) {

		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			// click on create New Folder
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("DocumentLibrary.CreateNewFolder")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.CreateNewFolder"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt=0;
			do{
				try{
					assertEquals("Create New Folder", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Create New Folder", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New Folder page is displayed");

				selenium.type(propElementDetails
						.getProperty("DocumentLibrary.FolderName"),
						strFolderName);
				selenium
						.type(propElementDetails
								.getProperty("DocumentLibrary.FolderDescr"),
								strFldDesc);

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strFldSelValue + "']");
				}

				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				if (blnAllFolders) {
					
					intCnt=0;
					do{
						try{
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
											+ strFolderName + "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						}
						catch(Exception e){
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFolderName + "']"));
						log4j
								.info("Folder "
										+ strFolderName
										+ " is displayed on 'Document Library' screen under All folders section. ");
					} catch (AssertionError Ae) {
						strReason = "Folder "
								+ strFolderName
								+ " is NOT displayed on 'Document Library' screen under All folders section. ";
						log4j
								.info("Folder "
										+ strFolderName
										+ " is NOT displayed on 'Document Library' screen under All folders section.  ");
					}
				} else {
					intCnt=0;
					do{
						try{
							assertTrue(selenium.isElementPresent("//div[@id='F-"
									+ strFldSelValue + "']/a/span[text()='"
									+ strFolderName + "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						}
						catch(Exception e){
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium.isElementPresent("//div[@id='F-"
								+ strFldSelValue + "']/a/span[text()='"
								+ strFolderName + "']"));
						log4j
								.info("Folder "
										+ strFolderName
										+ " is displayed on 'Document Library' screen under Another folder");
					} catch (AssertionError Ae) {
						strReason = "Folder "
								+ strFolderName
								+ " is NOT displayed on 'Document Library' screen Another folder";
						log4j
								.info("Folder "
										+ strFolderName
										+ " is NOT displayed on 'Document Library' screen Another folder");
					}

				}

			} catch (AssertionError Ae) {

				strReason = "Create New Folder page is NOT displayed" + Ae;
				log4j.info("Create New Folder page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("createNewFolder function failed" + e);

			strReason = "createNewFolder function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Add a new document to the folder
	'Precondition	:None
	'Arguments		:selenium,strDocTitle,strFolderName,blnAllFolders,strFldSelValue,
					strAutoFilePath,strAutoFileName,strUploadFilePath
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String addNewDocument(Selenium selenium, String strDocTitle,
			String strFolderName, boolean blnAllFolders, String strFldSelValue,
			String strAutoFilePath, String strAutoFileName,
			String strUploadFilePath) {

		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			// click on create New Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.AddNewDocument"));
			selenium.waitForPageToLoad(gstrTimeOut);

			
			int intCnt=0;
			do{
				try{
					assertEquals("Add A Document", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Add A Document", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Add A Document page is displayed");
				// enter document title
				selenium.type(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Title"),
						strDocTitle);

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strFldSelValue + "']");
				}
				

				String strArgs[] = { strAutoFilePath, strUploadFilePath };
				// Auto it to upload the file
				Runtime.getRuntime().exec(strArgs);
				selenium.windowFocus();
				// click on Browse
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Browse"));
				// wait for pop up to appear
				Thread.sleep(5000);
				// Wait for autoit file to finish execution
				String strProcess = "";

				intCnt = 0;
				do {
					
					
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 150);

				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				
				intCnt=0;
				do{
					try{
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFolderName + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				try {
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
									+ strFolderName + "']"));
					// click on folder name
					selenium
							.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
									+ strFolderName + "']");

					
					try {
						assertTrue(selenium.isElementPresent("//div[@id='F-"
								+ strFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocTitle + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='F-"
										+ strFldSelValue
										+ "']/table/tbody/tr/td[3]/a/span[text()='"
										+ strDocTitle
										+ "']/ancestor::tr/td[2]/form/input[@value='Move']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='F-"
										+ strFldSelValue
										+ "']/table/tbody/tr/td[3]/a/span[text()='"
										+ strDocTitle
										+ "']/ancestor::tr/td[1]/form/input[@value='Delete']"));
						log4j
								.info(strDocTitle
										+ " is displayed on 'Document Library' screen with" +
												" Delete and Move buttons.");
					} catch (AssertionError Ae) {
						strReason = strDocTitle
								+ " is NOT displayed on 'Document Library' screen with " +
										"Delete and Move buttons.";
						log4j
								.info(strDocTitle
										+ " is NOT displayed on 'Document Library' screen" +
												" with Delete and Move buttons.");
					}

				} catch (AssertionError Ae) {
					strReason = "Folder " + strFolderName
							+ " is NOT displayed on 'Document Library' screen";
					log4j.info("Folder " + strFolderName
							+ " is NOT displayed on 'Document Library' screen");
				}

			} catch (AssertionError Ae) {

				strReason = "Add A Document page is NOT displayed" + Ae;
				log4j.info("Add A Document page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("addNewDocument function failed" + e);
			strReason = "addNewDocument function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Move documents from one folder to other
	'Precondition	:None
	'Arguments		:selenium,strOldFoldName,strOldFldSelValue,strNewFoldName,
					strNewFldSelValue,blnAllFolders,strDocument
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String moveDocToFolder(Selenium selenium,String strOldFoldName,String strOldFldSelValue,
			String strNewFoldName,String strNewFldSelValue,boolean blnAllFolders,String strDocument){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"+strOldFoldName+"']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
		
			//click on folder name
			selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strOldFoldName+"']");
			
			//click on move button for particular document
			selenium.click("//div[@id='F-"+strOldFldSelValue+"']/table/tbody/tr/td[3]/a/span[text()='"+strDocument+"']/ancestor::tr/td[2]/form/input[@value='Move']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Move A Document", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Move A Document", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Move A Document page is displayed");			
				
				if(blnAllFolders){
					//select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				}else{
					//select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"+strNewFldSelValue+"']");
				}
				
				//click on save
				selenium.click(propElementDetails
									.getProperty("DocumentLibrary.MovDocToFold.Move"));					
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt=0;
				do{
					try{
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				try{
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					//click on folder name
					selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strOldFoldName+"']");
					
					try {
						assertFalse(selenium.isElementPresent("//div[@id='F-"
								+ strOldFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j
								.info(strDocument
										+ " is not displayed on 'Document Library' screen under Folder "
										+ strOldFoldName);
					} catch (AssertionError Ae) {
						strReason = strDocument
								+ " is still displayed on 'Document Library' screen under Folder "
								+ strOldFoldName;
						log4j
								.info(strDocument
										+ " is still displayed on 'Document Library' screen under Folder "
										+ strOldFoldName);
					}

					// click on folder name
					selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strNewFoldName+"']");

					try {
						assertTrue(selenium.isElementPresent("//div[@id='F-"
								+ strNewFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j
								.info(strDocument
										+ " is displayed on 'Document Library' screen under Folder "
										+ strNewFoldName);
					} catch (AssertionError Ae) {
						strReason = strDocument
								+ " is NOT displayed on 'Document Library' screen under Folder "
								+ strNewFoldName;
						log4j
								.info(strDocument
										+ " is NOT displayed on 'Document Library' screen under Folder "
										+ strNewFoldName);
					}

				} catch (AssertionError Ae) {
					strReason = "'Document Library' screen is NOT displayed";
					log4j.info("'Document Library' screen is NOT displayed" );
				}
				
			} catch (AssertionError Ae) {

				strReason = "Move A Document page is NOT displayed" + Ae;
				log4j.info("Move A Document page is NOT displayed" + Ae);
			}
		
			
		} catch (Exception e) {
			log4j.info("moveDocToFolder function failed" + e);
			strReason = "moveDocToFolder function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Delete document from a folder
	'Precondition	:None
	'Arguments		:selenium,strFoldName,strFldSelValue,strDocument
	'Returns		:strReason
	'Date	 		:21-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String deleteDocument(Selenium selenium,String strFoldName,String strFldSelValue,String strDocument){
	String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"+strFoldName+"']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			//click on folder name
			selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strFoldName+"']");
			
			//click on delete button for particular document
			selenium.click("//div[@id='F-" + strFldSelValue
					+ "']/table/tbody/tr/td[3]/a/span[text()='" + strDocument
					+ "']/ancestor::tr/td[1]/form/input[@value='Delete']");
			
			try {
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you absolutely sure that you want " +
								"to delete this document[\\s\\S] It will" +
								" remove the document from the entire region," +
								" not just your view\\.$"));
				log4j.info("A confirmation window is displayed.");

				selenium.waitForPageToLoad(gstrTimeOut);
				//click on folder name
				selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strFoldName+"']");

				try {
					assertFalse(selenium.isElementPresent("//div[@id='F-"
							+ strFldSelValue
							+ "']/table/tbody/tr/td[3]/a/span[text()='"
							+ strDocument + "']"));
					log4j
							.info(strDocument
									+ " is not displayed on 'Document Library' screen under Folder "
									+ strFoldName);
				} catch (AssertionError Ae) {
					strReason = strDocument
							+ " is still displayed on 'Document Library' screen under Folder "
							+ strFoldName;
					log4j
							.info(strDocument
									+ " is still displayed on 'Document Library' screen under Folder "
									+ strFoldName);
				}
			
				try {
					assertFalse(selenium
							.isElementPresent("//span[text()='Uncategorized Documents']" +
									"/following-sibling::table/tbody/tr/td/a/span[text()='"
									+ strDocument + "']"));
					log4j.info("Document " + strDocument
							+ " is not displayed under All Folders");
				} catch (AssertionError Ae) {
					strReason = strReason + " Document " + strDocument
							+ " is still displayed under All Folders";
					log4j.info("Document " + strDocument
							+ " is still displayed under All Folders");
				}

			} catch (AssertionError Ae) {

				strReason = "A confirmation window is NOT displayed." + Ae;
				log4j.info("A confirmation window is NOT displayed.");			
			}
		
			
		} catch (Exception e) {
			log4j.info("deleteDocument function failed" + e);
			strReason = "deleteDocument function failed" + e;
		}
		return strReason;
	}
	

	/***********************************************************************
	'Description	:Rename a folder
	'Precondition	:None
	'Arguments		:selenium,strFoldName,strFoldDesc,strFldSelValue,blnAllFolders
	'Returns		:strReason
	'Date	 		:21-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String renameFolder(Selenium selenium,String strFoldName,String strFoldDesc,String strFldSelValue,boolean blnAllFolders){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("DocumentLibrary.RenameAFolder")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			//click on Rename a Folder
			selenium.click(propElementDetails.getProperty("DocumentLibrary.RenameAFolder"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Select Folder to Rename", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
									
			try {
				assertEquals("Select Folder to Rename", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Select Folder to Rename page is displayed");				
				
				if(blnAllFolders){
					//select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				}else{
					//select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"+strFldSelValue+"']");
				}
				//click on rename
				selenium.click(propElementDetails.getProperty("DocumentLibrary.RenameAFolder.Rename"));				
				selenium.waitForPageToLoad(gstrTimeOut);
				
				
				intCnt=0;
				do{
					try{
						assertEquals("Edit Folder", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				
				try {
					assertEquals("Edit Folder", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Edit Folder page is displayed");	
					
					//Enter Folder name and description
					selenium.type(propElementDetails.getProperty("DocumentLibrary.FolderName"), strFoldName);
					selenium.type(propElementDetails.getProperty("DocumentLibrary.FolderDescr"), strFoldDesc);
					
					//click on save
					selenium.click(propElementDetails.getProperty("Save"));					
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try{
							assertEquals("Document Library", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						}
						catch(Exception e){
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					

					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFoldName + "']"));
						log4j.info("Updated Folder " + strFoldName
								+ " is displayed on 'Document Library' screen");
					} catch (AssertionError Ae) {
						strReason = "Updated Folder "
								+ strFoldName
								+ " is NOT displayed on 'Document Library' screen";
						log4j
								.info("Updated Folder "
										+ strFoldName
										+ " is NOT displayed on 'Document Library' screen");
					}
					
				} catch (AssertionError Ae) {

					strReason = "Edit Folder is NOT displayed" + Ae;
					log4j.info("Edit Folder page is NOT displayed");		
				}
				
								
			} catch (AssertionError Ae) {

				strReason = "Select Folder to Rename page is NOT displayed" + Ae;
				log4j.info("Select Folder to Rename page is NOT displayed");		
			}
		
			
		} catch (Exception e) {
			log4j.info("renameFolder function failed" + e);
			strReason = "renameFolder function failed" + e;
		}
		return strReason;
	}
	

	/***********************************************************************
	'Description	:Delete a folder
	'Precondition	:None
	'Arguments		:selenium,strFoldName,strFldSelValue,blnDeleteDocs,blnAllFolders,strDocuments
	'Returns		:strReason
	'Date	 		:21-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String deleteAFolder(Selenium selenium, String strFoldName,
			String strFldSelValue, boolean blnDeleteDocs,
			boolean blnAllFolders, String strDocuments[]) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("DocumentLibrary.DeleteAFolder")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on DELETE Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.DeleteAFolder"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Delete Folder", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Delete Folder", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Delete Folder page is displayed");

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strFldSelValue + "']");
				}
				if (blnDeleteDocs) {
					// select Delete Documents
					selenium
							.click(propElementDetails
									.getProperty("DocumentLibrary.DeleteAFolder.DeleteDocums"));
				}
				// click on rename
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.DeleteAFolder.Delete"));

				try {
					assertTrue(selenium
							.getConfirmation()
							.matches(
									"^Are you sure that you want to "
											+ "delete this folder[\\s\\S] If it contains documents, they will be"
											+ " removed from the entire region, not just your view\\.$"));
					log4j.info("A confirmation window is displayed.");

					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					do {
						try {
							assertEquals("Document Library", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCnt++;
						} catch (Exception e) {
							Thread.sleep(1000);
							intCnt++;
						}
					} while (intCnt < 60);

					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFoldName + "']"));
						log4j
								.info("Folder "
										+ strFoldName
										+ " is not displayed on 'Document Library' screen");

						if (blnDeleteDocs) {
							for (int intRec = 0; intRec < strDocuments.length; intRec++) {
								try {
									assertFalse(selenium
											.isElementPresent("//span[text()='Uncategorized Documents']/following-sibling::table/tbody/tr/td/a/span[text()='"
													+ strDocuments[intRec]
													+ "']"));
									log4j
											.info("Document "
													+ strDocuments[intRec]
													+ " is not displayed under All Folders");
								} catch (AssertionError Ae) {
									strReason = strReason
											+ " Document "
											+ strDocuments[intRec]
											+ " is still displayed under All Folders";
									log4j
											.info("Document "
													+ strDocuments[intRec]
													+ " is still displayed under All Folders");
								}
							}
						}
					} catch (AssertionError Ae) {
						strReason = "Folder "
								+ strFoldName
								+ " is still displayed on 'Document Library' screen";
						log4j
								.info("Folder "
										+ strFoldName
										+ " is still displayed on 'Document Library' screen");
					}

				} catch (AssertionError Ae) {

					strReason = "A confirmation window is NOT displayed." + Ae;
					log4j.info("A confirmation window is NOT displayed.");
				}

			} catch (AssertionError Ae) {

				strReason = "Delete Folder page is NOT displayed" + Ae;
				log4j.info("Delete Folder to Rename page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("deleteAFolder function failed" + e);
			strReason = "deleteAFolder function failed" + e;
		}
		return strReason;
	}
	
	 /********************************************************************************************************
	  'Description :Fetch Folder Value in Status Type List page
	  'Precondition :None
	  'Arguments  :selenium,strFolderName
	  'Returns  :String
	  'Date    :13-July-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/
	public String fetchFolderValueInAllFolderList(Selenium selenium,
			String strFolderName)
			throws Exception {
		String strStatValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			strStatValue = selenium
					.getAttribute("//div[@id='mainContainer']/div[2]/a/span[text()='"
							+ strFolderName + "']/parent::a@onclick");
			log4j.info(strStatValue);

			String strFoldrValue[] = strStatValue.split("'");
			for (String s : strFoldrValue) {
				log4j.info(s);
			}

			strStatValue = strFoldrValue[1].substring(2);
			log4j.info(strStatValue);

			log4j.info("Folder Value is " + strStatValue);
		} catch (Exception e) {
			log4j.info("fetchFolderValue function failed" + e);
			strStatValue = "";
		}
		return strStatValue;
	}
	
	/***********************************************************************
	'Description	:Create a new folder New 
	'Precondition	:None
	'Arguments		:selenium,strFolderName,strFldDesc,blnAllFolders,strFldSelValue
	'Returns		:strReason
	'Date	 		:13-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String createNewFolderNew(Selenium selenium, String strFolderName,
			String strFldDesc, boolean blnAllFolders, String strFldSelValue,
			String strParentFolder, boolean blnSave) {

		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("DocumentLibrary.CreateNewFolder")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on create New Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.CreateNewFolder"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Create New Folder", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Create New Folder", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New Folder page is displayed");

				selenium.type(propElementDetails
						.getProperty("DocumentLibrary.FolderName"),
						strFolderName);
				selenium
						.type(propElementDetails
								.getProperty("DocumentLibrary.FolderDescr"),
								strFldDesc);

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strFldSelValue + "']");
				}

				if (blnSave) {

					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					if (blnAllFolders) {
						intCnt = 0;
						do {
							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
												+ strFolderName + "']"));
								break;
							} catch (AssertionError Ae) {
								Thread.sleep(1000);
								intCnt++;
							} catch (Exception e) {
								Thread.sleep(1000);
								intCnt++;
							}
						} while (intCnt < 60);

						try {
							assertEquals("Document Library", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
											+ strFolderName + "']"));
							log4j
									.info("Folder "
											+ strFolderName
											+ " is displayed on 'Document Library' screen");
						} catch (AssertionError Ae) {
							strReason = "Folder "
									+ strFolderName
									+ " is NOT displayed on 'Document Library' screen ";
							log4j
									.info("Folder "
											+ strFolderName
											+ " is NOT displayed on 'Document Library' screen ");
						}

					} else {

						intCnt = 0;
						do {
							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='F-"
												+ strFldSelValue
												+ "']/a/span[text()='"
												+ strFolderName + "']"));
								break;
							} catch (AssertionError Ae) {
								Thread.sleep(1000);
								intCnt++;
							} catch (Exception e) {
								Thread.sleep(1000);
								intCnt++;
							}
						} while (intCnt < 60);

						try {
							assertEquals("Document Library", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							assertTrue(selenium
									.isElementPresent("//div[@id='F-"
											+ strFldSelValue
											+ "']/a/span[text()='"
											+ strFolderName + "']"));
							log4j
									.info("Folder "
											+ strFolderName
											+ " is displayed on 'Document Library' screen under "
											+ strParentFolder);
						} catch (AssertionError Ae) {
							strReason = "Folder "
									+ strFolderName
									+ " is NOT displayed on 'Document Library' screen under "
									+ strParentFolder;
							log4j
									.info("Folder "
											+ strFolderName
											+ " is NOT displayed on 'Document Library' screen under"
											+ strParentFolder);
						}

					}

				}

			} catch (AssertionError Ae) {

				strReason = "Create New Folder page is NOT displayed" + Ae;
				log4j.info("Create New Folder page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("createNewFolderNew function failed" + e);

			strReason = "createNewFolderNew function failed" + e;
		}
		return strReason;
	}
	
	 /********************************************************************************************************
	  'Description :Fetch Folder Value in Status Type List page
	  'Precondition :None
	  'Arguments  :selenium,strFolderName
	  'Returns  :String
	  'Date    :13-July-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/
	public String fetchFolderValueGeneral(Selenium selenium,
			String strFolderName,
			String strFldrDescription) throws Exception {
		String strStatValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			strStatValue = selenium.getAttribute("//span[@title='"
					+ strFldrDescription + "'][text()='" + strFolderName
					+ "']/parent::a@onclick");
			System.out.println(strStatValue);

			String strFoldrValue[] = strStatValue.split("'");
			for (String s : strFoldrValue) {
				System.out.println(s);
			}

			strStatValue = strFoldrValue[1].substring(2);
			System.out.println(strStatValue);

			System.out.println("Folder Value is " + strStatValue);
		} catch (Exception e) {
			log4j.info("fetchFolderValue function failed" + e);
		}
		return strStatValue;
	}
	
	/***********************************************************************
	'Description	:Navigate to document library section
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:13-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String navToBackToDocumentLibrary(Selenium selenium) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("RegionalInfo.DocumentLibrary.Cancel")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			
			
			// Click on Document Library link
			selenium.click(propElementDetails
					.getProperty("RegionalInfo.DocumentLibrary.Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			 intCnt=0;
			do{
				try{
					assertEquals("Document Library", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			try {
				assertEquals("Document Library", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Document Library page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Document Library page is NOT displayed" + Ae;
				log4j.info("Document Library page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToBackToDocumentLibrary function failed" + e);
			strReason = "navToBackToDocumentLibrary function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	 'Description :Add a new document to the folder
	 'Precondition :None
	 'Arguments  :selenium,strDocTitle,strFolderName,blnAllFolders,strFldSelValue,
	     strAutoFilePath,strAutoFileName,strUploadFilePath
	 'Returns  :strReason
	 'Date    :18-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	

	public String addNewDocumentWithSavACancelOption(Selenium selenium,
			String strDocTitle, String strFolderName, boolean blnAllFolders,
			String strFldSelValue, String strAutoFilePath,
			String strAutoFileName, String strUploadFilePath, boolean blnSave) {

		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("DocumentLibrary.AddNewDocument")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on create New Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.AddNewDocument"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Add A Document", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Add A Document", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Add A Document page is displayed");
				// enter document title
				selenium.type(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Title"),
						strDocTitle);

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strFldSelValue + "']");
				}

				String strArgs[] = { strAutoFilePath, strUploadFilePath };
				// Auto it to upload the file
				Runtime.getRuntime().exec(strArgs);
				selenium.windowFocus();
				// click on Browse
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Browse"));

				// wait for pop up to appear
				Thread.sleep(5000);
				selenium.windowFocus();
				// Wait for autoit file to finish execution
				String strProcess = "";
				intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 120);

				if (blnSave) {

					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					do {
						try {
							assertEquals("Document Library", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCnt++;
						} catch (Exception e) {
							Thread.sleep(1000);
							intCnt++;
						}
					} while (intCnt < 60);

					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFolderName + "']"));
						// click on folder name
						selenium
								.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFolderName + "']");

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='F-"
											+ strFldSelValue
											+ "']/table/tbody/tr/td[3]/a/span[text()='"
											+ strDocTitle + "']"));
							assertTrue(selenium
									.isElementPresent("//div[@id='F-"
											+ strFldSelValue
											+ "']/table/tbody/tr/td[3]/a/span[text()='"
											+ strDocTitle
											+ "']/ancestor::tr/td[2]/form/input[@value='Move']"));
							assertTrue(selenium
									.isElementPresent("//div[@id='F-"
											+ strFldSelValue
											+ "']/table/tbody/tr/td[3]/a/span[text()='"
											+ strDocTitle
											+ "']/ancestor::tr/td[1]/form/input[@value='Delete']"));
							log4j
									.info(strDocTitle
											+ " is displayed on 'Document Library' screen with"
											+ " Delete and Move buttons.");
						} catch (AssertionError Ae) {
							strReason = strDocTitle
									+ " is NOT displayed on 'Document Library' screen with "
									+ "Delete and Move buttons.";
							log4j
									.info(strDocTitle
											+ " is NOT displayed on 'Document Library' screen"
											+ " with Delete and Move buttons.");
						}

					} catch (AssertionError Ae) {
						strReason = "Folder "
								+ strFolderName
								+ " is NOT displayed on 'Document Library' screen";
						log4j
								.info("Folder "
										+ strFolderName
										+ " is NOT displayed on 'Document Library' screen");
					}

				} else {
					// click on Cancel
					selenium
							.click(propElementDetails
									.getProperty("RegionalInfo.DocumentLibrary.Cancel"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFolderName + "']"));
						// click on folder name
						selenium
								.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
										+ strFolderName + "']");

						try {
							assertFalse(selenium
									.isElementPresent("//div[@id='F-"
											+ strFldSelValue
											+ "']/table/tbody/tr/td[3]/a/span[text()='"
											+ strDocTitle + "']"));

							log4j
									.info(strDocTitle
											+ " is NOT displayed on 'Document Library' screen");
						} catch (AssertionError Ae) {
							strReason = strDocTitle
									+ " is  displayed on 'Document Library' screen";
							log4j
									.info(strDocTitle
											+ " is NOT displayed on 'Document Library' screen");
						}

					} catch (AssertionError Ae) {
						strReason = "Folder "
								+ strFolderName
								+ " is NOT displayed on 'Document Library' screen";
						log4j
								.info("Folder "
										+ strFolderName
										+ " is NOT displayed on 'Document Library' screen");
					}

				}

			} catch (AssertionError Ae) {

				strReason = "Add A Document page is NOT displayed" + Ae;
				log4j.info("Add A Document page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j
					.info("addNewDocumentWithSavACancelOption function failed"
							+ e);
			strReason = "addNewDocumentWithSavACancelOption function failed"
					+ e;
		}
		return strReason;
	}

	public String checkAttachedFilesForFolders(Selenium selenium,
			String strFolderName, String strDocument, String strText)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Click on Event name in event banner

			if (selenium.isVisible("//span[text()='" + strDocument + "']") == false) {
				selenium
						.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
								+ strFolderName + "']");
				selenium.waitForPageToLoad(gstrTimeOut);

			}

			try {
				assertTrue(selenium.isElementPresent("//span[text()='"
						+ strDocument + "']"));
				log4j.info("'Attached file' link is displayed.");
				// click on Attached File link
				selenium.click("//span[text()='" + strDocument + "']");

				// select the pop up
				selenium.waitForPopUp("EMResourceDocument", gstrTimeOut);
				selenium.selectWindow("name=EMResourceDocument");
				try {
					assertTrue(selenium.isTextPresent(strText));
					log4j
							.info("The attached file (while event creation) is opened.");
				} catch (AssertionError Ae) {
					log4j
							.info("The attached file (while event creation) is NOT opened.");
					strReason = "The attached file (while event creation) is NOT opened.";
				}

				selenium.close();
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			} catch (AssertionError Ae) {
				log4j.info("'Attached file' link is NOT displayed.");
				strReason = "'Attached file' link is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;

	}
	
	/***********************************************************************
	'Description	:
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:3-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String verifyErrorMsgInDeltFoldPge(Selenium selenium) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on DELETE  Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.DeleteAFolder"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Delete Folder", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			

			try {
				assertEquals("Delete Folder", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Delete Folder page is displayed");
				
				//click on rename
				selenium.click(propElementDetails.getProperty("DocumentLibrary.DeleteAFolder.Delete"));				
							
				try {
					assertTrue(selenium.getConfirmation().matches("^Are you sure that you want to " +
							"delete this folder[\\s\\S] If it contains documents, they will be" +
							" removed from the entire region, not just your view\\.$"));
					log4j.info("A confirmation window is displayed.");	
					
					selenium.waitForPageToLoad(gstrTimeOut);
					
					try{
						assertEquals("Please choose a folder.", selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsg")));
						assertEquals("The following error occurred on this page:", selenium.getText("css=span.emsErrorTitle"));
						log4j.info("''Please choose a folder'' message is  displayed and the page is not saved. ");	
					} catch (AssertionError Ae) {

						strReason = "''Please choose a folder'' message is NOT displayed and the page is not saved. " + Ae;
						log4j.info("''Please choose a folder'' message is NOT displayed and the page is not saved. ");	
					}
					
				} catch (AssertionError Ae) {

					strReason = "A confirmation window is NOT displayed." + Ae;
					log4j.info("A confirmation window is NOT displayed.");	
				}
				

			} catch (AssertionError Ae) {

				strReason = "Delete Folder page is NOT displayed" + Ae;
				log4j.info("Delete Folder to Rename page is NOT displayed");		
			}
		

		} catch (Exception e) {
			log4j.info("verifyErrorMsgInDeltFoldPge function failed" + e);
			strReason = "verifyErrorMsgInDeltFoldPge function failed" + e;
		}
		return strReason;
	}
	/*******************************************************************
	'Description    :verify document created time
	'Precondition   :None
	'Arguments      :selenium,UserName
	'Returns        :String
	'Date           :28-May-2012
	'Author         :QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'28-May-2012                               <Name>
	********************************************************************/

	public String varDocCreatedTime(Selenium selenium, String strUserName,
			String strTimeDate) throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue(selenium.isElementPresent("//span[text()='(By: "
						+ strUserName + " on " + strTimeDate + ")']"));
				log4j.info(strUserName + ", " + strTimeDate
						+ " of creation are displayed next to the document. ");
			} catch (AssertionError Ae) {
				log4j
						.info(strUserName
								+ ", "
								+ strTimeDate
								+ " of creation are NOT displayed next to the document. ");
				strErrorMsg = strUserName
						+ ", "
						+ strTimeDate
						+ " of creation are NOT displayed next to the document. ";
			}
		} catch (Exception e) {
			log4j.info("varDocCreatedTime function failed" + e);
			strErrorMsg = "varDocCreatedTime function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Without selecting folder Move documents Verify error message
	'Precondition	:None
	'Arguments		:selenium,strOldFoldName,strOldFldSelValue,strDocument
	'Returns		:strReason
	'Date	 		:11-sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String movDocWithtSelFolderVrfyErrMsg(Selenium selenium,
			String strDocValue, String strDocument) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Move from Uncategorized section
			selenium.click("//form[@name='move_document_" + strDocValue
					+ "']/input[@value='Move']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Move A Document", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Move A Document", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Move A Document page is displayed");

				// click on save
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.MovDocToFold.Move"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {
						assertEquals(
								"Please choose the new folder in which to move the document.",
								selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsg")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception e) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				try {
					assertEquals(
							"Please choose the new folder in which to move the document.",
							selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsg")));
					log4j
							.info("The message ''Please choose the new folder in which to move the document'' is displayed ");
				} catch (AssertionError Ae) {
					strReason = "The message ''Please choose the new folder in which to move the document'' is NOT displayed ";
					log4j
							.info("The message ''Please choose the new folder in which to move the document'' is NOT displayed ");
				}

			} catch (AssertionError Ae) {
				strReason = "Move A Document page is NOT displayed" + Ae;
				log4j.info("Move A Document page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("movDocWithtSelFolderVrfyErrMsg function failed" + e);
			strReason = "movDocWithtSelFolderVrfyErrMsg function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Move documents from one folder to another sub folder
	'Precondition	:None
	'Arguments		:selenium,strOldFoldName,strOldFldSelValue,blnAllFolders,strDocument
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String moveDocToAllFolders(Selenium selenium,String strOldFoldName,String strOldFldSelValue,
		boolean blnAllFolders,String strDocument){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			//click on folder name
			selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strOldFoldName+"']");
			
			//click on move button for particular document
			selenium.click("//div[@id='F-"+strOldFldSelValue+"']/table/tbody/tr/td[3]/a/span[text()='"+strDocument+"']/ancestor::tr/td[2]/form/input[@value='Move']");
 			selenium.waitForPageToLoad(gstrTimeOut);
 			
 			int intCnt=0;
			do{
				try{
					assertEquals("Move A Document", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			
			try {
				assertEquals("Move A Document", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Move A Document page is displayed");			
				
				if(blnAllFolders){
					//select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				}
				//click on save
				selenium.click(propElementDetails
									.getProperty("DocumentLibrary.MovDocToFold.Move"));					
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt=0;
				do{
					try{
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				
				
				try{
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					//click on folder name
					selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strOldFoldName+"']");

					try {
						assertFalse(selenium.isElementPresent("//div[@id='F-"
								+ strOldFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j.info(strDocument
								+ " is not displayed on 'Document Library' screen under Folder "
								+ strOldFoldName);
					} catch (AssertionError Ae) {
						strReason = strDocument
								+ " is still displayed on 'Document Library' screen under Folder "
								+ strOldFoldName;
						log4j.info(strDocument
								+ " is still displayed on 'Document Library' screen under Folder "
								+ strOldFoldName);
					}
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/table"
										+ "/tbody/tr/td/a/span[text()='"
										+ strDocument + "']"));
						log4j.info(strDocument
								+ " is displayed under 'Uncategorized documents' section.");
					} catch (AssertionError Ae) {
						strReason = strDocument
								+ " is NOT displayed under 'Uncategorized documents' section.";
						log4j.info(strDocument
								+ " is NOT displayed under 'Uncategorized documents' section.");
					}

				} catch (AssertionError Ae) {
					strReason = "'Document Library' screen is NOT displayed";
					log4j.info("'Document Library' screen is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strReason = "Move A Document page is NOT displayed" + Ae;
				log4j.info("Move A Document page is NOT displayed" + Ae);
			}
		
			
		} catch (Exception e) {
			log4j.info("moveDocToAllFolders function failed" + e);
			strReason = "moveDocToAllFolders function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:Move documents from one folder to other
	'Precondition	:None
	'Arguments		:selenium,strOldFoldName,strOldFldSelValue,strNewFoldName,
					strNewFldSelValue,blnAllFolders,strDocument
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String moveDocToSubFolder(Selenium selenium,String strOldFoldName,
			String strOldFldSelValue,
			String strNewFoldName,String strNewFoldValue,String strSubFolder,String 
			strSubFolderValue,boolean blnAllFolders,String strDocument){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			//click on folder name
			selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strOldFoldName+"']");
			
			//click on move button for particular document
			selenium.click("//div[@id='F-"+strOldFldSelValue+"']/table/tbody/tr/td[3]/a/span[text()='"+strDocument+"']/ancestor::tr/td[2]/form/input[@value='Move']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Move A Document", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			
			try {
				assertEquals("Move A Document", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Move A Document page is displayed");			
				
				if(blnAllFolders){
					//select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				}else{
					//select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"+strSubFolderValue+"']");
				}
				
				//click on save
				selenium.click(propElementDetails
									.getProperty("DocumentLibrary.MovDocToFold.Move"));					
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt=0;
				do{
					try{
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				
				
				try{
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					//click on folder name
					selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strOldFoldName+"']");
					
					try {
						assertFalse(selenium.isElementPresent("//div[@id='F-"
								+ strOldFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j
								.info(strDocument
										+ " is not displayed on 'Document Library' screen under Folder "
										+ strOldFoldName);
					} catch (AssertionError Ae) {
						strReason = strDocument
								+ " is still displayed on 'Document Library' screen under Folder "
								+ strOldFoldName;
						log4j
								.info(strDocument
										+ " is still displayed on 'Document Library' screen under Folder "
										+ strOldFoldName);
					}

					// click on folder name
				    selenium.click("//div[@id='F-"+strNewFoldValue+"']/a/span[text()='"
									+ strSubFolder + "']");

					try {
						assertTrue(selenium.isElementPresent("//div[@id='F-"
								+ strSubFolderValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j.info("Document " + strDocument
								+ " is displayed under Sub folder"+strSubFolder);
					} catch (AssertionError Ae) {
						strReason = "Document " + strDocument
								+ " is displayed  under Sub folder"+strSubFolder;
						log4j.info("Document " + strDocument
								+ " is displayed  under Sub folder"+strSubFolder);
					}

				} catch (AssertionError Ae) {
					strReason = "'Document Library' screen is NOT displayed";
					log4j.info("'Document Library' screen is NOT displayed" );
				}
				
			} catch (AssertionError Ae) {

				strReason = "Move A Document page is NOT displayed" + Ae;
				log4j.info("Move A Document page is NOT displayed" + Ae);
			}
		
			
		} catch (Exception e) {
			log4j.info("moveDocToFolder function failed" + e);
			strReason = "moveDocToFolder function failed" + e;
		}
		return strReason;
	}

	
	/***********************************************************************
	'Description	:Delete a folder
	'Precondition	:None
	'Arguments		:selenium,strFoldName,strFldSelValue,blnDeleteDocs,blnAllFolders,strDocuments
	'Returns		:strReason
	'Date	 		:08-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String deleteAFolderAnDCheckDcInUncategorizedFld(Selenium selenium,
			String strFoldName,
			String strFldSelValue, boolean blnDeleteDocs,
			boolean blnAllFolders, String strDocuments[]) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on DELETE  Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.DeleteAFolder"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Delete Folder", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			

			try {
				assertEquals("Delete Folder", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Delete Folder page is displayed");

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				}else{
					//select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"+strFldSelValue+"']");
				}
				if(blnDeleteDocs){
					//select Delete Documents
					selenium.click(propElementDetails.getProperty("DocumentLibrary.DeleteAFolder.DeleteDocums"));
				}
				//click on rename
				selenium.click(propElementDetails.getProperty("DocumentLibrary.DeleteAFolder.Delete"));				
							
				try {
					assertTrue(selenium.getConfirmation().matches("^Are you sure that you want to " +
							"delete this folder[\\s\\S] If it contains documents, they will be" +
							" removed from the entire region, not just your view\\.$"));
					log4j.info("A confirmation window is displayed.");	
					
					selenium.waitForPageToLoad(gstrTimeOut);
					
					
					intCnt=0;
					do{
						try{
							assertEquals("Document Library", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						}
						catch(Exception e){
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					
					try{
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertFalse(selenium.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"+strFoldName+"']"));
						log4j.info("Folder "+strFoldName+" is not displayed on 'Document Library' screen" );
						
						if(blnDeleteDocs){
							for(int intRec=0;intRec<strDocuments.length;intRec++){
								try{
									assertFalse(selenium.isElementPresent("//span[text()='Uncategorized Documents']/following-sibling::table/tbody/tr/td/a/span[text()='"+strDocuments[intRec]+"']"));
									log4j.info("Document "+strDocuments[intRec]+" is not displayed under All Folders" );
								} catch (AssertionError Ae) {
									strReason = strReason+" Document "+strDocuments[intRec]+" is still displayed under All Folders";
									log4j.info("Document "+strDocuments[intRec]+" is still displayed under All Folders" );
								}
							}
						}else{
							
							for(int intRec=0;intRec<strDocuments.length;intRec++){
								try{
									assertTrue(selenium.isElementPresent("//span[text()='Uncategorized Documents']/following-sibling::table/tbody/tr/td/a/span[text()='"+strDocuments[intRec]+"']"));
									log4j.info("Document "+strDocuments[intRec]+" is  displayed under Uncategorized Documents Section" );
								} catch (AssertionError Ae) {
									strReason = strReason+" Document "+strDocuments[intRec]+" is NOT still displayed under Uncategorized Documents Section";
									log4j.info("Document "+strDocuments[intRec]+" is NOT still displayed under Uncategorized Documents Section" );
								}
							}
							
						}
					} catch (AssertionError Ae) {
						strReason = "Folder "+strFoldName+" is still displayed on 'Document Library' screen";
						log4j.info("Folder "+strFoldName+" is still displayed on 'Document Library' screen" );
					}
					
				} catch (AssertionError Ae) {

					strReason = "A confirmation window is NOT displayed." + Ae;
					log4j.info("A confirmation window is NOT displayed.");	
				}
				
								
			} catch (AssertionError Ae) {

				strReason = "Delete Folder page is NOT displayed" + Ae;
				log4j.info("Delete Folder to Rename page is NOT displayed");		
			}
		
			
		} catch (Exception e) {
			log4j.info("deleteAFolder function failed" + e);
			strReason = "deleteAFolder function failed" + e;
		}
		return strReason;
	}
	/********************************************************************************************************
	  'Description :Fetch Status Type Value in Status Type List page
	  'Precondition :None
	  'Arguments  :selenium,strStatusType
	  'Returns  :String
	  'Date    :8-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/
	public String fetcDocValInUncategorizedList(Selenium selenium,
			String strDocument) throws Exception {
		
		
		String strDocValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try {
			String strDocValArr[] = selenium
					.getAttribute(
							"//div[@id='mainContainer']/div/table/tbody/tr/td/a/" +
							"span[text()='"+strDocument+"']/parent::a/@onclick").split(
							"DISPLAY_DOCUMENT&id=");
			strDocValue = strDocValArr[1];
			String[]strDocValues=strDocValue.split("'");
			strDocValue=strDocValues[0];
			log4j.info("Docment Value is " + strDocValue);
		} catch (Exception e) {
			log4j.info("fetcDocValInUncategorizedList function failed" + e);
			strDocValue = "";
		}
		return strDocValue;
	}
	
	
	/********************************************************************************************************
	  'Description :Fetch Status Type Value in Status Type List page
	  'Precondition :None
	  'Arguments  :selenium,strStatusType
	  'Returns  :String
	  'Date    :8-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/
	public String fetcDocValInFolderList(Selenium selenium, String strDocument,
			String strFolderValue) throws Exception {

		String strDocValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try {
			String strDocValArr[] = selenium.getAttribute(
					"//div[@id='F-" + strFolderValue + "']"
							+ "/table/tbody/tr/td[3]/a/span[text()='"
							+ strDocument + "']/parent::a/@onclick").split(
					"DISPLAY_DOCUMENT&id=");
			strDocValue = strDocValArr[1];
			String[] strDocValues = strDocValue.split("'");
			strDocValue = strDocValues[0];
			log4j.info("Docment Value is " + strDocValue);
		} catch (Exception e) {
			log4j.info("fetcDocValInFolderList function failed" + e);
			strDocValue = "";
		}
		return strDocValue;
	}
	/***********************************************************************
	'Description	:Without selecting folder Move documents Verify error message
	'Precondition	:None
	'Arguments		:selenium,strOldFoldName,strOldFldSelValue,strDocument
	'Returns		:strReason
	'Date	 		:11-sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String movDocToFolderFromUnCategarizedSec(Selenium selenium,
			String strDocValue, String strDocument, boolean blnMove,
			boolean blnAllFolders, String strNewFoldName,
			String strNewFldSelValue) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnMove) {
				// Move from Uncategorized section
				selenium.click("//form[@name='move_document_" + strDocValue
						+ "']/input[@value='Move']");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			try {
				assertEquals("Move A Document", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Move A Document page is displayed");

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strNewFldSelValue + "']");
				}

				// click on save
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.MovDocToFold.Move"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				do {
					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception e) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				try {
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					// click on folder name
					selenium
							.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
									+ strNewFoldName + "']");

					try {
						assertTrue(selenium.isElementPresent("//div[@id='F-"
								+ strNewFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j
								.info(strDocument
										+ " is displayed on 'Document Library' screen under Folder "
										+ strNewFoldName);
					} catch (AssertionError Ae) {
						strReason = strDocument
								+ " is NOT displayed on 'Document Library' screen under Folder "
								+ strNewFoldName;
						log4j
								.info(strDocument
										+ " is NOT displayed on 'Document Library' screen under Folder "
										+ strNewFoldName);
					}

				} catch (AssertionError Ae) {
					strReason = "'Document Library' screen is NOT displayed";
					log4j.info("'Document Library' screen is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strReason = "Move A Document page is NOT displayed" + Ae;
				log4j.info("Move A Document page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("movDocWithtSelFolderVrfyErrMsg function failed" + e);
			strReason = "movDocWithtSelFolderVrfyErrMsg function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Add a new document to the folder
	'Precondition	:None
	'Arguments		:selenium,strDocTitle,strFolderName,blnAllFolders,strFldSelValue,
					strAutoFilePath,strAutoFileName,strUploadFilePath
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String addDocument(Selenium selenium, String strDocTitle,
			String strFolderName, boolean blnAllFolders, String strFldSelValue,
			String strAutoFilePath, String strAutoFileName,
			String strUploadFilePath) {

		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			// click on create New Folder
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.AddNewDocument"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Add A Document", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Add A Document", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Add A Document page is displayed");
				// enter document title
				selenium.type(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Title"),
						strDocTitle);

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strFldSelValue + "']");
				}

				String strArgs[] = { strAutoFilePath, strUploadFilePath };
				// Auto it to upload the file
				Runtime.getRuntime().exec(strArgs);
				selenium.windowFocus();
				// click on Browse
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Browse"));
				// wait for pop up to appear
				Thread.sleep(5000);
				// Wait for autoit file to finish execution
				String strProcess = "";

				intCnt = 0;
				do {

					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 120);

				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {

				strReason = "Add A Document page is NOT displayed" + Ae;
				log4j.info("Add A Document page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("addNewDocument function failed" + e);
			strReason = "addNewDocument function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Move documents from one folder to another sub folder
	'Precondition	:None
	'Arguments		:selenium,strOldFoldName,strOldFldSelValue,blnAllFolders,strDocument
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String moveDocToAllFoldersFromSubFolder(Selenium selenium,
			String strOldFoldName,String strOldFldSelValue,
		boolean blnAllFolders,String strDocument){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	
			
			//click on move button for particular document
			selenium.click("//div[@id='F-"+strOldFldSelValue+"']/table/tbody/tr/td[3]/a/span[text" +
					"()='"+strDocument+"']/ancestor::tr/td[2]/form/input[@value='Move']");
 			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Move A Document", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Move A Document page is displayed");			
				
				if(blnAllFolders){
					//select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				}
				//click on save
				selenium.click(propElementDetails
									.getProperty("DocumentLibrary.MovDocToFold.Move"));					
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt=0;
				do{
					try{
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));	
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				
				
				try{
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));					
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div[2]/table"
										+ "/tbody/tr/td/a/span[text()='"
										+ strDocument + "']"));
						log4j.info(strDocument
								+ " is displayed under 'Uncategorized documents' section.");
					} catch (AssertionError Ae) {
						strReason = strDocument
								+ " is NOT displayed under 'Uncategorized documents' section.";
						log4j.info(strDocument
								+ " is NOT displayed under 'Uncategorized documents' section.");
					}

				} catch (AssertionError Ae) {
					strReason = "'Document Library' screen is NOT displayed";
					log4j.info("'Document Library' screen is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strReason = "Move A Document page is NOT displayed" + Ae;
				log4j.info("Move A Document page is NOT displayed" + Ae);
			}
		
			
		} catch (Exception e) {
			log4j.info("moveDocToAllFoldersFromSubFolder function failed" + e);
			strReason = "moveDocToAllFoldersFromSubFolder function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Cancel the process of moving document from onr folder to another
	'Precondition	:None
	'Arguments		:selenium,strOldFoldName,strOldFldSelValue,strNewFoldName,
					  strNewFldSelValue,blnAllFolders,strDocument
	'Returns		:strReason
	'Date	 		:1-April-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
		
	public String cancelMoveDocToFolder(Selenium selenium,
			String strOldFoldName, String strOldFldSelValue,
			String strNewFoldName, String strNewFldSelValue,
			boolean blnAllFolders, String strDocument) {
		String lStrReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div[2]/a/span[text()='"
									+ strOldFoldName + "']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on folder name
			selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
					+ strOldFoldName + "']");

			// click on move button for particular document
			selenium.click("//div[@id='F-" + strOldFldSelValue
					+ "']/table/tbody/tr/td[3]/a/span[text()='" + strDocument
					+ "']/ancestor::tr/td[2]/form/input[@value='Move']");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Move A Document", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Move A Document", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Move A Document page is displayed");

				if (blnAllFolders) {
					// select All Folders
					selenium.click("css=input[name=chosenFolderID][value='0']");
				} else {
					// select the particular folder for 'Create In'
					selenium.click("css=input[name=chosenFolderID][value='"
							+ strNewFldSelValue + "']");
				}

				// click on save
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.MovDocToFold.Cancel"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {
						assertEquals("Document Library", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception e) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				try {
					assertEquals("Document Library", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					// click on folder name
					selenium
							.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
									+ strOldFoldName + "']");

					try {
						assertTrue(selenium.isElementPresent("//div[@id='F-"
								+ strOldFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j
								.info(strDocument
										+ " is  displayed on 'Document Library' screen under Folder "
										+ strOldFoldName);
					} catch (AssertionError Ae) {
						lStrReason = strDocument
								+ " is NOT displayed on 'Document Library' screen under Folder "
								+ strOldFoldName;
						log4j
								.info(strDocument
										+ " is NOT displayed on 'Document Library' screen under Folder "
										+ strOldFoldName);
					}

					// click on folder name
					selenium
							.click("//div[@id='mainContainer']/div[2]/a/span[text()='"
									+ strNewFoldName + "']");

					try {
						assertFalse(selenium.isElementPresent("//div[@id='F-"
								+ strNewFldSelValue
								+ "']/table/tbody/tr/td[3]/a/span[text()='"
								+ strDocument + "']"));
						log4j
								.info(strDocument
										+ " is NOT displayed on 'Document Library' screen under Folder "
										+ strNewFoldName);
					} catch (AssertionError Ae) {
						lStrReason = strDocument
								+ " is displayed on 'Document Library' screen under Folder "
								+ strNewFoldName;
						log4j
								.info(strDocument
										+ " is displayed on 'Document Library' screen under Folder "
										+ strNewFoldName);
					}

				} catch (AssertionError Ae) {
					lStrReason = "'Document Library' screen is NOT displayed";
					log4j.info("'Document Library' screen is NOT displayed");
				}

			} catch (AssertionError Ae) {

				lStrReason = "Move A Document page is NOT displayed" + Ae;
				log4j.info("Move A Document page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j
					.info("DocumentLibrary.cancelMoveDocToFolder failed to complete due to "
							+ lStrReason + "; " + e.toString());
			lStrReason = "DocumentLibrary.cancelMoveDocToFolder failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	
}
