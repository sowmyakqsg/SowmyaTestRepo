package com.qsgsoft.EMResource.shared;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/************************************************************************************
' Description :This class includes common functions for creating users
' Precondition:
' Date		  :2-March-2012
' Author	  :QSG
'------------------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'***********************************************************************************/
public class CreateUsers{
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.shared.CreateUsers");

	public Properties propEnvDetails,pathProps;
	Properties propElementDetails;
	Properties propElementAutoItDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objPaths_Properties=new Paths_Properties();
	Paths_Properties objAP = new Paths_Properties();
	
	public String gstrTimeOut;

	ReadData rdExcel;
	
	/*******************************************************************
	'Description	:Verify user list page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	********************************************************************/
	
	public String navUserListPge(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");
			
			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
			}catch(Exception e){
				
			}
			
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
					.getProperty("SetUP.UsersLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Users List", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
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
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Users List' page is displayed ");
			} catch (AssertionError Ae) {

				strErrorMsg = "'Users List' page is NOT displayed " + Ae;
				log4j.info("'Users List' page is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("navUserListPge function failed" + e);
			strErrorMsg = "navUserListPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /*******************************************************************
	 'Description :Verify Role is selected or not for a user
	 'Precondition :None
	 'Arguments  :selenium,strRoleValue
	 'Returns  :String
	 'Date    :28-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '28-May-2012                               <Name>
	 ********************************************************************/

	 public String chkRoleSelectedOrNot(Selenium selenium,boolean blnRole,
			 String strRoleValue,String strRolesName) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  try {

	   rdExcel = new ReadData();
	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
		}catch(Exception e){
			
		}
		
	   if(blnRole){
	   try {
			assertTrue(selenium.isChecked("css=input[name='userRoleID'][value='"
				      + strRoleValue + "']"));
			log4j.info("Check box corresponding to region "+strRolesName+ " remains checked");
		} catch (AssertionError Ae) {
			strErrorMsg = " Check box corresponding to region "+strRolesName+ "is unchecked";
			log4j.info("Check box corresponding to region "+strRolesName+ "is unchecked");
		}
	   }else{
		try {

			assertFalse(selenium.isChecked("css=input[name='userRoleID'][value='"
				      + strRoleValue + "']"));;
			log4j.info("Check box corresponding to region "+strRolesName+ "is unchecked");
		} catch (AssertionError Ae) {
			strErrorMsg = " Check box corresponding to region "+strRolesName+ "is checked";
			log4j.info(" Check box corresponding to region "+strRolesName+ "is checked");
		}
	   }
	  } catch (Exception e) {
	   log4j.info("chkRoleSelectedOrNot function failed" + e);
	   strErrorMsg = "chkRoleSelectedOrNot function failed" + e;
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

	public String chkRolePresentOrNot(Selenium selenium, boolean blnRole,
			String strRoleValue, String strRolesName, String strUserPage)
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
							+ strUserPage + " user page");
				} catch (AssertionError Ae) {
					strErrorMsg = "The" + strRolesName
							+ " is NOT present in the " + strUserPage
							+ " user page";
					log4j.info("The" + strRolesName + " is NOT present in the "
							+ strUserPage + " user page");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='userRoleID'][value='"
									+ strRoleValue + "']"));
					log4j.info("The" + strRolesName + " is NOT present in the "
							+ strUserPage + " user page");
				} catch (AssertionError Ae) {
					strErrorMsg = "The" + strRolesName + " is present in the "
							+ strUserPage + " user page";
					log4j.info("The" + strRolesName + " is present in the "
							+ strUserPage + " user page");
				}
			}
		} catch (Exception e) {
			log4j.info("chkRolePresentOrNot function failed" + e);
			strErrorMsg = "chkRolePresentOrNot function failed" + e;
		}
		return strErrorMsg;
	}

	 /*******************************************************************
		'Description	:Check the multiple region list for user
		'Precondition	:None
		'Arguments		:selenium,strRegn
		'Returns		:String
		'Date	 		:4-April-2012
		'Author			:QSG
		'------------------------------------------------------------------
		'Modified Date                            Modified By
		'4-April-2012                               <Name>
		********************************************************************/
		
		public String chkRegionListForUser(Selenium selenium, String strRegionvalue,
				boolean blnRegion,String strRegionName) throws Exception {

			String strErrorMsg = "";// variable to store error message

			try {

				rdExcel = new ReadData();
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				try{
					selenium.selectWindow("");
					
				}catch(Exception e){
					
				}
				
				if(blnRegion){
					
					selenium.selectWindow("");
				try {
					assertTrue(selenium.isElementPresent("css=option[value='"+ strRegionvalue+"']"));
					log4j.info("Region "+strRegionName+  "is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Region "+strRegionName+  "is  displayed.";
					log4j.info("Region "+strRegionName+  "is  displayed.");
				}
			   }else{
				   try {
						assertFalse(selenium.isElementPresent("css=option[value='"+ strRegionvalue+"']"));
						log4j.info("Region "+strRegionName+  "is not displayed.");
					} catch (AssertionError Ae) {
						strErrorMsg = "Region "+strRegionName+  "is  displayed.";
						log4j.info("Region "+strRegionName+  "is  displayed.");
					}
			   }
			} catch (Exception e) {
				log4j.info("chkRegionListForUser function failed" + e);
				strErrorMsg = "chkRegionListForUser function failed" + e;
			}
			return strErrorMsg;
		}
		
		/*******************************************************************
		'Description	:Navigating to select region page
		'Precondition	:None
		'Arguments		:selenium,strRegn
		'Returns		:String
		'Date	 		:4-April-2012
		'Author			:QSG
		'------------------------------------------------------------------
		'Modified Date                            Modified By
		'4-April-2012                               <Name>
		********************************************************************/
		
		public String navToSelectRegionPage(Selenium selenium) throws Exception {

			String strErrorMsg = "";// variable to store error message

			try {

				rdExcel = new ReadData();
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				try {
					try{
						selenium.selectWindow("");
						
					}catch(Exception e){
						
					}
					
					assertEquals("Select Region", selenium
							.getText(propElementDetails.getProperty("Header.Text")));

					log4j.info("'Select Region' screen is displayed.");

				} catch (AssertionError Ae) {

					strErrorMsg = "'Select Region' screen is NOT displayed.";
					log4j.info("'Select Region' screen is NOT displayed.");
				}
				
			} catch (Exception e) {
				log4j.info("navToSelectRegionPage function failed" + e);
				strErrorMsg = "navToSelectRegionPage function failed" + e;
			}
			return strErrorMsg;
		}
		
	 /*******************************************************************
	 'Description :Verify Resource is selected or not for a user
	 'Precondition :None
	 'Arguments  :selenium,strRoleValue
	 'Returns  :String
	 'Date    :28-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '28-May-2012                               <Name>
	 ********************************************************************/

	 public String chkRSSelectedOrNotForUsr(Selenium selenium,boolean blnResource,
			 String strResVal,String strResource) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  try {

	   rdExcel = new ReadData();
	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   try{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
		}catch(Exception e){
			
		}
		
	   if(blnResource){
	   try {
			assertTrue(selenium.isChecked("css=input[name='association'][value='"+strResVal+"']"));
			log4j.info("Check box corresponding to region "+strResource+ " remains checked");
		} catch (AssertionError Ae) {
			strErrorMsg = " Check box corresponding to region "+strResource+ "is unchecked";
			log4j.info("Check box corresponding to region "+strResource+ "is unchecked");
		}
	   }else{
		try {

			assertFalse(selenium.isChecked("css=input[name='association'][value='"+strResVal+"']"));
			log4j.info("Check box corresponding to region "+strResource+ "is unchecked");
		} catch (AssertionError Ae) {
			strErrorMsg = " Check box corresponding to region "+strResource+ "is checked";
			log4j.info(" Check box corresponding to region "+strResource+ "is checked");
		}
	   }
	  } catch (Exception e) {
	   log4j.info("chkRSSelectedOrNotForUsr function failed" + e);
	   strErrorMsg = "chkRSSelectedOrNotForUsr function failed" + e;
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

	public String chkerrorMsgEditusrRegionPage(Selenium selenium,
			boolean blnSave) throws Exception {

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

			if (blnSave) {
				selenium.click(propElementDetails
						.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isTextPresent("You must select at least one Region."));
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
						.isTextPresent("You must select at least one Region."));
				log4j
						.info("'Error message'You must select at least one Region is displayed.");
				log4j.info("User is not saved.");
			} catch (AssertionError Ae) {

				strErrorMsg = "'Error message'You must select at least one Region is NOT displayed."
						+ Ae;
				log4j
						.info("'Error message'You must select at least one Region is NOT displayed.");
				log4j.info("User is saved.");
			}

		} catch (Exception e) {
			log4j.info("chkerrorMsgEditusrRegionPage function failed" + e);
			strErrorMsg = "chkerrorMsgEditusrRegionPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	 'Description :Verify edit user Region page is displayed
	 'Precondition :None
	 'Arguments  :selenium,stUserName
	 'Returns  :String
	 'Date    :25-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '25-May-2012                               <Name>
	 ********************************************************************/
	 
	public String navEditUserRegions(Selenium selenium, String strUserName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
			}catch(Exception e){
				
			}
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
							+ "[text()='" + strUserName
							+ "']/parent::tr/td[1]/a[text()='Regions']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			

			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2]"
					+ "[text()='" + strUserName
					+ "']/parent::tr/td[1]/a[text()='Regions']");
			selenium.waitForPageToLoad(gstrTimeOut);

			
			intCnt=0;
			do{
				try{
					assertEquals("Edit User Regions", selenium
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
				assertEquals("Edit User Regions", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("'Edit User Regions' screen is displayed  ");
			} catch (AssertionError Ae) {

				strErrorMsg = "'Edit User Regions' screen is NOT displayed "
						+ Ae;
				log4j.info("'Edit User Regions' screen is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("navEditUserRegions function failed" + e);
			strErrorMsg = "navEditUserRegions function failed" + e;
		}
		return strErrorMsg;
	}
	 
	/*******************************************************************
	 'Description :select and deselect region
	 'Precondition :None
	 'Arguments  :selenium,strRoleValue
	 'Returns  :String
	 'Date    :28-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '28-May-2012                               <Name>
	 ********************************************************************/

	public String slectAndDeselectRegion(Selenium selenium,
			String strRegionvalue, boolean blnselectRegion, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
			}catch(Exception e){
				
			}

			if (blnselectRegion) {
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='regionID'][value='"
								+ strRegionvalue + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked("css=input[name='regionID'][value='"
						+ strRegionvalue + "']")) {

				} else {
					selenium.click("css=input[name='regionID'][value='"
							+ strRegionvalue + "']");
				}
			} else {
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='regionID'][value='"
								+ strRegionvalue + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				if (selenium.isChecked("css=input[name='regionID'][value='"
						+ strRegionvalue + "']") == false) {

				} else {
					selenium.click("css=input[name='regionID'][value='"
							+ strRegionvalue + "']");
				}
			}
			if (blnSave) {
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			

		} catch (Exception e) {
			log4j.info("slectAndDeselectRegion function failed" + e);
			strErrorMsg = "slectAndDeselectRegion function failed" + e;
		}
		return strErrorMsg;
	}
	 
	/*********************************************************
	'Description	:Verify user in the user list
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************/
	
	public String navToCreateUserPage(Selenium selenium) throws Exception {

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

			selenium.click(propElementDetails.getProperty("CreateNewUsrLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			
			int intCnt=0;
			do{
				try{
					assertEquals("Create New User", selenium.getText(propElementDetails
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
				assertEquals("Create New User", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Create New User' screen is displayed");

			} catch (AssertionError Ae) {
				log4j.info("'Create New User' screen is not displayed");
				strErrorMsg = "'Create New User' screen is not displayed";
			}

		} catch (Exception e) {
			log4j.info("saveUser function failed" + e);
			strErrorMsg = "saveUser function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description: Canel user and navigating to userlist page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 28/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String cancelAndNavToUsrListPage(Selenium selenium) throws Exception
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
			selenium.click(propElementDetails
					.getProperty("Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Users List", selenium.getText(propElementDetails
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
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Users List' Screen is displayed ");
			} catch (AssertionError Ae) {

				lStrReason = "'Users List' Screen  is NOT displayed " + Ae;
				log4j.info("'Users List'  Screen  is NOT displayed " + Ae);
			}

		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description : Checking IPFILTER  key for region in user list
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String CheckForIPRangeLogin(Selenium selenium,
			boolean blnIPRange) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
			}catch(Exception e){
				
			}
			
			if(blnIPRange)
			{
				try
				{
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.IPRange")));
				log4j.info("'IP range for Login' field is displayed.");
				} catch (AssertionError Ae) {
					log4j.info("'IP range for Login' field is NOT displayed.");
					lStrReason = "'IP range for Login' field is NOT displayed.";
				}			
			}
			else{
				try
				{
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.IPRange")));
				assertFalse(selenium.isVisible(propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.IPRange")));
				log4j.info("'IP range for Login' field is NOT  displayed.");
				} catch (AssertionError Ae) {
					log4j.info("'IP range for Login' field is displayed.");
					lStrReason = "'IP range for Login' field is displayed.";
				}

			}
		} catch (Exception e) {
			log4j.info("selectAndDeselectIPFILTERSave function failed");
			lStrReason = "selectAndDeselectIPFILTERSave function failed";
		}

		return lStrReason;
	}

	/*******************************************************************************************
	' Description : Providing IPFILTER  key for region in user list
	' Precondition: N/A 
	' Arguments   : selenium,strTitle,strDescriptn,strHoverText,blnActive 
	' Returns     : String 
	' Date        : 02/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String validDataForIPRangeLogin(Selenium selenium,
			String strRangeOfIp) throws Exception {
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

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.AdvOptn.IPRange"), strRangeOfIp);

			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Users List", selenium
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
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Users List' Screen is displayed ");
			} catch (AssertionError Ae) {

				lStrReason = "'Users List' Screen  is NOT displayed " + Ae;
				log4j.info("'Users List'  Screen  is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("selectAndDeselectIPFILTERSave function failed");
			lStrReason = "selectAndDeselectIPFILTERSave function failed";
		}

		return lStrReason;
	}

	  /*********************************************************
	'Description	:Verify user  FullName in the user list Screen
	'Precondition	:None
	'Arguments		:selenium,strUserFullName
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************/
	
	public String vrfyUserFullName(Selenium selenium, String strFullName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[3]"
								+ "[text()='" + strFullName + "']"));
				log4j
						.info("Updated data FullName"
								+ strFullName
								+ "is displayed for the user in the 'Users List'screen.");

			} catch (AssertionError Ae) {
				strErrorMsg = "Updated data "
						+ strFullName
						+ "is displayed for the user in the 'Users List'screen.";
				log4j
						.info("Updated data "
								+ strFullName
								+ "is NOT displayed for the user in the 'Users List'screen.");
			}

		} catch (Exception e) {
			log4j.info("savVrfyUser function failed" + e);
			strErrorMsg = "savVrfyUser function failed" + e;
		}
		return strErrorMsg;
	}

  /*********************************************************
	'Description	:Verify user fields user list Screen
	'Precondition	:None
	'Arguments		:selenium,
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************/

	public String vrfyUserFields(Selenium selenium, String strFullName,
			String strFirstname, String strmiddlename, String strLastName,
			String strOrg, String StrPhoneNo, String strPremail,
			String strEmail, String strPager, String strVieID) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
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
				log4j
						.info("On 'Edit User' screen, updated data is displayed in all the fields for the user.  ");
			} catch (AssertionError Ae) {
				log4j
						.info("On 'Edit User' screen, updated data is NOT displayed in all the fields for the user. ");
				strErrorMsg = "On 'Edit User' screen, updated data is NOT displayed in all the fields for the user. ";
			}

		} catch (Exception e) {
			log4j.info("vrfyUserFields function failed" + e);
			strErrorMsg = "vrfyUserFields function failed" + e;
		}
		return strErrorMsg;
	}


	/*******************************************************************************************
	' Description: save user and navigating to user list page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 28/06/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String saveAndNavToUsrListPage(Selenium selenium) throws Exception
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
			 selenium.click(propElementDetails
						.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try{
					assertEquals("Users List", selenium.getText(propElementDetails
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
				
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Users List' Screen is displayed ");
			} catch (AssertionError Ae) {

				lStrReason = "'Users List' Screen  is NOT displayed " + Ae;
				log4j.info("'Users List'  Screen  is NOT displayed " + Ae);
			}

		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/***********************
	  'Description  :Verify user other data fields are filled
	  'Precondition :None
	  'Arguments    :selenium,strFirstName,strMiddleName,strLastName,
	  '              strOrganization,strPhoneNo,strPrimaryEMail,
	                 strEMail,strPagerValue,strAdminComments
	  'Returns      :String
	  'Date         :3-May-2012
	  'Author       :QSG
	  '----------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                               <Name>
	  *****************************************************************/
	 public String nonMandatoryUsrProfileFlds(Selenium selenium,
	   String strFirstName, String strMiddleName, String strLastName,
	   String strOrganization, String strPhoneNo, String strPrimaryEMail,
	   String strEMail, String strPagerValue, String strAdminComments)
	   throws Exception {

	  String strErrorMsg = "";

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Other Fields

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.firstName"), strFirstName);

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.middleName"), strMiddleName);

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.lastName"), strLastName);

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.OrgName"), strOrganization);

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.PhoneNo"), strPhoneNo);

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.primaryEMail"), strPrimaryEMail);

			selenium.type(propElementDetails.getProperty("CreateNewUsr.eMail"),
					strEMail);

			selenium.type(propElementDetails.getProperty("CreateNewUsr.Pager"),
					strPagerValue);

			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.adminComments"),
					strAdminComments);

		} catch (Exception e) {
			log4j.info("nonMandatoryUsrProfileFlds function failed" + e);
			strErrorMsg = "nonMandatoryUsrProfileFlds function failed" + e;
		}
		return strErrorMsg;
	}
	/*********************************************************
	'Description	:Verify user in the user list
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************/
	
	public String savVrfyUser(Selenium selenium, String strUserName)
	   throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  try {
	   rdExcel = new ReadData();
	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   gstrTimeOut=propEnvDetails.getProperty("TimeOut");


			selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (selenium.isVisible(propElementDetails
					.getProperty("SetUP.UserList.ManyUsrsLnk"))) {
				selenium.click(propElementDetails
						.getProperty("SetUP.UserList.ManyUsrsLnk"));
				// Thread.sleep(10000);

				int intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element"))
							&& intCnt < 60) {
						intCnt++;
						Thread.sleep(1000);
					}

				} catch (Exception e) {
					log4j.info(e);
				}
				log4j.info(intCnt);

				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
									+ "[text()='" + strUserName + "']"));

					log4j.info("User " + strUserName
							+ " is listed in the 'Users List' "
							+ "screen under Setup ");

				} catch (AssertionError Ae) {
					strErrorMsg = "User " + strUserName
							+ " is NOT listed in the 'Users List'"
							+ " screen under Setup " + Ae;
					log4j.info("User U1 is NOT listed in the 'Users List'"
							+ " screen under Setup " + Ae);
				}

			} else {
				try {

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
									+ "[text()='" + strUserName + "']"));

					log4j.info("User " + strUserName
							+ " is listed in the 'Users List' "
							+ "screen under Setup ");

				} catch (AssertionError Ae) {
					strErrorMsg = "User " + strUserName
							+ " is NOT listed in the 'Users List'"
							+ " screen under Setup " + Ae;
					log4j.info("User U1 is NOT listed in the 'Users List'"
							+ " screen under Setup " + Ae);
				}
			}

		} catch (Exception e) {
			log4j.info("savVrfyUser function failed" + e);
			strErrorMsg = "savVrfyUser function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*********************************************************
	'Description	:Verify user in the user list
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:27-Jul-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                              <Name>
	**********************************************************/
	
	public String verifyUsrInUserListPage(Selenium selenium,
			String strUserName,boolean blnPresent,boolean blnManyLnk)
	   throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  try {
		   rdExcel = new ReadData();
		   propEnvDetails = objReadEnvironment.readEnvironment();
		   propElementDetails = objelementProp.ElementId_FilePath();
		   gstrTimeOut=propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if(blnManyLnk){
				if (selenium.isVisible(propElementDetails
						.getProperty("SetUP.UserList.ManyUsrsLnk"))) {
					selenium.click(propElementDetails
							.getProperty("SetUP.UserList.ManyUsrsLnk"));
					// Thread.sleep(10000);

					int intCnt = 0;

					try {

						while (selenium.isVisible(propElementDetails
								.getProperty("Reloading.Element"))
								&& intCnt < 60) {
							intCnt++;
							Thread.sleep(1000);
						}

					} catch (Exception e) {
						log4j.info(e);
					}
					log4j.info(intCnt);
				}
			}
			
			if(blnPresent){
				try {
					assertEquals("Users List", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td"
									+ "[text()='" + strUserName + "']"));
	
					log4j.info("User " + strUserName
							+ " is listed in the 'Users List' "
							+ "screen");
	
				} catch (AssertionError Ae) {
					strErrorMsg = "User " + strUserName
							+ " is NOT listed in the 'Users List'"
							+ "" + Ae;
					log4j.info("User "+ strUserName+" is NOT listed in the 'Users List'"
							+ "" + Ae);
				}

			}else{
				try {
					assertEquals("Users List", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td"
									+ "[text()='" + strUserName + "']"));
	
					log4j.info("User " + strUserName
							+ " is not listed in the 'Users List' "
							+ "screen");
	
				} catch (AssertionError Ae) {
					strErrorMsg = "User " + strUserName
							+ " is listed in the 'Users List'"
							+ "" + Ae;
					log4j.info("User "+ strUserName+" is NOT listed in the 'Users List'"
							+ "" + Ae);
				}
			}
	  } catch (Exception e) {
			log4j.info("verifyUsrInUserListPage function failed" + e);
			strErrorMsg = "verifyUsrInUserListPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/**********************************************************************
	'Description	:Verify user mandatory data fields are filled
	'Precondition	:None
	'Arguments		:selenium,strUserName,strInitPwd,strConfirmPwd,
	'				 strUsrFulName
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************************/
	
	public String fillUsrMandatoryFlds(Selenium selenium, String strUserName,
			String strInitPwd, String strConfirmPwd, String strUsrFulName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			} catch (Exception e) {

			}

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewUsrLink")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click(propElementDetails.getProperty("CreateNewUsrLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					selenium.type(propElementDetails
							.getProperty("CreateNewUsr.UserName"), strUserName);
					break;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// Mandatory data fields
			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.UserName"), strUserName);
			selenium.type(
					propElementDetails.getProperty("CreateNewUsr.NewPwd"),
					strInitPwd);
			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.ConfrmPwd"), strConfirmPwd);
			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.FulUsrName"), strUsrFulName);

		} catch (Exception e) {
			log4j.info("fillUsrMandatoryFlds function failed" + e);
			strErrorMsg = "fillUsrMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/**********************************************************************
	'Description	:Fill only user full name
	'Precondition	:None
	'Arguments		:selenium,strUsrFulName
	'Returns		:String
	'Date	 		:13-09-2012
	'Author			:QSG
	'----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<date>			                              <Name>
	**********************************************************************/
	
	public String fillUsrFulName(Selenium selenium,String strUsrFulName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");
					
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewUsr.FulUsrName")));
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
					.getProperty("CreateNewUsr.FulUsrName"), strUsrFulName);

		} catch (Exception e) {
			log4j.info("fillUsrFulName function failed" + e);
			strErrorMsg = "fillUsrFulName function failed" + e;
		}
		return strErrorMsg;
	}
	
	/**********************************************************************
	'Description	:Fill only the mandatory fields in create user
	'Precondition	:None
	'Arguments		:selenium,strUserName,strInitPwd,strConfirmPwd,
	'				 strUsrFulName
	'Returns		:String
	'Date	 		:29-May-2012
	'Author			:QSG
	'----------------------------------------------------------------------
	'Modified Date                            Modified By
	'29-May-2012                               <Name>
	**********************************************************************/
	
	public String fillOnlyCreateUsrMandatFlds(Selenium selenium, String strUserName,
			String strInitPwd, String strConfirmPwd, String strUsrFulName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
								
			//Mandatory data fields
			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.UserName"), strUserName);
			selenium.type(
					propElementDetails.getProperty("CreateNewUsr.NewPwd"),
					strInitPwd);
			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.ConfrmPwd"), strConfirmPwd);
			selenium.type(propElementDetails
					.getProperty("CreateNewUsr.FulUsrName"), strUsrFulName);

		} catch (Exception e) {
			log4j.info("fillOnlyCreateUsrMandatFlds function failed" + e);
			strErrorMsg = "fillOnlyCreateUsrMandatFlds function failed" + e;
		}
		return strErrorMsg;
	}
	/**********************************************************************
	'Description	:Verify select Resource Rights
	'Precondition	:None
	'Arguments		:selenium,strResource,blnAssocWith,blnUpdStat,blnRunReport
	'				 blnViewRes
	'				 strUsrFulName
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************************/
	
	public String selectResourceRights(Selenium selenium, String strResource,
			boolean blnAssocWith, boolean blnUpdStat, boolean blnRunReport,
			boolean blnViewRes) {
		String strReason = "";
		try {
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
								+ strResource + "']"));
				log4j.info("The Resource " + strResource
						+ " is displayed in Resource Rights screen");
				// Select Associated With option
				if (blnAssocWith) {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[1]/input[@name='association']") == false) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[1]/input[@name='association']");
					}
				} else {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[1]/input[@name='association']")) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[1]/input[@name='association']");
					}
				}

				// Select Update Status option
				if (blnUpdStat) {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[2]/input[@name='updateRight']") == false) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[2]/input[@name='updateRight']");
					}
				} else {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[2]/input[@name='updateRight']")) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[2]/input[@name='updateRight']");
					}
				}

				// Select Run reports option
				if (blnRunReport) {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[3]/input[@name='reportRight']") == false) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[3]/input[@name='reportRight']");
					}
				} else {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[3]/input[@name='reportRight']")) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[3]/input[@name='reportRight']");
					}
				}

				// Select View Resource option
				if (blnViewRes) {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[4]/input[@name='viewRight']") == false) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[4]/input[@name='viewRight']");
					}
				} else {
					if (selenium
							.isChecked("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource
									+ "']/parent::tr/td[4]/input[@name='viewRight']")) {
						selenium
								.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource
										+ "']/parent::tr/td[4]/input[@name='viewRight']");
					}
				}
			} catch (AssertionError ae) {
				log4j.info("The Resource " + strResource
						+ " is NOT displayed in Resource Rights screen");
				strReason = "The Resource " + strResource
						+ " is NOT displayed in Resource Rights screen";
			}
		} catch (Exception e) {
			log4j.info("selectResourceRights function failed" + e);
			strReason = "selectResourceRights function failed" + e;
		}
		return strReason;
	}

	/************************************************************
	'Description	:Verify advanced option fields are selected
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	************************************************************/
	
	public String advancedOptns(Selenium selenium, String strOptions,
			boolean blnOptions) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnOptions) {
				if (selenium.isChecked(strOptions)) {

				} else {
					selenium.click(strOptions);
				}
			} else {
				if (selenium.isChecked(strOptions)) {
					selenium.click(strOptions);
				} else {

				}
			}
			
		} catch (Exception e) {
			log4j.info("advancedOptns function failed" + e);
			strErrorMsg = "advancedOptns function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*********************************************************
	'Description	:Verify user list page is displayed
	'Precondition	:None
	'Arguments		:selenium,strRegn
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	**********************************************************/
	
	public String navRegnListPge(Selenium selenium, String strRegn)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));

			selenium.click(propElementDetails.getProperty("SetUP.RegionsLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr"
									+ "/td[2][contains(text(),'"
									+ strRegn
									+ "')]"));
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
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr"
								+ "/td[2][contains(text(),'" + strRegn + "')]"));

				log4j.info("Region list page is Displayed ");

			} catch (AssertionError Ae) {
				log4j.info("Region list page is NOT Displayed " + Ae);
				strErrorMsg = "Region list page is NOT Displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info("navRegnListPge function failed" + e);
			strErrorMsg = "navRegnListPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	/****************************************************************
	'Description	:Verify Mandatory fields of the region are edited
	'Precondition	:None
	'Arguments		:selenium,strRegnName,strTimeZone,strContactFName
	'				 strContactLName,strEmailAlrtFrq,strAudioFreq
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	****************************************************************/

	public String editRegnMandatryFlds(Selenium selenium, String strRegnName,
			String strEditRegnName, String strTimeZone, String strContactFName,
			String strContactLName, String strEmailAlrtFrq, String strAudioFreq)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		

		try {

			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
							+ strRegnName + "']/parent::tr/td[1]/a");

			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Edit Region", selenium.getText("css=h1"));
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
				assertEquals("Edit Region", selenium.getText("css=h1"));

				log4j.info("Edit Region page is Displayed ");

				if (strRegnName != "") {
					selenium.type("css=input[name='regionName']",
							strEditRegnName);
				}

				if (strTimeZone != "") {
					selenium.select("css=select[name='timeZone']", "label="
							+ strTimeZone + "");
				}

				if (strContactFName != "") {
					selenium.type("css=input[name='contactFirst']",
							strContactFName);
				}

				if (strContactLName != "") {
					selenium.type("css=input[name='contactLast']",
							strContactLName);
				}

				if (strEmailAlrtFrq != "") {
					selenium.select("css=select[name=\"nagEmailFrequency\"]",
							"label=" + strEmailAlrtFrq + "");
				}

				if (strAudioFreq != "") {
					selenium.select("css=select[name=\"audioAlertFrequency\"]",
							"label=" + strAudioFreq + "");
				}

			} catch (AssertionError Ae) {
				log4j.info("Edit Region page is NOT Displayed " + Ae);
				strErrorMsg = "Edit Region page is NOT Displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info("editRegnMandatryFlds function failed" + e);
			strErrorMsg = "editRegnMandatryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/*********************************************************
	'Description	:Verify user in the user list
	'Precondition	:None
	'Arguments		:selenium,strRegn
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	**********************************************************/
	
	public String navRegnalMapView(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try {

			selenium.mouseOver("link=View");

			selenium.click("link=Map");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Regional Map View", selenium.getText("css=h1"));
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
				assertEquals("Regional Map View", selenium.getText("css=h1"));
				log4j.info("Regional Map View is Displayed ");

			} catch (AssertionError Ae) {
				log4j.info("Regional Map View is NOT Displayed " + Ae);
				strErrorMsg = "Regional Map View is NOT Displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info("navRegnalMapView function failed" + e);
			strErrorMsg = "navRegnalMapView function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*********************************************************
	'Description	:Verify user in the user list
	'Precondition	:None
	'Arguments		:selenium,strLoginUserName,strLoginPassword,
	'				 strRegnName
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	**********************************************************/
	
	public String resetRegnName(Selenium selenium, String strLoginUserName,
			String strLoginPassword, String strEditRegnName,
			String strRegionName) throws Exception {
				
		String strFuncResult="";
		String strErrorMsg = "";// variable to store error mesage

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		General objGeneral=new General();// object of class General
		
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		
		
		try {
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
		
			// verify user login
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objCreateUsers.navRegnListPge(selenium,
						strRegionName);

			} catch (AssertionError Ae) {
				strErrorMsg = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objCreateUsers.editRegnMandatryFlds(selenium,
						strRegionName, strEditRegnName, "", "", "", "", "");
				
			} catch (AssertionError Ae) {
				strErrorMsg = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				objGeneral.CheckForElements(selenium, "//div[@id='mainContainer']/table/tbody/tr"
											+ "/td[2][contains(text(),'"
											+ strEditRegnName + "')]");
				
			} catch (AssertionError Ae) {
				strErrorMsg = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				log4j.info("Region Modified");

			} catch (AssertionError Ae) {
				strErrorMsg = strFuncResult;
			}
			
		} catch (Exception e) {
			log4j.info("resetRegnName function failed" + e);
			strErrorMsg = "resetRegnName function failed" + e;
		}
		return strErrorMsg;
	}

	
	/************************************************************
	'Description	:Verify all users the user list are displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:16-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'16-April-2012                               <Name>
	**********************************************************/
	
	public String showAllUsers(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {

			if (selenium.isVisible(propElementDetails
					.getProperty("SetUP.UserList.ManyUsrsLnk"))) {

				/*
				 * int intXpath = selenium.getXpathCount(
				 * "//table[@id='tblUsers']/tbody/tr").intValue();
				 */

				selenium.click(propElementDetails
						.getProperty("SetUP.UserList.ManyUsrsLnk"));

				int intCnt = 0;
				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element"))
							&& intCnt < 60) {
						intCnt++;
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					log4j.info(e);
				}

				log4j.info(intCnt);
				log4j.info("Link present and Users  exceeded above 300");
			}

		} catch (Exception e) {
			log4j.info("Link NOT present and Users NOT exceeded above 300" + e);
			strErrorMsg = "";
		}
		return strErrorMsg;
	}

	/************************************************************
	'Description	:Verify user is inactivated
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:19-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'19-April-2012                               <Name>
	**********************************************************/

	public String deactivateUser(Selenium selenium, String strUserName,
			String strUserFullName, boolean blnInactive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
					+ strUserName + "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.click(propElementDetails
					.getProperty("EditUser.Deactivate"));
			selenium.click(propElementDetails.getProperty("EditUser.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("ConfirmUserDeactivation.Yes")));
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

			selenium.click(propElementDetails
					.getProperty("ConfirmUserDeactivation.Yes"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("UserDeactivationComplete.ReturnUsrLst")));
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

			selenium.click(propElementDetails
					.getProperty("UserDeactivationComplete.ReturnUsrLst"));
			selenium.waitForPageToLoad(gstrTimeOut);

			if (blnInactive) {
				intCnt=0;
				do{
					try{
						assertTrue(selenium
								.isElementPresent(propElementDetails
										.getProperty("UserList.IncludeActvUsers")));
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
					if (selenium.isChecked(propElementDetails
							.getProperty("UserList.IncludeActvUsers")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserList.IncludeActvUsers"));

						intCnt = 0;

						try {

							while (selenium.isVisible(propElementDetails
									.getProperty("Reloading.Element"))
									&& intCnt < 60) {
								intCnt++;
								Thread.sleep(100);
							}

						} catch (Exception e) {
							log4j.info(e);
						}
						log4j.info(intCnt);

					}
					log4j.info("Check Box  selected");
				} catch (Exception e) {
					strErrorMsg = "Check Box NOT selected" + e;
					log4j.info("Check Box NOT selected" + e);
				}
			}

			if (blnInactive) {

				try {
					assertTrue(selenium
							.isElementPresent("//td[@class='inactive'][contains(text(),'"
									+ strUserFullName + "')]"));

					log4j.info("Users are  deactivated ");

				} catch (AssertionError Ae) {
					strErrorMsg = "User is NOT deactivated ";
					log4j.info("User is NOT deactivated " + Ae);

				}

			} else {
				try {

					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr"
									+ "/td[2][text()='" + strUserName + "']"));

					log4j.info("User is deactivated ");

				} catch (AssertionError Ae) {
					strErrorMsg = "User is NOT deactivated ";
					log4j.info("User is NOT deactivated " + Ae);

				}
			}

		} catch (Exception e) {
			log4j.info("deactivateUser fuction " + e);
			strErrorMsg = "deactivateUser fuction " + e;
		}
		return strErrorMsg;

	}
	/************************************************************
	'Description	:Verify user is activated
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:20-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'20-April-2012                               <Name>
	**********************************************************/

	public String activateUser(Selenium selenium, String strUserName,
			String strUserFullName, boolean blnInactive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			if (selenium.isChecked(propElementDetails
					.getProperty("UserList.IncludeActvUsers")) == false) {
				selenium.click(propElementDetails
						.getProperty("UserList.IncludeActvUsers"));

				int intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element"))
							&& intCnt < 60) {
						intCnt++;
						Thread.sleep(100);
					}

				} catch (Exception e) {
					log4j.info(e);
				}
			}

			try {

				assertTrue(selenium
						.isElementPresent("//td[@class='inactive'][contains(text(),'"
								+ strUserFullName + "')]"));

				log4j.info("Active and inactive users are displayed "
						+ "on the 'Users List' screen.");

			} catch (AssertionError Ae) {
				strErrorMsg = "Active and inactive users are NOT displayed"
						+ " on the 'Users List' screen. ";

			}	
			
			
			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
					+ strUserName + "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			selenium.click(propElementDetails
					.getProperty("EditUser.Activate"));
			selenium.click(propElementDetails
					.getProperty("EditUser.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
		
			
			int intCnt=0;
			do{
				try{
					assertEquals("User Activation Complete", selenium.getText("css=h1"));
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
				assertEquals("User Activation Complete", selenium.getText("css=h1"));
				log4j.info("User Activation Complete page is  displayed");
				
				try{
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("UserActivationComplete.ReturnUsrLst")));
					log4j.info("'Return to User List' button is available. ");
					
					selenium.click(propElementDetails
							.getProperty("UserActivationComplete.ReturnUsrLst"));
					selenium.waitForPageToLoad(gstrTimeOut);

					if (blnInactive) {
						
						try {
							assertTrue(selenium
									.isElementPresent("//table[@id='tblUsers']/tbody/tr"
											+ "/td[3][text()='"
											+ strUserFullName + "']"));
					

							log4j.info("Users are  activated");
							
						} catch (AssertionError Ae) {
							strErrorMsg = "Users are NOT activated";
							log4j.info("Users are NOT activated" + Ae);

						}
						
					}
					
				}catch(AssertionError Ae){
					strErrorMsg = "'Return to User List' button is NOT available. ";
					log4j.info("'Return to User List' button is NOT available. " + Ae);

				}
				
		
			}catch(AssertionError Ae){
				strErrorMsg = "User Activation Complete page is NOT displayed";
				log4j.info("User Activation Complete page is NOT displayed" + Ae);

			}
			
		} catch (Exception e) {
			log4j.info("activateUser fuction " + e);
			strErrorMsg = "activateUser fuction " + e;
		}
		return strErrorMsg;

	}
	/*****************************************************************
	'Description	:Verify password is updated for user
	'Precondition	:None
	'Arguments		:selenium,strNewPassword,strUserName
	'Returns		:String
	'Date	 		:20-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'20-April-2012                               <Name>
	*****************************************************************/
	
	public String changePassword(Selenium selenium, String strNewPassword,
			String strUserName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
					+ strUserName + "']/parent::tr/td[1]/a[text()='Password']");
			int intCnt = 0;
			do {
				try {
					assertEquals("Reset User Password", selenium.getText("css=h1"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 40);

			try {
				assertEquals("Reset User Password", selenium.getText("css=h1"));
				log4j.info("Reset User Password page is  displayed");				
				selenium.click("link=here");				
				
				
				do {
					try {

						assertEquals("Set Up Your Password",
								selenium.getText(propElementDetails
										.getProperty("SetUpPwd")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 40);
				
				try {

					assertEquals("Set Up Your Password", selenium
							.getText(propElementDetails.getProperty("SetUpPwd")));

					log4j.info("Set Up Your Password is displayed");

					selenium.type(propElementDetails
							.getProperty("SetUpPwd.NewUsrName"), strNewPassword);
					selenium.type(propElementDetails
							.getProperty("SetUpPwd.CofrmUsrName"), strNewPassword);

					selenium.click(propElementDetails
							.getProperty("SetUpPwd.Submit"));		
					
					
					do{
						try{
							assertEquals("EMResource", selenium
									.getText(propElementDetails
											.getProperty("SelectRegion.EMResource")));
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

						assertEquals("EMResource", selenium
								.getText(propElementDetails
										.getProperty("SelectRegion.EMResource")));
						log4j.info("Home page is displayed");

					} catch (AssertionError Ae) {

						strErrorMsg = "Home page is NOT displayed" + Ae;
						log4j.info("Home page is NOT displayed" + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Set Up Your Password is NOT displayed" + Ae;
					log4j.info("Set Up Your Password is NOT displayed" + Ae);
				}

				
			} catch (AssertionError Ae) {

				strErrorMsg = "Reset User Password page is NOT displayed" + Ae;
				log4j.info("Reset User Password page is NOT displayed" + Ae);
			}

			
		} catch (Exception e) {
			log4j.info("changePassword function failed" + e);
			strErrorMsg = "changePassword function failed" + e;
		}
		return strErrorMsg;
	}

	/*****************************************************************
	'Description	:navigate to edit user page
	'Precondition	:None
	'Arguments		:selenium,strUsrName
	'Returns		:String
	'Date	 		:15-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'15-May-2012                               <Name>
	*****************************************************************/
	
	public String navToEditUserPage(Selenium selenium, String strUsrName) {
		String strReason = "";
		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Click on Edit link for the user
			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
					+ strUsrName + "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Edit User", selenium
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
				assertEquals("Edit User", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Edit User page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Edit User page is NOT displayed" + Ae;
				log4j.info("Edit User page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToEditUserPage function failed" + e);
			strReason = "navToEditUserPage function failed" + e;
		}
		return strReason;
	}

	/*******************************************************************
	 'Description :Verify edit user page is displayed
	 'Precondition :None
	 'Arguments  :selenium,stUserName
	 'Returns  :String
	 'Date    :25-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '25-May-2012                               <Name>
	 ********************************************************************/
	 
	public String navEditUserPge(Selenium selenium, String strUserName,
			String strByRole, String strByResourceType, String strByUserInfo,
			String strNameFormat) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			strErrorMsg = objSearchUserByDiffCrteria.searchUserByDifCriteria(
					selenium, strUserName, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
							+ "[text()='" + strUserName
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
			

			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2]"
					+ "[text()='" + strUserName
					+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			
			intCnt=0;
			do{
				try{
					assertEquals("Edit User", selenium.getText(propElementDetails
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
				assertEquals("Edit User", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Edit User' page is displayed ");
			} catch (AssertionError Ae) {

				strErrorMsg = "'Edit User' page is NOT displayed " + Ae;
				log4j.info("'Edit User' page is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("navEditUserPge function failed" + e);
			strErrorMsg = "navEditUserPge function failed" + e;
		}
		return strErrorMsg;
	}
	 
	/*******************************************************************
	 'Description :Verify role is selected or not
	 'Precondition :None
	 'Arguments  :selenium,strRoleValue
	 'Returns  :String
	 'Date    :28-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '28-May-2012                               <Name>
	 ********************************************************************/

	public String slectAndDeselectRole(Selenium selenium, String strRoleValue,
			boolean blnselectRole) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnselectRole) {

				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("css=input[name='userRoleID'][value='"
										+ strRoleValue + "']"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				if (selenium.isChecked("css=input[name='userRoleID'][value='"
						+ strRoleValue + "']")) {

				} else {
					selenium.click("css=input[name='userRoleID'][value='"
							+ strRoleValue + "']");
				}
			} else {

				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("css=input[name='userRoleID'][value='"
										+ strRoleValue + "']"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				if (selenium.isChecked("css=input[name='userRoleID'][value='"
						+ strRoleValue + "']") == false) {

				} else {
					selenium.click("css=input[name='userRoleID'][value='"
							+ strRoleValue + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectRole function failed" + e);
			strErrorMsg = "slectAndDeselectRole function failed" + e;
		}
		return strErrorMsg;
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

	public String navToRefineVisibleST(Selenium selenium, String strResource)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on Refine link
			selenium
					.click("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
							+ strResource
							+ "']/parent::tr/td[4]/a[text()='Refine']");
			try{
				selenium.waitForPageToLoad("30000");
			}catch(Exception e){
				log4j.info("Wait for page load is working");
			}

			// Wait till pop appears
			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 60) {
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
				log4j.info("Pop up window is displayed");
				
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				
				try {
					assertEquals("Refine Visible Status Types", selenium
							.getText("css=#TB_ajaxWindowTitle"));
					log4j
							.info("Refine Visible Status Types window is displayed");
					
					
				} catch (AssertionError Ae) {
					log4j
							.info("Refine Visible Status Types window NOT displayed");
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
	
	
	/*******************************************************************************************
	' Description: Save the User and verify the error message
	' Precondition:N/A 
	' Arguments: selenium
	' Returns:String 
	' Date:29-05-2012
	' Author:QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String saveAndCheckErrMsgForUser(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isTextPresent("A user that has any of Associated With," +
									" Update Status, or Run Reports for a resource" +
									" must have View Resource."));
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
						.isTextPresent("A user that has any of Associated With," +
								" Update Status, or Run Reports for a resource must" +
								" have View Resource."));
				log4j
						.info("An appropriate error is displayed stating, 'The " +
								"following error occurred on this page: A user" +
								" that has any of Associated With, Update Status," +
								" or Run Reports for a resource must have View Resource.'");
			} catch (AssertionError ae) {
				lStrReason = "An error is NOT displayed stating, 'The following " +
						"error occurred on this page: A user that has any of " +
						"Associated With, Update Status, or Run Reports for a" +
						" resource must have View Resource.'";

			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = e.toString();
		}

		return lStrReason;
	}

	/*******************************************************************************************
	' Description: Verify that By default particular right check box is selected.
	' Precondition:N/A 
	' Arguments: selenium,strRightValue,blnResRight
	' Returns:String 
	' Date:29-05-2012
	' Author:QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyViewResourceRight(Selenium selenium,
			String strRightName, String strRightValue, boolean blnResRight)
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
			if (blnResRight) {
				if (selenium.isChecked("css=input[name='" + strRightName
						+ "'][value='" + strRightValue + "']")) {
					log4j
							.info("By default, the '"
									+ strRightValue
									+ "' checkbox is selected corresponding " +
											"to all existing resources in that region.");
				} else {
					lStrReason = "By default, the '"
							+ strRightValue
							+ "' checkbox is NOT selected corresponding to " +
									"all existing resources in that region.";
					log4j
							.info("By default, the '"
									+ strRightValue
									+ "' checkbox is NOT selected corresponding" +
											" to all existing resources in that region.");
				}
			} else {
				if (selenium.isChecked("css=input[name='" + strRightName
						+ "'][value='" + strRightValue + "']") == false) {
					log4j
							.info("By default, the '"
									+ strRightValue
									+ "' checkbox is not selected corresponding" +
											" to all existing resources in that region.");
				} else {
					lStrReason = "By default, the '"
							+ strRightValue
							+ "' checkbox is selected corresponding to all existing" +
									" resources in that region.";
					log4j
							.info("By default, the '"
									+ strRightValue
									+ "' checkbox is selected corresponding" +
											" to all existing resources in that region.");
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Select/Deselect 'Select-All' Resource Rights
	' Precondition:N/A 
	' Arguments: selenium,strRightValue,blnResRightSel
	' Returns:String 
	' Date:29-05-2012
	' Author:QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String selectAndDeselectAllResRight(Selenium selenium,
			String strRightValue, boolean blnResRightSel) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			if (blnResRightSel) {
				if (selenium.isChecked("css=input[name='SELECT_ALL'][value='"
						+ strRightValue + "']") == false)
					selenium.click("css=input[name='SELECT_ALL'][value='"
							+ strRightValue + "']");
			} else {
				if (selenium.isChecked("css=input[name='SELECT_ALL'][value='"
						+ strRightValue + "']"))
					selenium.click("css=input[name='SELECT_ALL'][value='"
							+ strRightValue + "']");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************
	 'Description :navigate Assign Users page by clicking on Users link
	 				corresponding to particular resource.
	 'Precondition :None
	 'Arguments  :selenium,strResource
	 'Returns  :String
	 'Date    :29-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ********************************************************************/

	public String navToUsersFromResourceListPage(Selenium selenium,
			String strResource) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on Users link for particular resource
			selenium
					.click("//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']/parent::tr/td[1]/a[text()='Users']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
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

				log4j.info("Assign Users to " + strResource
						+ " page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Assign Users to " + strResource
						+ " page is NOT displayed";
				log4j.info("Assign Users to " + strResource
						+ " page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("navToUsersFromResourceListPage function failed" + e);
			strErrorMsg = "navToUsersFromResourceListPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description: Click on save in Create User and Verify the error message displayed
	' Precondition: N/A 
	' Arguments: selenium, 
	' Returns: String 
	' Date: 30-05-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************//*

	public String temSaveUserAndCheckErrMsg(Selenium selenium) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("The following error occurred on this page:\nA user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource.", selenium.getText(propElementDetails
						.getProperty("CreateNewUsr.ErroMsg")));
				log4j.info("'An appropriate error is displayed stating, 'The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource.' is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'An appropriate error is displayed stating, 'The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource.' is NOT displayed");
				lStrReason = lStrReason + "; " + "An appropriate error is displayed stating, 'The following error occurred on this page: A user that has any of Associated With, Update Status, or Run Reports for a resource must have View Resource.' is NOT displayed";
			}
			log4j.info("'function pass");
		}catch(Exception e){
			log4j.info(e);
			log4j.info("'temSaveUserAndCheckErrMsg function failed");
			lStrReason = lStrReason + "; " + "temSaveUserAndCheckErrMsg function failed";
		}

		return lStrReason;
	}*/
	/**********************************************************************
	 'Description :Verify select Resource Rights
	 'Precondition :None
	 'Arguments  :selenium,strResource,blnAssocWith,blnUpdStat,blnRunReport,strRSValue
	 '     blnViewRes
	 '     strUsrFulName
	 'Returns  :String
	 'Date    :14-June-2012
	 'Author   :QSG
	 '----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '14-June-2012                               <Name>
	 **********************************************************************/
	 
	public String selectResourceRightsWitRSValues(Selenium selenium,
			String strResource, String strRSValue, boolean blnAssocWith,
			boolean blnUpdStat, boolean blnRunReport, boolean blnViewRes) {
		String strReason = "";
		try {

			// Select Associated With option
			if (blnAssocWith) {
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='association'][value='"
								+ strRSValue + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				
				if (selenium.isChecked("css=input[name='association'][value='"
						+ strRSValue + "']") == false) {
					selenium.click("css=input[name='association'][value='"
							+ strRSValue + "']");
				}
			} else {
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='association'][value='"
								+ strRSValue + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked("css=input[name='association'][value='"
						+ strRSValue + "']")) {
					selenium.click("css=input[name='association'][value='"
							+ strRSValue + "']");
				}
			}

			// Select Update Status option
			if (blnUpdStat) {
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='updateRight'][value='"
								+ strRSValue + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				
				if (selenium.isChecked("css=input[name='updateRight'][value='"
						+ strRSValue + "']") == false) {
					selenium.click("css=input[name='updateRight'][value='"
							+ strRSValue + "']");
				}
			} else {
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("css=input[name='updateRight'][value='"
								+ strRSValue + "']"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				if (selenium.isChecked("css=input[name='updateRight'][value='"
						+ strRSValue + "']")) {
					selenium.click("css=input[name='updateRight'][value='"
							+ strRSValue + "']");
				}
			}

			// Select Run reports option
			if (blnRunReport) {
				if (selenium.isChecked("css=input[name='reportRight'][value='"
						+ strRSValue + "']") == false) {
					selenium.click("css=input[name='reportRight'][value='"
							+ strRSValue + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='reportRight'][value='"
						+ strRSValue + "']")) {
					selenium.click("css=input[name='reportRight'][value='"
							+ strRSValue + "']");
				}
			}

			// Select View Resource option
			if (blnViewRes) {
				if (selenium.isChecked("css=input[name='viewRight'][value='"
						+ strRSValue + "']") == false) {
					selenium.click("css=input[name='viewRight'][value='"
							+ strRSValue + "']");
				}
			} else {
				if (selenium.isChecked("css=input[name='viewRight'][value='"
						+ strRSValue + "']")) {
					selenium.click("css=input[name='viewRight'][value='"
							+ strRSValue + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("selectResourceRightsWitRSValues function failed" + e);
			strReason = "selectResourceRightsWitRSValues function failed" + e;
		}
		return strReason;
	}
	
	/***************
	   'Description :verify error message in update status screen
	   'Precondition :None
	   'Arguments  :selenium, statustype
	   'Returns  :strReason
	   'Date    :28-May-2012
	   'Author   :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String verifyErrorMsgInRefineVisibiltyScreen(Selenium selenium,
			String strErrorMsg) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");

			try {
				assertEquals(strErrorMsg,
						selenium.getText("css=p"));
				log4j
						.info("Refine Visible Status Types screen is displayed with a message  "
								+ strErrorMsg);
			} catch (AssertionError Ae) {
				log4j
						.info("Refine Visible Status Types screen is NOT displayed with a message"
								+ strErrorMsg);

				strReason = "Refine Visible Status Types screen is NOT displayed with a message"
						+ strErrorMsg;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "verifyErrorMsgInRefineVisibiltyScreen  function Failed "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************
	   'Description :verify error message in Refine visibilty screen
	   'Precondition :None
	   'Arguments  :selenium, statustype
	   'Returns  :strReason
	   'Date    :3rd-Jan-2013
	   'Author   :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String verifyErrorMsgInRefineVisibiltyScreenNew(Selenium selenium,
			String strErrorMsg) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");

			try {
				assertEquals(strErrorMsg, selenium.getText("css=p"));
				log4j
						.info("Refine Visible Status Types screen is displayed with a message"
								+ strErrorMsg);
			} catch (AssertionError Ae) {
				log4j
						.info("Refine Visible Status Types screen is NOT displayed with a message"
								+ strErrorMsg);

				strReason = "Refine Visible Status Types screen is NOT displayed with a message"
						+ strErrorMsg;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "verifyErrorMsgInRefineVisibiltyScreenNew  function Failed "
					+ e.toString();
		}
		return strReason;
	}

	/************************************************************
	'Description	:Verify advanced option fields are present or Not gor a user
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions,strRight
	'Returns		:String
	'Date	 		:10-july-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	************************************************************/
	
public String chkRightAdvancedOptnsPresentOrNot(Selenium selenium,
		String strOptions, boolean blnOptions,String strRight) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		if (blnOptions) {
			try {
				assertTrue(selenium.isElementPresent(strOptions));
				log4j.info(strRight+" right is available.");

			} catch (AssertionError Ae) {
				strErrorMsg = strRight+" right is NOT available.";
				log4j.info(strRight+" right is  NOT available.");
			}
		} else {
			try {
				assertFalse(selenium.isElementPresent(strOptions));
				log4j.info(strRight+" right is  NOT available.");

			} catch (AssertionError Ae) {
				strErrorMsg =strRight+" right is available.";
				log4j.info(strRight+" right is available.");
			}
		}
	} catch (Exception e) {
		log4j
				.info("chkRightAdvancedOptnsPresentOrNot function failed"
						+ e);
		strErrorMsg = "chkRightAdvancedOptnsPresentOrNot function failedd"
				+ e;
	}
	return strErrorMsg;
}
/*******************************************************************
'Description :Verify Region is selected or not
'Precondition :None
'Arguments  :selenium,strRoleValue
'Returns  :String
'Date    :28-May-2012
'Author   :QSG
'------------------------------------------------------------------
'Modified Date                            Modified By
'28-May-2012                               <Name>
********************************************************************/

public String chkRegionSelectedOrNot(Selenium selenium,boolean blnRegion,
		 String strRegionvalue,String strRegionName) throws Exception {

 String strErrorMsg = "";// variable to store error mesage

 try {

  rdExcel = new ReadData();
  propEnvDetails = objReadEnvironment.readEnvironment();
  propElementDetails = objelementProp.ElementId_FilePath();
  gstrTimeOut = propEnvDetails.getProperty("TimeOut");

  selenium.selectWindow("");
  selenium.selectFrame("Data");
  if(blnRegion){
  try {
		assertTrue(selenium.isChecked("css=input[value='"+strRegionvalue+"']"));
		log4j.info("Check box corresponding to region "+strRegionName+ " remains checked");
	} catch (AssertionError Ae) {
		strErrorMsg = " Check box corresponding to region "+strRegionName+ "is unchecked";
		log4j.info("Check box corresponding to region "+strRegionName+ "is unchecked");
	}
  }else{
	try {

		assertFalse(selenium.isChecked("css=input[value='"+strRegionvalue+"']"));
		log4j.info("Check box corresponding to region "+strRegionName+ "is unchecked");
	} catch (AssertionError Ae) {
		strErrorMsg = " Check box corresponding to region "+strRegionName+ "is checked";
		log4j.info(" Check box corresponding to region "+strRegionName+ "is checked");
	}
  }
 } catch (Exception e) {
  log4j.info("chkRegionSelectedOrNot function failed" + e);
  strErrorMsg = "chkRegionSelectedOrNot function failed" + e;
 }
 return strErrorMsg;
}	 

/************************************************************
'Description	:Verify advanced option fields are selected or Not gor a user
'Precondition	:None
'Arguments		:selenium,strOptions,blnOptions
'Returns		:String
'Date	 		:5-April-2012
'Author			:QSG
'-----------------------------------------------------------
'Modified Date                            Modified By
'5-April-2012                               <Name>
************************************************************/

	public String chkRightAdvancedOptnsSelectedOrNot(Selenium selenium,
			String strOptions, boolean blnOptions, String strRight)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnOptions) {
				try {
					assertTrue(selenium.isChecked(strOptions));
					log4j.info("Check boxes associated with the right"
							+ strRight + " is selected.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Check boxes associated with the right"
							+ strRight + " is deselected.";
					log4j.info("Check boxes associated with the right"
							+ strRight + " is deselected.");
				}
			} else {
				try {
					assertFalse(selenium.isChecked(strOptions));
					log4j.info("Check boxes associated with the right"
							+ strRight + " is deselected.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Check boxes associated with the right"
							+ strRight + " is selected.";
					log4j.info("Check boxes associated with the right"
							+ strRight + " is selected.");
				}
			}
		} catch (Exception e) {
			log4j
					.info("chkRightAdvancedOptnsSelectedOrNot function failed"
							+ e);
			strErrorMsg = "chkRightAdvancedOptnsSelectedOrNot function failedd"
					+ e;
		}
		return strErrorMsg;
	}
	
	 /***************************************************************
	  'Description :Select and Deselect Status Types in Edit View
	  'Precondition :None
	  'Arguments  :selenium,strStatusType,blnCheck
	  'Returns  :strReason
	  'Date    :04-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	public String selAndDeselViewInEditUserPage(Selenium selenium,String 
			strViewvalue,boolean blnCheck) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			if(blnCheck){
				if (selenium
						.isChecked("css=input[name='regionViewID'][value='"
								+ strViewvalue + "']") == false) {
					selenium
							.click("css=input[name='regionViewID'][value='"
									+ strViewvalue + "']");

				}
			}else{
				if (selenium
						.isChecked("css=input[name='regionViewID'][value='"
								+ strViewvalue + "']")) {
					selenium
							.click("css=input[name='regionViewID'][value='"
									+ strViewvalue + "']");

				}
			}
		
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function selAndDeselViewInEditUserPage " + e.toString();
		}
		return strReason;
	}
	
	/************************************************************
	'Description	:Verify advanced option fields are selected or Not gor a user
	'Precondition	:None
	'Arguments		:selenium,strOptions,blnOptions
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'-----------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                               <Name>
	************************************************************/

		public String chkViewSelOrNotInEditUsrpage(Selenium selenium,
				String strViewname, boolean blnview, String strViewvalue)
				throws Exception {

			String strErrorMsg = "";// variable to store error mesage

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				if (blnview) {
					try {
						assertTrue(selenium.isChecked("css=input[name='regionViewID'][value='"
								+ strViewvalue + "']"));
						log4j.info("Check boxes associated with the right"
								+ strViewname + " is selected.");

					} catch (AssertionError Ae) {
						strErrorMsg = "Check boxes associated with the right"
								+ strViewname + " is deselected.";
						log4j.info("Check boxes associated with the right"
								+ strViewname + " is deselected.");
					}
				} else {
					try {
						assertFalse(selenium.isChecked("css=input[name='regionViewID'][value='"
								+ strViewvalue + "']"));
						log4j.info("Check boxes associated with the right"
								+ strViewname + " is deselected.");

					} catch (AssertionError Ae) {
						strErrorMsg = "Check boxes associated with the right"
								+ strViewname + " is selected.";
						log4j.info("Check boxes associated with the right"
								+ strViewname + " is selected.");
					}
				}
			} catch (Exception e) {
				log4j
						.info("chkViewSelOrNotInEditUsrpage function failed"
								+ e);
				strErrorMsg = "chkViewSelOrNotInEditUsrpage function failedd"
						+ e;
			}
			return strErrorMsg;
		}
		
		
	/*********************************************************
	 * 'Description :Verify user in the user list 'Precondition :None 'Arguments
	 * :selenium,strUserName 'Returns :String 'Date :4-April-2012 'Author :QSG
	 * '--------------------------------------------------------- 'Modified Date
	 * Modified By '4-April-2012 <Name>
	 **********************************************************/

	public String savVrfyUserWithSearchUser(Selenium selenium,
			String strUserName, String strByRole, String strByResourceType,
			String strByUserInfo, String strNameFormat) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateNewUsr.Save")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			

			selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Users List", selenium.getText(propElementDetails
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
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Users List' page is displayed ");

				strErrorMsg = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

				try {
					assertEquals("", strErrorMsg);

					try {
						assertEquals(
								selenium
										.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
								1);

						assertTrue(selenium
								.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
										+ "td[2][text()='" + strUserName + "']"));
						log4j.info("User " + strUserName
								+ " Present in the User List ");

					} catch (AssertionError Ae) {
						strErrorMsg = "User " + strUserName
								+ " NOT Present in the User List " + Ae;
						log4j.info("User " + strUserName
								+ " NOT Present in the User List " + Ae);

					}
				} catch (AssertionError Ae) {
					log4j.info(strErrorMsg);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "'Users List' page is NOT displayed " + Ae;
				log4j.info("'Users List' page is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("savVrfyUserWithSearchUser function failed" + e);
			strErrorMsg = "savVrfyUserWithSearchUser function failed" + e;
		}
		return strErrorMsg;
	}

	/*********************************************************
	'Description	:Verify user details in the user list
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:13-09-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<date>		                               <Name>
	**********************************************************/

	public String verifyUserDetailsInUserList(Selenium selenium,
			String strUserName, String strFullName, String strOrg)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName + "']"));
				log4j
						.info("User " + strUserName
								+ " Present in the User List ");

				try {

					assertEquals(strFullName, selenium
							.getText("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserName
									+ "']/parent::tr/td[3]"));
					log4j.info("User Full name " + strFullName
							+ " Present in the User List ");

				} catch (AssertionError Ae) {
					strErrorMsg = " User Full name " + strFullName
							+ " NOT Present in the User List " + Ae;
					log4j.info("User full name " + strFullName
							+ " NOT Present in the User List " + Ae);

				}

				try {

					assertEquals(strOrg, selenium
							.getText("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserName
									+ "']/parent::tr/td[4]"));
					log4j.info("User organization " + strOrg
							+ " Present in the User List ");

				} catch (AssertionError Ae) {
					strErrorMsg = strErrorMsg + " User organization " + strOrg
							+ " NOT Present in the User List " + Ae;
					log4j.info("User organization " + strOrg
							+ " NOT Present in the User List " + Ae);

				}

			} catch (AssertionError Ae) {
				strErrorMsg = "User " + strUserName
						+ " NOT Present in the User List " + Ae;
				log4j.info("User " + strUserName
						+ " NOT Present in the User List " + Ae);

			}

		} catch (Exception e) {
			log4j.info("VerifyUserDetailsInUserList function failed" + e);
			strErrorMsg = "VerifyUserDetailsInUserList function failed" + e;
		}
		return strErrorMsg;
	}

	
	
	/*******************************************************************************************
	 ' Description: verifying error message in user screen
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 26/07/2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String verifyErrorMsgInUserScreen(Selenium selenium, boolean blnSave)
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
			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isTextPresent("This username already exists. Please choose a new one."));
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
						.isTextPresent("This username already exists. Please choose a new one."));
				log4j
						.info("Message The following error occurred on this page:"
								+ " 'This username already exists. Please choose a new one is displayed on the 'Create User' screen. ");
			} catch (AssertionError Ae) {
				log4j
						.info("'This username already exists. Please choose a new one. is NOT  displayed on the 'Create User' screen. ");
				lStrReason = "This username already exists. Please choose a new one.is NOT  displayed on the 'Create User' screen. ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/**********************************************************
	'Description :Verify user  FullName in the user list Screen
	'Precondition :None
	'Arguments  :selenium,strUserFullName
	'Returns  :String
	'Date    :4-April-2012
	'Author   :QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************/

	public String vrfyUserOrgAndloging(Selenium selenium, String strUsername,
			String strorg, String strlog) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
								+ strUsername
								+ "']"
								+ "/parent::tr/td[4][text()='" + strorg + "']"));
				log4j
						.info("Updated data organization "
								+ strorg
								+ "is displayed for the user in the 'Users List'screen under set up.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Updated data "
						+ strorg
						+ "is displayed for the user in the 'Users List'screen under set up.";
				log4j
						.info("Updated data "
								+ strorg
								+ "is NOT displayed for the user in the 'Users List'screen under set up.");
			}

			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
								+ strUsername
								+ "']"
								+ "/parent::tr/td[5][text()='" + strlog + "']"));
				log4j.info(strlog + "is displayed under 'Last Login' column.");
			} catch (AssertionError Ae) {
				strErrorMsg = strlog
						+ "is NOT displayed under 'Last Login' column.";
				log4j.info(strlog
						+ "is NOT displayed under 'Last Login' column.");
			}

		} catch (Exception e) {
			log4j.info("savVrfyUser function failed" + e);
			strErrorMsg = "savVrfyUser function failed" + e;
		}
		return strErrorMsg;
	}
	  
	  /*******************************************************************************************
	  ' Description: Verify user full name is displayed at the footer
	  ' Precondition: N/A 
	  ' Arguments: selenium, strFullName,strUserName
	  ' Returns: String 
	  ' Date: 05/06/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 13-09-12
	  ' Modified By: 
	  *******************************************************************************************/

	  public String varFullNameAtTheFooter(Selenium selenium,String 
			  strFullName,String strUserName) throws Exception
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
	  			assertTrue(selenium.getText("css=td.ftRt").contains(strFullName));
	  			log4j.info("User " + strUserName+ "'s full name "+strFullName+" is displayed at the bottom right of the application ");
	  		}
	  		catch (AssertionError Ae) 
	  		{
	  			log4j.info("User " + strUserName+ "'s full name "+strFullName+" is NOT displayed at the bottom right of the application ");
	  			lStrReason ="User " + strUserName+ "'s full name "+strFullName+" is NOT displayed at the bottom right of the application ";
	  		}
	  	}catch(Exception e){
	  		log4j.info("varFullNameAtTheFooter function failed");
	  		lStrReason = "varFullNameAtTheFooter function failed";
	  	}

	  	return lStrReason;
	  }
	  
	  /*******************************************************************
	   'Description :select and deselect region
	   'Precondition :None
	   'Arguments  :selenium,strRoleValue
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '28-May-2012                               <Name>
	   ********************************************************************/

	   public String slectAndDeselectOtherRegion(Selenium selenium, String strRegionvalue,
	     boolean blnselectRegion,boolean blnSave) throws Exception {

	    String strErrorMsg = "";// variable to store error mesage

	    try {

	     rdExcel = new ReadData();
	     propEnvDetails = objReadEnvironment.readEnvironment();
	     propElementDetails = objelementProp.ElementId_FilePath();
	     gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	     
	     selenium.selectWindow("");
	     selenium.selectFrame("Data");
	     
	     
	     if (blnselectRegion) {
	      if (selenium.isChecked("css=input[name='otherRegionViewID'][value='"
	        + strRegionvalue + "']")) {

	      } else {
	       selenium.click("css=input[name='otherRegionViewID'][value='"
	         + strRegionvalue + "']");
	      }
	     } else {
	      if (selenium.isChecked("css=input[name='otherRegionViewID'][value='"
	        + strRegionvalue + "']") == false) {

	      } else {
	       selenium.click("css=input[name='otherRegionViewID'][value='"
	         + strRegionvalue + "']");
	      }
	     }
	     if(blnSave){
	      selenium.click("css=input[value='Save']");
	    selenium.waitForPageToLoad(gstrTimeOut);
	      }
	     
	    } catch (Exception e) {
	     log4j.info("slectAndDeselectRegion function failed" + e);
	     strErrorMsg = "slectAndDeselectRegion function failed" + e;
	    }
	    return strErrorMsg;
	   }
	   /*******************************************************************
		  'Description :Click close in Refine Visible Status Type and check
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

		public String closeRefineSTAndVerifyEditUser(Selenium selenium)
				throws Exception {

			String strErrorMsg = "";// variable to store error mesage

			try {

				rdExcel = new ReadData();
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				// click close
				selenium.click("css=#TB_closeWindowButton");

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				int intCnt=0;
				do{
					try{
						assertEquals("Edit User", selenium.getText(propElementDetails
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
					assertEquals("Edit User", selenium.getText(propElementDetails
							.getProperty("Header.Text")));

					log4j.info("Edit User page is displayed");

				} catch (AssertionError Ae) {

					strErrorMsg = "Edit User page is NOT displayed" + Ae;
					log4j.info("Edit User page is NOT displayed" + Ae);
				}

			} catch (Exception e) {
				log4j.info("closeRefineSTAndVerifyEditUser function failed" + e);
				strErrorMsg = "closeRefineSTAndVerifyEditUser function failed" + e;
			}
			return strErrorMsg;
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

		 public String selAndDeselSTInRefineVisibleST(Selenium selenium, String strStatTypeVal,
		   boolean blnSelStatType) throws Exception {

		  String strErrorMsg = "";// variable to store error mesage

		  try {

		   rdExcel = new ReadData();
		   propEnvDetails = objReadEnvironment.readEnvironment();
		   propElementDetails = objelementProp.ElementId_FilePath();
		   gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		   
		   
		   selenium.selectWindow("");
		   selenium.selectFrame("Data");
		   
		   selenium
			.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");
		   
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
		   log4j.info("selAndDeselSTInRefineVisibleST function failed" + e);
		   strErrorMsg = "selAndDeselSTInRefineVisibleST function failed" + e;
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

	public String saveChangesInRefineSTAndVerifyEditUser(Selenium selenium)
			throws Exception {

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
			Thread.sleep(1000);

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {
					assertEquals("Edit User", selenium
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
				assertEquals("Edit User", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Edit User page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit User page is NOT displayed" + Ae;
				log4j.info("Edit User page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("saveChangesInRefineSTAndVerifyEditUser function failed"
					+ e);
			strErrorMsg = "saveChangesInRefineSTAndVerifyEditUser function failed"
					+ e;
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

	public String saveChangesInRefineSTAndVerifyAssignUsr(Selenium selenium,
			String strResource) throws Exception {

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
					assertEquals("Assign Users to " + strResource, selenium
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
				assertEquals("Assign Users to " + strResource, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Assign Users to " + strResource
						+ "screen is displayed ");
			} catch (AssertionError Ae) {

				strErrorMsg = "Assign Users to " + strResource
						+ "screen is NOT displayed" + Ae;
				log4j.info("Assign Users to " + strResource
						+ "screen is NOT displayed ");
			}

		} catch (Exception e) {
			log4j.info("saveChangesInRefineSTAndVerifyEditUser function failed"
					+ e);
			strErrorMsg = "saveChangesInRefineSTAndVerifyEditUser function failed"
					+ e;
		}
		return strErrorMsg;
	}
 /*******************************************************************
  'Description :check close and save changes buttons are displayed in 
      Refine Visible Status Type screen
  'Precondition :None
  'Arguments  :selenium,blnSaveChk,blnCloseChk
  'Returns  :String
  'Date    :28-May-2012
  'Author   :QSG
  '------------------------------------------------------------------
  'Modified Date                            Modified By
  '<Date>                                   <Name>
  ********************************************************************/

	public String chkSaveAndCloseinRefineVisibleST(Selenium selenium,
			boolean blnSaveChk, boolean blnCloseChk) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// verify close
			if (blnCloseChk) {
				try {
					assertTrue(selenium
							.isElementPresent("css=#TB_closeWindowButton"));
					log4j
							.info("Close button is available Refine Visible Status Types window");
				} catch (AssertionError Ae) {
					log4j
							.info("Close button is NOT available Refine Visible Status Types window");
					strErrorMsg = "Close button is NOT available Refine Visible Status Types window";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=#TB_closeWindowButton"));
					log4j
							.info("Close button is not available Refine Visible Status Types window");
				} catch (AssertionError Ae) {
					log4j
							.info("Close button is still available Refine Visible Status Types window");
					strErrorMsg = "Close button is still available Refine Visible Status Types window";
				}
			}

			// verify save
			if (blnSaveChk) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[value='Save Changes']"));
					log4j
							.info("Save Changes button is available Refine Visible Status Types window");
				} catch (AssertionError Ae) {
					log4j
							.info("Save Changes button is NOT available Refine Visible Status Types window");
					strErrorMsg = strErrorMsg
							+ " Save Changes button is NOT available Refine Visible Status Types window";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[value='Save Changes']"));
					log4j
							.info("Save Changes button is not available Refine Visible Status Types window");
				} catch (AssertionError Ae) {
					log4j
							.info("Save Changes button is still available Refine Visible Status Types window");
					strErrorMsg = strErrorMsg
							+ " Save Changes button is still available Refine Visible Status Types window";
				}
			}

		} catch (Exception e) {
			log4j.info("chkSaveAndCloseinRefineVisibleST function failed" + e);
			strErrorMsg = "chkSaveAndCloseinRefineVisibleST function failed"
					+ e;
		}
		return strErrorMsg;
	}

 /*******************************************************************
  'Description :check the status types are displayed correctly as
        selected/deselcted in Refine Visible Status type window.
  'Precondition :None
  'Arguments  :selenium,strStatTypeName,strStatusTypeVal,blnCheck
  'Returns  :String
  'Date    :28-May-2012
  'Author   :QSG
  '------------------------------------------------------------------
  'Modified Date                            Modified By
  '<Date>                                   <Name>
  ********************************************************************/

	public String verifySTInRefineVisibleST(Selenium selenium,
			String strStatTypeName, String strStatusTypeVal, boolean blnCheck)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");

			Thread.sleep(1000);
			if (blnCheck) {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatusTypeVal + "']")) {

					log4j
							.info(strStatTypeName
									+ " is displayed as selected in Refine Visible Status Types window");
				} else {
					log4j
							.info(strStatTypeName
									+ " is NOT displayed as selected in Refine Visible Status Types window");
					strErrorMsg = strStatTypeName
							+ " is NOT displayed as selected in Refine Visible Status Types window";
				}
			} else {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatusTypeVal + "']") == false) {

					log4j
							.info(strStatTypeName
									+ " is not selected in Refine Visible Status Types window");
				} else {
					log4j
							.info(strStatTypeName
									+ " is displayed as selected in Refine Visible Status Types window");
					strErrorMsg = strStatTypeName
							+ " is displayed as selected in Refine Visible Status Types window";
				}
			}

		} catch (Exception e) {
			log4j.info("verifySTInRefineVisibleST function failed" + e);
			strErrorMsg = "verifySTInRefineVisibleST function failed" + e;
		}
		return strErrorMsg;
	}

 /*******************************************************************
 'Description :check the status types are displayed correctly as
		        selected/deselcted in Refine Visible Status type window.
 'Precondition :None
 'Arguments  :selenium,strStatTypeName,strStatusTypeVal,blnCheck
 'Returns  :String
 'Date    :28-May-2012
 'Author   :QSG
 '------------------------------------------------------------------
 'Modified Date                            Modified By
 '<Date>                                   <Name>
 ********************************************************************/

	public String verifySTPresenceInRefineVisibleST(Selenium selenium,
			String strStatTypeName, String strStatusTypeVal, boolean blnPresent)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.selectFrame("css=iframe[id^=\"TB_iframeContent\"]");

			Thread.sleep(1000);
			if (blnPresent) {
				if (selenium
						.isElementPresent("css=input[name='statusTypeID'][value='"
								+ strStatusTypeVal + "']")) {

					log4j
							.info(strStatTypeName
									+ " is displayed  in Refine Visible Status Types window");
				} else {
					log4j
							.info(strStatTypeName
									+ " is NOT displayed as selected in Refine Visible Status Types window");
					strErrorMsg = strStatTypeName
							+ " is NOT displayed as selected in Refine Visible Status Types window";
				}
			} else {
				if (selenium
						.isElementPresent("css=input[name='statusTypeID'][value='"
								+ strStatusTypeVal + "']") == false) {

					log4j
							.info(strStatTypeName
									+ " is NOT displayed in Refine Visible Status Types window");
				} else {
					log4j
							.info(strStatTypeName
									+ " is displayed in Refine Visible Status Types window");
					strErrorMsg = strStatTypeName
							+ " is displayed as  in Refine Visible Status Types window";
				}
			}

		} catch (Exception e) {
			log4j.info("verifySTPresenceInRefineVisibleST function failed" + e);
			strErrorMsg = "verifySTPresenceInRefineVisibleST function failed"
					+ e;
		}
		return strErrorMsg;
	}

 /**********************************************************************
   'Description :Verify select Resource Rights
   'Precondition :None
   'Arguments  :selenium,strResource,blnAssocWith,blnUpdStat,blnRunReport,strRSValue
   '     blnViewRes
   '     strUsrFulName
   'Returns  :String
   'Date    :14-June-2012
   'Author   :QSG
   '----------------------------------------------------------------------
   'Modified Date                            Modified By
   '13-11-2012                               <Name>
   **********************************************************************/

	public String checkResourceRightsWitRSValues(Selenium selenium,
			String strResource, String strRSValue, boolean blnAssocWith,
			boolean blnUpdStat, boolean blnRunReport, boolean blnViewRes) {
		String strReason = "";
		try {
			// Select Associated With option
			if (blnAssocWith) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='association'][value='"
									+ strRSValue + "']"));
					log4j.info("Associated right is selected for resource "
							+ strResource);
				} catch (AssertionError ae) {
					log4j.info("Associated right is NOT selected for resource "
							+ strResource);
					strReason = "Associated right is NOT selected for resource "
							+ strResource;
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='association'][value='"
									+ strRSValue + "']"));
					log4j.info("Associated right is not selected for resource "
							+ strResource);
				} catch (AssertionError ae) {
					log4j.info("Associated right is selected for resource "
							+ strResource);
					strReason = "Associated right is selected for resource "
							+ strResource;
				}
			}

			// Select Update Status option
			if (blnUpdStat) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='updateRight'][value='"
									+ strRSValue + "']"));
					log4j.info("Update right is selected for resource "
							+ strResource);
				} catch (AssertionError ae) {
					log4j.info("Update right is NOT selected for resource "
							+ strResource);
					strReason = "Update right is NOT selected for resource "
							+ strResource;
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='updateRight'][value='"
									+ strRSValue + "']"));
					log4j.info("Update right is not selected for resource "
							+ strResource);
				} catch (AssertionError ae) {
					log4j.info("Update right is selected for resource "
							+ strResource);
					strReason = "Update right is selected for resource "
							+ strResource;
				}
			}

			// Select Run reports option
			if (blnRunReport) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='reportRight'][value='"
									+ strRSValue + "']"));
					log4j.info("Run report right is selected for resource "
							+ strResource);
				} catch (AssertionError ae) {
					log4j.info("Run report right is NOT selected for resource "
							+ strResource);
					strReason = "Run report right is NOT selected for resource "
							+ strResource;
				}

			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='reportRight'][value='"
									+ strRSValue + "']"));
					log4j.info("Run report right is not selected for resource "
							+ strResource);
				} catch (AssertionError ae) {
					log4j.info("Run report right is selected for resource "
							+ strResource);
					strReason = "Run report right is selected for resource "
							+ strResource;
				}
			}

			// Select View Resource option
			if (blnViewRes) {
				try {
					assertTrue(selenium
							.isChecked("css=input[name='viewRight'][value='"
									+ strRSValue + "']"));
					log4j.info("View Resource right is selected for resource "
							+ strResource);
				} catch (AssertionError ae) {
					log4j
							.info("View Resource right is NOT selected for resource "
									+ strResource);
					strReason = "View Resource right is NOT selected for resource "
							+ strResource;
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=input[name='viewRight'][value='"
									+ strRSValue + "']"));
					log4j
							.info("View Resource right is not selected for resource "
									+ strResource);
				} catch (AssertionError ae) {
					log4j.info("View Resource right is selected for resource "
							+ strResource);
					strReason = "View Resource right is selected for resource "
							+ strResource;
				}
			}

		} catch (Exception e) {
			log4j.info("checkResourceRightsWitRSValues function failed" + e);
			strReason = "checkResourceRightsWitRSValues function failed" + e;
		}
		return strReason;
	}

	/**********************************************************
	'Description :Verify user  FullName in the user list Screen
	'Precondition :None
	'Arguments  :selenium,strUserFullName
	'Returns  :String
	'Date    :4-April-2012
	'Author   :QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************/

	public String vrfyUserOrg(Selenium selenium, String strUsername,
			String strorg, boolean blnOrg) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnOrg) {
				try {

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
									+ strUsername
									+ "']"
									+ "/parent::tr/td[4][text()='"
									+ strorg
									+ "']"));
					log4j.info("Updated data organization "
							+ strorg
							+ "is displayed for the user in the 'Users List'screen under set up.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Updated data "
							+ strorg
							+ "is displayed for the user in the 'Users List'screen under set up.";
					log4j.info("Updated data "
							+ strorg
							+ "is NOT displayed for the user in the 'Users List'screen under set up.");
				}
			} else {

				try {
					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
									+ strUsername
									+ "']"
									+ "/parent::tr/td[4][text()='"
									+ strorg
									+ "']"));
					log4j.info("Updated data organization "
							+ strorg
							+ "is displayed for the user in the 'Users List'screen under set up.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Updated data "
							+ strorg
							+ "is displayed for the user in the 'Users List'screen under set up.";
					log4j.info("Updated data "
							+ strorg
							+ "is NOT displayed for the user in the 'Users List'screen under set up.");
				}

			}

		} catch (Exception e) {
			log4j.info("savVrfyUser function failed" + e);
			strErrorMsg = "savVrfyUser function failed" + e;
		}
		return strErrorMsg;
	}
	/*******************************************************************
	 'Description :Verify edit user Region page is displayed
	 'Precondition :None
	 'Arguments  :selenium,stUserName
	 'Returns  :String
	 'Date    :25-May-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '25-May-2012                               <Name>
	 ********************************************************************/
	 
	public String varFldsInEditUsrRgn(Selenium selenium, String strUserName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			int intCnt=0;
			do{
				try{
					assertEquals("Edit User Regions", selenium
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
				assertEquals("Edit User Regions", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Edit User Regions' screen is displayed  ");
				
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[2]/thead/tr/th"));
				log4j.info("1. User Profile field is displayed");				
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr/td[text()='Username:']"));
				log4j.info("In User Profile field Username: is displayed");				
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr/td[text()='Full Name:']"));
				log4j.info("In User Profile field Full Name: is displayed");	
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr/td[text()='Organization:']"));
				log4j.info("In User Profile field Organization: is displayed");	
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[3]/thead/tr/th"));
				log4j.info("2. User Regions feild is displayed");
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[3]/tbody/tr/td[text()='Regions:']"));
				log4j.info("In User Regions feild Regions: is displayed");
				
			} catch (AssertionError Ae) {
				strErrorMsg = "'Edit User Regions' screen is NOT displayed "+ Ae;
				log4j.info("'Edit User Regions' screen is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("navEditUserRegions function failed" + e);
			strErrorMsg = "navEditUserRegions function failed" + e;
		}
		return strErrorMsg;
	}
	
	/************************************************************
	'Description	:Verify user is activated
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:20-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'20-April-2012                               <Name>
	**********************************************************/

	public String activateAndDeactivateUser(Selenium selenium,boolean 
			blnInactive,boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			if(blnInactive){
			if (selenium.isChecked(propElementDetails
					.getProperty("EditUser.Deactivate")) == false) 
				selenium.click(propElementDetails
						.getProperty("EditUser.Deactivate"));				
			}else
			{
				if (selenium.isChecked(propElementDetails
						.getProperty("EditUser.Deactivate"))) 
					selenium.click(propElementDetails
							.getProperty("EditUser.Deactivate"));	
			}
			
			if(blnSave){
				selenium.click(propElementDetails.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);			
			}
		} catch (Exception e) {
			log4j.info("activateAndDeactivateUser fuction " + e);
			strErrorMsg = "activateAndDeactivateUser fuction " + e;
		}
		return strErrorMsg;

	}
	

	/************************************************************
	'Description	: varConfirmUserDeactivationPage
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:20-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'20-April-2012                               <Name>
	**********************************************************/

	public String varConfirmUserDeactivationPage(Selenium selenium,
			String strUsrFulName,String
			strUserName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			int intCnt=0;
			do{
				try{
					assertEquals("Confirm User Deactivation",
							selenium.getText(propElementDetails
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
				assertEquals("Confirm User Deactivation",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Confirm User Deactivation' screen is displayed");
				try {
					assertTrue(selenium.isElementPresent("css=p"));
					log4j.info("The user has been saved is displayed");
				} catch (Exception e) {
					log4j.info("'The user has been saved is NOT displayed");
					strErrorMsg = "'The user has been saved is NOT displayed";
				}
				try {
					assertEquals("You are now about to deactivate the account for "+strUsrFulName+
							" (username: "+strUserName+")", selenium.getText
							("//div[@id='mainContainer']/form/div/div[2]/p[2]"));
					log4j.info("You are now about to deactivate "
							+ "the account for " + strUsrFulName
							+ "(username: " + strUserName + ")is displayed");
				} catch (Exception e) {
					log4j.info("You are now about to deactivate "
							+ "the account for " + strUsrFulName
							+ "(username: " + strUserName + ")is NOT displayed");
					strErrorMsg = "You are now about to deactivate "
							+ "the account for " + strUsrFulName
							+ "(username: " + strUserName + ")is NOT displayed";
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/div/div[3]/ul/li"));
					log4j.info("Please Note: This user will NOT be able to log into EMSystem is displayed.");
				} catch (Exception e) {
					log4j.info("Please Note: This user will NOT be able to log into EMSystem is NOT displayed.");
					strErrorMsg = "Please Note: This user will NOT be able to log into EMSystem is NOT displayed.";
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/div/div[3]/ul/li[2]"));
					log4j.info("All email addresses for this user will be deleted is displayed.");
				} catch (Exception e) {
					log4j.info("All email addresses for this user will be deleted is NOT displayed.");
					strErrorMsg = "All email addresses for this user will be deleted is NOT displayed.";
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/div/div[3]/ul/li[3]"));
					log4j.info("All pager addresses for this user will be deleted is displayed.");
				} catch (Exception e) {
					log4j.info("All pager addresses for this user will be deleted is NOT displayed.");
					strErrorMsg = "All pager addresses for this user will be deleted is NOT displayed.";
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/div/div[4]"));
					log4j.info("Are you sure you would like to deactivate this user? is displayed.");
				} catch (Exception e) {
					log4j.info("Are you sure you would like to deactivate this user? is NOT displayed.");
					strErrorMsg = "Are you sure you would like to deactivate this user? is NOTdisplayed.";
				}
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("ConfirmUserDeactivation.Yes")));
					log4j.info("Yes, Deactivate this User'button is displayed");
				} catch (Exception e) {
					log4j.info("Yes, Deactivate this User'button is NOT displayed");
					strErrorMsg = "Yes, Deactivate this User'button is NOT displayed";
				}
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("ConfirmUserDeactivation.No")));
					log4j.info("'No, Do NOT Deactivate this User' button is displayed");
				} catch (Exception e) {
					log4j.info("'No, Do NOT Deactivate this User' button is NOT displayed");
					strErrorMsg ="'No, Do NOT Deactivate this User' button is NOT displayed";
				}

			} catch (Exception e) {
				log4j.info("'Confirm User Deactivation' screen is displayed");
				strErrorMsg = "'Confirm User Deactivation' screen is displayed";
			}
	} catch (Exception e) {
			log4j.info("activateAndDeactivateUser fuction " + e);
			strErrorMsg = "activateAndDeactivateUser fuction " + e;
		}
		return strErrorMsg;

	}
	
	/************************************************************
	'Description	:Navigate user deactivate page
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:19-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'19-April-2012                               <Name>
	**********************************************************/

	public String NavUserDeactivatePage(Selenium selenium, String strUserName,
			String strUserFullName, boolean blnInactive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("ConfirmUserDeactivation.Yes")));
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
			selenium.click(propElementDetails
					.getProperty("ConfirmUserDeactivation.Yes"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			intCnt=0;
			do{
				try{
					assertEquals("User Deactivated",
							selenium.getText(propElementDetails
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

			try{
			assertEquals("User Deactivated",
					selenium.getText(propElementDetails
							.getProperty("Header.Text")));
			log4j.info("User Deactivated' screen is displayed");

			try{
			assertEquals("You have successfully deactivated "+strUserFullName+" " +
					"(username: "+strUserName+")", selenium.getText("css=div.emsTitle"));
			log4j.info("You have successfully deactivated "+strUserFullName+" (username: "
					+ strUserName + ")is displayed");
			
			} catch (AssertionError Ae) {
				strErrorMsg ="You have successfully deactivated "+strUserFullName+" (username: "
						+ strUserName + ")is NOT displayed";
				log4j.info("You have successfully deactivated "+strUserFullName+" (username: "
						+ strUserName + ")is NOT displayed");
			}
			try{
			assertTrue(selenium.isElementPresent("css=p"));
			log4j.info("This user can no longer login is displayed.");	
			
			} catch (AssertionError Ae) {
				strErrorMsg ="This user can no longer login is displayed.";			
				log4j.info("This user can no longer login is displayed.");			
			}
			try{
			assertTrue(selenium
					.isElementPresent("//div[@id='mainContainer']/form/div/div[2]/p[2]"));
			log4j.info("Any email and pager addresses have been deleted is displayed.");
			
			} catch (AssertionError Ae) {
				strErrorMsg ="Any email and pager addresses have been deleted is displayed.";
				log4j.info("Any email and pager addresses have been deleted is displayed.");
			}	
			try{
			selenium.isElementPresent(propElementDetails
					.getProperty("UserDeactivationComplete.ReturnUsrLst"));
			log4j.info("'Return to user list' button is present. ");

			} catch (AssertionError Ae) {
				strErrorMsg ="'Return to user list' button is present. ";
				log4j.info("'Return to user list' button is present. ");
			}
			} catch (AssertionError Ae) {
				strErrorMsg = "User Deactivated' screen is NOT displayed";
				log4j.info("User Deactivated' screen is NOT displayed." );

			}
			selenium.click(propElementDetails
					.getProperty("UserDeactivationComplete.ReturnUsrLst"));
			selenium.waitForPageToLoad(gstrTimeOut);	

			if (blnInactive) {
				try {
					if (selenium.isChecked(propElementDetails
							.getProperty("UserList.IncludeActvUsers")) == false) {
						selenium.click(propElementDetails
								.getProperty("UserList.IncludeActvUsers"));

						intCnt = 0;

						try {

							while (selenium.isVisible(propElementDetails
									.getProperty("Reloading.Element"))
									&& intCnt < 60) {
								intCnt++;
								Thread.sleep(100);
							}

						} catch (Exception e) {
							log4j.info(e);
						}
						log4j.info(intCnt);

					}
					log4j.info("Check Box  selected");
				} catch (Exception e) {
					strErrorMsg = "Check Box NOT selected" + e;
					log4j.info("Check Box NOT selected" + e);
				}
			}

			if (blnInactive) {
				try {
					assertTrue(selenium
							.isElementPresent("//td[@class='inactive'][contains(text(),'"
									+ strUserFullName + "')]"));
					log4j.info("Users are  deactivated ");
				} catch (AssertionError Ae) {
					strErrorMsg = "User is NOT deactivated ";
					log4j.info("User is NOT deactivated " + Ae);
				}

			} else {
				try {

					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr"
									+ "/td[2][text()='" + strUserName + "']"));
					log4j.info("User is deactivated ");
				} catch (AssertionError Ae) {
					strErrorMsg = "User is NOT deactivated ";
					log4j.info("User is NOT deactivated " + Ae);

				}
			}

		} catch (Exception e) {
			log4j.info("NavUserDeactivatePage fuction " + e);
			strErrorMsg = "NavUserDeactivatePage fuction " + e;
		}
		return strErrorMsg;

	}
	
	/************************************************************
	'Description	:Verify user is activated
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:20-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'20-April-2012                               <Name>
	**********************************************************/

	public String activateUserVarMsg(Selenium selenium, String strUserName,
			String strUserFullName, boolean blnInactive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			if (selenium.isChecked(propElementDetails
					.getProperty("UserList.IncludeActvUsers")) == false) {
				selenium.click(propElementDetails
						.getProperty("UserList.IncludeActvUsers"));

				int intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element"))
							&& intCnt < 60) {
						intCnt++;
						Thread.sleep(100);
					}

				} catch (Exception e) {
					log4j.info(e);
				}
			}

			try {

				assertTrue(selenium
						.isElementPresent("//td[@class='inactive'][contains(text(),'"
								+ strUserFullName + "')]"));

				log4j.info("Active and inactive users are displayed "
						+ "on the 'Users List' screen.");

			} catch (AssertionError Ae) {
				strErrorMsg = "Active and inactive users are NOT displayed"
						+ " on the 'Users List' screen. ";

			}				
			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
					+ strUserName + "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			selenium.click(propElementDetails
					.getProperty("EditUser.Activate"));
			selenium.click(propElementDetails
					.getProperty("EditUser.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("User Activation Complete", selenium.getText(propElementDetails
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
		
			try{
				assertEquals("User Activation Complete", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("User Activation Complete Screen is  displayed");
				
				try {
					assertEquals("You have successfully re-activated "
							+ strUserFullName + " (username: " + strUserName
							+ ")", selenium.getText("css=div.emsTitle"));
					log4j.info("You have successfully re-activated "
							+ strUserFullName + " (username: " + strUserName
							+ "is displayed");
				} catch (AssertionError Ae) {
					strErrorMsg = "You have successfully re-activated "
							+ strUserFullName + " (username: " + strUserName
							+ "is NOT displayed";
					log4j.info("You have successfully re-activated "
							+ strUserFullName + " (username: " + strUserName
							+ "is NOT displayed");

				}
				try {
					assertEquals(
							"You or the user will need to reenter any email or pager addresses.",
							selenium.getText("css=div.emsNormalLabel.center"));
					log4j.info("You or the user will need to reenter any email or pager addresses. is displayed");
				} catch (AssertionError Ae) {
					strErrorMsg = "You or the user will need to reenter any email or pager addresses. is NOT displayed";
					log4j.info("You or the user will need to reenter any email or pager addresses. is NOT displayed");
				}
				try{
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("UserActivationComplete.ReturnUsrLst")));
					log4j.info("'Return to User List' button is available. ");
					
					selenium.click(propElementDetails
							.getProperty("UserActivationComplete.ReturnUsrLst"));
					selenium.waitForPageToLoad(gstrTimeOut);

					if (blnInactive) {
						
						try {
							assertTrue(selenium
									.isElementPresent("//table[@id='tblUsers']/tbody/tr"
											+ "/td[3][text()='"
											+ strUserFullName + "']"));
					

							log4j.info("Users are  activated");
							
						} catch (AssertionError Ae) {
							strErrorMsg = "Users are NOT activated";
							log4j.info("Users are NOT activated" + Ae);

						}
						
					}
					
				}catch(AssertionError Ae){
					strErrorMsg = "'Return to User List' button is NOT available. ";
					log4j.info("'Return to User List' button is NOT available. " + Ae);

				}						
			}catch(AssertionError Ae){
				strErrorMsg = "User Activation Complete page is NOT displayed";
				log4j.info("User Activation Complete page is NOT displayed" + Ae);

			}
			
		} catch (Exception e) {
			log4j.info("activateUser fuction " + e);
			strErrorMsg = "activateUser fuction " + e;
		}
		return strErrorMsg;

	}
	
	/************************************************************
	'Description	:Verify Edit Multi-Region Event Rights selected
	'				 for given user
	'Precondition	:None
	'Arguments		:selenium,strRegnName[],strRegionValue[]
	'Returns		:String
	'Date	 		:25-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'                             <Name>
	**********************************************************/
	
	public String navEditMultiRegnEventRitesAndSelctRegions(Selenium selenium,
			String[] strRegnName, String strRegionValue[], boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("EditUser.MultiRegnEvntLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Edit Multi-Region Event Rights", selenium
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
				assertEquals("Edit Multi-Region Event Rights", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Multi-Region Event Rights page is displayed");

				for (String s : strRegionValue) {

					if (selenium
							.isChecked("css=input[name='userRegionID'][value='"
									+ s + "']") == false) {
						log4j
						.info("'Edit Multi-Region Event Rights' screen is displayed with region "
								+ s);
						
						selenium.click("css=input[name='userRegionID'][value='"
								+ s + "']");
						
					}

				}
				

					
				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("EditMultiRegnEvntRites.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					intCnt=0;
					do{
						try{
							assertEquals("Edit User", selenium
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
						assertEquals("Edit User", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Edit User page is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Edit User page is NOT displayed";
						log4j.info("Edit User page is NOT displayed");
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Multi-Region Event Rights page is NOT displayed";
				log4j
						.info("Edit Multi-Region Event Rights page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("navEditMultiRegnEventRitesAndSelctRegions function failed" + e);
			strErrorMsg = "navEditMultiRegnEventRitesAndSelctRegions function failed";
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description: Select all the advanced rights for user
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 05-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String selectAllAdvancedRights(Selenium selenium) throws Exception
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
				int intCnt=1;
				while(selenium.isElementPresent("//table/tbody/tr[2]/td[text()='Additional User Rights:']/following-sibling::td[1]/input["+intCnt+"]")){
					selenium.click("//table/tbody/tr[2]/td[text()='Additional User Rights:']/following-sibling::td[1]/input["+intCnt+"]");
					intCnt++;
				}
			}catch(Exception e){
				log4j.info(e);
				lStrReason = lStrReason + "; " + e.toString();
			}
		
		return lStrReason;
		
	}
	/*********************************************************
	'Description	:Verify user details in the user list
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:13-09-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<date>		                               <Name>
	**********************************************************/
	
public String verifyActionFieldForUser(Selenium selenium,String strUserName) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
	
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
		
			assertEquals(
					selenium
							.getText("//table[@id='tblUsers']/thead/tr/th[1]"),"Actions");
			log4j.info("Actions header is Present in the User List .");
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='" + strUserName + "']" +
								"/parent::tr/td[1]/a[text()='Edit']"));
				log4j.info("In Action Header Edit link is Present in the User List for"+strUserName );
			} catch (AssertionError Ae) {
				strErrorMsg = "In Action Header Edit link is NOT Present in the User List for"+strUserName;
				log4j.info("In Action Header Edit link is NOT Present in the User List for"+strUserName );

			}	
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='" + strUserName + "']" +
								"/parent::tr/td[1]/a[text()='Password']"));
				log4j.info("In Action Header Password link is Present in the User List for"+strUserName );
			} catch (AssertionError Ae) {
				strErrorMsg = "In Action Header Password link is NOT Present in the User List for"+strUserName;
				log4j.info("In Action Header Password link is NOT Present in the User List for"+strUserName );

			}	
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='" + strUserName + "']" +
								"/parent::tr/td[1]/a[text()='Regions']"));
				log4j.info("In Action Header Regions link is Present in the User List for"+strUserName );
			} catch (AssertionError Ae) {
				strErrorMsg = "In Action Header Regions link is NOT Present in the User List for"+strUserName;
				log4j.info("In Action Header Regions link is NOT Present in the User List for"+strUserName );

			}	
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='" + strUserName + "']" +
								"/parent::tr/td[1]/a[text()='Switch']"));
				log4j.info("In Action Header Edit link is Present in the User List for"+strUserName );
			} catch (AssertionError Ae) {
				strErrorMsg = "In Action Header Switch link is NOT Present in the User List for"+strUserName;
				log4j.info("In Action Header Switch link is NOT Present in the User List for"+strUserName );

			}	
						
		} catch (AssertionError Ae) {
			strErrorMsg = "Actions header is Present NOT in the User List ." + Ae;
			log4j.info("Actions header is Present NOT in the User List . " + Ae);
		}		
		
		
	} catch (Exception e) {
		log4j.info("verifyActionFieldForUser function failed" + e);
		strErrorMsg = "verifyActionFieldForUser function failed" + e;
	}
	return strErrorMsg;
}

/*******************************************************************************************
' Description: Verify that Resource is present Resource Rights
' Precondition:N/A 
' Arguments: selenium,strResource,blnResRight
' Returns:String 
' Date:29-05-2012
' Author:QSG 
'--------------------------------------------------------------------------------- 
' Modified Date: 
' Modified By: 
*******************************************************************************************/

public String verifyResourceInResRights(Selenium selenium,String strResource,boolean blnPresent) throws Exception {
	String lStrReason = "";

	// Create an object to refer to the Element ID Properties file
	ElementId_properties objelementProp = new ElementId_properties();
	propElementDetails = objelementProp.ElementId_FilePath();

	// Create an object to refer to the Environment Properties file
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	propEnvDetails = objReadEnvironment.readEnvironment();
	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	try {
		if(blnPresent){
			if(selenium.isElementPresent("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
									+ strResource+"']")){
				log4j
				.info("resource "+strResource+"is listed under 'Resource Rights' section.");
			}else{
				lStrReason = "resource "+strResource+"is NOT listed under 'Resource Rights' section.";
				log4j
				.info("resource "+strResource+"is NOT listed under 'Resource Rights' section.");
			}
		}else{
			if(selenium.isElementPresent("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
					+ strResource+"']")==false){
				log4j
				.info("resource "+strResource+"is not listed under 'Resource Rights' section.");
			}else{
				lStrReason = "resource "+strResource+"is listed under 'Resource Rights' section.";
				log4j
				.info("resource "+strResource+"is listed under 'Resource Rights' section.");
			}
		}
	} catch (Exception e) {
		log4j.info(e);
		lStrReason = e.toString();
	}

	return lStrReason;
}
/*********************************************************
'Description	:Verify user details in the user list
'Precondition	:None
'Arguments		:selenium,strUserName
'Returns		:String
'Date	 		:13-09-2012
'Author			:QSG
'---------------------------------------------------------
'Modified Date                            Modified By
'<date>		                               <Name>
**********************************************************/

public String navSelRegnPageVarMsg(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

		try {
			selenium.click("id=regionName");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Select Region", selenium.getText(propElementDetails
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
				assertEquals("Select Region", selenium.getText("css=h1"));
				log4j.info("Select Region screen is displayed");

				try {
					assertEquals(
							"You have access to multiple regions. Please select which one you would like to view.",
							selenium.getText("css=p.emsInstructions"));
					log4j.info("The message 'You have access to multiple regions. Please select which one you"
							+ " would like to view.' is displayed. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "The message 'You have access to multiple regions. Please select which one you"
							+ " would like to view.' is NOT displayed. ";
					log4j.info("The message 'You have access to multiple regions. Please select which one you"
							+ " would like to view.' is NOT displayed. ");
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "Select Region screen is NOT displayed";
				log4j.info("Select Region screen is NOT displayed");

			}

		} catch (AssertionError Ae) {
			strErrorMsg = "Actions header is Present NOT in the User List ."
					+ Ae;
			log4j.info("Actions header is Present NOT in the User List . "
					+ Ae);
		}

		} catch (Exception e) {
			log4j.info("verifyActionFieldForUser function failed" + e);
			strErrorMsg = "verifyActionFieldForUser function failed" + e;
		}
		return strErrorMsg;
	}
/*******************************************************************************************
' Description: Verify User Mandatory Field Values Along with Role Info
' Precondition: N/A 
' Arguments: selenium, strUserID, strFulUserName, strRoleVal, blnRoleSelect
' Returns: String 
' Date: 08-11-2012
' Author: QSG 
'--------------------------------------------------------------------------------- 
' Modified Date: 
' Modified By: 
*******************************************************************************************/

public String verifyUsrMandValuesWithRole(Selenium selenium, String strUserID, String strFulUserName, String strRoleVal,boolean blnRoleSelect) throws Exception
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
			assertEquals(strUserID, selenium.getValue(propElementDetails.getProperty("CreateNewUsr.UserName")));
			assertEquals(strFulUserName, selenium.getValue(propElementDetails.getProperty("CreateNewUsr.FulUsrName")));
			assertTrue(selenium.isElementPresent("css=input[name='userRoleID'][value='"+strRoleVal+"']"));
			if(blnRoleSelect){
				assertTrue(selenium.isChecked("css=input[name='userRoleID'][value='"+strRoleVal+"']"));
				log4j.info("Role is selected");
			}else{
				assertFalse(selenium.isChecked("css=input[name='userRoleID'][value='"+strRoleVal+"']"));
				log4j.info("Role is not selected");
			}
			
			log4j.info("The values provided while uploading the user is retained.");
		}
		catch (AssertionError Ae) {
			log4j.info(Ae);
			log4j.info("The values provided while uploading the user is NOT retained.");
			lStrReason = lStrReason + "; " + "The values provided while uploading the user is NOT retained.";
		}
	}catch(Exception e){
		log4j.info(e);
		lStrReason = lStrReason + "; " + e.toString();
	}

	return lStrReason;
}

/*********************************************************
'Description	:Verify Duplicate of user present in the user list
'Precondition	:None
'Arguments		:selenium,strUserName
'Returns		:String
'Date	 		:09-11-2012
'Author			:QSG
'---------------------------------------------------------
'Modified Date                            Modified By
'<Date>		                              <Name>
**********************************************************/

public String checkDuplicateOfUserNotPresent(Selenium selenium,
		String strUserName)
   throws Exception {
	String lStrReason="";
	try{
		int intValue=0;
	  
		intValue=selenium
			.getXpathCount("//table[@id='tblUsers']/tbody/tr/td"
					+ "[text()='"+strUserName+"']").intValue();
		if(intValue==1){
			log4j.info("Duplicate of User "+strUserName+" is NOT present");
		}else if (intValue>1){			
			log4j.info("Duplicate of User "+strUserName+" is present");
			lStrReason = lStrReason + "; " + "Duplicate of User "+strUserName+" is present";
		}
		
	}catch(Exception e){
		log4j.info(e);
		lStrReason = lStrReason + "; " + e.toString();
	}
	return lStrReason;
}
/*****************************************************************
'Description	:navigate to edit user page
'Precondition	:None
'Arguments		:selenium,strUsrName
'Returns		:String
'Date	 		:15-May-2012
'Author			:QSG
'-----------------------------------------------------------------
'Modified Date                            Modified By
'15-May-2012                               <Name>
*****************************************************************/

public String navToEditUserPageFrmUplDetails(Selenium selenium,String strUsrName){
	String strReason="";
	try{
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		//Click on Edit link for the user
		selenium.click("link="+strUsrName);
		selenium.waitForPageToLoad(gstrTimeOut);
		
		int intCnt=0;
		do{
			try{
				assertEquals("Edit User", selenium
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
			assertEquals("Edit User", selenium
					.getText(propElementDetails
							.getProperty("Header.Text")));

			log4j.info("Edit User page is displayed");
			
		} catch (AssertionError Ae) {

			strReason = "Edit User page is NOT displayed" + Ae;
			log4j.info("Edit User page is NOT displayed" + Ae);
		}
		
	} catch (Exception e) {
		log4j.info("navToEditUserPageFrmUplDetails function failed" + e);
		strReason = "navToEditUserPageFrmUplDetails function failed" + e;
	}
	return strReason;
}


/************************************************************
'Description :Verify inactive users in the region 
'Precondition :None
'Arguments  :selenium,strUserName
'Returns  :String
'Date    :30-Nov-2012
'Author   :QSG
'---------------------------------------------------------
'Modified Date                            Modified By
'                             <Name>
**********************************************************/

	public String verfyUsrCountOfActiveandInactiveUsers(Selenium selenium,
			boolean blnUsrCount, boolean blnInactive, boolean blnChekDefault)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			if (blnChekDefault) {

				String strUsrCnt = selenium.getText("//td[@align='right']/"
						+ "span[@id='count']");
				String[] strUsrCntArray = strUsrCnt.split(" ");
				strUsrCnt = strUsrCntArray[0];
				int intUsrCount = Integer.parseInt(strUsrCnt);
				try {

					assertTrue(selenium.isElementPresent("//td[@align='right']/"
							+ "span[@id='count'][text()='" + intUsrCount
							+ " users']"));

					assertFalse(selenium.isChecked(propElementDetails.getProperty("UserList.IncludeActvUsers")));
					assertTrue(selenium.isEditable(propElementDetails.getProperty("UserList.IncludeActvUsers")));
					log4j
							.info("'Include inactive users' check box is present at left corner of the screen "
									+ "and is unchecked by default AND IS ENABLED");
					
		
					log4j
							.info("Total number of users in that region is displayed is"
									+ intUsrCount);

				} catch (AssertionError Ae) {
					log4j
							.info("'Include inactive users' check box is NOT present at left corner of the screen "
									+ "and is unchecked by default AND IS ENABLED");
					strErrorMsg = "'Include inactive users' check box is NOT present at left corner of the screen "
							+ "and is unchecked by default AND IS ENABLED";

				}
			}

			if (blnUsrCount) {
				if (blnInactive) {

					String strUsrCnt = selenium.getText("//td[@align='right']/"
							+ "span[@id='count']");
					String[] strUsrCntArray = strUsrCnt.split(" ");
					strUsrCnt = strUsrCntArray[0];
					int intUsrCount = Integer.parseInt(strUsrCnt);
					if (selenium.isChecked(propElementDetails.getProperty("UserList.IncludeActvUsers")) == false) {
						selenium.click(propElementDetails.getProperty("UserList.IncludeActvUsers"));
						try {
							assertTrue(selenium.isElementPresent("//td[@align='right']/"
									+ "span[@id='count'][text()='" + intUsrCount
									+ " users']"));

							assertTrue(selenium.isChecked(propElementDetails.getProperty("UserList.IncludeActvUsers")));
							log4j.info("'Include inactive users' check box is present at left corner of the screen and is checked.");
						} catch (AssertionError Ae) {
							log4j.info("'Include inactive users' check box is NOT present at left corner of the screen and is checked.");
							strErrorMsg = "'Include inactive users' check box is NOT present at left corner of the screen and is checked.";

						}
						int intCnt = 0;

						try {

							while (selenium.isVisible(propElementDetails
									.getProperty("Reloading.Element"))
									&& intCnt < 60) {
								intCnt++;
								Thread.sleep(100);
							}

						} catch (Exception e) {
							log4j.info(e);
						}

					}

					int intActualUsrCount = selenium.getXpathCount(
							"//table[@id='tblUsers']/tbody/tr").intValue();
					String strActualUsrCount = Integer
							.toString(intActualUsrCount);

					try {
						assertEquals(strUsrCnt, strActualUsrCount);
					} catch (AssertionError Ae) {
						log4j
								.info("User count is matching with the actual active and Inactive users");

					}

				} else {

					String strUsrCnt = selenium.getText("//td[@align='right']/"
							+ "span[@id='count']");
					String[] strUsrCntArray = strUsrCnt.split(" ");
					strUsrCnt = strUsrCntArray[0];

					if (selenium.isChecked(propElementDetails.getProperty("UserList.IncludeActvUsers"))) {
						selenium.click(propElementDetails.getProperty("UserList.IncludeActvUsers"));

						int intCnt = 0;

						try {

							while (selenium.isVisible(propElementDetails
									.getProperty("Reloading.Element"))
									&& intCnt < 60) {
								intCnt++;
								Thread.sleep(100);
							}

						} catch (Exception e) {
							log4j.info(e);
						}

					}

					int intActualUsrCount = selenium.getXpathCount(
							"//table[@id='tblUsers']/tbody/tr").intValue();
					String strActualUsrCount = Integer
							.toString(intActualUsrCount);

					try {
						assertEquals(strUsrCnt, strActualUsrCount);
					} catch (AssertionError Ae) {
						log4j
								.info("User count is NOT matching with the actual active users");

					}

				}

			}

		} catch (Exception e) {
			log4j.info("verfyUsrCountOfActiveandInactiveUsers fuction " + e);
			strErrorMsg = "verfyUsrCountOfActiveandInactiveUsers fuction " + e;
		}
		return strErrorMsg;

	}
	
	/*******************************************************************
	 'Description :Verify fileds in user profile section in create user page
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-Dec-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '5-Dec-2012                               <Name>
	 ********************************************************************/
	 
	public String varUsrProfileFldsInCreateUserPge(Selenium selenium)
			throws Exception {

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
				assertEquals(selenium.getText("//div[@id='mainContainer']/"
						+ "form/table[2]/thead/tr/th"), "1. User Profile");
				log4j.info("1. User Profile field is displayed");

				intNum++;

				assertTrue(selenium.getText(
						"//div[@id='mainContainer']"
								+ "/form/table[2]/tbody/tr[1]/td[1]").matches(
						"Username:\\**"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr[1]"
								+ "/td[1]/following-sibling::td[1]/input[1][@name='userID']"));
				log4j.info("1. User Name field is displayed");

				intNum++;

				assertTrue(selenium.getText(
						"//div[@id='mainContainer']"
								+ "/form/table[2]/tbody/tr[2]/td[1]").matches(
						"Initial Password: \\**"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr[2]"
								+ "/td[1]/following-sibling::td[1]/input[1][@name='newPass']"));
				log4j.info("2. User New password field is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[3]/td[1]")
						.matches("Confirm Password: \\**"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr[3]/td[1]/"
								+ "following-sibling::td[1]/input[1][@name='confirmPass']"));
				log4j.info("3. User Confirm password field is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[4]/td[1]")
						.matches("Full Name:\\**"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/"
								+ "tr[4]/td[1]/following-sibling::td[1]/input[1][@name='fullName']"));
				log4j.info("4. User Full name field is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[5]/td[1]")
						.matches("First Name:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[5]/td[1]/following-sibling::td[1]/input[1][@name='firstName']"));
				log4j.info("5. User First name field is displayed");
				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[6]/td[1]")
						.matches("Middle Name:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[6]/td[1]/following-sibling::td[1]/input[1][@name='middleName']"));
				log4j.info("6. User Middle Name: field is displayed");
				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[7]/td[1]")
						.matches("Last Name:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[7]/td[1]/following-sibling::td[1]/input[1][@name='lastName']"));
				log4j.info("7. User Last Name: field is displayed");
				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[8]/td[1]")
						.matches("Organization:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[8]/td[1]/following-sibling::td[1]/input[1][@name='organization']"));
				log4j.info("8. User organization field is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[9]/td[1]")
						.matches("Contact Phone:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[9]/td[1]/following-sibling::td[1]/input[1][@name='phone']"));
				log4j.info("9. User Contact Phone: field is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[10]/td[1]")
						.matches("Primary E-Mail:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[10]/td[1]/following-sibling::td[1]/input[1][@name='primaryEMail']"));
				log4j.info("10. User Primary E-Mail: field is displayed");

				intNum++;

				assertEquals(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[11]/td[1]"),
								"E-Mail Addresses (comma separate multiple addresses):");
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/" +
								"tbody/tr[11]/td[1]/following-sibling::td[1]/textarea[@name='eMail']"));
				log4j
						.info("11. User E-Mail Addresses (comma separate multiple addresses): field is displayed");

				intNum++;

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[12]/td[1]/following-sibling::td[1]/textarea[1][@name='textPager']"));
				log4j.info("12. User TextPager Addresses  field is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[2]/tbody/tr[13]/td[1]")
						.matches("Administrative Comments:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[2]/tbody/tr"
								+ "[13]/td[1]/following-sibling::td[1]/textarea[1][@name='adminComments']"));
				log4j
						.info("13. User Administrative Comments: field is displayed");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("User Profile field is NOT displayed");
					strErrorMsg = "User Profile field is NOT displayed";
					break;
				case 2:
					log4j.info("1. User name field is NOT displayed");
					strErrorMsg = "1. User name field is NOT displayed";
					break;

				case 3:
					log4j.info("2. User New password field is NOT displayed");
					strErrorMsg = "2. User New password field is NOT displayed";
					break;

				case 4:
					log4j
							.info("3. User Confirm password field is NOT displayed");
					strErrorMsg = "3. User Confirm password field is NOT displayed";
					break;

				case 5:
					log4j.info("4. User Full Name field is NOT displayed");
					strErrorMsg = "4. User Full Name field is NOT displayed";
					break;

				case 6:
					log4j.info("5. User First Name field is NOT displayed");
					strErrorMsg = "5. User First Name field is NOT displayed";
					break;

				case 7:
					log4j.info("6. User Middle Name field is NOT displayed");
					strErrorMsg = "6. User Middle Name field is NOT displayed";
					break;

				case 8:
					log4j.info("7. User Last Name field is NOT displayed");
					strErrorMsg = "7. User Last Name field is NOT displayed";
					break;

				case 9:
					log4j.info("8. User Organization field is NOT displayed");
					strErrorMsg = "8. User Organization field is NOT displayed";
					break;

				case 10:
					log4j.info("9. User Contact Phone field is NOT displayed");
					strErrorMsg = "9. User Contact Phone field is NOT displayed";
					break;

				case 11:
					log4j
							.info("10. User Primary E-Mail field is NOT displayed");
					strErrorMsg = "10. User Primary E-Mail field is NOT displayed";
					break;

				case 12:
					log4j
							.info("11. User E-Mail Addresses (comma separate multiple addresses)"
									+ " field is NOT displayed");
					strErrorMsg = "11. User E-Mail Addresses (comma separate multiple addresses)"
							+ " field is NOT displayed";
					break;

				case 13:
					log4j.info("12. User Text Pager"
							+ " field is NOT displayed");
					strErrorMsg = "12. User Text Pager"
							+ " field is NOT displayed";
					break;

				case 14:
					log4j.info("13. UserAdministrative Comments"
							+ " field is NOT displayed");
					strErrorMsg = "13. UserAdministrative Comments"
							+ " field is NOT displayed";
					break;

				}

			}

		} catch (Exception e) {
			log4j.info("varUsrProfileFldsInCreateUserPge function failed" + e);
			strErrorMsg = "varUsrProfileFldsInCreateUserPge function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/*******************************************************************
	 'Description :Verify fileds in user profile section in create user page
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-Dec-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '5-Dec-2012                               <Name>
	 ********************************************************************/
	 
	public String varUsrTypeAndRolesInCreateUserPge(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";

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
				assertEquals(selenium.getText("//div[@id='mainContainer']" +
						"/form/table[3]/thead[1]/tr/th"), "2. User Type & Roles");
				log4j.info("User Type and Role field is displayed");

				intNum++;

				assertTrue(selenium.getText(
						"//div[@id='mainContainer']/form/table[3]/tbody/tr[1]/td[1]").matches(
						"Web Services User:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[3]/tbody/" +
								"tr[1]/td[1]/following-sibling::td[1]/input[@name='webServices']" +
								"[@value='Y']"));
				log4j.info("1.Web service field is displayed");

				intNum++;

				assertTrue(selenium.getText(
						"//div[@id='mainContainer']/form/table[3]/tbody/tr[2]/td[1]").matches(
						"Roles:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[3]/tbody/tr[2]" +
								"/td[1]/following-sibling::td[1]/input[1][@name='userRoleID']"));
				log4j.info("2. User New password field is displayed");

				

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("User Type and Role field is NOT displayed");
					strErrorMsg = "User Type and Role field is NOT displayed";
					break;
				case 2:
					log4j.info("1.Web service field is NOT displayed");
					strErrorMsg = "1.Web service field is NOT displayed";
					break;
					
				case 3:
					log4j.info("2.Role field is NOT displayed");
					strErrorMsg = "2.Role field is NOT displayed";
					break;
	
				}

			}

		} catch (Exception e) {
			log4j.info("varUsrTypeAndRolesInCreateUserPge function failed" + e);
			strErrorMsg = "varUsrTypeAndRolesInCreateUserPge function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	 'Description :Verify fileds in user profile section in create user page
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-Dec-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '5-Dec-2012                               <Name>
	 ********************************************************************/
	 
	public String varViewsInCreateUserPge(Selenium selenium) throws Exception {

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
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/"
								+ "table[4]/thead/tr/th"), "3. Views");
				log4j.info("Views is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[4]/tbody/tr[1]/td[1]")
						.matches("Default View:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[4]/tbody"
								+ "/tr[1]/td[1]/following-sibling::td[1]/select[@name='defaultViewID']"));
				log4j.info("1.Default view field is displayed");

				intNum++;

				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[4]/tbody/tr[2]/td[1]")
						.matches("Views in This Region:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[4]/tbody"
								+ "/tr[2]/td[1]/following-sibling::td[1]/input[1]"
								+ "[@name='regionViewID']"));
				log4j.info("2.Views in This Region field is displayed");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("Views is NOT displayed");
					strErrorMsg = "Views is NOT displayed";
					break;
				case 2:
					log4j.info("1.Default view field NOT is displayed");
					strErrorMsg = "1.Default view field NOT is displayed";
					break;

				case 3:
					log4j.info("2.Views in This Region field is NOT displayed");
					strErrorMsg = "2.Views in This Region field is NOT displayed";
					break;

				}

			}

		} catch (Exception e) {
			log4j.info("varViewsInCreateUserPge function failed" + e);
			strErrorMsg = "varViewsInCreateUserPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	 'Description :Verify fileds in user profile section in create user page
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-Dec-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '5-Dec-2012                               <Name>
	 ********************************************************************/
	 
	public String varResourceRightsInCreateUserPge(Selenium selenium)
			throws Exception {

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
				int intCnt=0;
				   do{
				    try {

				     assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[5]/"
								+ "thead/tr/th"));
				     break;
				    }catch(AssertionError Ae){
				     Thread.sleep(1000);
				     intCnt++;
				    
				    } catch (Exception Ae) {
				     Thread.sleep(1000);
				     intCnt++;
				    }
				   }while(intCnt<60);
				intNum++;
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[5]/"
								+ "thead/tr/th"), "4. Resource Rights");
				log4j.info("Resource Rights is displayed");
				intCnt=0;
				   do{
				    try {

				     assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/form/table[5]/tbody/tr/td/"
										+ "table[@id='tbl_association']/thead/tr/th[1]"));
				     break;
				    }catch(AssertionError Ae){
				     Thread.sleep(1000);
				     intCnt++;
				    
				    } catch (Exception Ae) {
				     Thread.sleep(1000);
				     intCnt++;
				    }
				   }while(intCnt<60);
				intNum++;
				String str=selenium
				.getText("//table[@id='tbl_association']/thead/tr/th[1]").trim();
				assertTrue(str.matches("Associated With")); 

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[5]"
								+ "/tbody/tr/td/table[@id='tbl_association']/thead"
								+ "/tr/th[1]/input[@value='association']"));
				log4j.info("1.Associated With field is displayed");

				intNum++;
				str=selenium
				.getText("//table[@id='tbl_association']/thead/tr/th[2]").trim();
				assertTrue(str.matches("Update Status"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[5]"
								+ "/tbody/tr/td/table[@id='tbl_association']/thead"
								+ "/tr/th[2]/input[@value='updateRight']"));
				log4j.info("2.Update status field is displayed");

				intNum++;
				str=selenium
				.getText("//table[@id='tbl_association']/thead/tr/th[3]").trim();
				assertTrue(str.matches("Run Reports"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[5]"
								+ "/tbody/tr/td/table[@id='tbl_association']/thead"
								+ "/tr/th[3]/input[@value='reportRight']"));
				log4j.info("3.Run Reports field is displayed");

				intNum++;
				str=selenium
				.getText("//table[@id='tbl_association']/thead/tr/th[4]").trim();
				assertTrue(str.matches("View Resource"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[5]"
								+ "/tbody/tr/td/table[@id='tbl_association']/thead"
								+ "/tr/th[4]/input[@value='viewRight']"));
				log4j.info("4.View Resource field is displayed");

				intNum++;
				str=selenium
				.getText("//div[@id='mainContainer']/form/table[5]/tbody/tr/td/"
										+ "table[@id='tbl_association']/thead/tr/th[5]/a").trim();
				assertTrue(str.matches("Resource Name"));
				log4j.info("5.Resource Name field is displayed");

				intNum++;
				str=selenium
				.getText("//div[@id='mainContainer']/form/table[5]/tbody/tr/td/"
										+ "table[@id='tbl_association']/thead/tr/th[6]/a").trim();
				assertTrue(str.matches("Resource Type"));

				log4j.info("6.Resource type field is displayed");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("Resource Rights is NOT displayed");
					strErrorMsg = "Resource Rights is NOT displayed";
					break;
				case 2:
					log4j.info("1.Associated With field NOT is displayed");
					strErrorMsg = "1.Associated With field NOT is displayed";
					break;

				case 3:
					log4j.info("2.Update status field is NOT displayed");
					strErrorMsg = "2.Update status field is NOT displayed";
					break;

				case 4:
					log4j.info("3.Run Report field is NOT displayed");
					strErrorMsg = "2.Run Report field is NOT displayed";
					break;

				case 5:
					log4j.info("4.View Resource field is NOT displayed");
					strErrorMsg = "4.View Resource field is NOT displayed";
					break;

				case 6:
					log4j.info("5.Resource name field is NOT displayed");
					strErrorMsg = "5.Resource name field is NOT displayed";
					break;

				case 7:
					log4j.info("6.Resource type field is NOT displayed");
					strErrorMsg = "6.Resource type field is NOT displayed";
					break;

				}

			}

		} catch (Exception e) {
			log4j.info("varResourceRightsInCreateUserPge function failed" + e);
			strErrorMsg = "varResourceRightsInCreateUserPge function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	 'Description :Verify fileds in user profile section in create user page
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-Dec-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '5-Dec-2012                               <Name>
	 ********************************************************************/
	 
	public String varAdvanceOptnInCreateUserPge(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {

				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[7]"
								+ "/thead/tr/th"), "6. Advanced Options");
				log4j.info("Advanced Options is displayed");

			} catch (AssertionError Ae) {

				log4j.info("Advanced Options is NOT displayed");
				strErrorMsg = "Advanced Options is NOT displayed";

			}

		} catch (Exception e) {
			log4j.info("varAdvanceOptnInCreateUserPge function failed" + e);
			strErrorMsg = "varAdvanceOptnInCreateUserPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	 'Description :Verify fileds in user profile section in create user page
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :5-Dec-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '5-Dec-2012                               <Name>
	 ********************************************************************/
	 
	public String varUsrPreferenceInCreateUserPge(Selenium selenium)
			throws Exception {

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
				assertEquals(selenium
						.getText("//div[@id='mainContainer']/form/table[6]/"
								+ "thead/tr/th"), "5. User Preferences");
				log4j.info("User Preferences is displayed");

				intNum++;
				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[6]/tbody/tr[1]/td[1]")
						.matches("Receive expired status notifications:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[6]/tbody"
								+ "/tr[1]/td[1]/following-sibling::td[1]/input[1][@name="
								+ "'expiredStatusEmailInd'][@value='Y']"));

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[6]"
								+ "/tbody/tr[1]/td[1]/following-sibling::td[1]/input[3]"
								+ "[@name='expiredStatusPagerInd'][@value='Y']"));

				log4j
						.info("1.Receive expired status notifications:	 field is displayed");

			
				intNum++;
				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[6]/tbody/tr[2]/td[1]")
						.matches("Receive notifications for successful HHS HAvBED transmissions:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[6]/"
								+ "tbody/tr[2]/td[1]/following-sibling::td[1]/input[1]"
								+ "[@name='havbedSuccessEmail']"));

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[6]/"
								+ "tbody/tr[2]/td[1]/following-sibling::td[1]/input[3]"
								+ "[@name='havbedSuccessPager']"));
				

				log4j
						.info("2.Receive notifications for successful HHS HAvBED transmissions: field is displayed");

				intNum++;

				
				assertTrue(selenium
						.getText(
								"//div[@id='mainContainer']/form/table[6]/tbody/tr[3]/td[1]")
						.matches(
								"Receive notifications for failed HHS HAvBED transmissions:"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[6]/tbody/"
								+ "tr[3]/td[1]/following-sibling::td[1]/input[1]"
								+ "[@name='havbedFailureEmail']"));

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table[6]/tbody/"
								+ "tr[3]/td[1]/following-sibling::td[1]/input[3]"
								+ "[@name='havbedFailurePager']"));

				log4j
						.info("3.Receive notifications for failed HHS HAvBED transmissions	 field is displayed");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j.info("User Preferences is NOT displayed");
					strErrorMsg = "User Preferences is NOT displayed";
					break;
				case 2:
					log4j
							.info("1.Receive expired status notifications:	 field is NOT displayed");
					strErrorMsg = "1.Receive expired status notifications:	 field is NOT displayed";
					break;

				case 3:
					log4j
							.info("2.Receive notifications for successful HHS HAvBED transmissions: field is NOT displayed");
					strErrorMsg = "2.Receive notifications for successful HHS HAvBED transmissions: field is NOT displayed";
					break;

				case 4:
					log4j
							.info("3.Receive notifications for failed HHS HAvBED transmissions field is NOT displayed");
					strErrorMsg = "3.Receive notifications for failed HHS HAvBED transmissions field is NOT displayed";
					break;

				}

			}

		} catch (Exception e) {
			log4j.info("varResourceRightsInCreateUserPge function failed" + e);
			strErrorMsg = "varResourceRightsInCreateUserPge function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	
	/*********************************************************
	'Description	:Verify user details in the user list
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:6-Dec-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<date>		                               <Name>
	**********************************************************/

	public String verifyUserDetailsInUserListAlonWithHeaders(Selenium selenium,
			String strUserName, String strFullName, String strOrg,
			String strLoginTime) throws Exception {

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
						.isElementPresent("//table[@id='tblUsers']/thead/tr/th[2]/a[text()='Username']"));
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName + "']"));
				log4j
						.info("User "
								+ strUserName
								+ " Present in the User List along with heder Username");
				intNum++;

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/thead/tr/th[3]/a[text()='Full Name']"));
				assertEquals(strFullName, selenium
						.getText("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName
								+ "']/parent::tr/td[3]"));
				log4j
						.info("User Full name "
								+ strFullName
								+ " Present in the User List along with header Full Name");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/thead/tr/th[4]/a[text()='Organization']"));

				assertEquals(strOrg, selenium
						.getText("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName
								+ "']/parent::tr/td[4]"));
				log4j
						.info("User organization "
								+ strOrg
								+ " Present in the User List along with its header Organization ");

				intNum++;
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/thead/tr/th[5]/a[text()='Last Login']"));
				assertEquals(strLoginTime, selenium
						.getText("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName
								+ "']/parent::tr/td[5]"));
				log4j
						.info("User Last Login "
								+ strLoginTime
								+ " Present in the User List  along with heder Last Login");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j
							.info("User "
									+ strUserName
									+ " NOT Present in the User List along with heder Username");
					strErrorMsg = "User "
							+ strUserName
							+ " NOT Present in the User List along with heder Username";
					break;
				case 2:
					log4j
							.info("User Full name "
									+ strFullName
									+ " NOT Present in the User List along with header Full Name");
					strErrorMsg = "User Full name "
							+ strFullName
							+ " NOT Present in the User List along with header Full Name";
					break;

				case 3:
					log4j
							.info("User organization "
									+ strOrg
									+ " NOT Present in the User List along with its header Organization ");
					strErrorMsg = "User organization "
							+ strOrg
							+ " NOT Present in the User List along with its header Organization ";
					break;

				case 4:
					log4j
							.info("User Last Login "
									+ strLoginTime
									+ " NOT Present in the User List  along with heder Last Login");
					strErrorMsg = "User Last Login "
							+ strLoginTime
							+ " NOT Present in the User List  along with heder Last Login";
					break;

				}

			}

		} catch (Exception e) {
			log4j.info("verifyUserDetailsInUserListAlonWithHeaders function failed" + e);
			strErrorMsg = "verifyUserDetailsInUserListAlonWithHeaders function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/*********************************************************
	'Description	:Verify user details in the user list
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:6-Dec-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<date>		                               <Name>
	**********************************************************/

	public String verifyInUserListPgeHeadersFalseCondition(Selenium selenium)
			throws Exception {

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
				assertFalse(selenium
						.isElementPresent("//table[@id='tblUsers']/thead/tr/th/a[text()='Roles']"));

				log4j
						.info("�Roles� column is NOT available in user list 'screen'. ");
				intNum++;

				assertFalse(selenium
						.isElementPresent("//table[@id='tblUsers']/thead/tr/th/a[text()='Phone']"));

				log4j
						.info("�Phone� column is NOT available in user list 'screen'. ");

				intNum++;
				assertFalse(selenium
						.isElementPresent("//table[@id='tblUsers']/thead/tr/th/a[text()='Emails']"));

				log4j
						.info("�Emails� column is NOT available in user list 'screen'. ");

			} catch (AssertionError Ae) {
				switch (intNum) {
				case 1:
					log4j
							.info("�Roles� column is available in user list 'screen'. ");
					strErrorMsg = "�Roles� column is available in user list 'screen'. ";
					break;
				case 2:
					log4j
							.info("�Phone� column is available in user list 'screen'. ");
					strErrorMsg = "�Phone� column is available in user list 'screen'. ";
					break;

				case 3:
					log4j
							.info("�Emails� column is available in user list 'screen'. ");
					strErrorMsg = "�Emails� column is available in user list 'screen'. ";
					break;

				}

			}

		} catch (Exception e) {
			log4j
					.info("verifyInUserListPgeHeadersFalseCondition function failed"
							+ e);
			strErrorMsg = "verifyInUserListPgeHeadersFalseCondition function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	
	 
	  /*******************************************************************************************
	  ' Description: Fill CheckBoxes in UserInfo
	  ' Precondition: N/A 
	  ' Arguments: selenium, 
	  ' Returns: String 
	  ' Date: 13/12/2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	public String fillRecvExpStatusNotifinEditUsr(Selenium selenium,
			boolean blnEmailInd, boolean blnPagerInd) throws Exception {
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

			if (blnEmailInd) {
				if (selenium
						.isChecked(propElementDetails.getProperty("EditUsr.ExpireStatus.EmailId")) == false) {
					selenium.click(propElementDetails.getProperty("EditUsr.ExpireStatus.EmailId"));
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("EditUsr.ExpireStatus.EmailId"))) {
					selenium.click(propElementDetails.getProperty("EditUsr.ExpireStatus.EmailId"));
				}
			}
			if (blnPagerInd) {
				if (selenium
						.isChecked(propElementDetails.getProperty("EditUsr.ExpireStatus.PagerId")) == false) {
					selenium.click(propElementDetails.getProperty("EditUsr.ExpireStatus.PagerId"));
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("EditUsr.ExpireStatus.PagerId"))) {
					selenium.click(propElementDetails.getProperty("EditUsr.ExpireStatus.PagerId"));
				}
			}

		} catch (Exception e) {
			log4j.info("fillRecvExpStatusNotifinEditUsr function failed");
			lStrReason = "fillRecvExpStatusNotifinEditUsr function failed";
		}

		return lStrReason;
	}
	/***********************************************************************************************************
	'Description	:Check Password link is pressent under action column and 'Edit' 
	'                 and create new user option not availabe 
	'Precondition	:None
	'Arguments		:
	'Returns		:String
	'Date	 		:27-Dec-2012
	'Author			:QSG
	'----------------------------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                   <Name>
	**********************************************************************************************************/

	public String CheckPasswordPresentAndNotAvailableEditandCreateUserLink(Selenium selenium, boolean blnpassword,
			String strUserName,boolean blnedit, boolean blnCreatUser){
		
		String strReason = "";
		
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			if (blnpassword) {
				try{
					assertTrue(selenium.isElementPresent("//table/thead/tr/th[1]/ancestor::" +
						 "table/tbody/tr/td[2][text()='"+strUserName+"']/parent::tr/" +
						 "td[1]/a[1][text()='Password']"));
					log4j
					.info("'Password' link is present under Action column");
				}catch(AssertionError Ae){
					strReason = "'Password' link is not present under Action column";
					log4j.info("'Password' link is not present under Action column");
				}
			}else{
				try{
					assertFalse(selenium.isElementPresent("//table/thead/tr/th[1]/ancestor::" +
						 "table/tbody/tr/td[2][text()='"+strUserName+"']/parent::tr/" +
						 "td[1]/a[1][text()='Password']"));
					log4j
					.info("'Password' link is present under Action column");
				}catch(AssertionError Ae){
					strReason = "'Password' link is not present under Action column";
					log4j.info("'Password' link is not present under Action column");
				}
			}
					
			if (blnedit) {
				try{
					assertFalse(selenium.isElementPresent("//table/thead/tr/th[1]/ancestor::" +
						 "table/tbody/tr/td[2][text()='"+strUserName+"']/parent::tr/" +
						 "td[1]/a[1][text()='Edit']"));
					log4j
					.info("'Edit' link is not present under Action column");
				}catch(AssertionError Ae){
					strReason = "'Edit' link is present under Action column";
					log4j.info("'Edit' link is present under Action column");
				}
			}else{
				try{
					assertTrue(selenium.isElementPresent("//table/thead/tr/th[1]/ancestor::" +
							 "table/tbody/tr/td[2][text()='"+strUserName+"']/parent::tr/" +
					 "td[1]/a[1][text()='Edit']"));
					log4j
					.info("'Edit' link is not present under Action column");
				}catch(AssertionError Ae){
					strReason = "'Edit' link is present under Action column";
					log4j.info("'Edit' link is present under Action column");
				}
			}
			
			if (blnCreatUser) {
				try{
					assertFalse(selenium.isElementPresent(propElementDetails.getProperty("CreateNewUsrLink")));
					log4j.info("'Create New User' button is not present");
				}catch(AssertionError Ae){
					strReason = "'Create New User' button is present";
					log4j.info("'Create New User' button is present");
				}
			}else{
				try{
					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateNewUsrLink")));
					log4j.info("'Create New User' button is not present");
				}catch(AssertionError Ae){
					strReason = "'Create New User' button is present";
					log4j.info("'Create New User' button is present");
				}
			}
		} catch (Exception e) {
			log4j.info("CheckPasswordPresentAndNotAvailableEditandCreateUserLink function failed" + e);
			strReason = "CheckPasswordPresentAndNotAvailableEditandCreateUserLink function failed" + e;
		}
		return strReason;
	}
	/*****************************************************************************************************
	'Description : Check active and inactive user before selecting 'include inactive' check box and check
	'              inactive user after selecting 
	'Arguments   :selenium,strUserName
	'Returns     :String
	'Date        :27-Dec-2012
	'Author      :QSG
	'----------------------------------------------------------------------------------------------------
	'Modified Date                                                                      Modified By
	'                                                                                   <Name>
	*****************************************************************************************************/
	
	public String CheckInactiveAndActiveUsers(Selenium selenium,
			String strUserName, String strUserNameD, String strByRole,
			String strByResourceType, String strByUserInfo, String strNameFormat)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			strErrorMsg = objSearchUserByDiffCrteria.searchUserByDifCriteria(
					selenium, strUserName, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);

			try {
				assertEquals("", strErrorMsg);
				try {
					assertEquals(
							selenium.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
							1);
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserName + "']"));
					log4j.info("User " + strUserName
							+ " Present in the User List ");
				} catch (AssertionError Ae) {
					strErrorMsg = "User " + strUserName
							+ " NOT Present in the User List " + Ae;
					log4j.info("User " + strUserName
							+ " NOT Present in the User List " + Ae);
				}
			} catch (AssertionError Ae) {
				log4j.info("Failed to search User");
				strErrorMsg = "Failed to search User";
			}

			strErrorMsg = objSearchUserByDiffCrteria.searchUserByDifCriteria(
					selenium, strUserNameD, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);

			try {
				assertEquals("", strErrorMsg);
				try {
					assertEquals(
							selenium.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
							0);
					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserNameD + "']"));
					log4j.info("User " + strUserNameD
							+ " NOT Present in the User List ");
				} catch (AssertionError Ae) {
					strErrorMsg = "User " + strUserNameD
							+ " Present in the User List " + Ae;
					log4j.info("User " + strUserNameD
							+ " Present in the User List " + Ae);
				}
			} catch (AssertionError Ae) {
				log4j.info("Failed to search User");
				strErrorMsg = "Failed to search User";
			}

			if (selenium.isChecked(propElementDetails.getProperty("UserList.IncludeActvUsers")) == false) {
				selenium.click(propElementDetails.getProperty("UserList.IncludeActvUsers"));
			}
			log4j.info("After selecting include inactive check box");

			int intCnt = 0;

			try {
				while (selenium.isVisible(propElementDetails
						.getProperty("Reloading.Element")) && intCnt < 60) {
					intCnt++;
					Thread.sleep(100);
				}
			} catch (Exception e) {
				log4j.info(e);
			}

			strErrorMsg = objSearchUserByDiffCrteria.searchUserByDifCriteria(
					selenium, strUserNameD, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);

			try {
				assertEquals("", strErrorMsg);
				try {
					assertEquals(
							selenium.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
							1);
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserNameD + "']"));
					log4j.info("User " + strUserNameD
							+ "Present in the User List ");
				} catch (AssertionError Ae) {
					strErrorMsg = "User " + strUserNameD
							+ " NOT Present in the User List " + Ae;
					log4j.info("User " + strUserNameD
							+ " NOT Present in the User List " + Ae);
				}
			} catch (AssertionError Ae) {
				log4j.info("Failed to search User");
				strErrorMsg = "Failed to search User";
			}
		} catch (Exception e) {
			log4j.info("CheckInactiveAndActiveUsers fuction " + e);
			strErrorMsg = "CheckInactiveAndActiveUsers fuction " + e;
		}
		return strErrorMsg;
	}
	/*****************************************************************************************************
	'Description : Check active and inactive user before selecting 'include inactive' check box and check
	'              inactive user after selecting 
	'Arguments   :selenium,strUserName
	'Returns     :String
	'Date        :27-Dec-2012
	'Author      :QSG
	'----------------------------------------------------------------------------------------------------
	'Modified Date                                                                      Modified By
	'                                                                                   <Name>
	*****************************************************************************************************/

	public String VrfyUserWithSearchUser(Selenium selenium, String strUserName,
			String strByRole, String strByResourceType, String strByUserInfo,
			String strNameFormat) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Users List' page is displayed ");

				strErrorMsg = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

				try {
					assertEquals("", strErrorMsg);

					try {
						assertEquals(
								selenium.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
								1);

						assertTrue(selenium
								.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
										+ "td[2][text()='" + strUserName + "']"));
						log4j.info("User " + strUserName
								+ " Present in the User List ");

					} catch (AssertionError Ae) {
						strErrorMsg = "User " + strUserName
								+ " NOT Present in the User List " + Ae;
						log4j.info("User " + strUserName
								+ " NOT Present in the User List " + Ae);

					}
				} catch (AssertionError Ae) {
					log4j.info(strErrorMsg);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "'Users List' page is NOT displayed " + Ae;
				log4j.info("'Users List' page is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("VrfyUserWithSearchUser function failed" + e);
			strErrorMsg = "VrfyUserWithSearchUser function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*********************************************************
	'Description	:Provide expired status notification in create or edit user page
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:4-Feb-2013
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<date>		                               <Name>
	**********************************************************/

	public String provideExpirdStatusNotificatcion(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("EditUsr.ExpireStatus.EmailId"));
			selenium.click(propElementDetails.getProperty("EditUsr.ExpireStatus.PagerId"));
			
		} catch (Exception e) {
			log4j.info("provideExpirdStatusNotificatcion function failed" + e);
			strErrorMsg = "provideExpirdStatusNotificatcion function failed" + e;
		}
		return strErrorMsg;
	}
	/*******************************************************************
	 'Description :Verify role is selected or not
	 'Arguments   :selenium,strRoleValue
	 'Returns     :Integer
	 'Date        :28-May-2012
	 'Author      :QSG
	 '------------------------------------------------------------------
	 'Modified Date                                          Modified By
	 '28-May-2012                                            <Name>
	 ********************************************************************/

	public String checkAsteriskPresentOrNot(Selenium selenium, String strAdvacedRightValue, boolean blnPresent,
			String strAdvancedRightName) throws Exception {

		String strErrorMsg = "";// variable to store error message
		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intResult = 0;
			if (blnPresent) {
				int intPositionOfRight = selenium
						.getXpathCount(
								"//div[@id='mainContainer']/form/table[7]"
										+ "/tbody/tr[2]/td[2]/input[@value='"+strAdvacedRightValue+"']/preceding-sibling::input")
						.intValue();
				intPositionOfRight = intPositionOfRight + 1;

				int intAsteriskCount = selenium.getXpathCount(
						"//div[@id='mainContainer']/form/table[7]"
								+ "/tbody/tr[2]/td[2]/span[text()='*']")
						.intValue();
				for (int i = 1; i<=intAsteriskCount; i++) {
					intAsteriskCount = selenium.getXpathCount(
							"//div[@id='mainContainer']/form/table[7]/tbody/tr[2]/td[2]/span[text()='*']["
									+ i + "]/preceding-sibling::span")
							.intValue();
					intAsteriskCount = intAsteriskCount + 1;

					if (intAsteriskCount == intPositionOfRight) {
						intResult++;
						
					}				
				}
				if (intResult == 1) {

					log4j.info("'asterisk' (*) mark is  displayed next to right "
							+ strAdvancedRightName);
				} else {
					log4j.info("'asterisk' (*) mark is NOT displayed next to right "
							+ strAdvancedRightName);
					
				}
			} else {

				int intPositionOfRight = selenium
						.getXpathCount(
								"//div[@id='mainContainer']/form/table[7]"
										+ "/tbody/tr[2]/td[2]/input[@value='"+strAdvacedRightValue+"']/preceding-sibling::input")
						.intValue();
				intPositionOfRight = intPositionOfRight + 1;

				int intAsteriskCount = selenium.getXpathCount(
						"//div[@id='mainContainer']/form/table[7]"
								+ "/tbody/tr[2]/td[2]/span[text()='*']")
						.intValue();

				for (int i = 1; i <= intAsteriskCount; i++) {
					intAsteriskCount = selenium.getXpathCount(
							"//div[@id='mainContainer']/form/table[7]/tbody/tr[2]/td[2]/span[text()='*']["
									+ i + "]/preceding-sibling::span")
							.intValue();
					intAsteriskCount = intAsteriskCount + 1;
					
					if (intAsteriskCount == intPositionOfRight) {
						intResult++;
					}				
				}
				if (intResult == 0) {

					log4j.info("'asterisk' (*) mark is NOT displayed next to right "
							+ strAdvancedRightName);
				} else {
					log4j.info("'asterisk' (*) mark is  displayed next to right "
							+ strAdvancedRightName);
				}
			}
		} catch (Exception e) {
			log4j.info("slectAndDeselectRole function failed" + e);
			strErrorMsg = "slectAndDeselectRole function failed" + e;
		}
		return strErrorMsg;
	}
	
	/******************************************************
	 'Description :Navigate switch user page is by regadmin
	 'Arguments   :selenium,stUserName
	 'Returns     :String
	 'Date        :06-May-2013
	 'Author      :QSG
	 '-----------------------------------------------------
	 'Modified Date                             Modified By
	 '06-May-2013                             <Name>
	 ******************************************************/
	public String navSwitchUserPge(Selenium selenium, String strUserName,
			String strByRole, String strByResourceType, String strByUserInfo,
			String strNameFormat) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			strErrorMsg = objSearchUserByDiffCrteria.searchUserByDifCriteria(
					selenium, strUserName, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);

			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
					+ strUserName + "']/parent::tr/td[1]/a[text()='Switch']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					assertEquals("Region Default",
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
				assertEquals("Region Default",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Region Default' Screen of" + strUserName
						+ " is displayed ");
				log4j.info("RegAdmin is logged into the account of user "
						+ strUserName + " and has the rights of " + strUserName);
			} catch (AssertionError Ae) {

				strErrorMsg = "Region Default' Screen of" + strUserName
						+ " is NOT displayed " + Ae;
				log4j.info("Region Default' Screen of" + strUserName
						+ " is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("navSwitchUserPge function failed" + e);
			strErrorMsg = "navSwitchUserPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	/******************************************************
	 'Description :Navigate switch user page is by regadmin
	 'Arguments   :selenium,stUserName
	 'Returns     :String
	 'Date        :06-May-2013
	 'Author      :QSG
	 '-----------------------------------------------------
	 'Modified Date                             Modified By
	 '06-May-2013                             <Name>
	 ******************************************************/
	public String varAppSwitcherIsDisabledOrNot(Selenium selenium,
			boolean blnAppSwitch) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='header-selectApplication']/span[text()='EMResource']");

			if (blnAppSwitch) {
				try {
					assertTrue(selenium
							.isElementPresent("link=Account Management"));
					log4j.info("app switcher is NOT disabled ");
				} catch (AssertionError Ae) {

					strErrorMsg = "app switcher is disabled  " + Ae;
					log4j.info("app switcher is disabled ");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("link=Account Management"));
					log4j.info("app switcher is disabled ");
				} catch (AssertionError Ae) {

					strErrorMsg = "app switcher is NOT disabled  " + Ae;
					log4j.info("app switcher is NOT disabled ");
				}
			}
		} catch (Exception e) {
			log4j.info("navSwitchUserPge function failed" + e);
			strErrorMsg = "navSwitchUserPge function failed" + e;
		}
		return strErrorMsg;
	}
	/*****************************************************************
	'Description	:click password link in Userlist and Verify password is updated for user
	'Precondition	:None
	'Arguments		:selenium,strNewPassword,strUserName
	'Returns		:String
	'Date	 		:17-6-2013
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'                             <Name>
	*****************************************************************/
	
	public String changePasswordInUserList(Selenium selenium, String strNewPassword,
			String strUserName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
					+ strUserName + "']/parent::tr/td[1]/a[text()='Password']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Reset User Password", selenium.getText("css=h1"));
				log4j.info("Reset User Password page is  displayed");
				
				selenium.click("link=here");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try {

					assertEquals("Set Up Your Password", selenium
							.getText(propElementDetails.getProperty("SetUpPwd")));

					log4j.info("Set Up Your Password is displayed");

					selenium.type(propElementDetails
							.getProperty("SetUpPwd.NewUsrName"), strNewPassword);
					selenium.type(propElementDetails
							.getProperty("SetUpPwd.CofrmUsrName"), strNewPassword);

					selenium.click(propElementDetails
							.getProperty("SetUpPwd.Submit"));
					selenium.waitForPageToLoad(gstrTimeOut);
					
					int intCnt=0;
					do{
						try{
							assertEquals("Users List", selenium.getText(propElementDetails
									.getProperty("Header.Text")));
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
						assertEquals("Users List", selenium.getText(propElementDetails
								.getProperty("Header.Text")));

						log4j.info("'Users List' page is displayed ");
					} catch (AssertionError Ae) {

						strErrorMsg = "'Users List' page is NOT displayed " + Ae;
						log4j.info("'Users List' page is NOT displayed " + Ae);
					}

				} catch (AssertionError Ae) {

					strErrorMsg = "Set Up Your Password is NOT displayed" + Ae;
					log4j.info("Set Up Your Password is NOT displayed" + Ae);
				}

				
			} catch (AssertionError Ae) {

				strErrorMsg = "Reset User Password page is NOT displayed" + Ae;
				log4j.info("Reset User Password page is NOT displayed" + Ae);
			}

			
		} catch (Exception e) {
			log4j.info("changePassword function failed" + e);
			strErrorMsg = "changePassword function failed" + e;
		}
		return strErrorMsg;
	}
	/*******************************************************************************************
	' Description: Verify that sub Resource is present Resource Rights
	' Precondition:N/A 
	' Arguments: selenium,strResource,blnResRight
	' Returns:String 
	' Date:29-05-2012
	' Author:QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifySubResourceInResRights(Selenium selenium,String strResource,boolean blnPresent) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			if(blnPresent){
				if(selenium.isElementPresent("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
										+ strResource+"']")){
					log4j
					.info("Sub resource "+strResource+"is listed under 'Resource Rights' section.");
				}else{
					lStrReason = "Sub resource "+strResource+"is NOT listed under 'Resource Rights' section.";
					log4j
					.info("Sub resource "+strResource+"is NOT listed under 'Resource Rights' section.");
				}
			}else{
				if(selenium.isElementPresent("//table[@id='tbl_association']/tbody/tr/td[5][text()='"
						+ strResource+"']")==false){
					log4j
					.info("Sub resource "+strResource+"is not listed under 'Resource Rights' section.");
				}else{
					lStrReason = "Sub resource "+strResource+"is listed under 'Resource Rights' section.";
					log4j
					.info("Sub resource "+strResource+"is listed under 'Resource Rights' section.");
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: This is common function used to verify the Resource type in edit user page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 16/09/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String vrfyRTPresOrNotInUserPge(Selenium selenium,String strResourceTypeName,boolean blnPresent) throws Exception
	{
		String strReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		if(blnPresent){
			try {
				assertTrue(selenium.isElementPresent("//select[@id='tblUsers_RESOURCE_TYPE']/option[text()='"+strResourceTypeName+"']"));
				log4j.info("'"+strResourceTypeName+" is Dispalyed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'"+strResourceTypeName+" is NOT Dispalyed");
				strReason = strReason + "; " + ""+strResourceTypeName+" is NOT Dispalyed";
			}
		}
		else
		{
			try {
				assertFalse(selenium.isElementPresent("//select[@id='tblUsers_RESOURCE_TYPE']/option[text()='"+strResourceTypeName+"']"));
				log4j.info("'"+strResourceTypeName+" is NOT Dispalyed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'"+strResourceTypeName+" is Dispalyed");
				strReason = strReason + "; " + ""+strResourceTypeName+" is Dispalyed";
			}
		}
			
		return strReason;
	}
	
	//start//vrfyRTInSearchCriteriaOfEditUserPage//
	/*******************************************************************************************
	' Description: This is common function used to verify the Resource type in edit user page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 16/09/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String vrfyRTInSearchCriteriaOfEditUserPage(Selenium selenium,String strResourceTypeName,boolean blnPresent) throws Exception
	{
		String strReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		if(blnPresent){
			try {
				assertTrue(selenium.isElementPresent("//select[@id='tbl_association_RESOURCE_TYPE']/option[text()='"+strResourceTypeName+"']"));
				log4j.info("'"+strResourceTypeName+" is Dispalyed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'"+strResourceTypeName+" is NOT Dispalyed");
				strReason = strReason + "; " + ""+strResourceTypeName+" is NOT Dispalyed";
			}
		}
		else
		{
			try {
				assertFalse(selenium.isElementPresent("//select[@id='tblUsers_RESOURCE_TYPE']/option[text()='"+strResourceTypeName+"']"));
				log4j.info("'"+strResourceTypeName+" is NOT Dispalyed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'"+strResourceTypeName+" is Dispalyed");
				strReason = strReason + "; " + ""+strResourceTypeName+" is Dispalyed";
			}
		}

		return strReason;
	}
	//end//vrfyRTInSearchCriteriaOfEditUserPage//
	/*******************************************************************************************
	' Description: This is common function used to verify the Resource type in edit user page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 16/09/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String clkOnSysNotfinPrefrences(Selenium selenium, String strUserName)
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
			int intCnt = 0;
			while (selenium
					.isElementPresent("link=System Notification Preferences") == false
					&& intCnt < 60) {
				Thread.sleep(1000);
				intCnt++;
			}
			selenium.click("link=System Notification Preferences");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals(selenium.getText(propElementDetails
							.getProperty("Header.Text")),
							"System Notification Preferences for "
									+ strUserName);
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
				assertEquals(selenium.getText(propElementDetails
						.getProperty("Header.Text")),
						"System Notification Preferences for " + strUserName);
				log4j.info("System Notification Preferences page for "
						+ strUserName + "is displayed");
			} catch (AssertionError Ae) {
				log4j.info("System Notification Preferences page for "
						+ strUserName + "is NOT displayed");
				strReason = "System Notification Preferences page for "
						+ strUserName + "is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
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

	public String selAndDeselEmailInSysNotfPref(Selenium selenium,
			String strNotfyType, boolean blnSelEmail) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnSelEmail) {
				if (selenium.isChecked("//input[@name='emailInd' and @value='"
						+ strNotfyType + "']")) {

				} else {
					selenium.click("//input[@name='emailInd' and @value='"
							+ strNotfyType + "']");
				}
			} else {
				if (selenium.isChecked("//input[@name='emailInd' and @value='"
						+ strNotfyType + "']") == false) {

				} else {
					selenium.click("//input[@name='emailInd' and @value='"
							+ strNotfyType + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("selAndDeselEmailInSysNotfPref function failed" + e);
			strErrorMsg = "selAndDeselEmailInSysNotfPref function failed" + e;
		}
		return strErrorMsg;
	}
	
/*******************************************************************
  'Description  :Select and deselect Status Type in Refine Visible
                 Status Type screen.
  'Precondition :None
  'Arguments    :selenium,strStatTypeVal,blnSelStatType
  'Returns      :String
  'Date         :28-May-2012
  'Author       :QSG
  '------------------------------------------------------------------
  'Modified Date                            Modified By
  '<Date>                                <Name>
  ********************************************************************/

	public String selAndDeselPagerInSysNotfPref(Selenium selenium,
			String strNotfyType, boolean blnSelEmail) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnSelEmail) {
				if (selenium.isChecked("//input[@name='pagerInd' and @value='"
						+ strNotfyType + "']")) {

				} else {
					selenium.click("//input[@name='pagerInd' and @value='"
							+ strNotfyType + "']");
				}
			} else {
				if (selenium.isChecked("//input[@name='pagerInd' and @value='"
						+ strNotfyType + "']") == false) {

				} else {
					selenium.click("//input[@name='pagerInd' and @value='"
							+ strNotfyType + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("selAndDeselPagerInSysNotfPref function failed" + e);
			strErrorMsg = "selAndDeselPagerInSysNotfPref function failed" + e;
		}
		return strErrorMsg;
	}
	//end//vrfyRTInSearchCriteriaOfEditUserPage//
	
	//start//selAndDeselWebInSysNotfPref//
	/*******************************************************************
	  'Description  :Select and deselect web notification in system
	                 notification page
	  'Precondition :None
	  'Arguments    :selenium,strNotfyType,blnSelEmail
	  'Returns      :String
	  'Date         :28-May-2012
	  'Author       :QSG
	  '------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                <Name>
	  ********************************************************************/
	public String selAndDeselWebInSysNotfPref(Selenium selenium,
				String strNotfyType, boolean blnSelEmail) throws Exception {

			String strErrorMsg = "";// variable to store error mesage
			try {

				rdExcel = new ReadData();
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				if (blnSelEmail) {
					if (selenium.isChecked("//input[@name='webInd' and @value='"
							+ strNotfyType + "']")) {

					} else {
						selenium.click("//input[@name='webInd' and @value='"
								+ strNotfyType + "']");
					}
				} else {
					if (selenium.isChecked("//input[@name='webInd' and @value='"
							+ strNotfyType + "']") == false) {

					} else {
						selenium.click("//input[@name='webInd' and @value='"
								+ strNotfyType + "']");
					}
				}

			} catch (Exception e) {
				log4j.info("selAndDeselWebInSysNotfPref function failed" + e);
				strErrorMsg = "selAndDeselPagerInSysNotfPref function failed" + e;
			}
			return strErrorMsg;
		}
		//end//selAndDeselWebInSysNotfPref//
		
	/*******************************************************************************************
	' Description: This is common function used to verify the Resource type in edit user page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 16/09/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String savAndNavToEditUserPge(Selenium selenium) throws Exception
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
			 selenium.click(propElementDetails
						.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt=0;
			do{
				try{
					assertEquals("Edit User", selenium.getText(propElementDetails
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
				
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				assertEquals("Edit User", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Edit User' Screen is displayed ");
			} catch (AssertionError Ae) {

				lStrReason = "'Edit User' Screen  is NOT displayed " + Ae;
				log4j.info("'Edit User'  Screen  is NOT displayed " + Ae);
			}

		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//start//vrfyRTInSearchCriteriaOfUser//
	
	/*******************************************************************************************
	' Description  :Function used to verify the Resource type in Resource type under search criteria of Users
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 13/09/2013
	' Author       : Ajanta 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String vrfyRTInSearchCriteriaOfUser(Selenium selenium,
			String strResourceTypeName) throws Exception {
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
						.isElementPresent("//select[@id='tblUsers_RESOURCE_TYPE']/option[text()='"
								+ strResourceTypeName + "']"));

				log4j.info("" + strResourceTypeName + " displayed");

			} catch (AssertionError Ae) {
				log4j.info("" + strResourceTypeName + " is NOT displayed");
				strReason = "" + strResourceTypeName + " is NOT displayed" + Ae;

			}

		} catch (Exception e) {
			log4j.info("vrfyRTInSearchCriteriaOfUser function failed" + e);
			strReason = "vrfyRTInSearchCriteriaOfUser function failed" + e;
		}

		return strReason;
	}

	// end//vrfyRTInSearchCriteriaOfUser//

	//start//vrfyRTInSearchCriteriaOfEditUserPage//
	/*******************************************************************************************
	' Description : Function used to verify the Resource type in edit user page
	' Precondition: N/A 
	' Arguments   : selenium
	' Returns     : String 
	' Date        : 16/09/2013
	' Author      : Ajanta
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String vrfyRTInSearchCriteriaOfEditUserPage(Selenium selenium,
			String strResourceTypeName) throws Exception {
		String strReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertTrue(selenium
						.isElementPresent("//select[@id='tbl_association_RESOURCE_TYPE']/option[text()='"
								+ strResourceTypeName + "']"));
				log4j.info("'" + strResourceTypeName + " is Dispalyed");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'" + strResourceTypeName + " is NOT Dispalyed");
				strReason = strReason + "; " + "" + strResourceTypeName
						+ " is NOT Dispalyed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Resources.vrfyRTInSearchCriteriaOfEditUserPage failed to complete due to "
					+ strReason + "; " + e.toString();
		}

		return strReason;
	}
	// end//vrfyRTInSearchCriteriaOfEditUserPage//
	
	/***************************************************************
	'Description	:verify element is checked
	'Precondition	:None
	'Arguments		:strElementID,selenium
	'Returns		:None
	'Date	 		:25-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	public String selAndDeselAllResRights(Selenium selenium,
			boolean blnAssoRight, boolean blnUpdateRight,
			boolean blnRunRepRight, boolean blnViewRight) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnAssoRight) {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='association']")) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='association']");
				}
			} else {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='association']") == false) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='association']");
				}
			}
			if (blnUpdateRight) {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='updateRight']")) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='updateRight']");
				}
			} else {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='updateRight']") == false) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='updateRight']");
				}
			}
			if (blnRunRepRight) {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='reportRight']")) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='reportRight']");
				}
			} else {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='reportRight']") == false) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='reportRight']");
				}
			}
			if (blnViewRight) {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='viewRight']")) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='viewRight']");
				}
			} else {
				if (selenium
						.isChecked("css=input[name='SELECT_ALL'][value='viewRight']") == false) {

				} else {
					selenium.click("css=input[name='SELECT_ALL'][value='viewRight']");
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function isCheckedElement " + e;
		}
		return strReason;
	}
	/*******************************************************************************************
	' Description  : This is common function used to verify the select region page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 08/10/2013
	' Author       : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By  : 
	*******************************************************************************************/

	public String vfySelRegnPage(Selenium selenium,String strUserFullName) throws Exception
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
			assertEquals(selenium.getText("css=p.emsInstructions"),"You have access to multiple regions. Please select which one you would like to view.");
			log4j.info("'You have access to multiple regions. Please select which one you would like to view.' message is displayed.");
			
			assertEquals("Next", selenium.getValue("css=input.emsButtonText"));
			log4j.info("Next button is displayed.");
			
			assertTrue(selenium.isTextPresent("� 1998-2013 Intermedix / EMSystems"));
			log4j.info("Copyright edition '� 1998-2013 Intermedix / EMSystems is displayed at the footer");
			
			assertTrue(selenium.isElementPresent("link=Terms & Conditions"));
			log4j.info("Terms & Conditions is displayed as hyperlinks");
			
			assertTrue(selenium.isElementPresent("link=Privacy Policy"));
			log4j.info("Privacy Policy is displayed as hyperlink");
			
			assertTrue(selenium.isElementPresent("emLogo"));
			log4j.info("'intermedix emsystems' logo is displayed at the top right corner and is hyperlinked ");
			
			assertTrue(selenium.isElementPresent("css=span"));
			log4j.info("'EMResource' app switcher is displayed at the top left of the application. ");
			
			String strBuild=selenium.getText("css=td.ftMid");
			log4j.info("EMResource version and build number are: "+ strBuild );
			
			assertEquals(selenium.getText("css=td.ftRt"),strUserFullName);
			log4j.info("'EMSystem Admin' (logged in user's full name) ");
			
		}catch(AssertionError Ae)
		{
			log4j.info("All the fields are not displayed properly in select region page");
			strReason = "All the fields are not displayed properly in select region page";
		}
			
		return strReason;
	}
	/******************************************************
	 'Description :Navigate switch user page is by regadmin
	 'Arguments   :selenium,stUserName
	 'Returns     :String
	 'Date        :08-Oct-2013
	 'Author      :QSG
	 '-----------------------------------------------------
	 'Modified Date                             Modified By
	 '06-May-2013                             <Name>
	 ******************************************************/
	public String vfyAppSwitcherIsDisabledOrNotInSelRegPge(Selenium selenium,
			boolean blnAppSwitch) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("//div[@id='header-selectApplication']/span[text()='EMResource']");

			if (blnAppSwitch) {
				try {
					assertTrue(selenium
							.isElementPresent("link=Account Management"));
					log4j.info("app switcher is NOT disabled ");
				} catch (AssertionError Ae) {

					strErrorMsg = "app switcher is disabled  " + Ae;
					log4j.info("app switcher is disabled ");
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("link=Account Management"));
					log4j.info("app switcher is disabled ");
				} catch (AssertionError Ae) {

					strErrorMsg = "app switcher is NOT disabled  " + Ae;
					log4j.info("app switcher is NOT disabled ");
				}
			}
		} catch (Exception e) {
			log4j.info("navSwitchUserPge function failed" + e);
			strErrorMsg = "navSwitchUserPge function failed" + e;
		}
		return strErrorMsg;
	}
	/*******************************************************************************************
	' Description : This is common function used to save and navigating to System Notification Preferences page
	' Arguments   : selenium
	' Returns     : String 
	' Date        : 16/09/2013
	' Author      : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String savAndNavToSySNotiPrefPage(Selenium selenium,
			String strUserName) throws Exception {
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
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals(selenium.getText(propElementDetails
							.getProperty("Header.Text")),
							"System Notification Preferences for "
									+ strUserName);
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
				assertEquals(selenium.getText(propElementDetails
						.getProperty("Header.Text")),
						"System Notification Preferences for " + strUserName);
				log4j.info("System Notification Preferences page for "
						+ strUserName + "is displayed");
			} catch (AssertionError Ae) {
				log4j.info("System Notification Preferences page for "
						+ strUserName + "is NOT displayed");
				lStrReason = "System Notification Preferences page for "
						+ strUserName + "is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//start//selAndDeselWebServicesUserCheckBox//
		/*******************************************************************************************
		' Description		: select or de-select web services user check box
		' Precondition		: N/A 
		' Arguments			: selenium, blnCheck
		' Returns			: String 
		' Date				: 29/10/2013
		' Author			: Suhas
		'--------------------------------------------------------------------------------- 
		' Modified Date: 
		' Modified By: 
		*******************************************************************************************/	
		public String selAndDeselWebServicesUserCheckBox(Selenium selenium,
				boolean blnCheck) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				if (blnCheck) {
					if (selenium.isChecked("css=input[name='webServices']") == false) {
						selenium.click("css=input[name='webServices']");
						log4j.info("Web services user check box is selected");

					}
				} else {
					if (selenium.isChecked("css=input[name='webServices']")) {
						selenium.click("css=input[name='webServices']");
						log4j.info("Web services user check box is de-selected");
					}
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function selAndDeselWebServicesUserCheckBox "
						+ e.toString();
			}
			return strReason;
		}

		//end//selAndDeselWebServicesUserCheckBox//
		
	// starts//vrfyRoleInSearchCriteriaOfUser//
	/**************************************************************
	' Description  : Function used to verify the Role in drop down
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 13/09/2013
	' Author       : Ajanta 
	'--------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	****************************************************************/

	public String vrfyRoleInSearchCriteriaOfUser(Selenium selenium,
			String strRoleName, boolean blnRole) throws Exception {
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
							.isElementPresent("//select[@id='tblUsers_ROLE']/option[text()='"
									+ strRoleName + "']"));

					log4j.info("Role " + strRoleName
							+ " is displayed in Search by role dropdown");

				} catch (AssertionError Ae) {
					log4j.info("Role " + strRoleName
							+ " is NOT displayed in Search by role dropdown");
					strReason = "Role " + strRoleName
							+ " is NOT displayed in Search by role dropdown"
							+ Ae;
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='tblUsers_ROLE']/option[text()='"
									+ strRoleName + "']"));

					log4j.info("Role " + strRoleName
							+ " is NOT displayed in Search by role dropdown");

				} catch (AssertionError Ae) {
					log4j.info("Role " + strRoleName
							+ " is displayed in Search by role dropdown");
					strReason = "Role " + strRoleName
							+ " is displayed in Search by role dropdown" + Ae;
				}
			}

		} catch (Exception e) {
			log4j.info("vrfyRoleInSearchCriteriaOfUser function failed" + e);
			strReason = "vrfyRoleInSearchCriteriaOfUser function failed" + e;
		}

		return strReason;
	}

	// end//vrfyRoleInSearchCriteriaOfUser//
}