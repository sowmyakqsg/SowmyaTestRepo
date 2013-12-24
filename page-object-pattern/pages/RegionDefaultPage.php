<?php
class RegionDefaultPage extends PHPUnit_Extensions_Selenium2TestCase
{
	private static  $setup= "div#topNav>div>a";
	private static  $regionName= "regionName";
	private static  $statusTypesSubMenu= "Status Types";

	public function __construct($test)
	{
		$this->setupMenu =$test->byCssSelector(self::$setup);
		$this->selectedRegion = $test->byId(self::$regionName);
		$this->test = $test;
	}

	public function assertRegionNameDisplayed($regionName)
	{
		$this->assertEquals($regionName,$this->selectedRegion->text());
		return $this;
	}

	public function navStatusType()
	{
		$this->setupMenu->click();
		$this->test->byLinkText(self::$statusTypesSubMenu)->click();
		return new StatusTypePage($this->test);
	}
}