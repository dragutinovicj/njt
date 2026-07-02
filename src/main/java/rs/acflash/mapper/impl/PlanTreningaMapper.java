/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.PlanTreningaDto;
import rs.acflash.dto.impl.StavkaPlanaDto;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.Trener;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class PlanTreningaMapper implements DtoEntityMapper<PlanTreningaDto, PlanTreninga>{

    private final StavkaPlanaMapper stavkaMapper;

    public PlanTreningaMapper(StavkaPlanaMapper stavkaMapper) {
        this.stavkaMapper = stavkaMapper;
    }
    
    @Override
    public PlanTreningaDto toDto(PlanTreninga e) {
       Long idTrener = e.getTrener() != null ? e.getTrener().getIdTrener() :null;
       Long idAtleticar = e.getAtleticar() != null ? e.getAtleticar().getIdAtleticar() : null;
       List<StavkaPlanaDto> stavke = e.getStavke() != null
               ? e.getStavke().stream().map(stavkaMapper::toDto).toList()
                : null;
       
      return new PlanTreningaDto(e.getIdPlan(), e.getDatum(), e.getIntenzitet(), idAtleticar, idTrener, stavke);
    }

    @Override
    public PlanTreninga toEntity(PlanTreningaDto t) {
        Atleticar atleticar = t.getIdAtleticar() != null ? new Atleticar(t.getIdAtleticar()) : null;
        Trener trener = t.getIdTrener() != null ? new Trener(t.getIdTrener()) : null;
        PlanTreninga entity = new PlanTreninga(t.getIdPlan(), t.getDatum(), t.getIntenzitet(), atleticar, trener);
        if (t.getStavke() != null) {
        List<StavkaPlana> stavke = t.getStavke().stream()
            .map(stavkaDto -> {
                StavkaPlana stavka = stavkaMapper.toEntity(stavkaDto);
                stavka.setPlan(entity); 
                return stavka;
            })
            .toList();
        entity.setStavke(stavke);
    }

        return entity;
    }
    
}
