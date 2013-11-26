package com.qsgsoft.EMResource.shared;

import com.thoughtworks.selenium.Selenium;
import java.util.Properties;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;

import com.qsgsoft.EMResource.support.*;

/*******************************************************************************
' Description         :This class includes Upload related functions
' Requirement Group   :
' Requirement         :
' Date		          :7-Nov-2012
' Author	          :QSG
'--------------------------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'********************************************************************************/

public class Upload {

	Properties ElementDetails;
	static Logger log4j = Logger.getLogger("Upload");
	public Properties propElementDetails, propEnvDetails;
	public String gstrTimeOut;
	
/*******************************************************************************
' Description: Navigate to UploadListPage
' Precondition: N/A 
' Arguments: selenium
' Returns: String 
' Date: 05-11-2012
' Author: QSG 
'------------------------------------------------------------------------------- 
' Modified Date: 
' Modified By: 
********************************************************************************/
	
	public String navToUploadListPage(Selenium selenium) throws Exception {
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
			selenium.mouseOver(propElementDetails.getProperty("Prop2279"));
			selenium.click(propElementDetails.getProperty("Prop2280"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Upload List", selenium.getText(propElementDetails
						.getProperty("Prop40")));
				log4j.info("''Upload List' screen is displayed.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Upload List' screen is NOT displayed.");
				lStrReason = lStrReason + "; "
						+ "'Upload List' screen is NOT displayed.";
			}
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Prop2281")));
				log4j.info("''Upload Resources' option is available.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Upload Resources' option is NOT available.");
				lStrReason = lStrReason + "; "
						+ "'Upload Resources' option is NOT available.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

/*******************************************************************************************
' Description: navigate to New Upload page and verify that save, cancel buttons are displayed with browse button
' Precondition: N/A 
' Arguments: selenium
' Returns: String 
' Date: 05-11-2012
' Author: QSG 
'--------------------------------------------------------------------------------- 
' Modified Date: 
' Modified By: 
*******************************************************************************************/

	public String navToNewUploadPage(Selenium selenium) throws Exception {
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
			selenium.click(propElementDetails.getProperty("Prop2281"));
			selenium.waitForPageToLoad(gstrTimeOut);
			assertEquals("New Upload", selenium.getText(propElementDetails
					.getProperty("Prop40")));
			try {
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Prop46")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Prop2282")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("Prop2283")));
				log4j
						.info("''New Upload' screen is displayed with options to browse for a file along with 'Save' and 'Cancel' button. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("''New Upload' screen is NOT displayed with options to browse for a file along with 'Save' and 'Cancel' button. ");
				lStrReason = lStrReason
						+ "; "
						+ "'New Upload' screen is NOT displayed with options to browse for a file along with 'Save' and 'Cancel' button. ";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}


/*******************************************************************************************
' Description: Check the upload option is available under setup
' Precondition: N/A 
' Arguments: selenium
' Returns: String 
' Date: 05-11-2012
' Author: QSG 
'--------------------------------------------------------------------------------- 
' Modified Date: 
' Modified By: 
*******************************************************************************************/

public String checkOptionUploadInSetup(Selenium selenium,boolean blnUploadOpt) throws Exception
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
		
		selenium.mouseOver(propElementDetails.getProperty("Prop2279"));
		if(blnUploadOpt){
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop2280")));
				log4j.info("'Option 'Upload' is available under 'Setup' ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Option 'Upload' is not available under 'Setup' ");
				lStrReason = lStrReason + "; " + "Option 'Upload' is not available under 'Setup' ";
			}
		}else{
			try {
				assertFalse(selenium.isElementPresent(propElementDetails.getProperty("Prop2280")));
				log4j.info("'Option 'Upload' is not available under 'Setup' ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Option 'Upload' is still available under 'Setup' ");
				lStrReason = lStrReason + "; " + "Option 'Upload' is still available under 'Setup' ";
			}
		}
	}catch(Exception e){
		log4j.info(e);
		lStrReason = lStrReason + "; " + e.toString();
	}

	return lStrReason;
}

/*******************************************************************************************
' Description: Check the upload option is available under setup
' Precondition: N/A 
' Arguments: selenium
' Returns: String 
' Date: 05-11-2012
' Author: QSG 
'--------------------------------------------------------------------------------- 
' Modified Date: 
' Modified By: 
*******************************************************************************************/

	public String fillNewUploadFields(Selenium selenium,
			String strAutoFilePath, String strUploadFilePath,
			String strAutoFileName, boolean blnTest, boolean blnSave)
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

			String strArgs[] = { strAutoFilePath, strUploadFilePath };
			// Auto it to upload the file
			Runtime.getRuntime().exec(strArgs);
			selenium.windowFocus();
			// click on Browse
			selenium.click(propElementDetails
					.getProperty("DocumentLibrary.AddNewDocument.Browse"));
			// wait for pop up to appear
			Thread.sleep(5000);
			// Wait for autoit file to finish execution
			String strProcess = "";

			int intCnt = 0;
			do {

				GetProcessList objGPL = new GetProcessList();
				strProcess = objGPL.GetProcessName();
				intCnt++;
				Thread.sleep(1000);
			} while (strProcess.contains(strAutoFileName) && intCnt < 240);

			if (blnTest) {
				if (selenium.isChecked(propElementDetails.getProperty("Upload.Test")) == false) {
					selenium.click(propElementDetails.getProperty("Upload.Test"));
				}
			} else {
				if (selenium.isChecked(propElementDetails.getProperty("Upload.Test"))) {
					selenium.click(propElementDetails.getProperty("Upload.Test"));
				}
			}

			if (blnSave) {

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Upload Details", selenium
							.getText(propElementDetails.getProperty("Prop40")));
					log4j.info("'Upload Details' screen is displayed.");
				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'Upload Details' screen is NOT displayed.");
					lStrReason = lStrReason + "; "
							+ "'Upload Details' screen is NOT displayed.";
				}

			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Check the upload option is available under setup
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 06-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyElmentsInUploadDetailsPage(Selenium selenium)
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

			try {
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[@class='emsLabel'][text()='Spreadsheet:']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[@class='emsLabel'][text()='Test?:']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[@class='emsLabel'][text()='User:']"));
				assertTrue(selenium
						.isElementPresent("//table/tbody/tr/td[@class='emsLabel'][text()='Date:']"));

				log4j
						.info("Following fields are displayed 1. Spreadsheet: 2. Test?: 3. User:4. Date:");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Following fields are NOT displayed 1. Spreadsheet: 2. Test?: 3. User:4. Date:");
				lStrReason = lStrReason
						+ "; "
						+ "Following fields are NOT displayed 1. Spreadsheet: 2. Test?: 3. User:4. Date:";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Verify the data provided while uploading the file are retained in Upload details page
	' Precondition: N/A 
	' Arguments: selenium, strColHeaders,pStrArrUplData
	' Returns: String 
	' Date: 06-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyUplDetailsInUpldDetailsPge(Selenium selenium,String[] strColHeaders, String[] pStrArrUplData) throws Exception
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
				assertEquals("Upload Details", selenium.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("''Upload Details' screen is displayed. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Upload Details' screen is NOT displayed. ");
				lStrReason = lStrReason + "; " + "'Upload Details' screen is NOT displayed. ";
			}
			
			for(int intCol=0;intCol<strColHeaders.length;intCol++){
				try {
					assertEquals(strColHeaders[intCol], selenium.getText("//div[@id='mainContainer']/form/table[2]/thead/tr/th["+(intCol+1)+"]/a"));
					log4j.info("'The Column Header "+strColHeaders[intCol]+" is displayed");
				}
				catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'The Column Header "+strColHeaders[intCol]+" is NOT displayed");
					lStrReason = lStrReason + "; " + "'The Column Header "+strColHeaders[intCol]+" is NOT displayed";
				}
			}
			
			for(int intCol=0;intCol<pStrArrUplData.length;intCol++){
				if(intCol==1){
					try {
						assertTrue(selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr/td["+(intCol+1)+"]").matches(pStrArrUplData[intCol]));
						log4j.info("'The specified data "+pStrArrUplData[intCol]+" is displayed");
					}
					catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The specified data "+pStrArrUplData[intCol]+" is NOT displayed");
						lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intCol]+" is NOT displayed";
					}
				}else{
					try {
						assertEquals(pStrArrUplData[intCol], selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr/td["+(intCol+1)+"]"));
						log4j.info("'The specified data "+pStrArrUplData[intCol]+" is displayed");
					}
					catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The specified data "+pStrArrUplData[intCol]+" is NOT displayed");
						lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intCol]+" is NOT displayed";
					}
				}
			}
			
			try {
				assertTrue(selenium.isElementPresent("css=input[value='Return']"));
				log4j.info("'Return' button is available.");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Return' button is NOT available.");
				lStrReason = lStrReason + "; " + "'Return' button is NOT available.";
			}
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	

	/*******************************************************************************************
	' Description: Verify the data provided while uploading the file are retained in Upload details page
	' Precondition: N/A 
	' Arguments: selenium, pStrArrUplData
	' Returns: String 
	' Date: 09-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyUplDetailsInUpldDetailsPge_MultipleData(Selenium selenium, String[][] pStrArrUplData) throws Exception
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
						
			for(int intRow=0;intRow<pStrArrUplData.length;intRow++){
				for(int intCol=0;intCol<pStrArrUplData[0].length;intCol++){
					if(intCol==1){
						try {
							assertTrue(selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr["+(intRow+1)+"]/td["+(intCol+1)+"]").matches(pStrArrUplData[intRow][intCol]));
							log4j.info("'The specified data "+pStrArrUplData[intRow][intCol]+" is displayed");
						}
						catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "+pStrArrUplData[intRow][intCol]+" is NOT displayed");
							lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intRow][intCol]+" is NOT displayed";
						}
					}else{
						try {
							assertEquals(pStrArrUplData[intRow][intCol], selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr["+(intRow+1)+"]/td["+(intCol+1)+"]"));
							log4j.info("'The specified data "+pStrArrUplData[intRow][intCol]+" is displayed");
						}
						catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "+pStrArrUplData[intRow][intCol]+" is NOT displayed");
							lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intRow][intCol]+" is NOT displayed";
						}
					}
				}
			}
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Navigate to Upload List page by clicking on Return
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 06-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToUploadListPageByReturn(Selenium selenium) throws Exception
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
			selenium.click(propElementDetails.getProperty("Prop2351"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Upload List", selenium.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("''Upload List' screen is displayed.");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Upload List' screen is NOT displayed.");
				lStrReason = lStrReason + "; " + "'Upload List' screen is NOT displayed.";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Verify the data provided while uploading the file are retained in Upload details page
	' Precondition: N/A 
	' Arguments: selenium, strColHeaders,pStrArrUplData
	' Returns: String 
	' Date: 06-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyUplDetailsInUpldListPge(Selenium selenium,
			String[] strColHeaders, String[] pStrArrUplData) throws Exception {
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
			try {
				assertEquals("Upload List", selenium.getText(propElementDetails
						.getProperty("Prop40")));
				log4j.info("'Upload List' screen is displayed. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Upload List' screen is NOT displayed. ");
				lStrReason = lStrReason + "; "
						+ "'Upload List' screen is NOT displayed. ";
			}

			for (int intCol = 0; intCol < strColHeaders.length; intCol++) {

				if (strColHeaders[intCol].equals("Action")) {
					
					try {
						assertEquals(
								strColHeaders[intCol],
								selenium
										.getText("//div[@id='mainContainer']/table[2]/thead/tr/th["
												+ (intCol + 1) + "]"));
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed";
					}
				} else {
					try {
						assertEquals(
								strColHeaders[intCol],
								selenium
										.getText("//div[@id='mainContainer']/table[2]/thead/tr/th["
												+ (intCol + 1) + "]/a"));
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed";
					}
				}
			}

			for (int intCol = 0; intCol < pStrArrUplData.length; intCol++) {

				if (pStrArrUplData[intCol].equals("View Details")) {
					try {
						assertEquals(
								pStrArrUplData[intCol],
								selenium
										.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
												+ (intCol + 1) + "]/a"));
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed";
					}
				} else {

					try {
						
						assertEquals(
								pStrArrUplData[intCol],
								selenium
										.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
												+ (intCol + 1) + "]"));
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed";
					}

				}
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Verify the fields present in Upload details screen
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 07-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyFieldsPresentInUpldDetails(Selenium selenium,
			String strExcelName, String strUser, String strDate,
			boolean blnTest, boolean blnDisabled) throws Exception {
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

			try {
				assertEquals(
						selenium
								.getValue("css=input[type='text'][name='spreadsheetName']"),
						strExcelName);
				/*
				  assertTrue(selenium.isElementPresent(
				  "css=input[type='text'][name='spreadsheetName'][value='"
				 +strExcelName+"']"));
				 */
				if (blnTest)
					assertTrue(selenium
							.isElementPresent("css=input[name='test'][value='Y']"));
				else
					assertTrue(selenium
							.isElementPresent("css=input[name='test'][value='N']"));

				assertTrue(selenium
						.isElementPresent("css=input[name='createUserName'][value='"
								+ strUser + "']"));

				assertTrue(selenium
						.isElementPresent("css=input[id='createDateTime'][value='"
								+ strDate + "']"));

				log4j
						.info("Following fields are displayed with appropriate value 1. Spreadsheet: "+strExcelName+"  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Following fields are NOT displayed with appropriate value 1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ");
				lStrReason = lStrReason
						+ "; "
						+ "Following fields are NOT displayed with appropriate value 1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ";
			}

			if (blnDisabled) {
				try {
					assertFalse(selenium
							.isEditable("css=input[name='spreadsheetName']"));

					assertFalse(selenium.isEditable("css=input[name='test']"));

					assertFalse(selenium
							.isEditable("css=input[name='createUserName']"));
					assertFalse(selenium
							.isEditable("css=input[id='createDateTime']"));

					log4j
							.info("Following fields are displayed with appropriate value and is disabled 1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ");
				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j
							.info("Following fields are NOT displayed with appropriate value or is disabled1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ");
					lStrReason = lStrReason
							+ "; "
							+ "Following fields are NOT displayed with appropriate value or is disabled 1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ";
				}

			} else {
				try {
					assertTrue(selenium
							.isEditable("css=input[name='spreadsheetName']"));

					assertTrue(selenium.isEditable("css=input[name='test']"));

					assertTrue(selenium
							.isEditable("css=input[name='createUserName']"));
					assertTrue(selenium
							.isEditable("css=input[id='createDateTime']"));

					log4j
							.info("Following fields are displayed with appropriate value and is not disabled 1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ");
				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j
							.info("Following fields are NOT displayed with appropriate value or is disabled1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ");
					lStrReason = lStrReason
							+ "; "
							+ "Following fields are NOT displayed with appropriate value or is disabled 1. Spreadsheet: File path  2. Test?: Is selected  3. User: RegAdmin4. Date: Current Date ";
				}
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Verify the data provided while uploading the file are retained in Upload details page
	' Precondition: N/A 
	' Arguments: selenium, strColHeaders,pStrArrUplData
	' Returns: String 
	' Date: 08-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyUplDetailsInUpldDetailsPgeNew(Selenium selenium,
			String[] strColHeaders, String[] pStrArrUplData,String strUserName) throws Exception {
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
			try {
				assertEquals("Upload Details", selenium
						.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("''Upload Details' screen is displayed. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Upload Details' screen is NOT displayed. ");
				lStrReason = lStrReason + "; "
						+ "'Upload Details' screen is NOT displayed. ";
			}

			for (int intCol = 0; intCol < strColHeaders.length; intCol++) {
				try {
					assertEquals(
							strColHeaders[intCol],
							selenium
									.getText("//div[@id='mainContainer']/form/table[2]/thead/tr/th["
											+ (intCol + 1) + "]/a"));
					log4j.info("'The Column Header " + strColHeaders[intCol]
							+ " is displayed");
				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'The Column Header " + strColHeaders[intCol]
							+ " is NOT displayed");
					lStrReason = lStrReason + "; " + "'The Column Header "
							+ strColHeaders[intCol] + " is NOT displayed";
				}
			}

			int intRow = selenium.getXpathCount(
					"//div[@id='mainContainer']/form/table[2]"
							+ "/tbody/tr/td/a[text()='" + strUserName
							+ "']/ancestor::tr/" + "preceding-sibling::tr")
					.intValue();
			intRow = intRow + 1;

			for (int intCol = 0; intCol < pStrArrUplData.length; intCol++) {

				if (intCol == 1) {
					try {
						assertTrue(selenium
								.getText(
										"//div[@id='mainContainer']/form/table[2]/tbody/tr["
												+ intRow + "]/td["
												+ (intCol + 1) + "]").matches(
										pStrArrUplData[intCol]));
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed";
					}
				} else {
					try {
						assertEquals(
								pStrArrUplData[intCol],
								selenium
										.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr["
												+ intRow
												+ "]/td["
												+ (intCol + 1) + "]"));
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed";
					}
				}
			}

			try {
				assertTrue(selenium
						.isElementPresent("css=input[value='Return']"));
				log4j.info("'Return' button is available.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Return' button is NOT available.");
				lStrReason = lStrReason + "; "
						+ "'Return' button is NOT available.";
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}


	/*******************************************************************************************
	' Description: Verify all the fields are displayed properly in New Upload page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 12-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyElmentsInNewUploadPage(Selenium selenium) throws Exception
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
			assertEquals("Spreadsheet:**",selenium.getText("//div[@id='mainContainer']/form/table/tbody/tr/td"));
			assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop2283")));
			assertEquals("Only .xls files are allowed. Maximum file size is 5 megabytes (MB).", selenium.getText(propElementDetails.getProperty("Prop2391")));
			log4j.info("Field 'Spreadsheet' is displayed as mandatory with a 'Browse' button. An instruction 'Only .xls files are allowed. Maximum file size is 5 megabytes (MB).' is displayed. ");
		} catch (AssertionError Ae) {
			log4j.info(Ae);
			log4j
					.info("Field 'Spreadsheet' is displayed as mandatory with a 'Browse' button. An instruction 'Only .xls files are allowed. Maximum file size is 5 megabytes (MB).' is NOT displayed. ");
			lStrReason = lStrReason
					+ "; "
					+ "Field 'Spreadsheet' is displayed as mandatory with a 'Browse' button. An instruction 'Only .xls files are allowed. Maximum file size is 5 megabytes (MB).' is NOT displayed. ";
		}

		try{
			assertEquals("Test?:",selenium.getText("//div[@id='mainContainer']/form/table/tbody/tr[2]/td"));
			
			assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop2390")));
		
			assertEquals("If selected, then the system will not actually create Resources.", selenium.getText("//div[@id='mainContainer']/form/table/tbody/tr[2]/td[2]"));
			log4j.info("Option 'Test?' is available with a checkbox which is selected by default. An instruction 'If selected, then the system will not actually create Resources.' is displayed.");
		} catch (AssertionError Ae) {
			log4j.info(Ae);
			log4j
					.info("Option 'Test?' is available with a checkbox which is selected by default. An instruction 'If selected, then the system will not actually create Resources.' is NOT displayed. ");
			lStrReason = lStrReason
					+ "; "
					+ "Option 'Test?' is available with a checkbox which is selected by default. An instruction 'If selected, then the system will not actually create Resources.' is NOT displayed. ";
		}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Navigate to Upload Details page by clicking view details
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 16-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToUploadDetailsPageForRecentRes(Selenium selenium) throws Exception
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
			selenium.click("link=View Details");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Upload Details", selenium.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("Upload Details screen is displayed.");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Upload Details screen is NOT displayed.");
				lStrReason = lStrReason + "; " + "Upload Details screen is NOT displayed.";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Navigate to Upload Details page by clicking view details
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 8-March-2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToEditResourcePageFromResourceID(Selenium selenium,
			String strResourceName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click("//table[@summary='Upload Details']/"
					+ "tbody/tr/td[3][text()='" + strResourceName + "']"
					+ "/preceding-sibling::td[1]/a");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit Resource", selenium
						.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("Edit Resource screen is displayed.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Edit Resource screen is NOT displayed.");
				lStrReason = "Edit Resource screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: navToEditUserPageFromUserID
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 8-March-2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToEditUserPageFromUserID(Selenium selenium,
			String strUserFulName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click("//table[@summary='Upload Details']/"
					+ "tbody/tr/td[8][text()='" + strUserFulName + "']"
					+ "/preceding-sibling::td[1]/a");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit User", selenium
						.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("Edit User screen is displayed.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Edit User screen is NOT displayed.");
				lStrReason = "Edit User screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "Upload.navToEditUserPageFromUserID failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	

	
	/*******************************************************************************************
	' Description: navToUploadDetailsPageFromUploadListPage
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 11-March-2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToUploadDetailsPageFromUploadListPage(Selenium selenium,
			String strGenDateFormat) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click("//table[@summary='Uploads']/tbody/tr/td[3]" +
					"[text()='"+strGenDateFormat+"']/preceding-sibling::td[2]/a");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Upload Details", selenium
						.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("Upload Details screen is displayed.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Upload Details screen is NOT displayed.");
				lStrReason = "Upload Details screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "Upload.navToUploadDetailsPageFromUploadListPage " +
					"failed to complete due to " +lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: navToUploadDetailsPageFromUploadListPage
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 11-March-2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToUploadDetailsPageFromUploadListPageOfRecentUpload(
			Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click("//table[@summary='Uploads']/tbody/tr[1]/td[1]/a");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Upload Details", selenium
						.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("Upload Details screen is displayed.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Upload Details screen is NOT displayed.");
				lStrReason = "Upload Details screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "Upload.navToUploadDetailsPageFromUploadListPageOfRecentUpload" +
					" failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Verify the data provided while uploading the file are retained in Upload details page
	' Precondition: N/A 
	' Arguments: selenium, strColHeaders,pStrArrUplData
	' Returns: String 
	' Date: 06-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyUplDetailsInUpldListPgeWithVaryingMin(
			Selenium selenium, String[] strColHeaders, String[] pStrArrUplData)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		Date_Time_settings dts = new Date_Time_settings();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals("Upload List", selenium.getText(propElementDetails
						.getProperty("Prop40")));
				log4j.info("'Upload List' screen is displayed. ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Upload List' screen is NOT displayed. ");
				lStrReason = lStrReason + "; "
						+ "'Upload List' screen is NOT displayed. ";
			}

			for (int intCol = 0; intCol < strColHeaders.length; intCol++) {

				if (strColHeaders[intCol].equals("Action")) {

					try {
						assertEquals(
								strColHeaders[intCol],
								selenium
										.getText("//div[@id='mainContainer']/table[2]/thead/tr/th["
												+ (intCol + 1) + "]"));
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed";
					}
				} else {
					try {
						assertEquals(
								strColHeaders[intCol],
								selenium
										.getText("//div[@id='mainContainer']/table[2]/thead/tr/th["
												+ (intCol + 1) + "]/a"));
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The Column Header "
								+ strColHeaders[intCol] + " is NOT displayed";
					}
				}
			}

			for (int intCol = 0; intCol < pStrArrUplData.length; intCol++) {

				if (pStrArrUplData[intCol].equals("View Details")) {
					try {
						assertEquals(
								pStrArrUplData[intCol],
								selenium
										.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
												+ (intCol + 1) + "]/a"));
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is displayed");
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed");
						lStrReason = lStrReason + "; " + "'The specified data "
								+ pStrArrUplData[intCol] + " is NOT displayed";
					}
				} else {

					if (intCol == 2) {

						try {
							String strTimeTemp1 = dts.addTimetoExisting(
									pStrArrUplData[intCol], 1,
									"yyyy-MM-dd HH:mm");
							String strTimeTemp2 = dts.addTimetoExisting(
									pStrArrUplData[intCol], -1,
									"yyyy-MM-dd HH:mm");

							assertTrue(pStrArrUplData[intCol]
									.equals(selenium
											.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
													+ (intCol + 1) + "]"))
									|| strTimeTemp1
											.equals(selenium
													.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
															+ (intCol + 1)
															+ "]"))
									|| strTimeTemp2
											.equals(selenium
													.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
															+ (intCol + 1)
															+ "]")));
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol] + " is displayed");
						} catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol]
									+ " is NOT displayed");
							lStrReason = lStrReason + "; "
									+ "'The specified data "
									+ pStrArrUplData[intCol]
									+ " is NOT displayed";
						}

					} else {
						try {

							assertEquals(
									pStrArrUplData[intCol],
									selenium
											.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
													+ (intCol + 1) + "]"));
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol] + " is displayed");
						} catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol]
									+ " is NOT displayed");
							lStrReason = lStrReason + "; "
									+ "'The specified data "
									+ pStrArrUplData[intCol]
									+ " is NOT displayed";
						}
					}

				}
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	//start//downloadUploadTemplate//
	/*******************************************************************************************
	' Description: Check the upload option is available under setup
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 05-11-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String downloadUploadTemplate(Selenium selenium,
			String strAutoFilePath,String strAutoFileName,String strDownloadPath) throws Exception {
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

			String strArgs[] = { strAutoFilePath, strDownloadPath};
			// Auto it to upload the file
			Runtime.getRuntime().exec(strArgs);
			selenium.windowFocus();
			// click on Upload Template
			selenium.click("link=Upload Template");
			// wait for pop up to appear
			Thread.sleep(5000);
			// Wait for autoit file to finish execution
			String strProcess = "";

			int intCnt = 0;
			do {

				GetProcessList objGPL = new GetProcessList();
				strProcess = objGPL.GetProcessName();
				intCnt++;
				Thread.sleep(1000);
			} while (strProcess.contains(strAutoFileName) && intCnt < 240);

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	//end//downloadUploadTemplate//
	/*******************************************************************************************
	' Description: Verify the data provided while uploading the file are retained in Upload details page
	' Precondition: N/A 
	' Arguments: selenium, strColHeaders,pStrArrUplData
	' Returns: String 
	' Date: 17/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyUplDetailsInUpldDetailsNew(Selenium selenium,String[] pStrArrColHeaders, String[] pStrArrUplData) throws Exception
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
				assertEquals("Upload Details", selenium.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("''Upload Details' screen is displayed. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Upload Details' screen is NOT displayed. ");
				lStrReason = lStrReason + "; " + "'Upload Details' screen is NOT displayed. ";
			}
			for(int intCol=0;intCol<pStrArrColHeaders.length;intCol++){
				if(pStrArrColHeaders[intCol].equals("")==false){
					try {
						assertEquals(pStrArrColHeaders[intCol], selenium.getText("//div[@id='mainContainer']/form/table[2]/thead/tr/th["+(intCol+1)+"]/a"));
						log4j.info("'The Column Header "+pStrArrColHeaders[intCol]+" is displayed");
					}
					catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The Column Header "+pStrArrColHeaders[intCol]+" is NOT displayed");
						lStrReason = lStrReason + "; " + "'The Column Header "+pStrArrColHeaders[intCol]+" is NOT displayed";
					}
				}
			}
			
			for(int intCol=0;intCol<pStrArrUplData.length;intCol++){
				if(pStrArrUplData[intCol].equals("")==false){
					if(intCol==1){
						try {
							assertTrue(selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr/td["+(intCol+1)+"]").matches(pStrArrUplData[intCol]));
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is displayed");
						}
						catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is NOT displayed");
							lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intCol]+" is NOT displayed";
						}
					}else{
						try {
							assertEquals(pStrArrUplData[intCol], selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr/td["+(intCol+1)+"]"));
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is displayed");
						}
						catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is NOT displayed");
							lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intCol]+" is NOT displayed";
						}
					}
				}
			}
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop2351")));
				log4j.info("'Return' button is available.");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Return' button is NOT available.");
				lStrReason = lStrReason + "; " + "'Return' button is NOT available.";
			}
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description: Navigate to Upload Details page by clicking view details
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 8-March-2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToEditResourcePageFromSpecifiedResourceID(Selenium selenium,
			String strResourceName,String strRow) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click("//table[@summary='Upload Details']/"
					+ "tbody/tr["+strRow+"]/td[3][text()='" + strResourceName + "']"
					+ "/preceding-sibling::td[1]/a");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Edit Resource", selenium
						.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("Edit Resource screen is displayed.");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Edit Resource screen is NOT displayed.");
				lStrReason = "Edit Resource screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description: Verify the data provided while uploading the file are retained in Upload details page
	' Precondition: N/A 
	' Arguments: selenium, strColHeaders,pStrArrUplData
	' Returns: String 
	' Date: 17/07/2013
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyUplDetailsInUpldDetailsInRedColor(Selenium selenium,String[] pStrArrColHeaders, String[] pStrArrUplData) throws Exception
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
				assertEquals("Upload Details", selenium.getText(propElementDetails.getProperty("Prop40")));
				log4j.info("''Upload Details' screen is displayed. ");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Upload Details' screen is NOT displayed. ");
				lStrReason = lStrReason + "; " + "'Upload Details' screen is NOT displayed. ";
			}
			for(int intCol=0;intCol<pStrArrColHeaders.length;intCol++){
				if(pStrArrColHeaders[intCol].equals("")==false){
					try {
						assertEquals(pStrArrColHeaders[intCol], selenium.getText("//div[@id='mainContainer']/form/table[2]/thead/tr/th["+(intCol+1)+"]/a"));
						log4j.info("'The Column Header "+pStrArrColHeaders[intCol]+" is displayed");
					}
					catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j.info("'The Column Header "+pStrArrColHeaders[intCol]+" is NOT displayed");
						lStrReason = lStrReason + "; " + "'The Column Header "+pStrArrColHeaders[intCol]+" is NOT displayed";
					}
				}
			}
			
			for(int intCol=0;intCol<pStrArrUplData.length;intCol++){
				if(pStrArrUplData[intCol].equals("")==false){
					if(intCol==1){
						try {
							assertTrue(selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr[@class='error']/td["+(intCol+1)+"]").matches(pStrArrUplData[intCol]));
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is displayed in read color");
						}
						catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is NOT displayed in read color");
							lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intCol]+" is NOT displayed in read color";
						}
					}else{
						try {
							assertEquals(pStrArrUplData[intCol], selenium.getText("//div[@id='mainContainer']/form/table[2]/tbody/tr[@class='error']/td["+(intCol+1)+"]"));
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is displayed in read color");
						}
						catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "+pStrArrUplData[intCol]+" is NOT displayed in read color");
							lStrReason = lStrReason + "; " + "'The specified data "+pStrArrUplData[intCol]+" is NOT displayed in read color";
						}
					}
				}
			}
			
			try {
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("Prop2351")));
				log4j.info("'Return' button is available.");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Return' button is NOT available.");
				lStrReason = lStrReason + "; " + "'Return' button is NOT available.";
			}
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/**********************************************************
	' Description: Navigate to Edit user page by clicking 
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 16-11-2012
	' Author: QSG 
	'----------------------------------------------------------
	' Modified Date: 
	' Modified By: 
	***********************************************************/

	public String navToEditUserPageByClickingOnUser(Selenium selenium,
			String strUser) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click("link=" + strUser);
			selenium.waitForPageToLoad(gstrTimeOut);
			int intCnt = 0;
			do {
				try {
					assertEquals("Edit User",
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
				assertEquals("Edit User", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Edit User' page is displayed ");
			} catch (AssertionError Ae) {

				lStrReason = "'Edit User' page is NOT displayed " + Ae;
				log4j.info("'Edit User' page is NOT displayed " + Ae);
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/****************************************************************
	' Description  :Save And Verify Error Message fill New Upload
	' Precondition :N/A 
	' Arguments    :selenium
	' Returns      :String 
	' Date         :05-11-2012
	' Author       :QSG 
	'----------------------------------------------------------------
	' Modified Date: 
	' Modified By: 
	*****************************************************************/

	public String SavAndVerifyErrorMsgfillNewUpload(Selenium selenium,
			boolean blnSave) throws Exception {
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

				try {
					assertEquals("The following errors occurred on this page:",
							selenium.getText("css=span.emsErrorTitle"));

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()=" +
									"'Invalid column name; expecting: SubResource found: SubResource_Edit']"));
					log4j.info("Invalid column name; expecting: SubResource found: SubResource_Edit is displayed");

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='"
									+ "Invalid column name; expecting: ResourceName found: ResourceName_Edit']"));
					log4j.info("Invalid column name; expecting: ResourceName found: ResourceName_Edit is displayed");

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='"
									+ "Invalid column name; expecting: Abbrev found: Abbrev_Edit']"));
					log4j.info("Invalid column name; expecting: Abbrev found: Abbrev_Edit is displayed");

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='"
									+ "Invalid column name; expecting: ResourceTypeID found: ResourceTypeID_Edit']"));
					log4j.info("Invalid column name; expecting: ResourceTypeID found: ResourceTypeID_Edit is displayed");

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='"
									+ "Invalid column name; expecting: ResourceType found: ResourceType_Edit']"));
					log4j.info("Invalid column name; expecting: ResourceType found: ResourceType_Edit is displayed");

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='"
									+ "Invalid column name; expecting: StandardResourceTypeID found: StandardResourceTypeID_Edit']"));
					log4j.info("Invalid column name; expecting: StandardResourceTypeID found: StandardResourceTypeID_edit is displayed");

					log4j.info("'Appropriate error messages'is displayed while uploading the template.");
				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("'Upload Details' screen is NOT displayed.");
					lStrReason = lStrReason + "; "
							+ "'Upload Details' screen is NOT displayed.";
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/***********************************************************
	' Description  : Canel user and navigating to uploadlist page
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 28/06/2012
	' Author       : QSG 
	'-----------------------------------------------------------
	' Modified Date: 
	' Modified By: 
	************************************************************/

	public String cancelAndNavToUploadListPage(Selenium selenium,
			String[] pStrArrUplData) throws Exception {
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
					assertEquals("Upload List",
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
				assertEquals("Upload List", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Upload List' Screen is displayed ");

				for (int intCol = 0; intCol < pStrArrUplData.length; intCol++) {

					if (pStrArrUplData[intCol].equals("View Details")) {
						try {
							assertNotSame(
									pStrArrUplData[intCol],
									selenium.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
											+ (intCol + 1) + "]/a"));
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol]
									+ " is NOT displayed");
						} catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol] + " is displayed");
							lStrReason = lStrReason + "; "
									+ "'The specified data "
									+ pStrArrUplData[intCol] + " is displayed";
						}
					} else {
						try {
							assertNotSame(
									pStrArrUplData[intCol],
									selenium.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td["
											+ (intCol + 1) + "]"));
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol]
									+ " is NOT displayed");
						} catch (AssertionError Ae) {
							log4j.info(Ae);
							log4j.info("'The specified data "
									+ pStrArrUplData[intCol] + " is displayed");
							lStrReason = lStrReason + "; "
									+ "'The specified data "
									+ pStrArrUplData[intCol] + " is displayed";
						}
					}
				}
			} catch (AssertionError Ae) {

				lStrReason = "'Upload List' Screen  is NOT displayed " + Ae;
				log4j.info("'Upload List'  Screen  is NOT displayed " + Ae);
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
}
