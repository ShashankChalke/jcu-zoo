<?php 

include("class/MySQLConnection.php");
include("class/Cage.php");
interface IAdminDAO{
    public function getCages();
    public function getCage($cageId);
}
class AdminDAO implements IAdminDAO{
    private $conn;
    public function __construct(){
            $dbh = new MySQLConnection();
            $this->conn = $dbh->getConnection();        
    }
    function getCages(){
            $cages;
            $query = "SELECT * FROM CAGE";
            $statement = $this->conn->prepare($query);
            $statement->execute();
            $result = $statement->fetchAll();
             $statement = null;
             foreach ($result as $cage){
                 //echo($cage . "\n");
                 //echo(print_r($cage['CAGE_ID']));
                 $cages[] = new Cage($cage['CAGE_ID'],$cage['CAGE_NAME'],$cage['EXHIBIT_NAME'],$cage['EXHIBIT_DESCRIPTION'],$cage['LATITUDE'],$cage['LONGITUDE'],$cage['HAS_ANIMAL'],$cage['HAS_HUMAN']);
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
        $statement = $this->conn->prepare($query);
        $statement->bindParam(':cageId',$cageId);
        $statement->execute();
        $cage = $statement->fetch();
        $statement = null;
        return new Cage($cage['CAGE_ID'],$cage['CAGE_NAME'],$cage['EXHIBIT_NAME'],$cage['EXHIBIT_DESCRIPTION'],$cage['LATITUDE'],$cage['LONGITUDE'],$cage['HAS_ANIMAL'],$cage['HAS_HUMAN']);
    }

}?>