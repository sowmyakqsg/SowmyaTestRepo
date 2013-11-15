package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class FiltersPage{
	
	Properties ElementDetails;	
	static Logger log4j = Logger.getLogger("FiltersPage");
	public Properties propElementDetails, propEnvDetails;
	public String gstrTimeOut;


	//start//navToFiltersPage//
	/*******************************************************************************************
	' Description  : Function to navigate to Filters Page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String navToFiltersPage(Selenium selenium) throws Exception {
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
			selenium.focus(propElementDetails.getProperty("filtersLink"));
			selenium.click(propElementDetails.getProperty("filtersLink"));
			Thread.sleep(10000);
			waitForElements.WaitForElements(selenium, "newSearchFilterButton",
					"New Search/Filter Button ");
			try {
				assertTrue(selenium.isVisible(propElementDetails
						.getProperty("newSearchFilterButton")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("newSearchFilterButton")));
				log4j.info("Navigation to Filters/Search Page is successfull.");
			} catch (AssertionError assertionFailedError) {
				log4j.info(assertionFailedError);
				log4j.info("Navigation to Filters/Search Page is NOT successfull.");
				lStrReason = lStrReason
						+ "; "
						+ "Navigation to Filters/Search Page is NOT successfull.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.navToFiltersPage failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//navToFiltersPage//

	//start//selSearchCriteria//
	/*******************************************************************************************
	' Description  : Function to click on Add Search Term Button and Select Required Criteria
	' Precondition : N/A 
	' Arguments    : selenium, pStrCriteria
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String selSearchCriteria(Selenium selenium, String pStrCriteria)
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
			selenium.focus(propElementDetails
					.getProperty("addSearchTermButton"));
			selenium.click(propElementDetails
					.getProperty("addSearchTermButton"));
			
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//div[@class='x-list-group-item'][contains(text(),'"
								+ pStrCriteria + "')]", pStrCriteria
								+ " criteria");
				assertTrue(selenium
						.isElementPresent("//div[@class='x-list-group-item'][contains(text(),'"
								+ pStrCriteria + "')]"));
				log4j.info(pStrCriteria + " is present in the drop down");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrCriteria + " is Not present in the drop down");
				lStrReason = pStrCriteria + " is Not present in the drop down ";
			}
			selenium.focus("//div[@class='x-list-group-item'][contains(text(),'"
					+ pStrCriteria + "')]");
			selenium.click("//div[@class='x-list-group-item'][contains(text(),'"
					+ pStrCriteria + "')]");
			
			try {
				waitForElements
						.WaitForElementsWithoutProperty(
								selenium,
								"//div[contains(@id,'checklistfiltercriteria')]/a[contains(@id,'button')]/span/span/span[contains(text(),'"
										+ pStrCriteria + "')]", pStrCriteria
										+ " button");
				assertTrue(selenium
						.isElementPresent("//div[contains(@id,'checklistfiltercriteria')]/a[contains(@id,'button')]/span/span/span[contains(text(),'"
								+ pStrCriteria + "')]"));
				log4j.info(pStrCriteria + " Button is Displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrCriteria + " Button is Not Displayed");
				lStrReason = pStrCriteria + " Button is Not Displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.selSearchCriteria failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//selSearchCriteria//
	
	//start//clkSaveAsBtn//
	/*******************************************************************************************
	' Description  : Function to click on Save As Button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkSaveAsBtn(Selenium selenium) throws Exception {
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
			selenium.focus(propElementDetails.getProperty("saveAsButton"));
			selenium.click(propElementDetails.getProperty("saveAsButton"));
			
			try {
				waitForElements
						.WaitForElementsWithoutProperty(
								selenium,
								"//span[@class='x-header-text x-window-header-text x-window-header-text-default']",
								"'Save As' window title");
				assertEquals("Save As", selenium.getText(propElementDetails
						.getProperty("saveAsWindowTitle")));
				log4j.info("'Save As' window is Displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Save As' window is Not Displayed");
				lStrReason = "'Save As' window is Not Displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkSaveAsBtn failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//clkSaveAsBtn//

	// start//saveFilter//
	/*******************************************************************************************
	' Description  : Function to Save Filter
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName, pStrDescription
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String saveFilter(Selenium selenium, String pStrFilterName,
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
			selenium.type(propElementDetails.getProperty("nameInput"),
					pStrFilterName);
			selenium.type(propElementDetails.getProperty("descriptionInput"),
					pStrDescription);
			selenium.click(propElementDetails
					.getProperty("saveAsWindowSaveBtn"));
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.saveFilter failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//saveFilter//
	
	//start//verifyCreatedFilter//
	/*******************************************************************************************
	' Description  : Function to verify Filter created with delete option
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String verifyCreatedFilter(Selenium selenium, String pStrFilterName)
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
			try {
				waitForElements.WaitForElementsWithoutProperty(selenium,
						"//tbody[@id='gridview-1019-body']/tr/td/div/div/div[text()='"
								+ pStrFilterName + "']", pStrFilterName
								+ " Filter");
				assertTrue(selenium
						.isElementPresent("//tbody[@id='gridview-1019-body']/tr/td/div/div/div[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName
						+ " is created and Displayed with Delete Option");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is Not created and Displayed with Delete Option");
				lStrReason = lStrReason
						+ "; "
						+ "Filter is Not created and Displayed with Delete Option.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.verifyCreatedFilter failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//verifyCreatedFilter//
	
	//start//clkTrackingStatusBtnAndSelAtLocationCheckBox//
	/**********************************************************************************************
	' Description  : Function to click 'Tracking Status ' button and select 'At Location' checkbox
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	***********************************************************************************************/
	public String clkTrackingStatusBtnAndSelAtLocationCheckBox(
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
			selenium.click(propElementDetails.getProperty("trackingStatusBtn"));

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("trackingStatusAtLocationCheckbox")));
				log4j.info("'Tracking Status' Button is clicked");
				log4j.info("'At Location' Check box is Displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'At Location' Check box is Not Displayed");
				lStrReason = lStrReason + "; "
						+ "'At Location' Check box is Not Displayed.";
			}

			selenium.click(propElementDetails
					.getProperty("trackingStatusAtLocationCheckbox"));
			log4j.info("'At Location' Check box is selected");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkTrackingStatusBtnAndSelAtLocationCheckBox failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//clkTrackingStatusBtnAndSelAtLocationCheckBox//
	
	//start//clkGenderBtnAndSelMaleCheckBox//
	/*******************************************************************************************
	' Description  : Function to click 'Gender' button and select 'Male' checkbox
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkGenderBtnAndSelMaleCheckBox(Selenium selenium)
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
			selenium.click(propElementDetails.getProperty("genderBtn"));

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("genderBtnMaleCheckbox")));
				log4j.info("'Gender' Button is clicked");
				log4j.info("'Male' Check box is Displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Male' Check box is Not Displayed");
				lStrReason = lStrReason + "; "
						+ "'Male' Check box is Not Displayed.";
			}

			selenium.click(propElementDetails
					.getProperty("genderBtnMaleCheckbox"));
			log4j.info("'Male' Check box is selected");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkGenderBtnAndSelMaleCheckBox failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkGenderBtnAndSelMaleCheckBox//
	
	//start//clkTriageCategoryBtnAndSelRedCheckBox//
	/*******************************************************************************************
	' Description  : Function to click on 'Triage Category' button and select 'Red' checkbox
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul  
	*******************************************************************************************/
	public String clkTriageCategoryBtnAndSelRedCheckBox(Selenium selenium)
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
			selenium.click(propElementDetails.getProperty("triageCategoryBtn"));

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("triageCategoryRedCheckbox")));
				log4j.info("'Triage Category' Button is clicked");
				log4j.info("'Red' Check box is Displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Red' Check box is Not Displayed");
				lStrReason = lStrReason + "; "
						+ "'Red' Check box is Not Displayed.";
			}

			selenium.click(propElementDetails
					.getProperty("triageCategoryRedCheckbox"));
			log4j.info("'Red' Check box is selected");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkTriageCategoryBtnAndSelRedCheckBox failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkTriageCategoryBtnAndSelRedCheckBox//
	
	//start//clkSaveAsWindowSaveBtn//
	/**********************************************************************************************
	' Description  : Function to click 'Save' button in 'Save As' window and verify 'Alert' window
	' Precondition : N/A 
	' Arguments    : selenium 
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	**********************************************************************************************/
	public String clkSaveAsWindowSaveBtn(Selenium selenium) throws Exception {
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
					.getProperty("saveAsWindowSaveBtn"));
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("alertWindow")));
				log4j.info("'Alert' Window is Displayed.");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("Save As window Save button clicked");
				log4j.info("'Alert' Window is Not Displayed.");
				lStrReason = lStrReason + "; " + "'Alert' Window is Not Displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkSaveAsWindowSaveBtn failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//clkSaveAsWindowSaveBtn//
	
	//start//clickCreatedFilter//
	/***************************************************************************************************
	' Description  : Function to click on created filter and verify Filter Name is displayed in Heading
	' Precondition : N/A 
	' Arguments    : selenium ,pStrFilterName
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul  
	***************************************************************************************************/
	public String clickCreatedFilter(Selenium selenium, String pStrFilterName)
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
			selenium.focus("//tbody[@id='gridview-1019-body']/tr/td/div/div/div[text()='"
					+ pStrFilterName + "']");
			selenium.click("//tbody[@id='gridview-1019-body']/tr/td/div/div/div[text()='"
					+ pStrFilterName + "']");

			try {
				Thread.sleep(5000);
				assertTrue(selenium.isElementPresent("//h2[text()='"
						+ pStrFilterName + "']"));
				log4j.info(pStrFilterName + " is clicked");
				log4j.info(pStrFilterName + " is Displayed in the Heading");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName + " is Not clicked");
				log4j.info(pStrFilterName + " is Not Displayed in the Heading");
				lStrReason = lStrReason + pStrFilterName
						+ " is Not Displayed in the Heading";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clickCreatedFilter failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//clickCreatedFilter//
	
	//start//clickSaveButton//
	/*******************************************************************************************
	' Description  : Function click 'Save' button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clickSaveButton(Selenium selenium) throws Exception {
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
			selenium.click(propElementDetails.getProperty("saveButton"));
			try {
				waitForElement
						.WaitForElementsWithoutProperty(
								selenium,
								"//span[@class='x-header-text x-window-header-text x-window-header-text-default']",
								"'Save' window title");
				assertEquals("Save", selenium.getText(propElementDetails
						.getProperty("saveAsWindowTitle")));
				log4j.info("'Save' button clicked'");
				log4j.info("'Save As' window is Displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Save As' window is Not Displayed");
				lStrReason = "'Save As' window is Not Displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clickSaveButton failed to complete due to "
					+ lStrReason + " ;" + e.toString();
		}
		return lStrReason;
	}
	// end//clickSaveButton//
	
	//start//removeTriageCategoryButton//
	/*******************************************************************************************
	' Description  : Function to remove 'Triage Category' button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul  
	*******************************************************************************************/
	public String removeTriageCategoryButton(Selenium selenium)
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
			selenium.click(propElementDetails.getProperty("removeTrackingStatusBtn"));

			try {
				assertFalse(selenium.isElementPresent(propElementDetails
						.getProperty("triageCategoryBtn")));
				log4j.info("'Triage Category' Button is Removed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Triage Category' Button is Not Removed");
				lStrReason = "'Triage Category' Button is Not Removed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.removeTriageCategoryButton failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//removeTriageCategoryButton//
	
	//start//deleteFilter//
	/*******************************************************************************************
	' Description  : Function to delete created filter
	' Precondition : N/A 
	' Arguments    : selenium ,pStrFilterName
	' Returns      : String 
	' Date         : 05/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String deleteFilter(Selenium selenium, String pStrFilterName)
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
			selenium.focus("//tr[contains(@id,'gridview')]/td/div/div/div[text()='"
					+ pStrFilterName + "']");
			selenium.mouseOver("//tr[contains(@id,'gridview')]/td/div/div/div[text()='"
					+ pStrFilterName + "']");

			selenium.focus("//div[text()='"
					+ pStrFilterName
					+ "']/parent::div/parent::div/parent::td/parent::tr/td/div/div/i");
			selenium.mouseOver("//div[text()='"
					+ pStrFilterName
					+ "']/parent::div/parent::div/parent::td/parent::tr/td/div/div/i");
			selenium.click("//div[text()='"
					+ pStrFilterName
					+ "']/parent::div/parent::div/parent::td/parent::tr/td/div/div/i");

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("alertWindow")));
				log4j.info(pStrFilterName + " 'Delete' button is clicked");
				log4j.info("'Delete Filter' Pop Up is Displayed.");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Delete Filter' Pop Up is Not Displayed.");
				lStrReason = lStrReason + "; "
						+ "'Delete Filter' Pop Up is Not Displayed.";
			}

			selenium.click(propElementDetails.getProperty("alertWindowYesBtn"));

			try {
				assertFalse(selenium
						.isElementPresent("//tbody[@id='gridview-1019-body']/tr/td/div/div/div[text()='"
								+ pStrFilterName + "']"));
				log4j.info("'Yes' button in 'Delete Filter' pop up is clicked");
				log4j.info(pStrFilterName + " is Deleted Successfully");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName + " is Not Deleted Successfully");
				lStrReason = pStrFilterName + " is Not Deleted Successfully";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.deleteFilter failed to complete due to "
					+ lStrReason + " ;" + e.toString();
		}
		return lStrReason;
	}
	// end//deleteFilter//
	
	//start//clkOnSearchButn//
	/*******************************************************************************************
	' Description  : Function to click on search button
	' Precondition : N/A 
	' Arguments    : selenium,strColumn
	' Returns      : String 
	' Date         : 11/11/2013
	' Author       : Manasa  
	*******************************************************************************************/
	public String clkOnSearchButn(Selenium selenium) throws Exception {
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
			selenium.click(propElementDetails.getProperty("FilterSearchButn"));
			waitForElements.WaitForElements(selenium, "FiltersColumnButn",
					"Column Button");
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("FiltersColumnButn")));
				log4j.info("'Add Column' Button is displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Add Column' Button is NOT displayed");
				lStrReason = "'Add Column' Button is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkOnSearchButn failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}

	// end//clkOnSearchButn//
	//start//clkOnColumnButnAndSelectColumn//
	/**************************************************************************************************
	' Description  : Function to click on Column Button and select the column from the columns dropdown
	' Precondition : N/A 
	' Arguments    : selenium,strColumn
	' Returns      : String 
	' Date         : 11/11/2013
	' Author       : Manasa  
	***************************************************************************************************/
	public String clkOnColumnButnAndSelectColumn(Selenium selenium,
			String strColumn) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {
			selenium.click(propElementDetails.getProperty("FiltersColumnButn"));
			Thread.sleep(5000);
			try {
				selenium.focus("//div[@class='x-panel x-layer x-panel-default x-menu x-border-box x-vertical-scroller x-panel-vertical-scroller x-panel-default-vertical-scroller']/div/div/div/div/a/span[contains(text(),'"+strColumn+"')]/parent::a/div");
				selenium.click("//div[@class='x-panel x-layer x-panel-default x-menu x-border-box x-vertical-scroller x-panel-vertical-scroller x-panel-default-vertical-scroller']/div/div/div/div/a/span[contains(text(),'"+strColumn+"')]/parent::a/div");
				log4j.info("'Add Column' Button is displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Add Column' Button is NOT displayed");
				lStrReason = "'Add Column' Button is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkOnSearchButn failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}

	// end//clkOnColumnButnAndSelectColumn//
	//start//vfyTheCreatedColumn//
	/**************************************************************************************************
	' Description  : Function to verify that the added Column is displayed
	' Precondition : N/A 
	' Arguments    : selenium,strColumn
	' Returns      : String 
	' Date         : 11/11/2013
	' Author       : Manasa  
	***************************************************************************************************/
	public String vfyTheCreatedColumn(Selenium selenium, String strColumn)
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
			
			try {
				assertEquals(strColumn, selenium.getText("//div[@class='x-container x-border-item x-box-item x-container-default']/span/div/div/div/div/div/div/div/div/div/span[contains(text(),'"+strColumn+"')]"));
				log4j.info("The newly added column "+strColumn+" is displayed on the filters page ");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("The newly added column "+strColumn+" is NOT displayed on the filters page ");
				lStrReason = "The newly added column "+strColumn+" is NOT displayed on the filters page ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.vfyTheCreatedColumn failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//vfyTheCreatedColumn//
	
	//start//verifyCreatedFilterNotPresent//
	/***************************************************************************************************************
	' Description  : Function to verify Filter created using one region admin is not displayed in other region admin
	' Precondition : N/A 
	' Arguments    : selenium, pStrFilterName
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String verifyCreatedFilterNotPresent(Selenium selenium,
			String pStrFilterName) throws Exception {
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
						.isElementPresent("//tbody[@id='gridview-1019-body']/tr/td/div/div/div[text()='"
								+ pStrFilterName + "']"));
				log4j.info(pStrFilterName
						+ " is NOT Displayed with Delete Option");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrFilterName
						+ " is Displayed with Delete Option");
				lStrReason = lStrReason
						+ "; "
						+ "Filter is Displayed with Delete Option.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.verifyCreatedFilter failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//verifyCreatedFilterNotPresent//
	
	//start//clkLabelBtnAndSelRequiredLabel//
	/**********************************************************************************************
	' Description  : Function to click 'Label' button and select required checkbox
	' Precondition : N/A 
	' Arguments    : selenium, pStrLabelName
	' Returns      : String 
	' Date         : 12/11/2013
	' Author       : Rahul 
	***********************************************************************************************/
	public String clkLabelBtnAndSelRequiredLabel(Selenium selenium,
			String pStrLabelName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("labelBtn"));

			try {
				assertTrue(selenium.isElementPresent("//span[text()='"
						+ pStrLabelName + "']/parent::div/img"));
				log4j.info("'Label' Button is clicked");
				log4j.info(pStrLabelName + " Check box is Displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrLabelName + " Check box is Not Displayed");
				lStrReason = pStrLabelName + " Check box is Not Displayed";
			}

			selenium.click("//span[text()='" + pStrLabelName
					+ "']/parent::div/img");
			log4j.info(pStrLabelName + " Check box is selected");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkLabelBtnAndSelRequiredLabel failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkLabelBtnAndSelRequiredLabel//
		
	//start//clkMagnifierIconInSearchWindow//
	/*******************************************************************************************
	' Description  : Function to click on Magnifier Icon In Search Window
	' Precondition : N/A 
	' Arguments    : selenium ,pStrPatientID
	' Returns      : String 
	' Date         : 12/11/2013
	' Author       : Rahul 
	*******************************************************************************************/
	public String clkMagnifierIconInSearchWindow(Selenium selenium,
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
				log4j.info("'Magnifier' icon  clicked in 'Search' window");
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
				log4j.info("'Magnifier' icon Not clicked in 'Search' window");
				log4j.info("Detailed Client Information:"
						+ pStrPatientID
						+ " is Not displayed as 'Client Infomation' window title");
				lStrReason = "'Magnifier' icon Not clicked in 'Search' window";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.clkMagnifierIconInSearchWindow failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//clkMagnifierIconInSearchWindow//
	
	//start//shareFilter//
	/*******************************************************************************************
	' Description  : Function to Share the filter
	' Precondition : N/A 
	' Arguments    : selenium, pStrName, pStrDescription, pStrRole
	' Returns      : String 
	' Date         : 14/11/2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String shareFilter(Selenium selenium, String pStrFilterName,
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
			selenium.type(propElementDetails.getProperty("nameInput"),
					pStrFilterName);
			log4j.info(pStrFilterName
					+ " entered for 'Name' input field in 'Save Dashboard' window");
			selenium.type(propElementDetails.getProperty("descriptionInput"),
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
					.getProperty("saveAsWindowSaveBtn"));
			log4j.info("Save Dashboard window Save button clicked");

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "DashBoardPage.shareDashBoard failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}

	// end//shareDashBoard//
	
	//start//vfyTheDeletedColumn//
	/**************************************************************************************************
	' Description  : Function to verify that the added Column is displayed
	' Precondition : N/A 
	' Arguments    : selenium,strColumn
	' Returns      : String 
	' Date         : 11/11/2013
	' Author       : Manasa  
	***************************************************************************************************/
	public String vfyTheDeletedColumn(Selenium selenium, String strColumn)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strHeader=selenium.getText("//div[@id='headercontainer-1045']/div/div");
		try {
			try {
				assertFalse(strHeader.contains(strColumn));
				log4j.info("The deleted column " + strColumn
						+ " is displayed NOT on the filters page ");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("The deleted column " + strColumn
						+ " is displayed on the filters page ");
				lStrReason = "The deleted column " + strColumn
						+ " is displayed on the filters page ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.vfyTheCreatedColumn failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//vfyTheDeletedColumn//
	
	//start//clkOnSaveBtnInsaveWindow//
	/**********************************************************************************************
	' Description  : Function to click 'Save' button in 'Save' window and verify the filter name
	' Precondition : N/A 
	' Arguments    : selenium 
	' Returns      : String 
	' Date         : 14/11/2013
	' Author       : Manasa 
	**********************************************************************************************/
	public String clkOnSaveBtnInSaveWindow(Selenium selenium,
			String pStrFilterName) throws Exception {
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
					.getProperty("saveAsWindowSaveBtn"));
			try {
				assertTrue(selenium.isElementPresent("//h2[text()='"
						+ pStrFilterName + "']"));
				log4j.info(pStrFilterName + " Filter is saved");
			} catch (AssertionError e) {
				log4j.info(pStrFilterName + " Filter is NOT saved");
				lStrReason = pStrFilterName + " Filter is saved";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "FiltersPage.clkSaveAsWindowSaveBtn failed to complete due to "
					+ e.toString();
		}
		return lStrReason;
	}
	// end//clkOnSaveBtnInsaveWindow//
}