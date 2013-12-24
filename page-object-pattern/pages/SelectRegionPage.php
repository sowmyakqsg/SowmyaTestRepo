<?php
class SelectRegionPage extends PHPUnit_Extensions_Selenium2TestCase
{
	private static  $regionName= "regionID";
	private static  $next= "input[value='Next']";

	public function __construct($test)
	{
		$this->selectRegion = $test->byId(self::$regionName);
		$this->nextField = $test->byCssSelector(self::$next);
		$this->test = $test;
	}

	public function selectRegion($label){
		//selecting region
		PHPUnit_Extensions_Selenium2TestCase_Element_Select::fromElement($this->selectRegion)->selectOptionByLabel($label);
		$this->nextField->click();
		$this->test->frame('Data');
		return new RegionDefaultPage($this->test);
	}

	public function assertSelectRegionDisplayedIs()
	{
		$this->assertEquals('Select Region',$this->test->title());
		return $this;
	}
}