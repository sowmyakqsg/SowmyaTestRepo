package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( {	
	
	    // StatusTypes Module
	
		NewFor3pt15NotToForceStatUpdate.class,
		RoleBasedStatusTypes.class,
		PrivateStatusTypes.class,
		CreateNumberStatusType.class,
		CreateMultiStatusType.class,
		CreateTextStatusType.class,
		CreateSaturationScoreStatType.class,
		CreateStatTypeWithTimer.class,
		
		EditNumberStatusType.class,
		EditMultiStatusType.class,
		EditTextStatusType.class,
		EditSaturationScoreStatType.class,
		
		
		RecvExpiredStatusNotifiForNST.class,
		RecvExpiredStatusNotifiForMST.class,
		RecvExpiredStatusNotifiForTST.class,
		RecvExpiredStatusNotifiForSST.class,
		
		EditStatusReason.class,
		CreateStatusReason.class,
		EditStatus.class,
		CreateStatus.class,
		ViewResourceRight.class,	
		CommonScenarios.class,
		CommonScenariosForST.class,
		
		/*InactivateMultiStatType.class,
		InactiveNumberStatType.class,
		InactiveSaturScoreStatType.class,
		InactiveTextStatType.class,*/
})

public class MainClassTestCaseToBeRunNow {

}
