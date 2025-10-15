/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.entity.impl;

import jakarta.persistence.Embeddable;

/**
 *
 * @author Korisnik
 */
@Embeddable
public class StavkaPlanaId {
    
    private Long rb;
    private Long idPlan;

    public StavkaPlanaId() {
    }

    public StavkaPlanaId(Long rb, Long idPlan) {
        this.rb = rb;
        this.idPlan = idPlan;
    }

    public Long getRb() {
        return rb;
    }

    public void setRb(Long rb) {
        this.rb = rb;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }
    
    
    
    
}
