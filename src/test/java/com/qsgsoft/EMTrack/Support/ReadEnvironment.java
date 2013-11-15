package com.qsgsoft.EMTrack.Support;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadEnvironment {
	static Logger log4j = Logger.getLogger(ReadEnvironment.class);

	
	public Properties ReadEnvironment() throws Exception {
		Properties environmentProps = new Properties();
		String propertiesFilename = System.getProperty("environment.file", "/PropertiesFiles/environment.properties");
		InputStream is = this.getClass().getResourceAsStream(propertiesFilename);
		environmentProps.load(is);

		// bamboo sauce
		if (System.getProperty("bamboo.SELENIUM_HOST") != null) {
		    log4j.debug("Host: " + System.getProperty("bamboo.SELENIUM_HOST"));
		    environmentProps.setProperty("Server", System.getProperty("bamboo.SELENIUM_HOST"));
		}
		if (System.getProperty("bamboo.SELENIUM_PORT") != null) {
		    environmentProps.setProperty("ServerPort", System.getProperty("bamboo.SELENIUM_PORT"));
		}
		if (System.getProperty("bamboo.SELENIUM_BROWSER") != null) {
		    environmentProps.setProperty("Browser", System.getProperty("bamboo.SELENIUM_BROWSER"));
		}

		return environmentProps;
	}

	
}
