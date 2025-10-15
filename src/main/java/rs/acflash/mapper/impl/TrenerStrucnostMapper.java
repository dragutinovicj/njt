/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.mapper.impl;

import org.springframework.stereotype.Component;
import rs.acflash.dto.impl.TrenerStrucnostDto;
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.entity.impl.Trener;
import rs.acflash.entity.impl.TrenerStrucnost;
import rs.acflash.entity.impl.TrenerStrucnostId;
import rs.acflash.mapper.DtoEntityMapper;

/**
 *
 * @author Korisnik
 */
@Component
public class TrenerStrucnostMapper implements DtoEntityMapper<TrenerStrucnostDto, TrenerStrucnost> {

    @Override
    public TrenerStrucnostDto toDto(TrenerStrucnost e) {
        Long idTrener = e.getTrener() != null ? e.getTrener().getIdTrener() : null;
        Long idStrucnost = e.getStrucnost() != null ? e.getStrucnost().getIdStrucnost() : null;
        TrenerStrucnostDto dto = new TrenerStrucnostDto(idTrener, idStrucnost, e.getOdlikovanje());
        return dto;

    }

    @Override
    public TrenerStrucnost toEntity(TrenerStrucnostDto t) {
        Trener trener = t.getIdTrener() != null ? new Trener(t.getIdTrener()) : null;
        Strucnost strucnost = t.getIdStrucnost() != null ? new Strucnost(t.getIdStrucnost()) : null;
        TrenerStrucnostId id = new TrenerStrucnostId(t.getIdTrener(), t.getIdStrucnost());
        TrenerStrucnost entity = new TrenerStrucnost(id, trener, strucnost, t.getOdlikovanje());
        return entity;
    }

}
