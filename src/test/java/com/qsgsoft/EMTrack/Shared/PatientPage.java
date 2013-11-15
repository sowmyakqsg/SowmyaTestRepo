package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.*;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.thoughtworks.selenium.Selenium;

public class PatientPage {
	
	Properties ElementDetails;	
	static Logger log4j = Logger.getLogger("PatientPage");
	public Properties propElementDetails, propEnvDetails;
	public String gstrTimeOut;
	
	//start//navigateToPatientPage//
    /*******************************************************************************************
  	' Description  : Function to navigate to 'Patient' page
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 08-11-2013
  	' Author       : Rahul 
  	*******************************************************************************************/
	public String navigateToPatientPage(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		WaitForElement waitForElements = new WaitForElement();
		
		try {
			selenium.focus(propElementDetails.getProperty("patientLink"));
			selenium.click(propElementDetails.getProperty("patientLink"));
			Thread.sleep(20000);
			waitForElements.WaitForElementsWithoutProperty(selenium, "css=#triageTagField", "Triage Tag ID");
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("addDailyPatientID")));
				log4j.info("Navigation to 'Patient' Page is successfull.");
				
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Navigation to 'Patient' Page is NOT successfull.");
				lStrReason = lStrReason + "; " + "Navigation to 'Patient' Page is NOT successfull.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.navigateToPatientPage failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//navigateToPatientPage//
	
	//start//addDailyPatientMandatoryData//
	/************************************************************************************************************************
	' Description  : Function to add the daily patient with the mandatory data.
	' Precondition : N/A 
	' Arguments    : selenium, pStrPatientID, pStrGender, pStrIncident, pStrDestination, pStrProvider, pStrUnit, pStrETA
	' Returns      : String 
	' Date         : 08-11-2013
	' Author       : Rahul 
	*************************************************************************************************************************/
	public String addDailyPatientMandatoryData(Selenium selenium,
			String pStrPatientID, String pStrGender, String pStrIncident,
			String pStrDestination, String pStrProvider, String pStrUnit,
			String pStrETA) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				assertEquals("EMTrack ~Add Daily Patient", selenium.getTitle());
				log4j.info(" 'EMTrack ~Add Daily Patient' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'EMTrack ~Add Daily Patient' page is Not displayed");
				lStrReason = "'EMTrack ~Add Daily Patient' page is Not displayed";
			}

			selenium.type(propElementDetails.getProperty("addDailyPatientID"),
					pStrPatientID);

			try {
				assertEquals(pStrPatientID,
						selenium.getValue(propElementDetails
								.getProperty("addDailyPatientID")));
				log4j.info(pStrPatientID
						+ " entered for 'ID' field in 'EMTrack ~Add Daily Patient' page");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrPatientID
						+ " Not entered for 'ID' field in 'EMTrack ~Add Daily Patient' page");
				lStrReason = pStrPatientID
						+ " Not entered for 'ID' field in 'EMTrack ~Add Daily Patient' page";
			}

			selenium.click("//input[@id='" + pStrGender + "']");
			log4j.info(pStrGender + " radio selected for 'Gender' input");

			selenium.click("//label[text()='" + pStrIncident + "']/input");
			log4j.info(pStrIncident
					+ " checkbox selected for 'Incident Involvement:' input");

			selenium.focus("css=#propertySelection");
			selenium.select("css=#propertySelection",
					pStrDestination);
			log4j.info(pStrDestination
					+ " value selected for 'Destination' dropdown");

			selenium.focus("css=#propertySelection_0");
			selenium.select("css=#propertySelection_0", pStrProvider);
			log4j.info(pStrProvider + " value selected for 'Provider' dropdown");

			selenium.focus(propElementDetails.getProperty("unitInputField"));
			selenium.type(propElementDetails.getProperty("unitInputField"),
					pStrUnit);

			try {
				assertEquals(pStrUnit, selenium.getValue(propElementDetails
						.getProperty("unitInputField")));
				log4j.info(pStrUnit + " Entered For 'Unit' Field");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrUnit + " Not Entered For 'Unit' Field");
				lStrReason = pStrUnit + " Not entered for 'Unit' field";
			}

			selenium.focus(propElementDetails.getProperty("ETAField"));
			selenium.type(propElementDetails.getProperty("ETAField"), pStrETA);

			try {
				assertEquals(pStrETA, selenium.getValue(propElementDetails
						.getProperty("ETAField")));
				log4j.info(pStrETA + " Entered For 'ETA' Field");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrETA + " Not Entered For 'ETA' Field");
				lStrReason = pStrETA + " Not entered for 'ETA' field";
			}

			selenium.focus(propElementDetails.getProperty("saveBtn"));
			selenium.click(propElementDetails.getProperty("saveBtn"));
			Thread.sleep(15000);

			try {
				assertEquals("EMTrack ~ Dashboard", selenium.getTitle());
				log4j.info("'Save' button clicked in 'EMTrack ~Add Daily Patient' page");
				log4j.info("'EMTrack ~ Dashboard' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Save' button Not clicked in 'EMTrack ~Add Daily Patient' page");
				log4j.info("'EMTrack ~ Dashboard' page is Not displayed");
				lStrReason = "'EMTrack ~ Dashboard' page is Not displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.addDailyPatientMandatoryData failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//addDailyPatientMandatoryData//
		
	//start//addEvacueePatientMandatoryData//
	/**************************************************************************************************************************
	' Description  : Function to add the daily patient with the mandatory data.
	' Precondition : N/A 
	' Arguments    : selenium, pStrPatientID, pStrCurrentLocation, pStrDestination, pStrProvider, pStrUnit, pStrETA, pStrLabel
	' Returns      : String 
	' Date         : 08-11-2013
	' Author       : Rahul 
	***************************************************************************************************************************/
	public String addEvacueePatientMandatoryData(Selenium selenium,
			String pStrPatientID, String pStrCurrentLocation,
			String pStrDestination, String pStrProvider, String pStrUnit,
			String pStrETA, String pStrLabel) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.click(propElementDetails.getProperty("addEvacueeLink"));

			try {
				Thread.sleep(20000);
				assertEquals("EMTrack ~Add Evacuee", selenium.getTitle());
				log4j.info("EMTrack ~Add Evacuee page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("EMTrack ~Add Evacuee page is Not displayed");
				lStrReason = "EMTrack ~Add Evacuee page is Not displayed";
			}

			selenium.type(
					propElementDetails.getProperty("addEvacueePatientID"),
					pStrPatientID);

			try {
				assertEquals(pStrPatientID,
						selenium.getValue(propElementDetails
								.getProperty("addEvacueePatientID")));
				log4j.info(pStrPatientID
						+ " entered for 'ID' field in 'EMTrack ~Add Evacuee' page");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrPatientID
						+ " Not entered for 'ID' field in 'EMTrack ~Add Evacuee' page");
				lStrReason = pStrPatientID
						+ " Not entered for 'ID' field in 'EMTrack ~Add Evacuee' page";
			}

			selenium.focus("css=#propertySelection");
			selenium.select("css=#propertySelection", pStrCurrentLocation);
			log4j.info(pStrCurrentLocation
					+ " value selected for 'Current Location' dropdown in 'EMTrack ~Add Evacuee' page");

			selenium.focus("css=#propertySelection_0");
			selenium.select("css=#propertySelection_0", pStrDestination);
			log4j.info(pStrDestination
					+ " value selected for 'Destination' dropdown in 'EMTrack ~Add Evacuee' page");

			selenium.focus("css=#propertySelection_1");
			selenium.select("css=#propertySelection_1", pStrProvider);
			log4j.info(pStrProvider
					+ " value selected for 'Provider' dropdown in 'EMTrack ~Add Evacuee' page");

			selenium.focus(propElementDetails.getProperty("unitInputField"));
			selenium.type(propElementDetails.getProperty("unitInputField"),
					pStrUnit);

			try {
				assertEquals(pStrUnit, selenium.getValue(propElementDetails
						.getProperty("unitInputField")));
				log4j.info(pStrUnit
						+ " Entered For 'Unit' Field in 'EMTrack ~Add Evacuee' page");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrUnit
						+ " Not Entered For 'Unit' Field in 'EMTrack ~Add Evacuee' page");
				lStrReason = pStrUnit
						+ " Not entered for 'Unit' field in 'EMTrack ~Add Evacuee' page";
			}

			selenium.focus(propElementDetails.getProperty("ETAField"));
			selenium.type(propElementDetails.getProperty("ETAField"), pStrETA);

			try {
				assertEquals(pStrETA, selenium.getValue(propElementDetails
						.getProperty("ETAField")));
				log4j.info(pStrETA
						+ " Entered For 'ETA' Field in 'EMTrack ~Add Evacuee' page");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrETA
						+ " Not Entered For 'ETA' Field in 'EMTrack ~Add Evacuee' page");
				lStrReason = pStrETA
						+ " Not entered for 'ETA' field in 'EMTrack ~Add Evacuee' page";
			}

			selenium.focus("//div[@id='multiCheckBox_0']/div/label[contains(text(),'"
					+ pStrLabel + "')]/input");
			selenium.click("//div[@id='multiCheckBox_0']/div/label[contains(text(),'"
					+ pStrLabel + "')]/input");
			log4j.info(pStrLabel + " Label selected");

			selenium.focus(propElementDetails.getProperty("saveBtn"));
			selenium.click(propElementDetails.getProperty("saveBtn"));
			Thread.sleep(15000);

			try {
				assertEquals("EMTrack ~ Dashboard", selenium.getTitle());
				log4j.info("'Save' button clicked in 'EMTrack ~Add Evacuee' page");
				log4j.info("'EMTrack ~ Dashboard' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Save' button Not clicked in 'EMTrack ~Add Evacuee' page");
				log4j.info("'EMTrack ~ Dashboard' page is Not displayed");
				lStrReason = "'EMTrack ~ Dashboard' page is Not displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.addEvacueePatientMandatoryData failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//addEvacueePatientMandatoryData//

	//start//navigateToMCITriagePage//
    /*******************************************************************************************
  	' Description  : Function to navigate to 'MCI Triage' page
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 11-11-2013
  	' Author       : Manasa 
  	*******************************************************************************************/
	public String navigateToMCITriagePage(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		WaitForElement waitForElements = new WaitForElement();
		
		try {
			selenium.click(propElementDetails.getProperty("MCI_patient"));
			Thread.sleep(2000);
			waitForElements.WaitForElementsWithoutProperty(selenium, "//input[@id='triageTagField']", "Triage Tag ID");
			 try{
				  assertEquals("EMTrack ~Add MCI Triage", selenium.getTitle());
				  log4j.info("Add MCI Triage page is displayed");
			} catch (AssertionError ae) {
				log4j.info("Add MCI Triage page is NOT displayed");
				log4j.info("Add MCI Triage page is NOT displayed");
				lStrReason = lStrReason + "; " + "Add MCI Triage page is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.navigateToMCITriagePage failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//navigateToMCITriagePage//
	
	//start//enterDataInMCITriagePage//
    /*******************************************************************************************
  	' Description  : Function to enter the mandatory data in 'MCI Triage' page
  	' Precondition : N/A 
  	' Arguments    : selenium, pStrPatientId, strCurrentLoc, strTriage,strInciInvolmnt
  	' Returns      : String 
  	' Date         : 11-11-2013
  	' Author       : Manasa 
  	*******************************************************************************************/
	public String enterDataInMCITriagePage(Selenium selenium,
			String pStrPatientId, String strCurrentLoc, String strTriage,
			String strInciInvolmnt) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {
			// Give patient name
			selenium.type(propElementDetails.getProperty("MCI_TriageID"),
					pStrPatientId);
			log4j.info("MCI TriageID is " + pStrPatientId);
			// Select triage category
			selenium.click(strTriage);
			log4j.info("Triage catrgory is " + strTriage);
			// Select Incident Involvenment
			selenium.focus("//div[@id='relatedIncidentsField_0']/div/label[contains(text(),'"
					+ strInciInvolmnt + "')]/input");
			selenium.click("//div[@id='relatedIncidentsField_0']/div/label[contains(text(),'"
					+ strInciInvolmnt + "')]/input");
			log4j.info("Incident Involvement is " + strInciInvolmnt);
			// Select Current Location

			selenium.select(propElementDetails.getProperty("MCI_CurrentLoc"),
					"label=" + strCurrentLoc);
			log4j.info("Current Location is " + strCurrentLoc);
			// save
			selenium.click(propElementDetails.getProperty("MCI_Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("IncidentModeBtn")));
				log4j.info("MCI Patient is creaetd");

			} catch (AssertionError be) {
				log4j.info("MCI Patient is NOT creaetd");
				lStrReason = lStrReason + "MCI Patient is not creaetd";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.enterDataInMCITriagePage failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//enterDataInMCITriagePage//
	
	//start//vfyEndIncidentNotPresentInaddDailyPatientPage//
	/*****************************************************************************************************************************
	' Description  : Function to verify ended incident is Not displayed in 'Add Daily Patients Page' Incident Involvement Section.
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncident
	' Returns      : String 
	' Date         : 14-11-2013
	' Author       : Rahul 
	******************************************************************************************************************************/
	public String vfyEndIncidentNotPresentInaddDailyPatientPage(
			Selenium selenium, String pStrIncident) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				assertEquals("EMTrack ~Add Daily Patient", selenium.getTitle());
				log4j.info(" 'EMTrack ~Add Daily Patient' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'EMTrack ~Add Daily Patient' page is Not displayed");
				lStrReason = "'EMTrack ~Add Daily Patient' page is Not displayed";
			}
			
			try {
				assertFalse(selenium
						.isElementPresent("//div[@class='scrollableMultipleCheckBoxesComponent field scrollableMultipleCheckBoxes']/div[@class='input']/label[contains(text(),'"
								+ pStrIncident + "')]"));
				log4j.info(pStrIncident
						+ " Ended incident is Not present in 'Add Daily Patients Page' Incident Involvement Section");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrIncident
						+ " Ended incident is present in 'Add Daily Patients Page' Incident Involvement Section");
				lStrReason = pStrIncident
						+ " Ended incident is present in 'Add Daily Patients Page' Incident Involvement Section";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.vfyEndIncidentNotPresentInaddDailyPatientPage failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyEndIncidentNotPresentInaddDailyPatientPage//
	
	//start//vfyEndIncidentNotPresentInAddEvacueePatientPage//
	/*****************************************************************************************************************************
	' Description  : Function to verify ended incident is Not displayed in 'Add Evacuee Patients Page' Incident Involvement Section.
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncident
	' Returns      : String 
	' Date         : 14-11-2013
	' Author       : Rahul 
	******************************************************************************************************************************/
	public String vfyEndIncidentNotPresentInAddEvacueePatientPage(
			Selenium selenium, String pStrIncident) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.click(propElementDetails.getProperty("addEvacueeLink"));

			try {
				Thread.sleep(20000);
				assertEquals("EMTrack ~Add Evacuee", selenium.getTitle());
				log4j.info("EMTrack ~Add Evacuee page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("EMTrack ~Add Evacuee page is Not displayed");
				lStrReason = "EMTrack ~Add Evacuee page is Not displayed";
			}
			
			try {
				assertFalse(selenium
						.isElementPresent("//div[@class='scrollableMultipleCheckBoxesComponent field scrollableMultipleCheckBoxes']/div[@class='input']/label[contains(text(),'"
								+ pStrIncident + "')]"));
				log4j.info(pStrIncident
						+ " Ended incident is Not present in 'Add Evacuee Patients Page' Incident Involvement Section");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrIncident
						+ " Ended incident is present in 'Add Evacuee Patients Page' Incident Involvement Section");
				lStrReason = pStrIncident
						+ " Ended incident is present in 'Add Evacuee Patients Page' Incident Involvement Section";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.vfyEndIncidentNotPresentInAddEvacueePatientPage failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyEndIncidentNotPresentInAddEvacueePatientPage//
	
	//start//vfyEndIncidentNotPresentInVaccinationPatientPage//
	/*****************************************************************************************************************************
	' Description  : Function to verify ended incident is Not displayed in 'Vaccination Patients Page' Incident Involvement Section.
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncident
	' Returns      : String 
	' Date         : 14-11-2013
	' Author       : Rahul 
	******************************************************************************************************************************/
	public String vfyEndIncidentNotPresentInVaccinationPatientPage(
			Selenium selenium, String pStrIncident) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.click(propElementDetails
					.getProperty("vaccinationPatientLink"));

			try {
				Thread.sleep(20000);
				assertEquals("EMTrack ~Add VaccinationPatient",
						selenium.getTitle());
				log4j.info("'EMTrack ~Add VaccinationPatient' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'EMTrack ~Add VaccinationPatient' page is Not displayed");
				lStrReason = "'EMTrack ~Add VaccinationPatient' page is Not displayed";
			}

			try {
				assertFalse(selenium
						.isElementPresent("//div[@class='scrollableMultipleCheckBoxesComponent field scrollableMultipleCheckBoxes']/div[@class='input']/label[contains(text(),'"
								+ pStrIncident + "')]"));
				log4j.info(pStrIncident
						+ " Ended incident is Not present in 'Vaccination Patients Page' Incident Involvement Section");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrIncident
						+ " Ended incident is present in 'Vaccination Patients Page' Incident Involvement Section");
				lStrReason = pStrIncident
						+ " Ended incident is present in 'Vaccination Patients Page' Incident Involvement Section";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.vfyEndIncidentNotPresentInVaccinationPatientPage failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyEndIncidentNotPresentInVaccinationPatientPage//
	
	//start//vfyEndIncidentNotPresentInMCITriagePatientPage//
	/*****************************************************************************************************************************
	' Description  : Function to verify ended incident is Not displayed in 'MCI Triage Patients Page' Incident Involvement Section.
	' Precondition : N/A 
	' Arguments    : selenium, pStrIncident
	' Returns      : String 
	' Date         : 14-11-2013
	' Author       : Rahul 
	******************************************************************************************************************************/
	public String vfyEndIncidentNotPresentInMCITriagePatientPage(
			Selenium selenium, String pStrIncident) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.click(propElementDetails
					.getProperty("MCITriagePatientLink"));

			try {
				Thread.sleep(20000);
				assertEquals("EMTrack ~Add MCI Triage",
						selenium.getTitle());
				log4j.info("'EMTrack ~Add MCI Triage' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'EMTrack ~Add MCI Triage' page is Not displayed");
				lStrReason = "'EMTrack ~Add MCI Triage' page is Not displayed";
			}

			try {
				assertFalse(selenium
						.isElementPresent("//div[@class='scrollableMultipleCheckBoxesComponent field scrollableMultipleCheckBoxes']/div[@class='input']/label[contains(text(),'"
								+ pStrIncident + "')]"));
				log4j.info(pStrIncident
						+ " Ended incident is Not present in 'MCI Triage Patients Page' Incident Involvement Section");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrIncident
						+ " Ended incident is present in 'MCI Triage Patients Page' Incident Involvement Section");
				lStrReason = pStrIncident
						+ " Ended incident is present in 'MCI Triage Patients Page' Incident Involvement Section";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.vfyEndIncidentNotPresentInMCITriagePatientPage failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//vfyEndIncidentNotPresentInMCITriagePatientPage//
	
	//start//addDailyPatientMandatoryData//
	/************************************************************************************************************************
	' Description  : Function to add the daily patient with the mandatory data.
	' Precondition : N/A 
	' Arguments    : selenium, pStrPatientID, pStrGender, pStrDestination, pStrProvider, pStrUnit, pStrETA
	' Returns      : String 
	' Date         : 08-11-2013
	' Author       : Rahul 
	*************************************************************************************************************************/
	public String addDailyPatientMandatoryData(Selenium selenium,
			String pStrPatientID, String pStrGender, String pStrDestination,
			String pStrProvider, String pStrUnit, String pStrETA)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				assertEquals("EMTrack ~Add Daily Patient", selenium.getTitle());
				log4j.info(" 'EMTrack ~Add Daily Patient' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'EMTrack ~Add Daily Patient' page is Not displayed");
				lStrReason = "'EMTrack ~Add Daily Patient' page is Not displayed";
			}

			selenium.type(propElementDetails.getProperty("addDailyPatientID"),
					pStrPatientID);

			try {
				assertEquals(pStrPatientID,
						selenium.getValue(propElementDetails
								.getProperty("addDailyPatientID")));
				log4j.info(pStrPatientID
						+ " entered for 'ID' field in 'EMTrack ~Add Daily Patient' page");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrPatientID
						+ " Not entered for 'ID' field in 'EMTrack ~Add Daily Patient' page");
				lStrReason = pStrPatientID
						+ " Not entered for 'ID' field in 'EMTrack ~Add Daily Patient' page";
			}

			selenium.click("//input[@id='" + pStrGender + "']");
			log4j.info(pStrGender + " radio selected for 'Gender' input");

			selenium.focus("css=#propertySelection");
			selenium.select("css=#propertySelection", pStrDestination);
			log4j.info(pStrDestination
					+ " value selected for 'Destination' dropdown");

			selenium.focus("css=#propertySelection_0");
			selenium.select("css=#propertySelection_0", pStrProvider);
			log4j.info(pStrProvider + " value selected for 'Provider' dropdown");

			selenium.focus(propElementDetails.getProperty("unitInputField"));
			selenium.type(propElementDetails.getProperty("unitInputField"),
					pStrUnit);

			try {
				assertEquals(pStrUnit, selenium.getValue(propElementDetails
						.getProperty("unitInputField")));
				log4j.info(pStrUnit + " Entered For 'Unit' Field");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrUnit + " Not Entered For 'Unit' Field");
				lStrReason = pStrUnit + " Not entered for 'Unit' field";
			}

			selenium.focus(propElementDetails.getProperty("ETAField"));
			selenium.type(propElementDetails.getProperty("ETAField"), pStrETA);

			try {
				assertEquals(pStrETA, selenium.getValue(propElementDetails
						.getProperty("ETAField")));
				log4j.info(pStrETA + " Entered For 'ETA' Field");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrETA + " Not Entered For 'ETA' Field");
				lStrReason = pStrETA + " Not entered for 'ETA' field";
			}

			selenium.focus(propElementDetails.getProperty("saveBtn"));
			selenium.click(propElementDetails.getProperty("saveBtn"));
			Thread.sleep(15000);

			try {
				assertEquals("EMTrack ~ Dashboard", selenium.getTitle());
				log4j.info("'Save' button clicked in 'EMTrack ~Add Daily Patient' page");
				log4j.info("'EMTrack ~ Dashboard' page is displayed");
			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info("'Save' button Not clicked in 'EMTrack ~Add Daily Patient' page");
				log4j.info("'EMTrack ~ Dashboard' page is Not displayed");
				lStrReason = "'EMTrack ~ Dashboard' page is Not displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "PatientPage.addDailyPatientMandatoryData failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//addDailyPatientMandatoryData//
}