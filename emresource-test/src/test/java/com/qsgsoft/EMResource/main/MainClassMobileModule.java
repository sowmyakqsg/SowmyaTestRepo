package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

// mobile Module
		CreateEvent.class,// 5
		CreateEventTemplate.class, // 1
		CreatePrivateEvent.class, // 5
		EditEvent.class, // 1
		EditEventTemplate.class,// 4
		MultiRegionEvent.class,// 2
		SettingUpOfRegionViews.class,// 1
		SettingUpRegionViews.class,// 3
		RemoveUserFromRegion.class,// 1
		UsersWithOverrideViewingRestrictionRight.class,// 2
		CreateResourceType.class,// 1
		EditResource.class,// 1
		RightToViewUpdateRoleBasedST.class,// 3
		RightsToViewUpdatePrivateStatusTypes.class,// 3
		RoleBasedStatusTypes.class,// 4
		EditStatusTypeAtResourceLevel.class,// 1
		ViewResourceRight.class,// 1
		RefineStatusTypes.class,// 2
		UserAssignmentForViewingResourcesAndStatusTypes.class,// 1
})
public class MainClassMobileModule {

}