/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import rs.acflash.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class StavkaPlanaDto implements Dto{
    private Long idPlan;
    private Long rb;

    @NotNull
    private Long idVezba;
    
    @NotNull(message = "Broj serija je obavezan")
    @Min(value = 1, message = "Broj serija mora biti veći od nule")
    private Integer brojSerija;

    @NotNull(message = "Broj ponavljanja je obavezan")
    @Min(value = 1, message = "Broj ponavljanja mora biti veći od nule")
    private Integer brojPonavljanja;

    @NotNull(message = "Intenzitet je obavezan")
    @Min(value = 0, message = "Intenzitet ne sme biti negativan")
    private Integer intenzitet;

    public StavkaPlanaDto() {
    }

    public StavkaPlanaDto(Long idPlan, Long rb, Long idVezba, Integer brojSerija, Integer brojPonavljanja, Integer intenzitet) {
        this.idPlan = idPlan;
        this.rb = rb;
        this.idVezba = idVezba;
        this.brojSerija = brojSerija;
        this.brojPonavljanja = brojPonavljanja;
        this.intenzitet = intenzitet;
    }

    public Long getIdVezba() {
        return idVezba;
    }

    public void setIdVezba(Long idVezba) {
        this.idVezba = idVezba;
    }

    
    
    

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getRb() {
        return rb;
    }

    public void setRb(Long rb) {
        this.rb = rb;
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
