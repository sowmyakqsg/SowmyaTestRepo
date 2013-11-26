package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**********************************************************************
' Description		:This class contains test cases from   requirement
' Requirement		:Activate System notice
' Requirement Group	:Setting up Regions 
� Product		    :EMResource v3.19
' Date			    :2/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class NewRegion {
	
		
		Date dtStartDate;
		ReadData rdExcel;
		static Logger log4j = Logger
				.getLogger("com.qsgsoft.EMResource.features.NewRegion");
		static {
			BasicConfigurator.configure();
		}
		String gstrTCID, gstrTO, gstrResult, gstrReason;
		public static String gstrTimetake, gstrdate, gstrtime, gstrBuild,
				gstrBrowserName, strSessionId;
		double gdbTimeTaken;
		public static long gslsysDateTime;
		OfficeCommonFunctions objOFC;
		public Properties propEnvDetails;
		Properties propElementDetails;
		Properties propElementAutoItDetails;
		Properties pathProps;
		Selenium selenium;
		String gstrTimeOut;




		@Before
		public void setUp() throws Exception {

			dtStartDate = new Date();
			gstrBrowserName = "IE 8";
			gstrBuild = "";

			ReadEnvironment objReadEnvironment = new ReadEnvironment();
			propEnvDetails = objReadEnvironment.readEnvironment();

			ElementId_properties objelementProp = new ElementId_properties();
			propElementDetails = objelementProp.ElementId_FilePath();

			Paths_Properties objPaths_Properties = new Paths_Properties();
			propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();

			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, propEnvDetails.getProperty("Browser"), propEnvDetails
							.getProperty("urlEU"));
			

			selenium.start();
			selenium.windowMaximize();

			objOFC = new OfficeCommonFunctions();
			rdExcel = new ReadData();

		}


		@After
		public void tearDown() throws Exception {
			
			
			selenium.stop();

			if (gstrResult.toUpperCase().equals("PASS")) {
				log4j.info("-------------------Test Case Execution " + gstrTCID
						+ " has PASSED------------------");
			} else if (gstrResult.toUpperCase().equals("SKIP")) {
				log4j.info("-------------------Test Case Execution " + gstrTCID
						+ " was SKIPPED------------------");
			} else {
				log4j.info("-------------------Test Case Execution " + gstrTCID
						+ " has FAILED------------------");
			}

			gdbTimeTaken = objOFC.TimeTaken(dtStartDate);// and execution time
			String FILE_PATH = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			FILE_PATH = pathProps.getProperty("Resultpath");
			Date_Time_settings dts = new Date_Time_settings();
			gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");

			// gstrBuild=PropEnvDetails.getProperty("Build");
			String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
			boolean blnwriteres = blnresult.equals("true");
			gstrReason=gstrReason.replaceAll("'", " ");
			objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
					gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
					gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
		}
		
	
	@Test
	public void testBQSNewRegion() throws Exception {
		try {

			gstrTCID = "NewRegion"; // Test Case Id
			gstrTO = " Verify that system notice can be activated.";// Test
																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strEditRegnName = rdExcel.readData("Regions", 7, 2);
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
		
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		

			String strFilePath = pathProps.getProperty("UserDetails_path");
			objOFC.WriteTestDatatoSpecifiedCell(strEditRegnName, strFilePath,
					"Login", 3, 4);

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "NewRegion";
			gstrTO = "Verify that system notice can be activated.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

}

