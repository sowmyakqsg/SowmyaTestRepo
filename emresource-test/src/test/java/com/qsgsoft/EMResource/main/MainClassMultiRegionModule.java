package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 // Multi Region Module
	Notifications.class,
	SetupCustomView.class,
	DirectAccessToMultReg.class,
	RegionWithMutualAgreementAndOtherRegionViewRight.class,
	MultiRegionUserWithLoginAccessToMultipleRegions.class,
	RegionsWithMutualAgreementAndDirectAccess.class,

})
public class MainClassMultiRegionModule {

}
