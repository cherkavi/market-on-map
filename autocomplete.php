<?php

require_once 'dao/CommodityFinder.php';
require_once 'dao/JsonHelper.php';

// id=term
$requestValue = $_GET['term'];
if (strlen($requestValue) < 4) {
    // return empty 
    JsonHelper::printAsJsonArray(null);
} else {
    // set return type for write to browser 
    addCharsetToHeader();
    /**  finder */
    $finder = new CommodityFinder;

    $resultArray = $finder->getVariants($requestValue);

    JsonHelper::printAsJsonArray($resultArray);
}


/**
 * set Header for HTML browser as text of UTF8 format
 */
function addCharsetToHeader() {
    header('Content-Type: text/html; charset=UTF8');
}

?>
