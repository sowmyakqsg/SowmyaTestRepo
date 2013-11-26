package com.qsgsoft.EMResource.shared;

import java.util.Properties;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.Logger;

/*******************************************************************
' Description :This class includes common functions for Event Banner 
' Date		  :14-May-2013
' Author	  :QSG
'--------------------------------------------------------------------
' Modified Date                                          Modified By
' <Date>                           	                     <Name>
'********************************************************************/
public class EventBanner {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.shared.EventBanner");

	public Properties propEnvDetails;
	Properties propElementDetails;
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	ElementId_properties objelementProp = new ElementId_properties();
	public String gstrTimeOut;
	ReadData rdExcel;
	
	/*****************************************************
	 'Description  :To click event banner
	 'Arguments    :selenium,strEventName
	 'Returns      :String
	 'Date         :14-May-2013
	 'Author       :QSG
	 '---------------------------------------------------
	 'Modified Date                          Modified By
	 '<Date>                                 <Name>
	 *****************************************************/

	public String clickEventWithFocusOnEveBanner(Selenium selenium, String strEveName)
			throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");

			try {
				selenium.focus("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
						+ strEveName + "']");
			} catch (Exception e) {

			}
			// Click on Event name in event banner
			selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"
					+ strEveName + "']");
			try {
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (Exception e) {

			}
			
			Thread.sleep(5000);

		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}
	
	/*****************************************************
	 'Description  :To click event banner
	 'Arguments    :selenium,strEventName
	 'Returns      :String
	 'Date         :14-May-2013
	 'Author       :QSG
	 '---------------------------------------------------
	 'Modified Date                          Modified By
	 '<Date>                                 <Name>
	 *****************************************************/

	public String VarAddressOnEveBanner(Selenium selenium, String strEveValue,
			String strlocData) throws Exception {
		String strReason = "";
		propEnvDetails = objReadEnvironment.readEnvironment();
		propElementDetails = objelementProp.ElementId_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		General objMail = new General();

		try {
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			String strlocData1[]=objMail.enodeToUnicode(selenium,
					strlocData, 160);
			strlocData=strlocData1[1];
			
			String strEventbannerData = selenium
					.getText("//div[@id='ed"+strEveValue+"']");
			
			String[] strMails = objMail.enodeToUnicode(selenium,
					strEventbannerData, 160);
			
			strEventbannerData = strMails[1];
			
			if (strEventbannerData.equals(strlocData)) {
				log4j.info("Address is displayed in the �Event Banner�.");
			} else {
				log4j.info("Address is NOT displayed in the �Event Banner�.");
				strReason = "Address is NOT displayed in the �Event Banner�.";
			}
		} catch (Exception e) {
			log4j.info(e);
			strReason = e.toString();
		}
		return strReason;
	}

}
	



