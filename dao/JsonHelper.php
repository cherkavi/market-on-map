<?php

class JsonHelper {

    /**
     * @assert (null) == "[]"
     * print array as JSON Array to echo 
     * @param type $resultArray - array of elements (simple array)
     */
    public static function printAsJsonArray($resultArray) {
        echo('[');
        if ($resultArray == null) {
            
        } else {
            $flagMoreThanOne = FALSE;
            foreach ($resultArray as $eachResultValue) {
                if ($flagMoreThanOne) {
                    echo(',');
                } else {
                    $flagMoreThanOne = TRUE;
                }
                echo("{ \"value\":\"$eachResultValue\"}");
            }
        }
        echo(']');
    }

}

?>
