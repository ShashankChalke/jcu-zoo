<!DOCTYPE html>
<?php

require_once('class/AdminDAO.php');
require_once('class/Cage.php');
class AdminUI{
    private $admin;
    
    public function __construct(){
        $this->admin = new AdminDAO();
        
    }
    function listCages(){
        echo "<div><h1>Cage Listings</h1>";
        echo "<form method='POST' action='admin.php'>";
        echo "<label for='numOfCages'>Enter desired number of cages:</label><input type='textbox' name='numOfCages' value='". $this->admin->getNumOfCages() . "' />";
	echo "<input type='submit' name='action' value='set' /></form>";
        if ($this->admin->getNumOfCages() == 0){
            echo "<p>No cages yet available</p>";
        } else { 
            echo "<table><tr><td>Cage Id</td><td>Cage Name</td><td>Cage Type</td><td>Latitude</td><td>Longitude</td><td>Number of gates</td><td>Exhibit name</td><td>Exhibit Description</td><td>Controls</td></tr>";
            foreach ($this->admin->getCages() as $cage){
                echo "<tr>";
                echo "<td>".$cage->getCageId()."</td>";
                echo "<td>".$cage->getCageName()."</td>";
                echo "<td>".$cage->getCageType()."</td>";
                echo "<td>".$cage->getLatitude()."</td>";
                echo "<td>".$cage->getLongitude()."</td>";
                echo "<td>".$cage->getNumOfGates()."</td>";
                echo "<td>".$cage->getExhibitName()."</td>";
                echo "<td>".$cage->getExhibitDesc()."</td>";
                echo "<td><form method='POST' action='admin.php'>".
                    "<input type='hidden' name='cageId' value='" . $cage->getCageId() . "' />" .
                     "<input name='action' type='submit' value='modify' />" .
                    "<input name='action' type='submit' value='delete' />" .
                    "</form></td>";
                echo("</tr>");
            }
            echo("</table>");
        }
        echo "</div>";
    }
    function save(Cage $cage){
        
        $cage->setCageName($_POST["cageName"]);
        $cage->setLongitude($_POST["longitude"]);
        $cage->setLatitude($_POST["latitude"]);
        $cage->setExhibitName($_POST["exhibitName"]);
        $cage->setExhibitDesc($_POST["exhibitDesc"]);
        $cage->setCageType($_POST["cageType"]);
        $this->admin->updateCage($cage);                
    }
    function delete(Cage $cage){
        $this->admin->removeCage($cage);
    }
    function showCageEditor(){
        $cage = $this->admin->getCage($_POST['cageId']);
        if (isset($_POST['numGates'])){
            if ($_POST['numGates'] == "+1"){
                $cage->addGate();
            } elseif ($_POST['numGates'] == "-1"){
                $cage->removeGate();
            }
        }
        echo "<div><h1>Modify cage</h1>" .
                "<p>Number of gates: " . $cage->getNumOfGates() . "</p>".
                "<form action='admin.php' method='POST'>" .
                "<input type='hidden' name='cageId' value='" . $cage->getCageId() . "' />".
                "<input type='hidden' name='action' value='modify' />" .
                "<input type='submit' name='numGates' value='+1' />" . 
                "</form>" .
                "<form action='admin.php' method='POST'>" .
                "<input type='hidden' name='cageId' value='" . $cage->getCageId() . "' />" .
                "<input type='hidden' name='action' value='modify' />" .
                "<input type='submit' name='numGates' value='-1' />" .
                "</form>" . 
                "<form action='admin.php' method='POST'>" .
                "<input type='hidden' name='cageId' value='" . $cage->getCageId() . "' />" .
                "<label for='cageName'>Cage name: </label><input type='textbox' name='cageName' value='" . $cage->getCageName() . "' />" .
                "<label for='cageType'>Cage type: </label><input type='textbox' name='cageType' value='" . $cage->getCageType() . "' />" .
                "<label for='latitude'>Latitude: </label><input type='textbox' name='latitude' value='" . $cage->getLatitude() . "' />" .
                "<label for='longitude'>Longitude: </label><input type='textbox' name='longitude' value='" . $cage->getLongitude() . "' />" .
                "<label for='exhibitName'>Exhibit Name: </label><input type='textbox' name='exhibitName' value='" . $cage->getExhibitName() . "' />" .
                "<label for='exhibitDesc'>Exhibit Description: </label><textarea name='exhibitDesc'>" . $cage->getExhibitDesc() . "</textarea>" .
                "<input type='submit' name='action' value='save' /><a href='admin.php'>Go Back</a></form></div>";
    }
}




?>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        
        <?php 
        //echo(print_r($admin->getCages()));
        //echo(print_r($admin->getCages()));
        //echo("<br/>");
        //echo(print_r($admin->getCage(37)));
         $admin = new AdminDAO();
        $adminUI = new AdminUI();
        if (isset($_POST['action'])){
            if ($_POST['action'] == "modify"){
                $adminUI->showCageEditor();
            } elseif ($_POST["action"] == "save") {
                $adminUI->save($admin->getCage($_POST["cageId"]));
            } elseif ($_POST["action"] == "delete"){
                $adminUI->delete($admin->getCage($_POST["cageId"]));
            } elseif ($_POST["action"] == "set"){
                Cage::setNumOfCages($_POST["numOfCages"]);
            }
        }

          $adminUI->listCages();
             
        ?>
    </body>
</html>
