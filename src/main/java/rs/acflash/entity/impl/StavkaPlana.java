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
@Table(name = "stavka_plana")
public class StavkaPlana implements AppEntity{
    
    @EmbeddedId
    private StavkaPlanaId id;
    
    @ManyToOne
    @MapsId("idPlan")
    private PlanTreninga plan;

    @ManyToOne
    @JoinColumn(name = "idVezba")
    private Vezba vezba;
    
    private Integer brojSerija;
    private Integer brojPonavljanja;
    
    private Integer intenzitet;

    public StavkaPlana() {
    }

    public StavkaPlana(StavkaPlanaId id, PlanTreninga plan, Vezba vezba, Integer brojSerija, Integer brojPonavljanja, Integer intenzitet) {
        this.id = id;
        this.plan = plan;
        this.vezba = vezba;
        this.brojSerija = brojSerija;
        this.brojPonavljanja = brojPonavljanja;
        this.intenzitet = intenzitet;
    }

    public StavkaPlanaId getId() {
        return id;
    }

    public void setId(StavkaPlanaId id) {
        this.id = id;
    }

    public PlanTreninga getPlan() {
        return plan;
    }

    public void setPlan(PlanTreninga plan) {
        this.plan = plan;
    }

    public Vezba getVezba() {
        return vezba;
    }

    public void setVezba(Vezba vezba) {
        this.vezba = vezba;
    }

    public Integer getBrojSerija() {
        return brojSerija;
    }

    public void setBrojSerija(Integer brojSerija) {
        this.brojSerija = brojSerija;
    }

    public Integer getBrojPonavljanja() {
        return brojPonavljanja;
    }

    public void setBrojPonavljanja(Integer brojPonavljanja) {
        this.brojPonavljanja = brojPonavljanja;
    }

    public Integer getIntenzitet() {
        return intenzitet;
    }

    public void setIntenzitet(Integer intenzitet) {
        this.intenzitet = intenzitet;
    }
    
    
    
    
    
    
}
