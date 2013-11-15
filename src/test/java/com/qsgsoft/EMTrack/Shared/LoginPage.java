package com.qsgsoft.EMTrack.Shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.qsgsoft.EMTrack.Support.ElementId_properties;
import com.qsgsoft.EMTrack.Support.ReadData;
import com.qsgsoft.EMTrack.Support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;

public class LoginPage{
	
	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	static Logger log4j = Logger.getLogger(LoginPage.class);

	ReadData rdExcel;


    //start//Login//
	/*******************************************************************************************
	' Description  : Function to login to the Application
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 24-10-2013
	' Author       : Manasa 
	*******************************************************************************************/
	public String login(Selenium selenium, String varUserName,
			String varPassword) throws Exception {
		String lStrReason = "";

		// Create an object to invoke the common function for Element Wait.

		// Create an object to refer to the Element ID Properties file
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// WaitForElement waitForElements = new WaitForElement();
		selenium.setTimeout(gstrTimeOut);

		try {
			selenium.open(propEnvDetails.getProperty("urlEU"));
			try {
				assertEquals("EMTrack ~ Login", selenium.getTitle());
				log4j.info("EMTrack ~ Login screen is displayed.");

			} catch (AssertionError assertionFailedError) {
				log4j.info(assertionFailedError);
				log4j.info("EMTrack ~ Login screen is not displayed.");
				lStrReason = lStrReason + "; "
						+ "EMTrack ~ Login screen is NOT displayed.";
			}

			assertTrue(selenium.isElementPresent(propElementDetails
					.getProperty("userName")));
			selenium.type(propElementDetails.getProperty("userName"),
					varUserName);
			log4j.info(varUserName + " username entered");

			assertTrue(selenium.isElementPresent(propElementDetails
					.getProperty("password")));
			selenium.type(propElementDetails.getProperty("password"),
					varPassword);
			log4j.info(varPassword + " password entered");

			selenium.click(propElementDetails.getProperty("loginButton"));
			selenium.waitForPageToLoad(gstrTimeOut);
			log4j.info("LOGIN button clicked");

			while (selenium.isElementPresent(propElementDetails
					.getProperty("AcceptPolicy"))) {
				selenium.click(propElementDetails.getProperty("AcceptPolicy"));
				selenium.waitForPageToLoad(gstrTimeOut);
			}

			while (selenium
					.isElementPresent("//div[@class='vspace']/input[@name='okButtonId'][@id='okButtonId']")) {
				selenium.click("//div[@class='vspace']/input[@name='okButtonId'][@id='okButtonId']");
				selenium.waitForPageToLoad(gstrTimeOut);
			}

			Thread.sleep(20000);
			try {
				assertTrue(selenium
						.isElementPresent("//div[@class='mainMenuContainer']/ul/li/a[@href='/track/home']"));
				log4j.info("'Login is successfull and home page is displayed.");
			} catch (AssertionError assertionFailedError) {
				log4j.info(assertionFailedError);
				log4j.info("'Login is NOT successfull and home page is NOT displayed.");
				lStrReason = lStrReason
						+ "; "
						+ "Login is NOT successfull and home page is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "LoginFunctions.Login failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//Login//

    //start//logOut//
	/*******************************************************************************************
	' Description  : Function to Log Out from EMTrack application
	' Precondition : N/A 
	' Arguments    : selenium
	' Returns      : String 
	' Date         : 30/10/2013
	' Author       : Rahul
	*******************************************************************************************/
	public String logOut(Selenium selenium) throws Exception {
		String lStrReason = "";

		// Create an object to refer to the Element ID Properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		// Create an object to refer to the Environment Properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.ReadEnvironment();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.click(propElementDetails.getProperty("logOutLink"));
			Thread.sleep(5000);
			try {
				assertEquals("EMTrack ~ Login", selenium.getTitle());
				log4j.info("EMTrack ~ Login screen is displayed.");

			} catch (AssertionError assertionFailedError) {
				log4j.info(assertionFailedError);
				log4j.info("EMTrack ~ Login screen is not displayed.");
				lStrReason = lStrReason + "; "
						+ "EMTrack ~ Login screen is NOT displayed.";
			}
		} catch (Exception e) {
			log4j.info(e);
			lStrReason = "LoginPage.logOut failed to complete due to "
					+ lStrReason + "; " + e.toString();
		}
		return lStrReason;
	}
	// end//logOut//
}