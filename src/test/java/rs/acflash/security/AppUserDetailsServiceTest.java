package rs.acflash.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import rs.acflash.entity.impl.Trener;
import rs.acflash.repository.impl.TrenerRepository;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {

    @Mock
    private TrenerRepository trenerRepository;

    private AppUserDetailsService service;

    @BeforeEach
    void setUp() {
        service = new AppUserDetailsService(trenerRepository);
    }

    @Test
    void testLoadUserByUsernamePostojeciTrener() {
        Trener t = new Trener(1L, "Petar", "Petrovic", "petar@fon.rs", "sifra123", null);
        when(trenerRepository.findByUsername("petar@fon.rs")).thenReturn(t);

        UserDetails details = service.loadUserByUsername("petar@fon.rs");

        assertEquals("petar@fon.rs", details.getUsername());
        assertEquals("sifra123", details.getPassword());
        assertEquals(0, details.getAuthorities().size());
    }

    @Test
    void testLoadUserByUsernameNepostojeciBacaIzuzetak() {
        when(trenerRepository.findByUsername("nepostojeci")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("nepostojeci"));
    }
}
