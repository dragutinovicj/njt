/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.StavkaPlanaDto;
import rs.acflash.entity.impl.StavkaPlana;
import rs.acflash.entity.impl.StavkaPlanaId;
import rs.acflash.exception.NjtException;
import rs.acflash.mapper.impl.StavkaPlanaMapper;
import rs.acflash.repository.impl.StavkaPlanaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class StavkaPlanaServis {
    
    private final StavkaPlanaRepository stavkaPlanaRepository;
    private final StavkaPlanaMapper stavkaPlanaMapper;

    @Autowired
    public StavkaPlanaServis(StavkaPlanaRepository stavkaPlanaRepository, StavkaPlanaMapper stavkaPlanaMapper) {
        this.stavkaPlanaRepository = stavkaPlanaRepository;
        this.stavkaPlanaMapper = stavkaPlanaMapper;
    }

    
    public List<StavkaPlanaDto> findAll() {
        return stavkaPlanaRepository.findAll().stream().map(stavkaPlanaMapper::toDto).collect(Collectors.toList());
    }
    
     public StavkaPlanaDto findById(StavkaPlanaId id) throws NjtException {
        return stavkaPlanaMapper.toDto(stavkaPlanaRepository.findById(id));
    }

      public StavkaPlanaDto add(StavkaPlanaDto dto) throws NjtException {
        StavkaPlana stavka = stavkaPlanaMapper.toEntity(dto);
        stavkaPlanaRepository.save(stavka);
        return stavkaPlanaMapper.toDto(stavka);
    }
    
    public void deleteById(StavkaPlanaId id) {
        stavkaPlanaRepository.deleteById(id);
    }
    
    public StavkaPlanaDto update(StavkaPlanaDto dto) throws NjtException {
        StavkaPlana update = stavkaPlanaMapper.toEntity(dto);
        stavkaPlanaRepository.save(update);
        return stavkaPlanaMapper.toDto(update);
    }

    
}
