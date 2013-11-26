package com.qsgsoft.EMResource.shared;


import java.util.Properties;

import org.apache.log4j.Logger;


import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/******************************************************************
' Description :This class includes common functions of Forms
' Precondition:
' Date		  :16-April-2012
' Author	  :QSG
'------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class Forms {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.Forms");

	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;
	
	public String gstrTimeOut ="";
	
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();

	ReadData rdExcel;
	
	
	/**************************************************************
	'Description	:Verify Form Security Setting page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:3-May-2012
	'Author			:QSG
	'--------------------------------------------------------------
	'Modified Date                            Modified By
	'3-May-2012                               <Name>
	***************************************************************/

	public String navFormSecuritySettingPge(Selenium selenium) throws Exception {

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

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Form_Link")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			

			selenium.mouseOver(propElementDetails.getProperty("Form_Link"));

			selenium
					.click(propElementDetails.getProperty("Forms.FormSecurity"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Form Security Settings", selenium
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
				assertEquals("Form Security Settings", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Form Security Settings page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Security Settings  page is NOT displayed"
						+ Ae;
				log4j
						.info("Form Security Settings  page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navigate to Form Security Setting Page function failed"
					+ e);
			strErrorMsg = "navigate to Form Security Setting Page function failed"
					+ e;
		}
		return strErrorMsg;
	}

	public String createQuestion(Selenium selenium, String strFormTempTitle,
			String strQuestion, String strDescription, String strquesTypeID,
			boolean blnQuesMan, boolean blnDisplay) throws Exception {

		String strErrorMsg = "";// variable to store error message
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.click(propElementDetails
					.getProperty("CreateNewQuestionButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			selenium.type(propElementDetails
					.getProperty("CreateQuestion.QuestionTitle"), strQuestion);
			selenium.type(propElementDetails
					.getProperty("CreateQuestion.QuestionDesription"),
					strDescription);
			selenium.select(propElementDetails
					.getProperty("CreateQuestion.QuestionTypeID"), "label="
					+ strquesTypeID);

			if (blnQuesMan) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestioMan")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestioMan"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestioMan")))
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestioMan"));

			}

			if (blnDisplay) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestionDisplay")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestionDisplay"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestionDisplay")))
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestionDisplay"));
			}

			selenium.click(propElementDetails
					.getProperty("CreateQuestion.SubmitButton"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Form Questions: " + strFormTempTitle, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Questions: " + strFormTempTitle
						+ " page is displayed" + Ae;
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");
			}

		} catch (Exception e) {
			log4j.info("ToCreateNewQuestion " + "function failed" + e);
			strErrorMsg = "ToCreateNewQuestion" + " function failed" + e;
		}

		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify Form Security Setting page is displayed
	'Precondition	:None
	'Arguments		:selenium,strUserName,strFormTemplateName,blnActivateForm,
	'				 blnRunReport,blnCarteBlanche
	'Returns		:String
	'Date	 		:3-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-May-2012                               <Name>
	************************************************************************/
	
	public String selectUserInFormSettingPage(Selenium selenium,
			String strUserName, String strFormTemplateName,
			boolean blnActivateForm, boolean blnRunReport,
			boolean blnCarteBlanche) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();
		
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			selenium.click("//div[@id='mainContainer']/table/tbody/tr"
					+ "/td[2][text()='" + strFormTemplateName
					+ "']/preceding-sibling" + "::td/a[text()='Security']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium.isElementPresent("css=input[name='userID'][value='"
							+ strUserName + "']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);

			
			try {
				assertEquals("Form Security Settings: " + strFormTemplateName + "",
						selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Form Security Settings: " + strFormTemplateName + "is displayed ");
				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template",
						7, 12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);
				strErrorMsg = objSearchUserByDiffCrteria
						.searchUserByDifCriteriaInFormPage(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);				
				if (blnActivateForm) {
					if (selenium.isChecked("css=input[name='userID'][value='"
							+ strUserName + "']") == false) {
						selenium.click("css=input[name='userID'][value='"
								+ strUserName + "']");
					}
				}

				if (blnRunReport) {
					if (selenium.isChecked("css=input[name='rUserID'][value='"
							+ strUserName + "']") == false) {
						selenium.click("css=input[name='rUserID'][value='"
								+ strUserName + "']");
					}
				}

				if (blnCarteBlanche) {
					if (selenium.isChecked("css=input[name='cbUserID'][value='"
							+ strUserName + "']") == false) {
						selenium.click("css=input[name='cbUserID'][value='"
								+ strUserName + "']");
					}
				}

				
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt=0;
				do{
					try{
						assertEquals("Form Security Settings", selenium
								.getText(propElementDetails.getProperty("Header.Text")));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
						
					}catch(Exception e){
					
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				try {
					assertEquals("Form Security Settings", selenium
							.getText(propElementDetails.getProperty("Header.Text")));

					log4j.info("Form Security Settings page is displayed");

				} catch (AssertionError Ae) {

					strErrorMsg = "Form Security Settings  page is NOT displayed"
							+ Ae;
					log4j
							.info("Form Security Settings  page is NOT displayed"
									+ Ae);
				}
				
			} catch (AssertionError Ae) {
				strErrorMsg = "Form Security Settings:" + strFormTemplateName
						+ " page is displayed";
			}

		} catch (Exception e) {
			log4j.info("selectUserInFormSettingPage function failed" + e);
			strErrorMsg = "selectUserInFormSettingPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Active Form page is displayed
	'Precondition	:None
	'Arguments		:selenium,strFormTemplateName
	'Returns		:String
	'Date	 		:4-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-May-2012                               <Name>
	************************************************************************/
	
	public String navActiveFormPge(Selenium selenium, String strFormTemplateName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objGeneral =new General();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails.getProperty("Form_Link"));

			selenium.click(propElementDetails.getProperty("Forms.ActivateForms"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			String strElementID=propElementDetails.getProperty("Header.Text");
			strErrorMsg=objGeneral.CheckForElements(selenium, strElementID);


			try {
				assertEquals("Activate Forms", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Activate Forms page is displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/"
									+ "tr/td[2][text()='"
									+ strFormTemplateName
									+ "']"));
					log4j.info(strFormTemplateName + " Present");
				} catch (AssertionError Ae) {
					strErrorMsg = strFormTemplateName + " NOT Present" + Ae;
					log4j.info(strFormTemplateName + " NOT Present" + Ae);
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Activate Forms  page is NOT displayed" + Ae;
				log4j.info("Activate Forms  page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navigate to Form Security Setting Page "
					+ "function failed" + e);
			strErrorMsg = "navigate to Form Security Setting Page"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:fill the form and submit
	'Precondition	:None
	'Arguments		:selenium,strFormName,strResource,strSearchType,strSearchFld,
					strSearchOper,strQuesAns
	'Returns		:String
	'Date	 		:21-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'  <Date>                                  <Name>
	************************************************************************/
	public String fillForm(Selenium selenium, String strFormName,String strResource,String strSearchType,String strSearchFld,String strSearchOper,String strQuesAns) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			//click on Fill Form for particular form
			selenium.click("//div[@id='mainContainer']/form/table/tbody/tr/td[2][text()='"+strFormName+"']/parent::tr/td[1]/a[text()='Fill Form']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Activate Form: "+strFormName, selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Activate Form: "+strFormName+" page is displayed");
				//enter the search criteria 
				selenium.select(propElementDetails.getProperty("ActivateForm.FillForm.SearchResType"), "label="+strSearchType);
				selenium.select(propElementDetails.getProperty("ActivateForm.FillForm.SearchField"), "label="+strSearchFld);
				selenium.select(propElementDetails.getProperty("ActivateForm.FillForm.SearchOper"), "label="+strSearchOper);
				selenium.type(propElementDetails.getProperty("ActivateForm.FillForm.SearchText"), strResource);
				//click on search
				selenium.click(propElementDetails
						.getProperty("ActivateForm.FillForm.Search"));
				
				int intCnt=0;
				while (selenium.isVisible(propElementDetails
						.getProperty("Reloading.Element"))&&intCnt<120) {
					intCnt++;
					Thread.sleep(500);
				}
				
				//select check box for resource
				selenium.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"+strResource+"']/parent::tr/td[1]/input");
				
				selenium.click(propElementDetails.getProperty("ActivateForm.FillForm.ActivateForm"));
				
				intCnt=0;
				boolean blnFound=false;
				while(blnFound==false){
					try{
						String strWins[]=selenium.getAllWindowTitles();
                        //String strWin1[]=selenium.getAllWindowNames();
						//String strWind[]=selenium.getAllWindowIds();
						selenium.selectWindow(strWins[1]);
						blnFound=true;
					}catch(Exception e){
						Thread.sleep(1000);
						intCnt++;
					}
				}
				//type the answer
				selenium.type("css=textarea[name^=question_]",strQuesAns );
				//submit
				selenium.click("css=input[name='submit']");
				
				selenium.selectWindow("");
			} catch (AssertionError Ae) {

				strReason = "Activate Form: "+strFormName+" page is NOT displayed" + Ae;
				log4j.info("Activate Form: "+strFormName+" page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("fillForm "
					+ "function failed" + e);
			strReason = "fillForm "
					+ " function failed" + e;
		}
		
		
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Verify Active Form page is displayed
	'Precondition	:None
	'Arguments		:selenium,strFormTemplateName
	'Returns		:String
	'Date	 		:13-May-2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-May-2012                               <Name>
	************************************************************************/
	
	public String navToFormConfig(Selenium selenium) throws Exception {

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
							.getProperty("Form_Link")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.mouseOver(propElementDetails.getProperty("Form_Link"));

			selenium.click(propElementDetails.getProperty("Forms.CofigForms"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Form Configuration", selenium
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
				assertEquals("Form Configuration", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Form Configuration page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Configuration  page is NOT displayed" + Ae;
				log4j.info("Form Configuration  page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToFormConfig " + "function failed" + e);
			strErrorMsg = "navToFormConfig" + " function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:navigate to create new form template page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String navTocreateNewFormTemplate(Selenium selenium)
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

					assertTrue(selenium.isElementPresent(propElementDetails.getProperty("FormConfig.CreateNewFormTemp")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			selenium.click(propElementDetails.getProperty("FormConfig.CreateNewFormTemp"));
			
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Create New Form Template", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
		
			try {
				assertEquals("Create New Form Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
		
				log4j.info("Create New Form Template page is displayed");
		
			} catch (AssertionError Ae) {
		
				strErrorMsg = "Create New Form Template page is NOT displayed" + Ae;
				log4j.info("Create New Form Template  page is NOT displayed" + Ae);
			}
		
		} catch (Exception e) {
			log4j.info("navTocreateNewFormTemplate "
					+ "function failed" + e);
			strErrorMsg = "navTocreateNewFormTemplate"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:fill all the fields in create new form
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle,strFormDesc,strFormActiv,strComplFormDel
					,blnNotifWeb,blnNotifEmail,blnNotifPager,blnMandat,blnNewForm,blnActive
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String fillAllFieldsInCreateNewForm(Selenium selenium, String strFormTempTitle,String strFormDesc,String strFormActiv,String strComplFormDel,
			boolean blnNotifWeb,boolean blnNotifEmail,boolean blnNotifPager,boolean blnMandat,boolean blnNewForm,boolean blnActive)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			//		
		
			selenium.type(propElementDetails.getProperty("CreateNewFormTemp.Title"), strFormTempTitle);
			selenium.type(propElementDetails.getProperty("CreateNewFormTemp.Descr"), strFormDesc);
			selenium.select(propElementDetails.getProperty("CreateNewFormTemp.FormActive"), "label="+strFormActiv);
			selenium.select(propElementDetails.getProperty("CreateNewFormTemp.CompltFormDelv"), "label="+strComplFormDel);
			
			if(blnNotifWeb){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			}
			
			if(blnNotifEmail){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			}
			
			if(blnNotifPager){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			}
			
			if(blnMandat){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			}
			
			if(blnNewForm){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			}
			
			if(blnActive){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			}
			//Next
			selenium.click(propElementDetails.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Users to Fill Out Form", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			try {
				assertEquals("Users to Fill Out Form", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
		
				log4j.info("Users to Fill Out Form page is displayed");
		
			} catch (AssertionError Ae) {
		
				strErrorMsg = "Users to Fill Out Form page is NOT displayed" + Ae;
				log4j.info("Users to Fill Out Form page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("fillAllFieldsInCreateNewForm "
					+ "function failed" + e);
			strErrorMsg = "fillAllFieldsInCreateNewForm"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:select user for form
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String selectUsersForForm(Selenium selenium,String strUserName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
					
			if(strUserName.compareTo("")!=0){
				//select the user
				selenium.click("//table[@id='tbl_userID']/tbody/tr/td[3][text()='"+strUserName+"']/parent::tr/td[1]/input");
			}
			//Next
			selenium.click(propElementDetails.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Resources to Fill Form", selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			try {
				assertEquals("Resources to Fill Form", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
		
				log4j.info("Resources to Fill Form page is displayed");
		
			} catch (AssertionError Ae) {
		
				strErrorMsg = "Resources to Fill Form page is NOT displayed" + Ae;
				log4j.info("Resources to Fill Form page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("selectUsersForForm "
					+ "function failed" + e);
			strErrorMsg = "selectUsersForForm"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:select resources for form
	'Precondition	:None
	'Arguments		:selenium,strResourceName,strFormName
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String selectResourcesForForm(Selenium selenium,
			String strResourceName, String strFormName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			if (strResourceName.compareTo("") != 0) {
				// select the user
				selenium
						.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
								+ strResourceName + "']/parent::tr/td[1]/input");
			}
			// Next
			selenium.click(propElementDetails
					.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
									+ strFormName + "']"));
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
				assertEquals("Form Configuration", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
								+ strFormName + "']"));
				log4j.info("Form '" + strFormName
						+ "' is listed in the 'Form Configuration' page");

			} catch (AssertionError Ae) {

				strErrorMsg = "Form '" + strFormName
						+ "' is NOT listed in the 'Form Configuration' page";
				log4j.info("Form '" + strFormName
						+ "' is NOT listed in the 'Form Configuration' page");
			}

		} catch (Exception e) {
			log4j.info("selectResourcesForForm " + "function failed" + e);
			strErrorMsg = "selectResourcesForForm" + " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:navigate to edit form template page
	'Precondition	:None
	'Arguments		:selenium,strFormName
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String navToEditFormTemplate(Selenium selenium,String strFormName)
	throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		try {
					
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//click on Edit link for form
			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"+strFormName+"']/parent::tr/td[1]/a[text()='Edit']");
			
			selenium.waitForPageToLoad(gstrTimeOut);
		
			try {
				assertEquals("Edit Form Template", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
		
				log4j.info("Edit Form Template page is displayed");
		
			} catch (AssertionError Ae) {
		
				strErrorMsg = "Edit Form Template page is NOT displayed" + Ae;
				log4j.info("Edit Form Template page is NOT displayed" + Ae);
			}
		
		} catch (Exception e) {
			log4j.info("navToEditFormTemplate "
					+ "function failed" + e);
			strErrorMsg = "navToEditFormTemplate"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:navigate to create new question page
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String navToCreateNewQuestion(Selenium selenium,
			String strFormTempTitle) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strFormTempTitle
							+ "']/parent::tr/td[1]/a[text()='Questionnaire']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Form Questions: " + strFormTempTitle, selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			

			try {
				assertEquals("Form Questions: " + strFormTempTitle, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");
				
				try {
					assertTrue(selenium.isElementPresent("css=input[value='Create New Question']"));
					
					log4j.info("Create New Question Button is displayed");
					

				} catch (AssertionError Ae) {

					strErrorMsg = "Create New Question Button is NOT displayed";
					log4j.info("Create New Question Button is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Questions: " + strFormTempTitle
						+ " page is displayed" + Ae;
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");
			}

		} catch (Exception e) {
			log4j.info("navToCreateNewQuestion " + "function failed" + e);
			
			strErrorMsg = "navToCreateNewQuestion" + " function failed" + e;
		}
	
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:create the question
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle,strQuestion,strDescription,
					strquesTypeID,blnQuesMan,blnDisplay
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String ToCreateQuestion(Selenium selenium, String strFormTempTitle,
			String strQuestion, String strDescription, String strquesTypeID,
			boolean blnQuesMan, boolean blnDisplay) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.click(propElementDetails
					.getProperty("CreateNewQuestionButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium.isElementPresent(propElementDetails
					.getProperty("CreateQuestion.QuestionTitle")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			
			
			selenium.type(propElementDetails
					.getProperty("CreateQuestion.QuestionTitle"), strQuestion);
			selenium.type(propElementDetails
					.getProperty("CreateQuestion.QuestionDesription"),
					strDescription);
			selenium.select(propElementDetails
					.getProperty("CreateQuestion.QuestionTypeID"), "label="
					+ strquesTypeID);

			if (blnQuesMan) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestioMan")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestioMan"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestioMan")))
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestioMan"));

			}

			if (blnDisplay) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestionDisplay")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestionDisplay"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestionDisplay")))
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestionDisplay"));
			}

			selenium.click(propElementDetails
					.getProperty("CreateQuestion.SubmitButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try{
					assertEquals("Form Questions: " + strFormTempTitle, selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			

			try {
				assertEquals("Form Questions: " + strFormTempTitle, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table"
									+ "/tbody/tr/td[text()='"
									+ strQuestion
									+ "']"));

					log4j.info("Form :'" + strQuestion + "'  is displayed");
				} catch (AssertionError Ae) {

					strErrorMsg = "Form : " + strQuestion
							+ "  is NOT displayed" + Ae;
					log4j.info("Form :'" + strQuestion + "'  is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Questions: " + strFormTempTitle
						+ " page is displayed" + Ae;
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");
			}

		} catch (Exception e) {
			log4j.info("ToCreateNewQuestion " + "function failed" + e);
			strErrorMsg = "ToCreateNewQuestion" + " function failed" + e;
		}

		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:send form from active forms list
	'Precondition	:None
	'Arguments		:selenium,strFormName,strResFillOut,strResReciev,
					strSearchType,strSearchFld,strSearchOper,strResource
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String sendFormFromActivateForms(Selenium selenium,
			String strFormName, String strResFillOut, String strResReciev,
			String strSearchType, String strSearchFld, String strSearchOper,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on Send Form for particular form
			selenium
					.click("//div[@id='mainContainer']/form/table/tbody/tr/td[2][text()='"
							+ strFormName
							+ "']/parent::tr/td[1]/a[text()='Send Form']");
			selenium.waitForPageToLoad(gstrTimeOut);
			// select the resource to fill out
			//selenium
					//.click("//table[@id='tbl_fillResourceID']/tbody/tr/td[2][text()='"
						//	+ strResFillOut + "']/parent::tr/td[1]/input");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if(selenium.isElementPresent("ActivateForm.FillForm.Search"))
			{
			// enter the search criteria
			selenium.select(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchResType"),
					"label=" + strSearchType);
			selenium.select(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchField"), "label="
					+ strSearchFld);
			selenium.select(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchOper"), "label="
					+ strSearchOper);
			selenium.type(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchText"),
					strResource);
			// click on search
			selenium.click(propElementDetails
					.getProperty("ActivateForm.FillForm.Search"));

			int intCnt = 0;
			while (selenium.isVisible(propElementDetails
					.getProperty("Reloading.Element"))
					&& intCnt < 120) {
				intCnt++;
				Thread.sleep(500);
			}
			}
			// select check box for resource
			selenium
					.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
							+ strResource + "']/parent::tr/td[1]/input");
			// click on Activate Form
			selenium.click(propElementDetails
					.getProperty("ActivateForm.FillForm.ActivateForm"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Region Default", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Region Default screen is displayed");

			} catch (AssertionError Ae) {

				strReason = "Region Default screen is NOT displayed";
				log4j.info("Region Default screen is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("sendFormFromActivateForms " + "function failed" + e);
			strReason = "sendFormFromActivateForms" + " function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:navigate to edit question page
	'Precondition	:None
	'Arguments		:selenium,strQuestion
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String navToEditQuestion(Selenium selenium,
			String strQuestion) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {
			// selenium.selectFrame("Data");

			selenium
					.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strQuestion
							+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Question", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Edit Question page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Edit Question page is displayed is NOT displayed"
						+ Ae;
				log4j.info("Edit Question page is displayed is NOT displayed"
						+ Ae);
			}

		} catch (Exception e) {
			log4j.info("navToCreateNewQuestion " + "function failed" + e);
			strErrorMsg = "navToCreateNewQuestion" + " function failed" + e;
		}

		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Fill fields in create question page.
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle,strQuestion,strDescription,
					blnQuesMan,blnDisplay
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String CreateQuestionFlds(Selenium selenium, String strFormTempTitle,
			String strQuestion, String strDescription,
			boolean blnQuesMan, boolean blnDisplay) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {

			selenium.type(propElementDetails
					.getProperty("CreateQuestion.QuestionTitle"), strQuestion);
			selenium.type(propElementDetails
					.getProperty("CreateQuestion.QuestionDesription"),
					strDescription);
			
			if (blnQuesMan) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestioMan")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestioMan"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestioMan")))
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestioMan"));

			}

			if (blnDisplay) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestionDisplay")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestionDisplay"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateQuestion.QuestionDisplay")))
					selenium.click(propElementDetails
							.getProperty("CreateQuestion.QuestionDisplay"));
			}

			selenium.click(propElementDetails
					.getProperty("CreateQuestion.SubmitButton"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Form Questions: " + strFormTempTitle, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table"
									+ "/tbody/tr/td[text()='"
									+ strQuestion
									+ "']"));
				} catch (AssertionError Ae) {

					strErrorMsg = "Form : " + strQuestion + "  is displayed"
							+ Ae;
					log4j.info("Form :'" + strQuestion + "'  is displayed");
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Questions: " + strFormTempTitle
						+ " page is displayed" + Ae;
				log4j.info("Form Questions:'" + strFormTempTitle
						+ "' page is displayed");
			}

		} catch (Exception e) {
			log4j.info("CreateQuestionFlds " + "function failed" + e);
			strErrorMsg = "CreateQuestionFlds" + " function failed" + e;
		}

		return strErrorMsg;
	}
	
	/***********************
	 'Description :Verify link=Configure Forms elemnt is present when mouse hover on Form tab
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :7-July-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '7-July-2012                               <Name>
	 ************************************************************************/

	public String ConfigFormsElementPresentByMouseHover(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.mouseOver(propElementDetails.getProperty("Form_Link"));

			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Forms.CofigForms")));
				log4j.info("'Configure Forms' option "
						+ "is available in the dropdown.");

			} catch (AssertionError Ae) {
				strErrorMsg = "'Configure Forms' option "
						+ "is NOT available in the dropdown.";
				log4j.info("'Configure Forms' option "
						+ "is NOT available in the dropdown.");
			}

		} catch (Exception e) {
			log4j.info("ConfigFormsElementPresent " + "function failed" + e);
			strErrorMsg = "ConfigFormsElementPresent" + " function failed" + e;
		}
		return strErrorMsg;
	}
	 
	 /***********************************************************************
		 'Description :Verify link=Configure Forms elemnt is present when mouse click on Form tab
		 'Precondition :None
		 'Arguments  :selenium
		 'Returns  :String
		 'Date    :9-July-2012
		 'Author   :QSG
		 '-----------------------------------------------------------------------
		 'Modified Date                            Modified By
		 '9-July-2012                               <Name>
		 ************************************************************************/

	public String ConfigFormsElementPresentByMouseClick(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails.getProperty("Form_Link"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Forms.CofigForms")));
				log4j.info("'Configure Forms' option "
						+ "is available in the dropdown.");

			} catch (AssertionError Ae) {
				strErrorMsg = "'Configure Forms' option "
						+ "is NOT available in the dropdown.";
				log4j.info("'Configure Forms' option "
						+ "is NOT available in the dropdown.");
			}

		} catch (Exception e) {
			log4j.info("ConfigFormsElementPresent " + "function failed" + e);
			strErrorMsg = "ConfigFormsElementPresent" + " function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:fill all the fields in create new form
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle,strFormDesc,strFormActiv,strComplFormDel
					,blnNotifWeb,blnNotifEmail,blnNotifPager,blnMandat,blnNewForm,blnActive
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String filAllFieldsInCreatNewFormForHimORHer(Selenium selenium,
			String strFormTempTitle, String strFormDesc, String strFormActiv,
			String strComplFormDel, boolean blnNotifWeb, boolean blnNotifEmail,
			boolean blnNotifPager, boolean blnMandat, boolean blnNewForm,
			boolean blnActive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			//		

			selenium.type(propElementDetails
					.getProperty("CreateNewFormTemp.Title"), strFormTempTitle);
			selenium.type(propElementDetails
					.getProperty("CreateNewFormTemp.Descr"), strFormDesc);
			selenium.select(propElementDetails
					.getProperty("CreateNewFormTemp.FormActive"), "label="
					+ strFormActiv);
			selenium.select(propElementDetails
					.getProperty("CreateNewFormTemp.CompltFormDelv"), "label="
					+ strComplFormDel);

			if (blnNotifWeb) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Web")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Web")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			}

			if (blnNotifEmail) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Email")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Email")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			}

			if (blnNotifPager) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Pager")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Pager")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			}

			if (blnMandat) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")) == false)
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")))
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			}

			if (blnNewForm) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.NewForm")) == false)
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.NewForm")))
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			}

			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Active")) == false)
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Active")))
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			}
			// Next
			selenium.click(propElementDetails
					.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("filAllFieldsInCreatNewFormForHimORHer "
					+ "function failed" + e);
			strErrorMsg = "filAllFieldsInCreatNewFormForHimORHer"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:navigate to Add quessionaire page of new form
	'Precondition	:None
	'Arguments		:selenium,strFormName
	'Returns		:String
	'Date	 		:22-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String navToAddQestnPageOfNF(Selenium selenium, String strFormName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// click on Questionnaire link for form
			selenium.click("//div[@id='mainContainer']/table/tbody/tr/"
					+ "td[2][text()='" + strFormName
					+ "']/parent::tr/td[1]/a[text()='Questionnaire']");
			selenium.waitForPopUp("EMFormDesigner", gstrTimeOut);

			selenium.selectWindow("name=EMFormDesigner");

			int intCnt=0;
			do{
				try{
					assertEquals("EMSystem Form Creator", selenium
							.getText("id=formTitleOnFormCreatorPage"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
					selenium.typeKeys("//", "116");
					
				}catch(Exception e){
				
					Thread.sleep(1000);
					intCnt++;
					selenium.typeKeys("//", "116");
				}
			}while(intCnt<60);
			
		
			try {
				assertEquals("EMSystem Form Creator", selenium
						.getText("id=formTitleOnFormCreatorPage"));
				log4j.info("EMSystem Form Creator page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "EMSystem Form Creator page is NOT displayed"
						+ Ae;
				log4j.info("EMSystem Form Creator page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToAddQestnPageOfNF " + "function failed" + e);
			strErrorMsg = "navToAddQestnPageOfNF" + " function failed" + e;
		}
		
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:select only basic information of the quessionaire
	'Precondition	:None
	'Arguments		:selenium,strFormName
	'Returns		:String
	'Date	 		:22-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' 25-Feb-2013                               <Name>
	************************************************************************/
	
	
	public String newFormQuestnBasicInfo(Selenium selenium,
			String strNFQuestTitl, String strDesc, String strAbbr,
			String strInstructn, boolean blnAddNonMandQuestn, String strLabl)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("name=EMFormDesigner");

			selenium.type(propElementDetails
					.getProperty("Forms.BasicQuestnName"), strNFQuestTitl);
			selenium.type(propElementDetails
					.getProperty("Forms.BasicQuestnDesc"), strDesc);
			selenium.type(propElementDetails
					.getProperty("Forms.BasicQuestnAbbr"), strAbbr);
			selenium.type(propElementDetails
					.getProperty("Forms.BasicQuestnInstructn"), strInstructn);

			if (blnAddNonMandQuestn) {

				int intCnt = 0;
				do {
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Forms.QuestnType")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				selenium.select(propElementDetails
						.getProperty("Forms.QuestnType"), "label=" + strLabl);
				selenium.click(propElementDetails
						.getProperty("Forms.AddQuestnType"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} else {
				int intCnt = 0;
				do {
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Forms.PreViewForm")));
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
						.getProperty("Forms.PreViewForm"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Forms.SaveForm")));
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
						.click(propElementDetails.getProperty("Forms.SaveForm"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

		} catch (Exception e) {
			log4j.info("newFormQuestnBasicInfo " + "function failed" + e);
			strErrorMsg = "newFormQuestnBasicInfo" + " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Fill only Text information of the quessionaire
	'Precondition	:None
	'Arguments		:selenium,strFormName
	'Returns		:String
	'Date	 		:22-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String newFormQuestnTextInfo(Selenium selenium, String strTextTitl,
			String strTextAbb, String strTextLength, boolean blnAnsReq,
			boolean blnCheckBoxId) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objGeneral = new General();

		try {
			selenium.selectWindow("name=EMFormDesigner");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//input[@id='Question']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			String strElementID = "//input[@id='Question']";
			strErrorMsg = objGeneral.CheckForElements(selenium, strElementID);

			selenium.type(propElementDetails
					.getProperty("Forms.TextQuestnTitl"), strTextTitl);
			selenium.type(propElementDetails
					.getProperty("Forms.TextQuestnAbbr"), strTextAbb);
			selenium.type(propElementDetails
					.getProperty("Forms.TextQuestnLength"), strTextLength);

			if (blnAnsReq) {
				if (selenium.isChecked("id=Answer is required") == false)
					selenium.click("id=Answer is required");
			} else if (selenium.isChecked("id=Answer is required"))
				selenium.click("id=Answer is required");

			if (blnCheckBoxId) {
				if (selenium.isChecked("id=checkboxId") == false)
					selenium.click("id=checkboxId");
			} else if (selenium.isChecked("id=checkboxId"))
				selenium.click("id=checkboxId");

			intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Forms.PreViewForm")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click(propElementDetails.getProperty("Forms.PreViewForm"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("Forms.SaveForm")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			strElementID = propElementDetails.getProperty("Forms.SaveForm");
			strErrorMsg = objGeneral.CheckForElements(selenium, strElementID);

			selenium.click(propElementDetails.getProperty("Forms.SaveForm"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("newFormQuestnTextInfo " + "function failed" + e);
			strErrorMsg = "newFormQuestnTextInfo" + " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Fill only Text information of the quessionaire
	'Precondition	:None
	'Arguments		:selenium,strFormName
	'Returns		:String
	'Date	 		:22-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String fillMandTextDataAndVerifyInRevwUrChangsPage(
			Selenium selenium, String strEditTextTitl) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("name=EMFormDesigner");

			selenium.type(propElementDetails.getProperty("Forms.TextQuestnTitl"), strEditTextTitl);

			selenium.click(propElementDetails.getProperty("Forms.PreViewForm"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Review Your Changes", selenium
						.getText("css=span.summaryTitle"));
				log4j.info("Review Your Changes page is displayed");

				int intCnt = 0;
				boolean blnfound = true;
				do {
					try {
						assertTrue(selenium
								.isElementPresent("//form[@name='formReview']/div/div/fieldset/span/label"));
						blnfound = false;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
					}
				} while (intCnt < 60 && blnfound);

				try {
					assertEquals(strEditTextTitl, selenium
							.getText("css=#textQuestionTabblabel"));
					log4j
							.info("The changes for 'Text' question Q are updated in " +
									"'Review Your Changes' screen. ");

				} catch (AssertionError Ae) {

					strErrorMsg = "The changes for 'Text' question Q are " +
							"updated in 'Review Your Changes' screen. "
							+ Ae;
					log4j
							.info("The changes for 'Text' question Q are" +
									" updated in 'Review Your Changes' screen. "
									+ Ae);
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Review Your Changes page is NOT displayed" + Ae;
				log4j.info("Review Your Changes page is NOT displayed" + Ae);
			}

			selenium.click("css=input[value='Save Form']");
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("fillMandTextDataAndVerifyInRevwUrChangsPage "
					+ "function failed" + e);
			strErrorMsg = "fillMandTextDataAndVerifyInRevwUrChangsPage"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:fill all the fields in create new form
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle,strFormDesc,strFormActiv,strComplFormDel
					,blnNotifWeb,blnNotifEmail,blnNotifPager,blnMandat,blnNewForm,blnActive
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String fillAllFieldsInCreateNewFormWithDifOptions(Selenium selenium,
			String strFormTempTitle, String strFormDesc, String strFormActiv,
			String strComplFormDel, boolean blnNotifWeb, boolean blnNotifEmail,
			boolean blnNotifPager, boolean blnMandat, boolean blnNewForm,
			boolean blnActive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails
					.getProperty("CreateNewFormTemp.Title"), strFormTempTitle);
			selenium.type(propElementDetails
					.getProperty("CreateNewFormTemp.Descr"), strFormDesc);
			selenium.select(propElementDetails
					.getProperty("CreateNewFormTemp.FormActive"), "label="
					+ strFormActiv);

			if (selenium.isElementPresent(propElementDetails
					.getProperty("CreateNewFormTemp.CompltFormDelv"))) {
				selenium.select(propElementDetails
						.getProperty("CreateNewFormTemp.CompltFormDelv"),
						"label=" + strComplFormDel);
			}

			if (blnNotifWeb) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Web")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Web")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			}

			if (blnNotifEmail) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Email")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Email")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			}

			if (blnNotifPager) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Pager")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Pager")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			}

			if (blnMandat) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")) == false)
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")))
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			}

			if (blnNewForm) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.NewForm")) == false)
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.NewForm")))
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			}

			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Active")) == false)
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Active")))
					selenium
							.click(propElementDetails
									.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			}
			// Next
			selenium.click(propElementDetails
					.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Form Configuration", selenium
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
				assertEquals("Form Configuration", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Form Configuration page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Configuration page is NOT displayed" + Ae;
				log4j.info("Form Configuration page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("fillAllFieldsInCreateNewFormWithDifOptions "
					+ "function failed" + e);
			strErrorMsg = "fillAllFieldsInCreateNewFormWithDifOptions"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:add security rights to the individual user
	'Precondition	:None
	'Arguments		:selenium,strFormName
	'Returns		:String
	'Date	 		:22-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	
	
	public String addSecurityRightToIndividualUsers(Selenium selenium,
			String strFormName, String strUserName, boolean blnActForm,
			boolean blnRunRep, boolean blnCarteBlanche) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[2]"
					+ "[text()='" + strFormName
					+ "']/parent::tr/td/a[text()='Security']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Form Security Settings: " + strFormName,
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
				assertEquals("Form Security Settings: " + strFormName, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Form Security Settings: " + strFormName
						+ " page is displayed");

				if (selenium
						.isElementPresent("css=input[name='userID'][value='"
								+ strUserName + "']")) {

					if (blnActForm) {
						if (selenium
								.isChecked("css=input[name='userID'][value='"
										+ strUserName + "']") == false) {
							selenium.click("css=input[name='userID'][value='"
									+ strUserName + "']");
						}

					} else {
						if (selenium
								.isChecked("css=input[name='userID'][value='"
										+ strUserName + "']")) {
							selenium.click("css=input[name='userID'][value='"
									+ strUserName + "']");
						}

					}

				}

				if (blnRunRep) {
					if (selenium.isChecked("css=input[name='rUserID'][value='"
							+ strUserName + "']") == false) {
						selenium.click("css=input[name='rUserID'][value='"
								+ strUserName + "']");
					}

				} else {
					if (selenium.isChecked("css=input[name='rUserID'][value='"
							+ strUserName + "']")) {
						selenium.click("css=input[name='rUserID'][value='"
								+ strUserName + "']");
					}

				}

				if (blnCarteBlanche) {
					if (selenium.isChecked("css=input[name='cbUserID'][value='"
							+ strUserName + "']") == false) {
						selenium.click("css=input[name='cbUserID'][value='"
								+ strUserName + "']");
					}

				} else {
					if (selenium.isChecked("css=input[name='cbUserID'][value='"
							+ strUserName + "']")) {
						selenium.click("css=input[name='cbUserID'][value='"
								+ strUserName + "']");
					}

				}

				selenium.click(propElementDetails
						.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertEquals("Form Security Settings", selenium
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
					assertEquals("Form Security Settings", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Form Security Settings" + " page is displayed");

				} catch (AssertionError Ae) {

					strErrorMsg = "Form Security Settings"
							+ " page is NOT displayed" + Ae;
					log4j.info("Form Security Settings"
							+ " page is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Security Settings: " + strFormName
						+ " page is NOT displayed" + Ae;
				log4j.info("Form Security Settings: " + strFormName
						+ " page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("addSecurityRightToIndividualUsers " + "function failed"
					+ e);
			strErrorMsg = "addSecurityRightToIndividualUsers"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:activate and fill form to the particular user
	'Precondition	:None
	'Arguments		:selenium,strFormName
	'Returns		:String
	'Date	 		:22-Aug-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String fillFormAndActivateTheUsr(Selenium selenium,
			String strFormName, String strUserName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']"
					+ "/form/table/tbody/tr/td[2][text()='" + strFormName
					+ "']" + "/parent::tr/td[1]/a[text()='Fill Form']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Activate Form: " + strFormName, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Activate Form: " + strFormName
						+ " page is displayed");

				selenium.click("css=input[value='" + strUserName
						+ "'][name='userID']");
				selenium.click("css=input[value='Activate Form']");
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {

				strErrorMsg = "Activate Form: " + strFormName
						+ " page is NOT displayed" + Ae;
				log4j.info("Activate Form: " + strFormName
						+ " page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("fillMandTextDataAndVerifyInRevwUrChangsPage "
					+ "function failed" + e);
			strErrorMsg = "fillMandTextDataAndVerifyInRevwUrChangsPage"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:fill all the fields in create new form
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle,strFormDesc,strFormActiv,strComplFormDel
					,blnNotifWeb,blnNotifEmail,blnNotifPager,blnMandat,blnNewForm,blnActive
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String filAllFldsInCreatNewFormAsStatusChanges(Selenium selenium, String strFormTempTitle,String strFormDesc,String strFormActiv,
			boolean blnNotifWeb,boolean blnNotifEmail,boolean blnNotifPager,boolean blnMandat,boolean blnNewForm,boolean blnActive)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			//		
		
			selenium.type(propElementDetails.getProperty("CreateNewFormTemp.Title"), strFormTempTitle);
			selenium.type(propElementDetails.getProperty("CreateNewFormTemp.Descr"), strFormDesc);
			selenium.select(propElementDetails.getProperty("CreateNewFormTemp.FormActive"), "label="+strFormActiv);
			
			
			if(blnNotifWeb){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			}
			
			if(blnNotifEmail){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			}
			
			if(blnNotifPager){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			}
			
			if(blnMandat){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			}
			
			if(blnNewForm){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			}
			
			if(blnActive){
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active"))==false)
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			}else{
				if(selenium.isChecked(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active")))
					selenium.click(propElementDetails.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			}
			//Next
			selenium.click(propElementDetails.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
		} catch (Exception e) {
			log4j.info("filAllFldsInCreatNewFormAsStatusChanges "
					+ "function failed" + e);
			strErrorMsg = "filAllFldsInCreatNewFormAsStatusChanges"
					+ " function failed" + e;
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
	
	public String checkSTInFormActivationStPage(Selenium selenium,
			String strStatType, String strStatTypeVal, boolean blnPresent) throws Exception {

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
					log4j.info("Status Type "+ strStatType
									+ " is displayed under 'Form Activation Status Type' section ");
				} catch (AssertionError ae) {
					log4j.info("Status Type "+ strStatType
									+ " is NOT displayed under 'Form Activation Status Type' section");
					strReason = "Status Type "
							+ strStatType
							+ " is NOT displayed under 'Form Activation Status Type' section";
				}

			} else {
				try {
					assertFalse(selenium
							.isElementPresent("css=input[name='statusTypeID'][value='"
									+ strStatTypeVal + "']"));
					log4j.info("Status Type "+ strStatType
									+ " is NOT displayed under 'Form Activation Status Type' section ");
				} catch (AssertionError ae) {
					log4j.info("Status Type "+ strStatType
									+ " is displayed under 'Form Activation Status Type' section");
					strReason = "Status Type "
							+ strStatType
							+ " is displayed under 'Form Activation Status Type' section";
				}
			}

		} catch (Exception e) {
			log4j.info("checkSTInFormActivationStPage function failed" + e);
			strReason = "checkSTInFormActivationStPage function failed" + e;
		}
		return strReason;
	}
	
	
	/***********************************************************************
	'Description	:select Status type for form
	'Precondition	:None
	'Arguments		:selenium,statustype value
	'Returns		:String
	'Date	 		:5-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	
	public String selectSatusTypesForFormNSTAndSST(Selenium selenium,
			String strStatusTypeValue[], String strWhenToSend,
			String strNumericLmtValue) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals("Form Activation Status Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Form Activation Status Type page is displayed");

				for (String s : strStatusTypeValue) {
					selenium.click("css=input[name='statusTypeID'][value='" + s
							+ "']");
				}

				selenium.click("css=input[value='Select Status Type']");
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				do {
					try {

						assertEquals("Set Numeric Status", selenium
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
					assertEquals("Set Numeric Status", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Set Numeric Status page is displayed");
					selenium.select("name=isAbove", "label=" + strWhenToSend);

					selenium.type("name=numericLimit", strNumericLmtValue);
					selenium.click("css=input[value='Save Configuration']");
					selenium.waitForPageToLoad(gstrTimeOut);

				} catch (AssertionError Ae) {

					strErrorMsg = "Set Numeric Status page is NOT displayed";
					log4j.info("Set Numeric Status page is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Activation Status Type page is NOT displayed";
				log4j.info("Form Activation Status Type page is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("selectSatusTypesForForm " + "function failed" + e);
			strErrorMsg = "selectSatusTypesForForm" + " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:fill all the fields in create new form
	'Precondition	:None
	'Arguments		:selenium,strFormTempTitle,strFormDesc,strFormActiv,strComplFormDel
					,blnNotifWeb,blnNotifEmail,blnNotifPager,blnMandat,blnNewForm,blnActive
	'Returns		:String
	'Date	 		:5-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String fillAllFieldsInCreateNewFormWitoutPageVef(Selenium selenium,
			String strFormTempTitle, String strFormDesc, String strFormActiv,
			String strComplFormDel, boolean blnNotifWeb, boolean blnNotifEmail,
			boolean blnNotifPager, boolean blnMandat, boolean blnNewForm,
			boolean blnActive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		int intCnt = 0;
		do {
			try {

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("CreateNewFormTemp.Title")));
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
			selenium.type(propElementDetails
					.getProperty("CreateNewFormTemp.Title"), strFormTempTitle);
			selenium.type(propElementDetails
					.getProperty("CreateNewFormTemp.Descr"), strFormDesc);
			selenium.select(propElementDetails
					.getProperty("CreateNewFormTemp.FormActive"), "label="
					+ strFormActiv);
			selenium.select(propElementDetails
					.getProperty("CreateNewFormTemp.CompltFormDelv"), "label="
					+ strComplFormDel);

			if (blnNotifWeb) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Web")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Web")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Web"));
			}

			if (blnNotifEmail) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Email")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Email")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Email"));
			}

			if (blnNotifPager) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Pager")) == false)
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Pager")))
					selenium.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Pager"));
			}

			if (blnMandat) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")) == false)
					selenium
					.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Mandatory")))
					selenium
					.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Mandatory"));
			}

			if (blnNewForm) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.NewForm")) == false)
					selenium
					.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.NewForm")))
					selenium
					.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.NewForm"));
			}

			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Active")) == false)
					selenium
					.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("CreateNewFormTemp.HowToNotif.Active")))
					selenium
					.click(propElementDetails
							.getProperty("CreateNewFormTemp.HowToNotif.Active"));
			}
			// Next
			selenium.click(propElementDetails
					.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("fillAllFieldsInCreateNewFormWitoutPageVef " + "function failed" + e);
			strErrorMsg = "fillAllFieldsInCreateNewFormWitoutPageVef" + " function failed"
			+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:select user for form
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	
	public String selectUsersForFormNew(Selenium selenium, String strUserName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			if (strUserName.compareTo("") != 0) {

				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("//table[@id='tbl_userID']"
										+ "/tbody/tr/td[3][text()='"
										+ strUserName + "']/parent::"
										+ "tr/td[1]/input"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				// select the user
				selenium.click("//table[@id='tbl_userID']/tbody/tr/td[3][text"
						+ "()='" + strUserName + "']/parent::tr/td[1]/input");
			}
			// Next
			selenium.click(propElementDetails
					.getProperty("CreateNewFormTemp.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info("selectUsersForForm " + "function failed" + e);
			strErrorMsg = "selectUsersForForm" + " function failed" + e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:select Status type for form
	'Precondition	:None
	'Arguments		:selenium,statustype value
	'Returns		:String
	'Date	 		:6-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	
	public String selectSatusTypesForFormMST(Selenium selenium,
			String strStatusTypeValue[], String strStatusVal[])
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			int intCnt = 0;
			do {
				try {

					assertEquals("Form Activation Status Type", selenium
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
				assertEquals("Form Activation Status Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Form Activation Status Type page is displayed");

				for (String s : strStatusTypeValue) {
					selenium.click("css=input[name='statusTypeID'][value='" + s
							+ "']");
				}

				selenium.click("css=input[value='Select Status Type']");
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertEquals("Form - Status Change Setting", selenium
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
					assertEquals("Form - Status Change Setting", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j
							.info("Form - Status Change Setting page is displayed");

					intCnt = 0;
					do {
						try {

							assertTrue(selenium
									.isElementPresent("css=input[name='flag']"
											+ "[value='" + strStatusVal[0]
											+ "-" + strStatusVal[1] + "']"));
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCnt++;

						} catch (Exception Ae) {
							Thread.sleep(1000);
							intCnt++;
						}
					} while (intCnt < 60);

					selenium.click("css=input[name='flag'][value='"
							+ strStatusVal[0] + "-" + strStatusVal[1] + "']");
					selenium.click("css=input[value='Save Configuration']");
					selenium.waitForPageToLoad(gstrTimeOut);

				} catch (AssertionError Ae) {

					strErrorMsg = "Form - Status Change Setting page is NOT displayed";
					log4j
							.info("Form - Status Change Setting page is NOT displayed");
				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Form Activation Status Type page is NOT displayed";
				log4j.info("Form Activation Status Type page is NOT displayed");
			}

		} catch (Exception e) {

			log4j.info("selectSatusTypesForForm " + "function failed" + e);
			strErrorMsg = "selectSatusTypesForForm" + " function failed" + e;
		}
		return strErrorMsg;
	}

	/***************************************************************
	'Description	:Check status type in Form Activation Status Type Screen
	'Precondition	:None
	'Arguments		:selenium,strStatType
	'Returns		:strReason
	'Date	 		:26-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String checkStatTypeInFormActivationSTPage(Selenium selenium,String strStatType)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
		
			  try{	assertEquals("Form Activation Status Type", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				    assertTrue(selenium.isElementPresent("//table/tbody/tr/td[text()='"+strStatType+"']"));
				    log4j.info("Status Type "+strStatType+"is displayed in Form Activation Status Type");
				        
			  }catch(AssertionError ae){
				  log4j.info("Status Type "+strStatType+"is NOT displayed in Form Activation Status Type");
				    strReason="Status Type "+strStatType+"is NOT displayed in Form Activation Status Type";
			}  
				  
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkStatTypeInFormActivationSTPage "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Check status type in Form Activation Status Type Screen
	'Precondition	:None
	'Arguments		:selenium,strStatType,strStatusNameColorValue[]
	'Returns		:strReason
	'Date	 		:01-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String checkStatTypeMSTInFormActivationSTPage(Selenium selenium,
			String strStatType, String strStatusNameColorValue[][]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals("Form Activation Status Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='"
								+ strStatType + "']"));
				log4j.info("Status Type " + strStatType
						+ "is displayed in Form Activation Status Type");

				for (int i = 0; i < strStatusNameColorValue.length; i++) {

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']"
										+ "/form/table/tbody/tr[1]/td[text()='"
										+ strStatType
										+ "']"
										+ "/following-sibling::td[2]/font[text()='"
										+ strStatusNameColorValue[i][0] + "']"));

						assertEquals(
								selenium
										.getAttribute("//div[@id='mainContainer']"
												+ "/form/table/tbody/tr[1]/td[text()='"
												+ strStatType
												+ "']"
												+ "/following-sibling::td[2]/font[text()='"
												+ strStatusNameColorValue[i][0]
												+ "']/@color"), "#"
										+ strStatusNameColorValue[i][1]);

						log4j
								.info("Name and color of Status is displayed appropriately "
										+ "on the 'Form Activation Status Type' screen");

					} catch (Exception e) {
						log4j
								.info("Name and color of Status is NOT displayed appropriately"
										+ " on the 'Form Activation Status Type' screen");
						strReason = "Name and color of Status is NOT displayed appropriately on"
								+ " the 'Form Activation Status Type' screen";
					}
				}

			} catch (AssertionError ae) {
				log4j.info("Status Type " + strStatType
						+ "is NOT displayed in Form Activation Status Type");
				strReason = "Status Type " + strStatType
						+ "is NOT displayed in Form Activation Status Type";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkStatTypeMSTInFormActivationSTPage "
					+ e.toString();
		}
		return strReason;
	}

	
	/***********************************************************************
	'Description	:Verify Form Security Setting page is displayed
	'Precondition	:None
	'Arguments		:selenium,strUserName,strFormTemplateName,blnActivateForm,
	'				 blnRunReport,blnCarteBlanche
	'Returns		:String
	'Date	 		:7-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'7-Nov-2012                               <Name>
	************************************************************************/
	
	public String navFormSettingPageOfForm(Selenium selenium,
			String strFormTemplateName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		pathProps = objAP.Read_FilePath();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click("//div[@id='mainContainer']/table/tbody/tr"
					+ "/td[2][text()='" + strFormTemplateName
					+ "']/preceding-sibling" + "::td/a[text()='Security']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Form Security Settings: " + strFormTemplateName
						+ "", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Form Security Settings: " + strFormTemplateName
						+ "is displayed ");

			} catch (AssertionError Ae) {
				strErrorMsg = "Form Security Settings:" + strFormTemplateName
						+ " page is displayed";
			}

		} catch (Exception e) {
			log4j.info("navFormSettingPageOfForm function failed" + e);
			strErrorMsg = "navFormSettingPageOfForm function failed" + e;
		}
		return strErrorMsg;
	}

	
	/***************************************************************
	'Description	:Check status type in Form Activation Status Type Screen
	'Precondition	:None
	'Arguments		:selenium,strStatType,strStatusNameColorValue[]
	'Returns		:strReason
	'Date	 		:01-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String checkStatTypeMSTInFormActivationSTPageNew(Selenium selenium,
			String strStatType, String strStatusNameColorValue, String strStatus)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals("Form Activation Status Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='"
								+ strStatType + "']"));
				log4j.info("Status Type " + strStatType
						+ "is displayed in Form Activation Status Type");

				try {
					assertEquals(selenium.getAttribute("//font[text()='"
							+ strStatus + "']/@color"), "#"
							+ strStatusNameColorValue);

					log4j
							.info("Name and color of Status is displayed appropriately "
									+ "on the 'Form Activation Status Type' screen");

				} catch (Exception e) {
					log4j
							.info("Name and color of Status is NOT displayed appropriately"
									+ " on the 'Form Activation Status Type' screen");
					strReason = "Name and color of Status is NOT displayed appropriately on"
							+ " the 'Form Activation Status Type' screen";
				}

			} catch (AssertionError ae) {
				log4j.info("Status Type " + strStatType
						+ "is NOT displayed in Form Activation Status Type");
				strReason = "Status Type " + strStatType
						+ "is NOT displayed in Form Activation Status Type";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkStatTypeMSTInFormActivationSTPage "
					+ e.toString();
		}
		return strReason;
	}
	
	//start//checkUserInFormPage//
	/*******************************************************************************************
	' Description			: check user is listed or not in form page
	' Precondition			: N/A 
	' Arguments				: selenium, strUserName, blnPresent
	' Returns				: String 
	' Date					: 16/09/2013
	' Author				: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String checkUserInFormPage(Selenium selenium, String strUserName, boolean blnPresent) throws Exception
	{
		String strReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try
		{
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try
			{
				if(blnPresent)
				{
				assertTrue(selenium
						.isElementPresent("//table[@id='tbl_userID']/tbody/tr/td"
								+ "[text()='" + strUserName + "']"));
				assertTrue(selenium
						.isVisible("//table[@id='tbl_userID']/tbody/tr/td"
								+ "[text()='" + strUserName + "']"));
				log4j.info("User '"
						+ strUserName
						+ "' is  listed in form page. ");
				
				}
				else
				{
					assertTrue(selenium
							.isElementPresent("//table[@id='tbl_userID']/tbody/tr/td"
									+ "[text()='" + strUserName + "']"));
					assertFalse(selenium
							.isVisible("//table[@id='tbl_userID']/tbody/tr/td"
									+ "[text()='" + strUserName + "']"));
					log4j.info("User '"
							+ strUserName
							+ "' is NOT  listed in form page. ");
				}
				
			}catch (AssertionError Ae) {
				log4j.info("User '"
						+ strUserName
						+ "' is NOT  listed in form page. ");
				strReason="User '"
						+ strUserName
						+ "' is NOT  listed in form page. ";
			}
		}catch (Exception e) {
			log4j.info("checkUserInFormPage function failed"+ e);
			strReason="checkUserInFormPage function failed"+ e;
		}

		return strReason;
	}
	//end//checkUserInFormPage//
	
	/***********************************************************************
	'Description	:send form from active forms list
	'Precondition	:None
	'Arguments		:selenium,strFormName,strResFillOut,strResReciev,
					strSearchType,strSearchFld,strSearchOper,strResource
	'Returns		:String
	'Date	 		:22-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	' <Date>                               		<Name>
	************************************************************************/
	public String sendFormFromActivateFormsWithotFillRes(Selenium selenium,
			String strFormName, String strResFillOut, String strResReciev,
			String strSearchType, String strSearchFld, String strSearchOper,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on Send Form for particular form
			selenium
					.click("//div[@id='mainContainer']/form/table/tbody/tr/td[2][text()='"
							+ strFormName
							+ "']/parent::tr/td[1]/a[text()='Send Form']");
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if(selenium.isElementPresent("ActivateForm.FillForm.Search"))
			{
			// enter the search criteria
			selenium.select(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchResType"),
					"label=" + strSearchType);
			selenium.select(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchField"), "label="
					+ strSearchFld);
			selenium.select(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchOper"), "label="
					+ strSearchOper);
			selenium.type(propElementDetails
					.getProperty("ActivateForm.FillForm.SearchText"),
					strResource);
			// click on search
			selenium.click(propElementDetails
					.getProperty("ActivateForm.FillForm.Search"));

			int intCnt = 0;
			while (selenium.isVisible(propElementDetails
					.getProperty("Reloading.Element"))
					&& intCnt < 120) {
				intCnt++;
				Thread.sleep(500);
			}
			}
			// select check box for resource
			selenium
					.click("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
							+ strResource + "']/parent::tr/td[1]/input");
			// click on Activate Form
			selenium.click(propElementDetails
					.getProperty("ActivateForm.FillForm.ActivateForm"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Region Default", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Region Default screen is displayed");

			} catch (AssertionError Ae) {

				strReason = "Region Default screen is NOT displayed";
				log4j.info("Region Default screen is NOT displayed");
			}

		} catch (Exception e) {
			log4j.info("sendFormFromActivateForms " + "function failed" + e);
			strReason = "sendFormFromActivateForms" + " function failed" + e;
		}
		return strReason;
	}
	
 // starts//vrfyRoleInFormSecuritySettingsPage//
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

	public String vrfyRoleInFormSecuritySettingsPage(Selenium selenium,
			String strRoleName, boolean blnRole, String strFormName)
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
							.isElementPresent("//select[@id='tbl_userID_ROLE']/option[text()='"
									+ strRoleName + "']"));

					log4j.info("Role "
							+ strRoleName
							+ " is displayed in Search by role dropdown of Form Security Settings: "
							+ strFormName);

				} catch (AssertionError Ae) {
					log4j.info("Role "
							+ strRoleName
							+ " is NOT displayed in Search by role dropdown of Form Security Settings: "
							+ strFormName);
					strReason = "Role "
							+ strRoleName
							+ " is NOT displayed in Search by role dropdown of Form Security Settings: "
							+ strFormName + Ae;
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='tbl_userID_ROLE']/option[text()='"
									+ strRoleName + "']"));

					log4j.info("Role "
							+ strRoleName
							+ " is NOT displayed in Search by role dropdown of Form Security Settings: "
							+ strFormName);

				} catch (AssertionError Ae) {
					log4j.info("Role "
							+ strRoleName
							+ " is displayed in Search by role dropdown of Form Security Settings: "
							+ strFormName);
					strReason = "Role "
							+ strRoleName
							+ " is displayed in Search by role dropdown of Form Security Settings: "
							+ strFormName + Ae;
				}
			}
		} catch (Exception e) {
			log4j.info("vrfyRoleInFormSecuritySettingsPage function failed"
					+ e);
			strReason = "vrfyRoleInFormSecuritySettingsPage function failed"
					+ e;
		}

		return strReason;
	}

	// end//vrfyRoleInFormSecuritySettingsPage//

}
