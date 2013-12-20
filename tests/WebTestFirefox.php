<?php
require_once 'PHPUnit/Extensions/Selenium2TestCase.php';

class WebTestFirefox extends PHPUnit_Extensions_Selenium2TestCase
{

	protected function setUp()
	{
		$this->setBrowser('*firefox');
		$this->setBrowserUrl('http://www.example.com/');
	}

	
	protected function close()
	{
		$this->close;
	}
	
	
	public function testTitle()
	{
		$this->setBrowser('*firefox');
		$this->open('http://www.example.com/');
		$this->assertTitle('Example Web Page');
	}
}
?>