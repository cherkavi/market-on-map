<?php

/**
 * Generated by PHPUnit_SkeletonGenerator 1.2.0 on 2013-05-08 at 07:16:24.
 */
class JsonHelperTest extends PHPUnit_Framework_TestCase {

    /**
     * @var JsonHelper
     */
    protected $object;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp() {
        $this->object = new JsonHelper;
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown() {
        
    }

    /**
     * Generated from @assert (null) == "[]".
     *
     * @covers JsonHelper::printAsJsonArray
     */
    public function testPrintAsJsonArray() {
        $this->assertEquals(
                "[]", JsonHelper::printAsJsonArray(null)
        );
    }

}
