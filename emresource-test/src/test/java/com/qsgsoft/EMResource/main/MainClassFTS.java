package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	
	   //Withot AutoIT Test cases	    
	    FTSViewingAndManagingEMResourceEntitiesOnTheViewInterfaceMultiBrowser.class, 
		FTSActivateSystemNotice.class,
		FTSCreateRegion.class,		
		FTSEditRegion.class,
		FTSCreateBulletinMessage.class,
		FTSEditBulletinMessage.class,		
		FTSSwitchUser.class,
		FTSNewFeature.class,
		FTSStatusSnapShotReport.class,
		FTSStatusSummaryReport.class,
		FTSEventSnapshotReport.class,
		FTSProvideAccessToRegionsForAUser.class,
		FTSCreateRegionInterfaceOfTypeGetCADStatus.class,
		FTSCreateRegionInterfaceOfTypeHAvBEDInfo.class,
		FTSEditRegionInterfaceOfTypeHAvBEDInfo.class,
		FTSEditRegionInterfaceOfTypeHAvBEDInfo2.class,
		FTSCreateRegionInterfaceOfTypeHAvBED2pt5pt2Information.class,
		FTSCreateRegionInterfaceOfTypeHAvBED2pt5pt2Informations.class,
		FTSNewForEMRNumOfAllowableQuicklinksIsIncToSix.class,
		
		FTSProvideResourceRights.class,
		FTStatusChangeResourceTypeOfResource.class,
		FTSEditResource.class,
		FTSCreateResource.class,
		FTSSettingUpOfStandardStatusTypes.class,
		FTSEditingSubResource.class,
		FTSResourceSearch.class,
		FTSCreatingSubResource.class,
		FTSCreatingAndDemoteResource.class,
		FTSCreatingSubResourceType.class,
		FTSCreatingAndManagingEvents.class,
		FTSCreatingAndManagingUsers.class,
		FTSCreateEventTemplate.class,
		FTSEditEventTemplate.class,			
		FTSUserList.class,					
		FTSEventLists.class,
		FTSEditEvent.class,
		FTSCreateEvent.class,
		FTSCreateRole.class,
		FTSEditRole.class,
		FTSCreateResourceType.class,	
		FTSEditResourceType.class,					
		FTSRefineStatusTypes.class,	
		FTSInactivateResrcType.class,
		FTSUserAssignmentForViewingResourcesAndStatusTypes.class,
		
		//Multiple browser
		MultipleBrowser_FTSEditStatusTypeAtResLevel.class,
		MultipleBrowser_FTSEditEvent.class,
		MultipleBrowser_FTSCreateEvent.class,
		
		FTSRightsToViewUpdatePrivateStatusTypes.class,
		FTSRightToViewUpdateRoleBasedStatusTypes.class,
		FTSViewResourceRight.class,			
		FTSNewFeatureFor3Point15.class,								
		FTSPrivateStatusTypes.class,				
		FTSRoleBasedStatusTypes.class,
		FTSAddStatusTypesTOCurrentlyRunningEvent.class,
		FTSNewForEMRNotToForceStatusUpdate.class,
		FTSEditSTResourceLlevel.class,
		FTSEditSTResouceLevel.class,
		FTSEditStatusTypeAtResourceLevel.class,		
	    FTSRemoveUserFromRegion.class,	
	    FTSEditUser.class,
	    FTSCreateUser.class,
	    FTSProvidingSecurityToEventTemplate.class,
	    FTSCreateNumberStatusType.class,
	    FTSCreateTextStatusType.class,
	    
		//AutoIT Related Test cases
	    FTSImportSubResourcesFromSpreadsheet.class,
	    FTSImportResourcesFromSpreadsheet.class,
	    FTSImportUsersAndResources.class,
	    FTSImportUserFromSpreadSheet.class,
	    FTSNewFor3pt15_AddStatusTypeToCurrentlyRunningEvent_AutoIT.class,
	    FTSCreateEvent_AutoIT.class,
	    FTSCreatingAndManagingUsers_AutoIT.class,
	    FTSStatusSnapShotReport_AutoIT.class,
		FTSCreateAFolder.class,
		FTSRenameFolder.class,
		FTSDeleteFolder.class,
		FTSAddADocument.class,
		FTSMoveADocument.class,
		FTSDeleteADocument.class,	
		FTSCreateQuickLlinkForForm.class,
		FTSStatusDetailReport.class,
		FTSStatewideResourceDetailsReport.class,
		
		
})

public class MainClassFTS {
	
}
