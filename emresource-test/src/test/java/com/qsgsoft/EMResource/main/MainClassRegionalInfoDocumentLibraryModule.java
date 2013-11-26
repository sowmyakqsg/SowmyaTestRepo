package com.qsgsoft.EMResource.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.qsgsoft.EMResource.features.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({

	 //Regional Info >> Document Library
	AddDocument.class,
	CreateFolder.class,
	RenameFolder.class,
	DeleteDocument.class,
	DeleteFolder.class,
	MoveADocument.class,
	
})
public class MainClassRegionalInfoDocumentLibraryModule {

}
