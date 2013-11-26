package com.qsgsoft.EMResource.shared;


import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/******************************************************************
' Description :This class includes common functions of Roles
' Precondition:
' Date		  :9-April-2012
' Author	  :QSG
'-----------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class Roles {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.Roles");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	String gstrTimeOut="";
	ReadData rdExcel;
	
	/***********************************************************************
	'Description	:Verify Roles list page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:9-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String navRolesListPge(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
	
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
					.getProperty("SetUP.RolesLink"));

			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt = 0;
			do {
				try {

					assertEquals("Roles List", selenium
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
				assertEquals("Roles List", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));

				log4j.info("Roles List page is displayed");
				
			} catch (AssertionError Ae) {

				strErrorMsg = "Roles List page is NOT displayed" + Ae;
				log4j.info("Roles List page is NOT displayed" + Ae);
			}
				
			
		} catch (Exception e) {
			log4j.info("navRolesListPge function failed" + e);
			strErrorMsg = "navRolesListPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
    'Description  :selecting  ST in create Role
    'Precondition :None
    'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
    '       stSTViewValue,blnSave,strSTName,strSTUpdateValue
    'Returns      :String
    'Date         :21-June-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                            Modified By
    '21-June-2012                               <Name>
    ************************************************************************/

	public String slectAndDeselectSTInCreateRole(Selenium selenium,
			boolean blnViewRightSelectAll, boolean blnUpdateRightSelectAll,
			String[][] strSTViewValue, String[][] strSTUpdateValue,
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
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails.getProperty
								("CreateNewRole.CreateRole.ViewST.SelctAll")));
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
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"))) {

				} else {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				}
			} else {
				
				int intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails.getProperty
								("CreateNewRole.CreateRole.ViewST.SelctAll")));
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
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")))
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				else {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				}

				for (int i = 0; i < strSTViewValue.length; i++) {

					if (strSTViewValue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='viewRightInd'][value='"
										+ strSTViewValue[i][0] + "']") == false) {
							selenium
									.click("css=input[name='viewRightInd'][value='"
											+ strSTViewValue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='viewRightInd'][value='"
									+ strSTViewValue[i][0] + "']")) {
						selenium.click("css=input[name='viewRightInd'][value='"
								+ strSTViewValue[i][0] + "']");
					}

				}

			}

			// Select the Status Types this Role may Update
			if (blnUpdateRightSelectAll) {

				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {

				} else {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				}
			} else {
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll")))
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				else {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				}

				for (int i = 0; i < strSTUpdateValue.length; i++) {

					if (strSTUpdateValue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='updateRightInd'][value='"
										+ strSTUpdateValue[i][0] + "']") == false) {
							selenium
									.click("css=input[name='updateRightInd'][value='"
											+ strSTUpdateValue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='updateRightInd'][value='"
									+ strSTUpdateValue[i][0] + "']")) {
						selenium
								.click("css=input[name='updateRightInd'][value='"
										+ strSTUpdateValue[i][0] + "']");
					}

				}

			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectSTInCreateRole function failed" + e);
			strErrorMsg = "slectAndDeselectSTInCreateRole function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Roles Mandatory data are provided
	'Precondition	:None
	'Arguments		:selenium,strRolesName
	'Returns		:String
	'Date	 		:9-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String rolesMandatoryFlds(Selenium selenium, String strRolesName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewRoleLink")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click(propElementDetails.getProperty("CreateNewRoleLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("Create Role", selenium
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
				assertEquals("Create Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Create Role page is displayed");

				selenium.type(propElementDetails
						.getProperty("CreateNewRole.RolesName"), strRolesName);

			} catch (AssertionError Ae) {

				strErrorMsg = "Create Role  page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}

		} catch (AssertionError Ae) {

			strErrorMsg = "Create Role page is NOT displayed" + Ae;
			log4j.info("Create Role page is NOT displayed" + Ae);
		}

		return strErrorMsg;
	}

	
	public String CreateRoleWithAllFields(Selenium selenium,
			String strRoleName, String strRoleRights[][],
			String strViewRightValue[], boolean blnViewRight,
			String updateRightValue[], boolean blnUpdateRight, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewRoleLink")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			// click on Create New Role
			selenium.click(propElementDetails.getProperty("CreateNewRoleLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Create Role", selenium
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
				assertEquals("Create Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Create Role page is displayed");
				
				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("CreateNewRole.RolesName")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				// Enter Role name
				selenium.type(propElementDetails
						.getProperty("CreateNewRole.RolesName"), strRoleName);

				// Select the Status Types this Role may view
				if (blnViewRight) {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
					} else {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
					}
					for (String strVal : strViewRightValue) {
						selenium.click("css=input[name='viewRightInd'][value='"
								+ strVal + "']");
					}
				} else {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false)
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				}

				// Select the Status Types this Role may update
				if (blnUpdateRight) {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));

					}

					for (String strVal : updateRightValue) {
						selenium
								.click("css=input[name='updateRightInd'][value='"
										+ strVal + "']");
					}
				} else {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll")) == false)
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				}

				// Select the Rights for this Role
				for (int i = 0; i < strRoleRights.length; i++) {
					if (strRoleRights[i][1].equals("true")) {
						if (selenium
								.isChecked("css=input[name='SELECT_ALL'][value='rightID']") == false)
							selenium.click("css=input[name='rightID'][value='"
									+ strRoleRights[i][0] + "']");
					} else if (strRoleRights[i][1].equals("false")) {
						if (selenium
								.isChecked("css=input[name='SELECT_ALL'][value='rightID']"))
							selenium.click("css=input[name='rightID'][value='"
									+ strRoleRights[i][0] + "']");
					}

				}

				if (blnSave) {
					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					do {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
											+ strRoleName + "']"));
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
						assertEquals("Roles List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
										+ strRoleName + "']"));
						log4j.info("Role " + strRoleName
								+ " is displayed in 'Roles List' page.");
					} catch (AssertionError Ae) {
						strErrorMsg = "Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.";
						log4j.info("Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.");
					}
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Create Role page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("CreateRoleWithAllFields function failed" + e);
			strErrorMsg = "CreateRoleWithAllFields function failed" + e;
		}
		return strErrorMsg;
	}

	public String CreateRoleWithAllFieldsNew(Selenium selenium,
			String strRoleName, String strRoleRights[][],
			String strViewRightValue[], boolean blnViewRight,
			String updateRightValue[], boolean blnUpdateRight, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("CreateNewRoleLink")));
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
			
			
			// click on Create New Role
			selenium.click(propElementDetails.getProperty("CreateNewRoleLink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Create Role", selenium.getText(propElementDetails
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
				assertEquals("Create Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Create Role page is displayed");
				// Enter Role name
				selenium.type(propElementDetails
						.getProperty("CreateNewRole.RolesName"), strRoleName);

				// Select the Status Types this Role may view
				if (blnViewRight) {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
						
					}else if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"))) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));

					}
					for (int i = 0; i < strViewRightValue.length; i++) {

						selenium.click("css=input[name='viewRightInd'][value='"
								+ strViewRightValue[i] + "']");
					}
				}
				

				// Select the Status Types this Role may update
				if (blnUpdateRight) {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));

					}
					for (int i = 0; i < updateRightValue.length; i++) {

						selenium
								.click("css=input[name='updateRightInd'][value='"
										+ updateRightValue[i] + "']");
					}
				}
				else{
					
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));

					}
					
				}
				// Select the Rights for this Role
				for (int i = 0; i < strRoleRights.length; i++) {
					if (strRoleRights[i][1].equals("true")) {
						if (selenium
								.isChecked("css=input[name='SELECT_ALL'][value='rightID']") == false)
							selenium.click("css=input[name='rightID'][value='"
									+ strRoleRights[i][0] + "']");
					} else if (strRoleRights[i][1].equals("false")) {
						if (selenium
								.isChecked("css=input[name='SELECT_ALL'][value='rightID']"))
							selenium.click("css=input[name='rightID'][value='"
									+ strRoleRights[i][0] + "']");
					}

				}

				if (blnSave) {
					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt=0;
					do{
						try{
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
											+ strRoleName + "']"));
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
						assertEquals("Roles List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
										+ strRoleName + "']"));
						log4j.info("Role " + strRoleName
								+ " is displayed in 'Roles List' page.");
					} catch (AssertionError Ae) {
						strErrorMsg = "Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.";
						log4j.info("Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.");
					}
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Create Role page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("CreateRoleWithAllFields function failed" + e);
			strErrorMsg = "CreateRoleWithAllFields function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Roles Mandatory data are edited
	'Precondition	:None
	'Arguments		:selenium,strRolesName
	'Returns		:String
	'Date	 		:9-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String editRolesMandatoryFlds(Selenium selenium,
			String strRolesName, String strEditRolesName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
							+ strRolesName
							+ "']/parent::tr/td[1]/a[text()='edit']");

			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Edit Role page is displayed");

				selenium.type(propElementDetails
						.getProperty("CreateNewRole.RolesName"),
						strEditRolesName);

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Role page is NOT displayed" + Ae;
				log4j.info("Edit Role page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("editRolesMandatoryFlds function failed" + e);
			strErrorMsg = "editRolesMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}

	 /***********************************************************************
	 'Description :Verify Roles is saved and displayed in Role list page
	 'Precondition :None
	 'Arguments  :selenium,strRolesName
	 'Returns  :String
	 'Date    :23-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '6-April-2012                               <Name>
	 ************************************************************************/
	 
	public String savAndVerifyRoles(Selenium selenium, String strRolesName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails.getProperty("CreateNewRole.EditRole.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt = 0;
			do {
				try {

					assertEquals("Roles List", selenium
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
				assertEquals("Roles List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Roles List page is displayed");
				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/"
								+ "table/tbody/tr/td[3][text()='"
								+ strRolesName + "']"));
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
									+ "table/tbody/tr/td[3][text()='"
									+ strRolesName + "']"));

					log4j.info("Role is displayed in the role list Page");

				} catch (AssertionError Ae) {

					log4j.info("Role is NOT displayed in the role list Page");
					log4j.info(Ae);

					strErrorMsg = "Role is NOT displayed in the role list Page";

				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Roles List page is NOT displayed" + Ae;
				log4j.info("Roles List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("savAndVerifyRoles function failed" + e);
			strErrorMsg = "savAndVerifyRoles function failed" + e;
		}
		return strErrorMsg;
	}

	public String navEditRolesPge(Selenium selenium, String strRoleName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
							+ strRoleName
							+ "']/parent::tr/td[1]/a[text()='edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Edit Role page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Role page is NOT displayed" + Ae;
				log4j.info("Edit Role page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navEditRolesPge function failed" + e);
			strErrorMsg = "navEditRolesPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	 'Description :Verify navigate to Create Role Page page
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :23-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '6-April-2012                               <Name>
	 ************************************************************************/
	 
	public String navCreateRolePge(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt=0;
			 while(selenium.isElementPresent(propElementDetails.getProperty("CreateNewRoleLink"))==false && intCnt<20){
			    intCnt++;
			    Thread.sleep(3000);
			 }
			selenium.click(propElementDetails.getProperty("CreateNewRoleLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			 intCnt=0;
			 while(selenium.isElementPresent(propElementDetails
						.getProperty("Header.Text"))==false && intCnt<20){
			    intCnt++;
			    Thread.sleep(3000);
			 }
			try {
				assertEquals("Create Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Create Role page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Create Role page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navCreateRolePge function failed" + e);
			strErrorMsg = "navCreateRolePge function failed" + e;
		}
		return strErrorMsg;
	}

	 /********************************************************************************************************
		'Description	:Fetch Role Value in Role List page
		'Precondition	:None
		'Arguments		:selenium,strRole
		'Returns		:String
		'Date	 		:8-June-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'<Date>                                    <Name>
		***********************************************************************************************************/
	public String fetchRoleValueInRoleList(Selenium selenium, String strRole)
			throws Exception {
		String strRoleValue = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strRole + "']/parent::tr/td[1]/a"));
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
							+ strRole + "']/parent::tr/td[1]/a@href").split(
					"roleID=");
			strRoleValue = strResValueArr[1];
			log4j.info("Role Value is " + strRoleValue);
		} catch (Exception e) {
			log4j.info("fetchRoleValueInRoleList function failed" + e);
			strRoleValue = "";
		}
		return strRoleValue;
	}

	/****************************************************************
	'Description	:Verify  Status Types this Role may view check
	'				 boxes are selected
	'Precondition	:None
	'Arguments		:selenium,strSTView,strSTUpdate,blnSTView,
	'				 blnSTUpdate,strSTViewValue,strSTUpdateValue
	'Returns		:String
	'Date	 		:3-May-2012
	'Author			:QSG
	'----------------------------------------------------------------
	'Modified Date                            Modified By
	'3-May-2012                               <Name>
	*****************************************************************/
	
	/*public String roleViewUpdate(Selenium selenium, String strSTView,
			boolean blnSTView, String strSTUpdate, boolean blnSTUpdate,
			String strSTViewValue, String strSTUpdateValue)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if (strSTView != "" ) {
				
				if(blnSTView){
					if (selenium.isChecked("css=input[name='viewRightInd'][value='"
							+ strSTViewValue + "']") == false) 
						selenium.click("css=input[name='viewRightInd'][value='"
								+ strSTViewValue + "']");
					
				}else{
					selenium.click("css=input[name='viewRightInd'][value='"
							+ strSTViewValue + "']");
				}		
			}
			

			if (strSTUpdate != "") {

				if (blnSTUpdate) {
					if (selenium
							.isChecked("css=input[name='updateRightInd'][value='"
									+ strSTUpdateValue + "']") == false)
						selenium
								.click("css=input[name='updateRightInd'][value='"
										+ strSTUpdateValue + "']");

				} else {
					selenium.click("css=input[name='updateRightInd'][value='"
							+ strSTUpdateValue + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("role View Update function failed" + e);
			strErrorMsg = "role View Update function failed" + e;
		}
		return strErrorMsg;
	}*/
		
		
		/*******************************************************************
	    'Description  :selecting  ST in create Role
	    'Precondition :None
	    'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
	    '       stSTViewValue,blnSave,strSTName,strSTUpdateValue
	    'Returns      :String
	    'Date         :21-June-2012
	    'Author       :QSG
	    '-----------------------------------------------------------------------
	    'Modified Date                            Modified By
	    '21-June-2012                               <Name>
	    ************************************************************************/

	public String varSTInEditRolePage(Selenium selenium, String strSTViewValue,
			boolean blnView, String strSTUpdateValue, boolean blnUpdate,
			String strStatusType, String strRolesName, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnView) {

				assertTrue(selenium
						.isChecked("css=input[name='viewRightInd'][value='"
								+ strSTViewValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is selected under �select the Status Types this Role may view�"
								+ "section in the �Edit Role� screen of "
								+ strRolesName + "");
			} else {
				assertFalse(selenium
						.isChecked("css=input[name='viewRightInd'][value='"
								+ strSTViewValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is NOT selected under �select the Status Types this Role may view�"
								+ "section in the �Edit Role� screen of "
								+ strRolesName + "");
			}

			if (blnUpdate) {

				assertTrue(selenium
						.isChecked("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is selected  under "
								+ "�Select the Status Types this Role may update� section in the �Edit Role� screen of "
								+ strRolesName + "");
			} else {
				assertFalse(selenium
						.isChecked("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is  NOT selected under"
								+ "�Select the Status Types this Role may update� section in the �Edit Role� screen of "
								+ strRolesName + "");
			}
			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectSTInCreateRole function failed" + e);
			strErrorMsg = "slectAndDeselectSTInCreateRole function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
    'Description  :selecting  ST in create Role
    'Precondition :None
    'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
    '       stSTViewValue,blnSave,strSTName,strSTUpdateValue
    'Returns      :String
    'Date         :21-June-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                            Modified By
    '21-June-2012                               <Name>
    ************************************************************************/

	public String varSTInEditRolePageForViewSection(Selenium selenium,
			String strSTViewValue, boolean blnView, String strStatusType,
			boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnView) {

				assertTrue(selenium
						.isChecked("css=input[name='viewRightInd'][value='"
								+ strSTViewValue + "']"));
				assertTrue(selenium
						.isEditable("css=input[name='viewRightInd'][value='"
								+ strSTViewValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is checked and enabled under �select the Status Types this Role may view� section.");
			} else {
				assertFalse(selenium
						.isChecked("css=input[name='viewRightInd'][value='"
								+ strSTViewValue + "']"));
				assertFalse(selenium
						.isEditable("css=input[name='viewRightInd'][value='"
								+ strSTViewValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is Unchecked And disabled under �select the Status Types this Role may view�section.");
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectSTInCreateRole function failed" + e);
			strErrorMsg = "slectAndDeselectSTInCreateRole function failed" + e;
		}
		return strErrorMsg;
	}

/*******************************************************************
'Description  :selecting  ST in create Role
'Precondition :None
'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
'       stSTViewValue,blnSave,strSTName,strSTUpdateValue
'Returns      :String
'Date         :21-June-2012
'Author       :QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'21-June-2012                               <Name>
************************************************************************/

	public String varSTInEditRolePageForUpdateSec(Selenium selenium,
			String strSTUpdateValue, boolean blnUpdate, String strStatusType,
			boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnUpdate) {

				assertTrue(selenium
						.isChecked("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				assertTrue(selenium
						.isEditable("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is checked  and enabled under �select the Status Types this Role may update�section.");
			} else {
				assertFalse(selenium
						.isChecked("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				assertFalse(selenium
						.isEditable("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				log4j
						.info("Status type"
								+ strStatusType
								+ "is Unchecked  And disabled under �select " +
										"the Status Types this Role may update�section.");
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("slectAndDeselectSTInCreateRole function failed" + e);
			strErrorMsg = "slectAndDeselectSTInCreateRole function failed" + e;
		}
		return strErrorMsg;
	}

/***********************************************************************
'Description	:Verify Roles Mandatory data are provided
'Precondition	:None
'Arguments		:selenium,strRolesName
'Returns		:String
'Date	 		:9-April-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'6-April-2012                               <Name>
************************************************************************/

	public String roleMandtoryFlds(Selenium selenium, String strRolesName)
			throws Exception {

		String strErrorMsg = "";// variable to store error message

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				assertEquals("", strErrorMsg);

				selenium.type(propElementDetails
						.getProperty("CreateNewRole.RolesName"), strRolesName);

			} catch (AssertionError Ae) {

				strErrorMsg = "Create Role page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("rolesMandatoryFlds function failed" + e);
			strErrorMsg = "rolesMandatoryFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/*******************************************************************
    'Description  :selecting  ST in create Role
    'Precondition :None
    'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
    '       stSTViewValue,blnSave,strSTName,strSTUpdateValue
    'Returns      :String
    'Date         :21-June-2012
    'Author       :QSG
    '-----------------------------------------------------------------------
    'Modified Date                            Modified By
    '21-June-2012                               <Name>
    ************************************************************************/

public String varSTPresentOrNotInEditRolePage(Selenium selenium, String strSTViewValue,
		boolean blnView, String strSTUpdateValue, boolean blnUpdate,
		String strStatusType, boolean blnSave)
		throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		if (blnView) {

			assertTrue(selenium
					.isElementPresent("css=input[name='viewRightInd'][value='"
							+ strSTViewValue + "']"));
			log4j.info("Status type"
					+ strStatusType
					+ "is Displayed under �select the Status Types this Role may view�section.");
		} else {
			assertFalse(selenium
					.isElementPresent("css=input[name='viewRightInd'][value='"
							+ strSTViewValue + "']"));
			log4j.info("Status type"
							+ strStatusType
							+ "is NOT Displayed under �select the Status Types this Role may view�section.");
		}

		if (blnUpdate) {

			assertTrue(selenium
					.isElementPresent("css=input[name='updateRightInd'][value='"
							+ strSTUpdateValue + "']"));
			log4j.info("Status type"
					+ strStatusType
					+ "is displayed under"
					+ "�Select the Status Types this Role may update� section.");
		} else {
			assertFalse(selenium
					.isElementPresent("css=input[name='updateRightInd'][value='"
							+ strSTUpdateValue + "']"));
			log4j
					.info("Status type"
							+ strStatusType
							+ "is NOT  displayed under"
							+ "�Select the Status Types this Role may update� section.");
		}
		if (blnSave) {
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
		}

	} catch (Exception e) {
		log4j.info("slectAndDeselectSTInCreateRole function failed" + e);
		strErrorMsg = "slectAndDeselectSTInCreateRole function failed" + e;
	}
	return strErrorMsg;
}


/*******************************************************************
'Description  :selecting  ST in create Role
'Precondition :None
'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
'       stSTViewValue,blnSave,strSTName,strSTUpdateValue
'Returns      :String
'Date         :21-June-2012
'Author       :QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'21-June-2012                               <Name>
************************************************************************/

public String varSTDisabilityInEditRolePgeForViewSec(Selenium selenium,
		String strSTViewValue, boolean blnView, String strStatusType,
		boolean blnSave) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	try {

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		if (blnView) {
			assertTrue(selenium
					.isEditable("css=input[name='viewRightInd'][value='"
							+ strSTViewValue + "']"));
			log4j
					.info("Status type"
							+ strStatusType
							+ "is enabled under �select the Status Types this Role may view� section.");
		} else {
			assertFalse(selenium
					.isEditable("css=input[name='viewRightInd'][value='"
							+ strSTViewValue + "']"));
			log4j
					.info("Status type"
							+ strStatusType
							+ "is disabled under �select the Status Types this Role may view�section.");
		}
		if (blnSave) {
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
		}

	} catch (Exception e) {
		log4j.info("slectAndDeselectSTInCreateRole function failed" + e);
		strErrorMsg = "slectAndDeselectSTInCreateRole function failed" + e;
	}
	return strErrorMsg;
}


/*******************************************************************
'Description  :selecting  ST in create Role
'Precondition :None
'Arguments    :selenium,blnViewRightSelectAll,blnUpdateRightSelectAll,
'       stSTViewValue,blnSave,strSTName,strSTUpdateValue
'Returns      :String
'Date         :21-June-2012
'Author       :QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'21-June-2012                               <Name>
************************************************************************/

	public String varSTDisabilityInEditRolePgeForUpdateSec(Selenium selenium,
			String strSTUpdateValue, boolean blnUpdate, String strStatusType,
			boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnUpdate) {
				assertTrue(selenium
						.isEditable("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				log4j.info("Status type"
								+ strStatusType
								+ "is enabled under �select the Status Types this Role may update�section.");
			} else {
				assertFalse(selenium
						.isEditable("css=input[name='updateRightInd'][value='"
								+ strSTUpdateValue + "']"));
				log4j.info("Status type"
								+ strStatusType
								+ "is  disabled under �select " +
										"the Status Types this Role may update�section.");
			}
			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("varSTDisabilityInEditRolePgeForUpdateSec function failed" + e);
			strErrorMsg = "varSTDisabilityInEditRolePgeForUpdateSec function failed" + e;
		}
		return strErrorMsg;
	}
	
	public String deselectAllSTValue(Selenium selenium, String strRoleName,
			boolean blndeselctView, boolean blndeselctUpdate, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blndeselctView) {
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false) {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				} else {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				}

			}

			if (blndeselctUpdate) {
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				}
			}

			if (blnSave) {
				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Roles List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strRoleName + "']"));
					log4j.info("Role " + strRoleName
							+ " is displayed in 'Roles List' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Role " + strRoleName
							+ " is NOT displayed in 'Roles List' page.";
					log4j.info("Role " + strRoleName
							+ " is NOT displayed in 'Roles List' page.");
				}
			}

		} catch (Exception e) {
			log4j.info("deselectAllSTValue function failed" + e);
			strErrorMsg = "deselectAllSTValue function failed" + e;
		}
		return strErrorMsg;
	}
	
	public String onlyViewRiteToRole(Selenium selenium, String strRoleName,
			String strViewRightValue[], boolean blnViewRightfoParticularST,
			boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Select the Status Types this Role may view
			if (blnViewRightfoParticularST) {
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false) {
					selenium
					.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
			        selenium
					.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				} else {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")))
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));

				}
				for (String strVal : strViewRightValue) {
					selenium.click("css=input[name='viewRightInd'][value='"
							+ strVal + "']");
				}
			}

			if (blnSave) {
				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Roles List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strRoleName + "']"));
					log4j.info("Role " + strRoleName
							+ " is displayed in 'Roles List' page.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Role " + strRoleName
							+ " is NOT displayed in 'Roles List' page.";
					log4j.info("Role " + strRoleName
							+ " is NOT displayed in 'Roles List' page.");
				}
			}

		} catch (Exception e) {
			log4j.info("onlyViewRiteToRole function failed" + e);
			strErrorMsg = "onlyViewRiteToRole function failed" + e;
		}
		return strErrorMsg;
	}
	
	public String onlyUpdateRiteToRole(Selenium selenium,
			String strRoleName,
			String updateRightValue[], boolean blnUpdateRight,boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on Create New Role
			selenium.click(propElementDetails.getProperty("CreateNewRoleLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			// Select the Status Types this Role may update
			if (blnUpdateRight) {
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));

				} else {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll")) == false)
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				}

				for (String strVal : updateRightValue) {
					selenium.click("css=input[name='updateRightInd'][value='"
							+ strVal + "']");
				}
			}else{
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll")))
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
			}


				if (blnSave) {
					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Roles List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
										+ strRoleName + "']"));
						log4j.info("Role " + strRoleName
								+ " is displayed in 'Roles List' page.");
					} catch (AssertionError Ae) {
						strErrorMsg = "Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.";
						log4j.info("Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.");
					}
				}

		} catch (Exception e) {
			log4j.info("onlyUpdateRiteToRole function failed" + e);
			strErrorMsg = "onlyUpdateRiteToRole function failed" + e;
		}
		return strErrorMsg;
	}

	
	public String CreateRoleWithAllFieldsCorrect(Selenium selenium,
			String strRoleName, String strRoleRights[][],
			String strViewRightValue[], boolean blnViewRight,
			String updateRightValue[], boolean blnUpdateRight, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on Create New Role
			selenium.click(propElementDetails.getProperty("CreateNewRoleLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				int intCnt=0;
				   do{
				    try {

				    	assertEquals("Create Role", selenium.getText(propElementDetails
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
				assertEquals("Create Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Create Role page is displayed");
				// Enter Role name
				selenium.type(propElementDetails
						.getProperty("CreateNewRole.RolesName"), strRoleName);

				// Select the Status Types this Role may view
				if (blnViewRight) {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
					}
					for (String strVal : strViewRightValue) {
						selenium.click("css=input[name='viewRightInd'][value='"
								+ strVal + "']");
					}
				} else {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false)
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				}

				// Select the Status Types this Role may update
				if (blnUpdateRight) {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));

					}

					for (String strVal : updateRightValue) {
						selenium
								.click("css=input[name='updateRightInd'][value='"
										+ strVal + "']");
					}
				} else {
					if (selenium
							.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll")))
						selenium
								.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				}

				// Select the Rights for this Role
				for (int i = 0; i < strRoleRights.length; i++) {
					if (strRoleRights[i][1].equals("true")) {
						if (selenium
								.isChecked("css=input[name='SELECT_ALL'][value='rightID']") == false)
							selenium.click("css=input[name='rightID'][value='"
									+ strRoleRights[i][0] + "']");
					} else if (strRoleRights[i][1].equals("false")) {
						if (selenium
								.isChecked("css=input[name='SELECT_ALL'][value='rightID']"))
							selenium.click("css=input[name='rightID'][value='"
									+ strRoleRights[i][0] + "']");
					}

				}

				if (blnSave) {
					// click on save
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					intCnt=0;
					   do{
					    try {

					    	assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
											+ strRoleName + "']"));
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
						assertEquals("Roles List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
										+ strRoleName + "']"));
						log4j.info("Role " + strRoleName
								+ " is displayed in 'Roles List' page.");
					} catch (AssertionError Ae) {
						strErrorMsg = "Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.";
						log4j.info("Role " + strRoleName
								+ " is NOT displayed in 'Roles List' page.");
					}
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Create Role page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("CreateRoleWithAllFieldsCorrect function failed" + e);
			strErrorMsg = "CreateRoleWithAllFieldsCorrect function failed" + e;
		}
		return strErrorMsg;
	}

	
	/*******************************************************************************************
	' Description: Canel and navigating to RoleList page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 4/03/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String cancelAndNavToRoleListPage(Selenium selenium,
			String strRolesName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Roles List", selenium
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
				assertEquals("Roles List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[3][text()='"
									+ strRolesName + "']"));

					log4j.info("Role is NOT displayed in the role list Page");

				} catch (AssertionError Ae) {

					log4j.info("Role is displayed in the role list Page");
					log4j.info(Ae);

					lStrReason = "Role is displayed in the role list Page";

				}

				log4j.info("'Roles List' Screen is displayed ");
			} catch (AssertionError Ae) {

				lStrReason = "'Roles List' Screen  is NOT displayed " + Ae;
				log4j.info("'Roles List'  Screen  is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("Roles.cancelAndNavToRoleListPage function failed");
			lStrReason = "Roles.cancelAndNavToRoleListPage failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description: Canel and navigating to RoleList page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 4/03/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String cancelAndNavToRoleListPageNew(Selenium selenium) throws Exception {
		
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Roles List",
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
				assertEquals("Roles List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("'Roles List' Screen is displayed ");
			} catch (AssertionError Ae) {

				lStrReason = "'Roles List' Screen  is NOT displayed " + Ae;
				log4j.info("'Roles List'  Screen  is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("Roles.cancelAndNavToRoleListPageNew function failed");
			lStrReason = "Roles.cancelAndNavToRoleListPageNew failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description: Function to select or deselect 'Select All' checkbox in roles page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 4/03/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String selectOrDeselectAllSTValue(Selenium selenium, String strRoleName,
			boolean blndeselctView, boolean blndeselctUpdate, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blndeselctView) {
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll")) == false) {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				} else {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.ViewST.SelctAll"));
				}

			}

			if (blndeselctUpdate) {
				if (selenium
						.isChecked(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"))) {
					selenium
							.click(propElementDetails.getProperty("CreateNewRole.CreateRole.UpdateST.SelctAll"));
				}
			}

			if (blnSave) {
				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
			}

		} catch (Exception e) {
			log4j.info("deselectAllSTValue function failed" + e);
			strErrorMsg = "deselectAllSTValue function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description: Function to select or deselect 'Select All' checkbox in roles page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 4/03/2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	
	public String vfyErrMsgForRoleMayView(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Create Role", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Create Role page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Create Role page is NOT displayed" + Ae;
				log4j.info("Create Role page is NOT displayed" + Ae);
			}
			
			try{
				assertEquals(selenium.getText("//div[@id='mainContainer']/div/span"),
						"The following error occurred on this page:");
				log4j.info("Error Message header is displayed");
				
			}catch (AssertionError Ae) {
				log4j.info("Error Message header is not displayed" + Ae);
				strErrorMsg = "Error Message header is not displayed" + Ae;
			}
			
			try{
				assertEquals(selenium.getText("//div[@id='mainContainer']/div/ul/li"),
						"Each updatable status type must also be viewable.");
				log4j.info("Warning message stating 'Each updatable status type must also be viewable' is displayed and user is retained in 'Create role' page.");
				
			}catch (AssertionError Ae) {
				log4j.info("Warning message stating 'Each updatable status type must also be viewable' is not displayed and user is not retained in 'Create role' page." + Ae);
				strErrorMsg = "Warning message stating 'Each updatable status type must also be viewable' is not displayed and user is not retained in 'Create role' page." + Ae;
			}
			
			

		} catch (Exception e) {
			log4j.info("deselectAllSTValue function failed" + e);
			strErrorMsg = "deselectAllSTValue function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/**************************************************************
	' Description  : Function to select or set role as default role 
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 4/03/2012
	' Author       : QSG 
	'-------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	***************************************************************/
	
	public String setRoleAsDefaultRole(Selenium selenium, String strRoleName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
							+ strRoleName
							+ "']/parent::tr/td[1]/a[text()='default']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody" +
						"/tr/td[3][text()='"
							+ strRoleName
							+ "']/parent::tr/td[2][text()='Yes']"));

				log4j.info("Yes is displayed for a role "+strRoleName+" in default coloumn");

			} catch (AssertionError Ae) {

				strErrorMsg = "Yes is NOT displayed for a role "+strRoleName+" in default coloumn" + Ae;
				log4j.info("Yes is NOT displayed for a role "+strRoleName+" in default coloumn" + Ae);
			}

		} catch (Exception e) {
			log4j.info("setRoleToDefault function failed" + e);
			strErrorMsg = "setRoleToDefault function failed" + e;
		}
		return strErrorMsg;
	}
	
	/**************************************************************
	' Description  : Function to delete role 
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 4/03/2012
	' Author       : QSG 
	'-------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	***************************************************************/

	public String deleteRole(Selenium selenium, String strRoleName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
					+ strRoleName + "']/parent::tr/td[1]/a[text()='delete']");

			boolean blnFound = false;
			int intCnt = 0;
			// wait for the confirmation
			while (blnFound == false && intCnt <= 60) {
				try {
					assertTrue(selenium
							.getConfirmation()
							.matches(
									"^This role will be permanently deleted and cannot be recovered\\. Are you sure[\\s\\S]$"));
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

			intCnt = 0;
			do {
				try {

					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[3][text()='"
									+ strRoleName + "']"));
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
						.isElementPresent("//div[@id='mainContainer']/"
								+ "table/tbody/tr/td[3][text()='" + strRoleName
								+ "']"));
				log4j.info("Role " + strRoleName
						+ " is NOT displayed in the role list Page");

			} catch (AssertionError Ae) {

				log4j.info("Role " + strRoleName
						+ " is displayed in the role list Page");
				log4j.info(Ae);

				strErrorMsg = "Role " + strRoleName
						+ " is displayed in the role list Page";
			}
		} catch (Exception e) {
			log4j.info("deleteRole function failed" + e);
			strErrorMsg = "deleteRole function failed" + e;
		}
		return strErrorMsg;
	}
	
	/**************************************************************
	' Description  : Function to select or set role as default role 
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 4/03/2012
	' Author       : QSG 
	'-------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	***************************************************************/

	public String chkDefaultLinkAvailOrNotForRole(Selenium selenium,
			String strRoleName, boolean blnDefault) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnDefault) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strRoleName
									+ "']/parent::tr/td[1]/a[text()='default']"));
					log4j.info("'Default' link is available corresponding to a role "
							+ strRoleName + " under 'Action' column ");

				} catch (AssertionError Ae) {

					strErrorMsg = "'Default' link is NOT available corresponding to a role "
							+ strRoleName + " under 'Action' column "
							+ Ae;
					log4j.info("'Default' link is NOT available corresponding to a role "
							+ strRoleName + " under 'Action' column "
							+ Ae);
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strRoleName
									+ "']/parent::tr/td[1]/a[text()='default']"));
					log4j.info("'Default' link is NOT available corresponding to a role "
							+ strRoleName + " under 'Action' column ");

				} catch (AssertionError Ae) {

					strErrorMsg = "'Default' link is available corresponding to a role "
							+ strRoleName + " under 'Action' column "
							+ Ae;
					log4j.info("'Default' link is available corresponding to a role "
							+ strRoleName + " under 'Action' column "
							+ Ae);
				}
			}

		} catch (Exception e) {
			log4j.info("chkDefaultLinkAvailOrNotForRole function failed" + e);
			strErrorMsg = "chkDefaultLinkAvailOrNotForRole function failed" + e;
		}
		return strErrorMsg;
	}
	
	/**************************************************************
	' Description  : Function to select or set role as default role 
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 4/03/2012
	' Author       : QSG 
	'-------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	***************************************************************/

	public String chkYesOrNoForRoleInDefaultCol(Selenium selenium,
			String strRoleName, boolean blnRoleYesORNo) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnRoleYesORNo) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody"
									+ "/tr/td[3][text()='"
									+ strRoleName
									+ "']/parent::tr/td[2][text()='Yes']"));
					log4j.info("Yes' is displayed for " + strRoleName
							+ " under 'Default?' column.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Yes' is NOT displayed for " + strRoleName
							+ " under 'Default?' column." + Ae;
					log4j.info("Yes' is displayed for " + strRoleName
							+ " under 'Default?' column." + Ae);
				}
			} else {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody"
									+ "/tr/td[3][text()='"
									+ strRoleName
									+ "']/parent::tr/td[2][text()='No']"));
					log4j.info("No' is displayed for " + strRoleName
							+ " under 'Default?' column.");
				} catch (AssertionError Ae) {
					strErrorMsg = "No'  NOTis displayed for " + strRoleName
							+ " under 'Default?' column." + Ae;
					log4j.info("No' is  NOT displayed for " + strRoleName
							+ " under 'Default?' column." + Ae);
				}
			}
		} catch (Exception e) {
			log4j.info("chkYesOrNoForRoleInDefaultCol function failed" + e);
			strErrorMsg = "chkYesOrNoForRoleInDefaultCol function failed" + e;
		}
		return strErrorMsg;
	}
	
	/**************************************************************
	' Description  : Function to delete role 
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 4/03/2012
	' Author       : QSG 
	'-------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	***************************************************************/

	public String cancelDeleteRole(Selenium selenium, String strRoleName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		String strProcess="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		Paths_Properties objPaths_Properties=new Paths_Properties();
		Properties AutoItPath= objPaths_Properties.ReadAutoit_FilePath();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			// click on cancel on role
			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
					+ strRoleName + "']/parent::tr/td[1]/a[text()='delete']");
			Thread.sleep(1000);
			String pStrRefreshPathExe=AutoItPath.getProperty("Role_CancelRole");
			
			Thread.sleep(5000);					
			Runtime.getRuntime().exec(pStrRefreshPathExe);//To refresh the page.
			int intCnt=0;
			do {
				GetProcessList objGPL = new GetProcessList();
				strProcess = objGPL.GetProcessName();
				intCnt++;
			} while (strProcess.contains("CancelRole.exe")&&intCnt<=1200);
			Thread.sleep(5000);		

			intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[3][text()='"
									+ strRoleName + "']"));
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
						.isElementPresent("//div[@id='mainContainer']/"
								+ "table/tbody/tr/td[3][text()='" + strRoleName
								+ "']"));
				log4j.info("Role " + strRoleName
						+ " is displayed in the role list Page");
				log4j.info("Role " + strRoleName
						+ " is NOT Deleted in the role list Page");

			} catch (AssertionError Ae) {

				log4j.info("Role " + strRoleName
						+ " is NOT displayed in the role list Page");
				log4j.info(Ae);

				strErrorMsg = "Role " + strRoleName
						+ " is NOT displayed in the role list Page";
			}
		} catch (Exception e) {
			log4j.info("cancelDeleteRole function failed" + e);
			strErrorMsg = "cancelDeleteRole function failed" + e;
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
	
	public String navToAssignUsersOFRole(Selenium selenium, String strRoleName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
							+ strRoleName
							+ "']/parent::tr/td[1]/a[text()='users']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Assign Users to '"+strRoleName+"' role", selenium
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
				assertEquals("Assign Users to '"+strRoleName+"' role", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Assign Users to " + strRoleName
						+ "screen is displayed ");
			} catch (AssertionError Ae) {

				strReason = "Assign Users to " + strRoleName
						+ "screen is NOT displayed" + Ae;
				log4j.info("Assign Users to " + strRoleName
						+ "screen is NOT displayed ");
			}

		} catch (Exception e) {
			log4j.info("navToAssignUsersOFRole function failed" + e);
			strReason = "navToAssignUsersOFRole function failed" + e;
		}
		return strReason;
	}
	
	//start//checkUserSelOrNotInAssignRolePage//
	/*****************************************************************
	' Description		: Check user listed or not in assign user page
	' Precondition		: N/A 
	' Arguments			: selenium, strUserName, blnPresent
	' Returns			: String 
	' Date				: 16/09/2013
	' Author			: QSG 
	'------------------------------------------------------------------ 
	' Modified Date: 
	' Modified By: 
	*******************************************************************/

	public String checkUserSelOrNotInAssignRolePage(Selenium selenium,
			String strUserName, boolean blnCheck) throws Exception {
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
				if (blnCheck) {
					assertTrue(selenium
							.isChecked("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
									+ strUserName + "']"));
					log4j.info("User "
							+ strUserName
							+ " assigned for role"
							+ " remians selected on 'Assign Users to 'role name'' role screen ");

				} else {
					assertFalse(selenium
							.isChecked("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
									+ strUserName + "']"));
					log4j.info("User "
							+ strUserName
							+ " assigned for role"
							+ " remians NOT selected on 'Assign Users to 'role name'' role screen ");
				}
			} catch (AssertionError Ae) {
				log4j.info("User '" + strUserName
						+ "' is NOT  listed in assign user Role page. ");
				strReason = "User '" + strUserName
						+ "' is NOT  listed in assign user Role page. ";
			}
		} catch (Exception e) {
			log4j.info("checkUserSelOrNotInAssignRolePage function failed" + e);
			strReason = "checkUserSelOrNotInAssignRolePage function failed";
		}

		return strReason;
	}
	// end//checkUserSelOrNotInAssignRolePage//
	
//start//checkUserPresentOrNotInAssignRolePage//
	/*****************************************************************
	' Description		: Check user listed or not in assign user page
	' Precondition		: N/A 
	' Arguments			: selenium, strUserName, blnPresent
	' Returns			: String 
	' Date				: 16/09/2013
	' Author			: QSG 
	'------------------------------------------------------------------ 
	' Modified Date: 
	' Modified By: 
	*******************************************************************/

	public String checkUserPresentOrNotInAssignRolePage(Selenium selenium,
			String strUserName, boolean blnCheck) throws Exception {
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
				if (blnCheck) {
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
									+ strUserName + "']"));
					log4j.info("The active user "
							+ strUserName
							+ " is listed on < Assign Users to ' role name' role > screen");

				} else {
					assertFalse(selenium
							.isElementPresent("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
									+ strUserName + "']"));
					log4j.info("The active user "
							+ strUserName
							+ " is NOT listed on < Assign Users to ' role name' role > screen");
				}
			} catch (AssertionError Ae) {
				log4j.info("The active user "
						+ strUserName
						+ " is NOT listed on < Assign Users to ' role name' role > screen");
				strReason = "The active user "
						+ strUserName
						+ " is NOT listed on < Assign Users to ' role name' role > screen";
			}
		} catch (Exception e) {
			log4j.info("checkUserPresentOrNotInAssignRolePage function failed"
					+ e);
			strReason = "checkUserPresentOrNotInAssignRolePage function failed";
		}

		return strReason;
	}
	// end//checkUserPresentOrNotInAssignRolePage//
	
	//start//selAndDeselUserInAssignRolePage//
	 /*******************************************************************
	  'Description :Select and deselect User in assgn role page
	  'Arguments   :selenium,strStatTypeVal,blnSelStatType
	  'Returns     :String
	  'Date        :28-May-2012
	  'Author      :QSG
	  '------------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                <Name>
	  ********************************************************************/

	public String selAndDeselUserInAssignRolePage(Selenium selenium,
			String strUserName, boolean blnUser) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnUser) {
				if (selenium
						.isChecked("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
								+ strUserName + "']")) {

				} else {
					selenium.click("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
							+ strUserName + "']");
				}
			} else {
				if (selenium
						.isChecked("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
								+ strUserName + "']") == false) {

				} else {
					selenium.click("//table[@id='tbl_userID']/tbody/tr/td/input[@value='"
							+ strUserName + "']");
				}
			}

		} catch (Exception e) {
			log4j.info("selAndDeselUserInAssignRolePage function failed" + e);
			strErrorMsg = "selAndDeselUserInAssignRolePage function failed" + e;
		}
		return strErrorMsg;
	}
		//start//selAndDeselUserInAssignRolePage//
}