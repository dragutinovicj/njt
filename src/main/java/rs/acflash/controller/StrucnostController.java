/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
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
import rs.acflash.dto.impl.StrucnostDto;
import rs.acflash.servis.StrucnostServis;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/strucnost")
public class StrucnostController {

    private final StrucnostServis strucnostServis;

    public StrucnostController(StrucnostServis strucnostServis) {
        this.strucnostServis = strucnostServis;
    }

    @GetMapping
    @Operation(summary = "Vraća listu svih strucnosti")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = StrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<StrucnostDto>> getAll() {
        return new ResponseEntity<>(strucnostServis.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Vraća strucnost po ID-ju")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = StrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<StrucnostDto> finById(
            @PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(strucnostServis.findById(id), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StrucnostController ex");
        }

    }
    
    @PostMapping()
    @Operation(summary="Kreira novou strucnost")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = StrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<StrucnostDto> add(
        @Valid @RequestBody @NotNull StrucnostDto strucnostDto){
        try{
             StrucnostDto saved = strucnostServis.add(strucnostDto);
             return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri kreiranju atleticara");
        }
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Brise strucnost po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = StrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<String> delete(
        @PathVariable(value = "id") Long id){
        try{
             strucnostServis.deleteById(id);
             return new ResponseEntity<>("Strucnost je uspesno obrisana.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Strucnost "+ id + " ne postoji", HttpStatus.NOT_FOUND);
        }
       
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Azurira strucnost po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = StrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<StrucnostDto> update(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody StrucnostDto strucnostDto){
        try{
             strucnostDto.setIdStrucnost(id);
             StrucnostDto updated = strucnostServis.update(strucnostDto);
             return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri azuriranju atleticara");
        }
       
    }
    

}
