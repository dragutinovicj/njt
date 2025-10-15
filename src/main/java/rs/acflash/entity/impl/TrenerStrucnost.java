/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import jakarta.persistence.*;
import rs.acflash.entity.AppEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name= "trener_strucnost")
public class TrenerStrucnost implements AppEntity{
    @EmbeddedId
    private TrenerStrucnostId id;
    
    @ManyToOne
    @MapsId("idTrener")
    private Trener trener;
    
    @ManyToOne
    @MapsId("idStrucnost")
    private Strucnost strucnost;
    
    private String odlikovanje;

    public TrenerStrucnost() {
    }

    public TrenerStrucnost(TrenerStrucnostId id, Trener trener, Strucnost strucnost, String odlikovanje) {
        this.id = id;
        this.trener = trener;
        this.strucnost = strucnost;
        this.odlikovanje = odlikovanje;
    }

    public TrenerStrucnostId getId() {
        return id;
    }

    public void setId(TrenerStrucnostId id) {
        this.id = id;
    }

    public Trener getTrener() {
        return trener;
    }

    public void setTrener(Trener trener) {
        this.trener = trener;
    }

    public Strucnost getStrucnost() {
        return strucnost;
    }

    public void setStrucnost(Strucnost strucnost) {
        this.strucnost = strucnost;
    }

    public String getOdlikovanje() {
        return odlikovanje;
    }

    public void setOdlikovanje(String odlikovanje) {
        this.odlikovanje = odlikovanje;
    }

    @Override
    public String toString() {
        return odlikovanje;
    }
    
    
    
}
