<?php

require_once 'Point.php';
/**
 * Utility class for communication with Database 
 */
class DbHelper {

    /**
     * execute query over connection, retrieve all column by fieldName to array 
     * @param type $connection
     * @param type $query
     * @param type $fieldName
     * @return array - of elements from query 
     */
    public static function getArrayOfElementsFromQuery($connection, $query, $fieldName) {
        // result from database 
        $result = mysql_query($query, $connection);
        // returnValue 
        $returnValue = array();
        while ($row = mysql_fetch_array($result)) {
            array_push($returnValue, $row[$fieldName]);
        }
        // free resources 
        mysql_free_result($result);
        return $returnValue;
    }
    
    
    /**
     * print to output information about one point 
     * @param type $pointId
     * @return 
     */
    public static function getPointById($pointId){
        $connection=Connection::getConnection();
        $query="select * from points where id=$pointId";
        
        $result = mysql_query($query, $connection);
        if(mysql_num_rows($result)==1){
            $row = mysql_fetch_array($result);
            $point=new Point;
            $point->id=$row['id'];
            $point->pointnum=$row['pointnum'];
            $point->active=$row['active'];
            $point->pos_x=$row['pos_x'];
            $point->pos_y=$row['pos_y'];
            $point->html=$row['html'];
            $point->commodity=DbHelper::getArrayOfElementsFromQuery($connection, "SELECT commodity.name name FROM  point2commodity INNER JOIN commodity ON commodity.id = point2commodity.idcommodity WHERE idpoint=$pointId", "name");
            mysql_free_result($result);
            return $point;
        }else{
            mysql_free_result($result);
            return null;
        }
    }
    
}

?>
