package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/******************************************************************
' Description :This class includes common functions Login details
' Date		  :6-April-2012
' Author	  :QSG
'-------------------------------------------------------------------' 
'******************************************************************/
 public class Login {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.Login");
	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	ReadData rdExcel;
	
	/*********************************************************
	'Description	:Verify login of the users
	'Precondition	:None
	'Arguments		:selenium,strUserName,strPassword
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	**********************************************************/
	public String login(Selenium selenium, String strLoginUserName,
			String strLoginPassword) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.setTimeout(gstrTimeOut);
			selenium.open(propEnvDetails.getProperty("urlRel"));

			selenium.type(propElementDetails.getProperty("Login.UserName"),
					strLoginUserName);
			selenium.type(propElementDetails.getProperty("Login.Password"),
					strLoginPassword);

			selenium.click(propElementDetails.getProperty("Login.Submit"));
			selenium.waitForPageToLoad(gstrTimeOut);
			Thread.sleep(10000);
			try {
				selenium.selectWindow("");
			} catch (Exception e) {
				log4j.info(e);
			}
			try {
				selenium.windowMaximize();
				selenium.windowMaximize();
				selenium.windowFocus();
			} catch (Exception e) {
				log4j.info(e);
			}
			int intCnt = 0;
			do {
				try {

					assertEquals("EMResource",
							selenium.getText(propElementDetails
									.getProperty("SelectRegion.EMResource")));
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
				assertEquals("EMResource", selenium.getText(propElementDetails
						.getProperty("SelectRegion.EMResource")));
				log4j.info("Home page is displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = "Home page is NOT displayed" + Ae;
				log4j.info("Home page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info("login function failed" + e);
			strErrorMsg = "login function failed" + e;
		}
		strErrorMsg= "";
		return strErrorMsg;
	}

	/*********************************************************
	'Description	:Verify that login fails when login with 
					Invalid password
	'Precondition	:None
	'Arguments		:selenium,strUserName,strPassword
	'Returns		:String
	'Date	 		:06-June-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                               <Name>
	**********************************************************/
	
	public String loginWithInvalidPwd(Selenium selenium, String strLoginUserName,
			String strLoginPassword) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {		

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));			
			selenium.type(propElementDetails.getProperty("Login.UserName"),
					strLoginUserName);
			selenium.type(propElementDetails.getProperty("Login.Password"),
					strLoginPassword);
			selenium.click(propElementDetails.getProperty("Login.Submit"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertFalse(selenium
						.isElementPresent(propElementDetails.getProperty("Header.Text")));
				
				log4j.info("Login fails");
			} catch (AssertionError Ae) {

				strErrorMsg = "Login successful";
				log4j.info("Login successful");
			}
		} catch (Exception e) {
			log4j.info("loginWithInvalidPwd function failed" + e);
			strErrorMsg = "loginWithInvalidPwd function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************************
	'Description	:Verify 'set up user password'screen is displayed
	'Precondition	:None
	'Arguments		:selenium,strLoginUserName,strLoginPassword
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	*****************************************************************/	
	public String newUsrLogin(Selenium selenium, String strLoginUserName,
			String strLoginPassword) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();		
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			
			try{
				selenium.selectWindow("");			
			}catch(Exception e){			
			}			
			try{
				selenium.windowMaximize();
				selenium.windowMaximize();
				selenium.windowFocus();
			}catch(Exception e){
				log4j.info(e);
			}
					
			selenium.type(propElementDetails.getProperty("Login.UserName"),
					strLoginUserName);
			selenium.type(propElementDetails.getProperty("Login.Password"),
					strLoginPassword);			
			selenium.click(propElementDetails.getProperty("Login.Submit"));
			selenium.waitForPageToLoad(gstrTimeOut);		

			int intCnt=0;
			do{
				try {

					assertEquals("Set Up Your Password", selenium
							.getText(propElementDetails.getProperty("SetUpPwd")));
					break;
				} 
				catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				}catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<40);
			
			try {

				assertEquals("Set Up Your Password", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));

				log4j.info("Set Up Your Password is displayed");

				selenium.type(propElementDetails
						.getProperty("SetUpPwd.NewUsrName"), strLoginPassword);
				selenium
						.type(propElementDetails
								.getProperty("SetUpPwd.CofrmUsrName"),
								strLoginPassword);				
				selenium.click(propElementDetails
						.getProperty("SetUpPwd.Submit"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					selenium.selectWindow("");
					selenium.selectFrame("Data");
				}catch(Exception e){
					
				}
				
			} catch (AssertionError Ae) {

				strErrorMsg = "Set Up Your Password is NOT displayed" + Ae;
				log4j.info("Set Up Your Password is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("newUsrLogin function failed" + e);
			strErrorMsg = "newUsrLogin function failed" + e;
		}
		return strErrorMsg;
	}

	/*****************************************************************
	'Description	:Verify 'set up user password'screen is displayed
	'Precondition	:None
	'Arguments		:selenium,strLoginUserName,strLoginPassword
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	*****************************************************************/	
	public String newUsrLoginForSingleRegion(Selenium selenium, String strLoginUserName,
			String strLoginPassword) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();		
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			
			try{
				selenium.selectWindow("");		
			}catch(Exception e){				
			}
	
			try{
				selenium.windowFocus();				
			}catch(Exception e){
				log4j.info(e);
			}
					
			selenium.type(propElementDetails.getProperty("Login.UserName"),
					strLoginUserName);
			selenium.type(propElementDetails.getProperty("Login.Password"),
					strLoginPassword);
			
			selenium.click(propElementDetails.getProperty("Login.Submit"));
			selenium.waitForPageToLoad(gstrTimeOut);
		

			int intCnt=0;
			do{
				try {

					assertEquals("Set Up Your Password", selenium
							.getText(propElementDetails.getProperty("SetUpPwd")));
					break;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<70);
			
			try {

				assertEquals("Set Up Your Password", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));

				log4j.info("Set Up Your Password is displayed");

				selenium.type(propElementDetails
						.getProperty("SetUpPwd.NewUsrName"), strLoginPassword);
				selenium
						.type(propElementDetails
								.getProperty("SetUpPwd.CofrmUsrName"),
								strLoginPassword);
				
				selenium.click(propElementDetails
						.getProperty("SetUpPwd.Submit"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					selenium.selectWindow("");
					selenium.selectFrame("Data");
				}catch(Exception e){					
				}
				
				intCnt = 0;
				do {
					try {

						assertEquals("View", selenium
								.getText(propElementDetails
										.getProperty("View.ViewLink")));
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
					assertEquals("View", selenium.getText(propElementDetails
							.getProperty("View.ViewLink")));

					log4j.info("User default region is opened");

				} catch (AssertionError Ae) {

					strErrorMsg = "User default region is NOT opened" + Ae;
					log4j.info("User default region is NOT opened" + Ae);
				}
				
			} catch (AssertionError Ae) {

				strErrorMsg = "Set Up Your Password is NOT displayed" + Ae;
				log4j.info("Set Up Your Password is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("newUsrLogin function failed" + e);
			strErrorMsg = "newUsrLogin function failed" + e;
		}
		return strErrorMsg;
	}

	
	/*******************************************************************
	'Description	:Verify navigate to user deafult region is displayed
	'Precondition	:None
	'Arguments		:selenium,strRegn
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	********************************************************************/
	
	public String navUserDefaultRgn(Selenium selenium, String strRegn)
			throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try{
				selenium.windowMaximize();
				selenium.windowMaximize();
				selenium.windowFocus();
			}catch(Exception e){
				log4j.info(e);
			}
					
			selenium.select(propElementDetails
					.getProperty("SelectRegion.Region"), "label=" + strRegn
					+ "");

			selenium.click(propElementDetails.getProperty("SelectRegion.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			Thread.sleep(10000);

			try{
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}catch(Exception e){
				
			}
			
			int intCnt = 0;
			do {
				try {

					assertEquals("View", selenium.getText(propElementDetails
							.getProperty("View.ViewLink")));
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
				assertEquals("View", selenium.getText(propElementDetails
						.getProperty("View.ViewLink")));

				log4j.info("User default region is opened");

			} catch (AssertionError Ae) {

				strErrorMsg = "User default region is NOT opened" + Ae;
				log4j.info("User default region is NOT opened" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navUserDefaultRgn function failed" + e);
			strErrorMsg = "navUserDefaultRgn function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	'Description	:Verify logout of apllication
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	********************************************************************/
	public String logout(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage
		General objGeneral = new General();

		try {

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			} catch (Exception e) {

			}

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
			if (selenium.isElementPresent("link=refresh")
					&& selenium
							.isElementPresent("//div[@id='reloadingMessage']/span"
									+ "[@id='reloadingText'][text()='reloading information']")) {
				try {
					selenium
							.isVisible("//div[@id='reloadingMessage']/span"
									+ "[@id='reloadingText'][text()='reloading information']");

					Thread.sleep(10000);

				} catch (Exception e) {
					try {
						objGeneral.refreshPageNew(selenium);

					} catch (Exception Ae) {

					}
					Thread.sleep(1000);
				}
			}

			if (selenium.isElementPresent(propElementDetails
					.getProperty("Logout"))) {

				selenium.click(propElementDetails.getProperty("Logout"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intRSCount = 0;
				while (selenium.isElementPresent(propElementDetails
						.getProperty("Login.UserName")) == false
						&& intRSCount < 10) {
					try {
						selenium
								.click(propElementDetails.getProperty("Logout"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							selenium.selectWindow("");
						} catch (Exception e) {

						}
						break;
					} catch (Exception e) {
						intRSCount++;
					}

				}

				try {
					selenium.selectWindow("");
				} catch (Exception e) {

				}

				intCnt = 0;
				do {
					try {
						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Login.UserName")));
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
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Login.UserName")));

					log4j.info("Login page is displayed");
				} catch (AssertionError Ae) {

					strErrorMsg = "Login page is NOT displayed" + Ae;
					log4j.info("Login page is NOT displayed" + Ae);
				}
			} else if (selenium.isElementPresent(propElementDetails
					.getProperty("LogoutNormal"))) {

				selenium.click(propElementDetails.getProperty("LogoutNormal"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intRSCount = 0;
				while (selenium.isElementPresent(propElementDetails
						.getProperty("Login.UserName")) == false
						&& intRSCount < 10) {
					try {
						selenium
								.click(propElementDetails.getProperty("LogoutNormal"));
						selenium.waitForPageToLoad(gstrTimeOut);

						try {
							selenium.selectWindow("");
						} catch (Exception e) {

						}
						break;
					} catch (Exception e) {
						intRSCount++;
					}

				}

				try {
					selenium.selectWindow("");
				} catch (Exception e) {

				}

				intCnt = 0;
				do {
					try {
						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("Login.UserName")));
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
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Login.UserName")));

					log4j.info("Login page is displayed");
				} catch (AssertionError Ae) {

					strErrorMsg = "Login page is NOT displayed" + Ae;
					log4j.info("Login page is NOT displayed" + Ae);
				}
			}

		} catch (Exception e) {
			log4j.info("logout function failed" + e);
			strErrorMsg = "logout function failed" + e;
		}
		return strErrorMsg;
	}

	/***************************************************************
	'Description	:Login as inactive user and verify error message
	'Precondition	:None
	'Arguments		:selenium,strUserName,strPassword
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	****************************************************************/
	public String loginAsInActiveUsrVarMsg(Selenium selenium,
			String strLoginUserName, String strLoginPassword, String strText,
			String strMsg) throws Exception {

		String strErrorMsg = "";// variable to store error message
		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));

			selenium.type(propElementDetails.getProperty("Login.UserName"),
					strLoginUserName);
			selenium.type(propElementDetails.getProperty("Login.Password"),
					strLoginPassword);

			selenium.click(propElementDetails.getProperty("Login.Submit"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Account Deactivated",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Account Deactivated page is displayed");
				assertEquals(strMsg, selenium.getText("css=font"));
				log4j.info(strMsg + " is displayed.");
				assertEquals(
						strText,
						selenium.getText("//div[@id='mainContainer']/div/table/tbody/tr/td/p[2]/b/font"));
				log4j.info(strText + "' is displayed.");
			} catch (AssertionError Ae) {

				strErrorMsg = "Account Deactivated page is NOT displayed" + Ae;
				log4j.info("Account Deactivated page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("login function failed" + e);
			strErrorMsg = "login function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***************************************************************
	'Description	:Login as inactive user and verify error message
	'Precondition	:None
	'Arguments		:selenium,strUserName,strPassword
	'Returns		:String
	'Date	 		:4-April-2012
	'Author			:QSG
	'--------------------------------------------------------------
	'Modified Date                            Modified By
	'4-April-2012                               <Name>
	****************************************************************/
	public String vfyLoginPage(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			try {

				assertTrue(selenium.isElementPresent("main"));
				log4j.info("'Intermedix emsystems'is displayed");

				assertEquals("EMResource ~ Login", selenium.getTitle());
				log4j.info("EMResource as title is displayed");

				assertTrue(selenium.isElementPresent("loginName"));
				log4j.info("Username text field is displayed");

				assertTrue(selenium.isElementPresent("password"));
				log4j.info("Password text field is displayed");

				assertTrue(selenium.isElementPresent("LoginBtn"));
				log4j.info("'Log In' button is displayed");

				assertTrue(selenium.isElementPresent("link=Help"));
				log4j.info("'Help' Link is displayed");

				assertTrue(selenium.isElementPresent("link=www.intermedix.com"));
				log4j.info("'www.intermedix.com' link with copyright edition is displayed");

				assertTrue(selenium
						.isTextPresent("� 1998-2013 Intermedix / EMSystems  All rights reserved"));
				log4j.info("Password text field is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "" + Ae;
				log4j.info("" + Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "" + e;
		}
		return strErrorMsg;
	}

	//start//checkForgotUsernameAndPasswordLink//
	/********************************************************************************
	' Description		: Verify forgot password  and username link is present or not
	' Precondition		: N/A 
	' Arguments			: selenium
	' Returns			: String 
	' Date				: 29/10/2013
	' Author			: Suhas 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	**********************************************************************************/
	public String checkForgotUsernameAndPasswordLink(Selenium selenium)
			throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.setTimeout(gstrTimeOut);
			selenium.open(propEnvDetails.getProperty("urlRel"));			
			try {
				selenium.selectWindow("");
			} catch (Exception e) {
				log4j.info(e);
			}

			try {
				selenium.windowMaximize();
				selenium.windowMaximize();
				selenium.windowFocus();
			} catch (Exception e) {
				log4j.info(e);
			}
			try {
				assertTrue(selenium
						.isElementPresent("link=exact:forgot username?"));
				log4j.info("Forget username link is present");
				assertTrue(selenium
						.isElementPresent("link=exact:forgot password?"));
				log4j.info("Forget password link is present");

			} catch (AssertionError Ae) {
				strErrorMsg = "Forget username and password  link is NOT present";
				log4j.info("Forget username and password  link is NOT present");
			}

		} catch (Exception e) {
			log4j.info("checkForgetUsernameAndPassword function failed " + e);
			strErrorMsg = "checkForgetUsernameAndPassword function failed" + e;
		}
		return strErrorMsg;
	}
	
	//end//checkForgotUsernameAndPasswordLink//
	
	//start//clickOnForgotUsernameLink//
	/****************************************************
	' Description		: Click on forgot username link
	' Precondition		: N/A 
	' Arguments			: selenium
	' Returns			: String 
	' Date				: 29/10/2013
	' Author			: Suhas
	'----------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	******************************************************/
	public String clickOnForgotUsernameLink(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.setTimeout(gstrTimeOut);
			selenium.click("link=exact:forgot username?");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Look Up Your UserID", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				assertEquals("Forgot your user ID?", selenium
						.getText(propElementDetails.getProperty("pref.challengeSubHeader")));
				log4j.info("Look Up Your UserID page is displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Look Up Your UserID page  NOT displayed";
				log4j.info("Look Up Your UserID page NOT  displayed");
			}

		} catch (Exception e) {
			log4j.info("clickOnForgotUsernameLink function failed " + e);
			strErrorMsg = "clickOnForgotUsernameLink function failed" + e;
		}
		return strErrorMsg;
	}
	//end//clickOnForgotUsernameLink//
	
	//start//provideEmailIdInForgotUserIdPage//
	/*************************************************************
	' Description		: Provide email id in forgot user id page
	' Precondition		: N/A 
	' Arguments			: selenium, strEmail
	' Returns			: String 
	' Date				: 29/10/2013
	' Author			: Suhas
	'-------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	****************************************************************/
	public String provideEmailIdInForgotUserIdPage(Selenium selenium,
			String strEmail) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.setTimeout(gstrTimeOut);
			try {

				selenium.type("css=#username", strEmail);
				selenium.click(propElementDetails
						.getProperty("pref.challengesetup.submit"));
				selenium.waitForPageToLoad(gstrTimeOut);
				assertEquals("Verification Status",
						selenium.getText(propElementDetails
								.getProperty("SetUpPwd")));
				log4j.info(" Verification Status page is displayed ");

			} catch (AssertionError Ae) {
				strErrorMsg = "Verification Status page NOT displayed";
				log4j.info("Verification Status page NOT displayed");
			}

		} catch (Exception e) {
			log4j.info(" provideEmailIdInForgotUserIdPage function failed" + e);
			strErrorMsg = "provideEmailIdInForgotUserIdPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	// end//provideEmailIdInForgotUserIdPage//
	

	//start//clickOnForgotPasswordLink//
	/*******************************************************************************************
	' Description	: Function to click on forgot password link
	' Precondition	: N/A 
	' Arguments		: selenium
	' Returns		: String 
	' Date			: 07/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String clickOnForgotPasswordLink(Selenium selenium) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.setTimeout(gstrTimeOut);
			selenium.click("link=exact:forgot password?");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Reset Your Password", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				assertEquals("Forgot your password?", selenium
						.getText(propElementDetails.getProperty("pref.challengeSubHeader")));
				log4j.info("Reset Your Password page is displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Reset Your Password page  NOT displayed";
				log4j.info("Reset Your Password page NOT  displayed");
			}

		} catch (Exception e) {
			log4j.info("clickOnForgotPasswordLink function failed " + e);
			strErrorMsg = "clickOnForgotPasswordLink function failed" + e;
		}
		return strErrorMsg;
	}
	//end//clickOnForgotPasswordLink//
	
	//start//provideUserIdInResetPasswordPage//
	/*******************************************************************************************
	' Description	: Function to provide user id in 'Reset Password Page'
	' Precondition	: N/A 
	' Arguments		: selenium, strUserId
	' Returns		: String 
	' Date			: 07/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String provideUserIdInResetPasswordPage(Selenium selenium,
			String strUserId) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.setTimeout(gstrTimeOut);
			try {

				selenium.type("css=#username", strUserId);
				selenium.click(propElementDetails
						.getProperty("pref.challengesetup.submit"));
				selenium.waitForPageToLoad(gstrTimeOut);
				assertEquals("Verification Status", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				assertEquals(
						"Your user ID has been verified. Check your email for more information.",
						selenium.getText(propElementDetails
								.getProperty("pref.challengeSubHeader")));
				log4j.info(" Verification Status page is displayed ");

			} catch (AssertionError Ae) {
				strErrorMsg = "Verification Status page NOT displayed";
				log4j.info("Verification Status page NOT displayed");
			}

		} catch (Exception e) {
			log4j.info(" provideUserIdInResetPasswordPage function failed" + e);
			strErrorMsg = "provideUserIdInResetPasswordPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	//end//provideUserIdInResetPasswordPage//	

	//start//provideAnswerInPasswordChallengePage//
	/*******************************************************************************************
	' Description	: This function is used to provide answer in 'Password Challenge Page'
	' Precondition	: N/A 
	' Arguments		: selenium, strAnswer, strQuestion, strLink
	' Returns		: String 
	' Date			: 07/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/	
	public String provideAnswerInPasswordChallengePage(Selenium selenium,
			String strAnswer, String strQuestion, String strLink)
			throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.setTimeout(gstrTimeOut);
			log4j.info("The link is "+strLink);
			selenium.open(strLink);

			try {
				selenium.selectWindow("");
			} catch (Exception e) {
				log4j.info(e);
			}

			try {
				selenium.windowMaximize();
				selenium.windowMaximize();
				selenium.windowFocus();
			} catch (Exception e) {
				log4j.info(e);
			}
			try {
				int intCnt = 0;
				do {
					try {
						assertEquals("Password Challenge", selenium
								.getText(propElementDetails
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

				assertEquals("Password Challenge", selenium
						.getText(propElementDetails.getProperty("SetUpPwd")));
				assertEquals(strQuestion, selenium.getText(propElementDetails
						.getProperty("pref.challengeSubHeader")));
				log4j.info("The question "+strQuestion+" is retained");
				selenium.type(propElementDetails
						.getProperty("pref.challengesetup.answer"), strAnswer);
				selenium.click(propElementDetails
						.getProperty("pref.challengesetup.submit"));
				log4j.info("Answer is provided in password challenge page ");

			} catch (AssertionError Ae) {
				strErrorMsg = "Answer is not provied in password challenge page";
				log4j.info("Answer is not provied in password challenge page");
			}

		} catch (Exception e) {
			log4j.info(" provideAnswerInPasswordChallengePage function failed"
					+ e);
			strErrorMsg = "provideAnswerInPasswordChallengePage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	//end//provideAnswerInPasswordChallengePage//
	
	//start//resetPassword//
	/*******************************************************************************************
	' Description	: This function is for re-set user password
	' Precondition	: N/A 
	' Arguments		: selenium, strLoginUserName, strLoginPassword
	' Returns		: String 
	' Date			: 07/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String resetPassword(Selenium selenium, String strLoginUserName,
			String strLoginPassword) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			int intCnt = 0;
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
						.getProperty("SetUpPwd.NewUsrName"), strLoginPassword);
				selenium.type(propElementDetails.getProperty("SetUpPwd.CofrmUsrName"),
								strLoginPassword);
				selenium.click(propElementDetails
						.getProperty("SetUpPwd.Submit"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals("Password Status",
							selenium.getText(propElementDetails
									.getProperty("SetUpPwd")));
					assertEquals("Password set or changed successfully.",
							selenium.getText(propElementDetails
									.getProperty("pref.challengeSubHeader")));
					log4j.info("password reset successfully");
				} catch (AssertionError Ae) {

					strErrorMsg = "password reset is NOT successfull" + Ae;
					log4j.info("password reset is NOT successfull" + Ae);
				}
			} catch (AssertionError Ae) {

				strErrorMsg = "Set Up Your Password is NOT displayed" + Ae;
				log4j.info("Set Up Your Password is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("newUsrLogin function failed" + e);
			strErrorMsg = "newUsrLogin function failed" + e;
		}
		return strErrorMsg;
	}
	// end//resetPassword//
	
	//start//provideUserIdAndVryErrMsgInResetPasswordPage//
	/*******************************************************************************************
	' Description		: Function to verify error message in reset password page
	' Precondition		: N/A 
	' Arguments			: selenium, strUserId, strErrMsg
	' Returns			: String 
	' Date				: 11/11/2013
	' Author			: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/	
	public String provideUserIdAndVryErrMsgInResetPasswordPage(
			Selenium selenium, String strUserId, String ErrMSg)
			throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.setTimeout(gstrTimeOut);
			try {

				selenium.type("css=#username", strUserId);
				selenium.click(propElementDetails
						.getProperty("pref.challengesetup.submit"));
				selenium.waitForPageToLoad(gstrTimeOut);
				assertEquals(ErrMSg, selenium
						.getText("//div[@id='formErrors']"));
				log4j.info(ErrMSg + " message is displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = ErrMSg + " message is NOT displayed";
				log4j.info(ErrMSg + " message is NOT displayed");
			}

		} catch (Exception e) {
			log4j
					.info(" provideUserIdAndVryErrMsgInResetPasswordPage function failed"
							+ e);
			strErrorMsg = "provideUserIdAndVryErrMsgInResetPasswordPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	//end//provideUserIdAndVryErrMsgInResetPasswordPage//
	
	//start//provideEmailIdAndVryErrMsgInForgotUserIdPage//
	/*******************************************************************************************
	' Description		: Function to verify error message in forgot password page
	' Precondition		: N/A 
	' Arguments			: selenium, strEmail, strErrMsg
	' Returns			: String 
	' Date				: 11/11/2013
	' Author			: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/	
	public String provideEmailIdAndVryErrMsgInForgotUserIdPage(Selenium selenium,
			String strEmail,String strErrMsg) throws Exception {
		String strErrorMsg = "";// variable to store error mesage
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.setTimeout(gstrTimeOut);
			try {

				selenium.type("css=#username", strEmail);
				selenium.click(propElementDetails
						.getProperty("pref.challengesetup.submit"));
				selenium.waitForPageToLoad(gstrTimeOut);
				assertEquals(strErrMsg,selenium.getText("//div[@id='formErrors']"));
				log4j.info(strErrMsg+ " message is displayed ");

			} catch (AssertionError Ae) {
				strErrorMsg = strErrMsg+ " message is NOT displayed ";
				log4j.info(strErrMsg+ " message is NOT displayed "+Ae);
			}

		} catch (Exception e) {
			log4j.info(" provideEmailIdAndVryErrMsgInForgotUserIdPage function failed" + e);
			strErrorMsg = "provideEmailIdAndVryErrMsgInForgotUserIdPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	//end//provideEmailIdAndVryErrMsgInForgotUserIdPage//

}