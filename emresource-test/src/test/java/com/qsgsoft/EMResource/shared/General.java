package com.qsgsoft.EMResource.shared;


import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

import java.util.*;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/******************************************************************
' Description :This class includes common functions Login details
' Precondition:
' Date		  :2-March-2012
' Author	  :QSG
'-----------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class General  {
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.shared.General");

	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties propAutoItDetails ;
	Properties pathProps;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	
	public int intCount=0;
	public String strCount=""; 
	public String gstrTimeOut;
	ReadData rdExcel;
	Paths_Properties objAP = new Paths_Properties();
	
	/*********************************************************
	'Description	:Verify wait for element function
	'Precondition	:None
	'Arguments		:selenium,strElementID
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'---------------------------------------------------------
	'Modified Date                            Modified By
	'12-April-2012                               <Name>
	**********************************************************/
	
	public String CheckForElements(Selenium selenium, String strElementID)
			throws Exception {

		String strReason = "";
		
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		strCount = propEnvDetails.getProperty("WaitForElement");
		intCount = Integer.parseInt(strCount);

		int intSec=0;
		
		try {

			boolean blnFound=true;
			intSec=0;
			
			do{
				try{
					selenium.isElementPresent(strElementID);
					blnFound=false;
				}catch(Exception e){
					Thread.sleep(1000);
					intSec++;
				}
			}while(blnFound&&intSec<intCount);
				
		
			intSec = 0;
			while (selenium.isElementPresent(strElementID) == false
					&& intSec < intCount) {
				Thread.sleep(1000);
				intSec++;
			}

			try {
				assertTrue(selenium.isElementPresent(strElementID));
				intSec = 0;
				while (selenium.isVisible(strElementID) == false
						&& intSec < intCount) {
					Thread.sleep(1000);
					intSec++;
				}

				if (intSec >= intCount
						&& selenium.isVisible(strElementID) == false) {
					log4j.info("Element NOT Present");
					strReason = "Element NOT Present";
				}
			} catch (AssertionError e) {
				log4j.info("Element NOT Present");
				strReason = "Element NOT Present";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}

		return strReason;
	}
	
	  /*********************************************************
		  'Description :Verify wait for an element function
		  'Precondition :None
		  'Arguments  :selenium,strElementID
		  'Returns  :String
		  'Date    :5-April-2012
		  'Author   :QSG
		  '---------------------------------------------------------
		  'Modified Date                            Modified By
		  '12-April-2012                               <Name>
		  **********************************************************/

	public String checkForAnElements(Selenium selenium, String strElementID)
			throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			assertTrue(selenium.isElementPresent(strElementID));

		} catch (AssertionError Ae) {
			strReason = "Element NOT Present";

		}

		return strReason;
	}
	
	public String refreshPage(Selenium selenium) throws Exception{
		String strReason="";
		try{
			
			int intCnt=0;
			selenium.click("link=refresh");
			while(intCnt<30){
				try{				
					selenium.isVisible("css=#reloadingText");
					break;
				}catch(Exception ae){
					Thread.sleep(1000);
					intCnt++;
				}
			}
			intCnt=0;
			while(selenium.isVisible("css=#reloadingText")&&intCnt<30){
				Thread.sleep(1000);
				intCnt++;
			}
			if(intCnt>30){
				log4j.info("Page is NOT refreshed with in the time");
				strReason="Page is NOT refreshed with in the time";
			}else{
				log4j.info("Page is refreshed with in the time");
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
	public String loginAndnavToInboxInWebMail(Selenium seleniumMail,String strLoginName, String strPassword){
		String strReason="";
		try{
			seleniumMail.openWindow("http://mail.qsgsoft.com/","Mail");
			
			boolean blnFound=false;
			int intCnt=0;
			while(blnFound==false&&intCnt<60){
				try{
					seleniumMail.selectWindow("Mail");
					
					for (int second = 0;; second++){
						 if (second >= 60) fail("timeout");
							try { 
								seleniumMail.type("css=input[name=imapuser]", strLoginName); 
								break; 
							}catch(Exception e) {}
								Thread.sleep(1000);
						}
					blnFound=true;
				}catch(Exception e){
					intCnt++;
					Thread.sleep(1000);
					if(intCnt%10==0){
						seleniumMail.typeKeys("//", "116");
						Thread.sleep(2000);
		
					}
				}
			}
			
			if(blnFound){
				
				intCnt=0;
				do{
					try{
						assertTrue(seleniumMail.isElementPresent("css=input[name=loginButton]"));
						break;
						
					}catch(AssertionError Ae){
						seleniumMail.typeKeys("//", "116");
						Thread.sleep(2000);
						intCnt++;
					}catch(Exception e){
						intCnt++;
					}
				}while(intCnt<70);
				
				
				seleniumMail.type("css=input[name=pass]", strPassword);
				seleniumMail.focus("css=input[name=loginButton]");
				seleniumMail.click("css=input[name=loginButton]");
				seleniumMail.waitForPageToLoad("150000");
				
				
				if(seleniumMail.isElementPresent("css=input[value='Skip Maintenance']")){
					seleniumMail.click("css=input[value='Skip Maintenance']");
					seleniumMail.waitForPageToLoad("150000");
				}
					
				
				intCnt=0;
				do{
					try{
						assertTrue(seleniumMail.isElementPresent("link=Mail"));
						break;
						
					}catch(AssertionError Ae){
						seleniumMail.typeKeys("//", "116");
						Thread.sleep(2000);
						intCnt++;
					}catch(Exception e){
						intCnt++;
					}
				}while(intCnt<70);
				
				seleniumMail.selectFrame("horde_main");
				seleniumMail.click("link=Mail");//Click on mail link			 
				seleniumMail.waitForPageToLoad("150000");
				
				seleniumMail.selectFrame("relative=up");
				seleniumMail.selectFrame("horde_main");
				
				seleniumMail.click("link=Inbox");
				seleniumMail.waitForPageToLoad("150000");
			}else{
				log4j.info("User failed to login to web mail as "+strLoginName);
				strReason="User failed to login to web mail as "+strLoginName;
			}	
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}

	public String verifyEmail(Selenium seleniumMail, String strFrom,
			String strTo, String strSubjName) {
		String strReason = "";
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
							+ strSubjName + "']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<20);
			
			while ((seleniumMail.isElementPresent("//a/b[text()='"
					+ strSubjName + "']") == false)
					&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
				seleniumMail.click("//a[@id='next']/img");
				seleniumMail.waitForPageToLoad("150000");
			}
			
			try {

				if(seleniumMail.isElementPresent("//a/b[text()='"
						+ strSubjName + "']"))
				{
					log4j.info("The mail with subject " + strSubjName
						+ " is present in the inbox");

				seleniumMail.click("//a/b[text()='" + strSubjName + "']");
				seleniumMail.waitForPageToLoad("150000");

				
				intCnt=0;
				do{
					try {

						assertTrue(seleniumMail.isElementPresent("//tr[2]/td[2]/span"));
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
					log4j.info(seleniumMail.getText("//tr[2]/td[2]/span"));
					assertEquals(strFrom, seleniumMail
							.getText("//tr[2]/td[2]/span"));
					log4j
							.info("'From' field value is displayed correctly with value "
									+ strFrom);
				} catch (AssertionError ae) {
					log4j.info("'From' field value is NOT displayed correctly");
					strReason = "'From' field value is NOT displayed correctly";
				}

				try {
					log4j.info(seleniumMail.getText("//tr[3]/td[2]/span"));
					assertEquals(strTo, seleniumMail
							.getText("//tr[3]/td[2]/span"));
					log4j
							.info("'To' field value is displayed correctly with the value "
									+ strTo);
				} catch (AssertionError ae) {
					log4j.info("'To' field value is NOT displayed correctly");
					strReason = strReason
							+ " 'To' field value is NOT displayed correctly";
				}

				try {
					assertEquals(strSubjName, seleniumMail
							.getText("//tr[4]/td[2]"));
					log4j.info("'Subject' field value is displayed correctly ");
				} catch (AssertionError ae) {
					log4j
							.info("'Subject' field value is NOT displayed correctly");
					strReason = strReason
							+ " 'Subject' field value is NOT displayed correctly";
				}
				}
				else
				{
					assertTrue(seleniumMail.isElementPresent("//a/img[@alt='<<']"));
					seleniumMail.click("//a/img[@alt='<<']");
					seleniumMail.waitForPageToLoad("150000");
					
					while ((seleniumMail.isElementPresent("//a/b[text()='"
							+ strSubjName + "']") == false)
							&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
						seleniumMail.click("//a[@id='next']/img");
						seleniumMail.waitForPageToLoad("150000");
					}
					
					assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
							+ strSubjName + "']"));
					log4j.info("The mail with subject " + strSubjName
							+ " is present in the inbox");

					seleniumMail.click("//a/b[text()='" + strSubjName + "']");
					seleniumMail.waitForPageToLoad("150000");

					
					intCnt=0;
					do{
						try {

							assertTrue(seleniumMail.isElementPresent("//tr[2]/td[2]/span"));
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
						log4j.info(seleniumMail.getText("//tr[2]/td[2]/span"));
						assertEquals(strFrom, seleniumMail
								.getText("//tr[2]/td[2]/span"));
						log4j
								.info("'From' field value is displayed correctly with value "
										+ strFrom);
					} catch (AssertionError ae) {
						log4j.info("'From' field value is NOT displayed correctly");
						strReason = "'From' field value is NOT displayed correctly";
					}

					try {
						log4j.info(seleniumMail.getText("//tr[3]/td[2]/span"));
						assertEquals(strTo, seleniumMail
								.getText("//tr[3]/td[2]/span"));
						log4j
								.info("'To' field value is displayed correctly with the value "
										+ strTo);
					} catch (AssertionError ae) {
						log4j.info("'To' field value is NOT displayed correctly");
						strReason = strReason
								+ " 'To' field value is NOT displayed correctly";
					}

					try {
						assertEquals(strSubjName, seleniumMail
								.getText("//tr[4]/td[2]"));
						log4j.info("'Subject' field value is displayed correctly ");
					} catch (AssertionError ae) {
						log4j
								.info("'Subject' field value is NOT displayed correctly");
						strReason = strReason
								+ " 'Subject' field value is NOT displayed correctly";
					}
				}

			} catch (AssertionError ae) {
				log4j.info("The mail with subject " + strSubjName
						+ " is NOT present in the inbox");
				strReason = "The mail with subject " + strSubjName
						+ " is NOT present in the inbox";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
	
	public String verifyReadEmail(Selenium seleniumMail, String strFrom,
			String strTo, String strSubjName) {
		String strReason = "";
		try {
			while ((seleniumMail.isElementPresent("//a[text()='"
					+ strSubjName + "']") == false)
					&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
				seleniumMail.click("//a[@id='next']/img");
				seleniumMail.waitForPageToLoad("150000");
			}

			try {

				assertTrue(seleniumMail.isElementPresent("//a[text()='"
						+ strSubjName + "']"));
				log4j.info("The mail with subject " + strSubjName
						+ " is present in the inbox");

				seleniumMail.click("//a[text()='" + strSubjName + "']");
				seleniumMail.waitForPageToLoad("150000");

				try {
					log4j.info(seleniumMail.getText("//tr[2]/td[2]/span"));
					assertEquals(strFrom, seleniumMail
							.getText("//tr[2]/td[2]/span"));
					log4j
							.info("'From' field value is displayed correctly with value "
									+ strFrom);
				} catch (AssertionError ae) {
					log4j.info("'From' field value is NOT displayed correctly");
					strReason = "'From' field value is NOT displayed correctly";
				}

				try {
					log4j.info(seleniumMail.getText("//tr[3]/td[2]/span"));
					assertEquals(strTo, seleniumMail
							.getText("//tr[3]/td[2]/span"));
					log4j
							.info("'To' field value is displayed correctly with the value "
									+ strTo);
				} catch (AssertionError ae) {
					log4j.info("'To' field value is NOT displayed correctly");
					strReason = strReason
							+ " 'To' field value is NOT displayed correctly";
				}

				try {
					assertEquals(strSubjName, seleniumMail
							.getText("//tr[4]/td[2]"));
					log4j.info("'Subject' field value is displayed correctly ");
				} catch (AssertionError ae) {
					log4j
							.info("'Subject' field value is NOT displayed correctly");
					strReason = strReason
							+ " 'Subject' field value is NOT displayed correctly";
				}

			} catch (AssertionError ae) {
				log4j.info("The mail with subject " + strSubjName
						+ " is NOT present in the inbox");
				strReason = "The mail with subject " + strSubjName
						+ " is NOT present in the inbox";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}

	public String CheckWebMail(Selenium seleniumMail, String strLoginName, String strPassword,String strFrom,String strTo,String strSubjName,String strMsgBody ) throws Exception{
		
		String  strResult="";
		seleniumMail.openWindow("http://mail.qsgsoft.com/","Mail");
	
		boolean blnFound=false;
		int intCnt=0;
		while(blnFound==false&&intCnt<60){
			try{
				seleniumMail.selectWindow("Mail");
				
				for (int second = 0;; second++){
					 if (second >= 120) fail("timeout");
						try { 
							seleniumMail.type("css=input[name=imapuser]", strLoginName); 
							break; 
						}catch(Exception e) {}
							Thread.sleep(1000);
					}
				blnFound=true;
			}catch(Exception e){
				intCnt++;
				Thread.sleep(1000);
			}
		}
		
		if(blnFound){
			seleniumMail.type("css=input[name=pass]", strPassword);
			seleniumMail.focus("css=input[name=loginButton]");
			seleniumMail.click("css=input[name=loginButton]");
			seleniumMail.waitForPageToLoad("90000");
					 
			seleniumMail.selectFrame("horde_main");
			seleniumMail.click("link=Mail");//Click on mail link			 
			seleniumMail.waitForPageToLoad("90000");
			
			seleniumMail.click("link=Inbox");
			seleniumMail.waitForPageToLoad("90000");
			
			
			seleniumMail.selectFrame("relative=up");
			seleniumMail.selectFrame("horde_main");
				
			try{
				assertTrue(seleniumMail.isElementPresent("//a/b[text()='"+strSubjName+"']"));
				log4j.info("The mail with subject "+strSubjName+" is present in the inbox");
					
				seleniumMail.click("css=a:contains('"+strSubjName+"')");
				seleniumMail.waitForPageToLoad("90000");
					
				try{
					assertEquals(strFrom, seleniumMail.getText("//tr[2]/td[2]/span"));
					log4j.info("'From' field value is displayed correctly with value "+strFrom);
				}catch(AssertionError ae){
					log4j.info("'From' field value is NOT displayed correctly");
					strResult="'From' field value is NOT displayed correctly";
				}
					
				try{
					assertEquals(strTo, seleniumMail.getText("//tr[3]/td[2]/span"));
					log4j.info("'To' field value is displayed correctly with the value "+strTo);
				}catch(AssertionError ae){
					log4j.info("'To' field value is NOT displayed correctly");
					strResult=strResult+" 'To' field value is NOT displayed correctly";
				}
				
				try{
					assertEquals(strSubjName, seleniumMail.getText("//tr[4]/td[2]"));
					log4j.info("'Subject' field value is displayed correctly ");
				}catch(AssertionError ae){
					log4j.info("'Subject' field value is NOT displayed correctly");
					strResult=strResult+" 'Subject' field value is NOT displayed correctly";
				}
					
				try{
					assertEquals(strMsgBody, seleniumMail.getText("css=div.fixed.leftAlign"));
					log4j.info("Mail body contains the message as specified");
				}catch(AssertionError ae){
					 log4j.info("Mail body does NOT contains the message as specified");
					 strResult=strResult+" Mail body does NOT contains the message as specified";
				}
				 
				
					
			}catch(AssertionError ae){
				log4j.info("The mail with subject "+strSubjName+" is NOT present in the inbox");
				strResult="The mail with subject "+strSubjName+" is NOT present in the inbox";
			}
				
		}else{
			log4j.info("User failed to login to web mail as "+strLoginName);
			strResult="User failed to login to web mail as "+strLoginName;
		}	
		
				
		return strResult;
	}
	
	/**********************************************************
	'Description	:Auto It 
	'Precondition	:None
	'Arguments		:strPopUp,strPopUPName
	'Returns		:String
	'Date	 		:5-April-2012
	'Author			:QSG
	'-------------------------------------------------------
	'Modified Date                            Modified By
	'5-April-2012                                     <Name>
	************************************************************/
	
	public String autoItHandling(String strFilePath,String strFileName,String strSavePath) throws Exception{
		
		String strErrorMsg="";//variable to store error mesage 
		
		try{
			String strProcess="";
			String strArgs[]={strFilePath,strSavePath};
			//AutoIt
			Runtime.getRuntime().exec(strArgs);
			int intCnt=0; 
			do{
			   GetProcessList objGPL = new GetProcessList();
			   strProcess = objGPL.GetProcessName();
			   intCnt++;
			   Thread.sleep(500);
			 } while (strProcess.contains(strFileName)&&intCnt<120);
			
		}catch(Exception Ae){
			log4j.info(Ae);
			strErrorMsg="autoItHandling FAILED";
		}
		return strErrorMsg;
	}
	
	public void deleteWebMailInbox(Selenium selenium){
		selenium.selectFrame("relative=up");
		selenium.selectFrame("horde_main");
		
		selenium.click("link=Inbox");
		selenium.waitForPageToLoad("90000");
		
		//select checkAll and click on delete
		selenium.click("name=checkAll");
		selenium.click("link=Delete");
		selenium.waitForPageToLoad("90000");
	}
	
	public String verifyEmailWithUserName(Selenium seleniumMail,
			String strFrom, String strTo, String strSubjName,
			String strUserFullName, String strMsgBody) {
		String strReason = "";
		try {
			while ((seleniumMail.isElementPresent("//a/b[text()='"
					+ strSubjName + "']") == false)
					&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
				seleniumMail.click("//a[@id='next']/img");
				seleniumMail.waitForPageToLoad("90000");
			}

			try {

				assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
						+ strSubjName + "']"));
				log4j.info("The mail with subject " + strSubjName
						+ " is present in the inbox");

				seleniumMail.click("//a/b[text()='" + strSubjName + "']");
				seleniumMail.waitForPageToLoad("90000");

				try {
					assertTrue(seleniumMail.isTextPresent("For "
							+ strUserFullName + ""));
					log4j.info("Notifications are received by "
							+ strUserFullName);

					try {
						log4j.info(seleniumMail.getText("//tr[2]/td[2]/span"));
						assertEquals(strFrom, seleniumMail
								.getText("//tr[2]/td[2]/span"));
						log4j
								.info("'From' field value is displayed correctly with value "
										+ strFrom);

						try {
							log4j.info(seleniumMail
									.getText("//tr[3]/td[2]/span"));
							assertEquals(strTo, seleniumMail
									.getText("//tr[3]/td[2]/span"));
							log4j
									.info("'To' field value is displayed correctly with the value "
											+ strTo);

							try {
								assertEquals(strSubjName, seleniumMail
										.getText("//tr[4]/td[2]"));
								log4j
										.info("'Subject' field value is displayed correctly ");

								//Verify message body
								String strMsg = seleniumMail
										.getText("css=div.fixed.leftAlign");
								if (strMsg.equals(strMsgBody)) {

									log4j
											.info(strMsgBody
													+ " is displayed for Email Notification");
								} else {
									log4j
											.info(strMsgBody
													+ " is NOT displayed for Email Notification");
									strReason = strMsgBody
											+ " is NOT displayed for Email Notification";
								}
							} catch (AssertionError ae) {
								log4j
										.info("'Subject' field value is NOT displayed correctly");
								strReason = strReason
										+ " 'Subject' field value is NOT displayed correctly";
							}

						} catch (AssertionError ae) {
							log4j
									.info("'To' field value is NOT displayed correctly");
							strReason = strReason
									+ " 'To' field value is NOT displayed correctly";
						}

					} catch (AssertionError ae) {
						log4j
								.info("'From' field value is NOT displayed correctly");
						strReason = "'From' field value is NOT displayed correctly";
					}

				} catch (AssertionError Ae) {
					log4j.info("Email notifications are NOT received by "
							+ strUserFullName);
					strReason = "Email notifications are NOT received by "
							+ strUserFullName;
				}

			} catch (AssertionError ae) {
				log4j.info("The mail with subject " + strSubjName
						+ " is NOT present in the inbox");
				strReason = "The mail with subject " + strSubjName
						+ " is NOT present in the inbox";
			}

			
			if(seleniumMail.isElementPresent("link=Back to Inbox")){
				seleniumMail.selectFrame("relative=up");
				seleniumMail.selectFrame("horde_main");
				// click on Back to Inbox
				seleniumMail.click("link=Back to Inbox");
				seleniumMail.waitForPageToLoad("90000");
			}
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
	
	public String verifyEmailWithPagerNotification(Selenium seleniumMail,
			String strFrom, String strTo, String strSubjName, String strMsgBody) {
		String strReason = "";
		try {
			while ((seleniumMail.isElementPresent("//a/b[text()='"
					+ strSubjName + "']") == false)
					&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
				seleniumMail.click("//a[@id='next']/img");
				seleniumMail.waitForPageToLoad("90000");
			}

			try {

				assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
						+ strSubjName + "']"));
				log4j.info("The mail with subject " + strSubjName
						+ " is present in the inbox");

				seleniumMail.click("//a/b[text()='" + strSubjName + "']");
				seleniumMail.waitForPageToLoad("90000");

				try {
					log4j.info(seleniumMail.getText("//tr[2]/td[2]/span"));
					assertEquals(strFrom, seleniumMail
							.getText("//tr[2]/td[2]/span"));
					log4j
							.info("'From' field value is displayed correctly with value "
									+ strFrom);

					try {
						log4j.info(seleniumMail.getText("//tr[3]/td[2]/span"));
						assertEquals(strTo, seleniumMail
								.getText("//tr[3]/td[2]/span"));
						log4j
								.info("'To' field value is displayed correctly with the value "
										+ strTo);

						try {
							assertEquals(strSubjName, seleniumMail
									.getText("//tr[4]/td[2]"));
							log4j
									.info("'Subject' field value is displayed correctly ");

							// Verify message body
							String strMsg = seleniumMail
									.getText("css=div.fixed.leftAlign");
							if (strMsg.equals(strMsgBody)) {

								log4j
										.info(strMsgBody
												+ " is displayed for Email Notification");
							} else {
								log4j
										.info(strMsgBody
												+ " is NOT displayed for Email Notification");
								strReason = strMsgBody
										+ " is NOT displayed for Email Notification";
							}
						} catch (AssertionError ae) {
							log4j
									.info("'Subject' field value is NOT displayed correctly");
							strReason = strReason
									+ " 'Subject' field value is NOT displayed correctly";
						}

					} catch (AssertionError ae) {
						log4j
								.info("'To' field value is NOT displayed correctly");
						strReason = strReason
								+ " 'To' field value is NOT displayed correctly";
					}

				} catch (AssertionError ae) {
					log4j.info("'From' field value is NOT displayed correctly");
					strReason = "'From' field value is NOT displayed correctly";
				}

			} catch (AssertionError ae) {
				log4j.info("The mail with subject " + strSubjName
						+ " is NOT present in the inbox");
				strReason = "The mail with subject " + strSubjName
						+ " is NOT present in the inbox";
			}

			if (seleniumMail.isElementPresent("link=Back to Inbox")) {
				seleniumMail.selectFrame("relative=up");
				seleniumMail.selectFrame("horde_main");
				// click on Back to Inbox
				seleniumMail.click("link=Back to Inbox");
				seleniumMail.waitForPageToLoad("90000");
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
	
	public String[] getSnapTime(Selenium selenium){
		String strReason[]=new String[5];
		strReason[4]="";
		try{
			String strArSnapDtTime[]=selenium.getText("css=#statusTime").split(" ");
			String[] strArSnapTime=strArSnapDtTime[2].split(":");
			strReason[0]=strArSnapDtTime[0];
			strReason[1]=strArSnapDtTime[1];
			strReason[2]=strArSnapTime[0];
			strReason[3]=strArSnapTime[1];
			
		} catch (Exception e) {
			log4j.info(e);
			strReason[4] = "Exception Occured: " + e.toString();
		}
		return strReason;
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

	public String overallcancel(Selenium selenium) throws Exception
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
			selenium.click("//input[@value='Cancel']");
			selenium.waitForPageToLoad(gstrTimeOut);
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	public String verifyEmailSubName(Selenium seleniumMail, String strSubjName) {
		String strReason = "";
		try {
			
			while ((seleniumMail.isElementPresent("//a/b[text()='"
					+ strSubjName + "']") == false)
					&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
				seleniumMail.click("//a[@id='next']/img");
				seleniumMail.waitForPageToLoad("90000");
			}

			try {

				assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
						+ strSubjName + "']"));
				log4j.info("The mail with subject " + strSubjName
						+ " is present in the inbox");

			} catch (AssertionError ae) {
				log4j.info("The mail with subject " + strSubjName
						+ " is NOT present in the inbox");
				strReason = "The mail with subject " + strSubjName
						+ " is NOT present in the inbox";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
	
	
	public String verifyEmailSubNameAndRead(Selenium seleniumMail, String strSubjName) {
		String strReason = "";
		try {
			while ((seleniumMail.isElementPresent("//a/b[text()='"
					+ strSubjName + "']") == false)
					&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
				seleniumMail.click("//a[@id='next']/img");
				seleniumMail.waitForPageToLoad("90000");
			}

			try {

				assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
						+ strSubjName + "']"));
				log4j.info("The mail with subject " + strSubjName
						+ " is present in the inbox");

				seleniumMail.click("//a/b[text()='"
						+ strSubjName + "']");
				seleniumMail.waitForPageToLoad("150000");
				
				seleniumMail.selectFrame("relative=up");
				seleniumMail.selectFrame("horde_main");
				// click on Back to Inbox
				seleniumMail.click("link=Back to Inbox");
				seleniumMail.waitForPageToLoad("90000");
				
				
			} catch (AssertionError ae) {
				log4j.info("The mail with subject " + strSubjName
						+ " is NOT present in the inbox");
				strReason = "The mail with subject " + strSubjName
						+ " is NOT present in the inbox";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
	
	
	/***************************************************************
	'Description	:Check status type in Different Screen
	'Precondition	:None
	'Arguments		:selenium,strStatType
	'Returns		:strReason
	'Date	 		:26-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String checkStatTypeInDiffPage(Selenium selenium,String strStatType,String strLabelName,String strPageName)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
		
			  try{
				    assertTrue(selenium.getText("//td[@class='emsLabel'][contains(text(),'"+strLabelName+"')]/following-sibling::td").contains(strStatType));
				    log4j.info("Status Type "+strStatType+"is displayed in "+strPageName+" page under "+strLabelName+" section");
				        
			  }catch(AssertionError ae){
				  log4j.info("Status Type "+strStatType+"is NOT displayed in "+strPageName+" page under "+strLabelName+" section");
				    strReason="Status Type "+strStatType+"is NOT displayed in "+strPageName+" page under "+strLabelName+" section";
			}  
				  
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkStatTypeInDiffPage "
					+ e.toString();
		}
		return strReason;
	}
	
	
 /*********************************************************
  * 'Description :assertEuals
		  'Precondition :None
		  'Arguments  :selenium,strElementID,strText
		  'Returns  :String
		  'Date    :31-Oct-2012
		  'Author   :QSG
		  '---------------------------------------------------------
		  'Modified Date                            Modified By
		  '31-Oct-2012                               <Name>
		  **********************************************************/
		  
	public String assertEQUALS(Selenium selenium, String strElementID,
			String strText) throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			assertEquals(selenium.getText(strElementID), strText);

		} catch (AssertionError Ae) {
			strReason = "Element NOT Present";
		}

		return strReason;
	}
	
/*********************************************************
  'Description :enodeToUnicode
  'Precondition :None
  'Arguments   :selenium,strStringNeedToEncode,strRegularExpression
  'Returns     :String
  'Date        :27-Dec-2012
  'Author      :QSG
  '---------------------------------------------------------
  'Modified Date                            Modified By
  '27-Dec-2012                               <Name>
 **********************************************************/

	public String[] enodeToUnicode(Selenium selenium,
			String strStringNeedToEncode, int intcodePoint)
			throws Exception {

		String strReason[] = new String[2];

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {

			int intLength = strStringNeedToEncode.length();
			int intCnt=0;
			
			for (int i = 0; i < intLength; i++) {

				int codepoint = strStringNeedToEncode.codePointAt(i);
				if (codepoint == intcodePoint) {
					intCnt++;
					char ch[] = Character.toChars(32);
					strReason[1] = strStringNeedToEncode.replace(
							strStringNeedToEncode.charAt(i), ch[0]);
				}

			}
			
			if(intCnt==0){
				strReason[1]=strStringNeedToEncode;
			}

		} catch (Exception e) {
			strReason[0] = e.toString();

		}

		return strReason;
	}
	
	
	
	/*********************************************************
	  'Description :enodeToUnicode
	  'Precondition :None
	  'Arguments   :selenium,strStringNeedToEncode,strRegularExpression
	  'Returns     :String
	  'Date        :27-Dec-2012
	  'Author      :QSG
	  '---------------------------------------------------------
	  'Modified Date                            Modified By
	  '27-Dec-2012                               <Name>
	 **********************************************************/

	public String seleniumGetText(Selenium selenium, String strElementID,
			int intcodePoint) throws Exception {

		String strString = selenium.getText(strElementID);
	
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
	
			int intLength = strString.length();

			for (int i = 0; i < intLength; i++) {

				int codepoint = strString.codePointAt(i);
				if (codepoint == intcodePoint) {
					char ch[] = Character.toChars(32);
					strString = strString.replace(strString.charAt(i), ch[0]);
				}
			}

		} catch (Exception e) {
			strString = e.toString();

		}
		return strString;
	}

	public String assertEQUALSValue(Selenium selenium, String strElementID,
			String strText) throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		try{
			try {
				assertEquals(selenium.getValue(strElementID), strText);

			} catch (AssertionError Ae) {
				strReason = "Element NOT Present";

			}
		}catch(Exception e){
			strReason = e.toString();
		}
		return strReason;
	}

	public String assertEQUALSgetSelectedLabel(Selenium selenium,
			String strElementID, String strText) throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				assertEquals(selenium.getSelectedLabel(strElementID), strText);

			} catch (AssertionError Ae) {
				strReason = "Element NOT Present";

			}
		} catch (Exception e) {
			strReason = e.toString();
		}

		return strReason;
	}

	public String assertEQUALSAttribute(Selenium selenium, String strElementID,
			String strText) throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		try {
			try {
				assertEquals(selenium.getAttribute(strElementID), strText);

			} catch (AssertionError Ae) {
				strReason = "Element NOT Present";

			}
		} catch (Exception e) {
			strReason = e.toString();
		}

		return strReason;
	}

	public String readAndVerifySpecificDataInExcel(String strTestData[][],
			String FILE_PATH, String strUniqueValue, boolean blnContinuousRow,
			boolean blnParticularRow, int intRowCntInput,
			String strTestDataSpecific[]) throws Exception {
		String strReason = "";
		Sheet ws = null;
		try {

			// Read the existing file
			Workbook wb = Workbook.getWorkbook(new File(FILE_PATH));
			ws = wb.getSheet(0);

			int intRowCount = wb.getSheet(0).getRows();
			int intColCount = wb.getSheet(0).getColumns();

			int intRowCountSpecific = 0;

			if (blnContinuousRow) {
				for (int intRow = 0; intRow < intRowCntInput; intRow++) {
					for (int intCol = 0; intCol < intColCount; intCol++) {
						// Read content of the cell

						String strCellContent = ws.getCell(intCol, intRow)
								.getContents();
						if (strCellContent.equals(strTestData[intRow][intCol])) {
							log4j.info("Specified Data "
									+ strTestData[intRow][intCol]
									+ " is displayed in the report");
						} else {
							log4j.info("Specified Data "
									+ strTestData[intRow][intCol]
									+ " is NOT displayed in the report");
							strReason = "Specified Data "
									+ strTestData[intRow][intCol]
									+ " is NOT displayed in the report";
						}
					}

				}

			}

			if (blnParticularRow) {
				for (int intRow = 0; intRow < intRowCount; intRow++) {
					for (int intCol = 0; intCol < intColCount; intCol++) {
						// Read content of the cell

						String strCellContent = ws.getCell(intCol, intRow)
								.getContents();

						if (strCellContent.compareTo(strUniqueValue) == 0) {
							intRowCountSpecific = intRow;
							break;

						}

					}
				}

			}

			if (blnParticularRow) {
				for (int intCol = 0; intCol < intColCount; intCol++) {
					// Read content of the cell

					String strCellContent = ws.getCell(intCol,
							intRowCountSpecific).getContents();

					if (strCellContent.equals(strTestDataSpecific[intCol])) {
						log4j.info("Specified Data "
								+ strTestDataSpecific[intCol]
								+ " is displayed in the report");
					} else {
						log4j.info("Specified Data "
								+ strTestDataSpecific[intCol]
								+ " is NOT displayed in the report");
						strReason = "Specified Data "
								+ strTestDataSpecific[intCol]
								+ " is NOT displayed in the report";
					}
				}
			}
			wb.close();
		} catch (Exception e) {
			strReason = e.toString();

		}

		return strReason;
	}
	
	public String backToMailInbox(Selenium selenium, boolean blnBakToInbox,
			boolean blnClose, boolean blnLogOut) throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			if (blnBakToInbox) {
				if (selenium.isElementPresent("link=Back to Inbox")) {
					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad(gstrTimeOut);
				}

			}

			if (blnClose) {

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad(gstrTimeOut);
				selenium.close();
				selenium.selectWindow("");
				Thread.sleep(2000);

			}

			if (blnLogOut) {
				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");

				if (selenium.isElementPresent("link=Log out")) {
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
				} else {

				}

			}

		} catch (Exception e) {
			strReason = e.toString();
		}

		return strReason;
	}
	
	
	public String waitForMailNotification(Selenium selenium, int intTimeTowait,
			String strElementId) throws Exception {

		String strReason = "";

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objGeneral = new General();

		int intCnt = 0;
		try {
			do {
				try {
					assertTrue(selenium.isElementPresent(strElementId));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
					strReason = Ae.toString();
					
					try {
						objGeneral.refreshPageNew(selenium);
					} catch (Exception e) {

					}
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
					strReason = e.toString();
					try {
						objGeneral.refreshPageNew(selenium);
					} catch (Exception Ae) {

					}

				}
			} while (intCnt <= intTimeTowait);

			if (selenium.isElementPresent(strElementId)) {
				strReason = "";
			}

		} catch (Exception e) {
			strReason = e.toString();
		}
		return strReason;
	}

	public String pdftoText(String strPDFfileName, String strTextFileName) throws Exception {
		
		String strReason="";
		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objGeneral=new General();
		
		PDFParser parser;
		String parsedText;
		PDFTextStripper pdfStripper;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		
		System.out.println("Parsing text from PDF file " + strPDFfileName + "....");
		File f = new File(strPDFfileName);

		if (!f.isFile()) {
			System.out.println("File " + strPDFfileName + " does not exist.");
			return "File " + strPDFfileName + " does not exist.";
		}

		try {
			parser = new PDFParser(new FileInputStream(f));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return "Unable to open PDF Parser.";
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.out
					.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			return "An exception occured in parsing the PDF Document.";
		}
		System.out.println("Done.");
		
		strReason=objGeneral.writeTexttoFile(parsedText, strTextFileName);
		return strReason;

	}

	
	public String  writeTexttoFile(String pdfText, String fileName) {

		String strReason="";
		System.out.println("\nWriting PDF text to output text file " + fileName
				+ "....");
		try {
			PrintWriter pw = new PrintWriter(fileName);
			pw.print(pdfText);
			pw.close();
		} catch (Exception e) {
			System.out
					.println("An exception occured in writing the pdf text to file.");
			e.printStackTrace();
			strReason="An exception occured in writing the pdf text to file.";
		}
		System.out.println("Done.");
		return strReason;
	}
	
	public String parsepdfText(String fileName) {
		
		PDFParser parser;
		String parsedText;
		PDFTextStripper pdfStripper;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		@SuppressWarnings("unused")
		PDDocumentInformation pdDocInfo;

		System.out.println("Parsing text from PDF file " + fileName + "....");
		File f = new File(fileName);

		if (!f.isFile()) {
			System.out.println("File " + fileName + " does not exist.");
			return null;
		}

		try {
			parser = new PDFParser(new FileInputStream(f));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return null;
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc).toString();
			pdDoc.close();
		} catch (Exception e) {
			System.out
					.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			return null;
		}
		System.out.println("Done.");
		return parsedText;
	}
	
	
	  /*********************************************************
	  'Description :Selenium isVisible
	  'Precondition :None
	  'Arguments  :selenium,strElementID
	  'Returns  :String
	  'Date    :9-April-2013
	  'Author   :QSG
	  '---------------------------------------------------------
	  'Modified Date                            Modified By
	  '9-April-2013                               <Name>
	  **********************************************************/

	public boolean seleniumIsVisible(Selenium selenium, String strElementID)
			throws Exception {

		boolean blnElementPresent = false;

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		if (selenium.isElementPresent(strElementID)) {
			int intCnt = 0;
			do {
				try {
					assertTrue(selenium.isVisible(strElementID));
					blnElementPresent = true;
					break;

				} catch (AssertionError Ae) {
					intCnt++;
				} catch (Exception e) {
					intCnt++;
				}

			} while (intCnt < 50);
		}else{
			log4j.info(strElementID+" NOT Present");
		}

		return blnElementPresent;
	}
	
	public String refreshPageNew(Selenium selenium) throws Exception{
		String strReason="";
		try{
			
			int intCnt=0;
			selenium.click("link=refresh");
			
			while(intCnt<30){
				try{				
					selenium.isVisible("css=#reloadingText");
					Thread.sleep(10000);
					break;
				}catch(Exception ae){
					Thread.sleep(1000);
					intCnt++;
				}
			}
			
			
			if(intCnt>30){
				log4j.info("Page is NOT refreshed with in the time");
				strReason="Page is NOT refreshed with in the time";
			}else{
				log4j.info("Page is refreshed with in the time");
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
	//start//navToHelpCenterByHelpLink//
	/*******************************************************************************************
	' Description: navigating Help center page
	' Precondition: N/A 
	' Arguments: selenium, blnClose
	' Returns: String 
	' Date: 16/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToHelpCenterByHelpLink(Selenium selenium, boolean blnClose)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			  selenium.selectWindow("");
			  selenium.selectFrame("Data");
			   
			//Select the link 'Help'
			selenium.click("link=Help Center");
			selenium.waitForPopUp("", gstrTimeOut);
			String[] pStrWindNames=selenium.getAllWindowNames();
			int intCnt=0;
			boolean blnFound=false;
			while(blnFound==false && intCnt<60){
				try{
					selenium.selectWindow(pStrWindNames[1]);
					blnFound=true;
					
				}catch(Exception E){
					Thread.sleep(1000);
					intCnt++;
				}
			}
			
			blnFound=false;
			intCnt=0;
			while(blnFound==false && intCnt<60){
				try{
					assertEquals("Welcome to Help Center",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					blnFound=true;
					
				}catch(AssertionError ae){
					Thread.sleep(1000);
					intCnt++;
					if(intCnt%10==0){
					      selenium.typeKeys("//", "116");
					      Thread.sleep(2000);
					  
					     }
				}
			}

			try {
				assertEquals("Welcome to Help Center",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Welcome to Help Center Screen is displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Welcome to Help Center Screen is NOT displayed"
						+ Ae;
				log4j.info("Welcome to Help Center Screen is NOT displayed"
						+ Ae);
			}
			if (blnClose) {
				// Close Help center window
				selenium.close();
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}

		} catch (Exception e) {
			log4j.info("navToHelpCenterByHelpLink function failed" + e);
			strErrorMsg = "navToHelpCenterByHelpLink function failed" + e;
		}
		return strErrorMsg;
	}
	//end//navToHelpCenterByHelpLink//
	//start//ChkHelpCenterOptions//
	/*******************************************************************************************
	' Description: Checking Help center options
	' Precondition: N/A 
	' Arguments: selenium, blnClose
	' Returns: String 
	' Date: 16/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String ChkHelpCenterOptions(Selenium selenium, boolean blnClose)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectWindow("name=help");

			try {
				assertTrue(selenium
						.isElementPresent("//a[@id='online']/div/h3[text()='Online Help']"));
				log4j.info("1. Online Help option is displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "1. Online Help option is NOT displayed." + Ae;
				log4j.info("1. Online Help option is NOT displayed." + Ae);
			}
			try {
				assertTrue(selenium
						.isElementPresent("//a[@id='quick_reference_guides']/div/h3"
								+ "[text()='Quick Reference Guides']"));
				log4j.info("2. Quick Reference Guides option is displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "2. Quick Reference Guides option is NOT displayed."
						+ Ae;
				log4j.info("2. Quick Reference Guides option is NOT displayed."
						+ Ae);
			}
			try {
				assertTrue(selenium
						.isElementPresent("//a[@id='whats_new']/div/h3"));
				log4j.info("3. What's new  option is displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "3. What's new option is NOT displayed." + Ae;
				log4j.info("3. What's new option is NOT displayed." + Ae);
			}

			if (blnClose) {
				// Close Help center window
				selenium.close();
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}

		} catch (Exception e) {
			log4j.info("ChkHelpCenterOptions function failed" + e);
			strErrorMsg = "ChkHelpCenterOptions function failed" + e;
		}
		return strErrorMsg;
	}
	//end//ChkHelpCenterOptions//
	
	//start//navToQuickRefGuidePage//
	/*******************************************************************************************
	' Description: navigating To Quick Reference Guide page
	' Precondition: N/A 
	' Arguments: selenium, blnClose
	' Returns: String 
	' Date: 16/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToQuickRefGuidePage(Selenium selenium, boolean blnClose)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectWindow("name=help");

			selenium.click("//a[@id='quick_reference_guides']/div/h3"
					+ "[text()='Quick Reference Guides']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Quick Reference Guides",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Quick Reference Guides Screen is displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Quick Reference Guides Screen is NOT displayed"
						+ Ae;
				log4j.info("Quick Reference Guides Screen is NOT displayed"
						+ Ae);
			}

			if (blnClose) {
				// Close Help center window
				selenium.close();
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}

		} catch (Exception e) {
			log4j.info("navToQuickRefGuidePage function failed" + e);
			strErrorMsg = "navToQuickRefGuidePage function failed" + e;
		}
		return strErrorMsg;
	}
	//end//navToQuickRefGuidePage//
	
	//start//ChkQuickRefGuideOptions//
	/*******************************************************************************************
	' Description: ChkQuickRefGuideOptions
	' Precondition: N/A 
	' Arguments: selenium, blnClose
	' Returns: String 
	' Date: 16/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String ChkQuickRefGuideOptions(Selenium selenium, boolean blnClose)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectWindow("name=help");

			try {
				assertTrue(selenium
						.isElementPresent("//ul[@id='guides']/li/span[contains(text(),"
								+ "'Mobile for iPhone: Getting Started')]/a[text()='Download PDF']"));
				log4j.info("1. Mobile for iPhone: Getting Started option is displayed and"
						+ " 'Download PDF' link is displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "1. Mobile for iPhone: Getting Started option is NOT displayed and"
						+ " 'Download PDF' link is displayed." + Ae;
				log4j.info("1. Mobile for iPhone: Getting Started option is NOT displayed and"
						+ " 'Download PDF' link is displayed." + Ae);
			}
			try {
				assertTrue(selenium
						.isElementPresent("//ul[@id='guides']/li/span[contains(text(),"
								+ "'Mobile for Android: Getting Started ')]/a[text()='Download PDF']"));
				log4j.info("1. Mobile for Android: Getting Started option is displayed and"
						+ " 'Download PDF' link is displayed.");

			} catch (AssertionError Ae) {
				strErrorMsg = "1. Mobile for Android: Getting Started option is NOT displayed and"
						+ " 'Download PDF' link is displayed." + Ae;
				log4j.info("1. Mobile for Android: Getting Started option is NOT displayed and"
						+ " 'Download PDF' link is displayed." + Ae);
			}

			if (blnClose) {
				// Close Help center window
				selenium.close();
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}

		} catch (Exception e) {
			log4j.info("ChkQuickRefGuideOptions function failed" + e);
			strErrorMsg = "ChkQuickRefGuideOptions function failed" + e;
		}
		return strErrorMsg;
	}
	//start//navBackToHelpCenterLink//
	/*******************************************************************************************
	' Description: navBackToHelpCenterLink
	' Precondition: N/A 
	' Arguments: selenium, blnClose
	' Returns: String 
	' Date: 16/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navBackToHelpCenterLink(Selenium selenium, boolean blnClose)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("name=help");
			selenium.click("link=Back to the Help Center");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Welcome to Help Center",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Welcome to Help Center Screen is displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Welcome to Help Center Screen is NOT displayed"
						+ Ae;
				log4j.info("Welcome to Help Center Screen is NOT displayed"
						+ Ae);
			}
			if (blnClose) {
				// Close Help center window
				selenium.close();
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			}

		} catch (Exception e) {
			log4j.info("navToHelpCenterByHelpLink function failed" + e);
			strErrorMsg = "navToHelpCenterByHelpLink function failed" + e;
		}
		return strErrorMsg;
	}
	//end//navBackToHelpCenterLink//
	//start//VerifyIsTextPresent//
		/*******************************************************************************************
		' Description: VerifyIsTextPresent
		' Precondition: N/A 
		' Arguments: selenium, strText,blnPresent
		' Returns: String 
		' Date: 30/07/2013
		' Author: QSG 
		'--------------------------------------------------------------------------------- 
		' Modified Date: 
		' Modified By: 
		*******************************************************************************************/

		public String VerifyIsTextPresent(Selenium selenium, String strText, boolean blnPresent)
				throws Exception {

			String gstrReason = "";// variable to store error mesage

			try {
				rdExcel = new ReadData();
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();
				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				if(blnPresent){
					try{
						assertTrue(selenium.isTextPresent(strText));
						log4j.info(strText+" is present");
					} catch (AssertionError Ae) {
						gstrReason=strText+" is Not present";
					}
				}else{
					try{
						assertFalse(selenium.isTextPresent(strText));
						log4j.info(strText+" is not present");
					} catch (AssertionError Ae) {
						gstrReason=strText+" is present";
					}
				}

			} catch (Exception e) {
				log4j.info("VerifyIsTextPresent function failed" + e);
				gstrReason = "VerifyIsTextPresent function failed" + e;
			}
			return gstrReason;
		}
		//end//VerifyIsTextPresent//
	
	/*******************************************************************************************
	' Description  : Click on intermedix logo on home page
	' Precondition : N/A 
	' Arguments    : selenium,
	' Returns 	   : String 
	' Date         : 08/10/2013
	' Author       : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By  : 
	*******************************************************************************************/

	public String ChkOnIntrmdxLogoAndVfyPopUp(Selenium seleniumMail)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
					
			seleniumMail.openWindow("http://www.emsystems.com","Mail");
			Thread.sleep(10000);
			
			seleniumMail.selectWindow("Mail");
			try{
				assertTrue(seleniumMail.isElementPresent("//div[@id='implementationServices']/h3"));
				log4j.info("A new pop up is displayed");
			}catch(AssertionError ae){
				strErrorMsg="A new pop up is not displaye";
			}
			seleniumMail.close();	

		}catch (Exception e) {
		log4j.info("VerifyIsTextPresent function failed" + e);
		strErrorMsg = "VerifyIsTextPresent function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	' Description  : Click on intermedix logo on home page
	' Precondition : N/A 
	' Arguments    : selenium,
	' Returns 	   : String 
	' Date         : 08/10/2013
	' Author       : QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By  : 
	*******************************************************************************************/

	public String clkOnTermsAndConditionLink(Selenium seleniumMail)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			seleniumMail.selectWindow("");
			seleniumMail.openWindow("http://www.intermedix.com/legal/terms_of_service.html","Mail");
			Thread.sleep(10000);
			
			seleniumMail.selectWindow("Mail");
			try{
				assertTrue(seleniumMail.isElementPresent("css=img[alt=Intermedix]"));
				log4j.info("'TERMS AND CONDITIONS OF USE' page is opened in a new window ");
			}catch(AssertionError ae){
				strErrorMsg="'TERMS AND CONDITIONS OF USE' page is NOT opened in a new window ";
			}
			seleniumMail.close();	
			

		}catch (Exception e) {
		log4j.info("clkOnTermsAndConditionLink function failed" + e);
		strErrorMsg = "clkOnTermsAndConditionLink function failed" + e;
		}
		return strErrorMsg;
	}
	
	//start//verifyEmailAndFetchLink//
	/*******************************************************************************************
	' Description	: Function to verify mail with provied subject and fetch link in the mail.
	' Precondition	: N/A 
	' Arguments		: selenium, strFrom, strTo, strSubName, strLink
	' Returns		: String[] 
	' Date			: 07/11/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/	
	public String[] verifyEmailAndFetchLink(Selenium seleniumMail,
			String strFrom, String strTo, String strSubjName, String strLink) {
		String[] strReason = new String[2];
		strReason[0] = "";
		strReason[1] = "";
		try {

			int intCnt = 0;
			do {
				try {

					assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
							+ strSubjName + "']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 20);

			while ((seleniumMail.isElementPresent("//a/b[text()='"
					+ strSubjName + "']") == false)
					&& (seleniumMail.isElementPresent("//a[@id='next']/img"))) {
				seleniumMail.click("//a[@id='next']/img");
				seleniumMail.waitForPageToLoad("150000");
			}

			try {

				if (seleniumMail.isElementPresent("//a/b[text()='"
						+ strSubjName + "']")) {
					log4j.info("The mail with subject " + strSubjName
							+ " is present in the inbox");

					seleniumMail.click("//a/b[text()='" + strSubjName + "']");
					seleniumMail.waitForPageToLoad("150000");

					intCnt = 0;
					do {
						try {

							assertTrue(seleniumMail
									.isElementPresent("//tr[2]/td[2]/span"));
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
						log4j.info(seleniumMail.getText("//tr[2]/td[2]/span"));
						assertEquals(strFrom, seleniumMail
								.getText("//tr[2]/td[2]/span"));
						log4j
								.info("'From' field value is displayed correctly with value "
										+ strFrom);
					} catch (AssertionError ae) {
						log4j
								.info("'From' field value is NOT displayed correctly");
						strReason[0] = "'From' field value is NOT displayed correctly";
					}

					try {
						log4j.info(seleniumMail.getText("//tr[3]/td[2]/span"));
						assertEquals(strTo, seleniumMail
								.getText("//tr[3]/td[2]/span"));
						log4j
								.info("'To' field value is displayed correctly with the value "
										+ strTo);
					} catch (AssertionError ae) {
						log4j
								.info("'To' field value is NOT displayed correctly");
						strReason[0] = " 'To' field value is NOT displayed correctly";
					}

					try {
						assertEquals(strSubjName, seleniumMail
								.getText("//tr[4]/td[2]"));
						log4j
								.info("'Subject' field value is displayed correctly ");
					} catch (AssertionError ae) {
						log4j
								.info("'Subject' field value is NOT displayed correctly");
						strReason[0] = " 'Subject' field value is NOT displayed correctly";
					}

					strLink = seleniumMail
							.getText("//div[@id='messageBody']/table/tbody/tr/td/div/a[@target='_blank']");
					strReason[1] = strLink;
				} else {
					assertTrue(seleniumMail
							.isElementPresent("//a/img[@alt='<<']"));
					seleniumMail.click("//a/img[@alt='<<']");
					seleniumMail.waitForPageToLoad("150000");

					while ((seleniumMail.isElementPresent("//a/b[text()='"
							+ strSubjName + "']") == false)
							&& (seleniumMail
									.isElementPresent("//a[@id='next']/img"))) {
						seleniumMail.click("//a[@id='next']/img");
						seleniumMail.waitForPageToLoad("150000");
					}

					assertTrue(seleniumMail.isElementPresent("//a/b[text()='"
							+ strSubjName + "']"));
					log4j.info("The mail with subject " + strSubjName
							+ " is present in the inbox");

					seleniumMail.click("//a/b[text()='" + strSubjName + "']");
					seleniumMail.waitForPageToLoad("150000");

					intCnt = 0;
					do {
						try {

							assertTrue(seleniumMail
									.isElementPresent("//tr[2]/td[2]/span"));
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
						log4j.info(seleniumMail.getText("//tr[2]/td[2]/span"));
						assertEquals(strFrom, seleniumMail.getText("//tr[2]/td[2]/span"));
						log4j.info("'From' field value is displayed correctly with value " + strFrom);
					} catch (AssertionError ae) {
						log4j.info("'From' field value is NOT displayed correctly");
						strReason[0] = "'From' field value is NOT displayed correctly";
					}

					try {
						log4j.info(seleniumMail.getText("//tr[3]/td[2]/span"));
						assertEquals(strTo, seleniumMail.getText("//tr[3]/td[2]/span"));
						log4j.info("'To' field value is displayed correctly with the value "
										+ strTo);
					} catch (AssertionError ae) {
						log4j.info("'To' field value is NOT displayed correctly");
						strReason[0] = " 'To' field value is NOT displayed correctly";
					}

					try {
						assertEquals(strSubjName, seleniumMail.getText("//tr[4]/td[2]"));
						log4j.info("'Subject' field value is displayed correctly ");
					} catch (AssertionError ae) {
						log4j.info("'Subject' field value is NOT displayed correctly");
						strReason[0] = " 'Subject' field value is NOT displayed correctly";
					}

					strLink = seleniumMail.getText("//div[@id='messageBody']/table/tbody/tr/td/div/a[@target='_blank']");
					strReason[1] = strLink;
				}

			} catch (AssertionError ae) {
				log4j.info("The mail with subject " + strSubjName
						+ " is NOT present in the inbox");
				strReason[0] = "The mail with subject " + strSubjName
						+ " is NOT present in the inbox";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason[0] = "Exception Occured: " + e.toString();
		}
		return strReason;
	}
}
