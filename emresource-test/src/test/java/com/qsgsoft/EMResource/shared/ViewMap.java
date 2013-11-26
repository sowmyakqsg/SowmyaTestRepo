package com.qsgsoft.EMResource.shared;

import java.util.Properties;


import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

public class ViewMap {

	static Logger log4j = Logger
	.getLogger("com.qsgsoft.EMResource.shared.ViewMap");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;

	public String verifyStatTypesInResourcePopup(Selenium selenium,
			String strResource, String strEventStatType[],
			String strRoleStatType[], boolean blnCheckEveStat,
			boolean blnCheckRoleStat) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// View menu
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on Map link
			selenium.click(propElementDetails.getProperty("View.ViewLink.Map"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Regional Map View", selenium
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
				assertEquals("Regional Map View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Regional Map View screen is displayed");
				intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				
				int intVisbleCnt=0;
				do{
					try{
						
						selenium.isVisible(propElementDetails
								.getProperty("ViewMap.FindResource"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);
				
				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				if (strEventStatType != null) {
					if (blnCheckEveStat) {
						for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
							try {
								assertTrue(strStatType
										.contains(strEventStatType[intRec]));
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is displayed");
							} catch (AssertionError ae) {
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is NOT displayed");
								strReason = strReason
										+ " Event Based Status type "
										+ strEventStatType[intRec]
										+ " is NOT displayed";
							}

						}
					} else {
						for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
							try {
								assertFalse(strStatType
										.contains(strEventStatType[intRec]));
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is not displayed");
							} catch (AssertionError ae) {
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is still displayed");
								strReason = strReason
										+ " Event Based Status type "
										+ strEventStatType[intRec]
										+ " is still displayed";
							}

						}
					}
				}
				if (strRoleStatType != null) {
					if (blnCheckRoleStat) {
						for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
							try {
								assertTrue(strStatType
										.contains(strRoleStatType[intRec]));
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is displayed");
							} catch (AssertionError ae) {
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is NOT displayed");
								strReason = strReason
										+ " Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is NOT displayed";
							}

						}
					} else {
						for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
							try {
								assertFalse(strStatType
										.contains(strRoleStatType[intRec]));
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is not displayed");
							} catch (AssertionError ae) {
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is still displayed");
								strReason = strReason
										+ " Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is still displayed";
							}

						}
					}
				}

			} catch (AssertionError ae) {
				log4j.info("Regional Map View screen is NOT displayed");
				strReason = "Regional Map View screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	             
	public String navToCustomViewMapFromShowMap(Selenium selenium,
			String strResource, String strEventStatType[],
			String strRoleStatType[], boolean blnCheckEveStat,
			boolean blnCheckRoleStat) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// View menu
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on Map link
			selenium.click(propElementDetails.getProperty("View.Custom.ShowMap"));
			selenium.waitForPageToLoad(gstrTimeOut);

			   try {
			    assertEquals("Custom View - Map", selenium.getText(propElementDetails
			      .getProperty("Header.Text")));
			    
			    log4j.info("Custom View - Map screen is displayed");
			    
			   } catch (AssertionError ae) {
			    log4j.info("Custom View - Map screen is NOT displayed");
			    strReason = "Custom View - Map screen is NOT displayed";
			   }
			  } catch (Exception e) {
			   log4j.info(e);
			   strReason = e.toString();
			  }
			 
			  return strReason;
			 }
			 
	 /*******************************************************************
	   'Description :varify editResource link is not present
	   'Arguments  :selenium
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                   <Name>
	   ********************************************************************/

	  public String varEditResourceLink(Selenium selenium,boolean blnEditResLink) throws Exception {

	   String strErrorMsg = "";// variable to store error mesage

	   try {
	    propEnvDetails = objReadEnvironment.readEnvironment();
	    propElementDetails = objelementProp.ElementId_FilePath();
	    gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	    
	    //click save changes
	    
	    selenium.selectWindow("");
	    selenium.selectFrame("Data");
	   
	    if(blnEditResLink==false){
	    
			try {
					assertFalse(selenium.isElementPresent(propElementDetails
							.getProperty("Resources.EditResourceDetailsLink")));

				log4j.info("'edit resource details' link is NOT present in the"
						+ " 'View Resource Detail' screen. ");

			} catch (AssertionError Ae) {

				log4j.info("'edit resource details' link is  present in the"
						+ " 'View Resource Detail' screen. ");
				strErrorMsg = "'edit resource details' link is  present in the"
						+ " 'View Resource Detail' screen. ";
			}
	    }else{
	    	try {
	    		assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Resources.EditResourceDetailsLink")));

				log4j.info("'edit resource details' link is  present in the"
						+ " 'View Resource Detail' screen. ");

			} catch (AssertionError Ae) {

				log4j.info("'edit resource details' link is NOT  present in the"
						+ " 'View Resource Detail' screen. ");
				strErrorMsg = "'edit resource details' link is NOT present in the"
						+ " 'View Resource Detail' screen. ";
			}
	    	
	    }
			
	    
	   } catch (Exception e) {
	    log4j.info("varEditInfolink function failed" + e);
	    strErrorMsg = "varEditInfolink function failed" + e;
	   }
	   return strErrorMsg;
	  }
	  
	/*******************************************************************
	   'Description :varify edit link is not present
	   'Arguments  :selenium
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                   <Name>
	   ********************************************************************/

	public String varEditInfolink(Selenium selenium, boolean blneditLink)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click save changes

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blneditLink == false) {
				try {
					assertFalse(selenium.isElementPresent(propElementDetails
							.getProperty("Resources.EditInfoLink")));

					log4j.info("'Edit Info' link is not present.");

				} catch (AssertionError Ae) {

					log4j.info("'Edit Info' link is not present.");
					strErrorMsg = "'Edit Info' link is not present. ";
				}
			} else {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Resources.EditInfoLink")));

					log4j.info("'Edit Info' link is not present.");

				} catch (AssertionError Ae) {

					log4j.info("'Edit Info' link is not present.");
					strErrorMsg = "'Edit Info' link is not present. ";
				}

			}
			selenium.click("css=div.smallLink > a");
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("varEditInfolink function failed" + e);
			strErrorMsg = "varEditInfolink function failed" + e;
		}
		return strErrorMsg;
	}
	  
	  
	public String verifyStatTypeInViewResDetail(Selenium selenium,
			String strSection, String strEventStatType[],
			String strRoleStatType[], boolean blnCheckEveStat,
			boolean blnCheckRoleStat) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// click on View Info
			selenium.click(propElementDetails.getProperty("Resources.ViewInfoLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");

				if (blnCheckEveStat) {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a[text()='"
									+ strSection + "']"));
					for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
						try {
							assertTrue(selenium
									.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
											+ strEventStatType[intRec] + "']"));
							log4j.info("Event Based Status type "
									+ strEventStatType[intRec]
									+ " is displayed");
						} catch (AssertionError ae) {
							log4j.info("Event Based Status type "
									+ strEventStatType[intRec]
									+ " is NOT displayed");
							strReason = strReason + " Event Based Status type "
									+ strEventStatType[intRec]
									+ " is NOT displayed";
						}

					}
				} else {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a[text()='"
									+ strSection + "']"));
					for (int intRec = 0; intRec < strEventStatType.length; intRec++) {

						try {
							assertFalse(selenium
									.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
											+ strEventStatType[intRec] + "']"));
							log4j.info("Event Based Status type "
									+ strEventStatType[intRec]
									+ " is not displayed");
						} catch (AssertionError ae) {
							log4j.info("Event Based Status type "
									+ strEventStatType[intRec]
									+ " is still displayed");
							strReason = strReason + " Event Based Status type "
									+ strEventStatType[intRec]
									+ " is still displayed";
						}

					}
				}

				if (blnCheckRoleStat) {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a[text()='"
									+ strSection + "']"));
					for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
						try {
							assertTrue(selenium
									.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
											+ strRoleStatType[intRec] + "']"));
							log4j
									.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is displayed");
						} catch (AssertionError ae) {
							log4j.info("Role Based Status type "
									+ strRoleStatType[intRec]
									+ " is NOT displayed");
							strReason = strReason + " Role Based Status type "
									+ strRoleStatType[intRec]
									+ " is NOT displayed";
						}

					}
				} else {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a[text()='"
									+ strSection + "']"));
					for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
						try {
							assertFalse(selenium
									.isElementPresent("//table[starts-with(@id,'stGroup')]" +
											"/tbody/tr/td[2]/a[text()='"
											+ strRoleStatType[intRec] + "']"));
							log4j
									.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is displayed");
						} catch (AssertionError ae) {
							log4j.info("Role Based Status type "
									+ strRoleStatType[intRec]
									+ " is NOT displayed");
							strReason = strReason + " Role Based Status type "
									+ strRoleStatType[intRec]
									+ " is NOT displayed";
						}

					}
				}

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason = "View Resource Detail screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	
	/***************************************************************
	'Description	:Verify status updated time is fetched
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String fetchStatusTypeUpdateTime(Selenium selenium,
			String strResource, String strRoleStatType,
			boolean blnCheckRoleStat) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// View menu
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on Map link
			selenium.click(propElementDetails.getProperty("View.ViewLink.Map"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Regional Map View", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Regional Map View screen is displayed");
				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				
				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);
	
					String strStatType = selenium.getText(propElementDetails
							.getProperty("ViewMap.ResPopup.StatTypeList"));
					if (strRoleStatType != null) {
						if (blnCheckRoleStat) {

							try {
								assertTrue(strStatType
										.contains(strRoleStatType));
								log4j.info("Role Based Status type "
										+ strRoleStatType + " is displayed");

							} catch (AssertionError ae) {
								log4j
										.info("Role Based Status type "
												+ strRoleStatType
												+ " is NOT displayed");
								strReason = strReason
										+ " Role Based Status type "
										+ strRoleStatType + " is NOT displayed";
							}

						} else {

							try {
								assertFalse(strStatType
										.contains(strRoleStatType));
								log4j
										.info("Role Based Status type "
												+ strRoleStatType
												+ " is not displayed");
							} catch (AssertionError ae) {
								log4j.info("Role Based Status type "
										+ strRoleStatType
										+ " is still displayed");
								strReason = strReason
										+ " Role Based Status type "
										+ strRoleStatType
										+ " is still displayed";
							}

						}
					}
			
			} catch (AssertionError ae) {
				log4j.info("Regional Map View screen is NOT displayed");
				strReason = "Regional Map View screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function fetchStatusTypeUpdateTime " + e;
		}
		return strReason;
	}
	/***************************************************************
	'Description	:Verify status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateStatusType(Selenium selenium, String strResource,
			String[] strRoleStatTypeValue) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

				for (String s : strRoleStatTypeValue) {

					log4j.info(selenium
							.getValue("css=input[name='status_value_" + s
									+ "']"));

					selenium.click("css=#st_" + s + "");

					if ((selenium.getValue("css=input[name='status_value_" + s
							+ "']").equals("1"))
							|| (selenium
									.getValue("css=input[name='status_value_"
											+ s + "']").equals("0"))
							|| selenium.getValue(
									"css=input[name='status_value_" + s + "']")
									.equals("--")) {
						selenium.type("css=input[name='status_value_" + s
								+ "']", "2");
						// selenium.type("css=#comment_" + s + "", "updated 2");

						selenium.click("css=input[value='Save']");
						selenium.waitForPageToLoad(gstrTimeOut);

						int intCnt = 0;
						while (selenium
								.isElementPresent("//select[@id='resourceFinder']/option[text()='"
										+ strResource + "']") == false
								&& intCnt < 40) {
							Thread.sleep(1000);
							intCnt++;

						}

						// select the Find Resource option
						selenium.select(propElementDetails
								.getProperty("ViewMap.FindResource"), "label="
								+ strResource);
						
						int intVisbleCnt=0;
						do{
							try{
								selenium.isElementPresent(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								selenium.isVisible(propElementDetails
									.getProperty("ViewMap.ResPopup"));
								Thread.sleep(10000);
								break;
								
							}catch(Exception e){
								intVisbleCnt++;
								Thread.sleep(1000);
							}
							
						}while(intVisbleCnt<20);

						try {
							assertEquals(
									selenium
											.getText("css=div.emsText.maxheight > span"),
									"2");
							log4j.info("Status updated is displayed");

						} catch (AssertionError Ae) {
							log4j.info("Status updated is NOT displayed" + Ae);
							strReason = "Status updated is NOT displayed" + Ae;
						}

					} else if (selenium.getValue(
							"css=input[name='status_value_" + s + "']").equals(
							"2")) {

						selenium.type("css=input[name='status_value_" + s
								+ "']", "1");

						// selenium.type("css=#comment_" + s + "", "updated");

						selenium.click("css=input[value='Save']");
						selenium.waitForPageToLoad(gstrTimeOut);

						int intCnt = 0;
						while (selenium
								.isElementPresent("//select[@id='resourceFinder']/option[text()='"
										+ strResource + "']") == false
								&& intCnt < 40) {
							Thread.sleep(1000);
							intCnt++;

						}

						// select the Find Resource option
						selenium.select(propElementDetails
								.getProperty("ViewMap.FindResource"), "label="
								+ strResource);

						int intVisbleCnt=0;
						do{
							try{
								selenium.isElementPresent(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								selenium.isVisible(propElementDetails
									.getProperty("ViewMap.ResPopup"));
								Thread.sleep(10000);
								break;
								
							}catch(Exception e){
								intVisbleCnt++;
								Thread.sleep(1000);
							}
							
						}while(intVisbleCnt<20);

						try {
							assertEquals(
									selenium
											.getText("css=div.emsText.maxheight > span"),
									"1");
							log4j.info("Status updated is displayed");

						} catch (AssertionError Ae) {
							log4j.info("Status updated is NOT displayed" + Ae);
							strReason = "Status updated is NOT displayed" + Ae;
						}

					}

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Multi status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateMultiStatusType(Selenium selenium, String strResource,
			String strMultiST, String strRoleStatTypeValue, String strStatus,
			String strStatusValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status",selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");

				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				selenium.click("css=#statusValue_" + strStatusValue + "");

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);
				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strStatus + "']"));
					log4j.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j.info("Status updated is NOT displayed in Resource pop up" + Ae);
					strReason = "Status updated is NOT displayed in Resource pop up" + Ae;

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	
	 /***************************************************************
    'Description :navigating to update status promt screen
    'Precondition :None
    'Arguments  :selenium,strResource
    'Returns  :strReason
    'Date    :23-Aug-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String navToUpdateStatusPrompt(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("StatusType.UpdateStatusLink")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click(propElementDetails
					.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Update Status", selenium
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
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToUserView " + e.toString();
		}
		return strReason;
	}

	/***************************************************************
	'Description	:Verify Multi status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateMultiStatusType_ChangeVal(Selenium selenium, String strResource,
			String strMultiST, String strRoleStatTypeValue, String strStatus1,
			String strStatusValue1 ,String strStatus2,
			String strStatusValue2) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strStatVal=strStatusValue1;
			String strChStatus=strStatus1;
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("StatusType.UpdateStatusLink")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt=0;
			do{
				try {

					assertEquals("Update Status",selenium
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
				assertEquals("Update Status",selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");

				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if(selenium.isChecked("css=#statusValue_" + strStatusValue1 + "")){
					strStatVal=strStatusValue2;
					strChStatus=strStatus2;
				}
				selenium.click("css=#statusValue_" + strStatVal + "");

				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strChStatus + "']"));
					log4j.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j.info("Status updated is NOT displayed in Resource pop up" + Ae);
					strReason = "Status updated is NOT displayed in Resource pop up" + Ae;

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Multi status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateMultiStatusTypeForOverdue(Selenium selenium,
			 String strRoleStatTypeValue,String strStatusValue ) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
						
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if(selenium.isChecked("css=#st_" + strRoleStatTypeValue + "")){
				log4j.info("Status Type is explanded" );
							
				selenium.click("css=#statusValue_" + strStatusValue + "");

			
			}else{
				log4j.info("Status Type is NOT explanded" );
				strReason = "Status Type is NOT explanded" ;
			}		
		

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Multi status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String saveAndVerifyUpdtST(Selenium selenium, String strResource,
			String[] strStatus) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
		
			selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);

			int intVisbleCnt=0;
			do{
				try{
					selenium.isElementPresent(propElementDetails
							.getProperty("ViewMap.ResPopup"));
					selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup"));
					Thread.sleep(10000);
					break;
					
				}catch(Exception e){
					intVisbleCnt++;
					Thread.sleep(1000);
				}
				
			}while(intVisbleCnt<20);
			for(int inti=0;inti<strStatus.length;inti++){
				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strStatus[inti] + "']"));
					log4j.info("Status "+ strStatus[inti] +" updated is displayed in Resource pop up");
	
				} catch (AssertionError Ae) {
					log4j.info("Status "+ strStatus[inti] +" updated is NOT displayed in Resource pop up" + Ae);
					strReason = strReason+" Status "+ strStatus[inti] +" updated is NOT displayed in Resource pop up" + Ae;
	
				}
			}
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify status type is updated along with comments
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:06-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String verifyUpdSTValandComments(Selenium selenium, String strResource,
			String strUpdtVal,String strComments) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
					
			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);

			int intVisbleCnt=0;
			do{
				try{
					selenium.isElementPresent(propElementDetails
							.getProperty("ViewMap.ResPopup"));
					selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup"));
					Thread.sleep(10000);
					break;
					
				}catch(Exception e){
					intVisbleCnt++;
					Thread.sleep(1000);
				}
				
			}while(intVisbleCnt<20);

		
				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strUpdtVal+ "']"));
					selenium.click("//div[text()='Comments']");
					assertTrue(selenium.isElementPresent("//div[@class='emsText maxheight'][text()='"
							+ strComments+ "']"));
					log4j.info("Status type updated value "+ strUpdtVal +" is displayed in Resource pop up along with comments");
	
				} catch (AssertionError Ae) {
					log4j.info("Status type updated value "+ strUpdtVal +" is NOT displayed in Resource pop up along with comments");
					strReason = strReason+" Status type updated value "+ strUpdtVal +" is NOT displayed in Resource pop up along with comments" + Ae;
	
				}
			
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdSTValandComments " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify last updated time is updated along with comments
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:21-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String verifyUpdValCommentsAndLastUpdTimeInResPopup(Selenium selenium, String strResource,
			String strUpdtVal,String strComments,int intPos,String strLastUpdTime) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
					
			try {
				assertTrue(selenium.isElementPresent("//span["+intPos+"][text()='"
						+ strUpdtVal+ "']"));
				String a=selenium.getText("//span["+(intPos+1)+"]");
				System.out.println(a);
				assertEquals(selenium.getText("//span["+(intPos+1)+"]"),
						strLastUpdTime);
				selenium.click("//div[text()='Comments']");
				assertTrue(selenium.isElementPresent("//div[@class='emsText maxheight'][text()='"
						+ strComments+ "']"));
				log4j.info("Status type updated value "+ strUpdtVal +" is displayed in Resource pop up along with comments and last updated time"+strComments+" "+strLastUpdTime);

			} catch (AssertionError Ae) {
				log4j.info("Status type updated value "+ strUpdtVal +" is NOT displayed in Resource pop up along with comments and last updated time"+strComments+" "+strLastUpdTime);
				strReason = strReason+" Status type updated value "+ strUpdtVal +" is NOT displayed in Resource pop up along with comments and last updated time"+strComments+" "+strLastUpdTime + Ae;

			}
			
						
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdSTValandComments " + e;
		}
		return strReason;
	}
	
	/***************
	 'Description :Verify status type is updated
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :10-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String[] updateStatusTypeWithTime(Selenium selenium,
			String strResource, String[] strRoleStatTypeValue,
			String strTimeFormat) throws Exception {

		String strReason[] = new String[2];
		strReason[0] = "";
		strReason[1] = "";
		Date_Time_settings dts = new Date_Time_settings();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("StatusType.UpdateStatusLink")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click(propElementDetails
					.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			intCnt=0;
			do{
				try {
					assertEquals("Update Status", selenium
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
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

				for (String s : strRoleStatTypeValue) {
					
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='status_value_" + s
									+ "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					

					log4j.info(selenium
							.getValue("css=input[name='status_value_" + s
									+ "']"));
					
					

					selenium.click("css=#st_" + s + "");

					if ((selenium.getValue("css=input[name='status_value_" + s
							+ "']").equals("1"))
							|| (selenium.getValue(
									"css=input[name='status_value_" + s + "']")
									.equals("0") || selenium.getValue(
									"css=input[name='status_value_" + s + "']")
									.equals(""))) {
						selenium.type("css=input[name='status_value_" + s
								+ "']", "2");
						// selenium.type("css=#comment_" + s + "", "updated 2");

						selenium.click(propElementDetails
								.getProperty("UpdateStatus.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

						// System Time
						strReason[1] = dts.timeNow(strTimeFormat);
						System.out.println(strReason[1]);

						intCnt = 0;
						while (selenium
								.isElementPresent("//select[@id='resourceFinder']/option[text()='"
										+ strResource + "']") == false
								&& intCnt < 40) {
							Thread.sleep(1000);
							intCnt++;

						}

						// select the Find Resource option
						selenium.select(propElementDetails
								.getProperty("ViewMap.FindResource"), "label="
								+ strResource);

						int intVisbleCnt = 0;
						do {
							try {
								selenium.isElementPresent(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								selenium.isVisible(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								Thread.sleep(10000);
								break;

							} catch (Exception e) {
								intVisbleCnt++;
								Thread.sleep(1000);
							}

						} while (intVisbleCnt < 20);

						try {
							assertEquals(
									selenium
											.getText("css=div.emsText.maxheight > span"),
									"2");
							log4j.info("Status updated is displayed");

						} catch (AssertionError Ae) {
							log4j.info("Status updated is NOT displayed" + Ae);
							strReason[0] = "Status updated is NOT displayed"
									+ Ae;
						}

					} else if (selenium.getValue(
							"css=input[name='status_value_" + s + "']").equals(
							"2")) {

						selenium.type("css=input[name='status_value_" + s
								+ "']", "1");

						// selenium.type("css=#comment_" + s + "", "updated");

						selenium.click(propElementDetails
								.getProperty("UpdateStatus.Save"));
						selenium.waitForPageToLoad(gstrTimeOut);

						// System Time
						strReason[1] = dts.timeNow(strTimeFormat);
						System.out.println(strReason[1]);

						intCnt = 0;
						while (selenium
								.isElementPresent("//select[@id='resourceFinder']/option[text()='"
										+ strResource + "']") == false
								&& intCnt < 40) {
							Thread.sleep(1000);
							intCnt++;

						}

						// select the Find Resource option
						selenium.select(propElementDetails
								.getProperty("ViewMap.FindResource"), "label="
								+ strResource);

						int intVisbleCnt = 0;
						do {
							try {
								selenium.isElementPresent(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								selenium.isVisible(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								Thread.sleep(10000);
								break;

							} catch (Exception e) {
								intVisbleCnt++;
								Thread.sleep(1000);
							}

						} while (intVisbleCnt < 20);

						try {
							assertEquals(
									selenium
											.getText("css=div.emsText.maxheight > span"),
									"1");
							log4j.info("Status updated is displayed");

						} catch (AssertionError Ae) {
							log4j.info("Status updated is NOT displayed" + Ae);
							strReason[0] = "Status updated is NOT displayed"
									+ Ae;
						}

					}

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason[0] = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason[0] = "Failed in function updateStatusTypeWithTime " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Multi status type is updated with reason
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:02-July-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateMultiStatusTypeWithReason(Selenium selenium, String strResource,
			String strMultiST, String strRoleStatTypeValue, String strStatus1,
			String strStatusValue1 ,String strStatus2,
			String strStatusValue2,String strStatReasonVal1,String strStatReasonVal2) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strStatVal=strStatusValue1;
			String strChStatus=strStatus1;
			String strStReasonVal=strStatReasonVal1;
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");

				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if(selenium.isChecked("css=#statusValue_" + strStatusValue1 + "")){
					strStatVal=strStatusValue2;
					strChStatus=strStatus2;
					strStReasonVal=strStatReasonVal2;
				}
				selenium.click("css=#statusValue_" + strStatVal + "");
				//select the status reason
				selenium.click("css=[name='status_reason_id_"+strRoleStatTypeValue+"_"+strStatVal+"'][value='"+strStReasonVal+"']");
				
				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strChStatus + "']"));
					log4j.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j.info("Status updated is NOT displayed in Resource pop up" + Ae);
					strReason = "Status updated is NOT displayed in Resource pop up" + Ae;

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateMultiStatusTypeWithReason " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Multi status type is updated with reason
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:23-10-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateMultiSTWithReason(Selenium selenium, String strResource,
			String strMultiST, String strRoleStatTypeValue, String strStatus1,
			String strStatusValue1 ,String strStatReasonVal[]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
		
				selenium.click("css=#st_" + strRoleStatTypeValue + "");
			
				selenium.click("css=#statusValue_" + strStatusValue1 + "");
				
				for (int intR=0;intR<strStatReasonVal.length;intR++){
					//select the status reason
					selenium.click("css=[name='status_reason_id_"+strRoleStatTypeValue+"_"+strStatusValue1+"'][value='"+strStatReasonVal[intR]+"']");
				}	

		
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateMultiSTWithReason " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Number or Text status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:25-June-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateNumStatusType(Selenium selenium, String strResource,
			String strStatType, String strRoleStatTypeValue,
			String strUpdatTxtValue1, String strUpdatTxtValue2)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strUpdValue = strUpdatTxtValue1;
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent
							(propElementDetails.getProperty("StatusType.UpdateStatusLink")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
             
            selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
 			selenium.waitForPageToLoad(gstrTimeOut);

 			
 			intCnt=0;
			do{
				try {

					assertEquals("Update Status", selenium
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
				assertEquals("Update Status", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");
				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if (selenium.getValue(
						"css=input[name='status_value_" + strRoleStatTypeValue
								+ "']").equals(strUpdatTxtValue1)) {
					strUpdValue = strUpdatTxtValue2;
				}
				selenium.type("css=input[name='status_value_"
						+ strRoleStatTypeValue + "']", strUpdValue);

				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//span[text()='"
								+ strUpdValue + "']"));
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
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strUpdValue + "']"));
					log4j
							.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j
							.info("Status updated is NOT displayed in Resource pop up"
									+ Ae);
					strReason = "Status updated is NOT displayed in Resource pop up"
							+ Ae;

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}

	/***************************************************************
	'Description	:Verify Number or Text status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:24-09-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String verifyUpdValInMap(Selenium selenium,String strUpdValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strUpdValue + "']"));
					log4j.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j.info("Status updated is NOT displayed in Resource pop up" + Ae);
					strReason = "Status updated is NOT displayed in Resource pop up" + Ae;

				}		

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdValInMap " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Saturation score status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:25-June-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateSatuScoreStatusType(Selenium selenium, String strResource,
			String strStatType, String strSTValue, String strUpdateValue1[],String strUpdateValue2[],String strSatuValue1,String strSatuValue2) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status",selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");
				selenium.click("css=#st_" + strSTValue + "");
				boolean blnChange=false;
				if(selenium.getValue("css=#lobbyCapacity"+strSTValue).equals(strUpdateValue1[8])){
					//Enter the values for fields
					selenium.type("css=#edBedsOccupied"+strSTValue, strUpdateValue2[0]);
					selenium.type("css=#lobbyPatients"+strSTValue, strUpdateValue2[1]);
					selenium.type("css=#ambulancePatients"+strSTValue, strUpdateValue2[2]);
					selenium.type("css=#admitsGeneral"+strSTValue, strUpdateValue2[3]);
					selenium.type("css=#admitsIcu"+strSTValue, strUpdateValue2[4]);
					selenium.type("css=#oneOnOnePatients"+strSTValue, strUpdateValue2[5]);
					selenium.type("css=#shortStaffRn"+strSTValue, strUpdateValue2[6]);
					selenium.type("css=#edBedsAssigned"+strSTValue, strUpdateValue2[7]);
					selenium.type("css=#lobbyCapacity"+strSTValue, strUpdateValue2[8]);	
					blnChange=true;
				}else{
					//Enter the values for fields
					selenium.type("css=#edBedsOccupied"+strSTValue, strUpdateValue1[0]);
					selenium.type("css=#lobbyPatients"+strSTValue, strUpdateValue1[1]);
					selenium.type("css=#ambulancePatients"+strSTValue, strUpdateValue1[2]);
					selenium.type("css=#admitsGeneral"+strSTValue, strUpdateValue1[3]);
					selenium.type("css=#admitsIcu"+strSTValue, strUpdateValue1[4]);
					selenium.type("css=#oneOnOnePatients"+strSTValue, strUpdateValue1[5]);
					selenium.type("css=#shortStaffRn"+strSTValue, strUpdateValue1[6]);
					selenium.type("css=#edBedsAssigned"+strSTValue, strUpdateValue1[7]);
					selenium.type("css=#lobbyCapacity"+strSTValue, strUpdateValue1[8]);	
				}
			
				
				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

				if(blnChange){
					try {
						assertTrue(selenium.isElementPresent("//span[text()='"
								+ strSatuValue2 + "']"));
						log4j.info("Status updated is displayed in Resource pop up");

					} catch (AssertionError Ae) {
						log4j.info("Status updated is NOT displayed in Resource pop up" + Ae);
						strReason = "Status updated is NOT displayed in Resource pop up" + Ae;

					}

				}else{
					try {
						assertTrue(selenium.isElementPresent("//span[text()='"
								+ strSatuValue1 + "']"));
						log4j.info("Status updated is displayed in Resource pop up");

					} catch (AssertionError Ae) {
						log4j.info("Status updated is NOT displayed in Resource pop up" + Ae);
						strReason = "Status updated is NOT displayed in Resource pop up" + Ae;

					}

				}
			
			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String verifyMultiSTUpdatedInRSpopUp(Selenium selenium,
			String strResource, String strST) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);

			int intVisbleCnt=0;
			do{
				try{
					selenium.isElementPresent(propElementDetails
							.getProperty("ViewMap.ResPopup"));
					selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup"));
					Thread.sleep(10000);
					break;
					
				}catch(Exception e){
					intVisbleCnt++;
					Thread.sleep(1000);
				}
				
			}while(intVisbleCnt<20);

			try {
				assertEquals(selenium
						.getText("css=div.emsText.maxheight > span"), strST);
				log4j.info("Status updated is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Status updated is NOT displayed" + Ae);
				strReason = "Status updated is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info("Failed in function verifyMultiSTUpdatedInRSpopUp " + e);
			strReason = "Failed in function verifyMultiSTUpdatedInRSpopUp " + e;
		}
		return strReason;
	}

	public String navToEditResourceFromMapView(Selenium selenium,String strResource) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			//click on Edit Info
			selenium.click(propElementDetails.getProperty("Resources.EditInfoLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Edit Resource", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Edit Resource page is displayed");
			
			} catch (AssertionError Ae) {

				strReason = "Edit Resource page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Resource page is NOT displayed"
								+ Ae);
			}
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToEditResourceFromMapView " + e;
		}
		return strReason;
	}
	
	public String navToCustomViewMapFromShowMap(Selenium selenium) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// View menu
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on Map link
			selenium.click(propElementDetails.getProperty("View.Custom.ShowMap"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Custom View - Map", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				
				log4j.info("Custom View - Map screen is displayed");
				
			} catch (AssertionError ae) {
				log4j.info("Custom View - Map screen is NOT displayed");
				strReason = "Custom View - Map screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
	
		return strReason;
	}
	
	public String verifyStatTypesInResourcePopup_ShowMap(Selenium selenium,
			String strResource, String strEventStatType[],
			String strRoleStatType[], boolean blnCheckEveStat,
			boolean blnCheckRoleStat) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);

			int intVisbleCnt=0;
			do{
				try{
					selenium.isElementPresent(propElementDetails
							.getProperty("ViewMap.ResPopup"));
					selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup"));
					Thread.sleep(10000);
					break;
					
				}catch(Exception e){
					intVisbleCnt++;
					Thread.sleep(1000);
				}
				
			}while(intVisbleCnt<20);

				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				if (strEventStatType != null) {
					if (blnCheckEveStat) {
						for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
							try {
								assertTrue(strStatType
										.contains(strEventStatType[intRec]));
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is displayed");
							} catch (AssertionError ae) {
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is NOT displayed");
								strReason = strReason
										+ " Event Based Status type "
										+ strEventStatType[intRec]
										+ " is NOT displayed";
							}

						}
					} else {
						for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
							try {
								assertFalse(strStatType
										.contains(strEventStatType[intRec]));
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is not displayed");
							} catch (AssertionError ae) {
								log4j.info("Event Based Status type "
										+ strEventStatType[intRec]
										+ " is still displayed");
								strReason = strReason
										+ " Event Based Status type "
										+ strEventStatType[intRec]
										+ " is still displayed";
							}

						}
					}
				}
				if (strRoleStatType != null) {
					if (blnCheckRoleStat) {
						for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
							try {
								assertTrue(strStatType
										.contains(strRoleStatType[intRec]));
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is displayed");
							} catch (AssertionError ae) {
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is NOT displayed");
								strReason = strReason
										+ " Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is NOT displayed";
							}

						}
					} else {
						for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
							try {
								assertFalse(strStatType
										.contains(strRoleStatType[intRec]));
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is not displayed");
							} catch (AssertionError ae) {
								log4j.info("Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is still displayed");
								strReason = strReason
										+ " Role Based Status type "
										+ strRoleStatType[intRec]
										+ " is still displayed";
							}

						}
					}
				}
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	/*****************************************************************
	 'Description : Check Edit Info link is present in resource popup
	 'Precondition :None
	 'Arguments  :selenium,strResource,blnAvailable
	 'Returns  :String
	 'Date    :06-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                <Name>
	 *****************************************************************/
	public String checkEditInfoLinkInResPopup(Selenium selenium,
			String strResource, boolean blnAvailable) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);

			int intVisbleCnt=0;
			do{
				try{
					selenium.isElementPresent(propElementDetails
							.getProperty("ViewMap.ResPopup"));
					selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup"));
					Thread.sleep(10000);
					break;
					
				}catch(Exception e){
					intVisbleCnt++;
					Thread.sleep(1000);
				}
				
			}while(intVisbleCnt<20);

			
				if (blnAvailable) {
					try {
						assertTrue(selenium.isElementPresent("link=Edit Info"));
						log4j
								.info("'Edit info' link is present in the resource pop up window.");

					} catch (AssertionError Ae) {
						strReason = "'Edit info' link is NOT present in the resource pop up window.";
						log4j
								.info("'Edit info' link is NOT present in the resource pop up window.");
					}
				} else {
					try {
						assertFalse(selenium.isElementPresent("link=Edit Info"));
						log4j
								.info("'Edit info' link is not present in the resource pop up window.");

					} catch (AssertionError Ae) {
						strReason = "'Edit info' link is present in the resource pop up window.";
						log4j
								.info("'Edit info' link is present in the resource pop up window.");
					}
				}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}

	 /***
	 'Description : Check Edit Resource Detail link is present in View Resource Detail
	 'Precondition :None
	 'Arguments  :selenium,blnAvailable
	 'Returns  :String
	 'Date    :06-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                <Name>
	 *****************************************************************/
	public String navAndCheckEditResDetLinkInViewResDetail(Selenium selenium,
			boolean blnAvailable) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// click on View Info
			selenium.click(propElementDetails.getProperty("Resources.ViewInfoLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");

				if (blnAvailable) {
					try {
						assertTrue(selenium
								.isElementPresent(propElementDetails.getProperty("Resources.EditResourceDetailsLink")));
						log4j
								.info("'Edit resource details' link is present in the resource detail screen.");

					} catch (AssertionError Ae) {
						strReason = "'Edit resource details' link is NOT present in the resource detail screen. ";
						log4j
								.info("'Edit resource details' link is NOT present in the resource detail screen. ");
					}
				} else {
					try {
						assertFalse(selenium
								.isElementPresent(propElementDetails.getProperty("Resources.EditResourceDetailsLink")));
						log4j
								.info("'Edit resource details' link is not present in the resource detail screen. ");

					} catch (AssertionError Ae) {
						strReason = "'Edit resource details' link is present in the resource detail screen. ";
						log4j
								.info("'Edit resource details' link is present in the resource detail screen. ");
					}
				}

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason = "View Resource Detail screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	/*******************************************************************************************
	' Description: Fetch the Updated Time Values for the status type.
	' Precondition: N/A 
	' Arguments: selenium, pStrStatTypeIndex
	' Returns: String[] 
	' Date: 26-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String[] getUpdTimeValue(Selenium selenium, String pStrStatTypeIndex) throws Exception
	{
		String[] lStrReason=new String[5];
		lStrReason[4]="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			String strStatUpdTime = selenium.getText("//span[@class='time'][" + pStrStatTypeIndex + "]");
			strStatUpdTime=strStatUpdTime.substring(1, 13);
			String[] strArStatUpdTime=strStatUpdTime.split(" ");
			String[] strArStTime=strArStatUpdTime[2].split(":");
			
			lStrReason[0]=strArStatUpdTime[0];
			lStrReason[1]=strArStatUpdTime[1];
			lStrReason[2]=strArStTime[0];
			lStrReason[3]=strArStTime[1];
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason[4] = lStrReason[4] + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Fetch the Updated Status Values for the status type.
	' Precondition: N/A 
	' Arguments: selenium, pStrStatTypeIndex
	' Returns: String[] 
	' Date: 26-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String[] getUpdSTValueInMap(Selenium selenium, String pStrStatTypeIndex) throws Exception
	{
		String[] lStrReason=new String[2];
		lStrReason[1]="";
		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			String strStatUpdVal = selenium.getText("//div[@class='emsText maxheight']/span[" + pStrStatTypeIndex + "]");
			lStrReason[0]=	strStatUpdVal;
		}catch(Exception e){
			log4j.info(e);
			lStrReason[1] = lStrReason[1] + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description: Fetch the Updated Status Values for the status type.
	' Precondition: N/A 
	' Arguments: selenium, pStrStatTypeIndex
	' Returns: String[] 
	' Date: 26-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String navToRegionalMapView(Selenium selenium) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// View menu
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on Map link
			selenium.click(propElementDetails.getProperty("View.ViewLink.Map"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				int intCnt = 0;
				while (selenium.isVisible("//span[@id='reloadingText']"
						+ "[text()='reloading information']")
						&& intCnt <= 20) {
					intCnt++;
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				System.out.println(e);

			}
			
			int intCnt=0;
			do{
				try {
					assertEquals("Regional Map View", selenium
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
				assertEquals("Regional Map View", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Regional Map View screen is displayed");				
			} catch (AssertionError ae) {
				log4j.info("Regional Map View screen is NOT displayed");
				strReason = "Regional Map View screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
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
	
	public String checkResIconAndColor(Selenium selenium, String strTitle,
			String strIconSrc) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium.isElementPresent("css=img[title='"
						+ strTitle + "']"));
				String str = selenium.getAttribute("css=img[title='" + strTitle
						+ "']@src");
				log4j.info("One : "+str);
				assertEquals(strIconSrc, selenium
						.getAttribute("css=img[title='" + strTitle + "']@src"));
				log4j
						.info("The resource icon is displayed in the updated status color");
			} catch (AssertionError ae) {
				log4j
						.info("The resource icon is NOT displayed in the updated status color");
				strReason = "The resource icon is NOT displayed in the updated status color";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
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
	
	public String checkResIconAndColorInViewResDet(Selenium selenium,
			String strTitle, String strIconSrc) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium
						.isElementPresent("css=img[id=mtgt_unnamed_0]"));
				String str = selenium
						.getAttribute("css=img[id=mtgt_unnamed_0]@src");
				log4j.info("Two : "+str);
				assertEquals(strIconSrc, selenium
						.getAttribute("css=img[id=mtgt_unnamed_0]@src"));
				log4j
						.info("The resource icon is displayed in the updated status color");
			} catch (AssertionError ae) {
				log4j
						.info("The resource icon is NOT displayed in the updated status color");
				strReason = "The resource icon is NOT displayed in the updated status color";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	
	/*******************************************************************************************
	' Description: Fetch the Updated Status Values for the status type.
	' Precondition: N/A 
	' Arguments: selenium, pStrStatTypeIndex
	' Returns: String[] 
	' Date: 26-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String verRSInFindResDropDown(Selenium selenium,
			String strResource,boolean blnCheckRS) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
		
		if(blnCheckRS)
		{
		try {
			assertTrue(selenium.isElementPresent("//select[@id='resourceFinder']/option[text()='"+ strResource + "']" ));
			log4j.info(strResource+ "is displayed on the Map area and in the 'Find Resource' dropdown  ");
		} catch (Exception e) {
			log4j.info(strResource+ "is NOT displayed on the Map area and in the 'Find Resource' dropdown  ");
			strReason =strResource+ "is NOT displayed on the Map area and in the 'Find Resource' dropdown  ";
		}
		}
		else{
			try {
				assertFalse(selenium.isElementPresent("//select[@id='resourceFinder']/option[text()='"+ strResource + "']" ));
				log4j.info(strResource+ "is NOT displayed on the Map area and in the 'Find Resource' dropdown  ");
			} catch (Exception e) {
				log4j.info(strResource+ "is displayed on the Map area and in the 'Find Resource' dropdown  ");
				strReason =strResource+ "is displayed on the Map area and in the 'Find Resource' dropdown  ";
			}
		}
		
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
	

		return strReason;
	}
	
	/***************
	 'Description :Verify status type is updated
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :10-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String[] updateStatusTypeInAnyPositionWithTime(Selenium selenium,
			String strResource, String[] strRoleStatTypeValue,
			String strTimeFormat,int intStrPosition) throws Exception {

		String strReason[] = new String[2];
		strReason[0] = "";
		strReason[1] = "";
		Date_Time_settings dts = new Date_Time_settings();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

				for (String s : strRoleStatTypeValue) {

					log4j.info(selenium
							.getValue("css=input[name='status_value_" + s
									+ "']"));

					selenium.click("css=#st_" + s + "");

					if ((selenium.getValue("css=input[name='status_value_" + s
							+ "']").equals("1"))
							|| (selenium.getValue(
									"css=input[name='status_value_" + s + "']")
									.equals("0") || selenium.getValue(
									"css=input[name='status_value_" + s + "']")
									.equals(""))) {
						selenium.type("css=input[name='status_value_" + s
								+ "']", "2");
						// selenium.type("css=#comment_" + s + "", "updated 2");

						selenium.click("css=input[value='Save']");
						selenium.waitForPageToLoad(gstrTimeOut);

						// System Time
						strReason[1] = dts.timeNow(strTimeFormat);
						System.out.println(strReason[1]);

						int intCnt = 0;
						while (selenium
								.isElementPresent("//select[@id='resourceFinder']/option[text()='"
										+ strResource + "']") == false
								&& intCnt < 40) {
							Thread.sleep(1000);
							intCnt++;

						}

						// select the Find Resource option
						selenium.select(propElementDetails
								.getProperty("ViewMap.FindResource"), "label="
								+ strResource);

						int intVisbleCnt=0;
						do{
							try{
								selenium.isElementPresent(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								selenium.isVisible(propElementDetails
									.getProperty("ViewMap.ResPopup"));
								Thread.sleep(10000);
								break;
								
							}catch(Exception e){
								intVisbleCnt++;
								Thread.sleep(1000);
							}
							
						}while(intVisbleCnt<20);
						try {
							assertEquals(
									selenium
											.getText("//div[@class='emsText " +
													"maxheight']/span["+intStrPosition+"]"),
									"2");
							log4j.info("Status updated is displayed");

						} catch (AssertionError Ae) {
							log4j.info("Status updated is NOT displayed" + Ae);
							strReason[0] = "Status updated is NOT displayed"
									+ Ae;
						}

					} else if (selenium.getValue(
							"css=input[name='status_value_" + s + "']").equals(
							"2")) {

						selenium.type("css=input[name='status_value_" + s
								+ "']", "1");

						// selenium.type("css=#comment_" + s + "", "updated");

						selenium.click(propElementDetails
								.getProperty("StatusType.MultiST_CreateNewStatusSave"));
						selenium.waitForPageToLoad(gstrTimeOut);

						// System Time
						strReason[1] = dts.timeNow(strTimeFormat);
						System.out.println(strReason[1]);

						int intCnt = 0;
						while (selenium
								.isElementPresent("//select[@id='resourceFinder']/option[text()='"
										+ strResource + "']") == false
								&& intCnt < 40) {
							Thread.sleep(1000);
							intCnt++;

						}

						// select the Find Resource option
						selenium.select(propElementDetails
								.getProperty("ViewMap.FindResource"), "label="
								+ strResource);

						int intVisbleCnt=0;
						do{
							try{
								selenium.isElementPresent(propElementDetails
										.getProperty("ViewMap.ResPopup"));
								selenium.isVisible(propElementDetails
									.getProperty("ViewMap.ResPopup"));
								Thread.sleep(10000);
								break;
								
							}catch(Exception e){
								intVisbleCnt++;
								Thread.sleep(1000);
							}
							
						}while(intVisbleCnt<20);

						try {
							assertEquals(
									selenium
											.getText("//div[@class='emsText maxheight']/span["
													+ intStrPosition + "]"),
									"1");
							log4j.info("Status updated is displayed");

						} catch (AssertionError Ae) {
							log4j.info("Status updated is NOT displayed" + Ae);
							strReason[0] = "Status updated is NOT displayed"
									+ Ae;
						}

					}

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason[0] = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason[0] = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	
	
	/*****************************************************************
	 'Description : Navigate to reource pop up in regional map view page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :24-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                <Name>
	 *****************************************************************/
	public String navResPopupWindow(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}catch(Exception e){
				
			}

			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);

			int intVisbleCnt=0;
			do{
				try{
					selenium.isElementPresent(propElementDetails
							.getProperty("ViewMap.ResPopup"));
					selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup"));
					Thread.sleep(10000);
					break;
					
				}catch(Exception e){
					intVisbleCnt++;
					Thread.sleep(1000);
				}
				
			}while(intVisbleCnt<20);
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate to view resource detail page from reource pop up
					section.
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:24-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String navToViewResourceDetailPageFrmRSPopUp(Selenium selenium)
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
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("Resources.ViewInfoLink")));
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
			

			selenium.click(propElementDetails.getProperty("Resources.ViewInfoLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("View Resource Detail", selenium
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
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason = "View Resource Detail screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToViewResourceDetailPageFrmRSPopUp "
					+ e.toString();
		}
		return strReason;
	}

	/***************************************************************
	'Description	:navigate to view resource detail page from reource pop up
					section.
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:24-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/	
	public String navEventStatusPage(Selenium selenium, String strEveName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
							+ strEveName + "']");
			try{
				selenium.waitForPageToLoad(gstrTimeOut);
			}catch(Exception e){
				
			}
			

			try {
				assertEquals("Event Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Event Status screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("Event Status screen is NOT displayed");
				strReason = "Event Status screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info("navEventStatusPage function failed " + e);
			strReason = "navEventStatusPage function failed " + e.toString();
		}

		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate to view resource detail page from reource pop up
					section.
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:24-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/	
	public String navEventStatusPageWitEventValue(Selenium selenium, String strEveValue)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//a[@id='t"+strEveValue+"']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Event Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Event Status screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("Event Status screen is NOT displayed");
				strReason = "Event Status screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info("navEventStatusPageWitEventValue function failed " + e);
			strReason = "navEventStatusPageWitEventValue function failed " + e.toString();
		}

		return strReason;
	}

	/***************************************************************
	'Description	:navigate to view resource detail page from reource pop up
					section.
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:24-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String verSTInResourcePopup(Selenium selenium,
			String strResource, String strEventStatType[],
			String strRoleStatType[], boolean blnCheckEveStat,
			boolean blnCheckRoleStat) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

			
					String strStatType = selenium.getText(propElementDetails
							.getProperty("ViewMap.ResPopup.StatTypeList"));
					if (strEventStatType != null) {
						if (blnCheckEveStat) {
							for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
								try {
									assertTrue(strStatType
											.contains(strEventStatType[intRec]));
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is displayed");
								} catch (AssertionError ae) {
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is NOT displayed");
									strReason = strReason
											+ " Event Based Status type "
											+ strEventStatType[intRec]
											+ " is NOT displayed";
								}

							}
						} else {
							for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
								try {
									assertFalse(strStatType
											.contains(strEventStatType[intRec]));
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is not displayed");
								} catch (AssertionError ae) {
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is still displayed");
									strReason = strReason
											+ " Event Based Status type "
											+ strEventStatType[intRec]
											+ " is still displayed";
								}

							}
						}
					}
					if (strRoleStatType != null) {
						if (blnCheckRoleStat) {
							for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
								try {
									assertTrue(strStatType
											.contains(strRoleStatType[intRec]));
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is displayed");
								} catch (AssertionError ae) {
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is NOT displayed");
									strReason = strReason
											+ " Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is NOT displayed";
								}

							}
						} else {
							for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
								try {
									assertFalse(strStatType
											.contains(strRoleStatType[intRec]));
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is not displayed");
								} catch (AssertionError ae) {
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is still displayed");
									strReason = strReason
											+ " Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is still displayed";
								}

							}
						}
					}
		
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
	
		return strReason;
	}

	
	/*****************************************************************
	 'Description : Navigate to Event resource pop up in regional map view page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :24-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                <Name>
	 *****************************************************************/
	public String navEventPopupWindow(Selenium selenium, String strEvent)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}catch(Exception e){
				
			}

			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='eventSelector']/option[text()='"
							+ strEvent + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindEventResource"), "label="
					+ strEvent);

			
			intCnt=0;
			do{
				try{
					assertEquals(strEvent, selenium.getSelectedLabel(propElementDetails
							.getProperty("ViewMap.FindEventResource")));
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
			
			try{
				assertEquals(strEvent, selenium.getSelectedLabel(propElementDetails
						.getProperty("ViewMap.FindEventResource")));
				log4j.info("Event is found in drop down");
			}catch(AssertionError Ae){
				log4j.info("Event is NOT found in drop down");
				strReason="Event is NOT found in drop down";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	
	/***************************************************************
	'Description	:Verify Multi status type is updated with time
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:28-Feb-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String[] updateMultiStatusTypeWithTime(Selenium selenium,
			String strResource, String strMultiST, String strRoleStatTypeValue,
			String strStatus1, String strStatusValue1, String strStatus2,
			String strStatusValue2, String strTimeFormat) throws Exception {

		String strReason[] = new String[2];
		strReason[0] = "";
		strReason[1] = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strStatVal = strStatusValue1;
			String strChStatus = strStatus1;

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");

				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if (selenium.isChecked("css=#statusValue_" + strStatusValue1
						+ "")) {
					strStatVal = strStatusValue2;
					strChStatus = strStatus2;

				}
				selenium.click("css=#statusValue_" + strStatVal + "");

				selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusSave"));
				selenium.waitForPageToLoad(gstrTimeOut);

				// System Time
				strReason[1] = dts.timeNow(strTimeFormat);
				System.out.println(strReason[1]);

				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strChStatus + "']"));
					log4j
							.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j
							.info("Status updated is NOT displayed in Resource pop up"
									+ Ae);
					strReason[0] = "Status updated is NOT displayed in Resource pop up"
							+ Ae;

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason[0] = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason[0] = "ViewMap.updateMultiStatusTypeWithTime failed to complete due to "
					+ strReason[0] + "; " + e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:verifyTimeCorrespondingUpdValInMap
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:6-March-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	public String verifyTimeCorrespondingUpdTimeInMap(Selenium selenium,
			String strUpdValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			String strApplTime = selenium.getText("css=#statusTime");

			String strUpdatedTime[] = selenium.getText(
					"//span[text()='" + strUpdValue
							+ "']/following-sibling::span[@class='time'][1]").split("\\(");
			
			strUpdatedTime=strUpdatedTime[1].split("\\)");

			try {
				assertEquals(strApplTime, strUpdatedTime[0]);

				log4j
						.info("Status updated date and time with Time zone is displayed"
								+ " corresponding to each status types. ");

			} catch (AssertionError Ae) {
				log4j
						.info("Status updated date and time with Time zone is NOT displayed "
								+ "corresponding to each status types. ");
				strReason = "Status updated date and time with Time zone is NOT displayed "
						+ "corresponding to each status types. ";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyTimeCorrespondingUpdTimeInMap "
					+ e;
		}
		return strReason;
	}

	
	/*****************************************************************
	 'Description : Navigate to reource pop up in regional map view page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :24-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                <Name>
	 *****************************************************************/
	public String navResPopupWindowNew(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}catch(Exception e){
				
			}

			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);
			
			int intVisbleCnt=0;
			do{
				try{
					selenium.isElementPresent(propElementDetails
							.getProperty("ViewMap.ResPopup"));
					selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup"));
					Thread.sleep(10000);
					break;
					
				}catch(Exception e){
					intVisbleCnt++;
					Thread.sleep(1000);
				}
				
			}while(intVisbleCnt<20);
			
			try{
				assertTrue(selenium.isElementPresent(propElementDetails
					.getProperty("ViewMap.ResPopup")));
			
			}catch(AssertionError e){
				strReason="Resource pop up window NOT found";
			}catch(Exception e){
				strReason="Resource pop up window NOT found";
			}
			

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Number or Text status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:07-March-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateNumStatusTypeNew(Selenium selenium, String strResource,
			String strStatType, String strRoleStatTypeValue,
			String strUpdatTxtValue1, String strUpdatTxtValue2)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strUpdValue = strUpdatTxtValue1;
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("link=Update Status");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium.getText("css=h1"));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");
				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if (selenium.getValue(
						"css=input[name='status_value_" + strRoleStatTypeValue
								+ "']").equals(strUpdatTxtValue1)) {
					strUpdValue = strUpdatTxtValue2;
				}
				selenium.type("css=input[name='status_value_"
						+ strRoleStatTypeValue + "']", strUpdValue);

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);

				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strUpdValue + "']"));
					log4j
							.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j
							.info("Status updated is NOT displayed in Resource pop up"
									+ Ae);
					strReason = "Status updated is NOT displayed in Resource pop up"
							+ Ae;

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateNumStatusTypeNew " + e;
		}
		return strReason;
	}
	
	
	public String verifyStatTypesInResourcePopupWaitForPopUp(Selenium selenium,
			String strResource, String strEventStatType[],
			String strRoleStatType[], boolean blnCheckEveStat,
			boolean blnCheckRoleStat) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// View menu
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on Map link
			selenium.click(propElementDetails.getProperty("View.ViewLink.Map"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Regional Map View", selenium
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
				assertEquals("Regional Map View", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Regional Map View screen is displayed");
				intCnt = 0;
				
				if (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				int intVisbleCnt=0;
				do{
					try{
						selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.ResPopup"));
						selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup"));
						Thread.sleep(10000);
						break;
						
					}catch(Exception e){
						intVisbleCnt++;
						Thread.sleep(1000);
					}
					
				}while(intVisbleCnt<20);
		
					
					if (selenium.isVisible(propElementDetails
							.getProperty("ViewMap.ResPopup.StatTypeList")) == false
							&& intCnt < 20) {
						Thread.sleep(1000);
						intCnt++;
					}
	
					String strStatType = selenium.getText(propElementDetails
							.getProperty("ViewMap.ResPopup.StatTypeList"));
					if (strEventStatType != null) {
						if (blnCheckEveStat) {
							for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
								try {
									assertTrue(strStatType
											.contains(strEventStatType[intRec]));
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is displayed");
								} catch (AssertionError ae) {
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is NOT displayed");
									strReason = strReason
											+ " Event Based Status type "
											+ strEventStatType[intRec]
											+ " is NOT displayed";
								}

							}
						} else {
							for (int intRec = 0; intRec < strEventStatType.length; intRec++) {
								try {
									assertFalse(strStatType
											.contains(strEventStatType[intRec]));
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is not displayed");
								} catch (AssertionError ae) {
									log4j.info("Event Based Status type "
											+ strEventStatType[intRec]
											+ " is still displayed");
									strReason = strReason
											+ " Event Based Status type "
											+ strEventStatType[intRec]
											+ " is still displayed";
								}

							}
						}
					}
					if (strRoleStatType != null) {
						if (blnCheckRoleStat) {
							for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
								try {
									assertTrue(strStatType
											.contains(strRoleStatType[intRec]));
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is displayed");
								} catch (AssertionError ae) {
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is NOT displayed");
									strReason = strReason
											+ " Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is NOT displayed";
								}

							}
						} else {
							for (int intRec = 0; intRec < strRoleStatType.length; intRec++) {
								try {
									assertFalse(strStatType
											.contains(strRoleStatType[intRec]));
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is not displayed");
								} catch (AssertionError ae) {
									log4j.info("Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is still displayed");
									strReason = strReason
											+ " Role Based Status type "
											+ strRoleStatType[intRec]
											+ " is still displayed";
								}

							}
						}
					}

			} catch (AssertionError ae) {
				log4j.info("Regional Map View screen is NOT displayed");
				strReason = "Regional Map View screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
	
		return strReason;
	}
	
	
	/*******************************************************************************************
	' Description: Fetch the Updated Status Values for the status type.
	' Precondition: N/A 
	' Arguments: selenium, pStrStatTypeIndex
	' Returns: String[] 
	' Date: 26-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String navToRegionalMapViewWithoutVisible(Selenium selenium)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// View menu
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on Map link
			selenium.click(propElementDetails.getProperty("View.ViewLink.Map"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Regional Map View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Regional Map View screen is displayed");
			} catch (AssertionError ae) {
				log4j.info("Regional Map View screen is NOT displayed");
				strReason = "Regional Map View screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	/*****************************************************************
	 'Description : Navigate to reource pop up in regional map view page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :24-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                <Name>
	 *****************************************************************/
	public String navResPopupWindowNew1(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}catch(Exception e){
				
			}

			int intCnt = 0;
			while (selenium
					.isElementPresent("//select[@id='resourceFinder']/option[text()='"
							+ strResource + "']") == false
					&& intCnt < 40) {
				Thread.sleep(1000);
				intCnt++;
			}

			// select the Find Resource option
			selenium.select(propElementDetails
					.getProperty("ViewMap.FindResource"), "label="
					+ strResource);
				
			try{
				selenium.waitForPageToLoad("30000");
			}catch(Exception e){
				
			}
			try{
				assertTrue(selenium.isElementPresent(propElementDetails
					.getProperty("ViewMap.ResPopup")));
			
			}catch(AssertionError e){
				strReason="Resource pop up window NOT found";
			}catch(Exception e){
				strReason="Resource pop up window NOT found";
			}
			

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	/***************************************************************
	'Description	:Verify Number or Text status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:25-June-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateNumStatusType1(Selenium selenium, String strResource,
			String strStatType, String strRoleStatTypeValue,
			String strUpdatTxtValue1, String strUpdatTxtValue2)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strUpdValue = strUpdatTxtValue1;
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");

             
            selenium.click(propElementDetails.getProperty("StatusType.UpdateStatusLink"));
 			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText("css=h1.emrTitle.left"));
				log4j.info("Update Status screen is displayed");
				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if (selenium.getValue(
						"css=input[name='status_value_" + strRoleStatTypeValue
								+ "']").equals(strUpdatTxtValue1)) {
					strUpdValue = strUpdatTxtValue2;
				}
				selenium.type("css=input[name='status_value_"
						+ strRoleStatTypeValue + "']", strUpdValue);

				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				while (selenium
						.isElementPresent("//select[@id='resourceFinder']/option[text()='"
								+ strResource + "']") == false
						&& intCnt < 40) {
					Thread.sleep(1000);
					intCnt++;
				}

				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				// Wait for resource pop up to present
				try{
					selenium.waitForPageToLoad("30000");
				}catch(Exception e){
					
				}

				try {
					assertTrue(selenium.isElementPresent("//span[text()='"
							+ strUpdValue + "']"));
					log4j
							.info("Status updated is displayed in Resource pop up");

				} catch (AssertionError Ae) {
					log4j
							.info("Status updated is NOT displayed in Resource pop up"
									+ Ae);
					strReason = "Status updated is NOT displayed in Resource pop up"
							+ Ae;

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	/***************************************************************
	'Description :Verify Section And ST not displayed in View resource detail screen
	'Precondition:None
	'Arguments   :selenium,strSection,strStatType[]
	'Returns     :strReason
	'Date        :29/07/2013
	'Author      :QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySectionAndSTInViewResDetailFalse(Selenium selenium, String strSection,
			String strStatTypep[]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertFalse(selenium.isElementPresent("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a[contains(text()='"+strSection+"')]"));
				log4j.info("Section " + strSection
						+ " is not displayed in View Resource Detail screen");

				for (String s : strStatTypep) {
					try {
						assertFalse(selenium
								.isElementPresent("//table[starts-with(@id," +
										"'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ s + "']"));

						log4j
								.info("The Status Type "+s+" is Not displayed on the " +
										"view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j
								.info("The Status Type "+s+" is  displayed on" +
										" the view resource detail screen. ");
						strReason = "The Status Type "+s+" is  displayed on " +
								"the view resource detail screen. ";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Section " + strSection
						+ " is displayed in View Resource Detail screen");
				strReason = "Section " + strSection
						+ " is displayed in View Resource Detail screen";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySectionAndSTInViewResDetailFalse "
					+ e.toString();
		}
		return strReason;
	}
	
	/************************************************************************
	' Description  : Verifying status type are listed or not in the dropdown.
	' Precondition : N/A 
	' Arguments    : selenium, pStrStatTypeIndex
	' Returns      : String[] 
	' Date         : 26-06-2012
	' Author       : QSG 
	'------------------------------------------------------------------------ 
	' Modified Date: 
	' Modified By: 
	*************************************************************************/

	public String verSTInStatusTypeDropDown(Selenium selenium,
			String strStatType, boolean blnCheckST) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnCheckST) {
				try {
					assertTrue(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ strStatType + "']"));
					log4j.info(strStatType
							+ "is displayed  in the 'Status Type' dropdown  ");
				} catch (Exception e) {
					log4j.info(strStatType
							+ "is NOT displayed in the 'Status Type' dropdown  ");
					strReason = strStatType
							+ "is NOT displayed in the 'Find Resource' dropdown  ";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ strStatType + "']"));
					log4j.info(strStatType
							+ "is NOT displayed in the 'Status Typee' dropdown  ");
				} catch (Exception e) {
					log4j.info(strStatType
							+ "is displayed in the 'Status Type' dropdown  ");
					strReason = strStatType
							+ "is displayed in the 'Status Type' dropdown  ";
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
}
