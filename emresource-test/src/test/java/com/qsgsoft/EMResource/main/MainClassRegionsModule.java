package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

// Regions Module
		CreateRegion.class, ActivateSystemNotice.class,
})
public class MainClassRegionsModule {

}
