package com.qsgsoft.EMResource.shared;

import java.util.Properties;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;

public class EventList {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.shared.EventList");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	ReadData rdExcel;


	public String checkDataInEventListTable(Selenium selenium,
			String strHeader, String strEventName, String strData,
			String strColIndex) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		Date_Time_settings dts = new Date_Time_settings();

		try {
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th["
								+ strColIndex + "][text()='" + strHeader + "']")
						|| selenium
								.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th["
										+ strColIndex
										+ "]/a[text()='"
										+ strHeader + "']"));
				log4j.info("The Column header " + strHeader
						+ " is displayed in Event Management/List table");
				if (strHeader.equals("Icon")) {
					try {
						assertEquals(
								strData,
								selenium
										.getAttribute("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
												+ strEventName
												+ "']/parent::tr/td["
												+ strColIndex + "]/img@src"));
						log4j
								.info(strData
										+ " is found in the Event Management/List table for the event "
										+ strEventName);
					} catch (AssertionError ae) {
						log4j
								.info(strData
										+ " is NOT found in the Event Management/List table for the event "
										+ strEventName);
						strReason = strData
								+ " is NOT found in the Event Management/List table for the event "
								+ strEventName;
					}
				} else if (strHeader.equals("Start") || strHeader.equals("End")) {
					String strAddedDtTime = "";
					if (strData.equals("never") == false) {
						String[] strTime = strData.split(" ");
						strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
								"HH:mm");
						strAddedDtTime = strTime[0] + " " + strAddedDtTime;
					}
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEventName + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
										+ strEventName
										+ "']/parent::tr/td["
										+ strColIndex
										+ "][text()='"
										+ strData
										+ "']")
								|| selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
												+ strEventName
												+ "']/parent::tr/td["
												+ strColIndex
												+ "][text()='"
												+ strAddedDtTime + "']"));

						log4j
								.info(strData
										+ " is found in the Event Management/List table for the event "
										+ strEventName);

					} catch (AssertionError ae) {
						log4j
								.info(strData
										+ " is NOT found in the Event Management/List table for the event "
										+ strEventName);
						strReason = strData
								+ " is NOT found in the Event Management/List table for the event "
								+ strEventName;
					}

				} else {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEventName + "']"));
						String str=selenium
						.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
								+ strEventName
								+ "']/parent::tr/td["
								+ strColIndex
								+ "]");
						log4j.info(str);
						assertTrue(selenium
								.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
										+ strEventName
										+ "']/parent::tr/td["
										+ strColIndex
										+ "]").equals(strData)||selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
												+ strEventName
												+ "']/parent::tr/td["
												+ strColIndex
												+ "]/a[text()='"
												+ strData + "']"));
										
				
						log4j
								.info(strData
										+ " is found in the Event Management/List table for the event "
										+ strEventName);

					} catch (AssertionError ae) {
						log4j
								.info(strData
										+ " is NOT found in the Event Management/List table for the event "
										+ strEventName);
						strReason = strData
								+ " is NOT found in the Event Management/List table for the event "
								+ strEventName;
					}
				}
			} catch (AssertionError ae) {
				log4j.info("The Column header " + strHeader
						+ " is NOT displayed in Event Management/List table");
				strReason = strReason + " The Column header " + strHeader
						+ " is NOT displayed in Event Management/List table";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/********************************************************************************************************
	 'Description :check Status type in event status screen
	 'Precondition :None
	 'Arguments  :selenium,strStatTypeArr,blnST
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/

	public String chkSTInEventBanners(Selenium selenium,
			String[] strStatTypeArr, boolean blnST,String strResource) {
		String strReason = "";
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnST) {
				for (int i = 0; i < strStatTypeArr.length; i++) {
				try {					
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th/a[text()='"+strStatTypeArr[i]+"']/ancestor::thead/following-sibling::tbody/tr/td/a" +
										"[text()='"+strResource+"']/parent::td//following-sibling::td[text()='--']"));
						log4j.info(strStatTypeArr[i]
										+ "is associated in the 'Event Status' screen for "+ strResource +" -- is displayed.");
				} catch (AssertionError ae) {
						log4j.info(strStatTypeArr[i]+ "is NOT associated in the 'Event Status' screen for " + strResource +"-- is displayed.");
						strReason =strStatTypeArr[i]+ "is NOT associated in the 'Event Status' screen for " + strResource +"-- is displayed.";
				}
				}
			} else {
				for (int i = 0; i < strStatTypeArr.length; i++) {
				try {		
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th/a[text()='"+strStatTypeArr[i]+"']/ancestor::thead/following-sibling::tbody/tr/td/a" +
										"[text()='"+strResource+"']/parent::td//following-sibling::td[text()='N/A']"));
						log4j.info(strStatTypeArr[i]
													+ "is NOT associated in the 'Event Status' screen for "+ strResource +"N/A is displayed");
							
							} catch (AssertionError ae) {
									log4j.info(strStatTypeArr[i]+ "is associated in the 'Event Status' screen for "+ strResource +"N/A is displayed");
									strReason =strStatTypeArr[i]+ "is associated in the 'Event Status' screen for "+ strResource +"N/A is displayed";
							}
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/********************************************************************************************************
	 'Description :check Resources in event status screen
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/

	public String chkResourceInEventBanner(Selenium selenium,
			String strResource, boolean blnRes) {
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			selenium.selectWindow("");

			selenium.selectFrame("Data");
			if (blnRes) {
				try {

					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
									+ strResource + "']"));
					log4j.info("Resource" + strResource
							+ " is displayed in event status screen ");
				} catch (AssertionError ae) {
					log4j.info("Resource " + strResource
							+ " is NOT displayed in event status screen ");
					strReason = "Resource " + strResource
							+ " is NOT displayed in event status screen ";
				}
			} else {
				try {

					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
									+ strResource + "']"));
					log4j.info("Resource" + strResource
							+ " is NOT displayed in event status screen ");
				} catch (AssertionError ae) {
					log4j.info("Resource" + strResource
							+ " is displayed in event status screen ");
					strReason = "Resource" + strResource
							+ " is displayed in event status screen ";
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	 /********************************************************************************************************
	 'Description :check error message in event status screen
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/
	
	
	public String checkErrorMessageInEventBanner(Selenium selenium,
			String strEveName) {
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;

			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner'"
									+ "]/table/tbody/tr/td/a[text()='"
									+ strEveName + "']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;

			do {
				try {

					assertTrue(selenium
							.isTextPresent("Either there are no resources participating in this event, or you do not"
									+ " have viewing rights to any resources/status types involved in this event."));
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

				assertTrue(selenium
						.isTextPresent("Either there are no resources participating in this event, or you do not"
								+ " have viewing rights to any resources/status types involved in this event."));
				log4j
						.info("The following message is displayed:"
								+ " 'Either there are no resources participating in this event,"
								+ " or you do not have viewing rights to any resources/status types involved in this event.");
			} catch (AssertionError ae) {
				log4j
						.info("The following message is NOT displayed:"
								+ " 'Either there are no resources participating in this event,"
								+ " or you do not have viewing rights to any resources/status types involved in this event.");
				strReason = "The following message is NOT displayed:"
						+ " 'Either there are no resources participating in this event,"
						+ " or you do not have viewing rights to any resources/status types involved in this event.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	 /********************************************************************************************************
	 'Description :check resource in event status screen
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/
	
	public String chkRSAndSTInEventBannerfalseCndtn(Selenium selenium,
			String strEveName, String strResType, String strResource,
			String[] strStatTypeArr) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;

			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
									+ strEveName + "']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;

			do {
				try {

					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
									+ strResType + "']"));
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
				assertFalse(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
								+ strResType + "']"));
				log4j
						.info("ResourceType '"
								+ strResType
								+ "' is NOT displayed  when clicked on the event banner of EVE.On'Event Status' screen.");
				assertFalse(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
								+ strResource + "']"));
				log4j
						.info("Resource '"
								+ strResource
								+ "' is NOT displayed  when clicked on the event banner of EVE.On'Event Status' screen.");
				for (int i = 0; i < strStatTypeArr.length; i++) {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[3]/a[text()='"
									+ strStatTypeArr[i] + "']"));
					log4j.info(strStatTypeArr[i]
							+ "is NOT displayed on the 'Event Status' screen ");
				}
			} catch (AssertionError ae) {
				log4j
						.info("Resource '"
								+ strResource
								+ "'displayed when clicked on the event banner of EVE.On'Event Status' screen. ");
				strReason = "Resource '"
						+ strResource
						+ "'displayed when clicked on the event banner of EVE.on the 'Event Status' screen .";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	public String checkInEventBanner(Selenium selenium, String strEveName,
			String strResType, String strResource, String[] strStatTypeArr) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			// Click on Event name in event banner
			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			try{
				selenium.waitForPageToLoad("30000");
			}catch(Exception e){
				
			}
			
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 60) {
				try {
					selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
									+ strResType + "']");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			intCnt = 0;
			while (selenium
					.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
							+ strResType + "']") == false
					&& intCnt < 60) {
				Thread.sleep(1000);
				intCnt++;
			}
	
			try {
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
								+ strResType + "']"));
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
								+ strResource + "']"));
				for (int i = 0; i < strStatTypeArr.length; i++) {
					assertEquals(
							strStatTypeArr[i],
							selenium
									.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
											+ strResType
											+ "']/ancestor::tr/th["
											+ (i + 3)
											+ "]"));
				}

				log4j.info("Resource '" + strResource
						+ "' is displayed on the 'Event Status' screen under Resource Type "
						+ strResType + " along with all the status types.");

			} catch (AssertionError ae) {
				log4j
						.info("Resource '"
								+ strResource
								+ "' is NOT displayed on the 'Event Status' screen under Resource Type "
								+ strResType
								+ " along with all the status types.");
				strReason = "Resource '"
						+ strResource
						+ "' is NOT displayed on the 'Event Status' screen under Resource Type "
						+ strResType + " along with all the status types.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	public String checkInEventBannerNew(Selenium selenium, String strEveName,
			String strResType, String strResource, String[] strStatTypeArr)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Event name in event banner
			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			try{
				selenium.waitForPageToLoad(gstrTimeOut);
			}catch(Exception e){
				
			}
			

			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 60) {
				try {
					selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
									+ strResType + "']");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			intCnt = 0;
			while (selenium
					.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
							+ strResType + "']") == false
					&& intCnt < 60) {
				Thread.sleep(1000);
				intCnt++;
			}

			try {
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
								+ strResType + "']"));
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
								+ strResource + "']"));
				for (int i = 0; i < strStatTypeArr.length; i++) {
					try {
						assertEquals(
								strStatTypeArr[i],
								selenium
										.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
												+ strResType
												+ "']/ancestor::tr/th["
												+ (i + 3) + "]"));
						log4j.info("Status type displayed");
					} catch (AssertionError Ae) {
						log4j.info("Status type NOT displayed");
						strReason = "Status type NOT displayed";

					}
				}

				log4j
						.info("Resource '"
								+ strResource
								+ "' is displayed on the 'Event Status' screen under Resource Type "
								+ strResType
								+ " along with all the status types.");

			} catch (AssertionError ae) {
				log4j
						.info("Resource '"
								+ strResource
								+ "' is NOT displayed on the 'Event Status' screen under Resource Type "
								+ strResType
								+ " along with all the status types.");
				strReason = "Resource '"
						+ strResource
						+ "' is NOT displayed on the 'Event Status' screen under Resource Type "
						+ strResType + " along with all the status types.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public boolean navToEventList(Selenium selenium) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		// Event tab
		selenium.mouseOver(propElementDetails.getProperty("EventLink"));
		// Click on Event List link
		selenium.click(propElementDetails.getProperty("Event.EventList"));
		selenium.waitForPageToLoad(gstrTimeOut);

		int intCnt = 0;

		do {
			try {

				assertEquals("Event List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
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
			assertEquals("Event List", selenium.getText(propElementDetails
					.getProperty("Header.Text")));
			log4j.info("'Event List' screen is displayed.");
			return true;
		} catch (AssertionError ae) {
			log4j.info("'Event List' screen is NOT displayed.");
			return false;
		}
	}
	
	 /********************************************************************************************************
	 'Description :check error message in event status screen
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 * @throws Exception 
	 ***********************************************************************************************************/	
	
	
	public String checkSTypesEvntBanner(Selenium selenium, String strEveName,
			String strResType, String[] strStatTypeArr,boolean blnST) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
		}catch(Exception e){
			
		}
		try {
			// Click on Event name in event banner
			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			try{
				selenium.waitForPageToLoad(gstrTimeOut);
			}catch(Exception e){
				
			}
			
			if(blnST)
			{
				for (int i = 0; i < strStatTypeArr.length; i++) {
					try {

						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
										+ strResType
										+ "']/ancestor::tr/th/a[text()='"
										+ strStatTypeArr[i] + "']"));
						log4j.info("Status Type '"
								+ strStatTypeArr[i]
								+ "' is displayed on the 'Event Status' screen of"
								+ strEveName + ".");

					} catch (AssertionError ae) {

						log4j.info("Status Type '"
								+ strStatTypeArr[i]
								+ "' is NOT displayed on the 'Event Status' screen of"
								+ strEveName + ".");
						strReason = strReason
								+ " Status Type '"
								+ strStatTypeArr[i]
								+ "' is NOT displayed on the 'Event Status' screen of"
								+ strEveName + ".";
					}
				}
			}else{
				
				for (int i = 0; i < strStatTypeArr.length; i++) {
					try {

						assertFalse(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
										+ strResType
										+ "']/ancestor::tr/th/a[text()='"
										+ strStatTypeArr[i] + "']"));
						log4j.info("Status Type '"
								+ strStatTypeArr[i]
								+ "' is not displayed on the 'Event Status' screen of"
								+ strEveName + ".");

					} catch (AssertionError ae) {

						log4j.info("Status Type '"
								+ strStatTypeArr[i]
								+ "' is displayed on the 'Event Status' screen of"
								+ strEveName + ".");
						strReason = strReason
								+ " Status Type '"
								+ strStatTypeArr[i]
								+ "' is displayed on the 'Event Status' screen of"
								+ strEveName + ".";

					}
				}
				
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	 /********************************************************************************************************
	 'Description :check event color 
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/	
	
	public String varfyEventColor(Selenium selenium, String strEveName,
			String strEveColor) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			assertTrue(selenium
					.isElementPresent("//table/tbody/tr/td[@class='"+strEveColor+"']"));
			log4j.info("Event banner for '" + strEveName
					+ "' is displayed and is highlighted in '" + strEveColor
					+ "' color. ");
		} catch (AssertionError ae) {
			log4j.info("Event banner for '" + strEveName
					+ "' is NOT displayed and is highlighted in '"
					+ strEveColor + "' color. ");
			strReason="Event banner for '" + strEveName
					+ "' is NOT displayed and is highlighted in '"
					+ strEveColor + "' color. ";
		}
		return strReason;
	}
	
	 /********************************************************************************************************
	 'Description   :Navigating to event list page
	 'Precondition  :None
	 'Arguments     :selenium,strResource
	 'Returns       :String
	 'Date          :6-June-2012
	 'Author        :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/	

	public String navToEventListNew(Selenium selenium) throws Exception {
		String strErrorMSg = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
		
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Event tab
			selenium.mouseOver(propElementDetails.getProperty("EventLink"));
			// Click on Event List link
			selenium.click(propElementDetails.getProperty("Event.EventList"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Event List",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
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
				assertEquals("Event List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Event List' screen is displayed.");

			} catch (AssertionError ae) {
				log4j.info("'Event List' screen is NOT displayed.");
				strErrorMSg = "'Event List' screen is NOT displayed.";

			}

		} catch (Exception e) {
			log4j.info(e);
			strErrorMSg = e.toString();
		}
		return strErrorMSg;
	}
	
	 /********************************************************************************************************
	 'Description :check error message in event status screen
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/	

	public String chkErrorMsgInEventBanForUser(Selenium selenium,
			String strEveName) {
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Click on Event name in event banner
			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;

			do {
				try {

					assertTrue(selenium
							.isTextPresent("You do not have viewing rights to"
									+ " any resources participating in this event."));
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
				assertTrue(selenium
						.isTextPresent("You do not have viewing rights to any resources"
								+ " participating in this event."));
				log4j.info("The following message is displayed:"
						+ " You do not have viewing rights to any resources"
						+ " participating in this event.");
			} catch (AssertionError ae) {
				log4j.info("The following message is NOT displayed:"
						+ " You do not have viewing rights to any resources "
						+ "participating in this event.");
				strReason = "The following message is NOT displayed:"
						+ "You do not have viewing rights to any resources "
						+ "participating in this event.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	 /********************************************************************************************************
	 'Description  :To click event banner
	 'Arguments    :selenium,strResource
	 'Returns      :String
	 'Date         :6-June-2012
	 'Author       :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/	
	
	public String clickEventBanner(Selenium selenium, String strEveName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Event name in event banner
			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			try {
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (Exception e) {

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description	:navigate to Update status screen
	'Precondition	:None
	'Arguments		:selenium,strNavElement
	'Returns		:strReason
	'Date	 		:28-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String navToEvntStatScreenByEventIcon(Selenium selenium,
			String strEveName, String strResource) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue(selenium.isElementPresent("//a[text()='"
						+ strResource + "']"
						+ "/following-sibling::a/img[@alt='" + strEveName
						+ "']"));
				log4j.info("Event icon selected for event " + strEveName
						+ " is displayed next to resource " + strResource + ".");

				// click update status image
				selenium.click("//a[text()='" + strResource + "']"
						+ "/following-sibling::a/img[@alt='" + strEveName
						+ "']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt=0;

				do{
					try {

						assertEquals("Event Status",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
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
					assertEquals("Event Status",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Event Status screen is displayed");
				} catch (AssertionError ae) {
					log4j.info("Event Status screen is NOT displayed");
					strReason = "Event Status screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("Event icon selected for event " + strEveName
						+ " is NOT displayed next to resource " + strResource
						+ ".");
				strReason = "Event icon selected for event " + strEveName
						+ " is NOT displayed next to resource " + strResource
						+ ".";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToUpdateStatus " + e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description	:navigate to Update status screen
	'Precondition	:None
	'Arguments		:selenium,strNavElement
	'Returns		:strReason
	'Date	 		:28-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String checkOnlyDetailsInEventBanner(Selenium selenium, String strEveName,
			String strResType, String strResource, String[] strStatTypeArr)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
								+ strResType + "']"));
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"
								+ strResource + "']"));
				for (int i = 0; i < strStatTypeArr.length; i++) {
					try {
						assertEquals(
								strStatTypeArr[i],
								selenium
										.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]/a[text()='"
												+ strResType
												+ "']/ancestor::tr/th["
												+ (i + 3) + "]"));
						log4j.info("Status type displayed");
					} catch (AssertionError Ae) {
						log4j.info("Status type NOT displayed");
						strReason = "Status type NOT displayed";

					}
				}

				log4j
						.info("Resource '"
								+ strResource
								+ "' is displayed on the 'Event Status' screen under Resource Type "
								+ strResType
								+ " along with all the status types.");

			} catch (AssertionError ae) {
				log4j
						.info("Resource '"
								+ strResource
								+ "' is NOT displayed on the 'Event Status' screen under Resource Type "
								+ strResType
								+ " along with all the status types.");
				strReason = "Resource '"
						+ strResource
						+ "' is NOT displayed on the 'Event Status' screen under Resource Type "
						+ strResType + " along with all the status types.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	 /***************************************************************
	 'Description  :Navigate to event status screen
	 'Arguments    :selenium,strEveName
	 'Returns      :strReason
	 'Date         :26-Feb-2013
	 'Author       :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	
	public String navToEventStatusScreenByViewLink(Selenium selenium,
			String strEveName) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// click on View
			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
							+ strEveName
							+ "']/preceding-sibling::td/a[text()='View']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;

			do {
				try {

					assertEquals("Event Status", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
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

				assertEquals("Event Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Event Status Screen is  displayed.");

			} catch (AssertionError Ae) {
				log4j.info("Event Status Screen is NOT displayed." + Ae);
				strReason = "Event Status Screen is NOT displayed." + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function returnUserViews" + e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	 'Description  :Navigate to event status screen
	 'Arguments    :selenium,strEveName
	 'Returns      :strReason
	 'Date         :26-Feb-2013
	 'Author       :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String navToEventHistory(Selenium selenium, String strEveName,
			String strEveDesc, String strUser) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// click on View History
			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
					+ strEveName
					+ "']/preceding-sibling::td/a[text()='View�History']");
			
			try{
				selenium.waitForPageToLoad("30000");
			}catch(Exception e){
				log4j.info("Wait for page load Not worked");
				
			}
			
			// Wait till pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			if (blnFound) {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertEquals("Event History",
							selenium.getText("id=TB_ajaxWindowTitle"));
					log4j.info("Event History Pop up window is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("//html/body/dl[1]/dt[text()='"
										+ strEveName + "']"));
						
						assertTrue(selenium
								.isElementPresent("//html/body/dl[2]/dd[@class='desc']"
										+ "[text()='"
										+ strEveDesc
										+ " (updated by " + strUser + ")']"));
						
						
						log4j.info("Event Name " + strEveName
								+ "(< Created user full name>)is displayed.");

					} catch (AssertionError ae) {
						log4j.info("Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.");
						strReason = "Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.";
					}
					try {
						assertTrue(selenium
								.isElementPresent("//html/body/dl[3]/dt[text()='"
										+ strEveName + "']"));
						assertTrue(selenium
								.isElementPresent("//html/body/dl[3]/dd[@class='desc']"
										+ "[text()='Event ended (by "
										+ strUser
										+ ")']"));
						log4j.info("Event Name " + strEveName
								+ "(< Updated  user full name>)is displayed.");

					} catch (AssertionError ae) {
						log4j.info("Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.");
						strReason = "Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.";
					}
				} catch (AssertionError Ae) {
					log4j.info("Event History Pop up window is displayed." + Ae);
					strReason = "Event History Pop up window is displayed."
							+ Ae;
				}
			} else {
				log4j.info("Pop up window is NOT displayed");
				strReason = "Pop up window is NOT displayed";
			}
			selenium.selectFrame("relative=up");
			selenium.click("id=TB_closeWindowButton");

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function returnUserViews" + e.toString();
		}
		return strReason;
	}

	
	public String checkEvenetEventListTable(Selenium selenium,
			 String strEventName) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
				if(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[6][text()='Title']")
						|| selenium
								.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[6]/a[text()='Title']")){
				log4j.info("The Column header Title"
						+ " is displayed in Event Management/List table");

				try {
					assertFalse(

					selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
							+ strEventName
							+ "']/parent::tr/td[6]"));
					log4j.info(strEventName
							+ " is not listed in the Event Management/List table ");
				} catch (AssertionError ae) {
					log4j.info(strEventName
							+ " is listed in the Event Management/List table ");
					strReason = strEventName
							+ " is listed in the Event Management/List table ";
				}

			} else{
				log4j.info("The Column header Title is NOT displayed in Event Management/List table and "
						+strEventName+ " is not listed in the Event Management/List table");
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	//start//varfyEventIconInBanner//
	/*******************************************************************************************
	' Description: check event Icon
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 24/06/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String varfyEventIconInBanner(Selenium selenium, String strEveVlue,
			String strEveIcon,String strEveName) throws Exception {
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue((selenium.getAttribute("//a[@id='t" + strEveVlue
						+ "']/@style")).contains(strEveIcon));
				log4j.info("'Event Banner' for '"
						+ strEveName
						+ "' is displayed in and selected event icon.");
			} catch (AssertionError ae) {
				log4j.info("'Event Banner' for '"
						+ strEveName
						+ "' is NOT displayed in selected event icon.");

				lStrReason = "'Event Banner' for '"
						+ strEveName
						+ "' is NOT displayed in and selected event icon.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "EventList.varfyEventIconInBanner failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//varfyEventIconInBanner//
	
	 /***************************************************************
	 'Description  :Navigate to event status screen
	 'Arguments    :selenium,strEveName
	 'Returns      :strReason
	 'Date         :26-Feb-2013
	 'Author       :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String navToEventHistoryVerify(Selenium selenium, String strEveName,
			String strEveDesc, String strUser) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// click on View History
			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
					+ strEveName
					+ "']/preceding-sibling::td/a[text()='View�History']");
			
			try{
				selenium.waitForPageToLoad("30000");
			}catch(Exception e){
				log4j.info("Wait for page load Not worked");
				
			}
			
			// Wait till pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			if (blnFound) {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertEquals("Event History",
							selenium.getText("id=TB_ajaxWindowTitle"));
					log4j.info("Event History Pop up window is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("//html/body/dl[1]/dt[text()='"
										+ strEveName + "']"));
						
						assertTrue(selenium
								.isElementPresent("//html/body/dl[2]/dd[@class='desc']"
										+ "[text()='"
										+ strEveDesc
										+ " (created by " + strUser + ")']"));
						
						
						log4j.info("Event Name " + strEveName
								+ "(< Created user full name>)is displayed.");

					} catch (AssertionError ae) {
						log4j.info("Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.");
						strReason = "Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.";
					}
					try {
						assertTrue(selenium
								.isElementPresent("//html/body/dl[3]/dt[text()='"
										+ strEveName + "']"));
						assertTrue(selenium
								.isElementPresent("//html/body/dl[3]/dd[@class='desc']"
										+ "[text()='Event ended (by "
										+ strUser
										+ ")']"));
						log4j.info("Event Name " + strEveName
								+ "(< Updated  user full name>)is displayed.");

					} catch (AssertionError ae) {
						log4j.info("Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.");
						strReason = "Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.";
					}
				} catch (AssertionError Ae) {
					log4j.info("Event History Pop up window is displayed." + Ae);
					strReason = "Event History Pop up window is displayed."
							+ Ae;
				}
			} else {
				log4j.info("Pop up window is NOT displayed");
				strReason = "Pop up window is NOT displayed";
			}
			selenium.selectFrame("relative=up");
			selenium.click("id=TB_closeWindowButton");

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function returnUserViews" + e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	 'Description  :Navigate to event status screen
	 'Arguments    :selenium,strEveName
	 'Returns      :strReason
	 'Date         :26-Feb-2013
	 'Author       :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String navToEventHistoryVerifyNew(Selenium selenium, String strEveName,
			String strEveDesc, String strUser) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// click on View History
			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
					+ strEveName
					+ "']/preceding-sibling::td/a[text()='View�History']");
			
			try{
				selenium.waitForPageToLoad("30000");
			}catch(Exception e){
				log4j.info("Wait for page load Not worked");
				
			}
			
			// Wait till pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 30) {
				try {
					selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}
			if (blnFound) {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertEquals("Event History",
							selenium.getText("id=TB_ajaxWindowTitle"));
					log4j.info("Event History Pop up window is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("//html/body/dl[1]/dt[text()='"
										+ strEveName + "']"));
						
						assertTrue(selenium
								.isElementPresent("//html/body/dl[1]/dd[@class='desc']"
										+ "[text()='"
										+ strEveDesc
										+ " (created by " + strUser + ")']"));
						
						
						log4j.info("Event Name " + strEveName
								+ "(< Created user full name>)is displayed.");

					} catch (AssertionError ae) {
						log4j.info("Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.");
						strReason = "Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.";
					}
					try {
						assertTrue(selenium
								.isElementPresent("//html/body/dl[2]/dt[text()='"
										+ strEveName + "']"));
						assertTrue(selenium
								.isElementPresent("//html/body/dl[2]/dd[@class='desc']"
										+ "[text()='Event ended (by "
										+ strUser
										+ ")']"));
						log4j.info("Event Name " + strEveName
								+ "(< Updated  user full name>)is displayed.");

					} catch (AssertionError ae) {
						log4j.info("Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.");
						strReason = "Event Name "
								+ strEveName
								+ "(< Created user full name>)is NOT displayed.";
					}
				} catch (AssertionError Ae) {
					log4j.info("Event History Pop up window is displayed." + Ae);
					strReason = "Event History Pop up window is displayed."
							+ Ae;
				}
			} else {
				log4j.info("Pop up window is NOT displayed");
				strReason = "Pop up window is NOT displayed";
			}
			selenium.selectFrame("relative=up");
			selenium.click("id=TB_closeWindowButton");

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function returnUserViews" + e.toString();
		}
		return strReason;
	}
}
