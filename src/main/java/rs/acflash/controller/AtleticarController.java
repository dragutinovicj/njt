/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rs.acflash.dto.impl.AtleticarDto;
import rs.acflash.dto.impl.KategorijaDto;
import rs.acflash.servis.AtleticarServis;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/atleticar")
public class AtleticarController {
    private final AtleticarServis atleticarServis;

    public AtleticarController(AtleticarServis atleticarServis) {
        this.atleticarServis = atleticarServis;
    }
    
    @GetMapping
    @Operation(summary="Vraća listu svih atleticara")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = AtleticarDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<AtleticarDto>> getAll(){
        return new ResponseEntity<>(atleticarServis.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary="Vraća atleticara po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = AtleticarDto.class), mediaType = "application/json")
    })
    public ResponseEntity<AtleticarDto> finById(
        @PathVariable(value = "id") Long id){
        try{
             return new ResponseEntity<>(atleticarServis.findById(id), HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AtleticarController ex");
        }
       
    }
    
    @PostMapping()
    @Operation(summary="Kreira novog atleticara")
    @ApiResponse(responseCode = "201", content ={
        @Content(schema= @Schema(implementation = AtleticarDto.class), mediaType = "application/json")
    })
    public ResponseEntity<AtleticarDto> add(
        @Valid @RequestBody @NotNull AtleticarDto atleticarDto){
        try{
             AtleticarDto saved = atleticarServis.add(atleticarDto);
             return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri kreiranju atleticara");
        }
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Brise atleticara po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = AtleticarDto.class), mediaType = "application/json")
    })
    public ResponseEntity<String> delete(
        @PathVariable(value = "id") Long id){
        try{
             atleticarServis.deleteById(id);
             return new ResponseEntity<>("Atleticar je uspesno obrisan.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Atleticar "+ id + " ne postoji", HttpStatus.NOT_FOUND);
        }
       
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Azurira atleticara po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = AtleticarDto.class), mediaType = "application/json")
    })
    public ResponseEntity<AtleticarDto> update(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody AtleticarDto atleticarDto){
        try{
             atleticarDto.setIdAtleticar(id);
             AtleticarDto updated = atleticarServis.update(atleticarDto);
             return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri azuriranju atleticara");
        }
       
    }
    
}
