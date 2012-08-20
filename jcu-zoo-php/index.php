<!DOCTYPE html>
<?php include('class/AdminDAO.php');
$admin = new AdminDAO();
class AdminUI{
    private $admin;
    
    public function __construct(){
        $this->admin = new AdminDAO();
    }
    function listCages(){
        echo("<table>");
        foreach ($this->admin->getCages() as $cage){
            echo("<tr>");
            echo ("<td>".$cage->getCageId()."</td>");
            echo ("<td>".$cage->getCageName()."</td>");
            echo("</tr>");
        }
        echo("</table>");
       
    }
}




?>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        
        <h1>Hello, World!</h1>
        <a href="cage.php">CageUI</a>
        <a href="admin.php">AdminUI</a>
        <?php 
        //echo(print_r($admin->getCages()));
        //echo(print_r($admin->getCages()));
        //echo("<br/>");
        //echo(print_r($admin->getCage(37)));
          $adminUI = new AdminUI();
          $adminUI->listCages();

             
        ?>
    </body>
</html>
