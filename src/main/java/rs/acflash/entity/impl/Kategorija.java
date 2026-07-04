/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import java.util.List;
import jakarta.persistence.*;
import rs.acflash.entity.AppEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name="kategorija")
public class Kategorija implements AppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKategorija;
    private String naziv;
    private String opis; 
    
    @OneToMany(mappedBy="kategorija", cascade=CascadeType.ALL)
    private List<Atleticar> atleticari;

    public Kategorija() {
    }

    public Kategorija(Long idKategorija, String naziv, String opis, List<Atleticar> atleticari) {
        this.idKategorija = idKategorija;
        this.naziv = naziv;
        this.opis = opis;
        this.atleticari = atleticari;
    }

    public Kategorija(Long idKategorija) {
        this.idKategorija = idKategorija;
    }
    
    

    public Long getIdKategorija() {
        return idKategorija;
    }

    public void setIdKategorija(Long idKategorija) {
        this.idKategorija = idKategorija;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public List<Atleticar> getAtleticari() {
        return atleticari;
    }

    public void setAtleticari(List<Atleticar> atleticari) {
        this.atleticari = atleticari;
    }

    @Override
    public String toString() {
        return naziv;
    }
    
    
    
    
    
}
