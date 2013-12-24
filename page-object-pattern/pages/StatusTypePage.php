<?php
class StatusTypePage extends PHPUnit_Extensions_Selenium2TestCase
{
	private static  $createNewStatusType= "input[value='Create New Status Type']";
	private static  $pageHeader= "h1";

	public function __construct($test)
	{
		$this->createNewStatusTypeField =$test->byCssSelector(self::$createNewStatusType);
		$this->test = $test;
		$this->test->assertEquals('Status Type List',$this->test->byCssSelector(self::$pageHeader)->text());
	}

	public function clickCreateStatusType()
	{
		$this->createNewStatusTypeField->click();
		return new SelectStatusTypPage($this->test);
	}
}