package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 // Preferences>> Status Change Prefs Module
	AddStatusChangePreferences.class,
	DeleteStatusChangePreferences.class,
	EditStatusChangePreferences.class,
})
public class MainClassPreferencesStatusChangePrefsModule {

}
