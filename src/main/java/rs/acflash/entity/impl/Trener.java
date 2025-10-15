/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import jakarta.persistence.*;
import java.util.List;
import rs.acflash.entity.AppEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name="trener",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_trener_korisnickoIme", columnNames="korisnicko_ime")
        })

public class Trener implements AppEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrener;
    
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    
    @OneToMany(mappedBy="trener", cascade=CascadeType.ALL)
    private List<TrenerStrucnost> odlikovanja;

    public Trener() {
    }

    public Trener(Long idTrener) {
        this.idTrener = idTrener;
    }

    
    public Trener(Long idTrener, String ime, String prezime, String korisnickoIme, String lozinka, List<TrenerStrucnost> odlikovanja) {
        this.idTrener = idTrener;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.odlikovanja = odlikovanja;
    }

    public Trener(String korisnickoIme, String lozinka, List<Object> emptyList) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

    public Long getIdTrener() {
        return idTrener;
    }

    public void setIdTrener(Long idTrener) {
        this.idTrener = idTrener;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public List<TrenerStrucnost> getOdlikovanja() {
        return odlikovanja;
    }

    public void setOdlikovanja(List<TrenerStrucnost> odlikovanja) {
        this.odlikovanja = odlikovanja;
    }
    
    
    
}
