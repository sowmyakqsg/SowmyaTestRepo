package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/*******************************************************************************
' Description :This class includes common functions of Reasons for status Reason
' Date		  :10-April-2012
' Author	  :QSG
'-------------------------------------------------------------------------------
' Modified Date                                                    Modified By
' <Date>                           	                                <Name>
'********************************************************************************/

public class StatusReason {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.StatusReason");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();

	ReadData rdExcel;
	
	String gstrTimeOut="";
	
	/***********************************************************************
	'Description	:Verify Status Reason list page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:10-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'10-April-2012                               <Name>
	************************************************************************/
	
	public String navStatusReasonList(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));

			selenium.click(propElementDetails
					.getProperty("SetUP.StatusResnLink"));

			selenium.waitForPageToLoad(gstrTimeOut);

			// "css=input[name='abbreviation']"

			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Status Reason List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status Reason List page is displayed");

				} catch (AssertionError Ae) {

					strErrorMsg = "Status Reason List page is NOT displayed"
							+ Ae;
					log4j.info("Status Reason List page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("navStatusReasonList function failed" + e);
			strErrorMsg = "navStatusReasonList function failed" + e;
		}
		return strErrorMsg;
	}
	/*******************************************************************************************
	' Description:  verify the  Status reason
	' Precondition: N/A 
	' Arguments: selenium, pStrReason
	' Returns: String 
	' Date: 13/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String vrfyStReason(Selenium selenium, String pStrReason) throws Exception
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
			try {
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"+pStrReason+"']"));
				log4j.info("'SR'"+pStrReason+ "is displayed in the 'Status Reason List' screen with appropriate values under each of the column headers. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'SR'"+pStrReason+ "is  NOT displayed in the 'Status Reason List' screen with appropriate values under each of the column headers. ");
				lStrReason = lStrReason + "; " + "	'SR' is  NOT displayed in the 'Status Reason List' screen with appropriate values under each of the column headers. ";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Varify fields of  status region page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 13/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String varFieldsOfSRegion(Selenium selenium) throws Exception
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
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop317")));
				log4j.info("''Create Status Reason' button is displayed. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Create Status Reason' button is  NOT displayed. ");
				lStrReason = lStrReason + "; " + "'Create Status Reason' button is  NOT displayed. ";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop318")));
				log4j.info("'Following column headers Action is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Following column headers  Action is NOT displayed");
				lStrReason = lStrReason + "; " + "Following column headers  Action is NOT displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop319")));
				log4j.info("'Following column headers Status Reason is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Following column headers Status Reason is NOT displayed");
				lStrReason = lStrReason + "; " + "Following column headers Status Reason is NOT displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop320")));
				log4j.info("'Following column headers Abbreviation is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Following column headers Abbreviation is NOT displayed");
				lStrReason = lStrReason + "; " + "Following column headers Abbreviation is NOT displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop321")));
				log4j.info("'Following column headers Description is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Following column headers Description is NOT displayed");
				lStrReason = lStrReason + "; " + "Following column headers Description is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/***********************************************************************
	'Description	:Verify Status Reason is created
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:10-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'10-April-2012                               <Name>
	************************************************************************/
	
	public String createStatusReasn(Selenium selenium,
			String strMandReasonName, String strDefn, String strAbbreviation,
			boolean blnOptn,boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

			
		try {
			
			
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			selenium.click(propElementDetails
					.getProperty("CreateStatusReasnLink"));

			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("", strErrorMsg);
				try {
					assertEquals("Create Status Reason", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Create Status Reason page is displayed");

					//Name
					if (strMandReasonName != "") {
						selenium.type(propElementDetails
								.getProperty("CreateStatusReasn.StatReasnName"),
								strMandReasonName);
					}

					//Definition
				
					if (strDefn != "") {
						selenium.type(propElementDetails
								.getProperty("CreateStatusReasn.Description"),
								strDefn);
					}
					
					//Abbreviation
					
					
					if (strAbbreviation != "") {
						selenium.type(propElementDetails
								.getProperty("CreateStatusReasn.Abbrevation"),
								strAbbreviation);
					}
					//Display reason in comment section
					
					if(blnOptn){
						if(selenium.isChecked(propElementDetails
								.getProperty("CreateStatusReasn.Display"))){
							
						}else{
							selenium.click(propElementDetails
									.getProperty("CreateStatusReasn.Display"));
						}
					}else{
						if(selenium.isChecked(propElementDetails
								.getProperty("CreateStatusReasn.Display"))){
							selenium.click(propElementDetails
									.getProperty("CreateStatusReasn.Display"));
						}else{
							
						}
					}
					
					if(blnSav){
						selenium.click(propElementDetails.getProperty("Save"));
						selenium.waitForPageToLoad(gstrTimeOut);
												
					}
				} catch (AssertionError Ae) {

					strErrorMsg = "Create Status Reason page is NOT displayed"
							+ Ae;
					log4j.info("Create Status Reason page is NOT displayed"
							+ Ae);
				}
				
			} catch (AssertionError Ae) {
				strErrorMsg=strErrorMsg+Ae;
				log4j.info(Ae);
			}
			
		} catch (Exception e) {
			log4j.info("createStatusReasn function failed" + e);
			strErrorMsg = "createStatusReasn function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Status Reason is edited
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:10-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'10-April-2012                               <Name>
	************************************************************************/
	
	public String editStatusReasn(Selenium selenium, String strMandReasonName,
			String strEditMandReasonName, String strDefn,
			String strAbbreviation, boolean blnOptn,boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		General objGeneral=new General();//object of class General
		
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium
					.click("//div[@id='mainContainer']/table[@class='displayTable "
										+ "striped border sortable']/tbody/tr/td[2][text()='"
							+ strMandReasonName + "']/parent::tr/td[1]/a");

			strErrorMsg = objGeneral.CheckForElements(selenium,
					propElementDetails
							.getProperty("CreateStatusReasn.StatReasnName"));

			try {
				assertEquals("", strErrorMsg);
				try {
					assertEquals("Edit Status Reason", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Edit Status Reason page is displayed");

					//Name
					if (strEditMandReasonName != "") {
						selenium.type(propElementDetails
								.getProperty("CreateStatusReasn.StatReasnName"),
								strEditMandReasonName);
					}
					
		
					//Definition
				
					if (strDefn != "") {
						selenium.type(propElementDetails
								.getProperty("CreateStatusReasn.Description"),
								strDefn);
					}
					
					//Abbreviation
					
					
					if (strAbbreviation != "") {
						selenium.type(propElementDetails
								.getProperty("CreateStatusReasn.Abbrevation"),
								strAbbreviation);
					}
					//Display reason in comment section
					
					if(blnOptn){
						if(selenium.isChecked(propElementDetails
								.getProperty("CreateStatusReasn.Display"))){
							
						}else{
							selenium.click(propElementDetails
									.getProperty("CreateStatusReasn.Display"));
						}
					}else{
						if(selenium.isChecked(propElementDetails
								.getProperty("CreateStatusReasn.Display"))){
							selenium.click(propElementDetails
									.getProperty("CreateStatusReasn.Display"));
						}else{
							
						}
					}
					if(blnSav){
						selenium.click(propElementDetails.getProperty("Save"));					
						selenium.waitForPageToLoad(gstrTimeOut);
						
					}
					
				} catch (AssertionError Ae) {

					strErrorMsg = "Create Status Reason page is NOT displayed"
							+ Ae;
					log4j.info("Create Status Reason page is NOT displayed"
							+ Ae);
				}
				
			} catch (AssertionError Ae) {
				strErrorMsg=strErrorMsg+Ae;
				log4j.info(Ae);
			}
			
		} catch (Exception e) {
			log4j.info("editStatusReasn function failed" + e);
			strErrorMsg = "editStatusReasn function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /********************************************************************************************************
	  'Description :Fetch Status Reason Value in Status Reasons page
	  'Precondition :None
	  'Arguments  :selenium,strStatReason
	  'Returns  :String
	  'Date    :02-July-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/
	public String fetchStatReasonValue(Selenium selenium,
			String strStatReason) throws Exception {
		String strStatValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			String strResValueArr[] = selenium
					.getAttribute(
							"//div[@id='mainContainer']"
									+ "/table/tbody/tr/td[2][text()='"
									+ strStatReason + "']/parent::"
									+ "tr/td[1]/a@href").split(
							"nextStepDetail=");
			strStatValue = strResValueArr[1];
			log4j.info("Status Reason Value is " + strStatValue);
		} catch (Exception e) {
			log4j.info("fetchStatReasonValue function failed" + e);
			strStatValue = "";
		}
		return strStatValue;
	}
	
	/***********************************************************************
	'Description	:Select status reason while creating status 
	'Precondition	:None
	'Arguments		:selenium,strReasonVal,blnSelect
	'Returns		:String
	'Date	 		:02-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String selectAndDeselectStatusReasInStatus(Selenium selenium,String strReasonVal,boolean blnSelect) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
				
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			if(blnSelect){
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='status_reason_id'][value='"+strReasonVal+"']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if(selenium.isChecked("css=input[name='status_reason_id'][value='"+strReasonVal+"']")==false)
					selenium.click("css=input[name='status_reason_id'][value='"+strReasonVal+"']");
				
			}else{
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='status_reason_id'][value='"+strReasonVal+"']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				if(selenium.isChecked("css=input[name='status_reason_id'][value='"+strReasonVal+"']"))
					selenium.click("css=input[name='status_reason_id'][value='"+strReasonVal+"']");
			}
		
			
		} catch (Exception e) {
			log4j.info("selectAndDeselectStatusReasInStatus function failed" + e);
			strErrorMsg = "selectAndDeselectStatusReasInStatus function failed" + e;
		}
		return strErrorMsg;
	}
}