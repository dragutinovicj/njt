/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.dto.impl;

import rs.acflash.dto.Dto;

/**
 *
 * @author Korisnik
 */
public class AuthResponse implements Dto{
    private String token;
    private TrenerDto trener;

    public AuthResponse() {
    }

    public AuthResponse(String token, TrenerDto trener) {
        this.token = token;
        this.trener = trener;
    }

    public AuthResponse(String token) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TrenerDto getTrener() {
        return trener;
    }

    public void setTrener(TrenerDto trener) {
        this.trener = trener;
    }
    
    
    
}
