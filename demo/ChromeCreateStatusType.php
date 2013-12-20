<?php
require_once 'PHPUnit/Extensions/Selenium2TestCase.php';

class ChromeCreateStatusType extends PHPUnit_Extensions_Selenium2TestCase{
	
	protected function setUp(){
		$this->setBrowser('chrome');
		$this->setBrowserUrl('https://emresource.qa.intermedix.com/');
	}

	public function testTitle(){

		$this->url('/login.jsp');
		$this->currentWindow()->maximize();
		$this->timeouts()->implicitWait(30000);
		$this->assertEquals('EMResource ~ Login',$this->title());

		$username = $this->byName('loginName');
		$password = $this->byName('password');
		$loginBtn = $this->byName('LoginBtn');

		//Login
		$username->value('qsg');
		$password->value('abc123');

		//Assertin values in login fields
		$this->assertEquals('qsg',$username->value());
		$this->assertEquals('abc123',$password->value());
		$loginBtn->click();

		//Assertin page title
		$this->assertEquals('Select Region',$this->title());

		//selecting region
		PHPUnit_Extensions_Selenium2TestCase_Element_Select::fromElement($this->byId('regionID'))->selectOptionByLabel('Z_Smoke_Build_7655');
		$this->byCssSelector("input[value='Next']")->click();
		$this->frame('Data');
		$this->byCssSelector("div#topNav>div>a")->click();
		$this->byLinkText('Status Types')->click();

		$this->assertEquals('Status Type List',$this->byCssSelector('h1')->text());
		$this->byCssSelector("input[value='Create New Status Type']")->click();
		$this->assertEquals('Select Status Type',$this->byCssSelector('h1')->text());

		//selecting region
		PHPUnit_Extensions_Selenium2TestCase_Element_Select::fromElement($this->byName('statusValueCode'))->selectOptionByLabel('Number');
		$this->byCssSelector("input[value='Next']")->click();
		$this->assertEquals('Create Number Status Type',$this->byCssSelector('h1')->text());

		//Enter mandatory fields
		$statusTypeName = 'Auto'.date('Ymdhi').date('s')*1000;
		print $statusTypeName;
		$this->byName('statusTypeName')->value($statusTypeName);
		$this->byName('definition')->value('Automation');
		$this->byCssSelector("input[value='Save']")->click();
		$this->assertEquals('Status Type List',$this->byCssSelector('h1')->text());

		$this->byCssSelector("div#topNav>div>a")->click();
		$this->byLinkText('Users')->click();
		$this->assertEquals('Users List',$this->byCssSelector('h1')->text());
		$this->byCssSelector("input[value='Create New User']")->click();
		$this->assertEquals('Create New User',$this->byCssSelector('h1')->text());

		//Enter mandatory fields
		$userName = 'User'.date('Ymdhi').date('s')*1000;
		$this->byName('userID')->value($userName);
		$this->byName('newPass')->value($userName);
		$this->byName('confirmPass')->value($userName);
		$this->byName('fullName')->value($userName);
		$this->byCssSelector("input[value='Save']")->click();
		$this->assertEquals('Users List',$this->byCssSelector('h1')->text());
	}

	protected function tearDown(){
		$this->closeWindow();
	}
}
?>