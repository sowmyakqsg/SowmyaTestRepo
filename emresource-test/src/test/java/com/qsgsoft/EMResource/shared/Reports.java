package com.qsgsoft.EMResource.shared;

import static org.junit.Assert.*;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class Reports {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.shared.Reports");

	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties propElementAutoItDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objPaths_Properties=new Paths_Properties();
	
	public String gstrTimeOut;
	
	ReadData rdExcel;
	
	/*******************************************************************************************
	' Description: Navigate to status Report page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 21-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToStatusReports(Selenium selenium) throws Exception
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
			selenium.mouseOver(propElementDetails.getProperty("Prop676"));
			selenium.click(propElementDetails.getProperty("Prop677"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Status Reports Menu", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("'Staus Report Menu page is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Staus Report Menu page is NOT displayed");
				lStrReason = lStrReason + "; " + "Staus Report Menu page is NOT displayed";
			}
			
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	
	/*******************************************************************************************
	' Description: Navigate to Status Summary Report page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 21-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToStatusSummReport(Selenium selenium) throws Exception
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
			selenium.click(propElementDetails.getProperty("Prop679"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Status Summary Report", selenium.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("'Status Summary Report page is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Status Summary Report page is NOT displayed");
				lStrReason = lStrReason + "; " + "Status Summary Report page is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: navToStatusReasonDetail function failed
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 05-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToStatusReasonDetail(Selenium selenium) throws Exception
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
		
			selenium.click(propElementDetails.getProperty("Prop916"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Status Reason Detail Report (Step 1 of 3)", selenium.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("''Status Reason Detail Report (Step 1 of 3)' screen is displayed.");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Status Reason Detail Report (Step 1 of 3)' screen is NOT displayed.");
				lStrReason = lStrReason + "; " + "'Status Reason Detail Report (Step 1 of 3)' screen is NOT displayed.";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description: Enter Data for all the fields and click on Generate Report
	' Precondition: N/A 
	' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	' Returns: String 
	' Date: 21-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterReportDetAndGenReport(Selenium selenium,
			String strResourceVal, String strStatVal, String strStartDate,
			String strEndDate, boolean blnPDF) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("By default Adobe Acrobat (PDF) is selected)");

				if (blnPDF)
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.PDF"));
				else
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.CSV"));

				selenium.click("css=input[name='resourceID'][value='"
						+ strResourceVal + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strStatVal + "']");

				selenium
						.click(propElementDetails
								.getProperty("StatusSummrReport.RepFormat.GenerateRep"));

				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				log4j.info("By default Adobe Acrobat (PDF) is NOT selected)");
				lStrReason = "By default Adobe Acrobat (PDF) is NOT selected)";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	' Description: Enter Data for all the fields and click on Generate Report
	' Precondition: N/A 
	' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	' Returns: String 
	' Date: 17-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterReportDetAndGenReport_SelAll(Selenium selenium,
			String[] strResourceVal, String[] strStatVal, String strStartDate,
			String strEndDate, boolean blnPDF) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("By default Adobe Acrobat (PDF) is selected)");

				if (blnPDF)
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.PDF"));
				else
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.CSV"));

				for (String strRes : strResourceVal) {
					selenium.click("css=input[name='resourceID'][value='"
							+ strRes + "']");
				}

				for (String strST : strStatVal) {
					selenium.click("css=input[name='statusTypeID'][value='"
							+ strST + "']");
				}

				selenium
						.click(propElementDetails
								.getProperty("StatusSummrReport.RepFormat.GenerateRep"));

				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				log4j.info("By default Adobe Acrobat (PDF) is NOT selected)");
				lStrReason = "By default Adobe Acrobat (PDF) is NOT selected)";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	/*******************************************
	 ' Description: Navigate to Status Detail Report page
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 25-06-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String navToStatusDetailReport(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("Reports.StatusDetail_Link"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Status Detail Report (Step 1 of 2)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Status Detail Report (Step 1 of 2) page is displayed");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("'Status Detail Report (Step 1 of 2) page is NOT displayed");
				lStrReason = lStrReason
						+ "; "
						+ "Status Detail Report (Step 1 of 2) page is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************
	 ' Description: Enter Data for all the fields in summary details page and click on Generate Report
	 along with gernerated time
	 ' Precondition: N/A 
	 ' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	 ' Returns: String 
	 ' Date: 25-06-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String[] enterReportSumDetailAndGenReportWitTime(Selenium selenium,
			String strResourceVal, String strStatVal, String strStartDate,
			String strEndDate, boolean blnPDF, String strStatName,
			String strTimeFormat) throws Exception {

		String strReason[] = new String[2];
		strReason[0] = "";
		strReason[1] = "";
		Date_Time_settings dts = new Date_Time_settings();

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			if (blnPDF)
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF"));
			else
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.CSV"));

			selenium.select("css=select[name='statusTypeID']", "label="
					+ strStatName + "");

			selenium.click(propElementDetails.getProperty("SelectReport.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.click("css=input[name='resourceID'][value='"
					+ strResourceVal + "']");

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			Thread.sleep(3000);
			strReason[1] = dts.timeNow(strTimeFormat);
			log4j.info(strReason[1]);

		} catch (Exception e) {
			log4j.info(e);
			strReason[0] = strReason + "; " + e.toString();
		}

		return strReason;
	}

	public String enterReportSTSumDetailDate(Selenium selenium,
			String strStartDate, String strEndDate) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("Adobe Acrobat (PDF) is selected by default. ");
			} catch (AssertionError Ae) {
				lStrReason = "Adobe Acrobat (PDF) is NOT selected by default. ";
				log4j.info("Adobe Acrobat (PDF) is NOT selected by default. ");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	public String enterReportSTSumDetailSTAndNavigate(Selenium selenium,
			String strStatName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.select("css=select[name='statusTypeID']", "label="
					+ strStatName + "");

			selenium.click(propElementDetails.getProperty("SelectReport.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
			
	/*******************************************
	   ' Description: Enter Data for all the fields in summary details page and click on Generate Report
	   along with gernerated time
	   ' Precondition: N/A 
	   ' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	   ' Returns: String 
	   ' Date: 25-06-2012
	   ' Author: QSG 
	   '--------------------------------------------------------------------------------- 
	   ' Modified Date: 
	   ' Modified By: 
	   *******************************************************************************************/

	public String[] enterReportSumDetailAndGenReportWitTimeofMST(
			Selenium selenium, String strResourceVal, String strStatVal,
			String strStartDate, String strEndDate, boolean blnPDF,
			String strStatName, String strStatusValue, String strTimeFormat)
			throws Exception {

		String strReason[] = new String[2];
		strReason[0] = "";
		strReason[1] = "";
		Date_Time_settings dts = new Date_Time_settings();

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			if (blnPDF)
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF"));
			else
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.CSV"));

			selenium.select("css=select[name='statusTypeID']", "label="
					+ strStatName + "");

			selenium.click(propElementDetails.getProperty("SelectReport.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.click("css=input[name='resourceID'][value='"
					+ strResourceVal + "']");

			selenium.click("css=input[name='statusID'][value='"
					+ strStatusValue + "']");

			strReason[1] = dts.timeNow(strTimeFormat);
			log4j.info(strReason[1]);

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			strReason[0] = strReason + "; " + e.toString();
		}

		return strReason;
	}
	
	/*******************************************
	 ' Description: Enter Data for all the fields in summary details page and click on Generate Report
	 ' Precondition: N/A 
	 ' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	 ' Returns: String 
	 ' Date: 25-06-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String enterReportSumDetailAndGenReport(Selenium selenium,
			String strResourceVal, String strStatVal, String strStartDate,
			String strEndDate, boolean blnPDF, String strStatName)
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
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			if (blnPDF)
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF"));
			else
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.CSV"));

			selenium.select("css=select[name='statusTypeID']", "label="
					+ strStatName + "");

			selenium.click(propElementDetails.getProperty("SelectReport.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

			selenium.click("css=input[name='resourceID'][value='"
					+ strResourceVal + "']");

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));

			selenium.waitForPageToLoad(gstrTimeOut);
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	/*******************************************************************************************
	' Description: Navigate to status snapshot report page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 26-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToStatusSnapshotReport(Selenium selenium) throws Exception
	{
		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try{
			selenium.click(propElementDetails.getProperty("Prop732"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Status Snapshot Report", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("''Status Snapshot Report' screen is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("''Status Snapshot Report' screen is NOT displayed");
				lStrReason = lStrReason + "; " + "'Status Snapshot Report' screen is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Enter the data in Status snapshot report and click on Generate Report
	' Precondition: N/A 
	' Arguments: selenium, pStrGenDate, pStrHour, pStrMint
	' Returns: String 
	' Date: 26-06-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterStatSnapshotRepAndGenRep(Selenium selenium,
			String pStrGenDate, String pStrHour, String pStrMint)
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
			selenium.type(propElementDetails.getProperty("Prop733"),
					pStrGenDate);
			selenium
					.select(propElementDetails.getProperty("Prop734"), pStrHour);
			selenium
					.select(propElementDetails.getProperty("Prop735"), pStrMint);

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));

			selenium.waitForPageToLoad(gstrTimeOut);
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	/*******************************************************************************************
	' Description: Navigate to status reason summary report page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 02-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToStatusReasonSummReport(Selenium selenium) throws Exception
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
			selenium.click(propElementDetails.getProperty("Report.StatusRep.StatusReasSummry"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Status Reason Summary Report (Step 1 of 3)", selenium.getText(propElementDetails
						.getProperty("Header.Text")));
				log4j.info("Status Reason Summary Report (Step 1 of 3) screen is displayed");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Status Reason Summary Report (Step 1 of 3) screen is NOT displayed");
				lStrReason = lStrReason + "; " + "Status Reason Summary Report (Step 1 of 3) screen is NOT displayed";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Enter the data in Status Reason Summary report and click on Generate Report
	' Precondition: N/A 
	' Arguments: selenium, strResourceVal, strStatusType, strStartDate,strEndDate,blnPDF,strStatusVal,strReasonVal
	' Returns: String 
	' Date: 02-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterStatReasSummaryRepAndGenRep(Selenium selenium,
			String strResourceVal, String strStatusType, String strStartDate,
			String strEndDate, boolean blnPDF, String strStatusVal,
			String strReasonVal) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("By default Adobe Acrobat (PDF) is selected)");

				if (blnPDF)
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.PDF"));
				else
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.CSV"));

				selenium.click("css=input[name='resourceID'][value='"
						+ strResourceVal + "']");

				selenium.click(propElementDetails.getProperty("Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Reason Summary Report (Step 2 of 3)",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					
					log4j
							.info("Status Reason Summary Report (Step 2 of 3) screen is displayed");

					selenium.select("css=select[name='statusTypeID']", "label="
							+ strStatusType);

					selenium.click(propElementDetails.getProperty("Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals(
								"Status Reason Summary Report (Step 3 of 3)",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
								.info("Status Reason Summary Report (Step 3 of 3) screen is displayed");

						selenium
								.click("css=input[name='statusReasonID'][value='"
										+ strStatusVal
										+ ":"
										+ strReasonVal
										+ "']");

						selenium.click(propElementDetails.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
						
						selenium.waitForPageToLoad(gstrTimeOut);
						
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j
								.info("Status Reason Summary Report (Step 3 of 3) screen is NOT displayed");
						lStrReason = lStrReason
								+ "; "
								+ "Status Reason Summary Report (Step 3 of 3) screen is NOT displayed";
					}

				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j
							.info("Status Reason Summary Report (Step 3 of 3) screen is NOT displayed");
					lStrReason = lStrReason
							+ "; "
							+ "Status Reason Summary Report (Step 3 of 3) screen is NOT displayed";
				}

			} catch (AssertionError Ae) {
				log4j.info("By default Adobe Acrobat (PDF) is NOT selected)");
				lStrReason = "By default Adobe Acrobat (PDF) is NOT selected)";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Enter the data in Status Reason Summary report and click on Generate Report
	' Precondition: N/A 
	' Arguments: selenium, strResourceVal, strStatusType, strStartDate,strEndDate,blnPDF,strStatusVal,strReasonVal
	' Returns: String 
	' Date: 02-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterStatReasSummaryRepAndGenRep_SelAll(Selenium selenium,
			String[] strResourceVal, String strStatusType, String strStartDate,
			String strEndDate, boolean blnPDF, String strStatusVal,
			String strReasonVal[],boolean blnRegAgr) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("By default Adobe Acrobat (PDF) is selected)");

				if (blnPDF)
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.PDF"));
				else
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.CSV"));

				for(String strRes:strResourceVal){
					selenium.click("css=input[name='resourceID'][value='"
							+ strRes + "']");
				}
				selenium.click(propElementDetails.getProperty("Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Reason Summary Report (Step 2 of 3)",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					
					log4j
							.info("Status Reason Summary Report (Step 2 of 3) screen is displayed");

					selenium.select("css=select[name='statusTypeID']", "label="
							+ strStatusType);

					selenium.click(propElementDetails.getProperty("Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals(
								"Status Reason Summary Report (Step 3 of 3)",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
								.info("Status Reason Summary Report (Step 3 of 3) screen is displayed");
						for(String strResn:strReasonVal){
							selenium
									.click("css=input[name='statusReasonID'][value='"
											+ strStatusVal
											+ ":"
											+ strResn
											+ "']");
						}
						
						
						if(blnRegAgr)
							selenium.click(propElementDetails.getProperty("StatusReasSummrReport.RepFormat.RegAggrInfo"));
						selenium.click(propElementDetails.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
						selenium.waitForPageToLoad(gstrTimeOut);
						
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j
								.info("Status Reason Summary Report (Step 3 of 3) screen is NOT displayed");
						lStrReason = lStrReason
								+ "; "
								+ "Status Reason Summary Report (Step 3 of 3) screen is NOT displayed";
					}

				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j
							.info("Status Reason Summary Report (Step 3 of 3) screen is NOT displayed");
					lStrReason = lStrReason
							+ "; "
							+ "Status Reason Summary Report (Step 3 of 3) screen is NOT displayed";
				}

			} catch (AssertionError Ae) {
				log4j.info("By default Adobe Acrobat (PDF) is NOT selected)");
				lStrReason = "By default Adobe Acrobat (PDF) is NOT selected)";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Enter the data in Status Reason Summary report and check the error message
	' Precondition: N/A 
	' Arguments: selenium, strResourceVal, strStatusType, strStartDate,strEndDate,blnPDF,strStatusVal,strReasonVal
	' Returns: String 
	' Date: 02-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterStatReasSummaryRepAndCheckErrorMsg(Selenium selenium,
			String[] strResourceVal, String strStartDate,
			String strEndDate, boolean blnPDF) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("By default Adobe Acrobat (PDF) is selected)");

				if (blnPDF)
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.PDF"));
				else
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.CSV"));

				for(String strRes:strResourceVal){
					selenium.click("css=input[name='resourceID'][value='"
							+ strRes + "']");
				}
				selenium.click(propElementDetails.getProperty("Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("The following error occurred on this page:",
							selenium.getText("css=span.emsErrorTitle"));
					assertEquals(
							"There are no reported status reasons for the selected resources within the chosen date range.",
							selenium.getText("css=div.emsError > ul > li"));

					log4j.info("Error message 'The following error occurred on this page:There are no reported status reasons for the selected resources within the chosen date range' is displayed.");

				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("Error message 'The following error occurred on this page:There are no reported status reasons for the selected resources within the chosen date range' is NOT displayed.");
					lStrReason = lStrReason
							+ "; "
							+ "Error message 'The following error occurred on this page:There are no reported status reasons for the selected resources within the chosen date range' is NOT displayed.";
				}

			} catch (AssertionError Ae) {
				log4j.info("By default Adobe Acrobat (PDF) is NOT selected)");
				lStrReason = "By default Adobe Acrobat (PDF) is NOT selected)";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: enterRepDetailsAndGenStatusReasonDetRep function failed
	' Precondition: N/A 
	' Arguments: selenium, pStrStartDate, pStrEndDate, pStrStatType
	' Returns: String 
	' Date: 05-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterRepDetailsAndGenStatusReasonDetRep(Selenium selenium,
			String strResourceVal, String strStatusType, String strStartDate,
			String strEndDate, boolean blnPDF, String strStatusVal,
			String strReasonVal) throws Exception{

		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("By default Adobe Acrobat (PDF) is selected)");

				if (blnPDF)
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.PDF"));
				else
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.CSV"));

				selenium.click("css=input[name='resourceID'][value='"
						+ strResourceVal + "']");

				selenium.click(propElementDetails.getProperty("Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Reason Detail Report (Step 2 of 3)",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					
					log4j
							.info("Status Reason Detail Report (Step 2 of 3) screen is displayed");

					selenium.select("css=select[name='statusTypeID']", "label="
							+ strStatusType);

					selenium.click(propElementDetails.getProperty("Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals(
								"Status Reason Detail Report (Step 3 of 3)",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
								.info("Status Reason Detail Report (Step 3 of 3) screen is displayed");

						selenium
								.click("css=input[name='statusReasonID'][value='"
										+ strStatusVal
										+ ":"
										+ strReasonVal
										+ "']");

						selenium.click(propElementDetails.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
						
						selenium.waitForPageToLoad(gstrTimeOut);
						
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j
								.info("Status Reason Detail Report (Step 3 of 3) screen is NOT displayed");
						lStrReason = lStrReason
								+ "; "
								+ "Status Reason Detail Report (Step 3 of 3) screen is NOT displayed";
					}

				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j
							.info("Status Reason Detail Report (Step 2 of 3) screen is NOT displayed");
					lStrReason = lStrReason
							+ "; "
							+ "Status Reason Detail Report (Step 2 of 3) screen is NOT displayed";
				}

			} catch (AssertionError Ae) {
				log4j.info("By default Adobe Acrobat (PDF) is NOT selected)");
				lStrReason = "By default Adobe Acrobat (PDF) is NOT selected)";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: enterRepDetailsAndGenStatusReasonDetRep function failed
	' Precondition: N/A 
	' Arguments: selenium, pStrStartDate, pStrEndDate, pStrStatType
	' Returns: String 
	' Date: 05-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterRepDetailsAndGenStatusReasonDetRep_SelAll(Selenium selenium,
			String strResourceVal[], String strStatusType, String strStartDate,
			String strEndDate, boolean blnPDF, String strStatusVal,
			String strReasonVal[]) throws Exception{

		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("By default Adobe Acrobat (PDF) is selected)");

				if (blnPDF)
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.PDF"));
				else
					selenium.click(propElementDetails
							.getProperty("StatusSummrReport.RepFormat.CSV"));

				for(String strRes:strResourceVal){
					selenium.click("css=input[name='resourceID'][value='"
							+ strRes + "']");
				}

				selenium.click(propElementDetails.getProperty("Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Reason Detail Report (Step 2 of 3)",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					
					log4j
							.info("Status Reason Detail Report (Step 2 of 3) screen is displayed");

					selenium.select("css=select[name='statusTypeID']", "label="
							+ strStatusType);

					selenium.click(propElementDetails.getProperty("Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals(
								"Status Reason Detail Report (Step 3 of 3)",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
								.info("Status Reason Detail Report (Step 3 of 3) screen is displayed");

						for(String strReas:strReasonVal){
							selenium
									.click("css=input[name='statusReasonID'][value='"
											+ strStatusVal
											+ ":"
											+ strReas
											+ "']");
						}

						selenium.click(propElementDetails.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
						
						selenium.waitForPageToLoad(gstrTimeOut);
						
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j
								.info("Status Reason Detail Report (Step 3 of 3) screen is NOT displayed");
						lStrReason = lStrReason
								+ "; "
								+ "Status Reason Detail Report (Step 3 of 3) screen is NOT displayed";
					}

				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j
							.info("Status Reason Detail Report (Step 2 of 3) screen is NOT displayed");
					lStrReason = lStrReason
							+ "; "
							+ "Status Reason Detail Report (Step 2 of 3) screen is NOT displayed";
				}

			} catch (AssertionError Ae) {
				log4j.info("By default Adobe Acrobat (PDF) is NOT selected)");
				lStrReason = "By default Adobe Acrobat (PDF) is NOT selected)";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: Navigate to Monthly Assessment Report page
	' Precondition: N/A 
	' Arguments: selenium
	' Returns: String 
	' Date: 12-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String navToMonthlyAssesmRep(Selenium selenium) throws Exception
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
			
			selenium.click(propElementDetails.getProperty("Report.StatusRep.MonthlyAssmRep"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Monthly Status Assessment Report (Step 1 of 3)", selenium.getText(propElementDetails
						.getProperty("Header.Text")));

				log4j.info("Monthly Status Assessment Report (Step 1 of 3)' screen is displayed.");
			}
			catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Monthly Status Assessment Report (Step 1 of 3)' screen is NOT displayed.");
				lStrReason = lStrReason + "; " + "Monthly Status Assessment Report (Step 1 of 3)' screen is NOT displayed.";
			}
			
			
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	' Description: enterRepDetailsAndGenStatusReasonDetRep function failed
	' Precondition: N/A 
	' Arguments: selenium, pStrStartDate, pStrEndDate, pStrStatType
	' Returns: String 
	' Date: 05-07-2012
	' Author: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String enterRepDetailsAndGenMonthlyStatusAssmRep(Selenium selenium,
			String strMonth, String strYear, String strResVal,
			boolean blnHTML, String strStatType,String strStatusVal,
			String strReasonVal) throws Exception{

		String lStrReason="";

		//Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		//Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");

		try{
			
			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("MonthlyAssmRep.ReportFormat.HTML")));
												
				assertEquals(strMonth,selenium.getSelectedLabel(propElementDetails
						.getProperty("MonthlyAssmRep.Month")));
				assertEquals(strMonth,selenium.getSelectedLabel(propElementDetails
						.getProperty("MonthlyAssmRep.Year")));
				
				log4j.info("(by default current month,year and Web Browser (HTML) format will be selected)");
				
				selenium.select(propElementDetails.getProperty("MonthlyAssmRep.Month"), "label="+strMonth);
				selenium.select(propElementDetails.getProperty("MonthlyAssmRep.Year"), "label="+strYear);		
				
				selenium.click(propElementDetails.getProperty("MonthlyAssmRep.ReportFormat.HTML"));
				
				selenium.click(propElementDetails.getProperty("MonthlyAssmRep.ReportFormat.CSV"));
				
				selenium.click("css=input[name='resourceID'][value'"+strResVal+"']");
				
				if (blnHTML)
					selenium.click(propElementDetails
							.getProperty("MonthlyAssmRep.ReportFormat.HTML"));
				else
					selenium.click(propElementDetails
							.getProperty("MonthlyAssmRep.ReportFormat.CSV"));
			
				selenium.click(propElementDetails.getProperty("Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Status Reason Detail Report (Step 2 of 3)",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					
					log4j
							.info("Status Reason Detail Report (Step 2 of 3) screen is displayed");

					/*selenium.select("css=select[name='statusTypeID']", "label="
							+ strStatusType);*/

					selenium.click(propElementDetails.getProperty("Next"));
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals(
								"Status Reason Detail Report (Step 3 of 3)",
								selenium.getText(propElementDetails
										.getProperty("Header.Text")));
						log4j
								.info("Status Reason Detail Report (Step 3 of 3) screen is displayed");

						selenium
								.click("css=input[name='statusReasonID'][value='"
										+ strStatusVal
										+ ":"
										+ strReasonVal
										+ "']");

						selenium.click(propElementDetails.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
						
						selenium.waitForPageToLoad(gstrTimeOut);
						
					} catch (AssertionError Ae) {
						log4j.info(Ae);
						log4j
								.info("Status Reason Detail Report (Step 3 of 3) screen is NOT displayed");
						lStrReason = lStrReason
								+ "; "
								+ "Status Reason Detail Report (Step 3 of 3) screen is NOT displayed";
					}

				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j
							.info("Status Reason Detail Report (Step 2 of 3) screen is NOT displayed");
					lStrReason = lStrReason
							+ "; "
							+ "Status Reason Detail Report (Step 2 of 3) screen is NOT displayed";
				}

			} catch (AssertionError Ae) {
				log4j.info("(by default current month,year and Web Browser (HTML) format will NOT be selected)");
				lStrReason = "(by default current month,year and Web Browser (HTML) format will NOT be selected)";
			}
		}catch(Exception e){
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************
	 ' Description: Navigate to Event Report page
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 17-07-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	 public String navToEventReports(Selenium selenium) throws Exception {
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
	   selenium.mouseOver(propElementDetails.getProperty("Prop676"));
	   selenium.click(propElementDetails.getProperty("EventReport"));
	   selenium.waitForPageToLoad(gstrTimeOut);
	   try {
	    assertEquals("Event Reports Menu", selenium
	      .getText(propElementDetails.getProperty("Header.Text")));

	    log4j.info("'Event Reports Menu page is displayed");
	   } catch (AssertionError Ae) {
	    log4j.info(Ae);
	    log4j.info("'Event Reports Menu page is NOT displayed");
	    lStrReason = lStrReason + "; "
	      + "Event Reports Menu page is NOT displayed";
	   }

	  } catch (Exception e) {
	   log4j.info(e);
	   lStrReason = lStrReason + "; " + e.toString();
	  }

	  return lStrReason;
	 }
	 

	 /*******************************************************************************************
	  ' Description: Navigate to Event snapshot report page
	  ' Precondition: N/A 
	  ' Arguments: selenium
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	 public String navToEventSnapshotReport(Selenium selenium) throws Exception {
	  String lStrReason = "";

	  // Create an object to refer to the Element ID Properties file
	  ElementId_properties objelementProp = new ElementId_properties();
	  propElementDetails = objelementProp.ElementId_FilePath();

	  // Create an object to refer to the Environment Properties file
	  ReadEnvironment objReadEnvironment = new ReadEnvironment();
	  propEnvDetails = objReadEnvironment.readEnvironment();
	  gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	  try {
	   selenium.click(propElementDetails
	     .getProperty("EventReportSnapShot"));
	   selenium.waitForPageToLoad(gstrTimeOut);
	   try {
	    assertEquals("Event Snapshot Report (Step 1 of 2)", selenium
	      .getText(propElementDetails.getProperty("Header.Text")));
	    log4j
	      .info("'Event Snapshot Report (Step 1 of 2) screen is displayed");
	   } catch (AssertionError Ae) {
	    log4j.info(Ae);
	    log4j
	      .info("'Event Snapshot Report (Step 1 of 2) screen is NOT displayed");
	    lStrReason = lStrReason
	      + "; "
	      + "'Event Snapshot Report (Step 1 of 2) screen is NOT displayed";
	   }
	  } catch (Exception e) {
	   log4j.info(e);
	   lStrReason = lStrReason + "; " + e.toString();
	  }

	  return lStrReason;
	 }

	 
	 /*******************************************************************************************
	  ' Description: Enter event start date and end date in event snap shot report
	  ' Precondition: N/A 
	  ' Arguments: selenium,strStartDate,strEndDate,strETValue
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	 public String enterEvntReportStartDateNEndDate(Selenium selenium,
	   String strStartDate, String strEndDate, String strETValue)
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
	   selenium.type(propElementDetails
	     .getProperty("EventSanapShotReport.StartDate"), strStartDate);
	   selenium.type(propElementDetails
	     .getProperty("EventSanapShotReport.EndDate"), strEndDate);

	   selenium.click("css=input[name='eventTypeID'][value='" + strETValue
	     + "']");
	   selenium.click(propElementDetails
	     .getProperty("EventReportSnapShot.Next"));
	   selenium.waitForPageToLoad(gstrTimeOut);
	  } catch (Exception e) {
	   log4j.info(e);
	   lStrReason = lStrReason + ";" + e.toString();
	  }

	  return lStrReason;
	 }
	 
	 /*******************************************************************************************
	  ' Description: Enter event value to generate  snap shot report
	  ' Precondition: N/A 
	  ' Arguments: selenium,strStartDate,strEndDate,strETValue,strHr,strMin
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	public String enterEvntSnapshotGenerateReport(Selenium selenium,
			String strSnapshotDate, String strEventValue, String strHr,
			String strMin) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.click("css=input[name='eventID'][value='" + strEventValue
					+ "']");
			selenium.type(propElementDetails
					.getProperty("EventSanapShotReport.EvntSnapShotDate"),
					strSnapshotDate);

			selenium
					.select(propElementDetails
							.getProperty("EventSanapShotReport.Hour"), "label="
							+ strHr);
			selenium
					.select(propElementDetails
							.getProperty("EventSanapShotReport.Min"), "label="
							+ strMin);

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	 
	 
	 /*******************************************************************************************
	 ' Description: verify error message in status snapshot report page
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 30/07/2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String varErrorMsgInsnapShotReppage(Selenium selenium)
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
			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("Report date is required.", selenium
						.getText(propElementDetails.getProperty("UpdateStatus.ErrMsg")));
				log4j
						.info("'An error message 'Report date is required.' is displayed. ");
			} catch (AssertionError Ae) {
				log4j
						.info("'An error message 'Report date is required.' is NOT displayed. ");
				lStrReason = lStrReason
						+ "; "
						+ "An error message 'Report date is required.' is NOT displayed. ";
			}
			try {
				assertFalse(selenium.isTextPresent("Report Menu"));
				log4j.info("Report is not generated.  ");
			} catch (AssertionError Ae) {
				log4j.info("Report is generated.  ");
				lStrReason = "Report is generated. ";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

		/*******************************************************************************************
		' Description: Navigate to status Report page
		' Precondition: N/A 
		' Arguments: selenium
		' Returns: String 
		' Date: 21-06-2012
		' Author: QSG 
		'--------------------------------------------------------------------------------- 
		' Modified Date: 
		' Modified By: 
		*******************************************************************************************/

		public String chkForStatusReportMenuTab(Selenium selenium) throws Exception
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
				try {
					assertFalse(selenium.isElementPresent(propElementDetails
							.getProperty("Prop677")));

					log4j.info("'Staus Report Menu page is  not available. ");
				}
				catch (AssertionError Ae) {
					log4j.info("'Staus Report Menu page is  available. ");
					lStrReason = lStrReason + "; " + "Staus Report Menu page is available. ";
				}
				
				
			}catch(Exception e){
				log4j.info(e);
				lStrReason = lStrReason + "; " + e.toString();
			}

			return lStrReason;
		}
		
		
		 /*******************************************************************************************
		 ' Description: verify error message in status snapshot report page
		 ' Precondition: N/A 
		 ' Arguments: selenium
		 ' Returns: String 
		 ' Date: 30/07/2012
		 ' Author: QSG 
		 '--------------------------------------------------------------------------------- 
		 ' Modified Date: 
		 ' Modified By: 
		 *******************************************************************************************/

	public String varErrorMsgforReportGen(Selenium selenium,
			String pStrGenDate, String pStrHour, String pStrMint, String strMsg)
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
			selenium.type(propElementDetails.getProperty("Prop733"),
					pStrGenDate);
			selenium
					.select(propElementDetails.getProperty("Prop734"), pStrHour);
			selenium
					.select(propElementDetails.getProperty("Prop735"), pStrMint);

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals(strMsg, selenium
						.getText(propElementDetails.getProperty("UpdateStatus.ErrMsg")));
				log4j
						.info("An error message 'The report date/time may not be in the future.' is displayed.");
			} catch (AssertionError Ae) {
				log4j
						.info("'An error message 'The report date/time may not be in the future.' is displayed. ");
				lStrReason = lStrReason
						+ "; "
						+ "An error message 'The report date/time may not be in the future.' is displayed. ";
			}
			try {
				assertFalse(selenium.isTextPresent("Report Menu"));
				log4j.info("Report is not generated.  ");
			} catch (AssertionError Ae) {
				log4j.info("Report is generated.  ");
				lStrReason = "Report is generated. ";
			}
		} catch (Exception e) {
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	 
	 /*******************************************************************************************
	  ' Description: Navigate to Event snapshot report page
	  ' Precondition: N/A 
	  ' Arguments: selenium
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

  public String navToEventDetailReport(Selenium selenium) throws Exception {
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
			selenium.click(propElementDetails
					.getProperty("EventReportDetail"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Event Detail Report (Step 1 of 2)",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Event Detail Report (Step 1 of 2) screen is displayed");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'Event Detail Report (Step 1 of 2) screen is NOT displayed");
				lStrReason = lStrReason
						+ "; "
						+ "'Event Detail Report (Step 1 of 2) screen is NOT displayed";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
  
  /*******************************************************************************************
  ' Description: Enter event value to generate  snap shot report
  ' Precondition: N/A 
  ' Arguments: selenium,strStartDate,strEndDate,strETValue,strHr,strMin
  ' Returns: String 
  ' Date: 17-07-2012
  ' Author: QSG 
  '--------------------------------------------------------------------------------- 
  ' Modified Date: 
  ' Modified By: 
  *******************************************************************************************/
 
	public String enterEvntDetalRepGenerateReport(Selenium selenium,
			String strSnapshotDate, String strEventValue, String strResValue,
			String strStartDate, String strEndDate) throws Exception {
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
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);
			selenium.click("css=input[name='eventID'][value='" + strEventValue
					+ "']");

			// Click on resource

			selenium.click("css=[name='resourceID'][value='" + strResValue
					+ "']");

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	/****************************************************************************************
	 ' Description: verify calender widgets in Status Detail Report page
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 25-06-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String verifyCalndrWidInStatusDetailReport(Selenium selenium,
			String strSTname) throws Exception {
		String lStrReason = "";

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
						.isElementPresent("//input[@id='StartDate']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='ui-datepicker-div']"));
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is displayed with:1. 'Start Date' field (with calender widget) ");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is NOT displayed with:1. 'Start Date' field (with calender widget)");
				lStrReason = lStrReason
						+ "; "
						+ "Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:1. 'Start Date' field (with calender widget)";
			}
			
			try {
				assertTrue(selenium
						.isElementPresent("//input[@id='EndDate']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='ui-datepicker-div']"));
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is displayed with:2. 'End Date' field (with calender widget)");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is NOT displayed with:2. 'End Date' field (with calender widget)");
				lStrReason = lStrReason
						+ "; "
						+ "Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:2. 'End Date' field (with calender widget)";
			}

			try {
				assertTrue(selenium
						.isChecked("css=input[name='reportFormat'][value='PDF']"));
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is displayed with:3. 'Report Format' (with PDF and CSV "
								+ "options, PDF is selected by default) ");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is NOT displayed with:3. 'Report Format' (with PDF and CSV"
								+ " options, PDF is selected by default) ");
				lStrReason = lStrReason + "; "
						+ "Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:3. 'Report Format' (with PDF "
						+ "and CSV options, PDF is selected by default) ";
			}

			try {
				assertTrue(selenium
						.isElementPresent("//select[@name='statusTypeID']"
								+ "/option[text()='" + strSTname + "']"));
				log4j.info("Status Detail Report (Step 1 of 2)' screen "
						+ "is displayed with:4. Status Types dropdown "
						+ "with status types " + strSTname);

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:4. Status Types dropdown "
						+ "with status types " + strSTname);
				lStrReason = lStrReason + "; "
						+ "Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:4. Status Types dropdown "
						+ "with status types " + strSTname;
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	 
	 /***********************************************************************
		'Description	:Verify Resource Reports Menu page is displayed
		'Precondition	:None
		'Arguments		:selenium
		'Returns		:String
		'Date	 		:14-May-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'14-May-2012                               <Name>
		************************************************************************/
		
		public String navResourceReportsMenu(Selenium selenium) throws Exception {

			String strErrorMsg = "";// variable to store error mesage

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				selenium.mouseOver(propElementDetails
						.getProperty("Prop676"));

				selenium.click(propElementDetails
						.getProperty("Report.ResourceReportLink"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("", strErrorMsg);

					try {
						assertEquals("Resource Reports Menu", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Resource Reports Menu page is displayed");

					} catch (AssertionError Ae) {

						strErrorMsg = "Resource Reports Menu page is NOT displayed" + Ae;
						log4j.info("Resource Reports Menu page is NOT displayed" + Ae);
					}
				} catch (AssertionError Ae) {

					strErrorMsg = strErrorMsg + Ae;
					log4j.info(strErrorMsg + Ae);
				}
			} catch (Exception e) {
				log4j.info("navResourceReportsMenu function failed" + e);
				strErrorMsg = "navResourceReportsMenu function failed" + e;
			}
			return strErrorMsg;
		}
		
		 /***********************************************************************
		'Description	:Verify Resource Reports Menu page is displayed
		'Precondition	:None
		'Arguments		:selenium
		'Returns		:String
		'Date	 		:14-Sep-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'14-Sep-2012                               <Name>
		************************************************************************/
		
		public String navResourceDetailReport(Selenium selenium) throws Exception {

			String strErrorMsg = "";// variable to store error mesage

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				selenium.click(propElementDetails
						.getProperty("Report.ResourceDetailLink"));
				selenium.waitForPageToLoad(gstrTimeOut);

			

					try {
						assertEquals("Resource Detail Report", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));

						log4j.info("Resource Detail Report page is displayed");

					} catch (AssertionError Ae) {

						strErrorMsg = "Resource Detail Report page is NOT displayed" + Ae;
						log4j.info("Resource Detail Report page is NOT displayed" + Ae);
					}
				
			} catch (Exception e) {
				log4j.info("navResourceDetailReport function failed" + e);
				strErrorMsg = "navResourceDetailReport function failed" + e;
			}
			return strErrorMsg;
		}
	  
		
		 /***********************************************************************
		'Description	:Verify Resource List in Resource Detail Report page
		'Precondition	:None
		'Arguments		:selenium
		'Returns		:String
		'Date	 		:14-Sep-2012
		'Author			:QSG
		'-----------------------------------------------------------------------
		'Modified Date                            Modified By
		'14-Sep-2012                               <Name>
		************************************************************************/
		
	public String verifyRSInRsDeatilReport(Selenium selenium, String strRSValue)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Resource Detail Report", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Resource Detail Report page is displayed");

				if (selenium.getCssCount("css=input[name='resourceID']")
						.toString().compareTo("1") == 0) {

					try {
						assertTrue(selenium
								.isElementPresent("css=input[name='resourceID'][value='"
										+ strRSValue + "']"));

						log4j
								.info("(List of all the resources in region with check box ");
					} catch (AssertionError Ae) {

						strErrorMsg = "Resource Detail Report page is NOT displayed"
								+ Ae;
						log4j
								.info("(List of all the resources in region with check box are NOT present");

					}

				} else {

					try {
						assertTrue(selenium
								.isElementPresent("css=input[name='resourceID'][value='"
										+ strRSValue + "']"));

						log4j
								.info("(List of all the resources in region with check box ");
					} catch (AssertionError Ae) {

						strErrorMsg = "Resource Detail Report page is NOT displayed"
								+ Ae;
						log4j
								.info("(List of all the resources in region with check box are NOT present");

					}

					try {
						assertTrue(selenium
								.isElementPresent("css=input[value='resourceID'][name='SELECT_ALL']"));

						log4j
								.info("Resources:Select All(Check box) is Present");
					} catch (AssertionError Ae) {

						strErrorMsg = "Resources:Select All(Check box) is NOT Present"
								+ Ae;
						log4j
								.info("Resources:Select All(Check box) is NOT Present");

					}

				}

			} catch (AssertionError Ae) {

				strErrorMsg = "Resource Detail Report page is NOT displayed"
						+ Ae;
				log4j.info("Resource Detail Report page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("verifyRSInRsDeatilReport function failed" + e);
			strErrorMsg = "verifyRSInRsDeatilReport function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	'Description	:Generate Resource Detail Report 
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:14-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'14-Sep-2012                               <Name>
	************************************************************************/
	

	public String generateResourceDetailReport(Selenium selenium,
			String strRSValue[]) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Resource Detail Report", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Resource Detail Report page is displayed");

				for(String s:strRSValue){
					if(selenium.isChecked("css=input[name='resourceID'][value='" + s + "']")==false){
						selenium
						.click("css=input[name='resourceID'][value='" + s + "']");
					}
				}
				
				selenium
						.click(propElementDetails
								.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {

				strErrorMsg = "Resource Detail Report page is NOT displayed"
						+ Ae;
				log4j.info("Resource Detail Report page is NOT displayed" + Ae);
			}

		} catch (Exception e) {
			log4j.info("generateResourceDetailReport function failed" + e);
			strErrorMsg = "generateResourceDetailReport function failed" + e;
		}
		return strErrorMsg;
	}
	
	 /***********************************************************************
	'Description	:Check By default PDF radion button is selected in status detail report page
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:5-Oct-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'5-Oct-2012                                 <Name>
	************************************************************************/
	
	public String checkPDFIsSelectedByDefault(Selenium selenium)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertTrue(selenium.isChecked(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF")));
				log4j.info("Adobe Acrobat (PDF) is selected by default. ");
			} catch (AssertionError Ae) {
				lStrReason = "Adobe Acrobat (PDF) is NOT selected by default. ";
				log4j.info("Adobe Acrobat (PDF) is NOT selected by default. ");
			}

		} catch (Exception e) {
			log4j.info("checkPDFIsSelectedByDefault function failed" + e);
			lStrReason = "checkPDFIsSelectedByDefault function failed" + e;
		}
		return lStrReason;
	}

/*******************************************************************************************
' Description: Navigating to Statewide Resource Detail Report Page
' Precondition: N/A 
' Arguments: selenium
' Returns: String 
' Date: 31/10/2012
' Author: QSG 
'--------------------------------------------------------------------------------- 
' Modified Date: 
' Modified By: 
*******************************************************************************************/

public String navToStatewideResourceDetailReportPage(Selenium selenium) throws Exception
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
		selenium.click(propElementDetails.getProperty("Prop2249"));
		selenium.waitForPageToLoad(gstrTimeOut);
		try {
			assertEquals("Statewide Resource Detail Report (Step 1 of 2)", selenium
					.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("'Statewide Resource Detail Report (Step 1 of 2) page is displayed");
		}
		catch (AssertionError Ae) {
			log4j.info(Ae);
			log4j.info("'Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed");
			lStrReason = lStrReason + "; " + "Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed";
		}
	}catch(Exception e){
		log4j.info(e);
		lStrReason = lStrReason + "; " + e.toString();
	}

	return lStrReason;
}

/***********************************************************************
'Description	:Generate Resource Detail Report 
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:14-Sep-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'14-Sep-2012                               <Name>
************************************************************************/


public String selectStdResTypeForReport(Selenium selenium,
		String strStdResTypeVal,String strStdRt) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	rdExcel = new ReadData();
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	try {

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try {
			assertEquals("Statewide Resource Detail Report (Step 1 of 2)",
					selenium.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("'Statewide Resource Detail Report (Step 1 of 2) page is displayed");

		try {
			assertTrue(selenium.isElementPresent("css=input[name='standardResourceTypeID']" +
					"[value='"+strStdResTypeVal+"']"));
			log4j.info( strStdRt+" is listed on the page Statewide Resource Detail Report (Step 1 of 2).");

				if (selenium
						.isChecked("css=input[name='standardResourceTypeID'][value='"
								+ strStdResTypeVal + "']") == false) {
					selenium.click("css=input[name='standardResourceTypeID'][value='"
							+ strStdResTypeVal + "']");
				}

				selenium.click(propElementDetails.getProperty("Reports.StandardST.Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

		} catch (AssertionError Ae) {

			log4j.info("'Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed");
			strErrorMsg = strErrorMsg
					+ "; "
					+ "Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed";
		}
		} catch (AssertionError Ae) {
			strErrorMsg = strStdRt+" is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).";
			log4j.info( strStdRt+" is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).");

		}

	} catch (Exception e) {
		log4j.info("selectStdResTypeForReport function failed" + e);
		strErrorMsg = "selectStdResTypeForReport function failed" + e;
	}
	return strErrorMsg;
}

/***********************************************************************
'Description	:Select the standard resource types
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:02-Nov-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'02-Nov-2012                              <Name>
************************************************************************/


public String selectOnlyStdResTypeForReport(Selenium selenium,
		String strStdResTypeVal[],String strStdRt[]) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	rdExcel = new ReadData();
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

	try {

		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try {
			assertEquals("Statewide Resource Detail Report (Step 1 of 2)",
					selenium.getText(propElementDetails.getProperty("Header.Text")));
			log4j.info("'Statewide Resource Detail Report (Step 1 of 2) page is displayed");

			for(int intRt=0;intRt<strStdResTypeVal.length;intRt++){
				try {
					assertTrue(selenium.isElementPresent("css=input[name='standardResourceTypeID']" +
							"[value='"+strStdResTypeVal[intRt]+"']"));
					log4j.info( strStdRt[intRt]+" is listed on the page Statewide Resource Detail Report (Step 1 of 2).");
		
						if (selenium
								.isChecked("css=input[name='standardResourceTypeID'][value='"
										+ strStdResTypeVal[intRt] + "']") == false) {
							selenium.click("css=input[name='standardResourceTypeID'][value='"
									+ strStdResTypeVal[intRt] + "']");
						}
		
				} catch (AssertionError Ae) {
					strErrorMsg =strErrorMsg
					+ "; "+ strStdRt[intRt]+" is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).";
					log4j.info( strStdRt[intRt]+" is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).");
					
				}
			}
		} catch (AssertionError Ae) {
			log4j.info("'Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed");
			strErrorMsg =  "Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed";
			

		}

	} catch (Exception e) {
		log4j.info("selectOnlyStdResTypeForReport function failed" + e);
		strErrorMsg = "selectOnlyStdResTypeForReport function failed" + e;
	}
	return strErrorMsg;
}

/***********************************************************************
'Description	:Navigate select standard status type page by clicking on next
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:02-Nov-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'02-Nov-2012                              <Name>
************************************************************************/


public String navToSelStandSTPage(Selenium selenium) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	rdExcel = new ReadData();
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");



		selenium.selectWindow("");
		selenium.selectFrame("Data");
		try {
			    selenium.click(propElementDetails.getProperty("Reports.StandardST.Next"));
				selenium.waitForPageToLoad(gstrTimeOut);

	
				try {
					assertEquals("Statewide Resource Detail Report (Step 2 of 2)",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));

				} catch (AssertionError Ae) {
					strErrorMsg =  "Statewide Resource Detail Report (Step 2 of 2) is NOT displayed";
					log4j
							.info( "Statewide Resource Detail Report (Step 2 of 2) is NOT displayed");

				}
				
	

	} catch (Exception e) {
		log4j.info("navToSelStandSTPage function failed" + e);
		strErrorMsg = "navToSelStandSTPage function failed" + e;
	}
	return strErrorMsg;
}

/***********************************************************************
'Description	:Generate Statewide Resource detail report
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:02-Nov-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'02-Nov-2012                              <Name>
************************************************************************/


public String generateStatewideResDetRep(Selenium selenium) throws Exception {

	String strErrorMsg = "";// variable to store error mesage

	rdExcel = new ReadData();
	propEnvDetails = objReadEnvironment.readEnvironment();
	propElementDetails = objelementProp.ElementId_FilePath();

	gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		selenium.selectWindow("");
		selenium.selectFrame("Data");
	try {
		selenium
			.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

		
	} catch (Exception e) {
		log4j.info("generateStatewideResDetRep function failed" + e);
		strErrorMsg = "generateStatewideResDetRep function failed" + e;
	}
	return strErrorMsg;
}

/***********************************************************************
'Description	:Generate Resource Detail Report 
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:14-Sep-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'14-Sep-2012                               <Name>
************************************************************************/
	public String selectStdStatusesForReport(Selenium selenium,
			String strStdStatusVal, String strStdStatusName) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				assertEquals("Statewide Resource Detail Report (Step 2 of 2)",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j
						.info("'Statewide Resource Detail Report (Step 2 of 2) page is displayed");
				try {
					assertTrue(selenium
							.isElementPresent("css=input[name='standardStatusTypeID']"
									+ "[value='" + strStdStatusVal + "']"));
					log4j
							.info(strStdStatusName
									+ " is listed on the page Statewide Resource Detail Report (Step 1 of 2).");

					if (selenium
							.isChecked("css=input[name='standardStatusTypeID'][value='"
									+ strStdStatusVal + "']") == false) {
						selenium
								.click("css=input[name='standardStatusTypeID'][value='"
										+ strStdStatusVal + "']");
					}

					selenium
							.click(propElementDetails
									.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
					selenium.waitForPageToLoad(gstrTimeOut);

				} catch (AssertionError Ae) {

					log4j
							.info("'Statewide Resource Detail Report (Step 2 of 2) page is NOT displayed");
					strErrorMsg = strErrorMsg
							+ "; "
							+ "Statewide Resource Detail Report (Step 2 of 2) page is NOT displayed";
				}

			} catch (AssertionError Ae) {
				strErrorMsg = strStdStatusName
						+ " is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).";
				log4j
						.info(strStdStatusName
								+ " is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).");

			}

		} catch (Exception e) {
			log4j.info("selectStdResTypeForReport function failed" + e);
			strErrorMsg = "selectStdResTypeForReport function failed" + e;
		}
		return strErrorMsg;
	}
	
	/***********************************************************************
	'Description	:Select standard status types
	'Precondition	:None
	'Arguments		:selenium
	'Returns		:String
	'Date	 		:02-Nov-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'02-Nov-2012                               <Name>
	************************************************************************/
		public String selectOnlyStdStatusesForReport(Selenium selenium,
				String strStdStatusVal[], String strStdStatusName[]) throws Exception {

			String strErrorMsg = "";// variable to store error mesage

			rdExcel = new ReadData();
			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			try {

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				try {
					assertEquals("Statewide Resource Detail Report (Step 2 of 2)",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j
							.info("'Statewide Resource Detail Report (Step 1 of 2) page is displayed");
					for(int intSt=0;intSt<strStdStatusVal.length;intSt++){
						try {
							assertTrue(selenium
									.isElementPresent("css=input[name='standardStatusTypeID']"
											+ "[value='" + strStdStatusVal[intSt] + "']"));
							log4j
									.info(strStdStatusName[intSt]
											+ " is listed on the page Statewide Resource Detail Report (Step 1 of 2).");
	
							if (selenium
									.isChecked("css=input[name='standardStatusTypeID'][value='"
											+ strStdStatusVal[intSt] + "']") == false) {
								selenium
										.click("css=input[name='standardStatusTypeID'][value='"
												+ strStdStatusVal[intSt] + "']");
							}
	
							
						} catch (AssertionError Ae) {
							strErrorMsg = strStdStatusName[intSt]
							+ " is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).";
							log4j
							.info(strStdStatusName[intSt]
									+ " is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).");
							
						}
					}

				} catch (AssertionError Ae) {
					
					log4j
					.info("'Statewide Resource Detail Report (Step 2 of 2) page is NOT displayed");
					strErrorMsg = strErrorMsg
					+ "; "
					+ "Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed";
				}

			} catch (Exception e) {
				log4j.info("selectOnlyStdStatusesForReport function failed" + e);
				strErrorMsg = "selectOnlyStdStatusesForReport function failed" + e;
			}
			return strErrorMsg;
		}

/***********************************************************************
'Description	:Generate Resource Detail Report 
'Precondition	:None
'Arguments		:selenium
'Returns		:String
'Date	 		:1-Nov-2012
'Author			:QSG
'-----------------------------------------------------------------------
'Modified Date                            Modified By
'1-Nov-2012                              <Name>
************************************************************************/

	public String selectStdResTypeManyForReport(Selenium selenium,
			String[] strStdResTypeVal, String[] strStdRt, boolean blnNext)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		rdExcel = new ReadData();
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.selectWindow("");
			selenium.selectFrame("Data");
			try {
				assertEquals("Statewide Resource Detail Report (Step 1 of 2)",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j
						.info("'Statewide Resource Detail Report (Step 1 of 2) page is displayed");
				for (int i = 0; i < strStdResTypeVal.length; i++) {

					try {
						assertTrue(selenium
								.isElementPresent("css=input[name='standardResourceTypeID']"
										+ "[value='"
										+ strStdResTypeVal[i]
										+ "']"));
						log4j
								.info(strStdRt
										+ " is listed on the page Statewide Resource Detail Report (Step 1 of 2).");

						if (selenium
								.isChecked("css=input[name='standardResourceTypeID'][value='"
										+ strStdResTypeVal[i] + "']") == false) {
							selenium
									.click("css=input[name='standardResourceTypeID'][value='"
											+ strStdResTypeVal[i] + "']");
						}

					} catch (AssertionError Ae) {
						strErrorMsg = strStdRt[i]
								+ " is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).";
						log4j
								.info(strStdRt[i]
										+ " is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).");

					}

				}

				if (blnNext) {
					selenium.click(propElementDetails.getProperty("Reports.StandardST.Next"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}

			} catch (AssertionError Ae) {

				log4j
						.info("'Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed");
				strErrorMsg = strErrorMsg
						+ "; "
						+ "Statewide Resource Detail Report (Step 1 of 2) page is NOT displayed";
			}

		} catch (Exception e) {
			log4j.info("selectStdResTypeManyForReport function failed" + e);
			strErrorMsg = "selectStdResTypeManyForReport function failed" + e;
		}
		return strErrorMsg;
	}
	
	/*******************************************************************************************
	  ' Description: Enter event value to generate  snap shot report
	  ' Precondition: N/A 
	  ' Arguments: selenium,strETValue
	  ' Returns: String 
	  ' Date: 27-Nov-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	public String vrfyElementsInEvntSnapshotGenerateReport2Of2Pge(
			Selenium selenium, String strEventValue) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			try {
				assertEquals("Event Snapshot Report (Step 2 of 2)", selenium
						.getText("css=h1"));
				assertEquals("Select an Event", selenium
						.getText("css=td.emsCenteredLabel"));
				assertTrue(selenium
						.isElementPresent("css=input[name='eventID'][value='"
								+ strEventValue + "']"));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("EventSanapShotReport.Hour")));
				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("EventSanapShotReport.Min")));

				assertTrue(selenium.getText(
						"//div[@id='mainContainer']/form/table/tbody/tr[6]/td")
						.matches("Snapshot Time - Hour:\\**"));
				assertTrue(selenium.getText(
						"//div[@id='mainContainer']/form/table/tbody/tr[7]/td")
						.matches("Snapshot Time - Minutes:\\**"));

				log4j
						.info("'Event Snapshot Report (Step 2 of 2)' screen is displayed with :"
								+ "1.Select an Event "
								+ "2.Snapshot Time - Hour"
								+ "3.Snapshot Time - Minutes ");

			} catch (AssertionError Ae) {
				lStrReason = "'Event Snapshot Report (Step 2 of 2)' screen is NOT displayed with :"
						+ "1.Select an Event "
						+ "2.Snapshot Time - Hour"
						+ "3.Snapshot Time - Minutes ";

				log4j
						.info("'Event Snapshot Report (Step 2 of 2)' screen is NOT displayed with :"
								+ "1.Select an Event "
								+ "2.Snapshot Time - Hour"
								+ "3.Snapshot Time - Minutes ");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************
	 ' Description: Verify ST and RS in 'Status Detail Report (Step 1 of 2) page 
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 28-Nov-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String vrfySTandRSinStatusDetailReport2Of2Pge(Selenium selenium,
			String strSTName, String strRSName, boolean blnMulti,
			String[] strStatuses) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			try {
				assertEquals("Status Detail Report (Step 2 of 2)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j
						.info("'Status Detail Report (Step 2 of 2) page is displayed");

				if (blnMulti) {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/form/table/"
										+ "tbody/tr[1]/td[text()='Status Type:']/following-sibling"
										+ "::td[text()='" + strSTName + "']"));

						assertTrue(

						selenium
								.getText(
										"//div[@id='mainContainer']/form/table/tbody/tr[2]/td[1]")
								.equals("Statuses:**")
								|| selenium
										.getText(
												"//div[@id='mainContainer']/form/table/tbody/tr[2]/td[1]")
										.equals("Statuses:\\**"));
						
					

						assertEquals(strStatuses[0] + "\n" + strStatuses[1],
								selenium.getText("//div[@id='mainContainer']"
										+ "/form/table/tbody/tr[2]/td[2]"));

						assertTrue(

						selenium
								.getText(
										"//div[@id='mainContainer']/form/table/tbody/tr[3]/td[1]")
								.equals("Resources:**")
								|| selenium
										.getText(
												"//div[@id='mainContainer']/form/table/tbody/tr[3]/td[1]")
										.equals("Resources:\\**"));

						assertEquals(
								strRSName,
								selenium
										.getText("//div[@id='mainContainer']/form/table/tbody/tr[3]/td[2]"));

						log4j
								.info("'Status Detail Report (Step 2 of 2)' screen is displayed with:"
										+ "1. Status type "
										+ strSTName
										+ ""
										+ "2. Statuses "
										+ "3. Resources "
										+ strRSName + ". ");

					} catch (AssertionError Ae) {
						log4j
								.info("'Status Detail Report (Step 2 of 2)' screen is NOT displayed with:"
										+ "1. Status type "
										+ strSTName
										+ ""
										+ "2. Statuses "
										+ "3. Resources "
										+ strRSName + ". ");
						lStrReason = "'Status Detail Report (Step 2 of 2)' screen is NOT displayed with:"
								+ "1. Status type "
								+ strSTName
								+ ""
								+ "2. Statuses "
								+ "3. Resources "
								+ strRSName
								+ ". ";
					}
				} else {
					try {
						assertTrue(selenium
								.isElementPresent("//div[@id='mainContainer']/form/table/"
										+ "tbody/tr[1]/td[text()='Status Type:']/following-sibling"
										+ "::td[text()='" + strSTName + "']"));

						assertTrue(

								selenium
										.getText(
												"//div[@id='mainContainer']/form/table/tbody/tr[2]/td[1]")
										.equals("Resources:**")
										|| selenium
												.getText(
														"//div[@id='mainContainer']/form/table/tbody/tr[2]/td[1]")
												.equals("Resources:\\**"));

						assertEquals(
								strRSName,
								selenium
										.getText("//div[@id='mainContainer']/form/table/tbody/tr[2]/td[2]"));

						log4j
								.info("'Status Detail Report (Step 2 of 2)' screen is displayed with:"
										+ "1. Status type "
										+ strSTName
										+ "3. Resources " + strRSName + ". ");

					} catch (AssertionError Ae) {
						log4j
								.info("'Status Detail Report (Step 2 of 2)' screen is NOT displayed with:"
										+ "1. Status type "
										+ strSTName
										+ "3. Resources " + strRSName + ". ");
						lStrReason = "'Status Detail Report (Step 2 of 2)' screen is NOT displayed with:"
								+ "1. Status type "
								+ strSTName
								+ "3. Resources " + strRSName + ". ";
					}
				}

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("'Status Detail Report (Step 2 of 2) page is NOT displayed");
				lStrReason = lStrReason
						+ "; "
						+ "Status Detail Report (Step 2 of 2) page is NOT displayed";
			}
		} catch (Exception e) {
			log4j
					.info(e
							+ " vrfySTandRSinStatusDetailReport2Of2Pge function failed");
			lStrReason = " vrfySTandRSinStatusDetailReport2Of2Pge function failed"
					+ lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	/****************************************************************************************
	 ' Description: verify calender widgets in Status Detail Report page
	 ' Precondition: N/A 
	 ' Arguments: selenium
	 ' Returns: String 
	 ' Date: 28-Nov-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String verifyCalndrWidInStatusDetailReportNew(Selenium selenium,
			String strSTname[]) throws Exception {
		String lStrReason = "";

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
						.isElementPresent("//input[@id='StartDate']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='ui-datepicker-div']"));
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is displayed with:1. 'Start Date' field (with calender widget) ");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is NOT displayed with:1. 'Start Date' field (with calender widget)");
				lStrReason = lStrReason
						+ "; "
						+ "Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:1. 'Start Date' field (with calender widget)";
			}
			
			try {
				assertTrue(selenium
						.isElementPresent("//input[@id='EndDate']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='ui-datepicker-div']"));
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is displayed with:2. 'End Date' field (with calender widget)");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is NOT displayed with:2. 'End Date' field (with calender widget)");
				lStrReason = lStrReason
						+ "; "
						+ "Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:2. 'End Date' field (with calender widget)";
			}

			try {
				assertTrue(selenium
						.isChecked("css=input[name='reportFormat'][value='PDF']"));
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is displayed with:3. 'Report Format' (with PDF and CSV "
								+ "options, PDF is selected by default) ");

			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j
						.info("Status Detail Report (Step 1 of 2)' screen "
								+ "is NOT displayed with:3. 'Report Format' (with PDF and CSV"
								+ " options, PDF is selected by default) ");
				lStrReason = lStrReason + "; "
						+ "Status Detail Report (Step 1 of 2)' screen "
						+ "is NOT displayed with:3. 'Report Format' (with PDF "
						+ "and CSV options, PDF is selected by default) ";
			}
			for (String s : strSTname) {
				try {
					assertTrue(selenium
							.isElementPresent("//select[@name='statusTypeID']"
									+ "/option[text()='" + s + "']"));
					log4j.info("Status Detail Report (Step 1 of 2)' screen "
							+ "is displayed with:4. Status Types dropdown "
							+ "with status types " + s);

				} catch (AssertionError Ae) {
					log4j.info(Ae);
					log4j.info("Status Detail Report (Step 1 of 2)' screen "
							+ "is NOT displayed with:4. Status Types dropdown "
							+ "with status types " + s);
					lStrReason = lStrReason + "; "
							+ "Status Detail Report (Step 1 of 2)' screen "
							+ "is NOT displayed with:4. Status Types dropdown "
							+ "with status types " + s;
				}
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************
	 ' Description: Enter Data for all the fields in summary details page and click on Generate Report
	 ' Precondition: N/A 
	 ' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	 ' Returns: String 
	 ' Date: 28-Nov-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String enterReportStatusDetailAndNavigate(Selenium selenium,
			String strStartDate, String strEndDate, boolean blnPDF,
			String strStatName) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			if (blnPDF)
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF"));
			else
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.CSV"));

			selenium.select("css=select[name='statusTypeID']", "label="
					+ strStatName + "");

			selenium.click(propElementDetails.getProperty("EventReportSnapShot.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************
	 ' Description: Enter Data for all the fields in summary details page and click on Generate Report
	 ' Precondition: N/A 
	 ' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	 ' Returns: String 
	 ' Date: 28-Nov-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String enterReportStatusDetailGenerateReport(Selenium selenium,
			String[] strStatusValue, boolean blnMuti, String strResourceVal)
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
			if (blnMuti) {
				selenium.click("css=input[name='resourceID'][value='"
						+ strResourceVal + "']");
				
				for (String s : strStatusValue) {
					selenium.click("css=input[name='statusID'][value='" + s
							+ "']");
					
				}

				selenium
						.click(propElementDetails
								.getProperty("StatusSummrReport.RepFormat.GenerateRep"));

				
			} else {
				
				selenium.click("css=input[name='resourceID'][value='"
						+ strResourceVal + "']");

				selenium
						.click(propElementDetails
								.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
				
			}

			selenium.waitForPageToLoad(gstrTimeOut);
			
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	/*******************************************************************************************
	  ' Description: Enter event value to generate  snap shot report
	  ' Precondition: N/A 
	  ' Arguments: selenium,strETValue
	  ' Returns: String 
	  ' Date: 30-Nov-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	public String vrfyElementsInEvntDetalGenerateReport2Of2Pge(
			Selenium selenium, String strEventValue, String strRSName)
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

			try {
				assertEquals("Event Detail Report (Step 2 of 2)", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				assertEquals(
						"Search Dates Entered",
						selenium
								.getTable("css=form[name=\"form1\"] > table > tbody > tr > td > table.0.0"));
				assertEquals(
						"Report Dates",
						selenium
								.getText("//div[@id='mainContainer']/form/table/tbody/tr/td[2]/table/tbody/tr/td"));
				assertEquals(
						"Select Events",
						selenium
								.getText("//div[@id='mainContainer']/form/table/tbody/tr[3]/td"));

				assertTrue(selenium
						.isElementPresent("css=input[name='eventID'][value='"
								+ strEventValue + "']"));
				assertEquals(
						selenium
								.getText("//div[@id='mainContainer']/form/table/tbody/tr[5]/td[1]"),
						"Resources:");

				assertEquals(
						selenium
								.getText("//div[@id='mainContainer']/form/table/tbody/tr[5]/td[2]"),
						strRSName);

				log4j
						.info("'Event Detail Report (Step 2 of 2)' screen is displayed with:"
								+ "1. Search Dates Entered 2. Report Dates"
								+ "3. Select Events(check box)"
								+ "4. Resources (check box)  ");

			} catch (AssertionError Ae) {
				lStrReason = "'Event Detail Report (Step 2 of 2)' screen is NOT displayed with:"
						+ "1. Search Dates Entered 2. Report Dates"
						+ "3. Select Events(check box)"
						+ "4. Resources (check box)  ";

				log4j
						.info("'Event Detail Report (Step 2 of 2)' screen is NOT displayed with:"
								+ "1. Search Dates Entered 2. Report Dates"
								+ "3. Select Events(check box)"
								+ "4. Resources (check box)  ");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

	/*******************************************************************************************
	  ' Description: Enter event value to generate  snap shot report
	  ' Precondition: N/A 
	  ' Arguments: selenium,strStartDate,strEndDate,strETValue,strHr,strMin
	  ' Returns: String 
	  ' Date: 30-Nov-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	public String enterEvntDetalRepStartDateAndEndDateAndNavigate(
			Selenium selenium, String strETValue, String strStartDate,
			String strEndDate) throws Exception {
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
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);
			selenium.click("css=input[name='eventID'][value='" + strETValue
					+ "']");

			selenium.click(propElementDetails.getProperty("EventReportSnapShot.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			
			try {
				assertEquals("Event Detail Report (Step 2 of 2)", selenium
						.getText("css=h1"));
				
				log4j
				.info("'Event Detail Report (Step 2 of 2)' screen i displayed ");
			} catch (AssertionError Ae) {
				lStrReason = "'Event Detail Report (Step 2 of 2)' screen is NOT displayed ";

				log4j
						.info("'Event Detail Report (Step 2 of 2)' screen is NOT displayed ");
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

		/*******************************************************************************************
		  ' Description: Enter event value to generate  snap shot report
		  ' Precondition: N/A 
		  ' Arguments: selenium,strStartDate,strEndDate,strETValue,strHr,strMin
		  ' Returns: String 
		  ' Date: 30-Nov-2012
		  ' Author: QSG 
		  '--------------------------------------------------------------------------------- 
		  ' Modified Date: 
		  ' Modified By: 
		  *******************************************************************************************/

	public String enterEvntDetalRepGenerate(Selenium selenium,
			String strEventValue, String strResValue) throws Exception {
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

			selenium.click("css=input[name='eventID'][value='" + strEventValue
					+ "']");

			// Click on resource

			selenium.click("css=[name='resourceID'][value='" + strResValue
					+ "']");

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	 /*******************************************************************************************
	  ' Description:Without entering data in mandatory fields verify error message.
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	 public String navEventSnapShotRepVarErrMsg(Selenium selenium)
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
			selenium.click(propElementDetails
					.getProperty("EventReportSnapShot.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("The following errors occurred on this page:",
						selenium.getText("css=span.emsErrorTitle"));
				log4j.info("The following error messages are displayed:  ");
			} catch (AssertionError Ae) {
				lStrReason = "The following error messages are NOT displayed: ";

				log4j.info("The following error messages are NOT displayed:");
			}
			try{
			assertTrue(selenium
					.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='Start date is required.']"));
			log4j.info("Start date is required is displayed.");
			} catch (AssertionError Ae) {
				lStrReason = "Start date is required is NOT displayed.";

				log4j.info("Start date is required is NOT displayed.");
			}
			try{
			assertTrue(selenium
					.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='End date is required.']"));
			log4j.info("End date is required is displayed.");
			} catch (AssertionError Ae) {
				lStrReason = "End date is required is NOT displayed.";

				log4j.info("End date is required is NOT displayed.");
			} 
			try{
			assertTrue(selenium
					.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='" +
							"Event template is required. Please select one or more.']"));
			log4j.info("Event template is required. Please select one or more is displayed.");
			log4j.info("User is retained on the same page.");
			} catch (AssertionError Ae) {
				lStrReason = "Event template is required. Please select one or more is NOT displayed.";

				log4j.info("Event template is required. Please select one or more is NOT displayed.");
			}
	  } catch (Exception e) {
	   log4j.info(e);
	   lStrReason = lStrReason + ";" + e.toString();
	  }

	  return lStrReason;
	 }
 /*******************************************************************************************
  ' Description: Enter event value to generate  snap shot report
  ' Precondition: N/A 
  ' Arguments: selenium,strStartDate,strEndDate,strETValue,strHr,strMin
  ' Returns: String 
  ' Date: 17-07-2012
  ' Author: QSG 
  '--------------------------------------------------------------------------------- 
  ' Modified Date: 
  ' Modified By: 
  *******************************************************************************************/

	public String enterEvntSnapshotGenRepVarErrMsg(Selenium selenium) throws Exception {
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
			
			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("The following errors occurred on this page:",
						selenium.getText("css=span.emsErrorTitle"));
				log4j.info("The following error messages are displayed:");
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='Event is required. Please select an event.']"));
					log4j.info("Event is required. Please select an event. is displayed.");
				} catch (AssertionError Ae) {
					lStrReason = "Event is required. Please select an event is NOT displayed.";

					log4j.info("Event is required. Please select an event is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='Report date is required.']"));
					log4j.info("Report date is required is displayed.");
					log4j.info("Report is not generated.");

				} catch (AssertionError Ae) {
					lStrReason = "Report date is required is NOT displayed.";

					log4j.info("Report date is required NOT displayed.");
				}
			} catch (AssertionError Ae) {
				lStrReason = "The following error messages are NOT displayed: ";

				log4j.info("The following error messages are NOT displayed:");
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	/*******************************************************************************************
	  ' Description: Enter event value to generate  snap shot report
	  ' Precondition: N/A 
	  ' Arguments: selenium,strStartDate,strEndDate,strETValue,strHr,strMin
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/

	public String enterGenRepVarErrMsgInSumRep(Selenium selenium)
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
			
			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("The following errors occurred on this page:",
						selenium.getText(propElementDetails.getProperty("UpdateStatus.ErrMsgTitle")));
				log4j.info("The following error messages are displayed:");
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='Start date is required.']"));
					log4j.info("Start date is required is displayed.");
				} catch (AssertionError Ae) {
					lStrReason = "Start date is required is NOT displayed.";
					log4j.info("Start date is required is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='End date is required.']"));
					log4j.info("Start date is required is displayed.");
				} catch (AssertionError Ae) {
					lStrReason = "Start date is required is NOT displayed.";
					log4j.info("Start date is required is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='At least one resource must be selected.']"));
					log4j.info("At least one resource must be selected is NOT displayed.");
				} catch (AssertionError Ae) {
					lStrReason = "At least one resource must be selected is NOT displayed.";
					log4j.info("At least one resource must be selected is NOT displayed.");
				}
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/div/ul/li[text()='Status type is required.']"));
					log4j.info("Status type is required are displayed. ");
					log4j.info("Report is not generated.");
				} catch (AssertionError Ae) {
					lStrReason = "Status type is required are NOT displayed.";
					log4j.info("Status type is required are NOT displayed. ");
				}
			} catch (AssertionError Ae) {
				lStrReason = "The following error messages are NOT displayed: ";

				log4j.info("The following error messages are NOT displayed:");
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	 /*******************************************************************************************
	  ' Description: Enter event start date and end date in event snap shot report
	  ' Precondition: N/A 
	  ' Arguments: selenium,strStartDate,strEndDate,strETValue
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	 public String selectHtmlOrExcelformatInEventSnapShotReport(Selenium selenium,
	   String strStartDate, String strEndDate, String strETValue,boolean blnExcel)
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
	   selenium.type(propElementDetails
	     .getProperty("EventSanapShotReport.StartDate"), strStartDate);
	   selenium.type(propElementDetails
	     .getProperty("EventSanapShotReport.EndDate"), strEndDate);
	   
	   if(blnExcel){
		   if(selenium.isChecked("css=input[name='reportFormat'][value='CSV']")==false){
			   selenium.click("css=input[name='reportFormat'][value='CSV']");
			   
		   }
	   }else{
		   if(selenium.isChecked("css=input[name='reportFormat'][value='HTML']")==false){
			   selenium.click("css=input[name='reportFormat'][value='HTML']");
			   
		   }
	   }

	   selenium.click("css=input[name='eventTypeID'][value='" + strETValue
	     + "']");
	   selenium.click(propElementDetails
	     .getProperty("EventReportSnapShot.Next"));
	   selenium.waitForPageToLoad(gstrTimeOut);
	  } catch (Exception e) {
	   log4j.info(e);
	   lStrReason = lStrReason + ";" + e.toString();
	  }

	  return lStrReason;
	 }
	 
	 
	 
	 /*******************************************************************************************
	  ' Description: selectHTMLReport
	  ' Precondition: N/A 
	  ' Arguments:  selenium,  strEventName, Description,  strReportGeneratedTime
	  ' Returns: String 
	  ' Date: 6-May-2013
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	public String selectHTMLReport(Selenium selenium, String strEventName,
			String Description, String strReportGeneratedTime) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
		
			Thread.sleep(10000);
			String strGetWindowTitles[] = selenium.getAllWindowTitles();

			boolean blnFound=false;
			int intCnt=0;
			while(blnFound==false&&intCnt<60){
				try{
					selenium.selectPopUp(strGetWindowTitles[1]);
					Thread.sleep(5000);
					for (int second = 0;; second++){
						 if (second >= 60) fail("timeout");
							try { 
								selenium
								.isElementPresent("//h1[1][text()='Event Snapshot Report']");
								break; 
							}catch(Exception e) {}
								Thread.sleep(1000);
						}
					blnFound=true;
				}catch(Exception e){
					intCnt++;
					Thread.sleep(1000);
					if(intCnt%10==0){
						selenium.typeKeys("//", "116");
						Thread.sleep(2000);
		
					}
				}
			}
			
			if(blnFound){
				try {
					selenium.selectPopUp(strGetWindowTitles[1]);
					log4j.info("HTML Report is displayed");
				} catch (Exception e) {
					log4j.info("HTML Report is NOT displayed");
				}
				
				try {
					assertTrue(selenium
							.isElementPresent("//h1[1][text()='Event Snapshot Report']"));
					log4j.info("'Event Snapshot Report' heading is displayed in HTML report");
					assertTrue(selenium.isElementPresent("//h1[2][text()='"
							+ strEventName + "']"));
					log4j.info("Event Name : "+strEventName+" is displayed in HTML report");
					assertTrue(selenium.isElementPresent("//h2[text()='"
							+ strReportGeneratedTime + "']"));
					log4j.info(""+strReportGeneratedTime+" is displayed in HTML report");
					assertTrue(selenium
							.isElementPresent("//div[@class='desc'][text()='"
									+ Description + "']"));
					log4j.info(""+Description+" is displayed in HTML report");
				} catch (AssertionError Ae) {
					lStrReason = "Headers are NOT displayed";
					log4j.info("Headers are NOT displayed");
				}
			}
			
		

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	public String verifyHeadersInHTMLEventSnapshotReport(Selenium selenium,
			String strHeaderName[]) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			for (int i = 0; i < strHeaderName.length; i++) {
				try {
					assertTrue(selenium
							.isElementPresent("//table/tbody/tr[1]/th[1+" + i
									+ "][text()='" + strHeaderName[i] + "']"));

					log4j.info("Header " + strHeaderName[i]
							+ " is Disaplyed in HTML report");
				} catch (AssertionError Ae) {
					lStrReason = "Header " + strHeaderName[i]
							+ " is NOT Disaplyed in HTML report";
					log4j.info("Header " + strHeaderName[i]
							+ " is NOT Disaplyed in HTML report");
				}

			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	public String verifDataInHTMLEventSnapshotReport(Selenium selenium,
			String strData[]) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			for (String str : strData) {
				try {
					assertTrue(selenium
							.isElementPresent("//table/tbody/tr[2]/td[text()='"
									+ str + "']"));

					log4j.info("Data " + str + " is Disaplyed in HTML report");
				} catch (AssertionError Ae) {
					lStrReason = "Data  " + str
							+ " is Disaplyed in HTML report";
					log4j.info("Data " + str + " is Disaplyed in HTML report");
				}

			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	
	public String verifSummaryDataInHTMLEventSnapshotReport(Selenium selenium,
			String strData[]) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			for (String str : strData) {
				try {
					assertTrue(selenium
							.isElementPresent("//table/tbody/tr[3]/td[text()='"
									+ str + "']"));

					log4j.info("Data " + str + " is Disaplyed in HTML report");
				} catch (AssertionError Ae) {
					lStrReason = "Data  " + str
							+ " is NOT Disaplyed in HTML report";
					log4j.info("Data " + str + " is NOT Disaplyed in HTML report");
				}

			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}

	
	public String verifStatusSummaryHeaderDataInHTMLEventSnapshotReport(
			Selenium selenium, String strData[][], String strHeader[])
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

			for (String str : strHeader) {
				try {
					assertTrue(selenium
							.isElementPresent("//table[2]/tbody/tr[1]/th[text()='"
									+ str + "']"));

					log4j.info("Data " + str + " is Disaplyed in HTML report");
				} catch (AssertionError Ae) {
					lStrReason = "Data  " + str + " is Disaplyed in HTML report";
					log4j.info("Data " + str + " is Disaplyed in HTML report");
				}

			}

			for (int i = 0; i < strData.length; i++) {
				try {
					assertTrue(selenium
							.isElementPresent("//table[2]/tbody/tr[2+" + i
									+ "]/td[1][text()='"
									+ strData[i][0] + "']"));
					assertTrue(selenium
							.isElementPresent("//table[2]/tbody/tr[2+" + i
									+ "]/td[2][text()='"
									+ strData[i][1] + "']"));

					log4j.info("Data " + strData[i][0]
							+ " is Disaplyed in HTML report");
					log4j.info("Data " + strData[i][1]
							+ " is Disaplyed in HTML report");
				} catch (AssertionError Ae) {
					lStrReason = "Data " + strData[i][0]
							+ " is NOT Disaplyed in HTML report";
					log4j.info("Data " + strData[i][0]
							+ " is NOT Disaplyed in HTML report");
					log4j.info("Data " + strData[i][1]
							+ " is NOT Disaplyed in HTML report");
				}

			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	public String verifFoooterInHTMLEventSnapshotReport(Selenium selenium,
			String strReportGeneratedDate, String strGeneratedtime)
			throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		Date_Time_settings dts = new Date_Time_settings();

		try {
			String strGeneratedtime1 = dts.addTimetoExisting(strGeneratedtime,
					1, "HH:mm");
			String strGeneratedtime2 = dts.addTimetoExisting(strGeneratedtime,
					1, "HH:m");
			String strGeneratedtime3 = dts.addTimetoExisting(strGeneratedtime,
					0, "HH:m");

			String strGeneratedtime4 = dts.addTimetoExisting(strGeneratedtime,
					2, "HH:mm");
			String strGeneratedtime5 = dts.addTimetoExisting(strGeneratedtime,
					2, "HH:m");

			try {
				assertTrue(selenium
						.isElementPresent("//div[@class='footer']/div[@class='left'][text()='EMResource']"));

				log4j
						.info("EMResource is Disaplyed in in Footer in HTML report");
			} catch (AssertionError Ae) {
				lStrReason = "EMResource is NOT Disaplyed in in Footer in HTML report";
				log4j
						.info("EMResource is NOT Disaplyed in in Footer in HTML report");
			}

			try {
				assertTrue(selenium
						.isElementPresent("//div[@class='footer']/div[@class='right']"
								+ "[text()='Report Created: "
								+ strReportGeneratedDate
								+ " "
								+ strGeneratedtime + " CST']")
						|| selenium
								.isElementPresent("//div[@class='footer']/div[@class='right']"
										+ "[text()='Report Created: "
										+ strReportGeneratedDate
										+ " "
										+ strGeneratedtime1 + " CST']")
						|| selenium
								.isElementPresent("//div[@class='footer']/div[@class='right']"
										+ "[text()='Report Created: "
										+ strReportGeneratedDate
										+ " "
										+ strGeneratedtime2 + " CST']")
						|| selenium
								.isElementPresent("//div[@class='footer']/div[@class='right']"
										+ "[text()='Report Created: "
										+ strReportGeneratedDate
										+ " "
										+ strGeneratedtime3 + " CST']")
						|| selenium
								.isElementPresent("//div[@class='footer']/div[@class='right']"
										+ "[text()='Report Created: "
										+ strReportGeneratedDate
										+ " "
										+ strGeneratedtime4 + " CST']")
						|| selenium
								.isElementPresent("//div[@class='footer']/div[@class='right']"
										+ "[text()='Report Created: "
										+ strReportGeneratedDate
										+ " "
										+ strGeneratedtime5 + " CST']"));

				log4j
						.info("Report created date is Disaplyed in in Footer in HTML report");
			} catch (AssertionError Ae) {
				lStrReason = "Report created date is NOT Disaplyed in in Footer in HTML report";
				log4j
						.info("Report created date is NOT Disaplyed in in Footer in HTML report");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	
	
	 
	 /*******************************************************************************************
	  ' Description: enterEvntSnapshotGenerateReportAndVrfyValidationMsg
	  ' Precondition: N/A 
	  ' Arguments: selenium,strStartDate,strEndDate,strETValue,strHr,strMin
	  ' Returns: String 
	  ' Date: 17-07-2012
	  ' Author: QSG 
	  '--------------------------------------------------------------------------------- 
	  ' Modified Date: 
	  ' Modified By: 
	  *******************************************************************************************/
	 
	public String enterEvntSnapshotGenerateReportAndVrfyValidationMsg(
			Selenium selenium, String strSnapshotDate, String strEventValue,
			String strHr, String strMin) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {

			selenium.click("css=input[name='eventID'][value='" + strEventValue
					+ "']");
			selenium.type(propElementDetails
					.getProperty("EventSanapShotReport.EvntSnapShotDate"),
					strSnapshotDate);

			selenium
					.select(propElementDetails
							.getProperty("EventSanapShotReport.Hour"), "label="
							+ strHr);
			selenium
					.select(propElementDetails
							.getProperty("EventSanapShotReport.Min"), "label="
							+ strMin);

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertTrue(selenium
						.isElementPresent("//div[@class='emsError']"
								+ "/span[text()='The following error occurred on this page:']"));
				
				assertTrue(selenium
						.isElementPresent("//div[@class='emsError']/ul/" +
								"li[text()='Snapshot date/time may not be in the future.']"));
				
				log4j.info("The following error occurred on this page:Snapshot " +
						"date/time may not be in the future.is displayed ");
			} catch (Exception e) {
				lStrReason = "The following error occurred on this page:Snapshot " +
						"date/time may not be in the future.is NOT displayed ";
				
				log4j.info("The following error occurred on this page:Snapshot " +
				"date/time may not be in the future.is NOT displayed ");
			}

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}

  /*******************************************************************************************
  ' Description  : Enter event value to generate  snap shot report
  ' Precondition : N/A 
  ' Arguments    : selenium,strEventValue,strResValue
  ' Returns      : String 
  ' Date         : 30-Nov-2012
  ' Author       : QSG 
  '--------------------------------------------------------------------------------- 
  ' Modified Date: 
  ' Modified By: 
  *******************************************************************************************/

	public String enterEvntDetalRepGenerateNew(Selenium selenium,
			String strEventValue, String[] strResValue) throws Exception {
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

			selenium.click("css=input[name='eventID'][value='" + strEventValue
					+ "']");

			// Click on resource
			for (int i = 0; i < strResValue.length; i++) {
				selenium.click("css=[name='resourceID'][value='"
						+ strResValue[i] + "']");
			}
			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);

		} catch (Exception e) {
			log4j.info(e);
			lStrReason = lStrReason + "; " + e.toString();
		}

		return lStrReason;
	}
	

	//start//veifyErrorMsgInStatewideResDetailStepOne//
	/*******************************************************************************************
	' Description		: Verify error message in Statewide resource detail report step(1 of 2)
	' Precondition		: N/A 
	' Arguments			: selenium, strMsg
	' Returns			: String 
	' Date				: 07/10/2013
	' Author			: QSG 
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String veifyErrorMsgInStatewideResDetailStepOne(Selenium selenium,
			String strMsg) throws Exception {
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


			selenium.click(propElementDetails
					.getProperty("Reports.StandardST.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Statewide Resource Detail Report (Step 1 of 2)",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Statewide Resource Detail Report (Step 1 of 2) page is displayed");
			} catch (AssertionError Ae) {
				strReason = " is NOT listed on the page Statewide Resource Detail Report (Step 1 of 2).";
				log4j.info("Statewide Resource Detail Report (Step 1 of 2). page is NOT displayed");
			}
			try {
				assertEquals(strMsg, selenium.getText(propElementDetails
						.getProperty("UpdateStatus.ErrMsg")));
				log4j.info("'At least one standard resource must be selected.' message is displayed ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'At least one standard resource must be selected.' message is NOT displayed ");
				strReason = strReason
						+ "; "
						+ "'At least one standard resource must be selected.' message is NOT displayed ";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Reports.veifyErrorMsgInStatewideResDetailStepOne failed to complete due to "
					+ strReason + "; " + e.toString();
		}

		return strReason;
	}
	//end//veifyErrorMsgInStatewideResDetailStepOne//

	//start//verifyErrorMsgInStatewideResDetailReportStepTwoPage//
	/*******************************************************************************************
	' Description		: verify error message in statewide resource detail report step(2 of 2) page
	' Precondition		: N/A 
	' Arguments			: selenium, strMsg
	' Returns			: String 
	' Date				: 07/10/2013
	' Author			: Suhas
	'--------------------------------------------------------------------------------- 
	' Modified Date: 
	' Modified By: 
	*******************************************************************************************/

	public String verifyErrorMsgInStatewideResDetailReportStepTwoPage(
			Selenium selenium, String strMsg) throws Exception {
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

			selenium.click(propElementDetails
					.getProperty("StatusSummrReport.RepFormat.GenerateRep"));
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("Statewide Resource Detail Report (Step 2 of 2)",
						selenium.getText(propElementDetails
								.getProperty("Header.Text")));
				log4j.info("'Statewide Resource Detail Report (Step 2 of 2) page is displayed");
			} catch (AssertionError Ae) {
				log4j.info("'Statewide Resource Detail Report (Step 2 of 2) page is NOT displayed");
				strReason = strReason
						+ "; "
						+ "Statewide Resource Detail Report (Step 2 of 2) page is NOT displayed";
			}
			try {
				assertEquals(strMsg, selenium.getText(propElementDetails
						.getProperty("UpdateStatus.ErrMsg")));
				log4j.info("'At least one standard status must be selected.' message is displayed ");
			} catch (AssertionError Ae) {
				log4j.info(Ae);
				log4j.info("'At least one standard resource must be selected.' message is NOT displayed ");
				strReason = "'At least one standard status must be selected.' message is NOT displayed ";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = "Reports.verifyErrorMsgInStatewideResDetailReportStepTwoPage failed to complete due to "
					+ "; " + e.toString();
		}

		return strReason;
	}
	// end//verifyErrorMsgInStatewideResDetailReportStepTwoPage//
	// start//checkReportsHeaderPresentOrNot//
	/*******************************************************************************************
	 * ' Description : verify reports menu header present or not ' Precondition
	 * : N/A ' Arguments : selenium, blnPresent ' Returns : String ' Date :
	 * 07/10/2013 ' Author : QSG
	 * '--------------------------------------------------------------------------------
	 * - ' Modified Date: ' Modified By:
	 *******************************************************************************************/

	public String checkReportsHeaderPresentOrNot(Selenium selenium,
			boolean blnPresent) throws Exception {
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

			if (blnPresent) {
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("Prop676")));
					log4j.info("'Reports' menu header is  displayed. ");
				} catch (AssertionError Ae) {
					log4j.info("'Reports' menu header is NOT displayed. ");
					strReason = "'Reports' menu header is NOT displayed. ";
				}
			} else {
				try {
					assertFalse(selenium.isElementPresent(propElementDetails
							.getProperty("Prop676")));
					log4j.info("'Reports' menu header is NOT displayed. ");
				} catch (AssertionError Ae) {
					log4j.info("'Reports' menu header is displayed. ");
					strReason = "'Reports' menu header is  displayed. ";
				}

			}

		} catch (Exception e) {
			log4j.info(e);
			strReason = "Reports.checkReportsHeaderPresentOrNot failed to complete due to "
					+ strReason + "; " + e.toString();
		}

		return strReason;
	}
	// end//checkReportsHeaderPresentOrNot//
	 /*******************************************************************
	   'Description :check status types in edit res level page
	   'Precondition :None
	   'Arguments  :selenium,strStatTypeVal,blnSelStatType
	   'Returns  :String
	   'Date    :28-May-2012
	   'Author   :QSG
	   '------------------------------------------------------------------
	   'Modified Date                            Modified By
	   '<Date>                                <Name>
	   ********************************************************************/

	public String chkSTPresentOrNotForReport(Selenium selenium,boolean blnST,
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
			assertTrue(selenium.isElementPresent("css=select[name='statusTypeID'][value='"+strSTVal+"']"));
			log4j.info(statTypeName+ "Status type  is listed in the 'Status Type' drop-down list. ");
		} catch (AssertionError Ae) {
			strErrorMsg = statTypeName+ "Status type  is NOT listed in the 'Status Type' drop-down list. ";
			log4j.info(statTypeName+ "Status type  is NOT listed in the 'Status Type' drop-down list. ");
		}
	  }else{
		try {

			assertFalse(selenium.isElementPresent("css=select[name='statusTypeID'][value='"+strSTVal+"']"));
			log4j.info(statTypeName+ "Status type  is NOT listed in the 'Status Type' drop-down list. ");
		} catch (AssertionError Ae) {
			strErrorMsg = statTypeName+ "Status type is listed in the 'Status Type' drop-down list. ";
			log4j.info(statTypeName+ "Status type  is listed in the 'Status Type' drop-down list. ");
		}
	  }
	 } catch (Exception e) {
	  log4j.info("chkSTPresentOrNotForView function failed" + e);
	  strErrorMsg = "chkSTPresentOrNotForView function failed" + e;
	 }
	 return strErrorMsg;
	}	
	/*******************************************
	 ' Description: Enter Data for all the fields in summary details page and click on Generate Report
	 along with gernerated time
	 ' Precondition: N/A 
	 ' Arguments: selenium,strResource,strStartDate,strEndDate,blnPDF
	 ' Returns: String 
	 ' Date: 25-06-2012
	 ' Author: QSG 
	 '--------------------------------------------------------------------------------- 
	 ' Modified Date: 
	 ' Modified By: 
	 *******************************************************************************************/

	public String enterReportStatDetailAndVerErrorMsg(Selenium selenium,
			String strResourceVal, String strStatVal, String strStartDate,
			String strEndDate, boolean blnPDF, String strStatName) throws Exception {

		String strReason="";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.StartDate"), strStartDate);
			selenium.type(propElementDetails
					.getProperty("StatusSummrReport.EndDate"), strEndDate);

			if (blnPDF)
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.PDF"));
			else
				selenium.click(propElementDetails
						.getProperty("StatusSummrReport.RepFormat.CSV"));

			selenium.select("css=select[name='statusTypeID']", "label="
					+ strStatName + "");

			selenium.click(propElementDetails.getProperty("SelectReport.Next"));
			selenium.waitForPageToLoad(gstrTimeOut);

			try {
				assertEquals("The following error occurred on this page:",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsgTitle")));
				assertEquals(
						"The selected status type has no resources that you are authorized to view."
								+ " Please select another status type and try again.",
						selenium.getText(propElementDetails
								.getProperty("UpdateStatus.ErrMsg")));
				log4j.info("Error message 'The following error occurred on this page:"
						+ "The selected status type has no resources that you are authorized to view."
						+ "Please select another status type and try again.' is displayed ");
				
			} catch (AssertionError Ae) {
				strReason = "Error message 'The following error occurred on this page:"
						+ "The selected status type has no resources that you are authorized to view."
						+ "Please select another status type and try again.'is NOT displayed";

				log4j.info("Error message 'The following error occurred on this page:"
						+ "The selected status type has no resources that you are authorized to view."
						+ "Please select another status type and try again.'is NOT displayed");
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = strReason + "; " + e.toString();
		}
		return strReason;
	}
}
