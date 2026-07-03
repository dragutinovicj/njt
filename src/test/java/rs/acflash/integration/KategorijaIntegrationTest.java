package rs.acflash.integration;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import rs.acflash.dto.impl.KategorijaDto;

/**
 * Integracioni test - podize ceo Spring kontekst i pravu (H2 in-memory) bazu.
 * Za razliku od KategorijaControllerTest (koji mokuje servis), ovde se
 * proverava ceo lanac: HTTP -> Controller -> Servis -> Repository -> baza.
 *
 * Security filteri su iskljuceni (addFilters = false) jer je fokus ovde
 * na CRUD logici; auth tok se posebno proverava u AuthIntegrationTest.
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class KategorijaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPunKrugCrud() throws Exception {
        KategorijaDto novaDto = new KategorijaDto(null, "Sprint", "Kratkoprugaske discipline");

        String odgovor = mockMvc.perform(post("/api/kategorija")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.naziv").value("Sprint"))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(odgovor).get("idKategorija").asLong();

        mockMvc.perform(get("/api/kategorija/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv").value("Sprint"));

        mockMvc.perform(get("/api/kategorija"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)));

        KategorijaDto izmenjenaDto = new KategorijaDto(null, "Sprint", "Novi opis");
        mockMvc.perform(put("/api/kategorija/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(izmenjenaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opis").value("Novi opis"));

        mockMvc.perform(delete("/api/kategorija/" + id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/kategorija/" + id))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddSaNevalidnimPodacimaVraca400() throws Exception {
        KategorijaDto nevalidna = new KategorijaDto(null, "", "");

        mockMvc.perform(post("/api/kategorija")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nevalidna)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindByIdNepostojeciVraca400() throws Exception {
        mockMvc.perform(get("/api/kategorija/999999"))
                .andExpect(status().isBadRequest());
    }
}
