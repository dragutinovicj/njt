/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.acflash.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.acflash.dto.impl.AuthResponse;
import rs.acflash.dto.impl.LoginRequest;
import rs.acflash.dto.impl.TrenerDto;
import rs.acflash.entity.impl.Trener;
import rs.acflash.repository.impl.TrenerRepository;
import rs.acflash.servis.AuthService;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    private final TrenerRepository treneri;

    public AuthController(AuthService authService, TrenerRepository treneri) {
        this.authService = authService;
        this.treneri = treneri;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Korisnik nije autentifikovan");
        }

        
        Trener t = (Trener) treneri.findByUsername(auth.getName());
        if (t == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Trener nije pronađen");
        }

        
        TrenerDto dto = new TrenerDto(
                t.getIdTrener(),
                t.getIme(),
                t.getPrezime(),
                t.getKorisnickoIme(),
                t.getLozinka() 
        );

        return ResponseEntity.ok(dto);
    }

}
