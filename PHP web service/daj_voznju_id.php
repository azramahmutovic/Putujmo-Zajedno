<?php
 
// array for JSON response
$odgovor = array();

if(isset($_POST['id'])){

    $id = $_POST['id'];
   
    $dsn = 'mysql:dbname=pz;host=127.0.0.1';
    $user = 'pzuser';
    $password = 'pass';

    try{
        $con = new PDO($dsn, $user, $password);

     } catch (PDOException $e) {
          echo 'Konekcija nije uspjela: ' . $e->getMessage();
    }

    // get all products from products table
    //$result = mysql_query("SELECT *FROM products") or die(mysql_error());
    $result = $con->prepare('SELECT * FROM oglasi WHERE id = ?');
            $result->bindValue(1, $id, PDO::PARAM_INT);
            $result->execute();
    // check for empty result
    if ($result->rowCount() > 0) {
        // looping through all results
        // products node
        $odgovor["oglasi"] = array();
     
        foreach($result as $row) {
            // temp user array
            $oglas = array();
            $oglas["id"] = $row["id"];
            $oglas["tip_oglasa"] = $row["tip_oglasa"];
            $oglas["polazak"] = $row["polazak"];
            $oglas["odrediste"] = $row["odrediste"];
            $oglas["datum"] = $row["datum"];
            $oglas["vrijeme"] = $row["vrijeme"];
            $oglas["br_mjesta"] = $row["br_mjesta"];
            $oglas["flex_dana"] = $row["flex_dana"];
            $oglas["info"] = $row["info"];
            $oglas["trosak"] = $row["trosak"];
            $oglas["cijena"] = $row["cijena"];
            $oglas["korisnik_id"] = $row["korisnik_id"];

            // push single product into final response array
            array_push($odgovor["oglasi"], $oglas);
        }
        // success
        $odgovor["uspjeh"] = 1;
     
        // echoing JSON response
        echo json_encode($odgovor);
    } else {
        // no products found
        $odgovor["uspjeh"] = 0;
        $odgovor["poruka"] = "Nema oglasa sa tim ID-em";
     
        // echo no users JSON
        echo json_encode($odgovor);
    }
}
else{

    $poruka["uspjeh"] = 0;
    $poruka["tekst"] = "Nedostaju polja";
    echo json_encode($poruka);
}
?>