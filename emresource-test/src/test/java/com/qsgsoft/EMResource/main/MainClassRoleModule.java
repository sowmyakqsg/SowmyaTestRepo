package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	// Role Module
	CreateRoleAutoIT.class, CreateRole.class,
	RoleWithOverrideViewingRestrictionsright.class, EditRole.class,
	RightsToViewUpdatePrivateStatusTypes.class,
	RightToViewUpdateRoleBasedST.class,
})
public class MainClassRoleModule {

}
