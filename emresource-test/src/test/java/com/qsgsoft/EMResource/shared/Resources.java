package com.qsgsoft.EMResource.shared;


import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/******************************************************************
' Description :This class includes common functions of resource types
' Precondition:
' Date		  :6-April-2012
' Author	  :QSG
'-----------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class Resources {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.Resources");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	
	String gstrTimeOut="";

	ReadData rdExcel;
	
	/***********************************************************************
	'Description	:Verify Resources  list page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:14-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'14-May-2012                               <Name>
	************************************************************************/
	
	public String navResourcesList(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
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
							.isElementPresent(propElementDetails
									.getProperty("SetUP.SetUpLink")));
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
			

			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));

			selenium.click(propElementDetails
					.getProperty("SetUP.ResourcesLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt = 0;
			do {
				try {

					assertEquals("Resource List", selenium
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
			} while (intCnt < 70);

			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Resource List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Resource List page is displayed");

				} catch (AssertionError Ae) {

					strErrorMsg = "Resource  List page is NOT displayed" + Ae;
					log4j.info("Resource  List page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("navResourcesList function failed" + e);
			strErrorMsg = "navResourcesList function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/********************************************************************************************************
	 'Description :Navigating assign users of res page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/
	
	public String navToAssignUsersOFRes(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']/parent::tr/td[1]/a[text()='Users']");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Assign Users to " + strResource, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Assign Users to " + strResource
						+ "screen is displayed ");
			} catch (AssertionError Ae) {

				strReason = "Assign Users to " + strResource
						+ "screen is NOT displayed" + Ae;
				log4j.info("Assign Users to " + strResource
						+ "screen is NOT displayed ");
			}

		} catch (Exception e) {
			log4j.info("navToAssignUsersOFres function failed" + e);
			strReason = "navToAssignUsersOFres function failed" + e;
		}
		return strReason;
	}

	
	
	 /***********************************************************************
	 'Description :select por deselect ST in Edit Resource-Level Status Types page
	 'Precondition :None
	 'Arguments  :selenium, strST
	 'Returns  :String
	 'Date    :8-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '8-June-2012                               <Name>
	 ************************************************************************/
	
	
	 
	public String[] selDeselctSTInEditRSLevelPageWithTime(Selenium selenium,
			String strSTValue, boolean blnSelct, String strTimeFormat)
			throws Exception {

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
			// click on Edit link for Resource

			if (blnSelct) {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strSTValue + "']") == false) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTValue + "']");

				}
			} else {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strSTValue + "']")) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTValue + "']");

				}
			}

			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			// System Time
			strReason[1] = dts.timeNow(strTimeFormat);
			System.out.println(strReason[1]);

			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Resource List page is displayed");

			} catch (AssertionError Ae) {

				strReason[0] = "Resource List page is NOT displayed" + Ae;
				log4j.info("Resource List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("selDeselctSTInEditRSLevelPage function failed" + e);
			strReason[0] = "selDeselctSTInEditRSLevelPage function failed" + e;
		}
		return strReason;
	}
	
	

	/********************************************************************************************************
	'Description	:Save the Resource and verify resource details in Resource list page
	'Precondition	:None
	'Arguments		:selenium,strResource,strHavBed,strAHAId,strAbbrev,strResType
	'Returns		:String
	'Date	 		:16-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-May-2012                               <Name>
	***********************************************************************************************************/
	
	public String VerifyResource(Selenium selenium, String strResource,
			boolean blnRes) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			if (blnRes) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
									+ strResource + "']"));
					log4j
							.info("Resource "
									+ strResource
									+ " is displayed in Resource List page with all list of Resources in the region");
				} catch (AssertionError Ae) {

					strReason = "Resource "
							+ strResource
							+ " is NOT displayed in Resource List page with all list of Resources";
					log4j
							.info("Resource "
									+ strResource
									+ " is NOT displayed in Resource List page with all list of Resources in the region");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
									+ strResource + "']"));
					log4j
							.info("Resource "
									+ strResource
									+ " is NOT displayed in Resource List page or No resources are displayed");
				} catch (AssertionError Ae) {
					strReason = "Resource "
							+ strResource
							+ " is displayed in Resource List page with all list of Resources";
					log4j
							.info("Resource "
									+ strResource
									+ " is displayed in Resource List page with all list of Resources in the region");
				}
			}

		} catch (Exception e) {
			log4j.info("VerifyResource function failed" + e);
			strReason = "VerifyResource function failed" + e;
		}
		return strReason;
	}
	
	  /*******************************************************************
	   'Description :Select and deselect Status Type in Refine Visible
	       Status Type screen.
	   'Precondition :None
	   'Arguments  :selenium,strStatTypeVal,blnSelStatType
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                <Name>
	   ********************************************************************/

	public String cancelAndNavToRSListPage(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click(propElementDetails
					.getProperty("EditCustomViewSreenCancelButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("User is taken to Resource List screen.");
			} catch (AssertionError Ae) {

				strErrorMsg = "User is  NOT taken to Resource List screen.";
				log4j.info("User is  NOT taken to Resource List screen.");
			}
		} catch (Exception e) {
			log4j.info("cancelAndNavToRSListPage function failed" + e);
			strErrorMsg = "cancelAndNavToRSListPage function failed" + e;
		}
		return strErrorMsg;
	}

	  /*******************************************************************
	   'Description :check status types in edit res level page
	   'Precondition :None
	   'Arguments  :selenium,strStatTypeVal,blnSelStatType
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                <Name>
	   ********************************************************************/
	public String chkSTInEditResLevelPage(Selenium selenium, String strResType,
			String strResource, String strSTvalue, String strStatusType,
			boolean blnSTSval) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnSTSval) {
				try {

					assertTrue(selenium.isEditable("css=input[value='"
							+ strSTvalue + "']"));
					assertFalse(selenium.isChecked("css=input[value='"
							+ strSTvalue + "']"));
					log4j
							.info("Other status types in the region are available to be added to the "
									+ strResource
									+ "check box corresponding "
									+ strStatusType
									+ " is unchecked and enabled.");
				} catch (AssertionError Ae) {

					strReason = "Other status types in the region are NOT available to be added to the "
							+ strResource + "";
					log4j
							.info("Other status types in the region are NOT available to be added to the "
									+ strResource + "");
				}
			} else {
				try {
					assertFalse(selenium.isEditable("css=input[value='"
							+ strSTvalue + "']"));
					assertTrue(selenium.isChecked("css=input[value='"
							+ strSTvalue + "']"));
					log4j.info(strStatusType
							+ "associated with the resource type" + strResType
							+ "cannot be removed." + "check box corresponding "
							+ strStatusType + " is checked and disabled.");

				} catch (AssertionError Ae) {

					strReason = strStatusType
							+ "associated with the resource type" + strResType
							+ "can be removed.";
					log4j.info(strStatusType
							+ "associated with the resource type" + strResType
							+ "can be removed.");
				}
			}

		} catch (Exception e) {
			log4j.info("chkSTInEditResLevelPage function failed" + e);
			strReason = "chkSTInEditResLevelPage function failedd" + e;
		}
		return strReason;
	}
	
	 /*******************************************************************
	   'Description :check status types in edit res level page
	   'Precondition :None
	   'Arguments  :selenium,strStatTypeVal,blnSelStatType
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                <Name>
	   ********************************************************************/
	public String chkSTSelectedOrNotEditRsLevelPage(Selenium selenium,
			String strSTvalue, String strStatusType, boolean blnSTSval)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnSTSval) {
				try {

					assertTrue(selenium.isEditable("css=input[value='"
							+ strSTvalue + "']"));
					assertTrue(selenium.isChecked("css=input[value='"
							+ strSTvalue + "']"));
					log4j.info("check box corresponding " + strStatusType
							+ " is selected and enabled.");
				} catch (AssertionError Ae) {

					strReason = "check box corresponding " + strStatusType
							+ " is deselected and disabled.";
					log4j.info("check box corresponding " + strStatusType
							+ " is deselected and disabled.");
				}
			} else {
				try {
					assertTrue(selenium.isEditable("css=input[value='"
							+ strSTvalue + "']"));
					assertFalse(selenium.isChecked("css=input[value='"
							+ strSTvalue + "']"));
					log4j.info("check box corresponding " + strStatusType
							+ " is deselected and enabled.");

				} catch (AssertionError Ae) {

					strReason = "check box corresponding " + strStatusType
							+ " is selected and disabled.";
					log4j.info("check box corresponding " + strStatusType
							+ " is selected and disabled.");
				}
			}

		} catch (Exception e) {
			log4j.info("chkSTSelectedOrNotEditRsLevelPage function failed" + e);
			strReason = "chkSTSelectedOrNotEditRsLevelPage function failedd"
					+ e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Verify resource is created
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:14-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'27-Nov-2012                               <Name>
	************************************************************************/

	public String createResourceWitLookUPadres(Selenium selenium,
			String strResName, String strRSAbbr, String strRTLable,
			String strFName, String strLName, String strState,
			String strCountry, String strStdRT) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		Resources objResources = new Resources();

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateRes.CreateNewResource"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Create New Resource", selenium
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
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Create New Resource", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Create New Resource page is displayed");

					selenium.type(propElementDetails
							.getProperty("CreateResource.Name"), strResName);
					selenium.type(propElementDetails
							.getProperty("CreateResource.Abbreviation"),
							strRSAbbr);
					selenium.select(propElementDetails
							.getProperty("CreateResource.ResourceType"),
							"label=" + strRTLable);

					selenium.select(propElementDetails
							.getProperty("Pref.FindResources.State"), "label="
							+ strState);

					intCnt = 0;
					boolean blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//select[@name='county']/option[text()='"+strCountry+"']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(20000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);

					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactFName"),
							strFName);
					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactLName"),
							strLName);

					selenium.select(propElementDetails
							.getProperty("CreateResource.County"), "label="
							+ strCountry);
					selenium.select(propElementDetails
							.getProperty("CreateResource.StandResType"),
							"label=" + strStdRT);
					selenium
							.click(propElementDetails
									.getProperty("CreateRes.CreateNewResource.LookUpAddr"));

				
					do {
						try {
							assertTrue(selenium.isVisible("//div[@id='map']"));

							break;
						} catch (AssertionError Ae) {
							Thread.sleep(2000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(2000);
							intCnt++;

						}
					} while (intCnt < 60);
					
			
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try{
							assertTrue(selenium
									.isElementPresent(propElementDetails.getProperty("Save")));
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
				
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					int intRSCount = 0;
					while (selenium
							.isElementPresent("//div[@id='topSubNav']/h1[text()='Resource List']") == false
							&& intRSCount < 10) {
						try {
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);
							break;
						} catch (Exception e) {
							intRSCount++;
						}

					}

					intCnt=0;
					do{
						try{
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[5][text()='"
											+ strResName
											+ "']"));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						}
						catch(Exception e){
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<30);
					

					strErrorMsg = objResources.navResourcesList(selenium);

					try {
						assertEquals("", strErrorMsg);
						
						intCnt=0;
						do{
							try{
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table/tbody"
												+ "/tr/td[5][text()='"
												+ strResName
												+ "']"));
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
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[5][text()='"
											+ strResName
											+ "']"));

							log4j.info(" Resource " + strResName
									+ "  is  displayed");
						} catch (AssertionError Ae) {

							strErrorMsg = " Resource " + strResName
									+ "  is NOT displayed" + Ae;
							log4j.info(" Resource " + strResName
									+ "  is NOT displayed" + Ae);
						}
					} catch (AssertionError Ae) {
						log4j.info(strErrorMsg);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Create New Resource page is NOT displayed"
							+ Ae;
					log4j
							.info("Create New Resource page is NOT displayed"
									+ Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("createResourceWitLookUPadres function failed" + e);
			strErrorMsg = "createResourceWitLookUPadres function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:navigate to create resource page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:16-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-May-2012                               <Name>
	************************************************************************/
	
	public String navToCreateResourcePage(Selenium selenium) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		int intCnt=0;
		do{
			try{
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("CreateRes.CreateNewResource")));
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
			// click on Create New Resource
			selenium.click(propElementDetails
					.getProperty("CreateRes.CreateNewResource"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt=0;
			do{
				try{
					assertEquals("Create New Resource", selenium
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
				assertEquals("Create New Resource", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create New Resource page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Create New Resource page is NOT displayed" + Ae;
				log4j.info("Create New Resource page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToCreateResourcePage function failed" + e);
			strReason = "navToCreateResourcePage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:navigate to edit resource page
	'Precondition	:None
	'Arguments		:selenium, strResource
	'Returns		:String
	'Date	 		:16-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-May-2012                               <Name>
	************************************************************************/
	
	public String navToEditResourcePage(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/"
							+ "tr/td[5][text()='" + strResource
							+ "']/parent::tr/td[1]/a[text()='Edit']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			// click on Edit link for Resource
			selenium.click("//div[@id='mainContainer']/table[2]/tbody/"
					+ "tr/td[5][text()='" + strResource
					+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Edit Resource", selenium
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
				assertEquals("Edit Resource", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Resource page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Edit Resource page is NOT displayed" + Ae;
				log4j.info("Edit Resource page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToEditResourcePage function failed" + e);
			strReason = "navToEditResourcePage function failed" + e;
		}
		return strReason;
	}
	
	/********************************************************************************************************
	'Description	:Fill all the necessary fields in create resource page
	'Precondition	:None
	'Arguments		:selenium, strResource, strAbbrv, strResType,strStandResType
					,blnHavBedData,blnShareReg,AHAId,strExternalId,blnTriagPart,strStreetAddr,strCity,strState,
					strZipCode,strCounty,strWebSite,strContFName,strContLName,strTitle,strContAddr,strContPh1,
					strContPh2,strContPh2,strContFax,strContEmail,strNotes
	'Returns		:String
	'Date	 		:16-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-May-2012                               <Name>
	***********************************************************************************************************/
	
	public String createResource_FillAllFields(Selenium selenium,
			String strResource, String strAbbrv, String strResType,
			String strStandResType, boolean blnHavBedData, boolean blnShareReg,
			String AHAId, String strExternalId, boolean blnTriagPart,
			String strStreetAddr, String strCity, String strState,
			String strZipCode, String strCounty, String strWebSite,
			String strContFName, String strContLName, String strTitle,
			String strContAddr, String strContPh1, String strContPh2,
			String strContFax, String strContEmail, String strNotes)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(
					propElementDetails.getProperty("CreateResource.Name"),
					strResource);
			selenium.type(propElementDetails
					.getProperty("CreateResource.Abbreviation"), strAbbrv);
			if (selenium.isElementPresent(propElementDetails
					.getProperty("CreateResource.ResourceType"))) {
				selenium.select(propElementDetails
						.getProperty("CreateResource.ResourceType"), "label="
						+ strResType);
			}

			selenium.select(propElementDetails
					.getProperty("CreateResource.StandResType"), "label="
					+ strStandResType);

			if (blnHavBedData) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ReportHavBedData")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateResource.ReportHavBedData"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ReportHavBedData")))
					selenium.click(propElementDetails
							.getProperty("CreateResource.ReportHavBedData"));
			}

			if (blnShareReg) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ShareWithOtherReg")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateResource.ShareWithOtherReg"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ShareWithOtherReg")))
					selenium.click(propElementDetails
							.getProperty("CreateResource.ShareWithOtherReg"));
			}

			selenium.type(propElementDetails
					.getProperty("CreateResource.AHAId"), AHAId);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ExternalId"), strExternalId);

			if (blnTriagPart) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ITriagePart")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateResource.ITriagePart"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ITriagePart")))
					selenium.click(propElementDetails
							.getProperty("CreateResource.ITriagePart"));
			}

			selenium.type(propElementDetails
					.getProperty("CreateResource.StreetAddr"), strStreetAddr);
			selenium.type(
					propElementDetails.getProperty("CreateResource.City"),
					strCity);
			selenium.select(propElementDetails
					.getProperty("CreateResource.State"), "label=" + strState);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ZipCode"), strZipCode);
			
			int intCnt = 0;
		     boolean blnCnt = true;
		     do {
		      try {
		       assertTrue(selenium
		         .isVisible("//select[@name='county']/option[text()='"+strCounty+"']"));

		       blnCnt = false;
		      } catch (AssertionError Ae) {
		       Thread.sleep(20000);
		       intCnt++;

		      }catch (Exception Ae) {
		       Thread.sleep(20000);
		       intCnt++;

		      }
		     } while (intCnt < 60 && blnCnt);
			selenium
					.select(propElementDetails
							.getProperty("CreateResource.County"), "label="
							+ strCounty);
			selenium.type(propElementDetails
					.getProperty("CreateResource.Website"), strWebSite);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactFName"), strContFName);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactLName"), strContLName);
			selenium.type(propElementDetails
					.getProperty("CreateResource.Title"), strTitle);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactAddr"), strContAddr);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactPhone1"), strContPh1);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactPhone2"), strContPh2);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactFax"), strContFax);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactEmail"), strContEmail);
			selenium.type(propElementDetails
					.getProperty("CreateResource.Notes"), strNotes);

		} catch (Exception e) {
			log4j.info("createResource_FillAllFields function failed" + e);
			strReason = "createResource_FillAllFields function failed" + e;
		}
		return strReason;
	}
	
	
	/********************************************************************************************************
	'Description	:Save the Resource and verify resource details in Resource list page
	'Precondition	:None
	'Arguments		:selenium,strResource,strHavBed,strAHAId,strAbbrev,strResType
	'Returns		:String
	'Date	 		:16-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-May-2012                               <Name>
	***********************************************************************************************************/
	
	public String saveAndVerifyResource(Selenium selenium, String strResource,
			String strHavBed, String strAHAId, String strAbbrev,
			String strResType) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		int intCnt=0;
		do{
			try{
				assertTrue(selenium
						.isElementPresent(propElementDetails.getProperty("Save")));
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
			// click on save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Resource List", selenium
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
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource + "']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[3][text()='"
								+ strHavBed + "']"));

				/*
				 * if(strAHAId!=""){ assertTrue(selenium.isElementPresent(
				 * "//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
				 * + strResource +
				 * "']/parent::tr/td[4][text()='"+strAHAId+"']"));
				 * 
				 * }
				 */

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[6][text()='"
								+ strAbbrev + "']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[7][text()='"
								+ strResType + "']"));
				log4j
						.info("Resource "
								+ strResource
								+ " is displayed in Resource List page with all other data");

			} catch (AssertionError Ae) {

				strReason = "Resource "
						+ strResource
						+ " is NOT displayed in Resource List page with all other data";
				log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed in Resource List page with all other data");
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifyResource function failed" + e);
			strReason = "saveAndVerifyResource function failed" + e;
		}
		return strReason;
	}

	/********************************************************************************************************
	'Description	:verify resource details in Resource list page
	'Precondition	:None
	'Arguments		:selenium,strResource,strHavBed,strAHAId,strAbbrev,strResType
	'Returns		:String
	'Date	 		:06-11-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                              <Name>
	***********************************************************************************************************/
	
	
	public String verifyResourceInfoInResList(Selenium selenium,
			String strResource, String strHavBed, String strAHAId,
			String strAbbrev, String strResType) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource + "']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[3][text()='"
								+ strHavBed + "']"));

				assertEquals(
						strAHAId,
						selenium
								.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
										+ strResource + "']/parent::tr/td[4]"));

				/*
				 * if(strAHAId!=""){ assertTrue(selenium.isElementPresent(
				 * "//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
				 * + strResource +
				 * "']/parent::tr/td[4][text()='"+strAHAId+"']"));
				 * 
				 * }
				 */

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[6][text()='"
								+ strAbbrev + "']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[7][text()='"
								+ strResType + "']"));
				log4j
						.info("Resource "
								+ strResource
								+ " is displayed in Resource List page with all other data");

			} catch (AssertionError Ae) {

				strReason = "Resource "
						+ strResource
						+ " is NOT displayed in Resource List page with all other data";
				log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed in Resource List page with all other data");
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifyResource function failed" + e);
			strReason = "saveAndVerifyResource function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify I triage check box is selected or Not and enabled or Not
	'Precondition	:None
	'Arguments		:selenium, blnItriageSelctd,blnTriageEnabld
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-June-2012                               <Name>
	************************************************************************/
	
	public String verifyItriageCheckBox(Selenium selenium,
			boolean blnItriageSelctd, boolean blnTriageEnabld) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnItriageSelctd) {
				try {
					assertTrue(selenium
							.isChecked(propElementDetails.getProperty("CreateResource.InterfaceResourceInd")));
					log4j.info("Itriage check box is selected ");

				} catch (AssertionError Ae) {

					strReason = "Itriage check box is NOT selected ";
					log4j.info("Itriage check box is NOT selected ");
				}

			} else {

				try {
					assertFalse(selenium
							.isChecked(propElementDetails.getProperty("CreateResource.InterfaceResourceInd")));
					log4j.info("Itriage check box is NOT selected ");

				} catch (AssertionError Ae) {

					strReason = "Itriage check box is  selected ";
					log4j.info("Itriage check box is  selected ");
				}

			}

			if (blnTriageEnabld) {
				try {
					assertTrue(selenium
							.isEditable(propElementDetails.getProperty("CreateResource.InterfaceResourceInd")));
					log4j.info("Itriage check box is enabled ");

				} catch (AssertionError Ae) {

					strReason = "Itriage check box is NOT enabled ";
					log4j.info("Itriage check box is NOT enabled ");
				}

			} else {

				try {
					assertFalse(selenium
							.isEditable(propElementDetails.getProperty("CreateResource.InterfaceResourceInd")));
					log4j.info("Itriage check box is NOT enabled ");

				} catch (AssertionError Ae) {

					strReason = "Itriage check box is  enabled ";
					log4j.info("Itriage check box is  enabled ");
				}

			}

		} catch (Exception e) {
			log4j.info("verifyItriageCheckBox function failed" + e);
			strReason = "verifyItriageCheckBox function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	 * 'Description :navigate to Edit Resource-Level Status Types 
	 * 'Precondition:None 
	 * 'Arguments :selenium, strResource 
	 * 'Returns :String 
	 * 'Date:8-June-2012 
	 * 'Author :QSG
	 * '-----------------------------------------------------------------------
	 * 'Modified Date Modified By '16-May-2012 <Name>
	 ************************************************************************/
	
	
	
	public String navToEditRTInResourcePage(Selenium selenium,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on Edit link for Resource
			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']/parent::tr/td[1]/a[text()='Status Types']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Resource-Level Status Types", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("Edit Resource-Level Status Types page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Edit Resource-Level Status Types page is NOT displayed"
						+ Ae;
				log4j.info("Edit Resource-Level Status Types is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navToEditRTInResourcePage function failed" + e);
			strReason = "navToEditRTInResourcePage function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:select por deselect ST in Edit Resource-Level Status Types page
	'Precondition	:None
	'Arguments		:selenium, strST
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/

	public String selDeselctSTInEditRSLevelPage(Selenium selenium,
			String strSTValue, boolean blnSelct) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on Edit link for Resource

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("css=input[name='statusTypeID']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			if (blnSelct) {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strSTValue + "']") == false) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTValue + "']");

				}
			} else {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strSTValue + "']")) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTValue + "']");

				}
			}

			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Resource List", selenium
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
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Resource List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Resource List page is NOT displayed" + Ae;
				log4j.info("Resource List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("selDeselctSTInEditRSLevelPage function failed" + e);
			strReason = "selDeselctSTInEditRSLevelPage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:select and deselect ST in Edit Resource-Level Status Types page
	'Precondition	:None
	'Arguments		:selenium, strSTValue,blnSelct
	'Returns		:String
	'Date	 		:10-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	************************************************************************/
	
	public String selDeselctOnlySTInEditRSLevelPage(Selenium selenium,
			String strSTValue, boolean blnSelct) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("css=input[name='statusTypeID']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			
			if (blnSelct) {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strSTValue + "']") == false) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTValue + "']");

				}
			} else {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strSTValue + "']")) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTValue + "']");

				}
			}

		} catch (Exception e) {
			log4j.info("selDeselctOnlySTInEditRSLevelPage function failed" + e);
			strReason = "selDeselctOnlySTInEditRSLevelPage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Save and verify Edit Resource Level page.
	'Precondition	:None
	'Arguments		:selenium,strSTValue,blnSelct
	'Returns		:String
	'Date	 		:10-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	************************************************************************/
	
	public String savAndVerifyEditRSLevelPage(Selenium selenium)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Resource List", selenium
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
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Resource List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Resource List page is NOT displayed" + Ae;
				log4j.info("Resource List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("savAndVerifyEditRSLevelPage function failed" + e);
			strReason = "savAndVerifyEditRSLevelPage function failed" + e;
		}
		return strReason;
	}

	/***********************************************************************
	'Description	:Select Resource Type in edit Resource Page
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/
	
	public String selctRT(Selenium selenium, String strRT) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.select(propElementDetails
					.getProperty("CreateResource.ResourceType"), "label="
					+ strRT);

			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Resource List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Resource List page is NOT displayed" + Ae;
				log4j.info("Resource List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("selctRT function failed" + e);
			strReason = "selctRT function failed" + e;
		}
		return strReason;
	}

	
	/***********************************************************************
	'Description	:Verify ST is Editable or NOT in Edit Resource-Level Status Types page
	'Precondition	:None
	'Arguments		:selenium, strSTValue,blnEditable
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/
	
	public String verifySTisEditableOrNot(Selenium selenium, String strSTValue,
			boolean blnEditable) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnEditable) {

				try {
					assertTrue(selenium
							.isEditable("css=input[name='statusTypeID'][value='"
									+ strSTValue + "']"));

					log4j.info("Status type is  editable");
				} catch (AssertionError Ae) {
					log4j.info("Status type is NOT editable");
					strReason = "Status type is NOT editable";
				}
			} else {
				try {
					assertFalse(selenium
							.isEditable("css=input[name='statusTypeID'][value='"
									+ strSTValue + "']"));

					log4j.info("Status type is NOT editable");
				} catch (AssertionError Ae) {
					log4j.info("Status type is editable");
					strReason = "Status type is editable";
				}
			}

		} catch (Exception e) {
			log4j.info("verifySTisEditableOrNot function failed" + e);
			strReason = "verifySTisEditableOrNot function failed" + e;
		}
		return strReason;
	}
	
	
	
	/***********************************************************************
	'Description	:Back to Resource List page from Edit Resource-Level Status Types page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/
	
	public String backToRSListPgeFrmEditResourceLevelST(Selenium selenium)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.click(propElementDetails.getProperty("Prop2282"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Resource List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Resource List page is NOT displayed" + Ae;
				log4j.info("Resource List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("backToRSListPgeFrmEditResourceLevelST function failed"
					+ e);
			strReason = "backToRSListPgeFrmEditResourceLevelST function failed"
					+ e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:Verify ST is Checked or NOT in Edit Resource-Level Status Types page
	'Precondition	:None
	'Arguments		:selenium, strSTValue,blnChecked
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/
	
	public String verifySTisCheckedOrNot(Selenium selenium, String strSTValue,
			boolean blnChecked) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnChecked) {

				try {
					assertTrue(selenium
							.isChecked("css=input[name='statusTypeID'][value='"
									+ strSTValue + "']"));

					log4j.info("Status type is  selected");
				} catch (AssertionError Ae) {
					log4j.info("Status type is NOT selected");
					strReason = "Status type is NOT selected";
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='statusTypeID'][value='"
									+ strSTValue + "']"));

					log4j.info("Status type is NOT selected");
				} catch (AssertionError Ae) {
					log4j.info("Status type is selected");
					strReason = "Status type is selected";
				}
			}

		} catch (Exception e) {
			log4j.info("verifySTisEditableOrNot function failed" + e);
			strReason = "verifySTisEditableOrNot function failed" + e;
		}
		return strReason;
	}

	/********************************************************************************************************
	 'Description :Fill only the mandatory fields in create resource page
	 'Precondition :None
	 'Arguments  :selenium, strResource, strAbbrv, strResType,strStandResType
	     ,strContFName,strContLName
	 'Returns  :String
	 'Date    :4-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                <Name>
	 ***********************************************************************************************************/
	 
	public String createResourceWithMandFields(Selenium selenium,
			String strResource, String strAbbrv, String strResType,
			String strStandResType, String strContFName, String strContLName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		int intCnt=0;
		do{
			try{
				assertTrue(selenium
						.isElementPresent(propElementDetails.getProperty("CreateResource.Name")));
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
			selenium.type(
					propElementDetails.getProperty("CreateResource.Name"),
					strResource);
			selenium.type(propElementDetails
					.getProperty("CreateResource.Abbreviation"), strAbbrv);

			selenium.select(propElementDetails
					.getProperty("CreateResource.ResourceType"), "label="
					+ strResType);

			selenium.select(propElementDetails
					.getProperty("CreateResource.StandResType"), "label="
					+ strStandResType);

			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactFName"), strContFName);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactLName"), strContLName);

		} catch (Exception e) {
			log4j.info("createResourceWithMandFields function failed" + e);
			strReason = "createResourceWithMandFields function failed" + e;
		}
		return strReason;
	}
	 
	 /********************************************************************************************************
	 'Description :Save the Resource and verify Assign Users To Resource page is displayed
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :5-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/
	public String saveAndNavToAssignUsr(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		int intCnt=0;
		do{
			try{
				assertTrue(selenium
						.isElementPresent(propElementDetails.getProperty("Save")));
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
			// click on save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Assign Users to " + strResource, selenium
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
				assertEquals("Assign Users to " + strResource, selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("'Assign users to " + strResource
						+ "' page is displayed. ");

			} catch (AssertionError Ae) {

				strReason = strReason + " 'Assign users to " + strResource
						+ "' page is NOT displayed.";
				log4j.info("'Assign users to " + strResource
						+ "' page is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info("saveAndNavToAssignUsr function failed" + e);
			strReason = "saveAndNavToAssignUsr function failed" + e;
		}
	  return strReason;
	 }
	 
	 /********************************************************************************************************
	 'Description :Fetch Resource Value in Resource List page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :5-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/
	
	
	public String fetchResValueInResList(Selenium selenium, String strResource)
			throws Exception {
		String strResValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			String strResValueArr[] = selenium.getAttribute(
					"//div[@id='mainContainer']"
							+ "/table[2]/tbody/tr/td[5][text()='" + strResource
							+ "']/parent::tr/td[1]" + "/a@href").split(
					"resourceID=");
			strResValue = strResValueArr[1];
			log4j.info("Resource Value is " + strResValue);
		} catch (Exception e) {
			log4j.info("fetchResValueInResList function failed" + e);
			strResValue = "";
		}
		return strResValue;
	}
	 
	 /***********************************************************************
	 'Description :Click on search and navigate to Find Resources
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	
	
	public String navToFindResBySearch(Selenium selenium) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Search"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("'Find Resources' screen is displayed.");

			} catch (AssertionError Ae) {

				strReason = strReason
						+ " 'Find Resources' screen is NOT displayed.";
				log4j.info("'Find Resources' screen is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info("navToFindResBySearch function failed" + e);
			strReason = "navToFindResBySearch function failed" + e;
		}
		return strReason;
	}
	 
	 /***********************************************************************
	 'Description :find resources in Find Resource screen by searching resources
	 'Precondition :None
	 'Arguments  :selenium,strResource,strCategory,strRegion,strCityZipCd,
	     strState
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	
	public String findResourcesBySearch(Selenium selenium, String strResource,
			String strCategory, String strRegion, String strCityZipCd,
			String strState) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

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
					.getProperty("Pref.FindResources.CityZip"), strCityZipCd);
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
				log4j.info("Resource " + strResource
						+ " is displayed in Find Resources screeen");

			} catch (AssertionError Ae) {
				log4j.info("Resource " + strResource
						+ " is NOT displayed in Find Resources screeen");
				strReason = "Resource " + strResource
						+ " is NOT displayed in Find Resources screeen";
			}

		} catch (Exception e) {
			log4j.info("findResourcesBySearch function failed" + e);
			strReason = "findResourcesBySearch function failed" + e;
		}
		return strReason;
	}

	 /***********************************************************************
	 'Description :select the resource in Find Resources screen and add to
	     to Custom View
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :7-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	
	public String addResToCustomView(Selenium selenium, String strResource) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// select the resource
			selenium
					.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
							+ strResource + "']/parent::tr/td[1]/input");
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

		} catch (Exception e) {
			log4j.info("addResToCustomView function failed" + e);
			strReason = "addResToCustomView function failed" + e;
		}
		return strReason;
	}
	 
	 /***********************************************************************
	 'Description :Check Add  to Custom View button is available or not
	 'Precondition :None
	 'Arguments  :selenium,blnAvailable
	     
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	
	public String checkAddCustomViewButtonInFindRes(Selenium selenium,
			boolean blnAvailable) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnAvailable) {
				try {
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("EditCustomView.FindRes.AddToCustomView")));
					log4j.info("'Add to custom view' button is available.");

				} catch (AssertionError Ae) {
					log4j.info("'Add to custom view' button is NOT available.");
					strReason = "'Add to custom view' button is NOT available.";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent(propElementDetails
									.getProperty("EditCustomView.FindRes.AddToCustomView")));
					log4j.info("'Add to custom view' button is not available.");

				} catch (AssertionError Ae) {
					log4j.info("'Add to custom view' button is available.");
					strReason = "'Add to custom view' button is available.";
				}
			}

		} catch (Exception e) {
			log4j.info("checkAddCustomViewButtonInFindRes function failed" + e);
			strReason = "checkAddCustomViewButtonInFindRes function failed" + e;
		}
		return strReason;
	}

	/*****************************************
	 'Description :Select the resource rights for the user
	 'Precondition :None
	 'Arguments  :selenium,blnAssocWith,blnUpdStat,blnRunRep,blnViewRes,strUsrName
	 'Returns  :String
	 'Date    :5-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 *****************************************************************************/
	
	
	public String assignUsrToResource(Selenium selenium, boolean blnAssocWith,
			boolean blnUpdStat, boolean blnRunRep, boolean blnViewRes,
			String strUsrName) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			if (blnAssocWith) {
				if (selenium.isChecked("css=input[name='association'][value='"
						+ strUsrName + "']") == false) {
					selenium.click("css=input[name='association'][value='"
							+ strUsrName + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='association'][value='"
						+ strUsrName + "']")) {
					selenium.click("css=input[name='association'][value='"
							+ strUsrName + "']");
				}
			}

			if (blnUpdStat) {
				if (selenium.isChecked("css=input[name='updateRight'][value='"
						+ strUsrName + "']") == false) {
					selenium.click("css=input[name='updateRight'][value='"
							+ strUsrName + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='updateRight'][value='"
						+ strUsrName + "']")) {
					selenium.click("css=input[name='updateRight'][value='"
							+ strUsrName + "']");
				}
			}

			if (blnRunRep) {
				if (selenium.isChecked("css=input[name='reportRight'][value='"
						+ strUsrName + "']") == false) {
					selenium.click("css=input[name='reportRight'][value='"
							+ strUsrName + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='reportRight'][value='"
						+ strUsrName + "']")) {
					selenium.click("css=input[name='reportRight'][value='"
							+ strUsrName + "']");
				}
			}

			if (blnViewRes) {
				if (selenium.isChecked("css=input[name='viewRight'][value='"
						+ strUsrName + "']") == false) {
					selenium.click("css=input[name='viewRight'][value='"
							+ strUsrName + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='viewRight'][value='"
						+ strUsrName + "']")) {
					selenium.click("css=input[name='viewRight'][value='"
							+ strUsrName + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("assignUsrToResource function failed" + e);
			strReason = "assignUsrToResource function failed" + e;
		}
		return strReason;
	}

	/********************************************************************************
	   'Description :Verify elements in resource list page
	   'Precondition :None
	   'Arguments  :selenium,strResource
	   'Returns  :String
	   'Date    :6-June-2012
	   'Author   :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                    <Name>
	   *******************************************************************************/
	
	public String navToEditResLevelSTPage(Selenium selenium, String strResource)
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
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
									+ strResource
									+ "']"
									+ "/parent::tr/td[1]/a[text()='Status Types']"));
					break;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 70);

			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']"
							+ "/parent::tr/td[1]/a[text()='Status Types']");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Edit Resource-Level Status Types", selenium
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
			} while (intCnt < 70);
			try {
				assertEquals("Edit Resource-Level Status Types", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Resource-Level Status Types' screen is displayed ");
			} catch (AssertionError Ae) {

				strReason = "'Edit Resource-Level Status Types' screen is NOT displayed ";
				log4j
						.info("'Edit Resource-Level Status Types' screen is NOT displayed ");
			}

		} catch (Exception e) {
			log4j.info("navToEditResLevelSTPage function failed" + e);
			strReason = "navToEditResLevelSTPage function failed" + e;
		}
		return strReason;
	}
	
	 /*******************************************************************
	   'Description :Select and deselect Status Type in Refine Visible
	       Status Type screen.
	   'Precondition :None
	   'Arguments  :selenium,strStatTypeVal,blnSelStatType
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                <Name>
	   ********************************************************************/
	

	public String selAndDeselSTInEditResLevelST(Selenium selenium,
			String strStatTypeVal, boolean blnSelStatType) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnSelStatType) {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatTypeVal + "']")) {

				} else {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strStatTypeVal + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatTypeVal + "']") == false) {

				} else {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strStatTypeVal + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("selAndDeselSTInEditResLevelST function failed" + e);
			strErrorMsg = "selAndDeselSTInEditResLevelST function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/********************************************************************************
	   'Description :Check the status type is displayed in Edit Resource Level page
	   'Precondition :None
	   'Arguments  :selenium,strStatTypeVal,blnPresent
	   'Returns  :String
	   'Date    :11-July-2012
	   'Author   :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                    <Name>
	   *******************************************************************************/
	
	public String checkSTInEditRSLevePage(Selenium selenium,
			String strStatType, String strStatTypeVal, boolean blnPresent)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			if (blnPresent) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='statusTypeID'][value='"
									+ strStatTypeVal + "']"));
					log4j
							.info("Status Type "
									+ strStatType
									+ " is listed in the 'Edit Resource-Level Status Types' screen");

				} catch (AssertionError ae) {
					log4j
							.info("Status Type "
									+ strStatType
									+ " is NOT listed in the 'Edit Resource-Level Status Types' screen");
					strReason = "Status Type "
							+ strStatType
							+ " is NOT listed in the 'Edit Resource-Level Status Types' screen";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='statusTypeID'][value='"
									+ strStatTypeVal + "']"));
					log4j
							.info("Status Type "
									+ strStatType
									+ " is not listed in the 'Edit Resource-Level Status Types' screen");

				} catch (AssertionError ae) {
					log4j
							.info("Status Type "
									+ strStatType
									+ " is still listed in the 'Edit Resource-Level Status Types' screen");
					strReason = "Status Type "
							+ strStatType
							+ " is still listed in the 'Edit Resource-Level Status Types' screen";
				}
			}
		} catch (Exception e) {
			log4j.info("checkSTInEditRSLevePage function failed" + e);
			strReason = "checkSTInEditRSLevePage function failed" + e;
		}
		return strReason;
	}
	 /***********************************************************************
	 'Description :Verify elements in resource list page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***************************************************************************/
	
	
	public String navToEditResLevelST(Selenium selenium, String strResource,
			String[] strSTvalue, String[] strSTSval, boolean blnSTvalue,
			boolean blnSTSval) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']"
							+ "/parent::tr/td[1]/a[text()='Status Types']");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit Resource-Level Status Types", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Resource-Level Status Types' screen is displayed ");
			} catch (AssertionError Ae) {

				strReason = "'Edit Resource-Level Status Types' screen is NOT displayed ";
				log4j
						.info("'Edit Resource-Level Status Types' screen is NOT displayed ");
			}
			if (blnSTvalue == true) {
				try {
					for (int i = 0; i < strSTvalue.length; i++) {
						assertFalse(selenium.isEditable("css=input[value='"
								+ strSTvalue[i] + "']"));
					}
					log4j
							.info("Status types associated with the resource type RT cannot be removed.");
				} catch (AssertionError Ae) {

					strReason = "Status types associated with the resource type RT CAN be removed.";
					log4j
							.info("Status types associated with the resource type RT CAN be removed. ");
				}
			}
			if (blnSTSval == true) {
				try {
					for (int i = 0; i < strSTSval.length; i++) {
						assertFalse(selenium.isChecked("css=input[value='"
								+ strSTSval[i] + "']"));
						log4j
								.info("Other status types in the region are available to be added to the resource RS.");
					}
				} catch (AssertionError Ae) {

					strReason = "Other status types in the region are  NOT available to be added to the resource RS.";
					log4j
							.info("Other status types in the region are  NOT available to be added to the resource RS.");
				}
			} else {
				try {
					for (int i = 0; i < strSTSval.length; i++) {
						assertTrue(selenium.isChecked("css=input[value='"
								+ strSTSval[i] + "']"));
						log4j
								.info("ST is selected and enabled in the 'Edit Resource-Level Status Types' screen ");
					}
				} catch (AssertionError Ae) {

					strReason = "ST is selected and enabled in the 'Edit Resource-Level Status Types' screen ";
					log4j
							.info("ST is selected and enabled in the 'Edit Resource-Level Status Types' screen ");
				}

			}

		} catch (Exception e) {
			log4j.info("navToEditResLevelST function failed" + e);
			strReason = "navToEditResLevelST function failedd" + e;
		}
		return strReason;
	}
	
	
	 /***********************************************************************
	  'Description :Verify elements in resource list page
	  'Precondition :None
	  'Arguments  :selenium,strResource
	  'Returns  :String
	  'Date    :6-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ****************************************************************************/
	
	public String varFieldsInResListPage(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Resource List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Resource  List page is NOT displayed" + Ae;
				log4j.info("Resource  List page is NOT displayed" + Ae);
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateRes.CreateNewResource")));
				log4j.info("'Create New Resource' button is available. ");

			} catch (AssertionError Ae) {

				strReason = "'Create New Resource' button is  NOT available.";
				log4j.info("'Create New Resource' button is  NOT available. ");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource + "']"));
				log4j
						.info("Resource list screen is displayed with the resource in the region.");
			} catch (AssertionError Ae) {

				strReason = "Resource list screen is displayed with the resource in the region.";
				log4j
						.info("Resource list screen is displayed with the resource in the region.");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[1]/a[text()='Edit']"));
				log4j
						.info("'Edit' link is available corresponding to resource.");
			} catch (AssertionError Ae) {

				strReason = "'Edit' link is NOT available corresponding to resource "
						+ strResource + "";
				log4j
						.info("'Edit' link is NOT available corresponding to resource "
								+ strResource + "");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[1]/a[text()='Status Types']"));
				log4j
						.info("'Edit Status Type' link is available corresponding to resource.");
			} catch (AssertionError Ae) {

				strReason = "'Edit Status Type' link is NOT available corresponding to resource "
						+ strResource + "";
				log4j
						.info("'Edit Status Type' link is NOT available corresponding to resource "
								+ strResource + "");
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[1]/a[text()='Users']"));
				log4j
						.info("'Users' link is available corresponding to resource"
								+ strResource + "");
			} catch (AssertionError Ae) {

				strReason = "'Users'link is NOT available corresponding to resource"
						+ strResource + "";
				log4j
						.info("'Users'link is NOT available corresponding to resource"
								+ strResource + "");
			}

		} catch (Exception e) {
			log4j.info("varFieldsInResListPage function failed" + e);
			strReason = "varFieldsInResListPage function failed" + e;
		}
		return strReason;
	}
	 
	
	 /*******************************************************************
	 'Description :Verify elements in resource list page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 *************************************************************************/
	
	public String navToEditStatTypeResLevelSelectST(Selenium selenium,
			String strResource, String[] strSTSval, boolean blnSTvalue,
			boolean blnSav) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']"
							+ "/parent::tr/td[1]/a[text()='Status Types']");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit Resource-Level Status Types", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Resource-Level Status Types' screen is displayed ");
			} catch (AssertionError Ae) {

				strReason = "'Edit Resource-Level Status Types' screen is NOT displayed ";
				log4j
						.info("'Edit Resource-Level Status Types' screen is NOT displayed ");
			}
			if (blnSTvalue == true) {
				for (int i = 0; i < strSTSval.length; i++) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTSval[i] + "']");
				}
			} else {
				for (int i = 0; i < strSTSval.length; i++) {
					selenium.isChecked("css=input[name='statusTypeID'][value='"
							+ strSTSval[i] + "']");
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strSTSval[i] + "']");
				}
			}

			if (blnSav) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Resource List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Resource List page is displayed");

				} catch (AssertionError Ae) {

					strReason = "Resource  List page is NOT displayed" + Ae;
					log4j.info("Resource  List page is NOT displayed" + Ae);
				}
			}
		} catch (Exception e) {
			log4j.info("navToEditStatTypeResLevelSelectST function failed" + e);
			strReason = "navToEditStatTypeResLevelSelectST function failed" + e;
		}
		return strReason;
	}

	
	  /*******************************************************************
	   'Description :Select and deselect Status Type in Refine Visible
	       Status Type screen.
	   'Precondition :None
	   'Arguments  :selenium,strStatTypeVal,blnSelStatType
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                <Name>
	   ********************************************************************/

	public String saveResAndNavToEditResLvlPage(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Save")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			do {
				try {

					assertEquals("Resource List",
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
				assertEquals("Resource List",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("User is taken to Resource List screen.");
			} catch (AssertionError Ae) {

				strErrorMsg = "User is  NOT taken to Resource List screen.";
				log4j.info("User is  NOT taken to Resource List screen.");
			}
		} catch (Exception e) {
			log4j.info("saveResAndNavToEditResLvlPage function failed" + e);
			strErrorMsg = "saveResAndNavToEditResLvlPage function failed" + e;
		}
		return strErrorMsg;
	}
	  
	  /***********************************************************************
	  'Description :Create resource with look up address by the user having set up resource right 
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :String
	  'Date    :20-July-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '27-Nov-2012                               <Name>
	  ************************************************************************/

	public String createResourceWitLookUPadresWhenUserLogins(Selenium selenium,
			String strResName, String strRSAbbr, String strRTLable,
			String strFName, String strLName, String strState,
			String strCountry, String strStdRT) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateRes.CreateNewResource"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Create New Resource", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Create New Resource page is displayed");

					selenium.type(propElementDetails
							.getProperty("CreateResource.Name"),
							strResName);
					selenium
							.type(propElementDetails
									.getProperty("CreateResource.Abbreviation"), strRSAbbr);
					selenium.select(propElementDetails
							.getProperty("CreateResource.ResourceType"),
							"label=" + strRTLable);

					selenium.select(propElementDetails
							.getProperty("CreateResource.State"), "label=" + strState);

					int intCnt = 0;
					boolean blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//select[@name='county']/option[text()='"+strCountry+"']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(20000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);

					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactFName"), strFName);
					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactLName"), strLName);
					selenium.select(propElementDetails
							.getProperty("CreateResource.County"), "label=" + strCountry);
					selenium.select(
							propElementDetails
									.getProperty("CreateResource.StandResType"),
							"label=" + strStdRT);
					selenium
							.click(propElementDetails
									.getProperty("CreateRes.CreateNewResource.LookUpAddr"));
					
					intCnt = 0;
					blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//div[@id='map']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(2000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);
					

					selenium.selectWindow("");
					selenium.selectFrame("Data");

					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("", strErrorMsg);

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[5][text()='"
											+ strResName
											+ "']"));

							log4j.info(" Resource " + strResName
									+ "  is  displayed");
						} catch (AssertionError Ae) {

							strErrorMsg = " Resource " + strResName
									+ "  is NOT displayed" + Ae;
							log4j.info(" Resource " + strResName
									+ "  is NOT displayed" + Ae);
						}

					} catch (AssertionError Ae) {
						log4j.info(strErrorMsg);
					}
				} catch (AssertionError Ae) {

					strErrorMsg = "Create New Resource page is NOT displayed"
							+ Ae;
					log4j
							.info("Create New Resource page is NOT displayed"
									+ Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j
					.info("createResourceWitLookUPadresWhenUserLogins function failed"
							+ e);
			strErrorMsg = "createResourceWitLookUPadresWhenUserLogins function failed"
					+ e;
		}
		return strErrorMsg;
	}

	  /*******************************************************************************************
	  ' Description: verify values for lattitude and longitude
	  ' Precondition: N/A 
	  ' Arguments: selenium
	  ' Returns: String 
	  ' Date: 03/08/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	  public String chkValueForLatitudeAndLongitude(Selenium selenium,String strLatVal,String strLongVal) throws Exception
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
	  			assertEquals(strLatVal, selenium.getValue("id=latitude"));
	  			log4j.info("'Value for 'Latitude' is populated. ");
	  		}
	  		catch (AssertionError Ae) {
	  			log4j.info("'Value for 'Latitude' is NOT populated. ");
	  			lStrReason = lStrReason + "; " + "Value for 'Latitude' is NOT populated. ";
	  		}
	  		try {
	  			assertEquals(strLongVal, selenium.getValue("id=longitude"));
	  			log4j.info("'Values for 'Longitude' is populated. ");
	  		}
	  		catch (AssertionError Ae) {
	  			log4j.info("'Values for 'Longitude' NOT is populated. ");
	  			lStrReason = lStrReason + "; " + "Values for 'Longitude' NOT is populated. ";
	  		}
	  	}catch(Exception e){
	  		log4j.info(e);
	  		lStrReason = lStrReason + "; " + e.toString();
	  	}

	  	return lStrReason;
	  }
	  
	  
	  
	  
	  /***********************************************************************
		'Description	:Verify resource is created
		'Precondition	:None
		'Arguments		:selenium
		'Returns		:String
		'Date	 		:14-May-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'14-May-2012                               <Name>
		************************************************************************/

	public String createResourceWitLookUPadresSharWitRgn(Selenium selenium,
			String strResName, String strRSAbbr, String strRTLable,
			boolean blnShareReg, String strFName, String strLName,
			String strState, String strCountry, String strStdRT)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		Resources objResources = new Resources();

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateRes.CreateNewResource"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Create New Resource", selenium
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
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Create New Resource", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Create New Resource page is displayed");

					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent(propElementDetails
									.getProperty("CreateResource.Name")));
							break;
						}catch(AssertionError Ae){
							Thread.sleep(1000);
							intCnt++;
						
						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					}while(intCnt<60);
					
					selenium.type(propElementDetails
							.getProperty("CreateResource.Name"),
							strResName);
					selenium
							.type(propElementDetails
									.getProperty("CreateResource.Abbreviation"), strRSAbbr);
					selenium.select(propElementDetails
							.getProperty("CreateResource.ResourceType"),
							"label=" + strRTLable);
					if (blnShareReg) {
						if (selenium
								.isChecked(propElementDetails
										.getProperty("CreateResource.ShareWithOtherReg")) == false)
							selenium
									.click(propElementDetails
											.getProperty("CreateResource.ShareWithOtherReg"));
					} else {
						if (selenium
								.isChecked(propElementDetails
										.getProperty("CreateResource.ShareWithOtherReg")))
							selenium
									.click(propElementDetails
											.getProperty("CreateResource.ShareWithOtherReg"));
					}
					selenium.select(propElementDetails
							.getProperty("CreateResource.State"), "label=" + strState);
					
					intCnt = 0;
					boolean blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//select[@name='county']/option[text()='"+strCountry+"']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(20000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);

					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactFName"), strFName);
					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactLName"), strLName);
					selenium.select(propElementDetails
							.getProperty("CreateResource.County"), "label=" + strCountry);
					selenium.select(
							propElementDetails
									.getProperty("CreateResource.StandResType"),
							"label=" + strStdRT);
					selenium
							.click(propElementDetails
									.getProperty("CreateRes.CreateNewResource.LookUpAddr"));

					intCnt = 0;
					blnCnt = true;

					do {
						try {
							assertTrue(selenium
									.isVisible("//div[@id='map']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(20000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);

					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					blnCnt = true;

					do {
						try {
							assertTrue(selenium
									.isElementPresent(propElementDetails.getProperty("Save")));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(20000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);
					
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					strErrorMsg = objResources.navResourcesList(selenium);

					try {
						assertEquals("", strErrorMsg);

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[5][text()='"
											+ strResName
											+ "']"));

							log4j.info(" Resource " + strResName
									+ "  is  displayed");
						} catch (AssertionError Ae) {

							strErrorMsg = " Resource " + strResName
									+ "  is NOT displayed" + Ae;
							log4j.info(" Resource " + strResName
									+ "  is NOT displayed" + Ae);
						}
					} catch (AssertionError Ae) {
						log4j.info(strErrorMsg);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Create New Resource page is NOT displayed"
							+ Ae;
					log4j
							.info("Create New Resource page is NOT displayed"
									+ Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("createResourceWitLookUPadresSharWitRgn function failed" + e);
			strErrorMsg = "createResourceWitLookUPadresSharWitRgn function failed" + e;
		}
		return strErrorMsg;
	}

	  /***********************************************************************
	  'Description :Verify resource is created
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :String
	  'Date    :14-May-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '14-May-2012                               <Name>
	  ************************************************************************/

	public String createResourceWitLookUPadresNew(Selenium selenium,
			String strResName, String strRSAbbr, String strRTLable,
			String strFName, String strLName, String strState,
			String strCountry, String strStdRT) throws Exception {

		String strErrorMsg = "";// variable to store error message
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click(propElementDetails
					.getProperty("CreateRes.CreateNewResource"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Create New Resource", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Create New Resource page is displayed");

					selenium.type(propElementDetails
							.getProperty("CreateResource.Name"),
							strResName);
					selenium
							.type(propElementDetails
									.getProperty("CreateResource.Abbreviation"), strRSAbbr);
					selenium.select(propElementDetails
							.getProperty("CreateResource.ResourceType"),
							"label=" + strRTLable);

					selenium.select(propElementDetails
							.getProperty("CreateResource.State"), "label=" + strState);

					int intCnt = 0;
					boolean blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//select[@name='county']/option[text()='"+strCountry+"']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(20000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);

					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactFName"), strFName);
					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactLName"), strLName);
					selenium.select(propElementDetails
							.getProperty("CreateResource.County"), "label=" + strCountry);
					selenium.select(
							propElementDetails
									.getProperty("CreateResource.StandResType"),
							"label=" + strStdRT);
					selenium
							.click(propElementDetails
									.getProperty("CreateRes.CreateNewResource.LookUpAddr"));

					intCnt = 0;
					blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//div[@id='map']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(2000);
							intCnt++;

						}catch(Exception e){
							Thread.sleep(2000);
							intCnt++;
						}
					} while (intCnt < 60 && blnCnt);

				} catch (AssertionError Ae) {
					strErrorMsg = "Create New Resource page is NOT displayed"
							+ Ae;
					log4j
							.info("Create New Resource page is NOT displayed"
									+ Ae);
				}
			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("createResourceWitLookUPadres function failed" + e);
			strErrorMsg = "createResourceWitLookUPadres function failed" + e;
		}
		return strErrorMsg;
	}
	  
	  /*******************************************************************
	   'Description :Check error message edit region page
	   'Precondition :None
	   'Arguments  :selenium,strRoleValue
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '28-May-2012                               <Name>
	   ********************************************************************/

	 public String chkerrorMsgAssignUserToRes(Selenium selenium, boolean blnSave)
	   throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  try {

	   rdExcel = new ReadData();
	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");
	   if (blnSave) {
	    selenium.click(propElementDetails.getProperty("Save"));
	    selenium.waitForPageToLoad(gstrTimeOut);
	   }

	   try {
	    assertTrue(selenium
	      .isTextPresent("A user that has any of Associated With, Update Status,"
	        + " or Run Reports for a resource must have View Resource."));
	    log4j
	      .info("An appropriate error is displayed stating .."
	        + "The following error occurred on this page: A user that has any of Associated With,"
	        + " Update Status, or Run Reports for a resource must have View Resource.");
	   } catch (AssertionError Ae) {

	    strErrorMsg = "An appropriate error is  NOT displayed";
	    log4j
	      .info("An appropriate error is  NOT displayed stating .."
	        + "The following error occurred on this page: A user that has any of Associated With,"
	        + " Update Status, or Run Reports for a resource must have View Resource.");
	   }

	  } catch (Exception e) {
	   log4j.info("chkerrorMsgAssignUserToRes function failed" + e);
	   strErrorMsg = "chkerrorMsgAssignUserToRes function failed" + e;
	  }
	  return strErrorMsg;
	 }

	 
	 /***********************************************************************
		'Description	:Verify Shared resource is created
		'Precondition	:None
		'Arguments		:selenium
		'Returns		:String
		'Date	 		:17-Oct-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'27-Nov-2012                               <Name>
		************************************************************************/

	public String createShardResourceWitLookUPadres(Selenium selenium,
			String strResName, String strRSAbbr, String strRTLable,
			String strFName, String strLName, String strState,
			String strCountry, String strStdRT) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		Resources objResources = new Resources();

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateRes.CreateNewResource"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Create New Resource", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Create New Resource page is displayed");

					selenium.type(propElementDetails
							.getProperty("CreateResource.Name"),
							strResName);
					selenium
							.type(propElementDetails
									.getProperty("CreateResource.Abbreviation"), strRSAbbr);
					selenium.select(propElementDetails
							.getProperty("CreateResource.ResourceType"),
							"label=" + strRTLable);

					selenium.select(propElementDetails
							.getProperty("CreateResource.State"), "label=" + strState);
					selenium.click(propElementDetails
							.getProperty("CreateResource.ShareWithOthrRegns"));

					int intCnt = 0;
					boolean blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//select[@id='county']"
											+ "/option[text()='" + strCountry
											+ "']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(2000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);

					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactFName"), strFName);
					selenium.type(propElementDetails
							.getProperty("CreateResource.ContactLName"), strLName);
					selenium.select(propElementDetails
							.getProperty("CreateResource.County"), "label=" + strCountry);
					selenium.select(
							propElementDetails
									.getProperty("CreateResource.StandResType"),
							"label=" + strStdRT);
					selenium
							.click(propElementDetails
									.getProperty("CreateRes.CreateNewResource.LookUpAddr"));

					intCnt = 0;
					blnCnt = true;
					do {
						try {
							assertTrue(selenium
									.isVisible("//div[@id='map']"));

							blnCnt = false;
						} catch (AssertionError Ae) {
							Thread.sleep(2000);
							intCnt++;

						}catch (Exception Ae) {
							Thread.sleep(20000);
							intCnt++;

						}
					} while (intCnt < 60 && blnCnt);

					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					strErrorMsg = objResources.navResourcesList(selenium);

					try {
						assertEquals("", strErrorMsg);

						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody"
											+ "/tr/td[5][text()='"
											+ strResName
											+ "']"));

							log4j.info(" Resource " + strResName
									+ "  is  displayed");
						} catch (AssertionError Ae) {

							strErrorMsg = " Resource " + strResName
									+ "  is NOT displayed" + Ae;
							log4j.info(" Resource " + strResName
									+ "  is NOT displayed" + Ae);
						}
					} catch (AssertionError Ae) {
						log4j.info(strErrorMsg);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Create New Resource page is NOT displayed"
							+ Ae;
					log4j
							.info("Create New Resource page is NOT displayed"
									+ Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("createShardResourceWitLookUPadres function failed" + e);
			strErrorMsg = "createShardResourceWitLookUPadres function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify resource is created
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:14-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'27-Nov-2012                               <Name>
	************************************************************************/

	public String createLookUPadres(Selenium selenium, boolean blnSave)
			throws Exception {
		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				selenium.click(propElementDetails
						.getProperty("CreateRes.CreateNewResource.LookUpAddr"));
				Thread.sleep(20000);
				if (blnSave) {
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					Thread.sleep(10000);
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("createLookUPadres function failed" + e);
			strErrorMsg = "createLookUPadres function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify resource is created
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:1-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'1-Nov-2012                               <Name>
	************************************************************************/

	public String provideLookUpAddressToResource(Selenium selenium,
			String strState, String strCountry) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.select("css=#stateAbbrev", "label=" + strState);

			int intCnt = 0;
			boolean blnCnt = true;
			do {
				try {
					assertTrue(selenium.isVisible("//select[@name='county']/option[text()='"+strCountry+"']"));
					blnCnt = false;
				} catch (AssertionError Ae) {
					Thread.sleep(20000);
					intCnt++;

				}catch (Exception Ae) {
					Thread.sleep(20000);
					intCnt++;
				}
			} while (intCnt < 60 && blnCnt);

			selenium.select(propElementDetails.getProperty("CreateResource.County"), "label=" + strCountry);

			selenium.click(propElementDetails.getProperty("CreateRes.CreateNewResource.LookUpAddr"));
			Thread.sleep(20000);

		} catch (Exception e) {
			log4j.info("provideLookUpAddressToResource function failed" + e);
			strErrorMsg = "provideLookUpAddressToResource function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description: Verify Resource Mandatory Field Values Along with City and State
	' Precondition: N/A 
	' Arguments: selenium, pStrResName, pStrAbbrv, pStrCity, pStrResType, pStrStandResType, pStrState
	' Returns: String 
	' Date: 06-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyResMandValuesWithCityAndStInEditRes(Selenium selenium, String pStrResName, String pStrAbbrv, String pStrCity, String pStrResType, String pStrStandResType, String pStrState,String strContFName,String strContLName) throws Exception
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
			try{
				assertEquals(pStrResName, selenium.getValue(propElementDetails.getProperty("CreateResource.Name")));
				assertEquals(pStrAbbrv, selenium.getValue(propElementDetails.getProperty("CreateResource.Abbreviation")));
				assertEquals(pStrResType, selenium.getSelectedLabel(propElementDetails.getProperty("CreateResource.ResourceType")));
				assertEquals(pStrStandResType, selenium.getSelectedLabel(propElementDetails.getProperty("CreateResource.StandResType")));
				
				assertEquals(pStrCity, selenium.getValue(propElementDetails.getProperty("CreateResource.City")));
				assertEquals(pStrState, selenium.getSelectedLabel(propElementDetails.getProperty("CreateResource.State")));
				
				assertEquals(strContFName, selenium.getValue(propElementDetails.getProperty("CreateResource.ContactFName")));
				assertEquals(strContLName, selenium.getValue(propElementDetails.getProperty("CreateResource.ContactLName")));
				log4j.info("The values provided while uploading the resource is retained");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("The values provided while uploading the resource is NOT retained");
				lStrReason = lStrReason + "; " + "The values provided while uploading the resource is NOT retained";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Verify Resource Mandatory Field Values Along with City and State
	' Precondition: N/A 
	' Arguments: selenium, pStrResName, pStrAbbrv, pStrCity, pStrResType, pStrStandResType, pStrState
	' Returns: String 
	' Date: 06-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifySubResMandFieldsInEditSubRS(Selenium selenium,
			String pStrResName, String pStrAbbrv, String pStrCity,
			String pStrResType, String pStrStandResType, String pStrState,
			String strContFName, String strContLName) throws Exception {
		String lStrReason = "";

		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertEquals(pStrResName, selenium.getValue(propElementDetails
						.getProperty("CreateResource.Name")));
				assertEquals(pStrAbbrv, selenium.getValue(propElementDetails
						.getProperty("CreateResource.Abbreviation")));
				assertEquals(pStrStandResType,
						selenium.getSelectedLabel(propElementDetails
								.getProperty("CreateResource.StandResType")));

				assertEquals(pStrCity, selenium.getValue(propElementDetails
						.getProperty("CreateResource.City")));
				assertEquals(pStrState,
						selenium.getSelectedLabel(propElementDetails
								.getProperty("CreateResource.State")));

				assertEquals(strContFName, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactFName")));
				assertEquals(strContLName, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactLName")));
				log4j.info("Data provided while creating Sub-resource is retained in all fields.");
				
				try {
					assertTrue(
							selenium.isElementPresent("//table/tbody/tr/td/input" +
									"[@value='"+pStrResType+"'][@type='text']"));
					assertFalse(selenium
							.isEditable("//table/tbody/tr/td/input" +
									"[@value='"+pStrResType+"'][@type='text']"));
					log4j.info("'Resource-Type' selected is retained.");
					log4j.info("'Resource-Type' field is disabled.");
				} catch (AssertionError Ae) {
					log4j.info("'Resource-Type' field is NOT disabled.");
					lStrReason = lStrReason
							+ "'Resource-Type' field is disabled.";
				}
				
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Data provided while creating Sub-resource is retained in all fields.");
				lStrReason = lStrReason
						+ "; "
						+ "Data provided while creating Sub-resource is retained in all fields.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/********************************************************************************
	   'Description :Verify elements in resource list page
	   'Precondition :None
	   'Arguments  :selenium,strResource
	   'Returns  :String
	   'Date    :6-June-2012
	   'Author   :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                    <Name>
	   *******************************************************************************/
	public String navToEditResLevelSTPage_LinkChanged(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']"
							+ "/parent::tr/td[1]/a[text()='Status Types']");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit Resource-Level Status Types", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Edit Resource-Level Status Types' screen is displayed ");
			} catch (AssertionError Ae) {

				strReason = "'Edit Resource-Level Status Types' screen is NOT displayed ";
				log4j
						.info("'Edit Resource-Level Status Types' screen is NOT displayed ");
			}

		} catch (Exception e) {
			log4j.info("navToEditResLevelSTPage_LinkChanged function failed" + e);
			strReason = "navToEditResLevelSTPage_LinkChanged function failed" + e;
		}
		return strReason;
	}
	
	/*********************************************************
	'Description	:Verify Duplicate of Resource present in the user list
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:13-11-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                              <Name>
	**********************************************************/

	public String checkDuplicateOfResNotPresent(Selenium selenium,
			String strResource)
	   throws Exception {
		String lStrReason="";
		try{
			int intValue=0;
		  
			intValue=selenium
				.getXpathCount("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource + "']").intValue();
			if(intValue==1){
				log4j.info("Duplicate of Resource "+strResource+" is NOT present");
			}else if (intValue>1){			
				log4j.info("Duplicate of Resource "+strResource+" is present");
				lStrReason = lStrReason + "; " + "Duplicate of Resource "+strResource+" is present";
			}
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	
	/***********************************************************************
	'Description	:navigate to edit resource page
	'Precondition	:None
	'Arguments		:selenium, strResource
	'Returns		:String
	'Date	 		:16-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-May-2012                               <Name>
	************************************************************************/
	public String navToEditResourcePageFrmUplDetails(Selenium selenium,String strResource) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			try{
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr/td[text()='"+strResource+"']/parent::tr/td[2]/a"));
				log4j
				.info("Resource ID is hyperlinked");
				//click on Edit link for Resource
				selenium.click("//div[@id='mainContainer']/form/table[2]/tbody/tr/td[text()='"+strResource+"']/parent::tr/td[2]/a");
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
			
			} catch (AssertionError Ae) {
				
				strReason = "Resource ID is NOT hyperlinked"
					+ Ae;
			log4j
			.info("Resource ID is NOT hyperlinked");
			}
		} catch (Exception e) {
			log4j.info("navToEditResourcePageFrmUplDetails function failed" + e);
			strReason = "navToEditResourcePageFrmUplDetails function failed" + e;
		}
		return strReason;
	}
	
	/********************************************************************************************************
	'Description	:Save the Resource and verify resource details in Resource list page
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:11-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'27-Feb-2013                               <Name>
	***********************************************************************************************************/

	public String saveAndVerifyResourceInRSList(Selenium selenium,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Save")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
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
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
								+ strResource + "']"));
				log4j.info("Resource " + strResource
						+ " is displayed in Resource List page");
			} catch (AssertionError Ae) {

				strReason = "Resource " + strResource
						+ " is NOT displayed in Resource List page ";
				log4j.info("Resource " + strResource
						+ " is NOT displayed in Resource List page");
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifyResourceInRSList function failed" + e);
			strReason = "saveAndVerifyResourceInRSList function failed" + e;
		}
		return strReason;
	}

	
	/*********************************************************************
	'Description	:Save the Resource and navigate to resource list page
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:11-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'27-Feb-2013                               <Name>
	************************************************************************/

	public String saveAndNavRSList(Selenium selenium)
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
							.getProperty("Save")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("Resource List",
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
				assertEquals("Resource List",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Resource List page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Resource  List page is NOT displayed" + Ae;
				log4j.info("Resource  List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("saveAndNavRSList function failed" + e);
			strReason = "saveAndNavRSList function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:verifyLinksUndrActionInRSListPge
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:21-Jan-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'21-Jan-2012                               <Name>
	************************************************************************/

	public String verifyLinksUndrActionInRSListPge(Selenium selenium,
			String strResource) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			int intNum = 0;

			try {

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[1][text()='Action']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[1][text()='Edit']"));
				log4j.info("Edit link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[2][text()='Status Types']"));

				log4j.info("Status type link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[3][text()='Users']"));
				log4j.info("Users link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[4][contains(text(),'Sub-Resources')]"));
				log4j.info("SubResources link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[5][text()='Demote']"));

				log4j.info("Demote link is present under Action coloumn");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("Edit link is NOT present under Action coloumn");
					strErrorMsg = "Edit link NOT is present under Action coloumn";
					break;

				case 2:
					log4j
							.info("Status type link is NOT present under Action coloumn");
					strErrorMsg = "Status type link is NOT present under Action coloumn";
					break;

				case 3:
					log4j
							.info("Users link is NOT present under Action coloumn");
					strErrorMsg = "Users link is NOT present under Action coloumn";
					break;

				case 4:
					log4j
							.info("SubResources link is NOT present under Action coloumn");
					strErrorMsg = "SubResources link is NOT present under Action coloumn";
					break;

				case 5:
					log4j
							.info("Demote link is NOT present under Action coloumn");
					strErrorMsg = "Demote link is NOT present under Action coloumn";
					break;
				}

			}

		} catch (Exception e) {
			log4j.info("verifyLinksUndrActionInRSListPge function failed" + e);
			strErrorMsg = "verifyLinksUndrActionInRSListPge function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:navToEditSubResourcePage
	'Precondition	:None
	'Arguments		:selenium, strResource
	'Returns		:String
	'Date	 		:25-Feb-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2012                               <Name>
	************************************************************************/
	
	public String navToEditSubResourcePage(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/"
							+ "tr/td[5][text()='" + strResource
							+ "']/parent::tr/td[1]/a[contains(text(),'Sub-Resources')]"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.click("//div[@id='mainContainer']/table[2]/tbody/"
					+ "tr/td[5][text()='" + strResource
					+ "']/parent::tr/td[1]/a[contains(text(),'Sub-Resources')]");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			intCnt=0;
			do{
				try {

					assertEquals("Sub-Resource List for " + strResource, selenium
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
				assertEquals("Sub-Resource List for " + strResource, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Sub-Resource List for " + strResource
						+ " page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Sub-Resource List for " + strResource
						+ " page is NOT displayed";
				log4j.info("Sub-Resource List for " + strResource
						+ " page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("navToEditSubResourcePage function failed" + e);
			strReason = "navToEditSubResourcePage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:navToEditSubResourcePage
	'Precondition	:None
	'Arguments		:selenium, strResource
	'Returns		:String
	'Date	 		:25-Feb-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2012                               <Name>
	************************************************************************/
	
	public String navToCreateSubResourcePage(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.click(propElementDetails.getProperty("CreateResource.ValuesubResource"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {

					assertEquals("Create New Sub-Resource for " + strResource, selenium
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
				assertEquals("Create New Sub-Resource for " + strResource, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create New Sub-Resource for " + strResource
						+ " page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Create New Sub-Resource for " + strResource
						+ " page is NOT displayed";
				log4j.info("Create New Sub-Resource for " + strResource
						+ " page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("navToCreateSubResourcePage function failed" + e);
			strReason = "navToCreateSubResourcePage function failed" + e;
		}
		return strReason;
	}
	
	  /***********************************************************************
	  'Description :createSubResourceWitLookUPadres
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :String
	  'Date    :25-Feb-2013
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '25-Feb-2013                               <Name>
	  ************************************************************************/

	public String createSubResourceWitLookUPadres(Selenium selenium,
			String strSubResName, String strRSAbbr, String strRTLable,
			String strStandardRsType, boolean blnLookUpAdres, String strFName,
			String strLName, String strState, String strCountry)
			throws Exception {

		String strErrorMsg = "";// variable to store error message
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails.getProperty("CreateResource.subResource.Name"), strSubResName);
			selenium.type(propElementDetails.getProperty("CreateResource.subResource.Abbr"), strRSAbbr);
			selenium.select(propElementDetails.getProperty("CreateResource.subResource.ResourceTypeID"), "label="
					+ strRTLable);

			selenium.type(propElementDetails.getProperty("CreateResource.subResource.FN"), strFName);
			selenium.type(propElementDetails.getProperty("CreateResource.subResource.LN"), strLName);

			selenium.select(propElementDetails.getProperty("CreateResource.subResource.StdRTID"),
					"label=" + strStandardRsType);

			if (blnLookUpAdres) {
				selenium.select(propElementDetails.getProperty("CreateResource.subResource.State"), "label=" + strState);

				int intCnt = 0;
				boolean blnCnt = true;
				do {
					try {
						assertTrue(selenium.isVisible("//select[@id='county']"
								+ "/option[text()='" + strCountry + "']"));

						blnCnt = false;
					} catch (AssertionError Ae) {
						Thread.sleep(20000);
						intCnt++;

					} catch (Exception e) {
						Thread.sleep(2000);
						intCnt++;
					}
				} while (intCnt < 60 && blnCnt);

				selenium.select(propElementDetails.getProperty("CreateResource.subResource.Country"), "label=" + strCountry);
				selenium.click(propElementDetails
						.getProperty("CreateRes.CreateNewResource.LookUpAddr"));

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

		} catch (Exception e) {
			log4j.info("createSubResourceWitLookUPadres function failed" + e);
			strErrorMsg = "createSubResourceWitLookUPadres function failed" + e;
		}
		return strErrorMsg;
	}

	
	/********************************************************************************************************
	'Description	:saveAndVerifySubResourceInRSList
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:25-Feb-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                               <Name>
	***********************************************************************************************************/

	public String saveAndVerifySubResourceInRSList(Selenium selenium,
			String strSubResource,String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Save")));
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
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Sub-Resource List for " + strResource,
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
				assertEquals("Sub-Resource List for " + strResource,
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Sub-Resource List for " + strResource
						+ " page is displayed");
				
				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table"
										+ "[@summary='Sub-Resources']" +
												"/tbody/tr/td[3][text()='"+strSubResource+"']"));
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
							.isElementPresent("//div[@id='mainContainer']/table"
									+ "[@summary='Sub-Resources']" +
											"/tbody/tr/td[3][text()='"+strSubResource+"']"));
					log4j.info("Sub Resource " + strSubResource
							+ " is displayed in Resource List page");

				} catch (AssertionError Ae) {

					strReason = "Sub Resource " + strSubResource
							+ " is NOT displayed in Resource List page ";
					log4j.info("Sub Resource " + strSubResource
							+ " is NOT displayed in Resource List page");
				}

			} catch (AssertionError Ae) {

				strReason = "Sub-Resource List for " + strSubResource
						+ " page is NOT displayed";
				log4j.info("Sub-Resource List for " + strSubResource
						+ " page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifySubResourceInRSList function failed" + e);
			strReason = "saveAndVerifySubResourceInRSList function failed" + e;
		}
		return strReason;
	}
	
	
	/*******************************************************
	'Description	:Verify SubResource count In RSList
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:25-Feb-2013
	'Author			:QSG
	'------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                               <Name>
	*******************************************************/

	public String VerifySubResourceCountInRSList(Selenium selenium,
			String strSubRSCount, String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {

					assertEquals("Resource List", selenium
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
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='"
								+ strResource + "']"
								+ "/parent::tr/td[1]/a[text()='"
								+ strSubRSCount + "']"));
				log4j.info("'"
						+ strSubRSCount
						+ "' is displayed for 'Sub-Resource' corresponding to resource "
						+ strResource + ".");

			} catch (AssertionError Ae) {

				strReason = "'"
						+ strSubRSCount
						+ "' is NOT displayed for 'Sub-Resource' corresponding to resource "
						+ strResource + ".";
				log4j.info("'"
						+ strSubRSCount
						+ "' is NOT displayed for 'Sub-Resource' corresponding to resource "
						+ strResource + ".");
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifySubResourceInRSList function failed" + e);
			strReason = "saveAndVerifySubResourceInRSList function failed" + e;
		}
		return strReason;
	}
	/**********************************************************************************
	'Description	:save And Verify 'View Resource Detail' screen for parent resource. 
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:25-Feb-2013
	'Author			:QSG
	'----------------------------------------------------------------------------------
	'Modified Date                                                   Modified By
	'25-Feb-2013                                                     <Name>
	***********************************************************************************/

	public String saveAndViewResDetailScreenOfRS(Selenium selenium,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Save")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			// click on save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("View Resource Detail",
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
				assertEquals("View Resource Detail",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				assertTrue(selenium
						.isElementPresent("//div/h1[@id='r_name'][text()='"
								+ strResource + "']"));
				log4j.info("'View Resource Detail' screen for parent resource "
						+ strResource + " is displayed.");
			} catch (AssertionError Ae) {
				strReason = "'View Resource Detail' screen for parent resource "
						+ strResource + " is NOT displayed.";
				log4j.info("'View Resource Detail' screen for parent resource "
						+ strResource + " is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifySubResourceInRSList function failed" + e);
			strReason = "saveAndVerifySubResourceInRSList function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:navigate to edit resource page
	'Precondition	:None
	'Arguments		:selenium, strResource
	'Returns		:String
	'Date	 		:26-Feb-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-Feb-2013                               <Name>
	************************************************************************/
	
	public String navToDemoteResourcePage(Selenium selenium, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/"
									+ "tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[1]/a[text()='Demote']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on Edit link for Resource
			selenium.click("//div[@id='mainContainer']/table[2]/tbody/"
					+ "tr/td[5][text()='" + strResource
					+ "']/parent::tr/td[1]/a[text()='Demote']");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("Demote Resource -- " + strResource, selenium
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
				assertEquals("Demote Resource -- " + strResource, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Demote Resource -- " + strResource
						+ " page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Demote Resource -- " + strResource
						+ " page is NOT displayed" + Ae;
				log4j.info("Demote Resource -- " + strResource
						+ " page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("navToDemoteResourcePage function failed" + e);
			strReason = "navToDemoteResourcePage function failed" + e;
		}
		return strReason;
	}

	
	 /********************************************************************************************************
	 'Description :Fetch sub Resource Value in Resource List page
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :28-Feb-2013
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/
		
	public String fetchSubResValueInResList(Selenium selenium,
			String strSubResource) throws Exception {
		String strResValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			String strResValueArr[] = selenium
					.getAttribute(
							"//div[@id='mainContainer']"
									+ "/table[@summary='Sub-Resources']/tbody/tr/td[3][text()='"
									+ strSubResource + "']/parent::tr/td[1]"
									+ "/a@href").split("resourceID=");
			strResValue = strResValueArr[1];
			log4j.info("Resource Value is " + strResValue);
		} catch (Exception e) {
			log4j.info("fetchResValueInResList function failed" + e);
			strResValue = "";
		}
		return strResValue;
	}
	
	/***********************************************************************
	'Description	:verifyLinksUndrActionInRSListPge
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:21-Jan-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'21-Jan-2012                               <Name>
	************************************************************************/

	public String verifySubResDataInRSListPge(Selenium selenium,
			String strSubResource,String strSubRecAbbr,String strSubRecType,String pStrIconImg) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			int intNum = 0;

			try {
				log4j.info(" Data provided while creating are displayed appropriately under the following columns:");
				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[1][text()='Action']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[3][text()='"
								+ strSubResource
								+ "']/parent::tr/td[1]/a[1][text()='Edit']"));
				log4j.info("1 . Edit link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[2][text()='Icon']"));
				String strIcon=selenium.getAttribute("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"+strSubResource+"']/parent::tr/td[2]/img/@src");
				assertTrue(strIcon.contains(pStrIconImg));
				log4j.info("2. ICON : is displayed ");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[3]/a[text()='Name']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"+strSubResource+"']"));
				log4j.info("3 . Name :"+strSubResource+" is displayed");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[4]/a[text()='Abbreviation']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"+strSubResource+"']/parent::tr/td[4][text()='"+strSubRecAbbr+"']"));
				log4j.info("4 . Abbreviation : "+strSubRecAbbr+" is displayed");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[5]/a[text()='Resource�Type']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"+strSubResource+"']/parent::tr/td[5][text()='"+strSubRecType+"']"));

				log4j.info("5 . Sub Resource type : "+strSubRecType+" is displayed");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("1. Edit link is NOT present under Action coloumn");
					strErrorMsg = "1. Edit link NOT is present under Action coloumn";
					break;

				case 2:
					log4j
							.info("2. ICON : is Not displayed ");
					strErrorMsg = "2. ICON : is Not displayed ";
					break;

				case 3:
					log4j
							.info("3 . Name :"+strSubResource+" is Not displayed");
					strErrorMsg = "3 . Name :"+strSubResource+" is Not displayed";
					break;

				case 4:
					log4j
							.info("4 . Abbreviation : "+strSubRecAbbr+" is Not displayed");
					strErrorMsg = "4 . Abbreviation : "+strSubRecAbbr+" is Not displayed";
					break;

				case 5:
					log4j
							.info("5 . Sub Resource type : "+strSubRecType+" is Not displayed");
					strErrorMsg = "5 . Sub Resource type : "+strSubRecType+" is Not displayed";
					break;
				}

			}

		} catch (Exception e) {
			log4j.info("verifySubResDataInRSListPge function failed" + e);
			strErrorMsg = "verifySubResDataInRSListPge function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:navigate to edit resource page by clicking on link associated with sub resource
	'Precondition	:None
	'Arguments		:selenium,pStrResource, pStrSubResource
	'Returns		:String
	'Date	 		:07/05/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'16-May-2012                               <Name>
	************************************************************************/
	
	public String navToEditResourcePageWithEditLink(Selenium selenium, String pStrResource,String pStrSubResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/"
							+ "tr/td[text()='" + pStrSubResource
							+ "']/parent::tr/td[1]/a[text()='Edit']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			// click on Edit link for Resource
			selenium.click("//div[@id='mainContainer']/table[2]/tbody/"
					+ "tr/td[text()='" + pStrSubResource
					+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Edit Sub-Resource of "+pStrResource, selenium
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
				assertEquals("Edit Sub-Resource of "+pStrResource, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Sub-Resource of "+pStrResource+" page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Edit Sub-Resource of "+pStrResource+" page is Not displayed" + Ae;
				log4j.info("Edit Sub-Resource of "+pStrResource+" page is Not displayed");
			}

		} catch (Exception e) {
			log4j.info("navToEditResourcePageWithEditLink function failed" + e);
			strReason = "navToEditResourcePageWithEditLink function failed" + e;
		}
		return strReason;
	}
	/*******************************************************************************************
	' Description: Verify Sub Resource Field Values retained in Edit Sub resource page
	' Precondition: N/A 
	' Arguments: selenium, pStrResName, pStrAbbrv, pStrCity, pStrResType, pStrStandResType, pStrState
	' Returns: String 
	' Date: 07/05/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifySubResValuesRetInEditSubRes(Selenium selenium, String pStrSubResName, String pStrAbbrv, String pStrCity, String pStrResType, String pStrStandResType, String pStrState,String strContFName,String strContLName) throws Exception
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
			try{
				assertEquals(pStrSubResName, selenium.getValue(propElementDetails.getProperty("CreateResource.Name")));
				assertEquals(pStrAbbrv, selenium.getValue(propElementDetails.getProperty("CreateResource.Abbreviation")));
				assertEquals(pStrResType, selenium.getSelectedLabel(propElementDetails.getProperty("CreateResource.ResourceType")));
				assertEquals(pStrStandResType, selenium.getSelectedLabel(propElementDetails.getProperty("CreateResource.StandResType")));
				
				assertEquals(pStrCity, selenium.getValue(propElementDetails.getProperty("CreateResource.City")));
				assertEquals(pStrState, selenium.getSelectedLabel(propElementDetails.getProperty("CreateResource.State")));
				
				assertEquals(strContFName, selenium.getValue(propElementDetails.getProperty("CreateResource.ContactFName")));
				assertEquals(strContLName, selenium.getValue(propElementDetails.getProperty("CreateResource.ContactLName")));
				log4j.info("The values provided while creating sub resource are retained");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("The values provided while creating sub resource are Not retained");
				lStrReason = lStrReason + "; " + "The values provided while creating sub resource are Not retained";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//start//verRTInResTypeDropDown//
	/********************************************************************
	' Description : Verify RT In ResTypeDropDown.
    ' Precondition: N/A 
    ' Arguments   : selenium, strRTLable, strRTValue
    ' Returns     : String 
    ' Date        : 08/07/2013
    ' Author      : QSG 
    '--------------------------------------------------------------------- 
    ' Modified Date: 
    ' Modified By: 
**************************************************************************/

	public String verRTInResTypeDropDown(Selenium selenium, String strRTLable,
			String strRTValue) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals(
						strRTLable,
						selenium.getText("css=option[value=\"" + strRTValue
								+ "\"]"));
				log4j.info("'" + strRTLable
						+ "'is listed under Resource Type drop down.");
			} catch (Exception e) {
				log4j.info("'" + strRTLable
						+ "'is NOT listed under Resource Type drop down.");
				strReason = "'" + strRTLable
						+ "'is NOT listed under Resource Type drop down.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}

		return strReason;
	}
	//end//verRTInResTypeDropDown//
	
	//start//VerifyFieldsInDemoteResScreen//
		/*******************************************************************************************
		' Description: 
		' Precondition: N/A 
		' Arguments: selenium
		' Returns: String 
		' Date: 08/07/2013
		' Author: QSG 
		'--------------------------------------------------------------------------------- 
		' Modified Date: 
		' Modified By: 
		*******************************************************************************************/

		public String VerifyFieldsInDemoteResScreen(Selenium selenium,String pStrResourceName) throws Exception
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
					log4j.info("'Demote Resource Screen with following fields are displayed ");
					assertFalse(selenium.isEditable("//table/tbody/tr/td/input[@type='text'][@value='"+pStrResourceName+"']"));
					log4j.info("1 : Name (Selected resource Name is displayed by default and field is diabled)");
				}
				catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'Demote Resource Screen with following fields are Not displayed 1 : Name (Selected resource Name is displayed by default and field is diabled)");
					lStrReason = lStrReason + "; " + "Demote Resource Screen with following fields are Not displayed 1 : Name (Selected resource Name is displayed by default and field is diabled)";
				}
				try {
					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3777")));
					log4j.info("'2 : Resource Type: (Drop down with 'Sub-resource' types under it)");
				}
				catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'2 : Resource Type: (Drop down with 'Sub-resource' types under it) is not displayed");
					lStrReason = lStrReason + "; " + "2 : Resource Type: (Drop down with 'Sub-resource' types under it) is not displayed";
				}
				try {
					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3778")));
					log4j.info("'3 : Select the parent resource: (Table with list of Resource Name and associated Resource Type)");
				}
				catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'3 : Select the parent resource: [Table with list of Resource Name and associated Resource Type]  not displayed");
					lStrReason = lStrReason + "; " + "3 : Select the parent resource: [Table with list of Resource Name and associated Resource Type]  not displayed";
				}
				try {
					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3779")));
					log4j.info("'Check box associated with each resource name");
				}
				catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'Check box associated with each resource name not displayed");
					lStrReason = lStrReason + "; " + "Check box associated with each resource name not displayed";
				}
			}catch(Exception e){
				log4j.info(e);
				lStrReason = "Resources.VerifyFieldsInDemoteResScreen failed to complete due to " +lStrReason + "; " + e.toString();
			}

			return lStrReason;
		}
		//end//VerifyFieldsInDemoteResScreen//
		
		/********************************************************************************************************
		'Description	:Fill all the necessary fields in create resource page
		'Precondition	:None
		'Arguments		:selenium,
		'Returns		:String
		'Date	 		:07/09/2013
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'16-May-2012                               <Name>
		***********************************************************************************************************/
		
		public String VerifyCreateSubResScreen_AllFields(Selenium selenium)throws Exception {
			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try {
				log4j.info("following fields are displayed in Create subresource screen");
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try{
					assertTrue(selenium.isElementPresent(
							propElementDetails.getProperty("CreateResource.Name")));
					log4j.info("Name :");
				
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.Abbreviation")));
					log4j.info("Abbreviation :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ResourceType")));
					log4j.info("Resource Type :");
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.StandResType")));
					log4j.info("Standard Resource Type :");
				
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ExternalId")));
					log4j.info("External ID :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.StreetAddr")));
					log4j.info("Street Address :");
					
					assertTrue(selenium.isElementPresent(
							propElementDetails.getProperty("CreateResource.City")));
					log4j.info("City :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.State")));
					log4j.info("State :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ZipCode")));
					log4j.info("Zipe code :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
									.getProperty("CreateResource.County")));
					log4j.info("Country :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.Website")));
					log4j.info("Web site :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ContactFName")));
					log4j.info("Contact First Name :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ContactLName")));
					log4j.info("Contact Last Name :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.Title")));
					log4j.info("Title :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ContactAddr")));
					log4j.info("Contact Address :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ContactPhone1")));
					log4j.info("Contact Phone1 :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ContactPhone2")));
					log4j.info("Contact Phone2 :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ContactFax")));
					log4j.info("Contact Fax :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.ContactEmail")));
					log4j.info("Contact Email :");
					
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateResource.Notes")));
					log4j.info("Contact Notes :");
				}catch(AssertionError a){
					log4j.info("All Fields not displayed in create subresource screen" + a);
					strReason = "All Fields not displayed in create subresource screen" + a;
				}

			} catch (Exception e) {
				log4j.info("VerifyCreateSubResScreen_AllFields function failed" + e);
				strReason = "VerifyCreateSubResScreen_AllFields function failed" + e;
			}
			return strReason;
		}
		
		
	  /***********************************************************************
	  'Description  :createSubResourceWitLookUPadres
	  'Precondition :None
	  'Arguments    :selenium
	  'Returns      :String
	  'Date         :25-Feb-2013
	  'Author       :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '25-Feb-2013                               <Name>
	  ************************************************************************/

	public String editSubResourceWitLookUPadres(Selenium selenium,
			String strSubResName, String strRSAbbr,String strStandardRsType,
			boolean blnLookUpAdres, String strFName,
			String strLName, String strState, String strCountry)
			throws Exception {

		String strErrorMsg = "";// variable to store error message
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails
					.getProperty("CreateResource.subResource.Name"),
					strSubResName);
			selenium.type(propElementDetails
					.getProperty("CreateResource.subResource.Abbr"), strRSAbbr);

			selenium.type(propElementDetails
					.getProperty("CreateResource.subResource.FN"), strFName);
			selenium.type(propElementDetails
					.getProperty("CreateResource.subResource.LN"), strLName);

			selenium.select(propElementDetails
					.getProperty("CreateResource.subResource.StdRTID"),
					"label=" + strStandardRsType);

			if (blnLookUpAdres) {
				selenium.select(propElementDetails
						.getProperty("CreateResource.subResource.State"),
						"label=" + strState);

				int intCnt = 0;
				boolean blnCnt = true;
				do {
					try {
						assertTrue(selenium.isVisible("//select[@id='county']"
								+ "/option[text()='" + strCountry + "']"));

						blnCnt = false;
					} catch (AssertionError Ae) {
						Thread.sleep(20000);
						intCnt++;

					} catch (Exception e) {
						Thread.sleep(2000);
						intCnt++;
					}
				} while (intCnt < 60 && blnCnt);

				selenium.select(propElementDetails
						.getProperty("CreateResource.subResource.Country"),
						"label=" + strCountry);
				selenium.click(propElementDetails
						.getProperty("CreateRes.CreateNewResource.LookUpAddr"));

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

		} catch (Exception e) {
			log4j.info("createSubResourceWitLookUPadres function failed" + e);
			strErrorMsg = "createSubResourceWitLookUPadres function failed" + e;
		}
		return strErrorMsg;
	}

	//start//verRTInResTypeDropDownFalse//
		/********************************************************************
		' Description : Verify RT is not present in ResTypeDropDown.
	    ' Precondition: N/A 
	    ' Arguments   : selenium, strRTLable, strRTValue
	    ' Returns     : String 
	    ' Date        : 11/07/2013
	    ' Author      : QSG 
	    '--------------------------------------------------------------------- 
	    ' Modified Date: 
	    ' Modified By: 
	**************************************************************************/

		public String verRTInResTypeDropDownFalse(Selenium selenium, String pStrRTName,
				String pStrRTValue) throws Exception {
			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertFalse(selenium.isElementPresent("css=option[value=\"" + pStrRTValue+ "\"]"));
					log4j.info("'" + pStrRTName+ "'is Not listed under Resource Type drop down.");
				} catch (Exception e) {
					log4j.info("'" + pStrRTName
							+ "'is listed under Resource Type drop down.");
					strReason = "'" + pStrRTName
							+ "'is listed under Resource Type drop down.";
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = e.toString();
			}

			return strReason;
		}
		//end//verRTInResTypeDropDownFalse//
		
	
	/**********************************************************************************
	'Description	:save And Verify 'View Resource Detail' screen for parent resource. 
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:25-Feb-2013
	'Author			:QSG
	'----------------------------------------------------------------------------------
	'Modified Date                                                   Modified By
	'25-Feb-2013                                                     <Name>
	***********************************************************************************/

		public String saveAndViewResDetailScreenOfSubRS(Selenium selenium,
				String strSubResource) throws Exception {
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

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Save")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);
				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertEquals("View Resource Detail",
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
					assertEquals("View Resource Detail",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div/h1[@id='r_name'][text()='"
									+ strSubResource + "']"));
					log4j.info("User is directed to the 'View Resource Detail' screen for parent resource "
							+ strSubResource + ".");
				} catch (AssertionError Ae) {
					strReason = "User is directed to the 'View Resource Detail' screen for parent resource "
							+ strSubResource + " .";
					log4j.info("User is directed to the 'View Resource Detail' screen for parent resource "
							+ strSubResource + " .");
				}

			} catch (Exception e) {
				log4j.info("saveAndViewResDetailScreenOfSubRS function failed" + e);
				strReason = "saveAndViewResDetailScreenOfSubRS function failed" + e;
			}
			return strReason;
		}
		
		/********************************************************************************************************
		'Description	:Save the Resource and verify resource details in Resource list page
		'Precondition	:None
		'Arguments		:selenium,strResource,strHavBed,strAHAId,strAbbrev,strResType
		'Returns		:String
		'Date	 		:16-May-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'16-May-2012                               <Name>
		***********************************************************************************************************/

		public String VerifySubResource(Selenium selenium, String strSubResource,
				boolean blnSubRes) throws Exception {
			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try {
				
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				if (blnSubRes) {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[text()='"
										+ strSubResource + "']"));
						log4j.info("SubResource "
								+ strSubResource
								+ " is displayed for parent resource.");
					} catch (AssertionError Ae) {

						strReason = "SubResource "
								+ strSubResource
								+ " is NOT displayed for parent resource.";
						log4j.info("SubResource "
								+ strSubResource
								+ " is NOT displayed for parent resource.");
					}
				} else {
					try {
						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[text()='"
										+ strSubResource + "']"));
						log4j.info("SubResource "
								+ strSubResource
								+ " is NOT displayed for parent resource.");
					} catch (AssertionError Ae) {
						strReason = "SubResource "
								+ strSubResource
								+ " is displayed for parent resource.";
						log4j.info("SubResource "
								+ strSubResource
								+ " is displayed for parent resource.");
					}
				}
			} catch (Exception e) {
				log4j.info("VerifySubResource function failed" + e);
				strReason = "VerifySubResource function failed" + e;
			}
			return strReason;
		}
		/************************************************************
		'Description	:Verify error message
		'Arguments		:selenium,strEventName		     
		'Returns		:String
		'Date	 		:15/07/2013
		'Author			:QSG
		'-------------------------------------------------------------
		'Modified Date                            Modified By
		'                               <Name>
		 **************************************************************/

		public String chkErrMsgForOnGoingEveInDemote(Selenium selenium)
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
								.isElementPresent("//div[@id='mainContainer']/div/ul/li"));
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
					assertEquals("The following error occurred on this page:", selenium.getText("css=span.emsErrorTitle"));
					assertEquals("You may not demote a resource that is in an ongoing event.",
							selenium.getText("css=div.emsError > ul > li"));
					
					log4j.info("You may not demote a resource that is in an ongoing event. error message is displayed.");
				} catch (AssertionError ae) {
					log4j.info("You may not demote a resource that is in an ongoing event. error message is NOT displayed.");
					strReason = "You may not demote a resource that is in an ongoing event. error message is NOT displayed.";
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = e.toString();
			}
			return strReason;
		}
		/************************************************************
		'Description	:Verify error message
		'Arguments		:selenium,strEventName		     
		'Returns		:String
		'Date	 		:18/07/2013
		'Author			:QSG
		'-------------------------------------------------------------
		'Modified Date                            Modified By
		'                               <Name>
		 **************************************************************/

		public String chkErrMsgForSharedRSInDemote(Selenium selenium)
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
								.isElementPresent("//div[@id='mainContainer']/div/ul/li"));
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
					assertEquals("The following error occurred on this page:", selenium.getText("css=span.emsErrorTitle"));
					assertEquals("You may not demote a resource that is shared with other regions.",
							selenium.getText("css=div.emsError > ul > li"));
					
					log4j.info("You may not demote a resource that is shared with other regions. error message is displayed.");
				} catch (AssertionError ae) {
					log4j.info("You may not demote a resource that is shared with other regions. error message is NOT displayed.");
					strReason = "You may not demote a resource that is shared with other regions. error message is NOT displayed.";
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = e.toString();
			}
			return strReason;
		}
		/************************************************************
		'Description	:Verify error message
		'Arguments		:selenium,strEventName		     
		'Returns		:String
		'Date	 		:18/07/2013
		'Author			:QSG
		'-------------------------------------------------------------
		'Modified Date                            Modified By
		'                               <Name>
		 **************************************************************/

		public String chkErrMsgForHAvBEDRSInDemote(Selenium selenium)
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
								.isElementPresent("//div[@id='mainContainer']/div/ul/li"));
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
					assertEquals("The following error occurred on this page:", selenium.getText("css=span.emsErrorTitle"));
					assertEquals("You may not demote a resource that reports HAvBED.",
							selenium.getText("css=div.emsError > ul > li"));
					
					log4j.info("You may not demote a resource that reports HAvBED. error message is displayed.");
				} catch (AssertionError ae) {
					log4j.info("You may not demote a resource that reports HAvBED. error message is NOT displayed.");
					strReason = "You may not demote a resource that reports HAvBED. error message is NOT displayed.";
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = e.toString();
			}
			return strReason;
		}
	
	//start//verifyLinksUndrActionInRSListPgeNew//
	/*******************************************************************************************
	' Description : verify Links Under Action In RS List Page
	' Precondition: N/A 
	' Arguments   : selenium, strResource
	' Returns     : String 
	' Date        : 22/07/2013
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyLinksUndrActionInRSListPgeNew(Selenium selenium,
			String strResource) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			int intNum = 0;

			try {

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[1][text()='Action']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[1][text()='Edit']"));
				log4j.info("Edit link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[2][text()='Status Types']"));

				log4j.info("Status type link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[3][text()='Users']"));
				log4j.info("Users link is present under Action coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[4][contains(text(),'Sub-Resources')]"));
				log4j.info("SubResources link is present under Action coloumn");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("Edit link is NOT present under Action coloumn");
					strErrorMsg = "Edit link NOT is present under Action coloumn";
					break;

				case 2:
					log4j.info("Status type link is NOT present under Action coloumn");
					strErrorMsg = "Status type link is NOT present under Action coloumn";
					break;

				case 3:
					log4j.info("Users link is NOT present under Action coloumn");
					strErrorMsg = "Users link is NOT present under Action coloumn";
					break;

				case 4:
					log4j.info("SubResources link is NOT present under Action coloumn");
					strErrorMsg = "SubResources link is NOT present under Action coloumn";
					break;
				}

			}

		} catch (Exception e) {
			log4j.info("verifyLinksUndrActionInRSListPgeNew function failed"
					+ e);
			strErrorMsg = "verifyLinksUndrActionInRSListPgeNew function failed"
					+ e;
		}
		return strErrorMsg;
	}
	//end//verifyLinksUndrActionInRSListPgeNew//
	
	//start//savAndVrfyErrMsgForRSWithSubRS//
	/*******************************************************************************************
	' Description : Verify error message
	' Precondition: N/A 
	' Arguments   : selenium, strResource, strSubResource, strUser
	' Returns     : String 
	' Date        : 22/07/2013
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String savAndVrfyErrMsgForRSWithSubRS(Selenium selenium,
			String strResource, String strSubResource, String strUser)
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
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Save")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li"));
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
				assertEquals("Create New Sub-Resource for " + strResource,
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(strUser
						+ " is retained on 'Create New Sub-Resource for <"+strResource+">' screen.");

			} catch (AssertionError Ae) {

				strReason = strUser
						+ " is NOT retained on 'Create New Sub-Resource for <"+strResource+">' screen."
						+ Ae;
				log4j.info(strUser
						+ " is NOT retained on 'Create New Sub-Resource for <"+strResource+">' screen."
						+ Ae);
			}

			try {
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[text()='"
								+ strSubResource + "']"));
				log4j.info("Sub-resource is NOT created. ");
			} catch (AssertionError Ae) {
				strReason = "Sub-resource is created.";
				log4j.info("Sub-resource is created. ");
			}
			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText("css=span.emsErrorTitle"));
				assertEquals(
						"Duplicate sub-resource names are not allowed within a resource.",
						selenium.getText("css=div.emsError > ul > li"));

				log4j.info("Duplicate sub-resource names are not allowed within a resource."
						+ " error message is displayed.");
			} catch (AssertionError ae) {
				log4j.info("Duplicate sub-resource names are not allowed within a resource."
						+ " error message is NOT displayed.");
				strReason = "Duplicate sub-resource names are not allowed within a resource."
						+ " error message is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	//end//savAndVrfyErrMsgForRSWithSubRS//
	/*******************************************************
	'Description	:Verify SubResource count not displayed In RSList
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:String
	'Date	 		:25-Feb-2013
	'Author			:QSG
	'------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                               <Name>
	*******************************************************/

	public String VerifySubResourceCountInRSListFalse(Selenium selenium,
			String strSubRSCount, String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {

					assertEquals("Resource List", selenium
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
				assertFalse(selenium
						.isElementPresent("//table/tbody/tr/td[text()='"
								+ strResource + "']"
								+ "/parent::tr/td[1]/a[text()='"
								+ strSubRSCount + "']"));
				log4j.info("'"
						+ strSubRSCount
						+ "' is not displayed for 'Sub-Resource' corresponding to resource "
						+ strResource + ".");

			} catch (AssertionError Ae) {

				strReason = "'"
						+ strSubRSCount
						+ "' is displayed for 'Sub-Resource' corresponding to resource "
						+ strResource + ".";
				log4j.info("'"
						+ strSubRSCount
						+ "' is displayed for 'Sub-Resource' corresponding to resource "
						+ strResource + ".");
			}

		} catch (Exception e) {
			log4j.info("VerifySubResourceCountInRSListFalse function failed" + e);
			strReason = "VerifySubResourceCountInRSListFalse function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:select and Deselect Include inactive resource check box
	'Arguments		:selenium,strStatusType,blnCheck
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                <Name>
	************************************************************************/
	
	public String selAndDeselIncludInactiveResrc(Selenium selenium,
			boolean blnCheck) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnCheck) {
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td/input[@class='checkbox'][@name='inactive_r']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked("//div[@id='mainContainer']/table/tbody/tr/td/input[@class='checkbox'][@name='inactive_r']")== false) {
					selenium.click("//div[@id='mainContainer']/table/tbody/tr/td/input[@class='checkbox'][@name='inactive_r']");
					selenium.waitForPageToLoad(gstrTimeOut);
				}
			} else {
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td/input[@class='checkbox'][@name='inactive_r']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked("//div[@id='mainContainer']/table/tbody/tr/td/input[@class='checkbox'][@name='inactive_r']")) {
					selenium.click("//div[@id='mainContainer']/table/tbody/tr/td/input[@class='checkbox'][@name='inactive_r']");
					selenium.waitForPageToLoad(gstrTimeOut);
				}
			}
			

		} catch (Exception e) {
			log4j.info("selAndDeselIncludInactiveResrc function failed" + e);
			strErrorMsg = "selAndDeselIncludInactiveResrc function failed" + e;
		}
		return strErrorMsg;
	}
	/********************************************************************************************************
	'Description	:verify resource details in Resource list page
	'Precondition	:None
	'Arguments		:selenium,strResource,strHavBed,strAHAId,strAbbrev,strResType
	'Returns		:String
	'Date	 		:06-11-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                              <Name>
	***********************************************************************************************************/
	
	
	public String verifyResourceHeaderInResrcListPge(Selenium selenium) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Resource List' screen is displayed ");
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[1][text()='Action']"));
				log4j.info("'Action' screen is displayed at the column headers ");
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[2][text()='Icon']"));
				log4j.info("'Icon' screen is displayed at the column headers ");
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable striped border sortable']/thead/tr/th/a[contains(text(),'Active')]"));
				log4j.info("'Active' screen is displayed at the column headers ");
				
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable striped border sortable']/thead/tr/th/a[contains(text(),'HAvBED')]"));
				log4j.info("'HAvBED' screen is displayed at the column headers ");
				
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable striped border sortable']/thead/tr/th/a[contains(text(),'AHA ID')]"));
				log4j.info("'AHA ID' screen is displayed at the column headers ");
				
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable striped border sortable']/thead/tr/th/a[contains(text(),'Name')]"));
				log4j.info("'Name' screen is displayed at the column headers ");
				
				
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable striped border sortable']/thead/tr/th/a[contains(text(),'Abbreviation')]"));
				log4j.info("'Abbreviation' screen is displayed at the column headers ");
				
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[@class='displayTable striped border sortable']/thead/tr/th/a[contains(text(),'Resource�Type')]"));
				log4j.info("'Resource�Type' screen is displayed at the column headers ");
				

			} catch (AssertionError Ae) {

				strReason = "'Resource List' screen is not displayed with all the column headers ";
				log4j.info("'Resource List' screen is not displayed with all the column headers ");
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifyResource function failed" + e);
			strReason = "saveAndVerifyResource function failed" + e;
		}
		return strReason;
	}
	/********************************************************************************************************
	'Description	:verify resource details in Resource list page
	'Precondition	:None
	'Arguments		:selenium,strResource,strHavBed,strAHAId,strAbbrev,strResType
	'Returns		:String
	'Date	 		:06-11-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                              <Name>
	***********************************************************************************************************/
	
	
	public String verifyResourceNotListedInResrcLstPge(Selenium selenium,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals("Resource List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[6][text()='"
								+ strResource + "']"));
				
				log4j
						.info("Resource "
								+ strResource
								+ " is NOT displayed in Resource List page with all other data");

			} catch (AssertionError Ae) {

				strReason = "Resource "
						+ strResource
						+ " is displayed in Resource List page with all other data";
				log4j
						.info("Resource "
								+ strResource
								+ " is displayed in Resource List page with all other data");
			}

		} catch (Exception e) {
			log4j.info("verifyResourceNotListedInResrcLstPge function failed" + e);
			strReason = "verifyResourceNotListedInResrcLstPge function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:select and Deselect Include inactive resource check box
	'Arguments		:selenium,strStatusType,blnCheck
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                <Name>
	************************************************************************/
	
	public String selAndDeselRepHavBedChkBox(Selenium selenium,
			boolean blnHavBedData) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnHavBedData) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ReportHavBedData")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateResource.ReportHavBedData"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ReportHavBedData")))
					selenium.click(propElementDetails
							.getProperty("CreateResource.ReportHavBedData"));
			}

		} catch (Exception e) {
			log4j.info("selAndDeselIncludInactiveResrc function failed" + e);
			strErrorMsg = "selAndDeselIncludInactiveResrc function failed" + e;
		}
		return strErrorMsg;
	}
	//start//savAndVrfyErrMsgForRSWithSubRS//
	/*******************************************************************************************
	' Description : Verify error message
	' Precondition: N/A 
	' Arguments   : selenium, strResource, strSubResource, strUser
	' Returns     : String 
	' Date        : 22/07/2013
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String savAndVrfyErrMsgRepHavBedData(Selenium selenium)
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
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Save")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText("css=span.emsErrorTitle"));
				assertEquals(
						"The 'State' is required for resources that report HAvBED data.",
						selenium.getText("css=div.emsError > ul > li"));

				log4j.info("�The 'State' is required for resources that report HAvBED data."
						+ " error message is displayed.");
			} catch (AssertionError ae) {
				log4j.info("�The 'State' is required for resources that report HAvBED data."
						+ " error message is NOT displayed.");
				strReason = "�The 'State' is required for resources that report HAvBED data."
						+ " error message is NOT displayed.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	 /*******************************************************************
	  'Description :navigate Refine Visible Status Types pop up screen by 
	      clicking on Refine link for particular resource
	  'Precondition :None
	  'Arguments  :selenium,strResource
	  'Returns  :String
	  'Date    :28-May-2012
	  'Author   :QSG
	  '------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                   <Name>
	  ********************************************************************/

	public String navToRefineVisibleSTInAssignUsrPage(Selenium selenium,
			String strUserFullName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on Refine link
			selenium.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
					+ strUserFullName
					+ "']/parent::tr/td[4]/a[text()='Refine']");
			try {
				selenium.waitForPageToLoad("30000");
			} catch (Exception e) {
				log4j.info("Wait for page load is working");
			}

			// Wait till pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 60) {
				try {
					selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			if (blnFound) {
				log4j.info("Pop up window is displayed");

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				try {
					assertEquals("Refine Visible Status Types",
							selenium.getText("css=#TB_ajaxWindowTitle"));
					log4j.info("Refine Visible Status Types window is displayed");

				} catch (AssertionError Ae) {
					log4j.info("Refine Visible Status Types window NOT displayed");
					strErrorMsg = "Refine Visible Status Types window NOT displayed";
				}

			} else {
				log4j.info("Pop up window is NOT displayed");
				strErrorMsg = "Pop up window is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("navToRefineVisibleST function failed" + e);
			strErrorMsg = "navToRefineVisibleST function failed" + e;
		}
		return strErrorMsg;
	}

	 /*******************************************************************
	  'Description :Click on Save Changes in Refine Visible Status Type and check
	      Edit User Page is displayed.
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :String
	  'Date    :28-May-2012
	  'Author   :QSG
	  '------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                   <Name>
	  ********************************************************************/

	public String saveChangesInRefineSTAndVerifyAssignUserPage(
			Selenium selenium, String strResource) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click save changes

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");

			selenium.click("css=input[value='Save Changes']");
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {
					assertEquals("Assign Users to " + strResource,
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
				assertEquals("Assign Users to " + strResource,
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Assign Users to " + strResource
						+ "screen is displayed ");
			} catch (AssertionError Ae) {

				strErrorMsg = "Assign Users to " + strResource
						+ "screen is NOT displayed" + Ae;
				log4j.info("Assign Users to " + strResource
						+ "screen is NOT displayed ");
			}

		} catch (Exception e) {
			log4j.info("saveChangesInRefineSTAndVerifyAssignUserPage function failed"
					+ e);
			strErrorMsg = "saveChangesInRefineSTAndVerifyAssignUserPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	//start//assignUserRights//
	/*******************************************************************************************
	' Description	: check Associated  with ,Update status , Run Reports and View Resource check boxes are associated with user.
	' Precondition	: N/A 
	' Arguments		: selenium
	' Returns		: String 
	' Date			: 04/09/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String assignUserRights(Selenium selenium) throws Exception {
		String strReason = "";

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

			try {
				assertTrue(selenium
						.isElementPresent("css=input[name='SELECT_ALL'][value='association']"));
				log4j.info("Associated with is associated with each user");
			} catch (AssertionError Ae) {
				strReason = "Associated with is not associated with each user";
				log4j.info("Associated with is not associated with each user");
			}
			try {
				assertTrue(selenium
						.isElementPresent("css=input[name='SELECT_ALL'][value='updateRight']"));
				log4j.info("Update Right  is associated with each user");
			} catch (AssertionError Ae) {
				strReason = "Update Right is not associated with each user";
				log4j.info("Update Right is not associated with each user");
			}
			try {
				assertTrue(selenium
						.isElementPresent("css=input[name='SELECT_ALL'][value='reportRight']"));
				log4j.info("Report Right is associated with each user");
			} catch (AssertionError Ae) {
				strReason = "Report Right  is not associated with each user";
				log4j.info("Report Right  is not associated with each user");
			}
			try {
				assertTrue(selenium
						.isElementPresent("css=input[name='SELECT_ALL'][value='viewRight']"));
				log4j.info("View Right  is associated with each user");
			} catch (AssertionError Ae) {
				strReason = "View Right is not associated with each user";
				log4j.info("View Right is not associated with each user");
			}
		} catch (Exception e) {
			log4j.info("assignUserRights function failed " + e);
			strReason = "assignUserRights function failed " + e;
		}

		return strReason;
	}

	// end//assignUserRights//
	
	//start//Verify select Resource Rights//
	
	 /**********************************************************************
	   'Description 	:Verify select Resource Rights
	   'Precondition 	:None
	   'Arguments  		:selenium,blnAssocWith,blnUpdStat,blnRunReport,
	   '    		     blnViewRes, strUsrFulName
	   'Returns  		:String
	   'Date    		:04-Sep-2013
	   'Author   		:QSG
	   '----------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '04-09-2013                               <Name>
	   **********************************************************************/

	public String checkUserRightsInAssignUserPage(Selenium selenium,
			String strUserName, boolean blnAssocWith, boolean blnUpdStat,
			boolean blnRunReport, boolean blnViewRes) {
		String strReason = "";
		try {
			// Select Associated With option
			if (blnAssocWith) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='association'][value='"
									+ strUserName + "']"));
					log4j.info("Associated right is selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("Associated right is NOT selected for user "
							+ strUserName);
					strReason = "Associated right is NOT selected for user "
							+ strUserName;
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='association'][value='"
									+ strUserName + "']"));
					log4j.info("Associated right is not selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("Associated right is selected for user "
							+ strUserName);
					strReason = "Associated right is selected for user "
							+ strUserName;
				}
			}

			// Select Update Status option
			if (blnUpdStat) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='updateRight'][value='"
									+ strUserName + "']"));
					log4j.info("Update right is selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("Update right is NOT selected for user "
							+ strUserName);
					strReason = "Update right is NOT selected for user "
							+ strUserName;
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='updateRight'][value='"
									+ strUserName + "']"));
					log4j.info("Update right is not selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("Update right is selected for user "
							+ strUserName);
					strReason = "Update right is selected for user "
							+ strUserName;
				}
			}

			// Select Run reports option
			if (blnRunReport) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='reportRight'][value='"
									+ strUserName + "']"));
					log4j.info("Run report right is selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("Run report right is NOT selected for user "
							+ strUserName);
					strReason = "Run report right is NOT selected for user "
							+ strUserName;
				}

			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='reportRight'][value='"
									+ strUserName + "']"));
					log4j.info("Run report right is not selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("Run report right is selected for user "
							+ strUserName);
					strReason = "Run report right is selected for user "
							+ strUserName;
				}
			}

			// Select View Resource option
			if (blnViewRes) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='viewRight'][value='"
									+ strUserName + "']"));
					log4j.info("View Resource right is selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("View Resource right is NOT selected for user "
							+ strUserName);
					strReason = "View Resource right is NOT selected for user "
							+ strUserName;
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='viewRight'][value='"
									+ strUserName + "']"));
					log4j.info("View Resource right is not selected for user "
							+ strUserName);
				} catch (AssertionError ae) {
					log4j.info("View Resource right is selected for user "
							+ strUserName);
					strReason = "View Resource right is selected for user "
							+ strUserName;
				}
			}

		} catch (Exception e) {
			log4j.info("checkUserRightsWitRSValues function failed" + e);
			strReason = "checkUserRightsWitRSValues function failed" + e;
		}
		return strReason;
	}
	// ends//Verify select Resource Rights //
	
	/********************************************************************************************************
	'Description	:Fill all the necessary fields in create resource page 
	'Precondition	:None
	'Arguments		:selenium, strResource, strAbbrv, strResType,strStandResType
					,blnHavBedData,blnShareReg,AHAId,strExternalId,blnTriagPart,strStreetAddr,strCity,strState,
					strZipCode,strCounty,strWebSite,strContFName,strContLName,strTitle,strContAddr,strContPh1,
					strContPh2,strContPh2,strContFax,strContEmail,strNotes
	'Returns		:String
	'Date	 		:03-Sep-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'                                         <Name>
	***********************************************************************************************************/

	public String createResource_FillAllFieldsNew(Selenium selenium,
			String strResource, String strAbbrv, String strResType,
			String strStandResType, boolean blnHavBedData, boolean blnShareReg,
			String AHAId, String strExternalId, boolean blnTriagPart,
			String strStreetAddr, String strCity, String strState,
			String strZipCode, String strCounty, String strWebSite,
			String strContFName, String strContLName, String strTitle,
			String strContAddr, String strContPh1, String strContPh2,
			String strContFax, String strContEmail, String strNotes)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(
					propElementDetails.getProperty("CreateResource.Name"),
					strResource);
			selenium.type(propElementDetails
					.getProperty("CreateResource.Abbreviation"), strAbbrv);
			if (selenium.isElementPresent(propElementDetails
					.getProperty("CreateResource.ResourceType"))) {
				selenium.select(propElementDetails
						.getProperty("CreateResource.ResourceType"), "label="
						+ strResType);
			}

			selenium.select(propElementDetails
					.getProperty("CreateResource.StandResType"), "label="
					+ strStandResType);

			if (blnHavBedData) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ReportHavBedData")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateResource.ReportHavBedData"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ReportHavBedData")))
					selenium.click(propElementDetails
							.getProperty("CreateResource.ReportHavBedData"));
			}

			if (blnShareReg) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ShareWithOtherReg")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateResource.ShareWithOtherReg"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ShareWithOtherReg")))
					selenium.click(propElementDetails
							.getProperty("CreateResource.ShareWithOtherReg"));
			}

			selenium.type(
					propElementDetails.getProperty("CreateResource.AHAId"),
					AHAId);
			selenium.type(
					propElementDetails.getProperty("CreateResource.ExternalId"),
					strExternalId);

			if (blnTriagPart) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ITriagePart")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateResource.ITriagePart"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateResource.ITriagePart")))
					selenium.click(propElementDetails
							.getProperty("CreateResource.ITriagePart"));
			}

			selenium.select(
					propElementDetails.getProperty("CreateResource.State"),
					"label=" + strState);

			int intCnt = 0;
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

			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactFName"), strContFName);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactLName"), strContLName);
			selenium.select(
					propElementDetails.getProperty("CreateResource.County"),
					"label=" + strCounty);
			selenium.select(propElementDetails
					.getProperty("CreateResource.StandResType"), "label="
					+ strStandResType);
			selenium.click(propElementDetails
					.getProperty("CreateRes.CreateNewResource.LookUpAddr"));

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

			selenium.type(
					propElementDetails.getProperty("CreateResource.StreetAddr"),
					strStreetAddr);
			selenium.type(
					propElementDetails.getProperty("CreateResource.City"),
					strCity);
			selenium.type(
					propElementDetails.getProperty("CreateResource.ZipCode"),
					strZipCode);
			selenium.type(
					propElementDetails.getProperty("CreateResource.Website"),
					strWebSite);
			selenium.type(
					propElementDetails.getProperty("CreateResource.Title"),
					strTitle);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactAddr"), strContAddr);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactPhone1"), strContPh1);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactPhone2"), strContPh2);
			selenium.type(
					propElementDetails.getProperty("CreateResource.ContactFax"),
					strContFax);
			selenium.type(propElementDetails
					.getProperty("CreateResource.ContactEmail"), strContEmail);
			selenium.type(
					propElementDetails.getProperty("CreateResource.Notes"),
					strNotes);

		} catch (Exception e) {
			log4j.info("createResource_FillAllFieldsNew function failed" + e);
			strReason = "createResource_FillAllFieldsNew function failed" + e;
		}
		return strReason;
	}
	//start//fetchLatitudeAndLongitudeValue//
	/*******************************************************************************************
	' Description	: function to fetch Latitude and Longitude Value
	' Precondition	: N/A 
	' Arguments		: selenium
	' Returns		: String[] 
	' Date			: 03/09/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String[] fetchLatitudeAndLongitudeValue(Selenium selenium)
			throws Exception {
		String strReason[] = new String[2];
		strReason[0] = "";
		strReason[1] = "";
		try {
			int intCnt = 0;
			do {
				try {

					assertEquals("Edit Resource",
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
				assertEquals("Edit Resource",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Edit Resource page is displayed");

			} catch (AssertionError Ae) {

				strReason[0] = "Edit Resource page is NOT displayed" + Ae;
				log4j.info("Edit Resource page is NOT displayed" + Ae);
			}

			strReason[0] = selenium.getValue("id=latitude");
			log4j.info("'Value of 'Latitude' is  " + strReason[0]);

			strReason[1] = selenium.getValue("id=longitude");
			log4j.info("'Value of 'Latitude' is  " + strReason[1]);

		} catch (Exception e) {
			strReason[0] = " fetchLatitudeAndLongitudeValue function failed"
					+ e;
			log4j.info("fetchLatitudeAndLongitudeValue function failed" + e);
		}
		return strReason;
	}
	// end//fetchLatitudeAndLongitudeValue//
	
	//start//Verify all data retained in edit resource page//
	/********************************************************************************************************
	'Description	:Verify all data retained in edit resource page. 
	'Precondition	:None
	'Arguments		:selenium, strResource, strAbbrv, strResType,strStandResType,
					 blnHavBedData,blnShareReg,AHAId,strExternalId,blnTriagPart,strStreetAddr,strCity,strState,
					 strZipCode,strCounty,strWebSite,strContFName,strContLName,strTitle,strContAddr,strContPh1,
					 strContPh2,strContPh2,strContFax,strContEmail,strNotes
	'Returns		:String
	'Date	 		:04-Sep-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'                                         <Name>
	***********************************************************************************************************/

	public String verifyAllDataRetainedInEditRSPage(Selenium selenium,
			String strResource, String strAbbrv, String strResType,
			String strStandResType, boolean blnHavBedData, boolean blnShareReg,
			String AHAId, String strExternalId, boolean blnTriagPart,
			String strStreetAddr, String strCity, String strState,
			String strZipCode, String strCounty, String strWebSite,
			String strContFName, String strContLName, String strTitle,
			String strContAddr, String strContPh1, String strContPh2,
			String strContFax, String strContEmail, String strNotes)
			throws Exception {
		String strReason = "";

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
			try {
				assertEquals(strResource, selenium.getValue(propElementDetails
						.getProperty("CreateResource.Name")));
				assertEquals(strAbbrv, selenium.getValue(propElementDetails
						.getProperty("CreateResource.Abbreviation")));

				assertEquals(strResType,
						selenium.getSelectedLabel(propElementDetails
								.getProperty("CreateResource.ResourceType")));
				assertEquals(strStandResType,
						selenium.getSelectedLabel(propElementDetails
								.getProperty("CreateResource.StandResType")));

				if (blnHavBedData) {

					assertTrue(selenium.isChecked(propElementDetails
							.getProperty("CreateResource.ReportHavBedData")));
					log4j.info("HavBedData is selected");

				} else {
					assertFalse(selenium.isChecked(propElementDetails
							.getProperty("CreateResource.ReportHavBedData")));
					log4j.info("HavBedData is NOT selected");
				}

				if (blnShareReg) {

					assertTrue(selenium.isChecked(propElementDetails
							.getProperty("CreateResource.ShareWithOtherReg")));
					log4j.info("SharedRegion is selected");

				} else {
					assertFalse(selenium.isChecked(propElementDetails
							.getProperty("CreateResource.ShareWithOtherReg")));
					log4j.info("SharedRegion is NOT selected");
				}

				if (blnTriagPart) {

					assertTrue(selenium.isChecked(propElementDetails
							.getProperty("CreateResource.ITriagePart")));
					log4j.info("TriagPart is selected");

				} else {
					assertFalse(selenium.isChecked(propElementDetails
							.getProperty("CreateResource.ITriagePart")));
					log4j.info("TriagPart is NOT selected");
				}

				assertEquals(AHAId, selenium.getValue(propElementDetails
						.getProperty("CreateResource.AHAId")));
				assertEquals(strExternalId,
						selenium.getValue(propElementDetails
								.getProperty("CreateResource.ExternalId")));

				assertEquals(strStreetAddr,
						selenium.getValue(propElementDetails
								.getProperty("CreateResource.StreetAddr")));
				assertEquals(strCity, selenium.getValue(propElementDetails
						.getProperty("CreateResource.City")));

				assertEquals(strState,
						selenium.getSelectedLabel(propElementDetails
								.getProperty("CreateResource.State")));
				assertEquals(strZipCode, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ZipCode")));

				assertEquals(strCounty,
						selenium.getSelectedLabel(propElementDetails
								.getProperty("CreateResource.County")));
				assertEquals(strWebSite, selenium.getValue(propElementDetails
						.getProperty("CreateResource.Website")));

				assertEquals(strContFName, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactFName")));
				assertEquals(strContLName, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactLName")));

				assertEquals(strTitle, selenium.getValue(propElementDetails
						.getProperty("CreateResource.Title")));
				assertEquals(strContAddr, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactAddr")));

				assertEquals(strContPh1, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactPhone1")));
				assertEquals(strContPh2, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactPhone2")));

				assertEquals(strContFax, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactFax")));
				assertEquals(strContEmail, selenium.getValue(propElementDetails
						.getProperty("CreateResource.ContactEmail")));

				assertEquals(strNotes, selenium.getValue(propElementDetails
						.getProperty("CreateResource.Notes")));

				log4j.info("The values provided while creating the resource are retained");

			} catch (AssertionError Ae) {
				log4j.info(Ae);

				log4j.info("The values provided while uploading the resource is NOT retained");

				strReason = strReason
						+ "; "
						+ "The values provided while uploading the resource is NOT retained";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = strReason + "; " + e.toString();
		}

		return strReason;
	}
	// end//Verify all data retained in edit resource page//
	
	/***********************************************************************
	'Description	:Click on UserLink Image and verify
	'Precondition	:None
	'Arguments		:selenium,strURL,strTitle
	'Returns		:String
	'Date	 		:24-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/

	public String openResourcWebSiteInNewWindow(Selenium selenium,
			String strURL, String strTitle) {
		String strReason = "";

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (selenium.isElementPresent("link=" + strURL)) {
				log4j.info("Website: (Provided website address hyper linked )");
			}
			//Click on website
			selenium.click("link=" + strURL);
			selenium.waitForPopUp("", gstrTimeOut);
			selenium.selectWindow("");
			selenium.selectFrame("relative=up");

			try {
				assertEquals(strTitle, selenium.getTitle());
				log4j.info("Provided web Address is opened in new window. ");
				selenium.close();
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				strReason = "Provided web Address is NOT opened in new window. ";
				log4j.info("Provided web Address is NOT opened in new window. ");
			}

		} catch (Exception e) {
			log4j.info("openResourcWebSiteInNewWindow function failed" + e);
			strReason = "openResourcWebSiteInNewWindow function failed" + e;
		}
		return strReason;
	}
	//start//checkUserInAssignUserPage//
	/*******************************************************************************************
	' Description		: Check user listed or not in assign user page
	' Precondition		: N/A 
	' Arguments			: selenium, strUserName, blnPresent
	' Returns			: String 
	' Date				: 16/09/2013
	' Author			: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String checkUserInAssignUserPage(Selenium selenium, String strUserName, boolean blnPresent) throws Exception
	{
		String strReason="";

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
			try
			{
				if(blnPresent)
				{
				assertTrue(selenium
						.isElementPresent("//table[@id='tbl_association']/tbody/tr/td"
								+ "[text()='" + strUserName + "']"));
				assertTrue(selenium
						.isVisible("//table[@id='tbl_association']/tbody/tr/td"
								+ "[text()='" + strUserName + "']"));
				log4j.info("User '"
						+ strUserName
						+ "' is  listed in assign user page. ");
				
				}
				else
				{
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_association']/tbody/tr/td"
									+ "[text()='" + strUserName + "']"));
					assertFalse(selenium
							.isVisible("//table[@id='tbl_association']/tbody/tr/td"
									+ "[text()='" + strUserName + "']"));
					log4j.info("User '"
							+ strUserName
							+ "' is NOT  listed in assign user page. ");
				}
			}catch (AssertionError Ae) {
				log4j.info("User '"
						+ strUserName
						+ "' is NOT  listed in assign user page. ");
				strReason="User '"
						+ strUserName
						+ "' is NOT  listed in assign user page. ";
			}
		}catch(Exception e){
			log4j.info("checkUserInAssignUserPage function failed" +e);
			strReason = "checkUserInAssignUserPage function failed";
		}

		return strReason;
	}
	//end//checkUserInAssignUserPage//
	
	/***********************************************************************
	'Description	:verifyLinksUndrActionInRSListPgeAsUserLogin
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:25-Oct-2013S
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Oct-2012                               <Name>
	************************************************************************/

	public String verifyLinksUndrActionInRSListPgeAsUserLogin(Selenium selenium,
			String strResource) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			int intNum = 0;

			try {

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[1][text()='Action']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[1][text()='Edit']"));
				log4j.info("Edit link is present under Action coloumn");

				
				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[2][contains(text(),'Users')]"));
				log4j.info("Users link is present under Action coloumn");
				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[3][contains(text(),'Sub-Resources')]"));
				log4j.info("SubResources link is present under Action coloumn");
				

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("Edit link is NOT present under Action coloumn");
					strErrorMsg = "Edit link NOT is present under Action coloumn";
					break;
				case 2:
					log4j.info("SubResources link is NOT present under Action coloumn");
					strErrorMsg = "SubResources link is NOT present under Action coloumn";
					break;

				
				}

			}

		} catch (Exception e) {
			log4j.info("verifyLinksUndrActionInRSListPgeUserLogin function failed" + e);
			strErrorMsg = "verifyLinksUndrActionInRSListPgeUserLogin function failed"
					+ e;
		}
		return strErrorMsg;
	}

// starts//vrfyRoleInAssignUsrOfRSInRoleDropDown//
	/*******************************************************************
	' Description  :Function used to verify the role in role in dropdown
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 13/09/2013
	' Author       : Ajanta 
	'-------------------------------------------------------------------
	' Modified Date: 
	' Modified By: 
	********************************************************************/

	public String vrfyRoleInAssignUsrOfRSInRoleDropDown(Selenium selenium,
			String strRoleName, boolean blnRole, String strResource)
			throws Exception {
		String strReason = "";

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
			if (blnRole) {
				try {
					assertTrue(selenium
							.isElementPresent("//select[@id='tbl_association_ROLE']/option[text()='"
									+ strRoleName + "']"));

					log4j.info("Role "
							+ strRoleName
							+ " is displayed in Search by role dropdown of Assign Users to "
							+ strResource);

				} catch (AssertionError Ae) {
					log4j.info("Role "
							+ strRoleName
							+ " is NOT displayed in Search by role dropdown of Assign Users to "
							+ strResource);
					strReason = "Role "
							+ strRoleName
							+ " is NOT displayed in Search by role dropdown of Assign Users to "
							+ strResource + Ae;
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='tbl_association_ROLE']/option[text()='"
									+ strRoleName + "']"));

					log4j.info("Role "
							+ strRoleName
							+ " is NOT displayed in Search by role dropdown of Assign Users to "
							+ strResource);

				} catch (AssertionError Ae) {
					log4j.info("Role "
							+ strRoleName
							+ " is displayed in Search by role dropdown of Assign Users to "
							+ strResource);
					strReason = "Role "
							+ strRoleName
							+ " is displayed in Search by role dropdown of Assign Users to "
							+ strResource + Ae;
				}
			}

		} catch (Exception e) {
			log4j.info("vrfyRoleInAssignUsrOfRSInRoleDropDown function failed"
					+ e);
			strReason = "vrfyRoleInAssignUsrOfRSInRoleDropDown function failed"
					+ e;
		}

		return strReason;
	}

	// end//vrfyRoleInAssignUsrOfRSInRoleDropDown//
}