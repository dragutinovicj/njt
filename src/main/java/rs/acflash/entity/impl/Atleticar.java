/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import jakarta.persistence.*;
import java.util.Date;
import rs.acflash.entity.AppEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name="atleticar")
public class Atleticar implements AppEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAtleticar;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private String pol;
    private Double visina;
    private Double tezina;
    private Double bmi;
    private String imageUrl;
    
    @ManyToOne
    @JoinColumn(name="idKategorija")
    private Kategorija kategorija;

    public Atleticar() {
    }

    public Atleticar(Long idAtleticar) {
        this.idAtleticar = idAtleticar;
    }
    
    

    public Atleticar(Long idAtleticar, String ime, String prezime, Date datumRodjenja, String pol, Double visina, Double tezina, Double bmi, String imageUrl, Kategorija kategorija) {
        this.idAtleticar = idAtleticar;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.pol = pol;
        this.visina = visina;
        this.tezina = tezina;
        this.bmi = bmi;
        this.imageUrl = imageUrl;
        this.kategorija = kategorija;
    }

    

    public Long getIdAtleticar() {
        return idAtleticar;
    }

    public void setIdAtleticar(Long idAtleticar) {
        this.idAtleticar = idAtleticar;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public Double getVisina() {
        return visina;
    }

    public void setVisina(Double visina) {
        this.visina = visina;
    }

    public Double getTezina() {
        return tezina;
    }

    public void setTezina(Double tezina) {
        this.tezina = tezina;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    @Override
    public String toString() {
        return  ime + " " + prezime;
    }
    
    
    
}
