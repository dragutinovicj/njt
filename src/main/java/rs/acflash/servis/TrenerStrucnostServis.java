/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.TrenerStrucnostDto;
import rs.acflash.entity.impl.TrenerStrucnost;
import rs.acflash.entity.impl.TrenerStrucnostId;
import rs.acflash.mapper.impl.TrenerStrucnostMapper;
import rs.acflash.repository.impl.TrenerStrucnostRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class TrenerStrucnostServis {
    
    private final TrenerStrucnostRepository trenerStrucnostRepository;
    private final TrenerStrucnostMapper trenerStrucnostMapper;

    @Autowired
    public TrenerStrucnostServis(TrenerStrucnostRepository trenerStrucnostRepository, TrenerStrucnostMapper trenerStrucnostMapper) {
        this.trenerStrucnostRepository = trenerStrucnostRepository;
        this.trenerStrucnostMapper = trenerStrucnostMapper;
    }

   
    public List<TrenerStrucnostDto> findAll() {
        return trenerStrucnostRepository.findAll().stream().map(trenerStrucnostMapper::toDto).collect(Collectors.toList());
    }
    
     public TrenerStrucnostDto findById(TrenerStrucnostId id) throws Exception {
        return trenerStrucnostMapper.toDto(trenerStrucnostRepository.findById(id));
    }
     
     public List<TrenerStrucnostDto> findByStrucnostId(Long idStrucnost) {
        return trenerStrucnostRepository.findByStrucnostId(idStrucnost)
                .stream()
                .map(trenerStrucnostMapper::toDto)
                .collect(Collectors.toList());
    }
     
     public List<TrenerStrucnostDto> findByTrenerId(Long idTrener) {
         return trenerStrucnostRepository.findByTrenerId(idTrener)
                .stream()
                .map(trenerStrucnostMapper::toDto)
                .collect(Collectors.toList());
    }

      public TrenerStrucnostDto add(TrenerStrucnostDto dto) throws Exception {
        TrenerStrucnost ts = trenerStrucnostMapper.toEntity(dto);
        trenerStrucnostRepository.save(ts);
        return trenerStrucnostMapper.toDto(ts);
    }
      
    
    public void deleteById(TrenerStrucnostId id) {
        trenerStrucnostRepository.deleteById(id);
    }
    
    public TrenerStrucnostDto update(TrenerStrucnostDto dto) throws Exception {
        TrenerStrucnost update = trenerStrucnostMapper.toEntity(dto);
        trenerStrucnostRepository.save(update);
        return trenerStrucnostMapper.toDto(update);
    }
    
}
