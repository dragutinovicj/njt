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
public class TrenerStrucnostDto implements Dto{
    private Long idTrener;
    private Long idStrucnost;

    @NotBlank(message = "Odlikovanje je obavezno")
    private String odlikovanje;

    public TrenerStrucnostDto() {
    }

    public TrenerStrucnostDto(Long idTrener, Long idStrucnost, String odlikovanje) {
        this.idTrener = idTrener;
        this.idStrucnost = idStrucnost;
        this.odlikovanje = odlikovanje;
    }

    public Long getIdTrener() {
        return idTrener;
    }

    public void setIdTrener(Long idTrener) {
        this.idTrener = idTrener;
    }

    public Long getIdStrucnost() {
        return idStrucnost;
    }

    public void setIdStrucnost(Long idStrucnost) {
        this.idStrucnost = idStrucnost;
    }

    public String getOdlikovanje() {
        return odlikovanje;
    }

    public void setOdlikovanje(String odlikovanje) {
        this.odlikovanje = odlikovanje;
    }
    
    
    
}
