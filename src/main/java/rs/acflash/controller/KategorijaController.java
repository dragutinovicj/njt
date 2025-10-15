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
import rs.acflash.dto.impl.KategorijaDto;
import rs.acflash.servis.KategorijaServis;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/kategorija")
public class KategorijaController {
    private final KategorijaServis kategorijaServis;

    public KategorijaController(KategorijaServis kategorijaServis) {
        this.kategorijaServis = kategorijaServis;
    }

    
    @GetMapping
    @Operation(summary="Vraća listu svih kategorija")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = KategorijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<KategorijaDto>> getAll(){
        return new ResponseEntity<>(kategorijaServis.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary="Vraća kategoriju po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = KategorijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<KategorijaDto> finById(
        @PathVariable(value = "id") Long id){
        try{
             return new ResponseEntity<>(kategorijaServis.findById(id), HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "KategorijaController ex");
        }
       
    }
    
    @PostMapping
    @Operation(summary="Kreira novu kategoriju")
    @ApiResponse(responseCode = "201", content ={
        @Content(schema= @Schema(implementation = KategorijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<KategorijaDto> add(
        @Valid @RequestBody @NotNull KategorijaDto kategorijaDto){
        try{
             KategorijaDto saved = kategorijaServis.add(kategorijaDto);
             return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri kreiranju kategorije");
        }
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Brise kategoriju po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = KategorijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<String> delete(
        @PathVariable(value = "id") Long id){
        try{
             kategorijaServis.deleteById(id);
             return new ResponseEntity<>("Kategorija je uspesno obrisana.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Kategorija "+ id + " ne postoji", HttpStatus.NOT_FOUND);
        }
       
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Azurira kategoriju po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = KategorijaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<KategorijaDto> update(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody KategorijaDto kategorijaDto){
        try{
             kategorijaDto.setIdKategorija(id);
             KategorijaDto updated = kategorijaServis.update(kategorijaDto);
             return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri azuriranju kategorije");
        }
       
    }
}
