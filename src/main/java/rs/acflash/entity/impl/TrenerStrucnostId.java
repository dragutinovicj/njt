/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Korisnik
 */
@Embeddable
public class TrenerStrucnostId implements Serializable{
    private Long idTrener;
    private Long idStrucnost;

    public TrenerStrucnostId() {
    }

    public TrenerStrucnostId(Long idTrener, Long idStrucnost) {
        this.idTrener = idTrener;
        this.idStrucnost = idStrucnost;
    }

    public Long getIdTrener() {
        return idTrener;
    }

    public Long getIdStrucnost() {
        return idStrucnost;
    }
    
    
    
    
            
    
}
