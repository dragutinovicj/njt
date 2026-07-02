/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.entity.impl.Trener;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class TrenerMapper implements DtoEntityMapper<TrenerDto, Trener>{

    @Override
    public TrenerDto toDto(Trener e) {
        return new TrenerDto(e.getIdTrener(), e.getIme(), e.getPrezime(), e.getKorisnickoIme(), e.getLozinka());
    }

    @Override
    public Trener toEntity(TrenerDto t) {
       return new Trener(t.getIdTrener(), t.getIme(), t.getPrezime(), t.getKorisnickoIme(), t.getLozinka(), null);
    }
    
}
