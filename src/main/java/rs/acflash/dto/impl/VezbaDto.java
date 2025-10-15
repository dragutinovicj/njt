/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rs.acflash.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class VezbaDto implements Dto{
    private Long idVezba;

    @NotBlank(message = "Naziv vežbe je obavezan")
    @Size(max = 100, message = "Naziv vežbe ne sme biti duži od 100 karaktera")
    private String naziv;

    @NotBlank(message = "Tip vežbe je obavezan")
    private String tip;

    @NotBlank(message = "Opis vežbe je obavezan")
    @Size(max = 500, message = "Opis vežbe ne sme biti duži od 500 karaktera")
    private String opis;

    @NotNull(message = "Težina vežbe je obavezna")
    @Min(value = 0, message = "Težina vežbe ne sme biti negativna")
    private Integer tezina;

    public VezbaDto() {
    }

    public VezbaDto(Long idVezba, String naziv, String tip, String opis, Integer tezina) {
        this.idVezba = idVezba;
        this.naziv = naziv;
        this.tip = tip;
        this.opis = opis;
        this.tezina = tezina;
    }

    public Long getIdVezba() {
        return idVezba;
    }

    public void setIdVezba(Long idVezba) {
        this.idVezba = idVezba;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getTezina() {
        return tezina;
    }

    public void setTezina(Integer tezina) {
        this.tezina = tezina;
    }
    
    
}
