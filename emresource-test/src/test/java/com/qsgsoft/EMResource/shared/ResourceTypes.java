package com.qsgsoft.EMResource.shared;


import java.util.Properties;

import org.apache.log4j.Logger;

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

public class ResourceTypes {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.ResourceTypes");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	
	String gstrTimeOut="";

	ReadData rdExcel;
	
	/***********************************************************************
	'Description	:Verify Resource type list page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String navResourceTypList(Selenium selenium) throws Exception {

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
					.getProperty("SetUP.ResourceTypesLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt = 0;
			do {
				try {

					assertEquals("Resource Type List", selenium
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
					assertEquals("Resource Type List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Resource Type List page is displayed");
					
				} catch (AssertionError Ae) {

					strErrorMsg = "Resource Type List page is NOT displayed" + Ae;
					log4j.info("Resource Type List page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
	
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are edited
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String navToeditResrcTypepage(Selenium selenium,
			String strResrcTypName) throws Exception {

		String strErrorMsg = "";// variable to store error message

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
							+ strResrcTypName
							+ "']/"
							+ "parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try {
					assertEquals("Edit Resource Type", selenium
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
				try {
					assertEquals("Edit Resource Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Edit Resource Type page is displayed");
				} catch (AssertionError Ae) {

					strErrorMsg = "Edit Resource Type  page is NOT displayed"
							+ Ae;
					log4j.info("Edit Resource Type page is NOT displayed" + Ae);
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Resource Type  page is NOT displayed" + Ae;
				log4j.info("Edit Resource Type  Type page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navToeditResrcTypepage function failed" + e);
			strErrorMsg = "navToeditResrcTypepage function failed" + e;
		}
		return strErrorMsg;
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
	public String fetchResTypeValueInResTypeList(Selenium selenium,
			String strResourceType) throws Exception {
		String strRTValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/thead/tr/th/a[contains(text(),'Name')]"
							+ "/ancestor::table/tbody/tr/td[2][text()='"
							+ strResourceType + "']/parent::tr/td[1]"
							+ "/a"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			
			String strRTValueArr[] = selenium.getAttribute(
					"//div[@id='mainContainer']/table[2]/thead/tr/th/a[contains(text(),'Name')]"
							+ "/ancestor::table/tbody/tr/td[2][text()='"
							+ strResourceType + "']/parent::tr/td[1]"
							+ "/a@href").split("resourceTypeID=");
			strRTValue = strRTValueArr[1];
			log4j.info("Resource Type Value is " + strRTValue);
		} catch (Exception e) {
			log4j.info("fetchResValueInResList function failed" + e);
			strRTValue = "";
		}
		return strRTValue;
	}
	

	/*******************************************************************************************
	' Description: select Default Status Type field in Create New Resource Type page
	' Precondition: N/A 
	' Arguments: selenium, pStrStatType
	' Returns: String 
	' Date: 18-09-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String selectDefaultSTInCreateResType(Selenium selenium, String pStrStatType) throws Exception
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
			selenium.select(propElementDetails.getProperty("Prop1929"), pStrStatType);
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String resrcTypeMandatoryFlds(Selenium selenium,
			String strResrctTypName, String strStstTypLabl) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewRsrcTypeLink")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<40);
			

			selenium.click(propElementDetails
					.getProperty("CreateNewRsrcTypeLink"));

			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Create New Resource Type", selenium
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
				assertEquals("Create New Resource Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New Resource Type page is displayed");

				if (strResrctTypName != "") {

					intCnt = 0;
					do {
						try {

							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("CreateNewRsrcType.ResrcName")));
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCnt++;

						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					} while (intCnt < 60);

					selenium.type(propElementDetails
							.getProperty("CreateNewRsrcType.ResrcName"),
							strResrctTypName);
				}
				
				intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent(strStstTypLabl));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				selenium.click(strStstTypLabl);

			} catch (AssertionError Ae) {

				strErrorMsg = "Create New Resource Type List page is NOT displayed"
						+ Ae;
				log4j.info("Create New Resource Type page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("resrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "resrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String resrcTypeMandatoryFldsNew(Selenium selenium,
			String strResrcTypName, String strStstTypValue) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails
					.getProperty("CreateNewRsrcTypeLink"));

			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Create New Resource Type", selenium
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
				assertEquals("Create New Resource Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New Resource Type page is displayed");

				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("CreateNewRsrcType.ResrcName")));
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
						.getProperty("CreateNewRsrcType.ResrcName"),
						strResrcTypName);

				selenium.click("css=input[name='statusTypeID'][value='"
						+ strStstTypValue + "']");

			} catch (AssertionError Ae) {

				strErrorMsg = "Create New Resource Type List page is NOT displayed"
						+ Ae;
				log4j.info("Create New Resource Type page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("resrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "resrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are edited
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String editResrcTypeMandatoryFlds(Selenium selenium,
			String strResrcTypName, String streditResrcTypName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("ResourceTypeList.IncludeInactiveRT"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strResrcTypName
							+ "']/"
							+ "parent::tr/td[1]/a[text()='Edit']");

			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("", strErrorMsg);

				log4j.info("Edit Resource Type page is displayed");

				try {
					assertEquals("Edit Resource Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Edit Resource Type page is displayed");

					if (streditResrcTypName.compareTo("") != 0) {
						selenium.type(propElementDetails
								.getProperty("CreateNewRsrcType.ResrcName"),
								streditResrcTypName);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Edit Resource Type  page is NOT displayed"
							+ Ae;
					log4j.info("Edit Resource Type page is NOT displayed" + Ae);
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Resource Type  page is NOT displayed" + Ae;
				log4j.info("Edit Resource Type  Type page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("editResrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "editResrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are edited
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String editResrcTypeMandatoryFlds_new(Selenium selenium,
			String strResrcTypName, String streditResrcTypName,
			String strStstTypLabl, boolean blnInactive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnInactive) {

				if (selenium.isChecked(propElementDetails
						.getProperty("ResourceTypeList.IncludeInactiveRT")) == false) {
					selenium.click(propElementDetails
							.getProperty("ResourceTypeList.IncludeInactiveRT"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("ResourceTypeList.IncludeInactiveRT"))) {
					selenium.click(propElementDetails
							.getProperty("ResourceTypeList.IncludeInactiveRT"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			}

			selenium
					.click("//div[@id='mainContainer']/table[@class='displayTable "
							+ "striped border sortable']/tbody/tr/td[2][text()='"
							+ strResrcTypName + "']/" + "parent::tr/td[1]/a");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Resource Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Edit Resource Type page is displayed");

				if (strResrcTypName != "") {
					selenium.type(propElementDetails
							.getProperty("CreateNewRsrcType.ResrcName"),
							streditResrcTypName);
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Resource Type  page is NOT displayed" + Ae;
				log4j.info("Edit Resource Type page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("editResrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "editResrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:select and Deselect stauts types in Edit Resource type
	'Arguments		:selenium,strStatusType,blnCheck
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String selAndDeselSTInEditRT(Selenium selenium,
			String strStatusType, boolean blnCheck) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if (blnCheck) {
				
				int intCnt=0;
				do{
					try{
					assertTrue(selenium.isElementPresent("css=input[name='statusTypeID'][value='"+strStatusType+"']"));
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

				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatusType + "']") == false) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strStatusType + "']");

				}
			} else {
				
				int intCnt=0;
				do{
					try{
						assertTrue(selenium
								.isElementPresent(("css=input[name='statusTypeID'][value='"
										+ strStatusType + "']")));
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
				
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatusType + "']")) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strStatusType + "']");

				}
			}

		} catch (Exception e) {
			log4j.info("selAndDeselSTInEditRT function failed" + e);
			strErrorMsg = "selAndDeselSTInEditRT function failed" + e;
		}
		return strErrorMsg;
	}
	
	/********************************************************************************
		   'Description :Fetch Role Value in Role List page
		   'Precondition :None
		   'Arguments  :selenium,strRole
		   'Returns  :String
		   'Date    :8-June-2012
		   'Author   :QSG
		   '-----------------------------------------------------------------------
		   'Modified Date                            Modified By
		   '<Date>                                    <Name>
		   *****************************************************************************/
	public String fetchRTValueInRTList(Selenium selenium, String strRT)
			throws Exception {
		String strRTValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("SetUP.SetUpLink")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			String strResValueArr[] = selenium.getAttribute(
					"//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strRT + "']/parent::tr/td[1]/a@href").split(
					"resourceTypeID=");
			strRTValue = strResValueArr[1];
			log4j.info("RT Value is " + strRTValue);
		} catch (Exception e) {
			log4j.info("fetchRTValueInRTList function failed" + e);
			strRTValue = "";
		}
		return strRTValue;
	}

	/***********************************************************************
	'Description	:select and deselevt include button
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String selectDesectIncludeInactiveRT(Selenium selenium,
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
						.getProperty("ResourceTypeList.IncludeInactiveRT")) == false) {
					selenium.click(propElementDetails
							.getProperty("ResourceTypeList.IncludeInactiveRT"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("ResourceTypeList.IncludeInactiveRT"))) {
					selenium.click(propElementDetails
							.getProperty("ResourceTypeList.IncludeInactiveRT"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			}
		} catch (Exception e) {
			log4j.info("selectDesectIncludeInactiveRT function failed" + e);
			strErrorMsg = "selectDesectIncludeInactiveRT function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Check Status type is present and is selected or deselected
					in Edit Status Type page.
	'Arguments		:selenium,strStatType,strStatusType,blnPresent,blnChecked
	'Returns		:String
	'Date	 		:12-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String checkSTInEditResTypePage(Selenium selenium,
			String strStatType, String strStatTypeVal, boolean blnPresent,
			boolean blnChecked) throws Exception {

		String strReason = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnPresent) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='statusTypeID'][value='"
									+ strStatTypeVal + "']"));
					log4j
							.info("Status Type "
									+ strStatType
									+ " is listed in the 'Edit Resource-Level Status Types' screen");

					if (blnChecked) {
						try {
							assertTrue(selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strStatTypeVal + "']"));
							log4j.info("Status Type " + strStatType
									+ " is selected");

						} catch (AssertionError ae) {
							log4j.info("Status Type " + strStatType
									+ " is NOT selected");
							strReason = "Status Type " + strStatType
									+ " is NOT selected";
						}
					} else {
						try {
							assertFalse(selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strStatTypeVal + "']"));
							log4j.info("Status Type " + strStatType
									+ " is not selected");

						} catch (AssertionError ae) {
							log4j.info("Status Type " + strStatType
									+ " is selected");
							strReason = "Status Type " + strStatType
									+ " is selected";
						}
					}

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
			log4j.info("checkSTInEditResTypePage function failed" + e);
			strReason = "checkSTInEditResTypePage function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify View Other Region Security  type page is displayed
	'Precondition	:None
	'Arguments		:selenium,blnBack
	'Returns		:String
	'Date	 		:19-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'19-April-2012                               <Name>
	************************************************************************/
	
	public String navViewOtherRegionSecurity_Resources(Selenium selenium,
			boolean blnBack) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("OtherRegionList.ShareResources"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("View Other Region Security - Resources", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("View Other Region Security - Resources "
						+ "page is displayed");

				if (blnBack) {
					selenium
							.click(propElementDetails
									.getProperty("ViewOtherRegionSecurityResources.Back"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {

						assertEquals("Other Region List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Other Region List page is displayed");

					} catch (AssertionError Ae) {

						strErrorMsg = "Other Region List page is NOT displayed"
								+ Ae;
						log4j.info("Other Region List page is NOT displayed"
								+ Ae);
					}
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "View Other Region Security - Resources page "
						+ "is NOT displayed" + Ae;
				log4j.info("View Other Region Security - Resources page"
						+ " is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navViewOtherRegionSecurityPge function failed" + e);
			strErrorMsg = "navViewOtherRegionSecurityPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
		   'Description  :Save and verify Resource type 
		   'Precondition :None
		   'Arguments    :selenium,strResrcTypName
		   'Returns      :String
		   'Date         :6-April-2012
		   'Author       :QSG
		   '-----------------------------------------------------------------------
		   'Modified Date                                     Modified By
		   '6-April-2012                                        <Name>
		   ************************************************************************/
		    
	public String saveAndvrfyResType(Selenium selenium, String strResrcTypName)
			throws Exception {

		String strErrorMsg = "";// variable to store error message
		
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("css=input[value='Save']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/thead/tr/th/a[contains(text(),'Name')]"
									+ "/ancestor::table/tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"));
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
				assertEquals("Resource Type List", selenium.getText("css=h1"));
				log4j.info("'Resource Type List' page is displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/thead/tr/th/a[contains(text(),'Name')]"
									+ "/ancestor::table/tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"));

					log4j.info(strResrcTypName
							+ "is listed on the 'Resource Type List' page "
							+ "under appropriate column header.");

				} catch (AssertionError  ae) {
					log4j
							.info("Resource Type is  NOT listed on the 'Resource"
									+ " Type List' page under appropriate column header.");
					strErrorMsg = "Resource Type is  NOT listed on the 'Resource"
							+ " Type List' page under appropriate column header.";
				}

			} catch (AssertionError  a) {
				log4j.info("'Resource Type List' page is NOT displayed" );
				strErrorMsg = "'Resource Type List' page is NOT displayed" ;
			}

		} catch (Exception e) {
			log4j.info("saveAndvrfyResType function failed" + e);
			strErrorMsg = "saveAndvrfyResType function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	   'Description  :Activate/Deactivate resource type and verify in resource
	   				type list page
	   'Precondition :None
	   'Arguments    :selenium,strResrcTypName,blnActive
	   'Returns      :String
	   'Date         :6-June-2012
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                                     Modified By
	   '<Date>                                            <Name>
	   ************************************************************************/
	    

	public String activateAndDeactivateRT(Selenium selenium,
			String strResrcTypName, boolean blnActive) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

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
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Resource Type List", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("'Resource Type List' page is displayed");

				if (blnActive) {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]"
										+ "/thead/tr/th/a[contains(text(),'Name')]"
										+ "/ancestor::table/tbody/tr/td[2][text()='"
										+ strResrcTypName + "']"));

						log4j.info(strResrcTypName
								+ "is listed on the 'Resource Type List' page "
								+ "under appropriate column header.");

					} catch (AssertionError ae) {
						log4j
								.info("Resource Type is  NOT listed on the 'Resource"
										+ " Type List' page under appropriate column header.");
						strErrorMsg = "Resource Type is  NOT listed on the 'Resource"
								+ " Type List' page under appropriate column header.";
					}
				} else {
					try {
						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]"
										+ "/thead/tr/th/a[contains(text(),'Name')]"
										+ "/ancestor::table/tbody/tr/td[2][text()='"
										+ strResrcTypName + "']"));

						log4j
								.info(strResrcTypName
										+ "is not listed on the 'Resource Type List' page "
										+ "under appropriate column header.");

					} catch (AssertionError ae) {
						log4j
								.info("Resource Type is  still listed on the 'Resource"
										+ " Type List' page under appropriate column header.");
						strErrorMsg = "Resource Type is  still listed on the 'Resource"
								+ " Type List' page under appropriate column header.";
					}

				}

			} catch (AssertionError ae) {
				log4j.info("'Resource Type List' page is NOT displayed");
				strErrorMsg = "'Resource Type List' page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("saveAndvrfyResType function failed" + e);
			strErrorMsg = "saveAndvrfyResType function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are edited
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String varDeactivatedRT(Selenium selenium,
			String strResrcTypName,boolean blnInactive,boolean blnVar) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if(blnInactive){
				
				if(selenium.isChecked(propElementDetails
						.getProperty("ResourceTypeList.IncludeInactiveRT"))==false){
					selenium.click(propElementDetails
							.getProperty("ResourceTypeList.IncludeInactiveRT"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			}else{
				if(selenium.isChecked(propElementDetails
						.getProperty("ResourceTypeList.IncludeInactiveRT"))){
					selenium.click(propElementDetails
							.getProperty("ResourceTypeList.IncludeInactiveRT"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			}
			if(blnVar)
			{
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]"
								+ "/thead/tr/th/a[contains(text(),'Name')]"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ strResrcTypName + "']"));

				log4j.info(strResrcTypName
						+ "is listed on the 'Resource Type List' page "
						+ "under appropriate column header.");

			} catch (AssertionError  ae) {
				log4j
						.info("Resource Type is  NOT listed on the 'Resource"
								+ " Type List' page under appropriate column header.");
				strErrorMsg = "Resource Type is  NOT listed on the 'Resource"
						+ " Type List' page under appropriate column header.";
			}
			}
		} catch (Exception e) {
			log4j.info("varDeactivatedRT function failed" + e);
			strErrorMsg = "varDeactivatedRT function failed" + e;
		}
		return strErrorMsg;
	}
	
	

	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String navToCreateNewResrcTypePage(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails
					.getProperty("CreateNewRsrcTypeLink"));

			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create New Resource Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create New Resource Type page is displayed");
			} catch (AssertionError Ae) {

				strErrorMsg = "Create New Resource Type List page is NOT displayed"
						+ Ae;
				log4j.info("Create New Resource Type page is NOT displayed"
						+ Ae);
			}
		} catch (Exception e) {
			log4j.info("resrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "resrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	   'Description  :Save and verify Resource type 
	   'Precondition :None
	   'Arguments    :selenium,strResrcTypName
	   'Returns      :String
	   'Date         :6-April-2012
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                                     Modified By
	   '6-April-2012                                        <Name>
	   ************************************************************************/
	    
public String saveAndNavToResTypeList(Selenium selenium)
		throws Exception {

	String strErrorMsg = "";// variable to store error message
	
	try {
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		
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
		
		selenium.click(propElementDetails.getProperty("Save"));
		selenium.waitForPageToLoad(gstrTimeOut);

		intCnt=0;
		do{
			try {

				assertEquals("Resource Type List", selenium.getText("css=h1"));
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
			assertEquals("Resource Type List", selenium.getText("css=h1"));
			log4j.info("'Resource Type List' page is displayed");
		} catch (AssertionError  a) {
			log4j.info("'Resource Type List' page is NOT displayed" );
			strErrorMsg = "'Resource Type List' page is NOT displayed" ;
		}

	} catch (Exception e) {
		log4j.info("saveAndvrfyResType function failed" + e);
		strErrorMsg = "saveAndvrfyResType function failed" + e;
	}
	return strErrorMsg;
}


	public String activateAndDeactivateRTNew(Selenium selenium,
			String strResrcTypName, boolean blnActive) throws Exception {

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
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Resource Type List",  selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Resource Type List' page is displayed");

				if (blnActive) {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[@summary='Resource Types']"
										+ "/tbody/tr/td[text()='"
										+ strResrcTypName + "']"));

						log4j.info(strResrcTypName
								+ "is listed on the 'Resource Type List' page "
								+ "under appropriate column header.");

					} catch (AssertionError ae) {
						log4j
								.info("Resource Type is  NOT listed on the 'Resource"
										+ " Type List' page under appropriate column header.");
						strErrorMsg = "Resource Type is  NOT listed on the 'Resource"
								+ " Type List' page under appropriate column header.";
					}
				} else {
					try {
						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']/table[@summary='Resource Types']"
										+ "/tbody/tr/td[text()='"
										+ strResrcTypName + "']"));

						log4j
								.info(strResrcTypName
										+ "is not listed on the 'Resource Type List' page "
										+ "under appropriate column header.");

					} catch (AssertionError ae) {
						log4j
								.info("Resource Type is  still listed on the 'Resource"
										+ " Type List' page under appropriate column header.");
						strErrorMsg = "Resource Type is  still listed on the 'Resource"
								+ " Type List' page under appropriate column header.";
					}

				}

			} catch (AssertionError ae) {
				log4j.info("'Resource Type List' page is NOT displayed");
				strErrorMsg = "'Resource Type List' page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("saveAndvrfyResType function failed" + e);
			strErrorMsg = "saveAndvrfyResType function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	   'Description  :Save and verify Resource type 
	   'Precondition :None
	   'Arguments    :selenium,strResrcTypName
	   'Returns      :String
	   'Date         :16-Jan-2013
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                                     Modified By
	   '16-Jan-2013                                       <Name>
	   ************************************************************************/

	public String cancelAndVerifyResourceType(Selenium selenium,
			String strResrcTypName) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click(propElementDetails.getProperty("Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Resource Type List",  selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Resource Type List' page is displayed");

				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/thead/tr/th/a[contains(text(),'Name')]"
									+ "/ancestor::table/tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"));

					log4j.info(strResrcTypName
							+ "is NOT listed on the 'Resource Type List' page "
							+ "under appropriate column header.");

				} catch (AssertionError ae) {
					log4j
							.info(strResrcTypName
									+ " Resource Type is listed on the 'Resource"
									+ " Type List' page under appropriate column header.");
					strErrorMsg = strResrcTypName
							+ " Resource Type is isted on the 'Resource"
							+ " Type List' page under appropriate column header.";
				}

			} catch (AssertionError a) {
				log4j.info("'Resource Type List' page is NOT displayed");
				strErrorMsg = "'Resource Type List' page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("cancelAndVerifyResourceType function failed" + e);
			strErrorMsg = "cancelAndVerifyResourceType function failed" + e;
		}
		return strErrorMsg;
	}
	
 /***********************************************************************
   'Description  :Save and verify Resource type 
   'Precondition :None
   'Arguments    :selenium,strResrcTypName
   'Returns      :String
   'Date         :16-Jan-2013
   'Author       :QSG
   '-----------------------------------------------------------------------
   'Modified Date                                     Modified By
   '16-Jan-2013                                       <Name>
   ************************************************************************/

	public String savAndVerifyErrorMsgWithoutSelectingST(Selenium selenium,
			String strResrcTypName) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsgTitle")));
				assertEquals(
						"At least one status types must be selected when \"active\" is checked.",
						selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsg")));
				log4j.info("'The following error  occurred on this page:�At'"
						+ " least one status types must be selected when 'active' is checked.' ");
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/thead/tr/th/a[contains(text(),'Name')]"
									+ "/ancestor::table/tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"));

					log4j.info(strResrcTypName
							+ " Resource Type is NOT created. ");

				} catch (AssertionError ae) {
					log4j.info(strResrcTypName + " Resource Type is created. ");
					strErrorMsg = strResrcTypName
							+ " Resource Type is created. ";
				}

			} catch (AssertionError a) {
				log4j.info("'The following error NOT occurred on this page:�At'"
						+ " least one status types must be selected when 'active' is checked.' ");
				strErrorMsg = "'The following error NOT occurred on this page:�At'"
						+ " least one status types must be selected when 'active' is checked.' ";
			}

		} catch (Exception e) {
			log4j.info("cancelAndVerifyResourceType function failed" + e);
			strErrorMsg = "cancelAndVerifyResourceType function failed" + e;
		}
		return strErrorMsg;
	}
	
 /********************************************************************************************
   'Description  :select multi status types of same standard status types and var error message
   'Arguments    :selenium,strResrcTypName
   'Returns      :String
   'Date         :17-Jan-2013
   'Author       :QSG
   '--------------------------------------------------------------------------------------------
   'Modified Date                                                           Modified By
   '16-Jan-2013                                                             <Name>
   ********************************************************************************************/
	public String selectSTWithSameSDTVarErrorMsg(Selenium selenium,String stSTValue,
			String strResrcTypName) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsgTitle")));
				assertEquals(
						"More than one "+stSTValue+" status type with the same standard status type may not be "
								+ "selected for a single resource type.",
						selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsg")));
				log4j.info("The following error occurred on this page:"
						+ "  'More than one "+stSTValue+" status type with the same standard status type may not be "
						+ "selected for a single resource type is Displayed.");

				try {
					assertEquals("Create New Resource Type",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Create New Resource Type page is displayed");
					log4j.info("User is retained on the same page. ");
					try {
						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]"
										+ "/thead/tr/th/a[contains(text(),'Name')]"
										+ "/ancestor::table/tbody/tr/td[2][text()='"
										+ strResrcTypName + "']"));
						log4j.info(strResrcTypName
								+ " Resource Type is NOT created. ");

					} catch (AssertionError ae) {
						log4j.info(strResrcTypName
								+ " Resource Type is created. ");
						strErrorMsg = strResrcTypName
								+ " Resource Type is created. ";
					}
				} catch (AssertionError Ae) {
					strErrorMsg = "User is NOT retained on the same page." + Ae;
					log4j.info("User is NOT retained on the same page." + Ae);
				}

			} catch (AssertionError a) {
				log4j.info("'More than one "+stSTValue+" status type with the same standard status type may not be "
						+ "selected for a single resource type is not displayed.");
				strErrorMsg = "'More than one "+stSTValue+" status type with the same standard status type may not be "
						+ "selected for a single resource type is not displayed.";
			}

		} catch (Exception e) {
			log4j.info("selectMSTWithSameSDTVarErrorMsg function failed" + e);
			strErrorMsg = "selectMSTWithSameSDTVarErrorMsg function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:varDataFieldsInEditResourceTypePage
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:18-Jan-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-Jan-2013                               <Name>
	************************************************************************/
	
	public String varMandDataFieldsInEditResourceTypePage(Selenium selenium,
			String strResrcTypName, String strStstTypValue, boolean blnChecked)
			throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals(strResrcTypName,
						selenium.getValue(propElementDetails
								.getProperty("CreateNewRsrcType.ResrcName")));
				log4j.info("ResrcTypName " + strResrcTypName
						+ " Name is retained.");

				if (blnChecked) {
					try {
						assertTrue(selenium
								.isChecked("css=input[name='statusTypeID'][value='"
										+ strStstTypValue + "']"));
						log4j.info("Status type value is retained in edit resource type page");

					} catch (AssertionError Ae) {

						strErrorMsg = "Status type value is NOT retained in edit resource type page";
						log4j.info("Status type value is NOT retained in edit resource type page"
								+ Ae);
					}
				} else {

					try {
						assertFalse(selenium
								.isChecked("css=input[name='statusTypeID'][value='"
										+ strStstTypValue + "']"));

						log4j.info("Status type value is retained in edit resource type page");
					} catch (AssertionError Ae) {

						strErrorMsg = "Status type value is retained in edit resource type page";
						log4j.info("Status type value is retained in edit resource type page"
								+ Ae);
					}

				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Resource Type  page is NOT displayed" + Ae;
				log4j.info("Edit Resource Type  Type page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("varMandDataFieldsInEditResourceTypePage function failed"
					+ e);
			strErrorMsg = "varMandDataFieldsInEditResourceTypePage function failed"
					+ e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String fillResrcTypeMandatoryFlds(Selenium selenium,
			String strResrcTypName, String[] strStstTypValue) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.type(propElementDetails
					.getProperty("CreateNewRsrcType.ResrcName"),
					strResrcTypName);

			for (String s : strStstTypValue) {
				selenium.click("css=input[name='statusTypeID'][value='" + s
						+ "']");
			}

		} catch (Exception e) {
			log4j.info("fillResrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "fillResrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:varDataFieldsInEditResourceTypePage
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:18-Jan-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-Jan-2013                               <Name>
	************************************************************************/
	
	public String varStTypeSelOrNotForRT(Selenium selenium,
			String strResrcTypName, String strSTypValue,boolean blnChecked,String strStType)
			throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnChecked) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='statusTypeID'][value='"
									+ strSTypValue + "']"));
					log4j.info("Status type '"+strStType+"' is selected for '"+strResrcTypName+"'.");
				} catch (AssertionError Ae) {
					strErrorMsg ="Status type '"+strStType+"' is NOT selected for '"+strResrcTypName+"'.";
					log4j.info("Status type '"+strStType+"' is NOT selected for '"+strResrcTypName+"'.");
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='statusTypeID'][value='"
									+ strSTypValue + "']"));
					log4j.info("Status type '"+strStType+"' is NOT selected for '"+strResrcTypName+"'.");
				} catch (AssertionError Ae) {
					strErrorMsg ="Status type '"+strStType+"' is selected for '"+strResrcTypName+"'.";
					log4j.info("Status type '"+strStType+"' is selected for '"+strResrcTypName+"'.");
				}
			}

		} catch (Exception e) {
			log4j.info("varStTypeselOrNotForRT function failed"+ e);
			strErrorMsg = "varStTypeselOrNotForRT function failed"+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:fillResrcTypeAllFlds
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:21-Jan-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'21-Jan-2012                               <Name>
	************************************************************************/
	
	public String fillResrcTypeAllFlds(Selenium selenium,
			String strResrcTypName, String strDesc, String[] strStstTypValue,
			boolean blnSubResource, boolean blnDefaultST, String strMST,
			boolean blnType, boolean blnContctName, boolean blnContctTitl,
			boolean blnPne1, boolean blnPne2, boolean blnFax, boolean blnEmail)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.type(propElementDetails
					.getProperty("CreateNewRsrcType.ResrcName"),
					strResrcTypName);
			selenium.type(propElementDetails
					.getProperty("CreateNewRsrcType.Description"), strDesc);

			for (String s : strStstTypValue) {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ s + "']") == false)
					selenium.click("css=input[name='statusTypeID'][value='" + s
							+ "']");
			}
			
			
			if (blnSubResource) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.SubResource") )== false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.SubResource"));
			}

			if (blnDefaultST) {
				selenium.select(propElementDetails
						.getProperty("CreateNewRsrcType.DefaultStatustypeID"),
						"label=" + strMST);

			}

			if (blnType) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxtype")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxtype"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxtype")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxtype"));

			}

			if (blnContctName) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxContactName")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxContactName"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxContactName")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxContactName"));

			}

			if (blnContctTitl) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxContactTitle")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxContactTitle"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxContactTitle")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxContactTitle"));

			}

			if (blnPne1) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxPhone1")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxPhone1"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxPhone1")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxPhone1"));

			}

			if (blnPne2) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxPhone2")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxPhone2"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxPhone2")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxPhone2"));

			}

			if (blnFax) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxFax")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxFax"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxFax")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxFax"));
			}

			if (blnEmail) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxEmail")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxEmail"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxEmail")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxEmail"));

			}

		} catch (Exception e) {
			log4j.info("fillResrcTypeAllFlds function failed" + e);
			strErrorMsg = "fillResrcTypeAllFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:fill ResrcType Mandotory Fields
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:21-Jan-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'21-Jan-2012                               <Name>
	************************************************************************/

	public String savRTandVerifyDataInRTListPage(Selenium selenium,
			String strResrcTypName, String strDesc, boolean blnSubRs)
			throws Exception {

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
								+ "tbody/tr/td[2][text()='"
								+ strResrcTypName
								+ "']/parent::tr/td[1]/a[text()='Edit']"));
				log4j
						.info("Edit link is present under its appropriate coloumn");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[2]/a[text()='Name']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
								+ strResrcTypName + "']"));
				log4j.info("Resource type " + strResrcTypName
						+ "' is listed in Resource list page");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[3]/a[text()='Sub-Resource']"));

				if (blnSubRs) {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody"
									+ "/tr/td[2][text()='"
									+ strResrcTypName
									+ "']/following-sibling::td[1][text()='Yes']"));
					log4j
							.info("Sub resource provided value 'Yes'is  displayed appropriately "
									+ "in the corresponding column");
				} else {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody"
									+ "/tr/td[2][text()='"
									+ strResrcTypName
									+ "']/following-sibling::td[1][text()='No']"));
					log4j
							.info("Sub resource provided value 'No'is displayed appropriately "
									+ "in the corresponding column");
				}

				intNum++;
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[4]/a[text()='Description']"));

				if (strDesc.compareTo("") == 0) {
					String str = selenium
							.getText("//div[@id='mainContainer']/table[2]/"
									+ "tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"
									+ "/following-sibling::td[2]");

					assertTrue(str.matches(""));

					log4j
							.info("Description provided is displayed appropriately "
									+ "in the corresponding column");

				} else {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/"
									+ "tbody/tr/td[2][text()='"
									+ strResrcTypName
									+ "']"
									+ "/following-sibling::td[2][text()='"
									+ strDesc + "']"));

					log4j
							.info("Description provided is displayed appropriately "
									+ "in the corresponding column");
				}

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("Resource type " + strResrcTypName
							+ "' is NOT listed in Resource list page");
					strErrorMsg = "Resource type " + strResrcTypName
							+ "' is NOT listed in Resource list page";
					break;

				case 2:
					log4j
							.info("Edit link is NOT present under its appropriate coloumn");
					strErrorMsg = "Edit link is NOT present under its appropriate coloumn";
					break;

				case 3:
					log4j
							.info("Sub resource provided is NOT displayed appropriately "
									+ "in the corresponding column");
					strErrorMsg = "Sub resource provided is NOT displayed appropriately "
							+ "in the corresponding column";
					break;

				case 4:
					log4j
							.info("Description provided is NOT displayed appropriately "
									+ "in the corresponding column");
					strErrorMsg = "Description provided is NOT displayed appropriately "
							+ "in the corresponding column";
					break;
				}

			}

		} catch (Exception e) {
			log4j.info("savRTandVerifyDataInRTListPage function failed" + e);
			strErrorMsg = "savRTandVerifyDataInRTListPage function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:varAllDataFieldsRetainedInEditResourceTypePage
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:07/04/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'                              <Name>
	************************************************************************/
	
	public String varAllDataFieldsInCrtNewResType(
			Selenium selenium, String strResrcTypName, String strDesc,
			String strStstTypValue[][], boolean blnSubResource,
			boolean blnDefaultST, String strMST, boolean blnType,
			boolean blnContctName, boolean blnContctTitl, boolean blnPne1,
			boolean blnPne2, boolean blnFax, boolean blnEmail) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateNewRsrcTypeLink"));

			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Create New Resource Type", selenium
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
				assertEquals("Create New Resource Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Create New Resource Type page is displayed with following fields");
			
			} catch (AssertionError Ae) {
				log4j.info("Create New Resource Type page is Not displayed with following fields");
				strErrorMsg = "Create New Resource Type page is Not displayed with following fields";
			}
			try {
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("CreateNewRsrcType.ResrcName")));
				log4j.info("1. Name:");
			} catch (AssertionError Ae) {
				log4j.info("1. Name field is Not displayed");
				strErrorMsg = "1. Name field is Not displayed";
			}	
			try {
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("CreateNewRsrcType.Description")));
				log4j.info("2. Description:");
			} catch (AssertionError Ae) {
				log4j.info("2. Description field is Not displayed");
				strErrorMsg = "2. Description field is Not displayed";
			}
			try {
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("CreateNewRsrcType.SubResource")));
				log4j.info("3. Subresource:");
			} catch (AssertionError Ae) {
				log4j.info("3. Subresource field is Not displayed");
				strErrorMsg = "3. Subresource field is Not displayed";
			}	
			try {
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("CreateNewRsrcType.SubResource")));
				log4j.info("4. Subresource:");
			} catch (AssertionError Ae) {
				log4j.info("4. Subresource field is Not displayed");
				strErrorMsg = "4. Subresource field is Not displayed";
			}	

				

		} catch (Exception e) {
			log4j
					.info("varAllDataFieldsRetainedInEditResourceTypePage function failed"
							+ e);
			strErrorMsg = "varAllDataFieldsRetainedInEditResourceTypePage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:select and Deselect stauts types in Edit Resource type
	'Arguments		:selenium,strStatusType,blnCheck
	'Returns		:String
	'Date	 		:6-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                <Name>
	************************************************************************/
	
	public String selAndDeselSubResourceType(Selenium selenium,
			boolean blnSubResource) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnSubResource) {
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateNewRsrcType.SubResource")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked(propElementDetails.getProperty("CreateNewRsrcType.SubResource"))== false) {
					selenium.click(propElementDetails.getProperty("CreateNewRsrcType.SubResource"));

				}
			} else {
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateNewRsrcType.SubResource")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked(propElementDetails.getProperty("CreateNewRsrcType.SubResource"))) {
					selenium.click(propElementDetails.getProperty("CreateNewRsrcType.SubResource"));

				}
			}

		} catch (Exception e) {
			log4j.info("selAndDeselSubResourceType function failed" + e);
			strErrorMsg = "selAndDeselSubResourceType function failed" + e;
		}
		return strErrorMsg;
	}

	
  /***********************************************************************
   'Description  : verify Resource type 
   'Arguments    :selenium,strResrcTypName
   'Returns      :String
   'Date         :6-April-2012
   'Author       :QSG
   '-----------------------------------------------------------------------
   'Modified Date                                     Modified By
   '6-April-2012                                        <Name>
   ************************************************************************/

	public String vrfyResTypePresentOrNotInRTList(Selenium selenium,
			String strResrcTypName, boolean blnRT) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnRT) {
				int intCnt = 0;
				do {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]"
										+ "/thead/tr/th/a[contains(text(),'Name')]"
										+ "/ancestor::table/tbody/tr/td[2][text()='"
										+ strResrcTypName + "']"));
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
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/thead/tr/th/a[contains(text(),'Name')]"
									+ "/ancestor::table/tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"));

					log4j.info(strResrcTypName
							+ "is listed on the 'Resource Type List' pageunder appropriate column header.");

				} catch (AssertionError ae) {
					log4j.info("Resource Type is  NOT listed on the 'Resource"
							+ " Type List' page under appropriate column header.");
					strErrorMsg = "Resource Type is  NOT listed on the 'Resource"
							+ " Type List' page under appropriate column header.";
				}
			} else {

				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/thead/tr/th/a[contains(text(),'Name')]"
									+ "/ancestor::table/tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"));

					log4j.info(strResrcTypName
							+ "is NOT listed on the 'Resource Type List' page under appropriate column header.");

				} catch (AssertionError ae) {
					log4j.info("Resource Type is listed on the 'Resource"
							+ " Type List' page under appropriate column header.");
					strErrorMsg = "Resource Type is  listed on the 'Resource"
							+ " Type List' page under appropriate column header.";
				}
			}
		} catch (Exception e) {
			log4j.info("vrfyResTypePresentOrNotInRTList function failed" + e);
			strErrorMsg = "vrfyResTypePresentOrNotInRTList function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:verify Mandatory DataFields Retained In EditResourceType Page
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:22-Jan-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-Jan-2013                               <Name>
	************************************************************************/

	public String varMandDataFieldsRetainInEditRTPage(Selenium selenium,
			String strResrcTypName, String strStstTypValue[][],
			boolean blnSubResource) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals(strResrcTypName,
						selenium.getValue(propElementDetails
								.getProperty("CreateNewRsrcType.ResrcName")));
				log4j.info("ResrcTypName " + strResrcTypName
						+ " Name is retained.");

				for (int i = 0; i < strStstTypValue.length; i++) {
					if (strStstTypValue[i][2].equals("true")) {
						try {
							assertTrue(selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strStstTypValue[i][1] + "']"));
							log4j.info("Status type "
									+ strStstTypValue[i][0]
									+ " value is retained in the Edit resource type page");
						} catch (AssertionError Ae) {
							log4j.info("Status type "
									+ strStstTypValue[i][0]
									+ " value is NOT retained in the Edit resource type page");
							strErrorMsg = "Status type "
									+ strStstTypValue[i][0]
								+ " value is NOT retained in the Edit resource type page";
						}
					}

					if (strStstTypValue[i][2].equals("false")) {
						try {
							assertFalse(selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strStstTypValue[i][1] + "']"));
							log4j.info("Status type "
									+ strStstTypValue[i][0]
									+ " value is retained in the Edit resource type page");
						} catch (AssertionError Ae) {
							log4j.info("Status type "
									+ strStstTypValue[i][0]
									+ " value is NOT retained in the Edit resource type page");
							strErrorMsg = "Status type "
									+ strStstTypValue[i][0]
									+ " value is NOT retained in the Edit resource type page";
						}
					}
				}

				// SubResource
				if (blnSubResource) {
					try {
						assertTrue(selenium.isChecked(propElementDetails
								.getProperty("CreateNewRsrcType.SubResource")));
						log4j.info("Check Box corresponding to 'Sub-Resource' field is checked. ");
					} catch (AssertionError Ae) {
						log4j.info("Check Box corresponding to 'Sub-Resource' field is NOT checked. ");
						strErrorMsg = "Check Box corresponding to 'Sub-Resource' field is NOT checked. ";
					}

				} else {
					try {
						assertFalse(selenium.isChecked(propElementDetails
								.getProperty("CreateNewRsrcType.SubResource")));
						log4j.info("Check Box corresponding to 'Sub-Resource' field is unchecked. ");
					} catch (AssertionError Ae) {
						log4j.info("Check Box corresponding to 'Sub-Resource' field is NOT unchecked. ");
						strErrorMsg = "Check Box corresponding to 'Sub-Resource' field is NOT unchecked. ";
					}
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Resource Type  page is NOT displayed" + Ae;
				log4j.info("Edit Resource Type  Type page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("varMandDataFieldsRetainInEditRTPage function failed"
					+ e);
			strErrorMsg = "varMandDataFieldsRetainInEditRTPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:varAllDataFieldsRetainedInEditResourceTypePage
	'Precondition	:None
	'Arguments		:selenium,strResrcTypName,strStstTypLabl
	'Returns		:String
	'Date	 		:22-Jan-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'22-Jan-2013                               <Name>
	************************************************************************/
	
	public String varAllDataFieldsRetainedInEditResourceTypePage(
			Selenium selenium, String strResrcTypName, String strDesc,
			String strStstTypValue[][], boolean blnSubResource,
			boolean blnDefaultST, String strMST, boolean blnType,
			boolean blnContctName, boolean blnContctTitl, boolean blnPne1,
			boolean blnPne2, boolean blnFax, boolean blnEmail) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals(strResrcTypName, selenium
						.getValue(propElementDetails
								.getProperty("CreateNewRsrcType.ResrcName")));
				log4j.info("ResrcTypName " + strResrcTypName
						+ " Name is retained.");
				assertEquals(selenium
						.getText(propElementDetails
								.getProperty("CreateNewRsrcType.Description")), strDesc);

				for (int i = 0; i < strStstTypValue.length; i++) {
					if (strStstTypValue[i][2].equals("true")) {
						try {
							assertTrue(selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strStstTypValue[i][1] + "']"));
							log4j
									.info("Status type "
											+ strStstTypValue[i][0]
											+ " value is retained in the Edit resource type page");
						} catch (AssertionError Ae) {
							log4j
									.info("Status type "
											+ strStstTypValue[i][0]
											+ " value is NOT retained in the Edit resource type page");
							strErrorMsg = "Status type "
									+ strStstTypValue[i][0]
									+ " value is NOT retained in the Edit resource type page";
						}

					}

					if (strStstTypValue[i][2].equals("false")) {
						try {
							assertFalse(selenium
									.isChecked("css=input[name='statusTypeID'][value='"
											+ strStstTypValue[i][1] + "']"));
							log4j
									.info("Status type "
											+ strStstTypValue[i][0]
											+ " value is retained in the Edit resource type page");
						} catch (AssertionError Ae) {
							log4j
									.info("Status type "
											+ strStstTypValue[i][0]
											+ " value is NOT retained in the Edit resource type page");
							strErrorMsg = "Status type "
									+ strStstTypValue[i][0]
									+ " value is NOT retained in the Edit resource type page";
						}

					}

				}

				// SubResource
				if (blnSubResource) {
					try {
						assertTrue(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.SubResource")));
						log4j
								.info("SubResource value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("SubResource value is NOT retained in the Edit resource type page");
						strErrorMsg = "SubResource value is NOT retained in the Edit resource type page";

					}

				} else {
					try {
						assertFalse(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.SubResource")));
						log4j
								.info("SubResource value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("SubResource value is NOT retained in the Edit resource type page");
						strErrorMsg = "SubResource value is NOT retained in the Edit resource type page";

					}

				}
				// Default status type

				if (blnDefaultST) {
					try {
						assertTrue(selenium.getSelectedLabel(
								propElementDetails.getProperty("CreateNewRsrcType.DefaultStatustypeID"))
								.matches(strMST));

						log4j
								.info("Default status type "
										+ strMST
										+ " is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("Default status type "
										+ strMST
										+ " is NOT retained in the Edit resource type page");
						strErrorMsg = "Default status type "
								+ strMST
								+ " is NOT retained in the Edit resource type page";

					}

				} else {
					try {
						assertTrue(selenium.getSelectedLabel(
								propElementDetails.getProperty("CreateNewRsrcType.DefaultStatustypeID"))
								.matches(""));

						log4j
								.info("Default status type "
										+ strMST
										+ " is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("Default status type "
										+ strMST
										+ " is NOT retained in the Edit resource type page");
						strErrorMsg = "Default status type "
								+ strMST
								+ " is NOT retained in the Edit resource type page";

					}

				}

				// Resource type
				if (blnType) {
					try {
						assertTrue(selenium.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxtype")));
						log4j
								.info("Resource type name value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("Resource type name value is NOT retained in the Edit resource type page");
						strErrorMsg = "Resource type name value is NOT retained in the Edit resource type page";
					}
				} else {
					try {
						assertFalse(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxtype")));
						log4j
								.info("Resource type name value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("Resource type name value is NOT retained in the Edit resource type page");
						strErrorMsg = "Resource type name value is NOT retained in the Edit resource type page";
					}
				}

				if (blnContctName) {
					try {
						assertTrue(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxContactName")));
						log4j
								.info("ContctName value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("ContctName value is NOT retained in the Edit resource type page");
						strErrorMsg = "ContctName value is NOT retained in the Edit resource type page";
					}
				} else {
					try {
						assertFalse(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxContactName")));
						log4j
								.info("ContctName value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("ContctName value is NOT retained in the Edit resource type page");
						strErrorMsg = "ContctName value is NOT retained in the Edit resource type page";
					}
				}

				if (blnContctTitl) {
					try {
						assertTrue(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxContactTitle")));
						log4j
								.info("contactTitle value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("contactTitle value is NOT retained in the Edit resource type page");
						strErrorMsg = "contactTitle value is NOT retained in the Edit resource type page";
					}
				} else {
					try {
						assertFalse(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxContactTitle")));
						log4j
								.info("contactTitle value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("contactTitle value is NOT retained in the Edit resource type page");
						strErrorMsg = "contactTitle value is NOT retained in the Edit resource type page";
					}
				}

				if (blnPne1) {
					try {
						assertTrue(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxPhone1")));
						log4j
								.info("phone1 value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("phone1 value is NOT retained in the Edit resource type page");
						strErrorMsg = "phone1 value is NOT retained in the Edit resource type page";
					}
				} else {
					try {
						assertFalse(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxPhone1")));
						log4j
								.info("phone1 value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("phone1 value is NOT retained in the Edit resource type page");
						strErrorMsg = "phone1 value is NOT retained in the Edit resource type page";
					}
				}

				if (blnPne2) {
					try {
						assertTrue(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxPhone2")));
						log4j
								.info("phone2 value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("phone2 value is NOT retained in the Edit resource type page");
						strErrorMsg = "phone2 value is NOT retained in the Edit resource type page";
					}
				} else {
					try {
						assertFalse(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxPhone2")));
						log4j
								.info("phone2 value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("phone2 value is NOT retained in the Edit resource type page");
						strErrorMsg = "phone2 value is NOT retained in the Edit resource type page";
					}
				}

				if (blnFax) {
					try {
						assertTrue(selenium.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxFax")));
						log4j
								.info("fax value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("fax value is NOT retained in the Edit resource type page");
						strErrorMsg = "fax value is NOT retained in the Edit resource type page";
					}
				} else {
					try {
						assertFalse(selenium.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxFax")));
						log4j
								.info("fax value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("fax value is NOT retained in the Edit resource type page");
						strErrorMsg = "fax value is NOT retained in the Edit resource type page";
					}
				}

				if (blnEmail) {
					try {
						assertTrue(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxEmail")));
						log4j
								.info("fax value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("fax value is NOT retained in the Edit resource type page");
						strErrorMsg = "fax value is NOT retained in the Edit resource type page";
					}
				} else {
					try {
						assertFalse(selenium
								.isChecked(propElementDetails.getProperty("CreateNewRsrcType.checkBoxEmail")));
						log4j
								.info("email value is retained in the Edit resource type page");
					} catch (AssertionError Ae) {
						log4j
								.info("email value is NOT retained in the Edit resource type page");
						strErrorMsg = "email value is NOT retained in the Edit resource type page";
					}
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Resource Type  page is NOT displayed" + Ae;
				log4j.info("Edit Resource Type  Type page is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j
					.info("varAllDataFieldsRetainedInEditResourceTypePage function failed"
							+ e);
			strErrorMsg = "varAllDataFieldsRetainedInEditResourceTypePage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	//start//resrcTypeOnlyMandatoryFlds//
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/

	public String resrcTypeOnlyMandatoryFlds(Selenium selenium,
			String strResrcTypName, String strStstTypValue, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.type(propElementDetails
					.getProperty("CreateNewRsrcType.ResrcName"),
					strResrcTypName);

			selenium.click("css=input[name='statusTypeID'][value='"
					+ strStstTypValue + "']");
			
			//Click save
			if (blnSave) {
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]"
										+ "/thead/tr/th/a[contains(text(),'Name')]"
										+ "/ancestor::table/tbody/tr/td[2][text()='"
										+ strResrcTypName + "']"));
						break;

					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 70);
			}
		} catch (Exception e) {
			log4j.info("resrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "resrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	//end//resrcTypeOnlyMandatoryFlds//
	
	//start//savandVerifyErrorMsg//
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/

	public String savandVerifyErrorMsg(Selenium selenium,
			String strResrcTypName, String strStatTypeName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Click save
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("The following error occurred on this page:",
							selenium.getText(propElementDetails
									.getProperty("UpdateStatus.ErrMsgTitle")));
					break;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 70);

			assertEquals("The following error occurred on this page:",
					selenium.getText(propElementDetails
							.getProperty("UpdateStatus.ErrMsgTitle")));
			assertEquals(
					"Sub-Resource Types may not use Event only Status Types ("
							+ strStatTypeName + ")",
					selenium.getText(propElementDetails
							.getProperty("UpdateStatus.ErrMsg")));
			
			log4j.info("'The following error occurred on this page:�Sub-Resource Types may not"
					+ " use Event only Status Types (< "+strStatTypeName+" >)'is displayed.");
			
			intCnt = 0;
			do {
				try {
					assertEquals("Create New Resource Type",
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
			} while (intCnt < 70);

			try {
				assertEquals("Create New Resource Type",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("User is retained 'Create New Resource Type' page. ");

				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/thead/tr/th/a[contains(text(),'Name')]"
									+ "/ancestor::table/tbody/tr/td[2][text()='"
									+ strResrcTypName + "']"));

					log4j.info("'Sub-Resource Type' " + strResrcTypName
							+ "is NOT  created ");

				} catch (AssertionError ae) {
					log4j.info("'Sub-Resource Type' " + strResrcTypName
							+ "is created ");
					strErrorMsg = "'Sub-Resource Type' " + strResrcTypName
							+ "is created ";
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "User is NOT retained 'Create New Resource Type' page. "
						+ Ae;
				log4j.info("User is NOT retained 'Create New Resource Type' page. "
						+ Ae);
			}
		} catch (Exception e) {
			log4j.info("resrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "resrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	//end//savandVerifyErrorMsg//
	
	//start//varAllDataFieldsInCrtNewResType//
	/*******************************************************************************************
	' Description: 
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 04/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String varAllDataFieldsInCrtNewResType(Selenium selenium) throws Exception
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
			try {
				selenium.selectWindow("");
				selenium.selectFrame(propElementDetails.getProperty("Prop38"));
				log4j.info("''Create New Resource Type' screen is displayed with following fields: ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Create New Resource Type' screen is not displayed with following fields: ");
				lStrReason = lStrReason + "; " + "'Create New Resource Type' screen is not displayed with following fields: ";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3761")));
				log4j.info("'Name:(Text Field) ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Name:(Text Field)  is not displayed");
				lStrReason = lStrReason + "; " + "Name:(Text Field)  is not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent("//textarea[@name='description']"));
				log4j.info("'Description: (Text Field) ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Description: (Text Field)  is not displayed");
				lStrReason = lStrReason + "; " + "Description: (Text Field)  is not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3763")));
				log4j.info("'Active: (Check Box)Check if resource type is active. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Active: (Check Box)Check if resource type is active not displayed.");
				lStrReason = lStrReason + "; " + "Active: (Check Box)Check if resource type is active not displayed.";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3764")));
				log4j.info("'Sub-Resource: (Check Box)Check if only used for sub-resources ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Sub-Resource: (Check Box)Check if only used for sub-resources . is not displayed");
				lStrReason = lStrReason + "; " + "Sub-Resource: (Check Box)Check if only used for sub-resources . is not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3765")));
				log4j.info("'Status Types: (Check Box)(List of the status types in region check box present corresponding to each status type) ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Status Types: (Check Box)(List of the status types in region check box present  corresponding to each status type )not displayed ");
				lStrReason = lStrReason + "; " + "Status Types: (Check Box)(List of the status types in region check box present  corresponding to each status type )not displayed ";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3766")));
				log4j.info("'Default Status Type: (Drop Down) ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Default Status Type: (Drop Down)  is not displayed");
				lStrReason = lStrReason + "; " + "Default Status Type: (Drop Down)  is not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3767")));
				log4j.info("'Select the displayable info fields for this resource type(Check boxes) : Resource Type Name ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Select the displayable info fields for this resource type:  Resource Type Name  is not displayed");
				lStrReason = lStrReason + "; " + "Select the displayable info fields for this resource type:  Resource Type Name  is not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3768")));
				log4j.info("'Contact Name ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Contact Name  not displayed");
				lStrReason = lStrReason + "; " + "Contact Name  not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3769")));
				log4j.info("'Contact Title ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Contact Title  not displayed");
				lStrReason = lStrReason + "; " + "Contact Title  not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3770")));
				log4j.info("'Phone 1 ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Phone 1  is not displayed");
				lStrReason = lStrReason + "; " + "Phone 1  is not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3771")));
				log4j.info("'Phone 2 ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Phone 2  not displayed");
				lStrReason = lStrReason + "; " + "Phone 2  not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3772")));
				log4j.info("'Fax");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Fax not displayed");
				lStrReason = lStrReason + "; " + "Fax not displayed";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop3773")));
				log4j.info("'Email");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'email not displayed");
				lStrReason = lStrReason + "; " + "email not displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = "ResourceTypes.varAllDataFieldsInCrtNewResType failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//varAllDataFieldsInCrtNewResType//
	
	//start//savandVerifyErrorMsgForRT//
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/

	public String savandVerifyErrorMsgForRT(Selenium selenium,
			String strResrcTypName, String strStrUser) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Click save
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("The following error occurred on this page:",
							selenium.getText(propElementDetails
									.getProperty("UpdateStatus.ErrMsgTitle")));
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
				assertEquals("Edit Resource Type",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("'" + strStrUser
						+ "' is retained on 'Edit resource Type' screen.");
			} catch (AssertionError Ae) {

				strErrorMsg = "'" + strStrUser
						+ "' is NOT retained on 'Edit resource Type' screen."
						+ Ae;
				log4j.info("'" + strStrUser
						+ "' is NOT retained on 'Edit resource Type' screen."
						+ Ae);
			}

			try {

				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsgTitle")));
				assertEquals(
						"You can only change whether or not this is a Sub-Resource if there are no"
								+ " Resources of this type.",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsg")));

				log4j.info("'The following error occurred on this page:"
						+ "�You can only change whether or not this is a Sub-Resource if there"
						+ " are no Resources of this type'is displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "'The following error occurred on this page:"
						+ "�You can only change whether or not this is a Sub-Resource if there"
						+ " are no Resources of this type' is NOT displayed."
						+ Ae;
				log4j.info("'The following error occurred on this page:"
						+ "�You can only change whether or not this is a Sub-Resource if there"
						+ " are no Resources of this type' is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info("resrcTypeMandatoryFlds function failed" + e);
			strErrorMsg = "resrcTypeMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
//end//savandVerifyErrorMsgForRT//
	
	//start//vrfyActiveChkBoxSelOrNotForRT//
	/*******************************************************************************************
	' Description: 
	' Precondition: N/A 
	' Arguments: selenium, blnActive
	' Returns: String 
	' Date: 22/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String vrfyActiveChkBoxSelOrNotForRT(Selenium selenium,
			boolean blnActive) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnActive) {
				try {
					assertTrue(selenium.isChecked(propElementDetails
							.getProperty("CreateNewRsrcType.Active")));
					log4j.info("'Active' check box is selected. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "'Active' check box is NOT selected." + Ae;
					log4j.info("'Active' check box is NOT selected.");
				}
			} else {

				try {
					assertFalse(selenium.isChecked(propElementDetails
							.getProperty("CreateNewRsrcType.Active")));
					log4j.info("'Active' check box is NOT selected. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "'Active' check box is selected." + Ae;
					log4j.info("'Active' check box is selected.");
				}
			}

		} catch (Exception e) {
			log4j.info("vrfyActiveChkBoxSelOrNotForRT function failed" + e);
			strErrorMsg = "vrfyActiveChkBoxSelOrNotForRT function failed" + e;
		}
		return strErrorMsg;
	}
	//end//vrfyActiveChkBoxSelOrNotForRT//
	/***********************************************************************
	   'Description  :cancel and navigate to Resource Type List 
	   'Precondition :None
	   'Arguments    :selenium,strResrcTypName
	   'Returns      :String
	   'Date         :16-Jan-2013
	   'Author       :QSG
	   '-----------------------------------------------------------------------
	   'Modified Date                                     Modified By
	   '16-Jan-2013                                       <Name>
	   ************************************************************************/

	public String cancelAndNavToResourceTypeList(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click(propElementDetails.getProperty("Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			while(selenium.isElementPresent(propElementDetails
								.getProperty("Header.Text"))==false && intCnt<30){
				intCnt++;
				Thread.sleep(1000);
			}
			try {
				assertEquals("Resource Type List",  selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Resource Type List' page is displayed");

				
			} catch (AssertionError a) {
				log4j.info("'Resource Type List' page is NOT displayed");
				strErrorMsg = "'Resource Type List' page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("cancelAndNavToResourceTypeList function failed" + e);
			strErrorMsg = "cancelAndNavToResourceTypeList function failed" + e;
		}
		return strErrorMsg;
	}
	/*******************************************************
	'Description	:check Contact Fields Cheked Or Not
	'Precondition	:None
	'Arguments		:selenium,blnContctTitl
	'Returns		:String
	'Date	 		:21-Jan-2012
	'Author			:QSG
	'--------------------------------------------------------
	'Modified Date                            Modified By
	'21-Jan-2012                               <Name>
	*********************************************************/

	public String chkContactFieldsChkOrNot(Selenium selenium,
			boolean blnContctTitl) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnContctTitl) {
				try {
					assertTrue(selenium
							.isChecked(propElementDetails
									.getProperty("CreateNewRsrcType.checkBoxContactTitle")));
					log4j.info("'Contact Title' field is checked by default.");

				} catch (AssertionError a) {
					log4j.info("'Contact Title' field is NOT checked by default.");
					strErrorMsg = "'Contact Title' field is NOT checked by default.";
				}

			} else {
				try {
					assertFalse(selenium
							.isChecked(propElementDetails
									.getProperty("CreateNewRsrcType.checkBoxContactTitle")));
					log4j.info("'Contact Title' field is NOT checked.");

				} catch (AssertionError a) {
					log4j.info("'Contact Title' field is checked.");
					strErrorMsg = "'Contact Title' field is checked.";
				}
			}
		} catch (Exception e) {
			log4j.info("chkContactFieldsChkOrNot function failed" + e);
			strErrorMsg = "chkContactFieldsChkOrNot function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:fillResrcTypeAllFlds
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:21-Jan-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'21-Jan-2012                               <Name>
	************************************************************************/

	public String selAnddeselContactField(Selenium selenium,
			boolean blnContctTitl) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnContctTitl) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxContactTitle")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxContactTitle"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewRsrcType.checkBoxContactTitle")))
					selenium.click(propElementDetails
							.getProperty("CreateNewRsrcType.checkBoxContactTitle"));
			}
		} catch (Exception e) {
			log4j.info("fillResrcTypeAllFlds function failed" + e);
			strErrorMsg = "fillResrcTypeAllFlds function failed" + e;
		}
		return strErrorMsg;
	}
	//start//savandVerifyErrorMsgForMultiResource//
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/

	public String savandVerifyErrorMsgForMultiResource(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Click save
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("The following error occurred on this page:",
							selenium.getText(propElementDetails
									.getProperty("UpdateStatus.ErrMsgTitle")));
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
				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsgTitle")));
				assertEquals(
						"More than one multi status type with the same standard status type may not be selected for a single resource type.",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsg")));

				log4j
						.info("'The following error occurred on this page:More than one multi status type with the same standard status type may not be selected for a single resource type is displayed.");

			} catch (AssertionError Ae) {

				strErrorMsg = "The following error occurred on this page:More than one multi status type with the same standard status type may not be selected for a single resource type is displayed."
						+ Ae;
				log4j
						.info("The following error occurred on this page:More than one multi status type with the same standard status type may not be selected for a single resource type is displayed."
								+ Ae);
			}
		} catch (Exception e) {
			log4j.info("savandVerifyErrorMsgForMultiResource function failed"
					+ e);
			strErrorMsg = "savandVerifyErrorMsgForMultiResource function failed"
					+ e;
		}
		return strErrorMsg;
	}
	// end//savandVerifyErrorMsgForMultiResource//
	
	//start//savandVerifyErrorMsgForTextResource//
	/***********************************************************************
	'Description	:Verify Resource type Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/

	public String savandVerifyErrorMsgForTextResource(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Click save
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("The following error occurred on this page:",
							selenium.getText(propElementDetails
									.getProperty("UpdateStatus.ErrMsgTitle")));
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
				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsgTitle")));
				assertEquals(
						"More than one text status type with the same standard status type may not be selected for a single resource type.",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsg")));

				log4j
						.info("'The following error occurred on this page:More than one text status type with the same standard status type may not be selected for a single resource type is displayed.");

			} catch (AssertionError Ae) {

				strErrorMsg = "The following error occurred on this page:More than one text status type with the same standard status type may not be selected for a single resource type is displayed."
						+ Ae;
				log4j
						.info("The following error occurred on this page:More than one text status type with the same standard status type may not be selected for a single resource type is displayed."
								+ Ae);
			}
		} catch (Exception e) {
			log4j.info("savandVerifyErrorMsgForMultiResource function failed"
					+ e);
			strErrorMsg = "savandVerifyErrorMsgForMultiResource function failed"
					+ e;
		}
		return strErrorMsg;
	}
	// end//savandVerifyErrorMsgForTextResource//
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

	public String verActiveAndInActivateResrcType(Selenium selenium,
			String strResrcTypeName, boolean blnActive) throws Exception {

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
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
									+ strResrcTypeName
									+ "']/following-sibling::td[2][text()='Active']"));
					log4j.info("'Active' is displayed under 'Active' column for Resource Type '"
							+ strResrcTypeName + "'.");
				} catch (AssertionError ae) {
					log4j.info("'Active' is NOT displayed under 'Active' column for Resource Type '"
							+ strResrcTypeName + "'.");
					strErrorMsg = "'Active' is NOT displayed under 'Active' column for Resource Type '"
							+ strResrcTypeName + "'.";
				}
			} else {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
									+ strResrcTypeName
									+ "']/following-sibling::td[2][text()='Disabled']"));
					log4j.info("'Disabled' is displayed under 'Active' column for Resource Type '"
							+ strResrcTypeName + "'.");
				} catch (AssertionError ae) {
					log4j.info("'Disabled' is NOT displayed under 'Active' column for Resource Type '"
							+ strResrcTypeName + "'.");
					strErrorMsg = "'Disabled' is NOT displayed under 'Active' column for Resource Type '"
							+ strResrcTypeName + "'.";
				}
			}

		} catch (Exception e) {
			log4j.info("veractiveAndInActivateResrcType function failed" + e);
			strErrorMsg = "veractiveAndInActivateResrcType function failed" + e;
		}
		return strErrorMsg;
	}
}
