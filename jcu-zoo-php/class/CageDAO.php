<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of CageDAO
 *
 * @author Panda
 */
include_once('class/MySQLConnection.php'); 
include_once('class/Cage.php');
class CageDAO {
    private static $conn;

    public function __construct(){
        $dbh = new MySQLConnection();
        if (!isset(self::$conn)){
            self::$conn = $dbh->getConnection();        
        }
    }
    function getGates($cage){
        $gates;
        $query = "SELECT GATE.GATE_ID, GATE.IS_CLOSED, GATE.IS_LOCKED FROM GATE,CAGE_GATES WHERE CAGE_GATES.CAGE_ID=:cageId AND GATE.GATE_ID=CAGE_GATES.GATE_ID";
        $statement = self::$conn->prepare($query);
        $statement->bindParam(':cageId',$cage->getCageId(),SQL_INTEGER);
        $statement->execute();
        foreach ($statement->fetchAll() as $gate){
            $gates[] = new Gate($gate["GATE_ID"], $gate["IS_CLOSED"], $gate["IS_LOCKED"]);
        }
        return $gates;
    }
    function updateGate($gate){
        // update gate
        $query = "UPDATE GATE SET IS_CLOSED=:isClosed, IS_LOCKED=:isLocked WHERE GATE_ID=:gateId";
        $statement = self::$conn->prepare($query);
        $statement->bindParam(':isClosed',$gate->isClosed(),SQL_TINYINT);
        $statement->bindParam(':isLocked',$gate->isLocked(),SQL_TINYINT);
        $statement->bindParam(':gateId',$gate->getGateId(),SQL_INTEGER);
        $statement->execute();
    }
    function getGate($gateId){
        //get gate
        $query = "SELECT * FROM GATE WHERE GATE_ID=:gateId";
        $statement = self::$conn->prepare($query);
        $statement->bindParam(':gateId',$gateId,SQL_INTEGER);
        $statement->execute();
        $gate = $statement->fetch();
        return new Gate($gate["GATE_ID"], $gate["IS_CLOSED"], $gate["IS_LOCKED"]);
        
    }
        function getCages(){
            $cages;
            $query = "SELECT * FROM CAGE";
            $statement = self::$conn->prepare($query);
            $statement->execute();
            $result = $statement->fetchAll();
             $statement = null;
             foreach ($result as $cage){
                 $cages[] = new Cage($cage['CAGE_ID'],$cage['CAGE_NAME'],$cage['EXHIBIT_NAME'],$cage['EXHIBIT_DESCRIPTION'],$cage['LATITUDE'],$cage['LONGITUDE'],$cage['HAS_ANIMAL'],$cage['HAS_HUMAN'],$cage["CAGE_TYPE"]);
             }
             return $cages;
    }
    function getCage($cageId) {
        $cage;
        $query = "SELECT * FROM CAGE WHERE CAGE_ID=:cageId";
        $statement = self::$conn->prepare($query);
        $statement->bindParam(':cageId',$cageId,SQL_INTEGER);
        $statement->execute();
        $cage = $statement->fetch();
        $statement = null;
        return new Cage($cage['CAGE_ID'],$cage['CAGE_NAME'],$cage['EXHIBIT_NAME'],$cage['EXHIBIT_DESCRIPTION'],$cage['LATITUDE'],$cage['LONGITUDE'],$cage['HAS_ANIMAL'],$cage['HAS_HUMAN'],$cage["CAGE_TYPE"]);
    }
        function getNumOfCages(){
        $query = "SELECT COUNT(*) FROM CAGE";
        $statement = self::$conn->prepare($query);
        $statement->execute();
        $result = $statement->fetch();
        $statement = null;

        return $result[0];
    }
        function updateCage(Cage $cage){
        $query = "UPDATE CAGE SET CAGE_NAME=:cageName, LATITUDE=:latitude, LONGITUDE=:longitude, CAGE_TYPE=:cageType, HAS_HUMAN=:hasHuman, HAS_ANIMAL=:hasAnimal, EXHIBIT_NAME=:exhibitName, EXHIBIT_DESCRIPTION=:exhibitDesc WHERE CAGE_ID=:cageId";
        $statement = self::$conn->prepare($query);
        $statement->bindParam(':cageName',$cage->getCageName());
        $statement->bindParam(':latitude',$cage->getLatitude());
        $statement->bindParam(':longitude',$cage->getLongitude());
        $statement->bindParam(':cageType',$cage->getCageType());
        $statement->bindParam(':hasHuman',$cage->hasHuman());
        $statement->bindParam(':hasAnimal',$cage->hasAnimal());
        $statement->bindParam(':exhibitName',$cage->getExhibitName());
        $statement->bindParam(':exhibitDesc',$cage->getExhibitDesc());
        $statement->bindParam(':cageId',$cage->getCageId());
        $statement->execute();
        $statement = null;
    }
}

?>
