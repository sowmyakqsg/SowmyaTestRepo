package com.qsgsoft.EMResource.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;

/******************************************************************
' Description :This class includes common functions of interface
' Precondition:
' Date		  :6-April-2012
' Author	  :QSG
'-----------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class Interface {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.StatusTypes");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	ReadData rdExcel;
	String gstrTimeOut = "";
	
	/***********************************************************************
	'Description	:Verify interface List page is displayed
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String navToInterfaceList(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.mouseOver(propElementDetails
					.getProperty("SetUP.SetUpLink"));

			selenium.click(propElementDetails
					.getProperty("Prop1901"));

			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Interface List", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Interface List page is displayed");

			} catch (AssertionError Ae) {

				strErrorMsg = "Interface List page is NOT displayed" + Ae;
				log4j.info("Interface List page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToInterfaceList function failed" + e);
			strErrorMsg = "navToInterfaceList function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify  headers in interface List page 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String varHeadersInInterfaceList(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[1]"));
				log4j.info("1. Action header is displayed in interface list");
			} catch (AssertionError Ae) {
				log4j.info("Action header is NOT displayed in interface list");
				strErrorMsg ="Action header is NOT displayed in interface list";

			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[2]"));
				log4j.info("Name  header is displayed in interface list");
			} catch (AssertionError Ae) {
				log4j.info("Name  header is NOT displayed in interface list");
				strErrorMsg ="Name  header is NOT displayed in interface list";

			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[3]"));
				log4j.info("Active header is displayed in interface list");
			} catch (AssertionError Ae) {
				log4j.info("Active  header is NOT displayed in interface list");
				strErrorMsg ="Active  header is NOT displayed in interface list";

			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[4]"));
				log4j.info("Web Services Action  header is displayed in interface list");
			} catch (AssertionError Ae) {
				log4j.info("Web Services Action header is NOT displayed in interface list");
				strErrorMsg ="Web Services Action  header is NOT displayed in interface list";

			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[5]"));
				log4j.info("Type  header is displayed in interface list");
			} catch (AssertionError Ae) {
				log4j.info("Type  header is NOT displayed in interface list");
				strErrorMsg ="Type  header is NOT displayed in interface list";

			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[6]"));
				log4j.info("Schema  header is displayed in interface list");
			} catch (AssertionError Ae) {
				log4j.info("Schema  header is NOT displayed in interface list");
				strErrorMsg ="Schema  header is NOT displayed in interface list";

			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[7]"));
				log4j.info("Description  header is displayed in interface list");
			} catch (AssertionError Ae) {
				log4j.info("Description  header is NOT displayed in interface list");
				strErrorMsg ="Description  header is NOT displayed in interface list";

			}
			try {
				assertTrue(selenium
						.isElementPresent(propElementDetails.getProperty("Prop1904")));
				log4j.info("'Create New Interface' button is available at top left of the screen above column. ");
			} catch (AssertionError Ae) {
				log4j.info("'Create New Interface' button is NOT available at top left of the screen above column. ");
				strErrorMsg ="'Create New Interface' button is NOT available at top left of the screen above column. ";

			}

		} catch (Exception e) {
			log4j.info("varHeadersInInterfaceList function failed" + e);
			strErrorMsg = "varHeadersInInterfaceList function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Navigating o select interface type page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String navToSelInterfaceTypePage(Selenium selenium) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails.getProperty("Prop1904"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Select Interface Type",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Select Interface Type page is displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Select Interface Type:']"));
					log4j.info("Select Interface Type Drop down; is displayed");

				} catch (AssertionError Ae) {
					strErrorMsg = "Select Interface Type Drop down; is NOT displayed";
					log4j.info("Select Interface Type Drop down; is NOT displayed");
				}
				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='2001']"));
					log4j.info("Get CAD Status option is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Get CAD Status option is NOT displayed.";
					log4j.info("Get CAD Status option is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='1001']"));
					log4j.info("Get EDXL-HAVE 1.0 option is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Get EDXL-HAVE 1.0 option is NOT displayed.";
					log4j.info("Get EDXL-HAVE 1.0 option is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='5000']"));
					log4j.info("Get HAvBED 2.5.2 Information option is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Get HAvBED 2.5.2 Information option is NOT displayed.";
					log4j.info("Get HAvBED 2.5.2 Information option is NOT displayed.");
				}

				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='1000']"));
					log4j.info("Get Hospital Status option is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Get Hospital Status option is NOT displayed.";
					log4j.info("Get Hospital Status option is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='5001']"));
					log4j.info("Post HAvBED 2.5.2 Information option is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Post HAvBED 2.5.2 Information option is NOT displayed.";
					log4j.info("Post HAvBED 2.5.2 Information option is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='7000']"));
					log4j.info("Post Resource Detail Status is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Post Resource Detail Status option is NOT displayed.";
					log4j.info("Post Resource Detail Status option is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='2000']"));
					log4j.info("Update CAD Status option is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Update CAD Status option is NOT displayed.";
					log4j.info("Update CAD Status option is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("css=option[value='2000']"));
					log4j.info("Update Resource Availability option is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Update Resource Availability option is NOT displayed.";
					log4j.info("Update Resource Availability option is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("Interface.SelectInterface.Next")));
					assertTrue(selenium
							.isElementPresent(propElementDetails.getProperty("Prop2282")));
					log4j.info("Next and Cancel buttons are  available. ");
				} catch (AssertionError Ae) {
					strErrorMsg = "Next and Cancel buttons are NOT available. ";
					log4j.info("Next and Cancel buttons are NOT available. ");
				}
	} catch (AssertionError Ae) {
				strErrorMsg = "Select Interface Type page is NOT displayed"
						+ Ae;
				log4j.info("Select Interface Type page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToSelInterfaceTypePage function failed" + e);
			strErrorMsg = "navToSelInterfaceTypePage function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Navigating o select interface type page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:6-April-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-April-2012                               <Name>
	************************************************************************/
	
	public String CreatRgnInterfaceWithMandFlds(Selenium selenium,String strIntLable,
			String strWebAction ,String
			strInterfaceNmae,String strResId) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
			//click on create new interface
			
			selenium.click(propElementDetails.getProperty("Prop1904"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			selenium.select(propElementDetails.getProperty("Interface.selType"), "label="+strIntLable);
			selenium.click(propElementDetails.getProperty("Interface.SelectInterface.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Create Region Interface", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create Region Interface page is displayed");	
				
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Name:']"));
					log4j.info("Name: field is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Name: field is NOT displayed.";
					log4j.info("Name: field is NOT  displayed.");
				}
				
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Description:']"));
					log4j.info("Description: field is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Description: field is NOT displayed.";
					log4j.info("Description: field is NOT  displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Contact Information:']"));
					log4j.info("Contact Information: field is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Contact Information: field is NOT displayed.";
					log4j.info("Contact Information: field is NOT  displayed.");
				}
				
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Web Services Action:']"));
					assertEquals(strWebAction, selenium.getValue(propElementDetails
							.getProperty("Interface.InterfaceType")));
					log4j.info("Web Services Action:(Selected interface type in previous" +
							" step is displayed.) is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Web Services Action:(Selected interface type " +
							"in previous step is displayed.)  is NOT displayed.";
					log4j.info("Web Services Action:(Selected interface type in" +
							" previous step is displayed.)  is NOT  displayed.");
				}
				
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Resource Identification Method:']"));
					log4j.info("Resource Identification Method: (drop down)  is  displayed.");
				} catch (AssertionError Ae) {
					strErrorMsg = "Resource Identification Method: (drop down) is NOT displayed.";
					log4j.info("Resource Identification Method: (drop down) is NOT  displayed.");
				}
				
				
				try {
					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("Interface.active")));
					log4j.info("Active: Check to make this interface active (Check box) is present.");
				} catch (AssertionError Ae) {
					strErrorMsg ="Active: Check to make this interface active (Check box) is present.";
					log4j.info("Active: Check to make this interface active (Check box) is present. ");
				}
				
				selenium.type(propElementDetails.getProperty
						("InterfaceName"), strInterfaceNmae);
				selenium.select(propElementDetails.getProperty
						("Interface.ResourceIdentification"), "label="+strResId);
	

			} catch (AssertionError Ae) {
				strErrorMsg = "Create Region Interface page is NOT displayed"
						+ Ae;
				log4j.info("Create Region Interface page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("CreatRgnInterfaceWithMandFlds function failed" + e);
			strErrorMsg = "CreatRgnInterfaceWithMandFlds function failed" + e;
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
	 
	public String savAndVerifyInterface(Selenium selenium, String strInterfaceName)
			throws Exception {

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

				assertEquals("Interface List",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("Interface List is   displayed");

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
									+ strInterfaceName + "']"));
					log4j.info(strInterfaceName
							+ "  listed in the Interface list");

				} catch (AssertionError Ae) {
					log4j.info(strInterfaceName
							+ " NOT listed in the Interface list" + Ae);
					strErrorMsg = strInterfaceName
							+ " NOT listed in the Interface list" + Ae;
				}
			} catch (AssertionError Ae) {
				strErrorMsg = "Interface List is NOT displayed" + Ae;
				log4j.info("Interface List is NOT  displayed");

			}
		} catch (Exception e) {
			log4j.info("savAndVerifyInterface function failed" + e);
			strErrorMsg = "savAndVerifyInterface function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	public String checkDataInIntergaceListPage(Selenium selenium,
			String strHeader, String strInterfaceName, String strData,
			String strColIndex) throws Exception {
		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			if (strHeader.equals("Web Services Action")) {

				try {
					
					assertEquals(
							selenium
									.getText("//div[@id='mainContainer']/table/thead/tr/th["
											+ strColIndex + "]"), strHeader);
					log4j.info("The Column header " + strHeader
							+ " is displayed in Interface List page");

					if (strHeader.equals("Action")) {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
											+ strInterfaceName
											+ "']/parent::tr/td["
											+ strColIndex
											+ "]/a[text()='" + strData + "']"));
							log4j.info(strData
									+ " is found in the Interface List page "
									+ strInterfaceName);
						} catch (AssertionError ae) {
							log4j
									.info(strData
											+ " is NOT found in the Interface List page "
											+ strInterfaceName);
							strReason = strData
									+ " is NOT found in the Interface List page "
									+ strInterfaceName;
						}
					} else {
						try {
							
							assertEquals(
									strData,
									selenium
											.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
													+ strInterfaceName
													+ "']/parent::tr/td["
													+ strColIndex + "]"));
							log4j.info(strData
									+ " is found in the Interface List page "
									+ strInterfaceName);
						} catch (AssertionError ae) {
							log4j
									.info(strData
											+ " is NOT found in the Interface List page "
											+ strInterfaceName);
							strReason = strData
									+ " is NOT found in the Interface List page "
									+ strInterfaceName;
						}

					}

				} catch (AssertionError ae) {
					log4j.info("The Column header " + strHeader
							+ " is NOT displayed in Interface List page");
					strReason = strReason + " The Column header " + strHeader
							+ " is NOT displayed in Interface List page";
				}

			} else {
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th["
									+ strColIndex
									+ "][text()='"
									+ strHeader
									+ "']")
							|| selenium
									.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th["
											+ strColIndex
											+ "]/a[text()='"
											+ strHeader + "']"));
					log4j.info("The Column header " + strHeader
							+ " is displayed in Interface List page");
					if (strHeader.equals("Action")) {
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
											+ strInterfaceName
											+ "']/parent::tr/td["
											+ strColIndex
											+ "]/a[text()='" + strData + "']"));
							log4j.info(strData
									+ " is found in the Interface List page "
									+ strInterfaceName);
						} catch (AssertionError ae) {
							log4j
									.info(strData
											+ " is NOT found in the Interface List page "
											+ strInterfaceName);
							strReason = strData
									+ " is NOT found in the Interface List page "
									+ strInterfaceName;
						}
					} else {
						try {
							assertEquals(
									strData,
									selenium
											.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
													+ strInterfaceName
													+ "']/parent::tr/td["
													+ strColIndex + "]"));
							log4j.info(strData
									+ " is found in the Interface List page "
									+ strInterfaceName);
						} catch (AssertionError ae) {
							log4j
									.info(strData
											+ " is NOT found in the Interface List page "
											+ strInterfaceName);
							strReason = strData
									+ " is NOT found in the Interface List page "
									+ strInterfaceName;
						}

					}

				} catch (AssertionError ae) {
					log4j.info("The Column header " + strHeader
							+ " is NOT displayed in Interface List page");
					strReason = strReason + " The Column header " + strHeader
							+ " is NOT displayed in Interface List page";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Navigating o select interface type page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'20-Oct-2012                               <Name>
	************************************************************************/
	
	public String navToSelInterfaceTypePageNew(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click(propElementDetails
					.getProperty("Interface.CreateNewInterface"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Select Interface Type", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Select Interface Type page is displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Select Interface Type page is NOT displayed"
						+ Ae;
				log4j.info("Select Interface Type page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navToSelInterfaceTypePageNew function failed" + e);
			strErrorMsg = "navToSelInterfaceTypePageNew function failed" + e;
		}
		return strErrorMsg;
	}

	
	/***********************************************************************
	'Description	:Navigating o select interface type page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'20-Oct-2012                               <Name>
	************************************************************************/
	
	public String navCreateNewInterfPage(Selenium selenium,String strIntLable) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			

			selenium.select(propElementDetails.getProperty("Interface.selType"), "label="+strIntLable);
			selenium.click(propElementDetails.getProperty("Interface.SelectInterface.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Create Region Interface", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("Create Region Interface page is displayed");	
				
				
			} catch (AssertionError Ae) {
				strErrorMsg = "Create Region Interface page is NOT displayed"
						+ Ae;
				log4j.info("Create Region Interface page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("navCreateNewInterfPage function failed" + e);
			strErrorMsg = "navCreateNewInterfPage function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	/***********************************************************************
	'Description	:Navigating o select interface type page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'20-Oct-2012                               <Name>
	************************************************************************/
	
	public String fillCreatRgnInterfaceFlds(Selenium selenium,
			String strInterfaceName, String strDesc, String strContactInfo,
			String strResId, boolean blnStandardStatus, boolean blnActive)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
 
			selenium.type(propElementDetails.getProperty("InterfaceName"),
					strInterfaceName);
			
			selenium.type(propElementDetails.getProperty("Interface.description"), strDesc);
			
			selenium.type(propElementDetails.getProperty("Interface.ContactInfo"), strContactInfo);
			
			selenium.select(propElementDetails
					.getProperty("Interface.ResourceIdentification"), "label="
					+ strResId);
			
			if(blnStandardStatus){
				if(selenium.isChecked(propElementDetails
						.getProperty("Interface.UsrStandStatus"))==false){
					selenium.click(propElementDetails
							.getProperty("Interface.UsrStandStatus"));
				}
			}
			
			if(blnActive){
				if(selenium.isChecked(propElementDetails
						.getProperty("Interface.IntrfActive"))==false){
					selenium.click(propElementDetails
							.getProperty("Interface.IntrfActive"));
				}
			}
			

		} catch (Exception e) {
			log4j.info("fillCreatRgnInterfaceFlds function failed" + e);
			strErrorMsg = "fillCreatRgnInterfaceFlds function failed" + e;
		}
		return strErrorMsg;
	}

	
	 /***********************************************************************
	 'Description  :Navigating to edit region interface page
	 'Precondition :None
	 'Arguments    :selenium,strInterfaceNmae
	 'Returns      :String
	 'Date         :23-May-2012
	 'Author       :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '6-April-2012                               <Name>
	 ************************************************************************/
	
	public String navEditRegionInterfacePage(Selenium selenium, String strInterfaceName)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			selenium.click("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
									+ strInterfaceName + "']/parent::tr/td[1]/a[text()='edit']");
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Edit Region Interface", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Edit Region Interface page is  displayed");

			} catch (AssertionError Ae) {
				strErrorMsg = "Edit Region Interface page is NOT displayed" + Ae;
				log4j.info("Edit Region Interface page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("nav Edit Region Interfacepage  Function failed" + e);
			strErrorMsg = "nav Edit Region Interface page  Function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Navigating o select interface type page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'20-Oct-2012                               <Name>
	************************************************************************/
	
	public String chkDataRetainedInEditRgnInterfaceFlds(Selenium selenium,
			String strInterfaceNmae, String strDesc, String strContactInfo,
			String strResId, String strWebServiceAction, boolean blnActive)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try{
			assertEquals(strInterfaceNmae, selenium.getValue(propElementDetails
					.getProperty("InterfaceName")));
			log4j.info(strInterfaceNmae+" is retained in the edit Region interface page");
			
			assertEquals(strDesc, selenium.getValue(propElementDetails
					.getProperty("Interface.description")));
			log4j.info(strDesc+" is retained in the edit Region interface page");
					
			assertEquals(strContactInfo, selenium.getValue(propElementDetails
					.getProperty("Interface.ContactInfo")));
			log4j.info(strContactInfo+" is retained in the edit Region interface page");
			
			assertTrue(selenium.isElementPresent("css=input[name='actionName']" +
					"[value='"+strWebServiceAction+"']"));
			log4j.info(strWebServiceAction+" is retained in the edit Region interface page");
			
			assertEquals(strResId, selenium.getSelectedLabel(propElementDetails
					.getProperty("Interface.ResourceIdentification")));
			log4j.info(strResId+" is retained in the edit Region interface page");
		
			if (blnActive) {
				try {
					assertTrue(selenium.isChecked(propElementDetails
							.getProperty("Interface.IntrfActive")));
					log4j.info("Check boxes associated with Active is selected.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Check boxes associated with the Active is NOT selected.";
					log4j.info("Check boxes associated with the Active is NOT selected.");
				}
			} else {
				try {
					assertFalse(selenium.isChecked(propElementDetails
							.getProperty("Interface.IntrfActive")));
					log4j.info("Check boxes associated with the Active is not selected.");

				} catch (AssertionError Ae) {
					strErrorMsg = "Check boxes associated with the Active is selected.";
					log4j.info("Check boxes associated with the Active is selected.");
				}
			}
			
			}catch(AssertionError ae){
				strErrorMsg = "Data provided while creating the interface is NOT retained in all the fields.";
				log4j.info("Data provided while creating the interface is NOT retained in all the fields.");
			}
		
		} catch (Exception e) {
			log4j.info("chkDataRetainedInEditRgnInterfaceFlds function failed" + e);
			strErrorMsg = "chkDataRetainedInEditRgnInterfaceFlds function failed" + e;
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
	 
	public String VerifyInterfaceOtherFields(Selenium selenium, String strInterfaceName,String strActive
			,String strAction,String strType,String strSchema,String strDesc)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
				//Active
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text" +
									"()='"+strInterfaceName+"']/parent::tr/td[text()='"+strActive+"']"));
					log4j.info(strInterfaceName
							+ "  listed in the Interface list with the Active as"+strActive);
				} catch (AssertionError Ae) {
					log4j.info(strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strActive);
					strErrorMsg =strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strActive;
				}
				//Action
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text" +
									"()='"+strInterfaceName+"']/parent::tr/td[text()='"+strAction+"']"));
					log4j.info(strInterfaceName
							+ "  listed in the Interface list with the Web service action"+strAction);
				} catch (AssertionError Ae) {
					log4j.info(strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strAction);
					strErrorMsg =strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strAction;
				}
				//Type
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text" +
									"()='"+strInterfaceName+"']/parent::tr/td[text()='"+strType+"']"));
					log4j.info(strInterfaceName
							+ "  listed in the Interface list with the Web service action"+strType);
				} catch (AssertionError Ae) {
					log4j.info(strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strType);
					strErrorMsg =strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strType;
				}
				//Schema
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text" +
									"()='"+strInterfaceName+"']/parent::tr/td[text()='"+strSchema+"']"));
					log4j.info(strInterfaceName
							+ "  listed in the Interface list with the Web service action"+strSchema);
				} catch (AssertionError Ae) {
					log4j.info(strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strSchema);
					strErrorMsg =strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strSchema;
				}
				//Description
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[text" +
									"()='"+strInterfaceName+"']/parent::tr/td[text()='"+strDesc+"']"));
					log4j.info(strInterfaceName
							+ "  listed in the Interface list with the Web service action"+strDesc);
				} catch (AssertionError Ae) {
					log4j.info(strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strDesc);
					strErrorMsg =strInterfaceName
							+ "  NOT listed in the Interface list with the Web service action"+strDesc;
				}
			
		} catch (Exception e) {
			log4j.info("savAndVerifySTNew function failed" + e);
			strErrorMsg = "savAndVerifySTNew function failed" + e;
		}
		return strErrorMsg;
	}
	
	
	public String checkDataInIntergaceListPageNew(Selenium selenium,
			String strHeader, String strInterfaceName, String strData,
			String strColIndex) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objGeneral = new General();

		try {

			try {
				String s = selenium
						.getText("//div[@id='mainContainer']/table/thead/tr/th["
								+ strColIndex + "]");

				log4j.info(s);
				String strHeader1 = objGeneral.seleniumGetText(selenium,
						"//div[@id='mainContainer']/table/thead/tr/th["
								+ strColIndex + "]", 160);

				assertEquals(strHeader1, strHeader);
				log4j.info("The Column header " + strHeader
						+ " is displayed in Interface List page");

				try {

					assertEquals(
							selenium
									.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
											+ strInterfaceName
											+ "']/parent::tr/td["
											+ strColIndex
											+ "]"), strData);
					log4j.info(strData
							+ " is found in the Interface List page "
							+ strInterfaceName);

				} catch (AssertionError ae) {
					log4j.info(strData
							+ " is NOT found in the Interface List page "
							+ strInterfaceName);
					strReason = strData
							+ " is NOT found in the Interface List page "
							+ strInterfaceName;
				}

			} catch (AssertionError ae) {
				log4j.info("The Column header " + strHeader
						+ " is NOT displayed in Interface List page");
				strReason = strReason + " The Column header " + strHeader
						+ " is NOT displayed in Interface List page";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

	public String checkDataInIntergaceListPageNewWithRegularExpression(
			Selenium selenium, String strHeader, String strInterfaceName,
			String strData, String strColIndex) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			
			try {
				String s = selenium
						.getText("//div[@id='mainContainer']/table/thead/tr/th["
								+ strColIndex + "]");

				log4j.info(s);
				log4j.info("The Column header " + strHeader
						+ " is displayed in Interface List page");

				try {

					assertEquals(
							selenium
									.getText("//div[@id='mainContainer']/table/tbody/tr/td[text()='"
											+ strInterfaceName
											+ "']/parent::tr/td["
											+ strColIndex
											+ "]"), strData);
					log4j.info(strData
							+ " is found in the Interface List page "
							+ strInterfaceName);

				} catch (AssertionError ae) {
					log4j.info(strData
							+ " is NOT found in the Interface List page "
							+ strInterfaceName);
					strReason = strData
							+ " is NOT found in the Interface List page "
							+ strInterfaceName;
				}

			} catch (AssertionError ae) {
				log4j.info("The Column header " + strHeader
						+ " is NOT displayed in Interface List page");
				strReason = strReason + " The Column header " + strHeader
						+ " is NOT displayed in Interface List page";
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/***********************************************************************
	'Description	:Navigating o select interface type page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:19-Dec-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'19-Dec-2012                               <Name>
	************************************************************************/
	
	public String inactivateInterface(Selenium selenium,
			String strInterfaceName, boolean blnActive) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (blnActive) {
				if (selenium.isChecked(propElementDetails
						.getProperty("Interface.IntrfActive")) == false) {
					selenium.click(propElementDetails
							.getProperty("Interface.IntrfActive"));
				}
				selenium.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try {

					assertEquals("Interface List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Interface List is   displayed");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
										+ strInterfaceName + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='Active']"));

						log4j
								.info(strInterfaceName
										+ "  listed in the Interface list and is enabled");

					} catch (AssertionError Ae) {
						log4j
								.info(strInterfaceName
										+ " NOT listed in the Interface list and is NOT enabled "
										+ Ae);
						strErrorMsg = strInterfaceName
								+ " NOT listed in the Interface list and is NOT enabled "
								+ Ae;
					}
				} catch (AssertionError Ae) {
					strErrorMsg = "Interface List is NOT displayed" + Ae;
					log4j.info("Interface List is NOT  displayed");

				}
			} else {
				if (selenium.isChecked(propElementDetails
						.getProperty("Interface.IntrfActive"))) {
					selenium.click(propElementDetails
							.getProperty("Interface.IntrfActive"));
				}

				selenium.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				try {

					assertEquals("Interface List", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Interface List is   displayed");

					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2]" +
										"[text()='"
										+ strInterfaceName + "']"));
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/" +
										"td[3][text()='Disabled']"));

						log4j
								.info(strInterfaceName
										+ "  listed in the Interface list and is disabled");

					} catch (AssertionError Ae) {
						log4j
								.info(strInterfaceName
										+ " NOT listed in the Interface list and is NOT disabled "
										+ Ae);
						strErrorMsg = strInterfaceName
								+ " NOT listed in the Interface list and is NOT disabled "
								+ Ae;
					}
				} catch (AssertionError Ae) {
					strErrorMsg = "Interface List is NOT displayed" + Ae;
					log4j.info("Interface List is NOT  displayed");

				}
			}

		} catch (Exception e) {
			log4j.info("fillCreatRgnInterfaceFlds function failed" + e);
			strErrorMsg = "fillCreatRgnInterfaceFlds function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:check Fields In Edit Region Interface Of GETCADStatus
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'20-Oct-2012                               <Name>
	************************************************************************/

	public String chkFldsInEditRegionInterfaceOfGETCADStatus(Selenium selenium,
			String strWebAction, String strInterFaceKey,String strRSOption) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Name:']"));
				log4j.info("Name: field is  displayed.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Name: field is NOT displayed.";
				log4j.info("Name: field is NOT  displayed.");
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Description:']"));
				log4j.info("Description: field is  displayed.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Description: field is NOT displayed.";
				log4j.info("Description: field is NOT  displayed.");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Contact Information:']"));
				log4j.info("Contact Information: field is  displayed.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Contact Information: field is NOT displayed.";
				log4j.info("Contact Information: field is NOT  displayed.");
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Interface Key:']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='Interface Key:']" +
								"/following-sibling::td/input[@value='"+strInterFaceKey+"'][@type='text']"));
				assertFalse(selenium.isEditable("//table/tbody/tr/td[text()='Interface Key:']" +
						"/following-sibling::td/input[@value='"+strInterFaceKey+"'][@type='text']"));
				
				log4j.info("Interface key has the same value as that provided for the region and is disabled. ");
			} catch (AssertionError Ae) {
				strErrorMsg = "Interface key has NOT the same value as that provided for the region and is disabled. ";
				log4j.info("Interface key has NOT the same value as that provided for the region and is disabled. ");
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Web Services Action:']"));
				assertEquals(strWebAction, selenium.getValue(propElementDetails
						.getProperty("Interface.InterfaceType")));
				log4j.info("Web Services Action:Filled with '" + strWebAction
						+ "' by default is  displayed.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Web Services Action:(Selected interface type "
						+ "in previous step is displayed.)  is NOT displayed.";
				log4j.info("Web Services Action:(Selected interface type in"
						+ " previous step is displayed.)  is NOT  displayed.");
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/td[text()='Resource Identification Method:']"));
				assertEquals(strRSOption, selenium.getSelectedLabel(propElementDetails
						.getProperty("Interface.ResourceIdentification")));
				log4j.info("Resource Identification Method: (drop down)  is  displayed.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Resource Identification Method: (drop down) is NOT displayed.";
				log4j.info("Resource Identification Method: (drop down) is NOT  displayed.");
			}

			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Interface.active")));
				log4j.info("Active: Check to make this interface active (Check box) is present.");
			} catch (AssertionError Ae) {
				strErrorMsg = "Active: Check to make this interface active (Check box) is present.";
				log4j.info("Active: Check to make this interface active (Check box) is present. ");
			}

		} catch (Exception e) {
			log4j.info("chkFldsInEditRegionInterfaceOfGETCADStatus function failed"
					+ e);
			strErrorMsg = "chkFldsInEditRegionInterfaceOfGETCADStatus function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:check Fields In Edit Region Interface Of GETCADStatus
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:20-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'20-Oct-2012                               <Name>
	************************************************************************/
	public String chkFldsOptionalStatusTypeMappings(Selenium selenium)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			//Optional Status Type Mappings headers
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/form/table/tbody/tr/" +
								"td[text()='Optional Status Type Mappings']"));
				assertTrue(selenium
						.isElementPresent("//table/thead/tr/th[text()='Include?']"));
				assertTrue(selenium
						.isElementPresent("//table/thead/tr/th/a[text()='Type']"));
				assertTrue(selenium
						.isElementPresent("//table/thead/tr/th/a[text()='Standard�Status�Type']"));
				assertTrue(selenium
						.isElementPresent("//table/thead/tr/th/a[text()='Regional�Status�Type']"));
				assertTrue(selenium
						.isElementPresent("//table/thead/tr/th/a[text()='Description']"));
				
				log4j.info("'Optional Status Type mappings' section is displayed with the following columns:" +
						" 1. Include? - Check box" +
						"2. Type" +
						"3. Standard Status type" +
						"4. Regional status type" +
						"5. Description ");
				
			} catch (AssertionError Ae) {
				strErrorMsg = "'Optional Status Type mappings' section is NOT displayed" +
						" with the following columns.";
				log4j.info("'Optional Status Type mappings' section is NOT displayed" +
						" with the following columns.");
			}
			
			// Standard status types options
			try {
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='ALS']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='Average Wait Time']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='BLS']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='CCT']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='Maximum Wait Time']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='Units Available']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='Units en Route']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[text()='Units at Destination']"));

				log4j.info("The following Standard status types are available under the " +
						"'Optional Status type Mappings' section:" +
						"1. ALS" +
						"2. Average Wait Time" +
						"3. BLS" +
						"4. CCT" +
						"5. Maximum Wait Time" +
						"6. Units Available" +
						"7. Units at Destination" +
						"8. Units en Route ");

			} catch (AssertionError Ae) {
				strErrorMsg = "The following Standard status types are NOT available under the" +
						" 'Optional Status type Mappings' section.";
				log4j.info("The following Standard status types are NOT available " +
						"under the 'Optional Status type Mappings' section.");
			}

		} catch (Exception e) {
			log4j.info("chkFldsOptionalStatusTypeMappings function failed"
					+ e);
			strErrorMsg = "chkFldsOptionalStatusTypeMappings function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:select por deselect ST in Edit Resource-Level Status Types page
	'Precondition	:None
	'Arguments		:selenium, strST
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/

	public String selDeselctOptionalStatMappings(Selenium selenium,
			String strOptionName, boolean blnSelct) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try {
			// click on Edit link for Resource

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent("//table/tbody/tr/td[text()='"
									+ strOptionName + "']"));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			if (blnSelct) {
				if (selenium.isChecked("//table/tbody/tr/td[text()='"
						+ strOptionName + "']/parent::tr/td/input") == false) {
					selenium.click("//table/tbody/tr/td[text()='"
							+ strOptionName + "']/parent::tr/td/input");
				}
			} else {
				if (selenium.isChecked("//table/tbody/tr/td[text()='"
						+ strOptionName + "']/parent::tr/td/input")) {
					selenium.click("//table/tbody/tr/td[text()='"
							+ strOptionName + "']/parent::tr/td/input");
				}
			}
		} catch (Exception e) {
			log4j.info("selDeselctOptionalStatMappings function failed" + e);
			strReason = "selDeselctOptionalStatMappings function failed" + e;
		}
		return strReason;
	}
	/***********************************************************************
	'Description	:select por deselect ST in Edit Resource-Level Status Types page
	'Precondition	:None
	'Arguments		:selenium, strST
	'Returns		:String
	'Date	 		:8-June-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'8-June-2012                               <Name>
	************************************************************************/
	
	public String selectAndDeselectResInInterface(Selenium selenium,
			String strResource, String strRSVal, boolean blnSelect)
			throws Exception {
		String StrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		selenium.selectWindow("");
		selenium.selectFrame("Data");

		try {
			if (blnSelect) {
				int intCnt = 0;
				do {
					try {
						assertTrue(selenium
								.isElementPresent("//table[@class='displayTable striped border sortable']/tbody/tr/td[3][contains(text(),'"
										+ strResource
										+ "')]/parent::tr/td[2][text()='"
										+ strRSVal
										+ "']/parent::tr/td[1]/input"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				if (selenium
						.isChecked("//table[@class='displayTable striped border sortable']/tbody/tr/td[3][contains(text(),'"
								+ strResource
								+ "')]/parent::tr/td[2][text()='"
								+ strRSVal + "']/parent::tr/td[1]/input") == false)
					selenium.click("//table[@class='displayTable striped border sortable']/tbody/tr/td[3][contains(text(),'"
							+ strResource
							+ "')]/parent::tr/td[2][text()='"
							+ strRSVal + "']/parent::tr/td[1]/input");

			} else {

				int intCnt = 0;
				do {
					try {

						assertTrue(selenium
								.isElementPresent("//table[@class='displayTable striped border sortable']/tbody/tr/td[3][contains(text(),'"
										+ strResource
										+ "')]/parent::tr/td[2][text()='"
										+ strRSVal
										+ "']/parent::tr/td[1]/input"));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);

				if (selenium
						.isChecked("//table[@class='displayTable striped border sortable']/tbody/tr/td[3][contains(text(),'"
								+ strResource
								+ "')]/parent::tr/td[2][text()='"
								+ strRSVal + "']/parent::tr/td[1]/input"))
					selenium.click("//table[@class='displayTable striped border sortable']/tbody/tr/td[3][contains(text(),'"
							+ strResource
							+ "')]/parent::tr/td[2][text()='"
							+ strRSVal + "']/parent::tr/td[1]/input");
			}

		} catch (Exception e) {
			log4j.info("selectAndDeselectStatusReason function failed" + e);
			StrReason = "selectAndDeselectStatusReason function failed" + e;
		}
		return StrReason;
	}
	/***********************************************************************
	 'Description :Function to verify that the resource name and resource id are 
	 			   displayed under Allowed Resources
	 'Precondition :None
	 'Arguments  :selenium
	 'Returns  :String
	 'Date    :23-May-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '6-April-2012                               <Name>
	 ************************************************************************/
	 
	public String verifyResrceDispUnderAllowedResourec(Selenium selenium,
			String strResrcId, String strResourceName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals(selenium.getText("css=td.hdr"),
						"Allowed Resources");
				log4j.info("Allowed Resources section is displayed");
			} catch (AssertionError Ae) {
				log4j.info("Allowed Resources section is displayed");
				strErrorMsg = "Allowed Resources section is displayed";
			}

			try {
				assertTrue(selenium
						.isElementPresent("//table[@class='displayTable striped border sortable']/tbody/tr/td[2][contains(text(),'"
								+ strResrcId
								+ "')]/parent::tr/td[3][contains(text(),'"
								+ strResourceName
								+ "')]/parent::tr/td[1]/input"));
				log4j
						.info("Created Resource is displayed under 'Allowed Resources section'");
			} catch (AssertionError Ae) {
				log4j
						.info("Created Resource is not displayed under 'Allowed Resources section'");
				strErrorMsg = "Created Resource is not displayed under 'Allowed Resources section'";
			}

		} catch (Exception e) {
			log4j.info("verifyResrceDispUnderAllowedResourec function failed"
					+ e);
			strErrorMsg = "verifyResrceDispUnderAllowedResourec function failed"
					+ e;
		}
		return strErrorMsg;
	}
	
	/************************************************************************
	 'Description :Function to verify that the headers in interface list page
	 'Arguments   :selenium
	 'Returns     :String
	 'Date        :23-May-2012
	 'Author      :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                                              Modified By
	 '6-April-2012                                                <Name>
	 ************************************************************************/
	public String checkHeaderInInterfaceListPage(Selenium selenium)
			throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[1][text()='Action']"));
				log4j.info("Action header is displayed in the Interface List page");

			} catch (AssertionError ae) {
				log4j.info("Action header is NOT displayed in the Interface List page");
				strReason = "Action header is NOT displayed in the Interface List page";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[2]/a[text()='Name']"));
				log4j.info("Name header is displayed in the Interface List page");

			} catch (AssertionError ae) {
				log4j.info("Name header is NOT displayed in the Interface List page");
				strReason = "Name header is NOT displayed in the Interface List page";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[3]/a[text()='Active']"));
				log4j.info("Active header is displayed in the Interface List page");

			} catch (AssertionError ae) {
				log4j.info("Active header is NOT displayed in the Interface List page");
				strReason = "Active header is NOT displayed in the Interface List page";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[4]/a[text()='Web�Services�Action']"));
				log4j.info("Web Services Action header is displayed in the Interface List page");

			} catch (AssertionError ae) {
				log4j.info("Web Services Action header is NOT displayed in the Interface List page");
				strReason = "Web Services Action header is NOT displayed in the Interface List page";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[5]/a[text()='Type']"));
				log4j.info("Type header is displayed in the Interface List page");

			} catch (AssertionError ae) {
				log4j.info("Type header is NOT displayed in the Interface List page");
				strReason = "Type header is NOT displayed in the Interface List page";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[6]/a[text()='Schema']"));
				log4j.info("Schema header is displayed in the Interface List page");

			} catch (AssertionError ae) {
				log4j.info("Schema header is NOT displayed in the Interface List page");
				strReason = "Schema header is NOT displayed in the Interface List page";
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/thead/tr/th[7]/a[text()='Description']"));
				log4j.info("Description header is displayed in the Interface List page");

			} catch (AssertionError ae) {
				log4j.info("Description header is NOT displayed in the Interface List page");
				strReason = "Description header is NOT displayed in the Interface List page";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/************************************************************************
	 'Description :Function to verify that the Data in interface list page
	 'Arguments   :selenium
	 'Returns     :String
	 'Date        :23-May-2012
	 'Author      :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                                              Modified By
	 '6-April-2012                                                <Name>
	 ************************************************************************/
	public String checkIntergaceDetailsInIntListPage(Selenium selenium,
			String strInterfaceName, String strActive,
			String strWebServiceAction, String strType, String strSchema,
			String strDesc) throws Exception {

		String strReason = "";

		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {

				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[1]/a[text()='edit']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[1]/a[text()='users']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[1]/a[text()='parameters']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[1]/a[text()='delete']"));
				log4j.info("edit, users, parameters and delete links found in the Interface List " +
						"page under Action coloumn for "+ strInterfaceName);

			} catch (AssertionError ae) {
				log4j.info("edit, users, parameters and delete links NOT found in the Interface List page for "
						+ strInterfaceName);
				strReason = "edit, users, parameters and delete links NOT found in the Interface List page for "
						+ strInterfaceName;
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[2][text()='"+strInterfaceName+"']"));
				log4j.info(strInterfaceName+" is found in the Interface List " +
						"page under Name coloumn for "+ strInterfaceName);
			} catch (AssertionError ae) {
				log4j.info(strInterfaceName+" is NOT found in the Interface List " +
						"page under Name coloumn for "+ strInterfaceName);
				strReason = strInterfaceName+" is NOT found in the Interface List " +
						"page under Name coloumn for "+ strInterfaceName;
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[3][text()='"+strActive+"']"));
				log4j.info(strActive+" is found in the Interface List " +
						"page under Active coloumn for "+ strInterfaceName);
			} catch (AssertionError ae) {
				log4j.info(strActive+" is NOT found in the Interface List " +
						"page under Active coloumn for "+ strInterfaceName);
				strReason = strActive+" is NOT found in the Interface List " +
						"page under Active coloumn for "+ strInterfaceName;
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[4][text()='"+strWebServiceAction+"']"));
				log4j.info(strWebServiceAction+" is found in the Interface List " +
						"page under Web Services Action coloumn for "+ strInterfaceName);
			} catch (AssertionError ae) {
				log4j.info(strWebServiceAction+" is NOT found in the Interface List " +
						"page under Web Services Action coloumn for "+ strInterfaceName);
				strReason = strWebServiceAction+" is NOT found in the Interface List " +
						"page under Web Services Action coloumn for "+ strInterfaceName;
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[5][text()='"+strType+"']"));
				log4j.info(strType+" is found in the Interface List " +
						"page under Type coloumn for "+ strInterfaceName);
			} catch (AssertionError ae) {
				log4j.info(strType+" is NOT found in the Interface List " +
						"page under Type coloumn for "+ strInterfaceName);
				strReason = strType+" is NOT found in the Interface List " +
						"page under Type coloumn for "+ strInterfaceName;
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[6][text()='"+strSchema+"']"));
				log4j.info(strSchema+" is found in the Interface List " +
						"page under Schema coloumn for "+ strInterfaceName);
			} catch (AssertionError ae) {
				log4j.info(strSchema+" is NOT found in the Interface List " +
						"page under Schema coloumn for "+ strInterfaceName);
				strReason = strSchema+" is NOT found in the Interface List " +
						"page under Schema coloumn for "+ strInterfaceName;
			}
			try {
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td"
								+ "[text()='"
								+ strInterfaceName
								+ "']/parent::tr/td[7][text()='"+strDesc+"']"));
				log4j.info(strDesc+" is found in the Interface List " +
						"page under Description coloumn for "+ strInterfaceName);
			} catch (AssertionError ae) {
				log4j.info(strDesc+" is NOT found in the Interface List " +
						"page under Description coloumn for "+ strInterfaceName);
				strReason = strDesc+" is NOT found in the Interface List " +
						"page under Description coloumn for "+ strInterfaceName;
			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
}
