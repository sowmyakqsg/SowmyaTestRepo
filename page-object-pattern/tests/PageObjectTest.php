<?php
//Importing required class
require_once 'C:/workspace/PHPDemo/page-object-pattern/Configuration/Configuration.php';
require_once 'C:/workspace/PHPDemo/page-object-pattern/pages/LoginPage.php';
require_once 'C:/workspace/PHPDemo/page-object-pattern/pages/SelectRegionPage.php';
require_once 'C:/workspace/PHPDemo/page-object-pattern/pages/RegionDefaultPage.php';
require_once 'C:/workspace/PHPDemo/page-object-pattern/pages/StatusTypePage.php';
require_once 'C:/workspace/PHPDemo/page-object-pattern/pages/CreateStatusTypePage.php';
require_once 'C:/workspace/PHPDemo/page-object-pattern/pages/SelectStatusTypePage.php';

class Tests_PageObjectTest extends Configuration
{
    public function testAPageInteractsWithElementsExposingAnHigherLevelApi()
    {
    	$username = "qsg";
    	$password = "abc123";
    	$statusTypeName = 'Auto'.date('Ymdhi').date('s')*1000;
    	$description = "Automation";
    	$regionName = "Z_Smoke_Build_7655";
    	$statusTypeValue = 'Number';
    	
    	$this->url('/login.jsp');
    	$this->currentWindow()->maximize();
    	$this->timeouts()->implicitWait(30000);
    	
    	//Login to application
        $page = new LoginPage($this);
        $page->username($username);
        $page->password($password);
        $page->assertLoginPageDisplayed();
        $page->submit();
        
        //Selecting region
        $SelectRegionPage = new SelectRegionPage($this);
        $SelectRegionPage->assertSelectRegionDisplayedIs();
        $SelectRegionPage->selectRegion($regionName);
        
        $RegionDefaultPage = new RegionDefaultPage($this);
        $RegionDefaultPage->assertRegionNameDisplayed($regionName);
        $RegionDefaultPage->navStatusType();
        
        //Navigating to status type page
        $StatusTypePage = new StatusTypePage($this);
        $StatusTypePage->clickCreateStatusType();
        
        //Selecting status 
        $SelectStatusTypPage = new SelectStatusTypPage($this);
        $SelectStatusTypPage->selectStatusType($statusTypeValue);
        
        //Creating status type
        $CreateStatusTypePage = new CreateStatusTypePage($this);
        $CreateStatusTypePage->enterMandFields($statusTypeName,$description);
        $CreateStatusTypePage->saveStatusType();
    }
}
