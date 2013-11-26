package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class WaitForElement  {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.WaitForElement");
	public int intCount=0;
	public String strCount=""; 
	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;
	public String gstrTimeOut = "";
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();
	ReadData rdExcel;
	
	
	public String WaitForElements(Selenium selenium, String strElementID,
			String strElemName) throws Exception {
		String strReason = "";

		strCount = propEnvDetails.getProperty("WaitForElement");
		intCount = Integer.parseInt(strCount);

		try {

			int intSec = 0;
			while (selenium.isElementPresent(propElementDetails.getProperty(""
					+ strElementID + "")) == false
					&& intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}

			intSec = 0;
			while (selenium.isVisible(propElementDetails.getProperty(""
					+ strElementID + "")) == false
					&& intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}

		return strReason;
	}

	public String WaitForElementsWithoutProperty(Selenium selenium, String strElementID, String strElemName) throws Exception{
		String strReason="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		strCount=propEnvDetails.getProperty("WaitForElement");
		intCount=Integer.parseInt(strCount);
		
		try{
			
			int intSec = 0;
			while (selenium.isElementPresent(strElementID) == false
					&& intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}
			
			intSec = 0;
			while (selenium.isVisible(strElementID) == false
					&& intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}		
			
		}catch(Exception e){
			log4j.info(e);
			strReason="Exception Occured: "+e.toString();
		}
		
		return strReason;
	}
	
	public String WaitForElementEnable(Selenium selenium, String strElementID, String strElemName) throws Exception{
		String strReason="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		strCount=propEnvDetails.getProperty("WaitForElement");
		intCount=Integer.parseInt(strCount);
		
		try{
			
			int intSec=0;
			while (selenium.isEditable(propElementDetails.getProperty(""
					+ strElementID + "")) == false
					&& intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}
								
			if (intSec >= intCount
					&& selenium.isEditable(propElementDetails.getProperty(""
							+ strElementID + "")) == false) {
				 log4j.info("Field"+strElemName +"is not enabled even after waiting for "+intCount);
				 strReason="Field"+strElemName +"is not enabled even after waiting for "+intCount;
			}
			
		}catch(Exception e){
			log4j.info(e);
			strReason="Exception Occured: "+e.toString();
		}
		
		return strReason;
	}
	
	public String WaitForElementEnable_WithOutProperty(Selenium selenium, String strElementID, String strElemName) throws Exception{
		String strReason="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		strCount=propEnvDetails.getProperty("WaitForElement");
		intCount=Integer.parseInt(strCount);
		
		try{
			
			int intSec=0;
			while (selenium.isEditable(strElementID) == false
					&& intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}
								
			if (intSec >= intCount
					&& selenium.isEditable(strElementID) == false) {
				 log4j.info("Field"+strElemName +"is not enabled even after waiting for "+intCount);
				 strReason="Field"+strElemName +"is not enabled even after waiting for "+intCount;
			}
			
		}catch(Exception e){
			log4j.info(e);
			strReason="Exception Occured: "+e.toString();
		}
		
		return strReason;
	}
	
	public String WaitForTextPresent(Selenium selenium, String strText) throws Exception{
		String strReason="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		strCount=propEnvDetails.getProperty("WaitForElement");
		intCount=Integer.parseInt(strCount);
		
		try{
			
			int intSec = 0;
			while (selenium.isTextPresent(strText) == false && intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}
			intSec=0;
				
			if (intSec >= intCount && selenium.isTextPresent(strText) == false) {
				 log4j.info("Text"+strText +"is not present even after waiting for "+intCount);
				 strReason="Text"+strText +"is not present even after waiting for "+intCount;
			}
			
		}catch(Exception e){
			log4j.info(e);
			strReason="Exception Occured: "+e.toString();
		}
		
		return strReason;
	}
	
	public String WaitForElementPresent_WithoutProperty(Selenium selenium, String strElementID, String strElemName) throws Exception{
		String strReason="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		strCount=propEnvDetails.getProperty("WaitForElement");
		intCount=Integer.parseInt(strCount);
		
		try{			
			int intSec = 0;
			while (selenium.isElementPresent(strElementID) == false
					&& intSec < 120) {
				Thread.sleep(1000);
				intSec++;
			}			
			
		}catch(Exception e){
			log4j.info(e);
			strReason="Exception Occured: "+e.toString();
		}
		
		return strReason;
	}
	
	public String WaitForPageToLoad(Selenium selenium) throws Exception{
		String strReason="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		strCount=propEnvDetails.getProperty("WaitForElement");
		intCount=Integer.parseInt(strCount);
		
		try{			
			int intCnt=0;
			Thread.sleep(2000);
			while(selenium.isElementPresent("//div[@id='WaitMessage']") && intCnt<120){
				Thread.sleep(1000);
				intCnt++;
			}	
			
			intCnt=0;
			Thread.sleep(1000);
			while(selenium.isElementPresent("//div[@id='WaitMessage']") && intCnt<120){
				Thread.sleep(1000);
				intCnt++;
			}	
			
		}catch(Exception e){
			log4j.info(e);
			strReason="Exception Occured: "+e.toString();
		}
		
		return strReason;
	}
	
	public String WaitForPageToLoad_WithMessage(Selenium selenium,String strMessage) throws Exception{
		String strReason="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		strCount=propEnvDetails.getProperty("WaitForElement");
		intCount=Integer.parseInt(strCount);
		
		try{			
			int intCnt=0;
			while(selenium.isElementPresent("//div[@id='WaitMessage']/[text()='"+strMessage+"']") && intCnt<120){
				Thread.sleep(1000);
				intCnt++;
			}	
			
		}catch(Exception e){
			log4j.info(e);
			strReason="Exception Occured: "+e.toString();
		}
		
		return strReason;
	}
}
