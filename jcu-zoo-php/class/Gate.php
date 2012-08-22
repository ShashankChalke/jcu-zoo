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
}

?>
