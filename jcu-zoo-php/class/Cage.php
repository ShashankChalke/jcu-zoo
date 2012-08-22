<?php
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Cage
 *
 * @author Panda
 */
include_once("AdminDAO.php");
class Cage{
    public static $adminDAO;
    private $cageId;
    private $cageName;
    private $exhibitName;
    private $exhibitDesc;
    private $latitude;
    private $longitude;
    private $hasAnimal;
    private $hasHuman;
    private $cageType;
    function __construct($cageId, $cageName, $exhibitName, $exhibitDesc, $latitude, $longitude, $hasAnimal, $hasHuman, $cageType) {
        $this->cageId = $cageId;
        $this->cageName = $cageName;
        $this->exhibitName = $exhibitName;
        $this->exhibitDesc = $exhibitDesc;
        $this->latitude = $latitude;
        $this->longitude = $longitude;
        $this->hasAnimal = $hasAnimal;
        $this->hasHuman = $hasHuman;
        $this->cageType = $cageType;
        Cage::loadDefaultMemberFields();
    }
    static function loadDefaultMemberFields(){
         if (!isset(Cage::$adminDAO)){
            Cage::$adminDAO = new AdminDAO();

        }
    }
    
    

    public function getCageType(){
        return $this->cageType;
        
    }
    public function setCageType($cageType) {
        $this->cageType = $cageType;
    }

        /*public static function getCage($cageId){
        return Cage::$adminDAO->getCage($cageId);
    }
    public static function updateCage(Cage $cage){
        Cage::$adminDAO->updateCage($cage);
    }*/
    public static function setNumOfCages($numCages){
        Cage::loadDefaultMemberFields();
        
        if ($numCages > 0 && $numCages <= 100){
            $currentNumOfCages = Cage::$adminDAO->getNumOfCages();
            $diff = $numCages - $currentNumOfCages;
            if ($diff > 0){
                // add cages
                Cage::$adminDAO->addCages($diff);
            } elseif ($diff < 0){
                Cage::$adminDAO->removeCages(0-$diff);
            } else {
                //no change in cage difference 
            }
        } else {
            //invalid input parameter
        }
    }
    public function getNumOfGates(){
        Cage::loadDefaultMemberFields();
        return Cage::$adminDAO->getNumOfGates($this->getCageId());
    }
    public function getCageId() {
        return $this->cageId;
    }

    public function setCageId($cageId) {
        $this->cageId = $cageId;
    }

    public function getCageName() {
        return $this->cageName;
    }

    public function setCageName($cageName) {
        $this->cageName = $cageName;
    }

    public function getExhibitName() {
        return $this->exhibitName;
    }

    public function setExhibitName($exhibitName) {
        $this->exhibitName = $exhibitName;
    }

    public function getExhibitDesc() {
        return $this->exhibitDesc;
    }

    public function setExhibitDesc($exhibitDesc) {
        $this->exhibitDesc = $exhibitDesc;
    }

    public function getLatitude() {
        return $this->latitude;
    }

    public function setLatitude($latitude) {
        $this->latitude = $latitude;
    }

    public function getLongitude() {
        return $this->longitude;
    }

    public function setLongitude($longitude) {
        $this->longitude = $longitude;
    }

    public function hasAnimal() {
        return $this->hasAnimal;
    }

    public function setAnimal($hasAnimal) {
        $this->hasAnimal = $hasAnimal;
    }

    public function hasHuman() {
        return $this->hasHuman;
    }

    public function setHuman($hasHuman) {
        $this->hasHuman = $hasHuman;
    }
    public function addGate(){
        Cage::$adminDAO->addGateToCage($this);
    }
    public function removeGate(){
        Cage::$adminDAO->removeGate($this);
    }
}
?>
