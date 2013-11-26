package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	/*CreateRegion.class,  
	EditRegion.class,  */
	
	
	CreateRegionInterfaceOfTypeGetEDXLHAVE.class,  
	CreateRegionInterfaceOfTypePostResourceDetailStatus.class,  
	CreateRegionInterfaceOfTypeUpdateCADStatus.class,  
	CreateRegnInterfaceUpdateResourceAvailability.class,  
	CreateRgnInterfaceGetHospitalStatus.class,  
	CreatRgnInterfaceOfGetCADStatus.class,
	EditRegionInterfaceOfTypeGetHospitalStatus.class,  
	EditRegionInterfaceOfTypePostResourceDetailStatus.class,  
	EditRegionInterfaceOfTypeUpdateCADStatus.class,  
	EditRegionInterfOfGetCADStatus.class,  
	EditRegnInterfaceOfTypeGetEDXLHAVE1Point0.class,  
	EditRegnInterfaceUpdateResourceAvailability.class, 
	RenameFolder.class,  
	ActivateSystemNotice.class,
	ChangePassword.class,
	CommonScenarios.class,  
	CopyRegionView.class,  
	CreateBulletinMessage.class,  
	CreateFolder.class,  
	CreateMultiStatusType.class,  
	CreateNumberStatusType.class, 
	CreateResource.class,  
	CreateResourceType.class,  
	CreateRole.class,  
	
	SwitchUser.class,  
	CreateSaturationScoreStatType.class,  
	CreateStatTypeWithTimer.class,  
	CreateStatusReason.class,  
	CreateTextStatusType.class,  
	CreateUser.class,  
	DeleteBulletinMessage.class,  
	DeleteFolder.class,  
	DeleteRegionView.class,   
	EditBulletinMessage.class,  
	EditResource.class,  
	EditResourceType.class,  
	EditRole.class,  
	EditStatus.class, 
	EditUsers.class, 
	EditStatusReason.class,
	EditTextStatusType.class,  
	EditUserInformation.class,  
	SearchUsers.class,  	 
	SetupCustomView.class,  	
	UserAssgnForViewResAndStatTypes.class,  
	ViewResourceRight.class, 
	InActiveResourceType.class,  
	ITriageConfiguringUpdatingST.class,  	  
	NewFeatureFor3Point15.class,  
	NewFor3pt15NotToForceStatUpdate.class,  
	PromtUserToSelectState.class,  
	ProvideResourceRights.class,  
	ProvidingSecuritytoEventTemp.class,  
	
	
	
	//SectionRelated
	UpdateStatuScreen.class,  
	EditStatusTypeAtResourceLevel.class,  
	EditSTResourceLlevel.class,  
	RefineStatusTypes.class, 
	ViewResourceDetailScreen.class, 
	SettingUpOfRegionViews.class,  
	SettingUpRegionViews.class, 
	Notifications.class,
	RightsToViewUpdatePrivateStatusTypes.class,  
	RightTConfigureForms.class,  
	RightToViewUpdateRoleBasedST.class,  
	RoleBasedStatusTypes.class,  
	RoleWithOverrideViewingRestrictionsright.class, 
	RightsToView.class, 
	EditSaturationScoreStatType.class, 
	EditMultiStatusType.class,  
	EditNumberStatusType.class, 
	PrivateStatusTypes.class, 
	
	
	//Mail Related
	SelectNotifiPreferenceET.class,  
	AddStatusChangePreferences.class,  
	EditStatusChangePreferences.class, 
	DeleteStatusChangePreferences.class, 
	CommonScenariosForST.class,  
	RecvExpiredStatusNotifiForMST.class,  
	RecvExpiredStatusNotifiForNST.class,  
	RecvExpiredStatusNotifiForSST.class,  
	RecvExpiredStatusNotifiForTST.class,  
	
	
	//MultiRegn
	DirectAccessToMultReg.class,  
	RegionsWithMutualAgreementAndDirectAccess.class,  
	RegionWithMutualAgreementAndOtherRegionViewRight.class,  
	RemoveUserFromRegion.class,  
	MultiRegionEvent.class,  
	MultiRegionUserWithLoginAccessToMultipleRegions.class,
	UserAssignmentForViewingResourcesAndStatusTypes.class,  
	UsersWithOverrideViewingRestrictionRight.class, 
	ProvideAccessToRegionsForUser.class,  
	ProvideMultiRegnEventRightsForUsers.class,  
	
	//firfox	
	FireFoxCreateQuickLinkForForm.class,  
	FireFoxCreateQuickLinkForWebsite.class,  
	FireFoxCreateUserLinkForForm.class, 
	FireFoxCreateUserLinkForForm.class,  
	FireFoxCreateUserLinkForWebsite.class,  
	FirefoxEditNewFormQuestionnaire.class,  
	
	//Event Related
	CreateEvent.class,  
	CreateEventTemplate.class,  
	CreatePrivateEvent.class,  
	EditEvent.class,  
	EditEventTemplate.class,  
	EventLists.class,  
	EventNotifications.class,  
	
	//AutoIt
	AddDocument.class,  
	DeleteDocument.class,  
	DeleteUserLink.class, 
	ImportResFromSpreadSheet.class,  
	ImportUsersAndResources.class,  
	ImportUsersFromSpreadsheet.class,
	EventDetailReport.class, 
	EventSnapshotReport.class,
	MoveADocument.class, 
	StatusDetailReport.class,  
	StatusReasonDetailRep.class,  
	StatusReasonSummaryRep.class,  
	StatusSnapshotReport.class,  
	StatusSummaryReport.class,  
	FormDetailReportUpdated.class, 
	NewFor3pt15AddStatusTypeToCurrentlyRunningEvent.class,
	StatewideResourceDetailsReport.class, 	
	
	
	InactivateMultiStatType.class,  
	InactivateResourceType.class,  
	InactiveNumberStatType.class,  
	InactiveSaturScoreStatType.class,  
	InactiveTextStatType.class,  
	CreateStatus.class,  
	ChangeResTypeOfResource.class
})

public class MainClassBQS {
	
}
