package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 // Users Module
	CreateUser.class,
	CreateUserAutoIT.class,
	EditUsers.class,
	SwitchUser.class,
	RemoveUserFromRegion.class,
	NewFeatureFor3Point15.class,
	RefineStatusTypes.class,
	ViewResourceRight.class,
	SearchUsers.class,
	ProvideMultiRegnEventRightsForUsers.class,
	UsersWithOverrideViewingRestrictionRight.class,
	ProvideAccessToRegionsForUser.class,

})
public class MainClassUsersModule {

}
