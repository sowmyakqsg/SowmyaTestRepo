<?php
require_once 'C:/workspace/PHPDemo/page-object-pattern/pages/SelectRegionPage.php';

class LoginPage extends PHPUnit_Framework_TestCase
{
	private static  $username= "loginName";
	private static  $password= "password";
	private static  $login= "LoginBtn";

	public function __construct($test)
	{
		$this->usernameInput = $test->byName(self::$username);
		$this->passwordInput = $test->byName(self::$password);
		$this->loginBtn = $test->byName(self::$login);
		$this->test = $test;
	}

	public function assertLoginPageDisplayed()
	{
		$this->assertEquals('EMResource ~ Login',$this->test->title());
		return $this;
	}

	public function username($value)
	{
		$this->usernameInput->value($value);
		return $this;
	}

	public function password($value)
	{
		$this->passwordInput->value($value);
		return $this;
	}

	public function submit()
	{
		$this->loginBtn->click();
		return new SelectRegionPage($this->test);
	}
}