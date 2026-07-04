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
import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.dto.impl.VezbaDto;
import rs.acflash.servis.VezbaServis;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/vezba")
public class VezbaController {
    private final VezbaServis vezbaServis;

    public VezbaController(VezbaServis vezbaServis) {
        this.vezbaServis = vezbaServis;
    }

   
    
    @GetMapping
    @Operation(summary="Vraća listu svih vezbi")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = VezbaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<VezbaDto>> getAll(){
        return new ResponseEntity<>(vezbaServis.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary="Vraća vezbu po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = VezbaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<VezbaDto> finById(
        @PathVariable(value = "id") Long id){
        try{
             return new ResponseEntity<>(vezbaServis.findById(id), HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "VezbaController ex");
        }
       
    }
    
    @PostMapping()
    @Operation(summary="Kreira novou vezbu")
    @ApiResponse(responseCode = "201", content ={
        @Content(schema= @Schema(implementation = TrenerDto.class), mediaType = "application/json")
    })
    public ResponseEntity<VezbaDto> add(
        @Valid @RequestBody @NotNull VezbaDto vezbaDto){
        try{
             VezbaDto saved = vezbaServis.add(vezbaDto);
             return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri kreiranju vezbe");
        }
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Brise vezbu po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = VezbaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<String> delete(
        @PathVariable(value = "id") Long id){
        try{
             vezbaServis.deleteById(id);
             return new ResponseEntity<>("Vezba je uspesno obrisana.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Vezba "+ id + " ne postoji", HttpStatus.NOT_FOUND);
        }
       
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Azurira vezbu po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = VezbaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<VezbaDto> update(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody VezbaDto vezbaDto){
        try{
             vezbaDto.setIdVezba(id);
             VezbaDto updated = vezbaServis.update(vezbaDto);
             return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri azuriranju atleticara");
        }
       
    }
    
}
