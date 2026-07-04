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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import rs.acflash.dto.impl.TrenerStrucnostDto;
import rs.acflash.entity.impl.TrenerStrucnostId;
import rs.acflash.servis.TrenerStrucnostServis;

/**
 *
 * @author Korisnik
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/trener_strucnost")
public class TrenerStrucnostController {
    private final TrenerStrucnostServis trenerStrucnostServis;

    public TrenerStrucnostController(TrenerStrucnostServis trenerStrucnostServis) {
        this.trenerStrucnostServis = trenerStrucnostServis;
    }

    
    @GetMapping
    @Operation(summary="Vraća listu svih odlikovanja")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = TrenerStrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<TrenerStrucnostDto>> getAll(){
        return new ResponseEntity<>(trenerStrucnostServis.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/trener/{idTrener}")
    @Operation(summary = "Vraća sve stručnosti određenog trenera")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = TrenerStrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<TrenerStrucnostDto>> getByTrenerId(@PathVariable Long idTrener) {
        List<TrenerStrucnostDto> lista = trenerStrucnostServis.findByTrenerId(idTrener);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    
     @GetMapping("/strucnost/{idStrucnost}")
    @Operation(summary = "Vraća sve trenere koji imaju određenu stručnost")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = TrenerStrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<TrenerStrucnostDto>> getByStrucnostId(@PathVariable Long idStrucnost) {
        List<TrenerStrucnostDto> lista = trenerStrucnostServis.findByStrucnostId(idStrucnost);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    
    @GetMapping("/{idTrener}/{idStrucnost}")
    @Operation(summary = "Vraća tačno određeni odnos između trenera i stručnosti")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = TrenerStrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TrenerStrucnostDto> getSpecificRelation(
            @PathVariable Long idTrener,
            @PathVariable Long idStrucnost) {

        TrenerStrucnostId id = new TrenerStrucnostId(idTrener, idStrucnost);
        TrenerStrucnostDto dto = null;
        try {
            dto = trenerStrucnostServis.findById(id);
        } catch (Exception ex) {
            Logger.getLogger(TrenerStrucnostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dto != null
                ? new ResponseEntity<>(dto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    @Operation(summary = "Dodaje novu vezu trenera i stručnosti (npr. novo odlikovanje)")
    @ApiResponse(responseCode = "201", content = {
        @Content(schema = @Schema(implementation = TrenerStrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TrenerStrucnostDto> add(@Valid @RequestBody @NotNull TrenerStrucnostDto dto) {
        TrenerStrucnostDto saved = null;
        try {
            saved = trenerStrucnostServis.add(dto);
        } catch (Exception ex) {
            Logger.getLogger(TrenerStrucnostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
   @DeleteMapping("/{idTrener}/{idStrucnost}")
    @Operation(summary = "Briše tačno određenu vezu trenera i stručnosti")
    public ResponseEntity<String> delete(@PathVariable Long idTrener, @PathVariable Long idStrucnost) {
        try {
            TrenerStrucnostId id = new TrenerStrucnostId(idTrener, idStrucnost);
            trenerStrucnostServis.deleteById(id);
            return new ResponseEntity<>("Veza trenera i stručnosti uspešno obrisana.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Veza nije pronađena.", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{idTrener}/{idStrucnost}")
    @Operation(summary = "Ažurira odlikovanje za datog trenera i stručnost")
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = TrenerStrucnostDto.class), mediaType = "application/json")
    })
    public ResponseEntity<TrenerStrucnostDto> update(
            @PathVariable Long idTrener,
            @PathVariable Long idStrucnost,
            @Valid @RequestBody TrenerStrucnostDto dto) {

        dto.setIdTrener(idTrener);
        dto.setIdStrucnost(idStrucnost);
        TrenerStrucnostDto updated = null;
        try {
            updated = trenerStrucnostServis.update(dto);
        } catch (Exception ex) {
            Logger.getLogger(TrenerStrucnostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    
    
}
