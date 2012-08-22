
<?php
include('class/CageDAO.php');
class CageUI{
    static $cageDAO;
    static $test;
    function __construct(){
        if (!isset(self::$cageDAO)){
            self::$cageDAO = new CageDAO();
        }
    }
    
    function listCages(){
        echo "<div><h1>Cage Listings</h1>";
        if (self::$cageDAO->getNumOfCages() == 0){
            echo "<p>No cages yet available</p>";
        } else {
            echo "<table><tr><td>Cage Id</td><td>Cage Name</td><td>Cage Type</td><td>Number of gates</td><td>Exhibit name</td><td>Exhibit Description</td><td>Animal Inside</td><td>Human Inside</td><td>Controls</td></tr>";
            foreach (self::$cageDAO->getCages() as $cage){
                echo "<tr>";
                echo "<td>" . $cage->getCageId() . "</td>";
                echo "<td>" . $cage->getCageName() . "</td>";
                echo "<td>" . $cage->getCageType() . "</td>";
                echo "<td>" . $cage->getNumOfGates() . "</td>";
                echo "<td>" . $cage->getExhibitName() . "</td>";
                echo "<td>" . $cage->getExhibitDesc() . "</td>";
                echo "<td>" . $this->boolToString($cage->hasAnimal()) . "</td>";
                echo "<td>" . $this->boolToString($cage->hasHuman()) . "</td>";
                echo "<td><form method='POST' action='cage.php'>";
                echo "<input type='hidden' name='cageId' value='" . $cage->getCageId() . "' />";
                echo "<input name='action' type='submit' value='sensors' />";
                echo "<input name='action' type='submit' value='gates' /></form><td></tr>";
                
            }
            echo "</table>";
        }
        
    }
    function boolToString($bool){
        if ($bool){
		return "Yes";
	} else {
		return "No";
	}
    }
    function showSensorControls(){
        $cage = self::$cageDAO->getCage($_POST['cageId']);
        $humanCheck = "";
        $animalCheck = "";
        if ($cage->hasHuman()){
            $humanCheck = "checked";
        }
        if ($cage->hasAnimal()){
            $animalCheck = "checked";
        }
        echo "<div><form method='POST' action='cage.php'>";
        echo "<input type='hidden' name='cageId' value='" . $cage->getCageId() . "' />";
        echo "<label for='hasHuman'>Humans inside</label><input type='checkbox' name='hasHuman' " . $humanCheck . " />";
        echo "<label for='hasAnimal'>Animal inside</label><input type='checkbox' name='hasAnimal' " . $animalCheck . " />";
        echo "<input type='hidden' name='updateType' value='sensors' />";
        echo "<input type='submit' name='action' value='update' /></form></div>";
    }
    function updateSensors(){
        $cage = self::$cageDAO->getCage($_POST['cageId']);
        $cage->setAnimal(false);
        $cage->setHuman(false);
        if(isset($_POST["hasAnimal"])){
            $cage->setAnimal(true);
        }
        if(isset($_POST["hasHuman"])){
            $cage->setHuman(true);
        }
        self::$cageDAO->updateCage($cage);
    }
    function updateGate(){
        $gate = self::$cageDAO->getGate($_POST["gateId"]);
        if ($_POST["gateAction"] == "open"){
            $gate->open();
        } elseif ($_POST["gateAction"] == "close"){
            $gate->close();
        } elseif ($_POST["gateAction"] == "lock"){
            $gate->lock();
        } elseif ($_POST["gateAction"] == "unlock"){
            $gate->unlock();
        }
        self::$cageDAO->updateGate($gate);
        $this->showGates();
        
    }
    function showGates(){
        $cage = self::$cageDAO->getCage($_POST['cageId']);
        $gates = self::$cageDAO->getGates($cage);
        if (!isset($gates)){
            echo "<p>No gates found!</p>";
        } else {
            foreach ($gates as $gate){
                echo "<p>" . $gate->toString() . "</p>";
                echo "<form action='cage.php' method='POST'>";
                echo "<input type='hidden' name='gateId' value='" . $gate->getGateId() . "' />";
                echo "<input type='hidden' name='cageId' value='" . $cage->getCageId() . "' />";
                echo "<input type='hidden' name='action' value='update' />";
                echo "<input type='hidden' name='updateType' value='gates' />";
		if ($gate->isClosed()){
			echo "<input type='submit' name='gateAction' value='open' />";
		} else {
			echo "<input type='submit' name='gateAction' value='close' />";
		}
		if ($gate->isLocked()){
			echo "<input type='submit' name='gateAction' value='unlock' />";
		} else {
			echo "<input type='submit' name='gateAction' value='lock' />";
		}
		echo "</form>"; 
            }
            echo "<p><a href='cage.php'>Close</a></p>";
        }
    }
    
}
$cageUI = new CageUI();
if (isset($_POST["action"])){
    if ($_POST["action"] == "sensors"){
        $cageUI->showSensorControls();
    } elseif ($_POST["action"] == "gates"){
        $cageUI->showGates($cage);
    } elseif ($_POST["action"] == "update"){
        if ($_POST["updateType"] == "sensors"){
            $cageUI->updateSensors();
        } elseif ($_POST["updateType"] == "gates"){
            $cageUI->updateGate();
            
        }
    }
}

$cageUI->listCages();
?>
