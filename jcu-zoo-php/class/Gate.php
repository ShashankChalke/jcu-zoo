<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Gate
 *
 * @author Panda
 */
class Gate {
    private $gateId;
    private $isLocked;
    private $isClosed;
    
    function __construct($gateId,$isClosed,$isLocked){
        $this->gateId = $gateId;
        $this->isClosed = $isClosed;
        $this->isLocked = $isLocked;
    }
    function getGateId(){
        return $this->gateId;
    }
    function open(){
        $this->isClosed = false;
    }
    function close(){
        $this->isClosed = true;
    }
    function lock(){
        $this->isLocked = true;
    }
    function unlock(){
        $this->isLocked = false;
    }
    function toString(){
        return $this->gateId . 
                ", isClosed: " . $this->isClosed() .
                ", isLocked: " . $this->isLocked();
    }
    function isClosed(){
        return $this->isClosed;
    }
    function isLocked(){
        return $this->isLocked;
    }
}

?>
