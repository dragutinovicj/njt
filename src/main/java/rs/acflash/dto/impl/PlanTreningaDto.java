/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import rs.acflash.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class PlanTreningaDto implements Dto{
    private Long idPlan;

    @NotNull(message = "Datum plana je obavezan")
    private Date datum;

    @NotNull(message = "Intenzitet plana je obavezan")
    @Min(value = 0, message = "Intenzitet plana ne sme biti negativan")
    private Integer intenzitet;

    @NotNull
    private Long idAtleticar;
    @NotNull
    private Long idTrener;

    private List<StavkaPlanaDto> stavke;

    public PlanTreningaDto() {
    }

    public PlanTreningaDto(Long idPlan, Date datum, Integer intenzitet, Long idAtleticar, Long idTrener, List<StavkaPlanaDto> stavke) {
        this.idPlan = idPlan;
        this.datum = datum;
        this.intenzitet = intenzitet;
        this.idAtleticar = idAtleticar;
        this.idTrener = idTrener;
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

    public Long getIdAtleticar() {
        return idAtleticar;
    }

    public void setIdAtleticar(Long idAtleticar) {
        this.idAtleticar = idAtleticar;
    }

    public Long getIdTrener() {
        return idTrener;
    }

    public void setIdTrener(Long idTrener) {
        this.idTrener = idTrener;
    }

    public List<StavkaPlanaDto> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaPlanaDto> stavke) {
        this.stavke = stavke;
    }
    
    
    
}
