<?php
class SelectStatusTypPage extends PHPUnit_Extensions_Selenium2TestCase
{

	private static  $statusType= "statusValueCode";
	private static  $next= "input[value='Next']";
	private static  $pageHeader= "h1";

	public function __construct($test)
	{
		$this->nextField = $test->byCssSelector(self::$next);
		$this->test = $test;
		$this->test->assertEquals('Select Status Type',$this->test->byCssSelector(self::$pageHeader)->text());
	}

	public function selectStatusType($label)
	{
		//selecting region
		PHPUnit_Extensions_Selenium2TestCase_Element_Select::fromElement($this->test->byName(self::$statusType))->selectOptionByLabel($label);
		$this->nextField->click();
		return new CreateStatusTypePage($this->test);
	}
}