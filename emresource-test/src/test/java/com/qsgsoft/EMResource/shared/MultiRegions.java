package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

public class MultiRegions {

	static Logger log4j = Logger.getLogger("MultiRegion");
	public Properties propEnvDetails, propAutoItDetails;
	Properties propElementDetails;
	Properties pathProps;
	public String gstrTimeOut = "";
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();
	ReadData rdExcel;
		
	/********************************************************
	' Description: Navigation to create multi region Event
	' Arguments  : selenium
	' Returns    : String 
	' Date       : 12/06/2012
	' Author     : QSG 
	'--------------------------------------------------------
	' Modified Date: 
	' Modified By: 
	**********************************************************/

	public String navToCreateMultiRegionEvent(Selenium selenium) throws Exception
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
			selenium.click(propElementDetails.getProperty("CreateMultiRegnEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Create Multi-Region Event", selenium.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Create Event' screen is displayed. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Create Event' screen is  NOT displayed. ");
				lStrReason = lStrReason + "; " + "Create Event' screen is  NOT displayed. ";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Creation of Multi Region Event with file attach
	' Arguments  : selenium, pStrEventName, pStrEventDesc
	' Returns    : String 
	' Date       : 12/06/2012
	' Author     : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String createMultiRegionEvent(Selenium selenium,
			String pStrEventName, String pStrEventDesc, String strUploadPath)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		// Create an object to refer to the Autoit Properties file
		Paths_Properties objAP = new Paths_Properties();
		propAutoItDetails = objAP.ReadAutoit_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.type(propElementDetails.getProperty("Prop264"),
					pStrEventName);
			selenium.type(propElementDetails.getProperty("Prop265"),
					pStrEventDesc);
			String strAutoFilePath = propAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strAutoFileName = propAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");

			String strProcess = "";
			String strArgs[] = { strAutoFilePath, strUploadPath };
			// AutoIt
			Runtime.getRuntime().exec(strArgs);
			selenium.click(propElementDetails
					.getProperty("Event.CreateEVe.Browse"));
			Thread.sleep(5000);
			int intCnt = 0;
			do {
				GetProcessList objGPL = new GetProcessList();
				strProcess = objGPL.GetProcessName();
				intCnt++;
				Thread.sleep(500);
			} while (strProcess.contains(strAutoFileName) && intCnt < 120);

			selenium.click(propElementDetails.getProperty("Prop267"));
			selenium.click(propElementDetails.getProperty("Prop268"));
			selenium.click(propElementDetails.getProperty("Prop269"));
			selenium.waitForPageToLoad(gstrTimeOut);
			selenium.click(propElementDetails.getProperty("EventYesButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			selenium.click(propElementDetails.getProperty("EventDoneButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Event Management",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("''Event Management' screen is displayed. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Event Management' screen is NOT displayed. ");
				lStrReason = lStrReason + "; "
						+ "'Event Management' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

}
