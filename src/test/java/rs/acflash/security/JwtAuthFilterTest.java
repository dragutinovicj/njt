package rs.acflash.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AppUserDetailsService appUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthFilter(jwtService, appUserDetailsService);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testLoginPutanjaPreskacefilter() throws Exception {
        when(request.getServletPath()).thenReturn("/api/auth/login");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(any());
    }

    @Test
    void testBezAuthHeaderaNastavljaBezAutentifikacije() throws Exception {
        when(request.getServletPath()).thenReturn("/api/kategorija");
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNullAuthentication();
    }

    @Test
    void testAuthHeaderBezBearerPrefiksaNastavljaBezAutentifikacije() throws Exception {
        when(request.getServletPath()).thenReturn("/api/kategorija");
        when(request.getHeader("Authorization")).thenReturn("NekiDrugiHeader token123");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNullAuthentication();
    }

    @Test
    void testValidanTokenPostavljaAutentifikaciju() throws Exception {
        when(request.getServletPath()).thenReturn("/api/kategorija");
        when(request.getHeader("Authorization")).thenReturn("Bearer validan-token");
        when(jwtService.extractUsername("validan-token")).thenReturn("petar@fon.rs");

        UserDetails userDetails = new User("petar@fon.rs", "sifra", java.util.Collections.emptyList());
        when(appUserDetailsService.loadUserByUsername("petar@fon.rs")).thenReturn(userDetails);
        when(jwtService.isValid("validan-token", userDetails)).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        Object auth = SecurityContextHolder.getContext().getAuthentication();
        org.junit.jupiter.api.Assertions.assertNotNull(auth);
        org.junit.jupiter.api.Assertions.assertTrue(auth instanceof UsernamePasswordAuthenticationToken);
    }

    @Test
    void testNevalidanTokenNePostavljaAutentifikaciju() throws Exception {
        when(request.getServletPath()).thenReturn("/api/kategorija");
        when(request.getHeader("Authorization")).thenReturn("Bearer nevalidan-token");
        when(jwtService.extractUsername("nevalidan-token")).thenReturn("petar@fon.rs");

        UserDetails userDetails = new User("petar@fon.rs", "sifra", java.util.Collections.emptyList());
        when(appUserDetailsService.loadUserByUsername("petar@fon.rs")).thenReturn(userDetails);
        when(jwtService.isValid("nevalidan-token", userDetails)).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNullAuthentication();
    }

    @Test
    void testTokenBacaIzuzetakNePostavljaAutentifikaciju() throws Exception {
        when(request.getServletPath()).thenReturn("/api/kategorija");
        when(request.getHeader("Authorization")).thenReturn("Bearer los-token");
        when(jwtService.extractUsername("los-token")).thenThrow(new RuntimeException("Token parsing failed"));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNullAuthentication();
    }

    @Test
    void testVecPostojecaAutentifikacijaSePreskace() throws Exception {
        when(request.getServletPath()).thenReturn("/api/kategorija");
        when(request.getHeader("Authorization")).thenReturn("Bearer validan-token");
        when(jwtService.extractUsername("validan-token")).thenReturn("petar@fon.rs");

        UserDetails postojeciKorisnik = new User("neko-drugi@fon.rs", "sifra", java.util.Collections.emptyList());
        UsernamePasswordAuthenticationToken postojecaAuth =
                new UsernamePasswordAuthenticationToken(postojeciKorisnik, null, postojeciKorisnik.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(postojecaAuth);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(appUserDetailsService, never()).loadUserByUsername(any());
    }

    private void assertNullAuthentication() {
        org.junit.jupiter.api.Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}