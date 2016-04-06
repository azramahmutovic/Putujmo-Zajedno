<?php

$poruka = array();

if(isset($_POST['tip_oglasa']) && isset($_POST['polazak']) 
    && isset($_POST['odrediste']) && isset($_POST['datum']) 
    && isset($_POST['br_mjesta']) && isset($_POST['trosak'])){

	$tip_oglasa = $_POST['tip_oglasa'];
	$polazak = $_POST['polazak'];
	$odrediste = $_POST['odrediste'];
	$datum = $_POST['datum'];
    $vrijeme = $_POST['vrijeme'];
	$br_mjesta = $_POST['br_mjesta'];
    $flex_dana = $_POST['flex_dana'];
    $info = $_POST['info'];
	$trosak = $_POST['trosak'];
    $cijena = $_POST['cijena'];
    $korisnik_id = $_POST['korisnik_id'];

    if($vrijeme == "")
        $vrijeme = null;
    if($cijena == "")
        $cijena = null;
    if($flex_dana == "")
        $flex_dana = null;
    
    //formatiranje stringa u Date
    $dateObj = \DateTime::createFromFormat("d/m/Y", $datum);
    if (!$dateObj)
    {
        throw new \UnexpectedValueException("Could not parse the date");
    }
    $date = $dateObj->format("Y-m-d");

    //parametri za konekciju
	$dns = 'mysql:dbname=pz;host=127.0.0.1';
    $user = 'pzuser';
    $password = 'pass';

    try{
    	$con = new PDO($dns, $user, $password);
    }

    catch (PDOException $e) {
        echo 'Konekcija nije uspjela: ' . $e->getMessage();
    }

    	$result = $con->prepare('INSERT INTO oglasi SET tip_oglasa=?, polazak=?, odrediste=?, datum=?, 
                                    vrijeme=?, br_mjesta=?, flex_dana=?, info=?, trosak=?, cijena=?, korisnik_id=?');
    	$result->bindValue(1, $tip_oglasa, PDO::PARAM_STR);
    	$result->bindValue(2, $polazak, PDO::PARAM_STR);
    	$result->bindValue(3, $odrediste, PDO::PARAM_STR);
        $result->bindValue(4, $date);
    	$result->bindValue(5, $vrijeme);
    	$result->bindValue(6, $br_mjesta, PDO::PARAM_INT); 
        $result->bindValue(7, $flex_dana, PDO::PARAM_INT); 
        $result->bindValue(8, $info, PDO::PARAM_STR);
        $result->bindValue(9, $trosak, PDO::PARAM_STR); 
        $result->bindValue(10, $cijena); 
        $result->bindValue(11, $korisnik_id, PDO::PARAM_INT); 

        $result->execute();
   // $result = $con->query("INSERT INTO korisnici(ime, prezime, email, password, telefon, fb) VALUES('$ime', '$prezime', '$email', '$pass', '$telefon, '$fb')");

	if ($result) {
      	$poruka["uspjeh"] = 1;
        $poruka["tekst"] = "Oglas uspješno dodan";
 
        echo json_encode($poruka);
    }

    else{
    	$poruka["uspjeh"] = 0;
        $poruka["tekst"] = "Greška kod dodavanja oglasa";
 
        echo json_encode($poruka);
    }


}

else{
	$poruka["uspjeh"] = 0;
    $poruka["tekst"] = "Nedostaju polja";
 
    echo json_encode($poruka);
}
?>