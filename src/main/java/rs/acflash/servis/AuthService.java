/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.servis;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.acflash.dto.impl.AuthResponse;
import rs.acflash.dto.impl.LoginRequest;
import rs.acflash.entity.impl.Trener;
import rs.acflash.mapper.impl.TrenerMapper;
import rs.acflash.repository.impl.TrenerRepository;
import rs.acflash.security.JwtService;

/**
 *
 * @author Korisnik
 */
@Service
public class AuthService {
    
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final TrenerRepository treneri;
    private final TrenerMapper mapper;

    @Autowired
    public AuthService(AuthenticationManager authManager, JwtService jwt, TrenerRepository treneri, TrenerMapper mapper) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.treneri = treneri;
        this.mapper = mapper;
    }

    public AuthResponse login(LoginRequest req) {
    // Autentifikacija
    Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getKorisnickoIme(), req.getLozinka())
    );

    // Generisanje JWT tokena
    String token = jwt.generate(
            (org.springframework.security.core.userdetails.User) auth.getPrincipal(),
            Map.of() // dodatni claim-ovi, ovde prazno
    );
    Trener t =(Trener) treneri.findByUsername(req.getKorisnickoIme());
    

    // Vraćamo AuthResponse sa tokenom
    return new AuthResponse(token, mapper.toDto(t));
}

    
}
