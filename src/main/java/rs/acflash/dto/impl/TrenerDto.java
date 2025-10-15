/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import rs.acflash.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class TrenerDto implements Dto{
    private Long idTrener;

    @NotBlank(message = "Ime je obavezno")
    @Size(max = 50)
    private String ime;

    @NotBlank(message = "Prezime je obavezno")
    @Size(max = 50)
    private String prezime;

    @NotBlank(message = "Korisničko ime je obavezno")
    @Pattern(regexp = ".*@.*", message = "Korisničko ime mora sadržati znak '@'")
    private String korisnickoIme;

    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 8, message = "Lozinka mora imati minimum 8 karaktera")
    private String lozinka;

    public TrenerDto() {
    }

    public TrenerDto(Long idTrener, String ime, String prezime, String korisnickoIme, String lozinka) {
        this.idTrener = idTrener;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Long getIdTrener() {
        return idTrener;
    }

    public void setIdTrener(Long idTrener) {
        this.idTrener = idTrener;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    
    
}
