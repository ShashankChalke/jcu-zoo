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
class Cage{
    private $cageId;
    private $cageName;
    private $exhibitName;
    private $exhibitDesc;
    private $latitude;
    private $longitude;
    private $hasAnimal;
    private $hasHuman;
    function __construct($cageId, $cageName, $exhibitName, $exhibitDesc, $latitude, $longitude, $hasAnimal, $hasHuman) {
        $this->cageId = $cageId;
        $this->cageName = $cageName;
        $this->exhibitName = $exhibitName;
        $this->exhibitDesc = $exhibitDesc;
        $this->latitude = $latitude;
        $this->longitude = $longitude;
        $this->hasAnimal = $hasAnimal;
        $this->hasHuman = $hasHuman;
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
}
?>
