package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.ReadData;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class ReportsPage{
	
	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	static Logger log4j = Logger.getLogger(LoginPage.class);

	ReadData rdExcel;


     //start//navToReportsPge//
	/*******************************************************************************************
	' Description  : Function to navigate to reports page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 11-11-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String navToReportsPge(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to invoke the common function for Element Wait.

		// Create an object to refer to the Element ID Properties file
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// WaitForElement waitForElements = new WaitForElement();
		selenium.setTimeout(gstrTimeOut);

		try {
			selenium.click(propElementDetails.getProperty("Rep_Hme_Reports"));
			selenium.waitForPageToLoad("90000");

			try {
				assertEquals("EMTrack ~Select Report", selenium.getTitle());
				log4j.info("EMTrack ~Select Report page is displayed");
			} catch (AssertionError Ae) {
				log4j.info("EMTrack ~Select Report page is NOT displayed");
				lStrReason = "EMTrack ~Select Report page is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "navToReportsPge.Login failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//navToReportsPge//
	//start//navToIncidentDetailRepPge//
	/*******************************************************************************************
	' Description  : Function to navigate to incident detail report page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 11-11-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String navToIncidentDetailRepPge(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to invoke the common function for Element Wait.

		// Create an object to refer to the Element ID Properties file
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// WaitForElement waitForElements = new WaitForElement();
		selenium.setTimeout(gstrTimeOut);

		try {
			selenium.click(propElementDetails
					.getProperty("Rep_IncDetailRepLink"));
			selenium.waitForPageToLoad("90000");

			try {
				assertEquals("EMTrack ~Set Report Parameters",
						selenium.getTitle());
				log4j.info("Incident Detail report is displayed ");
			} catch (AssertionError Ae) {
				log4j.info("Incident Detail report page is NOT displayed ");
				lStrReason = "Incident Detail report page is NOT displayed ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "navToReportsPge.Login failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//navToIncidentDetailRepPge//
	
	//start//navToIncidentDetailRepPge//
		/*******************************************************************************************
		' Description  : Function to generate the incident detail report page
		' Precondition : N/A 
		' Arguments    : selenium,strIncName,strSaveFileName
		' Returns      : String 
		' Date         : 11-11-2013
		' Author       : Manasa 
		*******************************************************************************************/
		public String genereteInciDetailRep(Selenium selenium,String strIncName,String strSaveFileName) throws Exception {
			String lStrReason = "";

			// Create an object to invoke the common function for Element Wait.

			// Create an object to refer to the Element ID Properties file
			propEnvDetails = objReadEnvironment.ReadEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			// WaitForElement waitForElements = new WaitForElement();
			selenium.setTimeout(gstrTimeOut);

			try {
				selenium.click(propElementDetails.getProperty("Hme_Chechbox_All"));
				selenium.click(propElementDetails.getProperty("Loc_Searchbtn"));
							
				selenium.click("//table[@id='incidentTable']//td[text()='"+strIncName+"']//parent::tr//input[@type='radio']");
				selenium.click(propElementDetails.getProperty("Rep_Run_Report"));
				
				
				
				
			} catch (Exception e) {
				log4j.info(e);
				lStrReason = "navToReportsPge.Login failed to complete due to "
						+ lStrReason + "; " + e.toString();
			}
			return lStrReason;
		}
		// end//navToIncidentDetailRepPge//
}
