<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of MySQLConnection
 *
 * @author Panda
 */
class MySQLConnection {
   private $dbh;
   protected $user = "test";
   protected $pass = "test";
   protected $dbName = "jcu-zoo";
   public function __construct(){
       try{
           $this->dbh = new PDO('mysql:host=localhost;dbname='.$this->dbName, $this->user, $this->pass); 
       } catch (Exception $e){
            echo("Error connecting to database");
            die($e);
}
   }
   public function getConnection(){
       return $this->dbh;
   }

}

?>
