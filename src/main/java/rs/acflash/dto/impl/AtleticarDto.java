/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import java.util.Date;
import org.hibernate.validator.constraints.URL;
import rs.acflash.dto.Dto;
import rs.acflash.entity.impl.Kategorija;

/**
 *
 * @author Korisnik
 */
public class AtleticarDto implements Dto{
    private Long idAtleticar;
    @NotBlank(message = "Ime atleticara je obavezno")
    private String ime;
    @NotBlank(message = "Prezime atleticara je obavezno")
    private String prezime;
    @NotNull(message = "Datum rodjenja je obavezan")
    @Past(message = "Datum rodjenja mora biti u proslosti")
    private Date datumRodjenja;
    @NotBlank(message = "Pol je obavezan")
    private String pol;
    @Positive(message = "Visina mora biti veca od 0")
    private Double visina;
    @Positive(message = "Tezina mora biti veca od 0")
    private Double tezina;
    private Double bmi;
    @NotNull(message = "Kategorija je obavezna")
    private Long idKategorija;
    @URL(message = "Unesite validan URL za sliku")
    private String imageUrl;
    
    public AtleticarDto() {
    }

    public AtleticarDto(Long idAtleticar, String ime, String prezime, Date datumRodjenja, String pol, Double visina, Double tezina, Double bmi, Long idKategorija, String imageUrl) {
        this.idAtleticar = idAtleticar;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.pol = pol;
        this.visina = visina;
        this.tezina = tezina;
        this.bmi = bmi;
        this.idKategorija = idKategorija;
        this.imageUrl = imageUrl;
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

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public Long getIdAtleticar() {
        return idAtleticar;
    }

    public void setIdAtleticar(Long idAtleticar) {
        this.idAtleticar = idAtleticar;
    }

    public Double getVisina() {
        return visina;
    }

    public void setVisina(Double visina) {
        this.visina = visina;
    }

    public Double getTezina() {
        return tezina;
    }

    public void setTezina(Double tezina) {
        this.tezina = tezina;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getIdKategorija() {
        return idKategorija;
    }

    public void setIdKategorija(Long idKategorija) {
        this.idKategorija = idKategorija;
    }

    
   
    
    
    
}
