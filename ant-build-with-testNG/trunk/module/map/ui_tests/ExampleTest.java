package module.map.ui_tests;

import org.testng.annotations.Test;
import qaframework.lib.testngExtensions.TestNG_UI_EXTENSIONS;
import lib.dataObject.Login_data;
import lib.page.*;

public class ExampleTest extends TestNG_UI_EXTENSIONS {
	/**
	 * Test that satellite view is displayed when user view map in satellite view
	 * @throws Exception
	 */
	@Test (description = "Verify that satellite view is displayed when user view map in satellite view",
			   groups = {"136882","BQS"})
	public void satelliteViewOnMap() throws Exception{

		Login login_page = new Login(this.driver);
		Login_data login_data = new Login_data();
	    
	   // login_page.loginFailure(login_data.username1, login_data.password);
	}
}