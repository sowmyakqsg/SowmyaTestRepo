<?php

class LoginPage extends PHPUnit_Extensions_Selenium2TestCase{
	
	public function launchURL(){
		$this->$session->url('/login.jsp');
		$this->$session->currentWindow()->maximize();
		$this->$session->timeouts()->implicitWait(60000);
		return $this->$session;
	}
}