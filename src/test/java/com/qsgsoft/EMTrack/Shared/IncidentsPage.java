package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.apache.log4j.Logger;


import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class IncidentsPage{
	
	Properties ElementDetails;	
	static Logger log4j = Logger.getLogger("IncidentsPage");
	public Properties propElementDetails, propEnvDetails;
	public String gstrTimeOut;


    //start//NavToIncidentsPge//
	/*******************************************************************************************
	' Description  : Function to navigate to Incidents page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 24-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String navToIncidentsPge(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		WaitForElement waitForElements = new WaitForElement();
		try {
			Thread.sleep(20000);
			selenium.mouseOver(propElementDetails.getProperty("incidentsLink"));
			selenium.focus(propElementDetails.getProperty("incidentsLink"));
			selenium.click(propElementDetails.getProperty("incidentsLink"));
			Thread.sleep(5000);
			waitForElements
					.WaitForElementsWithoutProperty(
							selenium,
							"//span[@class='x-btn-wrap']/span/span[@class='x-btn-inner x-btn-inner-center']",
							"Incident Button");

			try {
				assertTrue(selenium.isVisible(propElementDetails
						.getProperty("CreateIncidentBtn")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateIncidentBtn")));
				log4j.info("Incidents page is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Incidents page is NOT displayed");
				lStrReason = "Incidents page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.NavToIncidentsPge failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//NavToIncidentsPge//

	/*******************************************************************************************
	' Description : Function to Click on Create Incident button
	' Precondition: N/A 
	' Arguments   : selenium
	' Returns     : String 
	' Date        : 29-10-2013
	' Author      : Manasa 
	*******************************************************************************************/
	public String clickOnCreateIncidentBtn(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {

			selenium.focus(propElementDetails.getProperty("CreateIncidentBtn"));
			selenium.click(propElementDetails.getProperty("CreateIncidentBtn"));
			
			try {
				assertEquals(selenium.getText(propElementDetails
						.getProperty("SelIncidTypeTitle")),
						"Select Incident Type");
				log4j.info("Select Incident Type page is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Select Incident Type page is NOT displayed");
				lStrReason = "Select Incident Type page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.ClickOnCreateIncidentBtn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description : Function to select the incident type from the drop down
	' Precondition: N/A 
	' Arguments   : selenium,string,boolean
	' Returns     : String 
	' Date        : 29-10-2013
	' Author      : Manasa 
	*******************************************************************************************/
	public String selIncidentType(Selenium selenium,String strIncidentType,boolean blnCreate) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
				
		try{
			
			selenium.focus(propElementDetails.getProperty("SelIncidentType"));
			selenium.click(propElementDetails.getProperty("SelIncidentType"));
			Thread.sleep(1000);	
									
			try{
			selenium.focus("//ul[@class='x-list-combo']/li[text()='"+strIncidentType+"']");
			selenium.click("//ul[@class='x-list-combo']/li[text()='"+strIncidentType+"']");
					
			try{
			assertEquals(selenium.getValue("//table[@class='x-form-trigger-wrap']/tbody/tr/td/input[@name='incidentType']"),strIncidentType);
			log4j.info("Incident Type "+strIncidentType+" is Selected from the list");
			}catch(AssertionError ae)
			{
				log4j.info("Incident Type "+strIncidentType+" is NOT Selected from the list");
				lStrReason="Incident Type "+strIncidentType+" is NOT Selected from the list";
			}
			
			}catch(AssertionError ae)
			{
				log4j.info("Incident Type "+strIncidentType+" is NOT Selected from the list");
				lStrReason="Incident Type "+strIncidentType+" is NOT Selected from the list";
			}
					
			@SuppressWarnings("unused")
			int intCnt=0;
			if (blnCreate) {
				selenium.focus(propElementDetails.getProperty("InciTypeCreateBtn"));
				selenium.click(propElementDetails.getProperty("InciTypeCreateBtn"));
				Thread.sleep(1000);				
				try {
						assertEquals("Create Incident",selenium.getText(propElementDetails.getProperty("CreateIncidentTitle")));
						log4j.info("Create Incident page is displayed");
																	
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}
					
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "IncidentsPage.ClickOnCreateIncidentBtn failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	//start//CreateIncident//
	/*******************************************************************************************
	' Description  : Function to create the new incident
	' Precondition : N/A 
	' Arguments    : selenium, pStrName, pStrDescription,strApplcnVal,blnCreate
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa
	*******************************************************************************************/
	public String createIncident(Selenium selenium, String pStrName, String pStrDescription,String strApplcnVal,boolean blnCreate) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			//selenium.type("//div[@class='x-window-body x-window-body-default x-layout-fit x-closable x-window-body-closable x-window-body-default-closable x-window-body-default x-window-body-default-closable']/div/div/span/div/fieldset/div/span/div/table/tbody/tr/td/input[@name='name']",pStrName);
			selenium.type(propElementDetails.getProperty("CreateIncidentName"), pStrName);
			selenium.type(propElementDetails.getProperty("CreateIncidentDesc"),pStrDescription);
			
			selenium.focus(propElementDetails.getProperty("CreateIncidentApplicn"));
			selenium.click(propElementDetails.getProperty("CreateIncidentApplicn"));
			
			selenium.focus("//div[@class='x-window-body x-window-body-default x-layout-fit x-closable x-window-body-closable x-window-body-default-closable x-window-body-default x-window-body-default-closable']/following::div/div/ul[@class='x-list-plain']/li[text()='"+strApplcnVal+"']");
			selenium.click("//div[@class='x-window-body x-window-body-default x-layout-fit x-closable x-window-body-closable x-window-body-default-closable x-window-body-default x-window-body-default-closable']/following::div/div/ul[@class='x-list-plain']/li[text()='"+strApplcnVal+"']");
			
			
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "IncidentsPage.CreateIncident failed to complete due to " +lStrReason + "; " + e.toString();
		}

		@SuppressWarnings("unused")
		int intCnt=0;
		if (blnCreate) {
			selenium.focus(propElementDetails.getProperty("CreateInciSave"));
			selenium.click(propElementDetails.getProperty("CreateInciSave"));
			Thread.sleep(20000);
					
			try {
					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("InciTypeCreateBtn")));
					log4j.info("Incidents is displayed");
																
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
		return lStrReason;
	}
	//end//CreateIncident//
	
	//start//vfyCreatedIncident//
	/*******************************************************************************************
	' Description  : Function to verify the created incident
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncidentname, pStrIncidentTypeName
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String vfyCreatedIncident(Selenium selenium, String pStrIncidentname, String pStrIncidentTypeName) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			
			try{
				assertTrue(selenium.isElementPresent("//div[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']/table/tbody/tr/td/div[contains(text(),'"+pStrIncidentname+"')]/parent::td/following-sibling::td/div[contains(text(),'"+pStrIncidentTypeName+"')]"));
				log4j.info("Created incident is displayed on the incidents page");
				
			}catch(AssertionError Ae)
			{
				log4j.info("Created incident is NOT displayed on the incidents page");
				lStrReason = "IncidentsPage.vfyCreatedIncident failed to complete due to " +lStrReason + "; " + Ae.toString();
			}
					
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "IncidentsPage.CreateIncident failed to complete due to " +lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//vfyCreatedIncident//
	
	//start//endCreatedIncident//
	/*******************************************************************************************
	' Description  : Function to end the created incident
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncidentname, pStrIncidentTypeName
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String endCreatedIncident(Selenium selenium, String pStrIncidentname, String pStrIncidentTypeName) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		//WaitForElement waitForElements = new WaitForElement();
		try{
			
			try{
				assertTrue(selenium.isElementPresent("//div[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']/table/tbody/tr/td/div[contains(text(),'"+pStrIncidentname+"')]/parent::td/following-sibling::td/div[contains(text(),'"+pStrIncidentTypeName+"')]/parent::td/following-sibling::td/div/div[@class='btn btn-block btn-small btn-danger action-end']"));
				selenium.focus("//div[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']/table/tbody/tr/td/div[contains(text(),'"+pStrIncidentname+"')]/parent::td/following-sibling::td/div[contains(text(),'"+pStrIncidentTypeName+"')]/parent::td/following-sibling::td/div/div[@class='btn btn-block btn-small btn-danger action-end']");
				selenium.click("//div[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']/table/tbody/tr/td/div[contains(text(),'"+pStrIncidentname+"')]/parent::td/following-sibling::td/div[contains(text(),'"+pStrIncidentTypeName+"')]/parent::td/following-sibling::td/div/div[@class='btn btn-block btn-small btn-danger action-end']");
				Thread.sleep(5000);
				try{
					assertEquals(selenium.getText(propElementDetails.getProperty("ConfirmWin")),"End Incident?");
					log4j.info("Confirmation window is displayed");
				}catch(AssertionError Ae)
				{
					log4j.info("Confirmation window is NOT displayed");
					lStrReason = "IncidentsPage.endCreatedIncident failed to complete due to " +lStrReason + "; " + Ae.toString();
				}
				
				selenium.focus("//div[@class='x-window x-message-box x-layer x-window-default x-closable x-window-closable x-window-default-closable x-border-box']/div/following::div/div/div/a/span/span/span[text()='Yes']");
				selenium.click("//div[@class='x-window x-message-box x-layer x-window-default x-closable x-window-closable x-window-default-closable x-border-box']/div/following::div/div/div/a/span/span/span[text()='Yes']");
				Thread.sleep(20000);
				try{
					assertFalse(selenium.isElementPresent("//div[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']/table/tbody/tr/td/div[contains(text(),'"+pStrIncidentname+"')]/parent::td/following-sibling::td/div[contains(text(),'"+pStrIncidentTypeName+"')]"));
					log4j.info("Ended incident is NOT displayed on the incident page");
					
				}catch(AssertionError Ae)
				{
					log4j.info("Ended incident is displayed on the incident page");
					lStrReason = "IncidentsPage.endCreatedIncident failed to complete due to " +lStrReason + "; " + Ae.toString();
				}
			}catch(AssertionError Ae)
			{
				log4j.info("Created incident is NOT displayed on the incidents page");
				lStrReason = "IncidentsPage.endCreatedIncident failed to complete due to " +lStrReason + "; " + Ae.toString();
			}	
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "IncidentsPage.endCreatedIncident failed to complete due to " +lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//endCreatedIncident//
	
	//start//vfyCreatedIncident//
	/*******************************************************************************************
	' Description  : Function to verify the created incident
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncidentname, pStrIncidentTypeName
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String selTheIncidentStatus(Selenium selenium, String pStrIncidentStatus) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			
			try{
				selenium.focus(propElementDetails.getProperty("IncidentStatus"));
				selenium.click(propElementDetails.getProperty("IncidentStatus"));
				
				selenium.focus("//div[@class='x-boundlist-list-ct x-unselectable']/ul/li[text()='"+pStrIncidentStatus+"']");
				selenium.click("//div[@class='x-boundlist-list-ct x-unselectable']/ul/li[text()='"+pStrIncidentStatus+"']");
				Thread.sleep(10000);
				
			}catch(AssertionError Ae)
			{
				log4j.info("Incident status is NOT selected");
				lStrReason = "IncidentsPage.selTheIncidentStatus failed to complete due to " +lStrReason + "; " + Ae.toString();
			}
					
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "IncidentsPage.selTheIncidentStatus failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//vfyCreatedIncident//
	
	//start//vfyTheIncidentStatus//
	/*******************************************************************************************
	' Description  : Function to verify the status of the created incident
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncidentname, pStrIncidentTypeName
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String vfyTheIncidentStatus(Selenium selenium,
			String pStrIncidentname, String pStrIncidentTypeName,
			String pStrIncidentStatus) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertTrue(selenium
						.isElementPresent("//div[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']/table/tbody/tr/td/div[contains(text(),'"
								+ pStrIncidentname
								+ "')]/parent::td/following-sibling::td/div[contains(text(),'"
								+ pStrIncidentTypeName
								+ "')]/parent::td/following-sibling::td/div[contains(text(),'"
								+ pStrIncidentStatus + "')]"));
				log4j.info("Incident is displayed with the appropriate status");
			} catch (AssertionError Ae) {
				log4j.info("Incident is NOT displayed with the appropriate status");
				lStrReason = "IncidentsPage.vfyTheIncidentStatus failed to complete due to "
						+ lStrReason + "; " + Ae.toString();
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.vfyTheIncidentStatus failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyTheIncidentStatus//
	
	//start//changeLocation//
	/*******************************************************************************************
	' Description  : Function to click on 'Any Where USA' link and select Location 
	' Precondition : N/A 
	' Arguments    : selenium, pStrLocationName
	' Returns      : String 
	' Date         : 13-11-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String changeLocation(Selenium selenium, String pStrLocationName) throws Exception{
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		WaitForElement waitForElements = new WaitForElement();
		
		try {
			selenium.click(propElementDetails.getProperty("divisionLink"));
			
			try {
				waitForElements.WaitForElements(selenium, "selLocWindowTitle", "Select Location Window");
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("selLocWindowTitle")));
				log4j.info("'Any Where USA' link clicked");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Any Where USA' link Not clicked");
				lStrReason = "'Any Where USA' link Not clicked";
			}
			
			waitForElements.WaitForElementsWithoutProperty(selenium, "//div[@id='navigatorTree']/ul/li/ul/li/a[text()='"+pStrLocationName+"']", pStrLocationName);
			selenium.focus("//div[@id='navigatorTree']/ul/li/ul/li/a[text()='"+pStrLocationName+"']");
			selenium.click("//div[@id='navigatorTree']/ul/li/ul/li/a[text()='"+pStrLocationName+"']");
			Thread.sleep(30000);
			//selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				waitForElements.WaitForElements(selenium, "configureButton", "Configure Button");
				assertTrue(selenium
						.isElementPresent(propElementDetails.getProperty("configureButton")));
				log4j.info(pStrLocationName+" link is clicked");
			} catch (AssertionError assertionFailedError) {
				log4j.info(assertionFailedError);
				log4j.info(pStrLocationName+" link is Not clicked");
				lStrReason = pStrLocationName+" link is Not clicked";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.changeLocation failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//changeLocation//
	
	//start//vfyCreatedIncidentNotPresentInOtherLocation//
	/*******************************************************************************************
	' Description  : Function to verify the created incident Not displayed in Other Location
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncidentname, pStrIncidentTypeName
	' Returns      : String 
	' Date         : 13-11-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String vfyCreatedIncidentNotPresentInOtherLocation(
			Selenium selenium, String pStrIncidentname,
			String pStrIncidentTypeName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertFalse(selenium
						.isElementPresent("//div[@class='x-grid-view x-fit-item x-grid-view-default x-unselectable']/table/tbody/tr/td/div[contains(text(),'"
								+ pStrIncidentname
								+ "')]/parent::td/following-sibling::td/div[contains(text(),'"
								+ pStrIncidentTypeName + "')]"));
				log4j.info("Created incident is Not displayed on the incidents page in Other Location");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Created incident is displayed on the incidents page in Other Location");
				lStrReason = "Created incident is displayed on the incidents page in Other Location";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.vfyCreatedIncidentNotPresentInOtherLocation failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyCreatedIncidentNotPresentInOtherLocation//
	
	//start//createIncidentWithProvider//
	/*******************************************************************************************
	' Description  : Function to create the new incident by adding Provider
	' Precondition : N/A 
	' Arguments    : selenium, pStrName, pStrDescription,strApplcnVal,blnCreate,pStrProviderName
	' Returns      : String 
	' Date         : 13-11-2013
	' Author       : Rahul
	*******************************************************************************************/
	public String createIncidentWithProvider(Selenium selenium,
			String pStrName, String pStrDescription, String strApplcnVal,
			boolean blnCreate, String pStrProviderName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails.getProperty("CreateIncidentName"),
					pStrName);
			log4j.info(pStrName + " Name entered for 'Name:' field");

			selenium.type(propElementDetails.getProperty("CreateIncidentDesc"),
					pStrDescription);
			log4j.info(pStrDescription
					+ " Description entered for 'Description:' field");

			selenium.focus(propElementDetails
					.getProperty("CreateIncidentApplicn"));
			selenium.click(propElementDetails
					.getProperty("CreateIncidentApplicn"));

			selenium.focus("//div[@class='x-window-body x-window-body-default x-layout-fit x-closable x-window-body-closable x-window-body-default-closable x-window-body-default x-window-body-default-closable']/following::div/div/ul[@class='x-list-plain']/li[text()='"
					+ strApplcnVal + "']");
			selenium.click("//div[@class='x-window-body x-window-body-default x-layout-fit x-closable x-window-body-closable x-window-body-default-closable x-window-body-default x-window-body-default-closable']/following::div/div/ul[@class='x-list-plain']/li[text()='"
					+ strApplcnVal + "']");
			log4j.info(strApplcnVal + " selected for 'Application:' input");

			selenium.focus("//div[contains(@id,'incidententitygrid')]/div/div[contains(@id,'gridpanel')]/div[contains(@id,'gridview')]/table/tbody/tr[contains(@id,'gridview')]/td/div[text()='"
					+ pStrProviderName
					+ "']/parent::td/parent::tr/td/div/div/div/i[@class='icon-circle-blank -3']");
			selenium.click("//div[contains(@id,'incidententitygrid')]/div/div[contains(@id,'gridpanel')]/div[contains(@id,'gridview')]/table/tbody/tr[contains(@id,'gridview')]/td/div[text()='"
					+ pStrProviderName
					+ "']/parent::td/parent::tr/td/div/div/div/i[@class='icon-circle-blank -3']");
			log4j.info("'Full in command' option selected For "
					+ pStrProviderName + " Location");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.createIncidentWithProvider failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		@SuppressWarnings("unused")
		int intCnt = 0;
		if (blnCreate) {
			selenium.focus(propElementDetails.getProperty("CreateInciSave"));
			selenium.click(propElementDetails.getProperty("CreateInciSave"));
			Thread.sleep(20000);

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("InciTypeCreateBtn")));
				log4j.info("Incidents is displayed");

			} catch (AssertionError Ae) {
				Thread.sleep(1000);
				intCnt++;

			} catch (Exception Ae) {
				Thread.sleep(1000);
				intCnt++;
			}
		}
		return lStrReason;
	}
	// end//createIncidentWithProvider//
	
	//start//editCreatedIncidentAndProvideAccess//
	/*******************************************************************************************
	' Description  : Function to Edit the created incident and Add Provider
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncidentName, pStrProviderName
	' Returns      : String 
	' Date         : 14-11-2013
	' Author       : Rahul
	*******************************************************************************************/
	public String editCreatedIncidentAndProvideAccess(Selenium selenium,String pStrIncidentName,
			String pStrProviderName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		WaitForElement waitForElement = new WaitForElement();

		try {
			selenium.focus("//div[@id='incidents']/div/div[contains(@id,'gridpanel')]/div/table/tbody/tr/td/div[text()='"+pStrIncidentName+"']/parent::td/parent::tr/td/div/div[@class='icon-fixed-width  action-edit icon-edit icon-medium']");
			selenium.click("//div[@id='incidents']/div/div[contains(@id,'gridpanel')]/div/table/tbody/tr/td/div[text()='"+pStrIncidentName+"']/parent::td/parent::tr/td/div/div[@class='icon-fixed-width  action-edit icon-edit icon-medium']");
			
			try {
				waitForElement.WaitForElements(selenium, "EditIncidentTitle", "Edit Incident window Title");
				assertEquals("Edit Incident",selenium.getText(propElementDetails.getProperty("EditIncidentTitle")));
				log4j.info(pStrIncidentName+" Incident Edit option clicked");
				log4j.info("Edit Incident page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrIncidentName+" Incident Edit option Not clicked");
				log4j.info("Edit Incident page is displayed");
				lStrReason = pStrIncidentName+" Incident Edit option Not clicked";
			}
			
			selenium.focus("//div[contains(@id,'incidententitygrid')]/div/div[contains(@id,'gridpanel')]/div[contains(@id,'gridview')]/table/tbody/tr[contains(@id,'gridview')]/td/div[text()='"
					+ pStrProviderName
					+ "']/parent::td/parent::tr/td/div/div/div/i[@class='icon-circle-blank -3']");
			selenium.click("//div[contains(@id,'incidententitygrid')]/div/div[contains(@id,'gridpanel')]/div[contains(@id,'gridview')]/table/tbody/tr[contains(@id,'gridview')]/td/div[text()='"
					+ pStrProviderName
					+ "']/parent::td/parent::tr/td/div/div/div/i[@class='icon-circle-blank -3']");
			log4j.info("'Full in command' option selected For "
					+ pStrProviderName + " Location");
			
			selenium.focus(propElementDetails.getProperty("EditInciSave"));
			selenium.click(propElementDetails.getProperty("EditInciSave"));

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.editCreatedIncidentAndProvideAccess failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//editCreatedIncidentAndProvideAccess//
	
	//start//switchBackFrmLocToRegion//
	/*******************************************************************************************
	' Description  : Function to click on 'Location' link and select 'Region' 
	' Precondition : N/A 
	' Arguments    : selenium, pStrLocationName, pStrRegionName
	' Returns      : String 
	' Date         : 14-11-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String switchBackFrmLocToRegion(Selenium selenium,
			String pStrLocationName, String pStrRegionName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		WaitForElement waitForElements = new WaitForElement();

		try {
			selenium.click(propElementDetails.getProperty("divisionLink"));

			try {
				waitForElements.WaitForElements(selenium, "selLocWindowTitle",
						"Select Location Window");
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("selLocWindowTitle")));
				log4j.info(pStrLocationName+" division link clicked");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrLocationName+" division link Not clicked");
				lStrReason = pStrLocationName+" division link Not clicked";
			}

			waitForElements.WaitForElements(selenium, "selLocWindowRegionLink", pStrRegionName+" Region link");
			selenium.focus(propElementDetails.getProperty("selLocWindowRegionLink"));
			selenium.click(propElementDetails.getProperty("selLocWindowRegionLink"));
			Thread.sleep(30000);
			//selenium.waitForPageToLoad(gstrTimeOut);

			try {
				waitForElements.WaitForElements(selenium, "configureButton", "Configure Button");
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("configureButton")));
				log4j.info(pStrRegionName+" link is clicked");
			} catch (AssertionError assertionFailedError) {
				log4j.info(assertionFailedError);
				log4j.info(pStrRegionName+" link is Not clicked");
				lStrReason = pStrRegionName+" link is Not clicked";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.switchBackFrmLocToRegion failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//switchBackFrmLocToRegion//
	
	//start//vfyTheIncidentStatus//
	/*******************************************************************************************
	' Description  : Function to verify the number of rows present in the incident page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : Number 
	' Date         : 29-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String getTheNumOfIncidentsInIncidentPage(Selenium selenium) throws Exception {
		String lStrReason = "";
		String strIncidentNum="";
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		
		try {
				int intIncidentRows=selenium.getXpathCount("//div[@id='incidents']/div/div[3]/div/table/tbody/tr").intValue();
				log4j.info("The number of incidents present in the incident page is: "+intIncidentRows);
				strIncidentNum=""+intIncidentRows;
				log4j.info("The number of incidents present in the incident page is: "+strIncidentNum);
			} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.vfyTheIncidentStatus failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		
		return strIncidentNum;
	}
	// end//vfyTheIncidentStatus//
}