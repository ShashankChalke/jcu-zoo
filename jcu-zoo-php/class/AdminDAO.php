<?php 
error_reporting(E_ALL ^ E_NOTICE);
include_once("MySQLConnection.php");
include_once("Cage.php");
include_once("Gate.php");
interface IAdminDAO{
    public function getCages();
    public function getCage($cageId);
}
class AdminDAO implements IAdminDAO{
    private static $conn;
    public function __construct(){
            $dbh = new MySQLConnection();
            if (!isset(AdminDAO::$conn)){
                AdminDAO::$conn = $dbh->getConnection();        
            }
    }
    function getNumOfCages(){
        $query = "SELECT COUNT(*) FROM CAGE";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->execute();
        $result = $statement->fetch();
        $statement = null;
        return $result[0];
    }
    function getNumOfGates($cageId){
        $query = "SELECT COUNT(*) FROM GATE, CAGE_GATES WHERE GATE.GATE_ID=CAGE_GATES.GATE_ID AND CAGE_GATES.CAGE_ID=:cageId";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':cageId',$cageId);
        $statement->execute();
        $result = $statement->fetch();
      
        return $result[0];
    }
    function getCages(){
            $cages;
            $query = "SELECT * FROM CAGE";
            $statement = AdminDAO::$conn->prepare($query);
            $statement->execute();
            $result = $statement->fetchAll();
             $statement = null;
             foreach ($result as $cage){
                 //echo($cage . "\n");
                 //echo(print_r($cage['CAGE_ID']));
                 $cages[] = new Cage($cage['CAGE_ID'],$cage['CAGE_NAME'],$cage['EXHIBIT_NAME'],$cage['EXHIBIT_DESCRIPTION'],$cage['LATITUDE'],$cage['LONGITUDE'],$cage['HAS_ANIMAL'],$cage['HAS_HUMAN'],$cage["CAGE_TYPE"]);
                 //$cages[] = new Cage($cag)
                 //($cageId, $cageName, $exhibitName, $exhibitDesc, $latitude, $longitude, $hasAnimal, $hasHuman)
             }
             //$listOfCages
             //make it so a list of cages are generated and returned
             return $cages;
    }
    function getCage($cageId) {
        $cage;
        $query = "SELECT * FROM CAGE WHERE CAGE_ID=:cageId";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':cageId',$cageId);
        $statement->execute();
        $cage = $statement->fetch();
        $statement = null;
        return new Cage($cage['CAGE_ID'],$cage['CAGE_NAME'],$cage['EXHIBIT_NAME'],$cage['EXHIBIT_DESCRIPTION'],$cage['LATITUDE'],$cage['LONGITUDE'],$cage['HAS_ANIMAL'],$cage['HAS_HUMAN'],$cage["CAGE_TYPE"]);
    }
    function removeCages($numOfCages){
        $query = "DELETE FROM CAGE ORDER BY CAGE_ID DESC LIMIT :numOfCages";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':numOfCages',$numOfCages,SQL_INTEGER);
        $statement->execute();
        $statement = null;
    }
    function removeCage(Cage $cage){
        $query = "DELETE FROM CAGE WHERE CAGE_ID=:cageId";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':cageId',$cage->getCageId());
        $statement->execute();
        $statement = null;
    }
    function addCages($numOfCages){
        $i = $numOfCages;
        while ($i > 0){
            $query = "INSERT INTO CAGE(HAS_HUMAN,HAS_ANIMAL) VALUES (False,False)";
            $statement = AdminDAO::$conn->prepare($query);
            $statement->execute();
            $statement = null;
            $i = $i - 1;
        }
    }
    function updateCage(Cage $cage){
        $query = "UPDATE CAGE SET CAGE_NAME=:cageName, LATITUDE=:latitude, LONGITUDE=:longitude, CAGE_TYPE=:cageType, HAS_HUMAN=:hasHuman, HAS_ANIMAL=:hasAnimal, EXHIBIT_NAME=:exhibitName, EXHIBIT_DESCRIPTION=:exhibitDesc WHERE CAGE_ID=:cageId";
        $statement = AdminDAO::$conn->prepare($query);
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
    
    function createGate(){
        $query = "INSERT INTO GATE() VALUES ()";
        $conn =& AdminDAO::$conn;
        //AdminDAO::$conn->beginTransaction();
        $conn->beginTransaction();
        //$statement = AdminDAO::$conn->prepare($query);
        $statement = $conn->prepare($query);
        $statement->execute();
        $gateId = $conn->lastInsertId();
        //echo "last insert id = " . $gateId;
        //AdminDAO::$conn->commit();
        $conn->commit();
        $statement = null;
       return $this->getGate($gateId);
        
    }
    function getGate($gateId){
        $query = "SELECT * FROM GATE WHERE GATE_ID=:gateId";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':gateId',$gateId);
        $statement->execute();
        $result = $statement->fetch();
        $statement = null;
        return new Gate($result["GATE_ID"],$result["IS_CLOSED"],$result["IS_LOCKED"]);
        // turn into gate and return!
    }
    function removeGate($cage){
        $query = "DELETE FROM CAGE_GATES WHERE CAGE_ID=:cageId LIMIT 1";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':cageId',$cage->getCageId(),SQL_INTEGER);
        $statement->execute();
        $this->removeUnusedGates();
    }
    function removeUnusedGates(){
        $query = "DELETE FROM GATE WHERE GATE_ID NOT IN (SELECT GATE_ID FROM CAGE_GATES)";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->execute();
    }
    function addGateToCage($cage){
        $gate = $this->createGate();
        $query = "INSERT INTO CAGE_GATES(CAGE_ID,GATE_ID) VALUES (:cageId,:gateId)";
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':cageId',$cage->getCageId(),SQL_INTEGER);
        $statement->bindParam(':gateId',$gate->getGateId(),SQL_INTEGER);
        $statement->execute();
    }
    /*
    function getNumOfGates($cage){
        $query = "SELECT COUNT(*) FROM GATE, CAGE_GATES WHERE GATE.GATE_ID=CAGE_GATES.GATE_ID AND CAGE_GATES.CAGE_ID=:cageId";   
        $statement = AdminDAO::$conn->prepare($query);
        $statement->bindParam(':cageId',$cage->getCageId(),SQL_INTEGER);
        $statement->execute();
        $result = $statement->fetch();
        return $result[0];
    }
*/
    //function addGateToCage
   
    //removeGate
    //removeUnusedGate
    
}
?>