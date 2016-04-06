<?php

$poruka = array();

if(isset($_POST['ime']) && isset($_POST['prezime']) && isset($_POST['email']) && isset($_POST['password'])){

	$ime = $_POST['ime'];
	$prezime = $_POST['prezime'];
	$email = $_POST['email'];
	$pass = $_POST['password'];
	$telefon = $_POST['telefon'];
	$fb = $_POST['fb'];

	$dns = 'mysql:dbname=pz;host=127.0.0.1';
    $user = 'pzuser';
    $password = 'pass';

    try{
    	$con = new PDO($dns, $user, $password);
    }

    catch (PDOException $e) {
        echo 'Konekcija nije uspjela: ' . $e->getMessage();
    }
        $checkMail = $con->prepare('SELECT * FROM korisnici WHERE email = ?');
        $checkMail->bindValue(1, $email, PDO::PARAM_STR);
        $checkMail->execute();

        if ($checkMail->rowCount() > 0) {
            $poruka["uspjeh"] = 0;
            $poruka["tekst"] = "Nalog sa e-mailom " . $email . " postoji"; 
            echo json_encode($poruka); 
        }

        else{
                $result = $con->prepare('INSERT INTO korisnici SET ime=?, prezime=?, email=?, password=?, telefon=?, fb=?');
                $result->bindValue(1, $ime, PDO::PARAM_STR);
                $result->bindValue(2, $prezime, PDO::PARAM_STR);
                $result->bindValue(3, $email, PDO::PARAM_STR);
                $result->bindValue(4, $pass, PDO::PARAM_STR);
                $result->bindValue(5, $telefon, PDO::PARAM_STR);
                $result->bindValue(6, $fb, PDO::PARAM_STR); 
                $result->execute();

            if ($result) {

                $result = $con->prepare('SELECT * FROM korisnici WHERE email = ?');
                $result->bindValue(1, $email, PDO::PARAM_STR);
                $result->execute();
                foreach($result as $row){
                    $id = $row["id"];  
                }

                $poruka["uspjeh"] = 1;
                $poruka["tekst"] = "Registracija uspješna";
                $poruka["id"] = $id;
                echo json_encode($poruka);
            }

            else{
                $poruka["uspjeh"] = 0;
                $poruka["tekst"] = "Registracija neuspješna";
         
                echo json_encode($poruka);
            }
        }
    	
}

else{
	$poruka["uspjeh"] = 0;
    $poruka["tekst"] = "Nedostaju polja";
 
    echo json_encode($poruka);
}
?>