<?php

require_once 'PHPUnit/Extensions/Selenium2TestCase.php';

class Extensions_Selenium2TestCaseTest extends PHPUnit_Extensions_Selenium2TestCase
{
	protected $captureScreenshotOnFailure = TRUE;
	protected $screenshotPath = '/var/www/localhost/htdocs/screenshots';
	protected $screenshotUrl = 'http://localhost/screenshots';
	
	protected function setUp()
	{
		$this->setBrowser('firefox');
		$this->setBrowserUrl('http://www.google.com/');
	}

	public function testTitle()
	{
		$this->url('http://www.google.com/');
		$this->assertEquals('Example WWW Page', $this->title());
		
	}
}
?>