/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.VezbaDto;
import rs.acflash.entity.impl.Vezba;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.VezbaMapper;
import rs.acflash.repository.impl.VezbaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class VezbaServis {
     private final VezbaRepository vezbaRepository;
    private final VezbaMapper vezbaMapper;

    @Autowired
    public VezbaServis(VezbaRepository vezbaRepository, VezbaMapper vezbaMapper) {
        this.vezbaRepository = vezbaRepository;
        this.vezbaMapper = vezbaMapper;
    }
 
    public List<VezbaDto> findAll() {
        return vezbaRepository.findAll().stream().map(vezbaMapper::toDto).toList();
    }
    
     public VezbaDto findById(Long id) throws NjtException {
        return vezbaMapper.toDto(vezbaRepository.findById(id));
    }

      public VezbaDto add(VezbaDto dto) {
        Vezba vezba = vezbaMapper.toEntity(dto);
        vezbaRepository.save(vezba);
        return vezbaMapper.toDto(vezba);
    }
    
    public void deleteById(Long id) {
        vezbaRepository.deleteById(id);
    }
    
    public VezbaDto update(VezbaDto dto) {
        Vezba update = vezbaMapper.toEntity(dto);
        vezbaRepository.save(update);
        return vezbaMapper.toDto(update);
    }

}