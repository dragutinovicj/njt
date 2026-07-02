/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.KategorijaDto;
import rs.acflash.entity.impl.Kategorija;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.KategorijaMapper;
import rs.acflash.repository.impl.KategorijaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class KategorijaServis {

    private final KategorijaRepository kategorijaRepository;
    private final KategorijaMapper kategorijaMapper;

    @Autowired
    public KategorijaServis(KategorijaRepository kategorijaRepository, KategorijaMapper kategorijaMapper) {
        this.kategorijaRepository = kategorijaRepository;
        this.kategorijaMapper = kategorijaMapper;
    }

    public List<KategorijaDto> findAll() {
        return kategorijaRepository.findAll().stream().map(kategorijaMapper::toDto).collect(Collectors.toList());
    }
    
    public KategorijaDto findById(Long id) throws NjtException {
        return kategorijaMapper.toDto(kategorijaRepository.findById(id));
    }
    
    public KategorijaDto add(KategorijaDto dto) throws NjtException {
        Kategorija kategorija = kategorijaMapper.toEntity(dto);
        kategorijaRepository.save(kategorija);
        return kategorijaMapper.toDto(kategorija);
    }
    
    public void deleteById(Long id) {
        kategorijaRepository.deleteById(id);
    }
    
    public KategorijaDto update(KategorijaDto dto) throws NjtException {
        Kategorija update = kategorijaMapper.toEntity(dto);
        kategorijaRepository.save(update);
        return kategorijaMapper.toDto(update);
    }
    

}
