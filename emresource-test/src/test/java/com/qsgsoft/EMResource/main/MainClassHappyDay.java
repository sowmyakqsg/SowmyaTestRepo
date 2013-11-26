package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	
	HappyDaySwitchUser.class,
	HappayDayForgotUserIDOrPassword.class,
	HappyDayAssociatingSectionToAstatusType.class,
	HappyDayEditEvent.class,
	HappyDayCreateEvent.class,	
	HappyDayAddStatusChangePreferences.class,
	HappyDayEventSnapshotReport.class,
	HappyDayEditNEDOCScoreStatusTypes.class,
	HappyDayEventDetailReport.class,
	HappyDayHelpDocumentation.class,
	HappyDayUpdateStatusScreen.class,
	HappyDayCreateNEDOCScoreStatusTypes.class,
	HappyDayNEDOCAndSaturationScoreCalculation.class,
	HappyDaySettingUpOfRegionViews.class,
	HappyDayStatusDetailReport.class,
	HappyDayStatusSnapshotReport.class,
	HappyDayImportUsersAndResources.class,
	HappyDayHICSICSNotifications.class,
	HappyDayDeleteRole.class,
	HappyDayImportedOrNewUserMinimumConfig.class,
	HappyDayDefaultRole.class,

})

public class MainClassHappyDay {
	
}
