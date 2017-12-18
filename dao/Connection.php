<?php
    class Connection {
        private static $host="localhost";
        private static $user="root"; 
        private static $password="root";
        private static $databaseName="market";
        private static $charset="utf8";
        private static $connection;
        
        /**
         * one point for connect to 
         * @return type
         */
        public static function getConnection (){
            if(Connection::$connection == null){
                Connection::$connection=mysql_connect(Connection::$host, Connection::$user, Connection::$password) or die("could not connect");
                mysql_set_charset(Connection::$charset);
                mysql_select_db(Connection::$databaseName);
            }
            return Connection::$connection;
        }
        
    }

?>
