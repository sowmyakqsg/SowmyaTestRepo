package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/************************************************************************
' Description :This class includes common functions of Preferences
' Date		  :25-April-2012
' Author	  :QSG
'------------------------------------------------------------------------
' Modified Date                                       Modified By
' <Date>                           	                  <Name>
'***********************************************************************/
public class Preferences {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.Preferences");
	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;	
	public String gstrTimeOut ="";	
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();
	ReadData rdExcel;
	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'23-April-2012                               <Name>
	************************************************************************/	
	public String navEditCustomViewPage(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error message
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}catch(Exception e){
				
			}			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Preferences")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.mouseOver(propElementDetails.getProperty("Preferences"));

			selenium.click(propElementDetails
					.getProperty("Preferences.CustomizedView"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Edit Custom View", selenium
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
				assertEquals("Edit Custom View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Custom View page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View page is NOT displayed" + Ae;
				log4j.info("Edit Custom View page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navigate to Edit Custom View Page function failed" + e);
			strErrorMsg = "navigate to Edit Custom View Page function failed"
					+ e;
		}
		return strErrorMsg;
	}	
	
	 /***********************************************************************
	'Description	:navigating to find  resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'9-May-2012                               <Name>
	************************************************************************/
	public String navTOFindResourcesPage(Selenium selenium,
			String strSearchRSName, String strSearchCategory, String strRegion,
			String strSearchWhere, String strSearchState,
			String strResourceValue) throws Exception {
		String strErrorMsg = "";// variable to store error message
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("My Status Change Preferences page is  displayed");

				selenium.click(propElementDetails.getProperty("Preferences.StatusChangePref.Add"));// Click 'Add' to add
				// the resources
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Find Resources", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Find Resources page is  displayed");
					// Search criteria
					selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strSearchRSName);
					selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="
							+ strSearchCategory);
					selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
					selenium.waitForPageToLoad(gstrTimeOut);
					if (selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strResourceValue + "']") == false) {
						selenium.click("css=input[name='resourceID'][value='"
								+ strResourceValue + "']");
					}
					selenium.click(propElementDetails.getProperty("StatusChangePrefs.FindRes.Notifications"));
					selenium.waitForPageToLoad(gstrTimeOut);

				} catch (AssertionError Ae) {
					strErrorMsg = "Find Resources page is NOT displayed" + Ae;
					log4j.info("Find Resources page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navTOFindResourcesPage function failed" + e);
			strErrorMsg = "navTOFindResourcesPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:navigating find resource page
	'Precondition	:None
	'Arguments		:selenium,
	'Returns		:String
	'Date	 		:25-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-April-2012                               <Name>
	************************************************************************/
public String navToFindResPage(Selenium selenium) throws Exception {
	String strErrorMsg = "";// variable to store error message
	try {
		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		selenium.click(propElementDetails
				.getProperty("EditCustomView.AddMoreResources"));
		selenium.waitForPageToLoad(gstrTimeOut);
		try {
			assertEquals("Find Resources", selenium
					.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("Find Resources Screen is  displayed");

		} catch (AssertionError Ae) {
			strErrorMsg = "Find Resources  Screen is NOT displayed" + Ae;
			log4j.info("Find Resources  Screen is NOT displayed" + Ae);
		}

	} catch (Exception e) {
		log4j.info("navToFindResPage function failed" + e);
		strErrorMsg = "navToFindResPage function failed" + e;
	}
	return strErrorMsg;
}

	/***********************************************************************
	'Description 	:find resources and add to custom view
	'Precondition 	:None
	'Arguments  	:selenium,strResource,strCategory,strRegion,strCityZipCd,
	    			 strState
	'Returns  		:String
	'Date    		:9-May-2012
	'Author   		:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String findResourcesVarErrMsg(Selenium selenium, String strResource,
			String strCategory, String strCityZipCd, String strState) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources page is displayed");

				// Enter value for Name
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.Name"), strResource);
				// Enter for category
				selenium.select(propElementDetails
						.getProperty("Pref.FindResources.Category"), "label="
						+ strCategory);

				// Enter city, county or zip
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.CityZip"),
						strCityZipCd);
				// Select the state
				selenium.select(propElementDetails
						.getProperty("Pref.FindResources.State"), "label="
						+ strState);
				// click on search button
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertFalse(selenium
							.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResource + "']"));
					log4j.info("Resource " + strResource + " is  NOT found");

				} catch (AssertionError Ae) {
					log4j.info("Resource " + strResource + " is  found");
					strReason = "Resource " + strResource + " is found";
				}
				try {
					assertEquals("No resources match your criteria...", selenium.getText("css=h2"));
					log4j.info("No resources match your criteria...");
				} catch (AssertionError Ae) {
					log4j
							.info("No resources match your criteria...NOT present");
					strReason = "No resources match your criteria...NOT present";
				}
			} catch (AssertionError Ae) {
				strReason = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("findResourcesVarErrMsg function failed" + e);
			strReason = "findResourcesVarErrMsg function failed" + e;
		}
		return strReason;
	}

	 /***********************************************************************
	 'Description 		:find resources and add to custom view
	 'Precondition 		:None
	 'Arguments  		:selenium,strResource,strCategory,strRegion,strCityZipCd,
	     				 strState
	 'Returns  			:String
	 'Date    			:9-May-2012
	 'Author   			:QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	public String findResourcesAndAddToCustomView(Selenium selenium,
			String strResource, String strCategory, String strRegion,
			String strCityZipCd, String strState, String s) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			// click on Add More Resource button
			selenium.click(propElementDetails
					.getProperty("EditCustomView.AddMoreResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources page is displayed");

				// Enter value for Name
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.Name"), strResource);
				// Enter for category
				selenium.select(propElementDetails
						.getProperty("Pref.FindResources.Category"), "label="
						+ strCategory);
				// Select region
				selenium.select(propElementDetails
						.getProperty("Pref.FindResources.Region"), "label="
						+ strRegion);
				// Enter city, county or zip
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.CityZip"),
						strCityZipCd);
				// Select the state
				selenium.select(propElementDetails
						.getProperty("Pref.FindResources.State"), "label="
						+ strState);

				// click on search button
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResource + "']"));
					log4j.info("Resource " + strResource + " is found");

					// select the resource
					selenium
							.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResource + "']/parent::tr/td[1]/input");
					// click on Add To Custom View
					selenium
							.click(propElementDetails
									.getProperty("EditCustomView.FindRes.AddToCustomView"));
					selenium.waitForPageToLoad(gstrTimeOut);

				} catch (AssertionError Ae) {
					log4j.info("Resource " + strResource + " is NOT found");
					strReason = "Resource " + strResource + " is NOT found";
				}
			} catch (AssertionError Ae) {
				strReason = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("findResourcesAndAddToCustomView function failed" + e);
			strReason = "findResourcesAndAddToCustomView function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-April-2012                               <Name>
	************************************************************************/
	
	public String createCustomView(Selenium selenium, String[] strResourceName,
			String strRT, String strST) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			

			

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditCustomView.AddMoreResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources page is  displayed");

				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
			
				intCnt=strResourceName.length;
				log4j.info(intCnt);
				
				String[] strResourceType = new String[intCnt];
				
				for (int i = 0; i < intCnt; i++) {
					
					try {
						strResourceType[i] = selenium
								.getText("//table[@id='tbl_resourceID']"
										+ "/tbody/tr/td[2][text()='"
										+ strResourceName[i] + "']/"
										+ "following-sibling::td[3]");
						log4j.info(intCnt);
						log4j.info(strResourceType[intCnt]);
					} catch (Exception e) {
					
					}

				}
				
				for (String s : strResourceName) {

					if (selenium
							.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
									+ s + "']/parent::tr/td/input") == false) {
						selenium
								.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ s + "']/parent::tr/td/input");

					}
				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.AddToCustomView"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				for (String s : strResourceType) {
					try {
						assertTrue(selenium
								.isElementPresent("//div/span[contains(text(),'"
										+ s + "')]"));
						log4j.info(" Resources is added to custom view ");

						selenium.click(propElementDetails
								.getProperty("EditCustomView.Options"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							assertEquals("Edit Custom View Options (Columns)",
									selenium.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j
									.info("Edit Custom View Options (Columns) page is  Displayed");

							if (selenium
									.isChecked("//div[@id='mainContainer']/form/div/div/b[text()='"
											+ strST
											+ "']/preceding-sibling::input") == false) {

								selenium
										.click("//div[@id='mainContainer']/form/div/div/b[text()='"
												+ strST
												+ "']/preceding-sibling::input");

							}
							selenium
									.click(propElementDetails
											.getProperty("EditCustomView.EditCustomViewOptions.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							try {
								assertEquals("Custom View - Table", selenium
										.getText(propElementDetails
												.getProperty("Header.Text")));

								try {
									assertTrue(selenium
											.isElementPresent("//th[text()"
													+ "='" + strRT
													+ "']/following-sibling"
													+ "::th/a[text()='" + strST
													+ "']"));

									log4j
											.info("Resource Type and Source Type present");

									try {

										for (String s1 : strResourceName) {
											assertTrue(selenium
													.isElementPresent("//td[@class='resourceName']"
															+ "/a[text()='"
															+ s1 + "']"));
											log4j
													.info("Resource Name present in custom view table");
										}

									} catch (AssertionError Ae) {
										strErrorMsg = "Resource Name NOT present in custom view table"
												+ Ae;
										log4j
												.info("Resource Name NOT present in custom view table"
														+ Ae);
									}

								} catch (AssertionError Ae) {
									strErrorMsg = "Resource Type and Source Type NOT present"
											+ Ae;
									log4j
											.info("Resource Type and Source Type present"
													+ Ae);
								}
							} catch (AssertionError Ae) {
								strErrorMsg = "Custom View - Table screen NOT displayed"
										+ Ae;
								log4j
										.info("Custom View - Table screen NOT displayed"
												+ Ae);
							}

						} catch (AssertionError Ae) {
							strErrorMsg = "Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae;
							log4j.info("Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae);
						}

					} catch (AssertionError Ae) {
						strErrorMsg = " Resources is NOT added to custom view "
								+ Ae;
						log4j.info(" Resources is NOT added to custom view "
								+ Ae);
						break;
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("create Custom View function failed" + e);
			strErrorMsg = "create Custom View function failed" + e;
		}
		return strErrorMsg;
	}	
	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:30-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'30-April-2012                               <Name>
	************************************************************************/	
	public String navViewCustomTable(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			selenium.click(propElementDetails.getProperty("View.CustomLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Custom View - Table", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Custom View - Table page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Custom View - Table page is NOT displayed" + Ae;
				log4j.info("Custom View - Table page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("navigate to Custom View - Table Page "
					+ "function failed" + e);
			strErrorMsg = "navigate to Custom View - Table Page"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:navigate to Event notification page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-May-2012                               <Name>
	************************************************************************/	
	public String navEventNotificationPage(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.mouseOver(propElementDetails.getProperty("Preferences"));
			selenium.click(propElementDetails.getProperty("Pref.EventNotification_Link"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("My Event Notification Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("My Event Notification Preferences page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "My Event Notification Preferences is NOT displayed" + Ae;
				log4j.info("My Event Notification Preferences is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navEventNotificationPage  function failed" + e);
			strErrorMsg = "navEventNotificationPage function failed"
					+ e;
		}
		return strErrorMsg;
	}	
	/**************************************************************************
	'Description	:Verify My Event Notification Preferences settings are done
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-May-2012                               <Name>
	************************************************************************/
	public String selectEvenNofMethods(Selenium selenium,
			String[] strTemplateValue, boolean blnEmail, boolean blnPager,
			boolean blnWebPage) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");			
			for(String s:strTemplateValue){				
				if(blnEmail){
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}
								
				if(blnPager){
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}
				
				
				if(blnWebPage){
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}
				
				
			}
			
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Preferences Menu", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Preferences Menu page is  displayed");

			} catch (AssertionError Ae) {
				strReason = "Preferences Menu page is NOT displayed" + Ae;
				log4j.info("Preferences Menu page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("Error in selectEvenNofMtdForUsr" + e);
			strReason = "Error in selectEvenNofMtdForUsr" + e;
		}
		return strReason;

	}
	
	/**************************************************************************
	'Description	:Verify My Event Notification Preferences settings are done
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-May-2012                               <Name>
	************************************************************************/
	public String selectEvenNofMethodsOnlyWitoutPageVerification(Selenium selenium,
			String[] strTemplateValue, boolean blnEmail, boolean blnPager,
			boolean blnWebPage) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			
			for(String s:strTemplateValue){
				
				if(blnEmail){
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}				
				
				if(blnPager){
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}
								
				if(blnWebPage){
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}			
			}			
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
		} catch (Exception e) {
			log4j.info("Error in selectEvenNofMtdForUsr" + e);
			strReason = "Error in selectEvenNofMtdForUsr" + e;
		}
		return strReason;

	}
	
	/**************************************************************************
	'Description	:Verify My Event Notification Preferences settings are done with page verification
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-May-2012                               <Name>
	************************************************************************/

	public String selectEvenNofMethodsOnlyWitPageVerification(Selenium selenium,
			String[] strTemplateValue, boolean blnEmail, boolean blnPager,
			boolean blnWebPage) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			
			for(String s:strTemplateValue){
				
				if(blnEmail){
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}
				
				
				if(blnPager){
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}
				
				if(blnWebPage){
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}
				
			}			
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);	
			
			try {
				assertEquals("My Event Notification Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("My Event Notification Preferences page is  displayed");

			} catch (AssertionError Ae) {
				strReason = "My Event Notification Preferences page is NOT displayed" + Ae;
				log4j.info("My Event Notification Preferences page is NOT displayed" + Ae);
			}			
		} catch (Exception e) {
			log4j.info("Error in selectEvenNofMtdForUsr" + e);
			strReason = "Error in selectEvenNofMtdForUsr" + e;
		}
		return strReason;

	}
	
	/***********************************************************************
	'Description	:navigate to My status change preferences
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/	
	public String navMyStatusTypeChangePreference(Selenium selenium)
	throws Exception {
		String strErrorMsg = "";// variable to store error message
		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.mouseOver(propElementDetails.getProperty("Preferences"));
			selenium.click(propElementDetails
					.getProperty("Preferences.StatusChangePref"));
			selenium.waitForPageToLoad(gstrTimeOut);			
			int intCnt=0;
			do{
				try {

					assertEquals("My Status Change Preferences", selenium
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
				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("My Status Change Preferences page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navMyStatusTypeChangePreference function failed" + e);
			strErrorMsg = "navMyStatusTypeChangePreference function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
		  'Description  :Verify status change notification preference for particular user is displayed
		  'Precondition :None
		  'Arguments    :selenium,strUserName
		  'Returns      :String
		  'Date         :25-May-2012
		  'Author       :QSG
		  '-----------------------------------------------------------------------
		  'Modified Date                            Modified By
		  '25-May-2012                               <Name>
		  ************************************************************************/		  
	public String navStatusChangeNotiFrmEditUserPge(Selenium selenium,
			String strUserName) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Pref.StatusChangeNotification")));
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
					.getProperty("Pref.StatusChangeNotification"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("Status Change Preferences for " + strUserName
							+ "", selenium.getText(propElementDetails
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
				assertEquals("Status Change Preferences for " + strUserName
						+ "", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Status Change Preferences for " + strUserName
						+ " page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Status Change Preferences for " + strUserName
						+ " page is NOT displayed" + Ae;
				log4j.info("Status Change Preferences for " + strUserName
						+ " page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j
					.info("navigate to StatusChangeNotiFrmEditUser Page function failed"
							+ e);
			strErrorMsg = "navigate to StatusChangeNotiFrmEditUser Page function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'9-May-2012                               <Name>
	************************************************************************/
	
	public String addResourcesInST(Selenium selenium, String strSearchRSName,
			String strSearchCategory, String strResourceValue, String strSTValue)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				
				log4j.info("My Status Change Preferences page is  displayed");
				
				
				selenium.click(propElementDetails.getProperty("Preferences.StatusChangePref.Add"));//Click 'Add' to add the resources 
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Pref.FindResources.Name")));
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
					assertEquals("Find Resources", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					
					log4j.info("Find Resources page is  displayed");
					
					// Search criteria
					selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strSearchRSName);
					selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="
							+ strSearchCategory);
				
					
					selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					
					if (selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strResourceValue + "']") == false) {
						selenium.click("css=input[name='resourceID'][value='"
								+ strResourceValue + "']");
					}
				
					
					selenium.click(propElementDetails.getProperty("StatusChangePrefs.FindRes.Notifications"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try {

							assertEquals("Edit My Status Change Preferences",
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
						assertEquals("Edit My Status Change Preferences",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j
								.info("Edit My Status Change Preferences page is  displayed");
						
						
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|N|A']");
						
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|N|A']");
						
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|N|A']");
						
						selenium.click("css=input[value='Save']");
						selenium.waitForPageToLoad(gstrTimeOut);
						

						
						try {
							assertEquals("My Status Change Preferences", selenium
									.getText(propElementDetails.getProperty("Header.Text")));
							
							log4j.info("My Status Change Preferences page is  displayed");
							
							
							

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/"
												+ "table/tbody/tr/td[text()='"
												+ strSearchRSName + "']"));
								
								log4j.info("Resource Name is displayed");
								
							} catch (AssertionError Ae) {
								strErrorMsg = "Resource Name is NOT displayed"
										+ Ae;
								log4j.info("Resource Name is NOT displayed"
										+ Ae);
							}
						
							
						} catch (AssertionError Ae) {
							strErrorMsg = "My Status Change Preferences page is NOT displayed"
									+ Ae;
							log4j.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
						}
							
					} catch (AssertionError Ae) {
						strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
								+ Ae;
						log4j
								.info("Edit My Status Change Preferences page is NOT displayed"
										+ Ae);
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Find Resources page is NOT displayed" + Ae;
					log4j.info("Find Resources page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navMyStatusTypeChangePreference function failed" + e);
			strErrorMsg = "navMyStatusTypeChangePreference function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:find resources and add to custom view
	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String findResourcesAndAddToCustomView(Selenium selenium,
			String strResource, String strCategory, String strRegion,
			String strCityZipCd, String strState) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			selenium.click(propElementDetails
					.getProperty("EditCustomView.AddMoreResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Find Resources", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
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
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources page is displayed");

				// Enter value for Name
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.Name"), strResource);
				// Enter for category
				selenium.select(propElementDetails
						.getProperty("Pref.FindResources.Category"), "label="
						+ strCategory);
				if (selenium.isElementPresent(propElementDetails
						.getProperty("Pref.FindResources.Region"))) {
					// Select region
					selenium.select(propElementDetails
							.getProperty("Pref.FindResources.Region"), "label="
							+ strRegion);
				}
				// Enter city, county or zip
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.CityZip"),
						strCityZipCd);
				// Select the state
				selenium.select(propElementDetails
						.getProperty("Pref.FindResources.State"), "label="
						+ strState);

				// click on search button
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);
				intCnt = 0;
				do {
					try{
						assertTrue(selenium
							.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResource + "']"));
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
							.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResource + "']"));
					log4j.info("Resource " + strResource + " is found");

					// select the resource
					selenium
							.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResource + "']/parent::tr/td[1]/input");
					// click on Add To Custom View
					selenium
							.click(propElementDetails
									.getProperty("EditCustomView.FindRes.AddToCustomView"));
					selenium.waitForPageToLoad(gstrTimeOut);

				} catch (AssertionError Ae) {
					log4j.info("Resource " + strResource + " is NOT found");
					strReason = "Resource " + strResource + " is NOT found";
				}
			} catch (AssertionError Ae) {
				strReason = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("findResourcesAndAddToCustomView function failed" + e);
			strReason = "findResourcesAndAddToCustomView function failed" + e;
		}
		return strReason;
	}	
	
	/***********************************************************************
	'Description	:edit custom view options and add the status type and check 
					in custom view table
	'Precondition	:None
	'Arguments		:selenium,strOptSelect,strOptionsName,strResTypeName,strStatusType,
					strResource
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String editCustomViewOptionsAndCheckInCustViewTable(Selenium selenium,String strOptSelect[],String[][] strOptionsName,String strResTypeName,String strStatusType,String strResource) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			selenium.click(propElementDetails.getProperty("EditCustomView.Options"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try{
				assertEquals("Edit Custom View Options (Columns)",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Edit Custom View Options (Columns)' screen is displayed. " );
				if(strOptSelect!=null){
					//Select the status types
					for(int intOpt=0;intOpt<strOptSelect.length;intOpt++){
						if(selenium.isChecked("//b[text()='"+strOptSelect[intOpt]+"']/preceding-sibling::input")==false)
							selenium.click("//b[text()='"+strOptSelect[intOpt]+"']/preceding-sibling::input");
					}
				}
				if(strOptionsName!=null){
					//select the options
					for(int i=0;i<strOptionsName.length;i++){
						if(strOptionsName[i][1].equals("true")){
							if(selenium.isChecked("css=input[name='"+strOptionsName[i][0]+"']")==false)
								selenium.click("css=input[name='"+strOptionsName[i][0]+"']");
						}else if(strOptionsName[i][1].equals("false")){
							if(selenium.isChecked("css=input[name='"+strOptionsName[i][0]+"']"))
								selenium.click("css=input[name='"+strOptionsName[i][0]+"']");
						}
					}
				
				}
				
				//click on save
				selenium.click(propElementDetails.getProperty("EditCustomView.EditCustomViewOptions.Save"));					
				selenium.waitForPageToLoad(gstrTimeOut);
				
				
				try{
					assertEquals("Custom View - Table", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "+strResTypeName+"']/thead/tr/th[2][text()='"+strResTypeName+"']"));
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "+strResTypeName+"']/thead/tr/th[3]/a[text()='"+strStatusType+"']"));
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "+strResTypeName+"']/tbody/tr/td[2]/a[text()='"+strResource+"']"));
					log4j.info("'Custom View - Table' screen is displayed with resource "+strResource+" and status type "+strStatusType);
				} catch (AssertionError Ae) {
					strReason = "'Custom View - Table' screen is NOT displayed with resource "+strResource+" and status type "+strStatusType;
					log4j.info("'Custom View - Table' screen is NOT displayed with resource "+strResource+" and status type "+strStatusType);
				}		
			
			} catch (AssertionError Ae) {
				strReason = "'Edit Custom View Options (Columns)' screen is NOT displayed." ;
				log4j.info("'Edit Custom View Options (Columns)' screen is NOT displayed.");
			}
		} catch (Exception e) {
			log4j.info("findResources function failed" + e);
			strReason = "findResources function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	: check status types in My status change preferences
	'Precondition	:None
	'Arguments		:selenium,strStatuTypes,strStatus,strResTypeName,strEmail,
					strText,strWeb
	'Returns		:String
	'Date	 		:24-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/	
	public String checkStatTypeInMyStatusChangePrefs(Selenium selenium,
			String strStatuTypes, String strStatus, String strEmail,
			String strText, String strWeb) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
								+ strStatuTypes + "']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[4][text()='"
								+ strStatus + "']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[5][contains(text(),'"
								+ strEmail + "')]"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][contains(text(),'"
								+ strText + "')]"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[7][contains(text(),'"
								+ strWeb + "')]"));
				log4j
						.info("Status Type "
								+ strStatuTypes
								+ " is displayed with appropriate notification methods");
			} catch (AssertionError Ae) {
				strReason = strReason
						+ " Status Type "
						+ strStatuTypes
						+ " is NOT displayed with appropriate notification methods";
				log4j
						.info("Status Type "
								+ strStatuTypes
								+ " is NOT displayed with appropriate notification methods");
			}

		} catch (Exception e) {
			log4j
					.info("checkStatTypeInMyStatusChangePrefs function failed"
							+ e);
			strReason = "checkStatTypeInMyStatusChangePrefs function failed"
					+ e;
		}
		return strReason;
	}
	
	 /***********************************************************************
		'Description	: navigate to Edit My status change preferences page
		'Precondition	:None
		'Arguments		:selenium,strResource
						
		'Returns		:String
		'Date	 		:24-May-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'<Date>                                   <Name>
		************************************************************************/
	public String navToEditMyStatusChangePrefs(Selenium selenium,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on Edit
			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strResource
							+ "']/parent::tr/td[1]/a[text()='edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

			} catch (AssertionError Ae) {
				strReason = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navToEditMyStatusChangePrefs function failed" + e);
			strReason = "navToEditMyStatusChangePrefs function failed" + e;
		}
		return strReason;
	}

	 /***********************************************************************
	'Description	: check status types in Edit My status change preferences
	'Precondition	:None
	'Arguments		:selenium,strStatusType,strNotifData
					
	'Returns		:String
	'Date	 		:24-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String checkStatTypeInEditMyStatChangePrefs(Selenium selenium,
			String strStatusType, String strNotifData[][]) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + strStatusType
								+ "']"));

				for (int intRec = 0; intRec < strNotifData.length; intRec++) {
					try {
						assertEquals(
								strNotifData[intRec][0],
								selenium
										.getValue("//table[@class='displayTable']/tbody/tr/td/div[text()='"
												+ strStatusType
												+ "']/following-sibling::table"
												+ "/tbody/tr["
												+ (intRec + 1)
												+ "]/td[1]/input"));

						if (strNotifData[intRec][1].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[2]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[2]/input"));
						}

						if (strNotifData[intRec][2].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[3]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[3]/input"));
						}

						if (strNotifData[intRec][3].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[4]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[4]/input"));
						}
					} catch (AssertionError Ae) {
					}
				}

				log4j
						.info("Status Type "
								+ strStatusType
								+ " is displayed with appropriate notification methods");
			} catch (AssertionError Ae) {
				strReason = strReason
						+ " Status Type "
						+ strStatusType
						+ " is NOT displayed with appropriate notification methods";
				log4j
						.info("Status Type "
								+ strStatusType
								+ " is NOT displayed with appropriate notification methods");
			}

		} catch (Exception e) {
			log4j
					.info("checkStatTypeInMyStatusChangePrefs function failed"
							+ e);
			strReason = "checkStatTypeInMyStatusChangePrefs function failed"
					+ e;
		}
		return strReason;
	}
	 /***********************************************************************
	'Description	: Add Status change preferences
	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:24-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String addStatusPrefs(Selenium selenium,String strResource,String strCategory,
			String strRegion,String strCityZipCd,String strState) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
	
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			//click on Add button
			selenium.click(propElementDetails.getProperty("StatusChangePrefs.Add"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try{
				assertEquals("Find Resources",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Find Resources page is displayed" );
				
				//Enter value for Name
				selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strResource);
				//Enter for category
				selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="+strCategory);
				if(selenium.isElementPresent("Pref.FindResources.Region")){
					//Select region 
					selenium.select(propElementDetails.getProperty("Pref.FindResources.Region"), "label="+strRegion);
				}
				//Enter city, county or zip
				selenium.type(propElementDetails.getProperty("Pref.FindResources.CityZip"), strCityZipCd);
				//Select the state
				selenium.select(propElementDetails.getProperty("Pref.FindResources.State"), "label="+strState);
				
				//click on search button
				selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']"));
					log4j.info("Resource "+strResource+" is found");
					
					//select the resource
					selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']/parent::tr/td[1]/input");
					//click on Notifications
					selenium.click(propElementDetails.getProperty("StatusChangePrefs.FindRes.Notifications"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
				} catch (AssertionError Ae) {
					log4j.info("Resource "+strResource+" is NOT found");
					strReason="Resource "+strResource+" is NOT found";
				}
			} catch (AssertionError Ae) {
				strReason = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("checkStatTypeInMyStatusChangePrefs function failed" + e);
			strReason = "checkStatTypeInMyStatusChangePrefs function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
		  'Description  :Verify ST is added in the edit custom view page
		  'Precondition :None
		  'Arguments    :selenium,strSTValue,blnSave
		  'Returns      :String
		  'Date         :29-May-2012
		  'Author       :QSG
		  '-----------------------------------------------------------------------
		  'Modified Date                            Modified By
		  '25-May-2012                               <Name>
		  ************************************************************************/
		  
	public String addSTInEditCustViewOptionPage(Selenium selenium,
			String[][] strSTValue, boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Custom View Options (Columns) is displayed");

				for (int i = 0; i < strSTValue.length; i++) {
					if (strSTValue[i][1].equals("true")) {
						if (selenium
								.isChecked("css=input[name='viewColumnID'][value='st_"
										+ strSTValue[i][0] + "']") == false)
							selenium
									.click("css=input[name='viewColumnID'][value='st_"
											+ strSTValue[i][0] + "']");
					} else if (strSTValue[i][1].equals("false")) {
						if (selenium
								.isChecked("css=input[name='viewColumnID'][value='st_"
										+ strSTValue[i][0] + "']"))
							selenium
									.click("css=input[name='viewColumnID'][value='st_"
											+ strSTValue[i][0] + "']");
					}
				}

				if (blnSave) {

					selenium.click(propElementDetails.getProperty("EditCustomView.EditCustomViewOptions.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View Options (Columns) is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Custom View Options (Columns) is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("addSTInEditCustViewOptionPage function failed" + e);
			strErrorMsg = "addSTInEditCustViewOptionPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	'Description	: check status types in Edit custom View options
	'Precondition	:None
	'Arguments		:selenium,strStatusType
					
	'Returns		:String
	'Date	 		:25-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String checkStatTypeInEditCustViewOptions(Selenium selenium,String strStatusType) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
	
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			selenium.click(propElementDetails.getProperty("EditCustomView.Options"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try{
				assertEquals("Edit Custom View Options (Columns)",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				assertTrue(selenium.isElementPresent("//b[text()='"+strStatusType+"']"));
				log4j.info("Status Type "+strStatusType+" is displayed in Edit Custom View Options (Columns)");
			} catch (AssertionError Ae) {
				strReason = "Status Type "+strStatusType+" is NOT displayed in Edit Custom View Options (Columns)";
				log4j.info("Status Type "+strStatusType+" is NOT displayed in Edit Custom View Options (Columns)");
			}
		
			
		} catch (Exception e) {
			log4j.info("checkStatTypeInEditCustViewOptions function failed" + e);
			strReason = "checkStatTypeInEditCustViewOptions function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	 'Description :verify ST in Edit Custom View Option Page
	 'Precondition :None
	 'Arguments  :selenium,String strST[]
	 'Returns  :String
	 'Date    :25-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '25-May-2012                               <Name>
	 ************************************************************************/	 
	public String verifySTinEditCuctmViewOptionPge(Selenium selenium,
			String strST[]) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("Edit Custom View Options (Columns) page is  displayed");

				for (String s : strST) {

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/form/div/div/b[text()='"
										+ s + "']"));
						log4j.info("Status Type " + s + " is displayed");
					} catch (AssertionError Ae) {
						strErrorMsg = strErrorMsg+" Status Type " + s + " is NOT displayed";
						log4j.info("Status Type " + s + " is NOT displayed");
					}

				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View Options (Columns) page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Custom View Options (Columns) page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("verify ST in Edit Custom View Option Page function failed"
							+ e);
			strErrorMsg = "verify ST in Edit Custom View Option Page function failed"
					+ e;
		}
		return strErrorMsg;
	}
	 
	 /***********************************************************************
	 'Description :Verify Edit custom view option page  displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :25-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '25-May-2012                               <Name>
	 ************************************************************************/
	 
	public String navEditCustomViewOptionPage(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("EditCustomView.Options"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Edit Custom View Options (Columns)", selenium
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
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("Edit Custom View Options (Columns) page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View Options (Columns) page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Custom View Options (Columns) page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("navigate to Edit Custom View Option Page function failed"
							+ e);
			strErrorMsg = "navigate to Edit Custom View Option Page function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description		: Navigate to Preferences > Change Password and verify Change Password page is displayed
	' Precondition		: N/A 
	' Arguments			: selenium
	' Returns			: String 
	' Date				: 02-06-2012
	' Author			: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String navToPrefChangePwd(Selenium selenium) throws Exception {
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

			selenium.mouseOver(propElementDetails.getProperty("Preferences"));
			selenium.click(propElementDetails.getProperty("Prop60"));
			selenium.waitForPageToLoad(gstrTimeOut);
			int intCnt = 0;
			do {
				try {
					assertEquals("Change Password",
							selenium.getText(propElementDetails
									.getProperty("Prop40")));
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
				assertEquals("Change Password",
						selenium.getText(propElementDetails
								.getProperty("Prop40")));
				assertEquals("If the Change Password dialog box did not open automatically, change your password by clicking here.",
						selenium.getText(propElementDetails
								.getProperty("Prop61")));
				log4j.info("''Change Password' page is displayed with instruction 'To change your password please click here.'");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Change Password' page is NOT displayed with instruction 'To change your password please click here.'");
				lStrReason = lStrReason
						+ "; "
						+ "'Change Password' page is NOT displayed with instruction 'To change your password please click here.'";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; navToPrefChangePwd function failed"
					+ e.toString();
		}

		return lStrReason;
	}
	
	/*****************************************************************
	'Description	:Navigate to Set Up Your Password by clicking on 'Click here' link
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:02-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/	
	public String navToSetUpPwdFromChangePwd(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error message
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//Click on here link
			selenium.click(propElementDetails
					.getProperty("Preferences.ChangePwd.here"));
			//selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt = 0;
			do {
				try {

					assertEquals("Set Up Your Password", selenium
							.getText(propElementDetails.getProperty("SetUpPwd")));
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

				assertEquals("Set Up Your Password", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));

				log4j.info("Set Up Your Password is displayed");

						
			} catch (AssertionError Ae) {

				strErrorMsg = "Set Up Your Password is NOT displayed" + Ae;
				log4j.info("Set Up Your Password is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("navToSetUpPwdFromChangePwd function failed" + e);
			strErrorMsg = "navToSetUpPwdFromChangePwd function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************************
	'Description	: Provide the data in Set Up Your Password screen 
					and submit. Verify that User is directed to region 
					default view.
	'Precondition	:None
	'Arguments		:selenium,strNewPasswd
	'Returns		:String
	'Date	 		:02-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/	
	public String changePasswdAndVerifyUser(Selenium selenium, String strNewPasswd) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Thread.sleep(2000);
			selenium.selectWindow("");
			selenium.selectFrame("Data");

		
			//Enter New password
			selenium.type(propElementDetails
					.getProperty("SetUpPwd.NewUsrName"), strNewPasswd);
			selenium
					.type(propElementDetails
							.getProperty("SetUpPwd.CofrmUsrName"),
							strNewPasswd);
			//click submit
			selenium.click(propElementDetails
					.getProperty("SetUpPwd.Submit"));
			//selenium.waitForPageToLoad(gstrTimeOut);
			selenium.click("id=TB_closeWindowButton");
			Thread.sleep(1000);
			
			int intCnt = 0;
			do {
				try {

					assertEquals("Change Password", selenium
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
				assertEquals("Change Password", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("User is directed to the 'Change Password' screen.");

			} catch (AssertionError Ae) {
				strErrorMsg = "User is NOT directed to the 'Change Password' screen.";
				log4j.info("User is NOT directed to the 'Change Password' screen.");
			}

					
		} catch (Exception e) {
			log4j.info("changePasswdAndVerifyUser function failed" + e);
			strErrorMsg = "changePasswdAndVerifyUser function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************************
	'Description	: Check Customized View link is present when mouse
					hover on Preferences
	'Precondition	:None
	'Arguments		:selenium,blnAvailable
	'Returns		:String
	'Date	 		:06-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/	
	public String checkCustomizedViewOptInPrefDropDown(Selenium selenium,boolean blnAvailable) throws Exception {
		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.mouseOver(propElementDetails.getProperty("Preferences"));

					
			if(blnAvailable){
				try {
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("Preferences.CustomizedView")));
					log4j.info("'Customized view' option is available in the 'Preferences' drop down.");
	
				} catch (AssertionError Ae) {
					strErrorMsg = "'Customized view' option is not available in the 'Preferences' drop down.";
					log4j.info("'Customized view' option is not available in the 'Preferences' drop down.");
				}
					
			}else{
				try {
					assertFalse(selenium
							.isElementPresent(propElementDetails
									.getProperty("Preferences.CustomizedView")));
					log4j.info("'Customized view' option is not available in the 'Preferences' drop down.");
	
				} catch (AssertionError Ae) {
					strErrorMsg = "'Customized view' option is still available in the 'Preferences' drop down.";
					log4j.info("'Customized view' option is still available in the 'Preferences' drop down.");
				}
			}
		} catch (Exception e) {
			log4j.info("checkCustomizedViewOptInPrefDropDown function failed" + e);
			strErrorMsg = "checkCustomizedViewOptInPrefDropDown function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************************
	'Description	: Check Customized View link is present when clicked on
					Preferences
	'Precondition	:None
	'Arguments		:selenium,blnAvailable
	'Returns		:String
	'Date	 		:06-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/
	public String checkCustomizedViewOptInPrefMenu(Selenium selenium,boolean blnAvailable) throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click(propElementDetails.getProperty("Preferences"));
			selenium.waitForPageToLoad(gstrTimeOut);
					
			if(blnAvailable){
				try {
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("Preferences.CustomizedView")));
					log4j.info("'Customized view' option is available in the 'Preferences' Menu");
	
				} catch (AssertionError Ae) {
					strErrorMsg = "'Customized view' option is not available in the 'Preferences' Menu";
					log4j.info("'Customized view' option is not available in the 'Preferences' Menu");
				}
					
			}else{
				try {
					assertFalse(selenium
							.isElementPresent(propElementDetails
									.getProperty("Preferences.CustomizedView")));
					log4j.info("'Customized view' option is not available in the 'Preferences' Menu");
	
				} catch (AssertionError Ae) {
					strErrorMsg = "'Customized view' option is still available in the 'Preferences' Menu";
					log4j.info("'Customized view' option is still available in the 'Preferences' Menu");
				}
			}
		} catch (Exception e) {
			log4j.info("checkCustomizedViewOptInPrefMenu function failed" + e);
			strErrorMsg = "checkCustomizedViewOptInPrefMenu function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************************
	'Description	: Click on delete link for Resource Type and save
	'Precondition	:None
	'Arguments		:selenium,strResourceType
	'Returns		:String
	'Date	 		:06-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/	
	public String deleteCustomViewInEditCustView(Selenium selenium,String strResourceType) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click("//span[contains(text(),'"+strResourceType+"')]/following-sibling::div/div[@id='editGroupLinks']/a[text()='Delete']");
			//save
			selenium.click(propElementDetails.getProperty("Save"));			
			selenium.waitForPageToLoad(gstrTimeOut);
					
			
		} catch (Exception e) {
			log4j.info("deleteCustomViewInEditCustView function failed" + e);
			strErrorMsg = "deleteCustomViewInEditCustView function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:navigate to edit custom view options, select the status types
					and save
	'Precondition	:None
	'Arguments		:selenium,strStatusType
	'Returns		:String
	'Date	 		:7-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/	
	public String editCustomViewOptions(Selenium selenium,
			String strStatusType[]) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditCustomView.Options"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try{
					assertEquals("Edit Custom View Options (Columns)", selenium
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
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed. ");
				if (strStatusType != null) {
					// Select the status types
					for (int intOpt = 0; intOpt < strStatusType.length; intOpt++) {
						if (selenium.isChecked("//b[text()='"
								+ strStatusType[intOpt]
								+ "']/preceding-sibling::input[1]") == false)
							selenium.click("//b[text()='"
									+ strStatusType[intOpt]
									+ "']/preceding-sibling::input[1]");
					}
				}

				// click on save
				selenium
						.click(propElementDetails
								.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				strReason = "'Edit Custom View Options (Columns)' screen is displayed.";
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed.");
			}
		} catch (Exception e) {
			log4j.info("editCustomViewOptions function failed" + e);
			strReason = "editCustomViewOptions function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:verify Resource Type,
					Status type, resources in Custom View Table
	'Precondition	:None
	'Arguments		:selenium,strResType,strResource,strStatType
	'Returns		:String
	'Date	 		:7-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/	
	public String verifyRTSTAndRSInCustView(Selenium selenium,
			String strResType, String strResource[], String[] strStatType)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
								+ strResType
								+ "']/thead/tr/th[2][text()='"
								+ strResType + "']"));
				log4j.info("Resource type '" + strResType
						+ "' is displayed in the Custom View Table screen '");
				int intRow = 0;
				for (intRow = 0; intRow < strResource.length; intRow++) {
					try {

						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
										+ strResType
										+ "']/tbody/tr["
										+ (intRow + 1)
										+ "]/td[2]/a[text()='"
										+ strResource[intRow] + "']"));
						log4j
								.info("Resource "
										+ strResource[intRow]
										+ " is displayed in the Custom View Table screen under resource type "
										+ strResType);
					} catch (AssertionError ae) {
						log4j
								.info("Resource "
										+ strResource[intRow]
										+ " is NOT displayed in the Custom View Table screen under resource type "
										+ strResType);
						strReason = strReason
								+ " Resource "
								+ strResource[intRow]
								+ " is NOT displayed in the Custom View Table screen under resource type "
								+ strResType;
					}
				}

				int intCol = 0;
				for (intCol = 0; intCol < strStatType.length; intCol++) {
					try {

						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
										+ strResType
										+ "']/thead/tr/th["
										+ (intCol + 3)
										+ "]/a[text()='"
										+ strStatType[intCol] + "']"));
						log4j
								.info("Status Type "
										+ strStatType[intCol]
										+ " is displayed in the Custom View Table screen under resource type "
										+ strResType);
					} catch (AssertionError ae) {
						log4j
								.info("Status Type is "
										+ strStatType[intCol]
										+ " is NOT displayed in the Custom View Table screen under resource type "
										+ strResType);
						strReason = strReason
								+ " Status Type  "
								+ strStatType[intCol]
								+ " is NOT displayed in the Custom View Table screen under resource type "
								+ strResType;
					}
				}

			} catch (AssertionError ae) {
				log4j
						.info("Resource type '"
								+ strResType
								+ "' is NOT displayed in the Custom View Table screen '");
				strReason = "Resource type '"
						+ strResType
						+ "' is NOT displayed in the Custom View Table screen '";
			}

		} catch (Exception e) {
			log4j.info("VerifyRTSTAndRSInCustView function failed" + e);
			strReason = "VerifyRTSTAndRSInCustView function failed" + e;
		}
		return strReason;
	}
	
	/*************************************************************************
	 'Description :Verify event notifications in'My Event Notification 
	 '     Preferences' page  displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	  * @throws Exception 
	 ************************************************************************/
	public String verifyEventNotifInMyEventNotifPref(Selenium selenium,
			String strTemplateValue, boolean blnEmail, boolean blnPager,
			boolean blnWebPage) throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnEmail) {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strTemplateValue + "'][name='emailInd']"));

					log4j.info("Email notification is Selected");

				} catch (AssertionError Aes) {
					log4j.info("Email notification is NOT Selected");
					strErrorMsg = "Email notification is NOT Selected";
				}

			} else {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strTemplateValue + "'][name='emailInd']") == false);

					log4j.info("Email notification is NOT Selected");

				} catch (AssertionError Aes) {
					log4j.info("Email notification is Selected");
					strErrorMsg = "Email notification is Selected";

				}

			}

			if (blnPager) {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strTemplateValue + "'][name='pagerInd']"));

					log4j.info("pager notification is Selected");

				} catch (AssertionError Aes) {
					log4j.info("pager notification is NOT Selected");
					strErrorMsg = "pager notification is NOT Selected";
				}

			} else {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strTemplateValue + "'][name='pagerInd']") == false);

					log4j.info("pager notification is NOT Selected");

				} catch (AssertionError Aes) {
					log4j.info("pager notification is Selected");
					strErrorMsg = "pager notification is Selected";

				}

			}

			if (blnEmail) {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strTemplateValue + "'][name='webInd']"));

					log4j.info("Web notification is Selected");

				} catch (AssertionError Aes) {
					log4j.info("Web notification is NOT Selected");
					strErrorMsg = "Web notification is NOT Selected";
				}

			} else {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strTemplateValue + "'][name='webInd']") == false);

					log4j.info("Web notification is NOT Selected");

				} catch (AssertionError Aes) {
					log4j.info("Web notification is Selected");
					strErrorMsg = "Web notification is Selected";

				}

			}

		} catch (Exception e) {
			log4j
					.info("navigate to verifyEventNotifInMyEventNotifPref function failed"
							+ e);
			strErrorMsg = "navigate to verifyEventNotifInMyEventNotifPref function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/*********************************************************************
	 'Description 		:Verify 'My Event Notification Preferences' page  displayed
	 'Precondition 		:None
	 'Arguments  		:selenium
	 'Returns  			:String
	 'Date    			:03-May-2012
	 'Author   			:QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	  * 
	 ************************************************************************/	 
	public String navToEventNotification(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error message
		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails.getProperty("Preferences"));

			selenium.click(propElementDetails.getProperty("Pref.EventNotification_Link"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("My Event Notification Preferences", selenium
						.getTitle());
				log4j.info("My Event Notification Preferences page displayed");
			} catch (AssertionError a) {
				log4j
						.info("My Event Notification Preferences page Not displayed");
				strErrorMsg = "My Event Notification Preferences page Not displayed"
						+ a;
			}
		} catch (Exception e) {
			log4j.info("navigate to EventNotification function failed" + e);
			strErrorMsg = "navigate to EventNotification function failed" + e;
		}
		return strErrorMsg;
	}
	
	  /*******************************************************************************************
	  ' Description		: Navigation to userinfo page
	  ' Precondition	: N/A 
	  ' Arguments		: selenium, 
	  ' Returns			: String 
	  ' Date			: 05/06/2012
	  ' Author			: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String navigateToUserInfo(Selenium selenium) throws Exception
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
	  		selenium.mouseOver(propElementDetails.getProperty("Preferences"));
	  		selenium.click(propElementDetails.getProperty("Prop131"));
	  		selenium.waitForPageToLoad(gstrTimeOut);
	  		
	  		try {
	  			assertEquals("Update User Info",selenium.getText(propElementDetails.getProperty("Header.Text")));
	  			log4j.info("'Update User Info' page is displayed. ");
	  		}
	  		catch (AssertionError Ae) {
	  			log4j.info(Ae);
	  			log4j.info("'Update User Info' page is NOT displayed. ");
	  			lStrReason ="'Update User Info' page is NOT displayed. ";
	  		}
	  	}catch(Exception e){
	  		log4j.info("navToUserInfo Function failed");
	  		lStrReason ="navToUserInfo Function failed";
	  	}

	  	return lStrReason;
	  }
	  
	  /*******************************************************************************************
	  ' Description: Fill CheckBoxes in UserInfo
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String fillCheckBoxInUsinfo(Selenium selenium,boolean blnContrast,boolean blnEmailValue,
			  boolean blnEmailInd, boolean blnPagerInd) throws Exception
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
	  		if(blnContrast){
				if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewHigh_Contrast"))==false){
					selenium.click(propElementDetails.getProperty("Pref.ViewHigh_Contrast"));
				}
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewHigh_Contrast"))){
					selenium.click(propElementDetails.getProperty("Pref.ViewHigh_Contrast"));
				}
				}
				if(blnEmailValue){
					if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewEmailValue"))==false){
						selenium.click(propElementDetails.getProperty("Pref.ViewEmailValue"));
					}
				}else{
					if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewEmailValue"))){
						selenium.click(propElementDetails.getProperty("Pref.ViewEmailValue"));
					}
				}
				
				/*if(blnEmailInd){
					if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewExpiredStatusEmailInd"))==false){
						selenium.click(propElementDetails.getProperty("Pref.ViewExpiredStatusEmailInd"));
					}
				}else{
					if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewExpiredStatusEmailInd"))){
						selenium.click(propElementDetails.getProperty("Pref.ViewExpiredStatusEmailInd"));
					}
				}
				if(blnPagerInd){
					if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewExpiredStatusPagerInd"))==false){
						selenium.click(propElementDetails.getProperty("Pref.ViewExpiredStatusPagerInd"));
					}
				}else{
					if(selenium.isChecked(propElementDetails.getProperty("Pref.ViewExpiredStatusPagerInd"))){
						selenium.click(propElementDetails.getProperty("Pref.ViewExpiredStatusPagerInd"));
					}
				}*/
				  		
	  	}catch(Exception e){
	  		log4j.info("fillCheckBoxInUsinfo function failed");
	  		lStrReason = "fillCheckBoxInUsinfo function failed";
	  	}

	  	return lStrReason;
	  }
	  
	  /*******************************************************************************************
	  ' Description: Fill RadioButtons in UserInfo of Notification Overview
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String fillNotification(Selenium selenium,boolean blnEmailNot,
			  boolean blnPagerNot, boolean blnWebNot,boolean blnEmailSum,boolean blnPagerSum,boolean blnEmailTime,
			  boolean blnPagerTime,boolean blnWebTime) throws Exception
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
	  		
	  		if(blnEmailNot){
				if(selenium.isChecked("xpath=(//input[@name='email-all-notif'])[2]")==false){
					selenium.click("xpath=(//input[@name='email-all-notif'])[2]");
				}
			}else{
				if(selenium.isChecked("xpath=(//input[@name='email-all-notif'])[2]")){
					selenium.click("xpath=(//input[@name='email-all-notif'])[2]");
				}
				}
	  		
	  		if(blnPagerNot){
				if(selenium.isChecked("xpath=(//input[@name='pager-all-notif'])[2]")==false){
					selenium.click("xpath=(//input[@name='pager-all-notif'])[2]");
				}
			}else{
				if(selenium.isChecked("xpath=(//input[@name='pager-all-notif'])[2]")){
					selenium.click("xpath=(//input[@name='pager-all-notif'])[2]");
				}
				}
	  		
	  		if(blnWebNot){
				if(selenium.isChecked("xpath=(//input[@name='web-all-notif'])[2]")==false){
					selenium.click("xpath=(//input[@name='web-all-notif'])[2]");
				}
			}else{
				if(selenium.isChecked("xpath=(//input[@name='web-all-notif'])[2]")){
					selenium.click("xpath=(//input[@name='web-all-notif'])[2]");
				}
				}
	  	
	  		if(blnEmailSum){
				if(selenium.isChecked("css=input[name=\"email-include-summary\"]")==false){
					selenium.click("css=input[name=\"email-include-summary\"]");
				}
			}else{
				if(selenium.isChecked("css=input[name=\"email-include-summary\"]")){
					selenium.click("css=input[name=\"email-include-summary\"]");
				}
				}
	  		

	  		if(blnPagerSum){
				if(selenium.isChecked("css=input[name=\"pager-include-summary\"]")==false){
					selenium.click("css=input[name=\"pager-include-summary\"]");
				}
			}else{
				if(selenium.isChecked("css=input[name=\"pager-include-summary\"]")){
					selenium.click("css=input[name=\"pager-include-summary\"]");
				}
				}
	  		if(blnEmailTime){
				if(selenium.isChecked("xpath=(//input[@name='email-which-times'])[2]")==false){
					selenium.click("xpath=(//input[@name='email-which-times'])[2]");
				}
			}else{
				if(selenium.isChecked("xpath=(//input[@name='email-which-times'])[2]")){
					selenium.click("xpath=(//input[@name='email-which-times'])[2]");
				}
				}
	  		
	  		if(blnPagerTime){
				if(selenium.isChecked("xpath=(//input[@name='pager-which-times'])[2]")==false){
					selenium.click("xpath=(//input[@name='pager-which-times'])[2]");
				}
			}else{
				if(selenium.isChecked("xpath=(//input[@name='pager-which-times'])[2]")){
					selenium.click("xpath=(//input[@name='pager-which-times'])[2]");
				}
				}
	  		
	  		if(blnWebTime){
				if(selenium.isChecked("xpath=(//input[@name='web-which-times'])[2]")==false){
					selenium.click("xpath=(//input[@name='web-which-times'])[2]");
				}
			}else{
				if(selenium.isChecked("xpath=(//input[@name='web-which-times'])[2]")){
					selenium.click("xpath=(//input[@name='web-which-times'])[2]");
				}
			}
				  		
	  	}catch(Exception e){
	  		log4j.info("fillCheckBoxInUsinfo function failed");
	  		lStrReason = "fillCheckBoxInUsinfo function failed";
	  	}

	  	return lStrReason;
	  }
	  /*******************************************************************************************
	  ' Description: Verify FullName of UserInfo
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String navAndVerifyFieldsUinfo(Selenium selenium,String strFullName,String strFirstname,String strmiddlename,String strLastName,
			  String strOrg, String StrPhoneNo,String strPremail,String strEmail,String strPager,String strVieID) throws Exception
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
	  		selenium.click(propElementDetails.getProperty("Prop131"));
	  		selenium.waitForPageToLoad(gstrTimeOut);
	  		
	  		try {
				assertEquals("Update User Info", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Update User Info' page is displayed ");
				
				try {
					assertEquals(strFullName, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.FulUsrName")));
					assertEquals(strFirstname, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.firstName")));
					assertEquals(strmiddlename, selenium
							.getValue(propElementDetails
									.getProperty("CreateNewUsr.middleName")));
					assertEquals(strLastName, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.lastName")));
					assertEquals(strOrg, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.OrgName")));
					assertEquals(StrPhoneNo, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.PhoneNo")));
					assertEquals(strPremail, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.primaryEMail")));
					assertEquals(strEmail, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.eMail")));
					assertEquals(strPager, selenium.getValue(propElementDetails
							.getProperty("CreateNewUsr.Pager")));
					
					assertTrue(selenium
							.isChecked(propElementDetails.getProperty("Pref.ViewHigh_Contrast")));
					assertTrue(selenium
							.isChecked(propElementDetails.getProperty("Pref.ViewEmailValue")));

					/*assertTrue(selenium
							.isChecked(propElementDetails.getProperty("Pref.ViewExpiredStatusEmailInd")));

					assertTrue(selenium
							.isChecked(propElementDetails.getProperty("Pref.ViewExpiredStatusPagerInd")));

					assertTrue(selenium
							.isChecked("css=input[name=\"expiredStatusPagerInd\"]"));
					assertTrue(selenium.isChecked("xpath=(//input[@name='email-all-notif'])[2]"));*/
		  	
					log4j.info("'Edited data is retained. ");
				}
				catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'Edited data is NOT retained. ");
					lStrReason = "Edited data is NOT retained. ";
				}

				
			} catch (AssertionError Ae) {

				lStrReason = "'Update User Info' page is NOT displayed " + Ae;
				log4j.info("'Update User Info' page is NOT displayed " + Ae);
			}

			
	  	
	  	}catch(Exception e){
	  		log4j.info("navAndVerifyFullNameUinfo function failed");
	  		lStrReason = "navAndVerifyFullNameUinfo function failed";
	  	}

	  	return lStrReason;
	  }
	  
	  /*******************************************************************************************
	  ' Description: Edit And Save FullName of UserInfo
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String verifyFullNameUserInfo(Selenium selenium,boolean blnSave,String strFullName) throws Exception
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
	  		if(blnSave){
	  	            // save
	  				selenium.click(propElementDetails.getProperty("EditCustomView.EditCustomViewOptions.Save"));
	  				selenium.waitForPageToLoad(gstrTimeOut);
	  		}
	  		try {
	  			assertEquals("Preferences Menu",selenium.getText(propElementDetails.getProperty("Header.Text")));
	  			log4j.info("'Preferences Menu' page is displayed. ");
	  		}
	  		catch (AssertionError Ae) {
	  			log4j.info("'Preferences Menu' page is NOT displayed. ");
	  			lStrReason ="'Preferences Menu' page is NOT displayed. ";
	  		}
	  		try {
	  			assertTrue(selenium.isTextPresent(strFullName));
	  			log4j.info("edited 'Full name "+strFullName+" of the user is updated at the footer of the application. ");
	  		}
	  		catch (AssertionError Ae) {
	  			log4j.info("edited 'Full name "+strFullName+" of the user is updated at the footer of the application. ");
	  			lStrReason ="edited 'Full name "+strFullName+" of the user is updated at the footer of the application. ";
	  		}
	  	}catch(Exception e){
	  		log4j.info("editAndSaveFullNameUinfo function failed");
	  		lStrReason = "editAndSaveFullNameUinfo function failed";
	  	}

	  	return lStrReason;
	  }
	  
	  /*******************************************************************************************
	  ' Description: Fill fields in UserInfo
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String fillfieldsInUsinfo(Selenium selenium,String strFullName,String strFirstname,String strmiddlename,String strLastName,
			   String strOrg,String StrPhoneNo,String strPremail,String strEmail,String strPager,String strVieID) throws Exception
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
	  		selenium.type(propElementDetails.getProperty("CreateNewUsr.FulUsrName"),strFullName);
	  		selenium.type(propElementDetails.getProperty("CreateNewUsr.firstName"), strFirstname);
			selenium.type(propElementDetails.getProperty("CreateNewUsr.middleName"), strmiddlename);
			selenium.type(propElementDetails.getProperty("CreateNewUsr.lastName"), strLastName);
			selenium.type(propElementDetails.getProperty("CreateNewUsr.OrgName"), strOrg);
			selenium.type(propElementDetails.getProperty("CreateNewUsr.PhoneNo"), StrPhoneNo);
			selenium.type(propElementDetails.getProperty("CreateNewUsr.primaryEMail"), strPremail);
			selenium.type(propElementDetails.getProperty("CreateNewUsr.eMail"), strEmail);//autoemr@qsgsoft.com
			selenium.type(propElementDetails.getProperty("CreateNewUsr.Pager"), strPager);
			selenium.select("css=select[name=\"defaultViewID\"]", "label="+strVieID+"");
	  	}catch(Exception e){
	  		log4j.info("fillfieldsInUsinfo function failed");
	  		lStrReason = "fillfieldsInUsinfo function failed";
	  	}

	  	return lStrReason;
	  }
	  
	  
	  /*******************************************************************************************
	  ' Description: Verify FullName of UserInfo
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String verifyFieldsUinfo(Selenium selenium,String strFullName,String strFirstname,
			  String strOrg, String strPhoneNo,String strEmail) throws Exception
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
				assertEquals(strFullName, selenium.getValue(propElementDetails
						.getProperty("CreateNewUsr.FulUsrName")));
				log4j.info(strFullName+"field is displayed respectively under Preferences.");
	  		}
			catch (AssertionError Ae) {
				log4j.info(strFullName+"field is NOT displayed respectively under Preferences.");
				lStrReason =strFullName+"field is NOT displayed respectively under Preferences.";
			}
			try
			{
				assertEquals(strFirstname, selenium.getValue(propElementDetails
						.getProperty("CreateNewUsr.firstName")));
				log4j.info(strFirstname+"field is displayed respectively under Preferences.");
	  		}
			catch (AssertionError Ae) {
				log4j.info(strFirstname+"field is NOT displayed respectively under Preferences.");
				lStrReason =strFirstname+"field is NOT  displayed respectively under Preferences.";
			}
			try
			{
				assertEquals(strOrg, selenium.getValue(propElementDetails
						.getProperty("CreateNewUsr.OrgName")));
				log4j.info(strOrg+"field is displayed respectively under Preferences.");
	  		}
			catch (AssertionError Ae) {
				log4j.info(strOrg+"field is NOT displayed respectively under Preferences.");
				lStrReason =strOrg+"field is NOT  displayed respectively under Preferences.";
			}
			try
			{
				assertEquals(strPhoneNo, selenium.getValue(propElementDetails
						.getProperty("CreateNewUsr.PhoneNo")));
				log4j.info(strPhoneNo+"field is displayed respectively under Preferences.");
	  		}
			catch (AssertionError Ae) {
				log4j.info(strPhoneNo+"field is NOT displayed respectively under Preferences.");
				lStrReason =strPhoneNo+"field is NOT  displayed respectively under Preferences.";
			}
			try
			{
				assertEquals(strEmail, selenium.getValue(propElementDetails
						.getProperty("CreateNewUsr.eMail")));
				log4j.info(strEmail+"field is displayed respectively under Preferences.");
	  		}
			catch (AssertionError Ae) {
				log4j.info(strEmail+"field is NOT displayed respectively under Preferences.");
				lStrReason =strEmail+"field is NOT  displayed respectively under Preferences.";
			}
			
	  	}catch(Exception e){
	  		log4j.info("verifyFieldsUinfo function failed");
	  		lStrReason = "verifyFieldsUinfo function failed";
	  	}

	  	return lStrReason;
	  }
	  
	  /***********************************************************************
		'Description	:navigating to find  resource is added in status type preference page
		'Precondition	:None
		'Arguments		:selenium
		'Returns		:String
		'Date	 		:9-May-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'9-May-2012                               <Name>
		************************************************************************/

	public String navToFindResourcesPageAndRSEditUser(Selenium selenium,
			String strSearchRSName, String strSearchCategory,
			String strResourceValue) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Preferences.StatusChangePref.Add"));// Click 'Add' to add
			// the resources
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Find Resources page is  displayed");

				// Search criteria
				selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strSearchRSName);
				selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="
						+ strSearchCategory);

				selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);

				if (selenium.isChecked("css=input[name='resourceID'][value='"
						+ strResourceValue + "']") == false) {
					selenium.click("css=input[name='resourceID'][value='"
							+ strResourceValue + "']");
				}

				selenium.click(propElementDetails.getProperty("Pref.EventNotification_Link"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToFindResourcesPageAndRSEditUser function failed"
					+ e);
			strErrorMsg = "navToFindResourcesPageAndRSEditUser function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'9-May-2012                               <Name>
	************************************************************************/
	
	public String addResourcesAndSelctNotifSTInFrmEditUsr(Selenium selenium,
			String strUserName, String strSearchRSName,
			String strSearchCategory, String strResourceValue, String strSTValue)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Preferences.StatusChangePref.Add")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.click(propElementDetails.getProperty("Preferences.StatusChangePref.Add"));// Click 'Add' to add the
														// resources
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			intCnt=0;
			do{
				try {

					assertEquals("Find Resources", selenium
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
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Find Resources page is  displayed");

				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Pref.FindResources.Name")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				
				// Search criteria
				selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strSearchRSName);
				selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="
						+ strSearchCategory);

				selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);

				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='resourceID'][value='"
								+ strResourceValue + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked("css=input[name='resourceID'][value='"
						+ strResourceValue + "']") == false) {
					selenium.click("css=input[name='resourceID'][value='"
							+ strResourceValue + "']");
				}

				selenium.click(propElementDetails.getProperty("Pref.StatusChangeNotification_Link"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				Thread.sleep(10000);
				
				intCnt=0;
				do{
					try {

						assertEquals("Edit Status Change Preferences for "
								+ strUserName, selenium.getText(propElementDetails
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
					assertEquals("Edit Status Change Preferences for "
							+ strUserName, selenium.getText(propElementDetails
							.getProperty("Header.Text")));

					log4j.info("Edit Status Change Preferences for "
							+ strUserName + " page is  displayed");

					selenium.click("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']");

					selenium.click("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']");

					selenium.click("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']");

					// save
	  				selenium.click(propElementDetails.getProperty("EditCustomView.EditCustomViewOptions.Save"));
	  				selenium.waitForPageToLoad(gstrTimeOut);
	  				
	  				intCnt=0;
	  				do{
	  					try {

	  						assertEquals("Status Change Preferences for "
									+ strUserName, selenium
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
						assertEquals("Status Change Preferences for "
								+ strUserName, selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Status Change Preferences for "
								+ strUserName + " page is  displayed");

						/*try {
							assertEquals("Edit User", selenium.getText(propElementDetails
									.getProperty("Header.Text")));

							log4j.info("'Edit User' page is displayed ");
						} catch (AssertionError Ae) {

							strErrorMsg = "'Edit User' page is NOT displayed " + Ae;
							log4j.info("'Edit User' page is NOT displayed " + Ae);
						}*/
					} catch (AssertionError Ae) {
						strErrorMsg = "Status Change Preferences for "
								+ strUserName + " page is NOT displayed" + Ae;
						log4j.info("Status Change Preferences for "
								+ strUserName + " page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Edit Status Change Preferences for "
							+ strUserName + " page is NOT displayed" + Ae;
					log4j.info("Edit Status Change Preferences for "
							+ strUserName + " page is NOT displayed" + Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j
					.info("addResourcesAndSelctNotifSTInFrmEditUsr function failed"
							+ e);
			strErrorMsg = "addResourcesAndSelctNotifSTInFrmEditUsr function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/*************************************************************************
	 'Description :Verify event notifications in'My Event Notification 
	 '     Preferences' page  displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	  * @throws Exception 
	 ************************************************************************/

	public String verifyEventNotifInMyStatusChangPref(Selenium selenium,
			String strSTName, String strRTName) throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']"
						+ "/h1[text()='" + strRTName + "']/following-sibling:"
						+ ":table/tbody/tr/td[3][text()='" + strSTName + "']"));

				log4j
						.info("The selected preferences are retained for status type ST. ");

			} catch (AssertionError Aes) {
				log4j
						.info("The selected preferences are NOT retained for status type ST. ");
				strErrorMsg = "The selected preferences are NOT retained for status type ST. ";
			}

		} catch (Exception e) {
			log4j.info("verifyEventNotifInMyStatusChangPref function failed"
					+ e);
			strErrorMsg = "verifyEventNotifInMyStatusChangPref function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:navigate to Event notification preferences page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-May-2012                               <Name>
	************************************************************************/
	
	public String navEventNotificationPreferencePage(Selenium selenium,
			String strUserName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Pref.EventNotificationPreferencesLink")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.click(propElementDetails.getProperty("Pref.EventNotificationPreferencesLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt=0;
			do{
				try {
					assertEquals("Event Notification Preferences for "
							+ strUserName, selenium.getText(propElementDetails
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
				assertEquals("Event Notification Preferences for "
						+ strUserName, selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Event Notification Preferences for " + strUserName);

			} catch (AssertionError Ae) {
				strErrorMsg = "Event Notification Preferences for "
						+ strUserName + " is NOT displayed" + Ae;
				log4j.info("Event Notification Preferences for " + strUserName
						+ " is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navEventNotificationPreferencePage  function failed" + e);
			strErrorMsg = "navEventNotificationPreferencePage function failed" + e;
		}
		return strErrorMsg;
	}
	/**************************************************************************
	'Description	:Verify My Event Notification Preferences settings are done
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-May-2012                               <Name>
	************************************************************************/

	public String selectEvenNofMethodsInEditUserPgeETValue(Selenium selenium,
			String[] strTemplateValue, boolean blnEmail, boolean blnPager,
			boolean blnWebPage) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			
			for(String s:strTemplateValue){
				
				if(blnEmail){
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='emailInd']")){
						selenium.click("css=input[value='"+s+"'][name='emailInd']");
					}
				}
				
				
				if(blnPager){
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='pagerInd']")){
						selenium.click("css=input[value='"+s+"'][name='pagerInd']");
					}
				}
				
				
				if(blnWebPage){
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")==false){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}else{
					if(selenium.isChecked("css=input[value='"+s+"'][name='webInd']")){
						selenium.click("css=input[value='"+s+"'][name='webInd']");
					}
				}			
			}
			
			// save
				selenium.click(propElementDetails.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt=0;
				do{
					try {

						assertEquals("Edit User", selenium
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
				assertEquals("Edit User", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit User page is displayed is  displayed");

			} catch (AssertionError Ae) {
				strReason = "Edit User page is displayed is NOT displayed" + Ae;
				log4j.info("Edit User page is displayed is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("selectEvenNofMethodsInEditUserPgeETValue function failed" + e);
			strReason = "selectEvenNofMethodsInEditUserPgeETValue function failed" + e;
		}
		return strReason;

	}

	/**************************************************************************
	'Description	:Verify Preference Menu page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-May-2012                               <Name>
	************************************************************************/

	public String navBackToPrefMenuPge(Selenium selenium) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// save
			selenium.click(propElementDetails
					.getProperty("EditCustomView.EditCustomViewOptions.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Preferences Menu", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Preferences Menu page is  displayed");

			} catch (AssertionError Ae) {
				strReason = "Preferences Menu page is NOT displayed" + Ae;
				log4j.info("Preferences Menu page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("Error in navBackToPrefMenuPge" + e);
			strReason = "Error in navBackToPrefMenuPge" + e;
		}
		return strReason;

	}

	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-April-2012                               <Name>
	************************************************************************/
	
	public String createCustomViewNew(Selenium selenium, String[] strResourceName,
			String strRT, String strST) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			

			

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditCustomView.AddMoreResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources page is  displayed");
				
				

				int intCnt = 0;
			
				intCnt=strResourceName.length;
				log4j.info(intCnt);
				
				String[] strResourceType = new String[intCnt];
				
				for (int i = 0; i < intCnt; i++) {
					
					// Enter value for Name
					selenium.type(propElementDetails
							.getProperty("Pref.FindResources.Name"), strResourceName[i]);
					
					selenium.click(propElementDetails
							.getProperty("EditCustomView.FindRes.Search"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					try {
						strResourceType[i] = selenium
								.getText("//table[@id='tbl_resourceID']"
										+ "/tbody/tr/td[2][text()='"
										+ strResourceName[i] + "']/"
										+ "following-sibling::td[3]");
						log4j.info(intCnt);
						log4j.info(strResourceType[intCnt]);
					} catch (Exception e) {
					
					}

				}
				
				for (String s : strResourceName) {

					if (selenium
							.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
									+ s + "']/parent::tr/td/input") == false) {
						selenium
								.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ s + "']/parent::tr/td/input");

					}
				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.AddToCustomView"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				for (String s : strResourceType) {
					try {
						assertTrue(selenium
								.isElementPresent("//div/span[contains(text(),'"
										+ s + "')]"));
						log4j.info(" Resources is added to custom view ");

						selenium.click(propElementDetails
								.getProperty("EditCustomView.Options"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							assertEquals("Edit Custom View Options (Columns)",
									selenium.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j
									.info("Edit Custom View Options (Columns) page is  Displayed");

							if (selenium
									.isChecked("//div[@id='mainContainer']/form/div/div/b[text()='"
											+ strST
											+ "']/preceding-sibling::input[1]") == false) {

								selenium
										.click("//div[@id='mainContainer']/form/div/div/b[text()='"
												+ strST
												+ "']/preceding-sibling::input[1]");

							}
							selenium
									.click(propElementDetails
											.getProperty("EditCustomView.EditCustomViewOptions.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							try {
								assertEquals("Custom View - Table", selenium
										.getText(propElementDetails
												.getProperty("Header.Text")));

								try {
									assertTrue(selenium
											.isElementPresent("//th[text()"
													+ "='" + strRT
													+ "']/following-sibling"
													+ "::th/a[text()='" + strST
													+ "']"));

									log4j
											.info("Resource Type and status Type present in custom view table");

									try {

										for (String s1 : strResourceName) {
											assertTrue(selenium
													.isElementPresent("//td[@class='resourceName']"
															+ "/a[text()='"
															+ s1 + "']"));
											log4j
													.info("Resource Name present in custom view table");
										}

									} catch (AssertionError Ae) {
										strErrorMsg = "Resource Name NOT present in custom view table"
												+ Ae;
										log4j
												.info("Resource Name NOT present in custom view table"
														+ Ae);
									}

								} catch (AssertionError Ae) {
									strErrorMsg = "Resource Type and status Type NOT present in custom view table"
											+ Ae;
									log4j
											.info("Resource Type and status Type present in custom view table"
													+ Ae);
								}
							} catch (AssertionError Ae) {
								strErrorMsg = "Custom View - Table screen NOT displayed"
										+ Ae;
								log4j
										.info("Custom View - Table screen NOT displayed"
												+ Ae);
							}

						} catch (AssertionError Ae) {
							strErrorMsg = "Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae;
							log4j.info("Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae);
						}

					} catch (AssertionError Ae) {
						strErrorMsg = " Resources is NOT added to custom view "
								+ Ae;
						log4j.info(" Resources is NOT added to custom view "
								+ Ae);
						break;
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("create Custom View function failed" + e);
			strErrorMsg = "create Custom View function failed" + e;
		}
		return strErrorMsg;
	}
		
	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-April-2012                               <Name>
	************************************************************************/
	
	public String createCustomViewNewWitTablOrMapOption(Selenium selenium,
			String[] strResourceName, String strRT, String strST)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("EditCustomView.AddMoreResources")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			selenium.click(propElementDetails
					.getProperty("EditCustomView.AddMoreResources"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Find Resources", selenium
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
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources page is  displayed");

				intCnt = 0;

				intCnt = strResourceName.length;
				log4j.info(intCnt);

				String[] strResourceType = new String[intCnt];

				for (int i = 0; i < intCnt; i++) {

					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent(propElementDetails
									.getProperty("Pref.FindResources.Name")));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					// Enter value for Name
					selenium.type(propElementDetails
							.getProperty("Pref.FindResources.Name"),
							strResourceName[i]);

					selenium.click(propElementDetails
							.getProperty("EditCustomView.FindRes.Search"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("//table[@id='tbl_resourceID']"
										+ "/tbody/tr/td[2][text()='"
										+ strResourceName[i] + "']/"
										+ "following-sibling::td[3]"));
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
						strResourceType[i] = selenium
								.getText("//table[@id='tbl_resourceID']"
										+ "/tbody/tr/td[2][text()='"
										+ strResourceName[i] + "']/"
										+ "following-sibling::td[3]");
						log4j.info(intCnt);
						log4j.info(strResourceType[intCnt]);
					} catch (Exception e) {

					}

				}

				for (String s : strResourceName) {

					if (selenium
							.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
									+ s + "']/parent::tr/td/input") == false) {
						selenium
								.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ s + "']/parent::tr/td/input");

					}
				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.AddToCustomView"));
				selenium.waitForPageToLoad(gstrTimeOut);

				for (String s : strResourceType) {
					try {
						
						intCnt=0;
						do{
							try {

								assertTrue(selenium.isElementPresent("//div/span[contains(text(),'"
										+ s + "')]"));
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
								.isElementPresent("//div/span[contains(text(),'"
										+ s + "')]"));
						log4j.info(" Resources is added to custom view ");

						selenium.click(propElementDetails
								.getProperty("EditCustomView.Options"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
						intCnt=0;
						do{
							try {

								assertEquals("Edit Custom View Options (Columns)",
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
							assertEquals("Edit Custom View Options (Columns)",
									selenium.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j
									.info("Edit Custom View Options (Columns) page is  Displayed");

							
							intCnt=0;
							do{
								try {

									assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/div/div/b[text()='"
											+ strST
											+ "']/preceding-sibling::input[1]"));
									break;
								}catch(AssertionError Ae){
									Thread.sleep(1000);
									intCnt++;
								
								} catch (Exception Ae) {
									Thread.sleep(1000);
									intCnt++;
								}
							}while(intCnt<60);
							
							if (selenium
									.isChecked("//div[@id='mainContainer']/form/div/div/b[text()='"
											+ strST
											+ "']/preceding-sibling::input[1]") == false) {

								selenium
										.click("//div[@id='mainContainer']/form/div/div/b[text()='"
												+ strST
												+ "']/preceding-sibling::input[1]");

							}
							selenium
									.click(propElementDetails
											.getProperty("EditCustomView.EditCustomViewOptions.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);
							
							intCnt=0;
							do{
								try {

									assertTrue(selenium.isElementPresent(propElementDetails
											.getProperty("View.Custom.ShowTabl")));
									break;
								}catch(AssertionError Ae){
									Thread.sleep(1000);
									intCnt++;
								
								} catch (Exception Ae) {
									Thread.sleep(1000);
									intCnt++;
								}
							}while(intCnt<20);
							

							if (selenium.isElementPresent(propElementDetails
									.getProperty("View.Custom.ShowTabl"))) {
								selenium.click(propElementDetails
										.getProperty("View.Custom.ShowTabl"));
								selenium.waitForPageToLoad(gstrTimeOut);
							}

							
							
							intCnt=0;
							do{
								try {

									assertTrue(selenium.isElementPresent("//th[text()"
											+ "='" + strRT
											+ "']/following-sibling"
											+ "::th/a[text()='" + strST
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
							
							try {
								assertEquals("Custom View - Table", selenium
										.getText(propElementDetails
												.getProperty("Header.Text")));

								try {
									assertTrue(selenium
											.isElementPresent("//th[text()"
													+ "='" + strRT
													+ "']/following-sibling"
													+ "::th/a[text()='" + strST
													+ "']"));

									log4j
											.info("Resource Type and status Type present in custom view table");

									try {

										for (String s1 : strResourceName) {
											assertTrue(selenium
													.isElementPresent("//td[@class='resourceName']"
															+ "/a[text()='"
															+ s1 + "']"));
											log4j
													.info("Resource Name present in custom view table");
										}

									} catch (AssertionError Ae) {
										strErrorMsg = "Resource Name NOT present in custom view table"
												+ Ae;
										log4j
												.info("Resource Name NOT present in custom view table"
														+ Ae);
									}

								} catch (AssertionError Ae) {
									strErrorMsg = "Resource Type and status Type NOT present in custom view table"
											+ Ae;
									log4j
											.info("Resource Type and status Type present in custom view table"
													+ Ae);
								}
							} catch (AssertionError Ae) {
								strErrorMsg = "Custom View - Table screen NOT displayed"
										+ Ae;
								log4j
										.info("Custom View - Table screen NOT displayed"
												+ Ae);
							}

						} catch (AssertionError Ae) {
							strErrorMsg = "Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae;
							log4j.info("Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae);
						}

					} catch (AssertionError Ae) {
						strErrorMsg = " Resources is NOT added to custom view "
								+ Ae;
						log4j.info(" Resources is NOT added to custom view "
								+ Ae);
						break;
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("create Custom View WitTablOrMapOption function failed"
					+ e);
			strErrorMsg = "create Custom View WitTablOrMapOption function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/*************************************************************************
	 'Description :Verify event notifications in'My Event Notification 
	 '     Preferences' page  displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	  * @throws Exception 
	 ************************************************************************/

	public String verifyEventNotifInMyEventNotifPrefAndET(Selenium selenium,String strETName,
			String strTemplateValue, boolean blnEmail, boolean blnPager,
			boolean blnWebPage) throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[1][text()='"
								+ strETName + "']"));
				log4j.info("Event template '"+strETName+"' is displayed. ");
				
				if (blnEmail) {

					try {
						assertTrue(selenium.isChecked("css=input[value='"
								+ strTemplateValue + "'][name='emailInd']"));

						log4j.info("Email notification is Selected");

					} catch (AssertionError Aes) {
						log4j.info("Email notification is NOT Selected");
						strErrorMsg = "Email notification is NOT Selected";
					}

				} else {

					try {
						assertTrue(selenium.isChecked("css=input[value='"
								+ strTemplateValue + "'][name='emailInd']") == false);

						log4j.info("Email notification is NOT Selected");

					} catch (AssertionError Aes) {
						log4j.info("Email notification is Selected");
						strErrorMsg = "Email notification is Selected";

					}

				}

				if (blnPager) {

					try {
						assertTrue(selenium.isChecked("css=input[value='"
								+ strTemplateValue + "'][name='pagerInd']"));

						log4j.info("pager notification is Selected");

					} catch (AssertionError Aes) {
						log4j.info("pager notification is NOT Selected");
						strErrorMsg = "pager notification is NOT Selected";
					}

				} else {

					try {
						assertTrue(selenium.isChecked("css=input[value='"
								+ strTemplateValue + "'][name='pagerInd']") == false);

						log4j.info("pager notification is NOT Selected");

					} catch (AssertionError Aes) {
						log4j.info("pager notification is Selected");
						strErrorMsg = "pager notification is Selected";

					}

				}

				if (blnEmail) {

					try {
						assertTrue(selenium.isChecked("css=input[value='"
								+ strTemplateValue + "'][name='webInd']"));

						log4j.info("Web notification is Selected");

					} catch (AssertionError Aes) {
						log4j.info("Web notification is NOT Selected");
						strErrorMsg = "Web notification is NOT Selected";
					}

				} else {

					try {
						assertTrue(selenium.isChecked("css=input[value='"
								+ strTemplateValue + "'][name='webInd']") == false);

						log4j.info("Web notification is NOT Selected");

					} catch (AssertionError Aes) {
						log4j.info("Web notification is Selected");
						strErrorMsg = "Web notification is Selected";

					}

				}

			} catch (AssertionError Ae) {
				log4j.info("Event template '"+strETName+"' is NOT displayed. ");
				strErrorMsg ="Event template '"+strETName+"' is NOT displayed. ";
			}

			
		} catch (Exception e) {
			log4j
					.info("navigate to verifyEventNotifInMyEventNotifPref function failed"
							+ e);
			strErrorMsg = "navigate to verifyEventNotifInMyEventNotifPref function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	  /*******************************************************************************************
	  ' Description: Edit And Save FullName of UserInfo
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String verifyUserFullNameWhenLogin(Selenium selenium,String strFullName) throws Exception
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
	  			assertTrue(selenium.isTextPresent(strFullName));
	  			log4j.info("'Full name"+strFullName+"of the user is Displayed at the footer of the application. ");
	  		}
	  		catch (AssertionError Ae) {
	  			log4j.info("'Full name"+strFullName+"of the user is Displayed at the footer of the application. ");
	  			lStrReason ="'Full name"+strFullName+"of the user is Displayed at the footer of the application. ";
	  		}
	  	}catch(Exception e){
	  		log4j.info("editAndSaveFullNameUinfo function failed");
	  		lStrReason = "editAndSaveFullNameUinfo function failed";
	  	}

	  	return lStrReason;
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

	public String chkSTAssoOrNotInCustomViewTable(Selenium selenium,
			String strResource, String[] strStatTypeArr, boolean blnST,
			String strResourceVal, String strRtValue) {
		String strReason = "";
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnST) {
				try {

					for (int i = 0; i < strStatTypeArr.length; i++) {
						assertTrue(selenium
								.isElementPresent("//tr[starts-with(@class,'rtr_"
										+ strRtValue
										+ "_"
										+ strResourceVal
										+ "')]/td[text()='--']"));
						log4j.info(strStatTypeArr[i]
								+ "is associated for "
								+ strResource
								+ " -- is displayed in Custom View - Table screen.");
					}
				} catch (AssertionError ae) {
					for (int i = 0; i < strStatTypeArr.length; i++) {
						log4j.info(strStatTypeArr[i]
								+ "is NOT associated for "
								+ strResource
								+ " -- is displayed in Custom View - Table screen.");
						strReason = strStatTypeArr[i]
								+ "is NOT associated for " + strResource
								+ " -- is displayed in user view screen.";
					}
				}
			} else {
				try {

					for (int i = 0; i < strStatTypeArr.length; i++) {
						assertTrue(selenium
								.isElementPresent("//tr[starts-with(@class,'rtr_"
										+ strRtValue
										+ "_"
										+ strResourceVal
										+ "')]/td[text()='N/A']"));
						log4j.info(strStatTypeArr[i]
								+ "is NOT associated  for "
								+ strResource
								+ "N/A is displayed in Custom View - Table screen.");
					}
				} catch (AssertionError ae) {
					for (int i = 0; i < strStatTypeArr.length; i++) {
						log4j.info(strStatTypeArr[i]
								+ "is associated for "
								+ strResource
								+ "N/A is NOT displayed in Custom View - Table screen.");
						strReason = strStatTypeArr[i]
								+ "is associated  for "
								+ strResource
								+ "N/A is NOT displayed in Custom View - Table screen.";
					}
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:navigating find resource page
	'Precondition	:None
	'Arguments		:selenium,
	'Returns		:String
	'Date	 		:25-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-April-2012                               <Name>
	************************************************************************/

	public String navToFindResPageBySearchLink(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();		
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("link=Search");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources Screen is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources  Screen is NOT displayed" + Ae;
				log4j.info("Find Resources  Screen is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToFindResPage function failed" + e);
			strErrorMsg = "navToFindResPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-Sep-2012                               <Name>
	************************************************************************/
	
	public String addResourcesAndNavToEditSTNotfPreferences(Selenium selenium,
			String strSearchRSName, String strResourceValue) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("My Status Change Preferences page is  displayed");

				selenium.click(propElementDetails
						.getProperty("Preferences.StatusChangePref.Add"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				int intCnt=0;
				do{
					try {

						assertEquals("Find Resources", selenium
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
					assertEquals("Find Resources", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Find Resources page is  displayed");
					
					intCnt = 0;
					do {
						try {

							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("Pref.FindResources.Name")));
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCnt++;

						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					} while (intCnt < 60);
					

					// Search criteria
					selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strSearchRSName);
					selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='resourceID'][value='"
									+ strResourceValue + "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);

					if (selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strResourceValue + "']") == false) {
						selenium.click("css=input[name='resourceID'][value='"
								+ strResourceValue + "']");
					}

					selenium.click(propElementDetails.getProperty("StatusChangePrefs.FindRes.Notifications"));
					selenium.waitForPageToLoad(gstrTimeOut);

					
					intCnt=0;
					do{
						try {

							assertEquals("Edit My Status Change Preferences",
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
						assertEquals("Edit My Status Change Preferences",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j
								.info("Edit My Status Change Preferences page is  displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
								+ Ae;
						log4j
								.info("Edit My Status Change Preferences page is NOT displayed"
										+ Ae);
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Find Resources page is NOT displayed" + Ae;
					log4j.info("Find Resources page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("addResourcesAndNavToEditSTNotfPreferences function failed" + e);
			strErrorMsg = "addResourcesAndNavToEditSTNotfPreferences function failed" + e;
		}
		return strErrorMsg;
	}

	

	/***********************************************************************
	'Description	:Verify Status types in particular section in Edit My Status Change Preferences page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-Sep-2012                               <Name>
	************************************************************************/
	
	public String verifySTInSectionInEditMySTPrfPage(Selenium selenium,
			String strSectionValue, String strSectionName, String[] strSTName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
		

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				try {
					assertTrue(selenium.isElementPresent("//table[@id='stt_"
							+ strSectionValue + "']"
							+ "/thead/tr/th[2][text()='" + strSectionName
							+ "']"));
					log4j.info("Section " + strSectionName + " is Present");

					for (String s : strSTName) {

						try {

							assertTrue(selenium
									.isElementPresent("//table[@id='stt_"
											+ strSectionValue + "']"
											+ "/thead/tr/th[2][text()='"
											+ strSectionName + "']/ancestor::"
											+ "table/tbody/tr/td/div[text()='"
											+ s + "']"));

							log4j
									.info("	"
											+ s
											+ " is displayed under section "
											+ strSectionName
											+ " in"
											+ " the 'Edit My Status Change Preferences' screen. ");

						} catch (AssertionError Ae) {
							strErrorMsg = strErrorMsg+"	"
									+ s
									+ " is NOT displayed under section "
									+ strSectionName
									+ " "
									+ "in the 'Edit My Status Change Preferences' screen.";
									
							log4j
									.info("	"
											+ s
											+ " is NOT  are displayed under section"
											+ strSectionName
											+ " in"
											+ " the 'Edit My Status Change Preferences' screen. "
											+ Ae);
						}
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Section " + strSectionName
							+ " is NOT Present";
					log4j.info("Section " + strSectionName + " is NOT Present"
							+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("verifySTInSectionInEditMySTPrfPage function failed"
							+ e);
			strErrorMsg = "verifySTInSectionInEditMySTPrfPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/
	
	public String selectSTChangeNotifInEditMySTNotifPage(Selenium selenium,
			String strSearchRSName, String strResourceValue, String strSTValue,String strSTName,
			boolean blnEmail, boolean blnPager, boolean blnWeb)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertEquals("Edit My Status Change Preferences", selenium
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
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='emailInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|N|A']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

				}
				

				if (blnPager) {
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='pagerInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|N|A']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

				}

				if (blnWeb) {
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='webInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|N|A']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);	
				
				intCnt=0;
				do{
					try {

						assertEquals("My Status Change Preferences", selenium
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
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

					
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[text()='"
									+ strSearchRSName + "']"));
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
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}
					
					
					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[2]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[3]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					
					if (blnWeb) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[4]"), "X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}
					}
				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifInEditMySTNotifPage function failed"
					+ e);
			strErrorMsg = "selectSTChangeNotifInEditMySTNotifPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/
	
	public String selectSTChangeNotifInEditMySTNotifPageBelow(Selenium selenium,
			String strSearchRSName, String strResourceValue, String strSTValue,String strSTName,
			boolean blnEmail, boolean blnPager, boolean blnWeb)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertEquals("Edit My Status Change Preferences", selenium
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
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='emailInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|N|B']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");

				}
				

				if (blnPager) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='pagerInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|N|B']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");

				}

				if (blnWeb) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='webInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|N|B']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");

				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.EditCustomViewOptions.Save"));
		        selenium.waitForPageToLoad(gstrTimeOut);	
		        
				intCnt=0;
				do{
					try {

						assertEquals("My Status Change Preferences", selenium
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
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

					intCnt=0;
					do{
						try {

							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/"
											+ "table/tbody/tr/td[text()='"
											+ strSearchRSName + "']"));
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
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}
					
					
					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"+strSTName+"']/parent" +
											"::tr/following-sibling::tr[@class='even']/td[5]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"+strSTName+"']/parent" +
											"::tr/following-sibling::tr[@class='even']/td[6]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					
					if (blnWeb) {
						try {

							assertEquals(
									selenium
											.getText("//table/tbody/tr/td[text()='"
													+ strSTName
													+ "']/parent"
													+ "::tr/following-sibling::tr[@class='even']/td[7]"),
									"X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}
					
					
					

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifInEditMySTNotifPageBelow function failed"
					+ e);
			strErrorMsg = "selectSTChangeNotifInEditMySTNotifPageBelow function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:find resources and add to custom view
	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String RetriveResourceInAddToCustomView(Selenium selenium,String strResource,String strCategory,
			String strRegion,String strCityZipCd,String strState,boolean blnAdd){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				
			selenium.selectWindow("");
			selenium.selectFrame("Data");
				//Enter value for Name
				selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strResource);
				//Enter for category
				selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="+strCategory);
				if(selenium.isElementPresent(propElementDetails.getProperty("Pref.FindResources.Region"))){
					//Select region 
					selenium.select(propElementDetails.getProperty("Pref.FindResources.Region"), "label="+strRegion);
				}
				//Enter city, county or zip
				selenium.type(propElementDetails.getProperty("Pref.FindResources.CityZip"), strCityZipCd);
				//Select the state
				selenium.select(propElementDetails.getProperty("Pref.FindResources.State"), "label="+strState);
				
				//click on search button
				selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']"));
					log4j.info("Resource "+strResource+" is Retrived");
					
					if(blnAdd){
					//select the resource
					selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']/parent::tr/td[1]/input");
					//click on Add To Custom View
					selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.AddToCustomView"));
					selenium.waitForPageToLoad(gstrTimeOut);
					}
					
				} catch (AssertionError Ae) {
					log4j.info("Resource "+strResource+" is NOT found");
					strReason="Resource "+strResource+" is NOT found";
				}
			
		} catch (Exception e) {
			log4j.info("findResourcesAndAddToCustomView function failed" + e);
			strReason = "findResourcesAndAddToCustomView function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:4-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-Oct-2012                               <Name>
	************************************************************************/
	
	public String createCustomViewWithRSAndST(Selenium selenium,
			String[] strResourceName, String strRT, String strST,
			boolean blnRS, boolean blnST) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnRS) {

				selenium.click(propElementDetails
						.getProperty("EditCustomView.AddMoreResources"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Find Resources", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Find Resources page is  displayed");

					int intCnt = 0;

					intCnt = strResourceName.length;
					log4j.info(intCnt);

					String[] strResourceType = new String[intCnt];

					for (int i = 0; i < intCnt; i++) {

						// Enter value for Name
						selenium.type(propElementDetails
								.getProperty("Pref.FindResources.Name"),
								strResourceName[i]);

						selenium.click(propElementDetails
								.getProperty("EditCustomView.FindRes.Search"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							strResourceType[i] = selenium
									.getText("//table[@id='tbl_resourceID']"
											+ "/tbody/tr/td[2][text()='"
											+ strResourceName[i] + "']/"
											+ "following-sibling::td[3]");
							log4j.info(intCnt);
							log4j.info(strResourceType[intCnt]);
						} catch (Exception e) {
							

						}

					}

					for (String s : strResourceName) {

						if (selenium
								.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ s + "']/parent::tr/td/input") == false) {
							selenium
									.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
											+ s + "']/parent::tr/td/input");

						}
					}

					selenium
							.click(propElementDetails
									.getProperty("EditCustomView.FindRes.AddToCustomView"));
					selenium.waitForPageToLoad(gstrTimeOut);

					for (String s : strResourceType) {
						try {
							assertTrue(selenium
									.isElementPresent("//div/span[contains(text(),'"
											+ s + "')]"));
							log4j.info(" Resources is added to custom view ");
						} catch (AssertionError Ae) {
							strErrorMsg = " Resources is NOT added to custom view "
									+ Ae;
							log4j
									.info(" Resources is NOT added to custom view "
											+ Ae);

						}
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Find Resources page is NOT displayed" + Ae;
					log4j.info("Find Resources page is NOT displayed" + Ae);
				}

			}

			if (blnST) {

				selenium.click(propElementDetails
						.getProperty("EditCustomView.Options"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Edit Custom View Options (Columns)", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j
							.info("Edit Custom View Options (Columns) page is  Displayed");

					if (selenium
							.isChecked("//div[@id='mainContainer']/form/div/div/b[text()='"
									+ strST + "']/preceding-sibling::input[1]") == false) {

						selenium
								.click("//div[@id='mainContainer']/form/div/div/b[text()='"
										+ strST
										+ "']/preceding-sibling::input[1]");

					}
					selenium
							.click(propElementDetails
									.getProperty("EditCustomView.EditCustomViewOptions.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Custom View - Table", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						try {
							assertTrue(selenium.isElementPresent("//th[text()"
									+ "='" + strRT + "']/following-sibling"
									+ "::th/a[text()='" + strST + "']"));

							log4j
									.info("Resource Type and Source Type present in custom view table");

							try {

								for (String s1 : strResourceName) {
									assertTrue(selenium
											.isElementPresent("//td[@class='resourceName']"
													+ "/a[text()='" + s1 + "']"));
									log4j
											.info("Resource Name present in custom view table");
								}

							} catch (AssertionError Ae) {
								strErrorMsg = "Resource Name NOT present in custom view table"
										+ Ae;
								log4j
										.info("Resource Name NOT present in custom view table"
												+ Ae);
							}

						} catch (AssertionError Ae) {
							strErrorMsg = "Resource Type and Source Type NOT present in custom view table"
									+ Ae;
							log4j
									.info("Resource Type and Source Type present in custom view table"
											+ Ae);
						}
					} catch (AssertionError Ae) {
						strErrorMsg = "Custom View - Table screen NOT displayed"
								+ Ae;
						log4j.info("Custom View - Table screen NOT displayed"
								+ Ae);
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Edit Custom View Options (Columns)"
							+ " page is NOT Displayed" + Ae;
					log4j.info("Edit Custom View Options (Columns)"
							+ " page is NOT Displayed" + Ae);
				}

			}

		} catch (Exception e) {
			log4j.info("createCustomViewWithRSAndST function failed" + e);
			strErrorMsg = "createCustomViewWithRSAndST function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:4-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-Oct-2012                               <Name>
	************************************************************************/
	
	public String createCustomViewWithRSAndSTCustMapOrViewTable(
			Selenium selenium, String[] strResourceName, String strRT,
			String strST, boolean blnRS, boolean blnST) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnRS) {

				selenium.click(propElementDetails
						.getProperty("EditCustomView.AddMoreResources"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Find Resources", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Find Resources page is  displayed");

					int intCnt = 0;

					intCnt = strResourceName.length;
					log4j.info(intCnt);

					String[] strResourceType = new String[intCnt];

					for (int i = 0; i < intCnt; i++) {

						// Enter value for Name
						selenium.type(propElementDetails
								.getProperty("Pref.FindResources.Name"),
								strResourceName[i]);

						selenium.click(propElementDetails
								.getProperty("EditCustomView.FindRes.Search"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							strResourceType[i] = selenium
									.getText("//table[@id='tbl_resourceID']"
											+ "/tbody/tr/td[2][text()='"
											+ strResourceName[i] + "']/"
											+ "following-sibling::td[3]");
							log4j.info(intCnt);
							log4j.info(strResourceType[intCnt]);
						} catch (Exception e) {

						}

					}

					for (String s : strResourceName) {

						if (selenium
								.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ s + "']/parent::tr/td/input") == false) {
							selenium
									.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
											+ s + "']/parent::tr/td/input");

						}
					}

					selenium
							.click(propElementDetails
									.getProperty("EditCustomView.FindRes.AddToCustomView"));
					selenium.waitForPageToLoad(gstrTimeOut);

					for (String s : strResourceType) {
						try {
							assertTrue(selenium
									.isElementPresent("//div/span[contains(text(),'"
											+ s + "')]"));
							log4j.info(" Resources is added to custom view ");
						} catch (AssertionError Ae) {
							strErrorMsg = " Resources is NOT added to custom view "
									+ Ae;
							log4j
									.info(" Resources is NOT added to custom view "
											+ Ae);

						}
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Find Resources page is NOT displayed" + Ae;
					log4j.info("Find Resources page is NOT displayed" + Ae);
				}

			}

			if (blnST) {

				selenium.click(propElementDetails
						.getProperty("EditCustomView.Options"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Edit Custom View Options (Columns)", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j
							.info("Edit Custom View Options (Columns) page is  Displayed");

					if (selenium
							.isChecked("//div[@id='mainContainer']/form/div/div/b[text()='"
									+ strST + "']/preceding-sibling::input[1]") == false) {

						selenium
								.click("//div[@id='mainContainer']/form/div/div/b[text()='"
										+ strST
										+ "']/preceding-sibling::input[1]");

					}
					selenium
							.click(propElementDetails
									.getProperty("EditCustomView.EditCustomViewOptions.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					if (selenium.isElementPresent(propElementDetails
							.getProperty("View.Custom.ShowTabl"))) {
						selenium.click(propElementDetails
								.getProperty("View.Custom.ShowTabl"));
						selenium.waitForPageToLoad(gstrTimeOut);
					}

					try {
						assertEquals("Custom View - Table", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						try {
							assertTrue(selenium.isElementPresent("//th[text()"
									+ "='" + strRT + "']/following-sibling"
									+ "::th/a[text()='" + strST + "']"));

							log4j
									.info("Resource Type and Source Type present in custom view table");

							try {

								for (String s1 : strResourceName) {
									assertTrue(selenium
											.isElementPresent("//td[@class='resourceName']"
													+ "/a[text()='" + s1 + "']"));
									log4j
											.info("Resource Name present in custom view table");
								}

							} catch (AssertionError Ae) {
								strErrorMsg = "Resource Name NOT present in custom view table"
										+ Ae;
								log4j
										.info("Resource Name NOT present in custom view table"
												+ Ae);
							}

						} catch (AssertionError Ae) {
							strErrorMsg = "Resource Type and Source Type NOT present in custom view table"
									+ Ae;
							log4j
									.info("Resource Type and Source Type present in custom view table"
											+ Ae);
						}
					} catch (AssertionError Ae) {
						strErrorMsg = "Custom View - Table screen NOT displayed"
								+ Ae;
						log4j.info("Custom View - Table screen NOT displayed"
								+ Ae);
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Edit Custom View Options (Columns)"
							+ " page is NOT Displayed" + Ae;
					log4j.info("Edit Custom View Options (Columns)"
							+ " page is NOT Displayed" + Ae);
				}

			}

		} catch (Exception e) {
			log4j
					.info("createCustomViewWithRSAndSTCustMapOrViewTable function failed"
							+ e);
			strErrorMsg = "createCustomViewWithRSAndSTCustMapOrViewTable function failed"
					+ e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify Status types in particular section in Edit My Status Change Preferences page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:4-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-Oct-2012                               <Name>
	************************************************************************/
	
	public String verifySTInSectionInEditCustomViwOptionPage(Selenium selenium,
			String strRegnName, String strSectionValue, String strSectionName,
			String[] strSTName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium.isElementPresent("//div[@class='region']/"
						+ "span[text()='" + strRegnName
						+ "']/following-sibling"
						+ "::div[@class='group']/span[text()='"
						+ strSectionName + "']"));

				log4j.info("Section " + strSectionName
						+ " is Present in Edit custom view option page");

				for (String s : strSTName) {

					try {

						assertTrue(selenium
								.isElementPresent("//div[@class='region']"
										+ "/span[text()='"
										+ strRegnName
										+ "']"
										+ "/following-sibling::div[@class='group']"
										+ "/span[text()='" + strSectionName
										+ "']/"
										+ "following-sibling::b[text()='" + s
										+ "']"));

						log4j.info("	" + s + " is displayed under section "
								+ strSectionName + " in"
								+ " the 'Edit custom view option' screen. ");

					} catch (AssertionError Ae) {
						strErrorMsg = "	" + s
								+ " is NOT displayed under section "
								+ strSectionName + " "
								+ "in the 'Edit custom view option' screen. "
								+ Ae;
						log4j.info("	" + s
								+ " is NOT  are displayed under section"
								+ strSectionName + " in"
								+ " the 'Edit custom view option' screen. "
								+ Ae);
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Section " + strSectionName + " is NOT Present";
				log4j.info("Section " + strSectionName
						+ " is NOT Present in Edit custom view option page"
						+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("verifySTInSectionInEditCustomViwOptionPage function failed"
							+ e);
			strErrorMsg = "verifySTInSectionInEditCustomViwOptionPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:4-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-Oct-2012                               <Name>
	************************************************************************/
	
	public String navViewCustomTableFromEditCustomViewOptionPage(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("StatusChangePrefs.FindRes.Notifications"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Custom View - Table", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Custom View - Table page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Custom View - Table page is NOT displayed" + Ae;
				log4j.info("Custom View - Table page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navViewCustomTableFromEditCustomViewOptionPage "
					+ "function failed" + e);
			strErrorMsg = "navViewCustomTableFromEditCustomViewOptionPage"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}

	/***************************************************************
    'Description :Verify Updated status value is displayed in custom view screen with comments
    'Precondition :None
    'Arguments  :selenium,strResource,strST,strUpdateValue,strComment
    'Returns  :strReason
    'Date    :05-10-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdateSTValInCustomView(Selenium selenium, 
			String strST, String strUpdateValue,String strPos) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
		
			try {
				assertEquals(selenium
						.getText("//table[starts-with(@id,'rgt')]"
								+ "/thead/tr/th/a[text()='"
								+ strST
								+ "']/ancestor::table/tbody/tr/td["+strPos+"]"),
						strUpdateValue);
				
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " is displayed for the status type "+strST);
			} catch (AssertionError Ae) {
				log4j
				.info("The status value "
						+ strUpdateValue
						+ " is NOT displayed for the status type "+strST);
				strReason = "The status value "
					+ strUpdateValue
					+ " is NOT displayed for the status type "+strST;

			}	

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdateSTValInCustomView "
					+ e.toString();
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify status value is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:17-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'17-Oct-2012                               <Name>
	************************************************************************/

	public String provideSTAboveBelowRangeInEditMySTNotifPage(
			Selenium selenium, String strAbove, String strBelow,
			String strResourceValue, String strSTValue, boolean blnAbove,
			boolean blnBelow) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertEquals("Edit My Status Change Preferences", selenium
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
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnAbove) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[type='text'][name='above"
									+ strResourceValue + "|" + strSTValue + "|N|A']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					selenium.type("css=input[type='text'][name='above"
							+ strResourceValue + "|" + strSTValue + "|N|A']",
							strAbove);

				}

				if (blnBelow) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[type='text'][name='below"
									+ strResourceValue + "|" + strSTValue + "|N|B']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					selenium.type("css=input[type='text'][name='below"
							+ strResourceValue + "|" + strSTValue + "|N|B']",
							strBelow);

				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.EditCustomViewOptions.Save"));
		        selenium.waitForPageToLoad(gstrTimeOut);
		        
		        intCnt=0;
				do{
					try {

						assertEquals("My Status Change Preferences", selenium
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
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("provideSTAboveBelowRangeInEditMySTNotifPage function failed"
							+ e);
			strErrorMsg = "provideSTAboveBelowRangeInEditMySTNotifPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify status value is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:17-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'17-Oct-2012                               <Name>
	************************************************************************/
	
	public String navEditMySatPrefPgeOfPartcularRS(Selenium selenium,String strResourceValue)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//table/tbody/tr/td/a[contains(@onclick,\"editNotifications('"
							+ strResourceValue + "')\")]"));
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
					.click("//table/tbody/tr/td/a[contains(@onclick,\"editNotifications('"
							+ strResourceValue + "')\")]");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt=0;
			do{
				try {

					assertEquals("Edit My Status Change Preferences", selenium
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
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");


			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navEditMySatPrefPgeOfPaertcularRS function failed"
					+ e);
			strErrorMsg = "navEditMySatPrefPgeOfPaertcularRS function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify status value is added in status type preference page is deleted
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:17-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'17-Oct-2012                               <Name>
	************************************************************************/
	
	public String deleteStaPrefOfPartcularRS(Selenium selenium,String strResourceValue,String strSearchRSName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//table/tbody/tr/td/a[contains(@onclick,\"deleteNotifications('"
							+ strResourceValue + "')\")]");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.getConfirmation().matches("^Are you sure you" +
						" would like to delete all status notification preferences" +
						" for the selected Resource[\\s\\S]$"));
				log4j
						.info("	A confirmation window is displayed with the " +
								"message 'Are you sure you would like to " +
								"delete all status change notification " +
								"preferences for the selected resource?' ");

				
				try {
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");
					
					try {
						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is NOT displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is  displayed" + Ae;
						log4j.info("Resource Name is  displayed" + Ae);
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}
				
				
				
				

			} catch (AssertionError Ae) {
				strErrorMsg = "	A confirmation window is displayed with the message" +
						" 'Are you sure you would like to delete all status change " +
						"notification preferences for the selected resource?' "
						+ Ae;
				log4j
						.info("	A confirmation window is NOT displayed with the message " +
								"'Are you sure you would like to delete all status " +
								"change notification preferences for the selected resource?' "
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("deleteStaPrefOfPartcularRS function failed"
					+ e);
			strErrorMsg = "deleteStaPrefOfPartcularRS function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:Verify status value is retained in edit my  preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:17-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'17-Oct-2012                               <Name>
	************************************************************************/

	public String verifyProvideSTAboveBelowRangeInEditMySTNotifPageisRetained(
			Selenium selenium, String strAbove, String strBelow,
			String strResourceValue, String strSTValue, boolean blnAbove,
			boolean blnBelow) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnAbove) {
					try{
						assertEquals(selenium.getValue("css=input[type='text'][name='above"
							+ strResourceValue + "|" + strSTValue + "|N|A']"),strAbove);
						log4j.info("The edited data in above textbox is retained ");
					}catch(AssertionError Ae){
						log4j.info("The edited data in above textbox is NOT retained ");
						strErrorMsg="The edited data in above textbox is NOT retained ";
					}
				

				}
				
				if (blnBelow) {
					try{
						assertEquals(selenium.getValue("css=input[type='text'][name='below"
							+ strResourceValue + "|" + strSTValue + "|N|B']"),strBelow);
						log4j.info("The edited data in below textbox is retained ");
					}catch(AssertionError Ae){
						log4j.info("The edited data in below textbox is NOT retained ");
						strErrorMsg=strErrorMsg+"The edited data in below textbox is NOT retained ";
					}
				

				}

				
				selenium.click(propElementDetails
						.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("provideSTAboveBelowRangeInEditMySTNotifPage function failed"
							+ e);
			strErrorMsg = "provideSTAboveBelowRangeInEditMySTNotifPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/
	
	public String selectSTChangeNotifInEditMySTNotifPageOfMST(
			Selenium selenium, String strSearchRSName, String strResourceValue,
			String strSTValue, String strSTName, String strStatusValue,
			boolean blnEmail, boolean blnPager, boolean blnWeb)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt=0;
			do{
				try {

					assertEquals("Edit My Status Change Preferences", selenium
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
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='emailInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|S|"
									+ strStatusValue + "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");

				}

				if (blnPager) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='pagerInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|S|"
									+ strStatusValue + "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");

				}

				if (blnWeb) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("css=input[name='webInd'][value='"
									+ strResourceValue + "|" + strSTValue + "|S|"
									+ strStatusValue + "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");

				}

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt=0;
				do{
					try {

						assertEquals("My Status Change Preferences", selenium
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
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[text()='"
									+ strSearchRSName + "']"));
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
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}

					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[2]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[3]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnWeb) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[4]"), "X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifInEditMySTNotifPage function failed"
					+ e);
			strErrorMsg = "selectSTChangeNotifInEditMySTNotifPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/
	
	public String selectSTChangeNotifInEditMySTNotifPageNew(Selenium selenium,
			String strSearchRSName, String strResourceValue, String strSTValue,String strSTName,
			boolean blnEmail, boolean blnPager, boolean blnWeb)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

				}else{
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") )
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

				}
				

				if (blnPager) {
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

				}else{
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']"))
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

					}

				if (blnWeb) {
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

				}else{
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|A']") )
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|A']");

					
				}

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt=0;
				do{
					try {
						assertEquals("My Status Change Preferences", selenium
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
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

					
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}
					
					
					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[2]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[3]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					
					if (blnWeb) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[4]"), "X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					
					

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifInEditMySTNotifPageNew function failed"
					+ e);
			strErrorMsg = "selectSTChangeNotifInEditMySTNotifPageNew function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/
	
	public String selectSTChangeNotifInEditMySTNotifPageBelowNew(Selenium selenium,
			String strSearchRSName, String strResourceValue, String strSTValue,String strSTName,
			boolean blnEmail, boolean blnPager, boolean blnWeb)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");

				}else{
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']"))
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");
				}
				

				if (blnPager) {
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");

				}else{
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']") )
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");
				}

				if (blnWeb) {
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");

				}else{
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|N|B']"))
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|N|B']");
				}

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

					
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}
					
					
					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"+strSTName+"']/parent" +
											"::tr/following-sibling::tr[@class='even']/td[5]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"+strSTName+"']/parent" +
											"::tr/following-sibling::tr[@class='even']/td[6]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					
					if (blnWeb) {
						try {

							assertEquals(
									selenium
											.getText("//table/tbody/tr/td[text()='"
													+ strSTName
													+ "']/parent"
													+ "::tr/following-sibling::tr[@class='even']/td[7]"),
									"X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}
					
					
					

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifInEditMySTNotifPageBelowNew function failed"
					+ e);
			strErrorMsg = "selectSTChangeNotifInEditMySTNotifPageBelowNew function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	

	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/
	
	public String selectSTChangeNotifInEditMySTNotifPageOfMSTNew(
			Selenium selenium, String strSearchRSName, String strResourceValue,
			String strSTValue, String strSTName, String strStatusValue,
			boolean blnEmail, boolean blnPager, boolean blnWeb)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");

				}else{
					
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']"))
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");
					
				}

				if (blnPager) {
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");

				}else{
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']"))
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");

				}

				if (blnWeb) {
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");

				}else{
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|S|"
							+ strStatusValue + "']"))
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|S|"
								+ strStatusValue + "']");
				}

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}

					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[2]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[3]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnWeb) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[4]"), "X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifInEditMySTNotifPageOfMSTNew function failed"
					+ e);
			strErrorMsg = "selectSTChangeNotifInEditMySTNotifPageOfMSTNew function failed"
					+ e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:find resources and add to custom view
	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String findResourcesInAddMoreRes(Selenium selenium,String strResource,String strCategory,String strRegion,String strCityZipCd,String strState){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//click on Add More Resource button
			selenium.click(propElementDetails.getProperty("EditCustomView.AddMoreResources"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try{
				assertEquals("Find Resources",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Find Resources page is displayed" );
				
				//Enter value for Name
				selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strResource);
				//Enter for category
				selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="+strCategory);
				if(selenium.isElementPresent(propElementDetails.getProperty("Pref.FindResources.Region"))){
					//Select region 
					selenium.select(propElementDetails.getProperty("Pref.FindResources.Region"), "label="+strRegion);
				}
				//Enter city, county or zip
				selenium.type(propElementDetails.getProperty("Pref.FindResources.CityZip"), strCityZipCd);
				//Select the state
				selenium.select(propElementDetails.getProperty("Pref.FindResources.State"), "label="+strState);
				
				//click on search button
				selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);
		
			} catch (AssertionError Ae) {
				strReason = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}
			
			
		} catch (Exception e) {
			log4j.info("findResourcesInAddMoreRes function failed" + e);
			strReason = "findResourcesInAddMoreRes function failed" + e;
		}
		return strReason;
	}

	/***********************************************************************
	'Description	:find resources and add to custom view
	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String AddResourceToCustomView(Selenium selenium,String strResource,boolean blnAdd){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
						
			try {
				try{
					assertTrue(selenium.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']"));
					log4j.info("Resource "+strResource+" is Retrived");
				} catch (AssertionError Ae) {
					log4j.info("Resource "+strResource+" is NOT Retrived");
					strReason="Resource "+strResource+" is NOT Retrived";
					
				}
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
						+ strResource + "']/parent::tr/td[1]/input");
				Thread.sleep(1000);
				
				if(blnAdd){
				// click on Add To Custom View
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.AddToCustomView"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Edit Custom View", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("Edit Custom View page is  displayed");

				} catch (AssertionError Ae) {
					strReason = "Edit Custom View page is NOT displayed" + Ae;
					log4j.info("Edit Custom View page is NOT displayed" + Ae);
				}
				}
			} catch (AssertionError Ae) {
				log4j.info("Resource " + strResource + " is NOT found");
				strReason = "Resource " + strResource + " is NOT found";
			}
			
		} catch (Exception e) {
			log4j.info("AddResourceToCustomView function failed" + e);
			strReason = "AddResourceToCustomView function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify status value is retained in edit my  preference page
	'Precondition	:None
	'Arguments		:selenium,strAbove,strBelow,strResourceValue,strSTValue,blnAbove,blnBelow
	'Returns		:String
	'Date	 		:31-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'31-Oct-2012                               <Name>
	************************************************************************/

	public String verifyProvideSTAboveBelowRangeInEditMySTNotifPageisRetainedOfMST(
			Selenium selenium, String strAbove, String strBelow,
			boolean blnAbove, boolean blnBelow) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnAbove) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'stt')]"
										+ "/tbody/tr/td/table/tbody/tr[1]/td[text()='"
										+ strAbove + "']"));
						log4j
								.info("The edited data in above Field OF MST  is retained ");
					} catch (AssertionError Ae) {
						log4j
								.info("The edited data in above Field OF MST is NOT retained ");
						strErrorMsg = "The edited data in above Field OF MST is NOT retained ";
					}

				}

				if (blnBelow) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'stt')]"
										+ "/tbody/tr/td/table/tbody/tr[2]/td[text()='"
										+ strBelow + "']"));
						log4j
								.info("The edited data in below Field OF MST is retained ");
					} catch (AssertionError Ae) {
						log4j
								.info("The edited data in below Field OF MST is NOT retained ");
						strErrorMsg = strErrorMsg
								+ "The edited data in below Field OF MST is NOT retained ";
					}

				}

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("verifyProvideSTAboveBelowRangeInEditMySTNotifPageisRetainedOfMST function failed"
							+ e);
			strErrorMsg = "verifyProvideSTAboveBelowRangeInEditMySTNotifPageisRetainedOfMST function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	
	
	/***********************************************************************
	'Description	:Verify Status types in particular section in Edit My Status Change Preferences page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:31-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'31-Oct-2012                               <Name>
	************************************************************************/
	
	public String verifySTInInEditMySTPrfPageInUncategorizedSection(
			Selenium selenium, String[] strSTName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'stt_')]"
									+ "/thead/tr/th[2][text()='Uncategorized']"));
					log4j.info("Section Uncategorized is Present");

					for (String s : strSTName) {

						try {

							assertTrue(selenium
									.isElementPresent("//table[starts-with(@id,'stt_')]"
											+ "/thead/tr/th[2][text()='Uncategorized']/ancestor::"
											+ "table/tbody/tr/td/div[text()='"
											+ s + "']"));

							log4j
									.info("	"
											+ s
											+ " is displayed under section Uncategorized in"
											+ " the 'Edit My Status Change Preferences' screen. ");

						} catch (AssertionError Ae) {
							strErrorMsg = "	"
									+ s
									+ " is NOT displayed under section Uncategorized "
									+ "in the 'Edit My Status Change Preferences' screen. "
									+ Ae;
							log4j
									.info("	"
											+ s
											+ " is NOT  are displayed under section Uncategorized in"
											+ " the 'Edit My Status Change Preferences' screen. "
											+ Ae);
						}
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Section Uncategorized is NOT Present" + Ae;
					log4j.info("Section Uncategorized is NOT Present" + Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("verifySTInInEditMySTPrfPageInUncategorizedSection function failed"
							+ e);
			strErrorMsg = "verifySTInInEditMySTPrfPageInUncategorizedSection function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Error Message in cusotm view table for user
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:5-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'5-Nov-2012                               <Name>
	************************************************************************/

	public String varErrorMsgInCustomViewTableNew(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));

			selenium.click(propElementDetails.getProperty("View.CustomLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("No Statuses in Custom View",
						selenium.getText("css=#viewContainer > h1"));
				assertEquals(
						"There are no statuses (columns) to display in your custom view.",
						selenium.getText("css=p"));
				assertEquals(
						"Click here to add status columns to your custom view",
						selenium.getText("link=Click here to add status columns to your custom view"));

				log4j.info("'No Statuses in Custom View"
						+ "There are no statuses (columns) to display in your "
						+ "custom view.Click here to add status columns to your"
						+ " custom view.' message is displayed.RS is displayed. ");

			} catch (AssertionError Ae) {
				strErrorMsg = "'No Statuses in Custom View"
						+ "There are no statuses (columns) to display in your "
						+ "custom view.Click here to add status columns to your"
						+ " custom view.' message is displayed.RS is NOT displayed. ";
				log4j.info("'No Statuses in Custom View"
						+ "There are no statuses (columns) to display in your "
						+ "custom view.Click here to add status columns to your"
						+ " custom view.' message is displayed.RS is NOT displayed. ");
			}

		} catch (Exception e) {
			log4j.info("varErrorMsgInCustomViewTableNew" + "function failed"
					+ e);
			strErrorMsg = "varErrorMsgInCustomViewTableNew"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	 /***********************************************************************
	'Description	: check status types in Edit My status change preferences
	'Precondition	:None
	'Arguments		:selenium,strStatusType,strNotifData
					
	'Returns		:String
	'Date	 		:13-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String checkStatTypeInEditMyStatChangePrefsNew(Selenium selenium,
			String strStatusType, String strNotifData[][]) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + strStatusType
								+ "']"));

				for (int intRec = 0; intRec < strNotifData.length; intRec++) {
					try {
						assertEquals(
								strNotifData[intRec][0],
								selenium.getValue("//table[@class='displayTable']/tbody/tr/td/div[text()='"
										+ strStatusType
										+ "']/following-sibling::table[@summary='Notifications for "
										+ strStatusType
										+ "']"
										+ "/tbody/tr["
										+ (intRec + 1) + "]/td[1]/input"));

						if (strNotifData[intRec][1].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[2]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[2]/input"));
						}

						if (strNotifData[intRec][2].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[3]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[3]/input"));
						}

						if (strNotifData[intRec][3].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[4]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[4]/input"));
						}
					} catch (AssertionError Ae) {
					}
				}

				log4j.info("Status Type " + strStatusType
						+ " is displayed with appropriate notification methods");
			} catch (AssertionError Ae) {
				strReason = strReason
						+ " Status Type "
						+ strStatusType
						+ " is NOT displayed with appropriate notification methods";
				log4j.info("Status Type "
						+ strStatusType
						+ " is NOT displayed with appropriate notification methods");
			}

		} catch (Exception e) {
			log4j.info("checkStatTypeInMyStatusChangePrefsNew function failed"
					+ e);
			strReason = "checkStatTypeInMyStatusChangePrefs function failed"
					+ e;
		}
		return strReason;
	}

	 /***********************************************************************
	'Description	: check status types in Edit My status change preferences
	'Precondition	:None
	'Arguments		:selenium,strStatusType,strNotifData
					
	'Returns		:String
	'Date	 		:13-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String checkStatTypeInEditMyStatChangePrefsMST(Selenium selenium,
			String strStatusType, String strNotifData[][]) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + strStatusType
								+ "']"));

				for (int intRec = 0; intRec < strNotifData.length; intRec++) {
					try {
						assertEquals(
								strNotifData[intRec][0],
								selenium
										.getText("//table[@class='displayTable']/tbody/tr/td/div[text()='"
												+ strStatusType
												+ "']/following-sibling::table[@summary='Notifications for "
												+ strStatusType
												+ "']"
												+ "/tbody/tr["
												+ (intRec + 1)
												+ "]/td[1]"));

						if (strNotifData[intRec][1].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[2]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[2]/input"));
						}

						if (strNotifData[intRec][2].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[3]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[3]/input"));
						}

						if (strNotifData[intRec][3].equals("true")) {
							assertTrue(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[4]/input"));
						} else {
							assertFalse(selenium
									.isChecked("//table[@class='displayTable']/tbody/tr/td/div[text()='"
											+ strStatusType
											+ "']/following-sibling::table[@summary='Notifications for "
											+ strStatusType
											+ "']"
											+ "/tbody/tr["
											+ (intRec + 1)
											+ "]/td[4]/input"));
						}
					} catch (AssertionError Ae) {
					}
				}

				log4j
						.info("Status Type "
								+ strStatusType
								+ " is displayed with appropriate notification methods");
			} catch (AssertionError Ae) {
				strReason = strReason
						+ " Status Type "
						+ strStatusType
						+ " is NOT displayed with appropriate notification methods";
				log4j
						.info("Status Type "
								+ strStatusType
								+ " is NOT displayed with appropriate notification methods");
			}

		} catch (Exception e) {
			log4j.info("checkStatTypeInEditMyStatChangePrefsMST function failed"
					+ e);
			strReason = "checkStatTypeInEditMyStatChangePrefsMST function failed"
					+ e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		13-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'13-Nov-2012                               <Name>
	************************************************************************/
	
	public String verifyNotifInEditMySTNotifPageAbove(Selenium selenium,
			String strSearchRSName, String strSTName, boolean blnEmail,
			boolean blnPager, boolean blnWeb) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("My Status Change Preferences page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[text()='"
									+ strSearchRSName + "']"));

					assertTrue(selenium
							.isElementPresent("//table/tbody/tr/td[text()='"
									+ strSTName + "']"));

					log4j
							.info("Resource Name AND status type name is displayed");

					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[2]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[3]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnWeb) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[4]"), "X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Resource Name AND status type name is NOT displayed";
					log4j
							.info("Resource Name AND status type name is NOT displayed"
									+ Ae);
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("verifyNotifInEditMySTNotifPageAbove function failed" + e);
			strErrorMsg = "verifyNotifInEditMySTNotifPageAbove function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		13-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'13-Nov-2012                               <Name>
	************************************************************************/
	
	public String verifyNotifInEditMySTNotifPageBelow(Selenium selenium,
			String strSearchRSName, String strSTName, boolean blnEmail,
			boolean blnPager, boolean blnWeb) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("My Status Change Preferences page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[text()='"
									+ strSearchRSName + "']"));

					assertTrue(selenium
							.isElementPresent("//table/tbody/tr/td[text()='"
									+ strSTName + "']"));

					log4j
							.info("Resource Name AND status type "+strSTName+" name is displayed");

					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"+strSTName+"']" +
											"/parent::tr/following-sibling::tr[1]/td[5]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"+strSTName+"']" +
											"/parent::tr/following-sibling::tr[1]/td[6]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

					if (blnWeb) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"+strSTName+"']" +
											"/parent::tr/following-sibling::tr[1]/td[7]"), "X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. " + Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. " + Ae;
						}

					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Resource Name AND status type "+strSTName+" name is displayed";
					log4j
							.info("Resource Name AND status type "+strSTName+" name is displayed"
									+ Ae);
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("verifyNotifInEditMySTNotifPageBelow function failed" + e);
			strErrorMsg = "verifyNotifInEditMySTNotifPageBelow function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	'Description	: check status types in Edit My status change preferences
	'Precondition	:None
	'Arguments		:selenium,strStatusType,strNotifData
					
	'Returns		:String
	'Date	 		:13-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String checkOnlyStatTypesInEditMyStatChangePrefs(Selenium selenium,
			String strStatusType[]) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			for(String s:strStatusType){
				try {
					assertTrue(selenium
							.isElementPresent("//table[@class='displayTable']"
									+ "/tbody/tr/td/div[text()='" + s
									+ "']"));

					log4j
							.info("Status Type "
									+ s
									+ " is displayed in Edit My Status Change Preferences page");
				} catch (AssertionError Ae) {
					strReason = " Status Type "
							+ s
							+ " is NOT displayed in Edit My Status Change Preferences page ";
					log4j
							.info("Status Type "
									+ s
									+ " is NOT displayed in Edit My Status Change Preferences page");
				}
			}

			

		} catch (Exception e) {
			log4j.info("checkOnlyStatTypesInEditMyStatChangePrefs function failed"
					+ e);
			strReason = "checkOnlyStatTypesInEditMyStatChangePrefs function failed"
					+ e;
		}
		return strReason;
	}
	
	/**************************************************************************
	'Description	:Verify Edit Custom view page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:16-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-Nov-2012                               <Name>
	************************************************************************/

	public String navBackToEditCustomViewPge(Selenium selenium) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Pref.Back_Button"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Custom View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Custom View page is  displayed");

			} catch (AssertionError Ae) {
				strReason = "Edit Custom View page is NOT displayed" + Ae;
				log4j.info("Edit Custom View page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("Error in navBackToEditCustomViewPge" + e);
			strReason = "Error in navBackToEditCustomViewPge" + e;
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

public String cancelAndNavToEditCustomViewPage(Selenium selenium)
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
		selenium.click(propElementDetails.getProperty("Pref.CancelButton"));
		selenium.waitForPageToLoad(gstrTimeOut);
		try {
			assertEquals("Edit Custom View", selenium
					.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("'Edit Custom View' screen is displayed.");
		} catch (AssertionError ae) {
			log4j.info("'Edit Custom View' screen is NOT displayed.");
		}

	} catch (Exception e) {
		log4j.info(e);
		lStrReason = lStrReason + "; " + e.toString();
	}

	return lStrReason;
}
	
   /***********************************************************************
	'Description	: check status types in Edit custom View options
	'Precondition	:None
	'Arguments		:selenium,strStatusType
					
	'Returns		:String
	'Date	 		:25-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String checkSTsInEditCustViewOptions(Selenium selenium,String[] strStatusType) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
	
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			int i = 0;
			for (i = 0; i < strStatusType.length; i++) {
				try {
					assertFalse(selenium.isElementPresent("//b[text()='"+strStatusType[i]+"']"));
					log4j.info("Status Type "+strStatusType[i]+" is NOT displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strReason = "Status Type "+strStatusType+" is displayed in Edit Custom View Options (Columns)";
					log4j.info("Status Type "+strStatusType+" is displayed in Edit Custom View Options (Columns)");
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkSTsInEditCustViewOptions Page "
					+ e.toString();
		}
		
		return strReason;
	}
	  /***********************************************************************
		'Description	: check status types in Edit custom View options
		'Precondition	:None
		'Arguments		:selenium,strStatusType
						
		'Returns		:String
		'Date	 		:25-May-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'<Date>                                   <Name>
		************************************************************************/
		public String chkSTPresenceInEditCustViewOptionPage(Selenium selenium,String[] strStatusType) throws Exception{
			String strReason="";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
		
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try {
				int i = 0;
				for (i = 0; i < strStatusType.length; i++) {
					try {
						assertTrue(selenium.isElementPresent("//b[text()='"+strStatusType[i]+"']"));
						log4j.info("Status Type "+strStatusType[i]+" is displayed in Edit Custom View Options (Columns)");
					} catch (AssertionError Ae) {
						strReason = "Status Type "+strStatusType[i]+" is NOT displayed in Edit Custom View Options (Columns)";
						log4j.info("Status Type "+strStatusType[i]+" is NOT displayed in Edit Custom View Options (Columns)");
					}
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function checkSTsInEditCustViewOptions Page "
						+ e.toString();
			}
			
			return strReason;
}
/***********************************************************************
'Description	:navigate to edit custom view options, select the status types
				and save
'Precondition	:None
'Arguments		:selenium,strStatusType
'Returns		:String
'Date	 		:7-June-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/
public String editCustomViewOptionsNew(Selenium selenium,
		String strStatusType[]) throws Exception {
	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();
	pathProps = objAP.Read_FilePath();
	gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	try {
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			assertEquals("Edit Custom View Options (Columns)",
					selenium.getText(propElementDetails
							.getProperty("Header.Text")));
			log4j.info("'Edit Custom View Options (Columns)' screen is displayed. ");
			if (strStatusType != null) {
				// Select the status types
				for (int intOpt = 0; intOpt < strStatusType.length; intOpt++) {
					if (selenium.isChecked("//b[text()='"
							+ strStatusType[intOpt]
							+ "']/preceding-sibling::input[1]") == false)
						selenium.click("//b[text()='"
								+ strStatusType[intOpt]
								+ "']/preceding-sibling::input[1]");
				}
			}

			// click on save
			selenium.click(propElementDetails
					.getProperty("EditCustomView.EditCustomViewOptions.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (AssertionError Ae) {
			strReason = "'Edit Custom View Options (Columns)' screen is NOT displayed.";
			log4j.info("'Edit Custom View Options (Columns)' screen is NOT displayed.");
		}
		} catch (Exception e) {
			log4j.info("editCustomViewOptions function failed" + e);
			strReason = "editCustomViewOptions function failed" + e;
		}
		return strReason;
	}
/***********************************************************************
'Description	:Verify resource is added in status type preference page
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:26-May-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'26-May-2012                               <Name>
************************************************************************/

public String selSTChangeNotInEditMySTNotifPage(Selenium selenium,
		String strSearchRSName, String strResourceValue, String strSTValue,String strSTName,
		boolean blnEmail, boolean blnPager, boolean blnWeb,boolean blnSave)
		throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		

			if (blnEmail) {
				if (selenium.isChecked("css=input[name='emailInd'][value='"
						+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
					selenium.click("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue
							+ "|N|A']");

			}else{
				if (selenium.isChecked("css=input[name='emailInd'][value='"
						+ strResourceValue + "|" + strSTValue + "|N|A']") )
					selenium.click("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue
							+ "|N|A']");

			}
			

			if (blnPager) {
				if (selenium.isChecked("css=input[name='pagerInd'][value='"
						+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
					selenium.click("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue
							+ "|N|A']");

			}else{
				if (selenium.isChecked("css=input[name='pagerInd'][value='"
						+ strResourceValue + "|" + strSTValue + "|N|A']"))
					selenium.click("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue
							+ "|N|A']");

				}

			if (blnWeb) {
				if (selenium.isChecked("css=input[name='webInd'][value='"
						+ strResourceValue + "|" + strSTValue + "|N|A']") == false)
					selenium.click("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue
							+ "|N|A']");

			}else{
				if (selenium.isChecked("css=input[name='webInd'][value='"
						+ strResourceValue + "|" + strSTValue + "|N|A']") )
					selenium.click("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue
							+ "|N|A']");				
			}

			if(blnSave){
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("My Status Change Preferences page is  displayed");
				
			} catch (AssertionError Ae) {
				strErrorMsg = "My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("My Status Change Preferences page is NOT displayed"
								+ Ae);
			}
			}
	} catch (Exception e) {
		log4j.info("selSTChangeNotInEditMySTNotifPage function failed"
				+ e);
		strErrorMsg = "selSTChangeNotInEditMySTNotifPage function failed"
				+ e;
	}
	return strErrorMsg;
}
/***********************************************************************
'Description	: Add and verify many Resource in  Status change preferences 
'Precondition	:None
'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
				strState
'Returns		:String
'Date	 		:26-Nov-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/
public String vrfyRSandAddRS(Selenium selenium, String strResourceVerify[],
		String strResourceNotify[], String strRegion,
		boolean blnRSverify, boolean blnNotification,boolean blnPage) throws Exception {

	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();
	gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	try {

		if (blnRSverify) {
			for (String s : strResourceVerify) {

				// Enter value for Name
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.Name"), s);

				if (selenium.isElementPresent("Pref.FindResources.Region")) {
					// Select region
					selenium.select(propElementDetails
							.getProperty("Pref.FindResources.Region"),
							"label=" + strRegion);
				}

				// click on search button
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ s + "']"));
					log4j.info("Resource " + s + " is found");

				} catch (AssertionError Ae) {
					log4j.info("Resource " + s + " is NOT found");
					strReason = "Resource " + s + " is NOT found";
				}
			}
		}
		if (blnNotification) {
			for (String s : strResourceNotify) {

				// Enter value for Name
				selenium.type(propElementDetails
						.getProperty("Pref.FindResources.Name"), s);

				if (selenium.isElementPresent("Pref.FindResources.Region")) {
					// Select region
					selenium.select(propElementDetails
							.getProperty("Pref.FindResources.Region"),
							"label=" + strRegion);
				}

				// click on search button
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.Search"));
				selenium.waitForPageToLoad(gstrTimeOut);

				if (selenium
						.isChecked("//table[@id='tbl_resourceID']/tbody/tr/" +
								"td[2][text()='"+s+"']/" +
								"preceding-sibling::td/input") == false) {
					selenium.click("//table[@id='tbl_resourceID']/tbody/tr/" +
								"td[2][text()='"+s+"']/" +
								"preceding-sibling::td/input");
				}				
			}		
			selenium.click("css=input[value='Notifications']");
			selenium.waitForPageToLoad(gstrTimeOut);
		}
		
		if(blnPage){
			try {
				assertEquals("Edit My Status Change Preferences",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

			} catch (AssertionError Ae) {
				strReason = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}
		}

	} catch (Exception e) {
		log4j.info("vrfyRSandAddRS function failed" + e);
		strReason = "vrfyRSandAddRS function failed" + e;
	}
	return strReason;
}


 /***********************************************************************
'Description	:navigating to find  resource is added in status type preference page
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:26-Nov-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'26-Nov-2012                               <Name>
************************************************************************/

public String navToFindResourcesPageInEditSTChangeNotifPref(
		Selenium selenium) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		selenium.click(propElementDetails
				.getProperty("Preferences.StatusChangePref.Add"));// Click
		selenium.waitForPageToLoad(gstrTimeOut);
		try {
			assertEquals("Find Resources", selenium
					.getText(propElementDetails.getProperty("Header.Text")));

		} catch (AssertionError Ae) {
			strErrorMsg = "Find Resources page is NOT displayed" + Ae;
			log4j.info("Find Resources page is NOT displayed" + Ae);
		}

	} catch (Exception e) {
		log4j
				.info("navToFindResourcesPageInEditSTChangeNotifPref function failed"
						+ e);
		strErrorMsg = "navToFindResourcesPageInEditSTChangeNotifPref function failed"
				+ e;
	}
	return strErrorMsg;
}

 /***********************************************************************
'Description	:navigating to find  resource is added in status type preference page
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:26-Nov-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'26-Nov-2012                               <Name>
************************************************************************/

public String savAndNavigate(Selenium selenium) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		selenium.click(propElementDetails.getProperty("Save"));
		selenium.waitForPageToLoad(gstrTimeOut);

	} catch (Exception e) {
		log4j.info("savAndNavigate function failed" + e);
		strErrorMsg = "savAndNavigate function failed" + e;
	}
	return strErrorMsg;
}
/***********************************************************************
'Description	:find resources and add to custom view
'Precondition	:None
'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
				strState
'Returns		:String
'Date	 		:9-May-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/
public String findResources(Selenium selenium,String[] strResource,String strCategory,
		String strRegion,String strCityZipCd,String strState){
	String strReason="";
	try{
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try{
			assertEquals("Find Resources",
					selenium.getText(propElementDetails
							.getProperty("Header.Text")));
			log4j.info("Find Resources page is displayed" );
			for(int i=0;i<strResource.length;i++){
			//Enter value for Name
			selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strResource[i]);
			//Enter for category
			selenium.select(propElementDetails.getProperty("Pref.FindResources.Category"), "label="+strCategory);
			if(selenium.isElementPresent(propElementDetails.getProperty("Pref.FindResources.Region"))){
				//Select region 
				selenium.select(propElementDetails.getProperty("Pref.FindResources.Region"), "label="+strRegion);
			}
			//Enter city, county or zip
			selenium.type(propElementDetails.getProperty("Pref.FindResources.CityZip"), strCityZipCd);
			//Select the state
			selenium.select(propElementDetails.getProperty("Pref.FindResources.State"), "label="+strState);
			
			//click on search button
			selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
			selenium.waitForPageToLoad(gstrTimeOut);			
			}
			
		} catch (AssertionError Ae) {
			strReason = "Find Resources page is NOT displayed" + Ae;
			log4j.info("Find Resources page is NOT displayed" + Ae);
		}
		
		
	} catch (Exception e) {
		log4j.info("findResourcesInAddMoreRes function failed" + e);
		strReason = "findResourcesInAddMoreRes function failed" + e;
	}
	return strReason;
}
/***********************************************************************
'Description	:navigate to edit custom view options, select the status types
				and save
'Precondition	:None
'Arguments		:selenium,strStatusType
'Returns		:String
'Date	 		:7-June-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/
	public String editCustomViewWith4Options(Selenium selenium,
			String[] strStatusType, boolean blnSave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed. ");
				if (strStatusType != null) {
					// Select the status types
					for (int intOpt = 0; intOpt < strStatusType.length; intOpt++) {
						if (selenium.isChecked("//b[text()='"
								+ strStatusType[intOpt]
								+ "']/preceding-sibling::input[1]") == false)
							selenium.click("//b[text()='"
									+ strStatusType[intOpt]
									+ "']/preceding-sibling::input[1]");
					}
				}
				if (selenium.isChecked(propElementDetails.getProperty("Pref.ShowComments_Option")) == false) {
					selenium.click(propElementDetails.getProperty("Pref.ShowComments_Option"));
				}
				if (selenium.isChecked(propElementDetails.getProperty("Pref.showUpdateTime_Option")) == false) {
					selenium.click(propElementDetails.getProperty("Pref.showUpdateTime_Option"));
				}
				if (selenium.isChecked(propElementDetails.getProperty("Pref.showUpdateUser_Option")) == false) {
					selenium.click(propElementDetails.getProperty("Pref.showUpdateUser_Option"));
				}
				if (selenium.isChecked(propElementDetails.getProperty("Pref.showTotals_Option")) == false) {
					selenium.click(propElementDetails.getProperty("Pref.showTotals_Option"));
				}

				if (blnSave) {
					// click on save
					selenium
							.click(propElementDetails
									.getProperty("EditCustomView.EditCustomViewOptions.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}

			} catch (AssertionError Ae) {
				strReason = "'Edit Custom View Options (Columns)' screen is displayed.";
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed.");
			}
		} catch (Exception e) {
			log4j.info("editCustomViewOptions function failed" + e);
			strReason = "editCustomViewOptions function failed" + e;
		}
		return strReason;
	}

	
	/***********************************************************************
	'Description	:Verify custom view is created
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-April-2012                               <Name>
	************************************************************************/
	
	public String createCustomViewWitTablOrMapOption(Selenium selenium,
			String[] strResourceName, String strRT, String strST)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditCustomView.AddMoreResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Find Resources page is  displayed");

				int intCnt = 0;

				intCnt = strResourceName.length;
				log4j.info(intCnt);

				String[] strResourceType = new String[intCnt];

				for (int i = 0; i < intCnt; i++) {

					// Enter value for Name
					selenium.type(propElementDetails
							.getProperty("Pref.FindResources.Name"),
							strResourceName[i]);

					selenium.click(propElementDetails
							.getProperty("EditCustomView.FindRes.Search"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						strResourceType[i] = selenium
								.getText("//table[@id='tbl_resourceID']"
										+ "/tbody/tr/td[2][text()='"
										+ strResourceName[i] + "']/"
										+ "following-sibling::td[3]");
						log4j.info(intCnt);
						log4j.info(strResourceType[intCnt]);
					} catch (Exception e) {

					}

				}

				for (String s : strResourceName) {

					if (selenium
							.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
									+ s + "']/parent::tr/td/input") == false) {
						selenium
								.click("//table[@id='tbl_resourceID']/tbody/tr/td[text()='"
										+ s + "']/parent::tr/td/input");

					}
				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.AddToCustomView"));
				selenium.waitForPageToLoad(gstrTimeOut);

				for (String s : strResourceType) {
					try {
						assertTrue(selenium
								.isElementPresent("//div/span[contains(text(),'"
										+ s + "')]"));
						log4j.info(" Resources is added to custom view ");

						selenium.click(propElementDetails
								.getProperty("EditCustomView.Options"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							assertEquals("Edit Custom View Options (Columns)",
									selenium.getText(propElementDetails
											.getProperty("Header.Text")));
							log4j
									.info("Edit Custom View Options (Columns) page is  Displayed");

							if (selenium
									.isChecked("//div[@id='mainContainer']/form/div/div/b[text()='"
											+ strST
											+ "']/preceding-sibling::input[1]") == false) {

								selenium
										.click("//div[@id='mainContainer']/form/div/div/b[text()='"
												+ strST
												+ "']/preceding-sibling::input[1]");

							}
							selenium
									.click(propElementDetails
											.getProperty("EditCustomView.EditCustomViewOptions.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);
							

							if(selenium.isElementPresent(propElementDetails
									.getProperty("View.Custom.ShowTabl"))){
								selenium.click(propElementDetails
										.getProperty("View.Custom.ShowTabl"));
								selenium.waitForPageToLoad(gstrTimeOut);
							}
						
							
							try {
								assertEquals("Custom View - Table", selenium
										.getText(propElementDetails
												.getProperty("Header.Text")));

								try {
									assertTrue(selenium
											.isElementPresent("//th[text()"
													+ "='" + strRT
													+ "']/following-sibling"
													+ "::th/a[text()='" + strST
													+ "']"));

									log4j
											.info("Resource Type and status Type present in custom view table");

									try {

										for (String s1 : strResourceName) {
											assertTrue(selenium
													.isElementPresent("//td[@class='resourceName']"
															+ "/a[text()='"
															+ s1 + "']"));
											log4j
													.info("Resource Name present in custom view table");
										}

									} catch (AssertionError Ae) {
										strErrorMsg = "Resource Name NOT present in custom view table"
												+ Ae;
										log4j
												.info("Resource Name NOT present in custom view table"
														+ Ae);
									}

								} catch (AssertionError Ae) {
									strErrorMsg = "Resource Type and status Type NOT present in custom view table"
											+ Ae;
									log4j
											.info("Resource Type and status Type present in custom view table"
													+ Ae);
								}
							} catch (AssertionError Ae) {
								strErrorMsg = "Custom View - Table screen NOT displayed"
										+ Ae;
								log4j
										.info("Custom View - Table screen NOT displayed"
												+ Ae);
							}

						} catch (AssertionError Ae) {
							strErrorMsg = "Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae;
							log4j.info("Edit Custom View Options (Columns)"
									+ " page is NOT Displayed" + Ae);
						}

					} catch (AssertionError Ae) {
						strErrorMsg = " Resources is NOT added to custom view "
								+ Ae;
						log4j.info(" Resources is NOT added to custom view "
								+ Ae);
						break;
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("create Custom View function failed" + e);
			strErrorMsg = "create Custom View function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	  'Description  :Verify ST is added in the edit custom view page
	  'Precondition :None
	  'Arguments    :selenium,strSTValue,blnSave
	  'Returns      :String
	  'Date         :29-May-2012
	  'Author       :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '25-May-2012                               <Name>
	  ************************************************************************/
	  

	public String addSTInEditCustViewOptionPageTablOrMap(Selenium selenium,
			String[][] strSTValue, boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Custom View Options (Columns) is displayed");

				for (int i = 0; i < strSTValue.length; i++) {
					if (strSTValue[i][1].equals("true")) {
						if (selenium
								.isChecked("css=input[name='viewColumnID'][value='st_"
										+ strSTValue[i][0] + "']") == false)
							selenium
									.click("css=input[name='viewColumnID'][value='st_"
											+ strSTValue[i][0] + "']");
					} else if (strSTValue[i][1].equals("false")) {
						if (selenium
								.isChecked("css=input[name='viewColumnID'][value='st_"
										+ strSTValue[i][0] + "']"))
							selenium
									.click("css=input[name='viewColumnID'][value='st_"
											+ strSTValue[i][0] + "']");
					}
				}

				if (blnSave) {

					selenium.click(propElementDetails
							.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					if (selenium.isElementPresent(propElementDetails
							.getProperty("View.Custom.ShowTabl"))) {
						selenium.click(propElementDetails
								.getProperty("View.Custom.ShowTabl"));
						selenium.waitForPageToLoad(gstrTimeOut);
					}

					try {
						assertEquals("Custom View - Table", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Custom View - Table page is  displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Custom View - Table page is NOT displayed"
								+ Ae;
						log4j.info("Custom View - Table page is NOT displayed"
								+ Ae);
					}

				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View Options (Columns) is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Custom View Options (Columns) is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("addStandardSTInEditCustViewOptionPageTablOrMap function failed" + e);
			strErrorMsg = "addStandardSTInEditCustViewOptionPageTablOrMap function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:edit custom view options and add the status type and check 
					in custom view table
	'Precondition	:None
	'Arguments		:selenium,strOptSelect,strOptionsName,strResTypeName,strStatusType,
					strResource
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String editCustomViewOptionsAndCheckInCustMapOrViewTable(
			Selenium selenium, String strOptSelect[],
			String[][] strOptionsName, String strResTypeName,
			String strStatusType, String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.click(propElementDetails
					.getProperty("EditCustomView.Options"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed. ");
				if (strOptSelect != null) {
					// Select the status types
					for (int intOpt = 0; intOpt < strOptSelect.length; intOpt++) {
						if (selenium.isChecked("//b[text()='"
								+ strOptSelect[intOpt]
								+ "']/preceding-sibling::input") == false)
							selenium.click("//b[text()='"
									+ strOptSelect[intOpt]
									+ "']/preceding-sibling::input");
					}
				}
				if (strOptionsName != null) {
					// select the options
					for (int i = 0; i < strOptionsName.length; i++) {
						if (strOptionsName[i][1].equals("true")) {
							if (selenium.isChecked("css=input[name='"
									+ strOptionsName[i][0] + "']") == false)
								selenium.click("css=input[name='"
										+ strOptionsName[i][0] + "']");
						} else if (strOptionsName[i][1].equals("false")) {
							if (selenium.isChecked("css=input[name='"
									+ strOptionsName[i][0] + "']"))
								selenium.click("css=input[name='"
										+ strOptionsName[i][0] + "']");
						}
					}

				}

				// click on save
				selenium
						.click(propElementDetails
								.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				if(selenium.isElementPresent(propElementDetails
						.getProperty("View.Custom.ShowTabl"))){
					selenium.click(propElementDetails
							.getProperty("View.Custom.ShowTabl"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}

				try {
					assertEquals("Custom View - Table", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
									+ strResTypeName
									+ "']/thead/tr/th[2][text()='"
									+ strResTypeName + "']"));
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
									+ strResTypeName
									+ "']/thead/tr/th[3]/a[text()='"
									+ strStatusType + "']"));
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt_')][@summary='Status for "
									+ strResTypeName
									+ "']/tbody/tr/td[2]/a[text()='"
									+ strResource + "']"));
					log4j
							.info("'Custom View - Table' screen is displayed with resource "
									+ strResource
									+ " and status type "
									+ strStatusType);
				} catch (AssertionError Ae) {
					strReason = "'Custom View - Table' screen is NOT displayed with resource "
							+ strResource + " and status type " + strStatusType;
					log4j
							.info("'Custom View - Table' screen is NOT displayed with resource "
									+ strResource
									+ " and status type "
									+ strStatusType);
				}

			} catch (AssertionError Ae) {
				strReason = "'Edit Custom View Options (Columns)' screen is NOT displayed.";
				log4j
						.info("'Edit Custom View Options (Columns)' screen is NOT displayed.");
			}
		} catch (Exception e) {
			log4j.info("findResources function failed" + e);
			strReason = "findResources function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:navigate to edit custom view options, select the status types
					and save
	'Precondition	:None
	'Arguments		:selenium,strStatusType
	'Returns		:String
	'Date	 		:7-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	
	public String editCustomViewOptionsViewMapOrTabl(Selenium selenium,
			String strStatusType[]) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditCustomView.Options"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed. ");
				if (strStatusType != null) {
					// Select the status types
					for (int intOpt = 0; intOpt < strStatusType.length; intOpt++) {
						if (selenium.isChecked("//b[text()='"
								+ strStatusType[intOpt]
								+ "']/preceding-sibling::input[1]") == false)
							selenium.click("//b[text()='"
									+ strStatusType[intOpt]
									+ "']/preceding-sibling::input[1]");
					}
				}

				// click on save
				selenium
						.click(propElementDetails
								.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				if(selenium.isElementPresent(propElementDetails
						.getProperty("View.Custom.ShowTabl"))){
					selenium.click(propElementDetails
							.getProperty("View.Custom.ShowTabl"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}
				

			} catch (AssertionError Ae) {
				strReason = "'Edit Custom View Options (Columns)' screen is displayed.";
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed.");
			}
		} catch (Exception e) {
			log4j.info("editCustomViewOptions function failed" + e);
			strReason = "editCustomViewOptions function failed" + e;
		}
		return strReason;
	}
	
	/***************************************************************
	 * 'Description :Drag and drop the status type to particular section and
	 * save 'Precondition :None 'Arguments
	 * :selenium,strStatType,strSection,blnCreateSec 'Returns :strReason 'Date
	 * :23-May-2012 'Author :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date Modified By '07-Sep-12 <Name>
	 * 
	 * @throws Exception
	 ***************************************************************/
	public String verRSAndRTInEditCustomViewPage(Selenium selenium,
			String strResource, String strResType) throws Exception {

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try{
			assertTrue(selenium.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
					+ strResType + "')]"));
			log4j.info("Section name (Resource Type name "+strResType+") is retained. ");
			
			selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"
					+ strResType + "')]");
			try {
				assertTrue(selenium
						.isElementPresent("//li[@class='item']/div[contains(text(),"
								+ "'"
								+ strResource
								+ " ["
								+ strResType
								+ "]')]"));
				assertTrue(selenium
						.isVisible("//li[@class='item']/div[contains(text(),"
								+ "'" + strResource + " [" + strResType
								+ "]')]"));
				log4j.info("Resource name " + strResource
						+ "is displayed With resource type" + strResType);

			} catch (AssertionError ae) {
				log4j.info("Resource name " + strResource
						+ "is NOT displayed With resource type" + strResType);
				strReason = strReason + " " + "Resource name " + strResource
						+ "is NOT displayed With resource type" + strResType;
			}
			} catch (AssertionError ae) {
				log4j.info("Section name (Resource Type name "+strResType+") is NOT retained.");
				strReason = strReason + " " + "Section name (Resource Type name "+strResType+
						") is NOT retained.";
			}
		} catch (Exception e) {
			log4j.info("dragAndDropSTtoSection function failed" + e);
			strReason = "dragAndDropSTtoSection function failed" + e;
		}
		return strReason;
	}
	
	  /***********************************************************************
	'Description	:navigating to find  resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'9-May-2012                               <Name>
	************************************************************************/

	public String navToFindResourcesPage(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Preferences.StatusChangePref.Add"));// Click 'Add' to add
			// the resources
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Find Resources page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Find Resources page is NOT displayed" + Ae;
				log4j.info("Find Resources page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToFindResourcesPage function failed" + e);
			strErrorMsg = "navToFindResourcesPage function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify error message in custom view table for user
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:27-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'5-Nov-2012                               <Name>
	************************************************************************/
	
	public String varErrorMsgInCustomViewTable(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));

			selenium.click(propElementDetails.getProperty("View.CustomLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("No Rows in Custom View",
						selenium.getText(propElementDetails.getProperty("Pref.ErrorMsgElementInTable")));
				assertEquals(
						"There are no resources to display in your custom view.",
						selenium.getText("css=p"));
				assertEquals(
						"Click here to add resources to your custom view",
						selenium.getText("link=Click here to add resources to your custom view"));

				log4j.info("No Rows in Custom View"
						+ "There are no resources to display in your custom view. "
						+ "Click here to add resources to your custom view is Displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "No Rows in Custom View"
						+ "There are no resources to display in your custom view. "
						+ "Click here to add resources to your custom view is NOT Displayed.";

				log4j.info("No Rows in Custom View"
						+ "There are no resources to display in your custom view. "
						+ "Click here to add resources to your custom view is NOT Displayed.");
			}

		} catch (Exception e) {
			log4j.info("varErrorMsgInCustomViewTable " + "function failed" + e);
			strErrorMsg = "varErrorMsgInCustomViewTable" + " function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	  'Description  :Verify ST is added in the edit custom view page
	  'Precondition :None
	  'Arguments    :selenium,strSTValue,blnSave
	  'Returns      :String
	  'Date         :09-Jan-2013
	  'Author       :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '09-Jan-2013                               <Name>
	  ************************************************************************/
	  

	public String addStandardSTInEditCustViewOptionPageTablOrMap(
			Selenium selenium, String[][] strSTValue, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Custom View Options (Columns) is displayed");

				for (int i = 0; i < strSTValue.length; i++) {
					if (strSTValue[i][1].equals("true")) {
						if (selenium
								.isChecked("css=input[name='viewColumnID'][value='sst_"
										+ strSTValue[i][0] + "']") == false)
							selenium
									.click("css=input[name='viewColumnID'][value='sst_"
											+ strSTValue[i][0] + "']");
					} else if (strSTValue[i][1].equals("false")) {
						if (selenium
								.isChecked("css=input[name='viewColumnID'][value='sst_"
										+ strSTValue[i][0] + "']"))
							selenium
									.click("css=input[name='viewColumnID'][value='sst_"
											+ strSTValue[i][0] + "']");
					}
				}

				if (blnSave) {

					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					if (selenium.isElementPresent(propElementDetails
							.getProperty("View.Custom.ShowTabl"))) {
						selenium.click(propElementDetails
								.getProperty("View.Custom.ShowTabl"));
						selenium.waitForPageToLoad(gstrTimeOut);
					}

					try {
						assertEquals("Custom View - Table", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Custom View - Table page is  displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Custom View - Table page is NOT displayed"
								+ Ae;
						log4j.info("Custom View - Table page is NOT displayed"
								+ Ae);
					}

				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View Options (Columns) is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Custom View Options (Columns) is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("addStandardSTInEditCustViewOptionPageTablOrMap function failed"
							+ e);
			strErrorMsg = "addStandardSTInEditCustViewOptionPageTablOrMap function failed"
					+ e;
		}
		return strErrorMsg;
	}

 /***********************************************************************
'Description	: navigate to Edit My status change preferences page
'Precondition	:None
'Arguments		:selenium,strResource
				
'Returns		:String
'Date	 		:24-May-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/
public String navToEditMyStatusChangePrefsForUser(Selenium selenium,
		String strResource,String strUser) throws Exception {
	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	try {
		// click on Edit
		selenium
				.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strResource
						+ "']/parent::tr/td[1]/a[text()='edit']");
		selenium.waitForPageToLoad(gstrTimeOut);

		try {
			assertEquals("Edit Status Change Preferences for "+strUser, selenium
					.getText(propElementDetails.getProperty("Header.Text")));

			log4j.info("Edit Status Change Preferences for "+strUser+" page is  displayed");

		} catch (AssertionError Ae) {
			strReason = "Edit Status Change Preferences for "+strUser+" page is NOT  displayed"+Ae;
			log4j.info("Edit Status Change Preferences for "+strUser+" page is NOT displayed"+Ae);
		}

	} catch (Exception e) {
		log4j.info("navToEditMyStatusChangePrefsForUser function failed" + e);
		strReason = "navToEditMyStatusChangePrefsForUser function failed" + e;
	}
	return strReason;
}

/******************************************************************************
'Description	:Verify Error Message in Edit My Status Change Preferences page
'Arguments		:selenium
'Returns		:String
'Date	 		:5-Dec-2012
'Author			:QSG
'------------------------------------------------------------------------------
'Modified Date                                                  Modified By
'5-Nov-2012                                                     <Name>
*******************************************************************************/

	public String varErrorMsgInEditMyStatChngPrefPage(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals("No visible status types are available.",
						selenium.getText(propElementDetails.getProperty("Prop61")));
				log4j.info("'No visible status types are available' message is displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "'No visible status types are available' message is NOT displayed.";
				log4j.info("'No visible status types are available' message is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info("varErrorMsgInEditMyStatChngPrefPage"
					+ "function failed" + e);
			strErrorMsg = "varErrorMsgInEditMyStatChngPrefPage"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
/***********************************************************************
'Description	:Adding resource for notifiation By searching for User
'Arguments		:selenium,strSearchRSName,strResourceValue
'Returns		:String
'Date	 		:11-Jan-2013
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                                             Modified By
'11-Jan-2013                                              <Name>
************************************************************************/
 public String addResourcesForNotIfication(Selenium selenium,
			String strSearchRSName, String strResourceValue,String strUserName) throws Exception {

	 String strErrorMsg = "";// variable to store error mesage

	 try {
		 rdExcel = new ReadData();
		 propEnvDetails = objReadEnvironment.readEnvironment();
		 propElementDetails = objelementProp.ElementId_FilePath();
		 pathProps = objAP.Read_FilePath();
		 gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		 selenium.selectWindow("");
		 selenium.selectFrame("Data");

		 selenium.click(propElementDetails
				 .getProperty("Preferences.StatusChangePref.Add")); // add
		 // the resources
		 selenium.waitForPageToLoad(gstrTimeOut);

		 try {
			 assertEquals("Find Resources",
					 selenium.getText(propElementDetails
							 .getProperty("Header.Text")));

			 log4j.info("Find Resources page is  displayed");

			 // Search criteria
			 selenium.type(propElementDetails.getProperty("Pref.FindResources.Name"), strSearchRSName);

			 selenium.click(propElementDetails.getProperty("EditCustomView.FindRes.Search"));
			 selenium.waitForPageToLoad(gstrTimeOut);

			 if (selenium.isChecked("css=input[name='resourceID'][value='"
					 + strResourceValue + "']") == false) {
				 selenium.click("css=input[name='resourceID'][value='"
						 + strResourceValue + "']");
			 }

			 selenium.click(propElementDetails.getProperty("StatusChangePrefs.FindRes.Notifications"));
			 selenium.waitForPageToLoad(gstrTimeOut);
			 try {
				 assertEquals("Edit Status Change Preferences for "
						 + strUserName, selenium.getText(propElementDetails
								 .getProperty("Header.Text")));
				 log4j.info("Edit Status Change Preferences for "
						 + strUserName + " page is  displayed");

			 } catch (AssertionError Ae) {
				 strErrorMsg = "Edit Status Change Preferences for "
						 + strUserName + " page is NOT displayed" + Ae;
				 log4j.info("Edit Status Change Preferences for "
						 + strUserName + " page is NOT displayed" + Ae);
			 }

		 } catch (AssertionError Ae) {
			 strErrorMsg = "Find Resources page is NOT displayed" + Ae;
			 log4j.info("Find Resources page is NOT displayed" + Ae);
		 }

	 } catch (Exception e) {
		 log4j.info("addResourcesAndNavToEditSTNotfPreferences function failed"
				 + e);
		 strErrorMsg = "addResourcesAndNavToEditSTNotfPreferences function failed"
				 + e;
	 }
	 return strErrorMsg;
 }

 
 /***********************************************************************
	'Description	:Verify Status types in particular section in Edit My Status Change Preferences page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-Sep-2012                               <Name>
	************************************************************************/
	
	public String verifySTInSectionInEditMySTPrfPageNew(Selenium selenium,
			String strSectionValue, String strSectionName, String[] strSTName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
		

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				try {
					assertTrue(selenium.isElementPresent("//table[@id='stt_"
							+ strSectionValue + "']"
							+ "/thead/tr/th[2][text()='" + strSectionName
							+ "']"));
					log4j.info("Section " + strSectionName + " is Present");

					for (String s : strSTName) {

						try {

							assertTrue(selenium
									.isElementPresent("//table[@id='stt_"
											+ strSectionValue + "']"
											+ "/thead/tr/th[2][text()='"
											+ strSectionName + "']/ancestor::"
											+ "table/tbody/tr/td/div[text()='"
											+ s + "']"));

							log4j
									.info("	"
											+ s
											+ " is displayed under section "
											+ strSectionName
											+ " in"
											+ " the 'Edit My Status Change Preferences' screen. ");

						} catch (AssertionError Ae) {
							strErrorMsg =s
									+ " is NOT displayed under section "
									+ strSectionName
									+ " "
									+ "in the 'Edit My Status Change Preferences' screen.";
									
							log4j
									.info("	"
											+ s
											+ " is NOT  are displayed under section"
											+ strSectionName
											+ " in"
											+ " the 'Edit My Status Change Preferences' screen. "
											+ Ae);
						}
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Section " + strSectionName
							+ " is NOT Present";
					log4j.info("Section " + strSectionName + " is NOT Present"
							+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("verifySTInSectionInEditMySTPrfPageNew function failed"
							+ e);
			strErrorMsg = "verifySTInSectionInEditMySTPrfPageNew function failed" + e;
		}
		return strErrorMsg;
	}
	
	//start//verifyChangePasswdFields//
	/*******************************************************************************************
	' Description: Verify the data in Set Up Your Password screen 
					and submit. Verify that User is directed to region 
					default view.
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 22/04/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	
	public String verifyChangePasswdFields(Selenium selenium) throws Exception {

		String lStrReason = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				assertEquals("Set Up Your Password", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));

				log4j.info("Set Up Your Password is displayed");

				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("SetUpPwd.NewUsrName")));
					log4j.info("New Password (Text field) is NOT displayed");

					try {
						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("SetUpPwd.CofrmUsrName")));
						log4j
								.info("Verify  Password (Text field) is NOT displayed");

						try {
							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("SetUpPwd.Submit")));
							log4j
									.info("'Submit'  buttons is displayed. ");

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='headingSubtitle']"
												+ "[text()='Enter and confirm a new password "
												+ "for your account.']"));
								log4j
										.info("Enter and confirm a new password for your account."
												+ " is displayed. ");
							} catch (AssertionError Ae) {
								lStrReason = "Enter and confirm a new password for your account."
										+ " is NOT displayed.";
								log4j
										.info("Enter and confirm a new password for your account."
												+ " is NOT displayed.");
							}

						} catch (AssertionError Ae) {
							lStrReason = "'Submit' and 'Cancel' buttons are NOT displayed. ";
							log4j
									.info("'Submit' and 'Cancel' buttons are NOT displayed. ");
						}

					} catch (AssertionError Ae) {
						lStrReason = "Verify  Password (Text field) is NOT displayed";
						log4j
								.info("Verify  Password (Text field) is NOT displayed");
					}

				} catch (AssertionError Ae) {
					lStrReason = "New Password (Text field) is NOT displayed";
					log4j.info("New Password (Text field) is NOT displayed");
				}

			} catch (AssertionError Ae) {

				lStrReason = "Set Up Your Password is NOT displayed" + Ae;
				log4j.info("Set Up Your Password is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "Preferences.verifyChangePasswdFields failed to"
					+ " complete due to " + lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//verifyChangePasswdFields//
	
	
	/***********************************************************************
	'Description	:Verify resource is added in status type preference page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:26-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/
	
	public String selectSTChangeNotifforTxtStatusType(Selenium selenium,
			String strSearchRSName, String strResourceValue, String strSTValue,String strSTName,
			boolean blnEmail, boolean blnPager, boolean blnWeb)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j
						.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|T|']");

				}
				

				if (blnPager) {
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|T|']");

				}

				if (blnWeb) {
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue
								+ "|T|']");

				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);	
				
				int intCnt=0;
				do{
					try {

						assertEquals("My Status Change Preferences", selenium
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
					assertEquals("My Status Change Preferences", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("My Status Change Preferences page is  displayed");

					
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}
					
					
					if (blnEmail) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[2]"), "X");
							log4j
									.info("Selected  Email notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Email notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Email notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					if (blnPager) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[3]"), "X");
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Pager notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Pager notification methods for"
									+ ""
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}

					}
					
					
					if (blnWeb) {
						try {

							assertEquals(selenium
									.getText("//table/tbody/tr/td[text()='"
											+ strSTName + "']"
											+ "/following-sibling::td[4]"), "X");
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. ");
						} catch (AssertionError Ae) {
							log4j
									.info("Selected Web notification methods for"
											+ strSTName
											+ " is NOT displayed in 'My Status Change Preferences'"
											+ " screen for resource RS. "+Ae);

							strErrorMsg = "Selected Web notification methods for"
									+ strSTName
									+ " is NOT displayed in 'My Status Change Preferences'"
									+ " screen for resource RS. "+Ae;
						}
					}
				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j
							.info("My Status Change Preferences page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit My Status Change Preferences page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifforTxtStatusType function failed"
					+ e);
			strErrorMsg = "selectSTChangeNotifforTxtStatusType function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify Status types in particular section in Edit My Status Change Preferences page
	'Precondition	:None
	'Arguments		:strSTName
	'Returns		:String[]
	'Date	 		:3-May-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-May-2013                               <Name>
	************************************************************************/
	
	public String verifySTInInEditMySTPrfPage(Selenium selenium,
			String[] strSTName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			for (String s : strSTName) {

				try {

					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'stt_')]"
									+ "/thead/tr/th[2][text()='Uncategorized']/ancestor::"
									+ "table/tbody/tr/td/div[text()='"
									+ s
									+ "']"));

					log4j
							.info( s
									+ " is displayed under section Uncategorized in"
									+ " the 'Edit My Status Change Preferences' screen. ");

				} catch (AssertionError Ae) {
					strErrorMsg =s+" is NOT displayed under section Uncategorized "
							+ "in the 'Edit My Status Change Preferences' screen. ";
					log4j
							.info( s
									+ " is NOT  are displayed under section Uncategorized in"
									+ " the 'Edit My Status Change Preferences' screen. "
									+ Ae);
				}
			}

		} catch (Exception e) {
			log4j
					.info("verifySTInInEditMySTPrfPage function failed"
							+ e);
			strErrorMsg = "verifySTInInEditMySTPrfPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Select and Deselect the status change notification for text status type.
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-May-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-May-2012                               <Name>
	************************************************************************/

	public String selectanddeselesctSTChangeNotifforTxtStatusType(
			Selenium selenium, String strSearchRSName, String strResourceValue,
			String strSTValue, String strSTName, boolean blnEmail,
			boolean blnPager, boolean blnWeb) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Edit My Status Change Preferences page is  displayed");

				if (blnEmail) {
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']") == false)
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|T|']");

				} else {
					if (selenium.isChecked("css=input[name='emailInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']"))
						selenium.click("css=input[name='emailInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|T|']");
				}

				if (blnPager) {
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']") == false)
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|T|']");

				} else {
					if (selenium.isChecked("css=input[name='pagerInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']"))
						selenium.click("css=input[name='pagerInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|T|']");
				}

				if (blnWeb) {
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']") == false)
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|T|']");

				} else {
					if (selenium.isChecked("css=input[name='webInd'][value='"
							+ strResourceValue + "|" + strSTValue + "|T|']"))
						selenium.click("css=input[name='webInd'][value='"
								+ strResourceValue + "|" + strSTValue + "|T|']");
				}

				selenium.click(propElementDetails
						.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				do {
					try {

						assertEquals("My Status Change Preferences",
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
					assertEquals("My Status Change Preferences",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("My Status Change Preferences page is  displayed");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/"
										+ "table/tbody/tr/td[text()='"
										+ strSearchRSName + "']"));

						log4j.info("Resource Name is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Resource Name is NOT displayed" + Ae;
						log4j.info("Resource Name is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j.info("My Status Change Preferences page is NOT displayed"
							+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("Edit My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("selectanddeselectSTChangeNotifforTxtStatusType function failed"
					+ e);
			strErrorMsg = "selectanddeselectSTChangeNotifforTxtStatusType function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	  'Description  :Verify status change notification preference for particular user is displayed
	  'Precondition :None
	  'Arguments    :selenium,strUserName
	  'Returns      :String
	  'Date         :25-May-2012
	  'Author       :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '25-May-2012                               <Name>
	  ************************************************************************/
	  
public String navMYStatusChangeNotiFrmEditUserPge(Selenium selenium,
		String strUserName) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		selenium.click(propElementDetails.getProperty("Pref.StatusChangeNotification"));
		selenium.waitForPageToLoad(gstrTimeOut);

		int intCnt=0;
		do{
			try {

				assertEquals("My Status Change Preferences", selenium
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
			assertEquals("My Status Change Preferences", selenium
					.getText(propElementDetails.getProperty("Header.Text")));

			log4j.info("My Status Change Preferences page is  displayed");

		} catch (AssertionError Ae) {
			strErrorMsg = "My Status Change Preferences page is NOT displayed"
					+ Ae;
			log4j.info("My Status Change Preferences page is NOT displayed"
					+ Ae);
		}

	} catch (Exception e) {
		log4j
				.info("navigate to navMYStatusChangeNotiFrmEditUserPge Page function failed"
						+ e);
		strErrorMsg = "navigate to navMYStatusChangeNotiFrmEditUserPge Page function failed"
				+ e;
	}
	return strErrorMsg;
}

/***************************************************************
 * 'Description :Drag and drop the status type to particular section and
 * save 'Precondition :None 'Arguments
 * :selenium,strStatType,strSection,blnCreateSec 'Returns :strReason 'Date
 * :23-May-2012 'Author :QSG
 * '---------------------------------------------------------------
 * 'Modified Date Modified By '07-Sep-12 <Name>
 * 
 * @throws Exception
 ***************************************************************/
public String verRSAndRTInEditCustomViewPageNew(Selenium selenium,
		String strResource, String strClickResType,String strChkRT) throws Exception {

	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();
	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	String strReason = "";

	try {
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try{
		assertTrue(selenium.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
				+ strClickResType + "')]"));
		log4j.info("Section name (Resource Type name "+strClickResType+") is retained. ");
		
		selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"
				+ strClickResType + "')]");
		try {
			assertTrue(selenium
					.isElementPresent("//li[@class='item']/div[contains(text(),"
							+ "'"
							+ strResource
							+ " ["
							+ strChkRT
							+ "]')]"));
			assertTrue(selenium
					.isVisible("//li[@class='item']/div[contains(text(),"
							+ "'" + strResource + " [" + strChkRT
							+ "]')]"));
			log4j.info("Resource name " + strResource
					+ "is displayed With resource type" + strChkRT);

		} catch (AssertionError ae) {
			log4j.info("Resource name " + strResource
					+ "is NOT displayed With resource type" + strChkRT);
			strReason = strReason + " " + "Resource name " + strResource
					+ "is NOT displayed With resource type" + strChkRT;
		}
		} catch (AssertionError ae) {
			log4j.info("Section name (Resource Type name "+strClickResType+") is NOT retained.");
			strReason = strReason + " " + "Section name (Resource Type name "+strClickResType+
					") is NOT retained.";
		}
	} catch (Exception e) {
		log4j.info("dragAndDropSTtoSection function failed" + e);
		strReason = "dragAndDropSTtoSection function failed" + e;
	}
	return strReason;
}
/***********************************************************************
'Description	:Verify error message in custom view table for user
'Arguments		:selenium
'Returns		:String
'Date	 		:18-6-2013
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'5-Nov-2012                               <Name>
************************************************************************/

public String varNoCustomViewErrorMsgInCustomViewTable(Selenium selenium)
		throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
		selenium.click(propElementDetails.getProperty("View.CustomLink"));
		selenium.waitForPageToLoad(gstrTimeOut);

		try {
			assertEquals("No Custom View",
					selenium.getText(propElementDetails.getProperty("Pref.ErrorMsgElementInTable")));
			assertEquals(
					"You have not yet created a Custom View configuration.",
					selenium.getText("css=p"));
			assertEquals(
					"Click here to setup your custom view",
					selenium.getText("link=Click here to setup your custom view"));

			log4j.info("No Custom View"
					+ "You have not yet created a Custom View configuration."
					+ "Click here to setup your custom view is Displayed.");

		} catch (AssertionError Ae) {
			strErrorMsg = "No Custom View"
					+ "You have not yet created a Custom View configuration."
					+ "Click here to setup your custom view is Not Displayed.";

			log4j.info("No Custom View"
					+ "You have not yet created a Custom View configuration."
					+ "Click here to setup your custom view is Not Displayed.");
		}

	} catch (Exception e) {
		log4j.info("varErrorMsgInCustomViewTable " + "function failed" + e);
		strErrorMsg = "varErrorMsgInCustomViewTable" + " function failed"
				+ e;
	}
	return strErrorMsg;
}
/***********************************************************************
'Description	:Add in preference notification for status type
'Precondition	:None
'Arguments		:selenium,pStrResourceValue,pStrSTValue,blnEmail,blnPager,blnWeb,blnSave
'Returns		:String
'Date	 		:06/25/2013
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'	<Date>                               <Name>
************************************************************************/

public String selectSTChangeNotifforTxtStatusTypeNew(Selenium selenium, String pStrResourceValue, String pStrSTValue,
		boolean blnEmail, boolean blnPager, boolean blnWeb,boolean blnSave)
		throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnEmail) {
				if (selenium.isChecked("css=input[name='emailInd'][value='"
						+ pStrResourceValue + "|" + pStrSTValue + "|T|']") == false)
					selenium.click("css=input[name='emailInd'][value='"
							+ pStrResourceValue + "|" + pStrSTValue + "|T|']");

			}

			if (blnPager) {
				if (selenium.isChecked("css=input[name='pagerInd'][value='"
						+ pStrResourceValue + "|" + pStrSTValue + "|T|']") == false)
					selenium.click("css=input[name='pagerInd'][value='"
							+ pStrResourceValue + "|" + pStrSTValue + "|T|']");

			}

			if (blnWeb) {
				if (selenium.isChecked("css=input[name='webInd'][value='"
						+ pStrResourceValue + "|" + pStrSTValue + "|T|']") == false)
					selenium.click("css=input[name='webInd'][value='"
							+ pStrResourceValue + "|" + pStrSTValue + "|T|']");

			}

			if (blnSave) {
				selenium.click(propElementDetails
						.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				do {
					try {

						assertEquals("My Status Change Preferences",
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
					assertEquals("My Status Change Preferences",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("My Status Change Preferences page is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "My Status Change Preferences page is NOT displayed"
							+ Ae;
					log4j.info("My Status Change Preferences page is NOT displayed"
							+ Ae);
				}
			}

		} catch (Exception e) {
			log4j.info("selectSTChangeNotifforTxtStatusType function failed"
					+ e);
			strErrorMsg =strErrorMsg+ "selectSTChangeNotifforTxtStatusType function failed"
					+ e;
		}
		return strErrorMsg;
	}

/***********************************************************************
'Description	:find resources and add to custom view
'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
				strState
'Returns		:String
'Date	 		:9-May-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/
	public String verifyResourceInFindResPage(Selenium selenium,
			String strResource,String strResFormat, boolean blnRes, boolean blnAdd) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnRes) {
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResFormat + "']"));
					log4j.info("Resource " + strResource + " is Retrived");
					log4j.info("Resource is displayed in format < "+strResFormat+" > ");
					
				} catch (AssertionError Ae) {
					log4j.info("Resource " + strResource + " is NOT found");
					strReason = "Resource " + strResource + " is NOT found";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResource + "']"));
					log4j.info("Resource " + strResource + " is Retrived");
					log4j.info("Resource is NOT displayed in format < "+strResFormat+" > ");
				} catch (AssertionError Ae) {
					log4j.info("Resource " + strResource + " is NOT found");
					strReason = "Resource " + strResource + " is NOT found";
				}
			}
			if (blnAdd) {
				// select the resource
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
						+ strResFormat + "']/parent::tr/td[1]/input");
				// click on Add To Custom View
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.AddToCustomView"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("findResourcesAndAddToCustomView function failed" + e);
			strReason = "findResourcesAndAddToCustomView function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:check status types in My status change preferences
	'Arguments		:selenium,strStatuTypes
	'Returns		:String
	'Date	 		:24-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String checkStatTypeInMyStatusChangePrefsNew(Selenium selenium,
			String strStatuTypes,String strResource, String strRowCount) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr["
								+ strRowCount
								+ "]/td[3][text()='"
								+ strStatuTypes + "']"));
				log4j.info("Status Type " + strStatuTypes
						+ " is displayed in My Status Change Preferences for "+strResource);
			} catch (AssertionError Ae) {
				strReason = strReason + "Status Type " + strStatuTypes
						+ " is NOT displayed in My Status Change Preferences for "+strResource;
				log4j.info("Status Type " + strStatuTypes
						+ " is NOT displayed in My Status Change Preferences for "+strResource);
			}
		} catch (Exception e) {
			log4j.info("checkStatTypeInMyStatusChangePrefsNew function failed"
					+ e);
			strReason = "checkStatTypeInMyStatusChangePrefsNew function failed"
					+ e;
		}
		return strReason;
	}
	/***************************************************************
	'Description	:Drag and drop the status type to particular section from another section and save
	'Precondition	:None
	'Arguments		:selenium,strStatType,strSection1,blnCreateSec,strSection2
	'Returns		:strReason
	'Date	 		:8-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'8-Oct-2012                                   <Name>
	 * @throws Exception 
	***************************************************************/
	public String dragAndDropRSFormOneRTToAnother(Selenium selenium,
			String strResource[], String strRTName1, String strRTName2) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {

			
			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strRTName1 + "')]"));
					break;
				} catch (AssertionError Ae) {
					intCnt++;
					Thread.sleep(1000);
				} catch (Exception e) {

					intCnt++;
					Thread.sleep(1000);

				}
			} while (intCnt < 60);
			
			
			try {
				assertTrue(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strRTName2 + "')]"));
				log4j.info("Resource type " + strRTName2 + " is displayed");

				// click on Uncategorized - does NOT show in view

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strRTName1 + "')]");
				for (String strStRsc : strResource) {
					// drag and drop status type
					selenium.dragAndDropToObject(
							"//li[@class='item']/div[contains(text(),'"
										+ strStRsc + "')]",
							"//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strRTName2 + "')]");

				}

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strRTName2 + "')]");
				for (String strStRsc : strResource) {
					try {
						assertTrue(selenium
								.isElementPresent("//li[@class='item']/div[contains(text(),'"
										+ strStRsc + "')]"));
						assertTrue(selenium
								.isVisible("//li[@class='item']/div[contains(text(),'"
										+ strStRsc + "')]"));
						log4j.info(strStRsc
								+ " Resource is displayed in section "
								+ strRTName2);

					} catch (AssertionError ae) {
						log4j.info(strStRsc
								+ " Resource is NOT displayed in section "
								+ strRTName2);
						strReason = strReason + " " + strStRsc
								+ " Resource is NOT displayed in section "
								+ strRTName2;
					}
				}
				
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				
				intCnt=0;
				do{
					try {

						assertEquals("Custom View - Table", selenium.getText(propElementDetails
								.getProperty("Header.Text")));
						break;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<70);
				
				try {
					assertEquals("Custom View - Table", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Custom View - Table screen is displayed");

				} catch (AssertionError ae) {
					log4j.info("Custom View - Table screen is NOT displayed");
					strReason = "Custom View - Table screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("Section " + strRTName2 + " is NOT displayed");
				strReason = "Section " + strRTName2 + " is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("dragAndDropSTFormOneSectionToAnother function failed"
					+ e);
			strReason = "dragAndDropSTFormOneSectionToAnother function failed"
					+ e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:find resources and add to custom view
	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String AddResourceToCustomViewNotification(Selenium selenium,String strResource,boolean blnAdd){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
						
			try {
				try{
					assertTrue(selenium.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']"));
					log4j.info("Resource "+strResource+" is Retrived");
				} catch (AssertionError Ae) {
					log4j.info("Resource "+strResource+" is NOT Retrived");
					strReason="Resource "+strResource+" is NOT Retrived";
					
				}
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
						+ strResource + "']/parent::tr/td[1]/input");
				Thread.sleep(1000);
				
				if(blnAdd){
				// click on Add To Custom View
				selenium.click(propElementDetails.getProperty("StatusChangePrefs.FindRes.Notifications"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Edit My Status Change Preferences", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info("Edit My Status Change Preferences page is  displayed");

				} catch (AssertionError Ae) {
					strReason = "Edit Custom View page is NOT displayed" + Ae;
					log4j.info("Edit Custom View page is NOT displayed" + Ae);
				}
				}
			} catch (AssertionError Ae) {
				log4j.info("Resource " + strResource + " is NOT found");
				strReason = "Resource " + strResource + " is NOT found";
			}
			
		} catch (Exception e) {
			log4j.info("AddResourceToCustomView function failed" + e);
			strReason = "AddResourceToCustomView function failed" + e;
		}
		return strReason;
	}
	
	/*****************************************************************
	'Description	:Navigate to Set Up Your Password by clicking on 'Click here' link
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:02-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/

	public String clickOnHereLinkNavToEditCustomViewPage(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			// Click on here link
			selenium.click("link=Click here to setup your custom view");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Edit Custom View", selenium
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
				assertEquals("Edit Custom View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Custom View page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View page is NOT displayed" + Ae;
				log4j.info("Edit Custom View page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("clickOnHereLinkNavToEditCustomViewPage function failed"
					+ e);
			strErrorMsg = "clickOnHereLinkNavToEditCustomViewPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:find resources and add to custom view
	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:9-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String AddResourceToCustomVrfyErrorMsg(Selenium selenium,
			String strResource, boolean blnAdd) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
						+ strResource + "']/parent::tr/td[1]/input");
				Thread.sleep(1000);

				if (blnAdd) {
					// click on Add To Custom View
					selenium.click(propElementDetails
							.getProperty("EditCustomView.FindRes.AddToCustomView"));
					selenium.waitForPageToLoad(gstrTimeOut);
					try {
						assertEquals(
								"The following error occurred on this page:",
								selenium.getText("css=span.emsErrorTitle"));
						assertEquals(
								"You may not add Sub-Resources to your custom view."
										+ " (" + strResource + ")",
								selenium.getText("css=div.emsError > ul > li"));

						log4j.info("'The following errors occurred on this page:"
								+ "�You may not add Sub-Resources to your custom view."
								+ " ( < "
								+ strResource
								+ " > )'"
								+ "is displayed on 'Find Resources' screen.");

					} catch (AssertionError Ae) {
						strReason = "'The following errors occurred on this page:"
								+ "�You may not add Sub-Resources to your custom view."
								+ " ( < "
								+ strResource
								+ " > )'"
								+ "is NOT displayed on 'Find Resources' screen."
								+ Ae;
						log4j.info("'The following errors occurred on this page:"
								+ "�You may not add Sub-Resources to your custom view."
								+ " ( < "
								+ strResource
								+ " > )'"
								+ "is NOT displayed on 'Find Resources' screen."
								+ Ae);
					}
				}
			} catch (AssertionError Ae) {
				log4j.info("Resource " + strResource + " is NOT found");
				strReason = "Resource " + strResource + " is NOT found";
			}

		} catch (Exception e) {
			log4j.info("AddResourceToCustomVrfyErrorMsg function failed" + e);
			strReason = "AddResourceToCustomVrfyErrorMsg function failed" + e;
		}
		return strReason;
	}
	
	//start//verifySTInEditMySTPrfPageInUncategorizedSectionFalseCond//
	/*******************************************************************************************
	' Description	: Verify Status types is not present  in Edit My Status Change Preferences page
	' Precondition	: N/A 
	' Arguments		: selenium, strSTName
	' Returns		: String 
	' Date			: 02/09/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifySTInEditMySTPrfPageInUncategorizedSectionFalseCond(
			Selenium selenium, String[] strSTName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Edit My Status Change Preferences",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Edit My Status Change Preferences page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'stt_')]"
									+ "/thead/tr/th[2][text()='Uncategorized']"));
					log4j.info("Section Uncategorized is Present");

					for (String s : strSTName) {

						try {

							assertFalse(selenium
									.isElementPresent("//table[starts-with(@id,'stt_')]"
											+ "/thead/tr/th[2][text()='Uncategorized']/ancestor::"
											+ "table/tbody/tr/td/div[text()='"
											+ s + "']"));

							log4j.info("	"
									+ s
									+ " is NOT displayed under section Uncategorized in"
									+ " the 'Edit My Status Change Preferences' screen. ");

						} catch (AssertionError Ae) {
							strErrorMsg = "	"
									+ s
									+ " is displayed under section Uncategorized "
									+ "in the 'Edit My Status Change Preferences' screen. "
									+ Ae;
							log4j.info("	"
									+ s
									+ " is displayed under section Uncategorized in"
									+ " the 'Edit My Status Change Preferences' screen. "
									+ Ae);
						}
					}

				} catch (AssertionError Ae) {
					strErrorMsg = "Section Uncategorized is NOT Present" + Ae;
					log4j.info("Section Uncategorized is NOT Present" + Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit My Status Change Preferences page is NOT displayed"
						+ Ae;
				log4j.info("Edit My Status Change Preferences page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("verifySTInInEditMySTPrfPageInUncategorizedSection function failed"
					+ e);
			strErrorMsg = "verifySTInInEditMySTPrfPageInUncategorizedSection function failed"
					+ e;
		}
		return strErrorMsg;
	}
			//end//verifySTInEditMySTPrfPageInUncategorizedSectionFalseCond//
	/***********************************************************************
	'Description	:Verify that the preference set values are not retained in My status
	 				 change preference page.
	'Precondition	:None
	'Arguments		:selenium,strStatuTypes,strStatus,strResTypeName,strEmail,
					 strText,strWeb
	'Returns		:String
	'Date	 		:11-Sep-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String vfyPrefrnceSetValuesNotRetained(Selenium selenium,
			String strStatuTypes, String strStatus, String strEmail,
			String strText, String strWeb,String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
								+ strResource + "']"));
				
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
								+ strStatuTypes + "']"));
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[4][text()='"
								+ strStatus + "']"));
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[5][contains(text(),'"
								+ strEmail + "')]"));
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][contains(text(),'"
								+ strText + "')]"));
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[7][contains(text(),'"
								+ strWeb + "')]"));
				log4j
						.info("Status Type "
								+ strStatuTypes
								+ " is NOT displayed with appropriate notification methods");
				log4j .info("Previously set preferences for resource 'RS' are NOT retained.");
				
			} catch (AssertionError Ae) {
				strReason = strReason
						+ " Status Type "
						+ strStatuTypes
						+ " is displayed with appropriate notification methods";
				log4j .info("Previously set preferences for resource 'RS' are retained.");
			}

		} catch (Exception e) {
			log4j
					.info("checkStatTypeNotPrsntInMyStatusChangePrefs function failed"
							+ e);
			strReason = "checkStatTypeNotPrsntInMyStatusChangePrefs function failed"
					+ e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:find resources and verify that the resource is not retrieved

	'Precondition	:None
	'Arguments		:selenium,strResource,strCategory,strRegion,strCityZipCd,
					strState
	'Returns		:String
	'Date	 		:11-Sep-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String vfyResourceNotPresInFindResrcPge(Selenium selenium,String strResource){
		String strReason="";
		try{
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
						
			try {
				try{
					assertFalse(selenium.isElementPresent("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']"));
					log4j.info("Resource "+strResource+" is NOT Retrived");
				} catch (AssertionError Ae) {
					log4j.info("Resource "+strResource+" is Retrived");
					strReason="Resource "+strResource+" is Retrived";
					
				}
				
			} catch (AssertionError Ae) {
				log4j.info("Resource " + strResource + " is NOT found");
				strReason = "Resource " + strResource + " is NOT found";
			}
			
		} catch (Exception e) {
			log4j.info("vfyResourceNotPresInFindResrcPge function failed" + e);
			strReason = "vfyResourceNotPresInFindResrcPge function failed" + e;
		}
		return strReason;
	}
	 /***********************************************************************
	'Description	: check status types in Edit My status change preferences
	'Precondition	:None
	'Arguments		:selenium,strStatusType,strNotifData
					
	'Returns		:String
	'Date	 		:24-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	public String checkStatTypeNotInEditMyStatChangePrefs(Selenium selenium,
			String strStatusType) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertFalse(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + strStatusType
								+ "']"));
				log4j
						.info("Status Type "
								+ strStatusType
								+ " is NOT displayed with appropriate notification methods");
			} catch (AssertionError Ae) {
				strReason = strReason
						+ " Status Type "
						+ strStatusType
						+ " is displayed with appropriate notification methods";
				log4j
						.info("Status Type "
								+ strStatusType
								+ " is  displayed with appropriate notification methods");
			}

		} catch (Exception e) {
			log4j
					.info("checkStatTypeNotInEditMyStatChangePrefs function failed"
							+ e);
			strReason = "checkStatTypeNotInEditMyStatChangePrefs function failed"
					+ e;
		}
		return strReason;
	}
	 /***********************************************************************
	  'Description  :Verify status change notification preference for particular user is displayed
	  'Precondition :None
	  'Arguments    :selenium,strUserName
	  'Returns      :String
	  'Date         :25-May-2012
	  'Author       :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '25-May-2012                               <Name>
	  ************************************************************************/
	  
public String navStatusChangeNotiFrmEditUserPgeNew(Selenium selenium) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		int intCnt = 0;
		do {
			try {

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Pref.StatusChangeNotification")));
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
				.getProperty("Pref.StatusChangeNotification"));
		selenium.waitForPageToLoad(gstrTimeOut);

		intCnt = 0;
		
		
			try {

				assertEquals("My Status Change Preferences", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
				.info("'My Status Change preferences' screen is displayed. ");
				
			} catch (AssertionError Ae) {
				Thread.sleep(1000);
				intCnt++;

			} catch (Exception Ae) {
				Thread.sleep(1000);
				intCnt++;
			}
		
		
	} catch (Exception e) {
		log4j
				.info("navigate to StatusChangeNotiFrmEditUser Page function failed"
						+ e);
		log4j
		.info("'My Status Change preferences' screen is NOT displayed. ");
		strErrorMsg = "navigate to StatusChangeNotiFrmEditUser Page function failed"
				+ e;
	}
	return strErrorMsg;
	}

/***********************************************************************
'Description	:navigate to edit custom view options, select the status types
				and save
'Precondition	:None
'Arguments		:selenium,strStatusType
'Returns		:String
'Date	 		:7-June-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/
	public String editCustomViewOptionsForStdStatuses(Selenium selenium,
			String strStdStatusType[]) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditCustomView.Options"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Edit Custom View Options (Columns)", selenium
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
				assertEquals("Edit Custom View Options (Columns)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed. ");
				if (strStdStatusType != null) {
					// Select the status types
					for (int intOpt = 0; intOpt < strStdStatusType.length; intOpt++) {
						if (selenium
								.isChecked("//div/input[@name='viewColumnID'][@value='sst_"
										+ strStdStatusType[intOpt] + "']") == false)
							selenium
									.click("//div/input[@name='viewColumnID'][@value='sst_"
											+ strStdStatusType[intOpt] + "']");
					}
				}

				// click on save
				selenium
						.click(propElementDetails
								.getProperty("EditCustomView.EditCustomViewOptions.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				strReason = "'Edit Custom View Options (Columns)' screen is displayed.";
				log4j
						.info("'Edit Custom View Options (Columns)' screen is displayed.");
			}
		} catch (Exception e) {
			log4j.info("editCustomViewOptions function failed" + e);
			strReason = "editCustomViewOptions function failed" + e;
		}
		return strReason;
	}

	//start//navToPrefChallengeSetup//
	/*******************************************************************************************
	' Description	: Function to navigate to challenge setup page
	' Precondition	: N/A 
	' Arguments		: selenium
	' Returns		: String 
	' Date			: 07/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String navToPrefChallengeSetup(Selenium selenium) throws Exception {
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
			selenium.mouseOver(propElementDetails.getProperty("Preferences"));
			selenium.click(propElementDetails.getProperty("Preferences.challengeSetup"));
			selenium.waitForPageToLoad(gstrTimeOut);
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {
					assertEquals("Challenge Setup", selenium.getText(propElementDetails
									.getProperty("pref.challengeHeader.txt")));
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
				assertEquals("Challenge Setup", selenium.getText(propElementDetails
								.getProperty("pref.challengeHeader.txt")));
				log4j.info("'Challenge Setup' page is displayed with instruction");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Challenge Setup' page is NOT displayed.");
				lStrReason = lStrReason + "; "
						+ "'Challenge Setup' page is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason
					+ "; navTonavToPrefChallengeSetup function failed"
					+ e.toString();
		}

		return lStrReason;
	}
	//end//navToPrefChallengeSetup//
	
	//start//selQusAndProvideAnswerInChallengeSetup//
	/*******************************************************************************************
	' Description	: Function to select question and provide answer in challenge setup page
	' Precondition	: N/A 
	' Arguments		: selenium, strQuestion, strAnswer
	' Returns		: String 
	' Date			: 07/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String selQusAndProvideAnswerInChallengeSetup(Selenium selenium,
			String strQuestion, String strAnswer) throws Exception {
		
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
			int intCnt = 0;
			do {
				try {
					assertEquals("Set Up Your Password Challenge",
							selenium.getText(propElementDetails
									.getProperty("SetUpPwd")));
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
				assertEquals("Set Up Your Password Challenge", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				selenium.select(propElementDetails
						.getProperty("pref.challengeSetup.question"), "label="
						+ strQuestion);
				selenium.type(propElementDetails
						.getProperty("pref.challengesetup.answer"), strAnswer);
				selenium.click(propElementDetails
						.getProperty("pref.challengesetup.submit"));
				log4j.info(" challenge question is answered ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'challenge question is NOT  answered");
				lStrReason = lStrReason + "; "
						+ "'challenge question is  NOT answered";
			}

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				do {
					try {
						assertEquals("Password Challenge", selenium
								.getText(propElementDetails.getProperty("SetUpPwd")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception e) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);
				assertEquals("Password Challenge", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				assertEquals("Your password challenge question and answer have been saved.",selenium
						.getText(propElementDetails.getProperty("pref.challengeSubHeader")));
				log4j.info("'Password Challenge' page is displayed with instruction Your"
				+ " password challenge question and answer have been saved.");
				selenium.selectFrame("relative=up");
				selenium.click("link=close");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Password Challenge' page is NOT displayed");
				lStrReason = lStrReason + "; "
						+ "'Password Challenge' page is NOT displayed";

			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason
					+ "; selQusAndProvideAnswerInChallengeSetup function failed"
					+ e.toString();
		}

		return lStrReason;
	}
	//end//selQusAndProvideAnswerInChallengeSetup//
	
	//start//chkDataRetainedInChallengeSetup//
	/*******************************************************************************************
	' Description		: Function to chek provided data is retained in challenge setup page
	' Precondition		: N/A 
	' Arguments			: selenium, strQuestion, strAnswer
	' Returns			: String 
	' Date				: 11/11/2013
	' Author			: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/	
	public String chkDataRetainedInChallengeSetup(Selenium selenium,
			String strQuestion, String strAnswer) throws Exception {
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
			int intCnt = 0;
			do {
				try {
					assertEquals("Set Up Your Password Challenge",
							selenium.getText(propElementDetails
									.getProperty("SetUpPwd")));
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
				assertEquals("Set Up Your Password Challenge", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				assertEquals(strQuestion, selenium
						.getSelectedLabel(propElementDetails
								.getProperty("pref.challengeSetup.question")));
				assertEquals(strAnswer, selenium.getValue(propElementDetails
						.getProperty("pref.challengesetup.answer")));

				log4j.info(" challenge setup question and answer retained");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'challenge setup question and answer NOT retained");
				lStrReason = lStrReason + "; "
						+ "'challenge setup question and answer NOT retained";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason
					+ "; selQusAndProvideAnswerInChallengeSetup function failed"
					+ e.toString();
		}

		return lStrReason;
	}
	//end//chkDataRetainedInChallengeSetup//
	

	//start//cancelChallengeSetup//
	/*******************************************************************************************
	' Description	: Function to select question and answer in challenge setup page and click on close button
	' Precondition	: N/A 
	' Arguments		: selenium, strQuestion, strAnswer
	' Returns		: String 
	' Date			: 15/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/	
	public String cancelChallengeSetup(Selenium selenium,
			String strQuestion, String strAnswer) throws Exception {
		
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
			int intCnt = 0;
			do {
				try {
					assertEquals("Set Up Your Password Challenge",
							selenium.getText(propElementDetails
									.getProperty("SetUpPwd")));
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
				assertEquals("Set Up Your Password Challenge", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				selenium.select(propElementDetails
						.getProperty("pref.challengeSetup.question"), "label="
						+ strQuestion);
				selenium.type(propElementDetails
						.getProperty("pref.challengesetup.answer"), strAnswer);
				selenium.selectFrame("relative=up");
				selenium.click("link=close");				
				log4j.info(" challenge question is cancled");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'challenge question is NOT  cancled");
				lStrReason = lStrReason + "; "
						+ "'challenge question is  NOT cancled";
			}			

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason
					+ "; cancelChallengeSetup function failed"
					+ e.toString();
		}

		return lStrReason;
	}
	
	//end//cancelChallengeSetup//

}
