/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.KategorijaDto;
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class KategorijaMapper implements DtoEntityMapper<KategorijaDto, Kategorija> {

    @Override
    public KategorijaDto toDto(Kategorija e) {
        return new KategorijaDto(e.getIdKategorija(), e.getNaziv(), e.getOpis());
    }

    @Override
    public Kategorija toEntity(KategorijaDto t) {
        Kategorija entity = new Kategorija();
        entity.setIdKategorija(t.getIdKategorija());
        entity.setNaziv(t.getNaziv());
        entity.setOpis(t.getOpis());
        return entity;
    }

}
