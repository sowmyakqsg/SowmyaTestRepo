package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

		// Interface Module
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
})
public class MainClassInterfaceModule {

}