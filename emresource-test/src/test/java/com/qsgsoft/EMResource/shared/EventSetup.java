package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;


public class EventSetup {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.EventSetup");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	ReadData rdExcel;


	public String createEventTemplate(Selenium selenium) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try {
			// Click on Create New Template
			selenium.click(propElementDetails
					.getProperty("Event.CreateNewTemp"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Create New Event Template", selenium
							.getText(propElementDetails.getProperty("Header.Text")));

					break;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 70);
			try {
				assertEquals("Create New Event Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Create New Event Template' screen is displayed.");

			} catch (AssertionError ae) {
				log4j
				.info("'Create New Event Template' screen is NOT displayed.");
				strReason = "'Create New Event Template' screen is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/*******************************************************************
	'Description :select deselect resource in edit event page
	'Precondition :None
	'Arguments  :selenium,strRoleValue
	'Returns  :String
	'Date    :28-May-2012
	'Author   :QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'28-May-2012                               <Name>
	 ********************************************************************/

	public String slectAndDeselectRSEditEventPage(Selenium selenium,
			String strResource, boolean blnrS, boolean blnSave)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Resource check box for the particular
			// resource

			if (blnrS) {
				if (selenium
						.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']") == false) {
					selenium
					.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
							+ strResource
							+ "']/parent::tr/td[1]/input[@name='resourceID']");
				}
			} else {
				if (selenium
						.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']")) {
					selenium
					.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
							+ strResource
							+ "']/parent::tr/td[1]/input[@name='resourceID']");
				}
			}

			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
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
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
					strErrorMsg="'Event Management' screen is NOT displayed.";
				}
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectRSEditEventPage function failed" + e);
			strErrorMsg = "slectAndDeselectRSEditEventPage function failed"
					+ e;
		}
		return strErrorMsg;
	}

	public String navToEventSetupPge(Selenium selenium) throws Exception {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Event.EventSetUp")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);


			// Event tab
			selenium.mouseOver(propElementDetails.getProperty("EventLink"));
			// Click on Event Management link
			selenium.click(propElementDetails.getProperty("Event.EventSetUp"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("Event Template List", selenium.getText(propElementDetails.getProperty("Header.Text")));

					break;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 70);

			try {
				assertEquals("Event Template List", selenium.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Event Template List' screen is displayed.");

			} catch (AssertionError ae) {
				log4j.info("'Event Template List' screen is NOT displayed.");
				strReason = "'Event Template List' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info("navToEventSetupPge function failed" + e);
			strReason = "navToEventSetupPge function failed" + e;
		}
		return strReason;

	}

	/********************************************************
	   'Description :Fetch Event Value in Role List page
	   'Precondition :None
	   'Arguments  :selenium,strEvent
	   'Returns  :String
	   'Date    :17-July-2012
	   'Author   :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                    <Name>
	 ***********************************************************************************************************/
	public String fetchEventValueInEventList(Selenium selenium, String strEvent)
			throws Exception {
		String strEventValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/tbody/tr/td[6][text()='"
									+ strEvent + "']/parent::tr/td[1]/a"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			String strResValueArr[] = selenium.getAttribute(
					"//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
							+ strEvent + "']/parent::tr/td[1]/a@href").split(
									"eventID=");
			strEventValue = strResValueArr[1];
			log4j.info("Event Value is " + strEventValue);
		} catch (Exception e) {
			log4j.info("fetchEventValueInEventList function failed" + e);
			strEventValue = "";
		}
		return strEventValue;
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
	 ***************************************************************************************/	

	public String chkErrMsgInCreatNewEventTemplate(Selenium selenium,
			String strResourceType, String statTypeName) {
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue(selenium
						.isTextPresent("None of the selected status types are associated with resource type: "
								+ strResourceType + ""));
				log4j
				.info("Error message is displayed stating that the status type "
						+ statTypeName
						+ " "
						+ "is not associated with selected"
						+ strResourceType + "");
			} catch (AssertionError ae) {
				log4j
				.info("Error message is NOT displayed stating that the status type "
						+ statTypeName
						+ " "
						+ "is not associated with selected"
						+ strResourceType + "");
				strReason = "Error message is displayed stating that the status type "
						+ statTypeName
						+ " "
						+ "is not associated with selected"
						+ strResourceType
						+ "";
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
	public String chkErrMsgCreateEventPage(Selenium selenium, String strResource) {
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				
				assertTrue(selenium
						.isTextPresent("The following resources are not associated with any selected status types: "+strResource));
				log4j.info("Error message is displayed stating that The "
						+ "following resources are not associated with"
						+ " any status types for this event's template: "
						+ strResource + "");
			} catch (AssertionError ae) {
				log4j
				.info("Error message is NOT displayed stating that The following "
						+ "resources are not associated with"
						+ " any status types for this event's template: "
						+ strResource + "");
				strReason = "Error message is NOT displayed stating that The following "
						+ "resources are not associated with"
						+ " any status types for this event's template: "
						+ strResource + "";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/************************************************************
	'Description	:Verify Resources in edit event page
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	 ************************************************************/

	public String chkResInEditEventPage(Selenium selenium, String strRSval,
			boolean blnRes, String strResource, boolean blnSave)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnRes) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='resourceID'][value='"
									+ strRSval + "']"));
					log4j.info("Resource " + strResource
							+ " is listed under the 'Resources to Participate "
							+ "in This Event:' section. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "Resource "
							+ strResource
							+ " is NOT listed under the 'Resources to Participate "
							+ "in This Event:' section. ";
					log4j
					.info("Resource "
							+ strResource
							+ " is  NOT listed under the 'Resources to Participate "
							+ "in This Event:' section. ");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='resourceID'][value='"
									+ strRSval + "']"));
					log4j
					.info("Resources "
							+ strResource
							+ " is NOT listed under the 'Resources to Participate "
							+ "in This Event:' section. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "Resource " + strResource
							+ " is listed under the 'Resources to Participate "
							+ "in This Event:' section. ";
					log4j.info("Resource " + strResource
							+ " is listed under the 'Resources to Participate "
							+ "in This Event:' section. ");
				}
			}
			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkResInEditEventPage function failed" + e);
			strErrorMsg = "chkResInEditEventPage function failedd" + e;
		}
		return strErrorMsg;
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
	 ******************************************************************************************/	

	public String chkErrMsgInEditEventPage(Selenium selenium, String strResource) {
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsgTitle")));
				assertEquals(
						"The following resources are not associated with any selected status"
								+ " types: " + strResource + "", selenium
								.getText(propElementDetails
										.getProperty("UpdateStatus.ErrMsg")));
				log4j
				.info("Error message is displayed stating that The following resources are not associated with any "
						+ "status types: " + strResource + "");
			} catch (AssertionError ae) {
				log4j
				.info("Error message is NOT displayed stating that The following resources are not associated with any "
						+ "status types: " + strResource + "");
				strReason = "Error message is  NOT displayed stating that The following resources are not associated with any "
						+ "status types: " + strResource + "";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}



	/************************************************************
'Description	:Verify Resources in edit event page
'Precondition	:None
'Arguments		:selenium,strOptions,blnOptions,strRight
'Returns		:String
'Date	 		:10-july-2012
'Author			:QSG
'-----------------------------------------------------------
'Modified Date                            Modified By
'5-April-2012                               <Name>
	 ************************************************************/

	public String chkRSselOrNotInEditEvntPage(Selenium selenium,
			String strRSval, boolean blnRes, String strResource, boolean blnSave)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnRes) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strRSval + "']"));
					assertTrue(selenium
							.isEditable("css=input[name='resourceID'][value='"
									+ strRSval + "']"));
					log4j.info("Resource " + strResource
							+ " is selected and enabled");
				} catch (AssertionError Ae) {
					strErrorMsg = "Resource " + strResource + " is deselected ";
					log4j.info("Resource " + strResource + " is deselected ");
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strRSval + "']"));
					assertTrue(selenium
							.isEditable("css=input[name='resourceID'][value='"
									+ strRSval + "']"));
					log4j.info("Resource " + strResource
							+ " is deselected and enabled");
				} catch (AssertionError Ae) {
					strErrorMsg = "Resource " + strResource + " is selected ";
					log4j.info("Resource " + strResource + " is selected ");
				}
			}
			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkRSselOrNotInEditEvntPage function failed" + e);
			strErrorMsg = "chkRSselOrNotInEditEvntPage function failedd" + e;
		}
		return strErrorMsg;
	}


	/********************************************************************************************************
	   'Description :Fetch Event Template Value in Role List page
	   'Precondition :None
	   'Arguments  :selenium,strET
	   'Returns  :String
	   'Date    :17-July-2012
	   'Author   :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                    <Name>
	 *************************************************************************************/

	public String fetchETInETList(Selenium selenium, String strET)
			throws Exception {
		String strETValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strET + "']/parent::tr/td[1]/a"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			String strResValueArr[] = selenium.getAttribute(
					"//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
							+ strET + "']/parent::tr/td[1]/a@href").split(
									"eventTypeID=");
			strETValue = strResValueArr[1];
			log4j.info("ET Value is " + strETValue);
		} catch (Exception e) {
			log4j.info("fetchETInETList function failed" + e);
			strETValue = "";
		}
		return strETValue;
	}

	public String CreateETBySelctngRTAndST(Selenium selenium,
			String strTempName, String strTempDef, boolean blnCheckPage,
			String[] strResTypeValue, String[] strStatusTypeValue)
					throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		String strReason = "";
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			for (String s : strResTypeValue) {
				selenium.click("css=input[name='rt'][value='" + s + "']");

			}

			for (String s : strStatusTypeValue) {
				selenium.click("css=input[name='st'][value='" + s + "']");

			}
			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);


			if (blnCheckPage) {

				int intCnt=0;
				do{
					try{
						assertTrue(selenium
								.isTextPresent("Event Notification Preferences for "
										+ strTempName));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);

				try {
					assertTrue(selenium
							.isTextPresent("Event Notification Preferences for "
									+ strTempName));
					log4j.info("Event Template " + strTempName + "is created");
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Email')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Pager')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
						log4j
						.info("E-mail, Pager and Web checkboxes are displayed for each user.");

						// deselect Web(Select All) check box and save
						selenium
						.click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");
						selenium.click(propElementDetails
								.getProperty("Event.EventNotPref.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

					} catch (AssertionError ae) {
						log4j
						.info("E-mail, Pager and Web checkboxes are NOT displayed for each user.");
						strReason = "E-mail, Pager and Web checkboxes are NOT displayed for each user.";
					}

				} catch (AssertionError ae) {
					log4j.info("Event Template " + strTempName
							+ "is NOT created");
					strReason = "Event Template " + strTempName
							+ "is NOT created";

				}

			}


			int intCnt=0;
			do{
				try{
					/*assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName + "']"));
					 */

					assertTrue(selenium
							.isElementPresent("css=div[id='mainContainer']>table" +
									":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
									+ strTempName + "')"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			try {
				assertTrue(selenium.isTextPresent("Event Template List"));
				/*assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));*/

				assertTrue(selenium
						.isElementPresent("css=div[id='mainContainer']>table" +
								":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
								+ strTempName + "')"));

				assertEquals(
						strTempDef,
						selenium
						.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']/parent::tr/td[5]"));

				log4j
				.info("The template name and definition is displayed "
						+ "in the 'Event Template List' screen with the specified icon");
			} catch (AssertionError ae) {
				log4j
				.info("The template name and definition is NOT displayed "
						+ "in the 'Event Template List' screen with the specified icon");
				strReason = "The template name and definition is NOT displayed"
						+ " in the 'Event Template List' screen with the specified icon";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/*******************************************************************
	'Description :select deselect resource in edit event page
	'Precondition :None
	'Arguments  :selenium,strRoleValue
	'Returns  :String
	'Date    :28-May-2012
	'Author   :QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'28-May-2012                               <Name>
	 ********************************************************************/

	public String eventTime(Selenium selenium, String strGenDate,
			String strGenTimeHrs, String strGenTimeMin) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue(selenium
						.isElementPresent("css=span[class='eventStart'][contains()='Created By: fg @ "
								+ strGenDate
								+ " "
								+ strGenTimeHrs
								+ ":"
								+ strGenTimeMin + "']"));
				log4j.info("'Created By' shows time" + strGenDate + " "
						+ strGenTimeHrs + ":" + strGenTimeMin + "is displayed");
			} catch (AssertionError ae) {
				log4j.info("'Created By' shows time" + strGenDate + " "
						+ strGenTimeHrs + ":" + strGenTimeMin
						+ "is NOT displayed");

			}
		} catch (Exception e) {
			log4j.info("eventTime function failed" + e);
			strErrorMsg = "eventTime function failed" + e;
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Verify Status type in edit event page
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	 ************************************************************/

	public String chkSTInEditEventPage(Selenium selenium,
			String strStatusTypeval, boolean blnST, String statTypeName)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j.info("Status types " + statTypeName
							+ " is displayed in the 'Edit Event' page.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Status types " + statTypeName
							+ " is NOT displayed in the 'Edit Event' page.";
					log4j.info("Status types " + statTypeName
							+ " is NOT displayed in the 'Edit Event' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j.info("Status types " + statTypeName
							+ " is NOT displayed in the 'Edit Event' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Status types " + statTypeName
							+ " displayed in the 'Edit Event' page.";
					log4j.info("Status types " + statTypeName
							+ " displayed in the 'Edit Event' page.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkSTInEditEventPage function failed" + e);
			strErrorMsg = "chkSTInEditEventPage function failedd" + e;
		}
		return strErrorMsg;
	}

	/************************************************************
'Description	:Verify All the field in Create New Event Page
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:22-10-2012
'Author			:QSG
'-----------------------------------------------------------
'Modified Date                            Modified By
'<Date>		                             <Name>
	 ************************************************************/

	public String chkFieldsInCreateNewEventPge(Selenium selenium,
			String strResource) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			String strLabelAr[] = { "Title:**", "Information:**",
					"Event Start:**", "Event End:**", "Attached File:",
					"Display in Event Banner?", "Private?", "Drill?",
					"End Quietly?", "Re-notify Every:", "Street Address:",
					"City:", "State:", "Zip Code:", "County:",
					"Latitude/Longitude:",
			"Resources to Participate in This Event:" };

			for (int intLabel = 0; intLabel < strLabelAr.length; intLabel++) {
				try {
					assertEquals(
							strLabelAr[intLabel],
							selenium.getText("//div[@id='mainContainer']/form/table/tbody/tr["
									+ (intLabel + 1)
									+ "]/td[@class='emsLabel']"));
					log4j.info(strLabelAr[intLabel]
							+ " is displayed in the 'Create New Event' page.");

				} catch (AssertionError Ae) {
					strErrorMsg = strErrorMsg
							+ strLabelAr[intLabel]
									+ " is NOT displayed in the 'Create New Event' page.";
					log4j.info(strLabelAr[intLabel]
							+ " is NOT displayed in the 'Create New Event' page.");
				}
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.title")));
				log4j.info("Title Field is displayed in the 'Create New Event' page.");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Title Field is NOT displayed in the 'Create New Event' page.";
				log4j.info("Title Field is NOT displayed in the 'Create New Event' page.");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.Information")));
				log4j.info("Information Field is displayed in the 'Create New Event' page.");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Information Field is NOT displayed in the 'Create New Event' page.";
				log4j.info("Information Field is NOT displayed in the 'Create New Event' page.");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EveStart.Immediately")));
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("Event.CreateEve.EveStart.NOtImmediately")));

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.StartMnt")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.StartDay")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.StartYear")));

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.StartHour")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.StartMinut")));
				log4j.info("Event Start: (Radio Buttons a.Immediately b. Drop down to select date month and time) is displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Event Start: (Radio Buttons a.Immediately b. Drop down to select date month and time) is NOT displayed";
				log4j.info("Event Start: (Radio Buttons a.Immediately b. Drop down to select date month and time) is NOT displayed");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.HourOpt")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.DateOpt")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.Never")));

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.HourDuration")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EndMnt")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EndDay")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EndYear")));

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EndHour")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EndMinut")));
				log4j.info("Event End: Radio Buttons a. 24 hours after event starts b. Drop down to select date month and time c. Never) is displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Event End: Radio Buttons a. 24 hours after event starts b. Drop down to select date month and time c. Never) is NOT displayed";
				log4j.info("Event End: Radio Buttons a. 24 hours after event starts b. Drop down to select date month and time c. Never) is NOT displayed");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEVe.Browse")));

				log4j.info("Attached File: (Option to browse files for attachment) is present");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Attached File: (Option to browse files for attachment) is NOT present";
				log4j.info("Attached File: (Option to browse files for attachment) is NOT present");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.DispEveBanner")));

				log4j.info("Display in Event Banner?(Check Box) is present");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Display in Event Banner?(Check Box) is present is NOT present";
				log4j.info("Display in Event Banner?(Check Box) is present is NOT present");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.Private")));

				log4j.info("Private? (Check Box) is present");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Private? (Check Box) is present is NOT present";
				log4j.info("Private? (Check Box) is present is NOT present");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.Drill")));

				log4j.info("Drill? (Check Box)  is present");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Drill? (Check Box) is present is NOT present";
				log4j.info("Drill? (Check Box) is present is NOT present");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.EndQuite")));

				log4j.info("End Quietly? (Check Box) is present");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " End Quietly? (Check Box) is present is NOT present";
				log4j.info("End Quietly? (Check Box) is present is NOT present");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.StreetAddr")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.City")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.State")));

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.ZipCode")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.Country")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.Latitude")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Event.CreateEve.Longitude")));

				log4j.info("Street Address: (Text Field) City: State: Zip Code: County: (Drop down) Latitude/Longitude: fields are present");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " Street Address: (Text Field) City: State: Zip Code: County: (Drop down) Latitude/Longitude: fields are NOT present";
				log4j.info("Street Address: (Text Field) City: State: Zip Code: County: (Drop down) Latitude/Longitude: fields are NOT present");
			}

			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']"));

				log4j.info("List of Resources associated with event template with check boxes. is displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg
						+ " List of Resources associated with event template with check boxes. is NOT displayed";
				log4j.info("List of Resources associated with event template with check boxes. is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("chkFieldsInCreateNewEventPge function failed" + e);
			strErrorMsg = "chkFieldsInCreateNewEventPge function failedd" + e;
		}
		return strErrorMsg;
	}

	/********************************************************************************************************
'Description :Navigating edit event page
'Precondition :None
'Arguments  :selenium,strResource
'Returns  :String
'Date    :6-June-2012
'Author   :QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                    <Name>
	 ***********************************************************************************************************/	

	public String navToeditEventPage(Selenium selenium, String strEveName,
			boolean blnSave) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable"
									+ " striped border sortable']/tbody/tr/td[6][text()="
									+ "'"
									+ strEveName
									+ "']/parent::tr/td[1]/a[1]"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click("//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[6][text()="
					+ "'"
					+ strEveName + "']/parent::tr/td[1]/a[1]");

			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("Edit Event",
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

				assertEquals("Edit Event", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Edit Event' screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("'Edit Event' screen is NOT displayed");
				strReason = "'Edit Event' screen is NOT displayed";
			}

			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/********************************************************************************************************
'Description :Navigating Create New event page
'Precondition :None
'Arguments  :selenium,strEveTemp
'Returns  :String
'Date    :22-10-2012
'Author   :QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                    <Name>
	 ***********************************************************************************************************/	
	public String navToCreateNewEventPage(Selenium selenium, String strEveTemp)
			throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			// Click on Create for the particular Event template
			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
					+ strEveTemp
					+ "']/preceding-sibling::td/a[text()='Create']");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Create New Event",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Create New Event' screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("'Create New Event' screen is NOT displayed");
				strReason = "'Create New Event' screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/*******************************************************************
'Description :select deselect st in edit event page
'Precondition :None
'Arguments  :selenium,strRoleValue
'Returns  :String
'Date    :28-May-2012
'Author   :QSG
'------------------------------------------------------------------
'Modified Date                            Modified By
'28-May-2012                               <Name>
	 ********************************************************************/

	public String slectAndDeselectSTInEditEventPage(Selenium selenium,
			String strStatusTypeval, boolean blnST, boolean blnSave)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				if (selenium.isChecked("css=input[name='st'][value='"
						+ strStatusTypeval + "']") == false) {
					selenium.click("css=input[name='st'][value='"
							+ strStatusTypeval + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='st'][value='"
						+ strStatusTypeval + "']")) {
					selenium.click("css=input[name='st'][value='"
							+ strStatusTypeval + "']");
				}
			}

			if (blnSave) {

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Event Management",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
					strErrorMsg = "'Event Management' screen is NOT displayed.";
				}
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectSTInEditEventPage function failed" + e);
			strErrorMsg = "slectAndDeselectSTInEditEventPage function failedd"
					+ e;
		}
		return strErrorMsg;
	}

	/************************************************************
'Description	:Verify Status type in edit event page
'Precondition	:None
'Arguments		:selenium,strOptions,blnOptions,strRight
'Returns		:String
'Date	 		:10-july-2012
'Author			:QSG
'-----------------------------------------------------------
'Modified Date                            Modified By
'5-April-2012                               <Name>
	 ************************************************************/

	public String chkSTSelectedOrNotEditEventPage(Selenium selenium,
			String strStatusTypeval, boolean blnST,String statTypeName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				try {
					assertTrue(selenium.isChecked("css=input[name='st'][value='"+strStatusTypeval+"']"));
					assertFalse(selenium.isEditable("css=input[name='st'][value='"+strStatusTypeval+"']"));
					log4j.info("Status type "+statTypeName+" is selected and disabled'Edit Event' page.");

				} catch (AssertionError Ae) {
					strErrorMsg ="Status types "+statTypeName+" is NOT selected and NOT disabled'Edit Event' page.";
					log4j.info("Status types "+statTypeName+" is NOT selected and NOT disabled'Edit Event' page.");
				}
			} else {
				try {
					assertFalse(selenium.isChecked("css=input[name='st'][value='"+strStatusTypeval+"']"));
					log4j.info("Status type "+statTypeName+" is NOT selected and NOT disabled'Edit Event' page.");
				} catch (AssertionError Ae) {
					strErrorMsg ="Status types "+statTypeName+" is selected and disabled'Edit Event' page.";
					log4j.info("Status types "+statTypeName+" is selected and  disabled'Edit Event' page.");
				}
			}
		} catch (Exception e) {
			log4j
			.info("chkSTSelectedOrNotEditEventPage function failed"
					+ e);
			strErrorMsg = "chkSTSelectedOrNotEditEventPage function failed"
					+ e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	   'Description  :save the event and verify it is listed or not 
	   'Precondition :None
	   'Arguments    :selenium,
	   'Returns      :String
	   'Date         :10-July-2012
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '14-June-2012                               <Name>
	 ************************************************************************/


	public String saveAndvrfyEvent(Selenium selenium,  String strEveName,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");


			if (blnSave) {

				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Event.CreateEve.Save")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));
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
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					strReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/***********************************************************************
	   'Description  :save the event and verify it is listed or not 
	   'Precondition :None
	   'Arguments    :selenium,
	   'Returns      :String
	   'Date         :10-July-2012
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '14-June-2012                               <Name>
	 ************************************************************************/

	public String vrfyEventPresentOrNotInEventMngmtScreen(Selenium selenium, String strEveName,
			boolean blnPresent) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnPresent) {
				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
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

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					strReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
					strReason = "Event '" + strEveName
							+ "' is listed on 'Event Management' screen.";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:fill mandatory fields of event template with RT and ST
	'Precondition	:None
	'Arguments		:selenium,statTypeName,strStatTypDefn, blnSav	
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	 ************************************************************************/


	public String fillMandfieldsEveTempNew(Selenium selenium,
			String strTempName, String strTempDef, String strEveColor,
			boolean blnCheckPage, String[] strResTypeVal, 
			String[] strStatusTypeval,boolean blnST,boolean blnRT)
					throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Event.CreateTemp.TempName")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);


			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
							+ strEveColor);

			if (blnST) {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.click("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
				}
			} else {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.isChecked("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
					selenium.click("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
				}
			}

			if (blnRT) {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.click("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
				}
			} else {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.isChecked("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
					selenium.click("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
				}
			}
			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			if (blnCheckPage) {


				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isTextPresent("Event Notification Preferences for "
										+ strTempName));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<80);


				try {
					assertTrue(selenium
							.isTextPresent("Event Notification Preferences for "
									+ strTempName));
					log4j.info("Event Template " + strTempName + "is created");
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Email')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Pager')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
						log4j
						.info("E-mail, Pager and Web checkboxes are displayed for each user.");

						// deselect Web(Select All) check box and save

						intCnt=0;
						do{
							try {

								assertTrue(selenium.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;

							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<60);


						selenium
						.click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");
						selenium.click(propElementDetails
								.getProperty("Event.EventNotPref.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);
						Thread.sleep(5000);

					} catch (AssertionError ae) {
						log4j
						.info("E-mail, Pager and Web checkboxes are NOT displayed for each user.");
						strReason = "E-mail, Pager and Web checkboxes are NOT displayed for each user.";
					}

				} catch (AssertionError ae) {
					log4j.info("Event Template " + strTempName
							+ "is NOT created");
					strReason = "Event Template " + strTempName
							+ "is NOT created";
				}
			}
			try {
				assertTrue(selenium.isTextPresent("Event Template List"));
				log4j.info("Event Template List screen is displayed");
				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));
				log4j
				.info("The template name "+strTempName+" is displayed in the 'Event Template List' screen ");
			} catch (AssertionError ae) {
				log4j
				.info("The template name  is NOT displayed in the 'Event Template List' screen ");
				strReason = "The template is NOT displayed in the 'Event Template List' screen";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String fillMandfieldsAndSaveEveTemp(Selenium selenium,
			String strTempName, String strTempDef, String strEveColor,
			String strAsscIcon, String strIconSrc, String strEveTitle,
			String strEventDesc, String strStadEvType, String strAlertAudio,
			boolean blnCheckPage, String[] strResType, String[] strStatusType,
			boolean blnActive, boolean blnSecurity, boolean blnMultiReg)
					throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		String strReason = "";
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			if (strEveTitle.compareTo("") != 0) {
				// Default Event Title
				selenium.type("Event.CreateNewTemp.DefEventTitle", strEveTitle);
			}
			if (strEventDesc.compareTo("") != 0) {
				// Default Event Description
				selenium.type("Event.CreateNewTemp.DefEventDesc", strEventDesc);
			}

			// Select the Standard Event Type
			selenium.select("name=standardEventTypeID", "label="
					+ strStadEvType);

			if (strAlertAudio.compareTo("") != 0) {
				// select Alert Audio
				selenium.select("name=audioFilename", "label=" + strAlertAudio);
			}

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
							+ strEveColor);
			// Select the Associated Icon
			selenium.select(propElementDetails
					.getProperty("Event.CreateNewTemp.AssociIcon"), "label="
							+ strAsscIcon);

			if(blnActive){
				if(selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.Active"))==false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.Active"));

			}else{
				if(selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.Active")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.Active"));
			}

			if(blnSecurity){
				if(selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.Security"))==false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.Security"));

			}else{
				if(selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.Security")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.Security"));
			}

			if(blnMultiReg){
				if(selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.MultiReg"))==false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.MultiReg"));

			}else{
				if(selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.MultiReg")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.MultiReg"));
			}

			//select resource types
			if(strResType!=null){

				for(String strVal:strResType){
					selenium.click("css=input[name='rt'][value='"+strVal+"']");
				}

			}
			//select status types
			if(strStatusType!=null){

				for(String strVal:strStatusType){
					selenium.click("css=input[name='st'][value='"+strVal+"']");
				}
			}

			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			if (blnCheckPage) {

				int intCnt=0;
				do{
					try{
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Email')]/input[@name='SELECT_ALL']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);


				try {
					assertTrue(selenium
							.isTextPresent("Event Notification Preferences for "
									+ strTempName));
					log4j.info("Event Template " + strTempName + "is created");
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Email')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Pager')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
						log4j
						.info("E-mail, Pager and Web checkboxes are displayed for each user.");

						// deselect Web(Select All) check box and save
						selenium
						.click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");
						selenium.click(propElementDetails
								.getProperty("Event.EventNotPref.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

						intCnt=0;
						do{
							try{
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
												+ strTempName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							}
							catch(Exception e){
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<60);



					} catch (AssertionError ae) {
						log4j
						.info("E-mail, Pager and Web checkboxes are NOT displayed for each user.");
						strReason = "E-mail, Pager and Web checkboxes are NOT displayed for each user.";
					}

				} catch (AssertionError ae) {
					log4j.info("Event Template " + strTempName
							+ "is NOT created");
					strReason = "Event Template " + strTempName
							+ "is NOT created";

				}

			}
			try {
				assertTrue(selenium.isTextPresent("Event Template List"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));

				/*log4j.info(selenium.getAttribute("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
										+ strTempName + "']/parent::tr/td[5]"));

				log4j.info(selenium.getAttribute("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
						+ strTempName + "']/parent::tr/td[2]/img@src"));
				 */
				assertEquals(
						strIconSrc,
						selenium
						.getAttribute("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName
								+ "']/parent::tr/td[2]/img@src"));

				/*String str=selenium
				.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
						+ strTempName + "']/parent::tr/td[5]");
				//log4j.info(str);
				 */
				assertEquals(
						strTempDef,
						selenium
						.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']/parent::tr/td[5]"));




				log4j
				.info("The template name and definition is displayed " +
						"in the 'Event Template List' screen with the specified icon");
			} catch (AssertionError ae) {
				log4j
				.info("The template name and definition is NOT displayed " +
						"in the 'Event Template List' screen with the specified icon");
				strReason = "The template name and definition is NOT displayed" +
						" in the 'Event Template List' screen with the specified icon";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String checkEventHeadersInEventMgmt(Selenium selenium,
			String strHeader,String strColIndex) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

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

	public String createEventMandFlds(Selenium selenium, String strEveTemp,
			String strEveName, String strInfo, boolean blnSave)
					throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try{
					assertEquals("Select Event Template", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);


			try {
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table[@class='displayTable"
						+ " striped border sortable']/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");

				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt=0;
				do{
					try{
						assertEquals("Create New Event", selenium.getText(propElementDetails.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);


				try {

					selenium.selectWindow("");
					selenium.selectFrame("Data");

					assertEquals("Create New Event", selenium.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);

					if (blnSave) {
						// click on save
						selenium.click(propElementDetails.getProperty("Save"));

						selenium.waitForPageToLoad(gstrTimeOut);

						intCnt=0;
						do{
							try{
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								break;
							}catch(AssertionError Ae){
								Thread.sleep(1000);
								intCnt++;
							}
							catch(Exception e){
								Thread.sleep(1000);
								intCnt++;
							}
						}while(intCnt<60);


						try {
							assertEquals("Event Management", selenium
									.getText(propElementDetails.getProperty("Header.Text")));
							log4j
							.info("'Event Management' screen is displayed.");

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								log4j
								.info("Event '"
										+ strEveName
										+ "' is listed on 'Event Management' screen.");
							} catch (AssertionError ae) {
								log4j
								.info("Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.");
								strReason = "Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.";
							}

						} catch (AssertionError ae) {
							log4j
							.info("'Event Management' screen is NOT displayed.");
							strReason = "'Event Management' screen is NOT displayed.";
						}

					}

				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String editEvent(Selenium selenium, String strEveName,
			String strEditEveName, String strInfo, boolean blnSave)
					throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			General objGeneral = new General();

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			String strElementID = "//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[6][text()="
					+ "'"
					+ strEditEveName + "']";

			selenium
			.click("//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[6][text()="
					+ "'" + strEveName + "']/parent::tr/td[1]/a[1]");

			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Edit Event", selenium.getText(propElementDetails.getProperty("Header.Text")));
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
				assertEquals("", strReason);

				try {

					assertEquals("Edit Event", selenium.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Edit Event' screen is displayed");

					// Enter the title and description

					if (strEditEveName != "") {
						selenium.type(propElementDetails
								.getProperty("Event.CreateEve.title"),
								strEditEveName);
					}

					if (strInfo != "") {
						selenium.type(propElementDetails
								.getProperty("Event.CreateEve.Information"),
								strInfo);
					}

					if (blnSave) {
						// click on save
						selenium.click(propElementDetails.getProperty("Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

						intCnt = 0;
						do {
							try {

								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable"
												+ " striped border sortable']/tbody/tr/td[6][text()="
												+ "'" + strEditEveName + "']"));
								break;
							} catch (AssertionError Ae) {
								Thread.sleep(1000);
								intCnt++;

							} catch (Exception Ae) {
								Thread.sleep(1000);
								intCnt++;
							}
						} while (intCnt < 60);


						strReason = objGeneral.CheckForElements(selenium,
								strElementID);
						try {
							assertEquals("", strReason);
							log4j
							.info("The updated data is displayed on the 'Event Management' screen. ");
						} catch (AssertionError Ae) {
							log4j
							.info("The updated data is not displayed on the 'Event Management' screen. ");
							log4j.info(strReason);
						}

					}

				} catch (AssertionError ae) {
					log4j.info("'Edit Event' screen is NOT displayed");
					strReason = "'Edit Event' screen is NOT displayed";
				}

			} catch (AssertionError Ae) {
				log4j.info(Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String createEvent(Selenium selenium, String strEveTemp,
			String strResource, String strEveName, String strInfo,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				int intCnt=0;
				do{
					try {
						assertEquals("Select Event Template", selenium
								.getText(propElementDetails.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);

				assertEquals("Select Event Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
									+ strEveTemp
									+ "']/preceding-sibling::td/a[text()='Create']"));
					log4j.info("Event template 'ET' "+ strEveTemp+" is listed under it. ");
				}catch(AssertionError Ae){
					log4j.info("Event template 'ET' "+ strEveTemp+" is not listed under it. ");
					strReason = "Event template 'ET' "+ strEveTemp+" is not listed under it. ";

				} 
				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");
				selenium.waitForPageToLoad(gstrTimeOut);

				do{
					try {
						assertEquals("Create New Event",
								selenium.getText(propElementDetails.getProperty("Header.Text")));
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
					assertEquals("Create New Event", selenium.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ strResource + "']"));
						log4j
						.info("Resource "
								+ strResource
								+ " is displayed under 'Resources to Participate in This Event' section.");

						// Click on Resource check box for the particular
						// resource
						selenium
						.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']");

						if (blnSave) {
							// click on save
							selenium.click(propElementDetails
									.getProperty("Event.CreateEve.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							intCnt=0;
							do{
								try {

									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
													+ strEveName + "']"));
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
								assertEquals("Event Management", selenium.getText(propElementDetails.getProperty("Header.Text")));
								log4j.info("'Event Management' screen is displayed.");

								try {
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
													+ strEveName + "']"));
									log4j
									.info("Event '"
											+ strEveName
											+ "' is listed on 'Event Management' screen.");
								} catch (AssertionError ae) {
									log4j
									.info("Event '"
											+ strEveName
											+ "' is NOT listed on 'Event Management' screen.");
									strReason = "Event '"
											+ strEveName
											+ "' is NOT listed on 'Event Management' screen.";
								}

							} catch (AssertionError ae) {
								log4j.info("'Event Management' screen is NOT displayed.");
								strReason = "'Event Management' screen is NOT displayed.";
							}

						}

					} catch (AssertionError ae) {
						log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.");
						strReason = "Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.";
					}
				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String createEventWithAddr(Selenium selenium, String strEveTemp,
			String strResource, String strEveName, String strInfo,String strState,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Create New Event", selenium.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);

					selenium.select("id=stateAbbrev", "label="+strState);
					selenium.click("css=input[value='Lookup Address']");
					Thread.sleep(2000);
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ strResource + "']"));
						log4j
						.info("Resource "
								+ strResource
								+ " is displayed under 'Resources to Participate in This Event' section.");

						// Click on Resource check box for the particular
						// resource
						selenium
						.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']");

						if (blnSave) {
							// click on save
							selenium.click(propElementDetails
									.getProperty("Event.CreateEve.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								log4j
								.info("Event '"
										+ strEveName
										+ "' is listed on 'Event Management' screen.");
							} catch (AssertionError ae) {
								log4j
								.info("Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.");
								strReason = "Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.";
							}
						}

					} catch (AssertionError ae) {
						log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.");
						strReason = "Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.";
					}
				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String checkAttachedFilesForEvents(Selenium selenium,
			String strEveName, String strText) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// Click on Event name in event banner
			selenium
			.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
					+ strEveName + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent("link=Attached File"));
				log4j.info("'Attached file' link is displayed.");
				// click on Attached File link
				selenium.click("link=Attached File");

				// select the pop up
				selenium.waitForPopUp("EMResourceDocument", gstrTimeOut);
				selenium.selectWindow("name=EMResourceDocument");
				try {
					assertTrue(selenium.isTextPresent(strText));
					log4j
					.info("The attached file (while event creation) is opened.");

				} catch (AssertionError Ae) {
					log4j
					.info("The attached file (while event creation) is NOT opened.");
					strReason = "The attached file (while event creation) is NOT opened.";
				}

				selenium.close();
			} catch (AssertionError Ae) {
				log4j.info("'Attached file' link is NOT displayed.");
				strReason = "'Attached file' link is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String checkAttachedFilesForEventsWithFocus(Selenium selenium,
			String strEveName, String strEventValue, String strText)
					throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			} catch (Exception e) {

			}

			try {
				selenium
				.focus("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
						+ strEveName + "']");
			} catch (Exception e) {
				log4j.info("Event is focussed");
			}
			// Click on Event name in event banner
			selenium
			.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
					+ strEveName + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent("link=Attached File"));
				log4j.info("'Attached file' link is displayed.");
				// click on Attached File link
				// selenium.click("link=Attached File");

				selenium.click("//div[@id='ed" + strEventValue
						+ "']/a[text()='Attached File']");

				// select the pop up
				selenium.waitForPopUp("EMResourceDocument", gstrTimeOut);
				selenium.selectWindow("name=EMResourceDocument");
				try {
					assertTrue(selenium.isTextPresent(strText));
					log4j
					.info("The attached file (while event creation) is opened.");

				} catch (AssertionError Ae) {
					log4j
					.info("The attached file (while event creation) is NOT opened.");
					strReason = "The attached file (while event creation) is NOT opened.";
				}

				selenium.close();
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			} catch (AssertionError Ae) {
				log4j.info("'Attached file' link is NOT displayed.");
				strReason = "'Attached file' link is NOT displayed.";
			}

			try {
				selenium
				.focus("//div[@id='eventsBanner']/table/tbody/tr/td[1]/a");
			} catch (Exception e) {
				log4j.info("Event is focussed");
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String createEvent_FillOtherFields(Selenium selenium,
			String strEveName, boolean blnDispEveBan, boolean blnPrivate,
			boolean blnDrill, boolean blnEndQuite, boolean blnImmediate,
			String strEventEnd, String strHrDuration, String strStartDateTime,
			String strEndDateTime, boolean blnReNotify,
			String strReNotifyPeriod, String strStreet, String strCity,
			String strState, String strZipCode, String strCountry,
			String strLatd, String strLogt) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			if (blnDispEveBan) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.DispEveBanner")) == false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.DispEveBanner"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.DispEveBanner")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.DispEveBanner"));
			}

			if (blnPrivate) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.private")) == false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.private"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.private")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.private"));
			}

			if (blnDrill) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.Drill")) == false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.Drill"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.Drill")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.Drill"));
			}

			if (blnEndQuite) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.EndQuite")) == false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.EndQuite"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEve.EndQuite")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateEve.EndQuite"));
			}

			if (blnImmediate) {

				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.EveStart.Immediately"));
			} else {

				selenium
				.click(propElementDetails
						.getProperty("Event.CreateEve.EveStart.NOtImmediately"));
				// Enter start Date and Time
				String strStartDtTimeArr[] = strStartDateTime.split(" ");

				String strStDateArr[] = strStartDtTimeArr[0].split("/");
				String strStTimeArr[] = strStartDtTimeArr[1].split(":");

				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"), "label="
								+ strStDateArr[0]);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartDay"), "label="
								+ strStDateArr[1]);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartYear"), "label="
								+ strStDateArr[2]);

				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartHour"), "label="
								+ strStTimeArr[0]);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"), "label="
								+ strStTimeArr[1]);

			}

			if (strEventEnd.equals("Hours")) {
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.HourOpt"));
				// Enter number of hours
				selenium.type(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.HourDuration"),
						strHrDuration);
			} else if (strEventEnd.equals("Date")) {
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.DateOpt"));

				// Enter end Date and Time
				String strEndDtTimeArr[] = strEndDateTime.split(" ");

				String strEndDateArr[] = strEndDtTimeArr[0].split("/");
				String strEndTimeArr[] = strEndDtTimeArr[1].split(":");

				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"), "label="
								+ strEndDateArr[0]);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndDay"), "label="
								+ strEndDateArr[1]);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndYear"), "label="
								+ strEndDateArr[2]);

				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndHour"), "label="
								+ strEndTimeArr[0]);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"), "label="
								+ strEndTimeArr[1]);

			} else {
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.Never"));
			}

			// Enter street address
			selenium.type(propElementDetails
					.getProperty("Event.CreateEve.StreetAddr"), strStreet);
			// Enter city
			selenium.type(propElementDetails
					.getProperty("Event.CreateEve.City"), strCity);
			// select state
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.State"), "label=" + strState);
			// enter zipcode
			selenium.type(propElementDetails
					.getProperty("Event.CreateEve.ZipCode"), strZipCode);
			// select country
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.Country"), strCountry);
			// Enter longitude and latitude
			selenium.type(propElementDetails
					.getProperty("Event.CreateEve.Latitude"), strLatd);
			selenium.type(propElementDetails
					.getProperty("Event.CreateEve.Longitude"), strLogt);

			if (blnReNotify) {
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Renotify.Hours"));
				// Select Hours for Re-notify
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.Renotify.HoursPeriod"),
						strReNotifyPeriod);

			} else {
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Renotify.Disabled"));
			}

			// click on save
			selenium.click(propElementDetails
					.getProperty("Event.CreateEve.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
								+ strEveName + "']"));
				log4j.info("Event '" + strEveName
						+ "' is listed on 'Event Management' screen.");
			} catch (AssertionError ae) {
				log4j.info("Event '" + strEveName
						+ "' is NOT listed on 'Event Management' screen.");
				strReason = "Event '" + strEveName
						+ "' is NOT listed on 'Event Management' screen.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public boolean navToEventManagement(Selenium selenium) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		// Event tab
		selenium.mouseOver(propElementDetails.getProperty("EventLink"));
		// Click on Event Management link
		selenium.click(propElementDetails.getProperty("Event.EventManagMent"));
		selenium.waitForPageToLoad(gstrTimeOut);

		int intCnt=0;
		do{
			try{
				assertEquals("Event Management", selenium.getText(propElementDetails.getProperty("Header.Text")));
				break;
			}catch(AssertionError Ae){
				Thread.sleep(1000);
				intCnt++;
			}
			catch(Exception e){
				Thread.sleep(1000);
				intCnt++;
			}
		}while(intCnt<60);


		try {
			assertEquals("Event Management", selenium.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("'Event Management' screen is displayed.");
			return true;
		} catch (AssertionError ae) {
			log4j.info("'Event Management' screen is NOT displayed.");
			return false;
		}
	}

	public String editEventTemplate(Selenium selenium, String strEveTemp)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium
			.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[text()='"
					+ strEveTemp
					+ "']/preceding-sibling::td/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Event Template", selenium.getText("css=h1"));
				log4j.info("'Edit Event Template' screen is displayed.");
			} catch (AssertionError ae) {
				log4j.info("'Edit Event Template' screen is NOT displayed.");
				strReason = "'Edit Event Template' screen is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public boolean navToEventSetup(Selenium selenium) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		// Event tab
		selenium.mouseOver(propElementDetails.getProperty("EventLink"));
		// Click on Event Management link
		selenium.click(propElementDetails.getProperty("Event.EventSetUp"));
		selenium.waitForPageToLoad(gstrTimeOut);

		try {
			assertEquals("Event Template List", selenium.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("'Event Template List' screen is displayed.");
			return true;
		} catch (AssertionError ae) {
			log4j.info("'Event Template List' screen is NOT displayed.");
			return false;
		}
	}


	/************************************************************
	'Description	:Verify  Multi-Region Event is vreated
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:17-April-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	 **************************************************************/

	public String createMultiRegnEventMandatory(Selenium selenium,
			String strMultiEventName, String strEventDescription,
			String[] strEvntTemplateNames) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();


			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateMultiRegnEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create Multi-Region Event", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create Multi-Region Event page is displayed");

				selenium.type(propElementDetails
						.getProperty("CreateMultiRegnEvent.Title"),
						strMultiEventName);
				selenium.type(propElementDetails
						.getProperty("CreateMultiRegnEvent.Descrptn"),
						strEventDescription);

				for (String s : strEvntTemplateNames) {

					if (selenium.isChecked(s) == false) {
						selenium.click(s);
					}

				}

				selenium.click(propElementDetails
						.getProperty("CreateMultiRegnEvent.Start"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Multi-Region Event Confirmation", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j
					.info("Multi-Region Event Confirmation page is  displayed");

					selenium.click(propElementDetails
							.getProperty("CreateMultiRegnEvent.Yes"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Multi-Region Event Status", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j
						.info("Multi-Region Event Status page is  displayed");

						selenium.click(propElementDetails
								.getProperty("CreateMultiRegnEvent.Done"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							assertEquals("Event Management", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));

							log4j.info("Event Management page is  displayed");

						} catch (AssertionError Ae) {
							strErrorMsg = "Event Managementpage is NOT displayed"
									+ Ae;
							log4j
							.info("Event Management page is NOT displayed");
						}

					} catch (AssertionError Ae) {
						strErrorMsg = "Multi-Region Event Status page is NOT displayed"
								+ Ae;
						log4j
						.info("Multi-Region Event Status page is NOT displayed");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Multi-Region Event Confirmation page is NOT displayed"
							+ Ae;
					log4j
					.info("Multi-Region Event Confirmation page is NOT displayed");
				}

			} catch (AssertionError Ae) {

			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}

	public String selectEventSecurityforUser(Selenium selenium,
			String strEveTemp, String[] strUserNameCheck,
			String[] strUserNameSel) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// click on Security link for Event template
			selenium
			.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
					+ strEveTemp
					+ "']/parent::tr/td[1]/a[text()='Security']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isTextPresent("Event Security for "
						+ strEveTemp));
				log4j.info("Event Security for " + strEveTemp
						+ " screen is displayed. ");

				for (int intRec = 0; intRec < strUserNameCheck.length; intRec++) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_userID']/tbody/tr/td[text()='"
										+ strUserNameCheck[intRec] + "']"));
						log4j.info("User " + strUserNameCheck[intRec]
								+ " is displayed");
					} catch (AssertionError ae) {
						log4j.info("User " + strUserNameCheck[intRec]
								+ "is NOT displayed");
						strReason = strReason + "User "
								+ strUserNameCheck[intRec]
										+ " is NOT displayed";

					}
				}

				for (int intRec = 0; intRec < strUserNameSel.length; intRec++) {
					// select the user check box
					selenium
					.click("//table[@id='tbl_userID']/tbody/tr/td[text()='"
							+ strUserNameSel[intRec]
									+ "']/parent::tr/td[1]/input");

				}
				// click on save button
				selenium.click(propElementDetails
						.getProperty("Event.EventSecurity.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Template List", selenium
							.getText("css=h1"));
					log4j.info("'Event Template List' screen is displayed.");

				} catch (AssertionError ae) {
					log4j
					.info("'Event Template List' screen is NOT displayed.");
					strReason = "'Event Template List' screen is NOT displayed.";
				}

			} catch (AssertionError ae) {
				log4j.info("Event Security for " + strEveTemp
						+ " screen is NOT displayed.");
				strReason = "Event Security for " + strEveTemp
						+ " screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;

	}

	public String endEvent(Selenium selenium, String strEveName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objGeneral = new General();

		try {
			// click on End button for the event
			selenium
			.click("//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[6][text()="
					+ "'"
					+ strEveName
					+ "']/parent::tr/td[1]/a[text()='End']");

			boolean blnFound = false;
			int intCnt = 0;
			// wait for the confirmation
			while (blnFound == false && intCnt <= 60) {
				try {
					assertTrue(selenium
							.getConfirmation()
							.matches(
									"^Are you sure you want"
											+ " to end this event[\\s\\S]\n\nPress OK to end the event\\. "
											+ "Press Cancel if you do NOT want to end the event\\.$"));
					blnFound = true;

				} catch (AssertionError ae) {
					intCnt++;
					Thread.sleep(1000);
				} catch (Exception e) {
					intCnt++;
					Thread.sleep(1000);
				}
			}

			Thread.sleep(30000);
			try {

				String strElementID = "//div[@id='mainContainer']/table/"
						+ "tbody/tr/td[6][text()='" + strEveName + "']/"
						+ "parent::tr/td[1]/a";
				strReason = objGeneral.CheckForElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				log4j.info(strReason);
			}

			try {

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table"
								+ "/tbody/tr/td[text()='"
								+ strEveName
								+ "']"
								+ "/parent::tr/td/a[contains(text(),'View')]"));

				log4j
				.info("The updated data is displayed on the 'Event Management' screen. ");

			} catch (AssertionError Ae) {
				log4j
				.info("The updated data is NOT displayed on the 'Event Management' screen. ");
				strReason = "The updated data is NOT displayed on the 'Event Management' screen.";

			}


		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String createNewEvent(Selenium selenium, String strEveTemp,
			String strResource, String strEveName, String strInfo,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try{
					assertEquals("Select Event Template", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);


			try {
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt=0;
				do{
					try{
						assertEquals("Create New Event", selenium
								.getText(propElementDetails.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);

				try {
					assertEquals("Create New Event", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);

					try {
						if (strResource != null) {
							assertTrue(selenium
									.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
											+ strResource + "']"));
							log4j
							.info("Resource "
									+ strResource
									+ " is displayed under 'Resources to Participate in This Event' section.");

							// Click on Resource check box for the particular
							// resource
							selenium
							.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
									+ strResource
									+ "']/parent::tr/td[1]/input[@name='resourceID']");
						}

						if (blnSave) {
							// click on save
							selenium.click(propElementDetails
									.getProperty("Event.CreateEve.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							intCnt=0;
							do{
								try{
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
													+ strEveName + "']"));
									selenium.isElementPresent(propElementDetails.getProperty("CreateNewUsrLink"));
									break;
								}catch(AssertionError Ae){
									Thread.sleep(1000);
									intCnt++;
								}
								catch(Exception e){
									Thread.sleep(1000);
									intCnt++;
								}
							}while(intCnt<60);

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								log4j
								.info("Event '"
										+ strEveName
										+ "' is listed on 'Event Management' screen.");
							} catch (AssertionError ae) {
								log4j
								.info("Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.");
								strReason = "Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.";
							}
						}

					} catch (AssertionError ae) {
						log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.");
						strReason = "Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.";
					}
				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}



	public String fillMandFieldsAndCreateEvent(Selenium selenium, 
			String strResource, String strEveName, String strInfo,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Enter the title and description
			selenium.type(propElementDetails
					.getProperty("Event.CreateEve.title"), strEveName);
			selenium.type(propElementDetails
					.getProperty("Event.CreateEve.Information"),
					strInfo);


			if (strResource != "") {
				assertTrue(selenium
						.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource + "']"));
				log4j
				.info("Resource "
						+ strResource
						+ " is displayed under 'Resources to Participate in This Event' section.");

				// Click on Resource check box for the particular
				// resource
				selenium
				.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
						+ strResource
						+ "']/parent::tr/td[1]/input[@name='resourceID']");
			}

			if (blnSave) {
				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j
					.info("Event '"
							+ strEveName
							+ "' is listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j
					.info("Event '"
							+ strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					strReason = "Event '"
							+ strEveName
							+ "' is NOT listed on 'Event Management' screen.";
				}
			}


		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String checkUserInfoInEventBanner(Selenium selenium,
			String strUsrInfo,boolean blnViewHistory) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals(strUsrInfo,selenium
						.getText("//div[starts-with(@id,'ed')]"));
				log4j
				.info("Created by is displayed along with time of event creation and event information.");
			} catch (AssertionError ae) {
				log4j
				.info("Created by is NOT displayed along with time of event creation and event information.");
				strReason = "Created by is NOT displayed along with time of event creation and event information.";
			}			

			if(blnViewHistory){
				try {
					assertTrue(selenium
							.isElementPresent("link=View�History"));
					log4j
					.info("'Event History' link is present on event banner. ");
				} catch (AssertionError ae) {
					log4j
					.info("'Event History' link is not present on event banner.");
					strReason = "'Event History' link is not present on event banner.";
				}			
			}else{
				try {
					assertFalse(selenium
							.isElementPresent("link=View�History"));
					log4j
					.info("'Event History' link is not present on event banner. ");
				} catch (AssertionError ae) {
					log4j
					.info("'Event History' link is present on event banner.");
					strReason = "'Event History' link is present on event banner.";
				}			
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/***********************************************************************
	'Description	:save and verify event template
	'Precondition	:None
	'Arguments		:selenium,statTypeName,strStatTypDefn, blnSav	
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	 ************************************************************************/

	public String savAndVerifyEventtemplate(Selenium selenium,
			String strTempName) throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				/*assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));*/

				assertTrue(selenium
						.isElementPresent("css=div[id='mainContainer']>table" +
								":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
								+ strTempName + "')"));

				log4j.info("The template name " + strTempName
						+ " is displayed in the 'Event Template List' screen ");
			} catch (AssertionError ae) {
				log4j
				.info("The template name "
						+ strTempName
						+ " is NOT displayed in the 'Event Template List' screen ");
				strReason = "The template name "
						+ strTempName
						+ " is NOT displayed in the 'Event Template List' screen .";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}	
	/*******************************************************************************************
	' Description: Canel  and navigating to event template list
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 24/07/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	 *******************************************************************************************/

	public String cancelAndNavToEventTemplatePage(Selenium selenium) throws Exception
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
			selenium.click(propElementDetails.getProperty("Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Event Template List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Event Template List' screen is displayed.");

			} catch (AssertionError ae) {
				log4j.info("'Event Template List' screen is NOT displayed.");
				lStrReason="'Event Template List' screen is NOT displayed.";
			}

		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}


	/************************************************************
	'Description	:Verify Status type in edit event page
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	 ************************************************************/

	public String chkSTSelOrNotEditEvntTemplatePage(Selenium selenium,
			String strStatusTypeval, boolean blnST, String statTypeName)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j.info("Status type " + statTypeName
							+ " is selected 'Edit Event'Template page.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Status types " + statTypeName
							+ " is NOT selected 'in Edit Event'template page.";
					log4j.info("Status types " + statTypeName
							+ " is NOT selected 'in Edit Event' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j.info("Status types " + statTypeName
							+ " is NOT selected 'in Edit Event'template page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Status type " + statTypeName
							+ " is selected 'Edit Event'Template page.";
					log4j.info("Status type " + statTypeName
							+ " is selected 'Edit Event'Template page.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkSTSelectedOrNotEditEventPage function failed" + e);
			strErrorMsg = "chkSTSelectedOrNotEditEventPage function failed" + e;
		}
		return strErrorMsg;
	}

	/************************************************************
'Description	:Verify Status type in edit event page
'Precondition	:None
'Arguments		:selenium,strOptions,blnOptions,strRight
'Returns		:String
'Date	 		:10-july-2012
'Author			:QSG
'-----------------------------------------------------------
'Modified Date                            Modified By
'5-April-2012                               <Name>
	 ************************************************************/

	public String chkSTInEditEventTemplatePage(Selenium selenium,
			String strStatusTypeval, boolean blnST, String statTypeName)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j
					.info("Status types "
							+ statTypeName
							+ " is displayed in the 'Edit Event Template' page.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Status types "
							+ statTypeName
							+ " is NOT displayed in the 'Edit Event Template' page.";
					log4j.info("Status types " + statTypeName
							+ " is NOT displayed in the 'Edit Event' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j
					.info("Status types "
							+ statTypeName
							+ " is NOT displayed in the 'Edit Event Template' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Status types " + statTypeName
							+ " displayed in the 'Edit Event Template' page.";
					log4j.info("Status types " + statTypeName
							+ " displayed in the 'Edit Event Template' page.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkSTInEditEventTemplatePage function failed" + e);
			strErrorMsg = "chkSTInEditEventTemplatePage function failedd" + e;
		}
		return strErrorMsg;
	}
	/************************************************************
	'Description	:Verify Status type in edit event page
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	 ************************************************************/

	public String chkSTSelOrNotEditEventTemplatePage(Selenium selenium,
			String strStatusTypeval, boolean blnST,String statTypeName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				try {
					assertTrue(selenium.isChecked("css=input[name='st'][value='"+strStatusTypeval+"']"));
					log4j.info("Status type "+statTypeName+" is selected in 'Edit Event template' page.");

				} catch (AssertionError Ae) {
					strErrorMsg ="Status types "+statTypeName+" is NOT selected in 'Edit Event template' page.";
					log4j.info("Status types "+statTypeName+" is NOT selected in 'Edit Event template' page.");
				}
			} else {
				try {
					assertFalse(selenium.isChecked("css=input[name='st'][value='"+strStatusTypeval+"']"));
					log4j.info("Status type "+statTypeName+" is NOT selected in 'Edit Event template' page.");
				} catch (AssertionError Ae) {
					strErrorMsg ="Status types "+statTypeName+" is selected in 'Edit Event template' page.";
					log4j.info("Status types "+statTypeName+" is selected in 'Edit Event template' page.");
				}
			}
		} catch (Exception e) {
			log4j
			.info("chkSTSelOrNotEditEventTemplatePage function failed"
					+ e);
			strErrorMsg = "chkSTSelOrNotEditEventTemplatePage function failed"
					+ e;
		}
		return strErrorMsg;
	}


	/************************************************************
	'Description	:Verify Status type in edit event page
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	 ************************************************************/

	public String chkSTInCreateEventTemplatePage(Selenium selenium,
			String strStatusTypeval, boolean blnST, String statTypeName)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j
					.info("Status types "
							+ statTypeName
							+ " is displayed in the 'Create Event Template' page.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Status types "
							+ statTypeName
							+ " is NOT displayed in the 'Create Event Template' page.";
					log4j.info("Status types " + statTypeName
							+ " is NOT displayed in the 'Create Event' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='st'][value='"
									+ strStatusTypeval + "']"));
					log4j
					.info("Status types "
							+ statTypeName
							+ " is NOT displayed in the 'Create Event Template' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Status types " + statTypeName
							+ " displayed in the 'Create Event Template' page.";
					log4j.info("Status types " + statTypeName
							+ " displayed in the 'Create Event Template' page.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkSTInCreateEventTemplatePage function failed" + e);
			strErrorMsg = "chkSTInCreateEventTemplatePage function failedd" + e;
		}
		return strErrorMsg;
	}

	/*******************************************************************************************
		' Description: Canel  and navigating to event template list
		' Precondition: N/A 
		' Arguments: selenium
		' Returns: String 
		' Date: 24/07/2012
		' Author: QSG 
		'--------------------------------------------------------------------------------- 
		' Modified Date: 
		' Modified By: 
	 *******************************************************************************************/

	public String cancelAndNavToEventManagementScreen(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click(propElementDetails.getProperty("Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Event Management", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Event Management' screen is displayed.");
			} catch (AssertionError ae) {
				log4j.info("'Event Management' screen is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	/*******************************************************************************************
	' Description: Canel  and navigating to event template list
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 24/07/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	 *******************************************************************************************/

	public String saveAndNavToEventManagementScreen(Selenium selenium,
			boolean blnSave) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnSave) {
				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

			try {
				assertEquals("Event Management", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Event Management' screen is displayed.");
			} catch (AssertionError ae) {
				log4j.info("'Event Management' screen is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}


	/********************************************************************************************************
'Description :select event template color 
'Precondition :None
'Arguments  :selenium,strResource
'Returns  :String
'Date    :6-June-2012
'Author   :QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                    <Name>
	 ***********************************************************************************************************/	

	public String selEventTemplateColor(Selenium selenium, String strEveColor,
			boolean blnSave) throws Exception {
		String lStrReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
							+ strEveColor);

			if (blnSave) {
				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}

	/********************************************************************************************************
	'Description :Navigate and verift event template
	'Precondition :None
	'Arguments  :selenium,strResource
	'Returns  :String
	'Date    :6-June-2012
	'Author   :QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	 ***********************************************************************/	

	public String navToSelEventTemplatePage(Selenium selenium,
			String strTempName, boolean blnSelEvent) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnSelEvent) {

				// Click on Create New Event
				selenium.click(propElementDetails
						.getProperty("Event.CreateEvent"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Select Event Template", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Select Event Template' screen is displayed.");
				} catch (AssertionError ae) {
					log4j
					.info("'Select Event Template' screen is NOT displayed.");
					strReason = "'Select Event Template' screen is NOT displayed.";
				}
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
								+ strTempName + "']"));

				/*assertTrue(selenium
						.isElementPresent("css=div[id='mainContainer']>table" +
								":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
								+ strTempName + "')"));*/
				log4j
				.info("The template  "
						+ strTempName
						+ " is displayed in the 'select event Template List' screen ");
			} catch (AssertionError ae) {
				log4j
				.info("The template "
						+ strTempName
						+ " is NOT displayed in the 'select Event Template List' screen ");
				strReason = "The template "
						+ strTempName
						+ " is NOT displayed in the 'select Event Template List' screen";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/********************************************************************************************************
	'Description :Navigate and verift event template
	'Precondition :None
	'Arguments  :selenium,strResource
	'Returns  :String
	'Date    :6-June-2012
	'Author   :QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	 ***********************************************************************************************************/		


	public String createEventonlyMandFlds(Selenium selenium,String strEveTemp,
			String strEveName, String strInfo, boolean blnSave)
					throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Create for the particular Event template
			selenium
			.click("//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[text()='"
					+ strEveTemp
					+ "']/preceding-sibling::td/a[text()='Create']");

			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create New Event", selenium.getText("css=h1"));
				log4j.info("'Create New Event' screen is displayed");

				// Enter the title and description
				selenium.type(propElementDetails
						.getProperty("Event.CreateEve.title"), strEveName);
				selenium.type(propElementDetails
						.getProperty("Event.CreateEve.Information"),
						strInfo);

				if (blnSave) {
					// click on save
					selenium.click(propElementDetails
							.getProperty("Save"));

					selenium.waitForPageToLoad(gstrTimeOut);


					try {
						assertEquals("Event Management", selenium.getText("css=h1"));
						log4j.info("'Event Management' screen is displayed.");

						try {
							/*	assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']" +
													"/table/tbody/tr/td[6][text()='"
													+ strEveName + "']"));*/

							assertTrue(selenium
									.isElementPresent("css=div[id='mainContainer']" +
											">table>tbody>tr>td:nth-child(6):contains('"
											+ strEveName + "')"));

							log4j
							.info("Event '"
									+ strEveName
									+ "' is listed on 'Event Management' screen.");
						} catch (AssertionError ae) {
							log4j
							.info("Event '"
									+ strEveName
									+ "' is NOT listed on 'Event Management' screen.");
							strReason = "Event '"
									+ strEveName
									+ "' is NOT listed on 'Event Management' screen.";
						}

					} catch (AssertionError ae) {
						log4j.info("'Event Management' screen is NOT displayed.");
						strReason = "'Event Management' screen is NOT displayed.";
					}


				}

			} catch (AssertionError ae) {
				log4j
				.info("'Create New Event' screen is NOT displayed");
				strReason = "'Create New Event' screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/***********************************************************************
	 'Description :fill mandatory fields of event template with RT and ST
	 'Precondition :None
	 'Arguments  :selenium,statTypeName,strStatTypDefn, blnSav 
	 'Returns  :String
	 'Date    :12-April-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '12-April-2012                               <Name>
	 ************************************************************************/


	public String filMndfldsEveTempAsMultiEveTemp(Selenium selenium,
			String strTempName, String strTempDef, String strEveColor,
			boolean blnMultiReg, boolean blnCheckPage, String[] strResTypeVal,
			String[] strStatusTypeval, boolean blnST, boolean blnRT)
					throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
							+ strEveColor);

			if (blnMultiReg) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.MultiReg")) == false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.MultiReg"));

			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.MultiReg")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.MultiReg"));
			}

			if (blnST) {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.click("css=input[name='st'][value='"
							+ strStatusTypeval[i] + "']");
				}
			} else {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					if(selenium.isChecked("css=input[name='st'][value='"
							+ strStatusTypeval[i] + "']"))
						selenium.click("css=input[name='st'][value='"
								+ strStatusTypeval[i] + "']");
				}
			}

			if (blnRT) {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.click("css=input[name='rt'][value='"
							+ strResTypeVal[i] + "']");
				}
			} else {
				for (int i = 0; i < strResTypeVal.length; i++) {
					if(selenium.isChecked("css=input[name='rt'][value='"
							+ strResTypeVal[i] + "']"))
						selenium.click("css=input[name='rt'][value='"
								+ strResTypeVal[i] + "']");
				}
			}
			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			if (blnCheckPage) {

				try {
					assertTrue(selenium
							.isTextPresent("Event Notification Preferences for "
									+ strTempName));
					log4j.info("Event Template " + strTempName + "is created");
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Email')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Pager')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
						log4j
						.info("E-mail, Pager and Web checkboxes are displayed for each user.");

						// deselect Web(Select All) check box and save
						selenium
						.click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");
						selenium.click(propElementDetails
								.getProperty("Event.EventNotPref.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

					} catch (AssertionError ae) {
						log4j
						.info("E-mail, Pager and Web checkboxes are NOT displayed for each user.");
						strReason = "E-mail, Pager and Web checkboxes are NOT displayed for each user.";
					}

				} catch (AssertionError ae) {
					log4j.info("Event Template " + strTempName
							+ "is NOT created");
					strReason = "Event Template " + strTempName
							+ "is NOT created";
				}
			}
			try {
				assertTrue(selenium.isTextPresent("Event Template List"));
				log4j.info("Event Template List screen is displayed");


				/*assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));
				 */

				assertTrue(selenium
						.isElementPresent("css=div[id='mainContainer']>table" +
								":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
								+ strTempName + "')"));


				log4j.info("The template name " + strTempName
						+ " is displayed in the 'Event Template List' screen ");
			} catch (AssertionError ae) {
				log4j
				.info("The template name  is NOT displayed in the 'Event Template List' screen ");
				strReason = "The template is NOT displayed in the 'Event Template List' screen";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	//start//selAndDeselMultiRegionChkBox//
	/*******************************************************************************************
	' Description : select And Deselt Multi Region Check Box
	' Precondition: N/A 
	' Arguments   : selenium, blnMultiReg
	' Returns     : String 
	' Date        : 29/07/2013
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String selAndDeselMultiRegionChkBox(Selenium selenium,
			boolean blnMultiReg) throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnMultiReg) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.MultiReg")) == false)
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.MultiReg"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateNewTemp.MultiReg")))
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewTemp.MultiReg"));
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	//end//selAndDeselMultiRegionChkBox//
	/************************************************************
		'Description	:Verify  Multi-Region Event is vreated
		'				 for given user
		'Precondition	:None
		'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
		'			     strEventDescription
		'Returns		:String
		'Date	 		:17-April-2012
		'Author			:QSG
		'-------------------------------------------------------------
		'Modified Date                            Modified By
		'18-April-2012                               <Name>
	 **************************************************************/

	public String createMultiRegnEventMandatoryByAttachingFile(
			Selenium selenium, String strMultiEventName,
			String strEventDescription, String[] strEvntTemplateNames,
			String strAutoFilePath, String strAutoFileName,
			String strUploadFilePath) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateMultiRegnEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create Multi-Region Event", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create Multi-Region Event page is displayed");

				selenium.type(propElementDetails
						.getProperty("CreateMultiRegnEvent.Title"),
						strMultiEventName);
				selenium.type(propElementDetails
						.getProperty("CreateMultiRegnEvent.Descrptn"),
						strEventDescription);

				for (String s : strEvntTemplateNames) {

					if (selenium.isChecked(s) == false) {
						selenium.click(s);
					}

				}

				String strArgs[] = { strAutoFilePath, strUploadFilePath };
				// Auto it to upload the file
				Runtime.getRuntime().exec(strArgs);
				selenium.windowFocus();
				// click on Browse
				selenium.click(propElementDetails
						.getProperty("DocumentLibrary.AddNewDocument.Browse"));
				// wait for pop up to appear
				Thread.sleep(5000);
				// Wait for autoit file to finish execution
				String strProcess = "";

				int intCnt = 0;
				do {

					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 120);

				selenium.click(propElementDetails
						.getProperty("CreateMultiRegnEvent.Start"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Multi-Region Event Confirmation", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j
					.info("Multi-Region Event Confirmation page is  displayed");

					selenium.click(propElementDetails
							.getProperty("CreateMultiRegnEvent.Yes"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Multi-Region Event Status", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j
						.info("Multi-Region Event Status page is  displayed");

						selenium.click(propElementDetails
								.getProperty("CreateMultiRegnEvent.Done"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							assertEquals("Event Management", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));

							log4j.info("Event Management page is  displayed");

						} catch (AssertionError Ae) {
							strErrorMsg = "Event Managementpage is NOT displayed"
									+ Ae;
							log4j
							.info("Event Management page is NOT displayed");
						}

					} catch (AssertionError Ae) {
						strErrorMsg = "Multi-Region Event Status page is NOT displayed"
								+ Ae;
						log4j
						.info("Multi-Region Event Status page is NOT displayed");
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Multi-Region Event Confirmation page is NOT displayed"
							+ Ae;
					log4j
					.info("Multi-Region Event Confirmation page is NOT displayed");
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Create Multi-Region Event page is NOT displayed"
						+ Ae;
				log4j.info("Create Multi-Region Event page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}

	public String checkAttachedFilesForEventsNewWithChangedElmentID(
			Selenium selenium, String strEveName, String strText,String strEventValue)
					throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// Click on Event name in event banner
			selenium.click("//a[text()='"+strEveName+"']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent("//div[@id='ed" + strEventValue
						+ "']/a[text()='Attached File']"));
				log4j.info("'Attached file' link is displayed.");
				// click on Attached File link
				selenium.click("//div[@id='ed" + strEventValue
						+ "']/a[text()='Attached File']");

				// select the pop up
				selenium.waitForPopUp("EMResourceDocument", gstrTimeOut);
				selenium.selectWindow("name=EMResourceDocument");
				try {
					assertTrue(selenium.isTextPresent(strText));
					log4j
					.info("The attached file (while event creation) is opened.");

				} catch (AssertionError Ae) {
					log4j
					.info("The attached file (while event creation) is NOT opened.");
					strReason = "The attached file (while event creation) is NOT opened.";
				}

				selenium.close();
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				log4j.info("'Attached file' link is NOT displayed.");
				strReason = "'Attached file' link is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String selectStartAndEndDateInCreatingEvent(Selenium selenium,
			String strEveName, String strMonth, String strDay, String strYear,
			String strHour, String strMinute, String strEndMonth,
			String strEndDay, String strEndYear, String strEndHour,
			String strEndMinute, boolean blnSave) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Start event
			selenium.click(propElementDetails
					.getProperty("Event.CreateEve.EveStart.NOtImmediately"));

			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartMnt"), "label="
							+ strMonth);
			selenium
			.select(propElementDetails
					.getProperty("Event.CreateEve.StartDay"), "label="
							+ strDay);
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartYear"), "label="
							+ strYear);

			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartHour"), "label="
							+ strHour);
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartMinut"), "label="
							+ strMinute);

			// End event
			selenium.click(propElementDetails
					.getProperty("Event.CreateEve.EveEnd.DateOpt"));

			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.EndMnt"), "label="
							+ strEndMonth);
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.EndDay"), "label="
							+ strEndDay);
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.EndYear"), "label="
							+ strEndYear);

			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.EndHour"), "label="
							+ strEndHour);
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.EndMinut"), "label="
							+ strEndMinute);

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));

				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Management", selenium.getText("css=h1"));
					log4j.info("'Event Management' screen is displayed.");


					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));
						log4j.info("Event '" + strEveName
								+ "' is listed on 'Event Management' screen.");
					} catch (AssertionError ae) {
						log4j.info("Event '" + strEveName
								+ "' is NOT listed on 'Event Management' screen.");
						strReason = "Event '" + strEveName
								+ "' is NOT listed on 'Event Management' screen.";
					}

				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");

				}


			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String savAndVerifyEvent(Selenium selenium, String strEveName)
			throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Save"));

			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
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
				assertEquals("Event Management", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Event Management' screen is displayed.");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					strReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
				}

			} catch (AssertionError ae) {
				log4j.info("'Event Management' screen is NOT displayed.");
				strReason="'Event Management' screen is NOT displayed.";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String[] getStartAndEndDateInCreatingEvent(Selenium selenium,
			String strEveName, boolean blnSave) throws Exception {

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strStartAndEndDate[] = new String[11];
		strStartAndEndDate[10] = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Start event

			strStartAndEndDate[0] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.StartMnt"));

			strStartAndEndDate[1] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.StartDay"));

			strStartAndEndDate[2] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.StartYear"));

			strStartAndEndDate[3] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.StartHour"));

			strStartAndEndDate[4] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.StartMinut"));

			strStartAndEndDate[5] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.EndMnt"));

			strStartAndEndDate[6] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.EndDay"));

			strStartAndEndDate[7] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.EndYear"));

			strStartAndEndDate[8] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.EndHour"));

			strStartAndEndDate[9] = selenium
					.getSelectedLabel(propElementDetails
							.getProperty("Event.CreateEve.EndMinut"));

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));

				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));
						log4j.info("Event '" + strEveName
								+ "' is listed on 'Event Management' screen.");
					} catch (AssertionError ae) {
						log4j
						.info("Event '"
								+ strEveName
								+ "' is NOT listed on 'Event Management' screen.");
						strStartAndEndDate[11] = "Event '"
								+ strEveName
								+ "' is NOT listed on 'Event Management' screen.";
					}

				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
					strStartAndEndDate[10] = "'Event Management' screen is NOT displayed.";

				}

			}

		} catch (Exception e) {
			log4j.info(e);
			strStartAndEndDate[10] = e.toString();
		}
		return strStartAndEndDate;
	}

	public String navToEventManagementNew(Selenium selenium) throws Exception {

		String strErrorMsg = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		// Event tab
		selenium.mouseOver(propElementDetails.getProperty("EventLink"));
		// Click on Event Management link
		selenium.click(propElementDetails.getProperty("Event.EventManagMent"));
		selenium.waitForPageToLoad(gstrTimeOut);


		int intCnt=0;
		do{
			try{
				assertEquals("Event Management", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				break;
			}catch(AssertionError Ae){
				Thread.sleep(1000);
				intCnt++;
			}
			catch(Exception e){
				Thread.sleep(1000);
				intCnt++;
			}
		}while(intCnt<60);
		try {
			assertEquals("Event Management", selenium
					.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("'Event Management' screen is displayed.");

		} catch (AssertionError ae) {
			log4j.info("'Event Management' screen is NOT displayed.");
			strErrorMsg = "'Event Management' screen is NOT displayed.";
		}

		return strErrorMsg;
	}


	public String isCheckedAndSelectedStatusForSTInEditEventPage(
			Selenium selenium, boolean blnChecked, boolean blnEnabled,
			String strST[], boolean blnOptionChecked, boolean blnOptionEnabled)
					throws Exception {

		String strErrorMsg = "";

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnOptionChecked) {

				if (blnChecked) {

					for (String s : strST) {
						try {
							assertTrue(selenium.isChecked("css=input[value='"
									+ s + "'][name='st']"));
							log4j.info("Status type is Selected ");
						} catch (AssertionError Ae) {
							log4j.info("Status type is  NOT Selected " + Ae);
							strErrorMsg = "Status type is  NOT Selected " + Ae;
						}

					}

				} else {
					for (String s : strST) {
						try {
							assertFalse(selenium.isChecked("css=input[value='"
									+ s + "'][name='st']"));
							log4j.info("Status type is NOT Selected ");
						} catch (AssertionError Ae) {
							log4j.info("Status type is Selected " + Ae);
							strErrorMsg = "Status type is Selected " + Ae;
						}
					}

				}

			}

			if (blnOptionEnabled) {

				if (blnEnabled) {

					for (String s : strST) {
						try {
							assertFalse(selenium
									.isElementPresent("//input[@value='"+s+"'][@type='hidden']"));

							log4j.info("Status type is Enabled ");
						} catch (AssertionError Ae) {
							log4j.info("Status type is disabled " + Ae);
							strErrorMsg = "Status type is disabled" + Ae;
						}

					}

				} else {
					for (String s : strST) {
						try {

							assertTrue(selenium
									.isElementPresent("//input[@value='"+s+"'][@type='hidden']"));
							String strDisble = selenium
									.getAttribute("//input[@value='" + s
											+ "']/@disabled");

							assertEquals(strDisble, "true");
							log4j.info("Status type is disabled ");
						} catch (AssertionError Ae) {
							log4j.info("Status type is  Enabled " + Ae);
							strErrorMsg = "Status type is Enabled " + Ae;
						}
					}

				}

			}

		} catch (Exception e) {
			strErrorMsg = "isCheckedAndSelectedStatusForSTInEditEventPage function failed "
					+ e;
			log4j
			.info("isCheckedAndSelectedStatusForSTInEditEventPage function failed "
					+ e);
		}

		return strErrorMsg;
	}

	public String selectAndDeselectST(Selenium selenium, String strST[],
			boolean blnOptionSelect,boolean blnSave) throws Exception {

		String strErrorMsg = "";

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnOptionSelect) {

				for (String s : strST) {
					if (selenium.isChecked("css=input[value='" + s
							+ "'][name='st']") == false) {
						selenium.click("css=input[value='" + s
								+ "'][name='st']");
					}

				}

			} else {

				for (String s : strST) {
					if (selenium.isChecked("css=input[value='" + s
							+ "'][name='st']")) {
						selenium.click("css=input[value='" + s
								+ "'][name='st']");
					}

				}

			}

			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
					strErrorMsg="'Event Management' screen is NOT displayed.";
				}
			}

		} catch (Exception e) {
			strErrorMsg = "selectAndDeselectST function failed " + e;
			log4j.info("selectAndDeselectST function failed " + e);
		}

		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:fill  fields of event template 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	 ************************************************************************/


	public String fillfieldsNavToEvenTNotPreferencesPage(Selenium selenium,
			String strTempName, String strTempDef, String strEveColor, String[] strResTypeVal, 
			String[] strStatusTypeval,boolean blnST,boolean blnRT)
					throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
							+ strEveColor);

			if (blnST) {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.click("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
				}
			} else {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.isChecked("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
					selenium.click("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
				}
			}

			if (blnRT) {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.click("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
				}
			} else {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.isChecked("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
					selenium.click("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
				}
			}
			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertTrue(selenium
						.isTextPresent("Event Notification Preferences for "
								+ strTempName));
				log4j.info("Event Notification Preferences for "
						+ strTempName+"is displayed");
			} catch (AssertionError ae) {
				log4j.info("Event Notification Preferences for "
						+ strTempName+"is NOT displayed");
				strReason = "Event Notification Preferences for "
						+ strTempName+"is  NOT displayed";
			}	

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/***********************************************************************
	'Description	:fill  fields of event template with secuirty option
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:10-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	 ************************************************************************/


	public String fillfieldsNavToEvenTNotPreferencesPageWithSecurityOption(
			Selenium selenium, String strTempName, String strTempDef,
			String strEveColor, String[] strResTypeVal,
			String[] strStatusTypeval, boolean blnST, boolean blnRT,
			boolean blnSecurity, boolean blnUsrPresent, String strUserName[],
			boolean blnEventNotiSave, boolean blnSave) throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Event.CreateTemp.TempName")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
							+ strEveColor);

			if (blnSecurity) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.EventSecurityOption")) == false) {
					selenium.click(propElementDetails
							.getProperty("Event.EventSecurityOption"));

				}
			}

			if (blnST) {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.click("css=input[name='st'][value='"
							+ strStatusTypeval[i] + "']");
				}
			} else {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.isChecked("css=input[name='st'][value='"
							+ strStatusTypeval[i] + "']");
					selenium.click("css=input[name='st'][value='"
							+ strStatusTypeval[i] + "']");
				}
			}

			if (blnRT) {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.click("css=input[name='rt'][value='"
							+ strResTypeVal[i] + "']");
				}
			} else {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.isChecked("css=input[name='rt'][value='"
							+ strResTypeVal[i] + "']");
					selenium.click("css=input[name='rt'][value='"
							+ strResTypeVal[i] + "']");
				}
			}

			if (blnEventNotiSave) {
				// Click on Save
				selenium.click(propElementDetails
						.getProperty("Event.CreateTemp.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isTextPresent("Event Notification Preferences for "
									+ strTempName));
					log4j.info("Event Notification Preferences for "
							+ strTempName + "is displayed");

					if (blnUsrPresent) {
						for (String s : strUserName)
							try {
								assertTrue(selenium
										.isElementPresent("//table[@id='tbl_emailInd']/tbody/"
												+ "tr/td[4][text()='"
												+ s
												+ "']"));

								log4j
								.info("User "
										+ s
										+ "is displayed in the event notification preference page");
							} catch (AssertionError Ae) {
								log4j
								.info("User "
										+ s
										+ "is NOT displayed in the event notification preference page");
								strReason = "User "
										+ s
										+ "is NOT displayed in the event notification preference page";
							}
					}

				} catch (AssertionError ae) {
					log4j.info("Event Notification Preferences for "
							+ strTempName + "is NOT displayed");
					strReason = "Event Notification Preferences for "
							+ strTempName + "is  NOT displayed";
				}

			}

			if (blnSave) {
				selenium.click(propElementDetails
						.getProperty("Event.EventNotPref.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Template List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Template List' screen is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[3][text()='"
										+ strTempName + "']"));

						log4j.info("'Event Template' " + strTempName
								+ "  is displayed.");

					} catch (AssertionError ae) {
						log4j.info("'Event Template' " + strTempName
								+ "  is NOT displayed.");
						strReason = "'Event Template' " + strTempName
								+ "  is NOT displayed.";
					}

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName + "']"));

				} catch (AssertionError ae) {
					log4j
					.info("'Event Template List' screen is NOT displayed.");
					strReason = "'Event Template List' screen is NOT displayed.";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/************************************************************
	'Description	:Verify Status type in edit event page
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	 ************************************************************/

	public String chkUserInEventNotPrefPage(Selenium selenium,
			String strUserName, boolean blnUser)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnUser) {
				try {
					assertTrue(selenium.isElementPresent("css=[value='"
							+ strUserName + "']"));
					log4j.info("User '"+strUserName+"' is listed in Event Notification Preferences page");
				} catch (AssertionError Ae) {
					strErrorMsg = "User '"+strUserName+"' is NOT listed in Event Notification Preferences page";
					log4j.info("User '"+strUserName+"' is NOT listed in Event Notification Preferences page");
				}
			} else {
				try {
					assertFalse(selenium.isElementPresent("css=[value='"
							+ strUserName + "']"));
					log4j.info("User '"+strUserName+"' is NOT listed in Event Notification Preferences page");
				} catch (AssertionError Ae) {
					strErrorMsg = "User '"+strUserName+"' is listed in Event Notification Preferences page";
					log4j.info("User '"+strUserName+"' is listed in Event Notification Preferences page");
				}
			}
		} catch (Exception e) {
			log4j.info("chkSTInCreateEventTemplatePage function failed" + e);
			strErrorMsg = "chkSTInCreateEventTemplatePage function failedd" + e;
		}
		return strErrorMsg;
	}



	/***********************************************************************
		'Description	:Navigate and find
		'Precondition	:None
		'Arguments		:selenium,statTypeName,strStatTypDefn, blnSav	
		'Returns		:String
		'Date	 		:12-April-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'12-April-2012                               <Name>
	 ************************************************************************/		
	public String savAndVarETFields(Selenium selenium, String strTempName,
			String strTempDef, boolean blnCheckPage) throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnCheckPage) {
				// deselect Web(Select All) check box and save
				selenium.click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");											
			}
			//Save
			selenium.click(propElementDetails
					.getProperty("Event.EventNotPref.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);	

			try {
				assertTrue(selenium.isTextPresent("Event Template List"));
				log4j.info("Event Template List screen is displayed");
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));
				log4j.info("The template name " + strTempName
						+ " is displayed in the 'Event Template List' screen ");
			} catch (AssertionError ae) {
				log4j.info("The template name  is NOT displayed in the 'Event Template List' screen ");
				strReason = "The template is NOT displayed in the 'Event Template List' screen";
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3]" +
								"[text()='"+ strTempName + "']/parent::tr/td[5][text()='"+strTempDef+"']"));
				log4j.info("The template Definition " + strTempDef
						+ " is displayed in the 'Event Template List' screen ");
			} catch (AssertionError ae) {
				log4j.info("The template Definition " + strTempDef
						+ " is displayed in the 'Event Template List' screen ");
				strReason = "The template Definition " + strTempDef
						+ " is displayed in the 'Event Template List' screen ";
			}


		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/***********************************************************************
		'Description	:save and verify event template
		'Precondition	:None
		'Arguments		:selenium,statTypeName,strStatTypDefn, blnSav	
		'Returns		:String
		'Date	 		:12-April-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'12-April-2012                               <Name>
	 ************************************************************************/

	public String VerifyEventtemplate(Selenium selenium, String strTempName)
			throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Event Template List",selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Event Template List' screen is displayed.");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName + "']"));
					log4j.info("The template name "
							+ strTempName
							+ " is displayed in the 'Event Template List' screen ");
				} catch (AssertionError ae) {
					log4j.info("The template name "
							+ strTempName
							+ " is NOT displayed in the 'Event Template List' screen ");
					strReason = "The template name "
							+ strTempName
							+ " is NOT displayed in the 'Event Template List' screen .";
				}

			} catch (AssertionError ae) {
				log4j.info("'Event Template List' screen is NOT displayed.");
				strReason = "'Event Template List' screen is NOT displayed.";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/***********************************************************************
	'Description	:Navigate and find
	'Precondition	:None
	'Arguments		:selenium,statTypeName,strStatTypDefn, blnSav	
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	 ************************************************************************/		
	public String chkEmailPagerWebChkBoxesForUser(Selenium selenium,
			String strUserName, boolean blnCheckPage) throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnCheckPage) {
				try {
					assertTrue(selenium.isElementPresent("css=[value='"
							+ strUserName + "'][name='emailInd']"));
					assertTrue(selenium.isElementPresent("css=[value='"
							+ strUserName + "'][name='pagerInd']"));
					assertTrue(selenium.isElementPresent("css=[value='"
							+ strUserName + "'][name='webInd']"));
					log4j.info("E-mail, Pager and Web checkboxes are displayed for user"
							+ strUserName + ".");
					assertTrue(selenium
							.isChecked("css=[name='SELECT_ALL'][value='webInd']"));
					log4j.info("Web check-boxe is selected for all the users by default");

				} catch (AssertionError ae) {
					log4j.info("E-mail, Pager and Web checkboxes are displayed for user"
							+ strUserName + ".");
					strReason = "E-mail, Pager and Web checkboxes are displayed for user"
							+ strUserName + ".";
				}
			} else {
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Email')]/input[@name='SELECT_ALL']"));
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Pager')]/input[@name='SELECT_ALL']"));
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
					log4j.info("E-mail, Pager and Web checkboxes are displayed for each user.");
					assertTrue(selenium
							.isChecked("css=[name='SELECT_ALL'][value='webInd']"));
					log4j.info("Web check-boxes are selected for all the users by default");
				} catch (AssertionError ae) {
					log4j.info("E-mail, Pager and Web checkboxes are NOT displayed for each user.");
					strReason = "E-mail, Pager and Web checkboxes are NOT displayed for each user.";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/*********************************************************
		  'Description :Verify wait for an element function
		  'Precondition :None
		  'Arguments  :selenium,strElementID
		  'Returns  :String
		  'Date    :5-April-2012
		  'Author   :QSG
		  '---------------------------------------------------------
		  'Modified Date                            Modified By
		  '12-April-2012                               <Name>
	 **********************************************************/

	public String checkForIconInEventTemplateList(Selenium selenium,
			String strElementID,String strData )
					throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			assertEquals(
					strData,
					selenium.getAttribute(strElementID));
		} catch (AssertionError Ae) {
			strReason = "Element NOT Present";
		}

		return strReason;
	}

	/***********************************************************************
		'Description	:select and deselect include button for st
		'Precondition	:None
		'Arguments		:selenium,strResrcTypName,strStstTypLabl
		'Returns		:String
		'Date	 		:6-july-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'6-April-2012                               <Name>
	 ************************************************************************/

	public String selectDesectIncludeInactiveET(Selenium selenium,
			boolean blnInactive) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnInactive) {

				if (selenium.isChecked(propElementDetails
						.getProperty("EventTemplateList.IncludeInactiveET")) == false) {
					selenium.click(propElementDetails
							.getProperty("EventTemplateList.IncludeInactiveET"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("EventTemplateList.IncludeInactiveET"))) {
					selenium.click(propElementDetails
							.getProperty("EventTemplateList.IncludeInactiveET"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			}
		} catch (Exception e) {
			log4j.info("selectDesectIncludeInactiveET function failed" + e);
			strErrorMsg = "selectDesectIncludeInactiveET function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Create private event
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:6-july-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	 ************************************************************************/
	public String createPrivateEventMandFlds(Selenium selenium, String strEveTemp,
			String strEveName, String strInfo, boolean blnSave)
					throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Select Event Template",selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table[@class='displayTable"
						+ " striped border sortable']/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");

				selenium.waitForPageToLoad(gstrTimeOut);

				try {

					selenium.selectWindow("");
					selenium.selectFrame("Data");

					assertEquals("Create New Event", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);
					//click private event checkbox
					selenium.click(propElementDetails
							.getProperty("Event.CreateNewPrivate_Event"));

					if (blnSave) {
						// click on save
						selenium.click(propElementDetails
								.getProperty("Save"));

						selenium.waitForPageToLoad(gstrTimeOut);

					}

				} catch (AssertionError ae) {
					log4j
					.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}



			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String navSelectEventTemp(Selenium selenium, String strEveTemp)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Select Event Template", selenium
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
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table/tbody/tr/td[3][text()='"
									+ strEveTemp + "']"));
					log4j
					.info("Event Template '"
							+ strEveTemp
							+ "' is  displayed in 'Select Event Template' screen ");
				} catch (AssertionError Ae) {
					log4j
					.info("Event Template '"
							+ strEveTemp
							+ "' is NOT displayed in 'Select Event Template' screen ");
					strReason = "Event Template '"
							+ strEveTemp
							+ "' is  NOT displayed in 'Select Event Template' screen ";

				}

			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String createEventWitManyRS(Selenium selenium, String strEveTemp,
			String strResourceValue[], String strEveName, String strInfo,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Select Event Template", selenium
							.getText(propElementDetails
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
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt=0;
				do{
					try {

						assertEquals("Create New Event", selenium
								.getText(propElementDetails
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
					assertEquals("Create New Event", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);

					// Click on Resource check box for the particular
					// resource

					for (String s : strResourceValue) {
						selenium.click("css=input[name='resourceID'][value='"
								+ s + "']");

					}

					if (blnSave) {
						// click on save
						selenium.click(propElementDetails
								.getProperty("Event.CreateEve.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

						intCnt=0;
						do{
							try {

								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
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
							assertEquals("Event Management", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j
							.info("'Event Management' screen is displayed.");

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								log4j
								.info("Event '"
										+ strEveName
										+ "' is listed on 'Event Management' screen.");
							} catch (AssertionError ae) {
								log4j
								.info("Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.");
								strReason = "Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.";
							}

						} catch (AssertionError ae) {
							log4j
							.info("'Event Management' screen is NOT displayed.");
							strReason = "'Event Management' screen is NOT displayed.";
						}

					}

				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/************************************************************
	'Description	:Verify  Multi-Region Event is created and navigate to Multi-Region Event Confirmation page
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String createMultiRegnEventMandatoryNavMultiRegnConfrmtn(
			Selenium selenium, String strMultiEventName,
			String strEventDescription, String[] strEvntTemplateNames,
			boolean blnstart) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateMultiRegnEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Create Multi-Region Event", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
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
				assertEquals("Create Multi-Region Event", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create Multi-Region Event page is displayed");

				selenium.type(propElementDetails
						.getProperty("CreateMultiRegnEvent.Title"),
						strMultiEventName);
				selenium.type(propElementDetails
						.getProperty("CreateMultiRegnEvent.Descrptn"),
						strEventDescription);

				for (String s : strEvntTemplateNames) {

					if (selenium.isChecked(s) == false) {
						selenium.click(s);
					}

				}

				if (blnstart) {
					selenium.click(propElementDetails
							.getProperty("CreateMultiRegnEvent.Start"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt=0;
					do{
						try {

							assertEquals("Multi-Region Event Confirmation",
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
						assertEquals("Multi-Region Event Confirmation",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
						.info("Multi-Region Event Confirmation page is  displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Multi-Region Event Confirmation page is NOT displayed"
								+ Ae;
						log4j
						.info("Multi-Region Event Confirmation page is NOT displayed");
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Create Multi-Region Event page is NOT displayed"
						+ Ae;
				log4j.info("Create Multi-Region Event page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Verify  Multi-Region Event is created and navigate to Multi-Region Event Confirmation page
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String startAndNavMultiRegnConfrmtn(
			Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");


			selenium.click(propElementDetails
					.getProperty("CreateMultiRegnEvent.Start"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Multi-Region Event Confirmation",
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
				assertEquals("Multi-Region Event Confirmation",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j
				.info("Multi-Region Event Confirmation page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Multi-Region Event Confirmation page is NOT displayed"
						+ Ae;
				log4j
				.info("Multi-Region Event Confirmation page is NOT displayed");
			}			


		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}
	/************************************************************
	'Description	:Verify  Multi-Region Event is created and navigate to Multi-Region Event Confirmation page
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String navMultiRegnEvntStatus(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateMultiRegnEvent.Yes"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Multi-Region Event Status", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
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
				assertEquals("Multi-Region Event Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Multi-Region Event Status page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Multi-Region Event Status page is NOT displayed"
						+ Ae;
				log4j.info("Multi-Region Event Status page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Verify  Multi-Region Event Confirmation page elements
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String verfyElementsInMultiRegnEvntConfrmPge(Selenium selenium,
			String strMultiEventName, String strEventDescription,
			String strStrtDate, String strEndDate, String strAttachedFile,
			String strDrill, String strEndQuietly,String strRegnName[],String strETNames[]) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[1]/td[1]"),"Event Title:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[2]/td[1]"),"Event Information:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[3]/td[1]"),"Start Date and Time:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[4]/td[1]"),"End Date and Time:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[5]/td[1]"),"Attached File:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[6]/td[1]"),"Drill?");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[7]/td[1]"),"End Quietly?");

				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[1]/td[2]"),strMultiEventName);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[2]/td[2]"),strEventDescription);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[3]/td[2]"),strStrtDate);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[4]/td[2]"),strEndDate);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[5]/td[2]"),strAttachedFile);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[6]/td[2]"),strDrill);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[7]/td[2]"),strEndQuietly);


				log4j.info("Multi-Region Event Confirmation' screen is displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? ");

			} catch (AssertionError Ae) {
				strErrorMsg = "Multi-Region Event Confirmation' screen is NOT displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? "
						+ Ae;
				log4j.info("Multi-Region Event Confirmation' screen is NOT displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? ");
			}

			for (int i = 1; i <=strRegnName.length; i++) {
				try {

					assertEquals(
							selenium
							.getText("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr["+i+"]/td[1]"), strRegnName[i-1]);

					log4j.info("Regions "+strRegnName[i-1]+" selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = "Regions "+strRegnName[i]+"selected are NOT "
							+ " displayed in the table format. " + Ae;
					log4j.info("Regions "+strRegnName[i]+"selected are NOT"
							+ " displayed in the table format. ");
				}
			}

			for (int i = 1; i <= strETNames.length; i++) {
				try {
					assertEquals(
							selenium
							.getText("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr["+i+"]/td[2]"), strETNames[i-1]);

					log4j.info("Events templates "+strETNames[i-1]+"selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = "Events templates "+strETNames[i-1]+" selected "
							+ "are NOT displayed in the table format. " + Ae;
					log4j.info("Events templates  "+strETNames[i-1]+" selected are"
							+ " NOT displayed in the table format. ");
				}
			}





		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = e.toString();
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Verify  Multi-Region Event Confirmation page elements
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String verfyEvenStatInMultiRegnEvntStatusPge(Selenium selenium,
			String strRegnName[],String strETNames[]) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");


			for (int i = 1; i < strRegnName.length; i++) {
				try {
					assertEquals(
							selenium
							.getText("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr["+i+"]/td[1]"), "Started");
					assertTrue(
							selenium
							.isElementPresent("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr/td[2][text()='"+strRegnName[i-1]+"']") );

					log4j.info("Event Status (as 'Started'),Regions selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = "Event Status (as 'Started'),Regions "+strRegnName[i-1]+"selected are NOT "
							+ " displayed in the table format. " + Ae;
					log4j.info("Event Status (as 'Started'),Regions "+strRegnName[i-1]+"selected are NOT"
							+ " displayed in the table format. ");
				}
			}

			for (int i = 1; i < strETNames.length; i++) {
				try {
					assertTrue(
							selenium
							.isElementPresent("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr/td[3][text()='"+strETNames[i-1]+"']") );


					log4j.info(" events templates "+strETNames[i-1]+"selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = " events templates "+strETNames[i-1]+" selected "
							+ "are NOT displayed in the table format. " + Ae;
					log4j.info("events templates  "+strETNames[i-1]+" selected are"
							+ " NOT displayed in the table format. ");
				}
			}

			try {
				assertTrue(
						selenium
						.isElementPresent("css=input[value='Done']"));

				log4j.info("'Done' button is displayed. ");

			} catch (AssertionError Ae) {
				strErrorMsg = "'Done' button is NOT displayed." + Ae;
				log4j.info("'Done' button is NOT displayed.");
			}
		} catch (Exception e) {
			log4j.info("" + e);
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Fetch the values of Event Start Date
	'Precondition	:None
	'Arguments		:selenium
	'			     
	'Returns		:String
	'Date	 		:26-11-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String[] fetchEventStartDateValues(Selenium selenium) throws Exception {

		String[] strArResult = new String[6];
		strArResult[0]="";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");


			strArResult[1]=selenium
					.getSelectedLabel("css=select[name='startMonth']");
			strArResult[2]=selenium
					.getSelectedLabel("css=select[name='startDay']");
			strArResult[3]=selenium
					.getSelectedLabel("css=select[name='startYear']");

			strArResult[4]=selenium
					.getSelectedLabel("css=select[name='startHour']");
			strArResult[5]=selenium
					.getSelectedLabel("css=select[name='startMinute']");		



		} catch (Exception e) {
			log4j.info("" + e);
			strArResult[0] =e.toString();
		}
		return strArResult;
	}

	/************************************************************
	'Description	:Fetch the values of Event End Date
	'Precondition	:None
	'Arguments		:selenium
	'			    
	'Returns		:String
	'Date	 		:26-11-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String[] fetchEventEndDateValues(Selenium selenium) throws Exception {

		String[] strArResult = new String[6];
		strArResult[0] = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			strArResult[1] = selenium
					.getSelectedLabel("css=select[name='endMonth']");
			strArResult[2] = selenium
					.getSelectedLabel("css=select[name='endDay']");
			strArResult[3] = selenium
					.getSelectedLabel("css=select[name='endYear']");

			strArResult[4] = selenium
					.getSelectedLabel("css=select[name='endHour']");
			strArResult[5] = selenium
					.getSelectedLabel("css=select[name='endMinute']");

		} catch (Exception e) {
			log4j.info("" + e);
			strArResult[0] = e.toString();
		}
		return strArResult;
	}

	/************************************************************
	'Description	:Verify  edit event page is displayed
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strEventName
	'			     
	'Returns		:String
	'Date	 		:27-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String navEditEventPage(Selenium selenium, String strEventName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
			.click("//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[6][text()="
					+ "'" + strEventName + "']/parent::tr/td[1]/a[1]");

			selenium.waitForPageToLoad(gstrTimeOut);


			int intCnt=0;
			do{
				try {

					assertEquals("Edit Event", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
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

				assertEquals("Edit Event",  selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Edit Event' screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("'Edit Event' screen is NOT displayed");
				strErrorMsg = "'Edit Event' screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("Failed in function navEditEventPage" + e);
			strErrorMsg = "Failed in function navEditEventPage";
		}
		return strErrorMsg;
	}


	/************************************************************
	'Description	:Verify  edit event page is displayed
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strEventName
	'			     
	'Returns		:String
	'Date	 		:27-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String selectAndDeselectSTInEditEventPage(Selenium selenium,
			String strEventName, boolean blnCheck, String strSTValue,
			boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnCheck) {
				if (selenium.isChecked("css=input[name='st'][value='"
						+ strSTValue + "']") == false) {
					selenium.click("css=input[name='st'][value='" + strSTValue
							+ "']");

				}
			} else {
				if (selenium.isChecked("css=input[name='st'][value='"
						+ strSTValue + "']")) {
					selenium.click("css=input[name='st'][value='" + strSTValue
							+ "']");

				}
			}

			if (blnSave) {
				selenium.click(propElementDetails
						.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEventName + "']"));
						log4j.info("Event '" + strEventName
								+ "' is listed on 'Event Management' screen.");
					} catch (AssertionError ae) {
						log4j.info("Event '" + strEventName
								+ "' is NOT listed on 'Event Management' screen.");
						strErrorMsg = "Event '" + strEventName
								+ "' is NOT listed on 'Event Management' screen.";
					}

				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
					strErrorMsg="'Event Management' screen is NOT displayed.";

				}
			}

		} catch (Exception e) {
			log4j.info("Failed in function selectAndDeselectSTInEditEventPage" + e);
			strErrorMsg = "Failed in function selectAndDeselectSTInEditEventPage";
		}
		return strErrorMsg;
	}


	/************************************************************
	'Description	:Verify  status is selected in edit event template page
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strEventName
	'			     
	'Returns		:String
	'Date	 		:15-Oct-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String selectAndDeselectSTInEditEventTemplatePage(Selenium selenium,
			String strTempName, boolean blnCheck, String strSTValue,
			boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnCheck) {
				if (selenium.isChecked("css=input[name='st'][value='"
						+ strSTValue + "']") == false) {
					selenium.click("css=input[name='st'][value='" + strSTValue
							+ "']");

				}
			} else {
				if (selenium.isChecked("css=input[name='st'][value='"
						+ strSTValue + "']")) {
					selenium.click("css=input[name='st'][value='" + strSTValue
							+ "']");

				}
			}

			if (blnSave) {
				selenium.click(propElementDetails
						.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Template List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Template List' screen is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
										+ strTempName + "']"));
						log4j
						.info("The template name "
								+ strTempName
								+ " is displayed in the 'Event Template List' screen ");
					} catch (AssertionError ae) {
						log4j
						.info("The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen ");
						strErrorMsg = "The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen .";
					}

				} catch (AssertionError ae) {
					log4j
					.info("'Event Template List' screen is NOT displayed.");
					strErrorMsg = "'Event Template List' screen is NOT displayed.";
				}

			}

		} catch (Exception e) {
			log4j.info("Failed in function selectAndDeselectSTInEditEventTemplatePage"
					+ e);
			strErrorMsg = "Failed in function selectAndDeselectSTInEditEventTemplatePage";
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Verify Res type in edit event template page
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	 ************************************************************/

	public String chkRTInEditEventTemplatePage(Selenium selenium,
			String strResTypeval, boolean blnRT, String statResTypeName)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnRT) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='rt'][value='"
									+ strResTypeval + "']"));
					log4j
					.info("Status types "
							+ statResTypeName
							+ " is displayed in the 'Edit Event Template' page.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Status types "
							+ statResTypeName
							+ " is NOT displayed in the 'Edit Event Template' page.";
					log4j.info("Status types " + statResTypeName
							+ " is NOT displayed in the 'Edit Event' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='rt'][value='"
									+ strResTypeval + "']"));
					log4j
					.info("Status types "
							+ statResTypeName
							+ " is NOT displayed in the 'Edit Event Template' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Status types " + statResTypeName
							+ " displayed in the 'Edit Event Template' page.";
					log4j.info("Status types " + statResTypeName
							+ " displayed in the 'Edit Event Template' page.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkRTInEditEventTemplatePage function failed" + e);
			strErrorMsg = "chkRTInEditEventTemplatePage function failedd" + e;
		}
		return strErrorMsg;
	}

	public String createEventEndNever(Selenium selenium, String strEveTemp,
			String strResource, String strEveName, String strInfo,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Select Event Template",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			
			try {
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try {
					assertEquals("Create New Event", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ strResource + "']"));
						log4j.info("Resource "
								+ strResource
								+ " is displayed under 'Resources to Participate in This Event' section.");

						// Click on Resource check box for the particular
						// resource
						selenium
						.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']");

						if (selenium
								.isChecked("css=input[name='eventEndType'][value='NEVER']") == false) {
							selenium
							.click("css=input[name='eventEndType'][value='NEVER']");
						}

						if (blnSave) {
							// click on save
							selenium.click(propElementDetails
									.getProperty("Event.CreateEve.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

						    intCnt = 0;
							do {
								try {
									assertEquals("Event Management", selenium
											.getText(propElementDetails
													.getProperty("Header.Text")));
									break;
								} catch (AssertionError Ae) {
									Thread.sleep(1000);
									intCnt++;
								} catch (Exception e) {
									Thread.sleep(1000);
									intCnt++;
								}
							} while (intCnt < 60);
							
							try {
								assertEquals("Event Management", selenium
										.getText(propElementDetails
												.getProperty("Header.Text")));
								log4j
								.info("'Event Management' screen is displayed.");

								try {
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
													+ strEveName + "']"));
									log4j
									.info("Event '"
											+ strEveName
											+ "' is listed on 'Event Management' screen.");
								} catch (AssertionError ae) {
									log4j
									.info("Event '"
											+ strEveName
											+ "' is NOT listed on 'Event Management' screen.");
									strReason = "Event '"
											+ strEveName
											+ "' is NOT listed on 'Event Management' screen.";
								}

							} catch (AssertionError ae) {
								log4j
								.info("'Event Management' screen is NOT displayed.");
								strReason = "'Event Management' screen is NOT displayed.";
							}

						}

					} catch (AssertionError ae) {
						log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.");
						strReason = "Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.";
					}
				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String createEventMandFldsEndNever(Selenium selenium, String strEveTemp,
			String strEveName, String strInfo, boolean blnSave)
					throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table[@class='displayTable"
						+ " striped border sortable']/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");

				selenium.waitForPageToLoad(gstrTimeOut);

				try {

					selenium.selectWindow("");
					selenium.selectFrame("Data");

					assertEquals("Create New Event",selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);

					if (selenium
							.isChecked("css=input[name='eventEndType'][value='NEVER']") == false) {
						selenium
						.click("css=input[name='eventEndType'][value='NEVER']");
					}


					if (blnSave) {
						// click on save
						selenium.click(propElementDetails.getProperty("Save"));

						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							assertEquals("Event Management", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j
							.info("'Event Management' screen is displayed.");

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								log4j
								.info("Event '"
										+ strEveName
										+ "' is listed on 'Event Management' screen.");
							} catch (AssertionError ae) {
								log4j
								.info("Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.");
								strReason = "Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.";
							}

						} catch (AssertionError ae) {
							log4j
							.info("'Event Management' screen is NOT displayed.");
							strReason = "'Event Management' screen is NOT displayed.";
						}

					}

				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/***********************************************************************
	   'Description  :save the event and verify it is listed or not 
	   'Precondition :None
	   'Arguments    :selenium,
	   'Returns      :String
	   'Date         :10-July-2012
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '14-June-2012                               <Name>
	 ************************************************************************/


	public String saveAndvrfyEventAlongWithPageName(Selenium selenium,  String strEveName,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");


			if (blnSave) {
				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));
						log4j.info("Event '" + strEveName
								+ "' is listed on 'Event Management' screen.");
					} catch (AssertionError ae) {
						log4j.info("Event '" + strEveName
								+ "' is NOT listed on 'Event Management' screen.");
						strReason = "Event '" + strEveName
								+ "' is NOT listed on 'Event Management' screen.";
					}

					log4j.info("'Event Management' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");

				}


			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/***********************************************************************
	   'Description  :check the confirmation message
	   'Precondition :None
	   'Arguments    :selenium,
	   'Returns      :String
	   'Date         :26-11-2012
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<date>		                              <Name>
	 ************************************************************************/


	public String checkConfirmMsgInMultiRegEveConfm(Selenium selenium)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try {
				assertEquals(
						"Are you sure you want to create these events?",
						selenium
						.getText("//div[@id='mainContainer']/form/center/strong"));
				assertTrue(selenium.isElementPresent("css=input[value='Yes']"));
				assertTrue(selenium.isElementPresent("css=input[value='No']"));
				log4j
				.info("A confirmation message 'Are you sure you want to create these events?' is displayed with Yes and No buttons.");
			} catch (AssertionError ae) {
				log4j
				.info("A confirmation message 'Are you sure you want to create these events?' is NOT displayed with Yes and No buttons.");
				strReason = "A confirmation message 'Are you sure you want to create these events?' is NOT displayed with Yes and No buttons.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String checkUserInfoInEventBannerCorrect(Selenium selenium,
			String strUsrInfo, boolean blnViewHistory, String strEventValue)
					throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals(strUsrInfo, selenium
						.getText("//div[starts-with(@id,'ed" + strEventValue
								+ "')]"));
				log4j
				.info("Created by is displayed along with time of event creation and event information.");
			} catch (AssertionError ae) {
				log4j
				.info("Created by is NOT displayed along with time of event creation and event information.");
				strReason = "Created by is NOT displayed along with time of event creation and event information.";
			}

			if (blnViewHistory) {
				try {
					assertTrue(selenium.isElementPresent("link=View�History"));
					log4j
					.info("'Event History' link is present on event banner. ");
				} catch (AssertionError ae) {
					log4j
					.info("'Event History' link is not present on event banner.");
					strReason = "'Event History' link is not present on event banner.";
				}
			} else {
				try {
					assertFalse(selenium.isElementPresent("link=View�History"));
					log4j
					.info("'Event History' link is not present on event banner. ");
				} catch (AssertionError ae) {
					log4j
					.info("'Event History' link is present on event banner.");
					strReason = "'Event History' link is present on event banner.";
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}


	/************************************************************
	'Description	:Verify  Multi-Region Event is vreated
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:22-Jan-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'22-Jan-2013                               <Name>
	 **************************************************************/

	public String uploadFilesInCreateEventPage(Selenium selenium,
			String strAutoFilePath, String strAutoFileName,
			String strUploadFilePath) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			String strArgs[] = { strAutoFilePath, strUploadFilePath };
			// Auto it to upload the file
			Runtime.getRuntime().exec(strArgs);
			selenium.windowFocus();
			// click on Browse
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.AddNewDocument.Browse"));
			// wait for pop up to appear
			Thread.sleep(5000);
			// Wait for autoit file to finish execution
			String strProcess = "";

			int intCnt = 0;
			do {

				GetProcessList objGPL = new GetProcessList();
				strProcess = objGPL.GetProcessName();
				intCnt++;
				Thread.sleep(500);
			} while (strProcess.contains(strAutoFileName) && intCnt < 120);

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}

	/********************************************************************************************************
	'Description :verifyDataReatinedInEditEvntPage
	'Precondition :None
	'Arguments  :selenium,strResource
	'Returns  :String
	'Date    :22-Jan-2013
	'Author   :QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	 ***********************************************************************************************************/		

	public String verifyMandDataReatinedInEditEvntPage(Selenium selenium,
			String strEveName, String strInfo, boolean blnDateCheck,
			String strStartDate, String strEndDate, boolean blnCheckFileUpload,
			String strUploadedFileName, boolean blnSave) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		Date_Time_settings dts = new Date_Time_settings();

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit Event", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Edit Event' screen is displayed");

				// Enter the title and description
				assertEquals(selenium.getValue(propElementDetails
						.getProperty("Event.CreateEve.title")), strEveName);
				assertEquals(selenium.getText(propElementDetails
						.getProperty("Event.CreateEve.Information")), strInfo);

				if (blnDateCheck) {
					// get Start Date
					String strFndStMnth = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.StartMnt"));
					String strFndStDay = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.StartDay"));
					String strFndStYear = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.StartYear"));

					// get Start Time
					String strFndStHr = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.StartHour"));
					String strFndStMinu = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.StartMinut"));

					String strStartDateLocal = dts.converDateFormat(
							strFndStYear + strFndStMnth + strFndStDay,
							"yyyyMMMdd", "M/d/yyyy");
					strStartDateLocal = strStartDateLocal + " " + strFndStHr
							+ ":" + strFndStMinu;

					String strStartDate1=dts.addTimetoExisting(strStartDate, 1, "M/d/yyyy HH:mm");

					// get Start Date
					String strFndStMnthEnd = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.EndMnt"));
					String strFndStDayEnd = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.EndDay"));
					String strFndStYearEnd = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.EndYear"));

					// get Start Time
					String strFndStHrEnd = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.EndHour"));
					String strFndStMinuEnd = selenium
							.getSelectedLabel(propElementDetails
									.getProperty("Event.CreateEve.EndMinut"));

					String strEndDateLocal = dts.converDateFormat(
							strFndStYearEnd + strFndStMnthEnd + strFndStDayEnd,
							"yyyyMMMdd", "M/d/yyyy");

					strEndDateLocal = strEndDateLocal + " " + strFndStHrEnd
							+ ":" + strFndStMinuEnd;

					String strEndDate1=dts.addTimetoExisting(strEndDate, 1, "M/d/yyyy HH:mm");

					log4j.info(strStartDateLocal);
					log4j.info(strEndDateLocal);

					try {
						assertTrue(strStartDate.matches(strStartDateLocal)
								|| strStartDate1.matches(strStartDateLocal));
						log4j.info("Start date is retained");
					} catch (AssertionError Ae) {
						strReason = "Start date is NOT retained";
						log4j.info("Start date is NOT retained");

					}

					try {
						assertTrue(strEndDate.matches(strEndDateLocal)
								|| strEndDate1.matches(strEndDateLocal));
						log4j.info("End date is retained");
					} catch (AssertionError Ae) {
						strReason = "End date is NOT retained";
						log4j.info("End date is NOT retained");

					}

				}

				if (blnCheckFileUpload) {
					try {
						assertTrue(selenium.getText(
								"//td[text()='Attached File:']"
										+ "/following-sibling::td[1]").contains(
												strUploadedFileName));
						log4j
						.info("Uploaded file name is retained in Edit event page");
					} catch (AssertionError Ae) {
						strReason = "Uploaded file name is NOT retained in Edit event page";
						log4j
						.info("Uploaded file name is NOT retained in Edit event page");

					}
				}

				if (blnSave) {
					// click on save
					selenium.click(propElementDetails.getProperty("Save"));

					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Event Management", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("'Event Management' screen is displayed.");

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
											+ strEveName + "']"));
							log4j
							.info("Event '"
									+ strEveName
									+ "' is listed on 'Event Management' screen.");
						} catch (AssertionError ae) {
							log4j
							.info("Event '"
									+ strEveName
									+ "' is NOT listed on 'Event Management' screen.");
							strReason = "Event '"
									+ strEveName
									+ "' is NOT listed on 'Event Management' screen.";
						}

					} catch (AssertionError ae) {
						log4j
						.info("'Event Management' screen is NOT displayed.");
						strReason = "'Event Management' screen is NOT displayed.";
					}

				}

			} catch (AssertionError ae) {
				log4j.info("'Edit Event' screen is NOT displayed");
				strReason = "'Edit Event' screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String checkAttachedPDFForEvents(Selenium selenium,
			String strEveName, String strText, String strEventValue,
			String strAutoFilePath, String strPDFDownlPath,
			String strAutoFileName) throws Exception {

		String strParsedText="";
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objGeneral = new General();
		try {
			// Click on Event name in event banner
			selenium.click("//a[text()='" + strEveName + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent("//div[@id='ed"
						+ strEventValue + "']/a[text()='Attached File']"));
				log4j.info("'Attached file' link is displayed.");
				// click on Attached File link
				selenium.click("//div[@id='ed" + strEventValue
						+ "']/a[text()='Attached File']");

				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);


				try {
					assertEquals("", strReason);
					strReason = objGeneral.parsepdfText(strPDFDownlPath);
					if(strReason.equals("")){

						strReason="Parsed text not found";
					}else{
						strParsedText=strReason;
						strReason="";
					}

				} catch (AssertionError Ae) {
					strReason = strReason + Ae.toString();
				}
				try{
					assertEquals(strParsedText,strText);
					log4j.info("'Attached file' content is displayed.");
				}catch(AssertionError Ae){
					log4j.info("'Attached file' content is NOT displayed.");
					strReason = "'Attached file' content is NOT displayed.";
				}

			} catch (AssertionError Ae) {
				log4j.info("'Attached file' link is NOT displayed.");
				strReason = "'Attached file' link is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	public String createNewEventWithPageVerify(Selenium selenium, String strEveTemp,
			String strResource, String strEveName, String strInfo,
			boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try{
					assertEquals("Select Event Template", selenium
							.getText("css=h1"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);


			try {
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
				.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
						+ strEveTemp
						+ "']/preceding-sibling::td/a[text()='Create']");
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt=0;
				do{
					try{
						assertEquals("Create New Event", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}
					catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);

				try {
					assertEquals("Create New Event", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");

					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);

					try {
						if (strResource != null) {
							assertTrue(selenium
									.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
											+ strResource + "']"));
							log4j
							.info("Resource "
									+ strResource
									+ " is displayed under 'Resources to Participate in This Event' section.");

							// Click on Resource check box for the particular
							// resource
							selenium
							.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
									+ strResource
									+ "']/parent::tr/td[1]/input[@name='resourceID']");
						}

						if (blnSave) {
							// click on save
							selenium.click(propElementDetails
									.getProperty("Event.CreateEve.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							intCnt=0;
							do{
								try{
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
													+ strEveName + "']"));
									break;
								}catch(AssertionError Ae){
									Thread.sleep(1000);
									intCnt++;
								}
								catch(Exception e){
									Thread.sleep(1000);
									intCnt++;
								}
							}while(intCnt<60);

							try {
								assertEquals("Event Management", selenium
										.getText(propElementDetails
												.getProperty("Header.Text")));
								log4j.info("'Event Management' screen is displayed.");

								try {
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
													+ strEveName + "']"));
									log4j
									.info("Event '"
											+ strEveName
											+ "' is listed on 'Event Management' screen.");
								} catch (AssertionError ae) {
									log4j
									.info("Event '"
											+ strEveName
											+ "' is NOT listed on 'Event Management' screen.");
									strReason = "Event '"
											+ strEveName
											+ "' is NOT listed on 'Event Management' screen.";
								}

							} catch (AssertionError ae) {
								log4j.info("'Event Management' screen is NOT displayed.");
								strReason = "'Event Management' screen is NOT displayed.";
							}



						}

					} catch (AssertionError ae) {
						log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.");
						strReason = "Resource "
								+ strResource
								+ " is NOT displayed under 'Resources to Participate in This Event' section.";
					}
				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String selectStartDateInCreatingEvent(Selenium selenium,
			String strEveName, String strMonth, String strDay, String strYear,
			String strHour, String strMinute,boolean blnSave) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Start event
			selenium.click(propElementDetails
					.getProperty("Event.CreateEve.EveStart.NOtImmediately"));

			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartMnt"), "label="
							+ strMonth);
			selenium
			.select(propElementDetails
					.getProperty("Event.CreateEve.StartDay"), "label="
							+ strDay);
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartYear"), "label="
							+ strYear);

			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartHour"), "label="
							+ strHour);
			selenium.select(propElementDetails
					.getProperty("Event.CreateEve.StartMinut"), "label="
							+ strMinute);


			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));

				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");


					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));
						log4j.info("Event '" + strEveName
								+ "' is listed on 'Event Management' screen.");
					} catch (AssertionError ae) {
						log4j.info("Event '" + strEveName
								+ "' is NOT listed on 'Event Management' screen.");
						strReason = "Event '" + strEveName
								+ "' is NOT listed on 'Event Management' screen.";
					}

				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");

				}


			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/***********************************************************************
	   'Description  :save the event and verify it is listed or not 
	   'Precondition :None
	   'Arguments    :selenium,
	   'Returns      :String
	   'Date         :10-July-2012
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '14-June-2012                               <Name>
	 ************************************************************************/


	public String provideRenotifyTime(Selenium selenium, String strEveName,
			String strRenotifyPeriod, boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("css=input[name='ongoingNotifications'][value='Y']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click("css=input[name='ongoingNotifications'][value='Y']");


			intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("css=select[name='ongoingNotificationPeriod']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);


			selenium.select("css=select[name='ongoingNotificationPeriod']",
					strRenotifyPeriod);

			if (blnSave) {
				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					strReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/*******************************************************************
	'Description :sslectAndDeselectEventBannerOption
	'Precondition :None
	'Arguments  :selenium,strRoleValue
	'Returns  :String
	'Date    :22-Feb-2013
	'Author   :QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-Feb-2013                               <Name>
	 ********************************************************************/

	public String slectAndDeselectEventBannerOption(Selenium selenium,String strEveName, boolean blnEvntBanner, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnEvntBanner) {
				if (selenium
						.isChecked("css=input[name='displayInEventBanner']") == false) {
					selenium.click("css=input[name='displayInEventBanner']");
				}
			} else {
				if (selenium
						.isChecked("css=input[name='displayInEventBanner']")) {
					selenium.click("css=input[name='displayInEventBanner']");
				}
			}

			if (blnSave) {

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Event Management", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));
						log4j.info("Event '" + strEveName
								+ "' is listed on 'Event Management' screen.");
					} catch (AssertionError ae) {
						log4j
						.info("Event '"
								+ strEveName
								+ "' is NOT listed on 'Event Management' screen.");
						strErrorMsg = "Event '"
								+ strEveName
								+ "' is NOT listed on 'Event Management' screen.";
					}

					log4j.info("'Event Management' screen is displayed.");
				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");
					strErrorMsg = "'Event Management' screen is NOT displayed.";
				}
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectEventBannerOption function failed" + e);
			strErrorMsg = "slectAndDeselectEventBannerOption function failedd"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
    'Description  :Activate/Deactivate Event template
    'Arguments    :selenium,strSTName,blnActive,blnSave
    'Returns      :String
    'Date         :6-July-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                                     Modified By
    '<Date>                                            <Name>
	 ************************************************************************/

	public String activateAndDeactivateETAndVer(Selenium selenium,
			String strTempName, boolean blnActive, boolean blnSave)
					throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.Active")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.Active"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.Active")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.Active"));
			}
			// save
			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info("activateAndDeactivateST function failed" + e);
			strErrorMsg = "activateAndDeactivateST function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
    'Description  :Activate/Deactivate Event template
    'Arguments    :selenium,strSTName,blnActive,blnSave
    'Returns      :String
    'Date         :6-July-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                                     Modified By
    '<Date>                                            <Name>
	 ************************************************************************/

	public String verETempPresentOrNot(Selenium selenium, String strTempName,
			boolean blnPresent) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnPresent) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName + "']"));
					log4j.info("The template name "
							+ strTempName
							+ " is displayed in the 'Event Template List' screen ");
				} catch (AssertionError ae) {
					log4j.info("The template name  is NOT displayed in the 'Event Template List' screen ");
					strErrorMsg = "The template is NOT displayed in the 'Event Template List' screen";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName + "']"));
					log4j.info("The  template name "
							+ strTempName
							+ " is NOT displayed in the 'Event Template List' screen ");
				} catch (AssertionError ae) {
					log4j.info("The template name  is displayed in the 'Event Template List' screen ");
					strErrorMsg = "The template is  displayed in the 'Event Template List' screen";
				}
			}
		} catch (Exception e) {
			log4j.info("activateAndDeactivateST function failed" + e);
			strErrorMsg = "activateAndDeactivateST function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
    'Description  :Activate/Deactivate Event template
    'Arguments    :selenium,strSTName,blnActive,blnSave
    'Returns      :String
    'Date         :6-July-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                                     Modified By
    '<Date>                                            <Name>
	 ************************************************************************/

	public String veractiveAndInActivateETemp(Selenium selenium,
			String strTempName, boolean blnActive) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnActive) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName
									+ "']/following-sibling::td[2][text()='Active']"));
					log4j.info("'Active' is displayed under 'Active' column for event template '"
							+ strTempName + "'.");
				} catch (AssertionError ae) {
					log4j.info("'Active' is NOT displayed under 'Active' column for event template '"
							+ strTempName + "'.");
					strErrorMsg = "'Active' is NOT displayed under 'Active' column for event template '"
							+ strTempName + "'.";
				}
			} else {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName
									+ "']/following-sibling::td[2][text()='Disabled']"));
					log4j.info("'Disabled' is displayed under 'Active' column for event template '"
							+ strTempName + "'.");
				} catch (AssertionError ae) {
					log4j.info("'Disabled' is NOT displayed under 'Active' column for event template '"
							+ strTempName + "'.");
					strErrorMsg = "'Disabled' is NOT displayed under 'Active' column for event template '"
							+ strTempName + "'.";
				}
			}

		} catch (Exception e) {
			log4j.info("activateAndDeactivateST function failed" + e);
			strErrorMsg = "activateAndDeactivateST function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
    'Description  :Create event template with mandatory fields
    'Arguments    :selenium,strTempName,strTempDef
    'Returns      :String
    'Date         :28th-Feb-2013
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                                     Modified By
    '<Date>                                            <Name>
	 ************************************************************************/
	public String CreateETByMandFields(Selenium selenium, String strTempName,
			String strTempDef, boolean blnSave) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		String strReason = "";
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Enter Template name
			selenium.type(
					propElementDetails.getProperty("Event.CreateTemp.TempName"),
					strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);
			if (blnSave) {
				// Click on Save
				selenium.click(propElementDetails
						.getProperty("Event.CreateTemp.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);


				try {
					assertEquals("Event Template List",selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Template List' screen is displayed.");

					try {
						/*assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
										+ strTempName + "']"));*/

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']>table" +
										":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
										+ strTempName + "')"));

						log4j.info("The template name "
								+ strTempName
								+ " is displayed in the 'Event Template List' screen ");
					} catch (AssertionError ae) {
						log4j.info("The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen ");
						strReason = "The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen .";
					}

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
	/***************************************************************
	'Description :verify Data Reatined In Edit Event Template Page
	'Arguments   :selenium,strTempName,strTempDef
	'Returns     :String
	'Date        :28th-Feb-2013
	'Author      :QSG
	'----------------------------------------------------------------
	'Modified Date                                      Modified By
	'<Date>                                             <Name>
	 *****************************************************************/		

	public String verifyMandDataReatinedInEditEvntTempPage(Selenium selenium,
			String strTempName, String strTempDef) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals(selenium.getValue(
						propElementDetails
						.getProperty("Event.CreateTemp.TempName")),
						strTempName);
				assertEquals(selenium.getValue(
						propElementDetails
						.getProperty("Event.CreateTemp.Definition")),
						strTempDef);
				log4j.info("Data provided while creating event template is displayed appropriately.");

			} catch (AssertionError ae) {
				log4j.info("Data provided while creating event template is NOT displayed appropriately.");
				strReason = "Data provided while creating event template is NOT displayed appropriately.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/***************************************************************
	 'Description :Select Standard Event Type while creating ET
	 'Arguments   :None
	 'Returns     :None
	 'Date        :1st-March-2013
	 'Author      :QSG
	 '---------------------------------------------------------------
	 'Modified Date                                   Modified By
	 '<Date>                                          <Name>
	 ***************************************************************/
	public String selectStdEventTypeInCreateET(Selenium selenium,
			String strStandardET) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.select("name=standardEventTypeID", "label="
					+ strStandardET);
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:fill  fields of event template with secuirty option
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:01-March-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                                              Modified By
	'12-April-2012                                               <Name>
	 ************************************************************************/
	public String saveAndNavToEvenTNotPreferencesPage(Selenium selenium,
			String strTempName) throws Exception {

		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isTextPresent("Event Notification Preferences for "
									+ strTempName));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			try {
				assertTrue(selenium
						.isTextPresent("Event Notification Preferences for "
								+ strTempName));
				log4j.info("Event Notification Preferences for " + strTempName
						+ "is displayed");
			} catch (AssertionError ae) {
				log4j.info("Event Notification Preferences for " + strTempName
						+ "is NOT displayed");
				strReason = "Event Notification Preferences for " + strTempName
						+ "is  NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/******************************************************************
	'Description	:Verify Event template details in the template list
	'Arguments		:selenium,
	'Returns		:String
	'Date	 		:1st-March-2013
	'Author			:QSG
	'------------------------------------------------------------------
	'Modified Date                                          Modified By
	'<date>		                                            <Name>
	 *******************************************************************/

	public String verifyETDetailsInETListAlonWithHeaders(Selenium selenium,
			String strTempName, String strStdEventType, String strTempDefn,
			String strStatus, String strEvntTempIcon) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intNum = 0;

			try {

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th/a[text()='Event�Template']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));
				log4j.info("The "
						+ strTempName
						+ "listed in the event template List along with header 'Event�Template'");
				intNum++;

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th/a[text()='Description']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName
								+ "']/following-sibling::td[text()='"
								+ strTempDefn + "']"));
				log4j.info("The "
						+ strTempName
						+ "is listed in the event template List with definition "
						+ strTempDefn + " along with header 'Description'");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th/a[text()='Event�Type']"));

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName
								+ "']/following-sibling::td[text()='"
								+ strStdEventType + "']"));
				log4j.info("The "
						+ strTempName
						+ "is listed in the event template List with Std event type "
						+ strStdEventType + " along with header 'Event�Type'");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[text()='Icon']"));
				assertEquals(
						strEvntTempIcon,
						selenium.getAttribute("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']/parent::tr/td[2]/img@src"));
				log4j.info("The "
						+ strTempName
						+ "is listed in the event template List with event icon "
						+ strEvntTempIcon + " Selected along with header 'Icon'");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[text()='Action']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName
								+ "']/preceding-sibling::td/a[text()='Edit']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName
								+ "']/preceding-sibling::td/a[text()='Notifications']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName
								+ "']/preceding-sibling::td/a[text()='Security']"));
				log4j.info("The "
						+ strTempName
						+ "is listed in the event template List with 'Edit','Notifications',and 'Security'links along with header 'Action'");


				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th/a[text()='Active']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName
								+ "']/following-sibling::td[text()='"+strStatus+"']"));				
				log4j.info("The "
						+ strTempName
						+ "is listed in the event template List with '"+strStatus+"' along with header 'Active'");
			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("The "
							+ strTempName
							+ "listed NOT in the event template List along with header 'Event�Template'");
					strErrorMsg = "The "
							+ strTempName
							+ "listed NOT in the event template List along with header 'Event�Template'";
					break;
				case 2:
					log4j.info("The "
							+ strTempName
							+ "is NOT listed in the event template List with definition "
							+ strTempDefn + " along with header 'Description'");
					strErrorMsg = "The "
							+ strTempName
							+ "is NOT listed in the event template List with definition "
							+ strTempDefn + " along with header 'Description'";
					break;

				case 3:
					log4j.info("The "
							+ strTempName
							+ "is NOT listed in the event template List with Std event type "
							+ strStdEventType
							+ " along with header 'Event�Type'");
					strErrorMsg = "The "
							+ strTempName
							+ "is NOT listed in the event template List with Std event type "
							+ strStdEventType
							+ " along with header 'Event�Type'";
					break;

				case 4:
					log4j.info("The "
							+ strTempName
							+ "is NOT listed in the event template List with event icon "
							+ strEvntTempIcon
							+ " Selected along with header 'Icon'");
					strErrorMsg = "The "
							+ strTempName
							+ "is NOT listed in the event template List with event icon "
							+ strEvntTempIcon
							+ " Selected along with header 'Icon'";
					break;

				case 5:
					log4j.info("The "
							+ strTempName
							+ "is NOT listed in the event template List with 'Edit','Notifications',"
							+ "and 'Security'links along with header 'Action'");
					strErrorMsg = "The "
							+ strTempName
							+ "is NOT listed in the event template List with 'Edit',"
							+ "'Notifications',and 'Security'links along with header 'Action'";
					break;

				case 6:
					log4j.info("The "
							+ strTempName
							+ "is NOT listed in the event template List with 'Edit','Notifications',"
							+ "and 'Security'links along with header 'Action'");
					strErrorMsg = "The "
							+ strTempName
							+ "is NOT listed in the event template List with '"+strStatus+"' along with header 'Active'";
					break;
				}
			}
		} catch (Exception e) {
			log4j.info("verifyETDetailsInETListAlonWithHeaders function failed"
					+ e);
			strErrorMsg = "verifyETDetailsInETListAlonWithHeaders function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/******************************************************************
	'Description	:Verify Event template details in the template list
	'Arguments		:selenium,
	'Returns		:String
	'Date	 		:1st-March-2013
	'Author			:QSG
	'------------------------------------------------------------------
	'Modified Date                                          Modified By
	'<date>		                                            <Name>
	 *******************************************************************/
	public String editEventFields(Selenium selenium, String strEveName,
			String strEditEveName, String strInfo, boolean blnSave)
					throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			General objGeneral = new General();
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			String strElementID = "//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[6][text()="+ "'"
					+ strEditEveName + "']";

			try {

				assertEquals("Edit Event", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Edit Event' screen is displayed");

				// Enter the title and description

				if (strEditEveName != "") {
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"),
							strEditEveName);
				}

				if (strInfo != "") {
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);
				}

				if (blnSave) {
					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					strReason = objGeneral.CheckForElements(selenium,
							strElementID);
					try {
						assertEquals("", strReason);
						log4j.info("The updated data is displayed on the 'Event Management' screen. ");
					} catch (AssertionError Ae) {
						log4j.info("The updated data is not displayed on the 'Event Management' screen. ");
						log4j.info(strReason);
					}
				}

			} catch (AssertionError ae) {
				log4j.info("'Edit Event' screen is NOT displayed");
				strReason = "'Edit Event' screen is NOT displayed";
			}		
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/************************************************************
	'Description	:Verify  Multi-Region Event Confirmation page elements
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String verfyElementsInMultiRegnEvntStatusPge(Selenium selenium,
			String strMultiEventName, String strEventDescription,
			String strStrtDate, String strEndDate, String strAttachedFile,
			String strDrill, String strEndQuietly,String strRegnName[],String strETNames[]) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[1]/td[1]"),"Event Title:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[2]/td[1]"),"Event Information:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[3]/td[1]"),"Start Date and Time:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[4]/td[1]"),"End Date and Time:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[5]/td[1]"),"Attached File:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[7]/td[1]"),"Drill?");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[6]/td[1]"),"End Quietly?");

				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[1]/td[2]"),strMultiEventName);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[2]/td[2]"),strEventDescription);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[3]/td[2]"),strStrtDate);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[4]/td[2]"),strEndDate);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[5]/td[2]"),strAttachedFile);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[7]/td[2]"),strDrill);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[6]/td[2]"),strEndQuietly);


				log4j.info("Multi-Region Event Confirmation' screen is displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? ");

			} catch (AssertionError Ae) {
				strErrorMsg = "Multi-Region Event Confirmation' screen is NOT displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? "
						+ Ae;
				log4j.info("Multi-Region Event Confirmation' screen is NOT displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? ");
			}

			for (int i = 1; i <=strRegnName.length; i++) {
				try {

					assertEquals(
							selenium
							.getText("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr["+i+"]/td[1]"), strRegnName[i-1]);

					log4j.info("Regions "+strRegnName[i-1]+" selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = "Regions "+strRegnName[i]+"selected are NOT "
							+ " displayed in the table format. " + Ae;
					log4j.info("Regions "+strRegnName[i]+"selected are NOT"
							+ " displayed in the table format. ");
				}
			}

			for (int i = 1; i <= strETNames.length; i++) {
				try {
					assertEquals(
							selenium
							.getText("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr["+i+"]/td[2]"), strETNames[i-1]);

					log4j.info("Events templates "+strETNames[i-1]+"selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = "Events templates "+strETNames[i-1]+" selected "
							+ "are NOT displayed in the table format. " + Ae;
					log4j.info("Events templates  "+strETNames[i-1]+" selected are"
							+ " NOT displayed in the table format. ");
				}
			}





		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = e.toString();
		}
		return strErrorMsg;
	}



	/********************************************************************************************************
	'Description :Navigating Create New event template page
	'Precondition :None
	'Arguments  :selenium,strEveTemp
	'Returns  :String
	'Date    :22-10-2012
	'Author   :QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	 ***********************************************************************************************************/	
	public String navToCreateNewEventTemplatePage(Selenium selenium)
			throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("Event.CreateNewTemp"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Create New Event Template",
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
				assertEquals("Create New Event Template",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Create New Event Template' screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("'Create New Event Template' screen is NOT displayed");
				strReason = "'Create New Event Template' screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/************************************************************
	'Description	:Select Deselect Mandatory check box for ET
	'Arguments		:selenium,strEventName		     
	'Returns		:String
	'Date	 		:26-April-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String selAndDeselMandChkBoxForEventTemp(Selenium selenium,
			String strTempName, boolean blnCheck, boolean blnSave)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

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
							.isElementPresent(propElementDetails
									.getProperty("Event.CreateEventTemplate_MandotoryBox")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			if (blnCheck) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEventTemplate_MandotoryBox")) == false) {
					selenium
					.click(propElementDetails
							.getProperty("Event.CreateEventTemplate_MandotoryBox"));
				}
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Event.CreateEventTemplate_MandotoryBox"))) {
					selenium
					.click(propElementDetails
							.getProperty("Event.CreateEventTemplate_MandotoryBox"));
				}
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']>table" +
										":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
										+ strTempName + "')"));
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
					assertEquals("Event Template List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Event Template List' screen is displayed.");

					try {
						/*	assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
										+ strTempName + "']"));*/

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']>table" +
										":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
										+ strTempName + "')"));
						log4j
						.info("The template name "
								+ strTempName
								+ " is displayed in the 'Event Template List' screen ");
					} catch (AssertionError ae) {
						log4j
						.info("The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen ");
						strErrorMsg = "The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen .";
					}

				} catch (AssertionError ae) {
					log4j
					.info("'Event Template List' screen is NOT displayed.");
					strErrorMsg = "'Event Template List' screen is NOT displayed.";
				}
			}
		} catch (Exception e) {
			log4j.info("Failed in function selAndDeselMandChkBoxForEventTemp"
					+ e);
			strErrorMsg = "Failed in function selAndDeselMandChkBoxForEventTemp";
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Select Deselect Mandatory check box for ET
	'Arguments		:selenium,strEventName		     
	'Returns		:String
	'Date	 		:26-April-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String fillonlyAddressFieldsOfEvent(Selenium selenium,
			String strCity, String strState, String strCounty,
			boolean blnLookUpAdd, String strEveName, boolean blnSave)
					throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent("css=#city"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// look Up Address Details
			selenium.type("css=#city", strCity);
			selenium.select("css=#stateAbbrev", "label=" + strState);
			intCnt = 0;
			boolean blnCnt = true;
			do {
				try {
					assertTrue(selenium
							.isVisible("//select[@name='county']/option[text()='"
									+ strCounty + "']"));

					blnCnt = false;
				} catch (AssertionError Ae) {
					Thread.sleep(20000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(20000);
					intCnt++;

				}
			} while (intCnt < 60 && blnCnt);

			selenium.select("css=#county", "label=" + strCounty);

			if (blnLookUpAdd) {
				selenium.click("css=input[value='Lookup Address']");
				intCnt = 0;
				blnCnt = true;
				do {
					try {
						assertTrue(selenium.isVisible("//div[@id='map']"));

						blnCnt = false;
					} catch (AssertionError Ae) {
						Thread.sleep(2000);
						intCnt++;

					} catch (Exception e) {
						Thread.sleep(2000);
						intCnt++;
					}
				} while (intCnt < 60 && blnCnt);
			}
			if (blnSave) {
				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);


				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']" +
										">table>tbody>tr>td:nth-child(6):contains('"
										+ strEveName + "')"));
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
					/*assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));*/

					assertTrue(selenium
							.isElementPresent("css=div[id='mainContainer']" +
									">table>tbody>tr>td:nth-child(6):contains('"
									+ strEveName + "')"));

					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					strReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
				}
			}
		} catch (Exception e) {
			log4j.info("Failed in function fillonlyAddressFieldsOfEvent" + e);
			strReason = "Failed in function fillonlyAddressFieldsOfEvent";
		}
		return strReason;
	}
	/************************************************************
	'Description	:Select Deselect Mandatory check box for ET
	'Arguments		:selenium,strEventName		     
	'Returns		:String
	'Date	 		:26-April-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String chkAddressFieldsMandForEvent(Selenium selenium)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/"
									+ "tr/td[text()='City:']/span[text()='**']"));
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
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/"
								+ "tr/td[text()='City:']/span[text()='**']"));
				log4j.info("City is a mandotory Field.");
			} catch (AssertionError ae) {
				log4j.info("City is NOT a mandotory Field.");
				strReason = "City is NOT a mandotory Field.";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']"
								+ "/form/table/tbody/tr/td[text()='State:']/span[text()='**']"));
				log4j.info("State is a mandotory Field.");
			} catch (AssertionError ae) {
				log4j.info("State is NOT a mandotory Field.");
				strReason = "State is NOT a mandotory Field.";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/"
								+ "table/tbody/tr/td[text()='County:']/span[text()='**']"));
				log4j.info("County is a mandotory Field.");
			} catch (AssertionError ae) {
				log4j.info("County is NOT a mandotory Field.");
				strReason = "County is NOT a mandotory Field.";
			}
		} catch (Exception e) {
			log4j.info("Failed in function chkAddFieldsOrMandOrNotForEvent" + e);
			strReason = "Failed in function chkAddFieldsOrMandOrNotForEvent";
		}
		return strReason;
	}
	/************************************************************
	'Description	:Select Deselect Mandatory check box for ET
	'Arguments		:selenium,strEventName		     
	'Returns		:String
	'Date	 		:26-April-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String chkAddressFieldsNonMandForEvent(Selenium selenium) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/"
									+ "tr/td[text()='City:']"));
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
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/"
								+ "tr/td[text()='City:']/span[text()='**']"));
				log4j.info("City is a NonMandotory Field.");
			} catch (AssertionError ae) {
				log4j.info("City is NOT a mandotory Field.");
				strReason = "City is NOT a mandotory Field.";
			}
			try {
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']"
								+ "/form/table/tbody/tr/td[text()='State:']/span[text()='**']"));
				log4j.info("State is a NonMandotory Field.");
			} catch (AssertionError ae) {
				log4j.info("State is NOT a mandotory Field.");
				strReason = "State is NOT a mandotory Field.";
			}
			try {
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/form/"
								+ "table/tbody/tr/td[text()='County:']/span[text()='**']"));
				log4j.info("County is a NonMandotory Field.");
			} catch (AssertionError ae) {
				log4j.info("County is NOT a mandotory Field.");
				strReason = "County is NOT a mandotory Field.";
			}

		} catch (Exception e) {
			log4j.info("Failed in function chkAddFieldsOrMandOrNotForEvent" + e);
			strReason = "Failed in function chkAddFieldsOrMandOrNotForEvent";
		}
		return strReason;
	}

	/************************************************************
	'Description	:Select Deselect Mandatory check box for ET
	'Arguments		:selenium,strEventName		     
	'Returns		:String
	'Date	 		:26-April-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String savEvent(Selenium selenium)
			throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");


			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Event.CreateEve.Save")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			selenium.click(propElementDetails.getProperty("Event.CreateEve.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	/************************************************************
	'Description	:Select Deselect Mandatory check box for ET
	'Arguments		:selenium,strEventName		     
	'Returns		:String
	'Date	 		:26-April-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String chkErrorMsgForMandAddress(Selenium selenium)
			throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[2]"));
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
				assertEquals("The following errors occurred on this page:",
						selenium.getText("css=span.emsErrorTitle"));
				assertEquals("No city was provided.",
						selenium.getText("css=div.emsError > ul > li"));
				assertEquals(
						"No state was provided.",
						selenium.getText("//div[@id='mainContainer']/div/ul/li[2]"));
				assertEquals(
						"No county was provided.",
						selenium.getText("//div[@id='mainContainer']/div/ul/li[3]"));
				log4j.info("No city ,state and county was provided error message is displayed.");
			} catch (AssertionError ae) {
				log4j.info("No city ,state and county was provided error message is NOT displayed.");
				strReason = "No city ,state and county was provided error message is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/***********************************************************************
    'Description  :Create event template with mandatory fields
    'Arguments    :selenium,strTempName,strTempDef
    'Returns      :String
    'Date         :28th-Feb-2013
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                                     Modified By
    '<Date>                                            <Name>
	 ************************************************************************/
	public String CreateETByMandFieldsByAdminAndUser(Selenium selenium,
			String strTempName, String strTempDef, boolean blnUserSave,
			boolean blnAdminSave) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		String strReason = "";
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Enter Template name
			selenium.type(
					propElementDetails.getProperty("Event.CreateTemp.TempName"),
					strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);
			if (blnUserSave) {
				// Click on Save
				selenium.click(propElementDetails
						.getProperty("Event.CreateTemp.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']>table" +
										":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
										+ strTempName + "')"));
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
					assertEquals("Event Template List",
							selenium.getText(propElementDetails.getProperty("Header.Text")));

					log4j.info("'Event Template List' screen is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']>table" +
										":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
										+ strTempName + "')"));

						log4j.info("The template name "
								+ strTempName
								+ " is displayed in the 'Event Template List' screen ");
					} catch (AssertionError ae) {
						log4j.info("The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen ");
						strReason = "The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen .";
					}

				} catch (AssertionError ae) {
					log4j.info("'Event Template List' screen is NOT displayed.");
					strReason = "'Event Template List' screen is NOT displayed.";
				}
			}
			if (blnAdminSave) {
				// Click on Save
				selenium.click(propElementDetails
						.getProperty("Event.CreateTemp.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//table[@id='tbl_emailInd']" +
								"/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);

				// deselect Web(Select All) check box and save
				selenium
				.click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");
				selenium.click(propElementDetails
						.getProperty("Event.EventNotPref.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']>table" +
										":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
										+ strTempName + "')"));

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
					assertEquals("Event Template List",
							selenium.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Event Template List' screen is displayed.");

					try {
						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']>table" +
										":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
										+ strTempName + "')"));

						log4j.info("The template name "
								+ strTempName
								+ " is displayed in the 'Event Template List' screen ");
					} catch (AssertionError ae) {
						log4j.info("The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen ");
						strReason = "The template name "
								+ strTempName
								+ " is NOT displayed in the 'Event Template List' screen .";
					}

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
	/***********************************************************************
    'Description  :Create event template with mandatory fields
    'Arguments    :selenium,strTempName,strTempDef
    'Returns      :String
    'Date         :28th-Feb-2013
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                                     Modified By
    '<Date>                                            <Name>
	 ************************************************************************/
	public String selectStartAndEndDate(Selenium selenium, String strEveName,
			String strMonth, String strDay, String strYear, String strHour,
			String strMinute, String strEndMonth, String strEndDay,
			String strEndYear, String strEndHour, String strEndMinute,
			boolean blnStartDate, boolean blnSave) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnStartDate) {
				// Start event
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.EveStart.NOtImmediately"));
				Thread.sleep(10000);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"), "label="
								+ strMonth);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartDay"), "label="
								+ strDay);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartYear"), "label="
								+ strYear);

				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartHour"), "label="
								+ strHour);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"), "label="
								+ strMinute);
			} else {
				// End event
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.EveEnd.DateOpt"));
				Thread.sleep(10000);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndMnt"), "label="
								+ strEndMonth);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndDay"), "label="
								+ strEndDay);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndYear"), "label="
								+ strEndYear);

				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndHour"), "label="
								+ strEndHour);
				selenium.select(propElementDetails
						.getProperty("Event.CreateEve.EndMinut"), "label="
								+ strEndMinute);
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));

				selenium.waitForPageToLoad(gstrTimeOut);
				int intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']" +
										">table>tbody>tr>td:nth-child(6):contains('"+strEveName+"')"));

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
					assertEquals("Event Management", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("'Event Management' screen is displayed.");

					try {
						/*	assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
										+ strEveName + "']"));*/

						assertTrue(selenium
								.isElementPresent("css=div[id='mainContainer']" +
										">table>tbody>tr>td:nth-child(6):contains('"+strEveName+"')"));




						log4j.info("Event '" + strEveName
								+ "' is listed on 'Event Management' screen.");
					} catch (AssertionError ae) {
						log4j.info("Event '"
								+ strEveName
								+ "' is NOT listed on 'Event Management' screen.");
						strReason = "Event '"
								+ strEveName
								+ "' is NOT listed on 'Event Management' screen.";
					}

				} catch (AssertionError ae) {
					log4j.info("'Event Management' screen is NOT displayed.");

				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}


	/************************************************************
	'Description	:Verify  Multi-Region Event Confirmation page elements
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strEventNames[],strEventName
	'			     strEventDescription
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String verfyElementsInMultiRegnEvntConfrmPgeNew(Selenium selenium,
			String strMultiEventName, String strEventDescription,
			String strStrtDate, String strEndDate, String strAttachedFile,
			String strDrill, String strEndQuietly,String strRegnName[],String strETNames[]) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[1]/td[1]"),"Event Title:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[2]/td[1]"),"Event Information:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[3]/td[1]"),"Start Date and Time:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[4]/td[1]"),"End Date and Time:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[5]/td[1]"),"Attached File:");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[6]/td[1]"),"Drill?");
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[7]/td[1]"),"End Quietly?");

				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[1]/td[2]"),strMultiEventName);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[2]/td[2]"),strEventDescription);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[3]/td[2]"),strStrtDate);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[4]/td[2]"),strEndDate);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[5]/td[2]"),strAttachedFile);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[6]/td[2]"),strDrill);
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[1]/tbody/tr[7]/td[2]"),strEndQuietly);


				log4j.info("Multi-Region Event Confirmation' screen is displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? ");

			} catch (AssertionError Ae) {
				strErrorMsg = "Multi-Region Event Confirmation' screen is NOT displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? "
						+ Ae;
				log4j.info("Multi-Region Event Confirmation' screen is NOT displayed " +
						"with following details:1. Event Title 2. Event Information" +
						"3. Start Date and Time 4. End Date and Time 5. Attached File" +
						"6. Drill? 7. End Quietly? ");
			}

			for (int i = 1; i <=strRegnName.length; i++) {
				try {

					assertTrue(
							selenium
							.isElementPresent("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr/td[1][text()='"+strRegnName[i-1]+"']") );

					log4j.info("Regions "+strRegnName[i-1]+" selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = "Regions "+strRegnName[i]+"selected are NOT "
							+ " displayed in the table format. " + Ae;
					log4j.info("Regions "+strRegnName[i]+"selected are NOT"
							+ " displayed in the table format. ");
				}
			}

			for (int i = 1; i <= strETNames.length; i++) {
				try {

					assertTrue(
							selenium
							.isElementPresent("//div[@id='mainContainer']/form/table[2]" +
									"/tbody/tr/td[2][text()='"+strETNames[i-1]+"']") );


					log4j.info("Events templates "+strETNames[i-1]+"selected are"
							+ " displayed in the table format. ");

				} catch (AssertionError Ae) {
					strErrorMsg = "Events templates "+strETNames[i-1]+" selected "
							+ "are NOT displayed in the table format. " + Ae;
					log4j.info("Events templates  "+strETNames[i-1]+" selected are"
							+ " NOT displayed in the table format. ");
				}
			}





		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = e.toString();
		}
		return strErrorMsg;
	}


	/************************************************************
	'Description	:Verify Event in event banner
	'Precondition	:None
	'Arguments		:selenium,strEventName
	'Returns		:String
	'Date	 		:14-May-2013
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                            Modified By
	'                               <Name>
	 **************************************************************/

	public String verfyEventInEventBanner(Selenium selenium, String strEventName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				try{
					selenium.focus("//div[@id='eventsBanner']"
							+ "/table[@class='events']/tbody/tr/td/a[text()='"
							+ strEventName + "']");
					log4j.info("Element is focussed");
				}catch(Exception e){
					log4j.info("Element is NOT focussed");
				}
				assertTrue(selenium
						.isElementPresent("//div[@id='eventsBanner']"
								+ "/table[@class='events']/tbody/tr/td/a[text()='"
								+ strEventName + "']"));

				log4j.info("Event " + strEventName
						+ " is displayed in event Banner");

				try{
					selenium.focus("//div[@id='eventsBanner']"
							+ "/table[@class='events']/tbody/tr/td[1]/a");
					log4j.info("Element is focussed");
				}catch(Exception e){
					log4j.info("Element is NOT focussed");
				}

			} catch (AssertionError Ae) {
				log4j.info("Event " + strEventName
						+ " is NOT displayed in event Banner");

				strErrorMsg = "Event " + strEventName
						+ " is NOT displayed in event Banner" + Ae;

			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = e.toString();
		}
		return strErrorMsg;
	}


	/***********************************************************************
	'Description	:fill mandatory fields of event template with RT and ST
	'Precondition	:None
	'Arguments		:selenium,statTypeName,strStatTypDefn, blnSav	
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	 ************************************************************************/


	public String fillMandfieldsEveTempNewByNewUser(Selenium selenium,
			String strTempName, String strTempDef, String strEveColor,
			boolean blnCheckPage, String[] strResTypeVal, 
			String[] strStatusTypeval,boolean blnST,boolean blnRT)
					throws Exception {
		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Event.CreateTemp.TempName")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);


			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
							+ strEveColor);

			if (blnST) {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.click("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
				}
			} else {
				for (int i = 0; i < strStatusTypeval.length; i++) {
					selenium.isChecked("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
					selenium.click("css=input[name='st'][value='"+strStatusTypeval[i]+"']");
				}
			}

			if (blnRT) {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.click("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
				}
			} else {
				for (int i = 0; i < strResTypeVal.length; i++) {
					selenium.isChecked("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
					selenium.click("css=input[name='rt'][value='"+strResTypeVal[i]+"']");
				}
			}
			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			intCnt=0;
			do{
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
									+ strTempName + "']"));
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
				log4j.info("Event Template List screen is displayed");
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
								+ strTempName + "']"));
				log4j
				.info("The template name "+strTempName+" is displayed in the 'Event Template List' screen ");
			} catch (AssertionError ae) {
				log4j
				.info("The template name  is NOT displayed in the 'Event Template List' screen ");
				strReason = "The template is NOT displayed in the 'Event Template List' screen";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	//start//selEventTemplateIcon//
	/*****************************************************
	'Description :select event template icon 
	'Arguments   :selenium,strResource
	'Returns     :String
	'Date        :17-June-2013
	'Author      :QSG
	'-----------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	 ******************************************************/	

	public String selEventTemplateIcon(Selenium selenium, String strEveIcon,
			boolean blnSave) throws Exception {
		String lStrReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Select the Associated Icon
			selenium.select(propElementDetails
					.getProperty("Event.CreateNewTemp.AssociIcon"), "label="
							+ strEveIcon);

			if (blnSave) {
				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//selEventTemplateIcon//
	//start//selEvntSecurityforUsrWithSavAndCancel//
	/*****************************************************
	'Description :select  Event Security for Usr With Save And Cancel
	'Arguments   :selenium,strResource
	'Returns     :String
	'Date        :17-June-2013
	'Author      :QSG
	'-----------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	 ******************************************************/	

	public String selEvntSecurityforUsrWithSavAndCancel(Selenium selenium,
			String strEveTemp, String[] strUserNameCheck,
			String[] strUserNameSel, boolean blnSave, boolean blnCancel)
					throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// click on Security link for Event template
			selenium.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
					+ strEveTemp + "']/parent::tr/td[1]/a[text()='Security']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isTextPresent("Event Security for "
						+ strEveTemp));
				log4j.info("Event Security for " + strEveTemp
						+ " screen is displayed. ");

				for (int intRec = 0; intRec < strUserNameCheck.length; intRec++) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_userID']/tbody/tr/td[text()='"
										+ strUserNameCheck[intRec] + "']"));
						log4j.info("User " + strUserNameCheck[intRec]
								+ " is displayed");
					} catch (AssertionError ae) {
						log4j.info("User " + strUserNameCheck[intRec]
								+ "is NOT displayed");
						strReason = strReason + "User "
								+ strUserNameCheck[intRec]
										+ " is NOT displayed";

					}
				}

				for (int intRec = 0; intRec < strUserNameSel.length; intRec++) {
					// select the user check box
					selenium.click("//table[@id='tbl_userID']/tbody/tr/td[text()='"
							+ strUserNameSel[intRec]
									+ "']/parent::tr/td[1]/input");

				}
				if (blnSave) {
					// click on save button
					selenium.click(propElementDetails
							.getProperty("Event.EventSecurity.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Event Template List",
								selenium.getText("css=h1"));
						log4j.info("'Event Template List' screen is displayed.");

					} catch (AssertionError ae) {
						log4j.info("'Event Template List' screen is NOT displayed.");
						strReason = "'Event Template List' screen is NOT displayed.";
					}
				}
				if (blnCancel) {
					// click on cancel button
					selenium.click(propElementDetails.getProperty("Cancel"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Event Template List",
								selenium.getText("css=h1"));
						log4j.info("'Event Template List' screen is displayed.");

					} catch (AssertionError ae) {
						log4j.info("'Event Template List' screen is NOT displayed.");
						strReason = "'Event Template List' screen is NOT displayed.";
					}
				}
			} catch (AssertionError ae) {
				log4j.info("Event Security for " + strEveTemp
						+ " screen is NOT displayed.");
				strReason = "Event Security for " + strEveTemp
						+ " screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;

	}
	//end//selEvntSecurityforUsrWithSavAndCancel//

	//start//chkRTInCreatEventTemplatePage//

	/************************************************************
		'Description	:Verify Res type in create event template page
		'Precondition	:None
		'Arguments		:selenium,strOptions,blnOptions,strRight
		'Returns		:String
		'Date	 		:27/06/2013
		'Author			:QSG
		'-----------------------------------------------------------
		'Modified Date                            Modified By
		'5-April-2012                               <Name>
	 ************************************************************/

	public String chkRTInCreatEventTemplatePage(Selenium selenium,
			String strResTypeval, boolean blnRT, String statResTypeName)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnRT) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='rt'][value='"
									+ strResTypeval + "']"));
					log4j
					.info("Resource types "
							+ statResTypeName
							+ " is displayed in the 'Create Event Template' page.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Resource types "
							+ statResTypeName
							+ " is NOT displayed in the 'Create Event Template' page.";
					log4j.info("Status types " + statResTypeName
							+ " is NOT displayed in the 'Create Event' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='rt'][value='"
									+ strResTypeval + "']"));
					log4j
					.info("Resource types "
							+ statResTypeName
							+ " is NOT displayed in the 'Create Event Template' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Resource types " + statResTypeName
							+ " displayed in the 'Create Event Template' page.";
					log4j.info("Status types " + statResTypeName
							+ " displayed in the 'Create Event Template' page.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkRTInCreatEventTemplatePage function failed" + e);
			strErrorMsg = "chkRTInCreatEventTemplatePage function failedd" + e;
		}
		return strErrorMsg;
	}
	//ends//chkRTInCreatEventTemplatePage//
	/*******************************************************************
	'Description :select deselect resource in edit event page
	'Precondition :None
	'Arguments  :selenium,strRoleValue
	'Returns  :String
	'Date    :28-May-2012
	'Author   :QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'28-May-2012                               <Name>
	 ********************************************************************/

	public String selAndDeselRSEditEventPageAndClkOnSav(Selenium selenium,
			String strResource, boolean blnRS, boolean blnSave)
					throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Resource check box for the particular
			// resource

			if (blnRS) {
				if (selenium
						.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']") == false) {
					selenium
					.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
							+ strResource
							+ "']/parent::tr/td[1]/input[@name='resourceID']");
				}
			} else {
				if (selenium
						.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
								+ strResource
								+ "']/parent::tr/td[1]/input[@name='resourceID']")) {
					selenium
					.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
							+ strResource
							+ "']/parent::tr/td[1]/input[@name='resourceID']");
				}
			}

			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectRSEditEventPage function failed" + e);
			strErrorMsg = "slectAndDeselectRSEditEventPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/********************************************************************************************************
	   'Description :Create event template with all fields
	   'Precondition :None
	   'Arguments  :selenium,strTempName,strTempDef,blnCheckPage,strResTypeValue,strStatusTypeValue,strTitle,strInfo,strAlertAudio,strEveColor,strAsscIcon
	   'Returns  :String
	   'Date    :24-Oct-2013
	   'Author   :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                    <Name>
	 *************************************************************************************/
	public String CreateETBySelctngRTAndSTNew(Selenium selenium,
			String strTempName, String strTempDef, boolean blnCheckPage,
			String[] strResTypeValue, String[] strStatusTypeValue,
			String strTitle, String strInfo, String strAlertAudio,
			String strEveColor, String strAsscIcon) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		String strReason = "";
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// Enter Template name
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.TempName"), strTempName);
			// Enter Definition
			selenium.type(propElementDetails
					.getProperty("Event.CreateTemp.Definition"), strTempDef);

			if (strTitle.compareTo("") != 0) {
				// Default Event Title
				selenium.type("css=input[name=defaultTitle]", strTitle);
			}
			if (strInfo.compareTo("") != 0) {
				// Default Event Description
				selenium.type("css=textarea[name=defaultDescription]", strInfo);
			}
			if (strAlertAudio.compareTo("") != 0) {
				// select Alert Audio
				selenium.select("name=audioFilename", "label=" + strAlertAudio);
			}

			// Select the Event Colour
			selenium.select(propElementDetails
					.getProperty("Event.CreateTemp.EventColor"), "label="
					+ strEveColor);
			// Select the Associated Icon
			selenium.select(propElementDetails
					.getProperty("Event.CreateNewTemp.AssociIcon"), "label="
					+ strAsscIcon);

			for (String s : strResTypeValue) {
				selenium.click("css=input[name='rt'][value='" + s + "']");

			}

			for (String s : strStatusTypeValue) {
				selenium.click("css=input[name='st'][value='" + s + "']");

			}
			// Click on Save
			selenium.click(propElementDetails
					.getProperty("Event.CreateTemp.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			if (blnCheckPage) {

				int intCnt = 0;
				do {
					try {
						assertTrue(selenium
								.isTextPresent("Event Notification Preferences for "
										+ strTempName));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception e) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				try {
					assertTrue(selenium
							.isTextPresent("Event Notification Preferences for "
									+ strTempName));
					log4j.info("Event Template " + strTempName + "is created");
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Email')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Pager')]/input[@name='SELECT_ALL']"));
						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']"));
						log4j
								.info("E-mail, Pager and Web checkboxes are displayed for each user.");

						// deselect Web(Select All) check box and save
						selenium
								.click("//table[@id='tbl_emailInd']/thead/tr/th[contains(text(),'Web')]/input[@name='SELECT_ALL']");
						selenium.click(propElementDetails
								.getProperty("Event.EventNotPref.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

					} catch (AssertionError ae) {
						log4j
								.info("E-mail, Pager and Web checkboxes are NOT displayed for each user.");
						strReason = "E-mail, Pager and Web checkboxes are NOT displayed for each user.";
					}

				} catch (AssertionError ae) {
					log4j.info("Event Template " + strTempName
							+ "is NOT created");
					strReason = "Event Template " + strTempName
							+ "is NOT created";
				}
			}

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("css=div[id='mainContainer']>table"
									+ ":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
									+ strTempName + "')"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			try {
				assertTrue(selenium.isTextPresent("Event Template List"));

				assertTrue(selenium
						.isElementPresent("css=div[id='mainContainer']>table"
								+ ":nth-child(2)>tbody>tr>td:nth-child(3):contains('"
								+ strTempName + "')"));

				assertEquals(
						strTempDef,
						selenium
								.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
										+ strTempName + "']/parent::tr/td[5]"));

				log4j
						.info("The template name and definition is displayed "
								+ "in the 'Event Template List' screen with the specified icon");
			} catch (AssertionError ae) {
				log4j
						.info("The template name and definition is NOT displayed "
								+ "in the 'Event Template List' screen with the specified icon");
				strReason = "The template name and definition is NOT displayed"
						+ " in the 'Event Template List' screen with the specified icon";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/********************************************************************************************************
	   'Description :Create event by checking default information and title
	   'Precondition :None
	   'Arguments  :selenium,strEveTemp,strEveName,blnsave,strDefTitle,strDefInfo,strInfo
	   'Returns  :String
	   'Date    :24-Oct-2013
	   'Author   :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                    <Name>
	 *************************************************************************************/
	
	public String createEventMandFldsByVerifyingDefaultTitle(Selenium selenium,
			String strEveTemp, String strEveName, String strInfo,
			boolean blnSave, String strDefTitle, String strDefInfo)
			throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on Create New Event
			selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Select Event Template", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Select Event Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Select Event Template' screen is displayed.");

				// Click on Create for the particular Event template
				selenium
						.click("//div[@id='mainContainer']/table[@class='displayTable"
								+ " striped border sortable']/tbody/tr/td[text()='"
								+ strEveTemp
								+ "']/preceding-sibling::td/a[text()='Create']");

				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {
						assertEquals("Create New Event", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception e) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				try {

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					assertEquals("Create New Event", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("'Create New Event' screen is displayed");
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[2]/input[@value='"
									+ strDefTitle + "']"));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[2]/textarea[text()='"
									+ strDefInfo + "']"));
					log4j.info("'Default title " + strDefTitle
							+ " is displayed");
					log4j.info("'Default information  " + strDefInfo
							+ " is displayed");
					// Enter the title and description
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.title"), strEveName);
					selenium.type(propElementDetails
							.getProperty("Event.CreateEve.Information"),
							strInfo);

					if (blnSave) {
						// click on save
						selenium.click(propElementDetails.getProperty("Save"));

						selenium.waitForPageToLoad(gstrTimeOut);

						intCnt = 0;
						do {
							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								break;
							} catch (AssertionError Ae) {
								Thread.sleep(1000);
								intCnt++;
							} catch (Exception e) {
								Thread.sleep(1000);
								intCnt++;
							}
						} while (intCnt < 60);

						try {
							assertEquals("Event Management", selenium
									.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j
									.info("'Event Management' screen is displayed.");

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
												+ strEveName + "']"));
								log4j
										.info("Event '"
												+ strEveName
												+ "' is listed on 'Event Management' screen.");
							} catch (AssertionError ae) {
								log4j
										.info("Event '"
												+ strEveName
												+ "' is NOT listed on 'Event Management' screen.");
								strReason = "Event '"
										+ strEveName
										+ "' is NOT listed on 'Event Management' screen.";
							}

						} catch (AssertionError ae) {
							log4j
									.info("'Event Management' screen is NOT displayed.");
							strReason = "'Event Management' screen is NOT displayed.";
						}

					}

				} catch (AssertionError ae) {
					log4j.info("'Create New Event' screen is NOT displayed");
					strReason = "'Create New Event' screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("'Select Event Template' screen is NOT displayed.");
				strReason = "'Select Event Template' screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
  /**************************************************************************
   'Description  :Cancel event by checking default information and title
   'Precondition :None
   'Arguments    :selenium,strEveName
   'Returns      :String
   'Date         :24-Oct-2013
   'Author       :QSG
   '-----------------------------------------------------------------------
   'Modified Date                            Modified By
   '<Date>                                    <Name>
  *************************************************************************/

	public String CancelEvent(Selenium selenium, String strEveName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// click on End button for the event
			selenium.click("//div[@id='mainContainer']/table[@class='displayTable"
					+ " striped border sortable']/tbody/tr/td[6][text()="
					+ "'"
					+ strEveName + "']/parent::tr/td[1]/a[text()='Cancel']");

			boolean blnFound = false;
			int intCnt = 0;
			// wait for the confirmation
			while (blnFound == false && intCnt <= 60) {
				try {
					assertTrue(selenium
							.getConfirmation()
							.matches(
									"^Are you sure you want"
											+ " to end this event[\\s\\S]\n\nPress OK to end the event\\. "
											+ "Press Cancel if you do NOT want to end the event\\.$"));
					blnFound = true;

				} catch (AssertionError ae) {
					intCnt++;
					Thread.sleep(1000);
				} catch (Exception e) {
					intCnt++;
					Thread.sleep(1000);
				}
			}

			Thread.sleep(30000);
			try {

				log4j.info("'OK' and 'Cancel' button are displayed.");

			} catch (AssertionError Ae) {
				log4j.info("'OK' and 'Cancel' button are NOT displayed.");
				strReason = "'OK' and 'Cancel' button are NOT displayed.";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
}
