package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

		// Events Module
		EventLists.class, CreateEvent.class, CreateEventTemplate.class,
		EditEventTemplate.class, EventNotifications.class, EditEvent.class,
		MultiRegionEvent.class, CreatePrivateEvent.class,
		ProvidingSecuritytoEventTemp.class,
		NewFor3pt15AddStatusTypeToCurrentlyRunningEvent.class,
})
public class MainClassEventsModule {

}
