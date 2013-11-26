package com.qsgsoft.EMResource.support;

import java.io.InputStream;
import java.util.Properties;

public class ElementId_properties {

	public Properties ElementId_FilePath() throws Exception {
		Properties ElementProps = new Properties();
		InputStream is = this.getClass().getResourceAsStream(System.getProperty("Element.file", "/PropertyFiles/ElementId.properties"));
		ElementProps.load(is);
		return ElementProps;
}
		
	public Properties ElementId_FilePathUsr() throws Exception {
		Properties ElementProps = new Properties();
		InputStream is = this.getClass().getResourceAsStream(System.getProperty("Element.file", "/PropertyFiles/UserDetails.properties"));
		ElementProps.load(is);
		return ElementProps;
}
	public Properties widgetElementId_FilePath() throws Exception {
		Properties ElementProps = new Properties();
		InputStream is = this.getClass().getResourceAsStream(System.getProperty("Element.file", "/PropertyFiles/ElementId_widget.properties"));
		ElementProps.load(is);
		return ElementProps;
}
	public Properties REGAPIElementId_FilePath() throws Exception {
		Properties ElementProps = new Properties();
		InputStream is = this.getClass().getResourceAsStream(System.getProperty("Element.file", "/PropertyFiles/REG_API_ElementIDs.properties"));
		ElementProps.load(is);
		return ElementProps;
}
}
