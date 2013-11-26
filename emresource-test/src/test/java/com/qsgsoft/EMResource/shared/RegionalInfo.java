package com.qsgsoft.EMResource.shared;


import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/******************************************************************
' Description :This class includes common functions of RegionalInfo
' Precondition:
' Date		  :16-April-2012
' Author	  :QSG
'------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class RegionalInfo {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.RegionalInfo");

	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;
	
	public String gstrTimeOut ="";
	
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();

	ReadData rdExcel;
	
	
	/***********************************************************************
	'Description	:Verify Message Bulletin Board page is displayed 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:29-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	************************************************************************/
	
	public String navMessageBulletin(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails
					.getProperty("RegionalInfo.Link"));
			
			selenium.click(propElementDetails
					.getProperty("RegionalInfo.Calender.Link"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			
			Calendar cal=Calendar.getInstance();
			  int year=cal.get(Calendar.YEAR);
			  
			  
			  int intCnt=0;
				do{
					try{
						assertEquals("Message Bulletin Board " + year + "", selenium
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
				assertEquals("Message Bulletin Board " + year + "", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Message Bulletin Board " + year
						+ " page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Message Bulletin Board " + year
						+ " page is NOT displayed" + Ae;
				log4j.info("Message Bulletin Board " + year
						+ " page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navMessageBulletin Function failed"+e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************
	'Description	:Verify user list page is displayed when navigated
					through regional Info>>User
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:27-Jul-2012
	'Author			:QSG
	'------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>			                             <Name>
	********************************************************************/
	
	public String navRegionalInfoUsrPge(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.mouseOver(propElementDetails
					.getProperty("RegionalInfo.Link"));
			
			selenium.click("//a[text()='Regional Info']/following-sibling::div/a[text()='Users']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Users List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Users List' page is displayed ");
			} catch (AssertionError Ae) {

				strErrorMsg = "'Users List' page is NOT displayed " + Ae;
				log4j.info("'Users List' page is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("navRegionalInfoUsrPge function failed" + e);
			strErrorMsg = "navRegionalInfoUsrPge function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*********************************************************
	'Description	:Verify user details in the user list page
	'Precondition	:None
	'Arguments		:selenium,strUserName
	'Returns		:String
	'Date	 		:13-09-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'<date>		                               <Name>
	**********************************************************/
	
public String verifyUserDetailsInRegInfoUserList(Selenium selenium,String strUserName,String strFullName, String strOrg,String strPhone,String strEmail) throws Exception {

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
							+ "td[1][text()='" + strUserName + "']"));
			log4j.info("User " + strUserName
					+ " Present in the User List ");

			try {
				
				assertEquals(strFullName,selenium
						.getText("//table[@id='tblUsers']/tbody/tr/"
								+ "td[1][text()='" + strUserName + "']/parent::tr/td[2]"));
				log4j.info("User Full name " + strFullName
						+ " Present in the User List ");

			} catch (AssertionError Ae) {
				strErrorMsg = " User Full name " + strFullName
						+ " NOT Present in the User List " + Ae;
				log4j.info("User full name " + strFullName
						+ " NOT Present in the User List " + Ae);

			}		
			
			try {
				
				assertEquals(strOrg,selenium
						.getText("//table[@id='tblUsers']/tbody/tr/"
								+ "td[1][text()='" + strUserName + "']/parent::tr/td[3]"));
				log4j.info("User organization " + strOrg
						+ " Present in the User List ");

			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg+" User organization " + strOrg
						+ " NOT Present in the User List " + Ae;
				log4j.info("User organization " + strOrg
						+ " NOT Present in the User List " + Ae);

			}		
			
			try {
				
				assertEquals(strPhone,selenium
						.getText("//table[@id='tblUsers']/tbody/tr/"
								+ "td[1][text()='" + strUserName + "']/parent::tr/td[4]"));
				log4j.info("User phone " + strPhone
						+ " Present in the User List ");

			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg+" User phone " + strPhone
						+ " NOT Present in the User List " + Ae;
				log4j.info("User phone " + strPhone
						+ " NOT Present in the User List " + Ae);

			}		
			
			try {
				
				assertEquals(strEmail,selenium
						.getText("//table[@id='tblUsers']/tbody/tr/"
								+ "td[1][text()='" + strUserName + "']/parent::tr/td[5]"));
				log4j.info("User email " + strEmail
						+ " Present in the User List ");

			} catch (AssertionError Ae) {
				strErrorMsg = strErrorMsg+" User email " + strEmail
						+ " NOT Present in the User List " + Ae;
				log4j.info("User email " + strEmail
						+ " NOT Present in the User List " + Ae);

			}		
		} catch (AssertionError Ae) {
			strErrorMsg = "User " + strUserName
					+ " NOT Present in the User List " + Ae;
			log4j.info("User " + strUserName
					+ " NOT Present in the User List " + Ae);

		}		
		
		
	} catch (Exception e) {
		log4j.info("verifyUserDetailsInRegInfoUserList function failed" + e);
		strErrorMsg = "verifyUserDetailsInRegInfoUserList function failed" + e;
	}
	return strErrorMsg;
}
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail
	'Returns		:String
	'Date	 		:29-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	************************************************************************/
	
	public String createMessage(Selenium selenium, String strMsgDate,
			String strVerifyMsgDate, String strMsgTitle, String strMessage,
			String strContactEmail) throws Exception {

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
					.getProperty("MessageBulletin.CreateNewMesage"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("MessageBulletin.DateTrigger")));
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
				assertEquals("Create Bulletin Message", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create Bulletin Message page is  displayed");
				
				selenium.click(propElementDetails
						.getProperty("MessageBulletin.DateTrigger"));
				
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.EventDate"), strMsgDate);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Titlle"), strMsgTitle);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Content"), strMessage);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Contact"),
						strContactEmail);

				selenium.click(propElementDetails
						.getProperty("MessageBulletin.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				intCnt=0;
				do{
					try{
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
										+ "tr/th[1][text()='Action']/ancestor::thead/parent::table"
										+ "/tbody/tr/td[2][text()='"
										+ strMsgTitle
										+ "']/parent::tr/td[1]/a[text()='Edit']"));
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
							.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
									+ "tr/th[1][text()='Action']/ancestor::thead/parent::table"
									+ "/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']/parent::tr/td[1]/a[text()='Edit']"));
					
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
									+ "tr/th[1][text()='Action']/ancestor::thead/parent::table"
									+ "/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']/parent::tr/td[1]/a[text()='Delete']"));

					log4j.info("Edit and Delete link of " + strMsgTitle
							+ " is  displayed under Action column");

				} catch (AssertionError Ae) {

					strErrorMsg = "Edit and Delete link  of " + strMsgTitle
							+ " is NOT displayed under Action column";
					log4j.info("Edit and Delete link  of " + strMsgTitle
							+ " is NOT displayed under Action column" + Ae);
				}
				
				//Date is Mandatory
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[3]/a[text()='Date']/ancestor::tr/ancestor::"
									+ "table/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']"
									+ "/parent::tr/td[3][text()='"
									+ strVerifyMsgDate + "']"));
					log4j.info("Message Date " + strVerifyMsgDate
							+ " is displayed under Date column");

				} catch (AssertionError Ae) {
					strErrorMsg = "Message Date " + strVerifyMsgDate
							+ " is NOT displayed under Date column";
					log4j.info("Message Date " + strVerifyMsgDate
							+ " is NOT displayed under Date column" + Ae);
				}
				
				//Title is Mandatory

				try {


					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[2]/a[text()='Title']/ancestor::tr/ancestor::table"
									+ "/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']"));
					log4j.info("Message title is displayed under Title column");

					//Non mandatory
					if (strMessage.compareTo("") !=0 ){
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/form/table/thead"
											+ "/tr/th[4]/a[text()='Message']/ancestor::tr/ancestor::"
											+ "table/tbody/tr/td[2][text()='"
											+ strMsgTitle
											+ "']/parent::tr/td[4][text()='"
											+ strMessage + "']"));
							log4j.info("Message  is displayed");
							
						} catch (AssertionError Ae) {
							strErrorMsg = "Message  is NOT displayed under Title column";
							log4j.info("Message  is NOT displayed under Title column" + Ae);
						}
						
					}
					//Nonmandatory

					if (strContactEmail .compareTo("")!=0) {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
											+ "tr/th[5][text()='Contact']/ancestor::thead/parent::"
											+ "table/tbody/tr/td[2][text()='"
											+ strMsgTitle
											+ "']/parent::tr/td[5]/a[text()='"
											+ strContactEmail + "']"));
							log4j.info("Contact Email is displayed is"
									+ " displayed under contact column");

						} catch (AssertionError Ae) {
							strErrorMsg = "Contact Email  is NOT displayed";
							log4j
									.info("Contact Email  is NOT displayed under contact column"
											+ Ae);
						}
					}


				} catch (AssertionError Ae) {
					strErrorMsg = "Message title is NOT displayed under contact column";
					log4j
							.info("Message title is NOT displayed under contact column"
									+ Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Create Bulletin Message page is NOT displayed"
						+ Ae;
				log4j
						.info("Create Bulletin Message page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "createMessage Function failed"+e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message can be edited
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail
	'Returns		:String
	'Date	 		:30-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'30-May-2012                               <Name>
	************************************************************************/

	public String editMessage(Selenium selenium, String strMsgDate,
			String strVerifyMsgDate, String strMsgTitle,
			String strEditMsgTitle, String strMessage, String strContactEmail)
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

			selenium.click("//div[@id='mainContainer']/form/table"
					+ "/tbody/tr/td[2][text()='" + strMsgTitle
					+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Bulletin Message", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Bulletin Message page is  displayed");
				
				selenium.click(propElementDetails
						.getProperty("MessageBulletin.DateTrigger"));
				
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.EventDate"), strMsgDate);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Titlle"), strEditMsgTitle);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Content"), strMessage);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Contact"),
						strContactEmail);

				selenium.click(propElementDetails
						.getProperty("MessageBulletin.Save"));
				selenium.waitForPageToLoad("30000");

				//Title is Mandatory

				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[2]/a[text()='Title']/ancestor::tr/ancestor::table"
									+ "/tbody/tr/td[2][text()='"
									+ strEditMsgTitle
									+ "']"));
					log4j.info("Message title "+strEditMsgTitle+" is displayed");
					
					//Date is Mandatory
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/form/table/thead"
										+ "/tr/th[3]/a[text()='Date']/ancestor::tr/ancestor::"
										+ "table/tbody/tr/td[2][text()='"
										+ strEditMsgTitle
										+ "']"
										+ "/parent::tr/td[3][text()='"
										+ strVerifyMsgDate + "']"));
						log4j.info("Message Date is displayed");

					} catch (AssertionError Ae) {
						strErrorMsg = "Message Date is NOT displayed";
						log4j.info("Message Date is NOT displayed" + Ae);
					}
					

					//Non mandatory
					if (strMessage != "") {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/form/table/thead"
											+ "/tr/th[4]/a[text()='Message']/ancestor::tr/ancestor::"
											+ "table/tbody/tr/td[2][text()='"
											+ strEditMsgTitle
											+ "']/parent::tr/td[4][text()='"
											+ strMessage + "']"));
							log4j.info("Message  is displayed");
							
						} catch (AssertionError Ae) {
							strErrorMsg = "Message  is NOT displayed";
							log4j.info("Message  is NOT displayed" + Ae);
						}
						
					}
					
					//Nonmandatory

					if (strContactEmail != "") {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
											+ "tr/th[5][text()='Contact']/ancestor::thead/parent::"
											+ "table/tbody/tr/td[2][text()='"
											+ strEditMsgTitle
											+ "']/parent::tr/td[5]/a[text()='"
											+ strContactEmail + "']"));
							log4j
									.info("Contact Email is displayed is displayed");

						} catch (AssertionError Ae) {
							strErrorMsg = "Contact Email  is NOT displayed";
							log4j.info("Contact Email  is NOT displayed" + Ae);
						}
					}


				} catch (AssertionError Ae) {
					strErrorMsg = "Message title "+strEditMsgTitle+" is NOT displayed";
					log4j.info("Message title "+strEditMsgTitle+" is NOT displayeds");
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Bulletin Message page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Bulletin Message page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "editMessage Function failed"+e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message is DELETED
	'Precondition	:None
	'Arguments		:selenium,strMsgTitle
	'Returns		:String
	'Date	 		:29-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'18-April-2012                               <Name>
	************************************************************************/
	
	public String deleteMessage(Selenium selenium,String strMsgTitle)
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

			selenium.click("//div[@id='mainContainer']/form/table"
					+ "/tbody/tr/td[2][text()='" + strMsgTitle
					+ "']/parent::tr/td[1]/a[text()='Delete']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			Calendar cal=Calendar.getInstance();
			  int year=cal.get(Calendar.YEAR);


			try {
				assertEquals("Message Bulletin Board "+year, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Message Bulletin Board "+year+" page is  displayed");
				
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr/td[2][text()='"
									+ strMsgTitle + "']"));
					log4j.info("Message title is NOT displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Message title is displayed";
					log4j.info("Message title is  displayed" + Ae);
				}
				

			} catch (AssertionError Ae) {
				strErrorMsg = "Message Bulletin Board "+year+" page is NOT displayed" + Ae;
				log4j.info("Message Bulletin Board "+year+" page is NOT displayed" + Ae);
			}
			
	
		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "deleteMessage Function failed"+e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message can be edited
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail
	'Returns		:String
	'Date	 		:30-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'30-May-2012                               <Name>
	************************************************************************/
	
	public String navEditMessageBulletin(Selenium selenium, String strMsgDate,
			String strMsgTitle, String strMessage, String strContactEmail,boolean blnSave)
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

			selenium.click("//div[@id='mainContainer']/form/table"
					+ "/tbody/tr/td[2][text()='" + strMsgTitle
					+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Bulletin Message", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Bulletin Message page is  displayed");
				
				selenium.click(propElementDetails
						.getProperty("MessageBulletin.DateTrigger"));


				try {
					
					assertEquals(selenium
							.getValue(propElementDetails
									.getProperty("MessageBulletin.Titlle")),strMsgTitle);
					log4j.info("Message title is displayed");

					if(strMessage!=""){
						try {
							assertEquals(selenium
									.getText(propElementDetails
											.getProperty("MessageBulletin.Content")),strMessage);
							log4j.info("Message  is displayed");

							
						} catch (AssertionError Ae) {
							strErrorMsg = "Message  is NOT displayed";
							log4j.info("Message  is NOT displayed" + Ae);
						}
						
					}

					if (strContactEmail != "") {
						try {
							assertEquals(selenium
									.getValue(propElementDetails
											.getProperty("MessageBulletin.Contact")),strContactEmail);
							log4j
									.info("Contact Email is displayed is displayed");

						} catch (AssertionError Ae) {
							strErrorMsg = "Contact Email  is NOT displayed";
							log4j.info("Contact Email  is NOT displayed" + Ae);
						}
					}
					
					if(blnSave){
						selenium.click(propElementDetails
								.getProperty("MessageBulletin.Save"));
						selenium.waitForPageToLoad("30000");

						
					}


				} catch (AssertionError Ae) {
					strErrorMsg = "Message title is NOT displayed";
					log4j.info("Message title is NOT displayed" + Ae);
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Bulletin Message page is NOT displayed"
						+ Ae;
				log4j
						.info("Edit Bulletin Message page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "editMessage Function failed"+e;
		}
		return strErrorMsg;
	}
	
	/*********
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
	 
	public String vrfyUsrNameAndFNInRegInfo(Selenium selenium,
			String struserName, String strFullName) throws Exception {

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
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[text()='"
								+ struserName + "']"));
				log4j.info("User " + struserName
						+ " is listed on the 'Users List' screen.");
				log4j
						.info("Updated data "
								+ struserName
								+ "is displayed for the user in the 'Users List'screen.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Updated data "
						+ struserName
						+ "is displayed for the user in the 'Users List'screen.";
				log4j
						.info("Updated data "
								+ struserName
								+ "is NOT displayed for the user in the 'Users List'screen.");
			}
			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[1][text()='"
								+ struserName
								+ "']"
								+ "/parent::tr/td[2][text()='"
								+ strFullName
								+ "']"));
				log4j
						.info("Updated data "
								+ strFullName
								+ "is displayed for the user in the 'Users List'screen. ");
			} catch (AssertionError Ae) {
				strErrorMsg = "Updated data "
						+ strFullName
						+ "is NOT displayed for the user in the 'Users List'screen.";
				log4j
						.info("Updated data "
								+ strFullName
								+ "is NOT displayed for the user in the 'Users List'screen.");
			}

		} catch (Exception e) {
			log4j.info("vrfyUsrNameAndFNInRegInfo function failed" + e);
			strErrorMsg = "vrfyUsrNameAndFNInRegInfo function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*********
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
	 
	public String vrfyOrgPageEmailInReginfoUsr(Selenium selenium,
			String struserName, String strorg, String stremail,
			String strPhone, String strPrimaryEMail) throws Exception {

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
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[1][text()='"
								+ struserName
								+ "']"
								+ "/parent::tr/td[3][text()='" + strorg + "']"));
				log4j
						.info("Updated data "
								+ strorg
								+ "is displayed for the user in the 'Users List'screen.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Updated data "
						+ strorg
						+ "is displayed for the user in the 'Users List'screen.";
				log4j
						.info("Updated data "
								+ strorg
								+ "is NOT displayed for the user in the 'Users List'screen.");
			}
			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[1][text()='"
								+ struserName
								+ "']"
								+ "/parent::tr/td[4][text()='"
								+ strPhone
								+ "']"));
				log4j
						.info("Updated data "
								+ strPhone
								+ "is displayed for the user in the 'Users List'screen.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Updated data "
						+ strPhone
						+ "is displayed for the user in the 'Users List'screen.";
				log4j
						.info("Updated data "
								+ strPhone
								+ "is NOT displayed for the user in the 'Users List'screen.");
			}

			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[1][text()='"
								+ struserName
								+ "']"
								+ "/parent::tr/td[5][text()='"
								+ strPrimaryEMail + "," + stremail + "']"));
				log4j
						.info("Updated data "
								+ stremail
								+ "is displayed for the user in the 'Users List'screen.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Updated data "
						+ stremail
						+ "is displayed for the user in the 'Users List'screen.";
				log4j
						.info("Updated data "
								+ stremail
								+ "is NOT displayed for the user in the 'Users List'screen.");
			}

		} catch (Exception e) {
			log4j.info("vrfyOrgPageEmailInReginfoUsr function failed" + e);
			strErrorMsg = "vrfyOrgPageEmailInReginfoUsr function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail,blnSave
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                                 <Name>
	************************************************************************/
	
	public String fillMsgFieldsAndSave(Selenium selenium, String strMsgDate,
			String strMsgTitle, String strMessage, String strContactEmail,
			boolean blnSave) throws Exception {

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
					.getProperty("MessageBulletin.CreateNewMesage"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Create Bulletin Message", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create Bulletin Message page is  displayed");

				selenium.type(propElementDetails
						.getProperty("MessageBulletin.EventDate"), strMsgDate);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Titlle"), strMsgTitle);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Content"), strMessage);
				selenium.type(propElementDetails
						.getProperty("MessageBulletin.Contact"),
						strContactEmail);

				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("MessageBulletin.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
										+ "tr/th[1][text()='Action']/ancestor::thead/parent::table"
										+ "/tbody/tr/td[2][text()='"
										+ strMsgTitle + "']"));

						log4j.info("Message title is displayed");

					} catch (AssertionError Ae) {

						strErrorMsg = "Message title is NOT displayed";
						log4j.info("Message title is NOT displayed");
					}
				}

			} catch (AssertionError Ae) {
				strErrorMsg = "Create Bulletin Message page is NOT displayed"
						+ Ae;
				log4j
						.info("Create Bulletin Message page is NOT displayed"
								+ Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "fillMsgFieldsAndSave Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail,blnSave
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                                 <Name>
	************************************************************************/
	
	public String saveMessageAndVerifyThePage(Selenium selenium) throws Exception {

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
					.getProperty("MessageBulletin.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);

			try {
				assertEquals("Message Bulletin Board " + year + "", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Message Bulletin Board " + year
						+ " page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Message Bulletin Board " + year
						+ " page is NOT displayed" + Ae;
				log4j.info("Message Bulletin Board " + year
						+ " page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "saveMessageAndVerifyThePage Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail,blnSave
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                                 <Name>
	************************************************************************/
	
	public String navPreviousYear(Selenium selenium, int intPreviousYearCnt)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		Date_Time_settings dts = new Date_Time_settings();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			String strMsgDate = dts.timeNow("yyyy");

			for (int i = 0; i < intPreviousYearCnt; i++) {

				selenium.click(propElementDetails
						.getProperty("MessageBulletin.PreviousYear"));
				selenium.waitForPageToLoad(gstrTimeOut);
				strMsgDate = dts.AddYearToExisting(strMsgDate, -1, "yyyy");
			}

			try {
				assertEquals("Message Bulletin Board " + strMsgDate + "",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Message Bulletin Board " + strMsgDate
						+ " page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Message Bulletin Board " + strMsgDate
						+ " page is NOT displayed" + Ae;
				log4j.info("Message Bulletin Board " + strMsgDate
						+ " page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navPreviousYear Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                                 <Name>
	************************************************************************/
	
	public String cancelMessageCreationAndVerifyThePage(Selenium selenium) throws Exception {

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
					.getProperty("MessageBulletin.Cancel"));
			selenium.waitForPageToLoad(gstrTimeOut);

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);

			try {
				assertEquals("Message Bulletin Board " + year + "", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Message Bulletin Board " + year
						+ " page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Message Bulletin Board " + year
						+ " page is NOT displayed" + Ae;
				log4j.info("Message Bulletin Board " + year
						+ " page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "cancelMessageCreationAndVerifyThePage Function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail,blnSave
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                                 <Name>
	************************************************************************/
	
	public String onlyFillMsgFieldsAndSave(Selenium selenium,
			String strMsgDate, String strMsgTitle, String strMessage,
			String strContactEmail, boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails
					.getProperty("MessageBulletin.EventDate"), strMsgDate);
			selenium.type(propElementDetails
					.getProperty("MessageBulletin.Titlle"), strMsgTitle);
			selenium.type(propElementDetails
					.getProperty("MessageBulletin.Content"), strMessage);
			selenium.type(propElementDetails
					.getProperty("MessageBulletin.Contact"), strContactEmail);

			if (blnSave) {
				selenium.click(propElementDetails
						.getProperty("MessageBulletin.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
									+ "tr/th[1][text()='Action']/ancestor::thead/parent::table"
									+ "/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']"));

					log4j.info("Message title is displayed");

				} catch (AssertionError Ae) {

					strErrorMsg = "Message title is NOT displayed";
					log4j.info("Message title is NOT displayed");
				}
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "onlyFillMsgFieldsAndSave Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                                 <Name>
	************************************************************************/
	
	public String verifyMessageInMsgBulletingBoard(Selenium selenium,
			String strMsgDate,boolean blnMsgDate, String strMsgTitle,boolean blnMsgTitl,
			String strMessage,boolean blnMsg, String strContactEmail,boolean blnMsgMail) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Title is Mandatory

			if(blnMsgTitl){
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[2]/a[text()='Title']/ancestor::tr/ancestor::table"
									+ "/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']"));
					log4j.info("Message title is displayed under Title column");
					
				} catch (AssertionError Ae) {
					strErrorMsg = "Message title is NOT displayed under contact column";
					log4j
							.info("Message title is NOT displayed under contact column"
									);
				}

				}else{
					
					try {

						assertFalse(selenium
								.isElementPresent("//div[@id='mainContainer']/form/table/thead"
										+ "/tr/th[2]/a[text()='Title']/ancestor::tr/ancestor::table"
										+ "/tbody/tr/td[2][text()='"
										+ strMsgTitle
										+ "']"));
						log4j.info("Message title NOT is displayed under Title column");
						
					} catch (AssertionError Ae) {
						strErrorMsg = "Message title is displayed under contact column";
						log4j
								.info("Message title is displayed under contact column");
					}
				}
					

			
			if(blnMsgDate){
				
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[3]/a[text()='Date']/ancestor::tr/ancestor::"
									+ "table/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']"
									+ "/parent::tr/td[3][text()='"
									+ strMsgDate + "']"));
					log4j.info("Message Date " + strMsgDate
							+ " is displayed under Date column");

				} catch (AssertionError Ae) {
					strErrorMsg = "Message Date " + strMsgDate
							+ " is NOT displayed under Date column";
					log4j.info("Message Date " + strMsgDate
							+ " is NOT displayed under Date column");
				}
			}else{
				
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[3]/a[text()='Date']/ancestor::tr/ancestor::"
									+ "table/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']"
									+ "/parent::tr/td[3][text()='"
									+ strMsgDate + "']"));
					log4j.info("Message Date " + strMsgDate
							+ " is NOT displayed under Date column");

				} catch (AssertionError Ae) {
					strErrorMsg = "Message Date " + strMsgDate
							+ " is  displayed under Date column";
					log4j.info("Message Date " + strMsgDate
							+ " is  displayed under Date column");
				}
			}
			
			
			if(blnMsg){
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[4]/a[text()='Message']/ancestor::tr/ancestor::"
									+ "table/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']/parent::tr/td[4][text()='"
									+ strMessage + "']"));
					log4j.info("Message  is displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Message  is NOT displayed under Title column";
					log4j
							.info("Message  is NOT displayed under Title column");
				}
			}else{
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead"
									+ "/tr/th[4]/a[text()='Message']/ancestor::tr/ancestor::"
									+ "table/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']/parent::tr/td[4][text()='"
									+ strMessage + "']"));
					log4j.info("Message  NOT is displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Message  is  displayed under Title column";
					log4j
							.info("Message  is displayed under Title column");
				}
			}
			
			
			if(blnMsgMail){
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
									+ "tr/th[5][text()='Contact']/ancestor::thead/parent::"
									+ "table/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']/parent::tr/td[5]/a[text()='"
									+ strContactEmail + "']"));
					log4j.info("Contact Email is"
							+ " displayed under contact column");

				} catch (AssertionError Ae) {
					strErrorMsg = "Contact Email  is NOT displayed";
					log4j
							.info("Contact Email  is NOT displayed under contact column");
				}
			

			}else{
				
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/thead/"
									+ "tr/th[5][text()='Contact']/ancestor::thead/parent::"
									+ "table/tbody/tr/td[2][text()='"
									+ strMsgTitle
									+ "']/parent::tr/td[5]/a[text()='"
									+ strContactEmail + "']"));
					log4j.info("Contact Email is NOT displayed under contact column");

				} catch (AssertionError Ae) {
					strErrorMsg = "Contact Email  is  displayed under contact column";
					log4j
							.info("Contact Email  is  displayed under contact column");
				}
			}
			
			
			
		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "verifyMessageInMsgBulletingBoard Function failed"
					+ e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Verify Message can be edited
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                               <Name>
	************************************************************************/
	
	public String navEditMessageBulletinPge(Selenium selenium,
			String strMsgTitle) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/form/table"
					+ "/tbody/tr/td[2][text()='" + strMsgTitle
					+ "']/parent::tr/td[1]/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Bulletin Message", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Bulletin Message page is  displayed");

				selenium.click(propElementDetails
						.getProperty("MessageBulletin.DateTrigger"));

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Bulletin Message page is NOT displayed"
						+ Ae;
				log4j.info("Edit Bulletin Message page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navEditMessageBulletinPge Function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Verify Message is created
	'Precondition	:None
	'Arguments		:selenium,strMsgDate,strMsgTitle,strMessage,strContactEmail,blnSave
	'Returns		:String
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'3-Dec-2012                                 <Name>
	************************************************************************/
	
	public String navFutureYear(Selenium selenium, int intFutureYearCnt)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		Date_Time_settings dts = new Date_Time_settings();

		try {
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			String strMsgDate = dts.timeNow("yyyy");

			for (int i = 0; i < intFutureYearCnt; i++) {

				selenium.click(propElementDetails
						.getProperty("MessageBulletin.NextYear"));
				selenium.waitForPageToLoad(gstrTimeOut);
				strMsgDate = dts.AddYearToExisting(strMsgDate, 1, "yyyy");
			}

			try {
				assertEquals("Message Bulletin Board " + strMsgDate + "",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Message Bulletin Board " + strMsgDate
						+ " page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Message Bulletin Board " + strMsgDate
						+ " page is NOT displayed" + Ae;
				log4j.info("Message Bulletin Board " + strMsgDate
						+ " page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("" + e);
			strErrorMsg = "navPreviousYear Function failed" + e;
		}
		return strErrorMsg;
	}

	//start//navToRegionalInfoFindResourcePage//
	/*******************************************************************************************
	' Description		: Verify find resources page is displayed when navigated through Regional Info>>Find Resource
	' Precondition		: N/A 
	' Arguments			: selenium
	' Returns			: String 
	' Date				: 03/10/2013
	' Author			: Suhas
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToRegionalInfoFindResourcePage(Selenium selenium) throws Exception
	{
		String strReason="";		
		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.mouseOver(propElementDetails
					.getProperty("RegionalInfo.Link"));
			
			selenium.click("//a[text()='Regional Info']/following-sibling::div/a[text()='Find Resources']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Find Resources", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Find Resources' page is displayed ");
			} catch (AssertionError Ae) {

				strReason = "'Find Resources' page is NOT displayed " + Ae;
				log4j.info("'Find Resources' page is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToRegionalInfoFindResourcePage" + e);
			strReason = "navToRegionalInfoFindResourcePage" + e;
		}
		return strReason;
	}
	//end//navToRegionalInfoFindResourcePage//
}