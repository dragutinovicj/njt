/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.AtleticarDto;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class AtleticarMapper implements DtoEntityMapper<AtleticarDto, Atleticar> {

    @Override
    public AtleticarDto toDto(Atleticar e) {
        Long idKategorija = e.getKategorija() != null ? e.getKategorija().getIdKategorija() : null;
        return new AtleticarDto(e.getIdAtleticar(), e.getIme(), e.getPrezime(), e.getDatumRodjenja(), e.getPol(), e.getVisina(), e.getTezina(), e.getBmi(), idKategorija, e.getImageUrl());
    }

    @Override
    public Atleticar toEntity(AtleticarDto t) {
        Kategorija kategorija = t.getIdKategorija() != null ? new Kategorija(t.getIdKategorija()) : null;
        return new Atleticar(t.getIdAtleticar(), t.getIme(), t.getPrezime(), t.getDatumRodjenja(), t.getPol(), t.getVisina(), t.getTezina(), t.getBmi(), t.getImageUrl(),  kategorija);
    }

    
}
