package rs.acflash.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import rs.acflash.dto.impl.LoginRequest;
import rs.acflash.entity.impl.Trener;
import rs.acflash.repository.impl.TrenerRepository;

/**
 * Integracioni test pravog bezbednosnog toka: prijava (JWT generisanje)
 * i pristup zasticenom endpointu sa/bez tokena. Ovde security filteri
 * OSTAJU ukljuceni (za razliku od KategorijaIntegrationTest), jer je
 * bas to sto se testira.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrenerRepository trenerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void kreirajTrenera(String korisnickoIme, String lozinka) {
        Trener t = new Trener(null, "Petar", "Petrovic", korisnickoIme, passwordEncoder.encode(lozinka), null);
        trenerRepository.save(t);
    }

    @Test
    void testPristupZasticenomEndpointuBezTokenaJeOdbijen() throws Exception {
        mockMvc.perform(get("/api/kategorija"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testLoginUspesnoIPristupZasticenomEndpointuSaTokenom() throws Exception {
        kreirajTrenera("petar@fon.rs", "sifra123");

        LoginRequest req = new LoginRequest();
        req.setKorisnickoIme("petar@fon.rs");
        req.setLozinka("sifra123");

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.trener.korisnickoIme").value("petar@fon.rs"))
                .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString()).get("token").asText();

        mockMvc.perform(get("/api/kategorija")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginPogresnaLozinkaNijeUspesan() throws Exception {
        kreirajTrenera("ana@fon.rs", "tacnasifra");

        LoginRequest req = new LoginRequest();
        req.setKorisnickoIme("ana@fon.rs");
        req.setLozinka("pogresnasifra");

        // Napomena: AuthenticationException baceno iz AuthController#login
        // propagira se kroz Spring Security filter lanac (ExceptionTranslationFilter),
        // koji ga hvata i vraca 403 Access Denied - ne 401 (jer nema
        // custom AuthenticationEntryPoint) i ne 500 (jer Security lanac
        // uhvati AuthenticationException pre nego sto stigne do Spring MVC-a).
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testPristupSaNevalidnimTokenomJeOdbijen() throws Exception {
        mockMvc.perform(get("/api/kategorija")
                        .header("Authorization", "Bearer ovo-nije-validan-token"))
                .andExpect(status().is4xxClientError());
    }
}