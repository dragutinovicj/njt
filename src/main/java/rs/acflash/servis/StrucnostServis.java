/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.StrucnostDto;
import rs.acflash.entity.impl.Strucnost;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.StrucnostMapper;
import rs.acflash.repository.impl.StrucnostRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class StrucnostServis {

    private final StrucnostRepository strucnostRepository;
    private final StrucnostMapper strucnostMapper;

    @Autowired
    public StrucnostServis(StrucnostRepository strucnostRepository, StrucnostMapper strucnostMapper) {
        this.strucnostRepository = strucnostRepository;
        this.strucnostMapper = strucnostMapper;
    }

    public List<StrucnostDto> findAll() {
        return strucnostRepository.findAll().stream().map(strucnostMapper::toDto).toList();
    }
    
     public StrucnostDto findById(Long id) throws NjtException {
        return strucnostMapper.toDto(strucnostRepository.findById(id));
    }

      public StrucnostDto add(StrucnostDto dto) {
        Strucnost strucnost = strucnostMapper.toEntity(dto);
        strucnostRepository.save(strucnost);
        return strucnostMapper.toDto(strucnost);
    }
    
    public void deleteById(Long id) {
        strucnostRepository.deleteById(id);
    }
    
    public StrucnostDto update(StrucnostDto dto) {
        Strucnost update = strucnostMapper.toEntity(dto);
        strucnostRepository.save(update);
        return strucnostMapper.toDto(update);
    }

    
}
