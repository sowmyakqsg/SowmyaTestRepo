package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 // ResourceHierarchies Module
	ResourceSearch.class,
	CreatingSubResource.class,
	CreatingSubResourceType.class,	
	CreatingAndManagingEvents.class,
	CreatingAndManagingUsers.class,
	EditingSubResource.class,
	CreatingAndDemoteResource.class,
	ViewingAndManagingEMResourceEntitiesOnTheViewInterface.class,
	//Multiple browser
	MultipleBrowser_ViewingAndManEMREntitiesOnTheViewInterface.class,
})
public class MainClassResourceHierarchiesModule {

}
