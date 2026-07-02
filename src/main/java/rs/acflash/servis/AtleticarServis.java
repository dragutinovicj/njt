/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.AtleticarDto;
import rs.acflash.entity.impl.Atleticar;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.AtleticarMapper;
import rs.acflash.repository.impl.AtleticarRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class AtleticarServis {
    
    private final AtleticarRepository atleticarRepository;
    private final AtleticarMapper atleticarMapper;

    @Autowired
    public AtleticarServis(AtleticarRepository atleticarRepository, AtleticarMapper atleticarMapper) {
        this.atleticarRepository = atleticarRepository;
        this.atleticarMapper = atleticarMapper;
    }
    
    public List<AtleticarDto> findAll() {
       return atleticarRepository.findAll().stream().map(atleticarMapper::toDto).toList();
    }
    
     public AtleticarDto findById(Long id) throws NjtException {
        return atleticarMapper.toDto(atleticarRepository.findById(id));
    }

      public AtleticarDto add(AtleticarDto dto){
        Atleticar atleticar = atleticarMapper.toEntity(dto);
        atleticarRepository.save(atleticar);
        return atleticarMapper.toDto(atleticar);
    }
    
    public void deleteById(Long id) {
        atleticarRepository.deleteById(id);
    }
    
    public AtleticarDto update(AtleticarDto dto) {
        Atleticar update = atleticarMapper.toEntity(dto);
        atleticarRepository.save(update);
        return atleticarMapper.toDto(update);
    }

}
