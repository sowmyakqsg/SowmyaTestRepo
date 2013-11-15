package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.*;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class LocatePage {
	
	Properties ElementDetails;
	static Logger log4j = Logger.getLogger("DashBoardPage");
	public Properties propElementDetails, propEnvDetails;
	public String gstrTimeOut;
	
	//start//navigateToLocatePage//
    /*******************************************************************************************
  	' Description  : Function to navigate to 'Locate' page
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 15-11-2013
  	' Author       : Rahul 
  	*******************************************************************************************/
	public String navigateToLocatePage(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		WaitForElement waitForElements = new WaitForElement();
		
		try {
			selenium.focus(propElementDetails.getProperty("locateLink"));
			selenium.click(propElementDetails.getProperty("locateLink"));
			Thread.sleep(15000);
			
			try {
				waitForElements.WaitForElements(selenium, "addSearchTermButton", "Add Search Term Button");
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("addSearchTermButton")));
				log4j.info("Navigation to 'Locate' Page is successfull.");
			} catch (AssertionError ae) {
				log4j.info(ae);
				log4j.info("Navigation to 'Locate' Page is NOT successfull.");
				lStrReason = lStrReason + "; " + "Navigation to 'Locate' Page is NOT successfull.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "LocatePage.navigateToLocatePage failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//navigateToLocatePage//
	
	//start//selSearchCriteria//
	/*******************************************************************************************
	' Description  : Function to click on Add Search Term Button and Select Required Criteria
	' Precondition : N/A 
	' Arguments    : selenium, pStrCriteria
	' Returns      : String 
	' Date         : 15/11/2013
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
				Thread.sleep(1000);
				/*waitForElements
						.WaitForElementsWithoutProperty(
								selenium,
								"//div[contains(@id,'identifiercriterion')]/a[contains(@id,'button')]/span/span/span[@class='x-btn-inner x-btn-inner-center']",
								pStrCriteria + " Button");*/
				assertEquals(
						pStrCriteria + ": Any",
						selenium.getText(""));
				log4j.info(pStrCriteria + " Button is Displayed");

			} catch (AssertionError e) {
				log4j.info(e);
				log4j.info(pStrCriteria + " Button is Not Displayed");
				lStrReason = pStrCriteria + " Button is Not Displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "LocatePage.selSearchCriteria failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//selSearchCriteria//
	//start//vfyDefaultColumnsInlocateClntsPge//
    /***********************************************************************************
  	' Description  : Function to verify that the default columns in locate clients page
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 15-11-2013
  	' Author       : Manasa 
  	************************************************************************************/
	public String vfyDefaultColumnsInlocateClntsPge(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		String strLocClintHeader = selenium.getText(propElementDetails
				.getProperty("LocateClintHeader"));
		try {
			try {
				assertTrue(strLocClintHeader.contains("First Name"));
				log4j.info("First Name column is displayed by default");

				assertTrue(strLocClintHeader.contains("Last Name"));
				log4j.info("Last Name column is displayed by default");

				assertTrue(strLocClintHeader.contains("Age"));
				log4j.info("Age column is displayed by default");

				assertTrue(strLocClintHeader.contains("Gender"));
				log4j.info("Gender column is displayed by default");

				assertTrue(strLocClintHeader.contains("Current/Next Location"));
				log4j.info("Current/Next Location column is displayed by default");

				assertTrue(strLocClintHeader.contains("Last Updated"));
				log4j.info("Last Updated column is displayed by default");

				assertTrue(strLocClintHeader.contains("Status"));
				log4j.info("Status column is displayed by default");

			} catch (AssertionError Ae) {
				log4j.info("Default columns are NOT displayed on locate clients page");
				lStrReason = "Default columns are NOT displayed on locate clients page";
			}

		} catch (Exception e) {
			log4j.info("Default columns are NOT displayed on locate clients page");
			lStrReason = "Default columns are NOT displayed on locate clients page";
		}
		return lStrReason;
	}
	// end//vfyDefaultColumnsInlocateClntsPge//
	
	//start//vfyActiveChkboxChecked//
    /*******************************************************************************************
  	' Description  : Function to verify active checkbox is selected or not.
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 15-11-2013
  	' Author       : Manasa 
  	*******************************************************************************************/
	public String vfyActiveChkboxChecked(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		try {
			if(selenium.isChecked(propElementDetails.getProperty("LocateActiveChkBox"))){
			log4j.info("Active check box is checked by default");
			}else{
				selenium.click(propElementDetails.getProperty("LocateActiveChkBox"));
				try{
				assertTrue(selenium.isChecked(propElementDetails.getProperty("LocateActiveChkBox")));
				log4j.info("Active check box is checked");
				}catch(AssertionError Ae){
					log4j.info("Active check box is NOT checked");
					lStrReason="Active check box is NOT checked";
				}
			}
				
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "LocatePage.vfyActiveChkboxChecked failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//vfyActiveChkboxChecked//
	
	//start//clkOnEditBtnInDetaildClntInfoPge//
    /********************************************************************************************
  	' Description  : Function to click on the edit button in the detailed client information page 
  					 and verify Contact information page is displayed
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 15-11-2013
  	' Author       : Manasa 
  	*********************************************************************************************/
	public String clkOnEditBtnInDetaildClntInfoPge(Selenium selenium) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		WaitForElement waitForElements = new WaitForElement();
		try {
			selenium.focus(propElementDetails.getProperty("LocWindwEditBtn"));
			selenium.click(propElementDetails.getProperty("LocWindwEditBtn"));
			waitForElements.WaitForElements(selenium,"DemgrphicHeader","Contact Information");
			try{
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("DemgrphicHeader")));
				log4j.info("Contact Information page is displayed");
			}catch(AssertionError Ae){
				log4j.info("Contact Information page is NOT displayed");
				lStrReason="Contact Information page is NOT displayed";
			}
				
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "LocatePage.vfyActiveChkboxChecked failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//clkOnEditBtnInDetaildClntInfoPge//
	
	//start//clkOnEditBtnInDetaildClntInfoPge//
    /********************************************************************************************************************
  	' Description  : Function enter the first name and the last name in contact information page
  	' Precondition : N/A 
  	' Arguments    : selenium
  	' Returns      : String 
  	' Date         : 15-11-2013
  	' Author       : Manasa 
  	*********************************************************************************************************************/
	public String entrFrstAndLstNameInContInfoPge(Selenium selenium,String strFstName,String strLstName,boolean blnDone) throws Exception{
		String lStrReason = "";
						
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		WaitForElement waitForElements = new WaitForElement();
		try {
			
			selenium.type(propElementDetails.getProperty("ContInfoFstName"), strFstName);
			log4j.info("First name entered is: "+strFstName);
			selenium.type(propElementDetails.getProperty("ContInfoLstName"), strLstName);
			log4j.info("Last name entered is: "+strLstName);
			
			if(blnDone)
			{
				selenium.focus(propElementDetails.getProperty("ContinfoDone"));
				selenium.click(propElementDetails.getProperty("ContinfoDone"));
				waitForElements.WaitForElements(selenium, "IncidentModeBtn", "Incident Mode Button");
				try {
					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("IncidentModeBtn")));
					log4j.info("Navigation to 'Dashboard' Page is successfull.");
					
				} catch (AssertionError ae) {
					log4j.info(ae);
					log4j.info("Navigation to 'Dashboard' Page is NOT successfull.");
					lStrReason = lStrReason + "; " + "Navigation to Dashboard Page is NOT successfull.";
				}
			}
			
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "LocatePage.vfyActiveChkboxChecked failed to complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//clkOnEditBtnInDetaildClntInfoPge//
}