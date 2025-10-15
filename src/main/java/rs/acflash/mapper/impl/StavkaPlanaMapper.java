/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.StavkaPlanaDto;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.StavkaPlanaId;
import rs.acflash.entity.impl.Vezba;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class StavkaPlanaMapper implements DtoEntityMapper<StavkaPlanaDto, StavkaPlana>{

    @Override
    public StavkaPlanaDto toDto(StavkaPlana e) {
        Long idPlan = e.getPlan() != null ? e.getPlan().getIdPlan() : null;
        Long idVezba = e.getVezba() != null ? e.getVezba().getIdVezba() : null;
        Long rb = e.getId() != null ? e.getId().getRb() : null;
        StavkaPlanaDto dto = new StavkaPlanaDto(idPlan, rb, idVezba, e.getBrojSerija(), e.getBrojPonavljanja(), e.getIntenzitet());
        return dto;
    }

    @Override
    public StavkaPlana toEntity(StavkaPlanaDto t) {
        PlanTreninga plan = t.getIdPlan() != null ? new PlanTreninga(t.getIdPlan()) : null;
        Vezba vezba = t.getIdVezba() != null ? new Vezba(t.getIdVezba()) : null;
        StavkaPlanaId id = new StavkaPlanaId(t.getRb(), t.getIdPlan());
        StavkaPlana entity = new StavkaPlana(id, plan, vezba, t.getBrojSerija(), t.getBrojSerija(), t.getIntenzitet());
        return entity;
    }
    
}
