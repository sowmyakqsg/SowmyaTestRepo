<?php
require_once 'PHPUnit/Extensions/Selenium2TestCase.php';
require_once 'PHPDemo/page-object-pattern/pages/LoginPage.php';

class Login extends PHPUnit_Extensions_Selenium2TestCase{
	protected $_session;
	
	protected function setUp(){
		$this->_session->setBrowserUrl('http://www.google.com/');
	}

	public function testTitle(){
		$this->_session->setBrowserUrl('http://www.google.com/');
	}
}
?>