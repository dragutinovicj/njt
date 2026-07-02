/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.VezbaDto;
import rs.acflash.entity.impl.Vezba;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class VezbaMapper implements DtoEntityMapper<VezbaDto, Vezba>{

    @Override
    public VezbaDto toDto(Vezba e) {
        return new VezbaDto(e.getIdVezba(), e.getNaziv(), e.getTip(), e.getOpis(), e.getTezina());
    }

    @Override
    public Vezba toEntity(VezbaDto t) {
        return new Vezba(t.getIdVezba(), t.getNaziv(), t.getTip(), t.getOpis(), t.getTezina());
    }
    
}
