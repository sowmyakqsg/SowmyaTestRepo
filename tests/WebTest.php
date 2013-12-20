<?php
require_once 'PHPUnit/Extensions/Selenium2TestCase.php';
require_once 'PHPDemo/pages/LoginPage.php';

class WebTest extends PHPUnit_Extensions_Selenium2TestCase{

	protected function setUp()
	{
		$this->setBrowser('firefox');
		$this->setBrowserUrl('https://emresource.qa.intermedix.com/');
	}


	protected function close()
	{
		$this->close;
		$this->quit;
	}

	public function testTitle()
	{
		$this->url('/login.jsp');
		$this->currentWindow()->maximize();
		$this->timeouts()->implicitWait(30000);
		$this->assertEquals('EMResource ~ Login',$this->title());

		$username = $this->byName('loginName');
		$password = $this->byName('password');
		$loginBtn = $this->byName('LoginBtn');

		$username->value('qsg');
		$password->value('abc123');
		$this->assertEquals('qsg',$username->value());
		$this->assertEquals('abc123',$password->value());
		$loginBtn->click();

		$this->assertEquals('Select Region',$this->title());
	}
}
?>