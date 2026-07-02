/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.entity.impl.Trener;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.TrenerMapper;
import rs.acflash.repository.impl.TrenerRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class TrenerServis {
    
    private final TrenerRepository trenerRepository;
    private final TrenerMapper trenerMapper;

    @Autowired
    public TrenerServis(TrenerRepository trenerRepository, TrenerMapper trenerMapper) {
        this.trenerRepository = trenerRepository;
        this.trenerMapper = trenerMapper;
    }
    
     public List<TrenerDto> findAll() {
        return trenerRepository.findAll().stream().map(trenerMapper::toDto).collect(Collectors.toList());
    }
    
     public TrenerDto findById(Long id) throws NjtException {
        return trenerMapper.toDto(trenerRepository.findById(id));
    }

      public TrenerDto add(TrenerDto dto) throws NjtException {
        Trener trener = trenerMapper.toEntity(dto);
        trenerRepository.save(trener);
        return trenerMapper.toDto(trener);
    }
    
    public void deleteById(Long id) {
        trenerRepository.deleteById(id);
    }
    
    public TrenerDto update(TrenerDto dto) throws NjtException {
        Trener update = trenerMapper.toEntity(dto);
        trenerRepository.save(update);
        return trenerMapper.toDto(update);
    }
    
}
