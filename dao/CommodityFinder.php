<?php

    require_once 'dao/Connection.php';
    require_once 'dao/DbHelper.php';
    use DbHelper;
    
    class CommodityFinder{
        /**
         *  get all variant for commodity name 
         * @param type $partOfWord - word for search in database 
         * @return array with all elements 
         */
        public function getVariants($partOfWord){
            $connection=Connection::getConnection();
            $search_value=mysql_real_escape_string($partOfWord);
            return DbHelper::getArrayOfElementsFromQuery($connection, "SELECT name FROM commodity where lower(name) like lower('%$search_value%')", "name");
        }
        
        /**
         * link commodity ( certain value ) with points 
         * @param type $commodity
         * @return array of points number by find commodity
         */
        public function getPointsByCommodity($commodity){
            $connection=Connection::getConnection();
            $search_value=mysql_real_escape_string($commodity);
            $sqlQuery="select distinct idpoint from point2commodity where idcommodity in (SELECT id FROM commodity where lower(commodity.name) like lower('%$search_value%'))";

            return DbHelper::getArrayOfElementsFromQuery($connection, $sqlQuery, "idpoint");
        }
    }
    
?>
