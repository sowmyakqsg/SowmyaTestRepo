<?php
require_once "PHPUnit/Autoload.php";
require_once "User.php";

class UserTest extends PHPUnit_Framework_TestCase
{
	protected $user;

	// make an instance of the user
	 

	protected function setUp() {
		$user = new User();
		$this->user = new User();
		$this->user->setName("Tom");
	}
	protected function tearDown() {
		unset($this->user);
	}
	public function testTalk() {
		$expected = "Hello world";
		$actual = $this->user->talk();
		$this->assertEquals($expected, $actual);
	}


}