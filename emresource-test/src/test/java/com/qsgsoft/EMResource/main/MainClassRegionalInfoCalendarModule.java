package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

		// Regional Info>> Calendar Module
		CreateBulletinMessage.class, EditBulletinMessage.class,
		DeleteBulletinMessage.class, })
public class MainClassRegionalInfoCalendarModule {

}
