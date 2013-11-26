package com.qsgsoft.EMResource.shared;


import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/************************************************************************
' Description :This class includes common functions of Instant Messaging
' Precondition:
' Date		  :23-April-2012
' Author	  :QSG
'------------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'***********************************************************************/

public class InstantMessaging {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.InstantMessaging");

	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;
	
	public String gstrTimeOut ="";
	
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();

	ReadData rdExcel;
	
	/***********************************************************************
	'Description	:Verify instant messaging page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:23-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'23-April-2012                               <Name>
	************************************************************************/
	
	public String navInstantMesgingPage(Selenium selenium) throws Exception {

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
					.getProperty("InstantMessagingLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));

			try {
				assertEquals("Instant Messaging", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Instant Messaging page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Instant Messaging page is NOT displayed" + Ae;
				log4j.info("Instant Messaging page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j
					.info("navigate to instant messaging page function failed"
							+ e);
			strErrorMsg = "navigate to instant messaging page function failed"
					+ e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify Create New Private Chat page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:23-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'23-April-2012                               <Name>
	************************************************************************/
	
	public String navCreateNewPrivateChatPge(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			General objGeneral=new General();//object of class General

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));

			selenium.click(propElementDetails
					.getProperty("InstantMessaging.NewPrivateChat"));

			String strElementID = propElementDetails
					.getProperty("IM.NewPrivateChat.ListAll");
			strErrorMsg = objGeneral.CheckForElements(selenium, strElementID);
			
			
			try {
				assertEquals("", strErrorMsg);
				assertEquals("Create New Chat (1 of 2)", selenium
						.getText(propElementDetails
								.getProperty("IM.NewPrivateChat.Header")));
				log4j.info("Create New Chat  frame is  displayed");

				try {
					assertTrue(selenium.isVisible(propElementDetails
							.getProperty("IM.NewPrivateChat.ListAll"))
							&& selenium
									.isVisible(propElementDetails
											.getProperty("IM.NewPrivateChat.FindUsers"))
							&& selenium
									.isVisible(propElementDetails
											.getProperty("IM.NewPrivateChat.SearchField"))
							&& selenium
									.isVisible(propElementDetails
											.getProperty("IM.NewPrivateChat.SearchText"))
							&& selenium
									.isVisible(propElementDetails
											.getProperty("IM.NewPrivateChat.ResourceList"))
							&& selenium
									.isVisible(propElementDetails
											.getProperty("IM.NewPrivateChat.FindResourceUsers")));

					log4j
							.info("'Create New Chat (1 of 2)' window is  popped up."
									+ "	The following radio buttons are  displayed in the window."
									+ "1. List All Users."
									+ "2. Find user(s) where, contains."
									+ "3. Find user(s) for resource. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "	'Create New Chat (1 of 2)' window is NOT popped up."
							+ "	The following radio buttons are NOT displayed in the window."
							+ "1. List All Users."
							+ "2. Find user(s) where, contains."
							+ "3. Find user(s) for resource. " + Ae;
					log4j
							.info("'Create New Chat (1 of 2)' window is NOT popped up."
									+ "	The following radio buttons are NOT displayed in the window."
									+ "1. List All Users."
									+ "2. Find user(s) where, contains."
									+ "3. Find user(s) for resource. " + Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Create New Chat  frame is NOT displayed" + Ae;
				log4j.info("Create New Chat  frame is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navigate to Create New Private Chat frame "
					+ "function failed" + e);
			strErrorMsg = "navigate to Create New Private Chat frame"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify New Conference page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:23-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'23-April-2012                               <Name>
	************************************************************************/
	
	public String navNewConference(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			General objGeneral=new General();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.click(propElementDetails
					.getProperty("InstantMessaging.NewConference"));
			
			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));
			
			String strElementID = propElementDetails
					.getProperty("IM.NewConference.Header");
			strErrorMsg = objGeneral.CheckForElements(selenium, strElementID);

			try {
				assertEquals("Create New Conference", selenium
						.getText(propElementDetails
								.getProperty("IM.NewConference.Header")));
				log4j.info("Create New Conference  frame is  displayed");

				try {
					assertTrue(selenium.isVisible(propElementDetails
							.getProperty("IM.NewConference.RoomName"))
							&& selenium
									.isVisible(propElementDetails
											.getProperty("IM.NewConference.Description")));

					log4j
							.info("'Create New Conference' window is popped up with "
									+ "'Room Name' and 'Description' fields. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "'Create New Conference' window is NOT popped up with"
							+ " 'Room Name' and 'Description' fields. " + Ae;
					log4j
							.info("'Create New Conference' window is NOT popped up with "
									+ "'Room Name' and 'Description' fields. "
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Create New Conference page is NOT displayed"
						+ Ae;
				log4j.info("Create New Conference page is NOT displayed" + Ae);
			}
		

		} catch (Exception e) {
			log4j
					.info("navigate to Create New Conference page function failed"
							+ e);
			strErrorMsg = "navigate to Create New Conference page function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:navigating to 'Invite to Conference (1 of 2)' window is displayed. 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'23-April-2012                               <Name>
	************************************************************************/
	
	public String navInviteToConference1Of2Page(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			General objGeneral=new General();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));
			
			selenium.click(propElementDetails.getProperty("InstantMessaging.Invite"));
			Thread.sleep(6000);
			String strElementID ="css=#inviteMemberDialog > div.titleBar > div.title";
			strErrorMsg = objGeneral.CheckForElements(selenium, strElementID);

			try {
				assertEquals("Invite to Conference (1 of 2)", 
						selenium.getText("css=#inviteMemberDialog > div.titleBar > div.title"));
				log4j.info("'Invite to Conference (1 of 2)' window is displayed. ");
			} catch (AssertionError Ae) {
				strErrorMsg = "'Invite to Conference (1 of 2)' window is NOT displayed. "+ Ae;
				log4j.info("'Invite to Conference (1 of 2)' window is NOT displayed. ");
			}
		
		} catch (Exception e) {
			log4j.info("navInviteToConference1Of2Page page function failed"+ e);
			strErrorMsg = "navigate to Create New Conference page function failed"+ e;
		}
		return strErrorMsg;
	}
	/*************************************************************
	'Description	:navigating to Create New Chat (2 of 2)' frame
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date                                    Modified By
	'23-April-2012                                    <Name>
	**************************************************************/
	
	public String navCreateNewPrivateChat2Of2Pge(Selenium selenium,
			String strLabel,String strSearchText) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));
			
			selenium.select(propElementDetails.getProperty("PrivateChatSearchField"), "label="+strLabel);
			selenium.type(propElementDetails.getProperty("PrivateChatSearchText"), strSearchText);
			selenium.click(propElementDetails.getProperty("PrivateChat_DialougeButton"));
			Thread.sleep(30000);
			try {
				assertEquals("Create New Chat (2 of 2)", selenium
						.getText(propElementDetails
								.getProperty("IM.NewPrivateChat.Header")));
				log4j.info("Create New Chat (2 of 2)  frame is  displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = "Create New Chat (2 of 2)  frame is NOT displayed" + Ae;
				log4j.info("Create New Chat (2 of 2)  frame is NOT displayed" + Ae);
			}
		
		} catch (Exception e) {
			log4j.info("navCreateNewPrivateChat2Of2Pge function failed"+ e);
			strErrorMsg = "navCreateNewPrivateChat2Of2Pge  function failed"+ e;
		}
		return strErrorMsg;
	}
	/*********************************************************************
	'Description	:navigating to 'Invite to Conference (1 of 2)' window  
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Dec-2012
	'Author			:QSG
	'---------------------------------------------------------------------
	'Modified Date                            Modified By
	'23-April-2012                               <Name>
	**********************************************************************/
	
	public String navToInviteConference2Of2Pge(Selenium selenium,
			String strLabel,String strSearchText) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));
			
			selenium.select("xpath=(//select[@name='searchField'])[2]", "label="+strLabel);
			selenium.type("xpath=(//input[@name='searchText'])[2]", strSearchText);
			selenium.click("id=inviteMemberDialog_buttons_next");
			try {
				assertEquals("Invite to Conference (2 of 2)", 
						selenium.getText("css=#inviteMemberDialog > div.titleBar > div.title"));
				log4j.info("'Invite to Conference (2 of 2)' window is displayed. ");
			} catch (AssertionError Ae) {
				strErrorMsg = "'Invite to Conference (2 of 2)' window is NOT displayed. "+ Ae;
				log4j.info("'Invite to Conference (2 of 2)' window is NOT displayed. ");
			}
		
		} catch (Exception e) {
			log4j.info("navToInviteConference2Of2Pge function failed"+ e);
			strErrorMsg = "navToInviteConference2Of2Pge  function failed"+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Create New Conference 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'23-April-2012                               <Name>
	************************************************************************/

	public String CreateConference(Selenium selenium, String strRoomName,
			String strdescptn) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));
			selenium.type(propElementDetails
					.getProperty("IM.NewConference.RoomName"), strRoomName);
			selenium.type(propElementDetails
					.getProperty("IM.NewConference.Description"), strdescptn);
			selenium.click("id=createNewConferenceDialog_closeButton");

		} catch (Exception e) {
			log4j.info("navigate to Create New Conference page function failed"
					+ e);
			strErrorMsg = "navigate to Create New Conference page function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/*******************************************************************
	'Description	:verify Error Message For Role In IM PrivateChatPage
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------------
	'Modified Date                                         Modified By
	'23-April-2012                                         <Name>
	********************************************************************/

	public String verifyErrorMsgForRoleInIMPrivateChatPage(Selenium selenium,
			String strRolename) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame(propElementDetails
					.getProperty("messagingFrame"));
			try {
				assertEquals(
						"No users found where Role (Type of Users) contains '"
								+ strRolename + "'",
						selenium.getText("id=createNewPrivateChatDialog_step2_noResults"));
				selenium.click("id=createNewPrivateChatDialog_buttons_cancel");
				Thread.sleep(30000);
				log4j.info("No users found where Role (Type of Users) contains '"
						+ strRolename + " is  displayed");
			} catch (AssertionError Ae) {
				strErrorMsg = "No users found where Role (Type of Users) contains '"
						+ strRolename + " is  displayed" + Ae;
				log4j.info("No users found where Role (Type of Users) contains '"
						+ strRolename + " is  displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("verifyErrorMsgForRoleInIMPrivateChatPage function failed"
					+ e);
			strErrorMsg = "verifyErrorMsgForRoleInIMPrivateChatPage  function failed"
					+ e;
		}
		return strErrorMsg;
	}
}