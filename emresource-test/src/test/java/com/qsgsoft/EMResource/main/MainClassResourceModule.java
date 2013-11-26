package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 // Resource Module
	EditStatusTypeAtResourceLevel.class,
	EditSTResourceLlevel.class,
	CreateResource.class,
	EditResource.class,
	ProvideResourceRights.class,
	UserAssignmentForViewingResourcesAndStatusTypes.class,
	ImportSubResourcesFromSpreadsheet.class,
	ImportResFromSpreadSheet.class,
	ImportUsersAndResources.class,
	ImportUsersFromSpreadsheet.class,
	//Multiple browser
	ChangeResTypeOfResource.class,
	
})
public class MainClassResourceModule {

}
