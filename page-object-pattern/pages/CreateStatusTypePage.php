<?php
class CreateStatusTypePage extends PHPUnit_Extensions_Selenium2TestCase
{
	private static  $statusTypeName= "statusTypeName";
	private static  $definition= "definition";
	private static  $save= "input[value='Save']";
	private static  $pageHeader= "h1";

	public function __construct($test)
	{
		$this->statusTypeNames = $test->byName(self::$statusTypeName);
		$this->description = $test->byName(self::$definition);
		$this->saveBtn =$test->byCssSelector(self::$save);
		$this->test = $test;
		$this->test->assertEquals('Create Number Status Type',$this->test->byCssSelector(self::$pageHeader)->text());
	}

	public function enterMandFields($statusTypeName,$description)
	{
		$this->statusTypeNames->value($statusTypeName);
		$this->description->value($description);
		return $this;
	}

	public function saveStatusType(){
		$this->saveBtn->click();
		return new StatusTypePage($this->test);;
	}
}