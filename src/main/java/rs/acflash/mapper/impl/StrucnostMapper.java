/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.StrucnostDto;
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class StrucnostMapper implements DtoEntityMapper<StrucnostDto, Strucnost>{

    @Override
    public StrucnostDto toDto(Strucnost e) {
       return new StrucnostDto(e.getIdStrucnost(), e.getNaziv(), e.getOpis());
    }

    @Override
    public Strucnost toEntity(StrucnostDto t) {
        return new Strucnost(t.getIdStrucnost(), t.getNaziv(), t.getOpis());
    }
    
    
}
