/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import rs.acflash.entity.AppEntity;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "plan_treninga")
public class PlanTreninga implements AppEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;
    
    private Date datum;
    private Integer intenzitet;
    
    @ManyToOne
    @JoinColumn(name="idAtleticar")
    private Atleticar atleticar;
    
    @ManyToOne
    @JoinColumn(name="idTrener")
    private Trener trener;
    
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StavkaPlana> stavke;

    public PlanTreninga() {
    }

    public PlanTreninga(Long idPlan) {
        this.idPlan = idPlan;
    }

    public PlanTreninga(Long idPlan, Date datum, Integer intenzitet, Atleticar atleticar, Trener trener) {
        this.idPlan = idPlan;
        this.datum = datum;
        this.intenzitet = intenzitet;
        this.atleticar = atleticar;
        this.trener = trener;
    }
    
    

    public PlanTreninga(Long idPlan, Date datum, Integer intenzitet, Atleticar atleticar, Trener trener, List<StavkaPlana> stavke) {
        this.idPlan = idPlan;
        this.datum = datum;
        this.intenzitet = intenzitet;
        this.atleticar = atleticar;
        this.trener = trener;
        this.stavke = stavke;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Integer getIntenzitet() {
        return intenzitet;
    }

    public void setIntenzitet(Integer intenzitet) {
        this.intenzitet = intenzitet;
    }

    public Atleticar getAtleticar() {
        return atleticar;
    }

    public void setAtleticar(Atleticar atleticar) {
        this.atleticar = atleticar;
    }

    public Trener getTrener() {
        return trener;
    }

    public void setTrener(Trener trener) {
        this.trener = trener;
    }

    public List<StavkaPlana> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaPlana> stavke) {
        this.stavke = stavke;
    }
    
    
    
    
    
    
    
}
