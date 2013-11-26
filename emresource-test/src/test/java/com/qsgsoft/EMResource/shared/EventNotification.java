package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;


/**
'*************************************************************
' Description: The class Event Notification contains the common]
'			   functions acknowledge web notification and Select
'			   notifications for user

' Precondition:
' Functions: ackWebNotification(), selectEventNofifForUser()
' Date: 19-Apr-2012
' Author:QSG
'---------------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*************************************************************
*/

public class EventNotification {

	static Logger log4j = Logger
	.getLogger("com.qsgsoft.EMResource.shared.EventSetup");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;

	/**
	'*************************************************************
	' Description: Wait and check for web notification, acknowledge the notification
	' Precondition: 				
	' Arguments: selenium, strEveName, strEveDesc, strEveDateTime
	' Returns:strReason
	' Date: 19-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	
	public String ackWebNotification(Selenium selenium, String strHicsName,
			String strHicsDesc, String strEveDateTime) {
		String strReason = "";
		Date_Time_settings dts = new Date_Time_settings();
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Wait till acknowledgement pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				// Wait for acknowledgement
				intCnt = 0;
				while (selenium.isElementPresent("css=dt.othr") == false
						&& intCnt < 30) {
					Thread.sleep(1000);
					intCnt++;
				}
				String[] strTime = strEveDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");
				
				log4j.info(strAddedDtTime);
				
				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				// check the event name, description and start time are displayed correctly
				
				try{
					assertEquals(strHicsName, selenium.getText("css=dt.othr"));
					assertEquals(strHicsDesc, selenium.getText("css=dd.desc"));
					assertTrue(selenium.isElementPresent("css=dd.ts:contains('"+strEveDateTime+"')")||
							selenium.isElementPresent("css=dd.ts:contains('"+strAddedDtTime+"')"));
					assertTrue(selenium.isElementPresent("css=input[value='Acknowledge All Notifications']"));
					log4j.info("Web Notification displays in the appropriate format");
					//click Acknowledge All Notifications button
					selenium.click("css=input[value='Acknowledge All Notifications']");
				}catch(AssertionError ae){
					log4j.info("Web Notification NOT displayed in the appropriate format");
					strReason="Web Notification NOT displayed in the appropriate format";
				}
			}else{
				log4j.info("Web Notification is NOT displayed");
				strReason="Web Notification is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		return strReason;
	}
	
	/**
	'*************************************************************
	' Description: Wait and check for web notification, acknowledge the notification
	' Precondition: 				
	' Arguments: selenium, strEveName, strEveDesc
	' Returns:strReason
	' Date: 19-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/	public String ackHicsWebNotification(Selenium selenium, String strEveName,
			String strEveDesc) {
		String strReason = "";		
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Wait till acknowledgement pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				// Wait for acknowledgement
				intCnt = 0;
				while (selenium.isElementPresent("css=dt.othr") == false
						&& intCnt < 30) {
					Thread.sleep(1000);
					intCnt++;
				}				
				// check the resource name and description are displayed correctly
				
				try{
					assertEquals(strEveName, selenium.getText("css=dt.othr"));
					assertEquals(strEveDesc, selenium.getText("css=dd.desc"));					
					assertTrue(selenium.isElementPresent("css=input[value='Acknowledge All Notifications']"));
					log4j.info("Web Notification displays in the appropriate format");
					//click Acknowledge All Notifications button
					selenium.click("css=input[value='Acknowledge All Notifications']");
				}catch(AssertionError ae){
					log4j.info("Web Notification NOT displayed in the appropriate format");
					strReason="Web Notification NOT displayed in the appropriate format";
				}
			}else{
				log4j.info("Web Notification is NOT displayed");
				strReason="Web Notification is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		return strReason;
	}
	
	
	public String ackSTWebNotification(Selenium selenium,
			String strResourceName, String strSTDateTime) {
		String strReason = "";
		Date_Time_settings dts = new Date_Time_settings();
		try {
			selenium.selectWindow("");
			// Wait till acknowledgement pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				// Wait for acknowledgement
				intCnt = 0;
				while (selenium.isElementPresent("css=dt.othr") == false
						&& intCnt < 30) {
					Thread.sleep(1000);
					intCnt++;
				}
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"hh:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				// check the event name, description and start time are
				// displayed correctly
				try {
					assertEquals("Status Change for " + strResourceName + "",
							selenium.getText("css=dt.othr"));

					assertTrue(selenium.isElementPresent("css=dd.ts:contains('"
							+ strSTDateTime + "')"));
					
					assertTrue(selenium
							.isElementPresent("css=input[value='Acknowledge All Notifications']")
							|| selenium.isElementPresent("css=dd.ts:contains('"
									+ strAddedDtTime + "')"));
					log4j
							.info("Web Notification displays in the appropriate format");
					
					// click Acknowledge All Notifications button
					selenium
							.click("css=input[value='Acknowledge All Notifications']");
				} catch (AssertionError ae) {
					log4j
							.info("Web Notification NOT displayed in the appropriate format");
					strReason = "Web Notification NOT displayed in the appropriate format";
				}
			} else {
				log4j.info("Web Notification is NOT displayed");
				strReason = "Web Notification is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String ackSTWebNotificationNew(Selenium selenium,
			String strResourceName, String strSTDateTime,String strDesc) {
		String strReason = "";
		Date_Time_settings dts = new Date_Time_settings();
		try {
			selenium.selectWindow("");
			// Wait till acknowledgement pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				// Wait for acknowledgement
				intCnt = 0;
				while (selenium.isElementPresent("css=dt.othr") == false
						&& intCnt < 30) {
					Thread.sleep(1000);
					intCnt++;
				}
				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
				// check the event name, description and start time are
				// displayed correctly
				try {
					assertEquals("Status Change for " + strResourceName + "",
							selenium.getText("css=dt.othr"));
					
					assertEquals(strDesc, selenium.getText("css=dd.desc"));

					assertTrue(selenium
							.isElementPresent("css=input[value='Acknowledge All Notifications']"));
					
					assertTrue(selenium.isElementPresent("css=dd.ts:contains('"
							+ strSTDateTime + "')")
							|| selenium.isElementPresent("css=dd.ts:contains('"
									+ strAddedDtTime + "')"));
					log4j
							.info("Web Notification displays in the appropriate format");
					
					// click Acknowledge All Notifications button
					selenium
							.click("css=input[value='Acknowledge All Notifications']");
				} catch (AssertionError ae) {
					log4j
							.info("Web Notification NOT displayed in the appropriate format");
					strReason = "Web Notification NOT displayed in the appropriate format";
				}
			} else {
				log4j.info("Web Notification is NOT displayed");
				strReason = "Web Notification is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	public String acknNotification(Selenium selenium){
		String strReason="";
	
		try{
			//Wait till acknowledgement pop appears
			boolean blnFound=false;
			int intCnt=0;
			while(blnFound==false&&intCnt<30){
				try{
					selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound=true;
				}catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}
			
			if(blnFound){
				log4j.info("Web Notification is displayed");
				//Wait for acknowledgement
				intCnt=0;
				while(selenium.isElementPresent("css=dt.othr")==false&&intCnt<30){
					Thread.sleep(1000);
					intCnt++;
				}
			
				//click Acknowledge All Notifications button
				selenium.click("css=input[value='Acknowledge All Notifications']");
				
			}else{
				log4j.info("Web Notification is NOT displayed");
				strReason="Web Notification is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			strReason=e.toString();
		}
		return strReason;
	}
	/**
	'*************************************************************
	' Description: Select Web, Email, Pager notification for user and save
	' Precondition: 				
	' Arguments: selenium, strFullUserName, strEveTemp, blnEmail, blnPager, blnWeb
	' Returns:strReason
	' Date: 19-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	
	public String selectEventNofifForUser(Selenium selenium,
			String strFullUserName, String strEveTemp, boolean blnEmail,
			boolean blnPager, boolean blnWeb) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		
		int intCnt = 0;
		do {
			try {

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strEveTemp
								+ "']/parent::tr/td[1]/a[text()='Notifications']"));
				break;
			} catch (AssertionError Ae) {
				Thread.sleep(1000);
				intCnt++;

			} catch (Exception Ae) {
				Thread.sleep(1000);
				intCnt++;
			}
		} while (intCnt < 60);
		
		
		try {
			// click on notification link for user
			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
							+ strEveTemp
							+ "']/parent::tr/td[1]/a[text()='Notifications']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt = 0;
			do {
				try {

					assertEquals(
							"Event Notification Preferences for " + strEveTemp,
							selenium.getText("css=h1"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			

			// Event Notification Preferences for user is displayed
			try {
				assertEquals(
						"Event Notification Preferences for " + strEveTemp,
						selenium.getText("css=h1"));
				log4j.info("Event Notification Preferences for " + strEveTemp
						+ " screen is displayed.");

				
				intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
										+ strFullUserName
										+ "']/parent::tr/td[1]/input"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);
				
				// select Email check box for user
				if (blnEmail) {
					if (selenium
							.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
									+ strFullUserName
									+ "']/parent::tr/td[1]/input") == false)
						selenium
								.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
										+ strFullUserName
										+ "']/parent::tr/td[1]/input");

				} else {
					if (selenium
							.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
									+ strFullUserName
									+ "']/parent::tr/td[1]/input"))
						selenium
								.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
										+ strFullUserName
										+ "']/parent::tr/td[1]/input");
				}

				// select Pager check box for user
				if (blnPager) {
					if (selenium
							.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
									+ strFullUserName
									+ "']/parent::tr/td[2]/input") == false)
						selenium
								.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
										+ strFullUserName
										+ "']/parent::tr/td[2]/input");

				} else {
					if (selenium
							.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
									+ strFullUserName
									+ "']/parent::tr/td[2]/input"))
						selenium
								.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
										+ strFullUserName
										+ "']/parent::tr/td[2]/input");
				}

				// select web check box for user
				if (blnWeb) {
					if (selenium
							.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
									+ strFullUserName
									+ "']/parent::tr/td[3]/input") == false)
						selenium
								.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
										+ strFullUserName
										+ "']/parent::tr/td[3]/input");

				} else {
					if (selenium
							.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
									+ strFullUserName
									+ "']/parent::tr/td[3]/input"))
						selenium
								.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
										+ strFullUserName
										+ "']/parent::tr/td[3]/input");
				}

				// click on save button
				selenium.click(propElementDetails
						.getProperty("Event.EventNotPref.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isTextPresent("Event Template List"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				

				try {
					assertTrue(selenium.isTextPresent("Event Template List"));
					log4j.info("'Event Template List' screen is displayed.");
				} catch (AssertionError ae) {
					log4j
							.info("'Event Template List' screen is NOT displayed.");
					strReason = "'Event Template List' screen is NOT displayed.";
				}
			} catch (AssertionError ae) {
				log4j.info("Event Notification Preferences for " + strEveTemp
						+ " screen is NOT displayed.");
				strReason = "Event Notification Preferences for " + strEveTemp
						+ " screen is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	
	/**
	'*************************************************************
	' Description: Select Web, Email, Pager notification for user and save
	' Precondition: 				
	' Arguments: selenium, strFullUserName, strEveTemp, blnEmail, blnPager, blnWeb
	' Returns:strReason
	' Date: 19-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	
	public String selectEventNofifForUserOnly(Selenium selenium,
			String strEventTempValue, boolean blnEmail,
			boolean blnPager, boolean blnWeb) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("css=input[name='emailInd'][value='"+strEventTempValue+"']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// select Email check box for user
			if (blnEmail) {
				if (selenium
						.isChecked("css=input[name='emailInd'][value='"+strEventTempValue+"']") == false)
					selenium
							.click("css=input[name='emailInd'][value='"+strEventTempValue+"']");

			} else {
				if (selenium
						.isChecked("css=input[name='emailInd'][value='"+strEventTempValue+"']"))
					selenium
							.click("css=input[name='emailInd'][value='"+strEventTempValue+"']");
			}

			// select Pager check box for user
			if (blnPager) {
				if (selenium
						.isChecked("css=input[name='pagerInd'][value='"+strEventTempValue+"']") == false)
					selenium
							.click("css=input[name='pagerInd'][value='"+strEventTempValue+"']");

			} else {
				if (selenium
						.isChecked("css=input[name='pagerInd'][value='"+strEventTempValue+"']"))
					selenium
							.click("css=input[name='pagerInd'][value='"+strEventTempValue+"']");
			}

			// select web check box for user
			if (blnWeb) {
				if (selenium
						.isChecked("css=input[name='webInd'][value='"+strEventTempValue+"']") == false)
					selenium
							.click("css=input[name='webInd'][value='"+strEventTempValue+"']");

			} else {
				if (selenium
						.isChecked("css=input[name='webInd'][value='"+strEventTempValue+"']"))
					selenium
							.click("css=input[name='webInd'][value='"+strEventTempValue+"']");
			}

			// click on save button
			selenium.click(propElementDetails
					.getProperty("Event.EventNotPref.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/**
	'*************************************************************
	' Description: Select Web, Email, Pager notification methods 
	               for user and save
	' Precondition: 				
	' Arguments: selenium, strUserName, strTemplateName
	' Returns:strReason
	' Date: 03-May-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'
	 * @throws Exception *************************************************************
	 */

	public String selectEvenNofMtdForUsr(Selenium selenium, String strUserName,
			String strTemplateName) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		 

		//click on 'Event Notification Preferences'link  for user
		try {
			selenium.click("link=Event Notification Preferences");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Event Notification Preferences for "
						+ strUserName, selenium.getTitle());

				log4j.info("'Event Notification Preferences for'" + strUserName
						+ " screen displayed");

				// Select email Check box
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				if (selenium
						.isChecked("//div[@id='mainContainer']/form/table/tbody/tr/td[1][text()='"
								+ strTemplateName + "']/parent::tr/td[2]/input")) {
					log4j.info("Email is Selected");
				} else {
					selenium
							.click("//div[@id='mainContainer']/form/table/tbody/tr/td[1][text()='"
									+ strTemplateName
									+ "']/parent::tr/td[2]/input");
					log4j.info("Email is Selected");
				}
				// Select TextPager Check box
				if (selenium
						.isChecked("//div[@id='mainContainer']/form/table/tbody/tr/td[1][text()='"
								+ strTemplateName + "']/parent::tr/td[3]/input")) {
					log4j.info("TextPager is Selected");
				} else {
					selenium
							.click("//div[@id='mainContainer']/form/table/tbody/tr/td[1][text()='"
									+ strTemplateName
									+ "']/parent::tr/td[3]/input");
					log4j.info("TextPager  is Selected");
				}
				// Select Web Check box
				if (selenium
						.isChecked("//div[@id='mainContainer']/form/table/tbody/tr/td[1][text()='"
								+ strTemplateName + "']/parent::tr/td[4]/input")) {
					log4j.info("Web Page is Selected");
				} else {
					selenium
							.click("//div[@id='mainContainer']/form/table/tbody/tr/td[1][text()='"
									+ strTemplateName
									+ "']/parent::tr/td[4]/input");
					log4j.info("WebPage is Selected");
				}

				selenium.click(propElementDetails
						.getProperty("Event.EventNotPref.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Edit User", selenium.getTitle());
					log4j.info("Edit User page displayed");

				} catch (AssertionError a) {
					log4j.info("Edit User page Not displayed" + a);
					strReason = "Edit User page Not displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("Event Notification Preferences for " + strUserName
						+ " screen is NOT displayed.");
				strReason = "Event Notification Preferences for " + strUserName
						+ " screen is NOT displayed.";
			}

		}catch(Exception e){
			log4j.info("Error"+e);
			strReason=e.toString();
		}
		return strReason;

	}
	
	/**
	'**************************************************************************
	' Description: Select Web, Email, Pager notification for ALL  users and save
	' Precondition: 				
	' Arguments: selenium,strEveTemp, blnEmail, blnPager, blnWeb
	' Returns:strReason
	' Date: 5-June-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'*************************************************************
*/
	
	public String selectAndDeselectEventNotifForAll(Selenium selenium,
			String strEveTemp, boolean blnEmail, boolean blnPager,
			boolean blnWeb) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// click on notification link for user
			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
							+ strEveTemp
							+ "']/parent::tr/td[1]/a[text()='Notifications']");
			selenium.waitForPageToLoad(gstrTimeOut);

			// Event Notification Preferences for user is displayed
			try {
				assertEquals(
						"Event Notification Preferences for " + strEveTemp,
						selenium.getText("css=h1"));
				log4j.info("Event Notification Preferences for " + strEveTemp
						+ " screen is displayed.");

				// select Email check box for user
				if (blnEmail) {
					if (selenium
							.isChecked("css=input[value='emailInd'][name='SELECT_ALL']") == false)
						selenium
								.click("css=input[value='emailInd'][name='SELECT_ALL']");

				} else {
					if (selenium
							.isChecked("css=input[value='emailInd'][name='SELECT_ALL']"))
						selenium
								.click("css=input[value='emailInd'][name='SELECT_ALL']");
					else {
						selenium
								.click("css=input[value='emailInd'][name='SELECT_ALL']");

						selenium
								.click("css=input[value='emailInd'][name='SELECT_ALL']");

					}
				}

				// select Pager check box for user
				if (blnPager) {
					if (selenium
							.isChecked("css=input[value='pagerInd'][name='SELECT_ALL']") == false)
						selenium
								.click("css=input[value='pagerInd'][name='SELECT_ALL']");

				} else {
					if (selenium
							.isChecked("css=input[value='pagerInd'][name='SELECT_ALL']"))
						selenium
								.click("css=input[value='pagerInd'][name='SELECT_ALL']");

					else {
						selenium
								.click("css=input[value='pagerInd'][name='SELECT_ALL']");
						selenium
								.click("css=input[value='pagerInd'][name='SELECT_ALL']");
					}
				}

				// select web check box for user
				if (blnWeb) {
					if (selenium
							.isChecked("css=input[value='webInd'][name='SELECT_ALL']") == false)
						selenium
								.click("css=input[value='webInd'][name='SELECT_ALL']");

				} else {
					if (selenium
							.isChecked("css=input[value='webInd'][name='SELECT_ALL']"))
						selenium
								.click("css=input[value='webInd'][name='SELECT_ALL']");
					else {
						selenium
								.click("css=input[value='webInd'][name='SELECT_ALL']");
						selenium
								.click("css=input[value='webInd'][name='SELECT_ALL']");

					}
				}

				// click on save button
				selenium.click(propElementDetails
						.getProperty("Event.EventNotPref.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isTextPresent("Event Template List"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				try {
					assertTrue(selenium.isTextPresent("Event Template List"));
					log4j.info("'Event Template List' screen is displayed.");
				} catch (AssertionError ae) {
					log4j
							.info("'Event Template List' screen is NOT displayed.");
					strReason = "'Event Template List' screen is NOT displayed.";
				}
			} catch (AssertionError ae) {
				log4j.info("Event Notification Preferences for " + strEveTemp
						+ " screen is NOT displayed.");
				strReason = "Event Notification Preferences for " + strEveTemp
						+ " screen is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e + "selectAndDeselectEventNotifForAll function failed");
			strReason = "selectAndDeselectEventNotifForAll function failed"
					+ e.toString();
		}
		return strReason;
	}

	
	public String ackSTWebNotificationCorrect(Selenium selenium,
			String strResourceName, String strSTDateTime1,
			String strSTDateTime2, String strDesc1, String strDesc2) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Wait till acknowledgement pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				// Wait for acknowledgement
				intCnt = 0;
				while (selenium.isElementPresent("css=dt.othr") == false
						&& intCnt < 30) {
					Thread.sleep(1000);
					intCnt++;
				}

				try {
					assertEquals("Status Change for " + strResourceName + "",
							selenium.getText("css=dt.othr"));

					assertTrue(selenium.getText("css=dd.desc").equals(strDesc1)
							|| selenium.getText("css=dd.desc").equals(strDesc2));

					assertTrue(selenium
							.isElementPresent("css=input[value='Acknowledge All Notifications']"));

					assertTrue(selenium.isElementPresent("css=dd.ts:contains('"
							+ strSTDateTime1 + "')")
							|| selenium.isElementPresent("css=dd.ts:contains('"
									+ strSTDateTime2 + "')"));
					log4j
							.info("Web Notification displays in the appropriate format");

					// click Acknowledge All Notifications button
					selenium
							.click("css=input[value='Acknowledge All Notifications']");
				} catch (AssertionError ae) {
					log4j
							.info("Web Notification NOT displayed in the appropriate format");
					strReason = "Web Notification NOT displayed in the appropriate format";
				}
			} else {
				log4j.info("Web Notification is NOT displayed");
				strReason = "Web Notification is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	
	
	public String ackSTWebNotificationForManyStatusTypes(Selenium selenium,
			String strResourceName, String strSTDateTime1,
			String strSTDateTime2, String strDesc1[], String strDesc2[]) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Wait till acknowledgement pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				// Wait for acknowledgement
				intCnt = 0;
				while (selenium.isElementPresent("css=dt.othr") == false
						&& intCnt < 30) {
					Thread.sleep(1000);
					intCnt++;
				}

				int i = 0;
				for (i = 0; i < strDesc1.length; i++) {
					try {
						assertEquals("Status Change for " + strResourceName
								+ "", selenium.getText("//dl[1+" + i
								+ "]/dt[@class='othr']"));

						assertTrue(selenium
								.isElementPresent("//dl/dd[@class='desc'][text()='"
										+ strDesc1[i] + "']")
								|| selenium
										.isElementPresent("//dl/dd[@class='desc'][text()='"
												+ strDesc2[i] + "']"));

						assertTrue(selenium.isElementPresent("//dl[1+" + i
								+ "]/dd[@class='ts'][text()='"
								+ strSTDateTime1 + "']")
								|| selenium.isElementPresent("//dl[1+" + i
										+ "]/dd[@class='ts'][text()='"
										+ strSTDateTime2 + "']"));
						log4j
								.info("Web Notification displays in the appropriate format");

					} catch (AssertionError ae) {
						log4j
								.info("Web Notification NOT displayed in the appropriate format");
						strReason = "Web Notification NOT displayed in the appropriate format";
					}
				}

				if (i == strDesc1.length && strReason.equals("") ){
					selenium
							.click("css=input[value='Acknowledge All Notifications']");
					Thread.sleep(5000);
				}

			} else {
				log4j.info("Web Notification is NOT displayed");
				strReason = "Web Notification is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	public String ackSTWebNotificationForManyEvents(Selenium selenium,
			String strEvent[], String strSTDateTime1,
			String strSTDateTime2, String strDesc1[]) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Wait till acknowledgement pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium
							.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				// Wait for acknowledgement
				intCnt = 0;
				while (selenium.isElementPresent("css=dt.othr") == false
						&& intCnt < 30) {
					Thread.sleep(1000);
					intCnt++;
				}

				int i = 0;
				for (i = 0; i < strDesc1.length; i++) {
					try {
						assertTrue(selenium.isElementPresent("//dl/dt[@class='othr'][text()='"+strEvent[i]+"']"));
						String str=selenium.getText("//dl/dd[@class='desc']");
						assertTrue(selenium
								.isTextPresent(str));

					
						assertTrue(selenium.isElementPresent("//dl/dd[@class='ts'][text()='"
								+ strSTDateTime1 + "']")
								|| selenium.isElementPresent("//dl/dd[@class='ts'][text()='"+strSTDateTime2+"']"));
						log4j
								.info("Web Notification displays in the appropriate format");

					} catch (AssertionError ae) {
						log4j
								.info("Web Notification NOT displayed in the appropriate format");
						strReason = "Web Notification NOT displayed in the appropriate format";
					}
				}

				if (i == strDesc1.length && strReason.equals("") ){
					selenium
							.click("css=input[value='Acknowledge All Notifications']");
					Thread.sleep(5000);
				}

			} else {
				log4j.info("Web Notification is NOT displayed");
				strReason = "Web Notification is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	//start//selectAll3EventNofifForUser//
	/*******************************************************************************************
	' Description: Select Web, Email, Pager notification for user and save and deselect Web other users
	' Precondition: N/A 
	' Arguments: selenium, strFullUserName, strEveTemp, blnEmail, blnPager, blnWeb,blnDesWebForAll,blnSave
	' Returns: String 
	' Date: 24/06/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String selectAll3EventNofifForUser(Selenium selenium,
			String pStrFullUserName, String pStrEveTemp, boolean blnEmail,
			boolean blnPager, boolean blnWeb,boolean blnDesWebForAll,boolean blnSave) throws Exception {
		String strReason = "";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
								+ pStrFullUserName + "']/parent::tr/td[1]/input"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// deselect Web(Select All) check box and save
			if(blnDesWebForAll){
				if(selenium.isChecked("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']")==true){
			      selenium
			        .click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");
				}
			}
			// select Email check box for user
			if (blnEmail) {
				if (selenium
						.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
								+ pStrFullUserName + "']/parent::tr/td[1]/input") == false)
					selenium.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
							+ pStrFullUserName + "']/parent::tr/td[1]/input");

			} else {
				if (selenium
						.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
								+ pStrFullUserName + "']/parent::tr/td[1]/input"))
					selenium.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
							+ pStrFullUserName + "']/parent::tr/td[1]/input");
			}

			// select Pager check box for user
			if (blnPager) {
				if (selenium
						.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
								+ pStrFullUserName + "']/parent::tr/td[2]/input") == false)
					selenium.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
							+ pStrFullUserName + "']/parent::tr/td[2]/input");

			} else {
				if (selenium
						.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
								+ pStrFullUserName + "']/parent::tr/td[2]/input"))
					selenium.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
							+ pStrFullUserName + "']/parent::tr/td[2]/input");
			}

			// select web check box for user
			if (blnWeb) {
				if (selenium
						.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
								+ pStrFullUserName + "']/parent::tr/td[3]/input") == false)
					selenium.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
							+ pStrFullUserName + "']/parent::tr/td[3]/input");

			} else {
				if (selenium
						.isChecked("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
								+ pStrFullUserName + "']/parent::tr/td[3]/input"))
					selenium.click("//table[@id='tbl_emailInd']/tbody/tr/td[4][text()='"
							+ pStrFullUserName + "']/parent::tr/td[3]/input");
			}
		      
			// click on save button
			if(blnSave){
				selenium.click(propElementDetails
						.getProperty("Event.EventNotPref.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				intCnt = 0;
				do {
					try {
	
						assertTrue(selenium.isTextPresent("Event Template List"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
	
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);
	
				try {
					assertTrue(selenium.isTextPresent("Event Template List"));
					log4j.info("'Event Template List' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Template List' screen is NOT displayed.");
					strReason = "'Event Template List' screen is NOT displayed.";
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	//end//selectAll3EventNofifForUser//
}
