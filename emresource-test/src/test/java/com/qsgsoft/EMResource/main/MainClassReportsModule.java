package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	// Reports Module
	StatewideResourceDetailsReport.class,
	StatusDetailReport.class,
	StatusReasonDetailRep.class,
	StatusReasonSummaryRep.class,
	StatusSnapshotReport.class,
	StatusSummaryReport.class,
	EventDetailReport.class,
	EventSnapshotReport.class,
	ResourceDetailReport.class,
	FormDetailReportUpdated.class,
})
public class MainClassReportsModule {

}