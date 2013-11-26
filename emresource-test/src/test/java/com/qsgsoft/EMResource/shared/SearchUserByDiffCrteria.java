package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.Selenium;

/******************************************************************
' Description :This class includes common functions of Search Users
' Date		  :8th-March-2013
' Author	  :QSG
'------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'******************************************************************/

public class SearchUserByDiffCrteria {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.SearchUsers");

	public Properties propEnvDetails;
	Properties propElementDetails;
	Properties pathProps;
	String gstrTimeOut="";
	
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	Paths_Properties objAP = new Paths_Properties();

	ReadData rdExcel;
	
	/***********************************************************************
	'Description	:Verify user is searched by different Criteria
	'Precondition	:None
	'Arguments		:selenium,strByRole,strByResourceType,strByUserInfo
	'				 strNameFormat,strUserName
	'Returns		:String
	'Date	 		:4-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-May-2012                               <Name>
	************************************************************************/

	public String searchUserByDifCriteria(Selenium selenium,
			String strUserName, String strByRole, String strByResourceType,
			String strByUserInfo, String strNameFormat) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			int intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewUsr.SearchByRole")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.select(
					propElementDetails.getProperty("CreateNewUsr.SearchByRole"),
					"label=" + strByRole);
			selenium.select(propElementDetails
					.getProperty("CreateNewUsr.SearchByResrc"), "label="
					+ strByResourceType);
			selenium.select(propElementDetails
					.getProperty("CreateNewUsr.SearchByUsrInfo"), "label="
					+ strByUserInfo);
			selenium.select(propElementDetails
					.getProperty("CreateNewUsr.SearchByOpreator"), "label="
					+ strNameFormat);

			selenium.type(
					propElementDetails.getProperty("CreateNewUsr.SearchText"),
					strUserName);
			intCnt = 0;
			do {
				try {

					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewUsr.Search")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);
			selenium.click(propElementDetails
					.getProperty("CreateNewUsr.Search"));

			intCnt = 0;

			try {

				while (selenium.isVisible(propElementDetails
						.getProperty("Reloading.Element")) && intCnt < 60) {
					intCnt++;
					Thread.sleep(500);
				}

			} catch (Exception e) {
				log4j.info(e);
			}
			log4j.info(intCnt);

		} catch (Exception e) {
			log4j.info("searchUserByDifCriteria function failed" + e);
			strErrorMsg = "searchUserByDifCriteria function failed" + e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify user is searched by different Criteria
	'Precondition	:None
	'Arguments		:selenium,strByRole,strByResourceType,strByUserInfo
	'				 strNameFormat,strUserName
	'Returns		:String
	'Date	 		:4-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-May-2012                               <Name>
	************************************************************************/

	public String searchUserByDifCriteriaInFormPage(Selenium selenium,
			String strUserName, String strByRole, String strByResourceType,
			String strByUserInfo, String strNameFormat) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			if (selenium.isElementPresent(propElementDetails
					.getProperty("CreateNewUsr.SearchInFormIDOption"))) {
				selenium.select(propElementDetails
						.getProperty("CreateNewUsr.SearchByRoleInForm"),
						"label=" + strByRole);
				selenium.select(propElementDetails
						.getProperty("CreateNewUsr.SearchByResrcInForm"),
						"label=" + strByResourceType);
				selenium.select(propElementDetails
						.getProperty("CreateNewUsr.SearchByUsrInfoInForm"),
						"label=" + strByUserInfo);
				selenium.select(propElementDetails
						.getProperty("CreateNewUsr.SearchByOpreatorInForm"),
						"label=" + strNameFormat);

				selenium.type(propElementDetails
						.getProperty("CreateNewUsr.SearchTextInForm"),
						strUserName);

				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.SearchInForm"));

				int intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element")) && intCnt < 60) {
						intCnt++;
						Thread.sleep(500);
					}

				} catch (Exception e) {
					log4j.info(e);
				}
				log4j.info(intCnt);
			}

		} catch (Exception e) {
			log4j.info("searchUserByDifCriteriaInFormPage function failed" + e);
			strErrorMsg = "searchUserByDifCriteriaInFormPage function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify user is searched by different Criteria
	'Precondition	:None
	'Arguments		:selenium,strByRole,strByResourceType,strByUserInfo
	'				 strNameFormat,strUserName
	'Returns		:String
	'Date	 		:4-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-May-2012                               <Name>
	************************************************************************/

	public String searchUserByDifCriteriaInAssignUsersPge(Selenium selenium,
			String strUserName, String strByRole, String strByResourceType,
			String strByUserInfo, String strNameFormat) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();

			selenium.selectWindow("");
			selenium.selectFrame("Data");

			if (selenium.isElementPresent(propElementDetails
					.getProperty("AssignUsers.SearchByRole"))) {

				selenium.select(propElementDetails
						.getProperty("AssignUsers.SearchByRole"), "label="
						+ strByRole);
				selenium.select(propElementDetails
						.getProperty("AssignUsers.SearchByResrc"), "label="
						+ strByResourceType);
				selenium.select(propElementDetails
						.getProperty("AssignUsers.SearchByUsrInfo"), "label="
						+ strByUserInfo);
				selenium.select(propElementDetails
						.getProperty("AssignUsers.SearchByOpreator"), "label="
						+ strNameFormat);

				selenium.type(propElementDetails
						.getProperty("AssignUsers.SearchText"), strUserName);

				selenium.click(propElementDetails
						.getProperty("AssignUsers.Search"));

				int intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element")) && intCnt < 60) {
						intCnt++;
						Thread.sleep(500);
					}

				} catch (Exception e) {
					log4j.info(e);
				}
				log4j.info(intCnt);

			}

		} catch (Exception e) {
			log4j.info("searchUserByDifCriteriaInAssignUsersPge function failed"
					+ e);
			strErrorMsg = "searchUserByDifCriteriaInAssignUsersPge function failed"
					+ e;
		}
		return strErrorMsg;
	}
	/***********************************************************************
	'Description	:Verify Resource is searched by different Criteria
	'Precondition	:None
	'Arguments		:selenium,strByRole,strByResourceType,strByUserInfo
	'				 strNameFormat,strUserName
	'Returns		:String
	'Date	 		:4-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-May-2012                               <Name>
	************************************************************************/
	public String searchRSByDifCriteriaInDemoteResourcePage(Selenium selenium,
			String strSubResourceType, String strByResourceType,
			String strByResourceFormat, String strNameFormat,
			String strResourceName, String strResourceValue, boolean blnSave)
			throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("CreateResource.SelectRTInDemoteResourcePage")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.select(
					propElementDetails
							.getProperty("CreateResource.SelectRTInDemoteResourcePage"),
					"label=" + strSubResourceType);

			if (selenium
					.isElementPresent(propElementDetails
							.getProperty("CreateResource.SearchoptionInDemoteResourcePage"))) {

				selenium.select(
						propElementDetails
								.getProperty("CreateResource.SearchByRoleInDemoteResourcePage"),
						"label=" + strByResourceType);
				selenium.select(
						propElementDetails
								.getProperty("CreateResource.SearchByFieldInDemoteResourcePage"),
						"label=" + strByResourceFormat);
				selenium.select(
						propElementDetails
								.getProperty("CreateResource.SearchByOperatorInDemoteResourcePage"),
						"label=" + strNameFormat);

				selenium.type(
						propElementDetails
								.getProperty("CreateResource.SearchTextInDemoteResourcePage"),
						strResourceName);

				selenium.click(propElementDetails
						.getProperty("CreateResource.SearchInDemoteResourcePage"));

				intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element")) && intCnt < 60) {
						intCnt++;
						Thread.sleep(500);
					}

				} catch (Exception e) {
					log4j.info(e);
				}
				log4j.info(intCnt);
			}
			selenium.click("css=input[name='resourceID'][value='"
					+ strResourceValue + "']");

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			}

		} catch (Exception e) {
			log4j.info("searchRSByDifCriteriaInDemoteResourcePage function failed"
					+ e);
			strErrorMsg = "searchRSByDifCriteriaInDemoteResourcePage function failed"
					+ e;
		}
		return strErrorMsg;
	}

	/***********************************************************************
	'Description	:Verify Resource is searched by different Criteria
	'Precondition	:None
	'Arguments		:selenium,strByRole,strByResourceType,strByUserInfo
	'				 strNameFormat,strUserName
	'Returns		:String
	'Date	 		:4-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'4-May-2012                               <Name>
	************************************************************************/

	public String searchRSByDifCriteriaInDemoteResourcePageByChaekingSearchFunctionality(
			Selenium selenium, String strSubResourceType,
			String strByResourceType, String strByResourceFormat,
			String strNameFormat, String strResourceName,
			String strResourceValue, boolean blnSave) throws Exception {

		String strErrorMsg = "";// variable to store error mesage

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			rdExcel = new ReadData();

			propEnvDetails = objReadEnvironment.readEnvironment();
			propElementDetails = objelementProp.ElementId_FilePath();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			int intCnt = 0;
			do {
				try {

					assertTrue(selenium
							.isElementPresent(propElementDetails
									.getProperty("CreateResource.SelectRTInDemoteResourcePage")));
					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCnt++;

				} catch (Exception Ae) {
					Thread.sleep(1000);
					intCnt++;
				}
			} while (intCnt < 60);

			selenium.select(
					propElementDetails
							.getProperty("CreateResource.SelectRTInDemoteResourcePage"),
					"label=" + strSubResourceType);

			if (selenium
					.isElementPresent(propElementDetails
							.getProperty("CreateResource.SearchoptionInDemoteResourcePage"))) {

				selenium.select(
						propElementDetails
								.getProperty("CreateResource.SearchByRoleInDemoteResourcePage"),
						"label=" + strByResourceType);
				selenium.select(
						propElementDetails
								.getProperty("CreateResource.SearchByFieldInDemoteResourcePage"),
						"label=" + strByResourceFormat);
				selenium.select(
						propElementDetails
								.getProperty("CreateResource.SearchByOperatorInDemoteResourcePage"),
						"label=" + strNameFormat);

				selenium.type(
						propElementDetails
								.getProperty("CreateResource.SearchTextInDemoteResourcePage"),
						strResourceName);

				selenium.click(propElementDetails
						.getProperty("CreateResource.SearchInDemoteResourcePage"));

				intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element")) && intCnt < 60) {
						intCnt++;
						Thread.sleep(500);
					}

				} catch (Exception e) {
					log4j.info(e);
				}
				log4j.info(intCnt);

				if (selenium.getXpathCount(
						"//table[@summary='Select Resources']/tbody/tr")
						.intValue() == 1) {
					log4j.info("Search is succesfull");
				} else {
					log4j.info("Search is NOT succesfull");
					strErrorMsg = "Search is NOT succesfull";
				}
			}
			selenium.click("css=input[name='resourceID'][value='"
					+ strResourceValue + "']");

			if (blnSave) {
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			}

		} catch (Exception e) {
			log4j.info("searchRSByDifCriteriaInDemoteResourcePage function failed"
					+ e);
			strErrorMsg = "searchRSByDifCriteriaInDemoteResourcePage function failed"
					+ e;
		}
		return strErrorMsg;
	}

}