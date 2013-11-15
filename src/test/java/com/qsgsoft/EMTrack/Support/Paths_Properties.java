package com.qsgsoft.EMTrack.Support;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Paths_Properties {
	static Logger log4j = Logger.getLogger(Paths_Properties.class);
	
	public Properties ReadAutoit_FilePath() throws Exception {
			Properties autoItProps = new Properties();
			InputStream is = this.getClass().getResourceAsStream(System.getProperty("autoit.file", "/PropertiesFiles/AutoitPath.properties"));
			autoItProps.load(is);
			return autoItProps;
	}
	
	public Properties Read_FilePath() throws Exception {
		Properties pathProperties = new Properties();
		InputStream is = this.getClass().getResourceAsStream(System.getProperty("path.file", "/PropertiesFiles/path.properties"));
		pathProperties.load(is);
		return pathProperties;
		}
}