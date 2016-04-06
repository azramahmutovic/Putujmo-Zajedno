package com.azra.putujmozajedno;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

/**
 * Created by Azra on 26.08.2015..
 */
public class Oglas {

    private int id;
    private String polazak;
    private String odrediste;
    private Date datum;
    private String vrijeme;
    private String tip_oglasa;
    private int br_mjesta;
    private int flex_dana;
    private String info;
    private String trosak;
    private String cijena;
    private int korisnik_id;


    public String getId() {
        return Integer.toString(id);
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getPolazak() {
        return polazak;
    }

    public void setPolazak(String polazak) {
        this.polazak = polazak;
    }

    public String getOdrediste() {
        return odrediste;
    }

    public void setOdrediste(String odrediste) {
        this.odrediste = odrediste;
    }

    public String getDatum() {
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        String date = df.format(datum);
        return date;
    }

    public void setDatum(String datum) {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = df.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.datum = date;
    }

    public String getVrijeme() {
        return  vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        if(vrijeme.equals("null"))
            this.vrijeme = "";
        else
            this.vrijeme = vrijeme.substring(0,5);
    }

    public String getTipOglasa() {
        return tip_oglasa;
    }

    public void setTipOglasa(String tip_oglasa) {
        if(tip_oglasa.equals("ponuda"))
            this.tip_oglasa = "Ponuda";
        else
            this.tip_oglasa = "Potražnja";
    }

    public String getBrMjesta() {
        if(br_mjesta == 0)
            return "";
        else
            return Integer.toString(br_mjesta);
    }

    public void setBrMjesta(String br_mjesta) {
        if(br_mjesta.equals(""))
            this.br_mjesta = 0;
        else
            this.br_mjesta = Integer.parseInt(br_mjesta);
    }

    public String getFlexDana() {
        if(flex_dana == 0)
            return "";
        else if(flex_dana % 10 == 1)
            return Integer.toString(flex_dana) + " dan";
        else
            return Integer.toString(flex_dana) + " dana";
    }

    public void setFlexDana(String flex_dana) {
        if(flex_dana.equals("") || flex_dana.equals("null"))
            this.flex_dana = 0;
        else
            this.flex_dana = Integer.parseInt(flex_dana);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTrosak() {
        return trosak;
    }

    public void setTrosak(String trosak) {
        this.trosak = trosak;
    }

    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }

    public String getKorisnikId() {
        return Integer.toString(korisnik_id);
    }

    public void setKorisnikId(String korisnik_id) {
        this.korisnik_id = Integer.parseInt(korisnik_id);
    }
}
