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
import rs.acflash.dto.impl.PlanTreningaDto;
import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.servis.PlanTreningaServis;
import rs.acflash.servis.TrenerServis;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/plan_treninga")
public class PlanTreningaController {
     private final PlanTreningaServis planTreningaServis;

    public PlanTreningaController(PlanTreningaServis planTreningaServis) {
        this.planTreningaServis = planTreningaServis;
    }
    
    @GetMapping
    @Operation(summary="Vraća listu svih planova")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = PlanTreningaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<List<PlanTreningaDto>> getAll(){
        return new ResponseEntity<>(planTreningaServis.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @Operation(summary="Vraća plan po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = PlanTreningaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<PlanTreningaDto> finById(
        @PathVariable(value = "id") Long id){
        try{
             return new ResponseEntity<>(planTreningaServis.findById(id), HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PlanTreningaController ex");
        }
       
    }
    
    @PostMapping()
    @Operation(summary="Kreira novi plan treninga")
    @ApiResponse(responseCode = "201", content ={
        @Content(schema= @Schema(implementation = PlanTreningaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<PlanTreningaDto> add(
        @Valid @RequestBody @NotNull PlanTreningaDto planTreningaDto){
        try{
             PlanTreningaDto saved = planTreningaServis.add(planTreningaDto);
             return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri kreiranju plana");
        }
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Brise plaan po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = PlanTreningaDto.class), mediaType = "application/json")
    })
    public ResponseEntity<String> delete(
        @PathVariable(value = "id") Long id){
        try{
             planTreningaServis.deleteById(id);
             return new ResponseEntity<>("Plan je uspesno obrisan.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Plan "+ id + " ne postoji", HttpStatus.NOT_FOUND);
        }
       
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Azurira plan po ID-ju")
    @ApiResponse(responseCode = "200", content ={
        @Content(schema= @Schema(implementation = TrenerDto.class), mediaType = "application/json")
    })
    public ResponseEntity<PlanTreningaDto> update(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody PlanTreningaDto planTreningaDto){
        try{
             planTreningaDto.setIdPlan(id);
             PlanTreningaDto updated = planTreningaServis.update(planTreningaDto);
             return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Greska pri azuriranju plana");
        }
       
    }
    
}
