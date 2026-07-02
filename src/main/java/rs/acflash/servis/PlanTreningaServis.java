/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.PlanTreningaDto;
import rs.acflash.entity.impl.PlanTreninga;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.PlanTreningaMapper;
import rs.acflash.repository.impl.PlanTreningaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class PlanTreningaServis {
    private final PlanTreningaRepository planTreningaRepository;
    private final PlanTreningaMapper planTreningaMapper;

    @Autowired
    public PlanTreningaServis(PlanTreningaRepository planTreningaRepository, PlanTreningaMapper planTreningaMapper) {
        this.planTreningaRepository = planTreningaRepository;
        this.planTreningaMapper = planTreningaMapper;
    }

    
    public List<PlanTreningaDto> findAll() {
        return planTreningaRepository.findAll().stream().map(planTreningaMapper::toDto).toList();
    }
    
     public PlanTreningaDto findById(Long id) throws NjtException {
        return planTreningaMapper.toDto(planTreningaRepository.findById(id));
    }

      public PlanTreningaDto add(PlanTreningaDto dto) {
        PlanTreninga plan = planTreningaMapper.toEntity(dto);

    
        if (plan.getStavke() != null) {
        for (StavkaPlana stavka : plan.getStavke()) {
            stavka.setPlan(plan); // veza parent -> child
        }
    }

    planTreningaRepository.save(plan);
    return planTreningaMapper.toDto(plan);
    }
    
    public void deleteById(Long id) {
        planTreningaRepository.deleteById(id);
    }
    
    public PlanTreningaDto update(PlanTreningaDto dto) {
        PlanTreninga update = planTreningaMapper.toEntity(dto);
        
        if (update.getStavke() != null) {
        for (StavkaPlana stavka : update.getStavke()) {
            stavka.setPlan(update); // veza parent -> child
        }
    }
        planTreningaRepository.save(update);
        return planTreningaMapper.toDto(update);
    }

    
}
