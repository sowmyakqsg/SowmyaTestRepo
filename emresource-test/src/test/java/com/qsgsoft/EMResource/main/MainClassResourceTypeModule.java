package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 // Resource Type Module
	CreateResourceType.class,
	EditResourceType.class,
	InActiveResourceType.class,
	//Multiple browser
	InactivateResourceType.class,
	
})
public class MainClassResourceTypeModule {

}
