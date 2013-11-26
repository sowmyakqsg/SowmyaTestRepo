package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/****************************************************************************
' Description       :This class includes common functions of HICS Application
' Requirement Group :Notifications
' Requirement       :ICS Notifications
' Date	            :23-April-2012
' Author	        :QSG
'--------------------------------------------------------------------------- 
'***************************************************************************/

public class HICSNotificationsFunctions {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.HICS_Functions");
	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;
	public String gstrTimeOut = "";
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();
	ReadData rdExcel;
	
	/***********************************************************
	' Description  : Login as a system admin to hics application
	' Arguments    : selenium, pStrUserName, pStrPassword
	' Returns      : String 
	' Date         : 18/08/2012
	' Author       : QSG 
	'------------------------------------------------------------
	' Modified Date: 
	' Modified By: 
	*************************************************************/

	public String loginAsSystemAdmin(Selenium selenium, String pStrUserName,
			String pStrPassword) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strUrl = propEnvDetails.getProperty("HICSUrl");
		try {
			selenium.open(strUrl);
			if (selenium.isElementPresent("link=Log Out")) {
				this.logoutFromHicsApplication(selenium);
			}
			int intCnt = 0;
			while (selenium.isElementPresent(propElementDetails
					.getProperty("Prop521")) == false && intCnt < 60) {
				Thread.sleep(1000);
				intCnt++;
			}
			selenium.type(propElementDetails.getProperty("Prop521"),
					pStrUserName);
			selenium.type(propElementDetails.getProperty("Prop522"),
					pStrPassword);
			selenium.click(propElementDetails.getProperty("Prop523"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isTextPresent("System Admin"));				 
				log4j.info("'HICS Home page is displayed");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'HICS Home page is NOT  displayed");
				lStrReason = lStrReason + "; "
						+ "HICS Home page is NOT  displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*************************************************************
	' Description  : Function to logout from the hics application
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 18/08/2012
	' Author       : QSG 
	'-------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	***************************************************************/

	public String logoutFromHicsApplication(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("Prop561"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Hospital-ICS ~ Login", selenium.getTitle());
				log4j.info("Logout is successfull and welcome screen is displayed.");

			} catch (AssertionError Ae) {
				log4j.info("Logout is NOT successfull and welcome screen is NOT displayed.");
				lStrReason = lStrReason
						+ ", Logout is NOT successfull and welcome screen is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/***************************************************************************
	'Description	:Function is used to navigate to home page with confirmation.
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:18/08/2012
	'Author			:QSG
	'---------------------------------------------------------------------------
	'Modified Date                                                 Modified By
	'22-June-2012                                                  <Name>
	****************************************************************************/

	public String navToHomePageWithConfirmation(Selenium selenium)
			throws Exception {
		String lStrReason = "";
		WaitForElement objWaitForElement = new WaitForElement();
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click("css=img[alt=\"Home\"]");
			objWaitForElement.WaitForElementPresent_WithoutProperty(selenium,
					"css=#confirmDialog", "confirmationWindow");
			selenium.click("//div[@id='confirmDialog']/following-sibling::div/div" +
					"/button/span[text()='Yes']/parent::button");
			objWaitForElement.WaitForPageToLoad(selenium);

			int intCnt = 0;
			boolean blnFound = false;
			while (blnFound == false && intCnt < 60) {
				try {
					assertEquals(
							"Home - Active Incidents",
							selenium.getText("css=#ctl00_navbar_Navigation1_contentTitle"));
					blnFound = true;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			try {
				assertEquals(
						"Home - Active Incidents",
						selenium.getText("css=#ctl00_navbar_Navigation1_contentTitle"));
				log4j.info("Navigation to home page is successfull.");

			} catch (AssertionError Ae) {
				log4j.info("Navigation to home page is NOT successfull.");
				lStrReason = lStrReason
						+ ", Navigation to home page is NOT successfull.";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/******************************************************************
	' Description:This function ends created incident for the facility.
	' Arguments  :selenium
	' Returns    :String 
	' Date       :18/08/2012
	' Author     :QSG 
	'------------------------------------------------------------------
	' Modified Date:                                     Modified By: 
	*******************************************************************/

	public String EndIncidentForFacility(Selenium selenium, String strFacility)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		WaitForElement objWaitForElement = new WaitForElement();
				
		try {			
			  Thread.sleep(9000);			
			if (selenium.isTextPresent("No Active Incidents")) {
				log4j.info("No Active Incidents.");

			} else if (selenium
					.isElementPresent("//table/tbody/tr/td/div[text()='"
							+ strFacility + "']")) {
				
				try {
					assertTrue(selenium
							.isElementPresent("//table/tbody/tr/td/div[text()='"
									+ strFacility + "']"));
					selenium.click("//table/tbody/tr/td/div[text()='"
							+ strFacility
							+ "']"
							+ "/parent::td/preceding-sibling::td/a[text()='View']");
					objWaitForElement.WaitForPageToLoad(selenium);
					// Click on end incident					
					 Thread.sleep(9000);
					selenium.click("css=input[id='EndIncident'][type='image']");
					objWaitForElement.WaitForPageToLoad(selenium);
					// OK Click
					selenium.click("//div[@id='selectContactDialog']/following-sibling::div/div/button/span[text()='OK']/parent::button");
					objWaitForElement.WaitForPageToLoad(selenium);
					this.navToHomePageWithConfirmation(selenium);

				} catch (AssertionError Ae) {
					log4j.info("No incident associated with the facility "
							+ strFacility);
					lStrReason = "No incident associated with the facility "
							+ strFacility;
				}
			} else {
				log4j.info("No incident associated with tht facility.");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	
	/********************************************************************************************
	' Description: Function is used to create the incident with the Incident name as a parameter.
	' Arguments  : selenium,pStrIRGName,pStrIncName,strIncDesc
	' Returns    : String 
	' Date       : 18/08/2012
	'--------------------------------------------------------------------------------------------
	' Modified Date: 
	' Modified By: 
	*********************************************************************************************/
	
	public String CreateIncidentWithIncidentName(Selenium selenium,
			String pStrIRGName, String pStrIncName, String strIncDesc)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		WaitForElement objWait = new WaitForElement();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			objWait.WaitForElementsWithoutProperty(selenium,
					"//div/table[@id='irgList']/tbody/tr/td[text()='"
							+ pStrIRGName + "']", "CreatedIRG");
			// click IRG
			selenium.focus("//div/table[@id='irgList']/tbody/tr/td[text()='"
					+ pStrIRGName + "']");
			selenium.click("//div/table[@id='irgList']/tbody/tr/td[text()='"
					+ pStrIRGName + "']");

			// click next
			selenium.click("css=input[id='nextPage']");
			objWait.WaitForPageToLoad(selenium);

			try {
				objWait.WaitForElementsWithoutProperty(
						selenium,
						"css=input[id='page2Start'][value='Activate Incident']",
						"ActiveIncButton");
				assertTrue(selenium
						.isElementPresent("css=input[id='page2Start'][value='Activate Incident']"));
				log4j.info("Incident Details screen is displayed. ");

				selenium.type("css=#incidentName", pStrIncName);
				selenium.type("id=incidentDescription", strIncDesc);
				selenium.typeKeys("id=incidentDescription", strIncDesc);

				// click Active incident
				selenium.click("css=input[id='page2Start'][value='Activate Incident']");
				objWait.WaitForPageToLoad(selenium);
				objWait.WaitForElementsWithoutProperty(selenium,
						"css=#editIncidentButton", "EditIncButton");
				try {
					assertTrue(selenium
							.isElementPresent("css=#editIncidentButton"));
					log4j.info("Incident dashboard is displayed. ");

				} catch (AssertionError Ae) {
					log4j.info("Incident dashboard is NOT displayed. ");
					lStrReason = "Incident dashboard is NOT displayed.";
				}

			} catch (AssertionError Ae) {
				log4j.info("Incident Details screen is NOT displayed. ");
				lStrReason = "Incident Details screen is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/******************************************************************************
	' Description:Function to navigate to create Incident page and  select facility
	' Arguments  :selenium
	' Returns    :String 
	' Date       :18/08/2012
	' Author     :QSG 
	'------------------------------------------------------------------------------
	' Modified Date:                                     Modified By: 
	*******************************************************************************/
	
	public String navToCreatIncidentPage(Selenium selenium
			) throws Exception {
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		WaitForElement objWaitForElement=new WaitForElement();
		try{
			selenium.focus("//li[text()='Response']");
			selenium.mouseOver("//li[text()='Response']");		
			int intCnt=0;
			while(selenium.isElementPresent("//li[text()='Response']/ul/li" +
					"[text()='Create�Incident']")==false && intCnt<20){
				Thread.sleep(1000);
				intCnt++;
			}
			Thread.sleep(2000);
			selenium.click("//li[text()='Response']/ul/li[text()='Create�Incident']");
			selenium.waitForPageToLoad(gstrTimeOut);
			objWaitForElement.WaitForPageToLoad(selenium);
			
			try {
				assertEquals(
						"Create Incident",
						selenium.getText("css=#ctl00_navbar_Navigation2_contentTitle"));
				log4j.info("Create Incident screen is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Create Incident screen is NOT displayed");
				lStrReason = lStrReason
						+ " ,Create Incident screen is NOT displayed";
			}
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*****************************************************************
	' Description:Function to select facility and verify irg presence
	' Arguments  :selenium,pStrFacilityName,pStrIRGName
	' Returns    :String 
	' Date       :18/08/2012
	' Author     :QSG 
	'----------------------------------------------------------------
	' Modified Date:                                     Modified By: 
	*****************************************************************/
	
	public String selFacilityAndVerIRGPresent(Selenium selenium,
			String pStrFacilityName, String pStrIRGName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		WaitForElement objWait = new WaitForElement();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.select("id=ctl00_body_createIncidentFacilities", "label="
					+ pStrFacilityName);
			selenium.typeKeys("id=ctl00_body_createIncidentFacilities", "\\13");
			objWait.WaitForPageToLoad(selenium);

			try {
				assertTrue(selenium
						.isElementPresent("//div/table[@id='irgList']/tbody/tr/td[text()='"
								+ pStrIRGName + "']"));
				log4j.info("Updated "
						+ pStrIRGName
						+ " name is displayed in the list on Create Incident screen. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Updated "
						+ pStrIRGName
						+ " name is displayed in the list on Create Incident screen. ");
				lStrReason = "Updated "
						+ pStrIRGName
						+ " name is displayed in the list on Create Incident screen. ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/**********************************************************************************
	' Description  :Function to logout from the hics application with the confirmation
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 18/08/2012
	' Author       : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	***********************************************************************************/

	public String LogoutWithConfirmation(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		WaitForElement objWaitForElement = new WaitForElement();
		try {
			selenium.clickAt("link=Log Out", "1,1");
			objWaitForElement.WaitForPageToLoad(selenium);
			int intCnt = 0;
			boolean blnFound = false;
			while (blnFound == false && intCnt < 60) {
				try {
					objWaitForElement
							.WaitForElementPresent_WithoutProperty(
									selenium,
									"//div[@id='confirmDialog']"
											+ "/following-sibling::div/div/button/span[text()='Yes']/parent::button",
									"confirmationWindowOKButton");
					selenium.clickAt(
							"//div[@id='confirmDialog']/following-sibling::div/div/button/span[text()='Yes']/parent::button",
							"1,1");
					blnFound = true;
					break;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			intCnt = 0;
			blnFound = false;
			while (blnFound == false && intCnt < 60) {
				try {
					assertEquals("Hospital-ICS ~ Login", selenium.getTitle());
					blnFound = true;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			try {
				assertEquals("Hospital-ICS ~ Login", selenium.getTitle());
				log4j.info("Logout is successfull and welcome screen is displayed.");

			} catch (AssertionError Ae) {
				log4j.info("Logout is NOT successfull and welcome screen is NOT displayed.");
				lStrReason = lStrReason
						+ ", Logout is NOT successfull and welcome screen is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}

/*****************************************************************
' Description  :Function to select facility and verify irg presence
' Arguments    :selenium,pStrFacilityName,pStrIRGName
' Returns      :String 
' Date         :18/08/2012
' Author       :QSG 
'----------------------------------------------------------------
' Modified Date:                                     Modified By: 
*****************************************************************/

	public String createDrillIncident(Selenium selenium, String pStrIRGName,
			String pStrIncName, String strIncDesc) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		WaitForElement objWait = new WaitForElement();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium.isTextPresent("Actual Incident"));
				assertTrue(selenium.isTextPresent("Exercise/Drill"));
				log4j
						.info("Type of Incident:1. Actual 2. Exercise/Drill are displayed with their icons");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Type of Incident:1. Actual 2. Exercise/Drill are NOT displayed with their icons");
				lStrReason = lStrReason
						+ "; "
						+ "Type of Incident:1. Actual 2. Exercise/Drill are NOT displayed with their icons";
			}

			objWait.WaitForElementsWithoutProperty(selenium, "css=td[title=\""
					+ pStrIRGName + "\"]", pStrIRGName);

			selenium.click("css=td[title=\"" + pStrIRGName + "\"]");
			selenium.click("id=typeExerciseRadio"); // Drill Inci.
			selenium.click("css=#nextPage");

			// Click on IRG name
			/*// click IRG
			selenium.focus("//div/table[@id='irgList']/tbody/tr/td[text()='"
					+ pStrIRGName + "']");
			selenium.click("//div/table[@id='irgList']/tbody/tr/td[text()='"
					+ pStrIRGName + "']");
			*/
			try {
				try{
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop1144")));
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop709")));
				log4j.info("The 'Incident Detail' screen displayed and 'Select Voice Notification Contacts..' option available at the bottom of the screen");
				}catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("The 'Incident Detail' screen NOT displayed and 'Select Voice Notification Contacts..' option available at the bottom of the screen");
					lStrReason = lStrReason + "; " + "The 'Incident Detail' screen NOT displayed and 'Select Voice Notification Contacts..' option available at the bottom of the screen";
				}
				
				
				selenium.type("css=#incidentName", pStrIncName);
				selenium.type("id=incidentDescription", strIncDesc);
				selenium.typeKeys("id=incidentDescription", strIncDesc);

				// click Active incident
				selenium
						.click("css=#page2Start");
				objWait.WaitForPageToLoad(selenium);
				objWait.WaitForElementsWithoutProperty(selenium,
						"css=#editIncidentButton", "EditIncButton");
				try {
					assertTrue(selenium
							.isElementPresent("css=#editIncidentButton"));
					log4j.info("Incident dashboard is displayed. ");

				} catch (AssertionError Ae) {
					log4j.info("Incident dashboard is NOT displayed. ");
					lStrReason = "Incident dashboard is NOT displayed.";
				}

			} catch (Exception e) {
				log4j.info(e);
				lStrReason = lStrReason + "; " + e.toString();
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}


	//start//createIncidentWithAllowStateOrRegion//
	/*******************************************************************************************
	' Description	: Creating  incident by selecting or de-selecting allow state
	' Precondition	: N/A 
	' Arguments		: selenium, strIRGName, strIncName, strIncDesc, blnAllowState
	' Returns		: String 
	' Date			: 19/09/2013
	' Author		: Suhas
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String createIncidentWithAllowStateOrRegion(Selenium selenium, String strIRGName, String strIncName,
			String strIncDesc, boolean blnAllowState) throws Exception
	{
		String strReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		WaitForElement objWait = new WaitForElement();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			
			objWait.WaitForElementsWithoutProperty(selenium,
					"//div/table[@id='irgList']/tbody/tr/td[text()='"
							+ strIRGName + "']", "CreatedIRG");
			// click IRG
			selenium.focus("//div/table[@id='irgList']/tbody/tr/td[text()='"
					+ strIRGName + "']");
			selenium.click("//div/table[@id='irgList']/tbody/tr/td[text()='"
					+ strIRGName + "']");

			// click next
			selenium.click("css=input[id='nextPage']");
			objWait.WaitForPageToLoad(selenium);

			try {
				objWait.WaitForElementsWithoutProperty(
						selenium,
						"css=input[id='page2Start'][value='Activate Incident']",
						"ActiveIncButton");
				assertTrue(selenium
						.isElementPresent("css=input[id='page2Start'][value='Activate Incident']"));
				log4j.info("Incident Details screen is displayed. ");

				selenium.type("css=#incidentName", strIncName);
				selenium.type("id=incidentDescription", strIncDesc);
				selenium.typeKeys("id=incidentDescription", strIncDesc);
				
				//selecting allow region
				if (blnAllowState) {
					if (selenium.isChecked("css=input[id='ctl00_body_visibilityCheck']" +
							"[name='ctl00$body$visibilityCheck']") == false) {
						selenium.click("css=input[id='ctl00_body_visibilityCheck']" +
							"[name='ctl00$body$visibilityCheck']");
					}
				} else {
					if (selenium.isChecked("css=input[id='ctl00_body_visibilityCheck']" +
							"[name='ctl00$body$visibilityCheck']]")) {
						selenium.click("css=input[id='ctl00_body_visibilityCheck']" +
							"[name='ctl00$body$visibilityCheck']");
					}
				}

				// click Active incident
				selenium.click("css=input[id='page2Start'][value='Activate Incident']");
				objWait.WaitForPageToLoad(selenium);
				objWait.WaitForElementsWithoutProperty(selenium,
						"css=#editIncidentButton", "EditIncButton");
				try {
					assertTrue(selenium
							.isElementPresent("css=#editIncidentButton"));
					log4j.info("Incident dashboard is displayed. ");

				} catch (AssertionError Ae) {
					log4j.info("Incident dashboard is NOT displayed. ");
					strReason = "Incident dashboard is NOT displayed.";
				}

			} catch (AssertionError Ae) {
				log4j.info("Incident Details screen is NOT displayed. ");
				strReason = "Incident Details screen is NOT displayed.";
			}
			
		}catch(Exception e){
			log4j.info(e);
			strReason = "HICSNotificationsFunctions.createIncidentWithAllowStateOrRegion failed to complete due to " +strReason + "; " + e.toString();
		}

		return strReason;
	}
	//end//createIncidentWithAllowStateOrRegion//
}
