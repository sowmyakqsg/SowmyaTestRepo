package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	
	//TCs dont hav pop ups an, downloads and uploads
	SmokeSetupFirefox.class,
	Smoke_Forms.class,	
	Smoke_RegionalInfo.class,
	SmokePreferences.class,
	
	SmokeMultiRegionFeatures.class,
	SmokeResourceHierarchies.class,
	Smoke_ImportResources.class,
	
	Smoke_Events.class,		
	Smoke_Setup.class,		
	Smoke_Reports.class,

})

public class MainClassSmokeSuiteTest {
	
}
