package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

		// UserLinks Module
		FireFoxCreateQuickLinkForForm.class,
		FireFoxCreateQuickLinkForWebsite.class,
		FireFoxCreateUserLinkForForm.class,
		FireFoxCreateUserLinkForWebsite.class, DeleteUserLink.class,
		FirefoxEditUserLink.class,
		// Forms
		FirefoxEditNewFormQuestionnaire.class,
})
public class MainClassUserLinkModule {

}