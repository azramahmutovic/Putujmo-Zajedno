<?php

$poruka = array();

if(isset($_POST['email']) && isset($_POST['password'])){

	$email = $_POST['email'];
	$pass = $_POST['password'];

	$dns = 'mysql:dbname=pz;host=127.0.0.1';
	$user = 'pzuser';
    $password = 'pass';

    try{
    	$con = new PDO($dns, $user, $password);
    }

    catch (PDOException $e) {
        echo 'Konekcija nije uspjela: ' . $e->getMessage();
    }

    	$result = $con->prepare('SELECT * FROM korisnici WHERE email = ?');
    	$result->bindValue(1, $email, PDO::PARAM_STR);
        $result->execute();

	if ($result->rowCount() > 0) {
      	
        $postoji = false;

        foreach($result as $row){
            if($row['password'] == $pass){
                $postoji = true;
                $id = $row["id"];  
            }
                
        }

        if($postoji){
             $poruka["uspjeh"] = 1;
             $poruka["tekst"] = "Prijava uspješna";
             $poruka["id"] = $id;
             echo json_encode($poruka);
        }

        else{
            $poruka["uspjeh"] = 0;
            $poruka["tekst"] = "Netačan password";
            $poruka["id"] = null;
            echo json_encode($poruka);
        }
                
            

    }

    else{

    	$poruka["uspjeh"] = 0;
        $poruka["tekst"] = "Nalog ne postoji";
        $poruka["id"] = null;
        echo json_encode($poruka);
    }
}


else{
	$poruka["uspjeh"] = 0;
    $poruka["tekst"] = "Nedostaju polja";
    $poruka["id"] = null;
    echo json_encode($poruka);
}

?>