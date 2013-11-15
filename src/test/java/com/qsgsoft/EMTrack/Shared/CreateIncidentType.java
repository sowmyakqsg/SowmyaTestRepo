package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.ReadData;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class CreateIncidentType{
	
	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	static Logger log4j = Logger.getLogger(LoginPage.class);

	ReadData rdExcel;

	//start//ClickOnPreferencesLink//
	/*******************************************************************************************
	' Description : Function to click on preferences link
	' Precondition: N/A 
	' Arguments   : Selenium
	' Returns     : String 
	' Date        : 29-10-2013
	' Author      : Manasa
	*******************************************************************************************/
	public String clickOnPreferencesLink(Selenium selenium) throws Exception {
		String lStrReason = "";
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// Create an object to invoke the common function for Element Wait.
		try {
			selenium.focus(propElementDetails.getProperty("PrefLink"));
			selenium.click(propElementDetails.getProperty("PrefLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals(selenium.getText(propElementDetails
						.getProperty("PrefTitle")), "Preferences");
				log4j.info("Preferences page is displayed");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				lStrReason = "Preconditions.ClickOnPreferencesLink failed to complete due to "
						+ lStrReason + "; " + Ae.toString();
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "Preconditions.ClickOnPreferencesLink failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	// end//ClickOnPreferencesLink//
	
	//start//navToIncidentType//
	/*******************************************************************************************
	' Description  : Function to navigate to the Incident type page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa
	 *******************************************************************************************/
	public String navToIncidentType(Selenium selenium) throws Exception
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
			selenium.focus(propElementDetails.getProperty("IncidentTypeLink"));
			selenium.click(propElementDetails.getProperty("IncidentTypeLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateIncidentTypeBtn")));
				log4j.info("Create Incident Type button is displayed");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				lStrReason = "CreateIncidentType.navToIncidentType failed to complete due to "
						+ lStrReason + "; " + Ae.toString();
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "CreateIncidentType.navToIncidentType failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//navToIncidentType//
	
	//start//navToIncidentType//
	/*******************************************************************************************
	' Description  : Function to click on Create Incident type button
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa
	 *******************************************************************************************/

	public String clkOnCreateIncidentType(Selenium selenium,String strWindowName) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		WaitForElement objWaitForElement=new WaitForElement();
		try{
			selenium.focus(propElementDetails.getProperty("CreateIncidentTypeBtn"));
			selenium.click(propElementDetails.getProperty("CreateIncidentTypeBtn"));
			objWaitForElement.WaitForElementsWithoutProperty(selenium, "//div[@class='x-window x-layer x-window-default x-closable x-window-closable x-window-default-closable x-border-box']/div/div/div/div/div/span[contains(text(),'"+strWindowName+"')]", "Create Incident Type");
			
			try {
				assertEquals(selenium.getText(propElementDetails.getProperty("CreateWindow")),"Create Incident Type");
				log4j.info("Create Incident Type page is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Create Incident Type page is NOT displayed");
				lStrReason = "CreateIncidentType.clkOnCreateIncidentType failed to complete due to "
						+ lStrReason + "; " + Ae.toString();
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "CreateIncidentType.navToIncidentType failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//clkOnCreateIncidentType//
	
	//start//createIncidentType//
	/*******************************************************************************************
	' Description  : Function to create new incident type
	' Precondition : N/A 
	' Arguments    : selenium, pStr
	' Returns      : String 
	' Date         : 29-10-2013
	' Author       : Manasa 
	*******************************************************************************************/

	public String createIncidentType(Selenium selenium, String pStrInciTypeName,String pStrInciTypeDesc,String pStrInciSiteName,String pStrInciAddress) throws Exception
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
			selenium.type(propElementDetails.getProperty("IncidentTypeName"), pStrInciTypeName);
			selenium.type(propElementDetails.getProperty("IncidentTypeDesc"), pStrInciTypeDesc);
			
			selenium.click(propElementDetails.getProperty("AddSite"));
			
			selenium.type(propElementDetails.getProperty("IncidentTypeSite"), pStrInciSiteName);
			selenium.click(propElementDetails.getProperty("Prop4116"));
			selenium.type(propElementDetails.getProperty("IncidentTypeAddress"), pStrInciAddress);
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "CreateIncidentType.createIncidentType failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//createIncidentType//
}