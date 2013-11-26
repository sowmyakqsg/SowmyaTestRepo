package com.qsgsoft.EMResource.shared;


import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/******************************************************************
' Description :This class includes common functions of Regions
' Precondition:
' Date		  :16-April-2012
' Author	  :QSG
'------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class Regions {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.Regions");

	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;
	
	public String gstrTimeOut ="";
	
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();

	ReadData rdExcel;
	
	/***********************************************************************
	'Description	:Verify region is allocated to user
	'Precondition	:None
	'Arguments		:selenium,strUserName,strRegionName
	'Returns		:String
	'Date	 		:16-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-April-2012                               <Name>
	************************************************************************/
	
	public String acessToRegnForUser(Selenium selenium, String strUserName,
			String[] strRegionNames) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			try {
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Users List is  displayed");
				
				selenium.click("//table[@id='tblUsers']/tbody/tr/td[text()='"
						+ strUserName
						+ "']/parent::tr/td[1]/a[text()='Regions']");
				

				selenium.waitForPageToLoad(gstrTimeOut);
				
				for (String s : strRegionNames) {
					
					try{
						if (selenium.isChecked(s) == false) {
							selenium.click(s);
						}
					}catch(Exception e){
						log4j.info(e);
					}
					
					
				}						
				

				selenium.click(propElementDetails
						.getProperty("EditUserRegions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Users List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Users List is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Users List is NOT displayed";
					log4j.info("Users List is NOT displayed");
				}
				
			} catch (AssertionError Ae) {
				strErrorMsg = "Users List is NOT displayed";
				log4j.info("Users List is NOT displayed");
			}
			
		} catch (Exception e) {
			log4j.info("acessToRegnForUser function failed" + e);
			strErrorMsg = "acessToRegnForUser function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	public String acessToRegnForUserWithRegionValue(Selenium selenium, String strUserName,
			String[] strRegionValue) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			
			
			int intCnt=0;
			do{
				try {

					assertEquals("Users List", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Users List is  displayed");
				
				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[text()='"
								+ strUserName
								+ "']/parent::tr/td[1]/a[text()='Regions']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				selenium.click("//table[@id='tblUsers']/tbody/tr/td[text()='"
						+ strUserName
						+ "']/parent::tr/td[1]/a[text()='Regions']");
				

				selenium.waitForPageToLoad(gstrTimeOut);

				for (String s : strRegionValue) {

					try {

						intCnt = 0;
						do {
							try {

								assertTrue(selenium
										.isElementPresent("css=input[name='regionID'][value='"
												+ s + "']"));
								break;
							} catch (AssertionError Ae) {
								Thread.sleep(1000);
								intCnt++;

							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						} while (intCnt < 60);

						if (selenium
								.isChecked("css=input[name='regionID'][value='"
										+ s + "']") == false) {
							selenium.click("css=input[name='regionID'][value='"
									+ s + "']");
						}
					} catch (Exception e) {
						log4j.info(e);
					}

				}

				selenium.click(propElementDetails
						.getProperty("EditUserRegions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				
				intCnt=0;
				do{
					try {

						assertEquals("Users List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				try {
					assertEquals("Users List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Users List is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Users List is NOT displayed";
					log4j.info("Users List is NOT displayed");
				}
				
			} catch (AssertionError Ae) {
				strErrorMsg = "Users List is NOT displayed";
				log4j.info("Users List is NOT displayed");
			}
			
		} catch (Exception e) {
			log4j.info("acessToRegnForUserWithRegionValue function failed" + e);
			strErrorMsg = "acessToRegnForUserWithRegionValue function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description: Navigation to HAvBED Reporting Schedule
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 18/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToHavBedRepScedulePage(Selenium selenium,String strState) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.select(propElementDetails.getProperty("Regions.stateAbbrev"), "label="+strState+"");
			selenium.click(propElementDetails.getProperty("SelectState.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("HAvBED Reporting Schedule", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'HAvBED Reporting Schedule' screen is displayed for the selected state. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("'HAvBED Reporting Schedule' screen is  NOT displayed for the selected state. ");
				lStrReason = "'HAvBED Reporting Schedule' screen is  NOT displayed for the selected state. ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/***********************************************************************
	'Description	:Navigating To'other region of other Region
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:18-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	************************************************************************/
	
	public String navOtherRegionsOFRegion(Selenium selenium,String strRegion) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Other Region Tab
			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.OtherRegionsLink"));
			// click other region link
			selenium.click("link=" + strRegion);
			selenium.waitForPageToLoad(gstrTimeOut);	
			
			try {
				assertEquals(strRegion, selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info(strRegion + " page is displayed");

			} catch (AssertionError Ae) {
				log4j.info(strRegion + " page is NOT displayed");
				strErrorMsg = strRegion + " page is NOT displayed";
			}
			
		} catch (Exception e) {
			log4j.info("navOtherRegionsOFRegion Function failed");
			strErrorMsg = "navOtherRegionsOFRegion Function failed"+e;
		}
		return strErrorMsg;
	}
	/*******************************************************************************************
	' Description: Navigation to Select Bed Reporting State screen
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 18/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToSelectBedRepState(Selenium selenium) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));
			selenium.click(propElementDetails.getProperty("Prop477"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Select Bed Reporting State", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Select Bed Reporting State' screen is displayed. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("'Select Bed Reporting State' screen is  NOT displayed.");
				lStrReason = "'Select Bed Reporting State' screen is  NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	 /*******************************************************************************************
	' Description : Fill mandatory fields of Manage system notification
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String fillFieldsOfManagSyseNotfctn(Selenium selenium,
			String strTitle, String strDescriptn, String strHoverText,
			boolean blnActive) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.Title"), strTitle);
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.Description"),
					strDescriptn);
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.HoverText"),
					strHoverText);

			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")) == false)
					selenium.click(propElementDetails
							.getProperty("editsystemnotification.ActiveBox"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")))
					selenium.click(propElementDetails
							.getProperty("editsystemnotification.ActiveBox"));
			}
			selenium.click(propElementDetails
					.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			

		} catch (Exception e) {
			log4j.info("fillFieldsOfManagSyseNotfctn function failed");
			lStrReason = "fillFieldsOfManagSyseNotfctn function failed";
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description:navigating System notice popUp
	' Precondition: N/A 
	' Arguments: selenium,strDescriptn
	' Returns: String 
	' Date: 02/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String navToSystemNotice(Selenium selenium, String strDescriptn,String strTitle)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click(propElementDetails
					.getProperty("editsystemnotification.SystemNoticelink"));
			
			try{
				selenium.waitForPageToLoad("30000");
			}catch(Exception e){
				log4j.info(e);
				
			}
			// Wait till Notification popUp appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium
			.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
			
			if (blnFound) {
				try {
					assertEquals(strDescriptn, selenium.getText("//p[2]"));
					log4j.info("Thick box containing Description "
							+ strDescriptn + " of system notice is displayed.");
				} catch (AssertionError Ae) {
					log4j.info("Thick box containing Description "
							+ strDescriptn
							+ " of system notice is NOT displayed.");
					lStrReason = "Thick box containing Description "
							+ strDescriptn
							+ " of system notice is NOT displayed.";
				}

			} else {
				log4j.info("Thick box Window is NOT displayed");
				lStrReason = "Thick box Window is NOT displayed";
			}
			
			try {
				assertEquals(strTitle, selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j
						.info("Title"
								+ strTitle
								+ "and Description are displayed with the correct data "
								+ "provided at the time of activation of 'System Notice'.");
			} catch (AssertionError Ae) {
				log4j
						.info("Title"
								+ strTitle
								+ "and Description are NOT displayed with the correct data "
								+ "provided at the time of activation of 'System Notice'.");

				lStrReason = "Title"
						+ strTitle
						+ "and Description are NOT displayed with the correct data "
						+ "provided at the time of activation of 'System Notice'.";
			}
			//close
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click("css=#TB_closeWindowButton");
		} catch (Exception e) {
			log4j.info("navToSystem Notice function failed");
			lStrReason = "navToSystem Notice function failed";
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description : Fill mandatory fields of system notification and cancel
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String editAndCancelNotification(Selenium selenium,
			String strTitle, String strDescriptn, String strHoverText,
			boolean blnActive) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.Title"), strTitle);
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.Description"),
					strDescriptn);
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.HoverText"),
					strHoverText);

			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")) == false)
					selenium.click(propElementDetails
							.getProperty("editsystemnotification.ActiveBox"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")))
					selenium.click(propElementDetails
							.getProperty("editsystemnotification.ActiveBox"));

			}
			selenium.click(propElementDetails
					.getProperty("editsystemnotification.CancelButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Region List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Region List' Screen is displayed");

			} catch (AssertionError Ae) {
				log4j.info("'Region List' Screen is NOT displayed");
				lStrReason = "'Region List' Screen is NOT displayed";
			}
			

		} catch (Exception e) {
			log4j.info("editAndCancelNotification function failed");
			lStrReason = "editAndCancelNotification function failed";
		}

		return lStrReason;
	}
	
	 /*******************************************************************************************
	 ' Description: checking For Domain field
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 27/06/2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	 public String checkForDomainAndIntField(Selenium selenium,boolean blnDomainField,boolean blnInterface) throws Exception
	 {
	 	String lStrReason="";

	 	//Create an object to refer to the Element ID Properties file
	 	ElementId_properties objelementProp = new ElementId_properties();
	 	propElementDetails = objelementProp.ElementId_FilePath();

	 	//Create an object to refer to the Environment Properties file
	 	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	 	propEnvDetails = objReadEnvironment.readEnvironment();
	 	gstrTimeOut=propEnvDetails.getProperty("TimeOut");
	 	
	 
	 	try{
	 		
	 		selenium.selectWindow("");
			   selenium.selectFrame("Data");

	 		if(blnDomainField)
	 		{
	 		try {
	 			assertFalse(selenium.isVisible(propElementDetails
						.getProperty("Regions.CreateNewRegn.DomainField")));
				
				log4j
						.info("'Domain' field is not displayed ");
			} catch (AssertionError Ae) {
				log4j
						.info("'Domain' field is  displayed ");
				lStrReason = "'Domain' field is  displayed ";
			}
	 		}
	 		
	 		if(blnInterface)
	 		{
	 			
	 			try {
		 			assertFalse(selenium.isVisible(propElementDetails
							.getProperty("Regions.CreateNewRegn.InterfaceKey")));
					
					log4j
							.info("'Interface' field is not displayed ");
				} catch (AssertionError Ae) {
					log4j
							.info("'Interface' field is  displayed ");
					lStrReason = "'Interface' field is  displayedd ";
				}
	 			
	 		}
	 	}catch(Exception e){
	 		log4j.info(e);
	 		lStrReason = lStrReason + "; " + e.toString();
	 	}

	 	return lStrReason;
	 }
	
	 /*******************************************************************************************
		' Description : Providing interface key for region
		' Precondition: N/A 
		' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
		' Returns     : String 
		' Date        : 02/06/2012
		' Author      : QSG 
		'--------------------------------------------------------------------------------- 
		' Modified Date: 
		' Modified By: 
		*******************************************************************************************/

		public String verifyRegionName(Selenium selenium,
				String strRegionName) throws Exception {
			String lStrReason = "";

			// Create an object to refer to the Element ID Properties file
			ElementId_properties objelementProp = new ElementId_properties();
			propElementDetails = objelementProp.ElementId_FilePath();

			// Create an object to refer to the Environment Properties file
			ReadEnvironment objReadEnvironment = new ReadEnvironment();
			propEnvDetails = objReadEnvironment.readEnvironment();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertEquals(strRegionName, selenium.getText("css=#regionName"));
					log4j.info("The Region name" + strRegionName
							+ "is displayed at the top left corner");
				} catch (AssertionError Ae) {
					log4j.info("The Region name" + strRegionName
							+ "is NOT  displayed at the top left corner");
					lStrReason = "The Region name" + strRegionName
							+ "is NOT  displayed at the top left corner";
				}
			} catch (Exception e) {
				log4j.info("verifyRegionName function failed");
				lStrReason = "verifyRegionName function failed";
			}

			return lStrReason;
		}
		
		/*******************************************************************************************
		' Description : Providing interface key for region
		' Precondition: N/A 
		' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
		' Returns     : String 
		' Date        : 02/06/2012
		' Author      : QSG 
		'--------------------------------------------------------------------------------- 
		' Modified Date: 
		' Modified By: 
		*******************************************************************************************/

		public String instancemsgKeyForRegion(Selenium selenium,
				boolean blnInStantMsging) throws Exception {
			String lStrReason = "";

			// Create an object to refer to the Element ID Properties file
			ElementId_properties objelementProp = new ElementId_properties();
			propElementDetails = objelementProp.ElementId_FilePath();

			// Create an object to refer to the Environment Properties file
			ReadEnvironment objReadEnvironment = new ReadEnvironment();
			propEnvDetails = objReadEnvironment.readEnvironment();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				if(blnInStantMsging)
				{
					if(selenium.isChecked(propElementDetails
							.getProperty("Regions.CreateNewRegn.InStantMsging"))==false);
				    selenium.click(propElementDetails
						.getProperty("Regions.CreateNewRegn.InStantMsging"));
				}
				else{
					if(selenium.isChecked(propElementDetails
							.getProperty("Regions.CreateNewRegn.InStantMsging")));
					selenium.click(propElementDetails
							.getProperty("Regions.CreateNewRegn.InStantMsging"));
				}
			} catch (Exception e) {
				log4j.info("instancemsgKeyForRegion function failed");
				lStrReason = "instancemsgKeyForRegion function failed";
			}

			return lStrReason;
		}
		
		 /*******************************************************************************************
		 ' Description: Navigating  to instant msging page of region
		 ' Precondition: N/A 
		 ' Arguments: selenium
		 ' Returns: String 
		 ' Date: 27/06/2012
		 ' Author: QSG 
		 '--------------------------------------------------------------------------------- 
		 ' Modified Date: 
		 ' Modified By: 
		 *******************************************************************************************/

		 public String navToInstantMsgPage(Selenium selenium) throws Exception
		 {
		 	String lStrReason="";

		 	//Create an object to refer to the Element ID Properties file
		 	ElementId_properties objelementProp = new ElementId_properties();
		 	propElementDetails = objelementProp.ElementId_FilePath();

		 	//Create an object to refer to the Environment Properties file
		 	ReadEnvironment objReadEnvironment = new ReadEnvironment();
		 	propEnvDetails = objReadEnvironment.readEnvironment();
		 	gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		 	
		 	selenium.selectWindow("");
			   selenium.selectFrame("Data");

		 	try{
		 		try
		 		{
		 		assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop752")));
		 		log4j.info("'IM' tab is available in the Menu header. ");
		 		}
		 		catch (AssertionError Ae) {
		 			log4j.info(Ae);
		 			log4j.info("'IM' tab is available in the Menu header. ");
		 			lStrReason = lStrReason + "; " + "'IM' tab is available in the Menu header.";
		 		}
		 		
		 		selenium.click(propElementDetails.getProperty("Prop752"));
		 		selenium.waitForPageToLoad(gstrTimeOut);
		 		selenium.selectFrame(propElementDetails.getProperty("Prop753"));
		 		selenium.selectFrame(propElementDetails.getProperty("Prop754"));
		 		try {
		 			assertEquals("Instant Messaging", selenium.getText(propElementDetails
		 			 		.getProperty("Header.Text")));
		 			log4j.info("'Instant Messaging' page is displayed.");
		 		}
		 		catch (AssertionError Ae) {
		 			log4j.info(Ae);
		 			log4j.info("'Instant Messaging' page is  NOT displayed.");
		 			lStrReason = lStrReason + "; " + "'Instant Messaging' page is  NOT displayed.";
		 		}
		 		
		 	}catch(Exception e){
		 		log4j.info(e);
		 		lStrReason = lStrReason + "; " + e.toString();
		 	}

		 	return lStrReason;
		 }
		 
		 /*******************************************************************************************
		 ' Description: checking For Domain field
		 ' Precondition: N/A 
		 ' Arguments: selenium
		 ' Returns: String 
		 ' Date: 27/06/2012
		 ' Author: QSG 
		 '--------------------------------------------------------------------------------- 
		 ' Modified Date: 
		 ' Modified By: 
		 *******************************************************************************************/

		 public String checkForIMLinkFOrRegion(Selenium selenium,boolean blnIMlink) throws Exception
		 {
		 	String lStrReason="";

		 	//Create an object to refer to the Element ID Properties file
		 	ElementId_properties objelementProp = new ElementId_properties();
		 	propElementDetails = objelementProp.ElementId_FilePath();

		 	//Create an object to refer to the Environment Properties file
		 	ReadEnvironment objReadEnvironment = new ReadEnvironment();
		 	propEnvDetails = objReadEnvironment.readEnvironment();
		 	gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		 	
		 	selenium.selectWindow("");
			   selenium.selectFrame("Data");

		 	try{
		 		if(blnIMlink)
		 		{
		 			try {
						assertFalse(selenium.isElementPresent(propElementDetails
								.getProperty("Prop752")));
						log4j.info("'IM' tab is not displayed on the Menu header. ");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'IM' tab is  displayed on the Menu header. ");
						lStrReason = "'IM' tab is displayed on the Menu header.";
					}
		 		}else
		 		{
		 			try {
						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Prop752")));
						log4j.info("'IM' tab is displayed on the Menu header. ");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'IM' tab is not displayed on the Menu header. ");
						lStrReason = "'IM' tab is not displayed on the Menu header.";
					}
		 		}
		 		
		 		
		 		
		 	}catch(Exception e){
		 		log4j.info(e);
		 		lStrReason = lStrReason + "; " + e.toString();
		 	}

		 	return lStrReason;
		 }
		 
		 
		 /*******************************************************************************************
			' Description : Providing IPFILTER  key for region
			' Precondition: N/A 
			' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
			' Returns     : String 
			' Date        : 02/06/2012
			' Author      : QSG 
			'--------------------------------------------------------------------------------- 
			' Modified Date: 
			' Modified By: 
			*******************************************************************************************/

			public String selectAndDeselectIPFILTER(Selenium selenium,
					boolean blnInStantMsging) throws Exception {
				String lStrReason = "";

				// Create an object to refer to the Element ID Properties file
				ElementId_properties objelementProp = new ElementId_properties();
				propElementDetails = objelementProp.ElementId_FilePath();

				// Create an object to refer to the Environment Properties file
				ReadEnvironment objReadEnvironment = new ReadEnvironment();
				propEnvDetails = objReadEnvironment.readEnvironment();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				try {
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					if(blnInStantMsging)
					{
						if(selenium.isChecked(propElementDetails
								.getProperty("Regions.CreateNewRegn.IPFILTER"))==false);
					    selenium.click(propElementDetails
							.getProperty("Regions.CreateNewRegn.IPFILTER"));
					}
					else{
						if(selenium.isChecked(propElementDetails
								.getProperty("Regions.CreateNewRegn.IPFILTER")));
						selenium.click(propElementDetails
								.getProperty("Regions.CreateNewRegn.IPFILTER"));
					}
				} catch (Exception e) {
					log4j.info("selectAndDeselectIPFILTERSave function failed");
					lStrReason = "selectAndDeselectIPFILTERSave function failed";
				}

				return lStrReason;
			}
			
			/*******************************************************************************************
			' Description : Providing IPFILTER  key for region
			' Precondition: N/A 
			' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
			' Returns     : String 
			' Date        : 02/06/2012
			' Author      : QSG 
			'--------------------------------------------------------------------------------- 
			' Modified Date: 
			' Modified By: 
			*******************************************************************************************/

			public String selectAndDeselectTraceName(Selenium selenium,
					boolean blnTraceName) throws Exception {
				String lStrReason = "";

				// Create an object to refer to the Element ID Properties file
				ElementId_properties objelementProp = new ElementId_properties();
				propElementDetails = objelementProp.ElementId_FilePath();

				// Create an object to refer to the Environment Properties file
				ReadEnvironment objReadEnvironment = new ReadEnvironment();
				propEnvDetails = objReadEnvironment.readEnvironment();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				try {
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					if(blnTraceName)
					{
						if(selenium.isChecked(propElementDetails
								.getProperty("Regions.CreateNewRegn.TraceName"))==false);
					    selenium.click(propElementDetails
							.getProperty("Regions.CreateNewRegn.TraceName"));
					}
					else{
						if(selenium.isChecked(propElementDetails
								.getProperty("Regions.CreateNewRegn.TraceName")));
						selenium.click(propElementDetails
								.getProperty("Regions.CreateNewRegn.TraceName"));
					}
				} catch (Exception e) {
					log4j.info("selectAndDeselectTraceName function failed");
					lStrReason = "selectAndDeselectTraceName function failed";
				}

				return lStrReason;
			}
			
		 /***********************************************************************
		 'Description :Fill all the fields
		 'Precondition :None
		 'Arguments  :selenium,strRegionName,strTimeZone,strContFN,strContLN,
		     strOrg,strAddr,strContactPhone1,strContactPhone2,strContactFax,strContactEMail,
		     strEmailFrequency,strAlertFrequency
		 'Returns  :String
		 'Date    :22-June-2012
		 'Author   :QSG
		 '-----------------------------------------------------------------------
		 'Modified Date                            Modified By
		 '22-June-2012                               <Name>
		 ************************************************************************/
		 
		 public String verifyAllRegnFields(Selenium selenium, String strRegionName,
		   String strTimeZone, String strContFN, String strContLN,
		   String strOrg, String strAddr, String strContactPhone1,
		   String strContactPhone2, String strContactFax,
		   String strContactEMail,String strEmailFrequency,
		   String strAlertFrequency) throws Exception {

		  String strErrorMsg = "";// variable to store error mesage

		  rdExcel = new ReadData();

		  try {
		   selenium.selectWindow("");
		   selenium.selectFrame("Data");

		   propEnvDetails = objReadEnvironment.readEnvironment();
		   propElementDetails = objelementProp.ElementId_FilePath();
		   pathProps = objAP.Read_FilePath();

		   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		   selenium.selectWindow("");
		   selenium.selectFrame("Data");
		   
		   try
		   {
		   
			assertEquals(strRegionName, selenium.getValue(propElementDetails
		     .getProperty("Regions.CreateNewRegn.RegnName")));
		  
		  assertEquals(strContFN,(selenium.getValue(propElementDetails
		     .getProperty("Regions.CreateNewRegn.ContFN"))));
		  
		  assertEquals(strContLN,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.ContLN"))));
		  
		  assertEquals(strOrg,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.Org"))));
		  
		  assertEquals(strAddr,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.Addr"))));
		  
		  assertEquals(strContactPhone1,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.ContactPhone1"))));
		  
		  assertEquals(strContactPhone2,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.ContactPhone2"))));
		  
		  assertEquals(strContactFax,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.ContactFax"))));
		  
		  assertEquals(strContactEMail,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.ContactEmail"))));
		  
		  assertEquals(strAlertFrequency,(selenium.getValue(propElementDetails
				     .getProperty("Regions.CreateNewRegn.AlertFrequency"))));

		  
		  log4j.info("Changes made to the fields are retained.");
	       } catch (AssertionError Ae) {
			    strErrorMsg = "Changes made to the fields are NOT retained." + Ae;
			    log4j.info("Changes made to the fields are NOT retained.");
			   }

		  } catch (Exception e) {
		   log4j.info("verifyAllRegnFields  Function failed" + e);
		   strErrorMsg = "verifyAllRegnFields  Function failed" + e;
		  }
		  return strErrorMsg;
		 }
		 /*******************************************************************************************
		 ' Description: checking For InterfaceKey
		 ' Precondition: N/A 
		 ' Arguments: selenium
		 ' Returns: String 
		 ' Date: 27/06/2012
		 ' Author: QSG 
		 '--------------------------------------------------------------------------------- 
		 ' Modified Date: 
		 ' Modified By: 
		 *******************************************************************************************/

		 public String checkForInterfaceKey(Selenium selenium,String strInterfaceKey,boolean blnInterface) throws Exception
		 {
		 	String lStrReason="";

		 	//Create an object to refer to the Element ID Properties file
		 	ElementId_properties objelementProp = new ElementId_properties();
		 	propElementDetails = objelementProp.ElementId_FilePath();

		 	//Create an object to refer to the Environment Properties file
		 	ReadEnvironment objReadEnvironment = new ReadEnvironment();
		 	propEnvDetails = objReadEnvironment.readEnvironment();
		 	gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		 	
		 	selenium.selectWindow("");
			   selenium.selectFrame("Data");

		 	try{
		 		if(blnInterface)
		 		{
		 		try {
					assertFalse(selenium.isEditable(propElementDetails
							.getProperty("Regions.CreateNewRegn.InterfaceKey")));
					
					assertEquals(strInterfaceKey,(selenium.getValue(propElementDetails
						     .getProperty("Regions.CreateNewRegn.InterfaceKey"))));
					log4j
							.info("Data provided for 'Interface key' is displayed on the 'Edit Region' screen and the field is disabled ");
				} catch (AssertionError Ae) {
					log4j
							.info("Data provided for 'Interface key' is NOT  displayed on the 'Edit Region' screen and the field is NOT disabled");
					lStrReason = "Data provided for 'Interface key' is NOT displayed on the 'Edit Region' screen and the field is NOT disabled";
				}
		 		}else
		 		{
		 			try {
						assertTrue(selenium.isEditable(propElementDetails
								.getProperty("Regions.CreateNewRegn.InterfaceKey")));
						log4j
								.info("Data provided for 'Interface key' is NOT displayed on the 'Edit Region' screen and the field is disabled ");
					} catch (AssertionError Ae) {
						log4j
								.info("Data provided for 'Interface key' is  displayed on the 'Edit Region' screen and the field is NOT disabled");
						lStrReason = "Data provided for 'Interface key' is  displayed on the 'Edit Region' screen and the field is NOT disabled";
					}
		 		}
		 		
		 	}catch(Exception e){
		 		log4j.info(e);
		 		lStrReason = lStrReason + "; " + e.toString();
		 	}

		 	return lStrReason;
		 }
	/*******************************************************************************************
	' Description : Providing interface key for region
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String interfaceKeyForRegion(Selenium selenium,
			String strInterfaceKey) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.InterfaceKey"),
					strInterfaceKey);
		} catch (Exception e) {
			log4j.info("interfaceKeyForRegion function failed");
			lStrReason = "interfaceKeyForRegion function failed";
		}

		return lStrReason;
	}

	
	/*******************************************************************************************
	' Description: checking For System NoticeLink region page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 18/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String checkForSystemNoticeLink(Selenium selenium,boolean blnActive) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnActive) {
				try {

					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("Regions.System.notice")));
					log4j.info("System notice is activated.");
					log4j
							.info("'System Notice' link is displayed at the top right corner below Menu header.");
				} catch (AssertionError Ae) {
					log4j.info("System notice link is NOT activated.");
					lStrReason = "System notice is NOT activated.";
				}
			}else
				try {

					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("Regions.System.notice")));
					assertFalse(selenium
							.isVisible(propElementDetails.getProperty("Regions.System.notice")));
					log4j.info("System notice is not activated. ");
					log4j
							.info("'System Notice' link is not displayed at the top right corner below Menu header.");
				} catch (AssertionError Ae) {
					log4j.info("System notice link is activated.");
					lStrReason = "System notice is  activatedd.";
				}
				

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description : Verify fields of system notification 
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String verifyFieldsOfNotification(Selenium selenium,
			String strTitle, String strDescriptn, String strHoverText,boolean blnverify,
			boolean blnActive) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if(blnverify)
			{
            try
            {
			assertEquals(strTitle,selenium.getValue(propElementDetails
					.getProperty("editsystemnotification.Title")));
			assertEquals(strDescriptn,selenium.getValue(propElementDetails
					.getProperty("editsystemnotification.Description")));
			assertEquals(strHoverText,selenium.getValue(propElementDetails
					.getProperty("editsystemnotification.HoverText")));

			if (blnActive) {
			assertTrue(selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")));
			} else {
				assertFalse(selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")));
			}
			log4j.info("Previously set data are retained on 'Edit System Notification'. ");

			} catch (AssertionError Ae) {
				log4j.info("Previously set data are NOT  retained on 'Edit System Notification'.");
				lStrReason = "Previously set data are NOT retained on 'Edit System Notification'.";
			}
			}else
			{
				
				try
				{
				assertNotSame(strTitle,selenium.getValue(propElementDetails
						.getProperty("editsystemnotification.Title")));
				assertNotSame(strDescriptn,selenium.getValue(propElementDetails
						.getProperty("editsystemnotification.Description")));
				assertNotSame(strHoverText,selenium.getValue(propElementDetails
						.getProperty("editsystemnotification.HoverText")));

				if (blnActive) {
				assertTrue(selenium.isChecked(propElementDetails
							.getProperty("editsystemnotification.ActiveBox")));
				} else {
					assertFalse(selenium.isChecked(propElementDetails
							.getProperty("editsystemnotification.ActiveBox")));
				}
				
				log4j.info("Changes done are not retained. ");

				} catch (AssertionError Ae) {
					log4j.info("Changes done are retained.");
					lStrReason = "Changes done are retained.";
				}
			}
			
		} catch (Exception e) {
			log4j.info("fillManFieldsOfSysNotification function failed");
			lStrReason = "fillManFieldsOfSysNotification function failed";
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: checking error msg in system notification page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 27/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String checkErrorMsgInSysNotPage(Selenium selenium) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue(selenium.isTextPresent("The following errors occurred on this page:"));
				log4j.info("'Appropriate error message 'The following errors occurred on this page:");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'	Appropriate error message 'The following errors NOT  occurred on this page:");
				lStrReason = lStrReason + "; " + "	Appropriate error message 'The following errors NOT  occurred on this page:";
			}
			try {
				assertTrue(selenium.isTextPresent("A title is required."));
				log4j.info("'A title is required. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'A title is NOT required. ");
				lStrReason = lStrReason + "; " + "A title is NOT required. ";
			}
			try {
				assertTrue(selenium.isTextPresent("A description is required."));
				log4j.info("'A description is required. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'A description is  NOT required. ");
				lStrReason = lStrReason + "; " + "A description is  NOT required. ";
			}
			try {
				assertTrue(selenium.isTextPresent("Hovertext is required."));
				log4j.info("'Hovertext is required.' is displayed. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Hovertext is required.' is NOT displayed. ");
				lStrReason = lStrReason + "; " + "Hovertext is required.' is NOT displayed. ";
			}
			
			try {
				assertEquals("Edit System Notification", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("User is retained on the 'Edit System Notification' page.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("User is NOT retained on the 'Edit System Notification' page.");
				lStrReason = lStrReason + "; "
						+ "User is  NOT retained on the 'Edit System Notification' page.";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description : Fill mandatory fields of system notification
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String editAndSaveNotification(Selenium selenium,
			String strTitle, String strDescriptn, String strHoverText,
			boolean blnActive) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.Title"), strTitle);
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.Description"),
					strDescriptn);
			selenium.type(propElementDetails
					.getProperty("editsystemnotification.HoverText"),
					strHoverText);

			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")) == false)
					selenium.click(propElementDetails
							.getProperty("editsystemnotification.ActiveBox"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("editsystemnotification.ActiveBox")))
					selenium.click(propElementDetails
							.getProperty("editsystemnotification.ActiveBox"));

			}
			selenium.click(propElementDetails
					.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Region List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Region List' Screen is displayed");
				if (blnActive) {
					try {

						assertTrue(selenium
								.isElementPresent(propElementDetails.getProperty("Regions.System.notice")));
						log4j.info("System notice is activated.");
						log4j
								.info("'System Notice' link is displayed at the top right corner below Menu header.");
					} catch (AssertionError Ae) {
						log4j.info("System notice link is NOT activated.");
						lStrReason = "System notice is NOT activated.";
					}
				}else
					try {

						assertTrue(selenium
								.isElementPresent(propElementDetails.getProperty("Regions.System.notice")));
						assertFalse(selenium
								.isVisible(propElementDetails.getProperty("Regions.System.notice")));
						log4j.info("System notice is not activated. ");
						log4j
								.info("'System Notice' link is not displayed at the top right corner below Menu header.");
					} catch (AssertionError Ae) {
						log4j.info("System notice link is activated.");
						lStrReason = "System notice is  activatedd.";
					}
					

			} catch (AssertionError Ae) {
				log4j.info("'Region List' Screen is NOT displayed");
				lStrReason = "'Region List' Screen is NOT displayed";
			}
			

		} catch (Exception e) {
			log4j.info("fillManFieldsOfSysNotification function failed");
			lStrReason = "fillManFieldsOfSysNotification function failed";
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
		 ' Description: navigating to Edit System Notification
		 ' Precondition: N/A 
		 ' Arguments: selenium, 
		 ' Returns: String 
		 ' Date: 02/06/2012
		 ' Author: QSG 
		 '--------------------------------------------------------------------------------- 
		 ' Modified Date: 
		 ' Modified By: 
		 *******************************************************************************************/

	public String navToEditSysNotification(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click(propElementDetails.getProperty("Prop62"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit System Notification", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("''Edit System Notification' page is displayed");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Edit System Notification' page is NOT displayed");
				lStrReason = lStrReason + "; "
						+ "'Edit System Notification' page is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info("navToEditSystemNotificatn function failed");
			lStrReason = "navToEditSystemNotificatn function failed";
		}

		return lStrReason;
	}

	/************************************************************
	'Description	:Verify Edit Multi-Region Event Rights selcted
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[]
	'Returns		:String
	'Date	 		:17-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'16-April-2012                               <Name>
	**********************************************************/
	
	public String editMultiRegnEventRites(Selenium selenium,
			String[] strRegnName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditUser.MultiRegnEvntLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Multi-Region Event Rights", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Multi-Region Event Rights page is displayed");

				for (String s : strRegnName) {

					if (selenium.isChecked(s) == false) {
						selenium.click(s);
					}

				}

				selenium.click(propElementDetails
						.getProperty("EditMultiRegnEvntRites.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Edit User", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Edit User page is displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Edit User page is NOT displayed";
					log4j.info("Edit User page is NOT displayed");
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Multi-Region Event Rights page is NOT displayed";
				log4j
						.info("Edit Multi-Region Event Rights page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}
	
	
	
	/***********************************************************************
	'Description	:Verify 'other region list' page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:18-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	************************************************************************/
	
	public String navOtherRegions(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("SetUP.SetUpLink")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));
			
			selenium.click(propElementDetails
					.getProperty("SetUP.OtherRegionsLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Other Region List", selenium
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
			
			
			
			try {
				assertEquals("Other Region List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Other Region List page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Other Region List page is NOT displayed" + Ae;
				log4j.info("Other Region List page is NOT displayed" + Ae);
			}
			
			
		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navOtherRegions Function failed"+e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify view agreement screen is displayed for the
	'				 particular region and users are asssigned to given region
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:19-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'19-April-2012                               <Name>
	************************************************************************/
	
	public String viewAgreementAndAssignUsers(Selenium selenium,
			String strRegionName, String strAgrementData, String strUserName,
			boolean blnAgreement, boolean blnAssignUsrs) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if(blnAgreement){
				selenium.click("//div[@id='mainContainer']/table/tbody/tr"
						+ "/td[2][text()='"+strRegionName+"']/parent::tr/td[1]"
						+ "/a[text()='view agreement']");
				
				String[] strWindowIds = selenium.getAllWindowIds();

				boolean blnCnt=true;
				int intCnt=0;
				
				do{		
					try{
						selenium.selectWindow(strWindowIds[1]);
						selenium.isTextPresent(strAgrementData);
						blnCnt=false;
					}catch(Exception e){
						intCnt++;
						Thread.sleep(1000);
					}
				}while(intCnt<30&&blnCnt);	
				
				try{
					assertTrue(selenium.isTextPresent(strAgrementData));
					log4j.info("user can view the aggrement. ");
					
				}catch(AssertionError Ae){
					strErrorMsg="user canNOT view the aggrement. "+Ae;
					log4j.info("user canNOT view the aggrement. "+Ae);
				}
				selenium.close();
				selenium.selectWindow("");
				
			}
			
			if(blnAssignUsrs){
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				selenium.click("//div[@id='mainContainer']/table/tbody/tr"
						+ "/td[2][text()='"+strRegionName+"']/parent::tr/td[1]"
						+ "/a[text()='assign users']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try {
					assertEquals("Assign Users to " + strRegionName + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Assign Users to " + strRegionName
							+ "' screen is displayed. ");

					selenium.click("css=input[value='" + strUserName + "']");

					selenium.click(propElementDetails
							.getProperty("AssignUsersToArkansas.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Other Region List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("The user " + strUserName
								+ " can assign the users to region Y. ");
					} catch (AssertionError Ae) {
						strErrorMsg = "The user " + strUserName
								+ " canNOT assign the users to region Y. " + Ae;
						log4j.info("The user " + strUserName
								+ " canNOT assign the users to region Y. " + Ae);
					}	

				} catch (AssertionError Ae) {
					strErrorMsg = "'Assign Users to " + strRegionName
							+ "' screen is NOT displayed. " + Ae;
					log4j.info("'Assign Users to " + strRegionName
							+ "' screen is NOT displayed.  " + Ae);
				}
			}
	
		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "viewAgreementAndAssignUsers Function failed"+e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify 'Userslist' page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:16-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	************************************************************************/
	
	public String navUserListRegnInfoLnk(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails
					.getProperty("RegionalInfo.Link"));

			selenium.click(propElementDetails.getProperty("SetUP.UsersLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Users List page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Users List page is NOT displayed" + Ae;
				log4j.info("Users List  page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navUserListRegnInfoLnk Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Edit Other Region Security - Status Types page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	************************************************************************/
	
	public String navEditOtherRegionSecurityST(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("OtherRegionList.ShareStatusTyp")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			

			selenium.click(propElementDetails
					.getProperty("OtherRegionList.ShareStatusTyp"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			intCnt=0;
			do{
				try {

					assertEquals("Edit Other Region Security - Status Types",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			

			try {
				assertEquals("Edit Other Region Security - Status Types",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j
						.info("Edit Other Region Security - Status Types page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Other Region Security - Status Types page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Other Region Security - Status Types page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navEditOtherRegionSecurityST Function failed" + e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify Check boxes are selected in edit other region security ST
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-June-2012                               <Name>
	************************************************************************/
	
	public String selAndDeselectSTInEditOthrRgnSecST(Selenium selenium,
			boolean blnSelectAlST, String[][] strIndividualSelDeslST)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			try {
				assertEquals("Edit Other Region Security - Status Types",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j
						.info("Edit Other Region Security - Status Types page is  displayed");
				
				if(blnSelectAlST){
					if(selenium.isChecked(propElementDetails.getProperty("Regions.EditOtherRegions.ST"))==false){
						selenium.click(propElementDetails.getProperty("Regions.EditOtherRegions.ST"));
						
					}
					
				}else{
					
					if(selenium.isChecked(propElementDetails.getProperty("Regions.EditOtherRegions.ST"))==false){
						selenium.click(propElementDetails.getProperty("Regions.EditOtherRegions.ST"));
						selenium.click(propElementDetails.getProperty("Regions.EditOtherRegions.ST"));
						
					}else{
						selenium.click(propElementDetails.getProperty("Regions.EditOtherRegions.ST"));
					}
					for (int i = 0; i < strIndividualSelDeslST.length; i++) {

						if (strIndividualSelDeslST[i][1] == ("true")) {
							if (selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strIndividualSelDeslST[i][0]
											+ "']") == false) {
								selenium
										.click("css=input[name='statusTypeID'][value='"
												+ strIndividualSelDeslST[i][0]
												+ "']");
								
							}

						} else {
							if (selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strIndividualSelDeslST[i][0]
											+ "']")) {
								selenium
										.click("css=input[name='statusTypeID'][value='"
												+ strIndividualSelDeslST[i][0]
												+ "']");
							}

						}

					}
				}
				selenium.click(propElementDetails.getProperty("EditUserRegions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				

				try {
					assertEquals("Other Region List",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j
							.info("Other Region List page is  displayed");
					
				} catch (AssertionError Ae) {
					strErrorMsg = "Other Region List page is NOT displayed"
							+ Ae;
					log4j
							.info("Other Region List page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Other Region Security - Status Types page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Other Region Security - Status Types page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "selAndDeselectSTInEditOthrRgnSecST Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify heck boxes are selected or deselected in edit other region ST
	'				 
	'Precondition	:None
	'Arguments		:selenium,blnST,strSTValue
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String verifySTInEditOthrRegnST(Selenium selenium,
			String strSTValue, boolean blnST) throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strSTValue + "'][name='statusTypeID']"));

					log4j.info("ST Checkbox is Selected");

				} catch (AssertionError Aes) {
					log4j.info("ST Checkbox is NOT Selected");
					strErrorMsg = "ST Checkbox is NOT Selected";
				}

			} else {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strSTValue + "'][name='statusTypeID']") == false);

					log4j.info("ST Checkbox is NOT Selected");

				} catch (AssertionError Aes) {
					log4j.info("ST Checkbox is  Selected");
					strErrorMsg = "ST Checkbox is  Selected";

				}

			}

			
		} catch (Exception e) {
			log4j
					.info("verifySTInEditOthrRegnST function failed"
							+ e);
			strErrorMsg = "verifySTInEditOthrRegnST function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify View Other Region Security - Status Types page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-June-2012                               <Name>
	************************************************************************/
	
	public String navViewOtherRegionSecurityST(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("OtherRegionList.ShareStatusTyp"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("View Other Region Security - Status Types",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j
						.info("View Other Region Security - Status Types page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "View Other Region Security - Status Types page is NOT displayed"
						+ Ae;
				log4j
						.info("View Other Region Security - Status Types page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navEditOtherRegionSecurityST Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify 'other region list' page is displayed when Back butoon is clicked
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-June-2012                               <Name>
	************************************************************************/
	
	public String navBakToOtherRegions(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

		
			selenium.click(propElementDetails.getProperty("Regions.OtherRegionBackButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Other Region List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Other Region List page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Other Region List page is NOT displayed" + Ae;
				log4j.info("Other Region List page is NOT displayed" + Ae);
			}
			
			
		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navBakToOtherRegions Function failed"+e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:Verify number of regions involved in agreement
	'Precondition	:None
	'Arguments		:selenium,strRegions[]
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'30-May-2012                               <Name>
	************************************************************************/
	
	public String verifyNumOfRegionsInAgreement(Selenium selenium,
			String strRegions[]) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Other Region List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Other Region List page is  displayed");

				for (String str : strRegions) {

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/tbody/tr/td[2][text()='"
										+ str + "']"));
						log4j.info(str + "  is displayed"
								+ "in 'Other Region List' screen");

					} catch (AssertionError Ae) {

						strErrorMsg = str + "  is NOT displayed"
								+ "in 'Other Region List' screen ";

						log4j.info(str + "  is NOT displayed"
								+ "in 'Other Region List' screen");
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Other Region List page is NOT displayed" + Ae;
				log4j.info("Other Region List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("verifyNumOfRegionsInAgreement Function failed" + e);
			strErrorMsg = "verifyNumOfRegionsInAgreement Function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:Verify the region list and their links are listed below the corresponding header 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'30-May-2012                               <Name>
	************************************************************************/
	
	public String otherRegnListHeader(Selenium selenium, boolean blnAdminUser,
			String strDemoRegionName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnAdminUser) {

				// Action header
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/thead/tr/th[text()='Action']"));

					log4j.info("Action header present");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/thead/tr/th[text()='Action']/ancestor::table/"
										+ "tbody/tr/td[1]/a[text()='update agreement']"));

						log4j
								.info("update agreement Link is present under 'Action' Column");

					} catch (AssertionError Ae) {

						strErrorMsg = "update agreement Link is NOT present under 'Action' Column";

						log4j
								.info("update agreement Link is NOT present under 'Action' Column");
					}
					

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/thead/tr/th[text()='Action']/ancestor::table/"
										+ "tbody/tr/td[1]/a[text()='view agreement']"));

						log4j
								.info("	view agreement Link is present under 'Action' Column");

					} catch (AssertionError Ae) {

						strErrorMsg = "	view agreement Link is NOT present under 'Action' Column";

						log4j
								.info("	view agreement Link is NOT present under 'Action' Column");
					}

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/thead/tr/th[text()='Action']/ancestor::table/"
										+ "tbody/tr/td[1]/a[text()='assign users']"));

						log4j
								.info("	assign users Link is present under 'Action' Column");

					} catch (AssertionError Ae) {

						strErrorMsg = "	assign users Link is NOT present under 'Action' Column";

						log4j
								.info("	assign users Link is NOT present under 'Action' Column");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Action header NOT present";

					log4j.info("Action header NOT present");
				}

				// Region Name
				try {
					
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/thead/tr/th[2]"));

					log4j.info("Region Name header present");
					
					try {

						
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/tbody/tr/td[2][text()='"+strDemoRegionName+"']"));

						
						log4j
								.info("Region Name present under 'Region Name' header");

					} catch (AssertionError Ae) {
						strErrorMsg = "Region Name NOT present under 'Region Name' header";

						log4j
								.info("Region Name NOT present under 'Region Name' header");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Region Name  header NOT present";

					log4j.info("Region Name  header NOT present");
				}

				
				// Display order

				try {
					
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/thead/tr/th[3]"));

					log4j.info("Display header present");

					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/tbody/tr/td[3]/a[text()='up']"));

						log4j
								.info("Up Link present under 'Display Order' header");

					} catch (AssertionError Ae) {
						strErrorMsg = "Up Link NOT present under 'Display Order' header";

						log4j
								.info("Up Link NOT present under 'Display Order' header");
					}

					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/tbody/tr/td[3]/a[text()='down']"));

						log4j
								.info("down Link present under 'Display Order' header");

					} catch (AssertionError Ae) {
						strErrorMsg = "down Link NOT present under 'Display Order' header";

						log4j
								.info("down Link NOT present under 'Display Order' header");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Display header NOT present";

					log4j.info("Display header NOT present");
				}

			}else{
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/thead/tr/th[text()='Action']"));

					log4j.info("Action header present");

					try {
						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/thead/tr/th[text()='Action']/ancestor::table/"
										+ "tbody/tr/td[1]/a[text()='update agreement']"));

						log4j
								.info("update agreement Link is NOT present under 'Action' Column");

					} catch (AssertionError Ae) {

						strErrorMsg = "update agreement Link is  present under 'Action' Column";

						log4j
								.info("update agreement Link is  present under 'Action' Column");
					}

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/thead/tr/th[text()='Action']/ancestor::table/"
										+ "tbody/tr/td[1]/a[text()='view agreement']"));

						log4j
								.info("	view agreement Link is present under 'Action' Column");

					} catch (AssertionError Ae) {

						strErrorMsg = "	view agreement Link is NOT present under 'Action' Column";

						log4j
								.info("	view agreement Link is NOT present under 'Action' Column");
					}

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/thead/tr/th[text()='Action']/ancestor::table/"
										+ "tbody/tr/td[1]/a[text()='assign users']"));

						log4j
								.info("	assign users Link is present under 'Action' Column");

					} catch (AssertionError Ae) {

						strErrorMsg = "	assign users Link is NOT present under 'Action' Column";

						log4j
								.info("	assign users Link is NOT present under 'Action' Column");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Action header NOT present";

					log4j.info("Action header NOT present");
				}

				// Region Name
				try {
					
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/thead/tr/th[2]"));

					log4j.info("Region Name header present");
					
					try {

						
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/tbody/tr/td[2][text()='"+strDemoRegionName+"']"));

						
						log4j
								.info("Region Name present under 'Region Name' header");

					} catch (AssertionError Ae) {
						strErrorMsg = "Region Name NOT present under 'Region Name' header";

						log4j
								.info("Region Name NOT present under 'Region Name' header");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Region Name  header NOT present";

					log4j.info("Region Name  header NOT present");
				}

				
				// Display order

				try {
					
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/thead/tr/th[3]"));

					log4j.info("Display header present");

					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/tbody/tr/td[3]/a[text()='up']"));

						log4j
								.info("Up Link present under 'Display Order' header");

					} catch (AssertionError Ae) {
						strErrorMsg = "Up Link NOT present under 'Display Order' header";

						log4j
								.info("Up Link NOT present under 'Display Order' header");
					}

					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table/tbody/tr/td[3]/a[text()='down']"));

						log4j
								.info("down Link present under 'Display Order' header");

					} catch (AssertionError Ae) {
						strErrorMsg = "down Link NOT present under 'Display Order' header";

						log4j
								.info("down Link NOT present under 'Display Order' header");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Display header NOT present";

					log4j.info("Display header NOT present");
				}
			}

		} catch (Exception e) {
			log4j.info("otherRegnListHeader Function failed" + e);
			strErrorMsg = "otherRegnListHeader Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	'Description	:Verify 'Share resources' and 'Share status types' buttons are available. 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	********************************************************************/
	
	public String verifyShareRSAndShareSTBtns(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
					
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("OtherRegionList.ShareResources")));

				log4j.info("'Share resources' button is available. ");
			} catch (AssertionError Ae) {

				strErrorMsg = "'Share resources' button is NOT available. ";
				log4j.info("'Share resources' button is NOT available. ");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("OtherRegionList.ShareStatusTyp")));

				log4j.info("'Share status types' buttons is available. ");
			} catch (AssertionError Ae) {

				strErrorMsg = "'Share status types' buttons is NOT available. ";
				log4j.info("'Share status types' buttons is NOT available. ");
			}

		} catch (Exception e) {
			log4j.info("verifySahreRSAndShareSTBtns function failed" + e);
			strErrorMsg = "verifySahreRSAndShareSTBtns function failed" + e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify Edit Other Region Security - Resources page is 
	'				 displayed when Sahre Resource button is clicked
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'11-June-2012                               <Name>
	************************************************************************/
	
	public String navEditOtherRegionSecurityRS(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("OtherRegionList.ShareResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Other Region Security - Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("Edit Other Region Security - Resources page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Other Region Security - Resources page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Other Region Security - Resources page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navEditOtherRegionSecurityRS Function failed" + e);
			strErrorMsg = "navEditOtherRegionSecurityRS Function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Resource and RT are displayed under Resource Name
	'				 and Resource Type header respaectively in Edit Other Region Security - Resources page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'11-June-2012                               <Name>
	************************************************************************/

	public String verfyRSandRTEditOtherRegionSecurityRS(Selenium selenium,
			String strRS, String strRT) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_resourceID']"
									+ "/thead/tr/th[2]/a[text()='Resource Name']/ancestor::table/"
									+ "tbody/tr/td[2][text()='" + strRS + "']"));

					log4j
							.info("'Edit Other Region Security - Resources' "
									+ "screen is  displayed with a list of all active "
									+ "resources in the region arranged under the column"
									+ " 'Resource name'");

				} catch (AssertionError Ae) {

					strErrorMsg = "'Edit Other Region Security - Resources' "
							+ "screen is NOT displayed with a list of all active "
							+ "resources in the region arranged under the column"
							+ " 'Resource name'";
					log4j
							.info("'Edit Other Region Security - Resources' "
									+ "screen is NOT displayed with a list of all active "
									+ "resources in the region arranged under the column"
									+ " 'Resource name'");

				}

				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_resourceID']"
									+ "/thead/tr/th[3]/a[text()='Resource Type']/ancestor::table/"
									+ "tbody/tr/td[3][text()='" + strRT + "']"));

					log4j
							.info("'Edit Other Region Security - Resources' "
									+ "screen is  displayed with a list of all active "
									+ "resources in the region arranged under the column"
									+ " 'Resource Type'");

				} catch (AssertionError Ae) {

					strErrorMsg = "'Edit Other Region Security - Resources' "
							+ "screen is NOT displayed with a list of all active "
							+ "resources in the region arranged under the column"
							+ " 'Resource Type'";
					log4j
							.info("'Edit Other Region Security - Resources' "
									+ "screen is NOT displayed with a list of all active "
									+ "resources in the region arranged under the column"
									+ " 'Resource Type'");

				}

			

		} catch (Exception e) {
			log4j.info("verfyRSandRTEditOtherRegionSecurityRS Function failed"
					+ e);
			strErrorMsg = "verfyRSandRTEditOtherRegionSecurityRS Function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Check box is available with Resource in Edit
	'				 Other Region Security - Resources page
	'Precondition	:None
	'Arguments		:selenium,strRS
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'11-June-2012                               <Name>
	************************************************************************/

	public String verifyCheckBoxAvailWitRS(Selenium selenium, String strRS)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tbl_resourceID']"
								+ "/tbody/tr/td[2][text()='" + strRS + "']/"
								+ "parent::tr/td[1]/input[@type='checkbox']"));

				log4j.info("A checkbox is available with each of the resource ");

			} catch (AssertionError Ae) {

				strErrorMsg = "A checkbox is NOT available with each of the resource ";
				
				log4j.info("A checkbox is NOT available with each of the resource ");

			}

		} catch (Exception e) {
			log4j.info("verifyCheckBoxAvailWitRS Function failed"
					+ e);
			strErrorMsg = "verifyCheckBoxAvailWitRS Function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify check boxes associated with RS is checked or NOT
	'Precondition	:None
	'Arguments		:selenium,strRS,strRSValue,blnRS
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'11-June-2012                               <Name>
	************************************************************************/

	public String selAndDeselctRSInEditOthrRegSecurRSPge(Selenium selenium,
			String strRS, String strRSValue, boolean blnRS,
			boolean blnverifyCheckBox, boolean blnEnable) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Verify Resource is selected or NOT

			
			if (blnverifyCheckBox) {
				
				try{
					assertTrue(selenium.isChecked("css=input[value='" + strRSValue + "']"));
					log4j.info(strRS + " is  selected.");
					
				}catch(AssertionError Ae){
					strErrorMsg=strRS + " is NOT selected.";
					log4j.info(strRS + " is NOT selected.");
				
					
				}

			} else {
				try{
					assertFalse(selenium.isChecked("css=input[value='" + strRSValue + "']"));
					log4j.info(strRS + " is NOT selected.");
					
				}catch(AssertionError Ae){
					strErrorMsg=strRS + " is selected.";
					log4j.info(strRS + " is  selected.");
				
					
				}
			}

			// Select Resources
			
			if(blnEnable){
				
				try{
					assertTrue(selenium.isEditable("css=input[value='" + strRSValue + "']"));
					log4j.info("checkboxes are enabled");
					
				}catch(AssertionError Ae){
					strErrorMsg = "checkboxes are disabled" + Ae;
					log4j.info("checkboxes are disabled" + Ae);
					
				}
				
			}else{
				try{
					assertFalse(selenium.isEditable("css=input[value='" + strRSValue + "']"));
					log4j.info("checkboxes are disabled");
					
				}catch(AssertionError Ae){
					strErrorMsg = "checkboxes are enabled" + Ae;
					log4j.info("checkboxes are enabled" + Ae);
					
				}
				
			}

			
			if (blnEnable) {

				if (blnRS) {
					if (selenium.isChecked("css=input[value='" + strRSValue
							+ "']") == false) {

						selenium.click("css=input[value='" + strRSValue + "']");
					}

				} else {
					if (selenium.isChecked("css=input[value='" + strRSValue
							+ "']")) {

						selenium.click("css=input[value='" + strRSValue + "']");
					}
				}
			}
			
			
			

		} catch (Exception e) {
			log4j.info("selAndDeselctRSInEditOthrRegSecurRSPge Function failed"
					+ e);
			strErrorMsg = "selAndDeselctRSInEditOthrRegSecurRSPge Function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Save
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'11-June-2012                               <Name>
	************************************************************************/

	public String savEditOthrRegnSecurtyRS(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditOtherRegionSecurityStatusTypes.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Other Region List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Other Region List page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Other Region List page is NOT displayed" + Ae;
				log4j.info("Other Region List page is NOT displayed" + Ae);
			}
			

		} catch (Exception e) {
			log4j.info("savEditOthrRegnSecurtyRS Function failed"
					+ e);
			strErrorMsg = "savEditOthrRegnSecurtyRS Function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify View Other Region Security - Resources page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'11-June-2012                               <Name>
	************************************************************************/
	
	public String navViewOtherRegionSecurityRS(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("OtherRegionList.ShareResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("View Other Region Security - Resources",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j
						.info("View Other Region Security - Resources page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "View Other Region Security - Resources page is NOT displayed"
						+ Ae;
				log4j
						.info("View Other Region Security - Resources page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navViewOtherRegionSecurityRS Function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***************************************************************
	   'Description :check status type, Resource Type and Resource in Region view screen
	   'Precondition :None
	   'Arguments  :selenium, strResType,strResource,statustype
	   'Returns  :strReason
	   'Date    :5-June-2012
	   'Author   :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String checkAllSTRTAndRSInRegionView(Selenium selenium,
			String strResType, String strResource[], String[] strStatType)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try {
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt_')]/thead/tr/th[2]/a[text()='"
								+ strResType + "']"));
				log4j.info("Resource type '" + strResType
						+ "' is displayed in the User view Detail screen '");
				int intRow = 0;
				for (intRow = 0; intRow < strResource.length; intRow++) {
					try {

						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt_')]/thead/tr/th[2]/a[text()='"
										+ strResType
										+ "']/ancestor::thead/following-sibling::tbody/tr["
										+ (intRow + 1)
										+ "]/td[2]/a[text()='"
										+ strResource[intRow] + "']"));
						log4j
								.info("Resource "
										+ strResource[intRow]
										+ " is displayed in the Region view Detail screen under resource type "
										+ strResType);
					} catch (AssertionError ae) {
						log4j
								.info("Resource "
										+ strResource[intRow]
										+ " is NOT displayed in the Region view Detail screen under resource type "
										+ strResType);
						strReason = strReason
								+ " Resource "
								+ strResource[intRow]
								+ " is NOT displayed in the Region view Detail screen under resource type "
								+ strResType;
					}
				}

				int intCol = 0;
				for (intCol = 0; intCol < strStatType.length; intCol++) {
					try {

						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt_')]/thead/tr/th[2]/a[text()='"
										+ strResType
										+ "']/ancestor::tr/th["
										+ (intCol + 3)
										+ "]/a[text()='"
										+ strStatType[intCol] + "']"));
						log4j
								.info("Status Type "
										+ strStatType[intCol]
										+ " is displayed in the Region view screen under resource type "
										+ strResType);
					} catch (AssertionError ae) {
						log4j
								.info("Status Type is "
										+ strStatType[intCol]
										+ " is NOT displayed in the Region view Detail screen under resource type "
										+ strResType);
						strReason = strReason
								+ " Status Type  "
								+ strStatType[intCol]
								+ " is NOT displayed in the Region view Detail screen under resource type "
								+ strResType;
					}
				}

			} catch (AssertionError ae) {
				log4j.info("Resource type '" + strResType
						+ "' is NOT displayed in the User view Detail screen'");
				strReason = "Resource type '" + strResType
						+ "' is NOT displayed in the User view Detail screen'";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkAllSTRTAndRSInRegionView "
					+ e.toString();
		}
		return strReason;
	}

	/***********************************************************************
		 'Description :Navigate to particular region from Other Regions
		 'Precondition :None
		 'Arguments  :selenium,strRegion
		 'Returns  :String
		 'Date    :5-June-2012
		 'Author   :QSG
		 '-----------------------------------------------------------------------
		 'Modified Date                            Modified By
		 '<Date>                                   <Name>
		 ************************************************************************/
		 
	public String navToRegionFrmOtherRegions(Selenium selenium, String strRegion)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("link=" + strRegion));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.OtherRegionsLink"));
			// click particular region
			selenium.click("link=" + strRegion);
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertTrue(selenium.isTextPresent(strRegion));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			intCnt = 0;
			while (selenium.isTextPresent(strRegion) == false && intCnt < 40) {
				intCnt++;
				Thread.sleep(1000);
			}
			intCnt=0;
			do{
				try {

					assertEquals(strRegion, selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			if (selenium.isTextPresent(strRegion)) {
				
				try {
					assertEquals(strRegion, selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					log4j.info(strRegion + " Region page is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = strRegion + " Region page is  NOT displayed";
					log4j.info(strRegion + " Region page is  NOT displayed");
				}
			} else {
				strErrorMsg = strRegion + " Region page is  NOT displayed";
				log4j.info(strRegion + " Region page is  NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navToRegionFrmOtherRegions Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify 'Region list' page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:22-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-June-2012                               <Name>
	************************************************************************/
	
	public String navRegionList(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("SetUP.SetUpLink")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));
			
			selenium.click(propElementDetails
					.getProperty("SetUP.RegionsLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Region List", selenium
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
			
			
			try {
				assertEquals("Region List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Region List page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Region List page is NOT displayed" + Ae;
				log4j.info("Region List page is NOT displayed" + Ae);
			}
			
			
		} catch (Exception e) {
			log4j.info("nav Region List page  Function failed" + e);
			strErrorMsg = "nav Region List page  Function failed"+e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify 'Create New Region ' page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:22-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-June-2012                               <Name>
	************************************************************************/
	
	public String navCreateNewRegionPge(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("Regions.CreateNewRegn"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create New Region", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create New Region page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Create New Region page is NOT displayed" + Ae;
				log4j.info("Create New Region page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("nav Create New Region page  Function failed" + e);
			strErrorMsg = "nav Create New Region page  Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify 'Create New Region ' page is displayed 
	'Precondition	:None
	'Arguments		:selenium,strRegionName
	'Returns		:String
	'Date	 		:22-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-June-2012                               <Name>
	************************************************************************/
	
	public String navEditRegionPge(Selenium selenium, String strRegionName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/table/tbody/"
					+ "tr/td[2][text()='" + strRegionName + "']/parent::tr/"
					+ "td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Edit Region", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Edit Region", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Edit Region page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Region page is NOT displayed" + Ae;
				log4j.info("Edit Region page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("nav Edit Region page  Function failed" + e);
			strErrorMsg = "nav Edit Region page  Function failed" + e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify Region is displayed in Region List Page
	'Precondition	:None
	'Arguments		:selenium,strRegionName
	'Returns		:String
	'Date	 		:22-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-June-2012                               <Name>
	************************************************************************/
	
	public String savAndVerifyRegion(Selenium selenium, String strRegionName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Regions.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Region List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Region List page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/tbody/tr/td[2][text()='"
									+ strRegionName + "']"));

					log4j.info("Region "+ strRegionName + " is displayed in Region List page.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Region is NOT displayed in Region List page."
							+ Ae;
					log4j.info("Region "+ strRegionName + " is NOT displayed in Region List page."
							+ Ae);

				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Region List page is NOT displayed" + Ae;
				log4j.info("Region List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("savAndVerifyRegion  Function failed" + e);
			strErrorMsg = "savAndVerifyRegion  Function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	
	/***********************************************************************
	'Description	:Fill all availbale option fields
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:22-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-June-2012                               <Name>
	************************************************************************/
	
	public String fillAvailableOptions(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("Regions.CreateNewRegn.StatusChangeNameTrace"));
			selenium.click(propElementDetails
					.getProperty("Regions.CreateNewRegn.IRFilter"));
			selenium.click(propElementDetails
					.getProperty("Regions.CreateNewRegn.DisplayTimeZone"));
			selenium.click(propElementDetails
					.getProperty("Regions.CreateNewRegn.InstantMessaging"));

		} catch (Exception e) {
			log4j.info("fillAvailableOptions  Function failed" + e);
			strErrorMsg = "fillAvailableOptions  Function failed" + e;
		}
		return strErrorMsg;
	}

	
	 /********************************************************************
	  'Description :Fetch Region  Value in Region List page
	  'Precondition :None
	  'Arguments  :selenium,Region Name
	  'Returns  :String
	  'Date    :22-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  **********************************************************************/
	
	public String fetchRegionValue(Selenium selenium, String strRegionName)
			throws Exception {

		String strRegionValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']"
							+ "/table/tbody/tr/td[2][text()='" + strRegionName
							+ "']/parent::" + "tr/td[1]/a"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			String strResValueArr[] = selenium.getAttribute(
					"//div[@id='mainContainer']"
							+ "/table/tbody/tr/td[2][text()='" + strRegionName
							+ "']/parent::" + "tr/td[1]/a@href").split("=");
			strRegionValue = strResValueArr[4];
			log4j.info("Region Value is " + strRegionValue);
		} catch (Exception e) {
			log4j.info("fetchRegionValue function failed" + e);
			strRegionValue = "fetchRegionValue function failed";
		}
		return strRegionValue;
	}
	
	
 /********************************************************************
  'Description  :Fetch InterFaceKey  Value in Region List page
  'Precondition :None
  'Arguments    :selenium,Region Name
  'Returns      :String
  'Date         :22-June-2012
  'Author       :QSG
  '-------------------------------------------------------------------
  'Modified Date                            Modified By
  '<Date>                                    <Name>
  *********************************************************************/

	public String fetchInterFaceKey(Selenium selenium, String strRegionName)
			throws Exception {

		String strInterfaceKey = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/tbody/tr/td[text()='"
									+ strRegionName + "']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			strInterfaceKey = selenium.getText("//div[@id='mainContainer']"
					+ "/table/tbody/tr/td[text()='" + strRegionName
					+ "']/following-sibling::td[3]");
			log4j.info("InteFace key Value is " + strInterfaceKey);
			
		} catch (Exception e) {
			log4j.info("fetchInterFaceKey function failed" + e);
			strInterfaceKey = "fetchInterFaceKey function failed";
		}
		return strInterfaceKey;
	}
	
	/***********************************************************************
	 'Description :Fill all the fields
	 'Precondition :None
	 'Arguments  :selenium,strRegionName,strTimeZone,strContFN,strContLN,
	     strOrg,strAddr,strContactPhone1,strContactPhone2,strContactFax,strContactEMail,
	     strEmailFrequency,strAlertFrequency
	 'Returns  :String
	 'Date    :22-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '22-June-2012                               <Name>
	 ************************************************************************/
	 
	 public String fillAllRegnFields(Selenium selenium, String strRegionName,
	   String strTimeZone, String strContFN, String strContLN,
	   String strOrg, String strAddr, String strContactPhone1,
	   String strContactPhone2, String strContactFax,
	   String strContactEMail,String strEmailFrequency,
	   String strAlertFrequency) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  rdExcel = new ReadData();

	  try {
	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   pathProps = objAP.Read_FilePath();

	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.RegnName"),
	     strRegionName);
	   selenium.select(propElementDetails
	     .getProperty("Regions.CreateNewRegn.TimeZone"), "label="
	     + strTimeZone);
	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContFN"), strContFN);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContLN"), strContLN);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.Org"), strOrg);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.Addr"), strAddr);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactPhone1"),
	     strContactPhone1);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactPhone2"),
	     strContactPhone2);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactFax"),
	     strContactFax);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactEmail"),
	     strContactEMail);

	   selenium.select(propElementDetails
	     .getProperty("Regions.CreateNewRegn.EmailFrequency"),
	     "label=" + strEmailFrequency);

	   selenium.select(propElementDetails
	     .getProperty("Regions.CreateNewRegn.AlertFrequency"),
	     "label=" + strAlertFrequency);


	  } catch (Exception e) {
	   log4j.info("fillAllRegnFields  Function failed" + e);
	   strErrorMsg = "fillAllRegnFields  Function failed" + e;
	  }
	  return strErrorMsg;
	 }
	 
	 /***********************************************************************
	 'Description :Verify 'Create New Region ' page is displayed 
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :22-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '22-June-2012                               <Name>
	 ************************************************************************/
	 
	 public String checkForRegionNotCreation(Selenium selenium,String strRegionName ) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  rdExcel = new ReadData();

	  try {
	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   pathProps = objAP.Read_FilePath();

	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   selenium.click(propElementDetails.getProperty("Pref.CancelButton"));
	   selenium.waitForPageToLoad(gstrTimeOut);

	   try {
	    assertEquals("Region List", selenium
	      .getText(propElementDetails.getProperty("Header.Text")));
	    log4j.info("Region List page is  displayed");

	   } catch (AssertionError Ae) {
	    strErrorMsg = "Region List page is NOT displayed" + Ae;
	    log4j.info("Region List page is NOT displayed" + Ae);
	   }
	   try
	   {
	   assertFalse(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/" +
	   		"td[2][text()='"+strRegionName+"']"));
	   log4j.info("Region is NOT created");

	   } catch (AssertionError Ae) {
		    strErrorMsg = "Region is created " + Ae;
		    log4j.info("Region is created " + Ae);
		   }
	  } catch (Exception e) {
	   log4j.info("checkForRegionNotCreation  Function failed" + e);
	   strErrorMsg = "checkForRegionNotCreation  Function failed" + e;
	  }
	  return strErrorMsg;
	 }
	 
	 /***********************************************************************
	 'Description :Verify 'Create New Region ' page is displayed 
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :22-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '22-June-2012                               <Name>
	 ************************************************************************/
	 
	public String saveAndVerifyRegion(Selenium selenium, String strRegionName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Regions.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			Thread.sleep(10000);

			int intCnt=0;
			do{
				try {

					assertEquals("Region List", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Region List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Region List page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Region List page is NOT displayed" + Ae;
				log4j.info("Region List page is NOT displayed" + Ae);
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/"
								+ "td[2][text()='" + strRegionName + "']"));
				log4j.info("Region" + strRegionName
						+ "is listed in region list screen");

			} catch (AssertionError Ae) {
				strErrorMsg = "Region " + strRegionName
						+ "is  NOT listed in region list screen" + Ae;
				log4j.info("Region " + strRegionName
						+ "is  NOT listed in region list screen" + Ae);
			}
		} catch (Exception e) {
			log4j.info("saveAndVerifyRegion  Function failed" + e);
			strErrorMsg = "saveAndVerifyRegion  Function failed" + e;
		}
		return strErrorMsg;
	 }
	 
	 /***********************************************************************
	 'Description :Function to Edit theRegion 
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :22-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '22-June-2012                               <Name>
	 ************************************************************************/
	 
	 public String editRegion(Selenium selenium,String strRegionName ) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  rdExcel = new ReadData();

	  try {
	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   pathProps = objAP.Read_FilePath();
	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");
	   try {
	    assertEquals("Region List", selenium
	      .getText(propElementDetails.getProperty("Header.Text")));
	    log4j.info("Region List page is  displayed");

	   } catch (AssertionError Ae) {
	    strErrorMsg = "Region List page is NOT displayed" + Ae;
	    log4j.info("Region List page is NOT displayed" + Ae);
	   }
	    selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"+strRegionName+"']" +
	    		"/parent::tr/td[1]/a[text()='Edit']");  
	    selenium.waitForPageToLoad(gstrTimeOut);
	   try {
		    assertEquals("Edit Region", selenium
		      .getText(propElementDetails.getProperty("Header.Text")));
		    log4j.info("Edit Region page is  displayed");

		   } catch (AssertionError Ae) {
		    strErrorMsg = "Edit Region page is NOT displayed" + Ae;
		    log4j.info("Edit Region page is NOT displayed" + Ae);
		   }

	   
	  } catch (Exception e) {
	   log4j.info("editRegion  Function failed" + e);
	   strErrorMsg = "editRegion  Function failed" + e;
	  }
	  return strErrorMsg;
	 }
	 
	 
	 /***********************************************************************
	 'Description :Function to Edit theRegion 
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :22-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '22-June-2012                               <Name>
	 ************************************************************************/
	 
	 public String navToContactUsFrame(Selenium selenium,String strRegionName,String strContFN, 
			 String strContLN, String strContactPhone1,
			   String strContactPhone2, String strContactFax,
			   String strContactEMail) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  rdExcel = new ReadData();

	  try {
	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   pathProps = objAP.Read_FilePath();

	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");
	   
	   selenium.click(propElementDetails.getProperty("Regions.ContactUsLink"));
	// Wait till pop appears
		boolean blnFound = false;
		int intCnt = 0;
		while (blnFound == false && intCnt < 30) {
			try {
				selenium
						.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
				blnFound = true;
			} catch (Exception e) {
				Thread.sleep(1000);
				intCnt++;
			}
		}
		if (blnFound) {
			log4j.info("Pop up window is displayed");
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");

		} else {
			log4j.info("Pop up window is NOT displayed");
			strErrorMsg = "Pop up window is NOT displayed";
		}
		
		selenium.selectWindow("");
		   selenium.selectFrame("Data");
		   
		   selenium
			.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
		   try
		   {
		    assertTrue(selenium.isTextPresent(""+strContFN+" "+strContLN+""));
		    log4j.info("Contact name provided for" +strRegionName+ "is displayed ");

		   } catch (AssertionError Ae) {
		    strErrorMsg = "Contact name provided for" +strRegionName+ "is  NOT displayed" + Ae;
		    log4j.info("Contact name provided for" +strRegionName+ "is NOT displayed" + Ae);
		   }
		   
		   if(strContactPhone2!="")
		   {
            try
            {
		    assertTrue(selenium.isTextPresent(""+strContactPhone1+" (Phone)"));
			assertTrue(selenium.isTextPresent(""+strContactPhone2+" (Phone)"));
			assertTrue(selenium.isTextPresent(""+strContactFax+" (Fax)"));
			assertTrue(selenium.isTextPresent(strContactEMail));
			log4j.info("Contact name, Phone numbers, Fax number and email ID provided for region" +strRegionName+ "is displayed");
            } catch (AssertionError Ae) {
    		    strErrorMsg = "Contact name, Phone numbers, Fax number and email ID provided for region" +strRegionName+ "is  NOT displayed" + Ae;
    		    log4j.info("Contact name, Phone numbers, Fax number and email ID provided for region" +strRegionName+ "is NOT displayed" + Ae);
    		   }}
	   
	  } catch (Exception e) {
	   log4j.info("navToContactUsFrame  Function failed" + e);
	   strErrorMsg = "navToContactUsFrame  Function failed" + e;
	  }
	  return strErrorMsg;
	 }
	 
	 /***********************************************************************
	  'Description :Verify heck boxes are selected or deselected in edit other region ST
	  '     
	  'Precondition :None
	  'Arguments  :selenium,blnST,strSTValue
	  'Returns  :String
	  'Date    :6-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                   <Name>
	  ************************************************************************/

	public String verifySTPresentOrNotInEditOthrRegnST(Selenium selenium,
			String strSTValue, boolean blnST, String strStatusType)
			throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {

				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("css=input[value='"
										+ strSTValue
										+ "'][name='statusTypeID']"));
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
					assertTrue(selenium.isElementPresent("css=input[value='"
							+ strSTValue + "'][name='statusTypeID']"));

					log4j.info("Status type" + strStatusType
							+ " is  displayed. ");

				} catch (AssertionError Aes) {
					log4j.info("Status type" + strStatusType
							+ " is not displayed. ");
					strErrorMsg = "Status type" + strStatusType
							+ " is not displayed. ";
				}

			} else {

				try {
					assertFalse(selenium.isElementPresent("css=input[value='"
							+ strSTValue + "'][name='statusTypeID']"));

					log4j.info("Status type" + strStatusType
							+ " is not displayed. ");

				} catch (AssertionError Aes) {
					log4j.info("Status type" + strStatusType
							+ " is displayed. ");
					strErrorMsg = "Status type" + strStatusType
							+ " is  displayed. ";
				}
			}

		} catch (Exception e) {
			log4j.info("verifySTPresentOrNotInEditOthrRegnST function failed"
					+ e);
			strErrorMsg = "verifySTPresentOrNotInEditOthrRegnST function failed"
					+ e;
		}
		return strErrorMsg;
	}
	  
	  /***********************************************************************
	  'Description :Verify 'other region list' page is displayed 
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :String
	  'Date    :18-April-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '18-April-2012                               <Name>
	  ************************************************************************/
	  
	  public String clickBackAndNavToOtherRegions(Selenium selenium) throws Exception {

	   String strErrorMsg = "";// variable to store error mesage

	   rdExcel = new ReadData();

	   try {
	    propEnvDetails = objReadEnvironment.readEnvironment();
	    propElementDetails = objelementProp.ElementId_FilePath();
	    pathProps = objAP.Read_FilePath();
	    
	    gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	    selenium.selectWindow("");
	    selenium.selectFrame("Data");
	    selenium.click(propElementDetails.getProperty("Regions.OtherRegionBackButton"));
	    selenium.waitForPageToLoad(gstrTimeOut);    
	    try {
	     assertEquals("Other Region List", selenium
	       .getText(propElementDetails.getProperty("Header.Text")));
	     log4j.info("Other Region List page is  displayed");

	    } catch (AssertionError Ae) {
	     strErrorMsg = "Other Region List page is NOT displayed" + Ae;
	     log4j.info("Other Region List page is NOT displayed" + Ae);
	    }
	    
	    
	   } catch (Exception e) {
	    log4j.info("" + e);
	    strErrorMsg = "navOtherRegions Function failed"+e;
	   }
	   return strErrorMsg;
	  }
	  
	  /***********************************************************************
	  'Description :Verify 'other region list' page is displayed 
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :String
	  'Date    :18-April-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '18-April-2012                               <Name>
	  ************************************************************************/ 

	  public String verifySTDisableOrNotInEditOthrRegnST(Selenium selenium,
	    String strSTValue, boolean blnST,String strStatusType) throws Exception {
	   String strErrorMsg = "";// variable to store error mesage

	   rdExcel = new ReadData();

	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   pathProps = objAP.Read_FilePath();

	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   try {
	    selenium.selectWindow("");
	    selenium.selectFrame("Data");

	    if (blnST) {

	     try {
	      assertTrue(selenium.isEditable("css=input[value='"
	        + strSTValue + "'][name='statusTypeID']"));

	      log4j.info("Status type"+strStatusType+ " is  displayed. ");

	     } catch (AssertionError Aes) {
	      log4j.info("Status type"+strStatusType+ " is not displayed. ");
	      strErrorMsg ="Status type"+strStatusType+ " is not displayed. ";
	     }

	    } else {

	     try {
	      assertFalse(selenium.isEditable("css=input[value='"
	        + strSTValue + "'][name='statusTypeID']"));

	      log4j.info("Status type"+strStatusType+ " is not displayed. ");

	     } catch (AssertionError Aes) {
	      log4j.info("Status type"+strStatusType+ " is displayed. ");
	      strErrorMsg ="Status type"+strStatusType+ " is  displayed. ";
	     }
	    }
	    
	   } catch (Exception e) {
	    log4j
	      .info("verifySTPresentOrNotInEditOthrRegnST function failed"
	        + e);
	    strErrorMsg = "verifySTPresentOrNotInEditOthrRegnST function failed"
	      + e;
	   }
	   return strErrorMsg;
	  }
	  
	
	 /***********************************************************************
	 'Description :Fill all the fields
	 'Precondition :None
	 'Arguments  :selenium,strRegionName,strTimeZone,strContFN,strContLN,
	     strOrg,strAddr,strContactPhone1,strContactPhone2,strContactFax,strContactEMail,
	     strEmailFrequency,strAlertFrequency
	 'Returns  :String
	 'Date    :22-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '22-June-2012                               <Name>
	 ************************************************************************/
		 
	public String verifyAllRegnFieldsValues(Selenium selenium,
			String strRegionName, String strTimeZone, String strContFN,
			String strContLN, String strOrg, String strAddr,
			String strContactPhone1, String strContactPhone2,
			String strContactFax, String strContactEMail,
			String strEmailFrequency, String strAlertFrequency)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertEquals(strRegionName,
						selenium.getValue(propElementDetails
								.getProperty("Regions.CreateNewRegn.RegnName")));

				assertEquals(strContFN, (selenium.getValue(propElementDetails
						.getProperty("Regions.CreateNewRegn.ContFN"))));

				assertEquals(strContLN, (selenium.getValue(propElementDetails
						.getProperty("Regions.CreateNewRegn.ContLN"))));

				assertEquals(strOrg, (selenium.getValue(propElementDetails
						.getProperty("Regions.CreateNewRegn.Org"))));

				assertEquals(strAddr, (selenium.getValue(propElementDetails
						.getProperty("Regions.CreateNewRegn.Addr"))));

				assertEquals(
						strContactPhone1,
						(selenium.getValue(propElementDetails
								.getProperty("Regions.CreateNewRegn.ContactPhone1"))));

				assertEquals(
						strContactPhone2,
						(selenium.getValue(propElementDetails
								.getProperty("Regions.CreateNewRegn.ContactPhone2"))));

				assertEquals(
						strContactFax,
						(selenium.getValue(propElementDetails
								.getProperty("Regions.CreateNewRegn.ContactFax"))));

				assertEquals(
						strContactEMail,
						(selenium.getValue(propElementDetails
								.getProperty("Regions.CreateNewRegn.ContactEmail"))));

				assertEquals(
						strAlertFrequency,
						(selenium.getValue(propElementDetails
								.getProperty("Regions.CreateNewRegn.AlertFrequency"))));

				log4j.info("All the values entered are retained. ");
			} catch (AssertionError Ae) {
				strErrorMsg = "All the values entered are NOT retained. "
						+ Ae;
				log4j.info("All the values entered are NOT retained. ");
			}

		} catch (Exception e) {
			log4j.info("verifyAllRegnFieldsValues  Function failed" + e);
			strErrorMsg = "verifyAllRegnFieldsValues  Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	 'Description :Fill all the fields
	 'Precondition :None
	 'Arguments  :selenium,strRegionName,strTimeZone,strContFN,strContLN,
	     strOrg,strAddr,strContactPhone1,strContactPhone2,strContactFax,strContactEMail,
	     strEmailFrequency,strAlertFrequency
	 'Returns  :String
	 'Date    :22-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '22-June-2012                               <Name>
	 ************************************************************************/
	 
	 public String fillAllRegnFieldsWitInterFaceKey(Selenium selenium, String strRegionName,
	   String strTimeZone, String strContFN, String strContLN,
	   String strOrg, String strAddr, String strContactPhone1,
	   String strContactPhone2, String strContactFax,
	   String strContactEMail,String strEmailFrequency,
	   String strAlertFrequency,String strInterfaceKey) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  rdExcel = new ReadData();

	  try {
	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   pathProps = objAP.Read_FilePath();

	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.RegnName"),
	     strRegionName);
	   selenium.select(propElementDetails
	     .getProperty("Regions.CreateNewRegn.TimeZone"), "label="
	     + strTimeZone);
	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContFN"), strContFN);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContLN"), strContLN);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.Org"), strOrg);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.Addr"), strAddr);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactPhone1"),
	     strContactPhone1);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactPhone2"),
	     strContactPhone2);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactFax"),
	     strContactFax);

	   selenium.type(propElementDetails
	     .getProperty("Regions.CreateNewRegn.ContactEmail"),
	     strContactEMail);

	   selenium.select(propElementDetails
	     .getProperty("Regions.CreateNewRegn.EmailFrequency"),
	     "label=" + strEmailFrequency);

	   selenium.select(propElementDetails
	     .getProperty("Regions.CreateNewRegn.AlertFrequency"),
	     "label=" + strAlertFrequency);

	   selenium.type("css=input[name='interfaceKey']", strInterfaceKey);
	   

	  } catch (Exception e) {
	   log4j.info("fillAllRegnFields  Function failed" + e);
	   strErrorMsg = "fillAllRegnFields  Function failed" + e;
	  }
	  return strErrorMsg;
	 }
	 
	 /***********************************************************************
	 'Description :Verify 'Create New Region ' page is displayed 
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :11-Jan-2013
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '11-Jan-2013                               <Name>
	 ************************************************************************/
	 
	public String verifyDomainInterfaceInRegionListPge(Selenium selenium,
			String strRegionName, String strInterfaceKey, String strDomain,
			boolean blnIntrface, boolean blnDomain) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Region List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Region List page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Region List page is NOT displayed" + Ae;
				log4j.info("Region List page is NOT displayed" + Ae);
			}

			if (blnIntrface) {

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody"
									+ "/tr/td[2][text()='"
									+ strRegionName
									+ "']/parent::tr/td[5][text()='"
									+ strInterfaceKey + "']"));
					log4j.info("Interface key" + strInterfaceKey
							+ "is listed in region list screen");

				} catch (AssertionError Ae) {
					strErrorMsg = "Interface key " + strInterfaceKey
							+ "is NOT listed in region list screen";
					log4j.info("Interface key " + strInterfaceKey
							+ "is NOT listed in region list screen");

				}
			}

			if (blnDomain) {

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody"
									+ "/tr/td[2][text()='"
									+ strRegionName
									+ "']/parent::tr/td[4][text()='"
									+ strDomain + "']"));
					log4j.info("Interface key" + strInterfaceKey
							+ "is listed in region list screen");

				} catch (AssertionError Ae) {
					strErrorMsg = "Domain " + strDomain
							+ "is NOT listed in region list screen";
					log4j.info("Domain " + strDomain
							+ "is NOT listed in region list screen");

				}

			}

		} catch (Exception e) {
			log4j.info("verifyDomainInterfaceInRegionListPge  Function failed"
					+ e);
			strErrorMsg = "verifyDomainInterfaceInRegionListPge  Function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	 'Description :Fill all the fields
	 'Precondition :None
	 'Arguments  :selenium,strRegionName,strTimeZone,strContFN,strContLN,
	     strOrg,strAddr,strContactPhone1,strContactPhone2,strContactFax,strContactEMail,
	     strEmailFrequency,strAlertFrequency
	 'Returns  :String
	 'Date    :11-Jan-2013
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '11-Jan-2013                               <Name>
	 ************************************************************************/
	 
	public String fillAllRegnFieldsWitInterFaceKeyAndDomain(Selenium selenium,
			String strRegionName, String strTimeZone, String strContFN,
			String strContLN, String strOrg, String strAddr,
			String strContactPhone1, String strContactPhone2,
			String strContactFax, String strContactEMail,
			String strEmailFrequency, String strAlertFrequency,
			String strInterfaceKey, String strDomain) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.RegnName"),
					strRegionName);
			selenium.select(propElementDetails
					.getProperty("Regions.CreateNewRegn.TimeZone"), "label="
					+ strTimeZone);
			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.ContFN"), strContFN);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.ContLN"), strContLN);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.Org"), strOrg);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.Addr"), strAddr);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.ContactPhone1"),
					strContactPhone1);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.ContactPhone2"),
					strContactPhone2);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.ContactFax"),
					strContactFax);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.ContactEmail"),
					strContactEMail);

			selenium.select(propElementDetails
					.getProperty("Regions.CreateNewRegn.EmailFrequency"),
					"label=" + strEmailFrequency);

			selenium.select(propElementDetails
					.getProperty("Regions.CreateNewRegn.AlertFrequency"),
					"label=" + strAlertFrequency);

			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.InterfaceKey"), strInterfaceKey);
			selenium.type(propElementDetails
					.getProperty("Regions.CreateNewRegn.Domain"), strDomain);

		} catch (Exception e) {
			log4j
					.info("fillAllRegnFieldsWitInterFaceKeyAndDomain  Function failed"
							+ e);
			strErrorMsg = "fillAllRegnFieldsWitInterFaceKeyAndDomain  Function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	//start//clickOnAssignUsersInOtherRegionPage//
	/*******************************************************************************************
	' Description		: Click on assign user link
	' Precondition		: N/A 
	' Arguments			: selenium, strRegionName
	' Returns			: String 
	' Date				: 16/09/2013
	' Author			: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String clickOnAssignUsersInOtherRegionPage(Selenium selenium,
			String strRegionName) throws Exception {
		String strReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/table/tbody/tr"
					+ "/td[2][text()='" + strRegionName + "']/parent::tr/td[1]"
					+ "/a[text()='assign users']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Assign Users to " + strRegionName + "",
							selenium.getText(propElementDetails
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
				assertEquals("Assign Users to " + strRegionName + "",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Assign Users to " + strRegionName
						+ "' screen is displayed. ");
			} catch (AssertionError Ae) {
				strReason = "'Assign Users to " + strRegionName
						+ "' screen is NOT displayed. " + Ae;
				log4j.info("'Assign Users to " + strRegionName
						+ "' screen is NOT displayed.  " + Ae);
			}

		} catch (Exception e) {
			log4j.info("clickOnAssignUsersInOtherRegionPage function failed");
			strReason = "clickOnAssignUsersInOtherRegionPage function failed"
					+ e;
		}

		return strReason;
	}
	// end//clickOnAssignUsersInOtherRegionPage//
}
