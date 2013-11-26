package com.qsgsoft.EMResource.shared;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

public class MobileView {
	
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.shared.MobileView");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;

	public String navToEventDetailInMob(Selenium selenium,String strEventName,String strEveImgSrc,String strResource) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails=objelementProp.ElementId_FilePath();
		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
		try{
			selenium.selectWindow("");
			//click on Mobile View link
			selenium.click(propElementDetails.getProperty("Mobilie_Link"));
		
			boolean blnFound=false;
			int intCnt=0;
			while(blnFound==false&&intCnt<60){
				try{			
					selenium.selectWindow("Main Menu");
					selenium.getText(propElementDetails
							.getProperty("Header.Text"));
					blnFound=true;
				}catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}
			try{
				assertEquals("Main Menu", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				assertTrue(selenium.getText("//a[text()='Events']/following-sibling::span").matches("\\(\\d+\\)"));
				log4j.info("'Main Menu' screen is displayed");
				//click on Events
				selenium.click("link=Events");
				selenium.waitForPageToLoad(gstrTimeOut);
				try{
					assertEquals("Event List", selenium.getTitle());
					log4j.info("'Event List' screen is displayed.");
				
					try{
						assertTrue(selenium.isElementPresent("css=a:contains('"+strEventName+"')"));
						
						assertEquals(strEveImgSrc,selenium.getAttribute("//td[contains(text(),'"+strEventName+"')]/preceding-sibling::td/img@src"));
						log4j.info("Event 'EVE' is listed with the selected icon.");
						//Click on the particular event name
						selenium.click("//td[contains(text(),'"+strEventName+"')]/preceding-sibling::td/img");
						selenium.waitForPageToLoad(gstrTimeOut);
						
						try{
							assertEquals("Event Detail", selenium.getTitle());
							log4j.info("'Event Detail' screen is displayed.");
							//click on Resources
							selenium.click(propElementDetails
									.getProperty("SetUP.ResourcesLink"));
							selenium.waitForPageToLoad(gstrTimeOut);
							
							try{
								assertEquals("Event Resources", selenium.getTitle());
								assertTrue(selenium.isElementPresent("link="+strResource));
								log4j.info(strResource+" is listed in the 'Event Resources' screen.");
							}catch(AssertionError ae){
								log4j.info(strResource+" is NOT listed in the 'Event Resources' screen.");
								strReason=strResource+" is NOT listed in the 'Event Resources' screen.";
							}
							
						}catch(AssertionError ae){
							log4j.info("'Event Detail' screen is NOT displayed.");
							strReason="'Event Detail' screen is NOT displayed.";
						}					
						
					}catch(AssertionError ae){
						log4j.info("Event 'EVE' is NOT listed with the selected icon.");
						strReason="Event 'EVE' is NOT listed with the selected icon.";
					}
					
				}catch(AssertionError ae){
					log4j.info("'Event List' screen is NOT displayed.");
					strReason="'Event List' screen is NOT displayed.";
				}
				
			}catch(AssertionError ae){
				log4j.info("'Main Menu' screen is NOT displayed");
				strReason="'Main Menu' screen is NOT displayed";
			}
				
	
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		
		return strReason;
	}
	 /***************************************************************
	  'Description :Lauch mobile View and navigate to views
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :strReason
	  'Date    :04-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	public String navToViewsInMobileView(Selenium selenium) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails=objelementProp.ElementId_FilePath();
		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
		try{
			selenium.selectWindow("");
			//click on Mobile View link
			selenium.click(propElementDetails.getProperty("Mobilie_Link"));
		
			boolean blnFound=false;
			int intCnt=0;
			while(blnFound==false&&intCnt<60){
				try{			
					selenium.selectWindow("Main Menu");
					selenium.getText(propElementDetails
							.getProperty("Header.Text"));
					blnFound=true;
				}catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}
			if(blnFound){
				try{
					assertEquals("Main Menu", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					log4j.info("'Main Menu' screen is displayed");
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("MobileView.ViewLink")));
					log4j.info("'Views' option is displayed in the 'Main Menu' screen");
					//click on Views
					selenium.click(propElementDetails
							.getProperty("MobileView.ViewLink"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					try{
						assertEquals("Views", selenium.getText(propElementDetails
								.getProperty("Header.Text")));
						log4j.info("'Views' screen is displayed");
					
					}catch(AssertionError ae){
						log4j.info("'Views' screen is NOT displayed");
						strReason="'Views' screen is NOT displayed";
					}
					
				}catch(AssertionError ae){
					log4j.info("'Main Menu' screen is NOT displayed");
					strReason="'Main Menu' screen is NOT displayed";
				}
				
			}else{
				log4j.info("Mobile View screen is NOT displayed");
				strReason="Mobile View screen is NOT displayed";
			}
	
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		
		return strReason;
	}
	 /***************************************************************
	  'Description :navigate to particular user view in mobile
	  'Precondition :None
	  'Arguments  :selenium,strViewName
	  'Returns  :strReason
	  'Date    :04-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	public String navToUserViewsInMob(Selenium selenium,String strViewName) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails=objelementProp.ElementId_FilePath();
		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
		try{
			//click on View link
			selenium.click("link="+strViewName);
			selenium.waitForPageToLoad(gstrTimeOut);
			try{
				assertEquals(strViewName, selenium.getTitle());
				log4j.info(strViewName+" screen is displayed.");
			}catch(AssertionError ae){
				log4j.info(strViewName+" screen is NOT displayed.");
				strReason=strViewName+" screen is NOT displayed.";
			}
			
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		
		return strReason;
	}
	public String checkResouceDetail(Selenium selenium,String strResource,String strEveSection,String strStatTypeArr[]) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails=objelementProp.ElementId_FilePath();
		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		try{
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");
			//click on Resource link
			selenium.click("//td[contains(text(),'"+strResource+"')]/preceding-sibling::td/img");
			selenium.waitForPageToLoad(gstrTimeOut);
			try{
				assertEquals("Resource Detail", selenium.getTitle());
				log4j.info("'Resource Detail' screen is displayed.");
				try{
					assertTrue(selenium.isElementPresent("//div/h3[text()='"+strEveSection+"']"));
					
					for(int i=0;i<strStatTypeArr.length;i++){
						assertTrue(selenium.isElementPresent("//div/h3/following-sibling::table[@class='topicListItem']/tbody/tr/td[contains(text(),'"+strStatTypeArr[i]+"')]"));
					}
					log4j.info("All the Status types are displayed correctly under section "+strEveSection);
				}catch(AssertionError ae){
					log4j.info("All the Status types are NOT displayed correctly under section "+strEveSection);
					strReason="All the Status types are NOT displayed correctly under section "+strEveSection;
				}
			}catch(AssertionError ae){
				log4j.info("'Resource Detail' screen is NOT displayed.");
				strReason="'Resource Detail' screen is NOT displayed.";
			}
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		return strReason;
	}
	
	public String checkInEventTimes(Selenium selenium,String strEventName,String strEveStDate,String strEveEndDate) throws Exception{
		String strReason="";
		Date_Time_settings dts = new Date_Time_settings();	
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails=objelementProp.ElementId_FilePath();
		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		try{
			selenium.selectWindow("");
			//click on Mobile View link
			selenium.click(propElementDetails.getProperty("Mobilie_Link"));
		
			boolean blnFound=false;
			int intCnt=0;
			while(blnFound==false&&intCnt<60){
				try{			
					selenium.selectWindow("Main Menu");
					selenium.getText(propElementDetails
							.getProperty("Header.Text"));
					blnFound=true;
				}catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}
			try{
				assertEquals("Main Menu", selenium.getText("css=h1"));
				assertTrue(selenium.getText("//a[text()='Events']/following-sibling::span").matches("\\(\\d+\\)"));
				log4j.info("'Main Menu' screen is displayed");
				//click on Events
				selenium.click("link=Events");
				selenium.waitForPageToLoad(gstrTimeOut);
				//Click on the particular event name
				selenium.click("link="+strEventName);
				selenium.waitForPageToLoad(gstrTimeOut);
				//click on Times
				selenium.click(propElementDetails.getProperty("Mobilie_TIMESLink"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				String[] strTime=strEveStDate.split(" ");
				String strAddedStDtTime=dts.addTimetoExisting(strTime[1], 1, "hh:mm");
				strTime[0]=strTime[0].replace("-", " ");
				strAddedStDtTime=strTime[0]+" "+strAddedStDtTime;
				
				String strAddedEdDtTime="";
				if(strEveEndDate.equals("never")==false){
					String[] strTime1=strEveEndDate.split(" ");
					strAddedEdDtTime=dts.addTimetoExisting(strTime1[1], 1, "hh:mm");
					strTime1[0]=strTime1[0].replace("-", " ");
					strAddedEdDtTime=strTime1[0]+" "+strAddedEdDtTime;
					
				}
				strEveEndDate=strEveEndDate.replace("-", " ");
				strEveStDate=strEveStDate.replace("-", " ");
				try{
					assertEquals("Event Times", selenium.getTitle());
					assertTrue(selenium.isTextPresent("Scheduled End: "+strEveEndDate)||selenium.isTextPresent("Scheduled End: "+strAddedEdDtTime));
					assertTrue(selenium.isTextPresent("Event Started: "+strEveStDate)||selenium.isTextPresent("Event Started: "+strAddedStDtTime));
					
					log4j.info("'Event Times' screen is displayed with appropriate data for 'Event Started' and 'Scheduled End' controls.");
				}catch(AssertionError ae){
					log4j.info("'Event Times' screen is NOT displayed with appropriate data for 'Event Started' and 'Scheduled End' controls.");
					strReason="'Event Times' screen is NOT displayed with appropriate data for 'Event Started' and 'Scheduled End' controls.";
				}
			}catch(AssertionError ae){
				log4j.info("'Main Menu' screen is NOT displayed");
				strReason="'Main Menu' screen is NOT displayed";
			}
				
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		return strReason;
	}
  /***************************************************************
  'Description :navigate to custom view in mobile
  'Arguments   :selenium
  'Returns     :strReason
  'Date        :04-June-2012
  'Author      :QSG
  '---------------------------------------------------------------
  'Modified Date                                    Modified By
  '<Date>                                             <Name>
  ***************************************************************/
	public String navToCustomPageInMobileView(Selenium selenium)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("View.CustomLink")));
				log4j.info("Custom link is displayed in the 'Views' screen.");
				// click on View link
				selenium.click(propElementDetails.getProperty("View.CustomLink"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Custom", selenium.getTitle());
					log4j.info("Custom screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("Custom screen is displayed.");
					strReason = "Custom screen is displayed.";
				}
				
			} catch (AssertionError ae) {
				log4j.info("Custom link is NOT displayed in the 'Views' screen.");
				strReason = "Custom link is NOT displayed in the 'Views' screen.";
			}
			

		} catch (Exception e) {
			log4j.info("navToCustomPageInMobileView function failed" + e);
			strReason = "navToCustomPageInMobileView function failed" + e;
		}
		
		return strReason;
	}
	  /***************************************************************
	  'Description :navigate to custom view in mobile
	  'Arguments   :selenium
	  'Returns     :strReason
	  'Date        :04-June-2012
	  'Author      :QSG
	  '---------------------------------------------------------------
	  'Modified Date                                    Modified By
	  '<Date>                                             <Name>
	  ***************************************************************/
	public String chkResInCustomPageInMobileView(Selenium selenium,String[] strResource,boolean blnRes)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			if(blnRes){
				int intCol = 0;
				for (intCol = 0; intCol < strResource.length; intCol++) {
			try {
				assertTrue(selenium.isElementPresent("//div[@id='pageMenu']/ul/li/a/table/tbody/tr" +
						"/td[text()='"+strResource[intCol]+"']"));
				log4j.info("Resources "+strResource[intCol]+" is displayed in'Custom' screen. ");
			} catch (AssertionError ae) {
				log4j.info("Resources "+strResource[intCol]+" is NOT displayed in'Custom' screen. ");
				strReason = "Resources "+strResource[intCol]+" is NOT displayed in'Custom' screen.  ";
			}
				}
			}else{
				int intCol = 0;
				for (intCol = 0; intCol < strResource.length; intCol++) {
				try {
					assertFalse(selenium.isElementPresent("//div[@id='pageMenu']/ul/li/a/table/tbody/tr" +
							"/td[text()='"+strResource[intCol]+"']"));
					log4j.info("Resources "+strResource[intCol]+" is NOT displayed in'Custom' screen.  ");
				} catch (AssertionError ae) {
					log4j.info("Resources "+strResource[intCol]+" is displayed in'Custom' screen.  ");
					strReason = "Resources "+strResource[intCol]+" is displayed in'Custom' screen.  ";
				}
				}
			}
			
		} catch (Exception e) {
			log4j.info("chkResInCustomPageInMobileView function failed" + e);
			strReason = "chkResInCustomPageInMobileView function failed" + e;
		}
		
		return strReason;
	}
	/***************************************************************
	  'Description :navigate to custom view in mobile
	  'Arguments   :selenium
	  'Returns     :strReason
	  'Date        :04-June-2012
	  'Author      :QSG
	  '---------------------------------------------------------------
	  'Modified Date                                    Modified By
	  '<Date>                                             <Name>
	  ***************************************************************/
	public String chkSTInCustomPageInMobileView(Selenium selenium,String strResource,String strSection,
			String strStatTypeArr[],boolean blnback) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails=objelementProp.ElementId_FilePath();
		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		try{
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");
			//click on Resource link
			selenium.click("//td[contains(text(),'"+strResource+"')]/preceding-sibling::td/img");
			selenium.waitForPageToLoad(gstrTimeOut);
			try{
				assertEquals("Resource Detail", selenium.getTitle());
				log4j.info("'Resource Detail' screen is displayed.");
				for(int i=0;i<strStatTypeArr.length;i++){
				try{
					assertTrue(selenium.isElementPresent("//div/h3[text()='"+strSection+"']"));					
					assertTrue(selenium.isElementPresent("//div/h3/following-sibling::table[@class='topicListItem']/tbody/tr/td[contains(text(),'"+strStatTypeArr[i]+"')]"));
					log4j.info(strStatTypeArr[i]+" is displayed correctly under section "+strSection);
									
				}catch(AssertionError ae){
					log4j.info(strStatTypeArr[i]+" is NOT displayed correctly under section "+strSection);
					strReason=strStatTypeArr[i]+" is NOT displayed correctly under section "+strSection;
				}
				}
			}catch(AssertionError ae){
				log4j.info("'Resource Detail' screen is NOT displayed.");
				strReason="'Resource Detail' screen is NOT displayed.";
			}
			if(blnback){
			// click on back
			selenium.click(propElementDetails.getProperty("Mobilie_BACK_LINK"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Custom", selenium.getTitle());
				log4j.info("Custom screen is displayed.");
			} catch (AssertionError ae) {
				log4j.info("Custom screen is NOT displayed.");
				strReason = "Custom screen is NOT displayed.";
			}
			}
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		return strReason;
	}
	/***************************************************************
	  'Description :navigate to custom view in mobile
	  'Arguments   :selenium
	  'Returns     :strReason
	  'Date        :04-June-2012
	  'Author      :QSG
	  '---------------------------------------------------------------
	  'Modified Date                                    Modified By
	  '<Date>                                             <Name>
	  ***************************************************************/
	
	public String navSelRgnPageAndverRgn(Selenium selenium,
			String strRegion[]) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails=objelementProp.ElementId_FilePath();		
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		try {

		try {
			assertEquals("Main Menu", selenium.getText(propElementDetails
					.getProperty("Header.Text")));
			log4j.info("'Main Menu' screen 'is displayed.");
			selenium.click(propElementDetails
					.getProperty("Mobilie_SelectRegionLINK"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Select Region",selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Pick Region' screen is displayed .");
				for (int i = 0; i < strRegion.length; i++) {
					try {
						assertTrue(selenium.isElementPresent("link="
								+ strRegion[i]));
						log4j.info("'Pick Region' screen is displayed with "
								+ strRegion[i]);

					} catch (AssertionError ae) {
						log4j.info("'Pick Region' screen is NOT displayed with "
								+ strRegion[i]);
						strReason = "'Pick Region' screen is NOT displayed with "
								+ strRegion[i];
					}
				}

			} catch (AssertionError ae) {
				log4j.info("'Pick Region' screen is NOT displayed .");
				strReason = "'Pick Region' screen is NOT displayed with .";
			}

			} catch (AssertionError ae) {
				log4j.info("'Main Menu' screen 'is displayed.");
				strReason = "'Main Menu' screen 'is displayed.";
			}		
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		return strReason;
	}
	/*******************************************************************************************
	' Description:check resource icon and appropriate color is displayed
	' Precondition: N/A 
	' Arguments: selenium, strTitle,strIconSrc
	' Returns: String 
	' Date: 18-09-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String checkResIconAndColorInMob(Selenium selenium,
			String strResource, String strIconSrc) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium.isElementPresent("//td[text()='"
						+ strResource + "']/preceding-sibling::td/img"));
				String str = selenium.getAttribute("//td[text()='"
						+ strResource + "']/preceding-sibling::td/img@src");
				log4j.info("Three : "+str);
				assertEquals(strIconSrc, selenium.getAttribute("//td[text()='"
						+ strResource + "']/preceding-sibling::td/img@src"));
				log4j
						.info("Resource "
								+ strResource
								+ " is displayed with colored ball (i.e. green) on view screen.");
			} catch (AssertionError ae) {
				log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed with colored ball (i.e. green) on view screen.");
				strReason = "Resource "
						+ strResource
						+ " is NOT displayed with colored ball (i.e. green) on view screen.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	/***************************************************************
	'Description	:verify resource details in view resource detail page
					section.
	'Precondition	:None
	'Arguments		:selenium,strResourceData
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/

	public String checkResouceDetailInMobileview(Selenium selenium,
			String strResource, String strResouceData[][]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// click on Resource link
			selenium.click("//td[contains(text(),'" + strResource
					+ "')]/preceding-sibling::td/img");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Resource Detail", selenium.getTitle());
				log4j.info("'Resource Detail' screen is displayed.");

				for (int intRec = 0; intRec < strResouceData.length; intRec++) {
					if (strResouceData[intRec][1].compareTo("") != 0) {
						try {
							assertTrue(selenium
									.isElementPresent("//table[@class='topicListItem']/tbody/tr/td[text()='"
											+ strResouceData[intRec][0]
											+ "']/following-sibling::td[text()='"
											+ strResouceData[intRec][1] + "']"));
							log4j.info(strResouceData[intRec][0]
									+ strResouceData[intRec][1]
									+ " is displayed in View Resource detail of Mobile View screen");
						} catch (AssertionError ae) {
							log4j.info(strResouceData[intRec][0]
									+ strResouceData[intRec][1]
									+ " is NOT displayed in View Resource detail of Mobile View screen");
							strReason = strReason
									+ " "
									+ strResouceData[intRec][0]
									+ strResouceData[intRec][1]
									+ " is NOT displayed in View Resource detail of Mobile View screen";
						}
					}
				}
			} catch (AssertionError ae) {
				log4j.info("'Resource Detail' screen is NOT displayed.");
				strReason = "'Resource Detail' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:verify resource details in view resource detail page
					section.
	'Precondition	:None
	'Arguments		:selenium,strResourceData
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/
	public String checkResouceDetailFalse(Selenium selenium,
			String strResource, String strSection, String strStatTypeArr[])
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			// click on Resource link
			selenium.click("//td[contains(text(),'" + strResource
					+ "')]/preceding-sibling::td/img");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Resource Detail", selenium.getTitle());
				log4j.info("'Resource Detail' screen is displayed.");
				try {
					assertTrue(selenium.isElementPresent("//div/h3[text()='"
							+ strSection + "']"));

					for (int i = 0; i < strStatTypeArr.length; i++) {
						assertFalse(selenium
								.isElementPresent("//div/h3/following-sibling::table"
										+ "[@class='topicListItem']/tbody/tr/td[contains(text(),'"
										+ strStatTypeArr[i] + "')]"));
						log4j.info("The Status types " + strStatTypeArr[i]
								+ " is NOT displayed correctly under section "
								+ strSection);
					}
				} catch (AssertionError ae) {
					log4j.info("The Status type is displayed correctly under section "
							+ strSection);
					strReason = "The Status types is displayed correctly under section "
							+ strSection;
				}
			} catch (AssertionError ae) {
				log4j.info("'Resource Detail' screen is NOT displayed.");
				strReason = "'Resource Detail' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
}
