package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

public class UserLinks {

	static Logger log4j = Logger
	.getLogger("com.qsgsoft.EMResource.shared.UserLinks");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;

	/***********************************************************************
	'Description	:Navigate User link page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String navToUserLinkList(Selenium selenium){
		String strReason="";
		
		try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));

			selenium
					.click(propElementDetails.getProperty("SetUP.UserLinkLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("User Link List", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			try {
				assertEquals("User Link List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("User Link List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "User Link List page is NOT displayed" + Ae;
				log4j.info("User Link List page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("navToUserLinkList function failed" + e);
			strReason = "navToUserLinkList function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Navigate edit user link page
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String navToEditLinkPage(Selenium selenium,String strLablText){
		String strReason="";
		
		try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			selenium.click("//div[@id='mainContainer']/table/tbody" +
					"/tr/td[text()='"+strLablText+"']/parent::tr/td[1]/a[text()='Edit']");
			
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Edit User Link", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Edit User Link page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Edit User Link page is NOT displayed" + Ae;
				log4j.info("Edit User Link page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("navToEditLinkPage function failed" + e);
			strReason = "navToEditLinkPage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify data retained in the edit user link page
	'Precondition	:None
	'Arguments		:selenium,strLablText,strImgFile,strUrlOfExternalLink,blnQuickLink
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String veriftDataRetainedInEditUserPage(Selenium selenium,
			String strLablText, String strUrlOfExternalLink,
			boolean blnQuickLink) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			//Label
			try {
				assertEquals(selenium.getValue("css=input[name='mouseOver']"),
						strLablText);
				log4j.info("Label  " + strLablText
						+ " is retained in edit user page");
				
			} catch (AssertionError Ae) {
				log4j.info("Label  " + strLablText
						+ " is NOT retained in edit user page" + Ae);
				strReason = "Label  " + strLablText
						+ " is NOT retained in edit user page" + Ae;
			}
			
			//Image file
			
			try {
				assertFalse(selenium.isElementPresent("css=input[name='document']"));
				log4j.info("Image upload option "
						+ " is NOT available in edit user page");
				
			} catch (AssertionError Ae) {
				log4j.info("Image upload option "
						+ " is available in edit user page");
				strReason = "Image upload option "
						+ " is available in edit user page";
			}
			
			
			// Link Type
			try {
				assertTrue(selenium.isChecked("css=input[name='linkType'][value='LINK']"));

				log4j.info("Link Type is checked in Edit user page");
				
				
				try {
					assertEquals(selenium
							.getValue("css=input[name='destinationUrl']"),
							strUrlOfExternalLink);
					log4j.info(strUrlOfExternalLink
							+ " Link  is retained in Edit user page");

				} catch (AssertionError Ae) {
					log4j.info(strUrlOfExternalLink
							+ " Link  is retained in Edit user page");

					strReason = strUrlOfExternalLink
							+ " Link  is retained in Edit user page";
				}
				

			} catch (AssertionError Ae) {
				log4j.info("Link Type is NOT checked in Edit user page");
				strReason = "Link Type is NOT checked in Edit user page";
			}
			
			//Quick Link

			if(blnQuickLink){
				
				try {
					assertTrue(selenium.isChecked("css=input[name='quickLink']"));
					log4j.info("Quick link is retained in edit user page");
					
				} catch (AssertionError Ae) {
					log4j.info("Quick link is NOT retained in edit user page");
					strReason = "Quick link is NOT retained in edit user page";
				}
				
			}else{
				try {
					assertFalse(selenium.isChecked("css=input[name='quickLink']"));
					log4j.info("Quick link is retained in edit user page");
					
				} catch (AssertionError Ae) {
					log4j.info("Quick link is NOT retained in edit user page");
					strReason = "Quick link is NOT retained in edit user page";
				}
				
			}
			
			
		} catch (Exception e) {
			log4j.info("veriftDataRetainedInEditUserPage function failed" + e);
			strReason = "veriftDataRetainedInEditUserPage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link is deleted
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String deleteUserLink(Selenium selenium,String strLablText){
		String strReason="";
		
		try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			selenium.click("//div[@id='mainContainer']/table/tbody" +
					"/tr/td[text()='"+strLablText+"']/parent::tr/td[1]/a[text()='Delete']");
			
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertFalse(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody" +
					"/tr/td[text()='"+strLablText+"']"));

				log4j.info("User Link is NOT displayed in User link list page");

			} catch (AssertionError Ae) {

				strReason = "User Link is displayed in User link list page";
				log4j.info("User Link is displayed in User link list page");
			}
			
		} catch (Exception e) {
			log4j.info("deleteUserLink function failed" + e);
			strReason = "deleteUserLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String createUserLink(Selenium selenium, String strLablText,
			String strExternalURL, boolean blnQuicklaunch,
			String strAutoFilePath, String strUploadFilePath,
			String strAutoFileName, boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("css=input[value='Create New User Link']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create New User Link", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New User Link page is displayed");

				// Enter lable name
				selenium.type(propElementDetails
						.getProperty("UserLink.LinkName"), strLablText);

				// Upload Image File
				String strArgs[] = { strAutoFilePath, strUploadFilePath };
				// Auto it to upload the file
				Runtime.getRuntime().exec(strArgs);
				selenium.windowFocus();
				selenium.windowFocus();
				// click on Browse
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Browse"));
				// wait for pop up to appear
				Thread.sleep(5000);
				// Wait for autoit file to finish execution
				String strProcess = "";
				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 120);

				selenium.type(propElementDetails
						.getProperty("UserLink.ExternalURL"), strExternalURL);

				if (blnQuicklaunch) {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserLink.QuickLink"));

					}
				}

				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("UserLink.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("User Link List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("User Link List page is displayed");

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
							+ "/tr/td[text()='"
											+ strLablText
											+ "']"));

							log4j
									.info("User Link is displayed in User link list page");

						} catch (AssertionError Ae) {

							strReason = "User Link is NOT displayed in User link list page";
							log4j
									.info("User Link is NOT displayed in User link list page");
						}

					} catch (AssertionError Ae) {

						strReason = "User Link List page is NOT displayed" + Ae;
						log4j.info("User Link List page is NOT displayed" + Ae);
					}
				}

			} catch (AssertionError Ae) {

				strReason = "Create New User Link page is NOT displayed";
				log4j.info("Create New User Link page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("createUserLink function failed" + e);
			strReason = "createUserLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link is present in sub navigator 
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String verifyUserLinkInHeader(Selenium selenium,String strLablText){
		String strReason="";
		
		try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			selenium.mouseOver(propElementDetails
					.getProperty("UserLink.TopRtCorner"));
			
			
			try {
				assertFalse(selenium.isElementPresent("//div[@id='topNav']" +
						"/div[@class='userLinks subNav']/div/a/span[text()='"+strLablText+"']"));

				log4j.info("User Link is NOT displayed in User link top right of the screen. ");

			} catch (AssertionError Ae) {

				strReason = "User Link is displayed in User link top right of the screen. ";
				log4j.info("User Link is displayed in User link top right of the screen. ");
			}
			
		} catch (Exception e) {
			log4j.info("verifyUserLinkInHeader function failed" + e);
			strReason = "verifyUserLinkInHeader function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Navigate User link page from create new user link list page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:7-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String navBakToUsrLnkLstFrmCrtNewUsrLnk(Selenium selenium){
		String strReason="";
		
		try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium
					.click(propElementDetails.getProperty("UserLink.Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("User Link List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("User Link List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "User Link List page is NOT displayed" + Ae;
				log4j.info("User Link List page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("navBakToUsrLnkLstFrmCrtNewUsrLnk function failed" + e);
			strReason = "navBakToUsrLnkLstFrmCrtNewUsrLnk function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String createUserLinkFirefox(Selenium selenium, String strLablText,
			String strExternalURL, boolean blnQuicklaunch,
			String strUploadFilePath, boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("css=input[value='Create New User Link']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create New User Link", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New User Link page is displayed");

				// Enter lable name
				selenium.type(propElementDetails
						.getProperty("UserLink.LinkName"), strLablText);

				selenium.type(propElementDetails
						.getProperty("UserLink.Document"), strUploadFilePath);

				selenium.type(propElementDetails
						.getProperty("UserLink.ExternalURL"), strExternalURL);

				if (blnQuicklaunch) {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserLink.QuickLink"));

					}
				}

				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("UserLink.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("User Link List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("User Link List page is displayed");

						if (strLablText.compareTo("") != 0) {
							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody"
												+ "/tr/td[2][text()='"
												+ strLablText + "']"));

								log4j
										.info("User Link is displayed in User link list page");

							} catch (AssertionError Ae) {

								strReason = "User Link is NOT displayed in User link list page";
								log4j
										.info("User Link is NOT displayed in User link list page");
							}
						}

					} catch (AssertionError Ae) {

						strReason = "User Link List page is NOT displayed" + Ae;
						log4j.info("User Link List page is NOT displayed" + Ae);
					}
				}

			} catch (AssertionError Ae) {

				strReason = "Create New User Link page is NOT displayed";
				log4j.info("Create New User Link page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("createUserLinkFirefox function failed" + e);
			strReason = "createUserLinkFirefox function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link data is retained in edit user page
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String verifyUserLinkretainedInEditUsrLinkPge(Selenium selenium,
			String strLablText, String strExternalURL, boolean blnQuicklaunch,
			String strUploadFilePath) {

		String strReason = "";

		try {
			General objGeneral = new General();

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strElementID = propElementDetails
					.getProperty("UserLink.LinkName");
			strReason = objGeneral.CheckForElements(selenium, strElementID);

			try {
				assertEquals("", strReason);
				try {
					assertEquals(strLablText, selenium
							.getValue(propElementDetails
									.getProperty("UserLink.LinkName")));

					log4j.info("User Link name is retained in Edit user page");
				} catch (AssertionError Ae) {

					strReason = "User Link name is NOT retained in Edit user page "
							+ Ae;
					log4j
							.info("User Link name is NOT retained in Edit user page "
									+ Ae);
				}
				
		

				try {
					assertEquals(strExternalURL,selenium
							.getValue(propElementDetails
									.getProperty("UserLink.ExternalURL")));

					log4j.info("External URL is present in Edit user page");
				} catch (AssertionError Ae) {

					strReason = "External URL  is NOT present in Edit user page "
							+ Ae;
					log4j
							.info("External URL  is NOT present in Edit user page "
									+ Ae);
				}

				
				try {
					assertFalse(selenium
							.isElementPresent(propElementDetails
									.getProperty("UserLink.Document")));

					log4j.info("Document is absent in Edit user page");
				} catch (AssertionError Ae) {

					strReason = "Document  is present in Edit user page "
							+ Ae;
					log4j
							.info("Document  is present in Edit user page "
									+ Ae);
				}

				if (blnQuicklaunch) {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink"))) {
						log4j
								.info("Quick launch field is retained in Edit user page ");

					} else {
						strReason = "Quick launch field  is NOT retained in Edit user page ";
						log4j
								.info("Quick launch field  is NOT retained in Edit user page ");
					}
				} else {

					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink")) == false) {
						log4j
								.info("Quick launch field  is  retained in Edit user page ");

					} else {
						strReason = "Quick launch field  is NOT retained in Edit user page ";
						log4j
								.info("Quick launch field is NOT retained in Edit user page ");
					}

				}

			} catch (AssertionError Ae) {
				log4j.info(strReason);
			}

		} catch (Exception e) {
			log4j.info("verifyUserLinkretainedInEditUsrLinkPge function failed"
					+ e);
			strReason = "verifyUserLinkretainedInEditUsrLinkPge function failed"
					+ e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link data is retained in edit user page
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String navEditUserLinkPage(Selenium selenium, String strLablText) {

		String strReason = "";

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()"
							+ "='"
							+ strLablText
							+ "']/parent::tr/td[1]/a[1][text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit User Link", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Edit User Link page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Edit User Link page is NOT displayed";
				log4j.info("Edit User Link page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("navEditUserLinkPage function failed" + e);
			strReason = "navEditUserLinkPage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link data is retained in edit user page
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String saveUserLink(Selenium selenium, String strLablText) {

		String strReason = "";

		try {
			General objGeneral = new General();
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails.getProperty("UserLink.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			String strElementID = "//div[@id='mainContainer']/table/tbody"
					+ "/tr/td[text()='" + strLablText + "']";
			strReason = objGeneral.CheckForElements(selenium, strElementID);

			try {
				assertEquals("User Link List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("User Link List page is displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody"
									+ "/tr/td[text()='" + strLablText + "']"));

					log4j.info("User Link is displayed in User link list page");

				} catch (AssertionError Ae) {

					strReason = "User Link is NOT displayed in User link list page";
					log4j
							.info("User Link is NOT displayed in User link list page");
				}

			} catch (AssertionError Ae) {

				strReason = "User Link List page is NOT displayed" + Ae;
				log4j.info("User Link List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("saveUserLink function failed" + e);
			strReason = "saveUserLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:fillUserLinkFieldsInEditUserPage
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String fillUserLinkFieldsInEditUserPage(Selenium selenium,
			String strLablText, String strExternalURL, boolean blnQuicklaunch,
			boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Enter lable name
			selenium.type(propElementDetails.getProperty("UserLink.LinkName"),
					strLablText);

			selenium.type(propElementDetails
					.getProperty("UserLink.ExternalURL"), strExternalURL);

			if (blnQuicklaunch) {
				if (selenium.isChecked(propElementDetails
						.getProperty("UserLink.QuickLink")) == false) {
					selenium.click(propElementDetails
							.getProperty("UserLink.QuickLink"));

				}
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("UserLink.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("User Link List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("User Link List page is displayed");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody"
										+ "/tr/td[text()='"
										+ strLablText
										+ "']"));

						log4j
								.info("User Link is displayed in User link list page");

					} catch (AssertionError Ae) {

						strReason = "User Link is NOT displayed in User link list page";
						log4j
								.info("User Link is NOT displayed in User link list page");
					}

				} catch (AssertionError Ae) {

					strReason = "User Link List page is NOT displayed" + Ae;
					log4j.info("User Link List page is NOT displayed" + Ae);
				}
			}

		} catch (Exception e) {
			log4j.info("fillUserLinkFieldsInEditUserPage function failed" + e);
			strReason = "fillUserLinkFieldsInEditUserPage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:verifyLinkHeadersInUserLinkListPage
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String verifyLinkHeadersInUserLinkListPage(Selenium selenium,
			String strLinkLable, String strLablHeaderAction,
			String strLablHeaderActionEdit, String strLablHeaderActionDelete,
			String strLablHeaderActionHide, String strLablHeaderActionShow,
			String strLablHeaderActionUp, String strLablHeaderActionDown,
			boolean blnQuickLink) {

		String strReason = "";

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				assertEquals("User Link List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("User Link List page is displayed");

				try {
					assertEquals(
							selenium
									.getText("//div[@id='mainContainer']/table/thead/tr/th[1]"),
							strLablHeaderAction);
					log4j.info("The user link is listed on the User Link "
							+ "list screen with the following column: Action ");

					try {
						assertEquals(
								selenium
										.getText("//div[@id='mainContainer']/table/"
												+ "thead/tr/th[1][text()='Action']/ancestor"
												+ "::table/tbody/tr/td[2][text()='"
												+ strLinkLable
												+ "']"
												+ "/parent::tr/td[1]/a[1]"),
								strLablHeaderActionEdit);
						log4j
								.info("The user link is listed on the User Link "
										+ "list screen with the following column: Edit ");
					} catch (AssertionError Ae) {
						log4j
								.info("The user link is NOT listed on the User Link "
										+ "list screen with the following column: Edit "
										+ Ae);
						strReason = "The user link is NOT listed on the User Link "
								+ "list screen with the following column: Edit "
								+ Ae;

					}

					try {
						assertEquals(
								selenium
										.getText("//div[@id='mainContainer']/table/"
												+ "thead/tr/th[1][text()='Action']/ancestor"
												+ "::table/tbody/tr/td[2][text()='"
												+ strLinkLable
												+ "']"
												+ "/parent::tr/td[1]/a[2]"),
								strLablHeaderActionDelete);
						log4j
								.info("The user link is listed on the User Link "
										+ "list screen with the following column: Delete ");
					} catch (AssertionError Ae) {
						log4j
								.info("The user link is NOT listed on the User Link "
										+ "list screen with the following column: Delete "
										+ Ae);
						strReason = "The user link is NOT listed on the User Link "
								+ "list screen with the following column: Delete "
								+ Ae;

					}

					if (selenium
							.isElementPresent("//div[@id='mainContainer']/table/"
									+ "thead/tr/th[1][text()='Action']/ancestor"
									+ "::table/tbody/tr/td[2][text()='"
									+ strLinkLable
									+ "']"
									+ "/parent::tr/td[1]/a[3][text()='Show']")) {

						try {

							assertEquals(
									selenium
											.getText("//div[@id='mainContainer']/table/"
													+ "thead/tr/th[1][text()='Action']/ancestor"
													+ "::table/tbody/tr/td[2][text()='"
													+ strLinkLable
													+ "']"
													+ "/parent::tr/td[1]/a[3]"),
									strLablHeaderActionShow);
							log4j
									.info("The user link is listed on the User Link "
											+ "list screen with the following column: Show ");
						} catch (AssertionError Ae) {
							log4j
									.info("The user link is NOT listed on the User Link "
											+ "list screen with the following column: Show "
											+ Ae);
							strReason = "The user link is NOT listed on the User Link "
									+ "list screen with the following column: Show "
									+ Ae;

						}

						selenium.click("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLinkLable + "']"
								+ "/parent::tr/td[1]/a[3]");
						selenium.waitForPageToLoad(gstrTimeOut);
						try {

							assertEquals(
									selenium
											.getText("//div[@id='mainContainer']/table/"
													+ "thead/tr/th[1][text()='Action']/ancestor"
													+ "::table/tbody/tr/td[2][text()='"
													+ strLinkLable
													+ "']"
													+ "/parent::tr/td[1]/a[3]"),
									strLablHeaderActionHide);
							log4j
									.info("The user link is listed on the User Link "
											+ "list screen with the following column: Hide ");
						} catch (AssertionError Ae) {
							log4j
									.info("The user link is NOT listed on the User Link "
											+ "list screen with the following column: Hide "
											+ Ae);
							strReason = "The user link is NOT listed on the User Link "
									+ "list screen with the following column: Hide "
									+ Ae;

						}

					} else {

						try {

							assertEquals(
									selenium
											.getText("//div[@id='mainContainer']/table/"
													+ "thead/tr/th[1][text()='Action']/ancestor"
													+ "::table/tbody/tr/td[2][text()='"
													+ strLinkLable
													+ "']"
													+ "/parent::tr/td[1]/a[3]"),
									strLablHeaderActionHide);
							log4j
									.info("The user link is listed on the User Link "
											+ "list screen with the following column: Hide ");
						} catch (AssertionError Ae) {
							log4j
									.info("The user link is NOT listed on the User Link "
											+ "list screen with the following column: Hide "
											+ Ae);
							strReason = "The user link is NOT listed on the User Link "
									+ "list screen with the following column: Hide "
									+ Ae;

						}

						selenium.click("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLinkLable + "']"
								+ "/parent::tr/td[1]/a[3]");
						selenium.waitForPageToLoad(gstrTimeOut);

						try {

							assertEquals(
									selenium
											.getText("//div[@id='mainContainer']/table/"
													+ "thead/tr/th[1][text()='Action']/ancestor"
													+ "::table/tbody/tr/td[2][text()='"
													+ strLinkLable
													+ "']"
													+ "/parent::tr/td[1]/a[3]"),
									strLablHeaderActionShow);
							log4j
									.info("The user link is listed on the User Link "
											+ "list screen with the following column: Show ");
						} catch (AssertionError Ae) {
							log4j
									.info("The user link is NOT listed on the User Link "
											+ "list screen with the following column: Show "
											+ Ae);
							strReason = "The user link is NOT listed on the User Link "
									+ "list screen with the following column: Show "
									+ Ae;

						}

					}

					if ((selenium
							.isElementPresent("//div[@id='mainContainer']/table/"
									+ "thead/tr/th[1][text()='Action']/ancestor"
									+ "::table/tbody/tr/td[2][text()='"
									+ strLinkLable
									+ "']"
									+ "/parent::tr/td[1]/a[4][text()='Up']") == true)
							&& (selenium
									.isElementPresent("//div[@id='mainContainer']/table/"
											+ "thead/tr/th[1][text()='Action']/ancestor"
											+ "::table/tbody/tr/td[2][text()='"
											+ strLinkLable
											+ "']"
											+ "/parent::tr/td[1]/a[5][text()='Down']") == false)) {

						selenium.click("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLinkLable + "']"
								+ "/parent::tr/td[1]/a[4][text()='Up']");
						selenium.waitForPageToLoad(gstrTimeOut);

					}

					try {

						assertEquals(
								selenium
										.getText("//div[@id='mainContainer']/table/"
												+ "thead/tr/th[1][text()='Action']/ancestor"
												+ "::table/tbody/tr/td[2][text()='"
												+ strLinkLable
												+ "']"
												+ "/parent::tr/td[1]/a[4]"),
								strLablHeaderActionUp);
						log4j.info("The user link is listed on the User Link "
								+ "list screen with the following column: Up ");
					} catch (AssertionError Ae) {
						log4j
								.info("The user link is NOT listed on the User Link "
										+ "list screen with the following column: Up "
										+ Ae);
						strReason = "The user link is NOT listed on the User Link "
								+ "list screen with the following column: Up "
								+ Ae;

					}

					try {

						assertEquals(
								selenium
										.getText("//div[@id='mainContainer']/table/"
												+ "thead/tr/th[1][text()='Action']/ancestor"
												+ "::table/tbody/tr/td[2][text()='"
												+ strLinkLable
												+ "']"
												+ "/parent::tr/td[1]/a[5]"),
								strLablHeaderActionDown);
						log4j
								.info("The user link is listed on the User Link "
										+ "list screen with the following column: Down ");
					} catch (AssertionError Ae) {
						log4j
								.info("The user link is NOT listed on the User Link "
										+ "list screen with the following column: Down "
										+ Ae);
						strReason = "The user link is NOT listed on the User Link "
								+ "list screen with the following column: Down "
								+ Ae;

					}

				} catch (AssertionError Ae) {
					log4j.info("The user link is NOT listed on the User Link "
							+ "list screen with the following column: Action "
							+ Ae);
					strReason = "The user link is NOT listed on the User Link "
							+ "list screen with the following column: Action "
							+ Ae;

				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/thead/tr/th[2][text()='Link']/ancestor"
									+ "::table/tbody/tr/td[2][text()" + "='"
									+ strLinkLable + "']"));
					log4j
							.info("The user link is listed on the User Link "
									+ "list screen with the following column: Link: Label text  ");

				} catch (AssertionError Ae) {
					log4j
							.info("The user link is NOT listed on the User Link "
									+ "list screen with the following column: Link: Label text   "
									+ Ae);
					strReason = "The user link is NOT listed on the User Link "
							+ "list screen with the following column: Link: Label text   "
							+ Ae;

				}

				if (blnQuickLink) {

					try {
						assertEquals(
								selenium
										.getText("//div[@id='mainContainer']/table/thead/tr/th[3]"),
								"Show As");
						log4j
								.info("The user link is listed on the User Link "
										+ "list screen with the following column: Link: Show As  ");

						if (selenium
								.isElementPresent("//div[@id='mainContainer']/table/"
										+ "thead/tr/th[1][text()='Action']/ancestor"
										+ "::table/tbody/tr/td[2][text()='"
										+ strLinkLable
										+ "']"
										+ "/parent::tr/td[1]/a[3][text()='Show']")) {

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']"
												+ "/table/tbody/tr/td[2][text()="
												+ "'"
												+ strLinkLable
												+ "']/parent:"
												+ ":tr/td[3][text()='(Hide)']"));
								log4j
										.info("The user link is listed on the User Link "
												+ "list screen with the following column: Link: (Hide)");

							} catch (AssertionError Ae) {
								log4j
										.info("The user link is NOT listed on the User Link "
												+ "list screen with the following column: Link: (Hide)"
												+ Ae);
								strReason = "The user link is NOT listed on the User Link "
										+ "list screen with the following column: Link:(Hide)"
										+ Ae;

							}
						} else {

							selenium
									.click("//div[@id='mainContainer']/table/"
											+ "thead/tr/th[1][text()='Action']/ancestor"
											+ "::table/tbody/tr/td[2][text()='"
											+ strLinkLable
											+ "']"
											+ "/parent::tr/td[1]/a[3][text()='Show']");
							selenium.waitForPageToLoad(gstrTimeOut);

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']"
												+ "/table/tbody/tr/td[2][text()="
												+ "'"
												+ strLinkLable
												+ "']/parent:"
												+ ":tr/td[3][text()='Quick Link']"));
								log4j
										.info("The user link is listed on the User Link "
												+ "list screen with the following column: Link: Quick Link");

							} catch (AssertionError Ae) {
								log4j
										.info("The user link is NOT listed on the User Link "
												+ "list screen with the following column: Link: Quick Link"
												+ Ae);
								strReason = "The user link is NOT listed on the User Link "
										+ "list screen with the following column: Link: Quick Link"
										+ Ae;

							}

						}

					} catch (AssertionError Ae) {
						log4j
								.info("The user link is NOT listed on the User Link "
										+ "list screen with the following column: Link: Show As   "
										+ Ae);
						strReason = "The user link is NOT listed on the User Link "
								+ "list screen with the following column:Show As "
								+ Ae;

					}

				} else {

					try {
						assertEquals(
								selenium
										.getText("//div[@id='mainContainer']/table/thead/tr/th[3]"),
								"Show As");
						log4j
								.info("The user link is listed on the User Link "
										+ "list screen with the following column: Link: Show As  ");

						if (selenium
								.isElementPresent("//div[@id='mainContainer']/table/"
										+ "thead/tr/th[1][text()='Action']/ancestor"
										+ "::table/tbody/tr/td[2][text()='"
										+ strLinkLable
										+ "']"
										+ "/parent::tr/td[1]/a[3][text()='Show']")) {

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']"
												+ "/table/tbody/tr/td[2][text()="
												+ "'"
												+ strLinkLable
												+ "']/parent:"
												+ ":tr/td[3][text()='(Hide)']"));
								log4j
										.info("The user link is listed on the User Link "
												+ "list screen with the following column: Link: (Hide)");

							} catch (AssertionError Ae) {
								log4j
										.info("The user link is NOT listed on the User Link "
												+ "list screen with the following column: Link: (Hide)"
												+ Ae);
								strReason = "The user link is NOT listed on the User Link "
										+ "list screen with the following column: Link:(Hide)"
										+ Ae;

							}
						} else {


							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']"
												+ "/table/tbody/tr/td[2][text()="
												+ "'"
												+ strLinkLable
												+ "']/parent:"
												+ ":tr/td[3][text()='User Link']"));
								log4j
										.info("The user link is listed on the User Link "
												+ "list screen with the following column: Link: User Link");

							} catch (AssertionError Ae) {
								log4j
										.info("The user link is NOT listed on the User Link "
												+ "list screen with the following column: Link: User Link"
												+ Ae);
								strReason = "The user link is NOT listed on the User Link "
										+ "list screen with the following column: Link: User Link"
										+ Ae;

							}

						}

					} catch (AssertionError Ae) {
						log4j
								.info("The user link is NOT listed on the User Link "
										+ "list screen with the following column: Link: Show As   "
										+ Ae);
						strReason = "The user link is NOT listed on the User Link "
								+ "list screen with the following column:Show As "
								+ Ae;

					}

				}

			} catch (AssertionError Ae) {

				strReason = "User Link List page is NOT displayed" + Ae;
				log4j.info("User Link List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("verifyLinkHeadersInUserLinkListPage function failed"
					+ e);
			strReason = "verifyLinkHeadersInUserLinkListPage function failed"
					+ e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on Show link and verify
	'Precondition	:None
	'Arguments		:selenium,strLablText,strShowAs
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String showTheLinkAndVerify(Selenium selenium, String strLablText,String strShowAs) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium
			.click("//div[@id='mainContainer']/table/tbody"
					+ "/tr/td[text()='"
					+ strLablText
					+ "']/parent::tr/td[1]/a[text()='Show']");
			selenium.waitForPageToLoad(gstrTimeOut);

			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody"
									+ "/tr/td[text()='"
									+ strLablText
									+ "']/parent::tr/td[1]/a[3][text()='Hide']"));

					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody"
								+ "/tr/td[text()='"
								+ strLablText
								+ "']/parent::tr/td[1]/a[3][text()='Hide']"));
				log4j.info("The link 'Show' is changed to 'Hide'");

				
			} catch (AssertionError Ae) {

				strReason = "The link 'Show' is NOT changed to 'Hide'";
				log4j.info("The link 'Show' is NOT changed to 'Hide'");
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody"
								+ "/tr/td[text()='"
								+ strLablText
								+ "']/parent::tr/td[3][text()='"+strShowAs+"']"));
				log4j.info("Show As is displayed as "+strShowAs);

				
			} catch (AssertionError Ae) {

				strReason = strReason+" Show As is NOT displayed as "+strShowAs;
				log4j.info("Show As is NOT displayed as "+strShowAs);
			}

			
		} catch (Exception e) {
			log4j.info("showTheLinkAndVerify function failed" + e);
			strReason = "showTheLinkAndVerify function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String openImgLinkAndVerifyUsrLink(Selenium selenium, String strURL,String strTitle) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
						
			selenium.mouseOver("link=User Links");
			try{
				assertEquals("",selenium.getText("//a[@class='clearFix'][contains(@onclick,'"+strURL+"')]/span"));
				log4j.info("Only the image is displayed without any text.");
				selenium.click("//a[@class='clearFix'][contains(@onclick,'"+strURL+"')]/img");
				
				selenium.waitForPopUp("", gstrTimeOut);
				selenium.selectWindow("name=undefined");
				
				try {
					assertEquals(strTitle, selenium.getTitle());
					log4j.info("The URL is opened in a new window");
				} catch (AssertionError Ae) {
					strReason = "The URL is NOT opened in a new window";
					log4j.info("The URL is NOT opened in a new window");
				}

			} catch (AssertionError Ae) {
				strReason = "Image is displayed with text.";
				log4j.info("Image is displayed with text.");
			}
		} catch (Exception e) {
			log4j.info("openImgLinkAndVerifyUsrLink function failed" + e);
			strReason = "openImgLinkAndVerifyUsrLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Delete the user link
	'Precondition	:None
	'Arguments		:selenium,strURL
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String deleteUsrLink(Selenium selenium, String strURL) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			selenium.selectWindow("");
			selenium.click("//div[@id='mainContainer']/table/tbody"
								+ "/tr/td[text()='"
								+ strURL
								+ "']/parent::tr/td[1]/a[text()='Delete']");
			
			selenium.waitForPageToLoad(gstrTimeOut);
			
		} catch (Exception e) {
			log4j.info("deleteUsrLink function failed" + e);
			strReason = "deleteUsrLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String openImgLinkAndVerifyUsrLinkByUsrLinkName(Selenium selenium,
			String strURL, String strTitle,String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Show']")) {

				selenium.click("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
						+ "/parent::tr/td[1]/a[3]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}

			selenium.mouseOver("link=User Links");

			selenium.click("//a[@class='clearFix']/span[text()='" + strLableText
					+ "']");

			selenium.waitForPopUp("", gstrTimeOut);
			selenium.selectWindow("name=undefined");

			try {
				assertEquals(strTitle, selenium.getTitle());
				log4j.info("The URL is opened in a new window");
				selenium.close();
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				strReason = "The URL is NOT opened in a new window";
				log4j.info("The URL is NOT opened in a new window");
			}

		} catch (Exception e) {
			log4j.info("openImgLinkAndVerifyUsrLink function failed" + e);
			strReason = "openImgLinkAndVerifyUsrLink function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String createUserLinkFirefoxForForm(Selenium selenium, String strLablText,
			String strExternalURL,boolean blnURL,boolean blnFORM, String strFormTempTitleOF,
			boolean blnQuicklaunch,
			String strUploadFilePath, boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("css=input[value='Create New User Link']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Create New User Link", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);


			try {
				assertEquals("Create New User Link", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New User Link page is displayed");

				// Enter lable name
				selenium.type(propElementDetails
						.getProperty("UserLink.LinkName"), strLablText);

				selenium.type(propElementDetails
						.getProperty("UserLink.Document"), strUploadFilePath);
				
				if(blnURL)
				{
					selenium.click("css=[name='linkType'][value='LINK']");
					selenium.type(propElementDetails
							.getProperty("UserLink.ExternalURL"), strExternalURL);
				}
				
				if(blnFORM)
				{
					selenium.click("css=[name='linkType'][value='FORM']");
					selenium.select("name=questionaireID", "label="+strFormTempTitleOF);
				}

				if (blnQuicklaunch) {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserLink.QuickLink"));

					}
				}

				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("UserLink.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try{
							assertEquals("User Link List", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));

							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
							
						}catch(Exception e){
						
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);

					try {
						assertEquals("User Link List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("User Link List page is displayed");

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[text()='"
											+ strLablText
											+ "']"));

							log4j
									.info("User Link is displayed in User link list page");

						} catch (AssertionError Ae) {

							strReason = "User Link is NOT displayed in User link list page";
							log4j
									.info("User Link is NOT displayed in User link list page");
						}

					} catch (AssertionError Ae) {

						strReason = "User Link List page is NOT displayed" + Ae;
						log4j.info("User Link List page is NOT displayed" + Ae);
					}
				}

			} catch (AssertionError Ae) {

				strReason = "Create New User Link page is NOT displayed";
				log4j.info("Create New User Link page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("createUserLinkFirefox function failed" + e);
			strReason = "createUserLinkFirefox function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Verify data retained in the edit user link page
	'Precondition	:None
	'Arguments		:selenium,strLablText,strImgFile,strUrlOfExternalLink,blnQuickLink
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String verDataRetainInEditUsrLinkPageForForm(Selenium selenium,
			String strLablText, String strTemplateTitle,
			boolean blnQuickLink) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			//Label
			try {
				assertEquals(selenium.getValue("css=input[name='mouseOver']"),
						strLablText);
				log4j.info("Label  " + strLablText
						+ " is retained in edit user page");
				
			} catch (AssertionError Ae) {
				log4j.info("Label  " + strLablText
						+ " is NOT retained in edit user page" + Ae);
				strReason = "Label  " + strLablText
						+ " is NOT retained in edit user page" + Ae;
			}
			
			//Image file
			
			try {
				assertFalse(selenium.isElementPresent("css=input[name='document']"));
				log4j.info("Image upload option "
						+ " is NOT available in edit user page");
				
			} catch (AssertionError Ae) {
				log4j.info("Image upload option "
						+ " is available in edit user page");
				strReason = "Image upload option "
						+ " is available in edit user page";
			}
			
			
			// Link Type
			try {
				assertTrue(selenium.isTextPresent(strTemplateTitle));
				log4j.info("Form template selected is present in EMResource form");
			} catch (AssertionError Ae) {
				log4j.info("Form template selected is present in EMResource form");
				strReason = "Form template selected is present in EMResource form";
			}
			
			//Quick Link

			if(blnQuickLink){
				
				try {
					assertTrue(selenium.isChecked("css=input[name='quickLink']"));
					log4j.info("Quick link is retained in edit user page");
					
				} catch (AssertionError Ae) {
					log4j.info("Quick link is NOT retained in edit user page");
					strReason = "Quick link is NOT retained in edit user page";
				}
				
			}else{
				try {
					assertFalse(selenium.isChecked("css=input[name='quickLink']"));
					log4j.info("Quick link is retained in edit user page");
					
				} catch (AssertionError Ae) {
					log4j.info("Quick link is NOT retained in edit user page");
					strReason = "Quick link is NOT retained in edit user page";
				}
				
			}
			
			
		} catch (Exception e) {
			log4j.info("navToEditLinkPage function failed" + e);
			strReason = "navToEditLinkPage function failed" + e;
		}
		return strReason;
	}
	
	
	
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String varOtherFldsInUserLink(Selenium selenium, String strLablText,String strShow,
			String strDestnURL,String strImgSize,String strFileSize) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
						
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td" +
								"[text()='"+strLablText+"']/parent::tr/td[3][text()='"+strShow+"']"));
				log4j.info("Show as: User Link is displayed");
			} catch (AssertionError Ae) {				
				strReason = "Show as: User Link is NOT displayed";
				log4j.info("Show as: User Link is NOT displayed");
			}
			
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td" +
								"[text()='"+strLablText+"']/parent::tr/td[4]/img"));
				log4j.info("Image: The attached image is displayed");
			} catch (AssertionError Ae) {				
				strReason = "Image: The attached image is NOT displayed";
				log4j.info("Image: The attached image is NOT displayed");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td" +
								"[text()='"+strLablText+"']/parent::tr/td[5][text()='"+strDestnURL+"']"));
				log4j.info("Destination URL: Updated Form name is displayed");
			} catch (AssertionError Ae) {				
				strReason = "Destination URL: Updated Form name is NOT displayed";
				log4j.info("Destination URL: Updated Form name is NOT displayed");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td" +
								"[text()='"+strLablText+"']/parent::tr/td[6][text()='"+strImgSize+"']"));
				log4j.info("Image size: Attached file size (in pixels) is displayed");
			} catch (AssertionError Ae) {				
				strReason = "Image size: Attached file size (in pixels) is NOT displayed";
				log4j.info("Image size: Attached file size (in pixels) is NOT displayed");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td" +
								"[text()='"+strLablText+"']/parent::tr/td[7][text()='"+strFileSize+"']"));
				log4j.info("File size: Attached file size (in bytes) is displayed");
			} catch (AssertionError Ae) {				
				strReason = "File size: Attached file size (in bytes) is NOT displayed";
				log4j.info("File size: Attached file size (in bytes) is NOT displayed");
			}
			
		} catch (Exception e) {
			log4j.info("varOtherFldsInUserLink function failed" + e);
			strReason = "varOtherFldsInUserLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String navTocreateUserLink(Selenium selenium) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails
					.getProperty("UserLink.CreateNewUsrLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Create New User Link", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Create New User Link", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New User Link page is displayed");
			} catch (AssertionError Ae) {

				strReason = "Create New User Link page is NOT displayed";
				log4j.info("Create New User Link page is NOT displayed");
			}
		} catch (Exception e) {
			log4j.info("navTocreateUserLink function failed" + e);
			strReason = "navTocreateUserLink function failed" + e;
		}
		return strReason;
	}

	/***********************************************************************
	'Description	:Verify data retained in the edit user link page
	'Precondition	:None
	'Arguments		:selenium,strLablText,strImgFile,strUrlOfExternalLink,blnQuickLink
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String verFieldsInCreateUserLink(Selenium selenium) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr/td[text()='Label Text:']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// Label
			try {

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']"
								+ "/form/table/tbody/tr/td[text()='Label Text:']"));
				log4j
						.info("Label Text field is displayed in Create new user user link page");
			} catch (AssertionError Ae) {
				log4j
						.info("Label Text field is NOT displayed in Create new user user link page");
				strReason = "Label Text field is NOT displayed in Create new user user link page";
			}

			// Image file

			try {
				assertTrue(selenium
						.isElementPresent("css=input[name='document']"));
				log4j.info("Image upload option "
						+ " is  available in Create new user user link page");

			} catch (AssertionError Ae) {
				log4j
						.info("Image upload option "
								+ " is NOT available in Create new user user link page");
				strReason = "Image upload option "
						+ " is NOT available in Create new user user link page";
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Select type of link to create:']"));
				log4j.info("Select type of link to create is available");

			} catch (AssertionError Ae) {
				log4j.info("Select type of link to create is available");
				strReason = "Select type of link to create is available";
			}
			// external url
			try {
				assertTrue(selenium
						.isElementPresent("css=[name='linkType'][value='LINK']"));
				assertTrue(selenium
						.isChecked("css=[name='linkType'][value='LINK']"));
				log4j
						.info("URL for an external site (which is selected by default)  "
								+ " is  available in Create new user user link page");

			} catch (AssertionError Ae) {
				log4j
						.info("URL for an external site (which is selected by default)  "
								+ " is NOT available in Create new user user link page");
				strReason = "URL for an external site (which is selected by default)  "
						+ " is   NOT available in Create new user user link page";
			}

			// EMResource Form
			try {
				assertTrue(selenium
						.isElementPresent("css=[name='linkType'][value='FORM']"));
				assertTrue(selenium
						.isChecked("css=[name='linkType'][value='LINK']"));
				log4j.info("EMResource Form "
						+ " is  available in Create new user user link page");

			} catch (AssertionError Ae) {
				log4j
						.info("EMResource Form   "
								+ " is NOT available in Create new user user link page");
				strReason = "EMResource Form "
						+ " is   NOT available in Create new user user link page";
			}

			// Quick Link

			try {
				assertTrue(selenium
						.isElementPresent("css=input[name='quickLink']"));
				log4j
						.info("Quick link is available in Create new user user link page");

			} catch (AssertionError Ae) {
				log4j
						.info("Quick link is  available in Create new user user link page");
				strReason = "Quick link is  available in Create new user user link page";
			}

		} catch (Exception e) {
			log4j.info("navToEditLinkPage function failed" + e);
			strReason = "navToEditLinkPage function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String fillcreateUserLinkFldsFirefox(Selenium selenium,
			String strLablText, String strExternalURL, boolean blnQuicklaunch,
			String strUploadFilePath, boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("UserLink.LinkName")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// Enter lable name
			selenium.type(propElementDetails.getProperty("UserLink.LinkName"),
					strLablText);

			selenium.type(propElementDetails.getProperty("UserLink.Document"),
					strUploadFilePath);

			selenium.type(propElementDetails
					.getProperty("UserLink.ExternalURL"), strExternalURL);

			if (blnQuicklaunch) {
				if (selenium.isChecked(propElementDetails
						.getProperty("UserLink.QuickLink")) == false) {
					selenium.click(propElementDetails
							.getProperty("UserLink.QuickLink"));

				}
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("UserLink.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertEquals("User Link List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				try {
					assertEquals("User Link List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("User Link List page is displayed");

					intCnt = 0;
					do {
						try {

							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[text()='"
											+ strLablText
											+ "']"));
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCnt++;

						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					} while (intCnt < 60);

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody"
										+ "/tr/td[text()='"
										+ strLablText
										+ "']"));
						log4j.info("User Link is displayed in User link list page");
					} catch (AssertionError Ae) {
						strReason = "User Link is NOT displayed in User link list page";
						log4j.info("User Link is NOT displayed in User link list page");
					}

				} catch (AssertionError Ae) {

					strReason = "User Link List page is NOT displayed" + Ae;
					log4j.info("User Link List page is NOT displayed" + Ae);
				}
			}

		} catch (Exception e) {
			log4j.info("fillcreateUserLinkFldsFirefox function failed" + e);
			strReason = "fillcreateUserLinkFldsFirefox function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String ShowQuickLinkAndVerify(Selenium selenium,String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Show']")) {

				selenium.click("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
						+ "/parent::tr/td[1]/a[3]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			
			assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Hide']"));
			log4j.info("The link ''Show'' is changed to ''Hide'' and ''Quick Link'' " +
					"is displayed under the column ''Show As'' ");
			
			try {
				assertTrue(selenium.isElementPresent("//div[@id='quickUserLinks-in']/a/img"));
				log4j.info("The image is displayed at the top of the screen (as a ''Quick Link'')...");
				
			} catch (AssertionError Ae) {
				log4j.info("The image is not displayed at the top of the screen (as a ''Quick Link'').");
			}

			try {
				assertTrue(selenium.isElementPresent("css=img[alt=\""+strLableText+"\"]"));
				log4j.info("The tool tip on the image displays the label text provided");
				log4j.info("The user link is not displayed in the list.");
			} catch (AssertionError Ae) {
				strReason = "The tool tip on the image NOT displays the label text provided";
				log4j.info("The tool tip on the image NOT displays the label text provided");
			}

		} catch (Exception e) {
			log4j.info("ShowQuickLinkAndVerify function failed....." + e);
			strReason = "ShowQuickLinkAndVerify function failed....." + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String openQuickLinkAndVerifyUrl(Selenium selenium,
			String strURL, String strTitle,String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			selenium.click("css=img[alt=\""+strLableText+"\"]");
			selenium.waitForPopUp("", gstrTimeOut);

			selenium.waitForPopUp("", gstrTimeOut);
			selenium.selectWindow("name=undefined");

			try {
				assertEquals(strTitle, selenium.getTitle());
				log4j.info("The URL is opened in a new window");
				selenium.close();
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				strReason = "The URL is NOT opened in a new window";
				log4j.info("The URL is NOT opened in a new window");
			}

		} catch (Exception e) {
			log4j.info("openImgLinkAndVerifyUsrLink function failed" + e);
			strReason = "openImgLinkAndVerifyUsrLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String HideQuickLinkAndVerify(Selenium selenium,String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Hide']")) {

				selenium.click("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
						+ "/parent::tr/td[1]/a[3]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			
			assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Show']"));
			log4j.info("The link ''Show'' is changed to ''Hide'' and ''Quick Link'' " +
					"is displayed under the column ''Show As'' ");
			
			try {
				assertFalse(selenium.isElementPresent("//div[@id='quickUserLinks-in']/a/img"));
				log4j.info("The image is NOT displayed at the top of the screen (as a ''Quick Link'').");
			} catch (AssertionError Ae) {
				log4j.info("The image is displayed at the top of the screen (as a ''Quick Link'').");
			}
			
		} catch (Exception e) {
			log4j.info("HideQuickLinkAndVerify function failed" + e);
			strReason = "HideQuickLinkAndVerify function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:verifyLinkHeadersInUserLinkListPage
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String verifyLinkHeadersLinks(Selenium selenium,
			String strLinkLable, String strLablHeaderAction,
			String strLablHeaderActionEdit, String strLablHeaderActionDelete, String strLablHeaderActionShow,
			String strLablHeaderActionUp) {

		String strReason = "";

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			try {
				assertEquals(
						selenium.getText("//div[@id='mainContainer']/table/thead/tr/th[1]"),
						strLablHeaderAction);
				log4j.info("The user link is listed on the User Link "
						+ "list screen with the following column: Action ");
			} catch (AssertionError Ae) {
				log4j.info("The user link is listed on the User Link "
						+ "list screen with the following column: Action " + Ae);
				strReason = "The user link is listed on the User Link "
						+ "list screen with the following column: Action" + Ae;

			}

			try {
				assertEquals(
						selenium.getText("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLinkLable + "']"
								+ "/parent::tr/td[1]/a[1]"),
						strLablHeaderActionEdit);
				log4j.info("The user link is listed on the User Link "
						+ "list screen with the following column: "+strLablHeaderActionEdit);
			} catch (AssertionError Ae) {
				log4j.info("The user link is NOT listed on the User Link "
						+ "list screen with the following column: "+strLablHeaderActionEdit + Ae);
				strReason = strReason+" The user link is NOT listed on the User Link "
						+ "list screen with the following column: "+strLablHeaderActionEdit + Ae;
			}

			try {
				assertEquals(
						selenium.getText("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLinkLable + "']"
								+ "/parent::tr/td[1]/a[2]"),
						strLablHeaderActionDelete);
				log4j.info("The user link is listed on the User Link "
						+ "list screen with the following column: "+strLablHeaderActionDelete);
			} catch (AssertionError Ae) {
				log4j.info("The user link is NOT listed on the User Link "
						+ "list screen with the following column: " +strLablHeaderActionDelete+ Ae);
				strReason = strReason+" The user link is NOT listed on the User Link "
						+ "list screen with the following column: " +strLablHeaderActionDelete+ Ae;

			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLinkLable + "']"
								+ "/parent::tr/td[1]/a[3][text()='"+strLablHeaderActionShow+"']"));
				log4j.info("The created user link is listed on the User Link list screen with the: "+strLablHeaderActionShow);
			} catch (AssertionError Ae) {
				log4j.info("The created user link is NOT listed on the User Link list screen with the: "+strLablHeaderActionShow);
				strReason = strReason+" The created user link is NOT listed on the User Link list screen with the: "+strLablHeaderActionShow;

			}
			
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/"
									+ "thead/tr/th[1][text()='Action']/ancestor"
									+ "::table/tbody/tr/td[2][text()='"
									+ strLinkLable
									+ "']"
									+ "/parent::tr/td[1]/a[4][text()='"+strLablHeaderActionUp+"']"));
				log4j.info("The created user link is listed on the User Link list screen with the: "+strLablHeaderActionUp);
			} catch (AssertionError Ae) {
				log4j.info("The created user link is NOT listed on the User Link list screen with the: "+strLablHeaderActionUp);
				strReason = strReason+" The created user link is NOT listed on the User Link list screen with the: "+strLablHeaderActionUp;
			}

		} catch (Exception e) {
			log4j.info("verifyLinkHeadersForQuickLink function failed"
					+ e);
			strReason = "verifyLinkHeadersForQuickLink function failed"
					+ e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String varUserLink(Selenium selenium, String strLableText,
			boolean blUserLink) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			if (blUserLink) {
				selenium.mouseOver("link=User Links");
				try {
					assertTrue(selenium
							.isElementPresent("//a[@class='clearFix']/span[text()='"
									+ strLableText + "']"));
					log4j
							.info("user link is displayed in the list with the label text.");
				} catch (Exception e) {
					log4j
							.info("user link is NOT displayed in the list with the label text.. ");
					strReason = "user link is NOT displayed in the list with the label text.. ";
				}
			} else {
				if (selenium.isElementPresent("link=User Links")) {
					selenium.mouseOver("link=User Links");
				}
				try {
					assertFalse(selenium
							.isElementPresent("//a[@class='clearFix']/span[text()='"
									+ strLableText + "']"));
					log4j
							.info("user link is NOT displayed in the list with the label text.");
				} catch (Exception e) {
					log4j
							.info("user link is displayed in the  list with the label text.");
					strReason = "user link is displayed in the  list with the label text. ";
				}
			}

		} catch (Exception e) {
			log4j.info("varUserLink function failed" + e);
			strReason = "varUserLink function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String clickOnHideAndVerifyShow(Selenium selenium,String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Hide']")) {

				selenium.click("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
						+ "/parent::tr/td[1]/a[3]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			
			assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Show']"));
			log4j.info("The link ''Show'' is changed to ''Hide'' and ''Quick Link'' " +
					"is displayed under the column ''Show As'' ");
			
		} catch (Exception e) {
			log4j.info("clickOnHideAndVerifyShow function failed" + e);
			strReason = "clickOnHideAndVerifyShow function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2012                                   <Name>
	************************************************************************/
	
	public String clickOnHideAndVerifyShowNew(Selenium selenium,
			String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Hide']")) {

				selenium.click("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText
						+ "']" + "/parent::tr/td[1]/a[3]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/"
									+ "thead/tr/th[1][text()='Action']/ancestor"
									+ "::table/tbody/tr/td[2][text()='"
									+ strLableText
									+ "']"
									+ "/parent::tr/td[1]/a[3][text()='Show']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLableText + "']"
								+ "/parent::tr/td[1]/a[3][text()='Show']"));
				log4j
						.info("The link 'Show' is changed to 'Hide' and 'Quick Link' "
								+ "is displayed under the column ''Show As'' ");
			} catch (AssertionError Ae) {
				log4j
						.info("The link 'Show' is NOT changed to 'Hide' and 'Quick Link' "
								+ "is displayed under the column 'Show As' ");

				strReason = "The link 'Show' is NOT changed to 'Hide' and 'Quick Link' "
						+ "is displayed under the column 'Show As' ";
			}

		} catch (Exception e) {
			log4j.info("clickOnHideAndVerifyShow function failed" + e);
			strReason = "clickOnHideAndVerifyShow function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Click on Show link and verify in right side User Link 
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:11-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String showTheLinkAndVerifyInSubNav(Selenium selenium, String strLablText) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody"
								+ "/tr/td[text()='"
								+ strLablText
								+ "']/parent::tr/td[1]/a[3][text()='Hide']")==false){
				
				selenium
				.click("//div[@id='mainContainer']/table/tbody"
						+ "/tr/td[text()='"
						+ strLablText
						+ "']/parent::tr/td[1]/a[text()='Show']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			}
			

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='topNav']" +
						"/div[@class='userLinks subNav']/div/a/span[text()='"+strLablText+"']"));
				log4j.info("User Link is displayed at the top right"
						+ " corner of the screen (in the menu header). 	");

			} catch (AssertionError Ae) {

				strReason = "User Link is NOT displayed at the top right"
						+ " corner of the screen (in the menu header). 	";
				log4j.info("User Link is NOT displayed at the top right"
						+ " corner of the screen (in the menu header). 	");
			}
			
			
		} catch (Exception e) {
			log4j.info("showTheLinkAndVerifyInSunNav function failed" + e);
			strReason = "showTheLinkAndVerifyInSunNav function failed" + e;
		}
		return strReason;
	}
	

	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String clickOnHideAndVerifyHideInShowAsCol(Selenium selenium,
			String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Hide']")) {

				selenium.click("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText
						+ "']" + "/parent::tr/td[1]/a[3]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/"
								+ "thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='"
								+ strLableText + "']"
								+ "/parent::tr/td[1]/a[3][text()='Show']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table"
								+ "/tbody/tr/td[text()='"
								+ strLableText
								+ "']/following-sibling::td[1][text()='(Hide)']"));

				log4j.info("The link ''Hide'' is changed to ''Show'' and the ''Hide'' is displayed " +
						"under the column ''Show As'' ");
			} catch (AssertionError Ae) {

				strReason = "The link ''Hide''is NOT changed to ''Show'' and the ''Hide'' is NOT"
						+ " displayed under the column ''Show As'' ";
				log4j.info("The link ''Hide'' is NOT changed to ''Show'' and the ''Hide'' is NOT "
						+ "displayed under the column ''Show As'' ");
			}
		} catch (Exception e) {
			log4j.info("clickOnHideAndVerifyHideInShowAsCol function failed" + e);
			strReason = "clickOnHideAndVerifyHideInShowAsCol function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String createUserLinkFirefoxNew(Selenium selenium,
			String strLablText, String strExternalURL, boolean blnQuicklaunch,
			String strUploadFilePath, boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("css=input[value='Create New User Link']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create New User Link",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Create New User Link page is displayed");

				// Enter lable name
				selenium.type(
						propElementDetails.getProperty("UserLink.LinkName"),
						strLablText);

				selenium.type(
						propElementDetails.getProperty("UserLink.Document"),
						strUploadFilePath);

				selenium.type(
						propElementDetails.getProperty("UserLink.ExternalURL"),
						strExternalURL);

				if (blnQuicklaunch) {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserLink.QuickLink"));

					}
				}

				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("UserLink.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("User Link List",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("User Link List page is displayed");

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='�']"));

							log4j.info("The created user link is listed on the User Link list screen" +
									" without any data under the ''Link'' column. ");

						} catch (AssertionError Ae) {

							strReason = "The created user link is listed on the User Link list screen" +
									" without any data under the ''Link'' column. ";
							log4j.info("The created user link is listed on the User Link list screen" +
									" without any data under the ''Link'' column. ");
						}

					} catch (AssertionError Ae) {

						strReason = "User Link List page is NOT displayed" + Ae;
						log4j.info("User Link List page is NOT displayed" + Ae);
					}
				}

			} catch (AssertionError Ae) {

				strReason = "Create New User Link page is NOT displayed";
				log4j.info("Create New User Link page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("createUserLinkFirefoxNew function failed" + e);
			strReason = "createUserLinkFirefoxNew function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:fillUserLinkFieldsInEditUserPage
	'Precondition	:None
	'Arguments		:selenium,strLablText,strExternalURL,blnQuicklaunch,strUploadFilePath,blnSave
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String ProvDataForLabelTextFld(Selenium selenium,
			String strLablText,boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Enter lable name
			selenium.type(propElementDetails.getProperty("UserLink.LinkName"),
					strLablText);

			

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("UserLink.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("User Link List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("User Link List page is displayed");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody"
										+ "/tr/td[text()='"
										+ strLablText
										+ "']"));

						log4j
								.info("User Link is displayed in User link list page");

					} catch (AssertionError Ae) {

						strReason = "User Link is NOT displayed in User link list page";
						log4j
								.info("User Link is NOT displayed in User link list page");
					}

				} catch (AssertionError Ae) {

					strReason = "User Link List page is NOT displayed" + Ae;
					log4j.info("User Link List page is NOT displayed" + Ae);
				}
			}

		} catch (Exception e) {
			log4j.info("fillUserLinkFieldsInEditUserPage function failed" + e);
			strReason = "fillUserLinkFieldsInEditUserPage function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:Delete all user links in the page
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String deleteAllUserLink(Selenium selenium){
		String strReason="";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			while (selenium
					.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[1]/a[2]")) {
				selenium
						.click("//div[@id='mainContainer']/table/tbody/tr/td[1]/a[2]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
				try {

					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[1]/a[2]"));
					log4j.info("All user links are deleted");

				} catch (AssertionError Ae) {

					strReason = "All user links are NOT deleted";
					log4j.info("All user links are NOT deleted");
				}
		

		} catch (Exception e) {
			log4j.info("deleteAllUserLink function failed" + e);
			strReason = "deleteAllUserLink function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:click on save button and verify that appropriate
	 				 error message is displayed
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:12-Sep-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String clkOnSaveAndVfyErrMsg(Selenium selenium) {

		String strReason = "";

		try {
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails
					.getProperty("UserLink.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try{
				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsgTitle")));
			
				assertEquals("No more than six \"quick links\" can be shown.",
						selenium.getText("//div[@id='mainContainer']/div/ul/li"));
				log4j.info("An error message stating no more than six quick links can be shown is displayed.");
				log4j.info("The quick link is not created. ");
				
			}catch (AssertionError Ae) {

				strReason = "Error message is NOT displayed";
				log4j.info("Error message is NOT displayed");
			}
			

		} catch (Exception e) {
			log4j.info("clkOnSaveAndVfyErrMsg function failed" + e);
			strReason = "clkOnSaveAndVfyErrMsg function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify User Link is created
	'Precondition	:None
	'Arguments		:selenium,strLablText
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String createUserLinkForForm(Selenium selenium, String strLablText,
			String strExternalURL, boolean blnQuicklaunch,
			String strAutoFilePath, String strUploadFilePath,
			String strAutoFileName, boolean blnURL, boolean blnFORM,
			String strFormTempTitleOF, boolean blnSave) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("css=input[value='Create New User Link']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create New User Link",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Create New User Link page is displayed");

				// Enter lable name
				selenium.type(
						propElementDetails.getProperty("UserLink.LinkName"),
						strLablText);

				selenium.type(
						propElementDetails.getProperty("UserLink.Document"),
						strUploadFilePath);

				if (blnURL) {
					selenium.click("css=[name='linkType'][value='LINK']");
					selenium.type(propElementDetails
							.getProperty("UserLink.ExternalURL"),
							strExternalURL);
				}

				if (blnFORM) {
					selenium.click("css=[name='linkType'][value='FORM']");
					selenium.select("name=questionaireID", "label="
							+ strFormTempTitleOF);
				}

				if (blnQuicklaunch) {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserLink.QuickLink"));

					}
				}

				// Upload Image File
				String strArgs[] = { strAutoFilePath, strUploadFilePath };
				// Auto it to upload the file
				Runtime.getRuntime().exec(strArgs);
				selenium.windowFocus();
				selenium.windowFocus();
				// click on Browse
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Browse"));
				// wait for pop up to appear
				Thread.sleep(5000);
				// Wait for autoit file to finish execution
				String strProcess = "";
				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 120);

				selenium.type(
						propElementDetails.getProperty("UserLink.ExternalURL"),
						strExternalURL);

				if (blnQuicklaunch) {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserLink.QuickLink")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserLink.QuickLink"));

					}
				}

				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("UserLink.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("User Link List",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("User Link List page is displayed");

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[text()='"
											+ strLablText
											+ "']"));

							log4j.info("User Link is displayed in User link list page");

						} catch (AssertionError Ae) {

							strReason = "User Link is NOT displayed in User link list page";
							log4j.info("User Link is NOT displayed in User link list page");
						}

					} catch (AssertionError Ae) {

						strReason = "User Link List page is NOT displayed" + Ae;
						log4j.info("User Link List page is NOT displayed" + Ae);
					}
				}
			} catch (AssertionError Ae) {

				strReason = "Create New User Link page is NOT displayed";
				log4j.info("Create New User Link page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("createUserLink function failed" + e);
			strReason = "createUserLink function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String ClkOnShowQuickLink(Selenium selenium,String strLableText) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("//div[@id='mainContainer']/table/"
					+ "thead/tr/th[1][text()='Action']/ancestor"
					+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
					+ "/parent::tr/td[1]/a[3][text()='Show']")) {

				selenium.click("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
						+ "/parent::tr/td[1]/a[3]");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			
			
			
			try {
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/"
						+ "thead/tr/th[1][text()='Action']/ancestor"
						+ "::table/tbody/tr/td[2][text()='" + strLableText + "']"
						+ "/parent::tr/td[1]/a[3][text()='Hide']"));
				log4j.info("The link ''Show'' is changed to ''Hide'' and ''Quick Link'' " +
						"is displayed under the column ''Show As'' ");
				
			} catch (AssertionError Ae) {
				log4j.info("The link ''Show'' is NOT changed to ''Hide'' and ''Quick Link'' " +
						"is displayed under the column ''Show As''");
			}

			
		} catch (Exception e) {
			log4j.info("ShowQuickLinkAndVerify function failed....." + e);
			strReason = "ShowQuickLinkAndVerify function failed....." + e;
		}
		return strReason;
	}
	
	//start//verifyQuickLinkAtTheTopOfTheScreen//
	/*******************************************************************************************
	' Description		: Verify quick link image is present at the top of the screen
	' Precondition		: N/A 
	' Arguments			: selenium, strLabelText
	' Returns			: String 
	' Date				: 18/09/2013
	' Author			: Suhas
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyQuickLinkAtTheTopOfTheScreen(Selenium selenium, String strLabelText) throws Exception
	{
		String strReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			
			try {
				assertTrue(selenium.isElementPresent("//div[@id='quickUserLinks-in']/a/img"));
				log4j.info("The image is displayed at the top of the screen (as a ''Quick Link'')...");
				
			} catch (AssertionError Ae) {
				log4j.info("The image is not displayed at the top of the screen (as a ''Quick Link'').");
				strReason="The image is not displayed at the top of the screen (as a ''Quick Link'').";
			}

			try {
				assertTrue(selenium.isElementPresent("css=img[alt=\""+strLabelText+"\"]"));
				log4j.info("The tool tip on the image displays the label text provided");				
			} catch (AssertionError Ae) {
				strReason = "The tool tip on the image NOT displays the label text provided";
				log4j.info("The tool tip on the image NOT displays the label text provided");
			}
			
		}catch(Exception e){
			log4j.info(e);
			strReason = "UserLinks.verifyQuickLinkAtTheTopOfTheScreen failed to complete due to " +strReason + "; " + e.toString();
		}

		return strReason;
	}
	//end//verifyQuickLinkAtTheTopOfTheScreen//
	
	/***********************************************************************
	'Description	:Select and deselect quick link
	'Precondition	:None
	'Arguments		:selenium,blnQuicklaunch
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String selAndDeselQuickLink(Selenium selenium, boolean blnQuicklaunch) {

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnQuicklaunch) {
				if (selenium.isChecked(propElementDetails
						.getProperty("UserLink.QuickLink")) == false) {
					selenium.click(propElementDetails
							.getProperty("UserLink.QuickLink"));
				}
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("UserLink.QuickLink"))) {
					selenium.click(propElementDetails
							.getProperty("UserLink.QuickLink"));
				}
			}
		} catch (Exception e) {
			log4j.info("selAndDeselQuickLink function failed" + e);
			strReason = "selAndDeselQuickLink function failed" + e;
		}
		return strReason;
	}
}