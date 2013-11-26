package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	
	EdgeCaseCreateEvent.class,
	EdgeCaseEditEvent.class,
	EdgeCaseImportUsersAndResources.class,
	EdgeCaseEditEventTemplate.class,	
	EdgeCaseEndEvent.class,
	EdgecaseNEDOCAndSaturationScoreCalculation.class,
	EdgeCaseAddStatusChangePreferences.class,
	EdgecaseEditStatusChangePreferences.class,
	EdgecaseDeleteStatusChangePreferences.class,
	EdgeCaseEventSnapshotReport.class,
	EdgeCaseInactiveNEDOCSStatusType.class,
	EdgeCaseAssociatingSectionToAstatusType.class,
	EdgeCaseRecvExpiredStatusNotifiForNEDOCST.class,
	EdgeCaseRefineStatusType.class,
	EdgeCaseHICSNotifications.class,
	EdgeCaseDeleteRole.class,
	EdgeCaseForgotUserIDOrPassword.class,
	EdgeCaseResetPasswordOfAUser.class,
	//Multiple browser
	EdgeCaseMultipleAssociatingSectionToAstatusType.class,
	
})

public class MainClassEdgeCase {
	
}
