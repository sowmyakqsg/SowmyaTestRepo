package com.qsgsoft.EMTrack.Support;

import java.io.InputStream;
import java.util.Properties;

public class ElementId_properties {

	public Properties ElementId_FilePath() throws Exception {
		Properties elementProps = new Properties();
		InputStream is = this.getClass().getResourceAsStream(System.getProperty("Element.file", "/PropertiesFiles/ElementID.properties"));
		elementProps.load(is);
		return elementProps;
	}

}
