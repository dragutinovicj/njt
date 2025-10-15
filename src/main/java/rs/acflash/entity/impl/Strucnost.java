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
@Table(name="strucnost")
public class Strucnost implements AppEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStrucnost;
    private String naziv;
    private String opis; 
    
    @OneToMany(mappedBy="strucnost", cascade=CascadeType.ALL)
    private List<TrenerStrucnost> odlikovanja;

    public Strucnost() {
    }

    public Strucnost(Long idStrucnost) {
        this.idStrucnost = idStrucnost;
    }
    
    

    public Strucnost(Long idStrucnost, String naziv, String opis, List<TrenerStrucnost> odlikovanja) {
        this.idStrucnost = idStrucnost;
        this.naziv = naziv;
        this.opis = opis;
        this.odlikovanja = odlikovanja;
    }

    public Strucnost(Long idStrucnost, String naziv, String opis) {
        this.idStrucnost = idStrucnost;
        this.naziv = naziv;
        this.opis = opis;
    }

    public Long getIdStrucnost() {
        return idStrucnost;
    }

    public void setIdStrucnost(Long idStrucnost) {
        this.idStrucnost = idStrucnost;
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

    public List<TrenerStrucnost> getOdlikovanja() {
        return odlikovanja;
    }

    public void setOdlikovanja(List<TrenerStrucnost> odlikovanja) {
        this.odlikovanja = odlikovanja;
    }

    @Override
    public String toString() {
        return naziv;
    }
    
    
}
