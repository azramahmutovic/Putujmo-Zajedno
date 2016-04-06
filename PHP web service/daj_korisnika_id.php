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
    $result = $con->prepare('SELECT * FROM korisnici WHERE id = ?');
            $result->bindValue(1, $id, PDO::PARAM_INT);
            $result->execute();
    // check for empty result
    if ($result->rowCount() > 0) {
        // looping through all results
        // products node
        $odgovor["korisnici"] = array();
     
        foreach($result as $row) {
            // temp user array
            $korisnik = array();
            $korisnik["id"] = $row["id"];
            $korisnik["ime"] = $row["ime"];
            $korisnik["prezime"] = $row["prezime"];
            $korisnik["email"] = $row["email"];
            $korisnik["telefon"] = $row["telefon"];
            $korisnik["fb"] = $row["fb"];
            // push single product into final response array
            array_push($odgovor["korisnici"], $korisnik);
        }
        // success
        $odgovor["uspjeh"] = 1;
     
        // echoing JSON response
        echo json_encode($odgovor);
    } else {
        // no products found
        $odgovor["uspjeh"] = 0;
        $odgovor["poruka"] = "Nema korisnika sa tim ID-em";
     
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