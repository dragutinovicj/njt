/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.controller;

/**
 *
 * @author Korisnik
 */

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.servis.TrenerServis;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/trener")
public class TrenerController {
    private final TrenerServis trenerServis;

    public TrenerController(TrenerServis trenerServis) {
        this.trenerServis = trenerServis;
    }
    
    @GetMapping
    @Operation(summary="Vraća listu svih trenera")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = TrenerDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<TrenerDto>> getAll(){
        return new ResponseEntity<>(trenerServis.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary="Vraća trenera po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = TrenerDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TrenerDto> finById(
        @PathVariable(value = "id") Long id){
        try{
             return new ResponseEntity<>(trenerServis.findById(id), HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TrenerController ex");
        }
       
    }
    
    @PostMapping()
    @Operation(summary="Kreira novog atleticara")
    @ApiResponse(responseCode = "201", content ={
        @Content(schema= @Schema(implementation = TrenerDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TrenerDto> add(
        @Valid @RequestBody @NotNull TrenerDto trenerDto){
        try{
             TrenerDto saved = trenerServis.add(trenerDto);
             return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri kreiranju trenera");
        }
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Brise trenera po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = TrenerDto.class), mediaType = "application/json")
    })
    public ResponseEntity<String> delete(
        @PathVariable(value = "id") Long id){
        try{
             trenerServis.deleteById(id);
             return new ResponseEntity<>("Trener je uspesno obrisan.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Trener "+ id + " ne postoji", HttpStatus.NOT_FOUND);
        }
       
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Azurira trenera po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = TrenerDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TrenerDto> update(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody TrenerDto trenerDto){
        try{
             trenerDto.setIdTrener(id);
             TrenerDto updated = trenerServis.update(trenerDto);
             return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri azuriranju atleticara");
        }
       
    }
    
}
