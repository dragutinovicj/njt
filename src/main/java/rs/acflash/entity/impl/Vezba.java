/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.util.List;
import rs.acflash.entity.AppEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "vezba")
public class Vezba implements AppEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVezba;
    private String naziv;
    private String tip;
    private String opis;
    private Integer tezina;
    
    @OneToMany(mappedBy="vezba", cascade=CascadeType.ALL)
    private List<StavkaPlana> stavke;

    public Vezba() {
    }

    public Vezba(Long idVezba) {
        this.idVezba = idVezba;
    }

    
    public Vezba(Long idVezba, String naziv, String tip, String opis, Integer tezina, List<StavkaPlana> stavke) {
        this.idVezba = idVezba;
        this.naziv = naziv;
        this.tip = tip;
        this.opis = opis;
        this.tezina = tezina;
        this.stavke = stavke;
    }

    public Vezba(Long idVezba, String naziv, String tip, String opis, Integer tezina) {
        this.idVezba = idVezba;
        this.naziv = naziv;
        this.tip = tip;
        this.opis = opis;
        this.tezina = tezina;
    }

    
    public Long getIdVezba() {
        return idVezba;
    }

    public void setIdVezba(Long idVezba) {
        this.idVezba = idVezba;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getTezina() {
        return tezina;
    }

    public void setTezina(Integer tezina) {
        this.tezina = tezina;
    }

    public List<StavkaPlana> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaPlana> stavke) {
        this.stavke = stavke;
    }

    @Override
    public String toString() {
        return naziv;
    }
    
    
    
}
