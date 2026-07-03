package rs.acflash.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", "ovo-je-tajni-kljuc-za-testiranje-jwt-servisa-1234567890");
        ReflectionTestUtils.setField(jwtService, "expirationMs", 60_000L);
    }

    private UserDetails korisnik(String username) {
        return new User(username, "sifra", java.util.Collections.emptyList());
    }

    @Test
    void testGenerateVracaNepraznToken() {
        String token = jwtService.generate(korisnik("petar@fon.rs"), Map.of());

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generate(korisnik("petar@fon.rs"), Map.of());

        assertEquals("petar@fon.rs", jwtService.extractUsername(token));
    }

    @Test
    void testIsValidZaIstogKorisnika() {
        UserDetails korisnik = korisnik("petar@fon.rs");
        String token = jwtService.generate(korisnik, Map.of());

        assertTrue(jwtService.isValid(token, korisnik));
    }

    @Test
    void testIsValidZaDrugogKorisnikaVracaFalse() {
        String token = jwtService.generate(korisnik("petar@fon.rs"), Map.of());

        assertFalse(jwtService.isValid(token, korisnik("neko-drugi@fon.rs")));
    }

    @Test
    void testIsValidIstekaoTokenVracaFalse() {
        ReflectionTestUtils.setField(jwtService, "expirationMs", -60_000L);
        UserDetails korisnik = korisnik("petar@fon.rs");
        String token = jwtService.generate(korisnik, Map.of());

        assertFalse(jwtService.isValid(token, korisnik));
    }

    @Test
    void testIsValidNevalidanTokenVracaFalse() {
        assertFalse(jwtService.isValid("nevalidan.token.string", korisnik("petar@fon.rs")));
    }
}
