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
' Description :This class includes common functions of status types
' Precondition:
' Date		  :6-April-2012
' Author	  :QSG
'-----------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class StatusTypes {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.StatusTypes");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();

	ReadData rdExcel;
	
	String gstrTimeOut="";
	
	/***********************************************************************
	'Description	:Verify status type list page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String navStatusTypList(Selenium selenium) throws Exception {

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
			
			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));

			selenium.click(propElementDetails
					.getProperty("SetUP.StatusTypesLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			intCnt = 0;
			do {
				try {

					assertEquals("Status Type List", selenium
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
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Status Type List page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Status Type List page is NOT displayed" + Ae;
				log4j.info("Status Type List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navStatusTypList function failed" + e);
			strErrorMsg = "navStatusTypList function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	 'Description :Verify select status type  page is displayed and 
	 '             mandatory fields are filled
	 'Arguments   :selenium,strStatusTypeValue,statTypeName,strStatTypDefn,blnSav
	 'Returns     :String
	 'Date        :12-April-2012
	 'Author      :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '12-April-2012                               <Name>
	 ************************************************************************/
	 
	public String createSTWithinMultiTypeSTNew(Selenium selenium,
			String strMultiStatTypeName, String strStatusName,
			String strDefinition, String strStatusTypeValue,
			String strStatTypeColor, boolean blnRegion, String strRegionValue,
			boolean blnComments, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Status Type List page is displayed");

				selenium
						.click("//div[@id='mainContainer']//table/tbody/tr/td[2]"
								+ "[text()='"
								+ strMultiStatTypeName
								+ "']/parent::tr/td[1]"
								+ "/a[text()='statuses']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals(
							"Status List for " + strMultiStatTypeName + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status Type List for " + strMultiStatTypeName
							+ "page is displayed");

					selenium.click(propElementDetails
							.getProperty("StatusType.MultiST_CreateNewStatus.Link"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Create New Status", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Create New Status page is displayed");

						selenium
								.type(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusName"), strStatusName);
						selenium.select(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusColourCode"),
								"label=" + strStatTypeColor);

						selenium.type(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusDesc"),
								strDefinition);

						if (blnRegion) {
							if (selenium
									.isChecked("css=input[name='status_reason_id'][value='"
											+ strRegionValue + "']")) {

							} else {
								selenium
										.click("css=input[name='status_reason_id'][value='"
												+ strRegionValue + "']");
							}
						} else {
							if (selenium
									.isChecked("css=input[name='status_reason_id'][value='"
											+ strRegionValue + "']") == false) {

							} else {
								selenium
										.click("css=input[name='status_reason_id'][value='"
												+ strRegionValue + "']");
							}
						}
						// comments mandatory button
						if (blnComments) {

							if (selenium
									.isChecked(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusCommentReq")) == false
									&& selenium
											.isChecked(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusCommentOptional"))) {
								selenium
										.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusCommentReq"));
							}
						}

						if (blnSav) {

							selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusSave"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {
								selenium
										.click(propElementDetails.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);
							}

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[2]"
												+ "/tbody/tr/td[2][text()='"
												+ strMultiStatTypeName
												+ "']"
												+ "/parent::tr/td[8]/font[text()='"
												+ strStatusName + "']"));

								log4j
										.info("Status is  Listed  in the corresponding"
												+ " multistatus type page");
							} catch (AssertionError Ae) {
								strErrorMsg = "Status is  NOT Listed  in the corresponding"
										+ " multistatus type page" + Ae;
								log4j
										.info("Status is  COT Listed  in the corresponding"
												+ " multistatus type page" + Ae);
							}

						}

					} catch (AssertionError Ae) {

						strErrorMsg = "Create New Status page is NOT displayed"
								+ Ae;
						log4j.info("Create New Status page is NOT displayed"
								+ Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Status Type List for "
							+ strMultiStatTypeName + " page is NOT displayed"
							+ Ae;
					log4j.info("Status Type List for " + strMultiStatTypeName
							+ " page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Status Type List page is NOT displayed" + Ae;
				log4j.info("Status Type List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("createSTWithinMultiTypeST function failed" + e);
			strErrorMsg = "createSTWithinMultiTypeST function failed" + e;
		}
		return strErrorMsg;
	}

	 /***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields with Time
	'Arguments		:selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	'				 blnSav
	'Returns		:String
	'Date	 		:11-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/
	
	public String selectSTAndChkForTraceName(Selenium selenium,
			String strStatusTypeValue, boolean blnTraceName) throws Exception {

		String strErrorMsg = "";// variable to store error message
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("CreateStatusType"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Create " + strStatusTypeValue
								+ " Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is  displayed");
					} catch (AssertionError Ae) {
						strErrorMsg = "Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae;
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}

			if (blnTraceName) {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateStatusType.TraceUser")));
					log4j.info("'Trace User' check box is displayed. ");
				} catch (AssertionError Ae) {
					log4j.info("'Trace User' check box is NOT displayed. ");
					strErrorMsg = "'Trace User' check box is NOT displayed.";
				}
			} else {
				try {
					assertFalse(selenium.isVisible(propElementDetails
							.getProperty("CreateStatusType.TraceUser")));
					log4j.info("'Trace User' check box is NOT displayed.");
				} catch (AssertionError Ae) {
					log4j.info("'Trace User' check box is displayed.");
					strErrorMsg = "'Trace User' check box is displayed.";
				}
			}

		} catch (Exception e) {
			log4j.info("selectStatusTypesAndFilMandFlds function failed" + e);
			strErrorMsg = "selectStatusTypesAndFilMandFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description: Canel user and navigating to statusTypeList page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 28/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String cancelAndNavToSTListPage(Selenium selenium) throws Exception {
		
		String lStrReason = "";
		propElementDetails = objelementProp.ElementId_FilePath();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// cancel
			selenium.click("//input[@value='Cancel']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Status Type List page is displayed");

			} catch (AssertionError Ae) {

				lStrReason = "Status Type List page is NOT displayed" + Ae;
				log4j.info("Status Type List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: selecting reset value checkbox for ST
	' Precondition: N/A 
	' Arguments: selenium,blnReset,blnSave
	' Returns: String 
	' Date: 05/07/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String SelectAndDeselectResetValueForST(Selenium selenium,
			boolean blnReset, boolean blnSave) throws Exception {
		
		String lStrReason = "";
		propElementDetails = objelementProp.ElementId_FilePath();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnReset) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Prop887")) == false)
					;
				selenium.click(propElementDetails.getProperty("Prop887"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Prop887")))
					;
				selenium.click(propElementDetails.getProperty("Prop887"));
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info("resetValueForST function failed");
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description : Providing Update Excempt key for ST key for region
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,blnSave,blnUpdate 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String selectAndDeselectExcemptFromUpdate(Selenium selenium,
			boolean blnUpdate, boolean blnSave) throws Exception {

		String lStrReason = "";
		propElementDetails = objelementProp.ElementId_FilePath();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnUpdate) {
				if (selenium
						.isChecked(propElementDetails
								.getProperty("StatusType.CreateStatType.ExcemptFromMustupdate")) == false)
					;
				selenium
						.click(propElementDetails
								.getProperty("StatusType.CreateStatType.ExcemptFromMustupdate"));
			} else {
				if (selenium
						.isChecked(propElementDetails
								.getProperty("StatusType.CreateStatType.ExcemptFromMustupdate")))
					;
				selenium
						.click(propElementDetails
								.getProperty("StatusType.CreateStatType.ExcemptFromMustupdate"));
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("selectAndDeselectIPFILTERSave function failed");
			lStrReason = "selectAndDeselectIPFILTERSave function failed";
		}

		return lStrReason;
	}

	/***********************************************************************
	'Description	:selecting  Role while create ST 
	'Precondition	:None
	'Arguments		:selenium,strRolesName
	'Returns		:String
	'Date	 		:9-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String SelectRoleAndRegForST(Selenium selenium, String strRoleName,
			boolean blnRegion, String strRegionValue,
			String strViewRightValue[], boolean blnViewRight,
			String updateRightValue[], boolean blnUpdateRight) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnRegion) {
				if (selenium
						.isChecked("css=input[name='statusReasonID'][value='"
								+ strRegionValue + "']")) {

				} else {
					selenium.click("css=input[name='statusReasonID'][value='"
							+ strRegionValue + "']");
				}
			} else {
				if (selenium
						.isChecked("css=input[name='statusReasonID'][value='"
								+ strRegionValue + "']") == false) {

				} else {
					selenium.click("css=input[name='statusReasonID'][value='"
							+ strRegionValue + "']");
				}
			}

			// Select the Status Types this Role may view
			if (blnViewRight) {
				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayView")) == true) {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));

				}
				for (String strVal : strViewRightValue) {
					selenium.click("css=input[name='roleView'][value='"
							+ strVal + "']");
				}
			} else {
				if (selenium.isChecked(propElementDetails.getProperty("StatusType.ALLRoles")) == false)
					selenium.click(propElementDetails.getProperty("StatusType.ALLRoles"));
			}

			// Select the Status Types this Role may update
			if (blnUpdateRight) {
				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate")) == true) {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));

				}

				for (String strVal : strViewRightValue) {
					selenium.click("css=input[name='roleUpdate'][value='"
							+ strVal + "']");
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate")) == false)
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
			}
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("SelectRoleForST function failed" + e);
			strErrorMsg = "SelectRoleForST function failed" + e;
		}
		return strErrorMsg;
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
	
	public String selectDesectIncludeInactiveST(Selenium selenium,
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
						.getProperty("StatusTypeList.IncludeInactiveST")) == false) {
					selenium.click(propElementDetails
							.getProperty("StatusTypeList.IncludeInactiveST"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("StatusTypeList.IncludeInactiveST"))) {
					selenium.click(propElementDetails
							.getProperty("StatusTypeList.IncludeInactiveST"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}
			}
		} catch (Exception e) {
			log4j.info("selectDesectIncludeInactiveST function failed" + e);
			strErrorMsg = "selectDesectIncludeInactiveST function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
    'Description  :Activate/Deactivate status type
    'Precondition :None
    'Arguments    :selenium,strSTName,blnActive,blnSave
    'Returns      :String
    'Date         :6-July-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                                     Modified By
    '<Date>                                            <Name>
    ************************************************************************/
     

	public String activateAndDeactivateST(Selenium selenium, String strSTName,
			boolean blnActive, boolean blnSave) throws Exception {

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
	'Description	:Verify mandatory data of status type is edited
	'Precondition	:None
	'Arguments		:selenium,statTypeName,strStatTypDefn, blnSav	
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/

	public String editStatusTypePage(Selenium selenium, String statTypeName,
			String strSTtype) throws Exception {

		String strErrorMsg = "";// variable to store error message

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table[@class="
							+ "'displayTable striped border sortable']/"
							+ "tbody/tr/td[2][text()='" + statTypeName
							+ "']/parent::tr/td[1]/a[1]"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			

			selenium.click("//div[@id='mainContainer']/table[@class="
					+ "'displayTable striped border sortable']/"
					+ "tbody/tr/td[2][text()='" + statTypeName
					+ "']/parent::tr/td[1]/a[1]");
			selenium.waitForPageToLoad(gstrTimeOut);

			
			intCnt=0;
			do{
				try {

					assertEquals("Edit " + strSTtype + " Status Type", selenium
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
				assertEquals("Edit " + strSTtype + " Status Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit" + strSTtype
						+ " Status Type page is displayed");

			} catch (AssertionError Ae) {

				log4j.info("Edit" + strSTtype
						+ " Status Type page is NOT displayed");
				strErrorMsg = "Edit" + strSTtype
						+ " Status Type page is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info("editStatusTypePage function failed" + e);
			strErrorMsg = "editStatusTypePage function failedd" + e;
		}
		return strErrorMsg;
	}

	 /***********************************************************************
	 'Description :Verify status type and all other filds are listed in 
	 '    Statues list pagee is displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :23-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '6-April-2012                               <Name>
	 ************************************************************************/
	 
	public String savAndVerifySTNew(Selenium selenium, String strSTName)
			throws Exception {

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
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateStatusType.Save")));
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
					.getProperty("CreateStatusType.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			intCnt=0;
			do{
				try {

					assertEquals("Status Type List", selenium
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

				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Status Type List is displayed");

				
				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("css=table[summary='Status Types']"
										+ ">tbody>tr>td:nth-child(2):contains('"+strSTName+"')"));
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
							.isElementPresent("//div[@id='mainContainer']" +
									"/table[@summary='Status Types']/tbody/tr/td[2][text()='"
									+ strSTName + "']"));*/
					
					assertTrue(selenium
							.isElementPresent("css=table[summary='Status Types']"
									+ ">tbody>tr>td:nth-child(2):contains('"+strSTName+"')"));
					
					log4j.info(strSTName + "  listed in the status type list");

				} catch (AssertionError Ae) {
					log4j.info(strSTName
							+ " NOT listed in the status type list" + Ae);
					strErrorMsg = strSTName
							+ " NOT listed in the status type list" + Ae;
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "Status Type List is NOT displayed" + Ae;
				log4j.info("Status Type List is NOT  displayed");

			}
		} catch (Exception e) {
			log4j.info("savAndVerifySTNew function failed" + e);
			strErrorMsg = "savAndVerifySTNew function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	 'Description :Save the status type page and verify status type is displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :17-Jul-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	 
	public String savAndVerifyMultiST(Selenium selenium, String strSTName)
			throws Exception {

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
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateStatusType.Save")));
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
					.getProperty("CreateStatusType.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateStatusType.ReturnToSTList")));
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
					.getProperty("CreateStatusType.ReturnToSTList"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("css=table[summary='Status Types']"
									+ ">tbody>tr>td:nth-child(2):contains('"+strSTName+"')"));
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

				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Status Type List is   displayed");

				try {
					/*assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
									+ strSTName + "']"));
					*/
					assertTrue(selenium
							.isElementPresent("css=table[summary='Status Types']"
									+ ">tbody>tr>td:nth-child(2):contains('"+strSTName+"')"));
					
					log4j.info(strSTName + "  listed in the status type list");

				} catch (AssertionError Ae) {
					log4j.info(strSTName
							+ " NOT listed in the status type list" + Ae);
					strErrorMsg = strSTName
							+ " NOT listed in the status type list" + Ae;
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "Status Type List is NOT displayed" + Ae;
				log4j.info("Status Type List is NOT  displayed");

			}
		} catch (Exception e) {
			log4j.info("savAndVerifyMultiST function failed" + e);
			strErrorMsg = "savAndVerifyMultiST function failed" + e;
		}
		return strErrorMsg;
	}

	/**************************************************************
	  'Description :Verify Sturation Score status type is updated
	  'Precondition :None
	  'Arguments  :None
	  'Returns  :None
	  'Date    :2-July-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	public String[] updateSatuScoreStatusTypeWitTime(Selenium selenium,
			String strResource, String strStatType, String strSTValue,
			String strUpdateValue1[], String strUpdateValue2[],
			String strSatuValue1, String strSatuValue2, String strTimeFormat)
			throws Exception {

		String strReason[] = new String[2];
		strReason[0] = "";
		strReason[1] = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		Date_Time_settings dts = new Date_Time_settings();

		try {

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
						.getText(propElementDetails.getProperty("UpdateStatus.OverdueTitle")));
				
				log4j.info("Update Status screen is displayed");
				selenium.click("css=#st_" + strSTValue + "");
				boolean blnChange = false;
				if (selenium.getValue("css=#lobbyCapacity" + strSTValue)
						.equals(strUpdateValue1[8])) {
					// Enter the values for fields
					selenium.type("css=#edBedsOccupied" + strSTValue,
							strUpdateValue2[0]);
					selenium.type("css=#lobbyPatients" + strSTValue,
							strUpdateValue2[1]);
					selenium.type("css=#ambulancePatients" + strSTValue,
							strUpdateValue2[2]);
					selenium.type("css=#admitsGeneral" + strSTValue,
							strUpdateValue2[3]);
					selenium.type("css=#admitsIcu" + strSTValue,
							strUpdateValue2[4]);
					selenium.type("css=#oneOnOnePatients" + strSTValue,
							strUpdateValue2[5]);
					selenium.type("css=#shortStaffRn" + strSTValue,
							strUpdateValue2[6]);
					selenium.type("css=#edBedsAssigned" + strSTValue,
							strUpdateValue2[7]);
					selenium.type("css=#lobbyCapacity" + strSTValue,
							strUpdateValue2[8]);
					blnChange = true;
				} else {
					// Enter the values for fields
					selenium.type("css=#edBedsOccupied" + strSTValue,
							strUpdateValue1[0]);
					selenium.type("css=#lobbyPatients" + strSTValue,
							strUpdateValue1[1]);
					selenium.type("css=#ambulancePatients" + strSTValue,
							strUpdateValue1[2]);
					selenium.type("css=#admitsGeneral" + strSTValue,
							strUpdateValue1[3]);
					selenium.type("css=#admitsIcu" + strSTValue,
							strUpdateValue1[4]);
					selenium.type("css=#oneOnOnePatients" + strSTValue,
							strUpdateValue1[5]);
					selenium.type("css=#shortStaffRn" + strSTValue,
							strUpdateValue1[6]);
					selenium.type("css=#edBedsAssigned" + strSTValue,
							strUpdateValue1[7]);
					selenium.type("css=#lobbyCapacity" + strSTValue,
							strUpdateValue1[8]);
				}

				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad("30000");
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

				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.FindResource")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				// select the Find Resource option
				selenium.select(propElementDetails
						.getProperty("ViewMap.FindResource"), "label="
						+ strResource);

				// Wait for resource pop up to present
				intCnt = 0;
				while (selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup")) == false
						&& intCnt < 20) {
					Thread.sleep(1000);
					intCnt++;
				}

				if (blnChange) {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("//span[text()='"
									+ strSatuValue2 + "']"));
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
								+ strSatuValue2 + "']"));
						log4j
								.info("Status updated is displayed in Resource pop up");

					} catch (AssertionError Ae) {
						log4j
								.info("Status updated is NOT displayed in Resource pop up"
										+ Ae);
						strReason[0] = "Status updated is NOT displayed in Resource pop up"
								+ Ae;

					}

				} else {
					
					intCnt=0;
					do{
						try {

							assertTrue(selenium.isElementPresent("//span[text()='"
									+ strSatuValue1 + "']"));
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
								+ strSatuValue1 + "']"));
						log4j
								.info("Status updated is displayed in Resource pop up");

					} catch (AssertionError Ae) {
						log4j
								.info("Status updated is NOT displayed in Resource pop up"
										+ Ae);
						strReason[0] = "Status updated is NOT displayed in Resource pop up"
								+ Ae;

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
	
	/**********************************************************************
	  'Description :Verify Multi status type is updated with time
	  'Precondition :None
	  'Arguments  :None
	  'Returns  :None
	  'Date    :7-July-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	
	public String[] updateMultiStatusType_ChangeValWithTime(Selenium selenium,
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
						.getText(propElementDetails.getProperty("UpdateStatus.OverdueTitle")));
				log4j.info("Update Status screen is displayed");

				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if (selenium.isChecked("css=#statusValue_" + strStatusValue1
						+ "")) {
					strStatVal = strStatusValue2;
					strChStatus = strStatus2;
				}
				selenium.click("css=#statusValue_" + strStatVal + "");

				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
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

				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("ViewMap.FindResource")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
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
								+ strChStatus + "']"));
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
			strReason[0] = "Failed in function updateMultiStatusType_ChangeValWithTime " + e;
		}
		return strReason;
	}

	/**********************************************************************
	  'Description :Verify Multi status type is updated
	  'Precondition :None
	  'Arguments  :None
	  'Returns  :None
	  'Date    :10-May-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	public String updateMultiStatusType_ChangeVal(Selenium selenium,
			String strResource, String strMultiST, String strRoleStatTypeValue,
			String strStatus1, String strStatusValue1, String strStatus2,
			String strStatusValue2) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strStatVal = strStatusValue1;
			String strChStatus = strStatus1;
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
						.getText(propElementDetails.getProperty("UpdateStatus.OverdueTitle")));
				log4j.info("Update Status screen is displayed");

				selenium.click("css=#st_" + strRoleStatTypeValue + "");

				if (selenium.isChecked("css=#statusValue_" + strStatusValue1
						+ "")) {
					strStatVal = strStatusValue2;
					strChStatus = strStatus2;
				}
				selenium.click("css=#statusValue_" + strStatVal + "");

				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad("30000");
				

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

				// Wait for resource pop up to present
				intCnt = 0;
				while (selenium.isElementPresent(propElementDetails
						.getProperty("ViewMap.ResPopup"))&&selenium.isVisible(propElementDetails
						.getProperty("ViewMap.ResPopup")) == false
						&& intCnt < 20) {
					Thread.sleep(1000);
					intCnt++;
				}
				
				intCnt=0;
				do{
					try {
						assertTrue(selenium.isElementPresent("//span[text()='"
								+ strChStatus + "']"));
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
							+ strChStatus + "']"));
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
			strReason = "Failed in function updateMultiStatusType_ChangeVal " + e;
		}
		return strReason;
	}
	
	/*******************************************************************
	  'Description :Verify Sturation Score status type is updated
	  'Precondition :None
	  'Arguments  :None
	  'Returns  :None
	  'Date    :25-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	public String updateSatuScoreStatusType(Selenium selenium,
			String strResource, String strStatType, String strSTValue,
			String strUpdateValue1[], String strUpdateValue2[],
			String strSatuValue1, String strSatuValue2) throws Exception {

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

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				assertEquals(strResource, selenium
						.getText(propElementDetails.getProperty("UpdateStatus.OverdueTitle")));
				log4j.info("Update Status screen is displayed");
				selenium.click("css=#st_" + strSTValue + "");
				boolean blnChange = false;
				if (selenium.getValue("css=#lobbyCapacity" + strSTValue)
						.equals(strUpdateValue1[8])) {
					// Enter the values for fields
					selenium.type("css=#edBedsOccupied" + strSTValue,
							strUpdateValue2[0]);
					selenium.type("css=#lobbyPatients" + strSTValue,
							strUpdateValue2[1]);
					selenium.type("css=#ambulancePatients" + strSTValue,
							strUpdateValue2[2]);
					selenium.type("css=#admitsGeneral" + strSTValue,
							strUpdateValue2[3]);
					selenium.type("css=#admitsIcu" + strSTValue,
							strUpdateValue2[4]);
					selenium.type("css=#oneOnOnePatients" + strSTValue,
							strUpdateValue2[5]);
					selenium.type("css=#shortStaffRn" + strSTValue,
							strUpdateValue2[6]);
					selenium.type("css=#edBedsAssigned" + strSTValue,
							strUpdateValue2[7]);
					selenium.type("css=#lobbyCapacity" + strSTValue,
							strUpdateValue2[8]);
					blnChange = true;
				} else {
					// Enter the values for fields
					selenium.type("css=#edBedsOccupied" + strSTValue,
							strUpdateValue1[0]);
					selenium.type("css=#lobbyPatients" + strSTValue,
							strUpdateValue1[1]);
					selenium.type("css=#ambulancePatients" + strSTValue,
							strUpdateValue1[2]);
					selenium.type("css=#admitsGeneral" + strSTValue,
							strUpdateValue1[3]);
					selenium.type("css=#admitsIcu" + strSTValue,
							strUpdateValue1[4]);
					selenium.type("css=#oneOnOnePatients" + strSTValue,
							strUpdateValue1[5]);
					selenium.type("css=#shortStaffRn" + strSTValue,
							strUpdateValue1[6]);
					selenium.type("css=#edBedsAssigned" + strSTValue,
							strUpdateValue1[7]);
					selenium.type("css=#lobbyCapacity" + strSTValue,
							strUpdateValue1[8]);
				}

				selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad("30000");

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

				// Wait for resource pop up to present
				intCnt = 0;
				while (selenium.isElementPresent(propElementDetails
						.getProperty("ViewMap.ResPopup"))
						&& selenium.isVisible(propElementDetails
								.getProperty("ViewMap.ResPopup")) == false
						&& intCnt < 20) {
					Thread.sleep(1000);
					intCnt++;
				}

				if (blnChange) {
					
					intCnt = 0;
					do {
						try {

							assertTrue(selenium.isElementPresent("//span[text()='"
									+ strSatuValue2 + "']"));
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
						assertTrue(selenium.isElementPresent("//span[text()='"
								+ strSatuValue2 + "']"));
						log4j
								.info("Status updated is displayed in Resource pop up");

					} catch (AssertionError Ae) {
						log4j
								.info("Status updated is NOT displayed in Resource pop up"
										+ Ae);
						strReason = "Status updated is NOT displayed in Resource pop up"
								+ Ae;

					}

				} else {
					
					intCnt = 0;
					do {
						try {

							assertTrue(selenium.isElementPresent("//span[text()='"
									+ strSatuValue1 + "']"));
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
						assertTrue(selenium.isElementPresent("//span[text()='"
								+ strSatuValue1 + "']"));
						log4j
								.info("Status updated is displayed in Resource pop up");

					} catch (AssertionError Ae) {
						log4j
								.info("Status updated is NOT displayed in Resource pop up"
										+ Ae);
						strReason = "Status updated is NOT displayed in Resource pop up"
								+ Ae;

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
	
	/***********************************************************************
	'Description	:Select status reason while creating status types
	'Precondition	:None
	'Arguments		:selenium,strReasonVal,blnSelect
	'Returns		:String
	'Date	 		:02-July-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String selectAndDeselectStatusReason(Selenium selenium,String strReasonVal,boolean blnSelect) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
				
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			if(blnSelect){
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent
								("css=input[name='statusReasonID'][value='"+strReasonVal+"']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if(selenium.isChecked("css=input[name='statusReasonID'][value='"+strReasonVal+"']")==false)
					selenium.click("css=input[name='statusReasonID'][value='"+strReasonVal+"']");
				
			}else{
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent
								("css=input[name='statusReasonID'][value='"+strReasonVal+"']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if(selenium.isChecked("css=input[name='statusReasonID'][value='"+strReasonVal+"']"))
					selenium.click("css=input[name='statusReasonID'][value='"+strReasonVal+"']");
			}
		
			
		} catch (Exception e) {
			log4j.info("selectAndDeselectStatusReason function failed" + e);
			strErrorMsg = "selectAndDeselectStatusReason function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	 'Description :Verify select status type  page is displayed and 
	 '     mandatory fields are filled
	 'Precondition :None
	 'Arguments  :selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	 '     blnSav
	 'Returns  :String
	 'Date    :12-April-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '12-April-2012                               <Name>
	 ************************************************************************/
	 
	public String createSTWithinMultiTypeST(Selenium selenium,
			String strMultiStatTypeName, String strStatusName,
			String strStatusTypeValue, String strStatTypeColor, boolean blnSav)
			throws Exception {

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
				try {

					assertEquals("Status Type List", selenium
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
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Status Type List page is displayed");

				selenium
						.click("//div[@id='mainContainer']//table/tbody/tr/td[2]"
								+ "[text()='"
								+ strMultiStatTypeName
								+ "']/parent::tr/td[1]"
								+ "/a[text()='statuses']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt = 0;
				do {
					try {

						assertEquals(
								"Status List for " + strMultiStatTypeName + "",
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
					assertEquals(
							"Status List for " + strMultiStatTypeName + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status Type List for " + strMultiStatTypeName
							+ "page is displayed");

					selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatus.Link"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt = 0;
					do {
						try {

							assertEquals("Create New Status", selenium
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
						assertEquals("Create New Status", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Create New Status page is displayed");

						selenium
								.type(propElementDetails
										.getProperty("StatusType.MultiST_CreateNewStatusName"), strStatusName);
						selenium.select(propElementDetails
								.getProperty("StatusType.MultiST_CreateNewStatusColourCode"),
								"label=" + strStatTypeColor);

						if (blnSav) {

							selenium
									.click(propElementDetails
											.getProperty("StatusType.MultiST_CreateNewStatusSave"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {

								intCnt = 0;
								do {
									try {

										assertTrue(selenium
												.isElementPresent(propElementDetails
														.getProperty("CreateStatusType.ReturnToSTList")));

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
										.click(propElementDetails
												.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);

								intCnt = 0;
								do {
									try {

										assertTrue(selenium
												.isElementPresent("//div[@id='mainContainer']/table[2]"
														+ "/tbody/tr/td[2][text()='"
														+ strMultiStatTypeName
														+ "']"
														+ "/parent::tr/td[8]/font[text()='"
														+ strStatusName + "']"));

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
													+ "/tbody/tr/td[2][text()='"
													+ strMultiStatTypeName
													+ "']"
													+ "/parent::tr/td[8]/font[text()='"
													+ strStatusName + "']"));

									log4j
											.info("Status is  Listed  in the corresponding"
													+ " multistatus type page");
								} catch (AssertionError Ae) {
									strErrorMsg = "Status is  NOT Listed  in the corresponding"
											+ " multistatus type page" + Ae;
									log4j
											.info("Status is  NOT Listed  in the corresponding"
													+ " multistatus type page"
													+ Ae);
								}
							}

						}

					} catch (AssertionError Ae) {

						strErrorMsg = "Create New Status page is NOT displayed"
								+ Ae;
						log4j.info("Create New Status page is NOT displayed"
								+ Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Status Type List for "
							+ strMultiStatTypeName + " page is NOT displayed"
							+ Ae;
					log4j.info("Status Type List for " + strMultiStatTypeName
							+ " page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Status Type List page is NOT displayed" + Ae;
				log4j.info("Status Type List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("createSTWithinMultiTypeST function failed" + e);
			strErrorMsg = "createSTWithinMultiTypeST function failed" + e;
		}
		return strErrorMsg;
	}

	public String activeStatusType(Selenium selenium, String strStatType,
			boolean blnActive) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			// select include inactive status types
			selenium.click(propElementDetails
					.getProperty("StatusType.IncludeInactiveST"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.click("//div[@id='mainContainer']/table[@class="
					+ "'displayTable striped border sortable']/"
					+ "tbody/tr/td[2][text()='" + strStatType
					+ "']/parent::tr/td[1]/a[1]");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateStatusType.StatTypName")));
				log4j.info("Edit  Status Type page is displayed");

				// select active check box
				if (blnActive) {
					if (selenium.isChecked(propElementDetails
							.getProperty("StatusType.EditStatusType.Active")) == false)
						selenium
								.click(propElementDetails
										.getProperty("StatusType.EditStatusType.Active"));
				} else {
					if (selenium.isChecked(propElementDetails
							.getProperty("StatusType.EditStatusType.Active")))
						selenium
								.click(propElementDetails
										.getProperty("StatusType.EditStatusType.Active"));
				}
				// save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				// de-select include inactive status types
				selenium.click(propElementDetails
						.getProperty("StatusType.IncludeInactiveST"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Type List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ strStatType + "']"));

					log4j.info("Status type " + strStatType
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

				} catch (AssertionError Ae) {

					log4j.info("Status type " + strStatType
							+ " is edited and is NOT listed in the "
							+ "'Status Type List' screen. ");

					strReason = "Status type " + strStatType
							+ " is edited and is NOT listed in the "
							+ "'Status Type List' screen. ";
				}

				if (blnActive == false) {
					if (strReason.equals("Status type " + strStatType
							+ " is edited and is NOT listed in the "
							+ "'Status Type List' screen. ")) {
						strReason = "";
					} else {
						strReason = "Status type " + strStatType
								+ " is edited and is still listed in the "
								+ "'Status Type List' screen. ";
					}

				}
			} catch (AssertionError Ae) {

				log4j.info("Edit  Status Type page is NOT displayed");
				strReason = "Edit  Status Type page is NOT displayed" + Ae;
			}
		} catch (Exception e) {
			log4j.info("activeStatusType function failed" + e);
			strReason = "activeStatusType function failed" + e;
		}
		return strReason;
	}
	
	public String inactiveStatusType(Selenium selenium, String strStatType)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.click("//div[@id='mainContainer']/table[@class="
					+ "'displayTable striped border sortable']/"
					+ "tbody/tr/td[2][text()='" + strStatType
					+ "']/parent::tr/td[1]/a[1]");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateStatusType.StatTypName")));
				log4j.info("Edit  Status Type page is displayed");

				if (selenium.isChecked(propElementDetails
						.getProperty("StatusType.EditStatusType.Active")))
					selenium.click(propElementDetails
							.getProperty("StatusType.EditStatusType.Active"));

				// save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				// select include inactive status types
				selenium.click(propElementDetails
						.getProperty("StatusType.IncludeInactiveST"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Type List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ strStatType + "']"));

					log4j
							.info("Status type "
									+ strStatType
									+ " is deactivated and is displayed in the status type list screen only "
									+ "when 'include inactive status types' check box is selected");

				} catch (AssertionError Ae) {

					log4j
							.info("Status type "
									+ strStatType
									+ " is deactivated and is NOT displayed in the status type list screen only "
									+ "when 'include inactive status types' check box is selected");
					strReason = "Status type "
							+ strStatType
							+ " is deactivated and is NOT displayed in the status type list screen only "
							+ "when 'include inactive status types' check box is selected";
				}

			} catch (AssertionError Ae) {

				log4j.info("Edit  Status Type page is NOT displayed");
				strReason = "Edit  Status Type page is NOT displayed" + Ae;
			}
		} catch (Exception e) {
			log4j.info("inactiveStatusType function failed" + e);
			strReason = "inactiveStatusType function failed" + e;
		}
		return strReason;
	}
	
	public String navToStatusListForStatusType(Selenium selenium,
			String strStatType) {
		
		
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on Status link for the particular status type
			selenium.click("//div[@id='mainContainer']/table[@class="
					+ "'displayTable striped border sortable']/"
					+ "tbody/tr/td[2][text()='" + strStatType
					+ "']/parent::tr/td[1]/a[text()='statuses']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Status List for " + strStatType, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Status List for " + strStatType
						+ " screen is displayed");
			} catch (AssertionError Ae) {

				log4j.info("Status List for " + strStatType
						+ " screen is NOT displayed");
				strReason = "Status List for " + strStatType
						+ " screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info("activeStatusType function failed" + e);
			strReason = "activeStatusType function failed" + e;
		}
		return strReason;
	}

	public String activeStatusUnderStatusType(Selenium selenium,
			String strStatType, String strStatus, boolean blnActive)
			throws Exception {
		
		
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			String strStatusDisp = "Active";

			selenium.click("//div[@id='mainContainer']/table[@class="
					+ "'displayTable striped border sortable']/"
					+ "tbody/tr/td[2][text()='" + strStatus
					+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Status", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Edit Status screen is displayed");

				// select active check box
				if (blnActive) {
					if (selenium.isChecked(propElementDetails
							.getProperty("StatusType.EditStatus.Active")) == false)
						selenium.click(propElementDetails
								.getProperty("StatusType.EditStatus.Active"));
				} else {
					if (selenium.isChecked(propElementDetails
							.getProperty("StatusType.EditStatus.Active")))
						selenium.click(propElementDetails
								.getProperty("StatusType.EditStatus.Active"));
				}
				// save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				if (blnActive)
					strStatusDisp = "Active";
				else
					strStatusDisp = "Disabled";

				try {
					assertEquals("Status List for " + strStatType, selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ strStatus
									+ "']/parent::tr/td[3][text()='"
									+ strStatusDisp + "']"));
					log4j
							.info(strStatus
									+ " is displayed in the 'Status List' screen along with status "
									+ strStatusDisp);
				} catch (AssertionError Ae) {

					log4j
							.info(strStatus
									+ " is NOT displayed in the 'Status List' screen along with status "
									+ strStatusDisp);
					strReason = strStatus
							+ " is NOT displayed in the 'Status List' screen along with status "
							+ strStatusDisp;
				}

			} catch (AssertionError ae) {
				log4j.info("Edit Status screen is NOT displayed");
				strReason = "Edit Status screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info("activeStatusType function failed" + e);
			strReason = "activeStatusType function failed" + e;
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields are filled
	'Precondition	:None
	'Arguments		:selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	'				 blnSav
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/
	
	public String selectStatusTypesAndFilMandFlds(Selenium selenium,
			String strStatusTypeValue, String statTypeName,
			String strStatTypDefn, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			int intCnt = 0;
			do {
				try {
					assertTrue( selenium
							.isElementPresent(propElementDetails.getProperty("CreateStatusType")));

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			
			selenium.click(propElementDetails.getProperty("CreateStatusType"));
			selenium.waitForPageToLoad(gstrTimeOut);
		

			intCnt = 0;
			do {
				try {
					assertEquals("Select Status Type", selenium
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
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					do {
						try {

							assertEquals("Create " + strStatusTypeValue
									+ " Status Type", selenium
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
						assertEquals("Create " + strStatusTypeValue
								+ " Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is  displayed");

						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypName"),
								statTypeName);
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypDefn"),
								strStatTypDefn);

						if (blnSav) {
							intCnt = 0;
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
							} while (intCnt < 70);
							
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {

								intCnt = 0;
								do {
									try {

										assertTrue(selenium
												.isElementPresent(propElementDetails
														.getProperty("CreateStatusType.ReturnToSTList")));
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
										.click(propElementDetails
												.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);

							}

							intCnt = 0;
							do {
								try {

								/*	assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/"
													+ "table[@summary='Status Types']/tbody/tr/td[2][text()='"
													+ statTypeName + "']"));
*/
									assertTrue(selenium
											.isElementPresent("css=table[summary='Status Types']" +
													">tbody>tr>td:contains('"+statTypeName+"')"));
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
								/*assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/"
												+ "table[@summary='Status Types']/tbody/tr/td[2][text()='"
												+ statTypeName + "']"));*/
								
								assertTrue(selenium
										.isElementPresent("css=table[summary='Status Types']" +
												">tbody>tr>td:contains('"+statTypeName+"')"));

								log4j.info("Status type " + statTypeName
										+ " is created and is listed in the "
										+ "'Status Type List' screen. ");

							} catch (AssertionError Ae) {

								log4j
										.info("Status type "
												+ statTypeName
												+ " is created and is NOT listed in the "
												+ "'Status Type List' screen. ");

								strErrorMsg = "Status type "
										+ statTypeName
										+ " is created and is NOT listed in the "
										+ "'Status Type List' screen. " + Ae;
							}

						}

						/*
						 * String strElementID = "//div[@id='mainContainer']
						 * /table/tbody/tr/td[2][text()='" + statTypeName +
						 * "']";
						 * 
						 * strErrorMsg = objGeneral.CheckForElements(selenium,
						 * strElementID);
						 * 
						 * try { assertEquals("", strErrorMsg);
						 * log4j.info("Status type " + statTypeName +
						 * " is created and is listed in the " +
						 * "'Status Type List' screen. "); } catch
						 * (AssertionError Ae) { log4j .info("Status type " +
						 * statTypeName +
						 * " is created and is NOT listed in the " +
						 * "'Status Type List' screen. ");
						 * 
						 * strErrorMsg = "Status type " + statTypeName +
						 * " is created and is NOT listed in the " +
						 * "'Status Type List' screen. " + Ae;; }
						 */

					} catch (AssertionError Ae) {
						strErrorMsg = "Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae;
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("selectStatusTypesAndFilMandFlds function failed" + e);
			strErrorMsg = "selectStatusTypesAndFilMandFlds function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Select status type visibility in Status Type screen.
	'Precondition	:None
	'Arguments		:selenium,blnShared,blnRegion,blnPrivate
	'Returns		:String
	'Date	 		:29-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String selectStatusTypeVisibility(Selenium selenium,
			boolean blnShared, boolean blnRegion, boolean blnPrivate)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnShared) {
				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent(propElementDetails
										.getProperty("StatusType.CreateStatType.StatusTypeVisib.Shared")));
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
						.click(propElementDetails
								.getProperty("StatusType.CreateStatType.StatusTypeVisib.Shared"));
			}

			else if (blnRegion) {
				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent(propElementDetails
										.getProperty("StatusType.CreateStatType.StatusTypeVisib.RegOnly")));
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
						.click(propElementDetails
								.getProperty("StatusType.CreateStatType.StatusTypeVisib.RegOnly"));
			}

			else if (blnPrivate) {
				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent(propElementDetails
										.getProperty("StatusType.CreateStatType.StatusTypeVisib.Private")));
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
						.click(propElementDetails
								.getProperty("StatusType.CreateStatType.StatusTypeVisib.Private"));
			}

		} catch (Exception e) {
			log4j.info("selectStatusTypeVisibility function failed" + e);
			strErrorMsg = "selectStatusTypeVisibility function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Select or deselect Event Only for status type
	'Precondition	:None
	'Arguments		:selenium,blnSelect
	'Returns		:String
	'Date	 		:01-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String selectDeSelEventOnly(Selenium selenium,
			boolean blnSelect)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnSelect){
				if(selenium.isChecked(propElementDetails
								.getProperty("StatusType.CreateStatType.EventOnly"))==false)
					selenium
							.click(propElementDetails
									.getProperty("StatusType.CreateStatType.EventOnly"));
			}else{
				if(selenium.isChecked(propElementDetails
						.getProperty("StatusType.CreateStatType.EventOnly")))
					selenium
							.click(propElementDetails
									.getProperty("StatusType.CreateStatType.EventOnly"));
			}

		} catch (Exception e) {
			log4j.info("selectDeSelEventOnly function failed" + e);
			strErrorMsg = "selectDeSelEventOnly function failed" + e;
		}
		return strErrorMsg;
	}
	public String selectTypeAndCreateStatTypeWithAllFields(Selenium selenium,
			String strStatusTypeValue, String strStatTypeName,
			String strStatTypeDefn) {
		
		
		String strReason = "";
		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// click Create New Status Type button
			selenium.click(propElementDetails.getProperty("CreateStatusType"));

			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Select Status Type", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Select Status Type page is displayed");

				selenium.select(propElementDetails
						.getProperty("CreateStatusType.SelStat.StatValue"),
						"label=" + strStatusTypeValue + "");
				//click on next
				selenium.click(propElementDetails
						.getProperty("CreateStatusType.SelStat.Next"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
							
				try {
					assertEquals("Create "+strStatusTypeValue+" Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j
							.info("Create "+strStatusTypeValue+" Status Type page is  displayed");
					//Enter name and description
					selenium.type(propElementDetails
							.getProperty("CreateStatusType.StatTypName"),
							strStatTypeName);
					selenium.type(propElementDetails
							.getProperty("CreateStatusType.StatTypDefn"),
							strStatTypeDefn);
					
			
					selenium.select(propElementDetails
							.getProperty("StatusType.CreateStatType.StandardStatType"), "label=Activity: Admissions in last 24 hours");
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.Active"));
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.KeepHistory"));
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.EventOnly"));
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.StatusTypeVisib.Shared"));
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.StatusTypeVisib.RegOnly"));
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.StatusTypeVisib.Private"));
					
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.StatusComments.Optional"));
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.StatusComments.Mandat"));
					
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.ResetValue"));
					
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.ResetComment"));
					
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.ExemptFrmUpdate"));
					
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.ShiftTime1"));
					selenium.select("css=select[name=expireHour1]", "label=01");
					selenium.select("css=select[name=expireMin1]", "label=02");
					
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.ShiftTime1"));
					selenium.select("css=select[name=expireHour2]", "label=02");
					selenium.select("css=select[name=expireMin2]", "label=01");
					
					selenium.click("css=input[name=shift][value=3]");
					selenium.select("css=select[name=expireHour3]", "label=01");
					selenium.select("css=select[name=expireMin3]", "label=02");
					
					selenium.click("css=select[name=timeoutCode][value=NEVER]");
					selenium.click("css=select[name=timeoutCode][value=EXPIR]");
					
					selenium.type("css=input[name=timeoutDays]", "1");
					selenium.select("css=select[name=timeoutHours]", "label=01");
					selenium.select("css=select[name=timeoutMinutes]", "label=02");
					
					selenium.type("css=inpu[name=gracePeriodMin]", "1");
					
					selenium.select("css=select[name=timer]", "label=Count down to expiration, then stop counting");
				} catch (AssertionError Ae) {
					strReason = "Create "+strStatusTypeValue+" Status Type page is NOT displayed"
							+ Ae;
					log4j
							.info("Create "+strStatusTypeValue+" Status Type page is NOT displayed"
									+ Ae);
				}

			} catch (AssertionError Ae) {

				strReason = "Select Status Type page is NOT displayed"
						+ Ae;
				log4j.info("Select Status Type page is NOT displayed" + Ae);
			}
		
				
			
		
		} catch (Exception e) {
			log4j.info("selectTypeAndCreateStatTypeWithAllFields function failed" + e);
			strReason = "selectTypeAndCreateStatTypeWithAllFields function failed" + e;
		}
		return strReason;
	}
	
	public String UpdateStatus(Selenium selenium, String strStatType,
			String strStatus, String strStatReason) throws Exception {
		
		
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='viewContainer']/table/thead/tr/th[3]/a[text()='"
								+ strStatType + "']"));
				log4j.info("Status Type is displayed in the User view table");

				// click on status
				selenium
						.click("//div[@id='viewContainer']/table/tbody/tr/td[3][text()='"
								+ strStatus + "']");
				selenium.waitForPageToLoad(gstrTimeOut);

				// select status
				selenium
						.click("//div[@class='multiStatus']/div/span/label[text()='"
								+ strStatus + "']/preceding-sibling::input");

				// select status reason
				selenium
						.click("//div[@class='multiStatus']/div/span/label[text()='"
								+ strStatus
								+ "']/parent::span/following-sibling::div/div/label[contains(text(),'"
								+ strStatReason
								+ "')]/preceding-sibling::input");

				// save
				selenium.click(propElementDetails
						.getProperty("View.UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='viewContainer']/table/thead/tr/th[4][text()='Comment']"));
					assertTrue(selenium
							.isElementPresent("//div[@id='viewContainer']/table/tbody/tr/td[4][text()='"
									+ strStatReason + "']"));
					log4j.info("Status is updated where '" + strStatReason
							+ "' is displayed under the 'Comments' section");
				} catch (AssertionError ae) {
					log4j
							.info("Status is updated where '"
									+ strStatReason
									+ "' is NOT displayed under the 'Comments' section");
					strReason = "Status is updated where '" + strStatReason
							+ "' is NOT displayed under the 'Comments' section";
				}

			} catch (AssertionError ae) {
				log4j
						.info("Status Type is NOT displayed in the User view table");
				strReason = "Status Type is NOT displayed in the User view table";
			}

		} catch (Exception e) {
			log4j
					.info("selectTypeAndCreateStatTypeWithAllFields function failed"
							+ e);
			strReason = "selectTypeAndCreateStatTypeWithAllFields function failed"
					+ e;
		}
		return strReason;
	}
	
	/***********************************************************************
	 'Description :Navigate to Status list page for a particular status type
	 'Precondition :None
	 'Arguments  :selenium,strMultiStatTypeName
	 'Returns  :String
	 'Date    :15-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ************************************************************************/
	 
	public String navToStatusList(Selenium selenium,
			String strMultiStatTypeName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			

			selenium
						.click("//div[@id='mainContainer']//table/tbody/tr/td[2]"
								+ "[text()='"
								+ strMultiStatTypeName
								+ "']/parent::tr/td[1]"
								+ "/a[text()='statuses']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals(
							"Status List for " + strMultiStatTypeName + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status List for " + strMultiStatTypeName
							+ "page is displayed");
				
				} catch (AssertionError Ae) {

					strErrorMsg = "Status List for "
							+ strMultiStatTypeName + " page is NOT displayed"
							+ Ae;
					log4j.info("Status Type List for " + strMultiStatTypeName
							+ " page is NOT displayed" + Ae);
				}
			

		} catch (Exception e) {
			log4j.info("navToStatusList function failed" + e);
			strErrorMsg = "navToStatusList function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:check appropriate values are displayed in respective columns for particular status type in Status Type List screen
	'Precondition	:None
	'Arguments		:selenium,strHeader,statTypeName,strData,strColIndex
	'				
	'Returns		:String
	'Date	 		:02-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                              		<Name>
	************************************************************************/
	
	public String checkDataInStatusTypeList(Selenium selenium,
			String strHeader, String statTypeName, String strData,
			String strColIndex) {
		rdExcel = new ReadData();

		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			

			try {
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Status Type List screen is displayed");
				try {
					// check column headers
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/thead/tr/th["
									+ strColIndex
									+ "][text()='"
									+ strHeader
									+ "']")
							|| selenium.getText(
									"//div[@id='mainContainer']/table[2]/thead/tr/th["
											+ strColIndex + "]/a").equals(
									strHeader));

					log4j.info("Column Header " + strHeader
							+ "is displayed in Status Type list");

					// check appropriate value displayed for particular status
					// type in all columns
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
										+ statTypeName + "']"));

						assertEquals(
								strData,
								selenium
										.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
												+ statTypeName
												+ "']/parent::tr/td["
												+ strColIndex + "]"));
						log4j
								.info("Status type "
										+ statTypeName
										+ " is listed in the status type list screen where appropriate value is displayed as "
										+ strData);

					} catch (AssertionError Ae) {
						log4j
								.info("Status type "
										+ statTypeName
										+ " is listed in the status type list screen where appropriate value is displayed as "
										+ strData);
						strReason = "Status type "
								+ statTypeName
								+ " is listed in the status type list screen where appropriate value is displayed as "
								+ strData;
					}

				} catch (AssertionError Ae) {
					log4j.info("Column Header " + strHeader
							+ " is NOT displayed in Status Type list");
					strReason = "Column Header " + strHeader
							+ " is NOT displayed in Status Type list";
				}
			} catch (AssertionError Ae) {
				log4j.info("Status Type List screen is NOT displayed");
				strReason = "Status Type List screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info("checkDataInStatusTypeList function failed" + e);
			strReason = "checkDataInStatusTypeList function failed"
					+ e.toString();
		}
		return strReason;
	}

	/***********************
	 'Description :Verify mandatory data of status type is edited
	 'Precondition :None
	 'Arguments  :selenium,statTypeName,strStatTypDefn, blnSav 
	 'Returns  :String
	 'Date    :12-April-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '12-April-2012                               <Name>
	 ************************************************************************/
	public String editStatusTypesMandFlds(Selenium selenium,
			String statTypeName, String editStatTypeName,
			String strStatTypDefn, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		General objGeneral = new General();// object of class General

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/table[@class="
					+ "'displayTable striped border sortable']/"
					+ "tbody/tr/td[2][text()='" + statTypeName
					+ "']/parent::tr/td[1]/a[1]");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateStatusType.StatTypName")));
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
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateStatusType.StatTypName")));
				log4j.info("Edit  Status Type page is displayed");

				if (editStatTypeName != "") {
					selenium.type(propElementDetails
							.getProperty("CreateStatusType.StatTypName"),
							editStatTypeName);
				}

				if (strStatTypDefn != "") {
					selenium.type(propElementDetails
							.getProperty("CreateStatusType.StatTypDefn"),
							strStatTypDefn);
				}

				if (blnSav) {
					selenium.click(propElementDetails.getProperty("Save"));

					String strElementID = "//div[@id='mainContainer']"
							+ "/table[@class='displayTable striped border sortable']"
							+ "/tbody/tr/td[2][text()='" + editStatTypeName
							+ "']";
					selenium.waitForPageToLoad(gstrTimeOut);
					
					
					if(selenium.isElementPresent(propElementDetails.getProperty("CreateStatusType.ReturnToSTList"))){
						
						intCnt=0;
						do{
							try{
								assertTrue(selenium
										.isElementPresent(propElementDetails.getProperty("CreateStatusType.ReturnToSTList")));
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
						
						
						selenium.click(propElementDetails.getProperty("CreateStatusType.ReturnToSTList"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
					}
					strErrorMsg = objGeneral.CheckForElements(selenium,
							strElementID);

					try {
						assertEquals("", strErrorMsg);
						log4j.info("Status type " + editStatTypeName
								+ " is created and is listed in the "
								+ "'Status Type List' screen. ");
					} catch (AssertionError Ae) {
						log4j.info("Status type " + editStatTypeName
								+ " The updated data is NOT displayed on the"
								+ " 'Status Type List' screen. ");

						strErrorMsg = "Status type " + editStatTypeName
								+ " The updated data is NOT displayed on the"
								+ " 'Status Type List' screen.  " + Ae;
						;
					}

				}

				if (blnSav && strStatTypDefn != "" && editStatTypeName != "") {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[@class="
									+ "'displayTable striped border sortable']/"
									+ "tbody/tr/td[2][text()='"
									+ editStatTypeName + "']")
							&& selenium
									.isElementPresent("//div[@id='mainContainer']/table[@class="
											+ "'displayTable striped border sortable']/"
											+ "tbody/tr/td[7][text()='"
											+ strStatTypDefn + "']"));

				} else if (blnSav && strStatTypDefn != ""
						&& editStatTypeName == "") {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[@class="
									+ "'displayTable striped border sortable']/"
									+ "tbody/tr/td[7][text()='"
									+ strStatTypDefn + "']"));

				} else if (blnSav && strStatTypDefn == ""
						&& editStatTypeName != "") {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[@class="
									+ "'displayTable striped border sortable']/"
									+ "tbody/tr/td[2][text()='"
									+ editStatTypeName + "']"));
				}

			} catch (AssertionError Ae) {

				log4j.info("Edit  Status Type page is NOT displayed");
				strErrorMsg = "Edit  Status Type page is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info("editStatusTypesMandFlds function failed" + e);
			strErrorMsg = "editStatusTypesMandFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify status types are sorted
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/
	
	public String sortStatusTypes(Selenium selenium, String statTypeName,
			String[] childStatTypeName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		General objGeneral = new General();// object of class General

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			String strSectdLabl[] = { "", "", "" };
			String gstrLabl = "";

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/table[@class="
					+ "'displayTable striped border sortable']/"
					+ "tbody/tr/td[2][text()='" + statTypeName
					+ "']/parent::tr/td[1]/a[3]");
			// selenium.waitForPageToLoad(gstrTimeOut);

			strErrorMsg = objGeneral.CheckForElements(selenium,
					propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusSave"));

			try {
				assertEquals("", strErrorMsg);
				assertEquals("Sort Status Type Values", selenium
						.getText("css=h1"));
				log4j.info("Sort Status Type Values is  displayed");

				strSectdLabl[0] = selenium
						.getSelectedLabel("//div[@id='mainContainer']/form/table/tbody/tr"
								+ "/td/font[text()='"
								+ childStatTypeName[0]
								+ "']/ancestor::tr/td[2]/" + "select");

				strSectdLabl[1] = selenium
						.getSelectedLabel("//div[@id='mainContainer']/form/table/tbody/tr"
								+ "/td/font[text()='"
								+ childStatTypeName[1]
								+ "']/ancestor::tr/td[2]/" + "select");

				strSectdLabl[2] = selenium
						.getSelectedLabel("//div[@id='mainContainer']/form/table/tbody/"
								+ "/td/font[text()='"
								+ childStatTypeName[2]
								+ "']/ancestor::tr/td[2]/" + "select");

				log4j.info(strSectdLabl[0]);
				log4j.info(strSectdLabl[1]);
				log4j.info(strSectdLabl[2]);

				if (strSectdLabl[0].equals("1")) {
					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[0] + "']/ancestor::"
									+ "tr/td[2]/select", "label=2");// auto

					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[1] + "']/ancestor::"
									+ "tr/td[2]/select", "label=3");// available

					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[2] + "']/ancestor::"
									+ "tr/td[2]/select", "label=1");// not
																	// available

					gstrLabl = "1";

				} else if (strSectdLabl[0].equals("2")) {
					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[0] + "']/ancestor::"
									+ "tr/td[2]/select", "label=3");// auto

					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[1] + "']/ancestor::"
									+ "tr/td[2]/select", "label=2");// available

					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[2] + "']/ancestor::"
									+ "tr/td[2]/select", "label=1");// not
																	// available

					gstrLabl = "2";

				} else if (strSectdLabl[0].equals("3")) {
					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[0] + "']/ancestor::"
									+ "tr/td[2]/select", "label=1");// auto

					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[1] + "']/ancestor::"
									+ "tr/td[2]/select", "label=2");// available

					selenium.select(
							"//div[@id='mainContainer']/form/table/tbody/tr"
									+ "/td/font[text()='"
									+ childStatTypeName[2] + "']/ancestor::"
									+ "tr/td[2]/select", "label=3");// not
																	// available

					gstrLabl = "3";
				}

				selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusSave"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Type List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Status Type List is   displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Status Type List is NOT displayed" + Ae;
					log4j.info("Status Type List is NOT  displayed");
				}

				if (gstrLabl.equals("1")) {
					try {
						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[8]/font[2]"),
								childStatTypeName[0]);

						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[8]/font[3]"),
								childStatTypeName[1]);

						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[8]/font[1]"),
								childStatTypeName[2]);

						log4j.info("Status types are  sorted");
					} catch (AssertionError Ae) {
						strErrorMsg = "Status types are  NOT sorted" + Ae;
						log4j.info("Status types are  NOT sorted");
					}
				} else if (gstrLabl.equals("2")) {
					try {
						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[7]/font[3]"),
								childStatTypeName[0]);

						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[7]/font[2]"),
								childStatTypeName[1]);

						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[7]/font[1]"),
								childStatTypeName[2]);

						log4j.info("Status types are  sorted");
					} catch (AssertionError Ae) {
						strErrorMsg = "Status types are  NOT sorted" + Ae;
						log4j.info("Status types are  NOT sorted");
					}
				} else if (gstrLabl.equals("3")) {
					try {
						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[7]/font[1]"),
								childStatTypeName[0]);

						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[7]/font[2]"),
								childStatTypeName[1]);

						assertEquals(selenium
								.getText("//div[@id='mainContainer']/table[2]"
										+ "/tbody/tr/td[2][text()='"
										+ statTypeName + "']"
										+ "/parent::tr/td[7]/font[3]"),
								childStatTypeName[2]);

						log4j.info("Status types are  sorted");
					} catch (AssertionError Ae) {
						strErrorMsg = "Status types are  NOT sorted" + Ae;
						log4j.info("Status types are  NOT sorted");
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Sort Status Type Values is NOT displayed" + Ae;
				log4j.info("Sort Status Type Values is NOT  displayed");
			}

		} catch (Exception e) {
			log4j.info("sortStatusTypes function failed" + e);
			strErrorMsg = "sortStatusTypes function failed" + e;
		}
		return strErrorMsg;
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
	
	public String navViewOtherRegionSecurityPge_StatusType(Selenium selenium,
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
					.getProperty("OtherRegionList.ShareStatusTyp"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("View Other Region Security - Status Types",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("View Other Region Security - Status Types "
						+ "page is displayed");

				if (blnBack) {
					selenium
							.click(propElementDetails
									.getProperty("ViewOtherRegionSecurityStatusTypes.Back"));
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

				strErrorMsg = "View Other Region Security - Status Types page "
						+ "is NOT displayed" + Ae;
				log4j.info("View Other Region Security - Status Types page"
						+ " is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j
					.info("navViewOtherRegionSecurityPge_StatusType function failed"
							+ e);
			strErrorMsg = "navViewOtherRegionSecurityPge_StatusType function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify status type and all other filds are listed in 
	'				Statues list pagee is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:23-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String savAndVerifyST(Selenium selenium, String strSTName,
			String strSTtype, String strStandardSTtype, String strDescription,
			String strStatues) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CreateStatusType.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Status Type List is   displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
									+ strSTName + "']"));
					log4j.info(strSTName + "  listed in the status type list");

				} catch (AssertionError Ae) {
					log4j.info(strSTName
							+ " NOT listed in the status type list" + Ae);
					strErrorMsg = strSTName
							+ " NOT listed in the status type list" + Ae;
				}

				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
									+ strSTName
									+ "']/parent::tr/td[3][text()='"
									+ strSTtype + "']"));
					log4j.info(strSTtype + "  listed in the status type list");

				} catch (AssertionError Ae) {
					log4j.info(strSTtype
							+ " NOT listed in the status type list" + Ae);
					strErrorMsg = strSTtype
							+ " NOT listed in the status type list" + Ae;
				}

				if (strStandardSTtype != "") {
					try {
						assertEquals("", strErrorMsg);

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
										+ strSTName
										+ "']/parent::tr/td[5][text()='"
										+ strStandardSTtype + "']"));
						log4j.info(strStandardSTtype
								+ "  listed in the status type list");

					} catch (AssertionError Ae) {
						log4j.info(strStandardSTtype
								+ " NOT listed in the status type list" + Ae);
						strErrorMsg = strStandardSTtype
								+ " NOT listed in the status type list" + Ae;
					}
				}

				try {
					assertEquals("", strErrorMsg);

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
									+ strSTName
									+ "']/parent::tr/td[7][text()='"
									+ strDescription + "']"));
					log4j.info(strDescription
							+ "  listed in the status type list");

				} catch (AssertionError Ae) {
					log4j.info(strDescription
							+ " NOT listed in the status type list" + Ae);
					strErrorMsg = strDescription
							+ " NOT listed in the status type list" + Ae;
				}

				if (strStatues != "") {
					try {
						assertEquals("", strErrorMsg);

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
										+ strSTName
										+ "']/parent::tr/td[8][text()='"
										+ strStatues + "']"));
						log4j.info(strStatues
								+ "  listed in the status type list");

					} catch (AssertionError Ae) {
						log4j.info(strStatues
								+ " NOT listed in the status type list" + Ae);
						strErrorMsg = strStatues
								+ " NOT listed in the status type list" + Ae;
					}
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "Status Type List is NOT displayed" + Ae;
				log4j.info("Status Type List is NOT  displayed");

			}

		} catch (Exception e) {
			log4j.info("savAndVerifyST function failed" + e);
			strErrorMsg = "savAndVerifyST function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify private status type in edit role page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:23-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String privateSTinEditRole(Selenium selenium, String strSTValue,
			boolean blnRoleUpdate) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertFalse(selenium.isChecked("css=input[value='" + strSTValue
						+ "'][@name='viewRightInd']"));
				assertFalse(selenium.isEditable("css=input[value='"
						+ strSTValue + "'][name='viewRightInd']"));

				log4j
						.info("Private status type ST is Unchecked and disabled"
								+ " under Select the Status Types this Role may view: section.");

			} catch (AssertionError Ae) {
				log4j
						.info("Private status type ST is NOT Unchecked and disabled"
								+ " under Select the Status Types this Role may view: section."
								+ Ae);
				strErrorMsg = "Private status type ST is NOT Unchecked and disabled"
						+ " under Select the Status Types this Role may view: section."

						+ Ae;
			}

			if (blnRoleUpdate) {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strSTValue + "'][name='updateRightInd']"));
					assertTrue(selenium.isEditable("css=input[value='"
							+ strSTValue + "'][name='updateRightInd']"));

					log4j
							.info("Private status type ST is checked and enabled"
									+ " under Select the Status Types this Role may update: section.");

				} catch (AssertionError Ae) {
					log4j
							.info("Private status type ST is NOT checked and enabled"
									+ " under Select the Status Types this Role may update: section."
									+ Ae);
					strErrorMsg = "Private status type ST is NOT checked and enabled"
							+ " under Select the Status Types this Role may view: section."
							+ Ae;
				}

			} else {

				try {
					assertFalse(selenium.isChecked("css=input[value='"
							+ strSTValue + "'][name='updateRightInd']"));
					assertTrue(selenium.isEditable("css=input[value='"
							+ strSTValue + "'][name='updateRightInd']"));

					log4j
							.info("Private status type ST is Unchecked and enabled"
									+ " under Select the Status Types this Role may update: section.");

				} catch (AssertionError Ae) {
					log4j
							.info("Private status type ST is NOT Unchecked and enabled"
									+ " under Select the Status Types this Role may update: section."
									+ Ae);
					strErrorMsg = "Private status type ST is NOT Unchecked and enabled"
							+ " under Select the Status Types this Role may view: section."
							+ Ae;
				}
			}

		} catch (Exception e) {
			log4j.info("navStatusTypList function failed" + e);
			strErrorMsg = "navStatusTypList function failed" + e;
		}
		return strErrorMsg;
	}

	 /********************************************************************************************************
	  'Description :Fetch Status Type Value in Status Type List page
	  'Precondition :None
	  'Arguments  :selenium,strStatusType
	  'Returns  :String
	  'Date    :8-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/
	public String fetchSTValueInStatTypeList(Selenium selenium,
			String strStatusType) throws Exception {

		String strStatValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//table[@summary='Status Types']/tbody/tr/td[2][text()='"
									+ strStatusType + "']/parent::"
									+ "tr/td[1]/a/"));

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			String strResValueArr[] = selenium
					.getAttribute(
							"//table[@summary='Status Types']/tbody/tr/td[2][text()='"
									+ strStatusType + "']/parent::"
									+ "tr/td[1]/a/@href").split(
							"nextStepDetail=");
			strStatValue = strResValueArr[1];
			log4j.info("Status Type Value is " + strStatValue);
		} catch (Exception e) {
			log4j.info("fetchSTValueInStatTypeList function failed" + e);
			strStatValue = "";
		}
		return strStatValue;
	}
	  
	  /***********************************************************************
	  'Description : Create multi status types with mandatory fields and Expiration
	      time.
	  'Precondition :None
	  'Arguments  :selenium,strMultiStatTypeName,strStatusName,strStatusTypeValue,
	      strStatTypeColor,strExpHr,strExpMn,blnSav
	  '     
	  'Returns  :String
	  'Date    :12-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  'date                                     <Name>
	  ************************************************************************/
	  
	public String createSTWithinMultiTypeSTExpTime(Selenium selenium,
			String strMultiStatTypeName, String strStatusName,
			String strStatusTypeValue, String strStatTypeColor,
			String strExpHr, String strExpMn, String strAutoChSt,
			String strAutoChCmn, boolean blnSav) throws Exception {

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
				try {
					assertEquals("Status Type List", selenium
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
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Status Type List page is displayed");

				selenium
						.click("//div[@id='mainContainer']//table/tbody/tr/td[2]"
								+ "[text()='"
								+ strMultiStatTypeName
								+ "']/parent::tr/td[1]"
								+ "/a[text()='statuses']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals(
							"Status List for " + strMultiStatTypeName + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status Type List for " + strMultiStatTypeName
							+ "page is displayed");

					selenium.click(propElementDetails
							.getProperty("StatusType.MultiST_CreateNewStatus.Link"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try {
							assertEquals("Create New Status", selenium
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
						assertEquals("Create New Status", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Create New Status page is displayed");

						selenium
								.click(propElementDetails
										.getProperty("StatusType.CreateStatType.Expire"));
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Hours"),
										"label=" + strExpHr);
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Mins"),
										"label=" + strExpMn);

						selenium
								.type(propElementDetails
										.getProperty("StatusType.MultiST_CreateNewStatusName"), strStatusName);
						selenium.select(propElementDetails
								.getProperty("StatusType.MultiST_CreateNewStatusColourCode"),
								"label=" + strStatTypeColor);

						selenium.select(propElementDetails
								.getProperty("StatusType.MultiST_CreateNewStatusIdAutoh"),
								"label=" + strAutoChSt);
						selenium.type(propElementDetails
								.getProperty("StatusType.MultiST_CreateNewStatusCommentCode"),
								strAutoChCmn);

						if (blnSav) {

							selenium.click(propElementDetails
									.getProperty("StatusType.MultiST_CreateNewStatusSave"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {
								
								intCnt=0;
								do{
									try {

										assertTrue(selenium.isElementPresent(propElementDetails
												.getProperty("CreateStatusType.ReturnToSTList")));
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
										.click(propElementDetails
												.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);
								intCnt=0;
								do{
									try {

										assertTrue(selenium
												.isElementPresent("//div[@id='mainContainer']/table[2]"
														+ "/tbody/tr/td[2][text()='"
														+ strMultiStatTypeName
														+ "']"
														+ "/parent::tr/td[8]/font[text()='"
														+ strStatusName + "']"));

										break;
									}catch(AssertionError Ae){
										Thread.sleep(1000);
										intCnt++;
									
									} catch (Exception Ae) {
										Thread.sleep(1000);
										intCnt++;
									}
								}while(intCnt<60);
								
								
							}

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[2]"
												+ "/tbody/tr/td[2][text()='"
												+ strMultiStatTypeName
												+ "']"
												+ "/parent::tr/td[8]/font[text()='"
												+ strStatusName + "']"));

								log4j
										.info("Status is  Listed  in the corresponding"
												+ " multistatus type page");
							} catch (AssertionError Ae) {
								strErrorMsg = "Status is  NOT Listed  in the corresponding"
										+ " multistatus type page" + Ae;
								log4j
										.info("Status is  COT Listed  in the corresponding"
												+ " multistatus type page" + Ae);
							}

						}

					} catch (AssertionError Ae) {

						strErrorMsg = "Create New Status page is NOT displayed"
								+ Ae;
						log4j.info("Create New Status page is NOT displayed"
								+ Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Status Type List for "
							+ strMultiStatTypeName + " page is NOT displayed"
							+ Ae;
					log4j.info("Status Type List for " + strMultiStatTypeName
							+ " page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Status Type List page is NOT displayed" + Ae;
				log4j.info("Status Type List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("createSTWithinMultiTypeSTExpTime function failed" + e);
			strErrorMsg = "createSTWithinMultiTypeSTExpTime function failed"
					+ e;
		}
	
		return strErrorMsg;
	}

	
	  /***********************************************************************
	  'Description : Create multi status types with mandatory fields and Expiration
	      time.
	  'Precondition :None
	  'Arguments  :selenium,strMultiStatTypeName,strStatusName,strStatusTypeValue,
	      strStatTypeColor,strExpHr,strExpMn,blnSav
	  '     
	  'Returns  :String
	  'Date    :12-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  'date                                     <Name>
	  ************************************************************************/
	  
	public String createSTWithinMultiTypeSTExpTimeWithoutChangeStatus(Selenium selenium,
			String strMultiStatTypeName, String strStatusName,
			String strStatusTypeValue, String strStatTypeColor,
			String strExpHr, String strExpMn,
			String strAutoChCmn, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Status Type List page is displayed");

				selenium
						.click("//div[@id='mainContainer']//table/tbody/tr/td[2]"
								+ "[text()='"
								+ strMultiStatTypeName
								+ "']/parent::tr/td[1]"
								+ "/a[text()='statuses']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals(
							"Status List for " + strMultiStatTypeName + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status Type List for " + strMultiStatTypeName
							+ "page is displayed");

					selenium.click(propElementDetails
							.getProperty("StatusType.MultiST_CreateNewStatus.Link"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Create New Status", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Create New Status page is displayed");

						selenium
								.click(propElementDetails
										.getProperty("StatusType.CreateStatType.Expire"));
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Hours"),
										"label=" + strExpHr);
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Mins"),
										"label=" + strExpMn);

						selenium
								.type(propElementDetails
										.getProperty("StatusType.MultiST_CreateNewStatusName"), strStatusName);
						selenium.select(propElementDetails
								.getProperty("StatusType.MultiST_CreateNewStatusColourCode"),
								"label=" + strStatTypeColor);

					
						selenium.type(propElementDetails
								.getProperty("StatusType.MultiST_CreateNewStatusCommentCode"),
								strAutoChCmn);

						if (blnSav) {

							selenium.click(propElementDetails
									.getProperty("StatusType.MultiST_CreateNewStatusSave"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {
								selenium
										.click(propElementDetails
												.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);
							}

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[2]"
												+ "/tbody/tr/td[2][text()='"
												+ strMultiStatTypeName
												+ "']"
												+ "/parent::tr/td[8]/font[text()='"
												+ strStatusName + "']"));

								log4j
										.info("Status is  Listed  in the corresponding"
												+ " multistatus type page");
							} catch (AssertionError Ae) {
								strErrorMsg = "Status is  NOT Listed  in the corresponding"
										+ " multistatus type page" + Ae;
								log4j
										.info("Status is  COT Listed  in the corresponding"
												+ " multistatus type page" + Ae);
							}

						}

					} catch (AssertionError Ae) {

						strErrorMsg = "Create New Status page is NOT displayed"
								+ Ae;
						log4j.info("Create New Status page is NOT displayed"
								+ Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Status Type List for "
							+ strMultiStatTypeName + " page is NOT displayed"
							+ Ae;
					log4j.info("Status Type List for " + strMultiStatTypeName
							+ " page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Status Type List page is NOT displayed" + Ae;
				log4j.info("Status Type List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("createSTWithinMultiTypeSTExpTimeWithoutChangeStatus function failed" + e);
			strErrorMsg = "createSTWithinMultiTypeSTExpTimeWithoutChangeStatus function failed"
					+ e;
		}
	
		return strErrorMsg;
	}

	
	 /********************************************************************************************************
	  'Description :Fetch Status Value in Status List page
	  'Precondition :None
	  'Arguments  :selenium,strStatus
	  'Returns  :String
	  'Date    :12-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/
	public String fetchStatValInStatusList(Selenium selenium,
			String strMultiStatTypeName, String strStatus) throws Exception {
		
		
		String strStatValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		int intCnt=0;
		do{
			try{
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']//table/tbody/tr/td[2]"
								+ "[text()='" + strMultiStatTypeName
								+ "']/parent::tr/td[1]" + "/a[text()='statuses']"));
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
			selenium.click("//div[@id='mainContainer']//table/tbody/tr/td[2]"
					+ "[text()='" + strMultiStatTypeName
					+ "']/parent::tr/td[1]" + "/a[text()='statuses']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent("link=Return to Status Type List"));
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
			
			

			String strResValueArr[] = selenium.getAttribute(
					"//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
							+ strStatus + "']/parent::tr/td[1]/a@href").split(
					"statusID=");
			strStatValue = strResValueArr[1];
			log4j.info("Status Value is " + strStatValue);

			selenium.click("link=Return to Status Type List");
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("fetchStatValInStatusList function failed" + e);
			strStatValue = "";
		}
		return strStatValue;
	}

	  /****************************************************************************************
		 'Description :Verify default visibility of the Status typr is retained while editing the ST
		 'Precondition :None
		 'Arguments  :selenium,statTypeName,strStatTypDefn, blnSav ,strVisibiltyValue
		 'Returns  :String
		 'Date    :13-June-2012
		 'Author   :QSG
		 '-----------------------------------------------------------------------
		 'Modified Date                            Modified By
		 '13-June-2012                               <Name>
	**********************************************************************************/
	  
	public String StatusTypesDefaultVisibility(Selenium selenium,
			String statTypeName, String strStatusTypeValue,
			boolean blnVisibltyEnable, boolean blnCheckd,
			String strVisiltyValue, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage


		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnVisibltyEnable) {
				try {
					assertTrue(selenium.isEditable("css=input[value='"
							+ strVisiltyValue + "'][name='visibility']"));

					log4j.info(strVisiltyValue
							+ "Options under  'Status Type Visibility' are "
							+ "enabled in the 'Create " + strStatusTypeValue
							+ " Status Type' screen. ");

				} catch (AssertionError Ae) {

					log4j.info(strVisiltyValue
							+ "Options under 'Status Type Visibility' are NOT "
							+ "enabled in the 'Create " + strStatusTypeValue
							+ " Status Type' screen. ");

					strErrorMsg = strVisiltyValue
							+ "Options under 'Status Type Visibility' are NOT "
							+ "enabled in the 'Create " + strStatusTypeValue
							+ " Status Type' screen. ";

				}
			} else {

				try {
					assertFalse(selenium.isEditable("css=input[value='"
							+ strVisiltyValue + "'][name='visibility']"));

					log4j.info(strVisiltyValue
							+ "Options under 'Status Type Visibility' are "
							+ "NOT enabled in the 'Create "
							+ strStatusTypeValue + " Status Type' screen. ");

				} catch (AssertionError Ae) {

					log4j.info(strVisiltyValue
							+ "Options under 'Status Type Visibility' are  "
							+ "enabled in the 'Create " + strStatusTypeValue
							+ " Status Type' screen. ");

					strErrorMsg = strVisiltyValue
							+ "Options under 'Status Type Visibility' are  "
							+ "enabled in the 'Create " + strStatusTypeValue
							+ " Status Type' screen. ";

				}

			}

			if (blnCheckd) {

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strVisiltyValue + "'][name='visibility']"));

					log4j.info(strVisiltyValue+"  option is selected"
							+ " for 'Status Type Visibility' ");

				} catch (AssertionError Ae) {

					log4j.info(strVisiltyValue+"  option is NOT selected"
							+ "  for 'Status Type Visibility'  ");

					strErrorMsg = strVisiltyValue+"  option is NOT selected"
							+ "  for 'Status Type Visibility'  ";

				}

			} else {
				try {
					assertFalse(selenium.isChecked("css=input[value='"
							+ strVisiltyValue + "'][name='visibility']"));

					log4j.info(strVisiltyValue+"  option is NOT selected"
							+ " for 'Status Type Visibility' ");

				} catch (AssertionError Ae) {


					log4j.info(strVisiltyValue+"  option is selected"
							+ "  for 'Status Type Visibility'  ");

					strErrorMsg = strVisiltyValue+"  option is selected"
							+ "  for 'Status Type Visibility'  ";
				}

			}

			if (blnSav) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);


				try {
					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']"
						+ "/table[@class='displayTable striped border sortable']"
						+ "/tbody/tr/td[2][text()='" + statTypeName + "']"));
					log4j.info("Status type " + statTypeName
							+ "  is listed in the "
							+ "'Status Type List' screen. ");
				} catch (AssertionError Ae) {
					log4j.info("Status type " + statTypeName
							+ "  is NOT listed in the"
							+ " 'Status Type List' screen. ");

					strErrorMsg = "Status type " + statTypeName
							+ "  is NOT listed in the"
							+ " 'Status Type List' screen.  " + Ae;
				}

			}

		} catch (Exception e) {
			log4j.info("StatusTypesDefaultVisibility function failed" + e);
			strErrorMsg = "StatusTypesDefaultVisibility function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields are filled along with Visibility Value
	'Precondition	:None
	'Arguments		:selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	'				 blnSav
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/
	
	public String selectSTAndFilMandFldsWithSTVisibility(Selenium selenium,
			String strStatusTypeValue, String statTypeName,
			String strStatTypDefn, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("CreateStatusType"));
			selenium.waitForPageToLoad(gstrTimeOut);


			int intCnt=0;
			do{
				try{
					assertEquals("Select Status Type", selenium
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
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					
					intCnt=0;
					do{
						try{
							assertEquals("Create " + strStatusTypeValue
									+ " Status Type", selenium
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
						assertEquals("Create " + strStatusTypeValue
								+ " Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is  displayed");

						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypName"),
								statTypeName);
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypDefn"),
								strStatTypDefn);

					try {
						assertTrue(selenium
								.isEditable(propElementDetails
										.getProperty("StatusType.CreateStatType.StatusTypeVisib.RegOnly"))
								&& selenium
										.isEditable(propElementDetails
												.getProperty("StatusType.CreateStatType.StatusTypeVisib.Shared"))
								&& selenium
										.isEditable(propElementDetails
												.getProperty("StatusType.CreateStatType.StatusTypeVisib.Private")));

							log4j
									.info("Options under 'Status Type Visibility' are "
											+ "enabled in the 'Create "
											+ strStatusTypeValue
											+ " Status Type' screen. ");

						} catch (AssertionError Ae) {

							log4j
									.info("Options under 'Status Type Visibility' are NOT "
											+ "enabled in the 'Create "
											+ strStatusTypeValue
											+ " Status Type' screen. ");

							strErrorMsg = "Options under 'Status Type Visibility' are NOT "
									+ "enabled in the 'Create "
									+ strStatusTypeValue
									+ " Status Type' screen. ";

						}

						try {
							assertTrue(selenium
									.isChecked(propElementDetails
											.getProperty("StatusType.CreateStatType.StatusTypeVisib.RegOnly")));

							log4j
									.info("By default option 'Users with "
											+ "appropriate roles within the region "
											+ "may view or update this status type' is selected. ");

						} catch (AssertionError Ae) {

							log4j
									.info("By default option 'Users with "
											+ "appropriate roles within the region "
											+ "may view or update this status type' is NOT selected. ");

							strErrorMsg = "By default option 'Users with "
									+ "appropriate roles within the region "
									+ "may view or update this status type' is NOT selected.";
						}

						if (blnSav) {
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);
				
							if (strStatusTypeValue.equals("Multi")) {
								intCnt=0;
								do{
									try{
										assertTrue(selenium
												.isElementPresent(propElementDetails
														.getProperty("CreateStatusType.ReturnToSTList")));
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
								
								selenium
										.click(propElementDetails
												.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);

							}

							
							intCnt=0;
							do{
								try{
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/"
													+ "table/tbody/tr/td[2][text()='"
													+ statTypeName + "']"));
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
										.isElementPresent("//div[@id='mainContainer']/"
												+ "table/tbody/tr/td[2][text()='"
												+ statTypeName + "']"));

								log4j.info("Status type " + statTypeName
										+ " is created and is listed in the "
										+ "'Status Type List' screen. ");

							} catch (AssertionError Ae) {

								log4j
										.info("Status type "
												+ statTypeName
												+ " is created and is NOT listed in the "
												+ "'Status Type List' screen. ");

								strErrorMsg = "Status type "
										+ statTypeName
										+ " is created and is NOT listed in the "
										+ "'Status Type List' screen. " + Ae;
							}

						}

					

					} catch (AssertionError Ae) {
						strErrorMsg = "Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae;
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			
		} catch (Exception e) {
			log4j.info("selectSTAndFilMandFldsWithSTVisibility function failed"
					+ e);
			strErrorMsg = "selectSTAndFilMandFldsWithSTVisibility function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	 /********************************************************************************************************
	  'Description :Select Visibility value
	  'Precondition :None
	  'Arguments  :selenium,strVisibilty,statTypeName,blnSave
	  'Returns  :String
	  'Date    :13-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ***********************************************************************************************************/

	public String selectVisibiltyValue(Selenium selenium, String statTypeName,
			String strVisibilty, boolean blnSave) throws Exception {

		String strErrorMsg = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("css=input[value='" + strVisibilty
							+ "'][name='visibility']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			selenium.click("css=input[value='" + strVisibilty
					+ "'][name='visibility']");
			log4j.info(" visibilty option "+strVisibilty+" is selected");

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				if(selenium.isElementPresent(propElementDetails
						.getProperty("CreateStatusType.ReturnToSTList"))){
					selenium.click(propElementDetails
							.getProperty("CreateStatusType.ReturnToSTList"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
				}
				intCnt=0;
				do{
					try {

						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/table[@class='displayTable striped border sortable']"
										+ "/tbody/tr/td[2][text()='" + statTypeName
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
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/table[@class='displayTable striped border sortable']"
									+ "/tbody/tr/td[2][text()='" + statTypeName
									+ "']"));
					log4j.info("Status type " + statTypeName
							+ " is listed in the "
							+ "'Status Type List' screen. ");

				} catch (AssertionError Ae) {
					log4j.info("Status type " + statTypeName
							+ " is NOT listed in the "
							+ "'Status Type List' screen. " + Ae);

					strErrorMsg = "Status type " + statTypeName
							+ " is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;

				}

			}

		} catch (Exception e) {
			log4j.info("selectVisibiltyValue function failed" + e);
			strErrorMsg = "selectVisibiltyValue function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	  'Description  :selecting  Role while creating  ST 
	  'Precondition :None
	  'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
	  '				strRoleValue,blnSave,strRoleName
	  'Returns      :String
	  'Date         :14-June-2012
	  'Author       :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '14-June-2012                               <Name>
	  ************************************************************************/
	  
	public String slectAndDeselectRoleInST(Selenium selenium,
			boolean blnViewRightSelectAll, boolean blnUpdateRightSelectAll,
			String[] strRoleValue, boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Select the Status Types this Role may view
			if (blnViewRightSelectAll) {

				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayView"))) {

				} else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayView")))
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
				else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
				}

				for (String strVal : strRoleValue) {
					selenium.click("css=input[name='roleView'][value='"
							+ strVal + "']");
				}

			}

			// Select the Status Types this Role may Update
			if (blnUpdateRightSelectAll) {

				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"))) {

				} else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate")))
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
				else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
				}

				for (String strVal : strRoleValue) {
					selenium.click("css=input[name='roleUpdate'][value='"
							+ strVal + "']");
				}

			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectRoleInST function failed" + e);
			strErrorMsg = "slectAndDeselectRoleInST function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
    'Description  :selecting  Role while creating  ST 
    'Precondition :None
    'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
    '    strRoleValue,blnSave,strRoleName
    'Returns      :String
    'Date         :14-June-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                            Modified By
    '14-June-2012                               <Name>
    ************************************************************************/

	public String slectAndDeselectRoleInSTNew(Selenium selenium,
			boolean blnViewRightSelectAll, boolean blnUpdateRightSelectAll,
			String[][] strRoleViewValue, String[][] strRoleUpdateValue,
			boolean blnSave) throws Exception {


		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Select the Status Types this Role may view
			if (blnViewRightSelectAll) {

				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayView"))) {

				} else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayView")))
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
				else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayView"));
				}

				for (int i = 0; i < strRoleViewValue.length; i++) {

					if (strRoleViewValue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='roleView'][value='"
										+ strRoleViewValue[i][0] + "']") == false) {
							selenium.click("css=input[name='roleView'][value='"
									+ strRoleViewValue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='roleView'][value='"
									+ strRoleViewValue[i][0] + "']")) {
						selenium.click("css=input[name='roleView'][value='"
								+ strRoleViewValue[i][0] + "']");
					}

				}

			}

			// Select the Status Types this Role may Update
			if (blnUpdateRightSelectAll) {

				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"))) {

				} else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate")))
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
				else {
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
					selenium
							.click(propElementDetails.getProperty("StatusType.ALL.RoleMayUpdate"));
				}

				for (int i = 0; i < strRoleUpdateValue.length; i++) {

					if (strRoleUpdateValue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='roleUpdate'][value='"
										+ strRoleUpdateValue[i][0] + "']") == false) {
							selenium
									.click("css=input[name='roleUpdate'][value='"
											+ strRoleUpdateValue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='roleUpdate'][value='"
									+ strRoleUpdateValue[i][0] + "']")) {
						selenium.click("css=input[name='roleUpdate'][value='"
								+ strRoleUpdateValue[i][0] + "']");
					}

				}

			}

			if (blnSave) {
				
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

						assertTrue(selenium.isElementPresent
								(propElementDetails.getProperty("CreateStatusType.ReturnToSTList")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<10);

				if (selenium.isElementPresent(propElementDetails
						.getProperty("CreateStatusType.ReturnToSTList"))) {
					selenium.click(propElementDetails
							.getProperty("CreateStatusType.ReturnToSTList"));
					selenium.waitForPageToLoad(gstrTimeOut);

				}

			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectRoleInSTNew function failed" + e);
			strErrorMsg = "slectAndDeselectRoleInSTNew function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	  'Description :Navigate to Edit Status list page for a particular status
	  'Precondition :None
	  'Arguments  :selenium,strStatusName
	  'Returns  :String
	  'Date    :15-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                    <Name>
	  ************************************************************************/
	  
	public String editStatusForST(Selenium selenium, String strStatusName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[2]"
					+ "[text()='" + strStatusName + "']/parent::tr/td[1]"
					+ "/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Status", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Edit Status screen is displayed ");
			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Status screen is NOT displayed";

				log4j.info("Edit Status screen is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("editStatusForST function failed" + e);
			strErrorMsg = "editStatusForST function failed" + e;
		}
		return strErrorMsg;
	}

	/**********
	  'Description :Save and Verify Status in Status List page
	  'Precondition :None
	  'Arguments  :selenium,strMultiStatTypeName,strStatusName
	  '     
	  'Returns  :String
	  'Date    :18-June-2012
	  'Author   :QSG
	  '-----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ************************************************************************/
	  
	public String saveAndVerifyStatus(Selenium selenium,
			String strMultiStatTypeName, String strStatusName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("StatusType.MultiST_CreateNewStatusSave")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			// save
			selenium.click(propElementDetails
					.getProperty("StatusType.MultiST_CreateNewStatusSave"));
			selenium.waitForPageToLoad(gstrTimeOut);

			
			intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateStatusType.ReturnToSTList")));
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
					.getProperty("CreateStatusType.ReturnToSTList"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/tbody/tr/td[2][text()='"
									+ strMultiStatTypeName + "']"
									+ "/parent::tr/td[8]/font[text()='"
									+ strStatusName + "']"));
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
						.isElementPresent("//div[@id='mainContainer']/table[2]"
								+ "/tbody/tr/td[2][text()='"
								+ strMultiStatTypeName + "']"
								+ "/parent::tr/td[8]/font[text()='"
								+ strStatusName + "']"));

				log4j.info("Status is  Listed  in the corresponding"
						+ " multistatus type page");
			} catch (AssertionError Ae) {
				strErrorMsg = "Status is  NOT Listed  in the corresponding"
						+ " multistatus type page" + Ae;
				log4j.info("Status is  NOT Listed  in the corresponding"
						+ " multistatus type page" + Ae);
			}

		} catch (Exception e) {
			log4j.info("saveAndVerifyStatus function failed" + e);
			strErrorMsg = "saveAndVerifyStatus function failed" + e;
		}
		return strErrorMsg;
	}

	 /*********************************************************************
     'Description :Verify select status type  page is displayed and 
     '     mandatory fields with Time
     'Arguments  :selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
     '     blnSav
     'Returns  :String
     'Date    :11-June-2012
     'Author   :QSG
     '-----------------------------------------------------------------------
     'Modified Date                            Modified By
     '12-April-2012                               <Name>
     ************************************************************************/
     
	public String selectSTypesAndFilMandFldsWithExTime(Selenium selenium,
			String strStatusTypeValue, String statTypeName,
			String strStatTypDefn, String strExpHr, String strExpMn,
			boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		General objGeneral = new General();// object of class General

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("CreateStatusType"));
			selenium.waitForPageToLoad(gstrTimeOut);

			// Wait for an element to present
			strErrorMsg = objGeneral.CheckForElements(selenium,
					propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"));

			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Create " + strStatusTypeValue
								+ " Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is  displayed");

						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypName"),
								statTypeName);
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypDefn"),
								strStatTypDefn);

						selenium
								.click(propElementDetails
										.getProperty("StatusType.CreateStatType.Expire"));
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Hours"),
										"label=" + strExpHr);
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Mins"),
										"label=" + strExpMn);

						if (blnSav) {
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/"
												+ "table/tbody/tr/td[2][text()='"
												+ statTypeName + "']"));

								log4j.info("Status type " + statTypeName
										+ " is created and is listed in the "
										+ "'Status Type List' screen. ");

							} catch (AssertionError Ae) {

								log4j
										.info("Status type "
												+ statTypeName
												+ " is created and is NOT listed in the "
												+ "'Status Type List' screen. ");

								strErrorMsg = "Status type "
										+ statTypeName
										+ " is created and is NOT listed in the "
										+ "'Status Type List' screen. " + Ae;
							}

						}

					} catch (AssertionError Ae) {
						strErrorMsg = "Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae;
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("selectStatusTypesAndFilMandFlds function failed" + e);
			strErrorMsg = "selectStatusTypesAndFilMandFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/*******************************************************************************************
	' Description: verifying a error message for creating status types
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 02/08/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String varErrorMsgInCreateST(Selenium selenium) throws Exception {
		String lStrReason = "";
		propElementDetails = objelementProp.ElementId_FilePath();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertTrue(selenium
						.isTextPresent("Each role that may update this status type must also be able to view it."));
				log4j
						.info("''Each role that may update this status type must also be able to view it' warning message is displayed in 'Create Number Status Type' screen. ");
			} catch (AssertionError Ae) {
				log4j
						.info("''Each role that may update this status type must also be able to view it' warning message is NOT displayed in 'Create Number Status Type' screen. ");
				lStrReason = lStrReason
						+ "; "
						+ "'Each role that may update this status type must also be able to view it' warning message is NOT displayed in 'Create Number Status Type' screen. ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/***************************************************************
	 'Description :Select standard status type while creating ST
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :20-July-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String selectStandardSTInCreateST(Selenium selenium,
			String strStandardST) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try{
			selenium.select("name=standardStatusTypeID", "label="
					+ strStandardST);
			log4j.info("Standard status type selected is: "+strStandardST);
			}
			catch(AssertionError Ae)
			{
				log4j.info("Standard status type "+strStandardST+" is not present in the dropdown");
				strReason="Standard status type "+strStandardST+" is not present in the dropdown";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusTypes " + e;
		}
		return strReason;
	}
	

	/***************************************************************
	 'Description :Select standard status type while creating ST
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :20-July-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	
	public String UpdateStatusOFMultiST(Selenium selenium, String strStatType,
			String strStatus, String strStatReason) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// select status
			selenium
					.click("//div[@class='multiStatus']/div/span/label[text()='"
							+ strStatus
							+ "']/preceding-sibling::input[@type='radio']");
			Thread.sleep(10000);
			// select status reason
			selenium
					.click("//div[@class='multiStatus']/div/span/label[text()='"
							+ strStatus
							+ "']/parent::span/following-sibling::div/div/label[contains(text(),'"
							+ strStatReason + "')]/preceding-sibling::input");

			// save
			selenium.click(propElementDetails
					.getProperty("View.UpdateStatus.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='viewContainer']/table/thead/tr/th[4][text()='Comment']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='viewContainer']/table/tbody/tr/td[4][text()='"
								+ strStatReason + "']"));
				log4j.info("Status is updated where '" + strStatReason
						+ "' is displayed under the 'Comments' section");
			} catch (AssertionError ae) {
				log4j.info("Status is updated where '" + strStatReason
						+ "' is NOT displayed under the 'Comments' section");
				strReason = "Status is updated where '" + strStatReason
						+ "' is NOT displayed under the 'Comments' section";
			}

		} catch (Exception e) {
			log4j.info("UpdateStatusOFMultiST function failed. " + e);
			strReason = "UpdateStatusOFMultiST function failed . " + e;
		}
		return strReason;
	}
	/***************************************************************
	 'Description :Select standard status type while creating ST
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :20-July-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	
	public String UpdateStatusOFMultiSTWithComment(Selenium selenium, String strStatTypeVal,
			String strStatus, String strStatReason,String strComment) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
				// select status
			    selenium
						.click("//div[@class='multiStatus']/div/span/label[text()='"
								+ strStatus + "']/preceding-sibling::input[@type='radio']");
			    Thread.sleep(10000);
				// select status reason
				selenium
						.click("//div[@class='multiStatus']/div/span/label[text()='"
								+ strStatus
								+ "']/parent::span/following-sibling::div/div/label[contains(text(),'"
								+ strStatReason
								+ "')]/preceding-sibling::input");
				
				selenium.type("id=comment_"+strStatTypeVal+"", strComment);

				// save
				selenium.click(propElementDetails
						.getProperty("View.UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			
		} catch (Exception e) {
			log4j
					.info("UpdateStatusOFMultiST function failed. "
							+ e);
			strReason = "UpdateStatusOFMultiST function failed . "
				+ e;
		}
		return strReason;
	}
	
	
	
	/***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields are filled
	'Precondition	:None
	'Arguments		:selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	'				 blnSav
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/
	
	public String selectStatusTypesAndFilMandFldsWithStdSt(Selenium selenium,
			String strStatusTypeValue, String statTypeName, String strStatTypDefn,
			String strStdLabel,boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage	
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
		try {
			
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");			
			selenium.click(propElementDetails
					.getProperty("CreateStatusType"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);
					try {
						assertEquals("Create "+strStatusTypeValue+" Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
								.info("Create "+strStatusTypeValue+" Status Type page is  displayed");
						
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypName"),
								statTypeName);
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypDefn"),
								strStatTypDefn);
						selenium.select("name=standardStatusTypeID", "label="+strStdLabel);

						if (blnSav) {
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if(strStatusTypeValue.equals("Multi")){
								selenium.click(propElementDetails
										.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);
								
							}
							
							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/"
												+ "table/tbody/tr/td[2][text()='"
												+ statTypeName + "']"));

								log4j.info("Status type " + statTypeName
										+ " is created and is listed in the "
										+ "'Status Type List' screen. ");

							} catch (AssertionError Ae) {

								log4j
										.info("Status type "
												+ statTypeName
												+ " is created and is NOT listed in the "
												+ "'Status Type List' screen. ");

								strErrorMsg = "Status type "
										+ statTypeName
										+ " is created and is NOT listed in the "
										+ "'Status Type List' screen. " + Ae;
							}

						}

					} catch (AssertionError Ae) {
						strErrorMsg = "Create "+strStatusTypeValue+" Status Type page is NOT displayed"
								+ Ae;
						log4j
								.info("Create "+strStatusTypeValue+" Status Type page is NOT displayed"
										+ Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("selectStatusTypesAndFilMandFlds function failed" + e);
			strErrorMsg = "selectStatusTypesAndFilMandFlds function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields are filled
	'Precondition	:None
	'Arguments		:selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	'				 blnSav
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/
	
	public String selStatusTypesAndCreatePrivateST(Selenium selenium,
			String strStatusTypeValue, String statTypeName, String strStatTypDefn,
			boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage	
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
		try {
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateStatusType")));
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
					.getProperty("CreateStatusType"));

			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Select Status Type", selenium
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
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try {

							assertEquals("Create "+strStatusTypeValue+" Status Type", selenium
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
						assertEquals("Create "+strStatusTypeValue+" Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
								.info("Create "+strStatusTypeValue+" Status Type page is  displayed");
						
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypName"),
								statTypeName);
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypDefn"),
								strStatTypDefn);

						selenium
								.click(propElementDetails
										.getProperty("StatusType.CreateStatType.StatusTypeVisib.Private"));

						if (blnSav) {
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if(strStatusTypeValue.equals("Multi")){
								intCnt=0;
								do{
									try {

										assertTrue(selenium
												.isElementPresent(propElementDetails
														.getProperty("CreateStatusType.ReturnToSTList")));
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
										.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);
								
							}
							
							intCnt=0;
							do{
								try {

									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/"
													+ "table/tbody/tr/td[2][text()='"
													+ statTypeName + "']"));
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
												+ "table/tbody/tr/td[2][text()='"
												+ statTypeName + "']"));

								log4j.info("Status type " + statTypeName
										+ " is created and is listed in the "
										+ "'Status Type List' screen. ");

							} catch (AssertionError Ae) {

								log4j
										.info("Status type "
												+ statTypeName
												+ " is created and is NOT listed in the "
												+ "'Status Type List' screen. ");

								strErrorMsg = "Status type "
										+ statTypeName
										+ " is created and is NOT listed in the "
										+ "'Status Type List' screen. " + Ae;
							}

						}

					} catch (AssertionError Ae) {
						strErrorMsg = "Create "+strStatusTypeValue+" Status Type page is NOT displayed"
								+ Ae;
						log4j
								.info("Create "+strStatusTypeValue+" Status Type page is NOT displayed"
										+ Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("selectStatusTypesAndFilMandFlds function failed" + e);
			strErrorMsg = "selectStatusTypesAndFilMandFlds function failed" + e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields are filled fo private status types
	'Precondition	:None
	'Arguments		:selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	'				 blnSav
	'Returns		:String
	'Date	 		:31-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'31-Aug-2012                               <Name>
	************************************************************************/
	
	public String createPrivateSTWitMandFlds(Selenium selenium,
			String strStatusTypeValue, String statTypeName,
			String strStatTypDefn, boolean blnSav) throws Exception {

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
					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateStatusType")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				}
				catch(Exception e){
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<40);
			
			selenium.click(propElementDetails.getProperty("CreateStatusType"));

			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("", strErrorMsg);

				try {
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					do {
						try {
							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("CreateStatusType.StatTypName")));
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCnt++;
						} catch (Exception e) {
							Thread.sleep(1000);
							intCnt++;
						}
					} while (intCnt < 40);

					try {
						assertEquals("Create " + strStatusTypeValue
								+ " Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is  displayed");

						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypName"),
								statTypeName);
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypDefn"),
								strStatTypDefn);

						selenium
								.click(propElementDetails
										.getProperty("StatusType.CreateStatType.StatusTypeVisib.Private"));

						if (blnSav) {
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {

								intCnt = 0;
								do {
									try {
										assertTrue(selenium
												.isElementPresent(propElementDetails
														.getProperty("CreateStatusType.ReturnToSTList")));
										break;
									} catch (AssertionError Ae) {
										Thread.sleep(1000);
										intCnt++;
									} catch (Exception e) {
										Thread.sleep(1000);
										intCnt++;
									}
								} while (intCnt < 40);
							

							selenium
									.click(propElementDetails
											.getProperty("CreateStatusType.ReturnToSTList"));
							selenium.waitForPageToLoad(gstrTimeOut);
							}

							intCnt = 0;
							do {
								try {
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/"
													+ "table/tbody/tr/td[2][text()='"
													+ statTypeName + "']"));
									break;
								} catch (AssertionError Ae) {
									Thread.sleep(1000);
									intCnt++;
								} catch (Exception e) {
									Thread.sleep(1000);
									intCnt++;
								}
							} while (intCnt < 40);

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/"
												+ "table/tbody/tr/td[2][text()='"
												+ statTypeName + "']"));

								log4j.info("Status type " + statTypeName
										+ " is created and is listed in the "
										+ "'Status Type List' screen. ");

							} catch (AssertionError Ae) {

								log4j
										.info("Status type "
												+ statTypeName
												+ " is created and is NOT listed in the "
												+ "'Status Type List' screen. ");

								strErrorMsg = "Status type "
										+ statTypeName
										+ " is created and is NOT listed in the "
										+ "'Status Type List' screen. " + Ae;
							}

						}

					} catch (AssertionError Ae) {
						strErrorMsg = "Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae;
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("createPrivateSTWitMandFlds function failed" + e);
			strErrorMsg = "createPrivateSTWitMandFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields are filled fo private status types
	'Precondition	:None
	'Arguments		:selenium,strStatusTypeValue,statTypeName,strStatTypDefn,
	'				 blnSav
	'Returns		:String
	'Date	 		:31-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'31-Aug-2012                               <Name>
	************************************************************************/
	
	public String createEventSTWitMandFlds(Selenium selenium,
			String strStatusTypeValue, String statTypeName,
			String strStatTypDefn, boolean blnSav) throws Exception {

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
				try {

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateStatusType")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			
			selenium.click(propElementDetails.getProperty("CreateStatusType"));

			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("", strErrorMsg);

				intCnt=0;
				do{
					try {

						assertEquals("Select Status Type", selenium
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
					assertEquals("Select Status Type", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Select Status Type page is displayed");

					selenium.select(propElementDetails
							.getProperty("CreateStatusType.SelStat.StatValue"),
							"label=" + strStatusTypeValue + "");

					selenium.click(propElementDetails
							.getProperty("CreateStatusType.SelStat.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					do {
						try {
							assertEquals("Create " + strStatusTypeValue
									+ " Status Type", selenium
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
					} while (intCnt < 40);

					try {
						assertEquals("Create " + strStatusTypeValue
								+ " Status Type", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is  displayed");

						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypName"),
								statTypeName);
						selenium.type(propElementDetails
								.getProperty("CreateStatusType.StatTypDefn"),
								strStatTypDefn);

						selenium
								.click(propElementDetails
										.getProperty("StatusType.CreateStatType.EventOnly"));

						if (blnSav) {
							selenium.click(propElementDetails
									.getProperty("Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {

								intCnt = 0;
								do {
									try {
										assertTrue(selenium
												.isElementPresent(propElementDetails
														.getProperty("CreateStatusType.ReturnToSTList")));
										break;
									} catch (AssertionError Ae) {
										Thread.sleep(1000);
										intCnt++;
									} catch (Exception e) {
										Thread.sleep(1000);
										intCnt++;
									}
								} while (intCnt < 60);
								selenium
										.click(propElementDetails
												.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);
							}

							intCnt = 0;
							do {
								try {
									assertTrue(selenium
											.isElementPresent("//div[@id='mainContainer']/"
													+ "table/tbody/tr/td[2][text()='"
													+ statTypeName + "']"));

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
										.isElementPresent("//div[@id='mainContainer']/"
												+ "table/tbody/tr/td[2][text()='"
												+ statTypeName + "']"));

								log4j.info("Status type " + statTypeName
										+ " is created and is listed in the "
										+ "'Status Type List' screen. ");

							} catch (AssertionError Ae) {

								log4j
										.info("Status type "
												+ statTypeName
												+ " is created and is NOT listed in the "
												+ "'Status Type List' screen. ");

								strErrorMsg = "Status type "
										+ statTypeName
										+ " is created and is NOT listed in the "
										+ "'Status Type List' screen. " + Ae;
							}

						}

					} catch (AssertionError Ae) {
						strErrorMsg = "Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae;
						log4j.info("Create " + strStatusTypeValue
								+ " Status Type page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Select Status Type page is NOT displayed"
							+ Ae;
					log4j.info("Select Status Type page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = strErrorMsg + Ae;
				log4j.info(strErrorMsg + Ae);
			}
		} catch (Exception e) {
			log4j.info("createEventSTWitMandFlds function failed" + e);
			strErrorMsg = "createEventSTWitMandFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	 'Description :FILL  mandatory fields  and save the status
	 'Precondition :None
	 'Arguments  :selenium,strStatusType,strStatusColor, blnSav
	 'Returns  :String
	 'Date    :01-Oct-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '01-Oct-2012                               <Name>
	 ************************************************************************/
	 
	public String fillMandFieldsAndSavStatusOfMST(Selenium selenium,
			String strMultiStatTypeName, String strStatusName,
			String strStatTypeColor, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusName"), strStatusName);
			selenium.select(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusColourCode"), "label="
					+ strStatTypeColor);

			if (blnSav) {

				selenium.click(propElementDetails.getProperty("CreateStatusType.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

					selenium.click(propElementDetails.getProperty("CreateStatusType.ReturnToSTList"));
					selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]"
									+ "/tbody/tr/td[2][text()='"
									+ strMultiStatTypeName
									+ "']"
									+ "/parent::tr/td[8]/font[text()='"
									+ strStatusName + "']"));

					log4j.info("Status is  Listed  in the corresponding"
							+ " multistatus type page");
				} catch (AssertionError Ae) {
					strErrorMsg = "Status is  NOT Listed  in the corresponding"
							+ " multistatus type page" + Ae;
					log4j.info("Status is  NOT Listed  in the corresponding"
							+ " multistatus type page" + Ae);
					strErrorMsg="";
				}
			}

		} catch (Exception e) {
			log4j.info("fillMandFieldsAndSavStatusOfMST function failed" + e);
			strErrorMsg = "fillMandFieldsAndSavStatusOfMST function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	 'Description :Verify select status type  page is displayed and 
	 '             mandatory fields are filled
	 'Arguments   :selenium,strStatusTypeValue,statTypeName,strStatTypDefn,blnSav
	 'Returns     :String
	 'Date        :12-April-2012
	 'Author      :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '12-April-2012                               <Name>
	 ************************************************************************/
	 
	public String creatSTWthnMultiST(Selenium selenium,
			String strMultiStatTypeName, String strStatusName,
			String strDefinition, String strStatusTypeValue,
			String strStatTypeColor, boolean blnRegion, String strRegionValue,boolean blnReasonn,
			boolean blnComments, boolean blnSav) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Status Type List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Status Type List page is displayed");

				selenium
						.click("//div[@id='mainContainer']//table/tbody/tr/td[2]"
								+ "[text()='"
								+ strMultiStatTypeName
								+ "']/parent::tr/td[1]"
								+ "/a[text()='statuses']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals(
							"Status List for " + strMultiStatTypeName + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status Type List for " + strMultiStatTypeName
							+ "page is displayed");

					selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatus.Link"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Create New Status", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Create New Status page is displayed");

						selenium
								.type("css=input[name=\"statusName\"]", strStatusName);
						selenium.select("css=select[name=\"colorCode\"]",
								"label=" + strStatTypeColor);

						selenium.type("css=textarea[name=\"description\"]",
								strDefinition);

						if (blnRegion) {
							if (selenium
									.isChecked("css=input[name='status_reason_id'][value='"
											+ strRegionValue + "']")) {

							} else {
								selenium
										.click("css=input[name='status_reason_id'][value='"
												+ strRegionValue + "']");
							}
						} else {
							if (selenium
									.isChecked("css=input[name='status_reason_id'][value='"
											+ strRegionValue + "']") == false) {

							} else {
								selenium
										.click("css=input[name='status_reason_id'][value='"
												+ strRegionValue + "']");
							}
						}
						
						//Is reason required
						if(blnReasonn)
						{
							if (selenium.isChecked(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusReasonReq")) == false) {
								selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusReasonReq"));
							}							
						}
						else
						{
							if (selenium.isChecked(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusReasonReq"))) {
								selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusReasonReq"));
							}
						}
						// comments mandatory button
						if (blnComments) {

							if (selenium
									.isChecked(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusCommentReq")) == false
									&& selenium
											.isChecked(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusCommentOptional"))) {
								selenium
										.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusCommentReq"));
							}

						}

						if (blnSav) {

							selenium.click(propElementDetails.getProperty("CreateStatusType.Save"));
							selenium.waitForPageToLoad(gstrTimeOut);

							if (strStatusTypeValue.equals("Multi")) {
								selenium
										.click(propElementDetails.getProperty("CreateStatusType.ReturnToSTList"));
								selenium.waitForPageToLoad(gstrTimeOut);
							}

							try {
								assertTrue(selenium
										.isElementPresent("//div[@id='mainContainer']/table[2]"
												+ "/tbody/tr/td[2][text()='"
												+ strMultiStatTypeName
												+ "']"
												+ "/parent::tr/td[8]/font[text()='"
												+ strStatusName + "']"));

								log4j
										.info("Status is  Listed  in the corresponding"
												+ " multistatus type page");
							} catch (AssertionError Ae) {
								strErrorMsg = "Status is  NOT Listed  in the corresponding"
										+ " multistatus type page" + Ae;
								log4j
										.info("Status is  COT Listed  in the corresponding"
												+ " multistatus type page" + Ae);
							}

						}

					} catch (AssertionError Ae) {

						strErrorMsg = "Create New Status page is NOT displayed"
								+ Ae;
						log4j.info("Create New Status page is NOT displayed"
								+ Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Status Type List for "
							+ strMultiStatTypeName + " page is NOT displayed"
							+ Ae;
					log4j.info("Status Type List for " + strMultiStatTypeName
							+ " page is NOT displayed" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Status Type List page is NOT displayed" + Ae;
				log4j.info("Status Type List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("createSTWithinMultiTypeST function failed" + e);
			strErrorMsg = "createSTWithinMultiTypeST function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:check appropriate values are displayed in respective columns for particular status type in Status Type List screen
	'Precondition	:None
	'Arguments		:selenium,strHeader,statTypeName,strData,strColIndex
	'				
	'Returns		:String
	'Date	 		:29-Jan-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                              		<Name>
	************************************************************************/
	
	public String checkDataInStatusTypeListNew(Selenium selenium,
			String strHeader, String statTypeName, String strData,
			String strColIndex) {
		rdExcel = new ReadData();

		String strReason = "";

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			General objGeneral = new General();

			try {

				assertEquals("Status Type List", objGeneral.seleniumGetText(
						selenium,
						propElementDetails.getProperty("Header.Text"), 160));
				log4j.info("Status Type List screen is displayed");
				try {

					// check column headers
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/thead/tr/th["
									+ strColIndex
									+ "][text()='"
									+ strHeader
									+ "']")
							|| objGeneral.seleniumGetText(
									selenium,
									"//div[@id='mainContainer']/table[2]/thead/tr/th["
											+ strColIndex + "]/a", 160).equals(
									strHeader));

					log4j.info("Column Header " + strHeader
							+ "is displayed in Status Type list");

					// check appropriate value displayed for particular status
					// type in all columns
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
										+ statTypeName + "']"));

						String str = objGeneral.seleniumGetText(selenium,
								"//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
										+ statTypeName + "']/parent::tr/td["
										+ strColIndex + "]", 160);
						assertEquals(strData, str);

						log4j
								.info("Status type "
										+ statTypeName
										+ " is listed in the status type list screen where appropriate value is displayed as "
										+ strData);

					} catch (AssertionError Ae) {
						log4j
								.info("Status type "
										+ statTypeName
										+ " is NOT listed in the status type list screen where appropriate value is displayed as "
										+ strData);
						strReason = "Status type "
								+ statTypeName
								+ " is listed NOT in the status type list screen where appropriate value is displayed as "
								+ strData;
					}

				} catch (AssertionError Ae) {
					log4j.info("Column Header " + strHeader
							+ " is NOT displayed in Status Type list");
					strReason = "Column Header " + strHeader
							+ " is NOT displayed in Status Type list";
				}
			} catch (AssertionError Ae) {
				log4j.info("Status Type List screen is NOT displayed");
				strReason = "Status Type List screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info("checkDataInStatusTypeList function failed" + e);
			strReason = "checkDataInStatusTypeList function failed"
					+ e.toString();
		}
		return strReason;
	}
	
	 /***********************************************************************
	 'Description :provideShiftTimeForStatusTypes
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :31-Jan-2013
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '31-Jan-2013                               <Name>
	 ************************************************************************/

	public String provideShiftTimeForStatusTypes(Selenium selenium,
			String strHour, String strMin) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("StatusType.CreateStatType.ShiftTime1"));
			selenium.select(propElementDetails
					.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
					strHour);
			selenium.select(propElementDetails
					.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
					strMin);

		} catch (Exception e) {
			log4j.info("provideShiftTimeForStatusTypes function failed" + e);
			strErrorMsg = "provideShiftTimeForStatusTypes function failed" + e;
		}
		return strErrorMsg;
	}
	//start//VerifySTInStatList//
	/*******************************************************************************************
	' Description :Save the status type page and verify status type is displayed
	' Precondition:N/A 
	' Arguments   :selenium, blnSave
	' Returns     :String 
	' Date        :25/04/2013
	' Author      :QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String VerifySTInStatList(Selenium selenium, String strSTName,
			boolean blnSave, boolean blnST) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnSave) {
				selenium.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			if (blnST) {
				try {
					assertTrue(selenium
							.isElementPresent("css=table[summary='Status Types']"
									+ ">tbody>tr>td:nth-child(2):contains('"
									+ strSTName + "')"));
					log4j.info(strSTName + " listed in the status type list");

				} catch (AssertionError Ae) {
					log4j.info(strSTName
							+ " is NOT listed in the status type list" + Ae);
					strErrorMsg = strSTName
							+ " is NOT listed in the status type list" + Ae;
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=table[summary='Status Types']"
									+ ">tbody>tr>td:nth-child(2):contains('"
									+ strSTName + "')"));
					log4j.info(strSTName + "is NOT listed in the status type list");

				} catch (AssertionError Ae) {
					log4j.info(strSTName
							+ " is listed in the status type list" + Ae);
					strErrorMsg = strSTName
							+ " is listed in the status type list" + Ae;
				}
			}
		} catch (Exception e) {
			log4j.info("navStatusTypList function failed" + e);
			strErrorMsg = "navStatusTypList function failed" + e;
		}
		return strErrorMsg;
	}
	//end//VerifySTInStatList//
	
	//start//VerifySTDisableOrActive//
	/*******************************************************************************************
	' Description: Check Status Type in list weather it is disabeled or active
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 25/04/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String VerifySTDisableOrActive(Selenium selenium, String strSTName,
			boolean blnSTActive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnSTActive) {
				try {
					assertTrue(selenium
							.isElementPresent("css=table[summary='Status Types']"
									+ ">tbody>tr>td:nth-child(2):contains('"
									+ strSTName + "')+td:contains('Active')"));
					log4j.info("for "+strSTName + "'Active' is displayed under the 'Active' column.");

				} catch (AssertionError Ae) {
					log4j.info("for "+strSTName
							+ " 'Active' is NOT listed in the status type list" + Ae);
					strErrorMsg ="for "+ strSTName
							+ "'Active' is NOT listed in the status type list" + Ae;
				}
			} else {
				try {
					assertTrue(selenium
							.isElementPresent("css=table[summary='Status Types']"
									+ ">tbody>tr>td:nth-child(2):contains('"
									+ strSTName + "')+td:contains('Disabled')"));
					log4j.info("for "+strSTName
							+ "'Disabled' is displayed under the 'Active' column.");

				} catch (AssertionError Ae) {
					log4j.info("for "+strSTName + " 'Disabled' is NOT displayed under the 'Active' column."
							+ Ae);
					strErrorMsg ="for " +strSTName
							+ " 'Disabled' is NOT displayed under the 'Active' column." + Ae;
				}
			}
		} catch (Exception e) {
			log4j.info("navStatusTypList function failed" + e);
			strErrorMsg = "navStatusTypList function failed" + e;
		}
		return strErrorMsg;
	}
	//end//VerifySTDisableOrActive//
	
	
	
	
	
	//start//VerifySTAllFieldsInStatList//
	/*******************************************************************************************
	' Description: Verfiy all Fields in Status type list page
	' Precondition: N/A 
	' Arguments: selenium, strSTName, strType, strEventOnly, strStandardST, strDesc
	' Returns: String 
	' Date: 30/05/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String VerifySTAllFieldsInStatList(Selenium selenium,
			String strSTName, String strType, String strEventOnly,
			String strStandardST, String strDesc) throws Exception {

		String lStrReason = "";// variable to store error mesage
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
						.isElementPresent("css=table[summary='Status Types']"
								+ ">tbody>tr>td:nth-child(2):contains('"
								+ strSTName + "')"));
				log4j.info(strSTName + " listed in the status type list");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//table[@summary='Status Types']"
								+ "/thead/tr/th[1][text()='Action']/ancestor"
								+ "::table/tbody/tr/td[2][text()='" + strSTName
								+ "']/"
								+ "preceding-sibling::td/a[text()='edit']"));
				log4j.info("EDIT is displayed under 'Action' column");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//table[@summary='Status Types']"
								+ "/thead/tr/th[3]/a[text()='Type']"
								+ "/ancestor::table/tbody/tr/td[2]"
								+ "[text()='" + strSTName
								+ "']/following-sibling::" + "td[1][text()='"
								+ strType + "']"));
				log4j.info(strType + " is displayed under 'Type' column");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//table[@summary='Status Types']"
								+ "/thead/tr/th[4]/a[text()='Event�Only?']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ strSTName + "']"
								+ "/following-sibling::td[2][text()='"
								+ strEventOnly + "']"));

				log4j.info(strEventOnly
						+ " is displayed under 'Event Only' column");

				intNum++;
				assertEquals(selenium
						.getText("//table[@summary='Status Types']"
								+ "/thead/tr/th[5]/a[text()='Standard�Type']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ strSTName + "']"
								+ "/following-sibling::td[3]"),strStandardST);

				log4j.info(strStandardST
						+ " is displayed under 'Standard status type' column");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//table[@summary='Status Types']"
								+ "/thead/tr/th[7]/a[text()='Description']"
								+ "/ancestor::table/tbody/tr/td[2][text()='"
								+ strSTName + "']"
								+ "/following-sibling::td[5][text()='"
								+ strDesc + "']"));

				log4j
						.info(strDesc
								+ " is displayed under 'Description' column");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info(strSTName
							+ " NOT listed in the status type list");
					lStrReason = strSTName
							+ " NOT listed in the status type list";
					break;

				case 2:
					log4j.info("EDIT is NOT displayed under 'Action' column");
					lStrReason = "EDIT is NOT displayed under 'Action' column";
					break;

				case 3:
					log4j.info(strType
							+ " is NOT displayed under 'Type' column");
					lStrReason = strType
							+ " is NOT displayed under 'Type' column";
					break;

				case 4:
					log4j.info(strEventOnly
							+ " is NOT displayed under 'Event Only' column");
					lStrReason = strEventOnly
							+ " is NOT displayed under 'Event Only' column";
					break;

				case 5:
					log4j
							.info(strStandardST
									+ " is NOT displayed under 'Standard status type' column");
					lStrReason = strStandardST
							+ " is NOT displayed under 'Standard status type' column";
					break;

				case 6:
					log4j.info(strDesc
							+ " is NOT displayed under 'Description' column");
					lStrReason = strDesc
							+ " is NOT displayed under 'Description' column";
					break;

				}

			}

		} catch (Exception e) {
			log4j.info("VerifySTAllFieldsInStatList function failed" + e);
			lStrReason = "StatusTypes.VerifySTAllFieldsInStatList failed to complete due to " +lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	//end//VerifySTAllFieldsInStatList//
	
	//start//navToNEDOCHelpCenterScreen//
	/***********************************************************************
	 'Description :navToNEDOCHelpCenterScreen
	 'Precondition :None
	 'Arguments  :selenium,
	 'Returns  :String
	 'Date    :3-June-2013
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ************************************************************************/

	public String navToNEDOCHelpCenterScreen(Selenium selenium)
			throws Exception {

		String lStrReason = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click("link=NEDOCS Help Document");
			//selenium.waitForPageToLoad(gstrTimeOut);

			selenium.waitForPopUp("help", gstrTimeOut);
			selenium.selectWindow("name=help");

			try {
				assertTrue(selenium
						.isTextPresent("Saturation Scoring"));

				log4j.info("Help Center window is opened");

			} catch (AssertionError Ae) {

				lStrReason = "Help Center window is NOT opened";
				log4j.info("Help Center window is NOT opened");
			}

		} catch (Exception e) {
			log4j.info("navToStatusList function failed" + e);
			lStrReason = "StatusTypes.navToNEDOCHelpCenterScreen failed to complete due to " +lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	
	//end//navToNEDOCHelpCenterScreen//
	//start//selSectionForStatusType//
	/***********************************************************************
	'Description	:Select status Type in edit Resource Page
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/

	public String selSectionForStatusType(Selenium selenium, String strSection,
			boolean blnsave) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
				
			//select Section
			selenium.select("css=select[name=groupListSelect]", "label="
					+ strSection);
			
			if (blnsave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Type List",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Status Type List page is displayed");

				} catch (AssertionError Ae) {

					strReason = "Status Type List page is NOT displayed" + Ae;
					log4j.info("Status Type List page is NOT displayed" + Ae);
				}
			}
		} catch (Exception e) {
			log4j.info("selSectionForStatusType function failed" + e);
			strReason = "selSectionForStatusType function failed" + e;
		}
		return strReason;
	}
	//end//selSectionForStatusType//
	
	//start//verSectionInStatusTypeTypeDropDown//
	/********************************************************************
	' Description : Verify RT is not present in ResTypeDropDown.
    ' Precondition: N/A 
    ' Arguments   : selenium, strRTLable, strRTValue
    ' Returns     : String 
    ' Date        : 11/07/2013
    ' Author      : QSG 
    '--------------------------------------------------------------------
    ' Modified Date: 
    ' Modified By: 
    *********************************************************************/

	public String verSectionInStatusTypeTypeDropDown(Selenium selenium,
			String strSection, String strSecValue, boolean blnSection,
			String strSTtype,String strPageName)
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
							.isElementPresent("//table/tbody/tr/td[@class='emsLabel'][text()='Sections:']"));
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
						.isElementPresent("//table/tbody/tr/td[@class='emsLabel'][text()='Sections:']"));
				log4j.info("Section field is displayed");
			} catch (AssertionError Ae) {

				strReason = "Section field is NOT displayed" + Ae;
				log4j.info("Section field is NOT displayed" + Ae);
			}
			
			if (blnSection) {
				try {
					assertTrue(selenium.isElementPresent("css=option[value=\""
							+ strSecValue + "\"]"));
					log4j.info(strSection
							+ "'is listed under "+strPageName+" "+strSTtype+" status Type page.");
				} catch (Exception e) {
					log4j.info(strSection
							+ "'is NOT listed under "+strPageName+" "+strSTtype+" status Type page.");
					strReason = strSection
							+ "'is NOT listed under "+strPageName+" "+strSTtype+" status Type page.";
				}
			} else {
				try {
					assertFalse(selenium.isElementPresent("css=option[value=\""
							+ strSection + "\"]"));
					log4j.info(strSection
							+ "'is NOT listed under "+strPageName+" "+strSTtype+" status Type page.");
				} catch (Exception e) {
					log4j.info(strSection
							+ "'is listed under "+strPageName+" "+strSTtype+" status Type page.");
					strReason = strSection
							+ "'is listed under "+strPageName+" "+strSTtype+" status Type page.";
				}
			}

		} catch (Exception e) {
			log4j.info("verSectionInStatusTypeTypeDropDown function failed" + e);
			strReason = "verSectionInStatusTypeTypeDropDown function failed" + e;
		}

		return strReason;
	}
	//end//verSectionInStatusTypeTypeDropDown//
	
	//start//savAndVerifySectionInStatPage//
	/***********************************************************************
	'Description	:Verify status type and all other filds are listed in 
	'				Statues list pagee is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:23-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/

	public String savAndVerifySectionInStatPage(Selenium selenium,
			String strSTName, String strSection, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnSave) {
				
				int intCnt = 0;
				do {
					try {

						selenium.click(propElementDetails
								.getProperty("CreateStatusType.Save"));
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
						.getProperty("CreateStatusType.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {

					assertEquals("Status Type List",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Status Type List is   displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Status Type List is NOT displayed" + Ae;
					log4j.info("Status Type List is NOT  displayed");

				}
			}
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
									+ strSTName
									+ "']/parent::tr/td[6][text()='"
									+ strSection + "']"));
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
						.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
								+ strSTName
								+ "']/parent::tr/td[6][text()='"
								+ strSection + "']"));
				log4j.info(strSection + " is listed in the status type list page under section coloumn.");

			} catch (AssertionError Ae) {
				log4j.info(strSection + " NOT listed in the status type list page under section coloumn."
						+ Ae);
				strErrorMsg = strSection
						+ " NOT listed in the status type list page under section coloumn." + Ae;
			}

		} catch (Exception e) {
			log4j.info("savAndVerifySectionInStatPage function failed" + e);
			strErrorMsg = "savAndVerifySectionInStatPage function failed" + e;
		}
		return strErrorMsg;
	}
	//end//savAndVerifySectionInStatPage//
	
	//start//selSectionForStatusType//
		/***********************************************************************
		'Description	:Select status Type in edit Resource Page
		'Precondition	:None
		'Arguments		:selenium, strRT
		'Returns		:String
		'Date	 		:29/07/2013
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		' <Date>                                 <Name>
		************************************************************************/

		public String VerifySelectedValueForSection(Selenium selenium, String strSection) throws Exception {
			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals(strSection,
						selenium.getSelectedLabel("css=select[name=groupListSelect]"));
				if(strSection.equals("")){
					log4j.info("Blank is selected in Section dropdown");
				}else{
					log4j.info(strSection + " is selected in Section dropdown");
				}
			} catch (AssertionError Ae) {
				strReason = strSection + " is Not selected in Section dropdown";
				if(strSection.equals("")){
					log4j.info("Blank is NOT selected in Section dropdown");
				}else{
					log4j.info(strSection + " is NOT selected in Section dropdown");
				}
			}

		} catch (Exception e) {
			log4j.info("selSectionForStatusType function failed" + e);
			strReason = "selSectionForStatusType function failed" + e;
		}
		return strReason;
		}
		//end//selSectionForStatusType//
		
	//start//vfyAllRoleDeseleted//
	/***********************************************************************
	'Description	:Function to verify that all the status types are disabled
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:09/08/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                                 <Name>
	************************************************************************/

	public String vfyAllRoleDeseleted(Selenium selenium) throws Exception {
			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
		
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td/input[@class='checkbox'][@value='roleView'][@name='SELECT_ALL'][@disabled='']"));
				log4j.info("All the status types are disabled");
				
			} catch (AssertionError Ae) {
				log4j.info("All the status types are not disabled");
				strReason= "All the status types are not disabled";
				
			}

		} catch (Exception e) {
			log4j.info("vfyAllRoleDeseleted function failed" + e);
			strReason = "vfyAllRoleDeseleted function failed" + e;
		}
		return strReason;
		}
		//end//vfyAllRoleDeseleted//
	
	//start//vfyAllRoleSeleted//
	/***********************************************************************
	'Description	:Function to verify that all the status types are enabled
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:09/08/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                                 <Name>
	************************************************************************/

	public String vfyAllRoleSeleted(Selenium selenium) throws Exception {
			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
		
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td/input[@class='checkbox'][@value='roleView'][@name='SELECT_ALL']"));
				log4j.info("All the status types are disabled");
				
			} catch (AssertionError Ae) {
				log4j.info("All the status types are not disabled");
				strReason= "All the status types are not disabled";
				
			}

		} catch (Exception e) {
			log4j.info("vfyAllRoleSeleted function failed" + e);
			strReason = "vfyAllRoleSeleted function failed" + e;
		}
		return strReason;
		}
		//end//vfyAllRoleSeleted//
	
	//start//vfyAllRoleSeleted//
	/***********************************************************************
	'Description	:Function to verify that all the status types are enabled
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:09/08/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                                 <Name>
	************************************************************************/

	public String ClkOnCreatNewStatAndFilMandFlds(Selenium selenium,String strStatusName
			,String strStatTypeColor,String strDefinition,boolean blnSav) throws Exception {
			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				selenium.click(propElementDetails
						.getProperty("StatusType.MultiST_CreateNewStatus.Link"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Create New Status", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Create New Status page is displayed");

					selenium
							.type(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusName"), strStatusName);
					selenium.select(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusColourCode"),
							"label=" + strStatTypeColor);

					selenium.type(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusDesc"),
							strDefinition);

					
					// comments mandatory button
					
					if (blnSav) {

						selenium.click(propElementDetails.getProperty("StatusType.MultiST_CreateNewStatusSave"));
						selenium.waitForPageToLoad(gstrTimeOut);
					}
				}
				catch(AssertionError Ae)
				{
					log4j.info("Create New Status page is not displayed");				
					strReason = "Create New Status page is not displayed" + Ae;
				}
			}catch(AssertionError Ae)
			{
				log4j.info("Create New Status link is not displayed");				
				strReason = "Create New Status link is not displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info("vfyAllRoleSeleted function failed" + e);
			strReason = "vfyAllRoleSeleted function failed" + e;
		}
		return strReason;
		}
		//end//vfyAllRoleSeleted//
	
	//start//vfyErrMsgForNonNumericNumStatTypes//
	/***********************************************************************
	'Description	:Function to error message for blank number status types
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:09/08/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                                 <Name>
	************************************************************************/

	public String vfyErrMsgForNonNumericNumStatTypes(Selenium selenium)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			
			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Update Status page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Create Role page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}

			try {
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/div/span"),
						"The following error occurred on this page:");
				log4j.info("Error Message header is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Error Message header is not displayed" + Ae);
				strReason = "Error Message header is not displayed" + Ae;
			}

			try {
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/div/ul/li"),
						"Please enter a numerical status value.");
				log4j
						.info("Error message stating 'Please enter a numerical status value' is displayed and user is retained in 'Update Status' page.");
				log4j
				.info("Status is not updated");

			} catch (AssertionError Ae) {
				log4j
						.info("Error message stating 'Please enter a numerical status value' is not displayed and user is not retained in 'Update Status' page."
								+ Ae);
				strReason = "Error message stating 'Please enter a numerical status value' is not displayed and user is not retained in 'Update Status' page."
						+ Ae;
			}

		} catch (Exception e) {
			log4j
					.info("vfyErrMsgForNonNumericNumStatTypes function failed"
							+ e);
			strReason = "vfyErrMsgForNonNumericNumStatTypes function failed"
					+ e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:Select or deselect Event Only for status type
	'Precondition	:None
	'Arguments		:selenium,blnSelect
	'Returns		:String
	'Date	 		:01-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	************************************************************************/
	
	public String selectDeSelRequired(Selenium selenium,
			boolean blnSelect)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnSelect){
				if(selenium.isChecked("//div[@id='mainContainer']/form/table/tbody/tr/td[2]/input[@name='required'][@value='Y'][@type='checkbox']")==false)
					selenium
							.click("//div[@id='mainContainer']/form/table/tbody/tr/td[2]/input[@name='required'][@value='Y'][@type='checkbox']");
			}else{
				if(selenium.isChecked("//div[@id='mainContainer']/form/table/tbody/tr/td[2]/input[@name='required'][@value='Y'][@type='checkbox']"))
					selenium
							.click("//div[@id='mainContainer']/form/table/tbody/tr/td[2]/input[@name='required'][@value='Y'][@type='checkbox']");
			}

		} catch (Exception e) {
			log4j.info("selectDeSelEventOnly function failed" + e);
			strErrorMsg = "selectDeSelEventOnly function failed" + e;
		}
		return strErrorMsg;
	}
	//start//vfyErrMsgForNonNumericNumStatTypes//
	/***********************************************************************
	'Description	:Function to error message for blank number status types
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:09/08/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                                 <Name>
	************************************************************************/

	public String vfyErrMsgForBlankTextStatTypes(Selenium selenium, String strStatusTypeName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			
			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Update Status page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Update Status page is NOT displayed" + Ae;
				log4j.info("Update Status page is NOT displayed" + Ae);
			}

			try {
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/div/span"),
						"The following error occurred on this page:");
				log4j.info("Error Message header is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Error Message header is not displayed" + Ae);
				strReason = "Error Message header is not displayed" + Ae;
			}

			try {
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/div/ul/li"),"Please enter a non-blank value for '"+strStatusTypeName+"'.");
				log4j
						.info("	An appropriate error is displayed,status should not be updated with blank value. ");
				
			} catch (AssertionError Ae) {
				log4j
						.info("An appropriate error is not displayed,status should not be updated with blank value. "
								+ Ae);
				strReason = "An appropriate error is not displayed,status should not be updated with blank value. "
						+ Ae;
			}

		} catch (Exception e) {
			log4j
					.info("vfyErrMsgForBlankTextStatTypes function failed"
							+ e);
			strReason = "vfyErrMsgForBlankTextStatTypes function failed"
					+ e;
		}
		return strReason;
	}
	
	//start//chkRolSelOrNotInEditSTPage//
	/***********************************************************************
	'Description	:Function to error message for blank number status types
	'Precondition	:None
	'Arguments		:selenium, strRT
	'Returns		:String
	'Date	 		:09/08/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                                 <Name>
	************************************************************************/
	public String chkRolSelOrNotInEditSTPage(Selenium selenium,
			String strRoleName, String strRoleValue, boolean blnUpdateRights,
			boolean blnViewRigths) throws Exception {
		String strErrorMsg = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnViewRigths) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='roleView'][value='"
									+ strRoleValue + "']"));
					log4j
							.info("Role "
									+ strRoleName
									+ " is selected in View Rights of 'Edit Status Type' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Role "
							+ strRoleName
							+ "is NOT selected in View Rights of 'Edit Status Type' page.";
					log4j
							.info("Role "
									+ strRoleName
									+ " is selected in View Rights of 'Edit Status Type' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='roleView'][value='"
									+ strRoleValue + "']"));
					log4j
							.info("Role "
									+ strRoleName
									+ " is NOT selected in View Rights of 'Edit Status Type' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Role "
							+ strRoleName
							+ "is selected in View Rights of 'Edit Status Type' page.";
					log4j
							.info("Role "
									+ strRoleName
									+ " is selected in View Rights of 'Edit Status Type' page.");
				}
			}

			if (blnUpdateRights) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='roleUpdate'][value='"
									+ strRoleValue + "']"));
					log4j
							.info("Role "
									+ strRoleName
									+ " is selected in Update Rights of 'Edit Status Type' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Role "
							+ strRoleName
							+ "is NOT selected in Update Rights of 'Edit Status Type' page.";
					log4j
							.info("Role "
									+ strRoleName
									+ " is selected in Update Rights of 'Edit Status Type' page.");
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='roleUpdate'][value='"
									+ strRoleValue + "']"));
					log4j
							.info("Role "
									+ strRoleName
									+ " is NOT selected in Update Rights of 'Edit Status Type' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Role "
							+ strRoleName
							+ "is selected in Update Rights of 'Edit Status Type' page.";
					log4j
							.info("Role "
									+ strRoleName
									+ " is selected in Update Rights of 'Edit Status Type' page.");
				}

			}

		} catch (Exception e) {
			log4j.info("chkRolSelOrNotInEditSTPage function failed" + e);
			strErrorMsg = "chkRolSelOrNotInEditSTPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	//end//chkRolSelOrNotInEditSTPage//
	/***********************************************************************
	'Description	:Verify select status type  page is displayed and 
	'				 mandatory fields are filled
	'Arguments		:selenium,strStatusTypeValue
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	************************************************************************/

	public String VarDifftStatusTypesInDropDown(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
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
							.getProperty("CreateStatusType")));

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
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateStatusType")));
				assertTrue(selenium
						.isElementPresent("css=input[value='Standard Status Type Labels']"));

				log4j.info("'Create New Status Type' and 'Standard Status Type Labels' "
						+ "buttons are available at top left. ");
			} catch (AssertionError ae) {
				log4j.info("'Create New Status Type' and 'Standard Status Type Labels' buttons"
						+ " are available at top left. ");
				strErrorMsg = "'Create New Status Type' and 'Standard Status Type Labels' "
						+ "buttons are available at top left. ";
			}
		
		} catch (Exception e) {
			log4j.info("VarDifftStatusTypesInDropDown function failed" + e);
			strErrorMsg = "VarDifftStatusTypesInDropDown function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /*******************************************************
	 'Description  :Verify Role is Present or not for a user
	 'Arguments    :selenium,strRoleValue
	 'Returns      :String
	 'Date         :28-May-2012
	 'Author       :QSG
	 '-------------------------------------------------------
	 'Modified Date                            Modified By
	 '28-May-2012                               <Name>
	 ********************************************************/

	public String chkRolePresentOrNotInStatPage(Selenium selenium, boolean blnRole,
			String strRoleValue, String strRolesName, String strStatPage)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			} catch (Exception e) {
			}

			if (blnRole) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='userRoleID'][value='"
									+ strRoleValue + "']"));
					log4j.info("The" + strRolesName + " is present in the "
							+ strStatPage + " StatusType page");
				} catch (AssertionError Ae) {
					strErrorMsg = "The" + strRolesName
							+ " is NOT present in the " + strStatPage
							+ " StatusType page";
					log4j.info("The" + strRolesName + " is NOT present in the "
							+ strStatPage + " StatusType page");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='userRoleID'][value='"
									+ strRoleValue + "']"));
					log4j.info("The" + strRolesName + " is NOT present in the "
							+ strStatPage + " StatusType page");
				} catch (AssertionError Ae) {
					strErrorMsg = "The" + strRolesName + " is present in the "
							+ strStatPage + " StatusType page";
					log4j.info("The" + strRolesName + " is present in the "
							+ strStatPage + " StatusType page");
				}
			}
		} catch (Exception e) {
			log4j.info("chkRolePresentOrNotInStatPage function failed" + e);
			strErrorMsg = "chkRolePresentOrNotInStatPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************
	'Description	:Navigating to Select Status Type page
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	******************************************************/

	public String navToSelectStatusTypePage(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click(propElementDetails.getProperty("CreateStatusType"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Select Status Type",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Select Status Type page is displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = "Select Status Type page is NOT displayed" + Ae;
				log4j.info("Select Status Type page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToSelectStatusTypePage function failed" + e);
			strErrorMsg = "navToSelectStatusTypePage function failed" + e;
		}
		return strErrorMsg;
	}
	/*****************************************************
	'Description	:Navigating to Select Status Type page
	'Arguments		:selenium, strStatusTypeValue
	'Returns		:String
	'Date	 		:12-April-2012
	'Author			:QSG
	'-----------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	******************************************************/

	public String navToCreateStatusTypePage(Selenium selenium,String strStatusTypeValue) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.select(propElementDetails
					.getProperty("CreateStatusType.SelStat.StatValue"),
					"label=" + strStatusTypeValue + "");

			selenium.click(propElementDetails
					.getProperty("CreateStatusType.SelStat.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create " + strStatusTypeValue
						+ " Status Type", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Create " + strStatusTypeValue
						+ " Status Type page is  displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = "Create " + strStatusTypeValue
						+ " Status Type page is NOT displayed" + Ae;
				log4j.info("Create " + strStatusTypeValue
						+ " Status Type page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToCreateStatusTypePage function failed" + e);
			strErrorMsg = "navToCreateStatusTypePage function failed" + e;
		}
		return strErrorMsg;
	}
}