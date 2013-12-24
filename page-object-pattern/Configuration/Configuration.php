<?php
class Configuration extends PHPUnit_Extensions_Selenium2TestCase
{
	/*public static $browsers = [
	 ['browserName' => 'chrome'],
	['browserName' => 'firefox'],
	['browserName' => 'iexplore']
	];

	public function __construct()
	{
	$this->setHost('localhost');
	$this->setPort(4444);
	$this->setSeleniumServerRequestsTimeout(60);
	$this->setDesiredCapabilities([array('javascriptEnabled' => true)]);
	} */
	
	protected function setUp(){
		$this->setBrowser('chrome');
		$this->setBrowserUrl('https://emresource.qa.intermedix.com/');
	}
}