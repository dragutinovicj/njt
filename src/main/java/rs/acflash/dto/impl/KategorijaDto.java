/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import jakarta.validation.constraints.NotBlank;
import rs.acflash.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class KategorijaDto implements Dto{
    private Long idKategorija;
    @NotBlank(message = "Naziv kategorije je obavezan")
    private String naziv;
    @NotBlank(message = "Opis kategorije je obavezan")
    private String opis; 

    public KategorijaDto() {
    }

    public KategorijaDto(Long idKategorija, String naziv, String opis) {
        this.idKategorija = idKategorija;
        this.naziv = naziv;
        this.opis = opis;
    }

    public Long getIdKategorija() {
        return idKategorija;
    }

    public void setIdKategorija(Long idKategorija) {
        this.idKategorija = idKategorija;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    
}
