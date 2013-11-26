package com.qsgsoft.EMResource.shared;

import static org.junit.Assert.*;

import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class Views {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.Views");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	ReadData rdExcel;
	/***************************************************************
	'Description	:navigate to particular user view
	'Precondition	:None
	'Arguments		:selenium,strViewName
	'Returns		:strReason
	'Date	 		:15-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String navToUserView(Selenium selenium, String strViewName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// View Tab
			selenium.mouseOver(propElementDetails
					.getProperty("View.ViewLink"));
			// click on View name link
			selenium.click("link=" + strViewName);
			selenium.waitForPageToLoad(gstrTimeOut);

			
			int intCnt=0;
			do{
				try {

					assertEquals(strViewName, selenium.getText("css=h1"));
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
				assertEquals(strViewName, selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info(strViewName + " page is displayed");

			} catch (AssertionError Ae) {
				log4j.info(strViewName + " page is NOT displayed");
				strReason = strViewName + " page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToUserView " + e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	   'Description :check status type in user view screen
	   'Precondition :None
	   'Arguments  :selenium, statustype
	   'Returns  :strReason
	   'Date    :28-May-2012
	   'Author   :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String chkSTInUserViewScreen(Selenium selenium, String[] strStatType)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			int intCol = 0;
			for (intCol = 0; intCol < strStatType.length; intCol++) {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/table/tbody/tr/td[3]/a[text()='"
									+ strStatType[intCol] + "']"));
					log4j.info("Status Type" + strStatType[intCol]
							+ "is displayed in the User view screen");
				} catch (AssertionError ae) {
					log4j.info("Status Type " + strStatType[intCol]
							+ "is NOT displayed in the User view screen");
					;
					strReason = "Status Type " + strStatType[intCol]
							+ "is NOT displayed in the User view screen";
	
				}
			}

		} catch (Exception e) {
			log4j.info("Failed in function chkSTInUserViewScreen ");
			strReason = "Failed in function chkSTInUserViewScreen "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:verify SType details in View Resource Detail' screen.
					section.
	'Precondition	:None
	'Arguments		:selenium,strStatType
	'Returns		:strReason
	'Date	 		:24-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/
	
	public String checkStatusTypeInViewScreen(Selenium selenium,String strStatType) throws Exception
	{
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason="";
		try{
			try
			{
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/div/table/thead"
								+ "/tr/th[3]/a[text()='" + strStatType + "']"));
				log4j.info(strStatType+"Status Type is not displayed in the User view  screen");
	  	}catch(AssertionError ae){
			log4j.info(strStatType+"Status Type is  displayed in the User view Detail screen");
			strReason="Status Type is displayed in the User view Detail screen";
		}
		
		
		} catch (Exception e) {
			log4j.info("verifySType Details InViewscreen function failed" + e);
			strReason = "verifyStype Details InViewScreen function failed"
					+ e;
		}
				
		return strReason;
	}
	
	 /***********************************************************************
	 'Description : check status types in Edit custom View options
	 'Precondition :None
	 'Arguments  :selenium,strStatusType
	     
	 'Returns  :String
	 'Date    :25-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                   <Name>
	 ************************************************************************/
	 public String checkStatTypeInEditCustViewOptions(Selenium selenium,String strStatusType) throws Exception{
	  String strReason="";
	  propEnvDetails = objReadEnvironment.readEnvironment();
	  propElementDetails = objelementProp.ElementId_FilePath();
	 
	  gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	  try{
	   selenium.click(propElementDetails.getProperty("EditCustomView.Options"));
	   selenium.waitForPageToLoad(gstrTimeOut);
	   
	   try{
	    assertEquals("Edit Custom View Options (Columns)",
	      selenium.getText(propElementDetails
	        .getProperty("Header.Text")));
	    assertTrue(selenium.isElementPresent("//b[text()='"+strStatusType+"']"));
	    log4j.info("Status Type "+strStatusType+" is displayed in Edit Custom View Options (Columns)");
	   } catch (AssertionError Ae) {
	    strReason = "Status Type "+strStatusType+" is not displayed in Edit Custom View Options (Columns)";
	    log4j.info("Status Type "+strStatusType+" is not displayed in Edit Custom View Options (Columns)");
	   }
	  
	   
	  } catch (Exception e) {
	   log4j.info("checkStatTypeInEditCustViewOptions function failed" + e);
	   strReason = "checkStatTypeInEditCustViewOptions function failed" + e;
	  }
	  return strReason;
	 }
	 
	 /***************************************************************
	 'Description  :Return to user view screen from update status
	                screen by clicking on Return to View.
	 'Precondition :None
	 'Arguments    :selenium,strViewName
	 'Returns      :strReason
	 'Date         :28-May-2012
	 'Author       :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	 public String returnUserView(Selenium selenium,String strViewName,boolean blnCheckpage)
	   throws Exception {

	  String strReason = "";
	  propEnvDetails = objReadEnvironment.readEnvironment();
	  propElementDetails = objelementProp.ElementId_FilePath();

	  gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("View.ReturnToView")));
				log4j.info("Return to View link is  available");

				
				if (blnCheckpage) {
					// click on Return to View
					selenium.click(propElementDetails
							.getProperty("View.ReturnToView"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals(strViewName, selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("User is returned to " + strViewName
								+ " page");

					} catch (AssertionError Ae) {
						log4j.info("User is NOT returned to " + strViewName
								+ " page");
						strReason = "User is NOT returned to " + strViewName
								+ " pages";
					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Return to View link is available");
				strReason = "Return to View link is available";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function returnUserViews" + e.toString();
		}
		return strReason;
	 }
	 
	 /***************************************************************
	 'Description  :Navigate to event status screen
	 'Precondition :None
	 'Arguments    :selenium
	 'Returns      :strReason
	 'Date         :28-May-2012
	 'Author       :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String navToEventStatusScreen(Selenium selenium) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on Return to View
			selenium.click(propElementDetails.getProperty("View.ReturnToView"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Event Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("User is returned to 'Event Status' screen ");

			} catch (AssertionError Ae) {
				log4j.info("User is NOT returned to 'Event Status' screen  ");
				strReason = "User is NOT returned to 'Event Status' screen ";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToEventStatusScreen" + e.toString();
		}
		return strReason;
	}
	
	
	 /***************************************************************
		'Description	:Navigate to Edit View Screen is displayed when 
		'				 click on customize link
		'Precondition	:None
		'Arguments		:None
		'Returns		:None
		'Date	 		:4th-june-2012
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date                            Modified By
		'<Date>                                     <Name>
		***************************************************************/
	
	public String navEditViewPgeByCustomizeLink(Selenium selenium)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("CustomViewTable.Customize"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Edit View", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info(" Edit View Screen is  displayed ");

			} catch (AssertionError Ae) {
				log4j.info(" Edit View Screen is NOT displayed " + Ae);
				strReason = "Edit View Screen is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navEditViewPgeByCustomizeLink " + e;
		}
		return strReason;
	}

/***************************************************************
'Description :Verify Updated status value is displayed in user view screen
'Precondition :None
'Arguments  :selenium,strUpdateValue,strRT,strST,strViewName
'Returns  :strReason
'Date    :7-June-2012
'Author   :QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/

	public String checkErrorMsgInUpdateStatusView(Selenium selenium,
			String strStatusName, String strStatusVal, String strDefinition,
			String strStatType, String strStatReason, String strErrMsg)
			throws Exception {
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
							+ strStatusName
							+ "']/preceding-sibling::input[@type='radio']");
			Thread.sleep(10000);

			try {
				assertEquals(strErrMsg, selenium
						.getText("css=span.emsText.IndicatesRequiredText"));
				log4j
						.info("You must select one or more from this list when choosing"
								+ strStatusName + " status: is displayed");

			} catch (AssertionError ae) {
				log4j
						.info("You must select one or more from this list when choosing"
								+ strStatusName + " status:is NOT displayed");
			}
			try {
				assertEquals(strStatReason + " -- " + strDefinition + "",
						selenium.getText("css=#statusValue_" + strStatusVal
								+ "section > div > label"));
				log4j.info("statusreason" + strDefinition + " is displayed");
			} catch (AssertionError ae) {
				log4j.info("SR" + strDefinition + " is NOT displayed");
			}
			// save
			selenium.click(propElementDetails
					.getProperty("View.UpdateStatus.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText("css=span.emsErrorTitle"));
				assertEquals(strStatType + " status " + strStatusName
						+ " must have status reason.", selenium
						.getText("css=div.emsError > ul > li"));
				log4j.info("" + strStatType + "status" + strStatusName
						+ " must have status reason.' is displayed");
			} catch (AssertionError ae) {
				log4j.info("" + strStatType + "status" + strStatusName
						+ " must have status reason.' is NOT displayed");
			}
			selenium.click("css=input[value='Cancel']");
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);

			strReason = "Failed in function checkErrorMsgInUpdateStatusView" + e.toString();
		}
		return strReason;
	}

	/***************************************************************
	'Description	:Update status of status type
	'Precondition	:None
	'Arguments		:selenium,strStatType,strStatus,strComent
	'Returns		:strReason
	'Date	 		:15-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String updateStatusOfStatusType(Selenium selenium,
			String strStatType, String strStatus, String strComent)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/div/table/thead"
								+ "/tr/th[3]/a[text()='" + strStatType + "']"));
				log4j.info("Status Type is displayed in the User view table");
				// click update status image
				selenium.click("//div[@id='mainContainer']/div/table/tbody/"
						+ "tr/td[1]/a/img");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Update Status", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Update Status screen is displayed");

					// select status
					selenium
							.click("//div[@class='multiStatus']/div/span/label[text()='"
									+ strStatus + "']/preceding-sibling::input");

					// save
					selenium.click(propElementDetails
							.getProperty("View.UpdateStatus.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='viewContainer']/table/thead/tr/th[3][text()='"
										+ strStatType + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='viewContainer']/table/tbody/tr/td[3][text()='"
										+ strStatus + "']"));
						log4j.info("Updated status value " + strStatus
								+ " is displayed on view");
					} catch (AssertionError ae) {
						log4j.info("Updated status value " + strStatus
								+ " is NOT displayed on view");
						strReason = "Updated status value " + strStatus
								+ " is NOT displayed on view";
					}
				} catch (AssertionError ae) {
					log4j.info("Update Status screen is NOT displayed");
					strReason = "Update Status screen is NOT displayed";
				}
			} catch (AssertionError Ae) {
				log4j.info(strStatType + " is NOT displayed for this view");
				strReason = strStatType + " is NOT displayed for this view";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusOfStatusType "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Region Views List page is displayed
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navRegionViewsList(Selenium selenium) throws Exception {

		String strReason = "";
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
							.getProperty("SetUP.SetUpLink")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));

			selenium.click(propElementDetails
					.getProperty("MobileView.ViewLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Region Views List", selenium
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

				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");

			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				strReason = "Region Views List page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navRegionViewsList " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify new View is Created
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String createView(Selenium selenium, String strViewName,
			String strVewDescription, String strViewType,
			boolean blnVisibleToAllUsers, boolean blnShowAllStatusTypes,
			String[] strSTvalue, boolean blnShowAllResourceTypes,
			String[] strRSValue) throws Exception {

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
							.getProperty("View.CreateNewView")));
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
					.click(propElementDetails.getProperty("View.CreateNewView"));
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {
					assertEquals("Create New View", selenium
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

				assertEquals("Create New View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Create New View page is  displayed ");

				
				intCnt=0;
				do{
					try {

						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("View.CreateNewView.Name")));
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
						.getProperty("View.CreateNewView.Name"), strViewName);// View
				// Name
				selenium.type(propElementDetails
						.getProperty("View.CreateNewView.Description"),
						strVewDescription);// View Description

				selenium.select(propElementDetails
						.getProperty("View.CreateNewView.ViewType"), "label="
						+ strViewType);

				if (blnVisibleToAllUsers) {
					if (selenium
							.isChecked(propElementDetails
									.getProperty("View.CreateNewView.VisibleToAllUsers")) == false) {
						selenium.click("View.CreateNewView.VisibleToAllUsers");
					}

				} else {
					if (selenium
							.isChecked(propElementDetails
									.getProperty("View.CreateNewView.VisibleToAllUsers"))) {
						selenium
								.click(propElementDetails
										.getProperty("View.CreateNewView.VisibleToAllUsers"));
					}
				}

				// Select status type

				if (blnShowAllStatusTypes) {

					if (selenium.isChecked(propElementDetails
							.getProperty("View.CreateNewView.ShowAllColumns")) == false) {
						selenium
								.click(propElementDetails
										.getProperty("View.CreateNewView.ShowAllColumns"));

					}
				} else {

					for (String s : strSTvalue) {
						if (selenium
								.isChecked("css=input[name='statusTypeID'][value='"
										+ s + "']") == false) {
							selenium
									.click("css=input[name='statusTypeID'][value='"
											+ s + "']");

						}
					}
				}

				// Select Resources and Resource Types
				if (blnShowAllResourceTypes) {

					if (selenium.isChecked(propElementDetails
							.getProperty("View.CreateNewView.ShowAllRows")) == false) {
						selenium.click(propElementDetails
								.getProperty("View.CreateNewView.ShowAllRows"));

					}
				} else {

					for (String RS : strRSValue) {

						if (selenium
								.isChecked("css=input[name='resourceID'][value='"
										+ RS + "']") == false) {
							selenium
									.click("css=input[name='resourceID'][value='"
											+ RS + "']");

						}
					}
				}

				intCnt = 0;
				do {
					try {
						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("View.CreateNewView.Save")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;
					} catch (Exception e) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				selenium.click(propElementDetails
						.getProperty("View.CreateNewView.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='listViews']/"
										+ "tbody/tr/td[2][text()='"
										+ strViewName + "']"));
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

					assertEquals("Region Views List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Region Views List page is  displayed ");

					try {

						assertTrue(selenium
								.isElementPresent("//table[@id='listViews']/"
										+ "tbody/tr/td[2][text()='"
										+ strViewName + "']"));

						log4j.info("View " + strViewName
								+ "  is  displayed in Region Views List page ");

					} catch (AssertionError Ae) {
						log4j
								.info("View "
										+ strViewName
										+ "  is NOT displayed in Region Views List page"
										+ Ae);
						strReason = "View "
								+ strViewName
								+ "  is NOT displayed in Region Views List page"
								+ Ae;
					}

				} catch (AssertionError Ae) {
					log4j
							.info(" Region Views List page is NOT displayed "
									+ Ae);
					strReason = "Region Views List page is NOT displayed " + Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info(" Create New View page is NOT displayed " + Ae);
				strReason = "Create New View page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function createView " + e;
		}
		return strReason;
	}

	/***************************************************************
	'Description	:Verify Views  fields are updated.
	'Precondition	:None
	'Arguments		:selenium,strViewName,strUpdatedName,strViewType,
	'				 strVewDescription
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String updateViewFlds(Selenium selenium, String strViewName,
			String strUpdatedName, String strViewType, String strVewDescription)
			throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");

				selenium.click("//table[@id='listViews']/tbody/tr/"
						+ "td[text()='" + strViewName + "']/"
						+ "preceding-sibling::td/a[text()='Edit']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Edit View", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Edit View  page is  displayed ");

					selenium.type(propElementDetails
							.getProperty("View.CreateNewView.Name"),
							strUpdatedName);// View
					// Name
					selenium.type(propElementDetails
							.getProperty("View.CreateNewView.Description"),
							strVewDescription);// View Description
					selenium.select(propElementDetails
							.getProperty("View.CreateNewView.ViewType"),
							"label=" + strViewType);

					String strViewTypesSplit[] = strViewType.split(" \\(");

					for (String str : strViewTypesSplit) {
						log4j.info(str);
					}

					selenium.click(propElementDetails
							.getProperty("View.CreateNewView.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {

						assertTrue(selenium
								.isElementPresent("//table[@id='listViews']/tbody/tr/"
										+ "td[2][text()='"
										+ strUpdatedName
										+ "']"));

						assertTrue(selenium
								.isElementPresent("//table[@id='listViews']/tbody/tr/"
										+ "td[3][text()='"
										+ strViewTypesSplit[0] + "']"));

						assertTrue(selenium
								.isElementPresent("//table[@id='listViews']/tbody/tr/"
										+ "td[4][text()='"
										+ strVewDescription
										+ "']"));

						log4j.info(" Views  updated ");

					} catch (AssertionError Ae) {
						log4j.info(" Views NOT updated " + Ae);
						strReason = " Views NOT updated " + Ae;
					}

				} catch (AssertionError Ae) {
					log4j.info(" Edit View  page is NOT displayed " + Ae);
					strReason = "Edit View  page is NOT displayed " + Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				strReason = "Region Views List page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateViewFlds " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify users are assigned to particular views
	'Precondition	:None
	'Arguments		:strViewName,selenium,strUserNames[],blnVisibleToAllUsers
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String assignUsersToViews(Selenium selenium, String strViewName,
			String[] strUserNames, boolean blnVisibleToAllUsers, boolean blnSave)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//table[@id='listViews']/tbody/tr/"
					+ "td[2][text()='" + strViewName + "']/"
					+ "preceding-sibling::td/a[text()='Users']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Assign Users to View " + strViewName + "",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(" Assign Users to View " + strViewName
						+ " page is  displayed ");

				if (blnVisibleToAllUsers) {
					if (selenium.isChecked(propElementDetails
							.getProperty("View.CreateNewView.VisibleToAll")) == false) {
						selenium
								.click(propElementDetails
										.getProperty("View.CreateNewView.VisibleToAll"));

					}

					try {

						assertFalse(selenium.isEditable(propElementDetails
								.getProperty("View.CreateNewView.SELECT_ALL")));

						log4j
								.info("Check box associated with all the users are disabled. ");
					} catch (AssertionError Ae) {

						log4j
								.info("Check box associated with all the users are enabled. "
										+ Ae);
						strReason = "Check box associated with all the users are enabled. "
								+ Ae;
					}
				} else {

					if (selenium.isChecked(propElementDetails
							.getProperty("View.CreateNewView.VisibleToAll"))) {
						selenium
								.click(propElementDetails
										.getProperty("View.CreateNewView.VisibleToAll"));

					}
					try {
						assertTrue(selenium.isEditable(propElementDetails
								.getProperty("View.CreateNewView.SELECT_ALL")));
						log4j
								.info("Check box associated with all the users are enabled. ");

						if (strUserNames != null) {
							for (String s : strUserNames) {

								if (selenium.isChecked("css=input[value='" + s
										+ "']") == false) {
									selenium.click("css=input[value='" + s
											+ "']");
								}

							}
						}

					} catch (AssertionError Ae) {

						log4j
								.info("Check box associated with all the users are disabled. "
										+ Ae);
						strReason = "Check box associated with all the users are disabled. "
								+ Ae;
					}
				}

				if (blnSave) {
					selenium.click(propElementDetails
							.getProperty("View.CreateNewView.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {

						assertEquals("Region Views List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info(" Region Views List page is  displayed ");

					} catch (AssertionError Ae) {
						log4j.info(" Region Views List page is NOT displayed "
								+ Ae);
						strReason = "Region Views List page is NOT displayed "
								+ Ae;
					}
				}

			} catch (AssertionError Ae) {

				log4j.info(" Assign Users to View " + strViewName
						+ " page is NOT displayed " + Ae);
				strReason = "Assign Users to View " + strViewName
						+ " page is NOT displayed " + Ae;

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function assignUsersToViews " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Edit View page is displayed when click 
	'				 on customize link
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navEditViewsPgeByCustomize(Selenium selenium,
			String strViewName) throws Exception {

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

					assertTrue(selenium.isElementPresent("link=customize"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.click("link=customize");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt = 0;
			do {
				try {

					assertEquals("Edit View", selenium
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

				assertEquals("Edit View", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info(" Edit View page is  displayed ");

				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertEquals(strViewName, selenium
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
					assertEquals(strViewName, selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(strViewName + " page is  displayed ");

				} catch (AssertionError Ae) {
					log4j.info(strViewName + " page is NOT displayed " + Ae);
					strReason = strViewName + " page is NOT displayed " + Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info(" Edit Viewpage is NOT displayed " + Ae);
				strReason = "Edit View page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navEditViewsPgeByCustomize " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	 'Description : TO edit view fields 
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :4-June-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
			 
		 
	public String editViewFields(Selenium selenium, String strViewName,
			String strVewDescription, String strViewType,
			boolean blnVisibleToAllUsers, boolean blnShowAllStatusTypes,
			String[] strSTvalue, boolean blnShowAllResourceTypes,
			String[][] strRSValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails
					.getProperty("View.CreateNewView.Name"), strViewName);// View
			// Name
			selenium
					.type(propElementDetails
							.getProperty("View.CreateNewView.Description"), strVewDescription);// View
			// Description

			selenium.select(propElementDetails
					.getProperty("View.CreateNewView.ViewType"), "label=" + strViewType);

			if (blnVisibleToAllUsers) {
				if (selenium.isChecked(propElementDetails
						.getProperty("View.CreateNewView.VisibleToAllUsers")) == false) {
					selenium.click(propElementDetails
							.getProperty("View.CreateNewView.VisibleToAllUsers"));
				}

			}

			// Select status type

			if (blnShowAllStatusTypes) {

				if (selenium.isChecked(propElementDetails
						.getProperty("View.CreateNewView.ShowAllColumns")) == false) {
					selenium.click(propElementDetails
							.getProperty("View.CreateNewView.ShowAllColumns"));

				}
			} else {

				for (String s : strSTvalue) {
					if (selenium
							.isChecked("css=input[name='statusTypeID'][value='"
									+ s + "']") == false) {
						selenium.click("css=input[name='statusTypeID'][value='"
								+ s + "']");

					}
				}
			}

			// Select Resources and Resource Types
			if (blnShowAllResourceTypes) {

				if (selenium.isChecked(propElementDetails
						.getProperty("View.CreateNewView.ShowAllRows")) == false) {
					selenium.click(propElementDetails
							.getProperty("View.CreateNewView.ShowAllRows"));

				}
			} else {

				for (int i = 0; i < strRSValue.length; i++) {

					if (strRSValue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='resourceID'][value='"
										+ strRSValue[i][0] + "']") == false) {
							selenium
									.click("css=input[name='resourceID'][value='"
											+ strRSValue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strRSValue[i][0] + "']")) {
						selenium.click("css=input[name='resourceID'][value='"
								+ strRSValue[i][0] + "']");
					}

				}
			}

			selenium.click(propElementDetails
					.getProperty("View.CreateNewView.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

				try {
                   
					assertEquals(strViewName,selenium.getText(propElementDetails
							.getProperty("Header.Text")));

					log4j.info("Edited "+strViewName+" page is displayed ");

				} catch (AssertionError Ae) {
					log4j.info("Edited "+strViewName+" page is  not displayed ");
					strReason = "Edited "+strViewName+" page is  not displayed ";
				}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function fillViewFields " + e;
		}
		return strReason;
	}
	/***************************************************************
	'Description	:Verify order and re-ordering views
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String orderAndReorderViews(Selenium selenium, String strOrderView1,
			String strOrderView2) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("View.CreateNewView.ReOrderViews"));

			selenium.dragAndDropToObject(
					"//table[@id='listViews']/tbody/tr/td[2][text()='"
							+ strOrderView1 + "']",
					"//table[@id='listViews']/tbody/tr/td[2][text()='"
							+ strOrderView2 + "']");

			try {

				assertEquals(
						"You must click on the 'Save' button below to save "
								+ "your view order changes.", selenium
								.getText("css=#saveWarning > ul > li"));

				assertEquals("Click on the 'Cancel' button below to undo your "
						+ "view order changes.", selenium
						.getText("//div[@id='saveWarning']" + "/ul/li[2]"));

				log4j.info("Information message is displayed stating "
						+ "Please note:You must click on the 'Save' button"
						+ " below to save your view order changes."
						+ "Click on the 'Cancel' button below to undo "
						+ "your view order changes.");

				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("View.CreateNewView.DoneReOrdering")));

					log4j.info("Done Re-ordering' button is displayed");

					int intRowView1 = selenium.getXpathCount(
							"//table[@id='listViews']/tbody/tr/td[2][text()='"
									+ strOrderView1
									+ "']/parent::tr/preceding-sibling::tr")
							.intValue();
					int intRowView2 = selenium.getXpathCount(
							"//table[@id='listViews']/tbody/tr/td[2][text()='"
									+ strOrderView2
									+ "']/parent::tr/preceding-sibling::tr")
							.intValue();

					selenium.click(propElementDetails
							.getProperty("View.CreateNewView.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);
					try {
						assertEquals("Region Views List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info("Region Views List page is  displayed ");

						try {
							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("View.CreateNewView.ReOrderViews")));

							log4j
									.info("'Done Re-ordering' button is changed back to 'Re-order Views' ");

							try {
								assertTrue(selenium
										.isElementPresent("//table[@id='listViews']/tbody/tr["
												+ (intRowView1 + 1)
												+ "]/td[2][text()='"
												+ strOrderView1 + "']"));
								assertTrue(selenium
										.isElementPresent("//table[@id='listViews']/tbody/tr["
												+ (intRowView2 + 1)
												+ "]/td[2][text()='"
												+ strOrderView2 + "']"));

								log4j
										.info("The re-ordered sequence is retained.");
							} catch (AssertionError Ae) {
								log4j
										.info("The re-ordered sequence is NOT retained.");

								strReason = "The re-ordered sequence is NOT retained."
										+ Ae;
							}

						} catch (AssertionError Ae) {
							log4j
									.info("'Done Re-ordering' button is NOT changed back to 'Re-order Views'");

							strReason = "'Done Re-ordering' button is NOT changed back to 'Re-order Views'"
									+ Ae;
						}

					} catch (AssertionError Ae) {
						log4j.info("Region Views List page is NOT displayed");
						strReason = "Region Views List page is NOT displayed";
					}

				} catch (AssertionError Ae) {
					log4j
							.info("Done Re-ordering' button is NOT displayed"
									+ Ae);

					strReason = "Done Re-ordering' button is NOT displayed"
							+ Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info("Information message is NOT displayed stating "
						+ "Please note:You must click on the 'Save' button"
						+ " below to save your view order changes."
						+ "Click on the 'Cancel' button below to undo "
						+ "your view order changes." + Ae);

				strReason = "Information message is NOT displayed stating "
						+ "Please note:You must click on the 'Save' button"
						+ " below to save your view order changes."
						+ "Click on the 'Cancel' button below to undo "
						+ "your view order changes." + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function orderAndReorderViews " + e;
		}
		return strReason;
	}

	/***************************************************************
	'Description	:navigate to view resource detail and check status type under particular
					section.
	'Precondition	:None
	'Arguments		:selenium,strSection,strStatus,strStatType,strResource
	'Returns		:strReason
	'Date	 		:15-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navAndCheckInViewResourceDetail(Selenium selenium,
			String strSection, String[] strStatType, String strResource)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on resource link
			selenium
					.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
							+ strResource + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");
				// check resource type is displayed

				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a[text()='"
								+ strSection + "']"));
				for (int intRec = 0; intRec < strStatType.length; intRec++) {

					try {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ strStatType[intRec] + "']"));
						log4j.info(strStatType[intRec]
								+ " is displayed under section " + strSection
								+ " on 'View Resource Detail'.");
					} catch (AssertionError ae) {
						log4j.info(strStatType[intRec]
								+ " is NOT displayed under section "
								+ strSection + " on 'View Resource Detail'.");
						strReason = strReason + strStatType[intRec]
								+ " is NOT displayed under section "
								+ strSection + " on 'View Resource Detail'.";
					}

				}

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason = "View Resource Detail screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navAndCheckInViewResourceDetail "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate to edit resource detail from user view
					section.
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:strReason
	'Date	 		:15-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navToEditResourceFromUserView(Selenium selenium,String strResource) throws Exception{
		String strReason="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			//click on resource link
			selenium.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"+strResource+"']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try{
				assertEquals("View Resource Detail", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");
				//click on edit resource details
				selenium.click("link=edit resource details");
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
				
			}catch(AssertionError ae){
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason="View Resource Detail screen is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			strReason = "Failed in function navToEditResourceFromUserView "+e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description	:navigate to edit custom view page from view custom
					section.
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:17-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String navEditCustomViewPageFromViewCustom(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on customize
			selenium.click(propElementDetails
					.getProperty("CustomViewTable.Customize"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Custom View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Custom View page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Custom View page is NOT displayed" + Ae;
				log4j.info("Edit Custom View page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navEditCustomViewPageFromViewCustom function failed"
					+ e);
			strErrorMsg = "navEditCustomViewPageFromViewCustom function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***************************************************************
	'Description	:navigate to view resource detail page
					section.
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navToViewResourceDetailPage(Selenium selenium,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
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
			// click on resource link
			selenium
					.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
							+ strResource + "']");
			try {
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (Exception e) {

			}

			intCnt = 0;
			do {
				try {

					assertEquals("View Resource Detail", selenium
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
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason = "View Resource Detail screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToViewResourceDetailPage "
					+ e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description	:verify resource details in view resource detail page
					section.
	'Precondition	:None
	'Arguments		:selenium,strResourceData
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/
	
	public String verifyResDetailsInViewResDetail(Selenium selenium,
			String strResource, String strResouceData[][]) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {

			int intCnt = 0;
			do {
				try {
					assertEquals(
							strResource,
							selenium
									.getText(propElementDetails
											.getProperty("View.ViewResourceDet.ResourceName")));
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
				assertEquals(strResource, selenium.getText(propElementDetails
						.getProperty("View.ViewResourceDet.ResourceName")));
				log4j.info(strResource
						+ " is displayed in View Resource Detail screen");
				for (int intRec = 0; intRec < strResouceData.length; intRec++) {
					if (strResouceData[intRec][1].compareTo("") != 0) {
						try {
							assertTrue(selenium
									.isElementPresent("//table[@class='compact']/tbody/tr/td[@class='emsLabel'][text()='"
											+ strResouceData[intRec][0] + "']"));
							
							if(strResouceData[intRec][0]=="EMResource/AHA ID:"){
								String str=selenium
								.getText("//table[@class='compact']/tbody/tr/td[@class='emsLabel'][text()='"
										+ strResouceData[intRec][0]
										+ "']/following-sibling::td");
								
								assertTrue(str.contains(strResouceData[intRec][1]));
							}else{
								assertTrue(selenium
										.isElementPresent("//table[@class='compact']/tbody/tr/td[@class='emsLabel'][text()='"
												+ strResouceData[intRec][0]
												+ "']/following-sibling::td[text()='"
												+ strResouceData[intRec][1] + "']"));
							}
							log4j.info(strResouceData[intRec][0]
									+ strResouceData[intRec][1]
									+ " is displayed in View Resource detail");
						} catch (AssertionError ae) {
							log4j
									.info(strResouceData[intRec][0]
											+ strResouceData[intRec][1]
											+ " is NOT displayed in View Resource detail");
							strReason = strReason
									+ " "
									+ strResouceData[intRec][0]
									+ strResouceData[intRec][1]
									+ " is NOT displayed in View Resource detail";
						}
					}
				}

			} catch (AssertionError ae) {
				log4j.info(strResource
						+ " is NOT displayed in View Resource Detail screen");
				strReason = strReason + " " + strResource
						+ " is NOT displayed in View Resource Detail screen";
			}
		} catch (Exception e) {
			log4j.info("verifyResDetailsInViewResDetail function failed" + e);
			strReason = "verifyResDetailsInViewResDetail function failed" + e;
		}
		return strReason;
	}

	/***************************************************************
	'Description	:navigate to Edit Resource Detail View Sections screen
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:23-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/
	
	public String navToEditResDetailViewSections(Selenium selenium)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {
			// click on customize resource detail view link
			selenium.click(propElementDetails
					.getProperty("RegionView.CustomizeResDetailView"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Edit Resource Detail View Sections", selenium
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
				assertEquals("Edit Resource Detail View Sections", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("Edit Resource Detail View Sections screen is displayed");

			} catch (AssertionError ae) {
				log4j
						.info("Edit Resource Detail View Sections screen is NOT displayed");
				strReason = "Edit Resource Detail View Sections screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("navToEditResDetailViewSections function failed" + e);
			strReason = "navToEditResDetailViewSections function failed" + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Drag and drop the status type to particular section and save
	'Precondition	:None
	'Arguments		:selenium,strStatType,strSection,blnCreateSec
	'Returns		:strReason
	'Date	 		:23-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'07-Sep-12                                   <Name>
	 * @throws Exception 
	***************************************************************/
	
	
	public String dragAndDropSTtoSection(Selenium selenium,
			String strStatType[], String strSection, boolean blnCreateSec)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {

			if (blnCreateSec) {
				// type the section name and click on create section

				selenium.type(propElementDetails
						.getProperty("EditResDetViewSec.SectionName"),
						strSection);
				selenium.click(propElementDetails
						.getProperty("EditResDetViewSec.CreateSection"));
			}

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strSection + "')]"));
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
				assertTrue(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection + "')]"));
				log4j.info("Section " + strSection + " is displayed");

				// click on Uncategorized - does NOT show in view

				selenium.click("css=#uncategorized > div > span");

				intCnt = 0;
				do {
					try {
						assertTrue(selenium
								.isElementPresent("//ul[@class='uncategorized ui-sortable']" +
										"/li/div[contains(text(),'"
										+ strStatType[1] + "')]"));
						break;
					} catch (AssertionError Ae) {
						intCnt++;
						Thread.sleep(1000);
					} catch (Exception e) {

						intCnt++;
						Thread.sleep(1000);

					}
				} while (intCnt < 10);

				for (String strStType : strStatType) {
					// drag and drop status type
					selenium.dragAndDropToObject(
							"//ul[@class='uncategorized ui-sortable']/li/div[contains(text(),'"
									+ strStType + "')]",
							"//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strSection + "')]");

				}

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection + "')]");
				intCnt = 0;
				do {
					try {
						assertTrue(selenium
								.isElementPresent("//li[@class='item']/div[contains(text(),'"
										+ strStatType[1] + "')]"));
						break;
					} catch (AssertionError Ae) {
						intCnt++;
						Thread.sleep(1000);
					} catch (Exception e) {

						intCnt++;
						Thread.sleep(1000);

					}
				} while (intCnt < 10);

				for (String strStType : strStatType) {
					try {
						assertTrue(selenium
								.isElementPresent("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						assertTrue(selenium
								.isVisible("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						log4j.info(strStType
								+ " status type is displayed in section "
								+ strSection);

					} catch (AssertionError ae) {
						log4j.info(strStType
								+ " status type is NOT displayed in section "
								+ strSection);
						strReason = strReason + " " + strStType
								+ " status type is NOT displayed in section "
								+ strSection;
					}
				}
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertEquals("Region Views List", selenium
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
					assertEquals("Region Views List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Region Views List screen is displayed");

				} catch (AssertionError ae) {
					log4j.info("Region Views List screen is NOT displayed");
					strReason = "Region Views List screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("Section " + strSection + " is NOT displayed");
				strReason = "Section " + strSection + " is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("dragAndDropSTtoSection function failed" + e);
			strReason = "dragAndDropSTtoSection function failed" + e;
		}
		return strReason;
	}
	/***************************************************************
	'Description	:Drag and drop the status type to particular section and save
	'Precondition	:None
	'Arguments		:selenium,strStatType,strSection,blnCreateSec
	'Returns		:strReason
	'Date	 		:23-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'07-Sep-12                                   <Name>
	 * @throws Exception 
	***************************************************************/

	public String verfySTInUncagorizedSection(Selenium selenium,
			String strStatType[]) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("css=#uncategorized > div > span"));
					break;
				} catch (AssertionError Ae) {
					intCnt++;
					Thread.sleep(1000);
				} catch (Exception e) {

					intCnt++;
					Thread.sleep(1000);

				}
			} while (intCnt < 60);

			// click on Uncategorized - does NOT show in view

			selenium.click("css=#uncategorized > div > span");

			intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//ul[@class='uncategorized ui-sortable']"
									+ "/li/div[contains(text(),'"
									+ strStatType[1] + "')]"));
					break;
				} catch (AssertionError Ae) {
					intCnt++;
					Thread.sleep(1000);
				} catch (Exception e) {

					intCnt++;
					Thread.sleep(1000);

				}
			} while (intCnt < 10);

			for (String strStType : strStatType) {
				try {
					assertTrue(selenium
							.isElementPresent("//li[@class='item']/div[contains(text(),'"
									+ strStType + "')]"));
					assertTrue(selenium
							.isVisible("//li[@class='item']/div[contains(text(),'"
									+ strStType + "')]"));
					log4j.info(strStType
							+ " status type is displayed in uncategorized section");

				} catch (AssertionError ae) {
					log4j.info(strStType
							+ " status type is displayed in uncategorized section");
					strReason = strReason
							+ " "
							+ strStType
							+ " status type is NOT displayed in uncategorized section";
				}
			}

		} catch (Exception e) {
			log4j.info("dragAndDropSTtoSection function failed" + e);
			strReason = "dragAndDropSTtoSection function failed" + e;
		}
		return strReason;
	}

	/***************************************************************
	'Description	:Delete a section
	'Precondition	:None
	'Arguments		:selenium,strSection
	'Returns		:strReason
	'Date	 		:24-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                  <Name>
	 * @throws Exception 
	***************************************************************/

	public String deleteSection(Selenium selenium, String strSection)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"
					+ strSection + "')]");

			selenium.click("css=a:contains('Delete')");

			try {
				assertFalse(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection + "')]"));
				// assertFalse(selenium.isVisible("//ul[@id='groups']/li/div/span[contains(text(),'"+strSection+"')]"));
				log4j.info(strSection + " is not displayed after deleting");

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Region Views List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Region Views List screen is displayed");

				} catch (AssertionError ae) {
					log4j.info("Region Views List screen is NOT displayed");
					strReason = "Region Views List screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info(strSection + " is displayed after deleting");
				strReason = strReason + strSection
						+ " displayed after deleting";
			}

		} catch (Exception e) {
			log4j.info("deleteSection function failed" + e);
			strReason = "deleteSection function failed" + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Edit a section
	'Arguments		:selenium,strSection,strEditSection
	'Returns		:strReason
	'Date	 		:24-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                                      Modified By
	<Date>                                             <Name>
	***************************************************************/

	public String editSection(Selenium selenium, String strSection,
			String strEditSection) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"
					+ strSection + "')]");

			selenium.click("css=a:contains('Edit')");

			// type the section name and click on create section

			selenium.type("//input[@id='editGroupNewName']",
					strEditSection);
			selenium.click("//input[@id='submitGroupEdit'][@value='Submit']");

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strEditSection + "')]"));
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
				assertTrue(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strEditSection + "')]"));
				assertTrue(selenium
						.isVisible("//ul[@id='groups']/li/div/span[contains(text(),"
								+ "'" + strEditSection + "')]"));

				log4j.info("Edited section " + strSection
						+ " is displayed after deleting");

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Region Views List",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Region Views List screen is displayed");

				} catch (AssertionError ae) {
					log4j.info("Region Views List screen is NOT displayed");
					strReason = "Region Views List screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info(strEditSection + " is displayed after deleting");
				strReason = strReason + strEditSection
						+ " displayed after deleting";
			}
		} catch (Exception e) {
			log4j.info("editSection function failed" + e);
			strReason = "editSection function failed" + e;
		}

		return strReason;
	}
	/***************************************************************
	'Description	:FetchSectionValue
	'Precondition	:None
	'Arguments		:selenium,strSection
	'Returns		:strReason
	'Date	 		:18-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'18-Sep-2012                                <Name>
	***************************************************************/
	public String fetchSectionID(Selenium selenium, String strSection)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			strReason = selenium.getAttribute("//input[@value='" + strSection
					+ "']/@name");
			log4j.info("Section value is " + strReason);
			String strReasons[] = strReason.split("_");
			strReason=strReasons[1];
			log4j.info("Section value is " + strReason);
		} catch (Exception e) {
			log4j.info("fetchSectionID function failed" + e);
			strReason = "fetchSectionID function failed" + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate to Edit Resource Detail View Sections
					section.
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:23-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String navEditResDetViewSecByCustomize(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// click on customize
			selenium.click("link=customize");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Edit Resource Detail View Sections", selenium
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
				assertEquals("Edit Resource Detail View Sections", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("Edit Resource Detail View Sections screen is displayed");

			} catch (AssertionError ae) {
				log4j
						.info("Edit Resource Detail View Sections screen is NOT displayed");
				strErrorMsg = "Edit Resource Detail View Sections screen is NOT displayed";

			}

		} catch (Exception e) {
			log4j.info("navEditResDetViewSecByCustomize function failed" + e);
			strErrorMsg = "navEditResDetViewSecByCustomize function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***************************************************************
	'Description	:Copy and create an View.
	'Precondition	:None
	'Arguments		:selenium,strViewName,strNewViewName,strVewDescription,strViewType				 
	'Returns		:None
	'Date	 		:23-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String copyAndCreateView(Selenium selenium, String strViewName,
			String strNewViewName,String strVewDescription,String strViewType)
			throws Exception {

		String strReason = "";
		
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			//click on Copy
			selenium.click("//table[@id='listViews']/tbody/tr/"
					+ "td[text()='" + strViewName + "']/"
					+ "preceding-sibling::td/a[text()='Copy']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
				
			int intCnt=0;
			do{
				try {

					assertEquals("Edit View", selenium
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
				assertEquals("Edit View", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(" Edit View  page is  displayed ");
				
				
				selenium.type(propElementDetails
						.getProperty("View.CreateNewView.Name"), strNewViewName);// View
																		// Name
				selenium.type(propElementDetails
						.getProperty("View.CreateNewView.Description"),
						strVewDescription);// View Description
				selenium.select(propElementDetails
						.getProperty("View.CreateNewView.ViewType"), "label=" + strViewType);

				selenium.click(propElementDetails
						.getProperty("View.CreateNewView.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				
				intCnt=0;
				do{
					try {
						assertEquals("Region Views List", selenium.getText(propElementDetails
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
					assertEquals("Region Views List", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					assertTrue(selenium
							.isElementPresent("//table[@id='listViews']/tbody/tr/"
									+ "td[2][text()='"
									+ strNewViewName
									+ "']"));

				
					log4j.info("View "+strNewViewName+" is displayed in 'Region Views List' screen");

				} catch (AssertionError Ae) {
					log4j.info("View "+strNewViewName+" is displayed in 'Region Views List' screen");
					strReason = "View "+strNewViewName+" is displayed in 'Region Views List' screen" + Ae;
				}

				
			} catch (AssertionError Ae) {
				log4j.info(" Edit View  page is NOT displayed " + Ae);
				strReason = "Edit View  page is NOT displayed " + Ae;
			}				
		

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function copyAndCreateView " + e;
		}
		return strReason;
	}
	

	/***************************************************************
	'Description	:Fill the mandatory fields and save. Verify View
					is displayed.
	'Precondition	:None
	'Arguments		:selenium,strNewViewName,strVewDescription,strViewType				 
	'Returns		:None
	'Date	 		:29-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String fillMandFieldsInEditViewAndVerifyView(Selenium selenium,
			String strNewViewName,String strVewDescription,String strViewType)
			throws Exception {

		String strReason = "";
		
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {				
				
			selenium.type(propElementDetails
					.getProperty("View.CreateNewView.Name"), strNewViewName);// View
			// Name
				selenium.type(propElementDetails
						.getProperty("View.CreateNewView.Description"),
				strVewDescription);// View Description
				selenium.select(propElementDetails
						.getProperty("View.CreateNewView.ViewType"), "label=" + strViewType);
				
				selenium.click(propElementDetails
						.getProperty("View.CreateNewView.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try {
					assertEquals("Region Views List", selenium.getText(propElementDetails.getProperty("Header.Text")));
					assertTrue(selenium
					.isElementPresent("//table[@id='listViews']/tbody/tr/"
					+ "td[2][text()='"
					+ strNewViewName
					+ "']"));
					
					
					log4j.info("View "+strNewViewName+" is displayed in 'Region Views List' screen");
				
				} catch (AssertionError Ae) {
					log4j.info("View "+strNewViewName+" is displayed in 'Region Views List' screen");
					strReason = "View "+strNewViewName+" is displayed in 'Region Views List' screen" + Ae;
				}			

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function fillMandFieldsInEditViewAndVerifyView " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate to Edit View page by clicking on Copy 
					to particular view
	'Precondition	:None
	'Arguments		:selenium,strViewName	 
	'Returns		:None
	'Date	 		:29-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navToEditViewByCopyView(Selenium selenium,String strViewName)
			throws Exception {

		String strReason = "";
		
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			//click on Copy
			selenium.click("//table[@id='listViews']/tbody/tr/"
					+ "td[text()='" + strViewName + "']/"
					+ "preceding-sibling::td/a[text()='Copy']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Edit View", selenium
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
				assertEquals("Edit View", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(" Edit View  page is  displayed ");
								
							
			} catch (AssertionError Ae) {
				log4j.info(" Edit View  page is NOT displayed " + Ae);
				strReason = "Edit View  page is NOT displayed " + Ae;
			}				
		

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToEditViewByCopyView " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify Resource and Status Types are selected
					or deselected.
	'Precondition	:None
	'Arguments		:selenium,strTypeName,strTypeVal,blnSelect
	'Returns		:None
	'Date	 		:29-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String verifyStatTypeAndResInEditView(Selenium selenium,
			String strTypeName, String strTypeVal, boolean blnSelect)
			throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnSelect) {
				if (selenium.isChecked("css=input[name='" + strTypeName
						+ "'][value='" + strTypeVal + "']")) {
					log4j.info(strTypeName + " is selected");
				} else {
					log4j.info(strTypeName + " is NOT selected");
					strReason = strTypeName + " is NOT selected";
				}
			} else {
				if (selenium.isChecked("css=input[name='" + strTypeName
						+ "'][value='" + strTypeVal + "']") == false) {
					log4j.info(strTypeName + " is not selected");
				} else {
					log4j.info(strTypeName + " is selected");
					strReason = strTypeName + " is selected";
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyStatTypeAndResInEditView "
					+ e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Delete a View and verify
	'Precondition	:None
	'Arguments		:selenium,strViewName
	'Returns		:strReason
	'Date	 		:22-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String deleteView(Selenium selenium, String strViewName)
			throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			// click on Copy
			selenium.click("//table[@id='listViews']/tbody/tr/" + "td[text()='"
					+ strViewName + "']/"
					+ "preceding-sibling::td/a[text()='Delete']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Delete View Confirmation", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Delete View Confirmation  page is  displayed ");

				try {
					assertEquals("Confirm Delete", selenium
							.getText("//div/form/h1"));
					assertTrue(selenium
							.isTextPresent("If it is OK to delete this Region View, please click on the 'Delete' button below. Otherwise, click on the 'Cancel' button."));
					log4j
							.info("Message 'Confirm Delete' and If it is OK to delete this Region View, please click on the 'Delete' button below. Otherwise, click on the 'Cancel' button.' is displayed. ");
					// click on delete
					selenium.click(propElementDetails
							.getProperty("RegView.DeleteViewConfm.Delete"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Region Views List", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						assertFalse(selenium
								.isElementPresent("//table[@id='listViews']/tbody/tr/"
										+ "td[2][text()='" + strViewName + "']"));

						log4j
								.info("View "
										+ strViewName
										+ " is not displayed in 'Region Views List' screen");

					} catch (AssertionError Ae) {
						log4j
								.info("View "
										+ strViewName
										+ " is still displayed in 'Region Views List' screen");
						strReason = "View "
								+ strViewName
								+ " is still displayed in 'Region Views List' screen"
								+ Ae;
					}

				} catch (AssertionError Ae) {
					log4j
							.info("Message 'Confirm Delete' and If it is OK to delete this Region View, please click on the 'Delete' button below. Otherwise, click on the 'Cancel' button.' is NOT displayed.");
					strReason = "Message 'Confirm Delete' and If it is OK to delete this Region View, please click on the 'Delete' button below. Otherwise, click on the 'Cancel' button.' is NOT displayed.";
				}

			} catch (AssertionError Ae) {
				log4j.info(" Delete View Confirmation  page is NOT displayed "
						+ Ae);
				strReason = "Delete View Confirmation  page is NOT displayed "
						+ Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function deleteView " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	 'Description :Save the page in Edit Resource Detail View Sections and navigate to 
	     View Resource Detail
	 'Precondition :None
	 'Arguments  :selenium,strViewName     
	 'Returns  :None
	 'Date    :23-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/

	public String saveAndNavToViewResDetail(Selenium selenium) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			// save
			selenium.click(propElementDetails.getProperty("View.CreateNewView.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason = "View Resource Detail screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function saveAndNavToViewResDetail " + e;
		}
		return strReason;
	}

		/***************************************************************
	 'Description :Save the page in update status and navigate to 
	     View screen
	 'Precondition :None
	 'Arguments  :selenium,strViewName     
	 'Returns  :None
	 'Date    :11-09-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String saveAndNavToViewScreen(Selenium selenium, String strViewName)
			throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			// save
			selenium.click(propElementDetails.getProperty("View.CreateNewView.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals(strViewName, selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info(strViewName + " screen is displayed");

			} catch (AssertionError ae) {
				log4j.info(strViewName + " screen is NOT displayed");
				strReason = strViewName + " screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function saveAndNavToViewScreen " + e;
		}
		return strReason;
	}

	 /***************************************************************
	 'Description :Check Section name in Edit Resource Detail View Section
	 'Precondition :None
	 'Arguments  :selenium,strSection,strStatType     
	 'Returns  :None
	 'Date    :23-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String checkSectionInEditResDetViewSec(Selenium selenium,
			String strSection, String strStatType) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertTrue(selenium
						.isElementPresent("css=#groups > li > div > span:contains('"
								+ strSection + "')"));
				log4j.info("Section " + strSection + " is displayed");

				selenium.click("css=#groups > li > div > span:contains('"
						+ strSection + "')");

				try {
					assertTrue(selenium
							.isElementPresent("//ul[starts-with(@class,'g_')]/li/div[text()='"
									+ strStatType + "']"));
					assertTrue(selenium
							.isVisible("//ul[starts-with(@class,'g_')]/li/div[text()='"
									+ strStatType + "']"));
					log4j.info("Status Type " + strStatType + " is displayed");
				} catch (AssertionError ae) {
					log4j.info("Status Type " + strStatType
							+ " is NOT displayed");
					strReason = "Status Type " + strStatType
							+ " is NOT displayed";
				}
			} catch (AssertionError ae) {
				log4j.info("Section " + strSection + " is NOT displayed");
				strReason = "Section " + strSection + " is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkSectionInEditResDetViewSec "
					+ e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate to Update status screen
	'Precondition	:None
	'Arguments		:selenium,strNavElement
	'Returns		:strReason
	'Date	 		:28-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String navToUpdateStatus(Selenium selenium, String strNavElement)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			// click update status image
			selenium.click(strNavElement);
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("Update Status screen is NOT displayed");
				strReason = "Update Status screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToUpdateStatus " + e.toString();
		}
		return strReason;
	}
	

	/***************************************************************
	'Description	:Return to user view screen from update status
					screen by clicking on Return to View.
	'Precondition	:None
	'Arguments		:selenium,strViewName
	'Returns		:strReason
	'Date	 		:28-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String returnUserView(Selenium selenium,String strViewName)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {		
				
			// click on Return to View
			selenium.click(propElementDetails
					.getProperty("View.ReturnToView"));
			selenium.waitForPageToLoad(gstrTimeOut);
				
			try {
				assertEquals(strViewName, selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("User is returned to "+strViewName + " page");

			} catch (AssertionError Ae) {
				log4j.info("User is NOT returned to "+strViewName + " page");
				strReason = "User is NOT returned to "+strViewName + " page";
			}
				  
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function returnUserView "
					+ e.toString();
		}
		return strReason;
	}
	 /***************************************************************
	  'Description :check status type in user view screen
	  'Precondition :None
	  'Arguments  :selenium, statustype
	  'Returns  :strReason
	  'Date    :28-May-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String checkStatusTypeInUserView(Selenium selenium,
			String[] strStatType) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			int intCol = 0;
			for (intCol = 0; intCol < strStatType.length; intCol++) {
				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/table/thead"
									+ "/tr/th["
									+ (intCol + 3)
									+ "]/a[text()='"
									+ strStatType[intCol] + "']"));
					log4j.info("Status Type " + strStatType[intCol]
							+ " is displayed in the User view screen");
				} catch (AssertionError ae) {
					log4j.info("Status Type " + strStatType[intCol]
							+ " is NOT displayed in the User view screen");
					strReason = "Status Type " + strStatType[intCol]
							+ " is NOT displayed in the User view screen";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkStatustype in user view screen "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	  'Description :check status type, Resource Type and Resource in user view screen
	  'Precondition :None
	  'Arguments  :selenium, strResType,strResource,statustype
	  'Returns  :strReason
	  'Date    :2-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	
	public String checkAllSTRTAndRSInUserView(Selenium selenium,
			String strResType, String strResource[], String[] strStatType)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			try {
				assertTrue(selenium
						.isElementPresent("//table/thead/tr/th[2]/a[text()='"
								+ strResType + "']"));
				log4j.info("Resource type '" + strResType
						+ "' is displayed in the User view Detail screen '");
				int intRow = 0;
				for (intRow = 0; intRow < strResource.length; intRow++) {
					try {

						assertTrue(selenium
								.isElementPresent("//table/thead/tr/th[2]/a[text()='"
										+ strResType
										+ "']/ancestor::thead/following-sibling::tbody/tr["
										+ (intRow + 1)
										+ "]/td[2]/a[text()='"
										+ strResource[intRow] + "']"));
						log4j
								.info("Resource "
										+ strResource[intRow]
										+ " is displayed in the User view Detail screen under resource type "
										+ strResType);
					} catch (AssertionError ae) {
						log4j
								.info("Resource "
										+ strResource[intRow]
										+ " is NOT displayed in the User view Detail screen under resource type "
										+ strResType);
						strReason = strReason
								+ " Resource "
								+ strResource[intRow]
								+ " is NOT displayed in the User view Detail screen under resource type "
								+ strResType;
					}
				}

				int intCol = 0;
				for (intCol = 0; intCol < strStatType.length; intCol++) {
					try {

						assertTrue(selenium.isElementPresent("//table/thead"
								+ "/tr/th[2]/a[text()='" + strResType
								+ "']/ancestor::tr/th[" + (intCol + 3)
								+ "]/a[text()='" + strStatType[intCol] + "']"));
						log4j
								.info("Status Type "
										+ strStatType[intCol]
										+ " is displayed in the User view Detail screen under resource type "
										+ strResType);
					} catch (AssertionError ae) {
						log4j
								.info("Status Type is "
										+ strStatType[intCol]
										+ " is NOT displayed in the User view Detail screen under resource type "
										+ strResType);
						strReason = strReason
								+ " Status Type  "
								+ strStatType[intCol]
								+ " is NOT displayed in the User view Detail screen under resource type "
								+ strResType;
					}
				}

			} catch (AssertionError ae) {
				log4j
						.info("Resource type '"
								+ strResType
								+ "' is NOT displayed in the User view Detail screen '");
				strReason = "Resource type '" + strResType
						+ "' is NOT displayed in the User view Detail screen '";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkAllSTRTAndRSInUserView "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	   'Description :check update status prompt displayed for status type
	   'Precondition :None
	   'Arguments  :selenium, strResource,strStatType
	   'Returns  :strReason
	   'Date    :11-June-2012
	   'Author   :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String cancelAndcheckUpdateStatPrompt(Selenium selenium,
			String strResource, String strStatType) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			// cancel
			selenium.click(propElementDetails
					.getProperty("View.CreateNewView.Cancel"));
			try {

				// View menu
				selenium.mouseOver(propElementDetails
						.getProperty("View.ViewLink"));
				// click on Map link
				selenium.click(propElementDetails
						.getProperty("View.ViewLink.Map"));
				// selenium.waitForPageToLoad(gstrTimeOut);
				try {

					assertEquals("Regional Map View", (propElementDetails
							.getProperty("Header.Text")));
					log4j.info("Regional Map View screen is displayed");
				} catch (AssertionError Ae) {
					log4j.info("Regional Map View screen is NOT displayed");
					strReason = "Regional Map View screen is NOT displayed";
				}

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("UpdateStatus.OverdueTitle")));

				assertEquals(strResource + " \nStatus is Overdue", selenium
						.getText(propElementDetails
								.getProperty("UpdateStatus.OverdueTitle")));

				assertTrue(selenium
						.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"
								+ strStatType + "')]"));
				assertTrue(selenium
						.isElementPresent("//div[@class='statusTitle clearFix']/label/span[text()='(Required/Overdue)']"));
				log4j.info("'Update Status' prompt for " + strStatType
						+ " is redisplayed.");
				log4j
						.info("User is not allowed to use the application until he updates the status of ST."
								+ strStatType + "");
			} catch (AssertionError Ae) {
				strReason = "'Update Status' prompt for " + strStatType
						+ " is NOT displayed.";
				log4j.info("'Update Status' prompt for " + strStatType
						+ " is NOT displayed.");
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkUpdateStatPrompt "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	    'Description :check RT,ST and RS in View cutom table
	    'Precondition :None
	    'Arguments  :selenium, statustype
	    'Returns  :strReason
	    'Date    :6-June-2012
	    'Author   :QSG
	    '---------------------------------------------------------------
	    'Modified Date                            Modified By
	    '<Date>                                     <Name>
	    ***************************************************************/
	
	
	public String checkResTypeRSAndSTInViewCustTabl(Selenium selenium,
			String strRT, String strRS, String[] strStatTypeArr)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertEquals("Custom View - Table", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Custom View - Table Page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]/"
									+ "thead/tr/th[2][text()='" + strRT + "']"));
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]"
									+ "/thead/tr/th[2][text()='"
									+ strRT
									+ "']/"
									+ "ancestor::table/tbody/tr/td[2]/a[text()='"
									+ strRS + "']"));

					for (int i = 0; i < strStatTypeArr.length; i++) {
						assertEquals(strStatTypeArr[i], selenium
								.getText("//table[starts-with(@id,'rgt')]"
										+ "/thead/tr/th[2][text()='" + strRT
										+ "']" + "/ancestor::tr/th[" + i
										+ "+3]"));
						log4j.info(strStatTypeArr[i]
								+ "is displayed for Resource" + strRS + "");

						log4j
								.info("Resource '"
										+ strRS
										+ "' is displayed on the 'Cusotm View table' screen under "
										+ strRT
										+ " along with all the status types.");
					}

				} catch (AssertionError ae) {
					log4j
							.info("Resource '"
									+ strRS
									+ "' is NOT displayed on the 'Cusotm View table' screen under "
									+ strRT
									+ " along with all the status types.");
					strReason = "Resource '"
							+ strRS
							+ "' is NOT displayed on the 'Cusotm View table' screen under "
							+ strRT + " along with all the status types.";
				}

			} catch (AssertionError Ae) {
				strReason = " Custom View - TablePage is NOT displayed" + Ae;
				log4j.info("Custom View - Table Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function check ST,RS and RT in view custom view table "
					+ e.toString();
		}
		return strReason;
	}

	/****************************************************************
	  'Description :verify error message in update status
	  'Precondition :None
	  'Arguments  :selenium, strErrorMsg
	  'Returns  :strReason
	  'Date    :28-May-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String VerifyErrorMsg(Selenium selenium, String strErrorMsg)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertEquals(strErrorMsg, selenium.getText("css=h2"));
				log4j.info("Update Status screen is displayed with a message'"
						+ strErrorMsg + "'");
			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed with a message'"
						+ strErrorMsg + "'");
				strReason = "Update Status screen is NOT displayed with a message'"
						+ strErrorMsg + "'";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "VerifyErrorMsg  function Failed " + e.toString();
		}
		return strReason;
	}
	
	
	/***************************************************************
	'Description	:Check status type in Update Status screen
	'Precondition	:None
	'Arguments		:selenium,strStatType
	'Returns		:strReason
	'Date	 		:28-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String checkStatTypeInUpdateStat(Selenium selenium,
			String strStatType) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium
						.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"
								+ strStatType + "')]"));
				log4j.info("Status Type " + strStatType
						+ "is displayed in Update Status");

			} catch (AssertionError ae) {
				log4j.info("Status Type " + strStatType
						+ "is NOT displayed in Update Status");
				strReason = "Status Type " + strStatType
						+ "is NOT displayed in Update Status";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkStatTypeInUpdateStat "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	  'Description :navigate to Update status screen
	  'Precondition :None
	  'Arguments  :selenium
	  'Returns  :strReason
	  'Date    :31-May-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	
	
	public String navToShowAllStatus(Selenium selenium) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			// click on show all status

			selenium.click(propElementDetails.getProperty("ShowAllStatuslink"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try {

					assertEquals("Update Status", selenium
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
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("Update Status screen is NOT displayed");
				strReason = "Update Status screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToUpdateStatus " + e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	  'Description :Navigate Edit View by clicking Edit on particular view
	  'Precondition :None
	  'Arguments  :selenium,strViewName
	  'Returns  :strReason
	  'Date    :04-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/
	
	
	
	public String navToEditView(Selenium selenium,String strViewName) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			//click on Edit
			selenium.click("//table[@id='listViews']/tbody/tr/"
					+ "td[text()='" + strViewName + "']/"
					+ "preceding-sibling::td/a[text()='Edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Edit View", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info(" Edit View page is  displayed ");				
			
			} catch (AssertionError Ae) {
				log4j.info(" Edit Viewpage is NOT displayed " + Ae);
				strReason = "Edit View page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToEditView " + e.toString();
		}
		return strReason;
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
	
	
	
	public String selAndDeselStatTypeInEditView(Selenium selenium,
			String strStatusType, boolean blnCheck) throws Exception {

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
			
			if (blnCheck) {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatusType + "']") == false) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strStatusType + "']");

				}
			} else {
				if (selenium.isChecked("css=input[name='statusTypeID'][value='"
						+ strStatusType + "']")) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strStatusType + "']");

				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function selAndDeselStatTypeInEditView "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Update status of status type
	'Precondition	:None
	'Arguments		:selenium,strStatType,strStatus,strComent
	'Returns		:strReason
	'Date	 		:5-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String updateStatusWithoutStatus(Selenium selenium,
			String strResource, String strStatType, String strStatTypeVal,
			String strUpdValue, String strComent) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click update status image
			selenium.click("//div[@id='mainContainer']/div/table/tbody/"
					+ "tr/td[2]/a[text()='" + strResource
					+ "']/ancestor::tr/td[1]/a/img");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"
									+ strStatType + "')]"));
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
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"
									+ strStatType + "')]"));
					log4j.info("Status Type " + strStatType
							+ " is displayed in Update Status screen");
					if (selenium
							.isChecked("//div[@class='statusTitle clearFix']/label[contains(text(),'"
									+ strStatType
									+ "')]/preceding-sibling::input") == false) {
						// select status type
						selenium
								.click("//div[@class='statusTitle clearFix']/label[contains(text(),'"
										+ strStatType
										+ "')]/preceding-sibling::input");
					}
					// Enter the value
					selenium.type("css=input[name='status_value_"
							+ strStatTypeVal + "']", strUpdValue);
					// save
					selenium.click(propElementDetails
							.getProperty("View.UpdateStatus.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					intCnt = 0;
					do {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/div/table/thead/tr/th[3]/a[text()='"
											+ strStatType + "']"));
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
								.isElementPresent("//div[@id='mainContainer']/div/table/thead/tr/th[3]/a[text()='"
										+ strStatType + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/div/table/tbody/tr/td[3][text()='"
										+ strUpdValue + "']"));
						log4j.info("Updated status value " + strUpdValue
								+ " is displayed on view");
					} catch (AssertionError ae) {
						log4j.info("Updated status value " + strUpdValue
								+ " is NOT displayed on view");
						strReason = "Updated status value " + strUpdValue
								+ " is NOT displayed on view";
					}

				} catch (AssertionError ae) {
					log4j.info("Status Type " + strStatType
							+ " is displayed in Update Status screen");
					strReason = "Status Type " + strStatType
							+ " is displayed in Update Status screen";
				}
			} catch (AssertionError ae) {
				log4j.info("Update Status screen is NOT displayed");
				strReason = "Update Status screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function updateStatusWithoutStatus "
					+ e.toString();
		}
		return strReason;
	}
	
	/*****************************************************************
	'Description	: Check Custom link is present when mouse
					hover on View
	'Precondition	:None
	'Arguments		:selenium,blnAvailable
	'Returns		:String
	'Date	 		:06-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/
	
	
	public String checkCustomOptInViewDropDown(Selenium selenium,
			boolean blnAvailable) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));

			if (blnAvailable) {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("View.CustomLink")));
					log4j
							.info("'Custom' view is available in the View drop down.");

				} catch (AssertionError Ae) {
					strErrorMsg = "'Custom' view is NOT available in the View drop down.";
					log4j
							.info("'Custom' view is NOT available in the View drop down.");
				}

			} else {
				try {
					assertFalse(selenium.isElementPresent(propElementDetails
							.getProperty("View.CustomLink")));
					log4j
							.info("'Custom' view is not available in the View drop down.");

				} catch (AssertionError Ae) {
					strErrorMsg = "'Custom' view is still available in the View drop down.";
					log4j
							.info("'Custom' view is still available in the View drop down.");
				}
			}
		} catch (Exception e) {
			log4j.info("checkCustomOptInViewDropDown function failed" + e);
			strErrorMsg = "checkCustomOptInViewDropDown function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************************
	'Description	: Check Custom link is present when clicked on
					View
	'Precondition	:None
	'Arguments		:selenium,blnAvailable
	'Returns		:String
	'Date	 		:06-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/
	
	
	public String checkCustomOptInViewMenu(Selenium selenium,
			boolean blnAvailable) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("View.ViewLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			if (blnAvailable) {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("View.CustomLink")));
					log4j
							.info("'Custom' view is available in 'View Menu' screen.");

				} catch (AssertionError Ae) {
					strErrorMsg = "'Custom' view is NOT available in 'View Menu' screen.";
					log4j
							.info("'Custom' view is NOT available in 'View Menu' screen.");
				}

			} else {
				try {
					assertFalse(selenium.isElementPresent(propElementDetails
							.getProperty("View.CustomLink")));
					log4j
							.info("'Custom' view is not available in 'View Menu' screen.");

				} catch (AssertionError Ae) {
					strErrorMsg = "'Custom' view is still available in 'View Menu' screen.";
					log4j
							.info("'Custom' view is still available in 'View Menu' screen.");
				}
			}
		} catch (Exception e) {
			log4j.info("checkCustomOptInViewMenu function failed" + e);
			strErrorMsg = "checkCustomOptInViewMenu function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*****************************************************************
	'Description	: Check user view is present in View Menu page
	'Precondition	:None
	'Arguments		:selenium,strViewName,blnAvailable
	'Returns		:String
	'Date	 		:27-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/
	
	
	public String checkUsrViewInViewMenuPge(Selenium selenium,
			String strViewName, boolean blnAvailable) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("View.ViewLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			if (blnAvailable) {
				try {
					assertTrue(selenium
							.isElementPresent("//table[@summary='Menu Options']/tbody/tr/td/a[text()='"
									+ strViewName + "']"));
					log4j.info(strViewName
							+ " view is available in 'View Menu' screen.");

				} catch (AssertionError Ae) {
					strErrorMsg = strViewName
							+ " view is NOT available in 'View Menu' screen.";
					log4j.info(strViewName
							+ " view is NOT available in 'View Menu' screen.");
				}

			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//table[@summary='Menu Options']/tbody/tr/td/a[text()='"
									+ strViewName + "']"));
					log4j.info(strViewName
							+ " view is NOT available in 'View Menu' screen.");

				} catch (AssertionError Ae) {
					strErrorMsg = strViewName
							+ " view is available in 'View Menu' screen.";
					log4j.info(strViewName
							+ " view is available in 'View Menu' screen.");
				}
			}
		} catch (Exception e) {
			log4j.info("checkUsrViewInViewMenuPge function failed" + e);
			strErrorMsg = "checkUsrViewInViewMenuPge function failed" + e;
		}
		return strErrorMsg;
	}

	/*****************************************************************
	'Description	: Check user view is present in View Menu Dropdown
	'Precondition	:None
	'Arguments		:selenium,strViewName,blnAvailable
	'Returns		:String
	'Date	 		:27-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                              		<Name>
	*****************************************************************/
	
	public String checkUsrViewInViewMenuDrpDwn(Selenium selenium,String strViewName,boolean blnAvailable) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
				
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
								
			if(blnAvailable){
				try {
					assertTrue(selenium
							.isElementPresent("link=" + strViewName));
					log4j.info(strViewName+" view is available in 'View Menu' Dropdown.");
					
				} catch (AssertionError Ae) {
					strErrorMsg =strViewName+" view is NOT available in 'View Menu' Dropdown.";
					log4j.info(strViewName+" view is NOT available in 'View Menu' Dropdown.");
				}
					
			}else{
				try {
					assertFalse(selenium
							.isElementPresent("link=" + strViewName));
					log4j.info(strViewName+" view is not available in 'View Menu' Dropdown.");
					
				} catch (AssertionError Ae) {
					strErrorMsg =strViewName+" view is available in 'View Menu' Dropdown.";
					log4j.info(strViewName+" view is available in 'View Menu' Dropdown.");
				}
			}
		} catch (Exception e) {
			log4j.info("checkUsrViewInViewMenuDrpDwn function failed" + e);
			strErrorMsg = "checkUsrViewInViewMenuDrpDwn function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***************************************************************
    'Description :Fetch update status value in user view screeen
    'Precondition :None
    'Arguments  :selenium
    'Returns  :strReason
    'Date    :7-June-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String[] fetchUpdateStatusValue(Selenium selenium, String strRT,
			String strST) throws Exception {

		String[] strReason = {"",""};
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			String strUdateValue = selenium
					.getText("//table[starts-with(@id,'rtt')]"
							+ "/thead/tr/th[2]/a[text()='" + strRT
							+ "']/ancestor::tr" + "/th/a[text()='" + strST
							+ "']/ancestor::table/tbody/tr/td[3]");
			strReason[0] = strUdateValue;
		} catch (Exception e) {
			log4j.info(e);
			strReason[1] = "Failed in function fetch update status value "
					+ e.toString();
		}
		return strReason;
	}
	

	 /***************************************************************
   'Description :Verify update status page is displayed
   'Precondition :None
   'Arguments  :selenium, strST,strRT
   'Returns  :strReason
   'Date    :7-June-2012
   'Author   :QSG
   '---------------------------------------------------------------
   'Modified Date                            Modified By
   '<Date>                                     <Name>
   ***************************************************************/

	public String navUpdateStatusByStatusCell(Selenium selenium, String strRT,
			String strST) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			selenium.click("//table[starts-with(@id,'rtt')]"
					+ "/thead/tr/th[2]/a[text()='" + strRT + "']/ancestor::tr"
					+ "/th/a[text()='" + strST
					+ "']/ancestor::table/tbody/tr/td[3]");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status Page is  displayed");

				
				
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate Update Status By StatusCell "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
    'Description :Verify User Verificstion header is displayed
    'Precondition :None
    'Arguments  :selenium, strST,strRT
    'Returns  :strReason
    'Date    :7-June-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdateStatusPageForElmnts(Selenium selenium,
			boolean blnUserVerificstion, boolean blnInstructn) throws Exception {

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
				log4j.info("Update Status Page is  displayed");
				
				if (blnInstructn) {
					try {
						assertEquals(
								"Please note: You must enter your name and "
										+ "password below, when changing this status",
								selenium
										.getText(propElementDetails
												.getProperty("UpdateStatus.IndicatesReqText")));

						log4j
								.info("An instruction 'Please note: You must enter your "
										+ "name and password below, when changing this status'"
										+ " is displayed below 'NST' ");

					} catch (AssertionError Ae) {
						strReason = "An instruction 'Please note: You must enter "
								+ "your name and password below, when changing"
								+ " this status' is NOT displayed below 'NST' ";

						log4j.info("An instruction 'Please note: You must enter "
								+ "your name and password below, when changing"
								+ " this status' is NOT displayed below 'NST' ");

					}
				}

				
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("UpdateStatus.ShowAllStatusLink")));

					log4j.info("Show All Statuses is displayed");

					if (blnUserVerificstion) {

						try {

							assertEquals(
									selenium
											.getText(propElementDetails
													.getProperty("UpdateStatus.UserVerfiText")),
									"User Verification *required to complete edits");
							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("UpdateStatus.TraceName")));
							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("UpdateStatus.Password")));
							assertTrue(selenium
									.isElementPresent(propElementDetails
											.getProperty("UpdateStatus.Save")));
							assertEquals(
									"Cancel",
									selenium
											.getValue(propElementDetails
													.getProperty("UpdateStatus.Cancel")));

							log4j
									.info("'User Verification' is displayed as"
											+ " the header below the link 'Show "
											+ "All Statuses' along with the instruction "
											+ "'*required to complete edits' corresponding"
											+ " to it.Fields 'Your Name' and 'Password' are"
											+ " available with 'Save' and 'Cancel' buttons. ");

						} catch (AssertionError Ae) {
							strReason = "'User Verification' is NOT displayed as"
									+ " the header below the link 'Show "
									+ "All Statuses' along with the instruction "
									+ "'*required to complete edits' corresponding"
									+ " to it.Fields 'Your Name' and 'Password' are"
									+ " available with 'Save' and 'Cancel' buttons.  ";

							log4j
									.info("'User Verification' is NOT displayed as"
											+ " the header below the link 'Show "
											+ "All Statuses' along with the instruction "
											+ "'*required to complete edits' corresponding"
											+ " to it.Fields 'Your Name' and 'Password' are"
											+ " available with 'Save' and 'Cancel' buttons.  ");

						}
						
					}

				} catch (AssertionError Ae) {
					strReason = "Show All Statuses is NOT displayed";
					log4j.info("Show All Statuses is NOT displayed");

				}
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate Update Status By StatusCell "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in user view screen
    'Precondition :None
    'Arguments  :selenium,strUpdateValue,strRT,strST,strViewName
    'Returns  :strReason
    'Date    :7-June-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String savAndVerifyUpdateST(Selenium selenium, String strViewName,
			String strRT, String strST, String strUpdateValue) throws Exception {

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
				log4j.info("Update Status Page is  displayed");

				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals(strViewName, selenium.getText(propElementDetails
							.getProperty("Header.Text")));

					log4j.info(strViewName + " page is displayed");
					
					try {
						assertEquals(selenium
								.getText("//table[starts-with(@id,'rtt')]"
										+ "/thead/tr/th[2]/a[text()='" + strRT
										+ "']/ancestor::tr" + "/th/a[text()='"
										+ strST
										+ "']/ancestor::table/tbody/tr/td[3]"),
								strUpdateValue);

						log4j
								.info("The status value "
										+ strUpdateValue
										+ " is updated and displayed on the view screen. ");
					} catch (AssertionError Ae) {
						log4j
								.info("The status value "
										+ strUpdateValue
										+ " is NOT updated and displayed on the view screen. ");
						strReason = "The status value "
								+ strUpdateValue
								+ " is NOT updated and displayed on the view screen. ";

					}

				} catch (AssertionError Ae) {
					log4j.info(strViewName + " page is NOT displayed");
					strReason = strViewName + " page is NOT displayed";
				}
				
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate Update Status By StatusCell "
					+ e.toString();
		}
		return strReason;
	}
	/*******************************************************************************************
	' Description: Fetch the last Updated Time Values for the status type.
	' Precondition: N/A 
	' Arguments: selenium, strResrcTypName,statTypeName
	' Returns: String[] 
	' Date: 11-09-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	

	public String[] getLastUpdTimeValue(Selenium selenium,
			String strResrcTypName, String statTypeName) throws Exception {
		String[] lStrReason = new String[5];
		lStrReason[4] = "";
		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			String strStatUpdTime = selenium
					.getText("//table[starts-with(@id,'rtt')]/thead/tr/th/a"
							+ "[text()='"
							+ strResrcTypName
							+ "']/ancestor::thead/following-sibling::tbody/tr/td[3]/a"
							+ "[text()='" + statTypeName
							+ "']/ancestor::tr/td[last()]");

			String[] strArStatUpdTime = strStatUpdTime.split(" ");
			String[] strArStTime = strArStatUpdTime[2].split(":");

			lStrReason[0] = strArStatUpdTime[0];
			lStrReason[1] = strArStatUpdTime[1];
			lStrReason[2] = strArStTime[0];
			lStrReason[3] = strArStTime[1];

		} catch (Exception e) {
			log4j.info(e);
			lStrReason[4] = lStrReason[4] + "; " + e.toString();
		}

		return lStrReason;
	}
	
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in user view screen
    'Precondition :None
    'Arguments  :selenium,strUpdateValue,strRT,strST,strViewName
    'Returns  :strReason
    'Date    :11-June-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdateST(Selenium selenium, 
			String strRT, String strST, String strUpdateValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals(selenium
						.getText("//table[starts-with(@id,'rtt')]"
								+ "/thead/tr/th[2]/a[text()='" + strRT
								+ "']/ancestor::tr" + "/th/a[text()='"
								+ strST
								+ "']/ancestor::table/tbody/tr/td[3]"),
						strUpdateValue);

				log4j
						.info("The status value "
								+ strUpdateValue
								+ " is updated and displayed on the view screen. ");
			} catch (AssertionError Ae) {
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " is NOT updated and displayed on the view screen. ");
				strReason = "The status value "
						+ strUpdateValue
						+ " is NOT updated and displayed on the view screen. ";

			}	

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdateST "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in user view screen with comments
    'Precondition :None
    'Arguments  :selenium,strResource,strST,strUpdateValue,strComment
    'Returns  :strReason
    'Date    :06-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	
	public String verifyUpdateSTValWithCommentsInViews(Selenium selenium,
			String strResource, String strST, String strUpdateValue,
			String strComment) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]"
						+ "/thead/tr/th/a[text()='" + strST
						+ "']/ancestor::table/tbody/tr/td[3]"), strUpdateValue);
				
				log4j
				.info("The status value "
						+ strUpdateValue
						+ " and comments are updated and displayed on the view screen. ");
				
				assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]"
						+ "/tbody/tr/td/a[text()='" + strResource
						+ "']/ancestor::tr/td[4]"), strComment);
				log4j
						.info("'SR' and provided comments are displayed under the 'Comments' section. ");
				
			} catch (AssertionError Ae) {
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " and comments are NOT updated and displayed on the view screen. ");
				strReason = "The status value "
						+ strUpdateValue
						+ " and comments are NOT updated and displayed on the view screen. ";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdateSTValWithCommentsInViews "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in user view screen with comments
    'Precondition :None
    'Arguments  :selenium,strResource,strST,strUpdateValue,strComment
    'Returns  :strReason
    'Date    :06-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/
	
	public String verifyUpdateSTValInViews(Selenium selenium, String strST,
			String strUpdateValue, String strPos) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			int intCnt = 0;
			do {
				try {

					assertEquals(selenium
							.getText("//table[starts-with(@id,'rtt')]"
									+ "/thead/tr/th/a[text()='" + strST
									+ "']/ancestor::table/tbody/tr/td["
									+ strPos + "]"), strUpdateValue);

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
				assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]"
						+ "/thead/tr/th/a[text()='" + strST
						+ "']/ancestor::table/tbody/tr/td[" + strPos + "]"),
						strUpdateValue);

				log4j.info("The status value " + strUpdateValue
						+ " is displayed for the status type " + strST);
			} catch (AssertionError Ae) {
				log4j.info("The status value " + strUpdateValue
						+ " is NOT displayed for the status type " + strST);
				strReason = "The status value " + strUpdateValue
						+ " is NOT displayed for the status type " + strST;

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdateSTValInViews "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in custom view table
    'Precondition :None
    'Arguments  :selenium,strST,strUpdateValue
    'Returns  :strReason
    'Date    :12-Oct-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdateSTValInCustomViewTabl(Selenium selenium,
			String strST, String strUpdateValue, String strPos)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(selenium.getText("//table[starts-with(@id,'rgt')]"
						+ "/thead/tr/th/a[text()='" + strST
						+ "']/ancestor::table/tbody/tr/td[" + strPos + "]"),
						strUpdateValue);

				log4j.info("The status value " + strUpdateValue
						+ " is displayed for the status type " + strST
						+ " in custom view table");
			} catch (AssertionError Ae) {
				log4j.info("The status value " + strUpdateValue
						+ " is NOT displayed for the status type " + strST
						+ " in custom view table");
				strReason = "The status value " + strUpdateValue
						+ " is NOT displayed for the status type " + strST
						+ " in custom view table";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdateSTValInCustomViewTabl "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in user view screen with comments
    'Precondition :None
    'Arguments  :selenium,strResource,strST,strUpdateValue,strComment,strLastUpdTime
    'Returns  :strReason
    'Date    :21-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdSTCommentsAndLastUpdTimeInViews(Selenium selenium,
			String strResource, String strST, String strUpdateValue,
			String strComment, String strLastUpdTime) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {

			try {

				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'rtt')]"
								+ "/tbody/tr/td/a[text()='" + strResource
								+ "']"));
				assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]"
						+ "/tbody/tr/td/a[text()='" + strST
						+ "']/ancestor::tr/td[4]"), strUpdateValue);
				assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]"
						+ "/tbody/tr/td/a[text()='" + strST
						+ "']/ancestor::tr/td[5]"), strComment);
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " and comments are updated and displayed on the view screen. ");
			} catch (AssertionError Ae) {
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " and comments are NOT updated and displayed on the view screen. ");
				strReason = "The status value "
						+ strUpdateValue
						+ " and comments are NOT updated and displayed on the view screen. ";

			}

			try {
				String s=selenium
				.getText("//table[starts-with(@id,'rtt')]"
						+ "/tbody/tr/td/a[text()='" + strST
						+ "']/ancestor::tr/td[last()]");
				log4j.info(s);
				assertEquals(strLastUpdTime, selenium
						.getText("//table[starts-with(@id,'rtt')]"
								+ "/tbody/tr/td/a[text()='" + strST
								+ "']/ancestor::tr/td[last()]"));

				log4j.info("Last update time is displayed as " + strLastUpdTime
						+ " in view table");
			} catch (AssertionError ae) {
				log4j.info("Last update time is NOT displayed as "
						+ strLastUpdTime + " in view table");
				strReason = strReason
						+ " Last update time is NOT displayed as "
						+ strLastUpdTime + " in view table";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdSTCommentsAndLastUpdTimeInViews "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in Event Status screen with comments
    'Precondition :None
    'Arguments  :selenium,strResource,strST,strUpdateValue,strComment
    'Returns  :strReason
    'Date    :06-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdateSTValWithCommentsInEveStat(Selenium selenium,
			String strResource, String strST, String strUpdateValue,
			String strComment) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]"
						+ "/thead/tr/th/a[text()='" + strST
						+ "']/ancestor::table/tbody/tr/td[3]"), strUpdateValue);
				assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]"
						+ "/tbody/tr/td/a[text()='" + strResource
						+ "']/ancestor::tr/td[last()-2]"), strComment);
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " and comments are updated and displayed on the Event Status screen. ");
			} catch (AssertionError Ae) {
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " and comments are NOT updated and displayed on the Event Status screen. ");
				strReason = "The status value "
						+ strUpdateValue
						+ " and comments are NOT updated and displayed on the Event Status screen. ";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdateSTValWithCommentsInEveStat "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in View resource detail screen with comments
    'Precondition :None
    'Arguments  :selenium,strSection,strStatType,strUpdtVal,strComment
    'Returns  :strReason
    'Date    :06-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdSTValWithCommentsInResDetail(Selenium selenium, 
			String strSection, String strStatType, String strUpdtVal,String strComment) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
		
			try {
				assertEquals(selenium
						.getText("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a"),
						strSection);
				log4j
				.info("Section "
						+ strSection
						+ " is displayed in View Resource Detail screen");

				try {
					assertEquals(
							selenium
									.getText("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
											+ strStatType
											+ "']/ancestor::tr/td[3]"),
							strUpdtVal);
					if (strComment.compareTo("") != 0) {
						assertEquals(
								selenium
										.getText("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
												+ strStatType
												+ "']/ancestor::tr/td[4]"),
								strComment);
					}

					log4j
							.info("The status value and comments are updated and displayed on" +
									" the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The status value and comments are NOT updated and displayed" +
									" on the view resource detail screen. ");
					strReason = "The status value and comments are NOT updated and displayed " +
							"on the view resource detail screen. ";

				}

			} catch (AssertionError Ae) {
				log4j
				.info("Section "
						+ strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section "
					+ strSection
					+ " is NOT displayed in View Resource Detail screen";

			}	

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdSTValWithCommentsInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in View resource detail screen with comments
    'Precondition :None
    'Arguments  :selenium,strSection,strStatType,strUpdtVal,strComment
    'Returns  :strReason
    'Date    :06-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdSTValInResDetail(Selenium selenium, 
			String strSection, String strStatType, String strUpdtVal,String strPos) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
		
			try {
				assertEquals(selenium
						.getText("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a"),
						strSection);
				log4j
				.info("Section "
						+ strSection
						+ " is displayed in View Resource Detail screen");

				try {
					assertEquals(
							selenium
									.getText("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
											+ strStatType
											+ "']/ancestor::tr/td["+strPos+"]"),
							strUpdtVal);
				
					log4j
							.info("The status value is updated and displayed on" +
									" the view resource detail screen. along with status type");
				} catch (AssertionError Ae) {
					log4j
							.info("The status value is NOT updated and displayed on" +
									" the view resource detail screen. along with status type");
					strReason = "The status value is NOT updated and displayed on" +
					" the view resource detail screen. along with status type";

				}

			} catch (AssertionError Ae) {
				log4j
				.info("Section "
						+ strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section "
					+ strSection
					+ " is NOT displayed in View Resource Detail screen";

			}	

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdSTValInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	 'Description :Verify Updated status value is displayed in user view screen
	 'Precondition :None
	 'Arguments  :selenium,strUpdateValue,strRT,strST,strViewName
	 'Returns  :strReason
	 'Date    :11-June-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/

		public String verifyUpdateStatusType(Selenium selenium, String strResrcTypName, String statTypeName,
				String strUpdateValue) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				try {
					assertEquals(selenium.getText("//table[starts-with(@id,'rtt')]/thead/tr/th/a" +
							"[text()='"+strResrcTypName+"']/ancestor::thead/following-sibling::tbody/tr/td/a" +
							"[text()='"+statTypeName+"']/parent::td/following-sibling::td[1]"), strUpdateValue);

					log4j.info("The status value " + strUpdateValue
							+ " is updated and displayed on the view screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The status value" + strUpdateValue
							+ " is NOT updated and displayed on the view screen.");
					strReason = "The status value " + strUpdateValue
							+ " is NOT updated and displayed on the view screen";

				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function verifyUpdateST" + e.toString();
			}
			return strReason;
		}
		
	 /***************************************************************
    'Description :Verify Eror message in update status screen
    'Precondition :None
    'Arguments  :selenium, blnUserInfo,strUserName,strPassword
    'Returns  :strReason
    'Date    :7-June-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyErrorMsgUpdateStatus(Selenium selenium,String strErrorMeassage)
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
				log4j.info("Update Status Page is  displayed");
				
				try{
					assertEquals("The following error occurred on this page:",
							selenium.getText(propElementDetails
									.getProperty("UpdateStatus.ErrMsgTitle")));
					
			
					assertEquals(strErrorMeassage, selenium
							.getText(propElementDetails
									.getProperty("UpdateStatus.ErrMsg")));

					log4j.info("Error Message " + strErrorMeassage
							+ "  is displayed");

				} catch (AssertionError Ae) {

					strReason = "Error Message " + strErrorMeassage
							+ " is NOT displayed" + Ae;
					log4j.info("Error Message " + strErrorMeassage
							+ "  is NOT displayed" + Ae);

				}
				
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate Update Status By StatusCell "
					+ e.toString();
		}
		return strReason;
	}
	

	 /***************************************************************
   'Description :Filll and save update status value
   'Precondition :None
   'Arguments  :selenium, blnUserInfo,strUserName,strPassword
   'Returns  :strReason
   'Date    :7-June-2012
   'Author   :QSG
   '---------------------------------------------------------------
   'Modified Date                            Modified By
   '<Date>                                     <Name>
   ***************************************************************/

	public String fillAndSavUpdateStatus(Selenium selenium,
			String strUpdateValue, String strSTValue, boolean blnUserInfo,
			String strUserName, String strPassword) throws Exception {

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
				log4j.info("Update Status Page is  displayed");

				if(selenium.isChecked("css=#st_"+strSTValue)==false){
					selenium.click("css=#st_"+strSTValue);
				}
				selenium.type("css=input[name='status_value_" + strSTValue
						+ "']", strUpdateValue);

				if (blnUserInfo) {
					selenium
							.type(propElementDetails
									.getProperty("UpdateStatus.TraceName"),
									strUserName);
					selenium.type(propElementDetails
							.getProperty("UpdateStatus.Password"), strPassword);

				}

			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate Update Status By StatusCell "
					+ e.toString();
		}
		return strReason;
	}
	

	 /***************************************************************
  'Description :enter the fields in update status screen for NST with comments
  'Precondition :None
  'Arguments  :selenium, strUpdateValue,strSTValue,strComments
  'Returns  :strReason
  'Date    :6-sep-2012
  'Author   :QSG
  '---------------------------------------------------------------
  'Modified Date                            Modified By
  '<Date>                                     <Name>
  ***************************************************************/

	public String fillAndSaveUpdStatusNSTWithComments(Selenium selenium,
			String strUpdateValue, String strSTValue,String strComments,String strPageName) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if(selenium.isChecked("css=#st_"+strSTValue)==false){
				selenium.click("css=#st_"+strSTValue);
			}
			
			selenium.type("css=input[name='status_value_" + strSTValue
						+ "']", strUpdateValue);

			selenium.type("css=#comment_"+strSTValue, strComments);
			
			selenium.click(propElementDetails
					.getProperty("UpdateStatus.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals(strPageName, selenium
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
				assertEquals(strPageName, selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(strPageName+" Page is  displayed");
			} catch (AssertionError Ae) {
				strReason = strPageName+" Page is NOT displayed" + Ae;
				log4j.info( strPageName+" Page is NOT displayed" + Ae);
			}
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function fillAndSaveUpdStatusNSTWithComments "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	  'Description :enter the fields in update status screen for NST with comments
	  'Precondition :None
	  'Arguments  :selenium, strUpdateValue,strSTValue,strComments
	  'Returns  :strReason
	  'Date    :20-sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

		public String fillUpdStatusNSTWithComments(Selenium selenium,
				String strUpdateValue, String strSTValue,String strComments) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				if(selenium.isChecked("css=#st_"+strSTValue)==false){
					selenium.click("css=#st_"+strSTValue);
				}
				
				selenium.type("css=input[name='status_value_" + strSTValue
							+ "']", strUpdateValue);

				selenium.type("css=#comment_"+strSTValue, strComments);
				
							
			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function fillUpdStatusNSTWithComments "
						+ e.toString();
			}
			return strReason;
		}
	 /***************************************************************
	  'Description :enter the fields in update status screen for SST with comments
	  'Precondition :None
	  'Arguments  :selenium, strUpdateValue,strSTValue,strComments
	  'Returns  :strReason
	  'Date    :18-sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

		public String fillAndSaveUpdStatusSSTWithComments(Selenium selenium,
				String[] strUpdateValue, String strSTValue,String strComments,String strPageName) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
							
				if(selenium.isChecked("css=#st_"+strSTValue)==false){
					selenium.click("css=#st_"+strSTValue);
				}
				//Enter the values for fields
				selenium.type("css=#edBedsOccupied"+strSTValue, strUpdateValue[0]);
				selenium.type("css=#lobbyPatients"+strSTValue, strUpdateValue[1]);
				selenium.type("css=#ambulancePatients"+strSTValue, strUpdateValue[2]);
				selenium.type("css=#admitsGeneral"+strSTValue, strUpdateValue[3]);
				selenium.type("css=#admitsIcu"+strSTValue, strUpdateValue[4]);
				selenium.type("css=#oneOnOnePatients"+strSTValue, strUpdateValue[5]);
				selenium.type("css=#shortStaffRn"+strSTValue, strUpdateValue[6]);
				selenium.type("css=#edBedsAssigned"+strSTValue, strUpdateValue[7]);
				selenium.type("css=#lobbyCapacity"+strSTValue, strUpdateValue[8]);
				
				selenium.type("css=#comment_"+strSTValue, strComments);
				
				// click caluclate button
				selenium.click("//div[@id='st_"+strSTValue+"section']/div[5]/input");
				
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals(strPageName, selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info(strPageName+" Page is  displayed");
				} catch (AssertionError Ae) {
					strReason = strPageName+" Page is NOT displayed" + Ae;
					log4j.info( strPageName+" Page is NOT displayed" + Ae);
				}
				
			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function fillAndSaveUpdStatusSSTWithComments "
						+ e.toString();
			}
			return strReason;
		}
		 /***************************************************************
		  'Description :enter the fields in update status screen for SST with comments
		  'Precondition :None
		  'Arguments  :selenium, strUpdateValue,strSTValue,strComments
		  'Returns  :strReason
		  'Date    :20-sep-2012
		  'Author   :QSG
		  '---------------------------------------------------------------
		  'Modified Date                            Modified By
		  '<Date>                                     <Name>
		  ***************************************************************/

			public String fillUpdStatusSSTWithComments(Selenium selenium,
					String[] strUpdateValue, String strSTValue,String strComments) throws Exception {

				String strReason = "";
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();

				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				try {

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					
								
					if(selenium.isChecked("css=#st_"+strSTValue)==false){
						selenium.click("css=#st_"+strSTValue);
					}
					//Enter the values for fields
					selenium.type("css=#edBedsOccupied"+strSTValue, strUpdateValue[0]);
					selenium.type("css=#lobbyPatients"+strSTValue, strUpdateValue[1]);
					selenium.type("css=#ambulancePatients"+strSTValue, strUpdateValue[2]);
					selenium.type("css=#admitsGeneral"+strSTValue, strUpdateValue[3]);
					selenium.type("css=#admitsIcu"+strSTValue, strUpdateValue[4]);
					selenium.type("css=#oneOnOnePatients"+strSTValue, strUpdateValue[5]);
					selenium.type("css=#shortStaffRn"+strSTValue, strUpdateValue[6]);
					selenium.type("css=#edBedsAssigned"+strSTValue, strUpdateValue[7]);
					selenium.type("css=#lobbyCapacity"+strSTValue, strUpdateValue[8]);
					
					selenium.type("css=#comment_"+strSTValue, strComments);
					
				     // click caluclate button
					 selenium.click("//div[@id='st_"+strSTValue+"section']/div[5]/input");
									
				} catch (Exception e) {
					log4j.info(e);
					strReason = "Failed in function fillUpdStatusSSTWithComments "
							+ e.toString();
				}
				return strReason;
			}
	 /***************************************************************
	  'Description :enter the fields in update status screen for NST with comments
	  'Precondition :None
	  'Arguments  :selenium, strUpdateValue,strSTValue,strComments
	  'Returns  :strReason
	  'Date    :6-sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

		public String fillAndSaveUpdStatusMSTWithComments(Selenium selenium,
				String strUpdateValue, String strSTValue,String strComments,String strPageName) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				if(selenium.isChecked("css=#st_"+strSTValue)==false){
					selenium.click("css=#st_"+strSTValue);
				}
				
				selenium.click("css=input[value='" + strUpdateValue
						+ "'][name='status_value_" + strSTValue + "']");
							
				selenium.type("css=#comment_"+strSTValue, strComments);
				
				selenium.click(propElementDetails
						.getProperty("UpdateStatus.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					assertEquals(strPageName, selenium
							.getText(propElementDetails.getProperty("Header.Text")));
					log4j.info(strPageName+" Page is  displayed");
				} catch (AssertionError Ae) {
					strReason = strPageName+" Page is NOT displayed" + Ae;
					log4j.info( strPageName+" Page is NOT displayed" + Ae);
				}
				
			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function fillAndSaveUpdStatusNSTWithComments "
						+ e.toString();
			}
			return strReason;
		}
		
		 /***************************************************************
		  'Description :enter the fields in update status screen for NST with comments
		  'Precondition :None
		  'Arguments  :selenium, strUpdateValue,strSTValue,strComments
		  'Returns  :strReason
		  'Date    :20-sep-2012
		  'Author   :QSG
		  '---------------------------------------------------------------
		  'Modified Date                            Modified By
		  '<Date>                                     <Name>
		  ***************************************************************/

			public String fillUpdStatusMSTWithComments(Selenium selenium,
					String strUpdateValue, String strSTValue,String strComments) throws Exception {

				String strReason = "";
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();

				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				try {

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					
					if(selenium.isChecked("css=#st_"+strSTValue)==false){
						selenium.click("css=#st_"+strSTValue);
					}
					
					selenium.click("css=input[value='" + strUpdateValue
							+ "'][name='status_value_" + strSTValue + "']");
								
					selenium.type("css=#comment_"+strSTValue, strComments);
					
									
				} catch (Exception e) {
					log4j.info(e);
					strReason = "Failed in function fillUpdStatusMSTWithComments "
							+ e.toString();
				}
				return strReason;
			}
	 /***************************************************************
  'Description :Filll and save update status value
  'Precondition :None
  'Arguments  :selenium, blnUserInfo,strUserName,strPassword,strUpdatTxtValue
  'Returns  :strReason
  'Date    :7-June-2012
  'Author   :QSG
  '---------------------------------------------------------------
  'Modified Date                            Modified By
  '<Date>                                     <Name>
  ***************************************************************/

	public String fillAndSavUpdateStatusTST(Selenium selenium,
			String strUpdatTxtValue, String strSTValue, boolean blnUserInfo,
			String strUserName, String strPassword) throws Exception {

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
				log4j.info("Update Status Page is  displayed");

				if(selenium.isChecked("css=#st_"+strSTValue)==false){
					selenium.click("css=#st_"+strSTValue);
				}
				
				selenium.type("css=input[name='status_value_" + strSTValue
						+ "']", strUpdatTxtValue);

				if (blnUserInfo) {
					selenium
							.type(propElementDetails
									.getProperty("UpdateStatus.TraceName"),
									strUserName);
					selenium.type(propElementDetails
							.getProperty("UpdateStatus.Password"), strPassword);

				}

			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate Update Status By StatusCell "
					+ e.toString();
		}
		return strReason;
	}
	

	

	 /***************************************************************
  'Description :Filll and save update status value
  'Precondition :None
  'Arguments  :selenium, blnUserInfo,strUserName,strPassword
  'Returns  :strReason
  'Date    :7-June-2012
  'Author   :QSG
  '---------------------------------------------------------------
  'Modified Date                            Modified By
  '<Date>                                     <Name>
  ***************************************************************/

	public String fillAndSavUpdateStatusMST(Selenium selenium,
			String strStatusValue, String strSTValue, boolean blnUserInfo,
			String strUserName, String strPassword) throws Exception {

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
				log4j.info("Update Status Page is  displayed");

				if(selenium.isChecked("css=#st_"+strSTValue)==false){
					selenium.click("css=#st_"+strSTValue);
				}
				
				selenium.click("css=input[value='" + strStatusValue
						+ "'][name='status_value_" + strSTValue + "']");

				if (blnUserInfo) {
					selenium
							.type(propElementDetails
									.getProperty("UpdateStatus.TraceName"),
									strUserName);
					selenium.type(propElementDetails
							.getProperty("UpdateStatus.Password"), strPassword);

				}

			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate Update Status By StatusCell "
					+ e.toString();
		}
		strReason="";
		return strReason;
	}
	
	 /***************************************************************
	   'Description :Filll and save update status value for Saturation score
	   'Precondition :None
	   'Arguments  :selenium,strUpdateValue,strSTValue,
	   'Returns  :strReason
	   'Date    :11-June-2012
	   'Author   :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

		public String fillAndSavUpdateStatusSSTWithTrace(Selenium selenium,
				String strUpdateValue[], String strSTValue, boolean blnUserInfo,
				String strUserName, String strPassword) throws Exception {

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
					log4j.info("Update Status Page is  displayed");
					//Enter the values for fields
					selenium.type("css=#edBedsOccupied"+strSTValue, strUpdateValue[0]);
					selenium.type("css=#lobbyPatients"+strSTValue, strUpdateValue[1]);
					selenium.type("css=#ambulancePatients"+strSTValue, strUpdateValue[2]);
					selenium.type("css=#admitsGeneral"+strSTValue, strUpdateValue[3]);
					selenium.type("css=#admitsIcu"+strSTValue, strUpdateValue[4]);
					selenium.type("css=#oneOnOnePatients"+strSTValue, strUpdateValue[5]);
					selenium.type("css=#shortStaffRn"+strSTValue, strUpdateValue[6]);
					selenium.type("css=#edBedsAssigned"+strSTValue, strUpdateValue[7]);
					selenium.type("css=#lobbyCapacity"+strSTValue, strUpdateValue[8]);

					// click caluclate button
					selenium.click("//div[@id='st_"+strSTValue+"section']/div[5]/input");
					
					if (blnUserInfo) {
						selenium
								.type(propElementDetails
										.getProperty("UpdateStatus.TraceName"),
										strUserName);
						selenium.type(propElementDetails
								.getProperty("UpdateStatus.Password"), strPassword);

					}
					
				} catch (AssertionError Ae) {
					strReason = "Update Status Page is NOT displayed" + Ae;
					log4j.info("Update Status Page is NOT displayed" + Ae);
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function navigate fillAndSavUpdateStatusSST "
						+ e.toString();
			}
			return strReason;
		}
		
	 /***************************************************************
	   'Description :Filll and save update status value for Saturation score
	   'Precondition :None
	   'Arguments  :selenium,strUpdateValue,strSTValue,
	   'Returns  :strReason
	   'Date    :11-June-2012
	   'Author   :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

		public String fillAndSavUpdateStatusSST(Selenium selenium,
				String strUpdateValue[], String strSTValue) throws Exception {

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
					log4j.info("Update Status Page is  displayed");
					if(selenium.isChecked("css=#st_"+strSTValue)==false){
						selenium.click("css=#st_"+strSTValue);
					}
					//Enter the values for fields
					selenium.type("css=#edBedsOccupied"+strSTValue, strUpdateValue[0]);
					selenium.type("css=#lobbyPatients"+strSTValue, strUpdateValue[1]);
					selenium.type("css=#ambulancePatients"+strSTValue, strUpdateValue[2]);
					selenium.type("css=#admitsGeneral"+strSTValue, strUpdateValue[3]);
					selenium.type("css=#admitsIcu"+strSTValue, strUpdateValue[4]);
					selenium.type("css=#oneOnOnePatients"+strSTValue, strUpdateValue[5]);
					selenium.type("css=#shortStaffRn"+strSTValue, strUpdateValue[6]);
					selenium.type("css=#edBedsAssigned"+strSTValue, strUpdateValue[7]);
					selenium.type("css=#lobbyCapacity"+strSTValue, strUpdateValue[8]);

					// click caluclate button
					selenium.click("//div[@id='st_"+strSTValue+"section']/div[5]/input");
					
				} catch (AssertionError Ae) {
					strReason = "Update Status Page is NOT displayed" + Ae;
					log4j.info("Update Status Page is NOT displayed" + Ae);
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function navigate fillAndSavUpdateStatusSST "
						+ e.toString();
			}
			return strReason;
		}
			
 /***************************************************************
   'Description :Filll and save update status value for Saturation score
   'Precondition :None
   'Arguments  :selenium,strUpdateValue,strSTValue,
   'Returns  :strReason
   'Date    :11-June-2012
   'Author   :QSG
   '---------------------------------------------------------------
   'Modified Date                            Modified By
   '<Date>                                     <Name>
   ***************************************************************/

	public String fillAndSavUpdateSSTWithoutCalcButton(Selenium selenium,
			String strUpdateValue[], String strSTValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Update Status",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status Page is  displayed");
				if (selenium.isChecked("css=#st_" + strSTValue) == false) {
					selenium.click("css=#st_" + strSTValue);
				}
				// Enter the values for fields
				selenium.type("css=#edBedsOccupied" + strSTValue,
						strUpdateValue[0]);
				selenium.type("css=#lobbyPatients" + strSTValue,
						strUpdateValue[1]);
				selenium.type("css=#ambulancePatients" + strSTValue,
						strUpdateValue[2]);
				selenium.type("css=#admitsGeneral" + strSTValue,
						strUpdateValue[3]);
				selenium.type("css=#admitsIcu" + strSTValue, strUpdateValue[4]);
				selenium.type("css=#oneOnOnePatients" + strSTValue,
						strUpdateValue[5]);
				selenium.type("css=#shortStaffRn" + strSTValue,
						strUpdateValue[6]);
				selenium.type("css=#edBedsAssigned" + strSTValue,
						strUpdateValue[7]);
				selenium.type("css=#lobbyCapacity" + strSTValue,
						strUpdateValue[8]);
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate fillAndSavUpdateStatusSST "
					+ e.toString();
		}
		return strReason;
	}

	 /***************************************************************
	  'Description :check update status prompt displayed for status type
	  'Precondition :None
	  'Arguments  :selenium, strResource,strStatType
	  'Returns  :strReason
	  'Date    :11-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

		public String checkUpdateStatPrompt(Selenium selenium,
				String strResource,String strStatType) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("UpdateStatus.OverdueTitle")));
					assertEquals(strResource+" \nStatus is Overdue", selenium
							.getText(propElementDetails.getProperty("UpdateStatus.OverdueTitle")));
					 assertTrue(selenium.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"+strStatType+"')]"));
					 assertTrue(selenium.isChecked("//div[@class='statusTitle clearFix']/label[contains(text(),'"+strStatType+"')]/parent::div/input"));
					 assertTrue(selenium.isElementPresent("//div[@class='statusTitle clearFix']/label/span[text()='(Required/Overdue)']"));
					log4j.info("'Update Status' prompt for "+strStatType+" is displayed and it is expanded");
				
				} catch (AssertionError Ae) {
					strReason = "'Update Status' prompt for "+strStatType+" is NOT displayed or it is not expanded";
					log4j.info("'Update Status' prompt for "+strStatType+" is NOT displayed it is not expanded");
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function checkUpdateStatPrompt "
						+ e.toString();
			}
			return strReason;
		}
		
		 /***************************************************************
		    'Description :Verify Updated status value is displayed in user view screen
		    'Precondition :None
		    'Arguments  :selenium,strUpdateValue,strRT,strST,strViewName
		    'Returns  :strReason
		    'Date    :7-June-2012
		    'Author   :QSG
		    '---------------------------------------------------------------
		    'Modified Date                            Modified By
		    '<Date>                                     <Name>
		    ***************************************************************/

	public String navToUpdateStatusPrompt(Selenium selenium, String strViewName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// View Tab
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on View name link
			selenium.click("link=" + strViewName);
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Update Status prompt is displayed.");

			} catch (AssertionError Ae) {
				log4j.info("Update Status prompt is NOT displayed");
				strReason = "Update Status prompt page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToUserView " + e.toString();
		}
		return strReason;
	}

	 /***************************************************************
	    'Description :check resource type and resource in user view screen
	    'Precondition :None
	    'Arguments  :selenium, strUserView, strRT, strRS, strRTValue
	    'Returns  :strReason
	    'Date    :28-May-2012
	    'Author   :QSG
	    '---------------------------------------------------------------
	    'Modified Date                            Modified By
	    '<Date>                                     <Name>
	    ***************************************************************/

	public String checkResTypeAndResourceInUserView(Selenium selenium,
			String strUserView, String strRT, String[] strRS, String strRTValue)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertEquals(strUserView, selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info(strUserView + " Page is  displayed");

				try {

					assertTrue(selenium.isElementPresent("//table[@id='rtt_"
							+ strRTValue + "']/thead/tr/th/a[text()='" + strRT
							+ "']"));
					log4j.info(strRT + " Page is  displayed");

					for (String str : strRS) {
						try {
							assertTrue(selenium
									.isElementPresent("//table[@id='rtt_"
											+ strRTValue + "']"
											+ "/tbody/tr/td/a[text()='" + str
											+ "']"));

							log4j.info(str + "  is displayed");

						} catch (AssertionError Ae) {
							strReason = str + "  is NOT displayed";
							log4j.info(str + "  is NOT displayed");
						}
					}

				} catch (AssertionError Ae) {
					strReason = strRT + "  is NOT displayed" + Ae;
					log4j.info(strRT + "  is NOT displayed" + Ae);
				}

			} catch (AssertionError Ae) {
				strReason = strUserView + " Page is NOT displayed" + Ae;
				log4j.info(strUserView + " Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("AutoRT513  is NOT displayed");
			strReason = "Failed in function check restype and resource in user view screen "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	  'Description :Verify last updated time in View
	  'Precondition :None
	  'Arguments  :selenium, strUpdTime,strResource
	  'Returns  :strReason
	  'Date    :14-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String verifyLastUpdTimeInView(Selenium selenium,String strUpdTime,String strResource,String strFulUsrName,boolean blnOverDue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {	
			
			if(blnOverDue){
				try{
					assertTrue(selenium.getText("//table/tbody/tr/td/a[text()='"+strResource+"']/ancestor::tr/td[last()][@class='overdue']").contains(strUpdTime));
					
					log4j.info("Last update time is displayed as "+strUpdTime+" in view table as red");
				} catch (AssertionError ae) {
					log4j.info("Last update time is NOT displayed as "+strUpdTime+" in view table as red");
					strReason = "Last update time is NOT displayed as "+strUpdTime+" in view table as red";
				}
				String strLastUpdTime=selenium.getText("//table/tbody/tr/td/a[text()='"+strResource+"']/ancestor::tr/td[last()]");
				selenium.mouseOver("//table/tbody/tr/td/a[text()='"+strResource+"']/ancestor::tr/td[3]");
				
				try{
					assertEquals("Last Update: "+strLastUpdTime,selenium.getText("//div[@id='theToolTip']/h1[@class='overdue']"));
					log4j.info("Last update time is displayed as "+strUpdTime+" in tool tip and displayed as red");
				} catch (AssertionError ae) {
					log4j.info("Last update time is NOT displayed as "+strUpdTime+" in tool tip and displayed as red");
					strReason = strReason+" Last update time is NOT displayed as "+strUpdTime+" in tool tip and displayed as red";
				}				
			
			}else{
				
				try{
					assertEquals(strUpdTime,selenium.getText("//table/tbody/tr/td/a[text()='"+strResource+"']/ancestor::tr/td[last()]"));
					
					log4j.info("Last update time is displayed as "+strUpdTime+" in view table");
				} catch (AssertionError ae) {
					log4j.info("Last update time is NOT displayed as "+strUpdTime+" in view table");
					strReason = "Last update time is NOT displayed as "+strUpdTime+" in view table";
				}
				
				selenium.mouseOver("//table/tbody/tr/td/a[text()='"+strResource+"']/ancestor::tr/td[3]");
				
				try{
					assertEquals("Last Update: "+strUpdTime,selenium.getText("//div[@id='theToolTip']/h1"));
					log4j.info("Last update time is displayed as "+strUpdTime+" in tool tip");
				} catch (AssertionError ae) {
					log4j.info("Last update time is NOT displayed as "+strUpdTime+" in tool tip");
					strReason = strReason+" Last update time is NOT displayed as "+strUpdTime+" in tool tip";
				}
			}
			
			selenium.mouseOver("//table/tbody/tr/td/a[text()='"+strResource+"']/ancestor::tr/td[3]");
			try{
				assertEquals("Updated By: "+strFulUsrName,selenium.getText("//div[@id='theToolTip']/p"));
				log4j.info("Updated By is displayed as "+strFulUsrName+ " in tool tip");
			} catch (AssertionError ae) {
				log4j.info("Updated By is NOT displayed as "+strFulUsrName+ " in tool tip");
				strReason = strReason+" Updated By is NOT displayed as "+strFulUsrName+ " in tool tip";
			}
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyLastUpdTimeInView "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	  'Description :Verify last updated time in View with comments in tool tip
	  'Precondition :None
	  'Arguments  :selenium,strResource,strStatType,strPos, strUpdTime,strFulUsrName,strComment
	  'Returns  :strReason
	  'Date    :07-Sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String verifyLastUpdTimeInViewToolTipWithComments(Selenium selenium,String strResource,String strStatType,String strPos,String strUpdTime,String strFulUsrName,String strComment) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {	
		
				try{
					assertTrue(selenium.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th["+strPos+"]/a[text()='"+strStatType+"']")||
							selenium.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th["+strPos+"][text()='"+strStatType+"']"));
					//selenium.mouseOver("//table[@starts-with(@id='rtt')]/tbody/tr/td["+intCnt+"]");
					selenium.mouseOver("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"+strResource+"']/ancestor::tr/td["+strPos+"]");
					selenium.mouseMoveAt("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"+strResource+"']/ancestor::tr/td["+strPos+"]", "10,10");
					
					assertTrue(selenium.isVisible("//div[@id='theToolTip']/h1"));
					log4j.info("Status Type "+strStatType+" is displayed");
					log4j.info("Tool tip is displayed");
					try{
						assertEquals("Last Update: "+strUpdTime,selenium.getText("//div[@id='theToolTip']/h1"));
						log4j.info("Last update time is displayed as "+strUpdTime+" in tool tip");
					} catch (AssertionError ae) {
						log4j.info("Last update time is NOT displayed as "+strUpdTime+" in tool tip");
						strReason = strReason+" Last update time is NOT displayed as "+strUpdTime+" in tool tip";
					}		
								
					try{
						assertEquals("Updated By: "+strFulUsrName,selenium.getText("//div[@id='theToolTip']/p[1]"));
						log4j.info("Updated By is displayed as "+strFulUsrName+ " in tool tip");
					} catch (AssertionError ae) {
						log4j.info("Updated By is NOT displayed as "+strFulUsrName+ " in tool tip");
						strReason = strReason+" Updated By is NOT displayed as "+strFulUsrName+ " in tool tip";
					}
					
					try{
						assertEquals("Comment: "+strComment,selenium.getText("//div[@id='theToolTip']/p[2]"));
						log4j.info("Comment is displayed as "+strComment+ " in tool tip");
					} catch (AssertionError ae) {
						log4j.info("Comment is NOT displayed as "+strComment+ " in tool tip");
						strReason = strReason+" Comment is NOT displayed as "+strComment+ " in tool tip";
					}
				} catch (AssertionError ae) {
					log4j.info("Tool tip is NOT displayed");
					log4j.info("Status Type "+strStatType+" is NOT displayed");
					strReason = "Status Type "+strStatType+" is NOT displayed";
				}	
		
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyLastUpdTimeInViewWithComments "
					+ e.toString();
		}
		return strReason;
	}
	

	 /***************************************************************
	  'Description :Verify last updated time in View with comments in tool tip
	  'Precondition :None
	  'Arguments  :selenium,strResource,strStatType,strPos, strUpdTime,strFulUsrName,strComment
	  'Returns  :strReason
	  'Date    :07-Sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String verifyUpdTimeInViewToolTipWithComments(Selenium selenium,String strResource,String strStatType,String strUpdTime,String strFulUsrName,String strComment) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		try {	
		
				try{					
					selenium.isElementPresent("//table[starts-with(@id,'rtt')]/tbody/tr/td[2]/a[text()='"+strResource+"']");
					selenium.mouseOver("//table[starts-with(@id,'rtt')]/tbody/tr/td[3]/a[text()='"+strStatType+"']/ancestor::tr/td[4]");
					selenium.mouseMoveAt("//table[starts-with(@id,'rtt')]/tbody/tr/td[3]/a[text()='"+strStatType+"']/ancestor::tr/td[4]", "10,10");
					
					assertTrue(selenium.isVisible("//div[@id='theToolTip']/h1"));
					log4j.info("Status Type "+strStatType+" is displayed");
					log4j.info("Tool tip is displayed");
					try{
						assertEquals("Last Update: "+strUpdTime,selenium.getText("//div[@id='theToolTip']/h1"));
						log4j.info("Last update time is displayed as "+strUpdTime+" in tool tip");
					} catch (AssertionError ae) {
						log4j.info("Last update time is NOT displayed as "+strUpdTime+" in tool tip");
						strReason = strReason+" Last update time is NOT displayed as "+strUpdTime+" in tool tip";
					}		
								
					try{
						assertEquals("Updated By: "+strFulUsrName,selenium.getText("//div[@id='theToolTip']/p[1]"));
						log4j.info("Updated By is displayed as "+strFulUsrName+ " in tool tip");
					} catch (AssertionError ae) {
						log4j.info("Updated By is NOT displayed as "+strFulUsrName+ " in tool tip");
						strReason = strReason+" Updated By is NOT displayed as "+strFulUsrName+ " in tool tip";
					}
					
					try{
						assertEquals("Comment: "+strComment,selenium.getText("//div[@id='theToolTip']/p[2]"));
						log4j.info("Comment is displayed as "+strComment+ " in tool tip");
					} catch (AssertionError ae) {
						log4j.info("Comment is NOT displayed as "+strComment+ " in tool tip");
						strReason = strReason+" Comment is NOT displayed as "+strComment+ " in tool tip";
					}
				} catch (AssertionError ae) {
					log4j.info("Tool tip is NOT displayed");
					log4j.info("Status Type "+strStatType+" is NOT displayed");
					strReason = "Status Type "+strStatType+" is NOT displayed";
				}	
		
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyLastUpdTimeInViewWithComments "
					+ e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
	  'Description :Verify last updated time in View Resource Detail with comments in tool tip
	  'Precondition :None
	  'Arguments  :selenium,strResource,strStatType,strPos, strUpdTime,strFulUsrName,strComment
	  'Returns  :strReason
	  'Date    :07-Sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String verifyLastUpdTimeInViewResDetToolTipWithComments(
			Selenium selenium, String strResource, String strStatType,
			String strUpdTime, String strFulUsrName, String strComment)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertTrue(selenium
						.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
								+ strStatType + "']"));
				selenium
						.mouseOver("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
								+ strStatType + "']/ancestor::tr/td[3]");
				selenium.mouseMoveAt(
						"//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
								+ strStatType + "']/ancestor::tr/td[3]",
						"10,10");
				assertTrue(selenium.isVisible("//div[@id='theToolTip']/h1"));
				log4j.info("Status Type " + strStatType + " is displayed");
				log4j.info("Tool tip is displayed");
				try {
					assertEquals("Last Update: " + strUpdTime, selenium
							.getText("//div[@id='theToolTip']/h1"));
					log4j.info("Last update time is displayed as " + strUpdTime
							+ " in tool tip");
				} catch (AssertionError ae) {
					log4j.info("Last update time is NOT displayed as "
							+ strUpdTime + " in tool tip");
					strReason = strReason
							+ " Last update time is NOT displayed as "
							+ strUpdTime + " in tool tip";
				}

				try {
					assertEquals("Updated By: " + strFulUsrName, selenium
							.getText("//div[@id='theToolTip']/p[1]"));
					log4j.info("Updated By is displayed as " + strFulUsrName
							+ " in tool tip");
				} catch (AssertionError ae) {
					log4j.info("Updated By is NOT displayed as "
							+ strFulUsrName + " in tool tip");
					strReason = strReason + " Updated By is NOT displayed as "
							+ strFulUsrName + " in tool tip";
				}

				try {
					assertEquals("Comment: " + strComment, selenium
							.getText("//div[@id='theToolTip']/p[2]"));
					log4j.info("Comment is displayed as " + strComment
							+ " in tool tip");
				} catch (AssertionError ae) {
					log4j.info("Comment is NOT displayed as " + strComment
							+ " in tool tip");
					strReason = strReason + " Comment is NOT displayed as "
							+ strComment + " in tool tip";
				}
			} catch (AssertionError ae) {
				log4j.info("Tool tip is NOT displayed");
				log4j.info("Status Type " + strStatType + " is NOT displayed");
				strReason = "Status Type " + strStatType + " is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyLastUpdTimeInViewWithComments "
					+ e.toString();
		}
		return strReason;
	}
	 /***************************************************************
	  'Description :check update status prompt is not displayed when 
	  				Remind Me 10 minutes button is clicked.
	  'Precondition :None
	  'Arguments  :selenium, strStatType
	  'Returns  :strReason
	  'Date    :20-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

		public String remindMeAndCheckPrompt(Selenium selenium,String strStatType) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				selenium.click(propElementDetails.getProperty("RemindMeIn10Mins"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {					
					 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"+strStatType+"')]"));
					 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']/label/span[text()='(Required/Overdue)']"));
					 log4j.info("'Update Status' prompt for "+strStatType+" is NOT displayed.");
				
				} catch (AssertionError Ae) {
					strReason = "'Update Status' prompt for "+strStatType+" is displayed.";
					log4j.info("'Update Status' prompt for "+strStatType+" is displayed.");
				}

			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function remindMeAndCheckPrompt "
						+ e.toString();
			}
			return strReason;
		}
		
		/***************************************************************
		  'Description :check user default screen is displayed when 
		  				Remind Me 10 minutes button is clicked.
		  'Precondition :None
		  'Arguments  :selenium
		  'Returns  :strReason
		  'Date    :24-Sep-2012
		  'Author   :QSG
		  '---------------------------------------------------------------
		  'Modified Date                            Modified By
		  '<Date>                                     <Name>
		  ***************************************************************/

			public String remindMeAndNavToRegDefault(Selenium selenium) throws Exception {

				String strReason = "";
				propEnvDetails = objReadEnvironment.readEnvironment();
				propElementDetails = objelementProp.ElementId_FilePath();

				gstrTimeOut = propEnvDetails.getProperty("TimeOut");

				try {
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					
					selenium.click(propElementDetails.getProperty("RemindMeIn10Mins"));
					selenium.waitForPageToLoad(gstrTimeOut);
					try {
						assertEquals("Region Default", selenium.getText(propElementDetails
								.getProperty("Header.Text")));

						log4j.info("Region Default" + " page is displayed");

					} catch (AssertionError Ae) {
						log4j.info("Region Default" + " page is NOT displayed");
						strReason = "Region Default" + " page is NOT displayed";
					}

				} catch (Exception e) {
					log4j.info(e);
					strReason = "Failed in function remindMeAndNavToRegDefault "
							+ e.toString();
				}
				return strReason;
			}
			
     /***************************************************************
	 'Description :Verify edit view page is displayed
	 'Precondition :None
	 'Arguments  :selenium,strViewName
	 'Returns  :None
	 'Date    :1-June-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
			 
	public String navEditViewPage(Selenium selenium, String strViewName)
			throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {

				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");

				selenium.click("//table[@id='listViews']/tbody/tr/"
						+ "td[text()='" + strViewName + "']/"
						+ "preceding-sibling::td/a[text()='Edit']");
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt=0;
				do{
					try{
						assertEquals("Edit View", selenium
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
					assertEquals("Edit View", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Edit View  page is  displayed ");

				} catch (AssertionError Ae) {
					log4j.info(" Edit View  page is NOT displayed " + Ae);
					strReason = "Edit View  page is NOT displayed " + Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				strReason = "Region Views List page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate to edit view  " + e;
		}
		return strReason;
	}
	
	/***************
	 'Description :Verify view fields are edited
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :4-June-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	 
	 
	public String fillViewFields(Selenium selenium, String strViewName,
			String strVewDescription, String strViewType,
			boolean blnVisibleToAllUsers, boolean blnShowAllStatusTypes,
			String[] strSTvalue, boolean blnShowAllResourceTypes,
			String[][] strRSValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails.getProperty("View.CreateNewView.Name"), strViewName);// View
			// Name
			selenium
					.type(propElementDetails.getProperty("View.CreateNewView.Description"), strVewDescription);// View
			// Description

			selenium.select(propElementDetails.getProperty("View.CreateNewView.ViewType"), "label=" + strViewType);

			if (blnVisibleToAllUsers) {
				if (selenium.isChecked("css=input[name='visibleToAllUsers']") == false) {
					selenium.click("css=input[name='visibleToAllUsers']");
				}

			}

			// Select status type

			if (blnShowAllStatusTypes) {

				if (selenium.isChecked("css=input[name='showAllColumns']") == false) {
					selenium.click("css=input[name='showAllColumns']");

				}
			} else {

				for (String s : strSTvalue) {
					if (selenium
							.isChecked("css=input[name='statusTypeID'][value='"
									+ s + "']") == false) {
						selenium.click("css=input[name='statusTypeID'][value='"
								+ s + "']");

					}
				}
			}

			// Select Resources and Resource Types
			if (blnShowAllResourceTypes) {

				if (selenium.isChecked("css=input[name='showAllRows']") == false) {
					selenium.click("css=input[name='showAllRows']");

				}
			} else {

				for (int i = 0; i < strRSValue.length; i++) {

					if (strRSValue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='resourceID'][value='"
										+ strRSValue[i][0] + "']") == false) {
							selenium
									.click("css=input[name='resourceID'][value='"
											+ strRSValue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strRSValue[i][0] + "']")) {
						selenium.click("css=input[name='resourceID'][value='"
								+ strRSValue[i][0] + "']");
					}

				}
			}

			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");

				try {

					assertTrue(selenium
							.isElementPresent("//table[@id='listViews']/"
									+ "tbody/tr/td[2][text()='" + strViewName
									+ "']"));

					log4j.info("View " + strViewName
							+ "  is  displayed in Region Views List page ");

				} catch (AssertionError Ae) {
					log4j.info("View " + strViewName
							+ "  is NOT displayed in Region Views List page"
							+ Ae);
					strReason = "View " + strViewName
							+ "  is NOT displayed in Region Views List page"
							+ Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				strReason = "Region Views List page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function fillViewFields " + e;
		}
		return strReason;
	}
	
	/***************************************************************
		 'Description :Verify Region Views List page is displayed
		 'Precondition :None
		 'Arguments  :None
		 'Returns  :None
		 'Date    :7-May-2012
		 'Author   :QSG
		 '---------------------------------------------------------------
		 'Modified Date                            Modified By
		 '<Date>                                     <Name>
		 ***************************************************************/
	public String navMainMenuByMobileViewLink(Selenium selenium)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("link=Mobile View");
			// selenium.waitForPageToLoad(gstrTimeOut);

			boolean blnFound = false;
			int intCnt = 0;
			while (blnFound == false && intCnt < 60) {
				try {
					selenium.selectWindow("Main Menu");
					selenium.getText("css=h1");
					blnFound = true;
				} catch (Exception e) {
					Thread.sleep(1000);
					intCnt++;
				}
			}

			try {
				assertEquals("Main Menu", selenium.getText("css=h1"));
				log4j.info(" Main Menu page is displayed ");

			} catch (AssertionError Ae) {
				log4j.info(" Main Menu page is NOT displayed " + Ae);
				strReason = "Main Menu page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navMainMenuByMobileViewLink " + e;
		}
		return strReason;
	}
	
 /********************************************************************
		 'Description :Verify Views page is displayed in Mobile link window
		 'Precondition :None
		 'Arguments  :None
		 'Returns  :None
		 'Date    :4-June-2012
		 'Author   :QSG
		 '---------------------------------------------------------------
		 'Modified Date                            Modified By
		 '<Date>                                     <Name>
		 ***************************************************************/
	public String navViewsPgeInMobileViewLnkWindow(Selenium selenium)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.click(propElementDetails
					.getProperty("MobileView.ViewLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Views", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("View page is displayed");

			} catch (AssertionError Ae) {
				log4j.info("View page is NOT displayed" + Ae);
				strReason = "View page is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navViewsPgeInMobileViewLnkWindow "
					+ e;
		}
		return strReason;
	}
	
	/*************************
	 'Description :Verify User Views page is displayed in Mobile link window
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :4-June-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	public String navUserViewsInMobleLnkWindow(Selenium selenium,
			String strViewName) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.click("//div[@id='pageMenu']/ul/li/a[text()='"
					+ strViewName + "']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals(strViewName, selenium.getText("css=h1"));
				log4j.info(strViewName + "page is  displayed ");

			} catch (AssertionError Ae) {
				log4j.info(strViewName + "page is NOT displayed " + Ae);
				strReason = strViewName + "page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navUserViewsInMobleLnkWindow " + e;
		}
		return strReason;
	}
	
	/*************************************************************************
		 'Description :Verify Resources in User Views page  in Mobile link window
		 'Precondition :None
		 'Arguments  :None
		 'Returns  :None
		 'Date    :4-June-2012
		 'Author   :QSG
		 '---------------------------------------------------------------
		 'Modified Date                            Modified By
		 '<Date>                                     <Name>
		 ***************************************************************/
	public String verifyResInUserViewPageInMobView(Selenium selenium,
			String strViewName, String[] strResource) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			assertEquals(strViewName, selenium.getText("css=h1"));
			log4j.info(strViewName + "page is  displayed ");

			for (String str : strResource) {

				try {
					assertTrue(selenium.isElementPresent("css=a:contains('"
							+ str + "')"));

					log4j.info(str + " is present in the User View");

				} catch (AssertionError Ae) {
					log4j.info(str + " is NOT present in the User View");
					strReason = str + " is NOT present in the User View";
				}

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyResInUserViewPageInMobView "
					+ e;
		}
		return strReason;
	}
	
	 /*************************************************************************
		 'Description :Verify status type in resource detail page in Mobile link window
		 'Precondition :None
		 'Arguments  :None
		 'Returns  :None
		 'Date    :4-June-2012
		 'Author   :QSG
		 '---------------------------------------------------------------
		 'Modified Date                            Modified By
		 '<Date>                                     <Name>
		 ***************************************************************/
	public String verifySTInResourceDetailPage(Selenium selenium,
			String strSectionName, String[] strST, String strResource)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.click("link=" + strResource);
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Resource Detail", selenium.getText("css=h1"));
				log4j
						.info(strResource
								+ " Resource Detail page is  displayed ");

				try {
					assertEquals(strSectionName, selenium.getText("css=h3"));

					log4j.info(strSectionName + " is displayed");

					for (String str : strST) {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='container']"
											+ "/div[2]/h3[text()='"
											+ strSectionName
											+ "']"
											+ "/following-sibling::table/tbody/tr/td[1]"
											+ "[text()='" + str + ":']"));

							log4j.info(str + " is displayed");
						} catch (AssertionError Ae) {
							log4j.info(str + " is NOT displayed");
							strReason = str + " is NOT displayed";
						}

					}

				} catch (AssertionError Ae) {
					log4j.info(strSectionName + " is NOT displayed");
					strReason = strSectionName + " is NOT displayed";
				}

			} catch (AssertionError Ae) {
				log4j.info(strResource
						+ " Resource Detail page is NOT displayed ");
				strReason = strResource + "page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTInResourceDetailPage " + e;
		}
		return strReason;
	}
	
	/***************
	 'Description :Verify update status screen is displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :strReason
	 'Date    :23-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	  * @throws Exception 
	 ***************************************************************/
	public String navToUpdateStatusByKey(Selenium selenium, String strResName)
			throws Exception {

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			
			// click on customize resource detail view link
			selenium.click("//table/tbody/tr/td/a[text()='" + strResName
					+ "']/ancestor::tr/td[1]/a/img");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			int intCnt=0;
			do{
				try{
					assertEquals("Update Status", selenium
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
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info("navToUpdateStatusByKey function failed" + e);
			strReason = "navToUpdateStatusByKey function failed" + e;
		}
		return strReason;
	}
	
	/***************
	 'Description :Verify ST in update status screen is displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :strReason
	 'Date    :29-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	  * @throws Exception 
	 ***************************************************************/
	public String verifySTInUpdateSTScreen(Selenium selenium, String strST[])
			throws Exception {

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

				for (String str : strST) {
					try {
						assertTrue(selenium
								.isElementPresent("//form/div/div/label[contains(text(),'"
										+ str + "')]"));

						log4j.info(str + " is displayed");

					} catch (AssertionError Ae) {
						log4j.info(str + " is NOT displayed" + Ae);
						strReason = str + " is NOT displayed";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info("verifySTInUpdateSTScreen function failed" + e);
			strReason = "verifySTInUpdateSTScreen function failed" + e;
		}
		return strReason;
	}
	
	/***************
	 'Description :Verify Expanded ST in update status screen is displayed
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :strReason
	 'Date    :29-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	  * @throws Exception 
	 ***************************************************************/
	public String verifyExpandedSTInUpdateSTScreen(Selenium selenium,
			String strST[]) throws Exception {

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {

			try {
				assertEquals("Update Status", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Update Status screen is displayed");

				for (String str : strST) {

					try {
						assertTrue(selenium
								.isVisible("//form/div/div/label" +
										"[contains(text(),'"
										+ str + "')]"));
						try {

							assertTrue(selenium
									.isElementPresent("//form/div/div/label" +
											"[contains(text(),'"
											+ str + "')]"));

							log4j.info(str + " is displayed");

						} catch (AssertionError Ae) {
							log4j.info(str + " is NOT displayed" + Ae);
							strReason = str + " is NOT displayed";

						}

					} catch (Exception e) {
						log4j.info(str + " is NOT displayed" + e);
						strReason = str + " is NOT displayed";
					}

				}

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is NOT displayed" + Ae);
				strReason = "Update Status screen is NOT displayed" + Ae;
			}

		} catch (Exception e) {
			log4j.info("verifyExpandedSTInUpdateSTScreen function failed" + e);
			strReason = "verifyExpandedSTInUpdateSTScreen function failed" + e;
		}
		return strReason;
	}
	
	/***************
	 'Description :Verify view fields are edited
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :4-June-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	 
	 
	public String fillViewFieldsSelAndDeselectST(Selenium selenium,
			String strViewName, String strVewDescription, String strViewType,
			boolean blnVisibleToAllUsers, boolean blnShowAllStatusTypes,
			String[][] strSTvalue, boolean blnShowAllResourceTypes,
			String[][] strRSValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.type(propElementDetails.getProperty("View.CreateNewView.Name"), strViewName);// View
			// Name
			selenium
					.type(propElementDetails.getProperty("View.CreateNewView.Description"), strVewDescription);// View
			// Description

			selenium.select(propElementDetails.getProperty("View.CreateNewView.ViewType"), "label=" + strViewType);

			if (blnVisibleToAllUsers) {
				if (selenium.isChecked("css=input[name='visibleToAllUsers']") == false) {
					selenium.click("css=input[name='visibleToAllUsers']");
				}

			}

			// Select status type

			if (blnShowAllStatusTypes) {

				if (selenium.isChecked("css=input[name='showAllColumns']") == false) {
					selenium.click("css=input[name='showAllColumns']");

				}
			} else {

				for (int i = 0; i < strSTvalue.length; i++) {

					if (strSTvalue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='statusTypeID'][value='"
										+ strSTvalue[i][0] + "']") == false) {
							selenium
									.click("css=input[name='statusTypeID'][value='"
											+ strSTvalue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='statusTypeID'][value='"
									+ strSTvalue[i][0] + "']")) {
						selenium.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[i][0] + "']");
					}

				}

			}

			// Select Resources and Resource Types
			if (blnShowAllResourceTypes) {

				if (selenium.isChecked("css=input[name='showAllRows']") == false) {
					selenium.click("css=input[name='showAllRows']");

				}
			} else {

				for (int i = 0; i < strRSValue.length; i++) {

					if (strRSValue[i][1].equals("true")) {

						if (selenium
								.isChecked("css=input[name='resourceID'][value='"
										+ strRSValue[i][0] + "']") == false) {
							selenium
									.click("css=input[name='resourceID'][value='"
											+ strRSValue[i][0] + "']");

						}

					} else if (selenium
							.isChecked("css=input[name='resourceID'][value='"
									+ strRSValue[i][0] + "']")) {
						selenium.click("css=input[name='resourceID'][value='"
								+ strRSValue[i][0] + "']");
					}

				}
			}

			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");

				try {

					assertTrue(selenium
							.isElementPresent("//table[@id='listViews']/"
									+ "tbody/tr/td[2][text()='" + strViewName
									+ "']"));

					log4j.info("View " + strViewName
							+ "  is  displayed in Region Views List page ");

				} catch (AssertionError Ae) {
					log4j.info("View " + strViewName
							+ "  is NOT displayed in Region Views List page"
							+ Ae);
					strReason = "View " + strViewName
							+ "  is NOT displayed in Region Views List page"
							+ Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				strReason = "Region Views List page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function fillViewFields " + e;
		}
		return strReason;
	}
	
	
	/***************************************************************
	'Description	:Select deselect visisble to users in view page
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String selAndDeselVisToUsersInViewPage(Selenium selenium,
			boolean blnVisibleToAllUsers,boolean blnsave) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");


				if (blnVisibleToAllUsers) {
					if (selenium.isChecked("css=input[name='visibleToAllUsers']") == false) {
						selenium.click("css=input[name='visibleToAllUsers']");
					}
					}else
					{
						if (selenium.isChecked("css=input[name='visibleToAllUsers']")) {
							selenium.click("css=input[name='visibleToAllUsers']");
						}
					}
				if(blnsave)
				{
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {

					assertEquals("Region Views List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Region Views List page is  displayed ");
				} catch (AssertionError Ae) {
					log4j.info(" Region Views List page is NOT displayed ");
					strReason = "Region Views List page is NOT displayed ";
				}
				}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function selAndDeselVisToUsersInViewPage ";
		}
		return strReason;
	}
	
	/********************************************************************************************************
	 'Description :check Status type in event status screen
	 'Precondition :None
	 'Arguments  :selenium,strStatTypeArr,blnST
	 'Returns  :String
	 'Date    :6-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/

	public String chkSTAssoOrNotInViewScreen(Selenium selenium,
			String[] strStatTypeArr, boolean blnST, String strResource) {
		String strReason = "";
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnST) {
				try {

					for (int i = 0; i < strStatTypeArr.length; i++) {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th/a[text()='"
										+ strStatTypeArr[i]
										+ "']/ancestor::thead/following-sibling::tbody/tr/td/a"
										+ "[text()='"
										+ strResource
										+ "']/parent::td//following-sibling::td[text()='--']"));
						log4j.info(strStatTypeArr[i] + "is associated for "
								+ strResource
								+ " -- is displayed in user view screen.");
					}
				} catch (AssertionError ae) {
					for (int i = 0; i < strStatTypeArr.length; i++) {
						log4j.info(strStatTypeArr[i] + "is NOT associated for "
								+ strResource
								+ " -- is displayed in user view screen.");
						strReason = strStatTypeArr[i]
								+ "is NOT associated for " + strResource
								+ " -- is displayed in user view screen.";
					}
				}
			} else {
				try {

					for (int i = 0; i < strStatTypeArr.length; i++) {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th/a[text()='"
										+ strStatTypeArr[i]
										+ "']/ancestor::thead/following-sibling::tbody/tr/td/a"
										+ "[text()='"
										+ strResource
										+ "']/parent::td//following-sibling::td[text()='N/A']"));
						log4j.info(strStatTypeArr[i]
								+ "is NOT associated  for " + strResource
								+ "N/A is displayed in user view screen.");
					}
				} catch (AssertionError ae) {
					for (int i = 0; i < strStatTypeArr.length; i++) {
						log4j.info(strStatTypeArr[i] + "is associated for "
								+ strResource
								+ " N/A is displayed in user view screen.");
						strReason = strStatTypeArr[i] + "is associated  for "
								+ strResource
								+ " N/A is displayed in user view screen.";
					}
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
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
	public String selAndDeselRTInEditView(Selenium selenium,String strResTypeVal,boolean blnCheck,boolean blnSave) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			if(blnCheck){
				if (selenium
						.isChecked("css=input[name='resourceTypeID'][value='"
								+ strResTypeVal + "']") == false) {
					selenium
							.click("css=input[name='resourceTypeID'][value='"
									+ strResTypeVal + "']");

				}
			}else{
				if (selenium
						.isChecked("css=input[name='resourceTypeID'][value='"
								+ strResTypeVal + "']")) {
					selenium
							.click("css=input[name='resourceTypeID'][value='"
									+ strResTypeVal + "']");

				}
			}
			
			if(blnSave)
			{
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Region Views List", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");
			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed ");
				strReason = "Region Views List page is NOT displayed ";
			}
			}
		
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function selAndDeselRTInEditView " + e.toString();
		}
		return strReason;
	}
	
	 /********************************************************************************************************
	'Description	:Fetch view Value in view  List page
	'Precondition	:None
	'Arguments		:selenium,strView
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	***********************************************************************************************************/
	public String fetchViewValueInViewList(Selenium selenium,String strView) throws Exception{
		String strViewValue="";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try{
			String strViewValueArr[]=selenium.getAttribute("//table[@id='listViews']/tbody/tr/td[2][text()='"+strView+"']/parent::tr/td[1]/a@href").split("viewID=");	
			strViewValue=strViewValueArr[1];			
			log4j.info("view Value is "+strViewValue);
		} catch (Exception e) {
			log4j.info("fetchViewValueInViewList function failed" + e);
			strViewValue = "";
		}
		return strViewValue;
	}
	
	 /*******************************************************************
	 'Description :Verify Resource is selected or not for a view
	 'Precondition :None
	 'Arguments  :selenium,
	 'Returns  :String
	 'Date    :23-july-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '28-May-2012                               <Name>
	 ********************************************************************/

	 public String chkRSSelOrNotForView(Selenium selenium,boolean blnResource,
			 String strResVal,String strResource) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  try {

	   rdExcel = new ReadData();
	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");
	   if(blnResource){
	   try {
			assertTrue(selenium.isChecked("css=input[name='resourceID'][value='"+strResVal+"']"));
			log4j.info("Check box corresponding to region "+strResource+ " remains checked");
		} catch (AssertionError Ae) {
			strErrorMsg = " Check box corresponding to region "+strResource+ "is unchecked";
			log4j.info("Check box corresponding to region "+strResource+ "is unchecked");
		}
	   }else{
		try {

			assertFalse(selenium.isChecked("css=input[name='resourceID'][value='"+strResVal+"']"));
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
	 'Description :Verify statustype is selected or not for a view
	 'Precondition :None
	 'Arguments  :selenium,
	 'Returns  :String
	 'Date    :23-july-2012
	 'Author   :QSG
	 '------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '28-May-2012                               <Name>
	 ********************************************************************/

	 public String chkSTSelOrNotForView(Selenium selenium,boolean blnST,
			 String strSTVal,String statTypeName) throws Exception {

	  String strErrorMsg = "";// variable to store error mesage

	  try {

	   rdExcel = new ReadData();
	   propEnvDetails = objReadEnvironment.readEnvironment();
	   propElementDetails = objelementProp.ElementId_FilePath();
	   gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	   selenium.selectWindow("");
	   selenium.selectFrame("Data");
	   if(blnST){
	   try {
			assertTrue(selenium.isChecked("css=input[name='statusTypeID'][value='"+strSTVal+"']"));
			log4j.info("Check box corresponding to region "+statTypeName+ " remains checked");
		} catch (AssertionError Ae) {
			strErrorMsg = " Check box corresponding to region "+statTypeName+ "is unchecked";
			log4j.info("Check box corresponding to region "+statTypeName+ "is unchecked");
		}
	   }else{
		try {

			assertFalse(selenium.isChecked("css=input[name='statusTypeID'][value='"+strSTVal+"']"));
			log4j.info("Check box corresponding to region "+statTypeName+ "is unchecked");
		} catch (AssertionError Ae) {
			strErrorMsg = " Check box corresponding to region "+statTypeName+ "is checked";
			log4j.info(" Check box corresponding to region "+statTypeName+ "is checked");
		}
	   }
	  } catch (Exception e) {
	   log4j.info("chkRSSelectedOrNotForUsr function failed" + e);
	   strErrorMsg = "chkRSSelectedOrNotForUsr function failed" + e;
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
	public String selAndDeselRSInEditView(Selenium selenium,String strResVal,boolean blnCheck,boolean blnSave) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try {

					assertTrue(selenium.isElementPresent("css=input[name='resourceID']"));
					break;
				}catch(AssertionError Ae){
					Thread.sleep(1000);
					intCnt++;
				
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			}while(intCnt<60);
			
			if(blnCheck){
				if (selenium
						.isChecked("css=input[name='resourceID'][value='"
								+ strResVal + "']") == false) {
					selenium
							.click("css=input[name='resourceID'][value='"
									+ strResVal + "']");

				}
			}else{
				if (selenium
						.isChecked("css=input[name='resourceID'][value='"
								+ strResVal + "']")) {
					selenium
							.click("css=input[name='resourceID'][value='"
									+ strResVal + "']");

				}
			}
			
			if(blnSave)
			{
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);
			
			intCnt=0;
			do{
				try {

					assertEquals("Region Views List", selenium
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

				assertEquals("Region Views List", selenium
						.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");
			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed ");
				strReason = "Region Views List page is NOT displayed ";
			}
			}
		
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function selAndDeselRTInEditView " + e.toString();
		}
		return strReason;
	}
	
	/********************************************************************************************************
	'Description :check error message in event status screen
	'Precondition :None
	'Arguments  :selenium,strResource
	'Returns  :String
	'Date    :6-June-2012
	'Author   :QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                    <Name>
	***********************************************************************************************************/	
	public String chkErrMsgEditViewPage(Selenium selenium,
			String strResType,boolean blnSave,String strViewName) {
		String strReason = "";
		try {
		
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if(blnSave)
			{
			selenium.click("css=input[value='Save']");
			selenium.waitForPageToLoad(gstrTimeOut);
			}
			try {
				assertEquals("No status types are associated at the resource level or with the resources of type " +
						strResType, selenium.getText("css=div.emsError > ul > li"));
				log4j
						.info("Error message is displayed stating that No status types are " +
								"associated at the resource level or with the resources of type "+strResType+"");
				log4j.info("View"+strViewName+ "NOT saved");
			} catch (AssertionError ae) {
				log4j.info("Error message is displayed stating that No status types are " +
						"associated at the resource level or with the resources of type "+strResType+"");
				strReason ="Error message is displayed stating that No status types are " +
						"associated at the resource level or with the resources of type "+strResType+"";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	 /***************************************************************
   'Description :check resource type and resource in user view screen
   'Precondition :None
   'Arguments  :selenium, strUserView, strRT, strRS, strRTValue
   'Returns  :strReason
   'Date    :28-May-2012
   'Author   :QSG
   '---------------------------------------------------------------
   'Modified Date                            Modified By
   '<Date>                                     <Name>
   ***************************************************************/

public String checkResTypeAndResInUserViewFalseCond(Selenium selenium,
		String strUserView, String strRT, String[] strRS, String strRTValue)
		throws Exception {

	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	try {
			try {
				assertFalse(selenium.isElementPresent("//table[@id='rtt_"
						+ strRTValue + "']/thead/tr/th/a[text()='" + strRT
						+ "']"));
				log4j.info(strRT + " Page is NOT displayed");
				for (String str : strRS) {
					try {
						assertFalse(selenium
								.isElementPresent("//table[@id='rtt_"
										+ strRTValue + "']"
										+ "/tbody/tr/td/a[text()='" + str
										+ "']"));
						log4j.info(str + "  is  NOT displayed");
					} catch (AssertionError Ae) {
						strReason = str + "  is displayed";
						log4j.info(str + "  is  displayed");
					}
				}
			} catch (AssertionError Ae) {
				strReason = strRT + "  is  displayed" + Ae;
				log4j.info(strRT + "  isdisplayed" + Ae);
			}

	} catch (Exception e) {
		log4j.info("Failed in function check restype and resource in user view screen");
		strReason = "Failed in function check restype and resource in user view screen ";
	}
	return strReason;
}

/****************************************************************
'Description :Verify view fields are edited
'Precondition :None
'Arguments  :None
'Returns  :None
'Date    :4-June-2012
'Author   :QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/


public String selAndDeselShowAllStAndShowAllRTInViewPage(Selenium selenium, String strViewName,
  boolean blnShowAllStatusTypes, boolean blnShowAllResourceTypes,boolean blnsave) throws Exception {

 String strReason = "";
 propEnvDetails = objReadEnvironment.readEnvironment();
 propElementDetails = objelementProp.ElementId_FilePath();
 gstrTimeOut = propEnvDetails.getProperty("TimeOut");
 try {

  selenium.selectWindow("");
  selenium.selectFrame("Data");

  // Select status type

  if (blnShowAllStatusTypes) {
   if (selenium.isChecked("css=input[name='showAllColumns']") == false) {
    selenium.click("css=input[name='showAllColumns']");
   }
  } else {
   if (selenium.isChecked("css=input[name='showAllColumns']")) {
    selenium.click("css=input[name='showAllColumns']");

   }
   else{
    selenium.click("css=input[name='showAllColumns']");
    selenium.click("css=input[name='showAllColumns']");    
   }
  }

  // Select Resources and Resource Types
  if (blnShowAllResourceTypes) {
   if (selenium.isChecked("css=input[name='showAllRows']")== false) {
    selenium.click("css=input[name='showAllRows']");
   }
  } else {

   if (selenium.isChecked("css=input[name='showAllRows']") ) {
    selenium.click("css=input[name='showAllRows']");
   }
   else
   {
    selenium.click("css=input[name='showAllRows']");
    selenium.click("css=input[name='showAllRows']");
   }
  }
  
  if(blnsave)
  {
  selenium.click("css=input[value='Save']");
  selenium.waitForPageToLoad(gstrTimeOut);  
  }
 } catch (Exception e) {
  log4j.info(e);
  strReason = "Failed in function fillViewFields " + e;
 }
 return strReason;
}

/***************************************************************
'Description	:navigate to particular user view
'Precondition	:None
'Arguments		:selenium,strViewName
'Returns		:strReason
'Date	 		:15-May-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/
public String navToRegionDefaultView(Selenium selenium, String strViewName)
		throws Exception {
	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	try {
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		// View Tab
		selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
		// click on View name link
		selenium.click("link=" + strViewName);
		selenium.waitForPageToLoad(gstrTimeOut);

		try {
			assertEquals("Region Default", selenium.getText(propElementDetails
					.getProperty("Header.Text")));

			log4j.info("Region Default" + "view  page is displayed");

		} catch (AssertionError Ae) {
			log4j.info(strViewName + " page is NOT displayed");
			strReason = strViewName + " page is NOT displayed";
		}

	} catch (Exception e) {
		log4j.info(e);
		strReason = "Failed in function navToUserView " + e.toString();
	}
	return strReason;
}

/***********************************************************************
'Description : check status types in Edit custom View options
'Precondition :None
'Arguments  :selenium,strStatusType
    
'Returns  :String
'Date    :25-May-2012
'Author   :QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                   <Name>
************************************************************************/

public String checkSTInEditCustViewOptionsfalseCond(Selenium selenium,String strStatusType) throws Exception{
 String strReason="";
 propEnvDetails = objReadEnvironment.readEnvironment();
 propElementDetails = objelementProp.ElementId_FilePath();

 gstrTimeOut = propEnvDetails.getProperty("TimeOut");
 try{
		selenium.selectWindow("");
		selenium.selectFrame("Data"); 
  try{
   assertFalse(selenium.isElementPresent("//b[text()='"+strStatusType+"']"));
   log4j.info("Status Type "+strStatusType+" is NOT displayed in Edit Custom View Options (Columns)");
  } catch (AssertionError Ae) {
   strReason = "Status Type "+strStatusType+" is displayed in Edit Custom View Options (Columns)";
   log4j.info("Status Type "+strStatusType+" is  displayed in Edit Custom View Options (Columns)");
  }
 
  
 } catch (Exception e) {
  log4j.info("checkSTInEditCustViewOptionsfalseCond function failed" + e);
  strReason = "checkSTInEditCustViewOptionsfalseCond function failed" + e;
 }
 return strReason;
}


/***************************************************************
'Description  :Return to user view screen from update status
               screen by clicking on Return to View.
'Precondition :None
'Arguments    :selenium,strViewName
'Returns      :strReason
'Date         :28-May-2012
'Author       :QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/
public String varReturnToViewLink(Selenium selenium,boolean blnReturrLink)
  throws Exception {

 String strReason = "";
 propEnvDetails = objReadEnvironment.readEnvironment();
 propElementDetails = objelementProp.ElementId_FilePath();
 gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	try {
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		if(blnReturrLink)
		{
		try {
			assertTrue(selenium.isElementPresent("link=Return to View"));
			log4j.info("Return to View link is  available");

		} catch (AssertionError Ae) {
			log4j.info("Return to View link is NOT available");
			strReason = "Return to View link is  NOT available";
		}
		}else{
		try {
			assertFalse(selenium.isElementPresent("link=Return to View"));
			log4j.info("Return to View link is NOT  available");

		} catch (AssertionError Ae) {
			log4j.info("Return to View link is available");
			strReason = "Return to View link is available";
		}
		}

	} catch (Exception e) {
		log4j.info(e);
		strReason = "Failed in function returnUserViews" + e.toString();
	}
	return strReason;
}

/***************************************************************
'Description	:Verify new View is Created
'Precondition	:None
'Arguments		:None
'Returns		:None
'Date	 		:7-May-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/


public String navToCreateViewPage(Selenium selenium) throws Exception {

	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	try {

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		selenium.click("css=input[value='Create New View']");
		selenium.waitForPageToLoad(gstrTimeOut);
		int intCnt=0;
		do{
			try {

				assertEquals("Create New View", selenium.getText(propElementDetails
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

			assertEquals("Create New View", selenium
					.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info(" Create New View page is  displayed ");
		} catch (AssertionError Ae) {
			log4j.info(" Create New View page is NOT displayed " + Ae);
			strReason = "Create New View page is NOT displayed " + Ae;
		}

	} catch (Exception e) {
		log4j.info(e);
		strReason = "Failed in function navToCreateViewPage " + e;
	}
	return strReason;
}


/*******************************************************************
'Description :Verify statustype is selected or not for a view
'Precondition :None
'Arguments  :selenium,
'Returns  :String
'Date    :23-july-2012
'Author   :QSG
'------------------------------------------------------------------
'Modified Date                            Modified By
'28-May-2012                               <Name>
********************************************************************/

public String chkSTPresentOrNotForView(Selenium selenium,boolean blnST,
		 String strSTVal,String statTypeName) throws Exception {

 String strErrorMsg = "";// variable to store error mesage

 try {

  rdExcel = new ReadData();
  propEnvDetails = objReadEnvironment.readEnvironment();
  propElementDetails = objelementProp.ElementId_FilePath();
  gstrTimeOut = propEnvDetails.getProperty("TimeOut");

  selenium.selectWindow("");
  selenium.selectFrame("Data");
  if(blnST){
  try {
		assertTrue(selenium.isElementPresent("css=input[name='statusTypeID'][value='"+strSTVal+"']"));
		log4j.info(statTypeName+ "is displayed under 'Select Status Types' section.");
	} catch (AssertionError Ae) {
		strErrorMsg = statTypeName+ "is NOT displayed under 'Select Status Types' section.";
		log4j.info(statTypeName+ "is NOT displayed under 'Select Status Types' section.");
	}
  }else{
	try {

		assertFalse(selenium.isElementPresent("css=input[name='statusTypeID'][value='"+strSTVal+"']"));
		log4j.info(statTypeName+ "is  NOT displayed under 'Select Status Types' section.");
	} catch (AssertionError Ae) {
		strErrorMsg =statTypeName+ "is displayed under 'Select Status Types' section.";
		log4j.info(statTypeName+ "is displayed under 'Select Status Types' section.");
	}
  }
 } catch (Exception e) {
  log4j.info("chkSTPresentOrNotForView function failed" + e);
  strErrorMsg = "chkSTPresentOrNotForView function failed" + e;
 }
 return strErrorMsg;
}	

/***************************************************************
'Description	:Copy and create an View.
'Precondition	:None
'Arguments		:selenium,strViewName,strNewViewName,strVewDescription,strViewType				 
'Returns		:None
'Date	 		:23-May-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/

public String navToEditViewByCopyLink(Selenium selenium, String strViewName)
		throws Exception {

	String strReason = "";
	
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();
	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	try {
		
		 selenium.selectWindow("");
		  selenium.selectFrame("Data");
		
		//click on Copy
		selenium.click("//table[@id='listViews']/tbody/tr/"
				+ "td[text()='" + strViewName + "']/"
				+ "preceding-sibling::td/a[text()='Copy']");
		selenium.waitForPageToLoad(gstrTimeOut);			
		try {
			assertEquals("Edit View", selenium
					.getText(propElementDetails
							.getProperty("Header.Text")));
			log4j.info(" Edit View  page is  displayed ");			
		} catch (AssertionError Ae) {
			log4j.info(" Edit View  page is NOT displayed " + Ae);
			strReason = "Edit View  page is NOT displayed " + Ae;
		}				
	} catch (Exception e) {
		log4j.info(e);
		strReason = "Failed in function copyAndCreateView " + e;
	}
	return strReason;
}

/*******************************************************************
'Description :Verify statustype is selected or not for a view
'Precondition :None
'Arguments  :selenium,
'Returns  :String
'Date    :23-july-2012
'Author   :QSG
'------------------------------------------------------------------
'Modified Date                            Modified By
'28-May-2012                               <Name>
********************************************************************/

public String chkSTEditResDetailViewSec(Selenium selenium,boolean blnST,
		String statTypeName) throws Exception {

 String strErrorMsg = "";// variable to store error mesage

 try {

  rdExcel = new ReadData();
  propEnvDetails = objReadEnvironment.readEnvironment();
  propElementDetails = objelementProp.ElementId_FilePath();
  gstrTimeOut = propEnvDetails.getProperty("TimeOut");

  selenium.selectWindow("");
  selenium.selectFrame("Data");
  if(blnST){
  try {
	  assertTrue(selenium.isTextPresent(statTypeName));
	  log4j.info(statTypeName+ "is displayed  under 'Uncategorized' section on " +
				"'Edit Resource Detail View Sections' screen ");
	} catch (AssertionError Ae) {
		strErrorMsg = statTypeName+ "is NOT displayed  under 'Uncategorized' section on " +
				"'Edit Resource Detail View Sections' screen ";
		log4j.info(statTypeName+ "is NOT displayed  under 'Uncategorized' section on " +
				"'Edit Resource Detail View Sections' screen ");
	}
  }else{
	try {

		assertFalse(selenium.isTextPresent(statTypeName));
		log4j.info(statTypeName+ "is NOT displayed  under 'Uncategorized' section on " +
				"'Edit Resource Detail View Sections' screen ");
	} catch (AssertionError Ae) {
		strErrorMsg =statTypeName+ "is displayed  under 'Uncategorized' section on " +
				"'Edit Resource Detail View Sections' screen ";
		 log4j.info(statTypeName+ "is displayed  under 'Uncategorized' section on " +
					"'Edit Resource Detail View Sections' screen ");
	}
  }
 } catch (Exception e) {
  log4j.info("chkSTEditResDetailViewSec function failed" + e);
  strErrorMsg = "chkSTEditResDetailViewSec function failed" + e;
 }
 return strErrorMsg;
}	

/***************************************************************
'Description :check update status prompt displayed for status type
'Precondition :None
'Arguments  :selenium, strResource,strStatType
'Returns  :strReason
'Date    :11-June-2012
'Author   :QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/

	public String varUpdateStatPrompt(Selenium selenium,
			String strResource,String strStatType) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			try {
				 assertFalse(selenium.isElementPresent(propElementDetails.getProperty("UpdateStatus.OverdueTitle")));
				 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']/label[contains(text(),'"+strStatType+"')]"));
				 assertFalse(selenium.isElementPresent("//div[@class='statusTitle clearFix']/label/span[text()='(Required/Overdue)']"));
				log4j.info("'Update Status' prompt for "+strStatType+" is  NOT displayed.");
			
			} catch (AssertionError Ae) {
				strReason = "'Update Status' prompt for "+strStatType+" is displayed.";
				log4j.info("'Update Status' prompt for "+strStatType+" is displayed.");
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkUpdateStatPrompt "
					+ e.toString();
		}
		return strReason;
	}
	 /*************************************************************************
	 'Description :navigate to update status page by clicking on status value
	 			corresponding to particular status type in View Res Detail
	 'Precondition :None
	 'Arguments  :None
	 'Returns  :None
	 'Date    :06-Sep-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
public String navToUpdStatusByFrmViewResDetail(Selenium selenium, String strResVal,String strStatTypeVal)
		throws Exception {

	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	try {

		selenium.click("css=#v_"+strResVal+"_"+strStatTypeVal);
		selenium.waitForPageToLoad(gstrTimeOut);
		
		
		int intCnt=0;
		do{
			try{
				assertEquals("Update Status", selenium
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
			assertEquals("Update Status", selenium
					.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("Update Status Page is  displayed");

			
			
		} catch (AssertionError Ae) {
			strReason = "Update Status Page is NOT displayed" + Ae;
			log4j.info("Update Status Page is NOT displayed" + Ae);
		}	

	} catch (Exception e) {
		log4j.info(e);
		strReason = "Failed in function navToUpdStatusByFrmViewResDetail " + e;
	}
	return strReason;
}

/***************************************************************
'Description	:navigate to particular user view
'Precondition	:None
'Arguments		:selenium,strViewName
'Returns		:strReason
'Date	 		:15-May-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
***************************************************************/
public String checkViewInMenuOption(Selenium selenium, String strViewName)
		throws Exception {
	String strReason = "";
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	try {
		selenium.selectWindow("");
		selenium.selectFrame("Data");
		// View Tab
		selenium.click(propElementDetails.getProperty("View.ViewLink"));
		selenium.waitForPageToLoad(gstrTimeOut);

		try {
			assertEquals("View Menu", selenium.getText(propElementDetails
					.getProperty("Header.Text")));	
			log4j.info("View Menu page is displayed");		
			try{
			assertTrue(selenium.isElementPresent("//div[@id='mainContainer']" +
					"/table/tbody/tr/td/a[text()='"+strViewName+"']"));
			log4j.info(strViewName+" is listed under View Menu.");
			} catch (AssertionError Ae) {
				log4j.info(strViewName+" is NOT listed under View Menu.");
				strReason =strViewName+" is NOT listed under View Menu.";
			}
		} catch (AssertionError Ae) {
			log4j.info("View Menu page is  NOT displayed");
			strReason ="View Menu page is  NOT displayed";
		}

	} catch (Exception e) {
		log4j.info(e);
		strReason = "Failed in function navToUserView " + e.toString();
	}
	return strReason;
}

	/***************************************************************
	'Description :Verify Updated status value is displayed in View resource detail screen with comments
	'Precondition :None
	'Arguments  :selenium,strSection,strStatType,strUpdtVal,strComment
	'Returns  :strReason
	'Date    :06-sep-2012
	'Author   :QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetail(Selenium selenium, String strSection,
			String strStatTypep[]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(
						selenium
								.getText("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a"),
						strSection);
				log4j.info("Section " + strSection
						+ " is displayed in View Resource Detail screen");

				for (String s : strStatTypep) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id," +
										"'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ s + "']"));

						log4j
								.info("The Status Type "+s+" is displayed on the " +
										"view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j
								.info("The Status Type "+s+" is NOT displayed on" +
										" the view resource detail screen. ");
						strReason = "The Status Type "+s+" is NOT displayed on " +
								"the view resource detail screen. ";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Section " + strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section " + strSection
						+ " is NOT displayed in View Resource Detail screen";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTViewInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate Custom view table
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:15-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String navToCustomViewTabl(Selenium selenium) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// View Tab
			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));
			// click on View name link
			selenium.click(propElementDetails.getProperty("View.CustomLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Custom View - Table", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Custom View - Table  page is displayed");

			} catch (AssertionError Ae) {
				log4j.info("Custom View - Table  page is NOT displayed");
				strReason = "Custom View - Table  page NOT is displayed";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToCustomViewTabl "
					+ e.toString();
		}
		return strReason;
	}

	/***************************************************************
	'Description	:Verify Number or Text status type is updated
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:25-June-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String updateNumStatusTypeViewScreen(Selenium selenium, String strResource,
			String strStatType, String strRoleStatTypeValue,String strUpdateValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Update Status", selenium.getText("css=h1"));
				log4j.info("Update Status screen is displayed");

				selenium.type("css=input[name='status_value_" + strRoleStatTypeValue
						+ "']", strUpdateValue);	
				
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try {
					assertEquals(selenium
							.getText("//table[starts-with(@id,'rtt')]"
									+ "/thead/tr/th/a[text()='"
									+ strStatType
									+ "']/ancestor::table/tbody/tr/td[3]"),
							strUpdateValue);
					log4j
							.info("The status value "
									+ strUpdateValue
									+ "  updated displayed on the view screen. ");
				} catch (AssertionError Ae) {
					log4j
					.info("The status value "
							+ strUpdateValue
							+ "  NOT updated and displayed on the view screen. ");
					strReason = "The status value "
							+ strUpdateValue
							+ " NOT updated and displayed on the view screen. ";

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
	/***************************************************************
	 'Description :Save and naviagte to region view list
	     View screen
	 'Precondition :None
	 'Arguments  :selenium,strViewName
	 'Returns  :None
	 'Date    :27-Sep-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	
	public String saveAndNavToRgionViewList(Selenium selenium,
			String strViewName) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			// save
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {

				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");
				
				try {

					assertTrue(selenium
							.isElementPresent("//table[@id='listViews']/"
									+ "tbody/tr/td[2][text()='"
									+ strViewName + "']"));

					log4j.info("View " + strViewName
							+ "  is  displayed in Region Views List page ");

				} catch (AssertionError Ae) {
					log4j
							.info("View "
									+ strViewName
									+ "  is NOT displayed in Region Views List page"
									+ Ae);
					strReason = "View "
							+ strViewName
							+ "  is NOT displayed in Region Views List page"
							+ Ae;
				}

			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				strReason = "Region Views List page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function saveAndNavToRgionViewList " + e;
		}
		return strReason;
	}

	

	/***********************************************************************
	'Description	:Verify Edit custom view page  displayed
	'Precondition	:None
	'Arguments		:selenium,blnMap,blnTable
	'Returns		:String
	'Date	 		:27-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'27-Sep-2012                               <Name>
	************************************************************************/
	
	public String navViewCustomTableOrCustmViewMap(Selenium selenium,
			boolean blnMap, boolean blnTable) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails.getProperty("View.ViewLink"));

			selenium.click(propElementDetails.getProperty("View.CustomLink"));
			selenium.waitForPageToLoad(gstrTimeOut);

			if (blnTable) {
				
				
				if(selenium.isElementPresent(propElementDetails
						.getProperty("View.Custom.ShowTabl"))){
					selenium.click(propElementDetails
							.getProperty("View.Custom.ShowTabl"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}
				
	
				try {
					assertEquals("Custom View - Table", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Custom View - Table page is  displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Custom View - Table page is NOT displayed"
							+ Ae;
					log4j
							.info("Custom View - Table page is NOT displayed"
									+ Ae);
				}

			}
			if (blnMap) {
				if(selenium.isElementPresent(propElementDetails
						.getProperty("View.Custom.ShowMap"))){
					selenium.click(propElementDetails
							.getProperty("View.Custom.ShowMap"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}
				
	
				try {
					assertEquals("Custom View - Map", selenium.getText(propElementDetails
							.getProperty("Header.Text")));
					
					log4j.info("Custom View - Map screen is displayed");
					
				} catch (AssertionError ae) {
					log4j.info("Custom View - Map screen is NOT displayed");
					strErrorMsg = "Custom View - Map screen is NOT displayed";
				}
			}

		} catch (Exception e) {
			log4j.info("navigate to Custom View - Table Page "
					+ "function failed" + e);
			strErrorMsg = "navigate to Custom View - Table Page"
					+ " function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***************************************************************
    'Description :Verify status type name and definition in definition screen
    'Precondition :None
    'Arguments  :selenium,strStatType,strSTDefn
    'Returns  :strReason
    'Date    :26-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifySTNameAndDefInDefnScreen(Selenium selenium,
			String strStatType, String strSTDefn) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click("link=" + strStatType);
			for (int second = 0;; second++) {
				if (second >= 60)
					fail("timeout");
				try {
					if (selenium.isElementPresent("id=TB_title")) {
						if (selenium.isVisible("id=TB_title"))
							break;
					}
				} catch (Exception e) {
				}

				Thread.sleep(1000);
			}

			selenium.selectFrame("css=iframe[id^=TB_iframeContent]");

			try {
				assertEquals(strStatType, selenium.getText("css=h1"));
				assertEquals("Definition: " + strSTDefn, selenium
						.getText("css=p"));
				log4j
						.info("Name and Definition is displayed appropriately on the 'Definition' screen");

			} catch (AssertionError ae) {
				log4j
						.info("Name and Definition is NOT displayed appropriately on the 'Definition' screen");
				strReason = "Name and Definition is NOT displayed appropriately on the 'Definition' screen";
			}
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click("css=#TB_closeWindowButton");

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTNameAndDefInDefnScreen "
					+ e.toString();
		}
		return strReason;
	}

	 /***************************************************************
	  'Description :Verify status type name and definition in update status
	  'Precondition :None
	  'Arguments  :selenium, strStatType,strDefn
	  'Returns  :strReason
	  'Date    :26-sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

		public String verifySTNameAndDefnInUpdtStatus(Selenium selenium,
				String strStatType,String strSTVal, String strDefn) throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try{
					assertEquals(strStatType+": "+strDefn, selenium.getText("css=label[for='st_"+strSTVal+"']"));
					log4j.info("Name and Definition is displayed appropriately on the 'Update Status' screen.");
				} catch (AssertionError Ae) {
					strReason = "Name and Definition is NOT displayed appropriately on the 'Update Status' screen." ;
					log4j.info("Name and Definition is NOT displayed appropriately on the 'Update Status' screen.");
				}
							
			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function verifySTNameAndDefnInUpdtStatus "
						+ e.toString();
			}
			return strReason;
		}
		
		/***************************************************************
		'Description	:Check status type in Create View screen
		'Precondition	:None
		'Arguments		:selenium,strStatType
		'Returns		:strReason
		'Date	 		:26-sep-2012
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date                            Modified By
		'<Date>                                     <Name>
		***************************************************************/
		public String checkStatTypeInCreateView(Selenium selenium,String strStatType)
				throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try {
			
				  try{
					    assertTrue(selenium.isElementPresent("//table[@id='statusTypes']/tbody/tr/td/table/tbody/tr[@class='statusType darkBg']/td[contains(text(),'"+strStatType+"')]"));
					    log4j.info("Status Type "+strStatType+"is displayed in Create View page");
					        
				  }catch(AssertionError ae){
					   log4j.info("Status Type "+strStatType+"is NOT displayed in Create View page");
					    strReason="Status Type "+strStatType+"is NOT displayed in Create View page";
				}  
					  
			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function checkStatTypeInCreateView "
						+ e.toString();
			}
			return strReason;
		}
		
		/***************************************************************
		'Description	:Check status type in Edit Resource detail view sections screen
		'Precondition	:None
		'Arguments		:selenium,strStatType,strSection
		'Returns		:strReason
		'Date	 		:26-sep-2012
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date                            Modified By
		'<Date>                                     <Name>
		***************************************************************/
		public String checkStatTypeInEditResDetViewSections(Selenium selenium,String strStatType,String strSection)
				throws Exception {

			String strReason = "";
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			try {
				
				selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"+strSection+"')]");
				
					try{
						assertTrue(selenium.isElementPresent("//li[@class='item']/div[contains(text(),'"+strStatType+"')]"));
						assertTrue(selenium.isVisible("//li[@class='item']/div[contains(text(),'"+strStatType+"')]"));
						log4j.info(strStatType+" status type is displayed in section "+strSection);
										
					}catch(AssertionError ae){
						log4j.info(strStatType+" status type is NOT displayed in section "+strSection);
						strReason=strReason+" "+strStatType+" status type is NOT displayed in section "+strSection;
					}
				
											  
			} catch (Exception e) {
				log4j.info(e);
				strReason = "Failed in function checkStatTypeInEditResDetViewSections "
						+ e.toString();
			}
			return strReason;
		}

		/***************************************************************
	    'Description :Verify status type name and definition in definition screen
	    'Precondition :None
	    'Arguments  :selenium,strStatType,strSTDefnstrStatusName[],strColorValue
	    'Returns  :strReason
	    'Date    :01-Oct-2012
	    'Author   :QSG
	    '---------------------------------------------------------------
	    'Modified Date                            Modified By
	    '<Date>                                     <Name>
	    ***************************************************************/

	public String verifySTNameAndDefInDefnScreenOfMST(Selenium selenium,
			String strStatType, String strSTDefn,
			String[][] strStatusNameColorValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click("link=" + strStatType);
			for (int second = 0;; second++) {
				if (second >= 60)
					fail("timeout");
				try {
					if (selenium.isElementPresent("id=TB_title")) {
						if (selenium.isVisible("id=TB_title"))
							break;
					}
				} catch (Exception e) {
				}

				Thread.sleep(1000);
			}

			selenium.selectFrame("css=iframe[id^=TB_iframeContent]");

			try {
				assertEquals(strStatType, selenium.getText("css=h1"));
				assertEquals("Definition: " + strSTDefn, selenium
						.getText("css=p"));
				log4j.info("Name and Definition is displayed appropriately"
						+ " on the 'Definition' screen");

				for (int i = 0; i < strStatusNameColorValue.length; i++) {

					try {
						assertTrue(selenium
								.isElementPresent("//li/strong[text()='"
										+ strStatusNameColorValue[i][0]
										+ ": ']"));

						String s = selenium
								.getAttribute("//li/strong[text()='"
										+ strStatusNameColorValue[i][0]
										+ ": ']/@style");
						System.out.println(s);

						assertEquals(selenium
								.getAttribute("//li/strong[text()='"
										+ strStatusNameColorValue[i][0]
										+ ": ']/@style"), "color: #"
								+ strStatusNameColorValue[i][1] + ";");
						
						log4j
						.info("Name and color of Status is  displayed appropriately"
								+ " on the 'Definition' screen");

					} catch (AssertionError Ae) {
						log4j
								.info("Name and color of Status is NOT displayed appropriately"
										+ " on the 'Definition' screen");
						strReason = "Name and color of Status is NOT displayed appropriately on"
								+ " the 'Definition' screen";
					}
				}
			} catch (AssertionError ae) {
				log4j
						.info("Name and Definition is NOT displayed appropriately on "
								+ "the 'Definition' screen");
				strReason = "Name and Definition is NOT displayed appropriately "
						+ "on the 'Definition' screen";

			}
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			selenium.click("css=#TB_closeWindowButton");

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTNameAndDefInDefnScreenOfMST "
					+ e.toString();
		}
		return strReason;
	}

	 /***************************************************************
	  'Description :Verify status type name and definition in update status
	  'Precondition :None
	  'Arguments  :selenium, strStatType,strDefn
	  'Returns  :strReason
	  'Date    :26-sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String verifySTNameAndDefnInUpdtStatusOfMST(Selenium selenium,
			String strStatType, String strSTVal, String strDefn,
			String strStatusNameColorValue[][]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals(strStatType + ": " + strDefn, selenium
						.getText("css=label[for='st_" + strSTVal + "']"));
				log4j
						.info("Name and Definition is displayed appropriately on the 'Update Status' screen.");

				for (int i = 0; i < strStatusNameColorValue.length; i++) {

					try {
						assertTrue(selenium
								.isElementPresent("//label[@class='status"
										+ strStatusNameColorValue[i][1]
										+ "'][text()='"
										+ strStatusNameColorValue[i][0] + "']"));

						log4j
								.info("Name and color of Status is displayed appropriately "
										+ "on the 'Definition' screen");

					} catch (Exception e) {
						log4j
								.info("Name and color of Status is NOT displayed appropriately"
										+ " on the 'Definition' screen");
						strReason = "Name and color of Status is NOT displayed appropriately on"
								+ " the 'Definition' screen";
					}
				}

			} catch (AssertionError Ae) {
				strReason = "Name and Definition is NOT displayed appropriately on the 'Update Status' screen.";
				log4j
						.info("Name and Definition is NOT displayed appropriately on the 'Update Status' screen.");
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTNameAndDefnInUpdtStatus "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:navigate to edit resource detail from user view
					section.
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:strReason
	'Date	 		:5-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navToEditResourceFromViewResourceDetail(Selenium selenium) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			
			int intCnt=0;
			do{
				try{
					assertTrue(selenium
							.isElementPresent("link=edit resource details"));
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
			

			selenium.click("link=edit resource details");
			selenium.waitForPageToLoad(gstrTimeOut);

			intCnt=0;
			do{
				try{
					assertEquals("Edit Resource", selenium
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
				assertEquals("Edit Resource", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Edit Resource page is displayed");

			} catch (AssertionError Ae) {

				strReason = "Edit Resource page is NOT displayed" + Ae;
				log4j.info("Edit Resource page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j
					.info("Failed in function navToEditResourceViewResourceDetail "
							+ e);
			strReason = "Failed in function navToEditResourceViewResourceDetail "
					+ e.toString();
		}
		return strReason;
	
}
	
	
	/***************************************************************
	'Description	:Drag and drop the status type to particular section from another section and save
	'Precondition	:None
	'Arguments		:selenium,strStatType,strSection1,blnCreateSec,strSection2
	'Returns		:strReason
	'Date	 		:8-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'8-Oct-2012                                   <Name>
	 * @throws Exception 
	***************************************************************/
	public String dragAndDropSTFormOneSectionToAnother(Selenium selenium,
			String strStatType[], String strSection1, String strSection2,
			boolean blnCreateSec) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {

			if (blnCreateSec) {
				// type the section name and click on create section

				selenium.type(propElementDetails
						.getProperty("EditResDetViewSec.SectionName"),
						strSection2);
				selenium.click(propElementDetails
						.getProperty("EditResDetViewSec.CreateSection"));
			}

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strSection2 + "')]"));
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
				assertTrue(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection2 + "')]"));
				log4j.info("Section " + strSection2 + " is displayed");

				// click on Uncategorized - does NOT show in view

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection1 + "')]");
				for (String strStType : strStatType) {
					// drag and drop status type
					selenium.dragAndDropToObject(
							"//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]",
							"//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strSection2 + "')]");

				}

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection2 + "')]");
				for (String strStType : strStatType) {
					try {
						assertTrue(selenium
								.isElementPresent("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						assertTrue(selenium
								.isVisible("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						log4j.info(strStType
								+ " status type is displayed in section "
								+ strSection2);

					} catch (AssertionError ae) {
						log4j.info(strStType
								+ " status type is NOT displayed in section "
								+ strSection2);
						strReason = strReason + " " + strStType
								+ " status type is NOT displayed in section "
								+ strSection2;
					}
				}
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				
				intCnt=0;
				do{
					try {

						assertEquals("Region Views List", selenium.getText(propElementDetails
								.getProperty("Header.Text")));
						break;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<70);
				
				try {
					assertEquals("Region Views List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Region Views List screen is displayed");

				} catch (AssertionError ae) {
					log4j.info("Region Views List screen is NOT displayed");
					strReason = "Region Views List screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("Section " + strSection2 + " is NOT displayed");
				strReason = "Section " + strSection2 + " is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("dragAndDropSTFormOneSectionToAnother function failed"
					+ e);
			strReason = "dragAndDropSTFormOneSectionToAnother function failed"
					+ e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description 	:Verify Updated status value is displayed in View resource detail screen with comments
	'Precondition 	:None
	'Arguments  	:selenium,strSection,strStatType,strSectionValue
	'Returns  		:strReason
	'Date    		:08-Oct-2012
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetailNew(Selenium selenium,
			String strSection, String strSectionValue, String strStatTypep[])
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(selenium
						.getText("//table[starts-with(@id,'stGroup"
								+ strSectionValue + "')]/thead/tr/th[2]/a"),
						strSection);
				log4j.info("Section " + strSection
						+ " is displayed in View Resource Detail screen");

				for (String s : strStatTypep) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,"
										+ "'stGroup" + strSectionValue
										+ "')]/tbody/tr/td[2]/a[text()='" + s
										+ "']"));

						log4j.info("The Status Type is displayed on the "
								+ "view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j.info("The Status Type is NOT displayed on"
								+ " the view resource detail screen. ");
						strReason = "The Status Type is NOT displayed on "
								+ "the view resource detail screen. ";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Section " + strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section " + strSection
						+ " is NOT displayed in View Resource Detail screen";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTViewInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	
	
	/***************************************************************
	'Description 	:Verify Updated status value is displayed in View resource detail screen with comments
	'Precondition 	:None
	'Arguments  	:selenium,strSection,strStatType,strSectionValue
	'Returns  		:strReason
	'Date    		:09-Jan-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetailUnderSection(Selenium selenium,
			String strSection, String strSectionValue, String strStatTypep[])
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(selenium
						.getText("//table[starts-with(@id,'stGroup"
								+ strSectionValue + "')]/thead/tr/th[2]/a"),
						strSection);
				log4j.info("Section " + strSection
						+ " is displayed in View Resource Detail screen");

				for (String s : strStatTypep) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[starts-with(@id,"
										+ "'stGroup" + strSectionValue
										+ "')]/tbody/tr/td[2]/a[text()='" + s
										+ "']"));

						log4j.info("The Status Type "+s+" is displayed on the "
								+ "view resource detail screen under section '"+strSection+"'.");
					} catch (AssertionError Ae) {
						log4j.info("The Status Type "+s+" is NOT displayed on"
								+ " the view resource detail screen'"+strSection+"'.");
						strReason = "The Status Type "+s+" is NOT displayed on "
								+ "the view resource detail screen'"+strSection+"'.";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Section " + strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section " + strSection
						+ " is NOT displayed in View Resource Detail screen";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTInViewResDetailUnderSection "
					+ e.toString();
		}
		return strReason;
	}
	
	
	/***************************************************************
	'Description	:Drag and drop the status type to particular section from another section and save and navigate to view resource detail screen
	'Precondition	:None
	'Arguments		:selenium,strStatType,strSection1,blnCreateSec,strSection2
	'Returns		:strReason
	'Date	 		:8-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'8-Oct-2012                                   <Name>
	 * @throws Exception 
	***************************************************************/
	public String dragAndDropSTFormOneSectionToAnotherAndNavToViewResourceDetailScreen(
			Selenium selenium, String strStatType[], String strSection1,
			String strSection2, boolean blnCreateSec) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {

			if (blnCreateSec) {
				// type the section name and click on create section

				selenium.type(propElementDetails
						.getProperty("EditResDetViewSec.SectionName"),
						strSection2);
				selenium.click(propElementDetails
						.getProperty("EditResDetViewSec.CreateSection"));
			}

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strSection2 + "')]"));
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
				assertTrue(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection2 + "')]"));
				log4j.info("Section " + strSection2 + " is displayed");

				// click on Uncategorized - does NOT show in view

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection1 + "')]");
				for (String strStType : strStatType) {
					// drag and drop status type
					selenium.dragAndDropToObject(
							"//li[@class='item']/div[contains(text(),'"
									+ strStType + "')]",
							"//ul[@id='groups']/li/div/span[contains(text(),'"
									+ strSection2 + "')]");

				}

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection2 + "')]");
				for (String strStType : strStatType) {
					try {
						assertTrue(selenium
								.isElementPresent("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						assertTrue(selenium
								.isVisible("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						log4j.info(strStType
								+ " status type is displayed in section "
								+ strSection2);

					} catch (AssertionError ae) {
						log4j.info(strStType
								+ " status type is NOT displayed in section "
								+ strSection2);
						strReason = strReason + " " + strStType
								+ " status type is NOT displayed in section "
								+ strSection2;
					}
				}
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				intCnt = 0;
				do {
					try {

						assertEquals("View Resource Detail", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;

					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					}catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 70);

				try {
					assertEquals("View Resource Detail", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("View Resource Detail screen is displayed");

				} catch (AssertionError ae) {
					log4j.info("View Resource Detail screen is NOT displayed");
					strReason = "View Resource Detail screen is NOT displayed";
				}

			} catch (AssertionError ae) {
				log4j.info("Section " + strSection2 + " is NOT displayed");
				strReason = "Section " + strSection2 + " is NOT displayed";
			}

		} catch (Exception e) {
			log4j
					.info("dragAndDropSTFormOneSectionToAnotherAndNavToViewResourceDetailScreen function failed"
							+ e);
			strReason = "dragAndDropSTFormOneSectionToAnotherAndNavToViewResourceDetailScreen function failed"
					+ e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify status types in Edit resource deatil view section page
	'Precondition	:None
	'Arguments		:selenium,strStatType,strSection
	'Returns		:strReason
	'Date	 		:8-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'8-Oct-2012                                   <Name>
	 * @throws Exception 
	***************************************************************/
	public String verifySTInEditViewSectionScreenUnderParticularSection(
			Selenium selenium, String strStatType[], String strSection)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		String strReason = "";

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection + "')]"));
				log4j.info("Section " + strSection + " is displayed");

				selenium
						.click("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection + "')]");
				for (String strStType : strStatType) {
					try {
						assertTrue(selenium
								.isElementPresent("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						assertTrue(selenium
								.isVisible("//li[@class='item']/div[contains(text(),'"
										+ strStType + "')]"));
						log4j.info(strStType
								+ " status type is displayed in section "
								+ strSection);

					} catch (AssertionError ae) {
						log4j.info(strStType
								+ " status type is NOT displayed in section "
								+ strSection);
						strReason = strReason + " " + strStType
								+ " status type is NOT displayed in section "
								+ strSection;
					}
				}

			} catch (AssertionError ae) {
				log4j.info("Section " + strSection + " is NOT displayed");
				strReason = "Section " + strSection + " is NOT displayed";
			}

		} catch (Exception e) {
			log4j
					.info("verifySTInEditViewSectionScreenUnderParticularSection function failed"
							+ e);
			strReason = "verifySTInEditViewSectionScreenUnderParticularSection function failed"
					+ e;
		}
		return strReason;
	}

	/***************************************************************
	'Description	:nav Assign Users to View page
	'Precondition	:None
	'Arguments		:strViewName,selenium
	'Returns		:None
	'Date	 		:25-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navAssignUsersViewsPge(Selenium selenium, String strViewName)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//table[@id='listViews']/tbody/tr/" + "td[2][text()='"
					+ strViewName + "']/"
					+ "preceding-sibling::td/a[text()='Users']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Assign Users to View " + strViewName + "",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(" Assign Users to View " + strViewName
						+ " page is  displayed ");

				
				
			} catch (AssertionError Ae) {

				log4j.info(" Assign Users to View " + strViewName
						+ " page is NOT displayed " + Ae);
				strReason = "Assign Users to View " + strViewName
						+ " page is NOT displayed " + Ae;

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navAssignUsersViewsPge " + e;
		}
		return strReason;
	}
	
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
	
	public String isCheckedElement(Selenium selenium, String strElementID)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium.isChecked(strElementID));
				log4j.info("Element is Checked ");
		
				
			} catch (AssertionError  Ae) {

				log4j.info("Element is NOT Checked ");
				strReason = "Element is NOT Checked ";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function isCheckedElement " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:verify element is editable
	'Precondition	:None
	'Arguments		:strElementID,selenium
	'Returns		:None
	'Date	 		:25-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String isEditableElement(Selenium selenium, String strElementID)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium.isEditable(strElementID));
				log4j.info("Element is Editable ");

				
				
			} catch (AssertionError  Ae) {

				log4j.info("Element is NOT Editable ");
				strReason = "Element is NOT Editable ";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function isEditableElement " + e;
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:Verify users are assigned to particular views
	'Precondition	:None
	'Arguments		:strViewName,selenium,strUserNames[],blnVisibleToAllUsers
	'Returns		:None
	'Date	 		:25-Oct-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String selectDesectVisibleToAllUsers(Selenium selenium,
			boolean blnVisibleToAllUsers, boolean blnSave) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			if (blnVisibleToAllUsers) {
				if (selenium.isChecked("css=#visibleToAll") == false) {
					selenium.click("css=#visibleToAll");

				}
			} else {
				if (selenium.isChecked("css=#visibleToAll")) {
					selenium.click("css=#visibleToAll");

				}
			}

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {

					assertEquals("Region Views List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Region Views List page is  displayed ");

				} catch (AssertionError Ae) {
					log4j
							.info(" Region Views List page is NOT displayed "
									+ Ae);
					strReason = "Region Views List page is NOT displayed " + Ae;
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function selectDesectVisibleToAllUsers " + e;
		}
		return strReason;
	}

	/********************************************************************************************************
	 'Description :check ST Associated Or Not In Custom View Screen
	 'Precondition :None
	 'Arguments  :selenium,strStatType,blnST,pos,strResource
	 'Returns  :String
	 'Date    :30-Oct-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/

	public String chkSTAssoOrNotInCustomViewScreen(Selenium selenium,
			String strStatType, boolean blnST, String strResource,int pos) {
		String strReason = "";
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
	
			if (blnST) {
				try {

					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]/thead/tr/th/a[text()='"
									+ strStatType
									+ "']/ancestor::thead/following-sibling::tbody/tr/td/a"
									+ "[text()='"
									+ strResource
									+ "']/parent::td/following-sibling::td["
									+ pos + "][text()='--']"));
					log4j.info(strStatType + "is associated for " + strResource
							+ " -- is displayed in custom  view screen.");

				} catch (AssertionError ae) {

					log4j.info(strStatType + "is NOT associated for "
							+ strResource
							+ " -- is displayed in user view screen.");
					strReason = strStatType + "is NOT associated for "
							+ strResource
							+ " -- is displayed in custom  view screen.";
				}

			} else {
				try {

					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]/thead/tr/th/a[text()='"
									+ strStatType
									+ "']/ancestor::thead/following-sibling::tbody/tr/td/a"
									+ "[text()='"
									+ strResource
									+ "']/parent::td/following-sibling::td["
									+ pos + "][text()='N/A']"));
					log4j.info(strStatType + "is NOT associated  for "
							+ strResource
							+ "N/A is displayed in custom  view screen.");

				} catch (AssertionError ae) {

					log4j.info(strStatType + "is associated for " + strResource
							+ " N/A is displayed in user view screen.");
					strReason = strStatType + "is associated  for "
							+ strResource
							+ " N/A is displayed in custom  view screen.";
				}

			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/********************************************************************************************************
	 'Description :check ST Associated Or Not In Custom View Screen
	 'Precondition :None
	 'Arguments  :selenium,strStatType,blnST,pos,strResource
	 'Returns  :String
	 'Date    :30-Oct-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                    <Name>
	 ***********************************************************************************************************/

	public String chkSTAssoOrNotInViewScreenNew(Selenium selenium,
			String strStatType, boolean blnST, String strResource,int pos) {
		String strReason = "";
		try {
			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
	
			if (blnST) {
				try {

					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th/a[text()='"
									+ strStatType
									+ "']/ancestor::thead/following-sibling::tbody/tr/td/a"
									+ "[text()='"
									+ strResource
									+ "']/parent::td/following-sibling::td["
									+ pos + "][text()='--']"));
					log4j.info(strStatType + "is associated for " + strResource
							+ " -- is displayed in user view screen.");

				} catch (AssertionError ae) {

					log4j.info(strStatType + "is NOT associated for "
							+ strResource
							+ " -- is displayed in user view screen.");
					strReason = strStatType + "is NOT associated for "
							+ strResource
							+ " -- is displayed in user view screen.";
				}

			} else {
				try {

					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rtt')]/thead/tr/th/a[text()='"
									+ strStatType
									+ "']/ancestor::thead/following-sibling::tbody/tr/td/a"
									+ "[text()='"
									+ strResource
									+ "']/parent::td/following-sibling::td["
									+ pos + "][text()='N/A']"));
					log4j.info(strStatType + "is NOT associated  for "
							+ strResource
							+ "N/A is displayed in user view screen.");

				} catch (AssertionError ae) {

					log4j.info(strStatType + "is associated for " + strResource
							+ " N/A is displayed in user view screen.");
					strReason = strStatType + "is associated  for "
							+ strResource
							+ " N/A is displayed in user view screen.";
				}

			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	
	 /***************************************************************
    'Description :check RT,ST and RS in View cutom table
    'Precondition :None
    'Arguments  :selenium, statustype
    'Returns  :strReason
    'Date    :12-Nov-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/
	public String checkResTypeRSAndSTInViewCustTablNew(Selenium selenium,
			String strRT, String strRS, String[] strStatTypeArr)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertEquals("Custom View - Table", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Custom View - Table Page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]/"
									+ "thead/tr/th[2][text()='" + strRT + "']"));
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]"
									+ "/thead/tr/th[2][text()='"
									+ strRT
									+ "']/"
									+ "ancestor::table/tbody/tr/td[2]/a[text()='"
									+ strRS + "']"));

					log4j
							.info("Resource '"
									+ strRS
									+ "' is displayed on the 'Cusotm View table' screen under "
									+ strRT
									+ " along with all the status types.");

					for (int i = 0; i < strStatTypeArr.length; i++) {

						try {
							assertEquals(strStatTypeArr[i], selenium
									.getText("//table[starts-with(@id,'rgt')]"
											+ "/thead/tr/th[2][text()='"
											+ strRT + "']"
											+ "/ancestor::tr/th[" + i + "+3]"));
							log4j.info(strStatTypeArr[i]
									+ "is displayed for Resource" + strRS + "");

						} catch (AssertionError ae) {
							log4j.info(strStatTypeArr[i]
									+ "is NOT displayed for Resource" + strRS);
							strReason = strReason +strStatTypeArr[i]
									+ "is NOT displayed for Resource" + strRS;
						}

					}

				} catch (AssertionError ae) {
					log4j
							.info("Resource '"
									+ strRS
									+ "' is NOT displayed on the 'Event Status' screen under "
									+ strRT
									+ " along with all the status types.");
					strReason = "Resource '"
							+ strRS
							+ "' is NOT displayed on the 'Cusotm View table' screen under "
							+ strRT + " along with all the status types.";
				}

			} catch (AssertionError Ae) {
				strReason = " Custom View - TablePage is NOT displayed" + Ae;
				log4j.info("Custom View - Table Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function check ST,RS and RT in view custom view table "
					+ e.toString();
		}
		return strReason;
	}

	/***************************************************************
	'Description	:Delete a section
	'Precondition	:None
	'Arguments		:selenium,strSection
	'Returns		:strReason
	'Date	 		:24-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                  <Name>
	 * @throws Exception 
	***************************************************************/
	public String deleteSectionDefaultRTName(Selenium selenium,String strResrctTypName) throws Exception{
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
		String strReason="";
		selenium.selectWindow("");
		selenium.selectFrame("Data");
			
		try{
			selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"+strResrctTypName+"')]");
			selenium.click("css=a:contains('Delete')");
			
			selenium.click(propElementDetails.getProperty("Save"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try{
				assertEquals("No Rows in Custom View", selenium.getText("css=#viewContainer > h1"));
				assertEquals("There are no resources to display in your custom view.", selenium.getText("css=p"));
				assertEquals("Click here to add resources to your custom view", selenium.getText("link=Click here to add resources to your custom view"));
				log4j.info("No Rows in Custom View"+"" +
						"There are no resources to display in your custom view."+
						"Click here to add resources to your custom view 'message is displayed.");
			}catch(AssertionError ae){
				log4j.info("No Rows in Custom View"+"" +
						"There are no resources to display in your custom view."+
						"Click here to add resources to your custom view 'message is NOT displayed.");
				strReason="No Rows in Custom View"+"" +
						"There are no resources to display in your custom view."+
						"Click here to add resources to your custom view 'message is NOT displayed.";
			}
									
		}catch(AssertionError ae){
			log4j.info("deleteSectionDefaultRTName function failed");
			strReason="deleteSectionDefaultRTName function failed";
		}			
	
		return strReason;
	}
	
	/***************************************************************
	'Description	:Delete a section
	'Precondition	:None
	'Arguments		:selenium,strSection
	'Returns		:strReason
	'Date	 		:24-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                  <Name>
	 * @throws Exception 
	***************************************************************/
	public String chkSTNOtPresentInViewCustTable(Selenium selenium,
			String strRT, String strRS, String[] strStatTypeArr)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if(selenium.isElementPresent(propElementDetails
					.getProperty("View.Custom.ShowTabl"))){
				selenium.click(propElementDetails
						.getProperty("View.Custom.ShowTabl"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			
			for (int i = 0; i < strStatTypeArr.length; i++) {

				try {
					assertNotSame(
							strStatTypeArr[i],
							selenium.getText("//table[starts-with(@id,'rgt')]"
									+ "/thead/tr/th[2][text()='" + strRT + "']"
									+ "/ancestor::tr/th[" + i + "+3]"));
					log4j.info(strStatTypeArr[i] + "is NOT displayed for Resource"
							+ strRS + "");

				} catch (AssertionError ae) {
					log4j.info(strStatTypeArr[i]
							+ "is  displayed for Resource" + strRS);
					strReason = strReason + strStatTypeArr[i]
							+ "is  displayed for Resource" + strRS;
				}

			}		
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function check ST,RS and RT in view custom view table "
					+ e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description	:Verify new View is Created
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String createViewNew(Selenium selenium, String strViewName,
			String strVewDescription, String strViewType,
			boolean blnVisibleToAllUsers, boolean blnShowAllStatusTypes,
			String[] strSTvalue, boolean blnShowAllResourceTypes,
			String[] strRSValue,boolean blnSave) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			try {

				assertEquals("Create New View", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Create New View page is  displayed ");

				selenium.type(propElementDetails.getProperty("View.CreateNewView.Name"), strViewName);// View
																		// Name
				selenium.type(propElementDetails.getProperty("View.CreateNewView.Description"),
						strVewDescription);// View Description

				selenium.select(propElementDetails.getProperty("View.CreateNewView.ViewType"), "label=" + strViewType);

				if (blnVisibleToAllUsers) {
					if (selenium
							.isChecked("css=input[name='visibleToAllUsers']") == false) {
						selenium.click("css=input[name='visibleToAllUsers']");
					}

				}else{
					if (selenium
							.isChecked("css=input[name='visibleToAllUsers']") ) {
						selenium.click("css=input[name='visibleToAllUsers']");
					}
				}

				// Select status type

				if (blnShowAllStatusTypes) {

					if (selenium.isChecked("css=input[name='showAllColumns']") == false) {
						selenium.click("css=input[name='showAllColumns']");

					}
				} else {

					for (String s : strSTvalue) {
						if (selenium
								.isChecked("css=input[name='statusTypeID'][value='"
										+ s + "']") == false) {
							selenium
									.click("css=input[name='statusTypeID'][value='"
											+ s + "']");

						}
					}
				}

				// Select Resources and Resource Types
				if (blnShowAllResourceTypes) {

					if (selenium.isChecked("css=input[name='showAllRows']") == false) {
						selenium.click("css=input[name='showAllRows']");

					}
				} else {

					for (String RS : strRSValue) {

						if (selenium
								.isChecked("css=input[name='resourceID'][value='"
										+ RS + "']") == false) {
							selenium
									.click("css=input[name='resourceID'][value='"
											+ RS + "']");

						}
					}
				}
                if(blnSave){
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
                }
				

			} catch (AssertionError Ae) {
				log4j.info(" Create New View page is NOT displayed " + Ae);
				strReason = "Create New View page is NOT displayed " + Ae;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function createView " + e;
		}
		return strReason;
	}
	/***************************************************************
	'Description	:Verify new View is Created
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String SaveAndVerifyView(Selenium selenium, String strViewName) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {

					assertEquals("Region Views List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Region Views List page is  displayed ");

					try {
						assertTrue(selenium
								.isElementPresent("//table[@id='listViews']/"
										+ "tbody/tr/td[2][text()='"
										+ strViewName + "']"));
						log4j.info("View " + strViewName
								+ "  is  displayed in Region Views List page ");

					} catch (AssertionError Ae) {
						log4j.info("View "
										+ strViewName
										+ "  is NOT displayed in Region Views List page"+ Ae);
						strReason = "View "+ strViewName
								+ "  is NOT displayed in Region Views List page"+ Ae;
					}

				} catch (AssertionError Ae) {
					log4j.info(" Region Views List page is NOT displayed "+ Ae);
					strReason = "Region Views List page is NOT displayed " + Ae;
				}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function createView " + e;
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in user view screen
    'Precondition :None
    'Arguments  :selenium,strUpdateValue,strRT,strST
    'Returns  :strReason
    'Date    :4-Dec-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdatedValueOfRowWiseSTInViewScreen(Selenium selenium, 
			String strRT, String strST, String strUpdateValue) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
		
			try {
				assertEquals(selenium
						.getText("//table[starts-with(@id,'rtt')]/thead/tr/th[2]" +
								"/a[text()='"+strRT+"']/ancestor::thead" +
								"/following-sibling::tbody/tr/td[3]/a[text()='"+strST+"']" +
								"/parent::td/following-sibling::td[1]"),
						strUpdateValue);

				log4j
						.info("The status value "
								+ strUpdateValue
								+ " is updated and displayed on the view screen. ");
			} catch (AssertionError Ae) {
				log4j
						.info("The status value "
								+ strUpdateValue
								+ " is NOT updated and displayed on the view screen. ");
				strReason = "The status value "
						+ strUpdateValue
						+ " is NOT updated and displayed on the view screen. ";

			}	

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdateST "
					+ e.toString();
		}
		return strReason;
	}
	
	
	/***************************************************************
	'Description	:Verify events in event banners in view resourc detail page of mutual agreement regions
					section.
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:strReason
	'Date	 		:7-Dec-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String verifyEventInBannrsInViewRSDetailPageOfAgrmntRgns(
			Selenium selenium, String strRgnEvents1Level[][],
			String strRgnEvents2Level[][]) throws Exception {
		String strErrorMsg = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			int intCnt = 0;
			for (int i = 0; i < strRgnEvents1Level.length
					|| i < strRgnEvents2Level.length; i++) {
				intCnt = 0;
				try {

					intCnt++;
					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner']/h1[text()='"
									+ strRgnEvents1Level[i][0] + "']"));
					log4j.info("Region name " + strRgnEvents1Level[i][0]
							+ "is displayed");

					intCnt++;
					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner']/h1[text()='"
									+ strRgnEvents1Level[i][0]
									+ "']"
									+ "/following-sibling::table[1]/tbody/tr/td/a[text()='"
									+ strRgnEvents1Level[i][1] + "']"));

					log4j.info("Region name " + strRgnEvents1Level[i][0]
							+ " event " + strRgnEvents1Level[i][1]
							+ " is displayed");

					intCnt++;
					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner']/h1[text()='"
									+ strRgnEvents2Level[i][0]
									+ "']"
									+ "/following-sibling::h1[text()='"
									+ strRgnEvents2Level[i][1] + "']"));

					log4j.info("Region name " + strRgnEvents1Level[i][0]
							+ "and " + strRgnEvents2Level[i][1]
							+ " is displayed");

					intCnt++;
					assertTrue(selenium
							.isElementPresent("//div[@id='eventsBanner']/h1[text()='"
									+ strRgnEvents2Level[i][0]
									+ "']"
									+ "/following-sibling::h1[text()='"
									+ strRgnEvents2Level[i][1]
									+ "']/following-sibling::table[1]/"
									+ "tbody/tr/td/a[text()='"
									+ strRgnEvents2Level[i][2] + "']"));

					log4j.info("Region name " + strRgnEvents1Level[i][0]
							+ "and " + strRgnEvents2Level[i][1] + " and event "
							+ strRgnEvents2Level[i][2] + " is displayed");

				} catch (AssertionError Ae) {
					switch (intCnt) {
					case 1:
						log4j.info("Region name " + strRgnEvents1Level[i][0]
								+ " is NOT displayed");
						strErrorMsg = "Region name " + strRgnEvents1Level[i][0]
								+ " is NOT displayed";
						break;
					case 2:
						log4j.info("Region name " + strRgnEvents1Level[i][0]
								+ " event " + strRgnEvents1Level[i][1]
								+ " is NOT displayed");
						strErrorMsg = "Region name " + strRgnEvents1Level[i][0]
								+ " event " + strRgnEvents1Level[i][1]
								+ " is NOT displayed";
						break;

					case 3:
						log4j.info("Region name " + strRgnEvents1Level[i][0]
								+ "and " + strRgnEvents2Level[i][1]
								+ " is NOT displayed");
						strErrorMsg = "Region name " + strRgnEvents1Level[i][0]
								+ "and " + strRgnEvents2Level[i][1]
								+ " is NOT displayed";
						break;

					case 4:
						log4j.info("Region name " + strRgnEvents1Level[i][0]
								+ "and " + strRgnEvents2Level[i][1]
								+ " and event " + strRgnEvents2Level[i][2]
								+ " is NOT displayed");
						strErrorMsg = "Region name " + strRgnEvents1Level[i][0]
								+ "and " + strRgnEvents2Level[i][1]
								+ " and event " + strRgnEvents2Level[i][2]
								+ " is NOT displayed";
						break;

					}

				}

			}

		} catch (Exception e) {
			log4j.info(e);
			strErrorMsg = "Failed in function verifyEventInBannrsInViewRSDetailPageOfAgrmntRgns "
					+ e.toString();
		}
		return strErrorMsg;
	}

	/***************************************************************
	'Description	:navigate to view resource detail page
					section.
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	public String navToViewResourceDetailPageWitoutWaitForPgeLoad(
			Selenium selenium, String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			// click on resource link
			selenium
					.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
							+ strResource + "']");
			int i = 0;

			do {
				try {
					i++;
					assertTrue(selenium.isElementPresent("//td[text()='Address:']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(2000);
				} catch (Exception e) {
					Thread.sleep(2000);
				}
			} while (i < 60);
			Thread.sleep(10000);
			try {
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
				strReason = "View Resource Detail screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToViewResourceDetailPageWitoutWaitForPgeLoad "
					+ e.toString();
		}
		return strReason;
	}

	 /***************************************************************
    'Description :checkResTypeRSAndSTInViewCustTablNewMapOrTableOption
    'Precondition :None
    'Arguments  :selenium, statustype
    'Returns  :strReason
    'Date    :30-Jan-2013
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/
	public String checkResTypeRSAndSTInViewCustTablNewMapOrTableOption(
			Selenium selenium, String strRT, String strRS,
			String[] strStatTypeArr) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if(selenium.isElementPresent(propElementDetails
					.getProperty("View.Custom.ShowTabl"))){
				selenium.click(propElementDetails
						.getProperty("View.Custom.ShowTabl"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

			try {
				assertEquals("Custom View - Table", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Custom View - Table Page is  displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]/"
									+ "thead/tr/th[2][text()='" + strRT + "']"));
					assertTrue(selenium
							.isElementPresent("//table[starts-with(@id,'rgt')]"
									+ "/thead/tr/th[2][text()='"
									+ strRT
									+ "']/"
									+ "ancestor::table/tbody/tr/td[2]/a[text()='"
									+ strRS + "']"));

					log4j
							.info("Resource '"
									+ strRS
									+ "' is displayed on the 'Cusotm View table' screen under "
									+ strRT );

					for (int i = 0; i < strStatTypeArr.length; i++) {

						try {
							assertEquals(strStatTypeArr[i], selenium
									.getText("//table[starts-with(@id,'rgt')]"
											+ "/thead/tr/th[2][text()='"
											+ strRT + "']"
											+ "/ancestor::tr/th[" + i + "+3]"));
							log4j.info(strStatTypeArr[i]
									+ "is displayed for Resource" + strRS + "");

						} catch (AssertionError ae) {
							log4j.info(strStatTypeArr[i]
									+ "is NOT displayed for Resource" + strRS);
							strReason = strReason + strStatTypeArr[i]
									+ "is NOT displayed for Resource" + strRS;
						}
						log4j
						.info("Resource '"
								+ strRS
								+ "' is displayed on the 'Cusotm View table' screen under "
								+ strRT
								+ " along with all the status types.");

					}

				} catch (AssertionError ae) {
					log4j
							.info("Resource '"
									+ strRS
									+ "' is NOT displayed on the 'Event Status' screen under "
									+ strRT
									+ " along with all the status types.");
					strReason = "Resource '"
							+ strRS
							+ "' is NOT displayed on the 'Cusotm View table' screen under "
							+ strRT + " along with all the status types.";
				}

			} catch (AssertionError Ae) {
				strReason = " Custom View - TablePage is NOT displayed" + Ae;
				log4j.info("Custom View - Table Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function checkResTypeRSAndSTInViewCustTablNewMapOrTableOption "
					+ e.toString();
		}
		return strReason;
	}

	
	/***************************************************************
	'Description	:Delete a section
	'Precondition	:None
	'Arguments		:selenium,strSection
	'Returns		:strReason
	'Date	 		:24-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                  <Name>
	 * @throws Exception 
	***************************************************************/
	public String chkSTNOtPresentInViewCustTableNew(Selenium selenium,
			String strRT, String strRS, String[] strStatTypeArr)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			if(selenium.isElementPresent(propElementDetails
					.getProperty("View.Custom.ShowTabl"))){
				selenium.click(propElementDetails
						.getProperty("View.Custom.ShowTabl"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
			
			for (int i = 0; i < strStatTypeArr.length; i++) {

				try {
					assertNotSame(strStatTypeArr[i], selenium
							.getText("//table[starts-with(@id,'rgt')]"
									+ "/thead/tr/th[2][text()='" + strRT + "']"
									+ "/ancestor::tr/th[" + i + "+3]"));
					log4j.info(strStatTypeArr[i]
							+ "is NOT displayed for Resource" + strRS + "");

				} catch (AssertionError ae) {
					log4j.info(strStatTypeArr[i] + "is  displayed for Resource"
							+ strRS);
					strReason = strReason + strStatTypeArr[i]
							+ "is  displayed for Resource" + strRS;
				} catch (Exception e) {
					log4j.info(strStatTypeArr[i] + "is  displayed for Resource"
							+ strRS);
					strReason = "Status type is NOT displayed for Resource";
				}

			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function chkSTNOtPresentInViewCustTableNew "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description	:saveUpdateStatusValue
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-Feb-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	
	public String saveUpdateStatusValue(Selenium selenium) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function saveUpdateStatusValue " + e;
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

	public String cancelAndNavToViewListpage(Selenium selenium) throws Exception
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
			
			int intCnt=0;
			do{
				try{
					assertEquals("Region Views List", selenium.getText(propElementDetails
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

				assertEquals("Region Views List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");

			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				lStrReason = "Region Views List page is NOT displayed " + Ae;
			}
			

		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	 /*******************************************************************
	 'Description :Verify Resource is present or not for a view
	 'Arguments   :selenium,
	 'Returns     :String
	 'Date        :23-july-2012
	 '------------------------------------------------------------------
	 'Modified Date                                      Modified By
	 '28-May-2012                                         <Name>
	 ********************************************************************/

	public String chkRSPresentOrNotForView(Selenium selenium,
			boolean blnResource, String strResVal, String strResource,
			String strResType) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnResource) {
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='resourceID'][value='"
									+ strResVal + "']"));
					log4j.info(" Resource '" + strResource + "' under '"
							+ strResType + "' is displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = " Resource '" + strResource + "' under '"
							+ strResType + "' is NOT displayed.";
					log4j.info(" Resource '" + strResource + "' under '"
							+ strResType + "' is NOT displayed.");
				}
			} else {
				try {

					assertFalse(selenium
							.isElementPresent("css=input[name='resourceID'][value='"
									+ strResVal + "']"));
					log4j.info(" Resource '" + strResource + "' under '"
							+ strResType + "' is NOT displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = " Resource '" + strResource + "' under '"
							+ strResType + "' is displayed.";
					log4j.info(" Resource '" + strResource + "' under '"
							+ strResType + "' is displayed.");
				}
			}
		} catch (Exception e) {
			log4j.info("chkRSSelectedOrNotForUsr function failed" + e);
			strErrorMsg = "chkRSSelectedOrNotForUsr function failed" + e;
		}
		return strErrorMsg;
	}

	
	/***************************************************************
	'Description	:navigate to Edit Sub Resource Detail View Sections screen
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:28-Feb-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/
	public String navToEditSubResDetailViewSections(Selenium selenium)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {
			// click on customize resource detail view link
			selenium.click(propElementDetails
					.getProperty("SubResources.Create"));
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {

					assertEquals("Edit Sub Resource Detail View Sections",
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
				assertEquals("Edit Sub Resource Detail View Sections", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("Edit Sub Resource Detail View Sections screen is displayed");

			} catch (AssertionError ae) {
				log4j
						.info("Edit Sub Resource Detail View Sections screen is NOT displayed");
				strReason = "Edit Sub Resource Detail View Sections screen is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("navToEditSubResDetailViewSections function failed" + e);
			strReason = "navToEditSubResDetailViewSections function failed" + e;
		}
		return strReason;
	}

	
	/***************************************************************
	'Description	:navigate to Edit Sub Resource Detail View Sections screen
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:28-Feb-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/
	public String selectStatusTypesInEditSubResourceSectionsPge(
			Selenium selenium, String strSubResourceType,
			String strSuResourceTypeValue, String strSTValue[],
			boolean blnSavSuRS) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue(selenium
						.isElementPresent("//li[@id='g_"
								+ strSuResourceTypeValue
								+ "']"
								+ "/div/span[text()='"
								+ strSubResourceType
								+ "']"
								+ "/following-sibling::input[@name='subresourceSelect'][@value='g_"
								+ strSuResourceTypeValue + "']"));

				log4j.info("Sub-Resoure Type '"
						+ strSubResourceType
						+ "' is listed under it with check box associated with it.");

				selenium.click("//span[text()='" + strSubResourceType + "']");
				Thread.sleep(2000);
				selenium.click("//li[@id='g_" + strSuResourceTypeValue + "']"
						+ "/div/span[text()='" + strSubResourceType + "']"
						+ "/following-sibling::input[@value='g_"
						+ strSuResourceTypeValue + "']"
						+ "[@name='subresourceSelect']");

				for (String s : strSTValue) {
					selenium.click("css=input[name='g_"
							+ strSuResourceTypeValue + "'][value='" + s + "']");
				}
				if (blnSavSuRS) {
					selenium.click(propElementDetails
							.getProperty("View.CreateNewView.Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					int intCnt = 0;
					do {
						try {
							assertEquals("Region Views List",
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

						assertEquals("Region Views List",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j.info(" Region Views List page is  displayed ");

					} catch (AssertionError Ae) {
						log4j.info(" Region Views List page is NOT displayed "
								+ Ae);
						strReason = "Region Views List page is NOT displayed "
								+ Ae;
					}

				}

			} catch (AssertionError Ae) {
				log4j.info("Sub-Resoure Type '"
						+ strSuResourceTypeValue
						+ "' is NOT listed under it with check box associated with it.");
				strReason = "Sub-Resoure Type '"
						+ strSuResourceTypeValue
						+ "' is NOT listed under it with check box associated with it.";
			}

		} catch (Exception e) {
			log4j.info("selectStatusTypesInEditSubResourceSectionsPge function failed"
					+ e);
			strReason = "selectStatusTypesInEditSubResourceSectionsPge function failed"
					+ e;
		}
		return strReason;
	}

	/***************************************************************
	'Description 	:verifySTInViewResDetailUnderSubResource
	'Precondition 	:None
	'Arguments  	:selenium,strSection,strStatType,strSectionValue
	'Returns  		:strReason
	'Date    		:28-Feb-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetailUnderSubResource(Selenium selenium,
			String strSubResourecType, String strStatusType[],
			String strSubResource) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				String strSubRSsection = selenium.getText("//table[@summary"
						+ "='Status for " + strSubResourecType + "']/"
						+ "preceding-sibling::div[1]/h1");
				assertTrue(strSubRSsection.contains("Sub-Resources"));
				log4j.info("'Sub-resources' section is displayed. ");

				try {
					assertTrue(selenium
							.isElementPresent("//table[@summary='Status for "
									+ "" + strSubResourecType
									+ "']/thead/tr/th" + "/a[text()='"
									+ strSubResourecType + "']/"
									+ "ancestor::table/tbody/tr/td/a[text()="
									+ "'" + strSubResource + "']"));

					log4j.info("Sub-resource '" + strSubResource
							+ "' is displayed under '" + strSubResourecType);

					for (int i = 0; i < strStatusType.length; i++) {
						try {
							assertTrue(selenium
									.isElementPresent("//table"
											+ "[@summary='Status for "
											+ strSubResourecType
											+ "']"
											+ "/thead/tr/th/a[text()='"
											+ strSubResourecType
											+ "']"
											+ "/parent::th/following-sibling::th/a[text()='"
											+ strStatusType[i] + "']"));

							log4j.info("Sub-resource '" + strSubResource
									+ "' is  displayed under '"
									+ strSubResourecType
									+ " along with status type "
									+ strStatusType[i]);
						} catch (AssertionError Ae) {
							log4j.info("Sub-resource '" + strSubResource
									+ "' is NOT displayed under '"
									+ strSubResourecType
									+ " along with status type "
									+ strStatusType[i]);
							strReason = "Sub-resource '" + strSubResource
									+ "' is NOT displayed under '"
									+ strSubResourecType
									+ " along with status type "
									+ strStatusType[i];
						}

					}

				} catch (AssertionError Ae) {
					log4j.info("Sub-resource '" + strSubResource
							+ "' is NOT displayed under '" + strSubResourecType);
					strReason = "Sub-resource '" + strSubResource
							+ "' is NOT displayed under '" + strSubResourecType;
				}

			} catch (AssertionError Ae) {
				log4j.info("'Sub-resources' section is NOT displayed. ");
				strReason = "'Sub-resources' section is NOT displayed. ";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTInViewResDetailUnderSubResource "
					+ e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description	:Verify new View is Created
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	public String createViewMandFields(Selenium selenium, String strViewName,
			String strVewDescription, String strViewType) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			//View Name
			selenium.type(propElementDetails.getProperty("View.CreateNewView.Name"), strViewName);
			// View Description
			selenium.type(propElementDetails.getProperty("View.CreateNewView.Description"), strVewDescription);
			// View Type																	
			selenium.select(propElementDetails.getProperty("View.CreateNewView.ViewType"), "label=" + strViewType);

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function createView " + e;
		}
		return strReason;
	}
	
	/***************************************************************
    'Description :Verify Updated status value is displayed in View resource detail screen
    'Precondition :None
    'Arguments  :selenium,strSection,strStatType,strUpdtVal,strComment
    'Returns  :strReason
    'Date    :05-March-2013
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyOnlyUpdSTValInResDetail(Selenium selenium,
			String strStatType[], String strUpdtVal[]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			for (int i = 0; i < strStatType.length; i++) {
				try {
					assertEquals(
							selenium
									.getText("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
											+ strStatType[i]
											+ "']/ancestor::tr/td[3]"),
							strUpdtVal[i]);

					log4j.info("The status value is displayed on"
							+ " the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The status value is NOT displayed on"
							+ " the view resource detail screen. ");
					strReason = "The status value is NOT displayed on"
							+ " the view resource detail screen. ";

				}

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyOnlyUpdSTValInResDetail "
					+ e.toString();
		}
		return strReason;
	}

	
	  /***************************************************************
	   'Description :Filll and save update status value for NEDOC Calculation
	   'Arguments   :selenium,strUpdateValue,strSTValue,
	   'Returns     :strReason
	   'Date        :11-June-2012
	   'Author      :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String fillUpdateNEDOCSTAndSave(Selenium selenium,
			String strUpdateValue[], String strSTValue, boolean blnsave)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Update Status",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status Page is  displayed");
				if (selenium.isChecked("css=#st_" + strSTValue) == false) {
					selenium.click("css=#st_" + strSTValue);
				}
				// Enter the values for fields
				selenium.type("css=#edPatients" + strSTValue, strUpdateValue[0]);
				selenium.type("css=#edAdmits" + strSTValue, strUpdateValue[1]);
				selenium.type("css=#lastDoorToBedTime" + strSTValue,
						strUpdateValue[2]);
				selenium.type("css=#criticalCarePatients" + strSTValue,
						strUpdateValue[3]);
				selenium.type("css=#longestEdAdmit" + strSTValue,
						strUpdateValue[4]);
				selenium.type("css=#edNumber" + strSTValue, strUpdateValue[5]);
				selenium.type("css=#ipNumber" + strSTValue, strUpdateValue[6]);

				// click caluclate button
				selenium.click("//div[@id='st_" + strSTValue
						+ "section']/div[2]/div[3]/input");
				
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

			if (blnsave) {
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate fillUpdateNEDOCSTAndSave "
					+ e.toString();
		}
		return strReason;
	}

	  /***************************************************************
	   'Description :Filll and save update status value for NEDOC Calculation
	   'Arguments   :selenium,strUpdateValue,strSTValue,
	   'Returns     :strReason
	   'Date        :11-June-2012
	   'Author      :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String fillUpdateNEDOCSTAndSaveWithoutCalcButton(Selenium selenium,
			String strUpdateValue[], String strSTValue, boolean blnsave)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Update Status",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status Page is  displayed");
				if (selenium.isChecked("css=#st_" + strSTValue) == false) {
					selenium.click("css=#st_" + strSTValue);
				}
				// Enter the values for fields
				selenium.type("css=#edPatients" + strSTValue, strUpdateValue[0]);
				selenium.type("css=#edAdmits" + strSTValue, strUpdateValue[1]);
				selenium.type("css=#lastDoorToBedTime" + strSTValue,
						strUpdateValue[2]);
				selenium.type("css=#criticalCarePatients" + strSTValue,
						strUpdateValue[3]);
				selenium.type("css=#longestEdAdmit" + strSTValue,
						strUpdateValue[4]);
				selenium.type("css=#edNumber" + strSTValue, strUpdateValue[5]);
				selenium.type("css=#ipNumber" + strSTValue, strUpdateValue[6]);
				
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

			if (blnsave) {
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate fillUpdateNEDOCSTAndSave "
					+ e.toString();
		}
		return strReason;
	}
	 /***************************************************************
	   'Description :Filll and save update status value for Saturation score
	   'Arguments   :selenium,strUpdateValue,strSTValue,
	   'Returns     :strReason
	   'Date        :19-June-2013
	   'Author      :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String fillAndSavUpdateSSTAndVerifyValue(Selenium selenium,
			String strUpdateValue[], String strSTValue, String strcalcValue,
			boolean blnSave) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Update Status",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status Page is  displayed");
				if (selenium.isChecked("css=#st_" + strSTValue) == false) {
					selenium.click("css=#st_" + strSTValue);
				}
				// Enter the values for fields
				selenium.type("css=#edBedsOccupied" + strSTValue,
						strUpdateValue[0]);
				selenium.type("css=#lobbyPatients" + strSTValue,
						strUpdateValue[1]);
				selenium.type("css=#ambulancePatients" + strSTValue,
						strUpdateValue[2]);
				selenium.type("css=#admitsGeneral" + strSTValue,
						strUpdateValue[3]);
				selenium.type("css=#admitsIcu" + strSTValue, strUpdateValue[4]);
				selenium.type("css=#oneOnOnePatients" + strSTValue,
						strUpdateValue[5]);
				selenium.type("css=#shortStaffRn" + strSTValue,
						strUpdateValue[6]);
				selenium.type("css=#edBedsAssigned" + strSTValue,
						strUpdateValue[7]);
				selenium.type("css=#lobbyCapacity" + strSTValue,
						strUpdateValue[8]);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='formElemLong']"
									+ "/span[@id='saturationScoreDisplay"
									+ strSTValue + "'][text()='--']"));
					log4j.info("Scores are not auto-calculated as soon as the values are entered "
							+ "in all the fields");

				} catch (AssertionError Ae) {
					log4j.info("Scores are auto-calculated as soon as the values are entered "
							+ "in all the fields");
					strReason = "Scores are auto-calculated as soon as the values are entered "
							+ "in all the fields";
				}

				// click caluclate button
				selenium.click("//div[@id='st_"+strSTValue+"section']/div[5]/input");
				int intCnt = 0;
				do {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@class='formElemLong']"
									+ "/span[@id='saturationScoreDisplay"
									+ strSTValue + "'][text()='" + strcalcValue
									+ "']"));
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
							.isElementPresent("//div[@class='formElemLong']"
									+ "/span[@id='saturationScoreDisplay"
									+ strSTValue + "'][text()='" + strcalcValue
									+ "']"));
					log4j.info("Scores  calculated only after clicking on 'Calculate' button.");

				} catch (AssertionError Ae) {
					log4j.info("Scores Not calculated only after clicking on 'Calculate' button.");
					strReason = "Scores Not calculated only after clicking on 'Calculate' button.";
				}
				if (blnSave) {
					selenium.click("css=input[value='Save']");
					selenium.waitForPageToLoad(gstrTimeOut);
				}
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate fillAndSavUpdateSSTAndVerifyValue "
					+ e.toString();
		}
		return strReason;
	}
	
	  /***************************************************************
	   'Description :Filll and save update status value for NEDOC Calculation
	   'Arguments   :selenium,strUpdateValue,strSTValue,
	   'Returns     :strReason
	   'Date        :11-June-2012
	   'Author      :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String fillAndSavUpdateNEDOCAndVerifyValue(Selenium selenium,
			String strUpdateValue[], String strSTValue, String strcalcValue, boolean blnsave)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Update Status",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status Page is  displayed");
				if (selenium.isChecked("css=#st_" + strSTValue) == false) {
					selenium.click("css=#st_" + strSTValue);
				}
				// Enter the values for fields
				selenium.type("css=#edPatients" + strSTValue, strUpdateValue[0]);
				selenium.type("css=#edAdmits" + strSTValue, strUpdateValue[1]);
				selenium.type("css=#lastDoorToBedTime" + strSTValue,
						strUpdateValue[2]);
				selenium.type("css=#criticalCarePatients" + strSTValue,
						strUpdateValue[3]);
				selenium.type("css=#longestEdAdmit" + strSTValue,
						strUpdateValue[4]);
				selenium.type("css=#edNumber" + strSTValue, strUpdateValue[5]);
				selenium.type("css=#ipNumber" + strSTValue, strUpdateValue[6]);

			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@class='formElemLong']"
								+ "/span[@id='saturationScoreDisplay"
								+ strSTValue + "'][text()='--']"));
				log4j.info("Scores are not auto-calculated as soon as the values are entered "
						+ "in all the fields");

			} catch (AssertionError Ae) {
				log4j.info("Scores are auto-calculated as soon as the values are entered "
						+ "in all the fields");
				strReason = "Scores are auto-calculated as soon as the values are entered "
						+ "in all the fields";
			}
			
			// click caluclate button
			selenium.click("//div[@id='st_"+strSTValue+"section']/div[2]/div[3]/input");

			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='formElemLong']"
									+ "/span[@id='saturationScoreDisplay"
									+ strSTValue + "'][text()='" + strcalcValue
									+ "']"));
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
						.isElementPresent("//div[@class='formElemLong']"
								+ "/span[@id='saturationScoreDisplay"
								+ strSTValue + "'][text()='" + strcalcValue
								+ "']"));
				log4j.info("Scores  calculated only after clicking on 'Calculate' button.");

			} catch (AssertionError Ae) {
				log4j.info("Scores Not calculated only after clicking on 'Calculate' button.");
				strReason = "Scores Not calculated only after clicking on 'Calculate' button.";
			}
			if (blnsave) {
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate fillUpdateNEDOCSTAndSave "
					+ e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description	:navigate to view resource detail page
					section.
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:strReason
	'Date	 		:18-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	public String navToSubResViewResourceDetailPage(Selenium selenium,
			String strSubResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			int intCnt=0;
			   do{
			    try {

			     assertTrue(selenium.isElementPresent("link="+strSubResource));
			     break;
			    }catch(AssertionError Ae){
			     Thread.sleep(1000);
			     intCnt++;
			    
			    } catch (Exception Ae) {
			     Thread.sleep(1000);
			     intCnt++;
			    }
			   }while(intCnt<60);
			// click on resource link
			selenium
					.click("link="+strSubResource);
			try {
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (Exception e) {

			}

			intCnt = 0;
			do {
				try {

					assertEquals("View Resource Detail", selenium
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
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertTrue(selenium.isElementPresent("//div/h1[@id='r_name'][text()='"+strSubResource+"']"));
				log4j.info("View Resource Detail screen of "+strSubResource+" is displayed.");

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen of '"+strSubResource+"' is NOT displayed.");
				strReason = "View Resource Detail screen of '"+strSubResource+"' is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToViewResourceDetailPage "
					+ e.toString();
		}
		return strReason;
	}
	
	  /***************************************************************
	   'Description :Filll and save update status value for NEDOC Calculation
	   'Arguments   :selenium,strUpdateValue,strSTValue,
	   'Returns     :strReason
	   'Date        :11-June-2012
	   'Author      :QSG
	   '---------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                     <Name>
	   ***************************************************************/

	public String fillAndSaveUpdateNEDOCSTWithComments(Selenium selenium,
			String strUpdateValue[], String strSTValue,String strComments, boolean blnsave)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Update Status",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Update Status Page is  displayed");
				if (selenium.isChecked("css=#st_" + strSTValue) == false) {
					selenium.click("css=#st_" + strSTValue);
				}
				// Enter the values for fields
				selenium.type("css=#edPatients" + strSTValue, strUpdateValue[0]);
				selenium.type("css=#edAdmits" + strSTValue, strUpdateValue[1]);
				selenium.type("css=#lastDoorToBedTime" + strSTValue,
						strUpdateValue[2]);
				selenium.type("css=#criticalCarePatients" + strSTValue,
						strUpdateValue[3]);
				selenium.type("css=#longestEdAdmit" + strSTValue,
						strUpdateValue[4]);
				selenium.type("css=#edNumber" + strSTValue, strUpdateValue[5]);
				selenium.type("css=#ipNumber" + strSTValue, strUpdateValue[6]);

				selenium.type("css=#comment_"+strSTValue, strComments);
				
				// click caluclate button
				selenium.click("//div[@id='st_" + strSTValue
						+ "section']/div[2]/div[3]/input");
					   
			} catch (AssertionError Ae) {
				strReason = "Update Status Page is NOT displayed" + Ae;
				log4j.info("Update Status Page is NOT displayed" + Ae);
			}

			if (blnsave) {
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navigate fillUpdateNEDOCSTAndSave "
					+ e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description :Verify Updated status value is displayed in View resource detail screen with comments
	'Precondition :None
	'Arguments  :selenium,strSection,strStatType,strUpdtVal,strComment
	'Returns  :strReason
	'Date    :06-sep-2012
	'Author   :QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetailFalse(Selenium selenium, String strSection,
			String strStatTypep[]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(
						selenium
								.getText("//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a"),
						strSection);
				log4j.info("Section " + strSection
						+ " is displayed in View Resource Detail screen");

				for (String s : strStatTypep) {
					try {
						assertFalse(selenium
								.isElementPresent("//table[starts-with(@id," +
										"'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ s + "']"));

						log4j
								.info("The Status Type "+s+" is Not displayed on the " +
										"view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j
								.info("The Status Type "+s+" is  displayed on" +
										" the view resource detail screen. ");
						strReason = "The Status Type "+s+" is  displayed on " +
								"the view resource detail screen. ";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Section " + strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section " + strSection
						+ " is NOT displayed in View Resource Detail screen";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTViewInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	/***************************************************************
	'Description 	:verifySTInViewResDetailUnderSubResource
	'Precondition 	:None
	'Arguments  	:selenium,strSection,strStatType,strSectionValue
	'Returns  		:strReason
	'Date    		:28-Feb-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetailUnderSubResourceFalse(Selenium selenium,
			String strSubResourecType, String strStatusType[],
			String strSubResource) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				String strSubRSsection = selenium.getText("//table[@summary"
						+ "='Status for " + strSubResourecType + "']/"
						+ "preceding-sibling::div[1]/h1");
				assertTrue(strSubRSsection.contains("Sub-Resources"));
				log4j.info("'Sub-resources' section is displayed. ");

				try {
					assertTrue(selenium
							.isElementPresent("//table[@summary='Status for "
									+ "" + strSubResourecType
									+ "']/thead/tr/th" + "/a[text()='"
									+ strSubResourecType + "']/"
									+ "ancestor::table/tbody/tr/td/a[text()="
									+ "'" + strSubResource + "']"));

					log4j.info("Sub-resource '" + strSubResource
							+ "' is displayed under '" + strSubResourecType);

					for (int i = 0; i <strStatusType.length; i++) {
						try {
							assertFalse(selenium
									.isElementPresent("//table"
											+ "[@summary='Status for "
											+ strSubResourecType
											+ "']"
											+ "/thead/tr/th/a[text()='"
											+ strSubResourecType
											+ "']"
											+ "/parent::th/following-sibling::th/a[text()='"
											+ strStatusType[i] + "']"));

							log4j.info("Status type : "+strStatusType[i] +" not displayed in  Sub resource detail");
						} catch (AssertionError Ae) {
							log4j.info("Status type : "+strStatusType[i] +"  displayed in  Sub resource detail");
							strReason = "Status type : "+strStatusType[i] +"  displayed in  Sub resource detail";
						}

					}

				} catch (AssertionError Ae) {
					log4j
							.info("Sub-resource '" + strSubResource
									+ "' is NOT displayed under '"
									+ strSubResourecType);
					strReason = "Sub-resource '" + strSubResource
							+ "' is NOT displayed under '" + strSubResourecType;
				}

			} catch (AssertionError Ae) {
				log4j.info("'Sub-resources' section is NOT displayed. ");
				strReason = "'Sub-resources' section is NOT displayed. ";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTInViewResDetailUnderSubResource "
					+ e.toString();
		}
		return strReason;
	}
	 /***************************************************************
    'Description :check ST View cutom table
    'Precondition :None
    'Arguments  :selenium, statustype
    'Returns  :strReason
    'Date    :12-Nov-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/
	public String checkSTInViewCustTablFalse(Selenium selenium,
			String strRT, String strRS, String[] strStatTypeArr)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			for (int i = 0; i < strStatTypeArr.length; i++) {

				try {
					assertEquals(strStatTypeArr[i], selenium
							.getText("//table[starts-with(@id,'rgt')]"
									+ "/thead/tr/th[2][text()='"
									+ strRT + "']"
									+ "/ancestor::tr/th[" + i + "+3]"));
					log4j.info(strStatTypeArr[i]
							+ "is displayed for Resource" + strRS + " in Custom View - Table");
					strReason = strReason +strStatTypeArr[i]
							+ "is  displayed for Resource" + strRS + " in Custom View - Table";
				} catch (AssertionError ae) {
					log4j.info(strStatTypeArr[i]
							+ "is  Not displayed for Resource" + strRS +" in Custom View - Table");						
				}

			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function check ST in view custom view table "
					+ e.toString();
		}
		return strReason;
	}
	
	//start//navToEditResDetailOfSubRes//
	/***************************************************************
	'Description	:navigate to edit resource detail from user view
					section.
	'Precondition	:None
	'Arguments		:selenium,strResource
	'Returns		:strReason
	'Date	 		:15-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	public String navToEditResDetailOfSubRes(Selenium selenium,
			String strResource) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			// click on edit resource details
			selenium.click("link=edit resource details");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Sub-Resource of "+strResource+"",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Edit Sub-Resource of "+strResource+" page is displayed");
			} catch (AssertionError Ae) {
				strReason = "Edit Sub-Resource of "+strResource+" page is NOT displayed" + Ae;
				log4j.info("Edit Sub-Resource of "+strResource+" page is NOT displayed" + Ae);
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function navToEditResDetailOfSubRes "
					+ e.toString();
		}
		return strReason;
	}
	//end//navToEditResDetailOfSubRes//
	

	/***************************************************************
    'Description :Verify Updated status value is displayed in View resource detail screen with comments
    'Precondition :None
    'Arguments  :selenium,strSection,strStatType,strUpdtVal,strComment
    'Returns  :strReason
    'Date    :06-sep-2012
    'Author   :QSG
    '---------------------------------------------------------------
    'Modified Date                            Modified By
    '<Date>                                     <Name>
    ***************************************************************/

	public String verifyUpdSTValInResDetailOfSubRes(Selenium selenium,
			String strSubRTValue, String strUpdtVal) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {

			try {
				assertTrue(selenium.isElementPresent("//table[@id='rtt_"
						+ strSubRTValue + "']/tbody/tr/td[text()='"
						+ strUpdtVal + "']"));
				log4j.info("Updated values for status types is displayed under sub-resource section.");
			} catch (AssertionError Ae) {

				log4j.info("Updated values for status types is NOT displayed under sub-resource section.");
				strReason = "Updated values for status types is NOT displayed under sub-resource section.";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifyUpdSTValInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description 	:verifySTInViewResDetailUnderSubResource
	'Precondition 	:None
	'Arguments  	:selenium,strSection,strStatType,strSectionValue
	'Returns  		:strReason
	'Date    		:28-Feb-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verfySubResourceDetailsInViewResDetailFalse(
			Selenium selenium, String strSubResourecType,
			String strStatusType[], String strSubResource) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertFalse(selenium
						.isElementPresent("//table[@summary='Status for " + ""
								+ strSubResourecType + "']/thead/tr/th"
								+ "/a[text()='" + strSubResourecType + "']/"
								+ "ancestor::table/tbody/tr/td/a[text()=" + "'"
								+ strSubResource + "']"));

				log4j.info("Sub-resource '" + strSubResource
						+ "' is NOT displayed under '" + strSubResourecType);

				for (int i = 0; i < strStatusType.length; i++) {
					try {
						assertFalse(selenium
								.isElementPresent("//table"
										+ "[@summary='Status for "
										+ strSubResourecType
										+ "']"
										+ "/thead/tr/th/a[text()='"
										+ strSubResourecType
										+ "']"
										+ "/parent::th/following-sibling::th/a[text()='"
										+ strStatusType[i] + "']"));

						log4j.info("Status type : " + strStatusType[i]
								+ " NOT displayed in  Sub resource detail");
					} catch (AssertionError Ae) {
						log4j.info("Status type : " + strStatusType[i]
								+ "  displayed in  Sub resource detail");
						strReason = "Status type : " + strStatusType[i]
								+ "  displayed in  Sub resource detail";
					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Sub-resource '" + strSubResource
						+ "' is displayed under '" + strSubResourecType);
				strReason = "Sub-resource '" + strSubResource
						+ "' is  displayed under '" + strSubResourecType;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verfySubResourceDetailsInViewResDetailFalse "
					+ e.toString();
		}
		return strReason;
	}
	//start//vrfySubResourceSectionInViewResDetail//
	/***************************************************************
	'Description 	:vrfySubResourceSectionInViewResDetail
	'Precondition 	:None
	'Arguments  	:selenium,strSubResourecSec
	'Returns  		:strReason
	'Date    		:28-Feb-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String vrfySubResourceSectionInViewResDetail(Selenium selenium,
			boolean blnSubResourecSec) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			if (blnSubResourecSec) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='clearFix']/h1[contains(text(),'Sub-Resources')]"));
					log4j.info("'Sub-resources' Section is displayed. ");
				} catch (AssertionError Ae) {
					log4j.info("'Sub-resources' Section is NOT displayed. ");
					strReason = "'Sub-resources' Section is NOT displayed. ";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@class='clearFix']/h1[contains(text(),'Sub-Resources')]"));
					log4j.info("'Sub-resources' Section is NOT displayed. ");
				} catch (AssertionError Ae) {
					log4j.info("'Sub-resources' Section is displayed. ");
					strReason = "'Sub-resources' Section is displayed. ";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTInViewResDetailUnderSubResource "
					+ e.toString();
		}
		return strReason;
	}
	//end//vrfySubResourceSectionInViewResDetail//
	
	//start//vrfyAllUpdateLinks//
	/***************************************************************
	'Description 	:vrfySubResourceSectionInViewResDetail
	'Precondition 	:None
	'Arguments  	:selenium,blnSelAll,blnClrAll,blnShowAll
	'Returns  		:strReason
	'Date    		:28-Feb-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String vrfyAllUpdateLinks(Selenium selenium, boolean blnSelAll,
			boolean blnClrAll, boolean blnShowAll) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnSelAll) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='left']/span[@id='selectAll']"));
					log4j.info("'Select All' link is left aligned dislplayed on the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info("'Select All' link is NOT left aligned displayed on the update status screen.");
					strReason = "'Select All'link is NOT left aligned displayed on the update status screen.";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@class='left']/span[@id='selectAll']"));
					log4j.info("'Select All' link is NOT left aligned displayed on the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info("'Select All' link is left aligned displayed on the update status screen.");
					strReason = "'Select All'link is left aligned displayed on the update status screen.";
				}
			}

			if (blnClrAll) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='left']/span[@id='clearAll']"));
					log4j.info("''Clear All'' link is left aligned displayed on the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info("''Clear All'' link is NOT left aligned displayed on the update status screen.");
					strReason = "''Clear All''link is NOT left aligned displayed on the update status screen.";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@class='left']/span[@id='clearAll']"));
					log4j.info("'Clear All' link is NOT left aligned displayed on the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info("'Clear All' link is left aligned displayed on the update status screen.");
					strReason = "'Clear All' link is left aligned displayed on the update status screen.";
				}
			}

			if (blnShowAll) {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@class='left']/span[@class='selectionLink showAll']"));
					log4j.info("'Show All Statuses'  link is left aligned displayed on the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info("'Show All Statuses' link is NOT left aligned displayed on the update status screen.");
					strReason = "'Show All Statuses' link is NOT left aligned displayed on the update status screen.";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//div[@class='left']/span[@class='selectionLink showAll']"));
					log4j.info("'Show All Statuses' ' link is NOT left aligned displayed on the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info("'Show All Statuses' link is left aligned displayed on the update status screen.");
					strReason = "'Show All Statuses' link is left aligned displayed on the update status screen.";
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function vrfyAllUpdateLinks " + e.toString();
		}
		return strReason;
	}
	//end//vrfyAllUpdateLinks//
	
	//start//clickSelectAll//
	/***************************************************************
	'Description 	:click SelectAll link in update status screen
	'Precondition 	:None
	'Arguments  	:selenium
	'Returns  		:strReason
	'Date    		:28-Feb-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String clickSelectAll(Selenium selenium) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on SelectAll Link
			selenium.click("//div[@class='left']/span[@id='selectAll']");
			Thread.sleep(5000);
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function clickSelectAll "
					+ e.toString();
		}
		return strReason;
	}
	//end//clickSelectAll//
	//start//clickSelectAll//
	/***************************************************************
	'Description 	:click SelectAll link in update status screen
	'Precondition 	:None
	'Arguments  	:selenium
	'Returns  		:strReason
	'Date    		:28-Feb-2013
	'Author   		:QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String clickClearAll(Selenium selenium) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			// Click on SelectAll Link
			selenium.click("//div[@class='left']/span[@id='clearAll']");
			Thread.sleep(5000);

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function clickClearAll " + e.toString();
		}
		return strReason;
	}
		//end//clickSelectAll//
	//start//chkStatTypesSelOrNotInUpdateScreen//
  /***************************************************************
 'Description :Filll and save update status value
 'Precondition :None
 'Arguments  :selenium, blnUserInfo,strUserName,strPassword,strUpdatTxtValue
 'Returns  :strReason
 'Date    :7-June-2012
 'Author   :QSG
 '---------------------------------------------------------------
 'Modified Date                            Modified By
 '<Date>                                     <Name>
 ***************************************************************/

	public String chkStatTypesSelOrNotInUpdateScreen(Selenium selenium,
			String strSTValue,String strStattype, boolean blnCheck) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnCheck) {
				try {
					assertTrue(selenium
							.isChecked("css=#st_"+strSTValue));
					log4j.info(strStattype +"is selcted and expanded in the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info(strStattype +"is NOT selcted and expanded in the update status screen.");
					strReason = strStattype +"is NOT selcted and expanded in the update status screen.";
				}
			} else {
				try {
					assertFalse(selenium
							.isChecked("css=#st_"+strSTValue));
					log4j.info(strStattype +"is NOT selcted and Not expanded in the update status screen.");
				} catch (AssertionError Ae) {
					log4j.info(strStattype +"is selcted and expanded in the update status screen.");
					strReason = strStattype +"is selcted and expanded in the update status screen.";
				}
			}
			
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function chkStatTypesSelOrNotInUpdateScreen "
					+ e.toString();
		}
		return strReason;
	}
	//end//chkStatTypesSelOrNotInUpdateScreen//
	
	//start//verifyStatusTypesInNewSection//
	/***************************************************************
	'Description	:Drag and drop the status type to particular section and save
	'Precondition	:None
	'Arguments		:selenium,strStatType,strSection,blnCreateSec
	'Returns		:strReason
	'Date	 		:23-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'07-Sep-12                                   <Name>
	 * @throws Exception 
	***************************************************************/

	public String verifyStatusTypesInNewSection(Selenium selenium,
			String strStatType[], String strSection) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			int intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
					+ strSection + "')]"));
					break;
				} catch (AssertionError Ae) {
					intCnt++;
					Thread.sleep(1000);
				} catch (Exception e) {

					intCnt++;
					Thread.sleep(1000);

				}
			} while (intCnt < 10);
			

			selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"
					+ strSection + "')]");
			
			intCnt = 0;
			do {
				try {
					assertTrue(selenium
							.isElementPresent("//li[@class='item']/div[contains(text(),'"
									+ strStatType[1] + "')]"));
					break;
				} catch (AssertionError Ae) {
					intCnt++;
					Thread.sleep(1000);
				} catch (Exception e) {

					intCnt++;
					Thread.sleep(1000);

				}
			} while (intCnt < 10);

			for (String strStType : strStatType) {
				try {
					assertTrue(selenium
							.isElementPresent("//li[@class='item']/div[contains(text(),'"
									+ strStType + "')]"));
					assertTrue(selenium
							.isVisible("//li[@class='item']/div[contains(text(),'"
									+ strStType + "')]"));
					log4j.info(strStType
							+ " status type is displayed in section "
							+ strSection);

				} catch (AssertionError ae) {
					log4j.info(strStType
							+ " status type is NOT displayed in section "
							+ strSection);
					strReason = strReason + " " + strStType
							+ " status type is NOT displayed in section "
							+ strSection;
				}
			}

		} catch (Exception e) {
			log4j.info("verifyStatusTypesInNewSection function failed" + e);
			strReason = "verifyStatusTypesInNewSection function failed" + e;
		}
		return strReason;
	}
	//end//verifyStatusTypesInNewSection//
	
	/***************************************************************
	'Description	:Get ST count in Edit Resource Detail View Sections screen
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:29/07/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name> 
	***************************************************************/
	
	public String[] GetSTCountInEditResDetailViewSec(Selenium selenium,String pStrSection)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		String strReturn[] = new String[2];
		String strCount = "";
		try {
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"+pStrSection+"')]/span"));
					break;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			
			strCount=selenium.getText("//ul[@id='groups']/li/div/span[contains(text(),'"+pStrSection+"')]/span");
			strCount=strCount.replace("(", "");
			strCount=strCount.replace(")", "");
			strCount=strCount.trim();

		} catch (Exception e) {
			log4j.info("GetSTCountInEditResDetailViewSec function failed" + e);
			strReason = "GetSTCountInEditResDetailViewSec function failed" + e;
		}
		strReturn[0]=strReason;
		strReturn[1]=strCount;
		
		return strReturn;
	}
	/***************************************************************
	'Description	:Delete a section
	'Precondition	:None
	'Arguments		:selenium,strSection
	'Returns		:strReason
	'Date	 		:24-sep-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                  <Name>
	 * @throws Exception 
	***************************************************************/
	
	
	public String deleteSectionWithoutSav(Selenium selenium, String strSection)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		String strReason = "";
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			selenium.click("//ul[@id='groups']/li/div/span[contains(text(),'"
					+ strSection + "')]");

			selenium.click("css=a:contains('Delete')");

			try {
				assertFalse(selenium
						.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"
								+ strSection + "')]"));
				log4j.info(strSection + " is not displayed after deleting");

			} catch (AssertionError ae) {
				log4j.info(strSection + " is displayed after deleting");
				strReason = strReason + strSection
						+ " displayed after deleting";
			}

		} catch (AssertionError ae) {
			log4j.info("Section " + strSection + " is NOT displayed");
			strReason = "Section " + strSection + " is NOT displayed";
		}

		return strReason;
	}
	/***************************************************************
	'Description	:Verify ST count in Edit Resource Detail View Sections screen
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:29/07/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name> 
	***************************************************************/
	
	public String VerifySTCountInEditResDetailViewSec(Selenium selenium,String pStrSection,String pStrSTCount,boolean blnSave)
			throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		String strCount = "";
		try {
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//ul[@id='groups']/li/div/span[contains(text(),'"+pStrSection+"')]/span"));
					break;

				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;
				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			
			strCount=selenium.getText("//ul[@id='groups']/li/div/span[contains(text(),'"+pStrSection+"')]/span");
			strCount=strCount.replace("(", "");
			strCount=strCount.replace(")", "");
			strCount=strCount.trim();

			try {
				assertEquals(pStrSTCount,strCount);
				log4j.info(pStrSection+ " section count is displayed as : "+pStrSTCount);
			} catch (AssertionError Ae) {
				log4j.info(pStrSection+ " section count is Not displayed as : "+pStrSTCount);
				strReason = pStrSection+ " section count is Not displayed as : "+pStrSTCount;
			} 
			if(blnSave){
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}
		} catch (Exception e) {
			log4j.info("GetSTCountInEditResDetailViewSec function failed" + e);
			strReason = "GetSTCountInEditResDetailViewSec function failed" + e;
		}
		
		
		return strReason;
	}
	
	//start//checkStatHyperLinkOrNotInViewScreens//
	/*******************************************************************************************
	' Description: check Statattypes  HyperLink Or NotI n View Screens.
	' Precondition: N/A 
	' Arguments: selenium, strResourceValue, strStatusTypeValue, blnCheck, strResourceName, strStatusTypeName
	' Returns: String 
	' Date: 23/08/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String checkStatHyperLinkOrNotInViewScreens(Selenium selenium,
			String strResourceValue, String strStatusTypeValue,
			boolean blnCheck, String strResourceName, String strStatusTypeName)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");

		if (blnCheck) {
			try {
				assertTrue(selenium.isElementPresent("//td[@id='v_"
						+ strResourceValue + "_" + strStatusTypeValue + "']"
						+ "[@class='addToolTipText statusWhite editable']"));
				log4j.info("For Status Type " + strStatusTypeName
						+ "Hand cursor is "
						+ " displayed (Status cell is  hyper linked)" + "."
						+ "And Associated with Resource" + strResourceName);
			} catch (AssertionError Ae) {
				log4j.info("For Status Type "
						+ strStatusTypeName
						+ "Hand cursor is not  displayed (Status cell is not  hyper linked)."
						+ "And Associated with Resource" + strResourceName);
				strReason = "Hand cursor is not displayed (Status cell is not hyper linked).";
			}
		} else {
			try {
				assertTrue(selenium.isElementPresent("//td[@id='v_"
						+ strResourceValue + "_" + strStatusTypeValue + "']"
						+ "[@class='addToolTipText statusWhite']"));
				log4j.info("For Status Type "
						+ strStatusTypeName
						+ "Hand cursor is not  displayed (Status cell is not  hyper linked)."
						+ "And Associated with Resource" + strResourceName);
			} catch (AssertionError Ae) {
				log4j.info("Hand cursor is not  displayed (Status cell is not hyper linked).");
				strReason = "Hand cursor is not  displayed (Status cell is not hyper linked).";
			}
		}

		return strReason;
	}
	//end//checkStatHyperLinkOrNotInViewScreens//
	
	/*************************************************************************
	'Description	:navigate to Edit Sub Resource Detail View Sections screen
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:28-Feb-2013
	'Author			:QSG
	'-------------------------------------------------------------------------
	'Modified Date                                              Modified By
	'<Date>                                                     <Name>
	**************************************************************************/
	public String verRTInEditSubResourceSectionsPge(Selenium selenium,
			String strSubResourceType, String strSuResourceTypeValue,
			boolean blnSubRes) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			if (blnSubRes) {
				try {
					assertTrue(selenium
							.isElementPresent("//li[@id='g_"
									+ strSuResourceTypeValue
									+ "']"
									+ "/div/span[text()='"
									+ strSubResourceType
									+ "']"
									+ "/following-sibling::input[@name='subresourceSelect'][@value='g_"
									+ strSuResourceTypeValue + "']"));

					log4j.info("Sub-Resoure Type '"
							+ strSubResourceType
							+ "' is listed under it with check box associated with it.");

				} catch (AssertionError Ae) {
					log4j.info("Sub-Resoure Type '"
							+ strSuResourceTypeValue
							+ "' is NOT listed under it with check box associated with it.");
					strReason = "Sub-Resoure Type '"
							+ strSuResourceTypeValue
							+ "' is NOT listed under it with check box associated with it.";
				}
			} else {
				try {
					assertFalse(selenium
							.isElementPresent("//li[@id='g_"
									+ strSuResourceTypeValue
									+ "']"
									+ "/div/span[text()='"
									+ strSubResourceType
									+ "']"
									+ "/following-sibling::input[@name='subresourceSelect'][@value='g_"
									+ strSuResourceTypeValue + "']"));

					log4j.info("Sub-Resoure Type '"
							+ strSubResourceType
							+ "' is listed under it with check box associated with it.");

				} catch (AssertionError Ae) {
					log4j.info("Sub-Resoure Type '"
							+ strSuResourceTypeValue
							+ "' is NOT listed under it with check box associated with it.");
					strReason = "Sub-Resoure Type '"
							+ strSuResourceTypeValue
							+ "' is NOT listed under it with check box associated with it.";
				}
			}
		} catch (Exception e) {
			log4j.info("verRTInEditSubResourceSectionsPge function failed" + e);
			strReason = "verRTInEditSubResourceSectionsPge function failed" + e;
		}
		return strReason;
	}
	/***************************************************************
	'Description	:navigate to Edit Sub Resource Detail View Sections screen
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:strReason
	'Date	 		:28-Feb-2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	 * @throws Exception 
	***************************************************************/
	public String selAndDeselRTInEditSubResourceSectionsPge(Selenium selenium,
			String strSubResourceType, String strSuResourceTypeValue,
			boolean blnSubRT, boolean blnSavSuRS) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			
			selenium.click("//span[text()='" + strSubResourceType + "']");
			Thread.sleep(2000);
			if (blnSubRT) {
				if (selenium.isChecked("//span[text()='" + strSubResourceType
						+ "']/following-sibling::input[4]") == false) {
					selenium.click("//span[text()='" + strSubResourceType
							+ "']/following-sibling::input[4]");
					Thread.sleep(2000);
					log4j.info("Sub resource type "+strSubResourceType+" is selected");
				}
			} else {
				if (selenium.isChecked("//span[text()='" + strSubResourceType
						+ "']/following-sibling::input[4]")) {
					selenium.click("//span[text()='" + strSubResourceType
							+ "']/following-sibling::input[4]");
					Thread.sleep(2000);
					log4j.info("Sub resource type "+strSubResourceType+" is de-selected");
				}
			}
			
			//Click on save
			if (blnSavSuRS) {
				selenium.click(propElementDetails
						.getProperty("View.CreateNewView.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				do {
					try {
						assertEquals("Region Views List",
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
					assertEquals("Region Views List",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Region Views List page is  displayed ");

				} catch (AssertionError Ae) {
					log4j.info(" Region Views List page is NOT displayed " + Ae);
					strReason = "Region Views List page is NOT displayed " + Ae;
				}
			}
		} catch (Exception e) {
			log4j.info("selAndDeselRTInEditSubResourceSectionsPge function failed"
					+ e);
			strReason = "selAndDeselRTInEditSubResourceSectionsPge function failed"
					+ e;
		}
		return strReason;
	}
	 /***************************************************************
	  'Description :enter the fields in update status screen for NST with comments
	  'Precondition :None
	  'Arguments  :selenium, strUpdateValue,strSTValue,strComments
	  'Returns  :strReason
	  'Date    :20-sep-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  ***************************************************************/

	public String verUpdSTvalueInUpdateStatusPage(Selenium selenium,
			String strUpdateValue, String strSTValue, String strComments)
			throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (selenium.isChecked("css=#st_" + strSTValue) == false) {
				selenium.click("css=#st_" + strSTValue);
			}
			try {
				assertEquals(strUpdateValue,
						selenium.getValue("name=status_value_" + strSTValue));
				assertEquals(strComments,
						selenium.getValue("id=comment_" + strSTValue));
				log4j.info(" Update Status screen is displayed where the status value is" +
						" displayed as '"+strUpdateValue+"' and Comments field is as "+strComments+".");

			} catch (AssertionError Ae) {
				log4j.info("Update Status screen is displayed where the status value is" +
						" displayed as '"+strUpdateValue+"' and Comments field is as "+strComments+".");
				strReason = "Update Status screen is displayed where the status value is" +
						" displayed as '"+strUpdateValue+"' and Comments field is as "+strComments+".";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function fillUpdStatusNSTWithComments "
					+ e.toString();
		}
		return strReason;
	}
	/*******************************************************************************
	  'Description :Verify that resource type is present or not present in View page
	  'Precondition :None
	  'Arguments  :selenium,strStatusType,blnCheck
	  'Returns  :strReason
	  'Date    :04-June-2012
	  'Author   :QSG
	  '---------------------------------------------------------------
	  'Modified Date                            Modified By
	  '<Date>                                     <Name>
	  *****************************************************************************/
	public String vfyResrcTypePresOrNotPresInViewPge(Selenium selenium,String strResTypeVal,boolean blnPresent) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			if(blnPresent){
				try{
				assertTrue (selenium.isElementPresent("css=input[name='resourceTypeID'][value='"
								+ strResTypeVal + "']")); 
				log4j.info("Resource type 'RT' is displayed. ");
				}
				catch(AssertionError Ae)
				{
					log4j.info("Resource type 'RT' is NOT displayed. ");
					strReason="Resource type 'RT' is NOT displayed. ";
				}
			}
			else{
				try{
				assertFalse (selenium.isElementPresent("css=input[name='resourceTypeID'][value='"
						+ strResTypeVal + "']")); 
				log4j.info("Resource type 'RT' is NOT displayed. ");

				}catch(AssertionError Ae)
				{
					log4j.info("Resource type 'RT' is displayed. ");
					strReason="Resource type 'RT' is displayed. ";
				}
					
		
		} 
		return strReason;
	}
	/***************************************************************
	'Description :Verify Updated status value is displayed in View resource detail screen with comments
	'Precondition :None
	'Arguments  :selenium,strSection,strStatType,strUpdtVal,strComment
	'Returns  :strReason
	'Date    :06-sep-2012
	'Author   :QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetailForSecTble(Selenium selenium, String strSection,
			String strStatTypep[]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(
						selenium
								.getText("//table[@summary=''][@width='100%']/tbody/tr/td[2]//table[starts-with(@id,'stGroup')]/thead/tr/th[2]/a"),
						strSection);
				log4j.info("Section " + strSection
						+ " is displayed in View Resource Detail screen");

				for (String s : strStatTypep) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[@summary=''][@width='100%']/tbody/tr/td[2]/table[starts-with(@id," +
										"'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ s + "']"));

						log4j
								.info("The Status Type "+s+" is displayed on the " +
										"view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j
								.info("The Status Type "+s+" is NOT displayed on" +
										" the view resource detail screen. ");
						strReason = "The Status Type "+s+" is NOT displayed on " +
								"the view resource detail screen. ";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Section " + strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section " + strSection
						+ " is NOT displayed in View Resource Detail screen";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTViewInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	
	/***************************************************************
	'Description  :Verify Updated status value is displayed in View resource detail screen with comments
	'Precondition :None
	'Arguments    :selenium,strSection,strStatType,strUpdtVal,strComment
	'Returns      :strReason
	'Date         :07-Oct-201
	'Author       :QSG
	'---------------------------------------------------------------
	'Modified Date                            		Modified By
	'<Date>                                   	 	 <Name>
	 ***************************************************************/

	public String verifySTInViewResDetailForThirdTble(Selenium selenium, String strSection,
			String strStatTypep[]) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals(
						selenium
								.getText("//table[@summary=''][@width='100%']/tbody/tr/td[2]/table[2][starts-with(@id,'stGroup')]/thead/tr/th[2]/a"),
						strSection);
				log4j.info("Section " + strSection
						+ " is displayed in View Resource Detail screen");

				for (String s : strStatTypep) {
					try {
						assertTrue(selenium
								.isElementPresent("//table[@summary=''][@width='100%']/tbody/tr/td[2]/table[2][starts-with(@id," +
										"'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ s + "']"));

						log4j
								.info("The Status Type "+s+" is displayed on the " +
										"view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j
								.info("The Status Type "+s+" is NOT displayed on" +
										" the view resource detail screen. ");
						strReason = "The Status Type "+s+" is NOT displayed on " +
								"the view resource detail screen. ";

					}
				}

			} catch (AssertionError Ae) {
				log4j.info("Section " + strSection
						+ " is NOT displayed in View Resource Detail screen");
				strReason = "Section " + strSection
						+ " is NOT displayed in View Resource Detail screen";

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function verifySTViewInResDetail "
					+ e.toString();
		}
		return strReason;
	}
	//start//selOrDeselSTInEditSubResourceSectionsPge//
	/*******************************************************************************************
	' Description	: Select or de-select Status Type in edit sub resource section page.
	' Precondition	: N/A 
	' Arguments		: selenium, strStatusTypeName, strStatusTypeValue, strSuResourceTypeValue, blnST, blnSavSubRS
	' Returns		: String 
	' Date			: 21/10/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/
	public String selOrDeselSTInEditSubResourceSectionsPge(Selenium selenium,
			String strStatusTypeName, String strStatusTypeValue,String strSuResourceTypeValue,
			boolean blnST, boolean blnSavSubRS) throws Exception {
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strReason = "";
		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnST) {
				if (selenium.isChecked("//div[@id='statusTypeId']" +
						"[text()='"+strStatusTypeName+"']/input" )== false) {
					selenium.click("css=input[name='g_"
							+ strSuResourceTypeValue + "'][value='" + strStatusTypeValue + "']");
					Thread.sleep(2000);
					log4j.info("Status Type "+strStatusTypeName+"is selected");
					
				}
			} else {
				if (selenium.isChecked("//div[@id='statusTypeId']" +
						"[text()='"+strStatusTypeName+"']/input")) {
					selenium.click("css=input[name='g_"
							+ strSuResourceTypeValue + "'][value='" + strStatusTypeValue + "']");
					Thread.sleep(2000);
					log4j.info("Status Type "+strStatusTypeName+"is de-selected");
				}
			}
			
			//Click on save
			if (blnSavSubRS) {
				selenium.click(propElementDetails
						.getProperty("View.CreateNewView.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				int intCnt = 0;
				do {
					try {
						assertEquals("Region Views List",
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
					assertEquals("Region Views List",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info(" Region Views List page is  displayed ");

				} catch (AssertionError Ae) {
					log4j.info(" Region Views List page is NOT displayed " + Ae);
					strReason = "Region Views List page is NOT displayed " + Ae;
				}
			}
		} catch (Exception e) {
			log4j.info("selAndDeselSTInEditSubResourceSectionsPge function failed"
					+ e);
			strReason = "selAndDeselSTInEditSubResourceSectionsPge function failed"
					+ e;
		}
		return strReason;
	}
	//end//selOrDeselSTInEditSubResourceSectionsPge//
	
	/**********************************************************************
	'Description	:save Update Status type Value and verify error message
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-Feb-2013
	'Author			:QSG
	'----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***********************************************************************/

	public String saveNedocSTAndVerErrorMsg(Selenium selenium) throws Exception {

		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("UpdateStatus.Save"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText("css=span.emsErrorTitle"));
				assertEquals(
						"In Last Door-to-bed Time, enter the hours in 15-minute increments,"
								+ " such as 1.25.",
						selenium.getText("css=div.emsError > ul > li"));
				log4j.info("The following error occurred on this page: and In Last Door-to-bed Time,"
						+ " enter the hours in 15-minute increments such as 1.25. is displayed");
			} catch (Exception e) {
				log4j.info("The following error occurred on this page: and In Last Door-to-bed Time,"
						+ " enter the hours in 15-minute increments such as 1.25. is NOT displayed");
				strReason = "The following error occurred on this page: and In Last Door-to-bed Time,"
						+ " enter the hours in 15-minute increments such as 1.25. is NOT displayed"
						+ e;
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Failed in function saveNedocSTAndVerErrorMsg " + e;
		}
		return strReason;
	}
	
	/**************************************************************************
	' Description : Canel user and navigating to Regional map view screen page
	' Precondition: N/A 
	' Arguments   : selenium
	' Returns     : String 
	' Date        : 28/06/2012
	' Author      : QSG 
	'--------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	****************************************************************************/

	public String cancelAndNavToRegionalMapView(Selenium selenium)
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
			selenium.click("//input[@value='Cancel']");
			selenium.waitForPageToLoad(gstrTimeOut);

			int intCnt = 0;
			do {
				try {
					assertEquals("Regional Map View",
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
				assertEquals("Regional Map View",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Regional Map View screen is displayed");
			} catch (AssertionError ae) {
				log4j.info("Regional Map View screen is NOT displayed");
				lStrReason = "Regional Map View screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//start//checkButtonsInRegionViewList//
	/*******************************************************************************************
	' Description	: Check 'Create New View','Re-order Views' and 'Customize resource Detail 
	                  View' buttons in 'Region View List'
	' Precondition	: N/A 
	' Arguments		: selenium
	' Returns		: String 
	' Date			: 25/10/2013
	' Author		: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String checkButtonsInRegionViewList(Selenium selenium)
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
			try {
				assertEquals("Region Views List",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info(" Region Views List page is  displayed ");
			} catch (AssertionError Ae) {
				log4j.info(" Region Views List page is NOT displayed " + Ae);
				strReason = "Region Views List page is NOT displayed " + Ae;

			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("View.CreateNewView")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("View.CreateNewView.ReOrderViews")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("RegionView.CustomizeResDetailView")));
				log4j.info(" 'Create New View','Re-order Views' and 'Customize resource Detail View' buttons are available at top left of the screen.  ");
			} catch (AssertionError Ae) {
				log4j.info(" 'Create New View','Re-order Views' and 'Customize resource Detail View' buttons are NOT available at top left of the screen.  "
						+ Ae);
				strReason = "'Create New View','Re-order Views' and 'Customize resource Detail View' buttons are NOT available at top left of the screen.  "
						+ Ae;

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Views.checkButtonsInRegionViewList failed to complete due to "
					+ strReason + "; " + e.toString();
		}

		return strReason;
	}
	// end//checkButtonsInRegionViewList//
}
	
