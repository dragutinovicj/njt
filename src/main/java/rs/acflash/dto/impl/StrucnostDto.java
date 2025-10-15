/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import rs.acflash.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class StrucnostDto implements Dto{
    private Long idStrucnost;

    @NotBlank(message = "Naziv strucnosti je obavezan")
    @Size(max = 100, message = "Naziv ne sme biti duži od 100 karaktera")
    private String naziv;

    @NotBlank(message = "Opis strucnosti je obavezan")
    @Size(max = 500, message = "Opis ne sme biti duži od 500 karaktera")
    private String opis;

    public StrucnostDto() {
    }

    public StrucnostDto(Long idStrucnost, String naziv, String opis) {
        this.idStrucnost = idStrucnost;
        this.naziv = naziv;
        this.opis = opis;
    }

    public Long getIdStrucnost() {
        return idStrucnost;
    }

    public void setIdStrucnost(Long idStrucnost) {
        this.idStrucnost = idStrucnost;
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
