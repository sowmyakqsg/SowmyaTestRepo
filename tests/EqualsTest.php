<?php

class EqualsTest extends PHPUnit_Framework_TestCase{

	public function testFailure()
	{
		$this->assertEquals(1, 0);
	}
	public function testFailure1()
	{
		$count = 0;
		while($this->assertFalse("EMResource ~ Login"=="EMResource ~ Login") == false && $count<20){
			print $count;
			$count ++;
		}
	}
}