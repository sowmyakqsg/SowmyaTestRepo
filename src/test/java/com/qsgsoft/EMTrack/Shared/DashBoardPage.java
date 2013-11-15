package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;

import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class DashBoardPage{
	
	Properties ElementDetails;	
	static Logger log4j = Logger.getLogger("DashBoardPage");
	public Properties propElementDetails, propEnvDetails;
	public String gstrTimeOut;
	
	//start//selectNewDashboardOption//
	/*******************************************************************************************
	' Description  : Function to click on 'Configure' Button and select 'New Dashboard' option
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selectNewDashboardOption(Selenium selenium) throws Exception {
		String lStrReason = "";

		WaitForElement waitForElements = new WaitForElement();
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			waitForElements.WaitForElements(selenium,"configureButton","Configure button");
			selenium.click(propElementDetails.getProperty("configureButton"));
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("newDashBoardOption")));
				log4j.info("Configure Button is Clicked");
			} catch (AssertionError ae) {
				log4j.info("Configure Button is NOT Clicked");
				lStrReason = "Configure Button is NOT Clicked";
			}

			selenium.click(propElementDetails.getProperty("newDashBoardOption"));
			Thread.sleep(5000);
			try {
				waitForElements.WaitForElements(selenium, "clientListTitle", "Client List Gadget Title");
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("clientListTitle")));
				log4j.info("New Dashboard option selected");
			} catch (AssertionError Ae) {
				log4j.info("New Dashboard option is NOT selected");
				lStrReason = "DashBoardPage.selectNewDashboardOption failed to complete due to "+ lStrReason + "; " + Ae.toString();
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selectNewDashboardOption failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//selectNewDashboardOption//

	//start//selectAddGadgetOption//
	/*******************************************************************************************
	' Description  : Function to click on 'Configure' Button and select 'Add Gadget' option
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selectAddGadgetOption(Selenium selenium) throws Exception{
		String lStrReason = "";
		
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		WaitForElement waitForElements = new WaitForElement();
		
		try {
			selenium.click(propElementDetails.getProperty("configureButton"));
			try{
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("addGadgetOption")));
				log4j.info("Configure Button is Clicked");
			}catch(AssertionError ae){
				log4j.info("Configure Button is NOT Clicked");
				lStrReason="Configure Button is NOT Clicked";
			}
			
			selenium.click(propElementDetails.getProperty("addGadgetOption"));
			waitForElements.WaitForElements(selenium,"CreateWindow", "Configure button");
			
			try{
				assertEquals(selenium.getText(propElementDetails.getProperty("CreateWindow")),"Gadget Directory");
				log4j.info("Gadget Directory page is displayed");
			}catch(AssertionError ae){
				log4j.info("Gadget Directory page is NOT displayed");
				lStrReason="Gadget Directory page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selectAddGadgetOption failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//selectAddGadgetOption//

	//start//selectActiveIncidentsInRegionOptionInGadgetDirectory//
	/*******************************************************************************************
	' Description  : Function to select 'Active Incidents In Region' option in Gadget Directory.
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 28-10-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selectActiveIncidentsInRegionOptionInGadgetDirectory(Selenium selenium) throws Exception{
		String lStrReason = "";
		
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		try {
			selenium.focus(propElementDetails.getProperty("ActiveInciRegn"));
			selenium.click(propElementDetails.getProperty("ActiveInciRegn"));
						
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selectActiveIncidentsInRegionInGadgetDirectory failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//selectActiveIncidentsInRegionOptionInGadgetDirectory//
	
	//start//navigateToDashboardPage//
    /*******************************************************************************************
  	' Description  : Function to navigate to 'DashBoard' page
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 28-10-2013
  	' Author       : Rahul 
  	*******************************************************************************************/
	public String navigateToDashboardPage(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		WaitForElement waitForElements = new WaitForElement();
		
		try {
			selenium.focus(propElementDetails.getProperty("dashBoardLink"));
			selenium.click(propElementDetails.getProperty("dashBoardLink"));
			Thread.sleep(15000);
			waitForElements.WaitForElements(selenium, "IncidentModeBtn", "Incident Mode Button");
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("IncidentModeBtn")));
				log4j.info("Navigation to 'Dashboard' Page is successfull.");
				
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Navigation to 'Dashboard' Page is NOT successfull.");
				lStrReason = lStrReason + "; " + "Navigation to Dashboard Page is NOT successfull.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.navigateToDashboardPage failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//navigateToDashboardPage//
	
	//start//clkOnIncidentModeBtn//
    /*******************************************************************************************
  	' Description  : Function to click on Incident mode button
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 28-10-2013
  	' Author       : Manasa 
  	*******************************************************************************************/
	public String clkOnIncidentModeBtn(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
	
		try {
			selenium.focus(propElementDetails.getProperty("IncidentModeBtn"));
			selenium.click(propElementDetails.getProperty("IncidentModeBtn"));
			Thread.sleep(1000);
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("SelectIncidentDropDown")));
				log4j.info("Select Incident drop down is displayed");
				
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Select Incident drop down is NOT displayed");
				lStrReason = lStrReason + "; " + "Select Incident drop down is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkOnIncidentModeBtn failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//clkOnIncidentModeBtn//
	
	//start//vfyIncidentNotPresentInInciModeDrpDwn//
    /*******************************************************************************************
  	' Description  : Function to click on Incident mode button
  	' Precondition : N/A 
  	' Arguments    : selenium , strIncidentName
  	' Returns      : String 
  	' Date         : 28-10-2013
  	' Author       : Manasa 
  	*******************************************************************************************/
	public String vfyIncidentNotPresentInInciModeDrpDwn(Selenium selenium,String strIncidentName) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
	
		try {
			selenium.focus(propElementDetails.getProperty("SelectIncidentDropDown"));
			selenium.click(propElementDetails.getProperty("SelectIncidentDropDown"));
			Thread.sleep(1000);
			
			try {
				assertFalse(selenium.isElementPresent("//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']/div/ul/li[text()='"+strIncidentName+"']"));
				log4j.info("Incident is NOT displayed in the drop down");
				
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Incident is displayed in the drop down");
				lStrReason = lStrReason + "; " + "Incident is displayed in the drop down";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyIncidentNotPresentInInciModeDrpDwn failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//vfyIncidentNotPresentInInciModeDrpDwn//
	
	//start//verifyActiveIncidentsInRegionGadgetTitle//
	/*******************************************************************************************
	' Description  : Function to select 'Active Incidents In Region' option in Gadget Directory.
	' Precondition : N/A 
	' Arguments    : selenium 
	' Returns      : String 
	' Date         : 28-10-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String verifyActiveIncidentsInRegionGadgetTitle(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		try {
			try {
				selenium.focus("//span[contains(@id,'activeincidentsregion')]");
				assertEquals(
						"Active Incidents - Anywhere, USA",
						selenium.getText("//span[contains(@id,'activeincidentsregion')]"));
				log4j.info("Active Incidents In Region Gadget is displayed with Title 'Active Incidents - Anywhere, USA'");

			} catch (AssertionFailedError Ae) {
				log4j.info(Ae);
				log4j.info("Active Incidents In Region Gadget is Not displayed with Title 'Active Incidents - Anywhere, USA'");
				lStrReason = lStrReason
						+ "Active Incidents In Region Gadget is Not displayed with Title 'Active Incidents - Anywhere, USA'";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.verifyActiveIncidentsInRegionGadgetTitle failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//verifyActiveIncidentsInRegionGadgetTitle//
		
	//start//verifyCreatedIncidentInActiveIncidentsInRegionGadget//
	/*******************************************************************************************
	' Description  : Function to select 'Active Incidents In Region' option in Gadget Directory.
	' Precondition : N/A 
	' Arguments    : selenium ,varIncidentName ,varIncidentApplication 
	' Returns      : String 
	' Date         : 28-10-2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String verifyCreatedIncidentInActiveIncidentsInRegionGadget(
			Selenium selenium, String varIncidentName,
			String varIncidentApplication) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				selenium.focus("//div[contains(@id,'activeincidentsregion')]/div[contains(@id,'gridpanel')]/div[contains(@id,'gridpanel')]/div/table/tbody/tr/td/div/div/b[contains(text(),'"
						+ varIncidentName
						+ " - "
						+ varIncidentApplication
						+ "')]");
				assertTrue(selenium
						.isElementPresent("//div[contains(@id,'activeincidentsregion')]/div[contains(@id,'gridpanel')]/div[contains(@id,'gridpanel')]/div/table/tbody/tr/td/div/div/b[contains(text(),'"
								+ varIncidentName
								+ " - "
								+ varIncidentApplication + "')]"));
				log4j.info(varIncidentName + " - " + varIncidentApplication
						+ "  is present in 'Active Incidents In Region' Gadget");

			} catch (AssertionFailedError e) {
				log4j.info(e);
				log4j.info(varIncidentName
						+ " - "
						+ varIncidentApplication
						+ "  is Not present in 'Active Incidents In Region' Gadget");
				lStrReason = varIncidentName
						+ " - "
						+ varIncidentApplication
						+ "  is Not present in 'Active Incidents In Region' Gadget";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.verifyCreatedIncidentInActiveIncidentsInRegionGadget failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//verifyCreatedIncidentInActiveIncidentsInRegionGadget//
	
	//start//vfyCreatedInciNotPresInActInciInRgnGdgt//
	/********************************************************************************************
	' Description  : Function to verify the created incident is not displayed in Active incidents in region gadget.
	' Precondition : N/A 
	' Arguments    : selenium ,varIncidentName ,varIncidentApplication 
	' Returns      : String 
	' Date         : 30-10-2013
	' Author       : Manasa 
	********************************************************************************************/
	public String vfyCreatedInciNotPresInActInciInRgnGdgt(Selenium selenium,
			String varIncidentName, String varIncidentApplication)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				assertFalse(selenium
						.isElementPresent("//div[contains(@id,'activeincidentsregion')]/div[contains(@id,'gridpanel')]/div[contains(@id,'gridpanel')]/div/table/tbody/tr/td/div/div/b[contains(text(),'"
								+ varIncidentName
								+ " - "
								+ varIncidentApplication + "')]"));
				log4j.info(varIncidentName
						+ " - "
						+ varIncidentApplication
						+ "  is NOT present in 'Active Incidents In Region' Gadget");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(varIncidentName + " - " + varIncidentApplication
						+ "  is present in 'Active Incidents In Region' Gadget");
				lStrReason = varIncidentName
						+ " - "
						+ varIncidentApplication
						+ "  is Not present in 'Active Incidents In Region' Gadget";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyCreatedInciNotPresInActInciInRgnGdgt failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}

	// end//vfyCreatedInciNotPresInActInciInRgnGdgt//
		
	//start//selFilterInClientListGadget//
	/*******************************************************************************************
	' Description  : Function to select Filter In Client List Gadget
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selFilterInClientListGadget(Selenium selenium,
			String pStrFilterName) throws Exception {
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
			Thread.sleep(5000);
			selenium.click(propElementDetails
					.getProperty("ClientListFilterInput"));
			
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-plain']/li[text()='"
								+ pStrFilterName + "']", pStrFilterName
								+ " Filter");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-plain']/li[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName + " is displayed in Client List Filter dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is Not displayed in Client List Filter dropdown");
				lStrReason = lStrReason + pStrFilterName
						+ " is Not displayed in Client List Filter dropdown";
			}
			selenium.click("//ul[@class='x-list-plain']/li[text()='"
					+ pStrFilterName + "']");
			
			try {
				assertEquals(pStrFilterName,
						selenium.getValue(propElementDetails
								.getProperty("ClientListFilterInput")));
				log4j.info(pStrFilterName
						+ " option selected for Client List Gadget 'Filter' input");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " option Not selected for Client List Gadget 'Filter' input");
				lStrReason = pStrFilterName
						+ " option Not selected for Client List Gadget 'Filter' input";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selFilterInClientListGadget failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//selFilterInClientListGadget//
		
	//start//clkClientListApplyBtn//
	/*******************************************************************************************
	' Description  : Function to click on Client List Gadget Apply Button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkClientListApplyBtn(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails
					.getProperty("ClientListApplyButton"));
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkClientListApplyBtn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//clkClientListApplyBtn//
		
	//start//selectSaveDashboardOption//
	/*******************************************************************************************
	' Description  : Function to Click on Configure button and select Save Dashboard option
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selectSaveDashboardOption(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("configureButton"));
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("saveDashBoardOption")));
				log4j.info("Configure Button is Clicked");
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Configure Button is NOT Clicked");
				lStrReason = "Configure Button is NOT Clicked";
			}

			selenium.click(propElementDetails
					.getProperty("saveDashBoardOption"));
			Thread.sleep(5000);

			try {
				assertEquals("Save Dashboard",
						selenium.getText(propElementDetails
								.getProperty("saveDashboardTitle")));
				log4j.info("Save Dashboard option selected");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Save Dashboard option is NOT selected");
				lStrReason = "Save Dashboard option is NOT selected";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selectSaveDashboardOption failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//selectSaveDashboardOption//
		
	//start//shareDashBoard//
	/*******************************************************************************************
	' Description  : Function to Share Dashboard
	' Precondition : N/A 
	' Arguments    : selenium, pStrName, pStrDescription, pStrRole
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String shareDashBoard(Selenium selenium, String pStrName,
			String pStrDescription, String pStrRole) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(
					propElementDetails.getProperty("SaveDashBoardNameInput"),
					pStrName);
			log4j.info(pStrName
					+ " entered for 'Name' input field in 'Save Dashboard' window");
			selenium.type(propElementDetails
					.getProperty("SaveDashboardDescriptionInput"),
					pStrDescription);
			log4j.info(pStrDescription
					+ " entered for 'Description' input field in 'Save Dashboard' window");
			selenium.click(propElementDetails
					.getProperty("SaveDashboardRoleInput"));
			try {
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/li[text()='"
								+ pStrRole + "']"));
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrRole + " is Not present in 'Role' drop down.");
				lStrReason = lStrReason + pStrRole
						+ " is Not present in 'Role' drop down.";
			}
			selenium.click("//ul[@class='x-list-combo']/li[text()='" + pStrRole
					+ "']");
			log4j.info(pStrRole
					+ " is selected for 'Role' input in 'Save Dashboard' window");

			selenium.click(propElementDetails
					.getProperty("SaveDashboardAddButton"));
			String strtext = selenium
					.getText("//div[@class='x-window x-layer x-window-default x-closable x-window-closable x-window-default-closable x-border-box x-resizable x-window-resizable x-window-default-resizable']/div/div/div/span/div/div/div/div/div/table/tbody/tr/td/div[@class='x-grid-cell-inner ']");

			try {
				assertTrue(strtext.contains(pStrRole));
				log4j.info("Role : " + pStrRole + " is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Role : " + pStrRole + " is Not displayed");
				lStrReason = "Role : " + pStrRole + " is Not displayed";
			}

			selenium.click(propElementDetails
					.getProperty("SaveDashboardSaveButton"));
			log4j.info("Save Dashboard window Save button clicked");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.shareDashBoard failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}

	// end//shareDashBoard//

	//start//verifyCurrentDashBoardInput//
	/*******************************************************************************************
	' Description  : Function to verify Current Dashboard Input
	' Precondition : N/A 
	' Arguments    : selenium, pStrDashboardName
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String verifyCurrentDashBoardInput(Selenium selenium,
			String pStrDashboardName) throws Exception {
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
			selenium.click(propElementDetails
					.getProperty("currentDashboardInput"));
			try {
				waitForElement.WaitForElementPresent_WithoutProperty(selenium, "//ul[@class='x-list-combo']/div[text()='Personal']/following-sibling::li[contains(text(),'"
								+ pStrDashboardName + "')]", pStrDashboardName+" Saved Dashboard Name");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/div[text()='Personal']/following-sibling::li[contains(text(),'"
								+ pStrDashboardName + "')]"));
				log4j.info(pStrDashboardName
						+ " is displayed under 'Personal' section");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrDashboardName
						+ "is Not displayed under 'Personal' section");
				lStrReason = lStrReason + pStrDashboardName
						+ "is Not displayed under 'Personal' section";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.verifyCurrentDashBoardInput failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//verifyCurrentDashBoardInput//
		
	//start//verifySharedDashboard//
	/*******************************************************************************************
	' Description  : Function to verify dashboard name displayed under Shared Section
	' Precondition : N/A 
	' Arguments    : selenium, pStrDashboardName
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String verifySharedDashboard(Selenium selenium,
			String pStrDashboardName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			Thread.sleep(10000);
			selenium.click(propElementDetails
					.getProperty("currentDashboardInput"));
			try {
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/div[text()='Shared']/following-sibling::li[contains(text(),'"
								+ pStrDashboardName + "')]"));
				log4j.info(pStrDashboardName
						+ " is displayed under 'Shared' section");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrDashboardName
						+ "is Not displayed under 'Shared' section");
				lStrReason = lStrReason + pStrDashboardName
						+ "is Not displayed under 'Shared' section";
			}
			selenium.click("//ul[@class='x-list-combo']/div[text()='Shared']/following-sibling::li[contains(text(),'"
								+ pStrDashboardName + "')]");
			log4j.info(pStrDashboardName+" selected");
			
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.verifySharedDashboard failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//verifySharedDashboard//
	
	//start//vfyFilterNameInClientListGadgetTitle//
	/*******************************************************************************************
	' Description  : Function to verify Filter name displayed in Client List Gadget Title
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName
	' Returns      : String 
	' Date         : 31/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String vfyFilterNameInClientListGadgetTitle(Selenium selenium, String pStrFilterName) throws Exception
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
			try {
				Thread.sleep(10000);
				assertTrue(selenium.isElementPresent("//div[contains(@id,'clientlistportlet')]/span[contains(text(),'"+pStrFilterName+"')]"));
				log4j.info(pStrFilterName+ "is displayed as Client List Gadget Title");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName+ "is Not displayed as Client List Gadget Title");
				lStrReason = lStrReason + pStrFilterName+ "is Not displayed as Client List Gadget Title";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyFilterNameInClientListGadgetTitle failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//vfyFilterNameInClientListGadgetTitle//
	
	//start//vfySharedDashboardCannotBeModified//
	/*******************************************************************************************
	' Description  : Function to verify shared Dashboard cannot be modified
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 31/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String vfySharedDashboardCannotBeModified(Selenium selenium) throws Exception
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
			selenium.click(propElementDetails.getProperty("configureButton"));
			try {
				assertFalse(selenium.isVisible(propElementDetails.getProperty("saveDashBoardOption")));
				log4j.info("'Save Dashboard' option is Not present");
	
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Save Dashboard' option is present");
				lStrReason = "'Save Dashboard' option is present";
			}
			try {
				assertFalse(selenium.isVisible(propElementDetails.getProperty("addGadgetOption")));
				log4j.info("'Add Gadget' option is Not present");
				
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Add Gadget' option is present");
				lStrReason = "'Add Gadget' option is present";
			}
			try {
				assertFalse(selenium.isVisible(propElementDetails.getProperty("changeDashBoardLayoutOption")));
				log4j.info("'Change Dashboard Layout' option is Not present");
				
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Change Dashboard Layout' option is present");
				lStrReason = "'Change Dashboard Layout' option is present";
			}
			try {
				assertFalse(selenium.isVisible(propElementDetails.getProperty("deleteDashBoardOption")));
				log4j.info("'Delete Dashboard' option is Not present");
				
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Delete Dashboard' option is present");
				lStrReason = "'Delete Dashboard' option is present";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "DashBoardPage.vfySharedDashboardCannotBeModified failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//vfySharedDashboardCannotBeModified//
	
	//start//selectClientSummaryOptionInGadgetDirectory//
	/************************************************************************************************
	' Description  : Function to select 'Client Summary' option in Gadget Directory.
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 31/10/2013
	' Author       : Rahul 
	*************************************************************************************************/
	public String selectClientSummaryOptionInGadgetDirectory(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			Thread.sleep(2000);
			selenium.focus(propElementDetails
					.getProperty("SummaryType"));
			selenium.click(propElementDetails
					.getProperty("SummaryType"));
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selectClientSummaryOptionInGadgetDirectory failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//selectClientSummaryOptionInGadgetDirectory//
	
	//start//selFilterInClientSummaryGadget//
	/*******************************************************************************************
	' Description  : Function to select Filter in Client Summary Gadget
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName, pStrGroupBy, pstrSummaryType
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selFilterInClientSummaryGadget(Selenium selenium,
			String pStrFilterName, String pStrGroupBy, String pstrSummaryType)
			throws Exception {
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
			Thread.sleep(5000);
			selenium.click(propElementDetails
					.getProperty("ClientSummaryFilterInput"));
			
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-combo']/li[text()='"
								+ pStrFilterName + "']", pStrFilterName
								+ " Filter");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/li[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName + " is displayed in Client Summary Filter dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is Not displayed in Client Summary Filter dropdown");
				lStrReason = lStrReason + pStrFilterName
						+ " is Not displayed in Client Summary Filter dropdown";
			}
			
			selenium.click("//ul[@class='x-list-combo']/li[text()='"
					+ pStrFilterName + "']");
			
			try {
				assertEquals(pStrFilterName,
						selenium.getValue(propElementDetails
								.getProperty("ClientSummaryFilterInput")));
				log4j.info(pStrFilterName
						+ " option selected for Client Summary Gadget 'Filter' input");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " option is Not selected for Client Summary Gadget 'Filter' input");
				lStrReason = pStrFilterName
						+ " option is Not selected for Client Summary Gadget 'Filter' input";
			}

			selenium.click(propElementDetails
					.getProperty("ClientSummaryGroupByInput"));
			
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-combo']/li[text()='" + pStrGroupBy
								+ "']", pStrGroupBy + " Group By option");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/li[text()='"
								+ pStrGroupBy + "']"));
				log4j.info(pStrGroupBy + " is displayed in Group By dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrGroupBy
						+ " is Not displayed in Group By dropdown");
				lStrReason = lStrReason + pStrGroupBy
						+ " is Not displayed in Group By dropdown";
			}
			
			selenium.click("//ul[@class='x-list-combo']/li[text()='"
					+ pStrGroupBy + "']");
			
			try {
				assertEquals(pStrGroupBy, selenium.getValue(propElementDetails
						.getProperty("ClientSummaryGroupByInput")));
				log4j.info(pStrGroupBy
						+ " option selected for Client Summary Gadget 'Group By' input");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrGroupBy
						+ " option Not selected for Client Summary Gadget 'Group By' input");
				lStrReason = pStrGroupBy
						+ " option Not selected for Client Summary Gadget 'Group By' input";
			}

			selenium.click(propElementDetails
					.getProperty("ClientSummaryTypeInput"));
			
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-combo']/li[text()='"
								+ pstrSummaryType + "']", pstrSummaryType
								+ " 'Summary Type' option");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/li[text()='"
								+ pstrSummaryType + "']"));
				log4j.info(pstrSummaryType
						+ " is displayed in 'Summary Type' dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pstrSummaryType
						+ " is Not displayed in 'Summary Type' dropdown");
				lStrReason = lStrReason + pstrSummaryType
						+ " is Not displayed in 'Summary Type' dropdown";
			}
			
			selenium.click("//ul[@class='x-list-combo']/li[text()='"
					+ pstrSummaryType + "']");
			
			try {
				assertEquals(pstrSummaryType,
						selenium.getValue(propElementDetails
								.getProperty("ClientSummaryTypeInput")));
				log4j.info(pstrSummaryType
						+ " option selected for Client Summary Gadget 'Summary Type' input");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pstrSummaryType
						+ " option Not selected for Client Summary Gadget 'Summary Type' input");
				lStrReason = pstrSummaryType
						+ " option Not selected for Client Summary Gadget 'Summary Type' input";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selFilterInClientSummaryGadget failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//selFilterInClientSummaryGadget//
	
	//start//clkClientSummaryApplyBtn//
	/*******************************************************************************************
	' Description  : Function to click on Client Summary Gadget Apply Button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkClientSummaryApplyBtn(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.focus(propElementDetails
					.getProperty("ClientSummaryApplyBtn"));
			selenium.click(propElementDetails
					.getProperty("ClientSummaryApplyBtn"));
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.ClientSummaryApplyBtn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//clkClientSummaryApplyBtn//
	
	//start//vfyClientSummaryGadgetTitle//
	/*******************************************************************************************
	' Description  : Function to verify Client Summary Gadget Title
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName, pStrGroupBy
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String vfyClientSummaryGadgetTitle(Selenium selenium,
			String pStrFilterName, String pStrGroupBy) throws Exception {
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
				Thread.sleep(10000);
				assertEquals(
						"Summary Of " + pStrFilterName + " - " + pStrGroupBy
								+ "",
						selenium.getText("//span[contains(@id,'clientsummaryportlet')]"));
				log4j.info("Summary Of " + pStrFilterName + " - " + pStrGroupBy
						+ " is displayed as 'Client Summary' Gadget Title");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Summary Of " + pStrFilterName + " - " + pStrGroupBy
						+ " is Not displayed as 'Client Summary' Gadget Title");
				lStrReason = lStrReason + "Summary Of " + pStrFilterName
						+ " - " + pStrGroupBy
						+ " is Not displayed as 'Client Summary' Gadget Title";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyClientSummaryGadgetTitle failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//vfyClientSummaryGadgetTitle//
	
	//start//selectActiveInciForProviderOptionInGadgetDirectory//
	/************************************************************************************************
	' Description  : Function to select 'Active Incidents For Provider' option in Gadget Directory.
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*************************************************************************************************/
	public String selectActiveInciForProviderOptionInGadgetDirectory(
			Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.focus(propElementDetails
					.getProperty("ActiveInciForProvider"));
			selenium.click(propElementDetails
					.getProperty("ActiveInciForProvider"));
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selectActiveInciForProviderOptionInGadgetDirectory failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//selectActiveInciForProviderOptionInGadgetDirectory//
	
	//start//selLocationFrActiveInciFrProviderGadget//
	/*********************************************************************************************
	' Description  : Function to select value for Location Input in Active Incidents For Provider
	' Precondition : N/A 
	' Arguments    : selenium, pStrLocation
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	**********************************************************************************************/
	public String selLocationFrActiveInciFrProviderGadget(Selenium selenium,
			String pStrLocation) throws Exception {
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
			Thread.sleep(5000);
			selenium.click(propElementDetails
					.getProperty("ActInciFrProLocationInput"));
			
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-combo']/li[text()='"
								+ pStrLocation + "']", pStrLocation
								+ " Location value");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/li[text()='"
								+ pStrLocation + "']"));
				log4j.info(pStrLocation + " is displayed in Location dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrLocation
						+ " is Not displayed in Location dropdown");
				lStrReason = lStrReason + pStrLocation
						+ " is Not displayed in Location dropdown";
			}
			
			selenium.click("//ul[@class='x-list-combo']/li[text()='"
					+ pStrLocation + "']");
			
			try {
				assertEquals(pStrLocation, selenium.getValue(propElementDetails
						.getProperty("ActInciFrProLocationInput")));
				log4j.info(pStrLocation
						+ " option selected for Active Incidents For Provider 'Location' input");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrLocation
						+ " option Not selected for Active Incidents For Provider 'Location' input");
				lStrReason = pStrLocation
						+ " option Not selected for Active Incidents For Provider 'Location' input";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selLocationFrActiveInciFrProviderGadget failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//selLocationFrActiveInciFrProviderGadget//
	
	//start//clkActiveInciFrProviderApplyBtn//
	/*******************************************************************************************
	' Description  : Function to click on Active Incidents For Provider Gadget Apply Button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkActiveInciFrProviderApplyBtn(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.focus(propElementDetails
					.getProperty("ActInciFrProApplyBtn"));
			selenium.click(propElementDetails
					.getProperty("ActInciFrProApplyBtn"));
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkActiveInciFrProviderApplyBtn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkActiveInciFrProviderApplyBtn//
	
	//start//vfyActiveInciFrProviderGadgetTitle//
	/*******************************************************************************************
	' Description  : Function to verify Active Incidents For Provider Gadget Title
	' Precondition : N/A 
	' Arguments    : selenium, pStrLocation
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String vfyActiveInciFrProviderGadgetTitle(Selenium selenium,
			String pStrLocation) throws Exception {
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
				Thread.sleep(10000);
				assertEquals(
						"Active Incidents - " + pStrLocation + "",
						selenium.getText("//span[contains(@id,'activeincidentsprovider')]"));
				log4j.info("Active Incidents - "
						+ pStrLocation
						+ " is displayed as 'Active Incidents For Provider' Gadget Title");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Active Incidents - "
						+ pStrLocation
						+ " is Not displayed as 'Active Incidents For Provider' Gadget Title");
				lStrReason = lStrReason
						+ "Active Incidents - "
						+ pStrLocation
						+ " is Not displayed as 'Active Incidents For Provider' Gadget Title";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyActiveInciFrProviderGadgetTitle failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyActiveInciFrProviderGadgetTitle//
		
	//start//saveDashBoard//
	/*******************************************************************************************
	' Description  : Function to Save Dashboard
	' Precondition : N/A 
	' Arguments    : selenium, pStrName, pStrDescription
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String saveDashBoard(Selenium selenium, String pStrName,
			String pStrDescription) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(
					propElementDetails.getProperty("SaveDashBoardNameInput"),
					pStrName);
			log4j.info(pStrName
					+ " entered for 'Name' input field in 'Save Dashboard' window");
			selenium.type(propElementDetails
					.getProperty("SaveDashboardDescriptionInput"),
					pStrDescription);
			log4j.info(pStrDescription
					+ " entered for 'Description' input field in 'Save Dashboard' window");

			selenium.click(propElementDetails
					.getProperty("SaveDashboardSaveButton"));
			log4j.info("Save Dashboard window Save button clicked");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.saveDashBoard failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//saveDashBoard//
	
	//start//vfyCreatedFilterInClientListFilterDropDown//
	/******************************************************************************************************
	' Description : Function to verify Created Filter is displayed in 'Client List Gadget' Filter drop down
	' Precondition: N/A 
	' Arguments   : selenium ,pStrFilterName
	' Returns     : String 
	' Date        : 05/11/2013
	' Author      : Rahul 
	*******************************************************************************************************/
	public String vfyCreatedFilterInClientListFilterDropDown(
			Selenium selenium, String pStrFilterName) throws Exception {
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
			Thread.sleep(5000);
			selenium.click(propElementDetails
					.getProperty("ClientListFilterInput"));
			
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-plain']/li[text()='"
								+ pStrFilterName + "']", pStrFilterName
								+ " Filter");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-plain']/li[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName + " is displayed in Client List Filter dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is Not displayed in Client List Filter dropdown");
				lStrReason = lStrReason + pStrFilterName
						+ " is Not displayed in Client List Filter dropdown";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyCreatedFilterInClientListFilterDropDown failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//vfyCreatedFilterInClientListFilterDropDown//
	
	//start//vfyCreatedFilterInClientSummaryFilterDropDown//
	/*********************************************************************************************************
	' Description : Function to verify Created Filter is displayed in 'Client Summary Gadget' Filter drop down
	' Precondition: N/A 
	' Arguments   : selenium ,pStrFilterName
	' Returns     : String 
	' Date        : 05/11/2013
	' Author      : Rahul 
	**********************************************************************************************************/
	public String vfyCreatedFilterInClientSummaryFilterDropDown(
			Selenium selenium, String pStrFilterName) throws Exception {
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
			Thread.sleep(5000);
			selenium.click(propElementDetails
					.getProperty("ClientSummaryFilterInput"));

			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-combo']/li[text()='"
								+ pStrFilterName + "']", pStrFilterName
								+ " Filter");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-combo']/li[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName
						+ " is displayed in Client Summary Filter dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is Not displayed in Client Summary Filter dropdown");
				lStrReason = lStrReason + pStrFilterName
						+ " is Not displayed in Client Summary Filter dropdown";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyCreatedFilterInClientSummaryFilterDropDown failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//vfyCreatedFilterInClientListFilterDropDown//
	
	//start//clkClientListConfigureOption//
	/*******************************************************************************************
	' Description  : Function to click on Client List Gadget Configure option
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkClientListConfigureOption(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			Thread.sleep(5000);
			selenium.focus(propElementDetails
					.getProperty("ClientListConfigureOption"));
			selenium.click(propElementDetails
					.getProperty("ClientListConfigureOption"));
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("ClientListFilterInput")));
				log4j.info("Client List Gadget 'Configure' option clicked");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Client List Gadget 'Configure' option Not clicked");
				lStrReason = "Client List Gadget 'Configure' option Not clicked";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkClientListConfigureOption failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkClientListConfigureOption//
	
	//start//clkClientSummaryConfigureOption//
	/*******************************************************************************************
	' Description  : Function to click on Client Summary Gadget Configure option
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkClientSummaryConfigureOption(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			Thread.sleep(5000);
			selenium.focus(propElementDetails
					.getProperty("ClientSummaryConfigureOption"));
			selenium.click(propElementDetails
					.getProperty("ClientSummaryConfigureOption"));
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("ClientSummaryFilterInput")));
				log4j.info("Client Summary Gadget 'Configure' option clicked");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Client Summary Gadget 'Configure' option Not clicked");
				lStrReason = "Client Summary Gadget 'Configure' option Not clicked";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkClientSummaryConfigureOption failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkClientSummaryConfigureOption//
	
	//start//clkActiveInciFrProviderConfigureOption//
	/*******************************************************************************************
	' Description  : Function to click on Active Incidents For Provider Gadget Configure option
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkActiveInciFrProviderConfigureOption(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			Thread.sleep(5000);
			selenium.focus(propElementDetails
					.getProperty("ActiveInciFrProviderConfigureOption"));
			selenium.click(propElementDetails
					.getProperty("ActiveInciFrProviderConfigureOption"));
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("ActInciFrProLocationInput")));
				log4j.info("Active Incidents For Provider Gadget 'Configure' option clicked");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Active Incidents For Provider Gadget 'Configure' option Not clicked");
				lStrReason = "Active Incidents For Provider 'Configure' option Not clicked";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkActiveInciFrProviderConfigureOption failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkActiveInciFrProviderConfigureOption//
	
	//start//vfyDeletedFilterNotPresentInClientListFilterDropDown//
	/**********************************************************************************************************
	' Description : Function to verify Deleted Filter is Not displayed in 'Client List Gadget' Filter drop down
	' Precondition: N/A 
	' Arguments   : selenium ,pStrFilterName
	' Returns     : String 
	' Date        : 05/11/2013
	' Author      : Rahul 
	***********************************************************************************************************/
	public String vfyDeletedFilterNotPresentInClientListFilterDropDown(
			Selenium selenium, String pStrFilterName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			Thread.sleep(5000);
			selenium.click(propElementDetails
					.getProperty("ClientListFilterInput"));

			try {
				assertFalse(selenium
						.isElementPresent("//ul[@class='x-list-plain']/li[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName
						+ " is Not displayed in Client List Filter dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is displayed in Client List Filter dropdown");
				lStrReason = lStrReason + pStrFilterName
						+ " is displayed in Client List Filter dropdown";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyDeletedFilterNotPresentInClientListFilterDropDown failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//vfyDeletedFilterNotPresentInClientListFilterDropDown//
	
	//start//vfyDeletedFilterNotPresentInClientSummaryFilterDropDown//
	/*************************************************************************************************************
	' Description : Function to verify Deleted Filter is Not displayed in 'Client Summary Gadget' Filter drop down
	' Precondition: N/A 
	' Arguments   : selenium ,pStrFilterName
	' Returns     : String 
	' Date        : 05/11/2013
	' Author      : Rahul 
	**************************************************************************************************************/
	public String vfyDeletedFilterNotPresentInClientSummaryFilterDropDown(
			Selenium selenium, String pStrFilterName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			Thread.sleep(5000);
			selenium.click(propElementDetails
					.getProperty("ClientSummaryFilterInput"));

			try {
				assertFalse(selenium
						.isElementPresent("//ul[@class='x-list-combo']/li[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName
						+ " is Not displayed in Client Summary Filter dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is displayed in Client Summary Filter dropdown");
				lStrReason = lStrReason + pStrFilterName
						+ " is displayed in Client Summary Filter dropdown";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.vfyDeletedFilterNotPresentInClientSummaryFilterDropDown failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//vfyDeletedFilterNotPresentInClientSummaryFilterDropDown//
	
	//start//deleteDashboard//
	/*************************************************************************************************************
	' Description  : Function to Click on Configure button and select Delete Dashboard option and Delete Dashboard
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	**************************************************************************************************************/
	public String deleteDashboard(Selenium selenium,
			String pStrDashboardName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("configureButton"));
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("deleteDashBoardOption")));
				log4j.info("Configure Button is Clicked");
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Configure Button is NOT Clicked");
				lStrReason = "Configure Button is NOT Clicked";
			}

			selenium.click(propElementDetails
					.getProperty("deleteDashBoardOption"));
			Thread.sleep(5000);

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("alertWindow")));
				log4j.info("'Delete Dashboard' Pop Up is Displayed.");
				log4j.info("Delete Dashboard option selected");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Delete Dashboard option is NOT selected");
				lStrReason = "Delete Dashboard option is NOT selected";
			}

			selenium.click(propElementDetails.getProperty("alertWindowYesBtn"));

			try {
				assertFalse(selenium
						.isElementPresent("//ul[@class='x-list-combo']/div[text()='Personal']/following-sibling::li[contains(text(),'"
								+ pStrDashboardName + "')]"));
				log4j.info(pStrDashboardName
						+ " is successfully Deleted And Not displayed under 'Personal' section");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrDashboardName
						+ "is Not successfully Deleted And displayed under 'Personal' section");
				lStrReason = lStrReason
						+ pStrDashboardName
						+ "is Mot successfully Deleted And displayed under 'Personal' section";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.deleteDashboard failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//deleteDashboard//
	
	//start//cloneDashboard//
	/*******************************************************************************************
	' Description  : Function to Click on Configure button and select Clone Dashboard option
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 06/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String cloneDashboard(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("configureButton"));

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("cloneDashBoardOption")));
				log4j.info("Configure Button is Clicked");
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Configure Button is NOT Clicked");
				lStrReason = "Configure Button is NOT Clicked";
			}

			selenium.click(propElementDetails
					.getProperty("cloneDashBoardOption"));
			Thread.sleep(3000);

			try {
				assertEquals("New Dashboard",
						selenium.getValue(propElementDetails
								.getProperty("currentDashboardInput")));
				log4j.info("'New Dashboard' is Displayed as 'Current Dashboard' input value");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'New Dashboard' is Not Displayed as 'Current Dashboard' input value");
				lStrReason = "'New Dashboard' is Not Displayed as 'Current Dashboard' input value";
			}

			try {
				assertEquals("Client List", selenium.getText(propElementDetails
						.getProperty("clientListTitle")));
				log4j.info("Clone Dashboard option selected");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Clone Dashboard option is NOT selected");
				lStrReason = "Clone Dashboard option is NOT selected";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.cloneDashboard failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//cloneDashboard//
	
	//start//vfyUnSavedGadgetsAreNotCopied//
    /***************************************************************************
  	' Description  : Function to verify that UnSaved Gadgets cannot be Copied
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 07-11-2013
  	' Author       : Rahul 
  	****************************************************************************/
	public String vfyUnSavedGadgetsAreNotCopied(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				assertFalse(selenium.isVisible("//input[@name='division']"));
				log4j.info("Active Incidents For Provider Gadget is Not displayed");

			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Active Incidents For Provider Gadget is displayed");
				lStrReason = "Active Incidents For Provider Gadget is displayed";
			}

			/*try {
				assertFalse(selenium.isVisible("//div[contains(@id,'activeincidentsregion')]/span[contains(text(),'Active Incidents - Anywhere, USA')]"));
				log4j.info("Active Incidents In Region Gadget is Not displayed");

			} catch (AssertionFailedError Ae) {
				log4j.info(Ae);
				log4j.info("Active Incidents In Region Gadget is displayed");
				lStrReason = "Active Incidents In Region Gadget is displayed";
			}*/
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.vfyUnSavedGadgetsAreNotCopied failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyUnSavedGadgetsAreNotCopied//
	
	//start//vfyCreatedIncidentPresentInInciModeDrpDwn//
    /******************************************************************************************************************
  	' Description  : Function to click Select Incident Input and Verify created incident is Present in the Drop Down
  	' Precondition : N/A 
  	' Arguments    : selenium ,pStrIncidentName
  	' Returns      : String 
  	' Date         : 07-11-2013
  	' Author       : Rahul 
  	*******************************************************************************************************************/
	public String vfyCreatedIncidentPresentInInciModeDrpDwn(Selenium selenium,String pStrIncidentName) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
	
		try {
			selenium.focus(propElementDetails.getProperty("SelectIncidentDropDown"));
			selenium.click(propElementDetails.getProperty("SelectIncidentDropDown"));
			Thread.sleep(1000);
			
			try {
				assertTrue(selenium.isElementPresent("//ul[@class='x-list-plain']/li[text()='"+pStrIncidentName+"']"));
				log4j.info("Created Incident is displayed in the 'Select Incident' drop down");
				
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Created Incident is Not displayed in the 'Select Incident' drop down");
				lStrReason ="Created Incident is Not displayed in the 'Select Incident' drop down";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.vfyCreatedIncidentPresentInInciModeDrpDwn failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//vfyCreatedIncidentPresentInInciModeDrpDwn//
	
	//start//selCreatedIncidentInInciModeDrpDwn//
    /******************************************************************************************************************
  	' Description  : Function to click Select Incident Input and select created incident in the Drop Down
  	' Precondition : N/A 
  	' Arguments    : selenium ,pStrIncidentName
  	' Returns      : String 
  	' Date         : 08-11-2013
  	' Author       : Rahul 
  	*******************************************************************************************************************/
	public String selCreatedIncidentInInciModeDrpDwn(Selenium selenium,
			String pStrIncidentName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.focus(propElementDetails
					.getProperty("SelectIncidentDropDown"));
			selenium.click(propElementDetails
					.getProperty("SelectIncidentDropDown"));
			Thread.sleep(1000);

			try {
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-plain']/li[text()='"
								+ pStrIncidentName + "']"));
				log4j.info(pStrIncidentName
						+ " Incident is displayed in the 'Select Incident' drop down");

			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info(pStrIncidentName
						+ " Created Incident is Not displayed in the 'Select Incident' drop down");
				lStrReason = pStrIncidentName
						+ " Created Incident is Not displayed in the 'Select Incident' drop down";
			}

			selenium.focus("//ul[@class='x-list-plain']/li[text()='"
					+ pStrIncidentName + "']");
			selenium.click("//ul[@class='x-list-plain']/li[text()='"
					+ pStrIncidentName + "']");

			try {
				assertEquals(pStrIncidentName,
						selenium.getValue(propElementDetails
								.getProperty("SelectIncidentDropDown")));
				log4j.info(pStrIncidentName
						+ " Incident is selected in the 'Select Incident' drop down");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrIncidentName
						+ " Incident is Not Selected in the 'Select Incident' drop down");
				lStrReason = pStrIncidentName
						+ " Incident is Not Selected in the 'Select Incident' drop down";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.selCreatedIncidentInInciModeDrpDwn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//selCreatedIncidentInInciModeDrpDwn//
	
	//start//clkClientListMagnifierIcon//
	/*******************************************************************************************
	' Description  : Function to click on Client List Gadget Magnifier Icon
	' Precondition : N/A 
	' Arguments    : selenium ,pStrPatientID
	' Returns      : String 
	' Date         : 08/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkClientListMagnifierIcon(Selenium selenium,
			String pStrPatientID) throws Exception {
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
			selenium.focus(propElementDetails
					.getProperty("ClientListMagnifierIcon"));
			selenium.click(propElementDetails
					.getProperty("ClientListMagnifierIcon"));

			try {
				waitForElement
						.WaitForElementsWithoutProperty(
								selenium,
								"//span[contains(text(),'Detailed Client Information:')]",
								"Detailed Client Information Window");
				assertTrue(selenium
						.isElementPresent("//span[contains(text(),'Detailed Client Information: "
								+ pStrPatientID + "')]"));
				log4j.info("Client List Gadget 'Magnifier' icon clicked");
				log4j.info("Detailed Client Information: " + pStrPatientID
						+ " is displayed as 'Client Infomation' window title");

				String strtext = selenium
						.getText("//div[contains(@id,'clientactions')]/div[contains(@id,'component')]/p");

				log4j.info(strtext);

				try {
					assertTrue(strtext.contains(pStrPatientID));
					log4j.info(pStrPatientID
							+ " is displayed as Tracking Number:");
				} catch (AssertionError e) {
					log4j.info(e);
					log4j.info(pStrPatientID
							+ " is Not displayed as Tracking Number:");
					lStrReason = pStrPatientID
							+ " is Not displayed as Tracking Number:";
				}

				selenium.click("//span[text()='Done']");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Client List Gadget 'Magnifier' icon Not clicked");
				log4j.info("Detailed Client Information:"
						+ pStrPatientID
						+ " is Not displayed as 'Client Infomation' window title");
				lStrReason = "Client List Gadget 'Magnifier' icon Not clicked";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkClientListMagnifierIcon failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkClientListMagnifierIcon//
	
	//start//configClientListGadget//
	/*******************************************************************************************
	' Description  : Function to Configure Client List Gadget
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selFilteFrmClientListGadget(Selenium selenium,
			String pStrFilterName) throws Exception {
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
			selenium.click(propElementDetails
					.getProperty("ClientListFilterInput"));
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//ul[@class='x-list-plain']/li[text()='"
								+ pStrFilterName + "']", pStrFilterName
								+ " Filter");
				assertTrue(selenium
						.isElementPresent("//ul[@class='x-list-plain']/li[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName + " is displayed in Filter dropdown");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is Not displayed in Filter dropdown");
				lStrReason = lStrReason + pStrFilterName
						+ " is Not displayed in Filter dropdown";
			}
			selenium.click("//ul[@class='x-list-plain']/li[text()='"
					+ pStrFilterName + "']");
			log4j.info(pStrFilterName
					+ " option selected for Client List Gadget 'Filter' input");

			try {
				assertEquals(selenium.getValue(propElementDetails
						.getProperty("ClientListFilterInput")), pStrFilterName);
				log4j.info("Appropriate filter " + pStrFilterName
						+ " is selected from the dropdown");
			} catch (AssertionError ae) {
				log4j.info("Appropriate filter " + pStrFilterName
						+ " is NOT selected from the dropdown");
				lStrReason = "Appropriate filter " + pStrFilterName
						+ " is NOT selected from the dropdown";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.configClientListGadget failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//configClientListGadget//
		
	//start//clkClientListApplyBtn//
	/*******************************************************************************************
	' Description  : Function to click on Client List Gadget Apply Button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkClientListApplyBtn(Selenium selenium, String strFilterName)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails
					.getProperty("ClientListApplyButton"));
			Thread.sleep(10000);
			try {
				String strTitle = selenium
						.getText("//div[contains(@id,'clientlistportlet')]/span");
				assertTrue(strTitle.contains(strFilterName));
				log4j.info("Filter name is displayed in the title of client list gadget");

			} catch (AssertionError ae) {
				log4j.info("Filter name is NOT displayed in the title of client list gadget");
				lStrReason = "Filter name is NOT displayed in the title of client list gadget";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkClientListApplyBtn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//clkClientListApplyBtn//
		
	//start//selectActiveIncidentsInRegionOptionInGadgetDirectory//
	/*******************************************************************************************
	' Description  : Function to select 'Active Incidents In Region' option in Gadget Directory.
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 28-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String selFilterFrmClientSumryGdgt(Selenium selenium,
			String strFilter, String strSumryType) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.focus(propElementDetails
					.getProperty("ClintSumryFilterInputBox"));
			selenium.click(propElementDetails
					.getProperty("ClintSumryFilterInputBox"));
			Thread.sleep(500);
			try {
				selenium.focus("//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']/div/ul/li[text()='"
						+ strFilter + "']");
				selenium.click("//div[@class='x-boundlist x-boundlist-floating x-layer x-boundlist-default x-border-box']/div/ul/li[text()='"
						+ strFilter + "']");
				try {
					assertEquals(strFilter,
							selenium.getValue(propElementDetails
									.getProperty("ClintSumryFilterInputBox")));
					log4j.info("Filter "
							+ strFilter
							+ " is selected from the client summary gadget list");
				} catch (AssertionError Ae) {
					log4j.info("Filter "
							+ strFilter
							+ " is NOT selected from the client summary gadget list");
					lStrReason = "Filter "
							+ strFilter
							+ " is NOT selected from the client summary gadget list";
				}

				selenium.focus(propElementDetails
						.getProperty("ClintSumrySumType"));
				selenium.click(propElementDetails
						.getProperty("ClintSumrySumType"));

				selenium.focus("//div[@class='x-boundlist-list-ct x-unselectable']/ul/li[text()='"
						+ strSumryType + "']");
				selenium.click("//div[@class='x-boundlist-list-ct x-unselectable']/ul/li[text()='"
						+ strSumryType + "']");

				try {
					assertEquals(strSumryType,
							selenium.getValue(propElementDetails
									.getProperty("ClintSumrySumType")));
					log4j.info("Summary Type "
							+ strSumryType
							+ " is selected from the client summary gadget list");
				} catch (AssertionError ae) {
					log4j.info("Summary Type "
							+ strSumryType
							+ " is NOT selected from the client summary gadget list");
					lStrReason = "Summary Type "
							+ strSumryType
							+ " is NOT selected from the client summary gadget list";
				}

			} catch (AssertionError ae) {
				log4j.info("Client summary gadget is NOT added");
				lStrReason = "Client summary gadget is NOT added";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.selectActiveIncidentsInRegionInGadgetDirectory failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	
	//start//clkClientSumryApplyBtn//
	/*******************************************************************************************
	' Description  : Function to click on Client List Gadget Apply Button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 31/10/2013
	' Author       : Manasa
	*******************************************************************************************/
	public String clkClientSumryApplyBtn(Selenium selenium, String strFilterName)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strTitle = selenium.getText(propElementDetails
				.getProperty("ClintSumryTitle"));

		try {
			selenium.click(propElementDetails
					.getProperty("ClientSummaryApplyBtn"));
			Thread.sleep(3000);
			try {
				assertTrue(strTitle.contains(strFilterName));
				log4j.info("Filter name is displayed in the title of client list gadget");

			} catch (AssertionError ae) {
				log4j.info("Filter name is NOT displayed in the title of client list gadget");
				lStrReason = "Filter name is NOT displayed in the title of client list gadget";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkClientListApplyBtn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//clkClientSumryApplyBtn//
	
	//start//vfyDefaultColumnsOnClintListGadgets//
    /******************************************************************************************************************
  	' Description  : Function to verify that the age, gender and complaints columns are displayed by default
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 07-11-2013
  	' Author       : Rahul 
  	*******************************************************************************************************************/
	public String vfyDefaultColumnsOnClintListGadgets(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		String strClntListHeader=selenium.getText(propElementDetails.getProperty("ClientListHeader"));
	
		try {
			try{
			assertTrue(strClntListHeader.contains("Age"));
			log4j.info("Age column is displayed by default");
			assertTrue(strClntListHeader.contains("Gender"));
			log4j.info("Gender column is displayed by default");
			assertTrue(strClntListHeader.contains("Complaint"));
			log4j.info("Complaint column is displayed by default");
			}catch(AssertionError Ae){
				log4j.info("Default columns are NOT displayed by in the client list page");
				lStrReason="Default columns are NOT displayed by in the client list page";
			}
			
		} catch (Exception e) {
			log4j.info("Default columns are NOT displayed on gadgets page");
			lStrReason="Default columns are NOT displayed on gadgets page";
			lStrReason = "IncidentsPage.vfyCreatedIncidentPresentInInciModeDrpDwn failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//vfyDefaultColumnsOnClintListGadgets//
	
	//start//vfyNewelyAddedColumnOnClintList//
    /******************************************************************************************************************
  	' Description  : Function to verify that the newely added column on the Client list gadget
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 07-11-2013
  	' Author       : Rahul 
  	*******************************************************************************************************************/
	public String vfyNewelyAddedColumnOnClintList(Selenium selenium,String strColumn) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		String strClntListHeader=selenium.getText(propElementDetails.getProperty("ClientListHeader"));
		
		try {
			assertTrue(strClntListHeader.contains(strColumn));
			log4j.info("The newly added column "+strColumn+" is displayed on the client list page ");
						
		} catch (Exception e) {
			log4j.info("The newly added column "+strColumn+" is NOT displayed on the client list page ");
			lStrReason="The newly added column "+strColumn+" is NOT displayed on the client list page ";
		}
		return lStrReason;
	}
	//end//vfyNewelyAddedColumnOnClintList//
	//start//clkClientSummaryApplyBtn//
	/*******************************************************************************************
	' Description  : Function to verify the newly added column in the client summery gadget
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 14/11/2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String vfyNewlyAddedColumnInClintSumryGadgt(Selenium selenium,
			String strColumn) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strClntSumryHeader=selenium.getText(propElementDetails.getProperty("ClientSumryHeader"));
		
		try {
			assertTrue(strClntSumryHeader.contains(strColumn));
			log4j.info("The newly added column " + strColumn
					+ " is displayed on the client summary page ");

		} catch (Exception e) {
			log4j.info("The newly added column " + strColumn
					+ " is NOT displayed on the client summary page ");
			lStrReason = "The newly added column " + strColumn
					+ " is NOT displayed on the client summary page ";

		}

		return lStrReason;
	}
	// end//clkClientSummaryApplyBtn//
	
	//start//vfyDefaultColumnsOnClintSumryGadgets//
    /******************************************************************************************************************
  	' Description  : Function to verify that the age, gender and complaints columns are displayed by default
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 14-11-2013
  	' Author       : Manasa 
  	*******************************************************************************************************************/
	public String vfyDefaultColumnsOnClintSumryGadgets(Selenium selenium,boolean blnclk) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
			
		try {
			if(blnclk){
			selenium.focus("css=div.x-grid-cell-inner.");	
			selenium.clickAt("css=div.x-grid-cell-inner.","0,0");
			Thread.sleep(5000);
			}
			String strClntSumryHeader=selenium.getText(propElementDetails.getProperty("ClientSumryHeader"));
			assertTrue(strClntSumryHeader.contains("Age"));
			log4j.info("Age column is displayed by default");
			assertTrue(strClntSumryHeader.contains("Gender"));
			log4j.info("Gender column is displayed by default");
			assertTrue(strClntSumryHeader.contains("Complaint"));
			log4j.info("Complaint column is displayed by default");
			
		} catch (Exception e) {
			log4j.info("Default columns are NOT displayed on gadgets page");
			lStrReason="Default columns are NOT displayed on gadgets page";
			lStrReason = "IncidentsPage.vfyCreatedIncidentPresentInInciModeDrpDwn failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//vfyDefaultColumnsOnClintSumryGadgets//
	
	//start//vfyTheDeletedColumnInClintLstGadget//
    /******************************************************************************************************************
  	' Description  : Function to verify that the deleted column on the Client list gadget
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 14-11-2013
  	' Author       : Manasa 
  	*******************************************************************************************************************/
	public String vfyTheDeletedColumnInClintLstGadget(Selenium selenium,String strColumn) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		String strClntListHeader=selenium.getText(propElementDetails.getProperty("ClientListHeader"));
		
		try {
			assertFalse(strClntListHeader.contains(strColumn));
			log4j.info("The newly deleted column "+strColumn+" is NOT displayed on the client list page ");
						
		} catch (Exception e) {
			log4j.info("The newly added column "+strColumn+" is displayed on the client list page ");
			lStrReason="The newly added column "+strColumn+" is displayed on the client list page ";
		}
		return lStrReason;
	}
	//end//vfyTheDeletedColumnInClintLstGadget//
	
	//start//vfyTheDeletedColumnInClintSumryGadget//
    /******************************************************************************************************************
  	' Description  : Function to verify that the deleted column on the Client summary gadget
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 14-11-2013
  	' Author       : Manasa 
  	*******************************************************************************************************************/
	public String vfyTheDeletedColumnInClintSumryGadget(Selenium selenium,String strColumn) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		String strClntSumryHeader=selenium.getText(propElementDetails.getProperty("ClientSumryHeader"));
		try {
			assertFalse(strClntSumryHeader.contains(strColumn));
			log4j.info("The newly deleted column "+strColumn+" is NOT displayed on the client summary page ");
						
		} catch (Exception e) {
			log4j.info("The newly deleted column "+strColumn+" is  displayed on the client list page ");
			lStrReason="The newly deleted column "+strColumn+" is  displayed on the client list page ";
		}
		return lStrReason;
	}
	//end//vfyTheDeletedColumnInClintSumryGadget//
	//start//getTheNumOfIncidentsInDashbrdPage//
	/*****************************************************************************************************************
	' Description  : Function to verify the number of rows present in the incident list dropdown in the dashboard page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 15-11-2013
	' Author       : Manasa 
	******************************************************************************************************************/
	public String getTheNumOfIncidentsInDashbrdPage(Selenium selenium)
			throws Exception {
		String lStrReason = "";
		String strIncidentNum = "";
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();

		try {
			selenium.focus(propElementDetails.getProperty("SelectIncidentDropDown"));
			selenium.click(propElementDetails.getProperty("SelectIncidentDropDown"));
			Thread.sleep(1000);
			
			int intIncidentRows = selenium.getXpathCount(propElementDetails.getProperty("DashBrdIncidentCnt")).intValue();
			int intIncidentCount=intIncidentRows-1;
			log4j.info("The number of incidents present in the incident page is: "+ intIncidentCount);
			
			strIncidentNum = "" + intIncidentCount;
			log4j.info("The number of incidents present in the incident page is: "
					+ intIncidentCount);
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "IncidentsPage.getTheNumOfIncidentsInDashbrdPage failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return strIncidentNum;
	}
	// end//getTheNumOfIncidentsInDashbrdPage//
	
	//start//getTheNumOfIncidentsInDashbrdPage//
	/**************************************************************************************************************************
	' Description  : Function to verify that the same number of incidents are present in both incident page and dash board page
	' Precondition : N/A 
	' Arguments    : selenium,strInciCnt,strDashbrdCnt
	' Returns      : String 
	' Date         : 15-11-2013
	' Author       : Manasa 
	***************************************************************************************************************************/
	public String vfyTheDashBrdAndIncidentPgeInciCnt(Selenium selenium,
			String strInciCnt, String strDashbrdCnt) throws Exception {
		String lStrReason = "";
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();

		try {
			try {
				if (strInciCnt == strDashbrdCnt)
					;
				log4j.info("Number of Incidents in Incident page and Dashboard page are equal");
			} catch (AssertionError Ae) {
				log4j.info("Number of Incidents in Incident page and Dashboard page are NOT equal");
			}
		} catch (Exception e) {
			log4j.info("Number of Incidents in Incident page and Dashboard page are not equal");
			lStrReason = "IncidentsPage.vfyTheDashBrdAndIncidentPgeInciCnt failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//getTheNumOfIncidentsInDashbrdPage//

}
