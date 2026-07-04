package rs.acflash.servis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import rs.acflash.dto.impl.AuthResponse;
import rs.acflash.dto.impl.LoginRequest;
import rs.acflash.entity.impl.Trener;
import rs.acflash.mapper.impl.TrenerMapper;
import rs.acflash.repository.impl.TrenerRepository;
import rs.acflash.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private TrenerRepository trenerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private TrenerMapper trenerMapper;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        trenerMapper = new TrenerMapper();
        authService = new AuthService(authManager, jwtService, trenerRepository, trenerMapper);
    }

    @Test
    void testLoginUspesnoVracaTokenITrenera() {
        LoginRequest req = new LoginRequest();
        req.setKorisnickoIme("petar@fon.rs");
        req.setLozinka("sifra123");

        User principal = new User("petar@fon.rs", "sifra123", Collections.emptyList());
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        when(authManager.authenticate(any())).thenReturn(auth);
        when(jwtService.generate(any(), org.mockito.ArgumentMatchers.eq(Map.of()))).thenReturn("jwt-token-123");

        Trener trener = new Trener(1L, "Petar", "Petrovic", "petar@fon.rs", "sifra123", null);
        when(trenerRepository.findByUsername("petar@fon.rs")).thenReturn(trener);

        AuthResponse odgovor = authService.login(req);

        assertEquals("jwt-token-123", odgovor.getToken());
        assertNotNull(odgovor.getTrener());
        assertEquals("petar@fon.rs", odgovor.getTrener().getKorisnickoIme());
    }
}
