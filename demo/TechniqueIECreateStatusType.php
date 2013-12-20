<?php
require_once 'PHPUnit/Extensions/Selenium2TestCase.php';

class IECreateStatusType extends PHPUnit_Extensions_Selenium2TestCase{

	protected function setUp(){
		$this->setDesiredCapabilities(array(
				'loggingPrefs' => array('driver' => 'WARNING'),
				'javascriptEnabled' => true,
				'databaseEnabled' => false)
		);
		$this->setBrowser('iexplore');
		$this->setBrowserUrl('https://emresource.qa.intermedix.com/');
	
	}

	public function testTitle(){

		$this->url('/login.jsp');
		$this->currentWindow()->maximize();
		$this->timeouts()->implicitWait(60000);

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

		//selecting region
		PHPUnit_Extensions_Selenium2TestCase_Element_Select::fromElement($this->byId('regionID'))->selectOptionByLabel('Z_Smoke_Build_7655');
		$this->byCssSelector("input[value='Next']")->click();
		$this->frame('Data');
		$this->byCssSelector("div#topNav>div>a")->click();
		$this->byLinkText('Status Types')->click();
		$this->byCssSelector("input[value='Create New Status Type']")->click();
		
		//selecting region
		PHPUnit_Extensions_Selenium2TestCase_Element_Select::fromElement($this->byName('statusValueCode'))->selectOptionByLabel('Number');
		$this->byCssSelector("input[value='Next']")->click();

		//Enter mandatory fields
		$statusTypeName = 'Auto'.date('Ymdhi').date('s')*1000;
		$this->byName('statusTypeName')->value($statusTypeName);
		$this->byName('definition')->value('Automation');
		$this->byCssSelector("input[value='Save']")->click();
	
		$this->byCssSelector("div#topNav>div>a")->click();
		$this->byLinkText('Users')->click();
		$this->byCssSelector("input[value='Create New User']")->click();
		

		//Enter mandatory fields
		$userName = 'User'.date('Ymdhi').date('s')*1000;
		$this->byName('userID')->value($userName);
		$this->byName('newPass')->value($userName);
		$this->byName('confirmPass')->value($userName);
		$this->byName('fullName')->value($userName);
		$this->byCssSelector("input[value='Save']")->click();
	}

	protected function tearDown(){
		$this->closeWindow();
	}
}
?>