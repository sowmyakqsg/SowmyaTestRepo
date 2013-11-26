package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 // Views Module
	SettingUpOfRegionViews.class,
	SettingUpRegionViews.class,
	CopyRegionView.class,
	DeleteRegionView.class,
	ViewResourceDetailScreen.class,
	UpdateStatuScreen.class,
	
	
})
public class MainClassViewModule {

}
