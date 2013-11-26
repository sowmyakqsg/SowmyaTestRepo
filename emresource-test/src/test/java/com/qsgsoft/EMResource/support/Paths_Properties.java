package com.qsgsoft.EMResource.support;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Paths_Properties {
	static Logger log4j = Logger.getLogger("EMResource.Support.AutoITProperties");
	
	public Properties ReadAutoit_FilePath() throws Exception {
			Properties autoitProps = new Properties();
			InputStream is = this.getClass().getResourceAsStream(System.getProperty("autoit.file", "/PropertyFiles/AutoitPath.properties"));
			autoitProps.load(is);
			return autoitProps;
	}
	
	public Properties Read_FilePath() throws Exception {
		Properties autoitProps = new Properties();
		InputStream is = this.getClass().getResourceAsStream(System.getProperty("path.file", "/PropertyFiles/path.properties"));
		autoitProps.load(is);
		return autoitProps;
}

}
